package edu.ustb.sei.mde.bxcore.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import edu.ustb.sei.mde.bxcore.dsl.bXCore.DashedPathType;
import edu.ustb.sei.mde.bxcore.util.PathTypeUtil;

public class SetWithCustomizedCompare<E> implements Set<E> {
	private List<E> array;
	
	public SetWithCustomizedCompare() {
		array = new LinkedList<>();
	}

	@Override
	public int size() {
		return array.size();
	}
	
	@Override
	public boolean isEmpty() {
		return array.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		if(o==null) return false;
		if(o instanceof DashedPathType) {
			for(E e : array) {
				if(PathTypeUtil.isEqual((DashedPathType)o, e)) return true;
			}
			return false;
		} else {
			return array.contains(o);
		}
	}

	@Override
	public Iterator<E> iterator() {
		return array.iterator();
	}

	@Override
	public Object[] toArray() {
		return array.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return array.toArray(a);
	}

	@Override
	public boolean add(E e) {
		if(!contains(e)) {
			array.add(e);
			return true;
		}
		return false;
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

}
