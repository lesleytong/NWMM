package edu.ustb.sei.mde.bxcore.bigul;

import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.structure.Tuple2;

public class Product<S1, S2, V1, V2> extends BidirectionalTransformation<Tuple2<S1, S2>, Tuple2<V1, V2>> {
	
	protected BidirectionalTransformation<S1,V1> left;
	protected BidirectionalTransformation<S2,V2> right;

	@Override
	public Tuple2<V1, V2> forward(Tuple2<S1, S2> s) throws NothingReturnedException {
		return new Tuple2<V1, V2>(left.forward(s.first),right.forward(s.second));
	}

	@Override
	public Tuple2<S1, S2> backward(Tuple2<S1, S2> s, Tuple2<V1, V2> v) throws NothingReturnedException {
		return new Tuple2<S1, S2>(left.backward(s.first,v.first),right.backward(s.second,v.second));
	}
	
}
