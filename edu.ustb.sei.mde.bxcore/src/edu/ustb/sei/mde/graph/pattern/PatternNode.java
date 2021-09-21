package edu.ustb.sei.mde.graph.pattern;

import java.util.List;

import edu.ustb.sei.mde.bxcore.structures.Context;
import edu.ustb.sei.mde.bxcore.structures.Index;
import edu.ustb.sei.mde.graph.INode;
import edu.ustb.sei.mde.graph.type.TypeNode;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;
import edu.ustb.sei.mde.graph.typedGraph.TypedNode;

public class PatternNode extends PatternElement<TypeNode> implements INode {
	public String toString() {
		return this.getName()+(isCollection() ? "[]" : "")+":"+this.getType().getName();
	}

	@Override
	public boolean isMatchOf(Context match, TypedGraph graph) {
		try {
			if(this.isCollection()) {
				List<Index> values = match.getValue(getName());
				return getType().isInstance(values.stream().map(v->{
					try {
						return graph.getElementByIndexObject(v);
					} catch (Exception e) {
						return TypedNode.NULL;
					}
				}));
			} else {
				Index index = match.getValue(getName());
				TypedNode e = graph.getElementByIndexObject(index);
				return getType().isInstance(e);
			}
		} catch(Exception e) {
			return false;
		}
	}
}
