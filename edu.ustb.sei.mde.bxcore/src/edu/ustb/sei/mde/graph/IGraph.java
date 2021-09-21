package edu.ustb.sei.mde.graph;

import java.util.List;

/**
 * The interface of graphs. 
 * A graph consists of a list of nodes and a list of edges.
 * @author hexiao
 *
 */
public interface IGraph {
	List<INode> getNodes();
	List<IEdge> getEdges();
	
	default boolean integrityCheck() {
		List<INode> nodes = getNodes();
		List<IEdge> edges = getEdges();
		
		for(IEdge e : edges) {
			if(nodes.contains(e.getSource()) && nodes.contains(e.getTarget()))
				continue;
			return false;
		}
		
		return true;
	}
}
