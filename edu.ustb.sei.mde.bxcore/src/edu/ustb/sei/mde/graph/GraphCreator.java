package edu.ustb.sei.mde.graph;

import java.util.HashMap;
import java.util.Map;

abstract public class GraphCreator {
	private Map<String, INode> nodeMap = new HashMap<String,INode>();
	
	abstract protected INode createNode(String type, Object... argv);
	abstract protected IEdge createEdge(INode src, INode tar, String type, Object... argv);
	
	public INode createNode(String name, String type, Object... argv) {
		INode node = createNode(type, argv);
		if(name!=null)
			nodeMap.put(name, node);
		return node;
	}
	
	public IEdge createEdge(String srcName, String tarName, String type, Object... argv) {
		INode src = nodeMap.get(srcName);
		INode tar = nodeMap.get(tarName);
		IEdge edge = createEdge(src,tar,type,argv);
		return edge;
	}
	
	public void registerNode(String name, INode node) {
		nodeMap.put(name, node);
	}
	
	public INode getNode(String name) {
		return this.nodeMap.get(name);
	}
}
