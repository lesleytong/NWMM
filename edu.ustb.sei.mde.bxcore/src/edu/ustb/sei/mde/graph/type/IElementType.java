package edu.ustb.sei.mde.graph.type;

public interface IElementType extends IType {
	default IType getElementType() {
		return this;
	}
}
