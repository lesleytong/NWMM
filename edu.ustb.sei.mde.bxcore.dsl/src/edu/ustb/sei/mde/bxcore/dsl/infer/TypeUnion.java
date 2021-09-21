package edu.ustb.sei.mde.bxcore.dsl.infer;

import java.util.ArrayList;
import java.util.List;

import edu.ustb.sei.mde.bxcore.dsl.structure.TupleType;

public class TypeUnion extends TypeConstraint {
	public TupleType unionType;
	public List<TupleType> types = new ArrayList<TupleType>();
	public boolean subset;
	
	static public TypeUnion makeSubSet(TupleType type, TupleType subset) {
		TypeUnion tu = new TypeUnion();
		tu.unionType = type;
		tu.types.add(subset);
		tu.subset = true;
		return tu;
	}
	
	static public TypeUnion makeUnion(TupleType union) {
		TypeUnion tu = new TypeUnion();
		tu.unionType = union;
		tu.subset = false;
		return tu;
	}
}
