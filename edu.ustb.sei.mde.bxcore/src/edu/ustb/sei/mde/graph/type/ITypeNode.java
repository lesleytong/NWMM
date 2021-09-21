package edu.ustb.sei.mde.graph.type;

import edu.ustb.sei.mde.graph.INode;

public interface ITypeNode extends INode, ITypeGraphItem {
	default boolean isSuperTypeOf(ITypeNode node) {
		if(this.getTypeGraph()!=node.getTypeGraph()) 
			return false;
		return this.getTypeGraph().isSuperTypeOf(node, this);
	}
}
