package edu.ustb.sei.mde.graph.type;

import edu.ustb.sei.mde.graph.IEdge;

public interface IStructuralFeatureEdge extends IEdge, ITypeGraphItem {
	/**
	 * Note it does not indicate whether or not the value of this type is a collection. 
	 * @return the multiplicity of this feature
	 */
	boolean isMany();
	boolean isUnique();
}
