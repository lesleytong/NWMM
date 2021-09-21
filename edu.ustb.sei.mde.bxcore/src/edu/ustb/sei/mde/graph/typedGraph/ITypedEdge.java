package edu.ustb.sei.mde.graph.typedGraph;

import edu.ustb.sei.mde.graph.IEdge;
import edu.ustb.sei.mde.graph.type.IStructuralFeatureEdge;

public interface ITypedEdge extends IEdge {
	
	IStructuralFeatureEdge getType();
}
