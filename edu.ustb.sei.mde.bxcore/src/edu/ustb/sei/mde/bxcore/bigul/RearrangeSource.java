package edu.ustb.sei.mde.bxcore.bigul;

import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;

public class RearrangeSource<S,SP,V> extends BidirectionalTransformation<S, V> {
	protected BidirectionalTransformation<S,SP> reform;
	protected BidirectionalTransformation<SP, V> body;
	@Override
	public V forward(S s) throws NothingReturnedException {
		return body.forward(reform.forward(s));
	}
	@Override
	public S backward(S s, V v) throws NothingReturnedException {
		return reform.backward(s,body.backward(reform.forward(s),v));
	}
}
