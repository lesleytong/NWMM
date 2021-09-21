package edu.ustb.sei.mde.bxcore.bigul;

import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;

public class RearrangeView<S,V,VP> extends BidirectionalTransformation<S, V> {
	protected BijectiveTransformation<V,VP> reform;
	protected BidirectionalTransformation<S, VP> body;
	@Override
	public V forward(S s) throws NothingReturnedException {
		return reform.backward(body.forward(s));
	}
	@Override
	public S backward(S s, V v) throws NothingReturnedException {
		return body.backward(s, reform.forward(v));
	}
}
