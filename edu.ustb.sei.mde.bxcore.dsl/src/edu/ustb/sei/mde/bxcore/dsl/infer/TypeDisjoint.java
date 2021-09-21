package edu.ustb.sei.mde.bxcore.dsl.infer;

import edu.ustb.sei.mde.bxcore.dsl.structure.TupleType;

public class TypeDisjoint extends TypeConstraint {
	public TupleType left;
	public TupleType right;
	
	static public TypeDisjoint makeDisjoint(TupleType left, TupleType right) {
		TypeDisjoint d = new TypeDisjoint();
		d.left = left;
		d.right = right;
		return d;
	}
}
