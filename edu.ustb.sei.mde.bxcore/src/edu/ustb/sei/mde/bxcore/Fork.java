package edu.ustb.sei.mde.bxcore;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
import edu.ustb.sei.mde.structure.Tuple3;

public class Fork extends XmuCore {
	private Tuple3<Tuple2<String,String>[],Tuple2<String,String>[], XmuCore>[] forks;
	private Tuple2<String,String>[][] allSourceMappings;
	private Tuple2<String,String>[][] allViewMappings;

	@SuppressWarnings("unchecked")
	public Fork(Object key, ContextType sourceDef, ContextType viewDef, Tuple3<Tuple2<String,String>[],Tuple2<String,String>[], XmuCore>[] forks) 
			throws BidirectionalTransformationDefinitionException {
		super(key, sourceDef, viewDef);
		this.forks = forks;
		this.allSourceMappings = (Tuple2<String, String>[][]) Arrays.stream(forks).map(f->f.first).toArray(i->new Tuple2[i][]);
		this.allViewMappings = (Tuple2<String, String>[][]) Arrays.stream(forks).map(f->f.second).toArray(i->new Tuple2[i][]);
		
		checkWellDefinedness();
	}
	
	@Override
	protected void checkWellDefinedness() throws BidirectionalTransformationDefinitionException {
		Set<FieldDef<?>> viewFields = new HashSet<>();
		
		for(Tuple3<Tuple2<String,String>[],Tuple2<String,String>[], XmuCore> fork : forks) {
			Set<FieldDef<?>> downSrcFields = new HashSet<>();
			
			for(Tuple2<String,String> m : fork.first) {
				FieldDef<?> up = this.getSourceDef().getField(m.first);
				FieldDef<?> down = fork.third.getSourceDef().getField(m.second);
				if(up==null || down==null)
					throw new BidirectionalTransformationDefinitionException("Invalid mapping "+m);
				
				downSrcFields.add(down);
			}
			
			if(!downSrcFields.containsAll(fork.third.getSourceDef().fields()))
				throw new BidirectionalTransformationDefinitionException("Invalid downstream fork type: "+fork.third.getSerializationKey());
			
			Set<FieldDef<?>> downViwFields = new HashSet<>();
			for(Tuple2<String,String> m : fork.second) {
				FieldDef<?> up = this.getViewDef().getField(m.first);
				FieldDef<?> down = fork.third.getViewDef().getField(m.second);
				if(up==null || down==null)
					throw new BidirectionalTransformationDefinitionException("Invalid mapping "+m);
				
				downViwFields.add(down);
				viewFields.add(up);
			}
			
			if(!downViwFields.containsAll(fork.third.getViewDef().fields()))
				throw new BidirectionalTransformationDefinitionException("Invalid downstream fork type: "+fork.third.getSerializationKey());
		}
		
		if(!viewFields.containsAll(this.getViewDef().fields()))
			throw new BidirectionalTransformationDefinitionException("Not all view fields are converted! "+viewFields);
	}


	@Override
	protected GraphConstraint generateConsistencyConstraint() {
		GraphConstraint[] cons = new GraphConstraint[forks.length];
		for(int i=0;i<forks.length;i++) {			
			GraphConstraint g = forks[i].third.getConsistencyConstraint();
			Tuple3<Tuple2<String, String>[], Tuple2<String, String>[], XmuCore> fork = forks[i];
			cons[i] = (gs,cs,gv,cv)->{
				Context ds = cs.createDownstreamContext(fork.third.getSourceDef(), fork.first);
				Context dv = cs.createDownstreamContext(fork.third.getViewDef(), fork.second);
				
				return g.check(gs, ds, gv, dv);
			};
		}
		
		return GraphConstraint.and(cons);
	}

	@Override
	public ViewType forward(SourceType s) throws NothingReturnedException {
		ViewType[] viewResults = new ViewType[this.forks.length];
		Context[] downStreamSources = new Context[this.forks.length];
		
		for(int i=0;i<this.forks.length;i++) {
//			Context newSource = s.second.downstream(this.forks[i].first, this.forks[i].third.getSourceDef(), false);
			Context newSource = s.second.createDownstreamContext(this.forks[i].third.getSourceDef(), this.forks[i].first, false);
			
			newSource.setUpstream(s.second, this.forks[i].first);
			downStreamSources[i] = newSource;
			
			viewResults[i] = forks[i].third.forward(SourceType.makeSource(s.first, newSource, s.third));
		}
		
		ContextType upstreamViewType = this.getViewDef();
		Context upstreamView = this.createViewContext();
		
		for(FieldDef<?> uk : upstreamViewType.fields()) {
			try {
				upstreamView.setValue(uk, ViewType.summarize(viewResults,uk,allViewMappings, this));
			} catch (Exception e) {
				if(uk.isElementType()) {
//					Object common = IndexSystem.generateUUID();
					Index index = IndexSystem.generateFreshIndex();
//					Index index = IndexSystem.generateFreshViewIndex(null, s.second, uk, s.third);
					upstreamView.setValue(uk, index);
					
					for(int i=0;i<viewResults.length;i++) {
						ViewType v = viewResults[i];
						if(v==null || v==ViewType.empty()) continue;
						Tuple2<String,String>[] mappings = this.forks[i].second;
						Set<FieldDef<?>> dks = XmuCoreUtils.findDownKeys(uk, mappings,this.forks[i].third.getViewDef());
						for(FieldDef<?> dk : dks) {
							try {
								// in principle, we should reset downstream values
								Index indexValue = v.second.getIndexValue(dk);
								IndexableElement elementByIndexObject = null;
								try {
									elementByIndexObject = v.first.getElementByIndexObject(indexValue);
								} catch (Exception noe) {
								}
								if(elementByIndexObject!=null) v.first.addIndex(index, elementByIndexObject);
							} catch (UninitializedException e1) {
								return nothing(e1);
							}
						}
					}
					
				} else return nothing();
			}
		}
		
		TypedGraph finalView = null;
		for(int i=0;i<this.forks.length;i++) {
			ViewType v = viewResults[i];
			if(v==null || v==ViewType.empty()) continue;
			v.second.setUpstream(upstreamView, this.forks[i].second);
			v.second.submit();
			if(finalView==null) finalView = v.first;
			else finalView = finalView.additiveMerge(v.first);
		}
		
		if(finalView==null) 
			return ViewType.empty();
		
		this.submit(downStreamSources);
		finalView.setConstraint(getConsistencyConstraint());
		
		return ViewType.makeView(finalView, upstreamView);
	}

