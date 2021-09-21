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

//(s->s0) -> BiGUL (s, s0) v -> BiGUL s v
public class Derive extends XmuCore {
	private XmuCore body;
	private Tuple2<FieldDef<?>, Function<ContextGraph, Object>>[] derivations;

	public Derive(Object key, ContextType sourceDef, XmuCore body, Tuple2<FieldDef<?>, Function<ContextGraph, Object>>[] derivations)
			throws BidirectionalTransformationDefinitionException {
		super(key, sourceDef, body.getViewDef());
		this.body = body;
		this.derivations = derivations;
		
		this.checkWellDefinedness();
	}
	
	@Override
	protected void checkWellDefinedness() throws BidirectionalTransformationDefinitionException {
		if(!this.getSourceDef().isSuperOf(this.body.getSourceDef()))
			throw new BidirectionalTransformationDefinitionException("The parent source type is inconsistent with the child source type");
		for(Tuple2<FieldDef<?>, ?> t : derivations) {
			if(this.getSourceDef().fields().contains(t.first))
				throw new BidirectionalTransformationDefinitionException("The derived field should not be defined in the parent");
			if(!this.body.getSourceDef().fields().contains(t.first))
				throw new BidirectionalTransformationDefinitionException("The derived field should be defined in the child");
		}
		
		if(!this.body.getSourceDef().fields().stream().allMatch(f->{
			return this.getSourceDef().fields().contains(f) || Arrays.stream(derivations).anyMatch(t->t.first.equals(f));
		})) {
			throw new BidirectionalTransformationDefinitionException("The child fields should be defined in the parent or derived");
		}
	}

	@Override
	protected GraphConstraint generateConsistencyConstraint() {
		GraphConstraint c = this.body.getConsistencyConstraint();
		return (gs,cs,gv,cv)->{
			try {
				Context ds = derive(SourceType.makeSource(gs, cs, null));
				return c.check(gs, ds, gv, cv);
			} catch (NothingReturnedException e) {
				return ConstraintStatus.unenforceable;
			}
		};
	}

	@Override
	public ViewType forward(SourceType s) throws NothingReturnedException {
		Context downstreamSource = derive(s);
		downstreamSource.setUpstream(s.second);
		ViewType v = body.forward(s.replaceSecond(downstreamSource));
		submit(downstreamSource);
		
		return v;
	}

	protected Context derive(SourceType s) throws NothingReturnedException {
		Context downstreamSource = s.second.createDownstreamContext(this.body.getSourceDef());
		for(Tuple2<FieldDef<?>, Function<ContextGraph, Object>> tuple : derivations) {
			try {
				downstreamSource.setValue(tuple.first, tuple.second.apply(s));
			} catch (Exception e) {
				return nothing(e);
			}
		}
		return downstreamSource;
	}
	
	protected boolean checkDerivation(SourceType s) {
		for(Tuple2<FieldDef<?>, Function<ContextGraph, Object>> tuple : derivations) {
			try {
				Object dv = tuple.second.apply(s);
				Object av = s.second.getValue(tuple.first);
				if(!(dv==av || (dv!=null && dv.equals(av)))) return false;
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	@Override
	public SourceType backward(SourceType s, ViewType v) throws NothingReturnedException {
		Context downstreamSource = derive(s);
		downstreamSource.setUpstream(s.second);
		SourceType sourcePost = this.body.backward(s.replaceSecond(downstreamSource), v);
		if(checkDerivation(sourcePost)==false)
			return nothing();
		
		Context upstreamSourcePost = sourcePost.second.createUpstreamContext(this.getSourceDef());
		sourcePost.second.setUpstream(upstreamSourcePost);
		
		sourcePost.third.trace(this.getSerializationKey(), s.second, v.second, sourcePost.second);
		submit(downstreamSource);
		submit(sourcePost.second);
		
		return sourcePost.replaceSecond(upstreamSourcePost);
	}

}
