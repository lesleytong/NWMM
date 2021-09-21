package edu.ustb.sei.mde.bxcore.dsl.infer;

import java.util.ArrayList;
import java.util.List;

import edu.ustb.sei.mde.bxcore.dsl.structure.TupleType;

public class UnsolvedTupleType extends TupleType {
	public List<String> candidates = new ArrayList<>();
	
	@Override
	public boolean equals(Object o) {
		return this==o;
	}
}
