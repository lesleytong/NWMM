package edu.ustb.sei.mde.bxcore.structures;

import java.util.Arrays;

import edu.ustb.sei.mde.graph.IEdge;
import edu.ustb.sei.mde.graph.type.IPathType;
import edu.ustb.sei.mde.graph.typedGraph.IndexableElement;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;

public class IndexPath {
	protected Index[] indices;
	protected IPathType type;
	protected GraphPath originalPath;
	
	public IPathType getType() {
		return type;
	}

	public IndexPath(Index[] indices, IPathType type) {
		this.indices = indices;
		this.type = type;
		this.originalPath = null;
	}
	
	public IndexPath(Index[] indices, IPathType type, GraphPath originalPath) {
		this.indices = indices;
		this.type = type;
		this.originalPath = originalPath;
	}
	
	public Index[] getPathIndices() {
		return indices;
	}
	
	public GraphPath toGraphPath(TypedGraph graph) {
		try { 
			IEdge[] edges = new IEdge[indices.length];
			for(int i=0;i<indices.length;i++)
				edges[i] = (IEdge) graph.getElementByIndexObject(indices[i]);
			GraphPath gp = new GraphPath(edges, type);
			originalPath = gp;
			return gp;
		} catch (Exception e) {
			return null;
		}
	}
	
	public GraphPath toGraphPathWithRecovery(TypedGraph graph) {
		try { 
			IEdge[] edges = new IEdge[indices.length];
			for(int i=0;i<indices.length;i++)
				edges[i] = (IEdge) graph.getElementByIndexObject(indices[i]);
			GraphPath gp = new GraphPath(edges, type);
			return gp;
		} catch (Exception e) {
			if(originalPath!=null) {
//				IEdge[] edges = new IEdge[indices.length];
//				for(int i=0;i<originalPath.getPathEdges().length;i++) {
//					IndexableElement edge = (IndexableElement) originalPath.getPathEdges()[i];
//					try {
//						edges[i] = (IEdge) graph.getElementByIndexObject(edge.getIndex());
//					} catch (Exception e1) {
//						edges[i] = (IEdge) edge;
//					}
//				}
//				GraphPath gp = new GraphPath(edges, type);
//				return gp;
				return originalPath;
			}
			return null;
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null) return false;
		if(!(obj instanceof IndexPath)) return false;
		if(this.type!=((IndexPath)obj).type) return false;
		return Arrays.equals(indices, ((IndexPath)obj).indices);
	}
}
