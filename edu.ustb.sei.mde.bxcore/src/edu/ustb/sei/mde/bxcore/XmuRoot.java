package edu.ustb.sei.mde.bxcore;

import edu.ustb.sei.mde.bxcore.exceptions.BidirectionalTransformationDefinitionException;
import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.bxcore.structures.ContextType;
import edu.ustb.sei.mde.graph.typedGraph.constraint.GraphConstraint;

public class XmuRoot extends XmuCore {
	
	private XmuRoot body;

	public XmuRoot(Object key, XmuRoot body)
			throws BidirectionalTransformationDefinitionException {
		super(key, body.getSourceDef(), body.getViewDef());
		this.body = body;
	}

	@Override
	protected GraphConstraint generateConsistencyConstraint() {
		return body.generateConsistencyConstraint();
	}

	@Override
	public ViewType forward(SourceType s) throws NothingReturnedException {
		ViewType v = body.forward(s);
		v.first.enforceOrder();
		return v;
	}

	@Override
	public SourceType backward(SourceType s, ViewType v) throws NothingReturnedException {
		SourceType sp = body.backward(s, v);
		sp.first.enforceOrder();
		return sp;
	}

}
