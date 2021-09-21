package edu.ustb.sei.mde.graph.pattern.solving;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;


public class ValueDomainIterator<E> implements Iterator<E> {
	public ValueDomainIterator(Variable<E> variable, List<E> values, Decision associatedDecision) {
		super();
		this.variable = variable;
		this.values = values;
		this.associatedDecision = associatedDecision;
	}

	private Decision associatedDecision;
	private List<E> values;
	private int index = 0;
	private Variable<E> variable;
	
	@Override
	public boolean hasNext() {
		return index<this.values.size();
	}

	@Override
	public E next() {
		E r = this.values.get(index);
		index++;
		return r;
	}

	@SuppressWarnings("unchecked")
	public void decide(Object value) {
		associatedDecision.change(variable);
		values.clear();
		values.add((E)value);
		index = 1;
	}
	
	public void remove() {
		associatedDecision.change(variable);
		values.remove(index-1);
		index--;
	}
	
	public boolean exists(Predicate<E> p) {
		while(hasNext()) {
			if(p.test(next()))
				return true;
		}
		return false;
	}
	
	public void restart() {
		index = 0;
	}

}
