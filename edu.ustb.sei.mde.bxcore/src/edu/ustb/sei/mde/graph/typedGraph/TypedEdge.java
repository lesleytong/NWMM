package edu.ustb.sei.mde.graph.typedGraph;

import edu.ustb.sei.mde.graph.type.TypeEdge;

public class TypedEdge extends IndexableElement implements ITypedEdge {
	
	private TypedNode source;
	private TypedNode target;
	private TypeEdge type;	//TypedEdge�����Ϳ�TypeEdge
	
	public TypedEdge() {
		
	}
	
	public TypedEdge(TypedNode source, TypedNode target, TypeEdge type) {
		super();
		this.source = source;
		this.target = target;
		this.type = type;
	}

	public TypedNode getSource() {
		return source;
	}
	
	// lyt - Ϊ����TestTwoOrder��ʹ�ã��޸ĳ���public
	public void setSource(TypedNode source) {
		this.source = source;
	}
	public TypedNode getTarget() {
		return target;
	}
	
	// lyt - Ϊ����TestTwoOrder��ʹ�ã��޸ĳ���public
	public void setTarget(TypedNode target) {
		this.target = target;
	}
	
	public TypeEdge getType() {
		return type;
	}
	
	// lyt - Ϊ����TestTwoOrder��ʹ�ã��޸ĳ���public
	public void setType(TypeEdge type) {
		this.type = type;
	}
	
	public String toString() {
		return source.toString()+"-"+type.getName()+"->"+target.toString();
	}
}
