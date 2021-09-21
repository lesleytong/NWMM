package edu.ustb.sei.mde.graph.pattern.solving;

public class NoValueException extends Exception {
	private static final long serialVersionUID = -3147667404473898379L;
	private Variable<?> variable;
	
	public Variable<?> getVariable() {
		return variable;
	}

	public NoValueException(Variable<?> var) {
		super(var.getName()+" does not have a value");
		variable = var;
	}

}
