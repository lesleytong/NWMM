package edu.ustb.sei.mde.bxcore.dsl.infer;

import java.util.List;

import edu.ustb.sei.mde.bxcore.dsl.bXCore.VarMapping;
import edu.ustb.sei.mde.bxcore.dsl.structure.TupleType;

public class TypeEqual extends TypeConstraint {
	public TupleType left;
	public TupleType right;
	public int sort;
	public List<VarMapping> mappings;
	
	final static int EQUAL = 0;
	final static int LEFT_ABSTRACT = -1;
	final static int RIGHT_ABSTRACT = 1;
	
	static public TypeEqual makeEqual(TupleType l, TupleType r) {
		TypeEqual eq = new TypeEqual();
		eq.left = l;
		eq.right = r;
		eq.sort = EQUAL;
		eq.mappings = null;
		return eq;
	}
	
	static public TypeEqual makeLeftAbstractEqual(TupleType l, TupleType r) {
		TypeEqual eq = new TypeEqual();
		eq.left = l;
		eq.right = r;
		eq.sort = LEFT_ABSTRACT;
		eq.mappings = null;
		return eq;
	}
	
	static public TypeEqual makeRightAbstractEqual(TupleType l, TupleType r) {
		TypeEqual eq = new TypeEqual();
		eq.left = l;
		eq.right = r;
		eq.sort = RIGHT_ABSTRACT;
		eq.mappings = null;
		return eq;
	}
	
	static public TypeEqual makeMapping(TupleType l, TupleType r, List<VarMapping> mappings) {
		TypeEqual eq = new TypeEqual();
		eq.left = l;
		eq.right = r;
		eq.sort = EQUAL;
		eq.mappings = mappings;
		return eq;
	}
	
	static public TypeEqual makeLeftAbstractMapping(TupleType l, TupleType r, List<VarMapping> mappings) {
		TypeEqual eq = new TypeEqual();
		eq.left = l;
		eq.right = r;
		eq.sort = LEFT_ABSTRACT;
		eq.mappings = mappings;
		return eq;
	}
	
	static public TypeEqual makeRightAbstractMapping(TupleType l, TupleType r, List<VarMapping> mappings) {
		TypeEqual eq = new TypeEqual();
		eq.left = l;
		eq.right = r;
		eq.sort = RIGHT_ABSTRACT;
		eq.mappings = mappings;
		return eq;
	}
}
