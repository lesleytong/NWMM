package edu.ustb.sei.mde.graph.typedGraph;

import edu.ustb.sei.mde.graph.INode;
import edu.ustb.sei.mde.graph.type.ITypeNode;

public interface ITypedNode extends INode {
	ITypeNode getType();
}
