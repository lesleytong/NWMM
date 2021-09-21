package edu.ustb.sei.mde.bxcore;

import edu.ustb.sei.mde.bxcore.exceptions.BidirectionalTransformationDefinitionException;
import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.bxcore.exceptions.UninitializedException;
import edu.ustb.sei.mde.bxcore.structures.Context;
import edu.ustb.sei.mde.bxcore.structures.FieldDef;
import edu.ustb.sei.mde.graph.pattern.Pattern;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;
import edu.ustb.sei.mde.graph.typedGraph.constraint.GraphConstraint;
import edu.ustb.sei.mde.graph.typedGraph.constraint.GraphConstraint.ConstraintStatus;
import edu.ustb.sei.mde.structure.Tuple2;

public class ExpandSource extends XmuCore {

	
	public ExpandSource(Object key, Pattern patS, XmuCore body,Tuple2<String,String>[] remappings) 
			throws BidirectionalTransformationDefinitionException {
		super(key, patS.getType(), body.getViewDef());
		this.patS = patS;
		this.body = body;
		this.remappings = remappings;
		
		checkWellDefinedness();
	}
	
	@Override
	protected void checkWellDefinedness() throws BidirectionalTransformationDefinitionException {
//		try {
//			List<Tuple2<String,String>> directMappings = new ArrayList<>();
			for(Tuple2<String,String> m : remappings) {
//				PatternElement<?> sourceElement = this.patS.getPatternElement(m.first);
				
				FieldDef<?> up = this.getSourceDef().getField(m.first);
				FieldDef<?> down = this.body.getSourceDef().getField(m.second);
				
				if(up==null || down==null)
					throw new BidirectionalTransformationDefinitionException("Invalid mapping "+m);
//				if(sourceElement instanceof PatternNode 
//						|| sourceElement instanceof PatternEdge
//						|| sourceElement instanceof PatternValueEdge)
//					directMappings.add(m);
			}
//			indexableElementMappings = new Tuple2[directMappings.size()];
//			indexableElementMappings = directMappings.toArray(indexableElementMappings);
//		} catch (Exception e) {
//			throw new InvalidBidirectionalTransformationException(e);
//		}
	}
	
	
	
	private Pattern patS; 
	private XmuCore body;
	private Tuple2<String,String>[] remappings;
	
//	private Tuple2<String,String>[] indexableElementMappings;

	@Override
	public ViewType forward(SourceType s) throws NothingReturnedException {
		if(patS.isMatchOf(s.first, s.second)==false) 
			return nothing();
		
		Context downstreamSourceMatch = s.second.createDownstreamContext(body.getSourceDef(), remappings, false);
		downstreamSourceMatch.setUpstream(s.second, remappings);
		
		ViewType v = body.forward(SourceType.makeSource(s.first, downstreamSourceMatch, s.third));
		
		if(v==null || v==ViewType.empty()) return v;
		
		downstreamSourceMatch.submit();
		v.first.setConstraint(getConsistencyConstraint());
		
		return v;
	}

	@Override
	public SourceType backward(SourceType s, ViewType v) throws NothingReturnedException {
		s.second.setWillCreateElement(true); // shared node issues in source is ignored now
		
		Context downstreamSourceMatch = s.second.createDownstreamContext(body.getSourceDef(), remappings, false);
		downstreamSourceMatch.setUpstream(s.second, remappings);
		
		SourceType sp = body.backward(SourceType.makeSource(s.first, downstreamSourceMatch, s.third), v);
		
		Context upstreamSourceMatchPost = s.second.getCopy();
		
		Context downstreamSourceMatchPost = sp.second;
		for(Tuple2<String, String> m : remappings) {
			try {
				upstreamSourceMatchPost.setValue(m.first, downstreamSourceMatchPost.getValue(m.second));
			} catch (UninitializedException e) {
				return nothing();
			}
		}
		
		downstreamSourceMatchPost.setUpstream(upstreamSourceMatchPost, remappings);
		
		
		try {
			TypedGraph graph = patS.construct(sp.first, upstreamSourceMatchPost);
			
			TypedGraph modelPost = sp.first.additiveMerge(graph); // I should check whether body is still available after merge
			
			downstreamSourceMatch.submit();
			downstreamSourceMatchPost.submit();
			
			sp.third.trace(this.getSerializationKey(), s.second, v.second, upstreamSourceMatchPost);
			
			modelPost.setConstraint(getConsistencyConstraint());
			
			return SourceType.makeSource(modelPost, upstreamSourceMatchPost, sp.third);
		} catch (UninitializedException e) {
			return nothing();
		}
	}
	
	@Override
	protected GraphConstraint generateConsistencyConstraint() {
		return (gs,cs,gv,cv) -> {
			GraphConstraint inner = body.getConsistencyConstraint();
			
			ConstraintStatus upperCons = ConstraintStatus.sat;
			if(patS.isMatchOf(gs, cs)==false) 
				upperCons = ConstraintStatus.enforceable;
			Context downstreamSourceMatch = body.createSourceContext();
			for(Tuple2<String, String> m : remappings) {
				try {
					downstreamSourceMatch.setValue(m.second, cs.getValue(m.first));
				} catch (UninitializedException | NothingReturnedException e) {
					return ConstraintStatus.unenforceable;
				}
			}
			
			return GraphConstraint.mergeStatus(upperCons, 
					inner.check(gs, downstreamSourceMatch, gv, cv));
		};
	}

}
