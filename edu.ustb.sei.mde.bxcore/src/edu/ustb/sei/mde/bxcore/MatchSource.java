package edu.ustb.sei.mde.bxcore;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import edu.ustb.sei.mde.bxcore.exceptions.BidirectionalTransformationDefinitionException;
import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.bxcore.structures.Context;
import edu.ustb.sei.mde.bxcore.structures.ContextType;
import edu.ustb.sei.mde.graph.pattern.Pattern;
import edu.ustb.sei.mde.graph.typedGraph.constraint.GraphConstraint;
import edu.ustb.sei.mde.graph.typedGraph.constraint.GraphConstraint.ConstraintStatus;

public class MatchSource extends XmuCore {

	private Pattern patS;
	private XmuCore body;
	public MatchSource(Object key, ContextType sourceDef, Pattern p, XmuCore b) 
			throws BidirectionalTransformationDefinitionException {
		super(key, sourceDef, b.getViewDef());
		patS = p;
		body = b;
		
		checkWellDefinedness();
	}
	
	@Override
	protected void checkWellDefinedness() throws BidirectionalTransformationDefinitionException {
		ContextType upType = this.getSourceDef();
		
		Set<String> upKeys = upType.fields().stream().map(f->f.getName()).collect(Collectors.toSet());
		Set<String> downKeys = body.getSourceDef().fields().stream().map(f->f.getName()).collect(Collectors.toSet());
		
		if(downKeys.containsAll(upKeys)==false 
				|| patS.getType()!=body.getSourceDef())
			throw new BidirectionalTransformationDefinitionException("Incompatible type");
	}

	@Override
	public ViewType forward(SourceType s) throws NothingReturnedException {
		Context upstreamSource = s.second;
		
		List<Context> sourceMatches = patS.match(s.first, upstreamSource);
		if(sourceMatches.size()==1) {
			Context downstreamSource = sourceMatches.get(0);
			downstreamSource.setUpstream(upstreamSource);
			
			SourceType innerSource = SourceType.makeSource(s.first, downstreamSource, s.third);
			ViewType v = body.forward(innerSource);
			if(v==null || v==ViewType.empty()) return v;
			
			submit(downstreamSource);
			v.first.setConstraint(getConsistencyConstraint());
			
			return v;
		} else
			return nothing();
	}

	@Override
	public SourceType backward(SourceType s, ViewType v) throws NothingReturnedException {
		Context upstreamSource = s.second;
		
		List<Context> sourceMatches = patS.match(s.first, upstreamSource);
		SourceType sp;
		Context downstreamSource = null;
		if(sourceMatches.size()==0) {
			downstreamSource = upstreamSource.createDownstreamContext(body.getSourceDef());
			downstreamSource.setUpstream(upstreamSource);
			patS.getType().initializeSource(downstreamSource,s.second,v.second,s.third);
			sp = body.backward(SourceType.makeSource(s.first, downstreamSource, s.third), v);
		} else if(sourceMatches.size()==1) {
			downstreamSource = sourceMatches.get(0);
			downstreamSource.setUpstream(upstreamSource);
			sp = body.backward(SourceType.makeSource(s.first, downstreamSource, s.third), v);
		} else {
			return nothing();
		}

		
		
		Context downstreamSourcePost = sp.second;
		Context upstreamSourcePost = downstreamSourcePost.createUpstreamContext(this.getSourceDef());
		downstreamSourcePost.setUpstream(upstreamSourcePost);
		
		List<Context> sourceMatchesPost = patS.match(sp.first, upstreamSourcePost);

		if(sourceMatchesPost.size()==1 && Context.isIdentifical(downstreamSourcePost, sourceMatchesPost.get(0))) {
			submit(downstreamSource,downstreamSourcePost);
			sp.third.trace(this.getSerializationKey(), s.second, v.second, upstreamSourcePost);
			
			sp.first.setConstraint(getConsistencyConstraint());
			
			return SourceType.makeSource(sp.first, upstreamSourcePost, sp.third);
		}
		else 
			return nothing();
	}
	
	@Override
	protected GraphConstraint generateConsistencyConstraint() {
		return (gs,cs,gv,cv) -> {
			GraphConstraint inner = body.getConsistencyConstraint();
			List<Context> ss = patS.match(gs, cs);
			if(ss.size()!=1)
				return ConstraintStatus.unenforceable;
			else return inner.check(gs, ss.get(0), gv, cv);
		};
	}

}
