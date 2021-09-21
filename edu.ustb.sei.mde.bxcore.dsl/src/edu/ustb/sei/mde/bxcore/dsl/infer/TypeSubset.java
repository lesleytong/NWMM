package edu.ustb.sei.mde.bxcore.dsl.infer;

import edu.ustb.sei.mde.bxcore.dsl.structure.TupleType;

public class TypeSubset extends TypeConstraint {
	public TupleType complete;
	public TupleType subset;
	
	
	static public TypeSubset makeSubset(TupleType subset, TupleType complete) {
		TypeSubset c = new TypeSubset();
		c.subset = subset;
		c.complete = complete;
		return c;
	}

}
