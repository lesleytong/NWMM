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

public class MatchView extends XmuCore {

	private Pattern patV;
	private XmuCore body;
	
	public MatchView(Object key, ContextType viewDef, Pattern p, XmuCore b) 
			throws BidirectionalTransformationDefinitionException {
		super(key, b.getSourceDef(), viewDef);
		patV = p;
		body = b;
		
		checkWellDefinedness();
	}
	
	@Override
	protected void checkWellDefinedness() throws BidirectionalTransformationDefinitionException {
		ContextType upType = this.getViewDef();
		
		Set<String> upKeys = upType.fields().stream().map(f->f.getName()).collect(Collectors.toSet());
		Set<String> downKeys = body.getViewDef().fields().stream().map(f->f.getName()).collect(Collectors.toSet());
		
		if(!downKeys.containsAll(upKeys) || patV.getType()!=body.getViewDef())
			throw new BidirectionalTransformationDefinitionException("Incompatible type");
	}

	@Override
	public ViewType forward(SourceType s) throws NothingReturnedException {
		ViewType v = body.forward(s);
		if(v==null) nothing();
		
		Context downstreamView = v.second;
		
		if(patV.isMatchOf(v.first, downstreamView)==false) 
			return nothing();
		
		Context upstreamView = downstreamView.createUpstreamContext(this.getViewDef());
		downstreamView.setUpstream(upstreamView);
		
		List<Context> views = patV.match(v.first, upstreamView);
		
		if(views.size()>0) {
			if(views.size()==1 && Context.isIdentifical(downstreamView, views.get(0))) {// quick check
				submit(downstreamView);
				v.first.setConstraint(getConsistencyConstraint());
				
				return ViewType.makeView(v.first, upstreamView);
			} else if(views.size()>1) {
				SourceType finalSource = null;
				for(Context viewMatch : views) {
					try {
						SourceType sp = body.backward(s, ViewType.makeView(v.first, viewMatch));
						if(finalSource==null) {
							if(Context.isIdentifical(downstreamView, viewMatch)) {
								finalSource = sp;
							}
							else nothing(); // we go back through a different way
						} else nothing(); // multiple alignment
					}  catch (Exception e) {
					}
				}
				if(finalSource!=null) {
					submit(downstreamView);
					
					v.first.setConstraint(getConsistencyConstraint());
					
					return ViewType.makeView(v.first, upstreamView);
				} else return nothing(); // GetPut fails, cannot go back
			} else return nothing(); // GetPut fails, no view match
		} else return nothing(); // no view match
	}

	@Override
	public SourceType backward(SourceType s, ViewType v) throws NothingReturnedException {
		Context upstreamView = v.second;
		
		List<Context> views = patV.match(v.first, upstreamView);
		Context downstreamView = null;
		
		if(views.size()==0) return nothing();
		else {
			SourceType finalSource = null;
			for(Context viewMatch : views) {
				try {
					viewMatch.setUpstream(upstreamView);
					
					SourceType sp = body.backward(s, ViewType.makeView(v.first, viewMatch));
					if(finalSource==null) {
						downstreamView = viewMatch;
						finalSource = sp;
					}
					else return nothing(); // multiple alignment
				}  catch (Exception e) {
					if(views.size()==1) {
						e.printStackTrace();
						return nothing(e);
					}
				}
			}
			if(finalSource!=null) {
				// check again to ensure PutTwice
				{
					finalSource.first.enforceOrder();
					GraphConstraint inner = getBodyConstraint();
					
					int count = 0;
					
					for(Context vc : views) {
						if(inner.check(finalSource.first, finalSource.second, v.first, vc)!=ConstraintStatus.unenforceable) {
							count++;
						}
					}
					
					if(count!=1) throw new NothingReturnedException("PutTwice may be violated!");
				}
				
				
				submit(downstreamView);
				finalSource.third.trace(this.getSerializationKey(), s.second, v.second, finalSource.second);
				
				finalSource.first.setConstraint(getConsistencyConstraint());
				
				return finalSource;
			}
			else return nothing();
		}
	}

	
	private GraphConstraint bodyConstraint = null;
	private GraphConstraint getBodyConstraint() {
		if(bodyConstraint==null) 
			bodyConstraint = body.getConsistencyConstraint();
		return bodyConstraint;
	}
	
	@Override
	protected GraphConstraint generateConsistencyConstraint() {
		return (gs,cs, gv,cv) -> {
			GraphConstraint inner = getBodyConstraint();
			List<Context> vv = patV.match(gv, cv);
			if(vv.size()==0) return ConstraintStatus.unenforceable;
			else {
				ConstraintStatus status = ConstraintStatus.sat;
				int count = 0;
				
				for(Context vc : vv) {
					if((status = inner.check(gs, cs, gv, vc))!=ConstraintStatus.unenforceable) {
						count++;
					}
				}
				
				if(count==1) return status;
				else return ConstraintStatus.unenforceable;
			}
		};
	}

}