	@Override
	public SourceType backward(SourceType s, ViewType v) throws NothingReturnedException {
		Context[] downstreamSources = new Context[this.forks.length];
		Context[] downstreamViews = new Context[this.forks.length];
		
		SourceType[] sourceResults = new SourceType[this.forks.length];
		
		TypedGraph[] interSources = new TypedGraph[this.forks.length];
		TraceSystem[] interTraces = new TraceSystem[this.forks.length];
		
		for(int i=0;i<this.forks.length;i++) {
//			downstreamSources[i] = s.second.downstream(this.forks[i].first, this.forks[i].third.getSourceDef(), true);
//			downstreamViews[i] = v.second.downstream(this.forks[i].second, this.forks[i].third.getViewDef(), true);
			downstreamSources[i] = s.second.createDownstreamContext(this.forks[i].third.getSourceDef(), this.forks[i].first);
			downstreamSources[i].setUpstream(s.second, this.forks[i].first);
			downstreamViews[i] = v.second.createDownstreamContext(this.forks[i].third.getViewDef(), this.forks[i].second);
			downstreamViews[i].setUpstream(v.second, this.forks[i].second);
			
			sourceResults[i] = this.forks[i].third.backward(SourceType.makeSource(s.first, downstreamSources[i], s.third), ViewType.makeView(v.first, downstreamViews[i]));
			
			interSources[i] = sourceResults[i].first;
			interTraces[i] = sourceResults[i].third;
		}
		
		if(v.second.horizontalCorrnectness(downstreamViews, interTraces)==false) {
			XmuCoreUtils.failure("Shared node issue in view detected");
		}
		
		Context finalSourcePost = this.createSourceContext();
		finalSourcePost.initWith(s.second);
		
		ContextType st = this.getSourceDef();
		
		for(FieldDef<?> uk : st.fields()) {
			try {
				finalSourcePost.setValue(uk, SourceType.summarize(sourceResults, uk, allSourceMappings, this));
			} catch (Exception e) {
				if(uk.isElementType()) {
//					Object value = IndexSystem.generateUUID();
					Index index = IndexSystem.generateFreshIndex();
//					Index index = IndexSystem.generateFreshSourceIndex(null, s.second, v.second, uk, s.third);
					finalSourcePost.setValue(uk, index);
					
					for(int i=0;i<sourceResults.length;i++) {
						SourceType sp = sourceResults[i];
						Tuple2<String,String>[] mappings = this.forks[i].second;
						Set<FieldDef<?>> dks = XmuCoreUtils.findDownKeys(uk, mappings, this.forks[i].third.getSourceDef());
						
						for(FieldDef<?> dk : dks) {
							try {
								// in principle, we should reset downstream values
								Index indexValue = sp.second.getIndexValue(dk);
								IndexableElement elementByIndexObject = null;
								try {
									elementByIndexObject = sp.first.getElementByIndexObject(indexValue);
								} catch (NothingReturnedException noe) {
								}
								if(elementByIndexObject!=null) sp.first.addIndex(index, elementByIndexObject);
							} catch (UninitializedException e1) {
								return nothing(e1);
							}
						}
					}
				} else return nothing(e);
			}
		}
		
		TypedGraph finalSource = s.first.merge(interSources);
		TraceSystem finalTrace = TraceSystem.merge(interTraces);
		
		
		submit(downstreamSources);
		submit(downstreamViews);
		for(int i=0;i<this.forks.length;i++) {
			SourceType r = sourceResults[i];
			r.second.setUpstream(finalSourcePost, this.forks[i].first);
			r.second.submit();
		}
		
		finalTrace.trace(this.getSerializationKey(), s.second, v.second, finalSourcePost);
		finalSource.setConstraint(getConsistencyConstraint());
		
		return SourceType.makeSource(finalSource, finalSourcePost, finalTrace);
	}

}
