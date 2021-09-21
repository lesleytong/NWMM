package edu.ustb.sei.mde.graph.type;

import edu.ustb.sei.mde.graph.Nullable;
import edu.ustb.sei.mde.graph.typedGraph.TypedEdge;

public class TypeEdge implements IStructuralFeatureEdge {
	@Nullable(false)
	private TypeNode source;
	@Nullable(false)
	private TypeNode target;
	@Nullable(false)
	private String name;

	private boolean isMany;
	private boolean isUnique;
	private boolean isContainment;
	
	private TypeGraph typeGraph;
	
	public TypeNode getSource() {
		return source;
	}
	void setSource(TypeNode source) {
		this.source = source;
	}
	
	public TypeNode getTarget() {
		return target;
	}
	void setTarget(TypeNode target) {
		this.target = target;
	}
	
	public String getName() {
		return name;
	}
	void setName(String name) {
		this.name = name;
	}
	
	public boolean isMany() {
		return isMany;
	}
	void setMany(boolean isMany) {
		this.isMany = isMany;
	}
	
	public boolean isUnique() {
		return isMany==false || isUnique;
	}
	void setUnique(boolean isUnique) {
		this.isUnique = isUnique;
	}
	
	public boolean isContainment() {
		return isContainment;
	}
	public void setContainment(boolean isContainment) {
		this.isContainment = isContainment;
	}
	

	
	public TypeGraph getTypeGraph() {
		return typeGraph;
	}

	public void setTypeGraph(TypeGraph typeGraph) {
		this.typeGraph = typeGraph;
	}

	public String toString() {
		return source.getName()+"-"+(isMany?"*":"-")+"->"+target.getName();
	}

	@Override
	public Class<?> getJavaType() {
		return TypedEdge.class;
	}
	
	@Override
	public boolean isInstance(Object value) {
		if(value instanceof TypedEdge) {
			return ((TypedEdge) value).getType()==this;
		} return false;
	}
}
