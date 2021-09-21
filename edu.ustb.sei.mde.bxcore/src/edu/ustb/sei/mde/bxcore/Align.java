package edu.ustb.sei.mde.bxcore;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

import edu.ustb.sei.mde.bxcore.exceptions.BidirectionalTransformationDefinitionException;
import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.bxcore.exceptions.UninitializedException;
import edu.ustb.sei.mde.bxcore.structures.Context;
import edu.ustb.sei.mde.bxcore.structures.ContextGraph;
import edu.ustb.sei.mde.bxcore.structures.ContextType;
import edu.ustb.sei.mde.bxcore.structures.FieldDef;
import edu.ustb.sei.mde.bxcore.structures.Index;
import edu.ustb.sei.mde.graph.pattern.Pattern;
import edu.ustb.sei.mde.graph.pattern.PatternElement;
import edu.ustb.sei.mde.graph.typedGraph.IndexSystem;
import edu.ustb.sei.mde.graph.typedGraph.IndexableElement;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;
import edu.ustb.sei.mde.graph.typedGraph.constraint.GraphConstraint;
import edu.ustb.sei.mde.graph.typedGraph.constraint.GraphConstraint.ConstraintStatus;
import edu.ustb.sei.mde.structure.Tuple2;

public class Align extends XmuCore {
	private Pattern patS;
	public Align(Object key, ContextType sourceDef, ContextType viewDef, Pattern patS, Pattern patV,
			BiFunction<ContextGraph, ContextGraph, Boolean> alignment, XmuCore match,
			BiFunction<SourceType, ViewType, SourceType> unmatchedSource,
			BiFunction<SourceType, ViewType, SourceType> unmatchedView) throws BidirectionalTransformationDefinitionException {
		super(key, sourceDef, viewDef);
		this.patS = patS;
		this.patV = patV;
		this.alignment = alignment;
		this.match = match;
		this.unmatchedSource = unmatchedSource;
		this.unmatchedView = unmatchedView;
		this.isOrdered = patV.getOrderBy()!=null;
		this.allowMultiple = true;
		checkWellDefinedness();
	}

	private Pattern patV;
	private BiFunction<ContextGraph,ContextGraph,Boolean> alignment;
	private XmuCore match;
	private BiFunction<SourceType, ViewType, SourceType> unmatchedSource;
	private BiFunction<SourceType, ViewType, SourceType> unmatchedView;
	
	private boolean isOrdered;
	private boolean allowMultiple;
	
	public Align(Object key, ContextType sourceDef, ContextType viewDef, Pattern patS, Pattern patV, XmuCore match,
			BiFunction<SourceType, ViewType, SourceType> unmatchedSource,
			BiFunction<SourceType, ViewType, SourceType> unmatchedView) throws BidirectionalTransformationDefinitionException {
		super(key, sourceDef, viewDef);
		this.patS = patS;
		this.patV = patV;
		this.alignment = (s,v)->true;
		this.match = match;
		this.unmatchedSource = unmatchedSource;
		this.unmatchedView = unmatchedView;
		this.isOrdered = true;
		
		checkWellDefinedness();
	}
	
	@Override
	protected void checkWellDefinedness() throws BidirectionalTransformationDefinitionException {
		if(Boolean.logicalXor(patS.getOrderBy()!=null, patV.getOrderBy()!=null)) {
			throw new BidirectionalTransformationDefinitionException("Order inconsistent");
		}
		
		if(isOrdered && (patS.getOrderBy()==null || patV.getOrderBy()==null)) {
			throw new BidirectionalTransformationDefinitionException("Order inconsistent");
		}
		
		
		if(patS.getType()!=match.getSourceDef() || patV.getType()!=match.getViewDef())
			throw new BidirectionalTransformationDefinitionException("Type inconsistent");
	}

	@Override
	protected GraphConstraint generateConsistencyConstraint() {
		GraphConstraint innerCons = match.getConsistencyConstraint();
		
		return (gs,cs,gv,cv) -> {
			List<Context> sources = patS.match(gs, cs);
			List<Context> views = patV.match(gv, cv);
			
			if(sources.size()!=views.size()) 
				return ConstraintStatus.enforceable; // it may not be enforceable
			
			// construct one-to-one mapping 
			List<Tuple2<Context,Context>> alignments = new ArrayList<>();
			try {
				if(checkAndConstructAlignment(gs, sources, gv, views, alignments)==false)
					return ConstraintStatus.enforceable; // in fact, we should check
			} catch (Exception e) {
				return ConstraintStatus.unenforceable;
			}
			
			// check match condition for each alignment
			ConstraintStatus status = ConstraintStatus.sat;
			
			for(Tuple2<Context, Context> a : alignments) {
				status = GraphConstraint.mergeStatus(status, innerCons.check(gs, a.first, gv, a.second));
			}
			
			return status;
		};
	}
	
