package edu.ustb.sei.mde.bxcore;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

/*
 * cannot forget primitive values because we cannot set default values
 * @author hexiao
 *
 */
public class ExpandView extends XmuCore {
	
	private Pattern patV;
	private XmuCore body;
	private Tuple2<String,String>[] remappings;
//	private Tuple2<String,String>[] indexableElementMappings;

//	@SuppressWarnings("unchecked")
	public ExpandView(Object key, Pattern patV, XmuCore body, Tuple2<String,String>[] remappings) 
			throws BidirectionalTransformationDefinitionException {
		super(key, body.getSourceDef(), patV.getType());
		
		this.patV = patV;
		this.body = body;
		this.remappings = remappings;
		
		checkWellDefinedness();
		
//		try {
//			List<Tuple2<String,String>> directMappings = new ArrayList<>();
//			for(Tuple2<String,String> m : remappings) {
//				PatternElement<?> viewElement = this.patV.getPatternElement(m.first);
//				if(viewElement instanceof PatternNode 
//						|| viewElement instanceof PatternEdge
//						|| viewElement instanceof PatternValueEdge)
//					directMappings.add(m);
//			}
//			indexableElementMappings = new Tuple2[directMappings.size()];
//			indexableElementMappings = directMappings.toArray(indexableElementMappings);
//		} catch (Exception e) {
//			throw new InvalidBidirectionalTransformationException(e);
//		}
	}
	
	@Override
	protected void checkWellDefinedness() throws BidirectionalTransformationDefinitionException {
		Set<String> upKeys = new HashSet<>();
		
		for(Tuple2<String,String> m : remappings) {
			FieldDef<?> up = this.getViewDef().getField(m.first);
			FieldDef<?> down = this.body.getViewDef().getField(m.second);
			
			if(up==null || down==null)
				throw new BidirectionalTransformationDefinitionException("Invalid mapping "+m);
			
			upKeys.add(up.getName());
		}
		
		Set<String> primKeys = this.getViewDef().fields().stream().filter(f->f.isElementType()==false).map(f->f.getName()).collect(Collectors.toSet());
		if(upKeys.containsAll(primKeys)==false)
			throw new BidirectionalTransformationDefinitionException("All primitive view fields must be converted by body");
	}

	@Override
	public ViewType forward(SourceType s) throws NothingReturnedException {
		ViewType downstreamView = body.forward(s);
		
		Context upstreamViewMatch = this.createViewContext();
		patV.getType().initializeView(upstreamViewMatch, s.second, s.third);
		
		if(downstreamView==null) {
			try {
				TypedGraph view = patV.construct(null, upstreamViewMatch);
				return ViewType.makeView(view, upstreamViewMatch);
			} catch (UninitializedException e) {
				return nothing();
			}
		} else {
			downstreamView.second.setUpstream(upstreamViewMatch, remappings);
			
			for(Tuple2<String, String> m : remappings) {
				try {
					upstreamViewMatch.setValue(m.first, downstreamView.second.getValue(m.second));
				} catch (UninitializedException e) {
					return nothing();
				}
			}
			TypedGraph view;
			try {
				view = patV.construct(downstreamView.first, upstreamViewMatch);
				TypedGraph mergedView = downstreamView.first.additiveMerge(view);	
				downstreamView.second.submit();
				
				mergedView.setConstraint(getConsistencyConstraint());
				
				return ViewType.makeView(mergedView, upstreamViewMatch);
			} catch (UninitializedException e) {
				return nothing();
			}
		}
		
	}

	@Override
	public SourceType backward(SourceType s, ViewType v) throws NothingReturnedException {
		v.second.setWillCreateElement(true);
		if(v.second.verticalCorrectness(s.third)==false) {
			XmuCoreUtils.warning("Shared node issue in view detected");
		}
		
		Context downstreamViewMatch = v.second.createDownstreamContext(body.getViewDef(), remappings, false);
		downstreamViewMatch.setUpstream(v.second, remappings);
		
		SourceType downstreamSourcePost = body.backward(s, ViewType.makeView(v.first, downstreamViewMatch));
		
		downstreamViewMatch.submit();
		
		downstreamSourcePost.third.trace(this.getSerializationKey(), s.second, v.second, downstreamSourcePost.second);
		downstreamSourcePost.first.setConstraint(getConsistencyConstraint());
		
		return downstreamSourcePost;
	}
	
	@Override
	public GraphConstraint generateConsistencyConstraint() {
		return (gs,cs,gv,cv)->{
			GraphConstraint inner = body.getConsistencyConstraint();
			if(patV.isMatchOf(gv, cv)==false) return ConstraintStatus.enforceable;
			Context downstreamViewMatch = body.createViewContext();
			for(Tuple2<String, String> m : remappings) {
				try {
					downstreamViewMatch.setValue(m.second, cv.getValue(m.first));
				} catch (UninitializedException | NothingReturnedException e) {
					return ConstraintStatus.unenforceable;
				}
			}
			return inner.check(gs, cs, gv, downstreamViewMatch);
		};
	}

}
