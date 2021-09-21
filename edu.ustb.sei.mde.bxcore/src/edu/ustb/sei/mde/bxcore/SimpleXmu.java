package edu.ustb.sei.mde.bxcore;

import java.util.Collection;

import edu.ustb.sei.mde.bxcore.bigul.BidirectionalTransformation;
import edu.ustb.sei.mde.bxcore.exceptions.BidirectionalTransformationDefinitionException;
import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.bxcore.exceptions.UninitializedException;
import edu.ustb.sei.mde.bxcore.structures.Context;
import edu.ustb.sei.mde.bxcore.structures.ContextType;
import edu.ustb.sei.mde.bxcore.structures.FieldDef;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;
import edu.ustb.sei.mde.graph.typedGraph.constraint.GraphConstraint;

public class SimpleXmu extends XmuCore {

	public SimpleXmu(Object key, ContextType sourceDef, ContextType viewDef, 
			String[] sourceKeys,
			String[] viewKeys,
			BidirectionalTransformation<Object[], Object[]> bodyConversion) throws BidirectionalTransformationDefinitionException {
		super(key, sourceDef, viewDef);
		this.bodyConversion = bodyConversion;
		this.sourceKeys = sourceKeys;
		this.viewKeys = viewKeys;
		
		checkWellDefinedness();
	}
	
	@Override
	protected void checkWellDefinedness() throws BidirectionalTransformationDefinitionException {
		check(this.getSourceDef(), sourceKeys);
		check(this.getViewDef(), viewKeys);
	}

	protected void check(ContextType sourceDef, String[] sourceKeys) 
			throws BidirectionalTransformationDefinitionException {
		
		for(int i=0;i<sourceKeys.length;i++) {
			for(int j=i+1;j<sourceKeys.length;j++) {
				if(sourceKeys[i].equals(sourceKeys[j]))
					throw new BidirectionalTransformationDefinitionException("Duplicate keys");
			}
		}
		
		Collection<FieldDef<?>> sk = sourceDef.fields();
		
		if(sk.stream().allMatch(s->{
			if(s.isElementType()) return true;
			for(String ss : sourceKeys)
				if(ss.equals(s.getName())) return true;
			return false;
		})==false) throw new BidirectionalTransformationDefinitionException("All primitive values must be converted.");
		
		for(String ss : sourceKeys) 
			if(sk.stream().anyMatch(s->s.getName().equals(ss))==false) 
				throw new BidirectionalTransformationDefinitionException("Invalid keys: "+ss);
	}
	
	private String[] sourceKeys;
	private String[] viewKeys;
	private BidirectionalTransformation<Object[], Object[]> bodyConversion;

	@Override
	protected GraphConstraint generateConsistencyConstraint() {
		return GraphConstraint.TRUE;
	}

	@Override
	public ViewType forward(SourceType s) throws NothingReturnedException {
		Context upstreamSource = s.second;
		Context viewMatch = createViewContext();
		
		Object[] sources = new Object[sourceKeys.length];
		for(int i=0;i<sourceKeys.length;i++) {
			try {
				sources[i] = upstreamSource.getPrimitiveValue(this.getSourceDef().getField(sourceKeys[i]));
			} catch (UninitializedException e) {
				return nothing();
			}
		}
		
		Object[] views = bodyConversion.forward(sources);
		for(int i=0;i<viewKeys.length;i++) {
			viewMatch.setValue(viewKeys[i], views[i]);
		}
		
		TypedGraph view = new TypedGraph(null);
		view.setConstraint(getConsistencyConstraint());
		return ViewType.makeView(view, viewMatch);
	}

	@Override
	public SourceType backward(SourceType s, ViewType v) throws NothingReturnedException {
		Context upstreamSource = s.second;
		upstreamSource.setWillCreateElement(true);
		
		Context upstreamView = v.second;
		upstreamView.setWillCreateElement(true);
		
		Context sourceMatchNew = createSourceContext();
		sourceMatchNew.initWith(upstreamSource);
		
		Object[] sources = new Object[sourceKeys.length];
		for(int i=0;i<sourceKeys.length;i++) {
			try {
				sources[i] = upstreamSource.getPrimitiveValue(this.getSourceDef().getField(sourceKeys[i]));
			} catch (UninitializedException e) {
				sources[i] = null;
			}
		}
		
		Object[] views = new Object[viewKeys.length];
		for(int i=0;i<viewKeys.length;i++) {
			try {
				views[i] = upstreamView.getPrimitiveValue(this.getViewDef().getField(viewKeys[i]));
			} catch (UninitializedException e) {
				nothing(e);
			}
		}
		
		Object[] sourcesNew = bodyConversion.backward(sources, views);
		for(int i=0;i<sourceKeys.length;i++) {
			sourceMatchNew.setValue(sourceKeys[i], sourcesNew[i]);
		}
		
		TypedGraph mergedSource = s.first.getCopy();
		mergedSource.setConstraint(getConsistencyConstraint());
		
		return SourceType.makeSource(mergedSource, sourceMatchNew, new TraceSystem());
	}

}
