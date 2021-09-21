package edu.ustb.sei.mde.bxcore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import edu.ustb.sei.mde.bxcore.exceptions.BidirectionalTransformationDefinitionException;
import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.bxcore.exceptions.UninitializedException;
import edu.ustb.sei.mde.bxcore.structures.Context;
import edu.ustb.sei.mde.bxcore.structures.ContextType;
import edu.ustb.sei.mde.bxcore.structures.FieldDef;
import edu.ustb.sei.mde.bxcore.structures.Index;
import edu.ustb.sei.mde.graph.typedGraph.IndexSystem;
import edu.ustb.sei.mde.graph.typedGraph.IndexableElement;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;
import edu.ustb.sei.mde.graph.typedGraph.constraint.GraphConstraint;
import edu.ustb.sei.mde.structure.Tuple2;

public class ParallelComposition extends XmuCore {
	
	private XmuCore[] bodies;
	private List<Tuple2<String,String>[]> sourceMappings;
	private List<Tuple2<String,String>[]> viewMappings;

	@SuppressWarnings("unchecked")
	public ParallelComposition(Object key, ContextType sourceDef, ContextType viewDef, XmuCore[] bodies) throws BidirectionalTransformationDefinitionException {
		super(key, sourceDef, viewDef);
		this.bodies = bodies;
		
		sourceMappings = new ArrayList<>();
		viewMappings = new ArrayList<>();
		
		Collection<String> sk = sourceDef.fields().stream().map(f->f.getName()).collect(Collectors.toSet());
		Collection<String> vk = viewDef.fields().stream().map(f->f.getName()).collect(Collectors.toSet());
		
		for(XmuCore b : bodies) {
			Collection<String> bsk = b.getSourceDef().fields().stream().map(f->f.getName()).collect(Collectors.toSet());
			Collection<String> bvk = b.getViewDef().fields().stream().map(f->f.getName()).collect(Collectors.toSet());
			
			bsk.retainAll(sk);
			bvk.retainAll(vk);
			
			if(bsk.isEmpty() || bvk.isEmpty()) 
				throw new BidirectionalTransformationDefinitionException("A body statement is disjoint with the parallel composition");
			
			sourceMappings.add(bsk.stream().map(k->Tuple2.make(k, k)).toArray(i->new Tuple2[i]));
			viewMappings.add(bvk.stream().map(k->Tuple2.make(k, k)).toArray(i->new Tuple2[i]));
		}
		
		checkWellDefinedness();
	}
	
	@Override
	protected void checkWellDefinedness() throws BidirectionalTransformationDefinitionException {
		// FIXME
		
		Set<FieldDef<?>> viewFields = new HashSet<>();
		
		for(XmuCore fork : bodies) {
			
			for(FieldDef<?> m : fork.getSourceDef().fields()) {
				FieldDef<?> up = this.getSourceDef().getField(m.getName());
				if(up==null)
					throw new BidirectionalTransformationDefinitionException("Invalid upstream field "+m);
			}
			
			for(FieldDef<?> m : fork.getViewDef().fields()) {
				FieldDef<?> up = this.getViewDef().getField(m.getName());
				if(up==null)
					throw new BidirectionalTransformationDefinitionException("Invalid upstream field "+m);
				viewFields.add(up);
			}
		}
		
		if(!viewFields.containsAll(this.getViewDef().fields()))
			throw new BidirectionalTransformationDefinitionException("Not all view fields are converted! "+viewFields);
	}

