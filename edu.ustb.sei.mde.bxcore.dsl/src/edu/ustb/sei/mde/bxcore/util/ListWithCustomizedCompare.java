package edu.ustb.sei.mde.bxcore.util;

import java.util.ArrayList;
import java.util.Collection;

import edu.ustb.sei.mde.bxcore.dsl.bXCore.DashedPathType;

public class ListWithCustomizedCompare<E> extends ArrayList<E> {

	public ListWithCustomizedCompare() {
		super();
	}

	public ListWithCustomizedCompare(int initialCapacity) {
		super(initialCapacity);
	}

	public ListWithCustomizedCompare(Collection<? extends E> c) {
		super(c);
	}
	
	@Override
	public int indexOf(Object o) {
		if(o==null) return -1;
		if(o instanceof DashedPathType) {
			for(int i=0;i<size();i++) {
				Object r = get(i);
				if(PathTypeUtil.isEqual((DashedPathType) o, r)) return i;
			}
			return -1;
		} else 
			return super.indexOf(o);
	}
}
