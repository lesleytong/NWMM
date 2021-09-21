package edu.ustb.sei.mde.graph.pattern.solving;

import java.util.ArrayList;
import java.util.List;

import edu.ustb.sei.mde.structure.Tuple2;

public class Decision {
	protected Decision parent;
	protected Variable<?> choice;
	protected ChoiceIterator valueIterator;
	protected SolverContext context;
	protected int depth;
	
	public int getDepth() {
		return depth;
	}

	public Decision(SolverContext context) {
		parent = null;
		choice = null;
		depth = 0;
		this.context = context;
	}
	
	public Decision(Variable<?> var, Decision parent) {
		this.choice = var;
		this.parent = parent;
		this.context = parent.context;
		this.depth = parent.depth+1;
		
		if(this.choice!=null) 
			valueIterator = context.createChoiceIterator(choice);
		else valueIterator = null;
	}
	
	protected List<Tuple2<Variable<?>, List<?>>> originalCopy = new ArrayList<Tuple2<Variable<?>, List<?>>>(); 
	
	public void restore() {
		for(Tuple2<Variable<?>, List<?>> t : originalCopy) {
			context.setDomain(t.first,t.second);
		}
		originalCopy.clear();
	}
	
	public void decide(Object value) {
		List<Object> delta = new ArrayList<Object>();
		
		ValueDomainIterator<?> domain = context.getDomainIterator(this.choice,this);
		
		while(domain.hasNext()) {
			Object o = domain.next();
			if(o==value) 
				continue;
			else {
				delta.add(o);
			}
		}
		domain.decide(value);
		notify(choice,delta);
	}
	
	public boolean hasNext() {
		if(valueIterator==null) return false;
		else return valueIterator.hasNext();
	}
	
	public Object next() {
		return valueIterator.next();
	}
	
	public void notify(Variable<?> source, List<?> removedValues) {
		for(Constraint c : source.constaints) {
			c.apply(source, removedValues, this, context);
		}
	}

	public Decision getParent() {
		return parent;
	}

	public void change(Variable<?> variable) {
		if(originalCopy.stream().anyMatch(c->(c.first==variable))) {
			return;
		}
		originalCopy.add(new Tuple2<Variable<?>,List<?>>(variable, context.getDomainCopy(variable)));
	}
}