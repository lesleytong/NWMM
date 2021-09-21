package edu.ustb.sei.mde.graph.pattern.solving;

import java.util.ArrayList;
import java.util.List;

public class Variable<T> {

	protected List<Constraint> constaints = new ArrayList<Constraint>();
	protected String name;
	
	public String getName() {
		return name;
	}
	
	static int varID = 0;
	
	public Variable() {
		this("VAR_"+varID);
		varID++;
	}
	
	public Variable(String name) {
		this.name=name;
	}
	
	public void register(Constraint c) {
		this.constaints.add(c);
	}
	
}
