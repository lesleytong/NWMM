package edu.ustb.sei.mde.graph.typedGraph;

import java.util.HashMap;
import java.util.Map;

import edu.ustb.sei.mde.graph.AbstractGraphvizExporter;
import edu.ustb.sei.mde.graph.IEdge;
import edu.ustb.sei.mde.graph.INode;

public class GraphvizExporterForTypedGraph extends AbstractGraphvizExporter {
	
	private Map<INode, Integer> nodeMap = new HashMap<>();
	
	
	@Override
	public String getNodeID(INode node) {
		Integer id = nodeMap.get(node);
		if(id==null) {
			id = nodeMap.size();
			nodeMap.put(node, id);
		}
		return "ID_"+id;
	}

	@Override
	public String getNodeShapeAndStyle(INode node) {
		if(node instanceof TypedNode) {
			return "shape=ellipse, width=.75, height=.5"; // default shape
		} else return "shape=box, color=red";
	}

	@Override
	public String getNodeLabel(INode node) {
		if(node instanceof TypedNode) {
			return "label=\""+getNodeID(node)+":"+((TypedNode)node).getType().getName()+"\"";
		} else {
			return "label=\""+((ValueNode)node).getValue().toString()+":"+((ValueNode)node).getType().getName()+"\"";
		}
	}

	@Override
	public String getEdgeShapeAndStyle(IEdge edge) {
		if(edge instanceof TypedEdge) {
			return "style=solid"; // default shape
		} else return "style=dashed,color=red";
	}

	@Override
	public String getEdgeLabel(IEdge edge) {
		if(edge instanceof TypedEdge) {
			return "label=\":"+((TypedEdge)edge).getType().getName()+"\""; // default shape
		} else return "label=\":"+((ValueEdge)edge).getType().getName()+"\"";
	}

}
