package edu.ustb.sei.mde.graph.pattern.solving;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Problem {
	protected List<Variable<?>> variables;
	protected List<Constraint> constraints;
	
	public Problem() {
		this.variables = new ArrayList<Variable<?>>();
		this.constraints = new ArrayList<Constraint>();
	}
	
	public void postVariable(Variable<?> v) {
		this.variables.add(v);
	}
	
	public void postConstraint(Constraint c) {
		List<Variable<?>> allVariablesInC = c.getVariables();
		
		if(this.variables.containsAll(allVariablesInC)==false)
			return;
		
		this.constraints.add(c);
		for(Variable<?> v : allVariablesInC) {
			v.register(c);
		}
	}
	
	public List<Constraint> getInitConstraints() {
		return this.constraints.stream()
				.filter(c->c.getPriority()==ConstraintPriority.INIT)
				.collect(Collectors.toList());
	}

	public List<Constraint> getProcessConstraints() {
		return this.constraints.stream()
				.filter(c->c.getPriority()==ConstraintPriority.HIGH||c.getPriority()==ConstraintPriority.NORMAL||c.getPriority()==ConstraintPriority.LOW)
				.collect(Collectors.toList());
	}

	public List<Constraint> getPostConstraints() {
		return this.constraints.stream()
				.filter(c->c.getPriority()==ConstraintPriority.FINAL)
				.collect(Collectors.toList());
	}

}
