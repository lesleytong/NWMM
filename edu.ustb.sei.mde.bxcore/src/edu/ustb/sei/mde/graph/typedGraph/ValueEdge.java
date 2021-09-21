package edu.ustb.sei.mde.graph.typedGraph;

import edu.ustb.sei.mde.graph.type.PropertyEdge;

public class ValueEdge extends IndexableElement  implements ITypedEdge {
	
	private TypedNode source;
	private ValueNode target;
	private PropertyEdge type;	// ValueEdge��type��PropertyEdge
		
	public ValueEdge() {
		
	}
	
	public ValueEdge(TypedNode source, ValueNode target, PropertyEdge type) {
		super();
		this.source = source;
		this.target = target;
		this.type = type;
	}
	
	public TypedNode getSource() {
		return source;
	}

	// lyt-��ʱ�ĳ�public 
	public void setSource(TypedNode source) {
		this.source = source;
	}

	public ValueNode getTarget() {
		return target;
	}

	// lyt-��ʱ�ĳ�public 
	public void setTarget(ValueNode target) {
		this.target = target;
	}


	public PropertyEdge getType() {
		return type;
	}

	// lyt ��ʱ�ĳ�public 
	public void setType(PropertyEdge type) {
		this.type = type;
	}
	
	public String toString() {
		return source.toString()+"."+type.getName()+"="+target.toString();
	}

}
