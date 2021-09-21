package edu.ustb.sei.mde.graph.pattern;

import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.bxcore.exceptions.UninitializedException;
import edu.ustb.sei.mde.bxcore.structures.Context;
import edu.ustb.sei.mde.graph.INode;
import edu.ustb.sei.mde.graph.type.DataTypeNode;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;

public class PatternValueNode extends PatternElement<DataTypeNode> implements INode {
	public String toString() {
		return this.getName()+(isCollection() ? "[]" : "")+":"+this.getType().getName();
	}
	
	@Override
	public boolean isMatchOf(Context match, TypedGraph graph) {
		try {
			 // we do not check value type
			match.getValue(getName());
			return true;
		} catch (UninitializedException | NothingReturnedException e) {
			return false;
		}
	}
}
