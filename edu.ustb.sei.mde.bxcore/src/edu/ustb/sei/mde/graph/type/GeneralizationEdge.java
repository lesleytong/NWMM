package edu.ustb.sei.mde.graph.type;

import edu.ustb.sei.mde.graph.IEdge;
import edu.ustb.sei.mde.graph.Nullable;

public class GeneralizationEdge implements IEdge {
	@Nullable(false) private TypeNode source; // child
	@Nullable(false) private TypeNode target; // parent
	
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
	
	public String toString() {
		return source.getName()+"=>"+target.getName();
	}

}
