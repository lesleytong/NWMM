package edu.ustb.sei.mde.graph.pattern.solving;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SolverContext {
	
	private Map<Variable<?>,List<?>> valueMap = new HashMap<Variable<?>,List<?>>();
	
	public <T> void setDomain(Variable<?> var, List<T> domain) {
		this.valueMap.put(var, domain);
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getDomainCopy(Variable<?> var) {
		return (List<T>) new ArrayList<>(this.valueMap.get(var));
	}
	
	@SuppressWarnings("unchecked")
	public <T> ValueDomainIterator<T> getDomainIterator(Variable<?> var,Decision decision) {
		return new ValueDomainIterator<T>((Variable<T>)var, (List<T>) this.valueMap.get(var),decision);
	}
	
	public ChoiceIterator createChoiceIterator(Variable<?> var) {
		return new ChoiceIterator(this.valueMap.get(var));
	}
	
	public boolean isEmpty(Variable<?> var) {
		return this.valueMap.get(var).isEmpty();
	}
	
	public <T> T getValue(Variable<?> var) throws NoValueException{
		@SuppressWarnings("unchecked")
		List<T> list = (List<T>) this.valueMap.get(var);;
		if(list.size()==0) 
			throw new NoValueException(var);
		else 
			if(list.size()==1) return list.get(0);
		else 
			throw new NoValueException(var);
	}
	
	public <T> void addValue(Variable<?> var, T value) {
		@SuppressWarnings("unchecked")
		List<T> list = (List<T>) this.valueMap.get(var);
		list.add(value);
	}
	
	public <T> void decide(Variable<?> var, T value) {
		@SuppressWarnings("unchecked")
		List<T> list = (List<T>) this.valueMap.get(var);
		list.clear();
		list.add(value);
	}

	@SuppressWarnings("unchecked")
	public ContextState contextState(List<Constraint>... constraints) {
		ContextState state = ContextState.UNKNOWN;
		
		for(Entry<Variable<?>,List<?>> e : this.valueMap.entrySet()) {
			if(e.getValue().size()==0) {
				state = ContextState.NON_SOLUTION;
				break;
			}
			else if(e.getValue().size()!=1)
				state = ContextState.UNDECIDED;
		}
		
		if(state==ContextState.UNKNOWN) {
			// check constraints;
			for(List<Constraint> cons : constraints) {
				if(cons.stream().allMatch(c->c.satisfy(this)))
					state = ContextState.SOLUTION;
				else {
					state = ContextState.NON_SOLUTION;
					break;
				}
			}
		}
		
		return state;
	}

	public <T> T getSolution(SolutionBuilder<T> builder) {
		builder.begin();
		for(Entry<Variable<?>,List<?>> e : valueMap.entrySet()) {
//			builder.addValue(e.getKey(), e.getValue().get(0));
		}
		return builder.end();
	}
	
	public Map<Variable<?>,Object> getSolution() {
		return getSolution(SolutionBuilder.MAP_BUILDER);
	}
	
	
	
	enum ContextState {
		UNKNOWN,
		SOLUTION,
		NON_SOLUTION,
		UNDECIDED
	}
}
