package edu.ustb.sei.mde.graph.pattern.solving;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//
public class ChoiceIterator implements Iterator<Object> {
	private List<?> values;
	private int index = 0;
	public ChoiceIterator(List<?> v) {
		values = new ArrayList<Object>(v);
	}

	@Override
	public boolean hasNext() {
		return index<this.values.size();
	}

	@Override
	public Object next() {
		Object r = this.values.get(index);
		index++;
		return r;
	}
}