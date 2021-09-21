package edu.ustb.sei.mde.bxcore.bigul;

import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;

public class Skip<S> extends BidirectionalTransformation<S, S>{

	@Override
	public S forward(S s) {
		return s;
	}

	@Override
	public S backward(S s, S v) throws NothingReturnedException {
		if(checkValueEqual(s,v))
			return s;
		else throw new NothingReturnedException();
	}

	private boolean checkValueEqual(Object s, Object v) {
		if(s==v) return true;
		else if(s!=null && v!=null) {
			if(s.getClass().isArray() && v.getClass().isArray()) {
				Object[] sVals = (Object[])s;
				Object[] vVals = (Object[])v;
				
				if(sVals.length!=vVals.length) return false;
				else {
					for(int i=0;i<sVals.length;i++) {
						Object sv = sVals[i];
						Object vv = vVals[i];
						if(!checkValueEqual(sv, vv)) return false;
					}
					return true;
				}
			} else {
				return s.equals(v);
			}
		} else return false;
	}
}
