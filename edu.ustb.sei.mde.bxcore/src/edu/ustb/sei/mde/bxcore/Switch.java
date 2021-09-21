package edu.ustb.sei.mde.bxcore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import edu.ustb.sei.mde.bxcore.exceptions.BidirectionalTransformationDefinitionException;
import edu.ustb.sei.mde.bxcore.exceptions.InternalBidirectionalTransformationError;
import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.bxcore.structures.Context;
import edu.ustb.sei.mde.bxcore.structures.ContextType;
import edu.ustb.sei.mde.graph.typedGraph.constraint.GraphConstraint;
import edu.ustb.sei.mde.graph.typedGraph.constraint.GraphConstraint.ConstraintStatus;
import edu.ustb.sei.mde.structure.Tuple2;
import edu.ustb.sei.mde.structure.Tuple3;

public class Switch extends XmuCore {
	
	List<Tuple3<BiFunction<SourceType,ViewType,Boolean>,XmuCore,Function<SourceType,Boolean>>> branches = new ArrayList<Tuple3<BiFunction<SourceType,ViewType,Boolean>,XmuCore,Function<SourceType,Boolean>>>();
	List<Tuple2<BiFunction<SourceType,ViewType,Boolean>,BiFunction<SourceType,ViewType,SourceType>>> adaptions = new ArrayList<Tuple2<BiFunction<SourceType,ViewType,Boolean>,BiFunction<SourceType,ViewType,SourceType>>>();
	

	public Switch(Object key, ContextType sourceDef, ContextType viewDef, 
			List<Tuple3<BiFunction<SourceType,ViewType,Boolean>,XmuCore,Function<SourceType,Boolean>>> branches,
			List<Tuple2<BiFunction<SourceType,ViewType,Boolean>,BiFunction<SourceType,ViewType,SourceType>>> adaptions) 
					throws BidirectionalTransformationDefinitionException {
		super(key, sourceDef, viewDef);
		this.branches = branches;
		this.adaptions = adaptions;
		
		checkWellDefinedness();
	}
	
	public Switch(Object key, ContextType sourceDef, ContextType viewDef, 
			Tuple3<BiFunction<SourceType,ViewType,Boolean>,XmuCore,Function<SourceType,Boolean>>[] branches,
			Tuple2<BiFunction<SourceType,ViewType,Boolean>,BiFunction<SourceType,ViewType,SourceType>>[] adaptions) 
					throws BidirectionalTransformationDefinitionException {
		this(key, sourceDef,viewDef,Arrays.asList(branches), Arrays.asList(adaptions));
	}
	
	@Override
	protected void checkWellDefinedness() throws BidirectionalTransformationDefinitionException {
		Set<String> sourceKeys = this.getSourceDef().fields().stream().map(f->f.getName()).collect(Collectors.toSet());
		Set<String> viewKeys = this.getViewDef().fields().stream().map(f->f.getName()).collect(Collectors.toSet());
		
		if(branches.stream().allMatch(branch->{
			Set<String> downSrcKeys = branch.second.getSourceDef().fields().stream().map(f->f.getName()).collect(Collectors.toSet());
			Set<String> downViwKeys = branch.second.getViewDef().fields().stream().map(f->f.getName()).collect(Collectors.toSet());
			
			return sourceKeys.equals(downSrcKeys) && viewKeys.equals(downViwKeys);
		})==false)
			throw new BidirectionalTransformationDefinitionException("A branch type is incompatible with the main BX.");
	}

	@Override
	public ViewType forward(SourceType s) throws NothingReturnedException {
		try {
			for(int i=0;i<branches.size();i++) {
				Tuple3<BiFunction<SourceType,ViewType,Boolean>,XmuCore,Function<SourceType,Boolean>> branch = branches.get(i);
				
				Context downstreamSource = s.second.createDownstreamContext(branch.second.getSourceDef());
				downstreamSource.setUpstream(s.second);
				SourceType ds = SourceType.makeSource(s.first, downstreamSource, s.third);
				
				ViewType v = null;
				try {
					v = getBranch(branch,ds);
					if(v==null) continue; // should I continue?
				} catch(NothingReturnedException e) {
					continue;
				}
				for(int j=0;j<i;j++) {
					Tuple3<BiFunction<SourceType,ViewType,Boolean>,XmuCore,Function<SourceType,Boolean>> prevBranch = branches.get(j);
					if(prevBranch.first.apply(ds, v)) 
						throw new InternalBidirectionalTransformationError();
				}
				
				Context upstreamView = v.second.createUpstreamContext(this.getViewDef());
				v.second.setUpstream(upstreamView);
				
				submit(downstreamSource, v.second);
				
				v.first.setConstraint(getConsistencyConstraint());
				
				return ViewType.makeView(v.first, upstreamView);
			}
		} catch(InternalBidirectionalTransformationError e) {
		}
		// no branch can be applied
		throw new NothingReturnedException();
	}
	
