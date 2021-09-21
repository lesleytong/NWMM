package edu.ustb.sei.mde.bxcore;

import java.util.Arrays;
import java.util.function.Function;

import edu.ustb.sei.mde.bxcore.exceptions.BidirectionalTransformationDefinitionException;
import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.bxcore.structures.Context;
import edu.ustb.sei.mde.bxcore.structures.ContextGraph;
import edu.ustb.sei.mde.bxcore.structures.ContextType;
import edu.ustb.sei.mde.bxcore.structures.FieldDef;
import edu.ustb.sei.mde.graph.typedGraph.constraint.GraphConstraint;
import edu.ustb.sei.mde.graph.typedGraph.constraint.GraphConstraint.ConstraintStatus;
import edu.ustb.sei.mde.structure.Tuple2;


// (v->v0) -> BiGUL a v -> BiGUL a (v, v0)
public class Dependency extends XmuCore {
	private XmuCore body;
	private Tuple2<FieldDef<?>, Function<ContextGraph, Object>>[] derivations;
	private Tuple2<String,String>[] keyMappings;
	
	@SuppressWarnings("unchecked")
	public Dependency(Object key, ContextType viewDef, XmuCore body, Tuple2<FieldDef<?>, Function<ContextGraph, Object>>[] derivations)
			throws BidirectionalTransformationDefinitionException {
		super(key, body.getSourceDef(), viewDef);
		this.body = body;
		this.derivations = derivations;
		this.checkWellDefinedness();

		keyMappings = new Tuple2[this.body.getViewDef().fields().size()];
		for(int i=0;i<keyMappings.length;i++) {
			String n = this.body.getViewDef().orderedFields().get(i).getName();
			keyMappings[i] = Tuple2.make(n, n);
		}
	}
	
	@Override
	protected void checkWellDefinedness() throws BidirectionalTransformationDefinitionException {
		if(!this.body.getViewDef().isSuperOf(this.getViewDef()))
			throw new BidirectionalTransformationDefinitionException("The child source type is inconsistent with the parent source type");
		for(Tuple2<FieldDef<?>, ?> t : derivations) {
			if(this.body.getViewDef().fields().contains(t.first))
				throw new BidirectionalTransformationDefinitionException("The derived field should not be defined in the child");
			if(!this.getViewDef().fields().contains(t.first))
				throw new BidirectionalTransformationDefinitionException("The derived field should be defined in the parent");
		}
		
		if(!this.getViewDef().fields().stream().allMatch(f->{
			return this.body.getViewDef().fields().contains(f) || Arrays.stream(derivations).anyMatch(t->t.first.equals(f));
		})) {
			throw new BidirectionalTransformationDefinitionException("The parent fields should be defined in the child or derived");
		}
	}

	@Override
	protected GraphConstraint generateConsistencyConstraint() {
		GraphConstraint bc = this.body.getConsistencyConstraint();
		return (gs,cs,gv,cv)->{
			if(!checkDerivation(ViewType.makeView(gv, cv))) 
				return ConstraintStatus.unenforceable;
			else {
				Context downstreamViewContext = cv.createDownstreamContext(this.body.getViewDef(), keyMappings);
				return bc.check(gs, cs, gv, downstreamViewContext);
			}
		};
	}
	
	protected Context derive(ViewType v) throws NothingReturnedException {
		Context upstream = v.second.createUpstreamContext(this.getViewDef(), keyMappings);
		for(Tuple2<FieldDef<?>, Function<ContextGraph, Object>> tuple : derivations) {
			try {
				upstream.setValue(tuple.first, tuple.second.apply(v));
			} catch (Exception e) {
				return nothing(e);
			}
		}
		return upstream;
	}
	
	protected boolean checkDerivation(ViewType v) {
		for(Tuple2<FieldDef<?>, Function<ContextGraph, Object>> tuple : derivations) {
			try {
				Object dv = tuple.second.apply(v);
				Object av = v.second.getValue(tuple.first);
				if(!(dv==av || (dv!=null && dv.equals(av)))) return false;
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ViewType forward(SourceType s) throws NothingReturnedException {
		ViewType downstreamView = this.body.forward(s);
		if(downstreamView==null || downstreamView==ViewType.empty())
			return ViewType.empty();
		
		Context upstreamViewContext = derive(downstreamView);
		downstreamView.second.setUpstream(upstreamViewContext, keyMappings);
		submit(downstreamView.second);
		return downstreamView.replaceSecond(upstreamViewContext);
	}

	@Override
	public SourceType backward(SourceType s, ViewType v) throws NothingReturnedException {
		if(checkDerivation(v)) 
			return nothing();
		
		Context downstreamViewContext = v.second.createDownstreamContext(this.body.getViewDef(), keyMappings);
		downstreamViewContext.setUpstream(v.second);
		SourceType sourcePost = this.backward(s, v.replaceSecond(downstreamViewContext));
		sourcePost.third.trace(this.getSerializationKey(), s.second, v.second, sourcePost.second);
		submit(downstreamViewContext);
		return sourcePost;
	}

}
