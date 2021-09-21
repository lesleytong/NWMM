package edu.ustb.sei.mde.bxcore.bigul;

import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;

public abstract class BijectiveTransformation<S, V> extends BidirectionalTransformation<S, V> {
	@Override
	final public S backward(S s, V v) throws NothingReturnedException {
		return backward(v);
	}
	
	abstract public S backward(V v) throws NothingReturnedException;
	
	public BijectiveTransformation<V,S> inverse() {
		return InverseBijectiveTransformation.makeInverse(this);
	}
}

class InverseBijectiveTransformation<S,V> extends BijectiveTransformation<S, V> {
	
	public static <S,V> BijectiveTransformation<S,V> makeInverse(BijectiveTransformation<V,S> base) {
		if(base instanceof InverseBijectiveTransformation) {
			return ((InverseBijectiveTransformation<V,S>) base).base;
		} else
			return new InverseBijectiveTransformation<S,V>(base);
		
	}
	
	protected BijectiveTransformation<V,S> base;
	public InverseBijectiveTransformation(BijectiveTransformation<V,S> base) {
		this.base = base;
	}
	

	@Override
	public S backward(V v) throws NothingReturnedException {
		return base.forward(v);
	}

	@Override
	public V forward(S s) throws NothingReturnedException {
		return base.backward(s);
	}
	
}
