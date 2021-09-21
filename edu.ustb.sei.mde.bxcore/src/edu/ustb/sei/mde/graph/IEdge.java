package edu.ustb.sei.mde.graph;

/**
 * The interface of edges.
 * An edge references to a source node and a target node.
 * @author hexiao
 *
 */
public interface IEdge {
	INode getSource();
	INode getTarget();
}