	protected ViewType getBranch(Tuple3<BiFunction<SourceType,ViewType,Boolean>,XmuCore,Function<SourceType,Boolean>> branch, SourceType s)
			throws NothingReturnedException {
		if(branch.third==null || branch.third.apply(s)) {
			ViewType v = branch.second.forward(s);
			if(branch.first.apply(s, v)) {
				return v;
			} else 
				throw new NothingReturnedException();
		} else 
			throw new NothingReturnedException();
	}

	public SourceType backward(SourceType s, ViewType v) throws NothingReturnedException {
		return plainBackward(s, v, true);
	}
	
	protected SourceType plainBackward(SourceType s, ViewType v, boolean adaption) throws NothingReturnedException {
		for(int i=0;i<branches.size();i++) {
			Tuple3<BiFunction<SourceType,ViewType,Boolean>,XmuCore,Function<SourceType,Boolean>> branch = branches.get(i);
			if(branch.first.apply(s, v)) {
				Context downstreamSource = s.second.createDownstreamContext(branch.second.getSourceDef());
				Context downstreamView = v.second.createDownstreamContext(branch.second.getViewDef());
				downstreamSource.setUpstream(s.second);
				downstreamView.setUpstream(v.second);
				
				SourceType ds = SourceType.makeSource(s.first, downstreamSource, s.third);
				ViewType dv = ViewType.makeView(v.first, downstreamView);
				
				SourceType sp = branch.second.backward(ds, dv);
				
				Context upstreamSourcePost = sp.second.createUpstreamContext(this.getSourceDef());
				sp.second.setUpstream(upstreamSourcePost);
				
				SourceType usp = SourceType.makeSource(sp.first, upstreamSourcePost, sp.third);
				
				if(branch.first.apply(sp, v) && (branch.third==null || branch.third.apply(sp))) {
					for(int j=0;j<i;j++) {
						Tuple3<BiFunction<SourceType,ViewType,Boolean>,XmuCore,Function<SourceType,Boolean>> prevBranch = branches.get(j);
						try {
							@SuppressWarnings("unused")
							ViewType vp = getBranch(prevBranch, sp);
						} catch(NothingReturnedException e) {
							continue;
						}
						throw new NothingReturnedException();
					}
					
					submit(downstreamSource, downstreamView, sp.second);

					usp.first.setConstraint(getConsistencyConstraint());
					
					return usp;
				} else
					throw new NothingReturnedException();
			}
		}
		// no branch can be applied, try adaption
		if(adaption) {
			for(int i=0;i<this.adaptions.size();i++) {
				Tuple2<BiFunction<SourceType,ViewType,Boolean>,BiFunction<SourceType,ViewType,SourceType>> adaptionBranch = adaptions.get(i);
				if(adaptionBranch.first.apply(s, v)) {
					SourceType sa = adaptionBranch.second.apply(s, v);
					return plainBackward(sa, v, false);
				}
			}
		}
		
		throw new NothingReturnedException();
	}

	
	
	@Override
	protected GraphConstraint generateConsistencyConstraint() {
		return (gs,cs,gv,cv) -> {
			SourceType s = SourceType.makeSource(gs, cs, null);
			ViewType v = ViewType.makeView(gv, cv);
			
			for(int j=0;j<branches.size();j++) {
				Tuple3<BiFunction<SourceType,ViewType,Boolean>,XmuCore,Function<SourceType,Boolean>> cur = branches.get(j);
				GraphConstraint inner = cur.second.getConsistencyConstraint();
				if(cur.first.apply(s, v))
					return inner.check(gs, cs, gv, cv); // whether it is sufficient (without checking the forward transformation)?
			}
			
			return ConstraintStatus.unenforceable;
		};
	}
}
