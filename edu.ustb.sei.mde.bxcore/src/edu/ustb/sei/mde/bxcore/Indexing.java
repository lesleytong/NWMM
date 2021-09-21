package edu.ustb.sei.mde.bxcore;

import edu.ustb.sei.mde.bxcore.exceptions.BidirectionalTransformationDefinitionException;
import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.bxcore.exceptions.UninitializedException;
import edu.ustb.sei.mde.bxcore.structures.Context;
import edu.ustb.sei.mde.bxcore.structures.FieldDef;
import edu.ustb.sei.mde.bxcore.structures.Index;
import edu.ustb.sei.mde.graph.typedGraph.IndexableElement;
import edu.ustb.sei.mde.graph.typedGraph.constraint.GraphConstraint;
import edu.ustb.sei.mde.structure.Tuple2;

public class Indexing extends XmuCore {
	
	private XmuCore body;
	private Tuple2<String,String>[] sourceKeys;
	private Tuple2<String,String>[] viewKeys;
	
	private IndexSignature signature;
	private IndexingFunction indexingFunction;

	@SuppressWarnings("unchecked")
	public Indexing(IndexSignature signature, String[] sourceKeys, String[] viewKeys, XmuCore body) 
			throws BidirectionalTransformationDefinitionException {
		super(signature.getSignatureName(), body.getSourceDef(), body.getViewDef());
		this.signature = signature;
		this.body = body;
		
		
		this.sourceKeys = new Tuple2[sourceKeys.length];
		this.viewKeys = new Tuple2[viewKeys.length];
		
		for(int i=0;i<sourceKeys.length;i++) {
			this.sourceKeys[i] = new Tuple2<String, String>(sourceKeys[i],signature.getSourceType().orderedFields().get(i).getName());
		}
		
		for(int i=0;i<viewKeys.length;i++) {
			this.viewKeys[i] = new Tuple2<String, String>(viewKeys[i],signature.getViewType().orderedFields().get(i).getName());
		}
		
		indexingFunction = new IndexingFunction();
		
		checkWellDefinedness();
	}
	
	@Override
	protected void checkWellDefinedness() throws BidirectionalTransformationDefinitionException {
		if(this.sourceKeys.length!=signature.getSourceType().fields().size()
				|| this.viewKeys.length!=signature.getViewType().fields().size())
			throw new BidirectionalTransformationDefinitionException("Incorrect parameter length");
		
		for(Tuple2<String, String> m : this.sourceKeys) {
			FieldDef<?> up = this.getSourceDef().getField(m.first);
			if(up==null)
				throw new BidirectionalTransformationDefinitionException("Invalid actual parameter "+m.first);
		}
		
		for(Tuple2<String, String> m : this.viewKeys) {
			FieldDef<?> up = this.getViewDef().getField(m.first);
			if(up==null)
				throw new BidirectionalTransformationDefinitionException("Invalid actual parameter "+m.first);
		}
	}
	
	

	@Override
	public ViewType forward(SourceType s) throws NothingReturnedException {
		Context source = s.second.createDownstreamContext(this.signature.getSourceType(), this.sourceKeys, false);
		source.setUpstream(s.second,this.sourceKeys);
		Context viewIndexing = null;
		
		try {
			viewIndexing = this.indexingFunction.computeForwardIndices(source, this.signature.getViewType());
		} catch (UninitializedException e3) {
			return nothing(e3);
		}
		
		viewIndexing.setUpstream(this.getViewDef(), this.viewKeys);
		
		TraceSystem newSystem = s.third.getCopy();
		newSystem.trace(this.getSerializationKey(), source, viewIndexing, source); // if trace exists, viewIndexing should not be record
		
		ViewType v = this.body.forward(SourceType.makeSource(s.first, s.second, newSystem));
		if(v==null || v==ViewType.empty()) return v;
		
		viewIndexing.setUpstream(v.second,this.viewKeys); // useless in forward transformation
		
		// handle primitive view variables
		try {
			for(FieldDef<?> f : this.signature.getViewType().fields()) {
				if(!(f.isElementType() || f.isPathType())) {
					FieldDef<?> uf = viewIndexing.getUpKeyFromDownstreamKey(f);
					if(uf!=null) {
						viewIndexing.setValue(f, v.second.getValue(uf));					
					} else {
						nothing();
					}
				}
			}
		} catch (UninitializedException e) {
			nothing(e);
		}
		
		for(int i=0;i<viewKeys.length;i++) {
			String k = this.viewKeys[i].first;
			FieldDef<?> upK = this.getViewDef().getField(k);
			if(upK.isElementType()) {
				IndexableElement e;
				try {
					e = v.first.getElementByIndexObject(v.second.getIndexValue(upK));
					Index ip = viewIndexing.getIndexValue(this.signature.getViewType().getField(this.viewKeys[i].second));	// here, we must fix to support delta-based BXs
					v.first.addIndex(ip, e);
					
				} catch (UninitializedException e1) {
					return nothing(e1);
				}
			}
		}
		
		return v;
	}

	@Override
	public SourceType backward(SourceType s, ViewType v) throws NothingReturnedException {		
		Context source = s.second.createDownstreamContext(this.signature.getSourceType(), this.sourceKeys, false);
		source.setUpstream(s.second,this.sourceKeys);
		
		Context view = v.second.createDownstreamContext(this.signature.getViewType(), viewKeys, false);
		view.setUpstream(v.second,this.viewKeys);
		
		try {
			Context sourcePost = this.indexingFunction.computeBackwardIndices(source, view);
			sourcePost.setUpstream(s.second, this.sourceKeys); // temporally set to s.second, useless in backward transformation
			
			TraceSystem newSystem = s.third.getCopy();
			newSystem.trace(this.getSerializationKey(), source,view,sourcePost);
			
			SourceType sp = body.backward(SourceType.makeSource(s.first, s.second, newSystem), v);
			
			Context traceSourcePost = sourcePost.getType().createInstance();
			
			for(int i=0;i<this.sourceKeys.length;i++) {
				String k = this.sourceKeys[i].first;
				FieldDef<?> upK = this.getSourceDef().getField(k);
				
				if(upK.isElementType()) {
					IndexableElement e;
					try {
						e = sp.first.getElementByIndexObject(sp.second.getIndexValue(upK));
						
						Index ip = sourcePost.getIndexValue(this.signature.getSourceType().getField(this.sourceKeys[i].second));						
						sp.first.addIndex(ip, e);
						
						traceSourcePost.setValue(this.sourceKeys[i].second, ip);
					} catch (UninitializedException e1) {
						return nothing(e1);
					}
				} else {
					traceSourcePost.setValue(this.sourceKeys[i].second, sourcePost.getValue(this.sourceKeys[i].second));
				}
			}
			
			sp.third.trace(this.getSerializationKey(), source, view, traceSourcePost);
			
			return sp;
		} catch(Exception e) {
			return nothing(e);
		}
	}
	
	@Override
	protected GraphConstraint generateConsistencyConstraint() {
		return body.getConsistencyConstraint();
	}

}