	protected boolean checkAndConstructAlignment(TypedGraph sg, List<Context> s, TypedGraph vg, List<Context> v, List<Tuple2<Context,Context>> alignments) throws NothingReturnedException {
		boolean isAligned = true;
		
		int sizeOfS = s.size();
		int sizeOfV = v.size();
		
		if(sizeOfS!=sizeOfV) 
			isAligned = false;
		
		ContextGraph source = ContextGraph.makeContextGraph(sg, null);
		ContextGraph view = ContextGraph.makeContextGraph(vg, null);
		
		if(isOrdered) {
			for(int si=0,vi=0 ; si<sizeOfS || vi<sizeOfV; ) {
				Context sc = null;
				Context vc = null;
				
				if(si<sizeOfS)
					sc = s.get(si);
				if(vi<sizeOfV)
					vc = v.get(vi);
				
				if(sc==null) {
					isAligned = false;
					alignments.add(new Tuple2<Context, Context>(null, vc));
					vi++;
				} else if(vc==null) {
					isAligned = false;
					alignments.add(new Tuple2<Context, Context>(sc, null));
					si++;
				} else {
					source.replaceContext(sc);
					view.replaceContext(vc);
					if(this.alignment.apply(source, view)) {
						alignments.add(new Tuple2<Context, Context>(sc, vc));
						si++;
						vi++;
					} else {
						isAligned = false;
						alignments.add(new Tuple2<Context, Context>(sc, null));
						si++;
					}
				}
			}
		} else {
			Set<Context> aligned = new HashSet<>();
			
			for(Context vc : v) {
				Context matched = null;
				for(Context sc : s) {
					source.replaceContext(sc);
					view.replaceContext(vc);
					
					if(alignment.apply(source, view)) {
						if(!this.allowMultiple) {
							if(matched!=null) {// multiple alignment
								throw new NothingReturnedException();
							}
							if(aligned.contains(sc)) // multiple alignment
								throw new NothingReturnedException();
							matched = sc;
							aligned.add(sc);
						} else {
							if(aligned.contains(sc)) continue;
							else {
								matched = sc;
								aligned.add(sc);
								break;
							}
						}
						
					}
				}
				
				if(matched!=null) {
					alignments.add(new Tuple2<Context, Context>(matched, vc));
				} else {
					alignments.add(new Tuple2<Context, Context>(null, vc));
					isAligned = false;
				}
			}
			
			for(Context sc : s) {
				if(aligned.contains(sc)==false) {
					alignments.add(new Tuple2<Context, Context>(sc, null));
					isAligned = false;
				}
			}
			
		}
		
		return isAligned;
				
	}

	@Override
	public ViewType forward(SourceType s) throws NothingReturnedException {
		List<Context> sourceMatches = patS.match(s.first, s.second);
		ViewType[] views = new ViewType[sourceMatches.size()];
		
		int i=0;
		for(Context sc : sourceMatches) {
			sc.setUpstream(s.second);
			ViewType v = match.forward(SourceType.makeSource(s.first, sc, s.third));
			views[i] = v;
			i++;
		}
		
		ContextType vt = this.getViewDef();
		Context upstreamView = this.createViewContext();
		TypedGraph finalView = null;
		
		if(views.length==0) {
			return ViewType.empty();
		} else {
			for(FieldDef<?> vk : vt.fields()) {
				if(vk.isElementType()) {
					try {
						upstreamView.setValue(vk,  ViewType.summarize(views,vk,this));
					} catch (Exception e) {
//						Object common = IndexSystem.generateUUID();
//						Index index = Index.freshIndex(common);
						Index index = IndexSystem.generateFreshIndex();
//						Index index = IndexSystem.generateFreshViewIndex(null, s.second, vk, s.third);
						upstreamView.setValue(vk, index);
						for(ViewType v : views) {
							try {
								// in principle, we should reset downstream values
								v.second.setUpstream(upstreamView);
								Index value = (Index) v.second.getValue(vk.getName());
								IndexableElement elementByIndexObject = null;
								try {
									elementByIndexObject = v.first.getElementByIndexObject(value);
								} catch (NothingReturnedException noe) {
								}
								if(elementByIndexObject!=null) v.first.addIndex(index, elementByIndexObject);
							} catch (UninitializedException e1) {
								return nothing(e1);
							}
						}
					}
					
				} else {
					try {
						upstreamView.setValue(vk, ViewType.summarize(views,vk,this));
					} catch (Exception e) {
						// not confluent
						return nothing(e);
					}
				}
			}
			List<Context> viewMatches = new ArrayList<>();
			for(ViewType v : views) {
				v.second.setUpstream(upstreamView);
				v.second.submit();
				
				viewMatches.add(v.second);
				
				if(finalView==null)
					finalView = v.first;
				else 
					finalView = finalView.additiveMerge(v.first);
			}
			if(this.isOrdered) {
				enforceOrder(patV, viewMatches, finalView);
			}
		}
		
		this.submit(sourceMatches);
		
		finalView.setConstraint(getConsistencyConstraint());
		
		return ViewType.makeView(finalView, upstreamView);
	}

