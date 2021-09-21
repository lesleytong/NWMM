package edu.ustb.sei.mde.bxcore;
import java.util.List;

import edu.ustb.sei.mde.bxcore.exceptions.BidirectionalTransformationDefinitionException;
import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.bxcore.exceptions.UninitializedException;
import edu.ustb.sei.mde.bxcore.structures.Context;
import edu.ustb.sei.mde.bxcore.structures.ContextType;
import edu.ustb.sei.mde.bxcore.structures.FieldDef;
import edu.ustb.sei.mde.bxcore.structures.Index;
import edu.ustb.sei.mde.graph.pattern.Pattern;
import edu.ustb.sei.mde.graph.typedGraph.IndexSystem;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;
import edu.ustb.sei.mde.graph.typedGraph.constraint.GraphConstraint;
import edu.ustb.sei.mde.graph.typedGraph.constraint.GraphConstraint.ConstraintStatus;

public class ForEachMatchSource extends XmuCore {
	
	private XmuCore body;
	private Pattern sourcePattern;

	public ForEachMatchSource(Object key, ContextType sourceDef, Pattern sourcePattern, XmuCore body)
			throws BidirectionalTransformationDefinitionException {
		super(key, sourceDef, body.getViewDef());
		this.sourcePattern = sourcePattern;
		this.body = body;
	}

	@Override
	protected GraphConstraint generateConsistencyConstraint() {
		GraphConstraint bodyCons = body.generateConsistencyConstraint();
		
		return (gs,cs,gv,cv)->{
			List<Context> sourceMatches = sourcePattern.match(gs,cs);
			ConstraintStatus r = ConstraintStatus.sat;
			for(Context m : sourceMatches) {
				ConstraintStatus br = bodyCons.check(gs, m, gv, cv);
				if(br==ConstraintStatus.unenforceable) return ConstraintStatus.unenforceable;
				else if(br==ConstraintStatus.enforceable) r = ConstraintStatus.enforceable;
			}
			return r;
		};
	}

	@Override
	public ViewType forward(SourceType s) throws NothingReturnedException {
		List<Context> sourceMatches = sourcePattern.match(s.first, s.second);
		if(sourceMatches.isEmpty()) return ViewType.empty();
		
		ViewType[] views = new ViewType[sourceMatches.size()];
		
		for(int i=0;i<sourceMatches.size();i++) {
			Context match = sourceMatches.get(i);
			ViewType v = this.body.forward(s.replaceSecond(match));
			views[i] = v;
			match.setUpstream(s.second);
		}
		
		Context upstreamView = this.createViewContext();
		TypedGraph finalView = null;
		
		for(FieldDef<?> vk : this.getViewDef().fields()) {
			if(vk.isElementType()) {
				try {
					upstreamView.setValue(vk,  ViewType.summarize(views,vk,this));
				} catch (Exception e) {
//					Object common = IndexSystem.generateUUID();
//					Index index = Index.freshIndex(common);
					Index index = IndexSystem.generateFreshIndex();
//					Index index = IndexSystem.generateFreshViewIndex(null, s.second, vk, s.third);
					upstreamView.setValue(vk, index);
					for(ViewType v : views) {
						try {
							if(v==null || v==ViewType.empty()) continue;
							// in principle, we should reset downstream values
							v.second.setUpstream(upstreamView);
							v.first.addIndex(index, v.first.getElementByIndexObject((Index) v.second.getValue(vk.getName())));
						} catch (UninitializedException e1) {
							return nothing(e1);
						}
					}
				}
				
			} else {
				try {
					upstreamView.setValue(vk, ViewType.summarize(views,vk,this));
				} catch (Exception e) {
					return nothing(e);
				}
			}
		}
		
		for(ViewType v : views) {
			if(v==null || v==ViewType.empty()) continue;
			
			v.second.setUpstream(upstreamView);
			v.second.submit();

			if(finalView==null)
				finalView = v.first;
			else 
				finalView = finalView.additiveMerge(v.first);
		}
		
		if(finalView==null) 
			return ViewType.empty();
		
		this.submit(sourceMatches);
		
		finalView.setConstraint(getConsistencyConstraint());
		
		return ViewType.makeView(finalView, upstreamView);
	}

	@Override
	public SourceType backward(SourceType s, ViewType v) throws NothingReturnedException {
		List<Context> sourceMatches = sourcePattern.match(s.first, s.second);
		if(sourceMatches.isEmpty()) return s;
		
		sourceMatches.forEach(m->m.setUpstream(s.second));
		
		SourceType[] postSources = new SourceType[sourceMatches.size()];
		TypedGraph[] interSources = new TypedGraph[sourceMatches.size()];
		TraceSystem[] interTraces = new TraceSystem[sourceMatches.size()];
		Context[] newSources = new Context[sourceMatches.size()];
		
		Context downstreamView = v.second.createDownstreamContext(body.getViewDef());
		
		for(int i=0;i<sourceMatches.size();i++) {
			postSources[i] = body.backward(s.replaceSecond(sourceMatches.get(i)), v.replaceSecond(downstreamView));
			interSources[i] = postSources[i].first;
			interTraces[i] = postSources[i].third;
			newSources[i] = postSources[i].second;
		}
		
		Context finalSourcePost = s.second.createUpstreamContext(this.getSourceDef());
		
		ContextType st = this.getSourceDef();
		for(FieldDef<?> sk : st.fields()) {
			if(sk.isElementType()) {
				try {
					finalSourcePost.setValue(sk, SourceType.summarize(postSources, sk,this));
				} catch (Exception e) {
//					Object value = IndexSystem.generateUUID();
					Index index = IndexSystem.generateFreshIndex();
//					Index index = IndexSystem.generateFreshSourceIndex(null, s.second, v.second, sk, s.third);
					finalSourcePost.setValue(sk, index);
					
					for(SourceType r : postSources) {
						try {
							r.first.addIndex(index, r.first.getElementByIndexObject(r.second.getIndexValue(sk)));
						} catch (UninitializedException e1) {
							return nothing(e1);
						}
					}
				}
			} else {
				try {
					finalSourcePost.setValue(sk, SourceType.summarize(postSources, sk,this));
				} catch (Exception e) {
					return nothing(e);
				}
			}
		}
		
		for(Context m : newSources) {
			m.setUpstream(finalSourcePost);
		}
		
		TypedGraph finalSource = s.first.merge(interSources);
		TraceSystem finalTrace = TraceSystem.merge(interTraces);
		
		// do we have to check?
//		List<Context> doubleCheckSourceMatches = sourcePattern.match(finalSource, finalSourcePost);
//		if(doubleCheckSourceMatches.size()!=sourceMatches.size()) return nothing();
//		else {
//			for(Context dc : doubleCheckSourceMatches) {
//				if(sourceMatches.stream().noneMatch(m->Context.isIdentifical(m, dc)))
//					return nothing();
//			}
//		}
		
		submit(newSources);
		
		for(int i=0;i<postSources.length;i++) {
			SourceType r = postSources[i];
			r.second.setUpstream(finalSourcePost);
			r.second.submit();
		}
		
		finalTrace.trace(this.getSerializationKey(), s.second, v.second, finalSourcePost);
		finalSource.setConstraint(getConsistencyConstraint());
		return SourceType.makeSource(finalSource, finalSourcePost, finalTrace);
	}

}
