package edu.ustb.sei.mde.bxcore.structures;

import java.util.Arrays;

import edu.ustb.sei.mde.graph.IEdge;
import edu.ustb.sei.mde.graph.INode;
import edu.ustb.sei.mde.graph.type.IPathType;
import edu.ustb.sei.mde.graph.typedGraph.IndexableElement;

public class GraphPath implements IEdge {
	public GraphPath(IEdge[] path, IPathType type) {
		super();
		this.path = path;
		this.type = type;
	}

	protected IEdge[] path; 
	protected IPathType type;
	
	public IPathType getType() {
		return type;
	}

	public INode getSource() {
		return path[0].getSource();
	}
	
	public INode getTarget() {
		return path[path.length-1].getTarget();
	}
	
	public IEdge[] getPathEdges() {
		return path;
	}
	
	public IndexPath toIndexPath() {
		Index[] indices = new Index[path.length];
		for(int i=0;i<path.length;i++)
			indices[i] = ((IndexableElement)path[i]).getIndex();
		return new IndexPath(indices, type, this);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null) return false;
		if(!(obj instanceof GraphPath)) return false;
		if(this.type!=((GraphPath)obj).type) return false;
		return Arrays.equals(path, ((GraphPath)obj).path);
	}
}
