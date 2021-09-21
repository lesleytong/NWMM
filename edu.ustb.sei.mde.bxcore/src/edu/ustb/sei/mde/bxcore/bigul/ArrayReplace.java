package edu.ustb.sei.mde.bxcore.bigul;

import java.util.List;

import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;

public class ArrayReplace extends BidirectionalTransformation<List<?>, List<?>> {

	@Override
	public List<?> forward(List<?> s) throws NothingReturnedException {
		return s;
	}

	@Override
	public List<?> backward(List<?> s, List<?> v) throws NothingReturnedException {
		if(s.size()!=v.size()) throw new NothingReturnedException();
		return v;
	}

}
