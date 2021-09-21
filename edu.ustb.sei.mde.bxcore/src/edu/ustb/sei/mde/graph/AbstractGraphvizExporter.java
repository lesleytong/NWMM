package edu.ustb.sei.mde.graph;

public abstract class AbstractGraphvizExporter {
	abstract public String getNodeID(INode node);
	abstract public String getNodeShapeAndStyle(INode node);
	abstract public String getNodeLabel(INode node);
	abstract public String getEdgeShapeAndStyle(IEdge node);
	abstract public String getEdgeLabel(IEdge node);
	
	public String export(IGraph graph, String name) {
		StringBuilder builder = new StringBuilder();
		
		builder.append("digraph ");
		builder.append("name ");
		builder.append("{\n");
		
		for(INode node : graph.getNodes()) {
			builder.append("\t");
			builder.append(getNodeID(node));
			builder.append(" [");
			builder.append(getNodeShapeAndStyle(node));
			builder.append(",");
			builder.append(getNodeLabel(node));
			builder.append("];\n");
		}
		
		for(IEdge edge : graph.getEdges()) {
			builder.append("\t");
			builder.append(getNodeID(edge.getSource()));
			builder.append("->");
			builder.append(getNodeID(edge.getTarget()));
			builder.append(" [");
			builder.append(getEdgeShapeAndStyle(edge));
			builder.append(",");
			builder.append(getEdgeLabel(edge));
			builder.append("];\n");
		}
		
		builder.append("}\n");
		
		return builder.toString();
	}
}
