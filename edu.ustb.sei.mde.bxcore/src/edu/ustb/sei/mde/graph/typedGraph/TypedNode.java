package edu.ustb.sei.mde.graph.typedGraph;

import edu.ustb.sei.mde.graph.type.TypeNode;

public class TypedNode  extends IndexableElement implements ITypedNode {
	
	private TypeNode type;	// TypedNode�����Ϳ�TypeNode����ƥ��ڵ㿴����ʱ��ֱ���õ���==��Ӧ�ú�ǳ�����йأ���ͬһ����
	
	static final public TypedNode NULL = new TypedNode(TypeNode.NULL_TYPE);
			
	public TypedNode() {
	}
	
	public TypedNode(TypeNode type) {
		this.type = type;
	}
		
	public TypeNode getType() {
		return type;
	}

	// lyt-��ʱ�ĳ�public 
	public void setType(TypeNode type) {
		this.type = type;
	}
	
	public String toString() {
		return Integer.toHexString(hashCode())+":"+type.getName();
	}
		
}
