package edu.ustb.sei.mde.graph.type;

import edu.ustb.sei.mde.graph.INamedElement;

public interface IType extends INamedElement {
	IType getElementType();
	Class<?> getJavaType();
	boolean isInstance(Object value);
}