	@Override
	public SourceType backward(SourceType s, ViewType v) throws NothingReturnedException {
		return plainBackward(s, v, true, null, new ArrayList<>());
	}

	protected SourceType plainBackward(SourceType s, ViewType v, boolean adaption, List<Context> viewMatches, List<Context> skippedViews) throws NothingReturnedException {
		List<Context> sourceMatches = patS.match(s.first, s.second);
		if(viewMatches==null)
			viewMatches = patV.match(v.first, v.second);
		viewMatches.removeIf(vm->skippedViews.stream().anyMatch(svm->(svm==vm)));
		
		List<Tuple2<Context,Context>> alignments = new ArrayList<>();
		
		boolean aligned = checkAndConstructAlignment(s.first, sourceMatches, v.first, viewMatches, alignments);
		
		if(!aligned) {
			if(adaption) {
				TypedGraph ma = adaption(s,v,alignments,skippedViews);
				return plainBackward(SourceType.makeSource(ma, s.second, s.third), v, false, viewMatches, skippedViews);
			} else return nothing();
		} else {
			sourceMatches.forEach(sm->sm.setUpstream(s.second));
			viewMatches.forEach(vm->vm.setUpstream(v.second));
			
			Context upstreamSourcePost = s.second.getCopy();
			
			List<SourceType> updatedSources = new ArrayList<>();
			for(Tuple2<Context, Context> alignment : alignments) {
				SourceType sp = match.backward(SourceType.makeSource(s.first, alignment.first, s.third), ViewType.makeView(v.first, alignment.second));
				sp.second.setUpstream(upstreamSourcePost);
				updatedSources.add(sp);
			}
			
			TraceSystem[] interTraces = updatedSources.stream().map(us->us.third).toArray(size->new TraceSystem[size]);

			if(v.second.horizontalCorrnectness(viewMatches.toArray(new Context[viewMatches.size()]), interTraces)==false) {
				XmuCoreUtils.failure("Shared node issue in view detected");
			}
			
			TypedGraph finalSourcePost = s.first.merge(updatedSources.stream().map(us->us.first).toArray(size->new TypedGraph[size]));
			TraceSystem finalTs = TraceSystem.merge(interTraces);
			
			/* call a special constraint generator for backward transformation, because we may skip some view matches */
			finalSourcePost.setConstraint(getConsistencyConstraint());
			
			this.submit(sourceMatches);
			this.submit(viewMatches);
			this.submit(updatedSources.stream().map(us->us.second).toArray(size->new Context[size]));
			
			return SourceType.makeSource(finalSourcePost, upstreamSourcePost, finalTs);
		}
	}

//	private GraphConstraint getConsistencyConstraint(List<Context> viewMatches) {
//		return generateConsistencyConstraint(viewMatches);
//	}

	private TypedGraph adaption(SourceType s, ViewType v, List<Tuple2<Context, Context>> alignments, List<Context> skippedViews) throws NothingReturnedException {
		List<TypedGraph> delta = new ArrayList<>();
//		List<Context> views = new ArrayList<>();
		List<Context> sources = new ArrayList<>();
		
		for(Tuple2<Context, Context> alignment : alignments) {
			if(alignment.first!=null&&alignment.second!=null) {
				sources.add(alignment.first);
//				views.add(alignment.second);
			} else if(alignment.first==null) { // unmatchV
				SourceType sp = this.unmatchedView.apply(s, v.replaceSecond(alignment.second));
				if(sp==SourceType.drop()) { // drop this view!
					skippedViews.add(alignment.second);
				} else if(sp==SourceType.skip()) {
					// do nothing
				} else {
					if(!patS.getType().isSuperOf(sp.second.getType()))
						throw new NothingReturnedException("Adaption must return a valid source match");
					Context sc = sp.second;
					if(patS.getType()!=sp.second.getType()) {
						sc = sp.second.createUpstreamContext(patS.getType());
					}
					delta.add(sp.first);
					sources.add(sc);					
				} 
			} else if(alignment.second==null) { // unmatchS
				SourceType sp = this.unmatchedSource.apply(s.replaceSecond(alignment.first), v);
				delta.add(sp.first);
			}
		}
		
		TypedGraph finalSource = s.first.merge(delta.toArray(new TypedGraph[delta.size()]));
		
		if(this.isOrdered) {
			enforceOrder(patS, sources, finalSource);
		}
		
		return finalSource;
	}

	private void enforceOrder(Pattern pattern, List<Context> matches, 
			TypedGraph graph) throws NothingReturnedException {
		
		PatternElement<?> order = pattern.getOrderBy();
		FieldDef<?> field = pattern.getType().getField(order.getName());
		
		Index prev = null;
		Index cur = null;
		
		for(Context mat : matches) {
			prev = cur;
			try {
				cur = mat.getIndexValue(field);
				if(prev!=null) {
					graph.getOrder().add(prev, cur);
				}
			} catch (UninitializedException e) {
				throw new NothingReturnedException(e);
			}
		}
	}

}