	@Override
	public ViewType forward(SourceType s) throws NothingReturnedException {
		ViewType[] result = new ViewType[this.bodies.length];
		Context[] newSources = new Context[this.bodies.length];
		for(int i=0;i<this.bodies.length;i++) {
			Context newSource = s.second.createDownstreamContext(this.bodies[i].getSourceDef(), sourceMappings.get(i));
			newSource.setUpstream(s.second, sourceMappings.get(i));
			newSources[i] = newSource;
			result[i] = bodies[i].forward(SourceType.makeSource(s.first, newSource, s.third));
		}
		
		ContextType vt = this.getViewDef();
		Context finalViewContext = this.createViewContext();
		
		for(FieldDef<?> vk : vt.fields()) {
			if(vk.isElementType()) {
				try {
					finalViewContext.setValue(vk, ViewType.summarize(result,vk, this));
				} catch (Exception e) {
					// merge index
//					Object common = IndexSystem.generateUUID();
					Index index = IndexSystem.generateFreshIndex();
//					Index index = IndexSystem.generateFreshViewIndex(null, s.second, vk, s.third);
					finalViewContext.setValue(vk, index);
					for(ViewType v : result) {
						try {
							if(v==null || v==ViewType.empty()) continue;
							// in principle, we should reset downstream values
							Index indexValue = v.second.getIndexValue(vk);
							IndexableElement elementByIndexObject = null;
							try {
								elementByIndexObject = v.first.getElementByIndexObject(indexValue);
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
					finalViewContext.setValue(vk, ViewType.summarize(result,vk,this));
				} catch (Exception e) {
					// not confluent
					return nothing(e);
				}
			}
		}
		
		TypedGraph finalView = null;
		for(int i=0;i<result.length;i++) {
			ViewType v = result[i];
			if(v==null || v==ViewType.empty()) continue;
			v.second.setUpstream(finalViewContext, viewMappings.get(i));
			v.second.submit();
			if(finalView==null)
				finalView = v.first;
			else 
				finalView = finalView.additiveMerge(v.first);
		}
		
		if(finalView==null) 
			return ViewType.empty();
		
		this.submit(newSources);
		
		finalView.setConstraint(getConsistencyConstraint());
		
		return ViewType.makeView(finalView, finalViewContext);
	}

	@Override
	public SourceType backward(SourceType s, ViewType v) throws NothingReturnedException {
		Context[] newSources = new Context[this.bodies.length];
		Context[] newViews = new Context[this.bodies.length];
		SourceType[] results = new SourceType[this.bodies.length];
		TypedGraph[] interSources = new TypedGraph[this.bodies.length];
		TraceSystem[] interTraces = new TraceSystem[this.bodies.length];
		
		for(int i=0;i<this.bodies.length;i++) {
			newSources[i] = s.second.createDownstreamContext(this.bodies[i].getSourceDef(), sourceMappings.get(i));
//			newSources[i].initWith(s.second);
			
			newViews[i] = v.second.createDownstreamContext(this.bodies[i].getViewDef(), viewMappings.get(i));
//			newViews[i].initWith(v.second);
			
			newSources[i].setUpstream(s.second,sourceMappings.get(i));
			newViews[i].setUpstream(v.second, viewMappings.get(i));
			
			results[i] = this.bodies[i].backward(SourceType.makeSource(s.first, newSources[i], s.third), ViewType.makeView(v.first, newViews[i]));
			interSources[i] = results[i].first;
			interTraces[i] = results[i].third;
		}
		
		
		if(v.second.horizontalCorrnectness(newViews, interTraces)==false) {
			XmuCoreUtils.warning("Shared node issue in view detected");
		}
		
		Context finalSourcePost = this.createSourceContext();
		finalSourcePost.initWith(s.second);
		
		ContextType st = this.getSourceDef();
		for(FieldDef<?> sk : st.fields()) {
			if(sk.isElementType()) {
				try {
					finalSourcePost.setValue(sk, SourceType.summarize(results, sk,this));
				} catch (Exception e) {
//					Object value = IndexSystem.generateUUID();
					Index index = IndexSystem.generateFreshIndex();
//					Index index = IndexSystem.generateFreshSourceIndex(null, s.second, v.second, sk, s.third);
					finalSourcePost.setValue(sk, index);
					
					for(SourceType r : results) {
						try {
							Index indexValue = r.second.getIndexValue(sk);
							IndexableElement elementByIndexObject = null;
							try {
								elementByIndexObject = r.first.getElementByIndexObject(indexValue);
							} catch (NothingReturnedException noe) {
							}
							if(elementByIndexObject!=null) r.first.addIndex(index, elementByIndexObject);
						} catch (UninitializedException e1) {
							return nothing(e1);
						}
					}
				}
			} else {
				try {
					finalSourcePost.setValue(sk, SourceType.summarize(results, sk,this));
				} catch (Exception e) {
					return nothing(e);
				}
			}
		}
		
		
		
		TypedGraph finalSource = s.first.merge(interSources);
		TraceSystem finalTrace = TraceSystem.merge(interTraces);
		
		
		submit(newSources);
		submit(newViews);
		for(int i=0;i<results.length;i++) {
			SourceType r = results[i];
			r.second.setUpstream(finalSourcePost,sourceMappings.get(i));
			r.second.submit();
		}
		
		finalTrace.trace(this.getSerializationKey(), s.second, v.second, finalSourcePost);
		
		finalSource.setConstraint(getConsistencyConstraint());
		
		return SourceType.makeSource(finalSource, finalSourcePost, finalTrace);
	}

//	private Object summarize(SourceType[] results, FieldDef<?> sk) throws NothingReturnedException {
//		Object value = null;
//		boolean mayBeEmpty=true;
//		for(SourceType v : results) {
//			try {
//				if(value==null)
//					value = v.second.getValue(sk.getName());
//				else if(value.equals(v.second.getValue(sk))==false)
//					return internalError();
//				mayBeEmpty = false;
//			} catch (NothingReturnedException e) {
//			}catch (Exception e) {
//				return nothing();
//			}
//		}
//		if(mayBeEmpty) nothing();
//		return value;
//	}

	@Override
	protected GraphConstraint generateConsistencyConstraint() {
		GraphConstraint[] cons = new GraphConstraint[bodies.length];
		for(int i=0;i<bodies.length;i++) {
			XmuCore b = bodies[i];
			GraphConstraint cc = b.getConsistencyConstraint();
			Tuple2<String, String>[] sm = sourceMappings.get(i);
			Tuple2<String, String>[] vm = viewMappings.get(i);
			cons[i] = (gs,cs, gv, cv)->{
				Context dsc = cs.createDownstreamContext(b.getSourceDef(), sm);
				Context dvc = cv.createDownstreamContext(b.getViewDef(), vm);
				return cc.check(gs, dsc, gv, dvc);
			};
		}
		return GraphConstraint.and(cons);
	}

}
