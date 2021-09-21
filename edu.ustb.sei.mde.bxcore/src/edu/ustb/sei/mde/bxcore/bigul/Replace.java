package edu.ustb.sei.mde.bxcore.bigul;

import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;

public class Replace<S> extends BidirectionalTransformation<S, S> {

	@Override
	public S forward(S s) throws NothingReturnedException {
		return s;
	}

	@Override
	public S backward(S s, S v) throws NothingReturnedException {
		return v;
	}

}
