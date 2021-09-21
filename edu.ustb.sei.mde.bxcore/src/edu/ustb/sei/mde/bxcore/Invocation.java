package edu.ustb.sei.mde.bxcore;


import java.util.function.Supplier;

import edu.ustb.sei.mde.bxcore.exceptions.BidirectionalTransformationDefinitionException;
import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.bxcore.structures.Context;
import edu.ustb.sei.mde.bxcore.structures.ContextType;
import edu.ustb.sei.mde.graph.typedGraph.constraint.GraphConstraint;
import edu.ustb.sei.mde.structure.Tuple2;

public class Invocation extends XmuCore {

	public Invocation(Object key, ContextType sourceDef, ContextType viewDef, Tuple2<String,String>[] sourceKeyMappings, Tuple2<String,String>[] viewKeyMappings, Supplier<XmuCore> bodyProvider)
			throws BidirectionalTransformationDefinitionException {
		super(key, sourceDef, viewDef); // to support lazy binding, we must specify the source and view types
		this.body = null;
		this.bodyProvider = bodyProvider;
		this.sourceKeyMappings = sourceKeyMappings;
		this.viewKeyMappings = viewKeyMappings;
	}
	
	protected XmuCore body;
	protected Supplier<XmuCore> bodyProvider;
	protected Tuple2<String,String>[] sourceKeyMappings;
	protected Tuple2<String,String>[] viewKeyMappings;
	
	protected XmuCore getBody() {
		if(body==null) { // lazy binding
			body = this.bodyProvider.get();
		}
		return body;
	}

	@Override
	protected GraphConstraint generateConsistencyConstraint() {
		return (gs,cs,gv,cv)->{
			GraphConstraint g = this.getBody().generateConsistencyConstraint();
			Context ds = cs.createDownstreamContext(this.getBody().getSourceDef(), sourceKeyMappings);
			Context dv = cv.createDownstreamContext(this.getBody().getViewDef(), viewKeyMappings);
			return g.check(gs, ds, gv, dv);
		};
	}

	@Override
	public ViewType forward(SourceType s) throws NothingReturnedException {
		ContextType upstreamViewType = this.getViewDef();
		Context downstreamSource = s.second.createDownstreamContext(this.getBody().getSourceDef(), sourceKeyMappings);
		downstreamSource.setUpstream(s.second, sourceKeyMappings);
		
		ViewType view = this.getBody().forward(SourceType.makeSource(s.first, downstreamSource, s.third));
		if(view==null || view==ViewType.empty()) return view;
		
		Context upstreamView = view.second.createUpstreamContext(upstreamViewType, viewKeyMappings);
		view.second.setUpstream(upstreamView, viewKeyMappings);
		
		view.first.setConstraint(getConsistencyConstraint());
		
		downstreamSource.submit();
		view.second.submit();
		
		return ViewType.makeView(view.first, upstreamView);
	}

	@Override
	public SourceType backward(SourceType s, ViewType v) throws NothingReturnedException {
		Context downstreamSource = s.second.createDownstreamContext(this.getBody().getSourceDef(), sourceKeyMappings);
		downstreamSource.setUpstream(s.second, sourceKeyMappings);
		
		Context downstreamView = v.second.createDownstreamContext(this.getBody().getViewDef(), viewKeyMappings);
		downstreamView.setUpstream(v.second, viewKeyMappings);
		
		SourceType sourcePost = this.getBody().backward(SourceType.makeSource(s.first, downstreamSource, s.third), ViewType.makeView(v.first, downstreamView));
		
		Context upstreamSourcePost = sourcePost.second.createUpstreamContext(getSourceDef(), sourceKeyMappings);
		sourcePost.second.setUpstream(upstreamSourcePost, sourceKeyMappings);
		
		submit(downstreamSource, downstreamView, sourcePost.second);
		
		sourcePost.third.trace(this.getSerializationKey(), s.second, v.second, upstreamSourcePost);
		
		return SourceType.makeSource(sourcePost.first, upstreamSourcePost, sourcePost.third);
	}

}
