package edu.ustb.sei.mde.bxcore.bigul;

import java.util.List;

import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;

public class ArraySkip extends BidirectionalTransformation<List<?>, List<?>> {

	@Override
	public List<?> forward(List<?> s) throws NothingReturnedException {
		return s;
	}

	@Override
	public List<?> backward(List<?> s, List<?> v) throws NothingReturnedException {
		if(s.size()!=v.size()) throw new NothingReturnedException();
		
		for(int i=0;i<v.size();i++) {
			Object si = s.get(i);
			Object vi = v.get(i);
			
			if(!si.equals(vi)) 
				throw new NothingReturnedException();
		}
		
		return s;
	}

}
