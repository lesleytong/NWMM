package edu.ustb.sei.mde.graph.typedGraph;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.ustb.sei.mde.bxcore.structures.Index;
import edu.ustb.sei.mde.graph.GraphCreator;
import edu.ustb.sei.mde.graph.IEdge;
import edu.ustb.sei.mde.graph.INode;
import edu.ustb.sei.mde.graph.type.DataTypeNode;
import edu.ustb.sei.mde.graph.type.PropertyEdge;
import edu.ustb.sei.mde.graph.type.TypeEdge;
import edu.ustb.sei.mde.graph.type.TypeNode;

public class TypedGraphCreator extends GraphCreator {
	
	public TypedGraph graph;
	
	public TypedGraphCreator(TypedGraph g) {
		graph = g;
	}

	private static final String VALUE_EDGE = ValueEdge.class.getSimpleName();
	private static final String TYPED_EDGE = TypedEdge.class.getSimpleName();
	private static final String VALUE_NODE = ValueNode.class.getSimpleName();
	private static final String TYPED_NODE = TypedNode.class.getSimpleName();

	@Override
	protected INode createNode(String type, Object... argv) {
		INode node = null;
		
		if(TYPED_NODE.equals(type)) {
			TypedNode n = new TypedNode();
			n.setType((TypeNode)argv[0]);
			node = n;
		} else if(VALUE_NODE.equals(type)) {
			ValueNode n = ValueNode.createConstantNode(argv[0], (DataTypeNode)argv[1]);
			node = n;
		}
		
		return node;
	}
	
	public TypedNode createTypedNode(String nodeID, TypeNode type) {
		return createTypedNode(nodeID, type,null);
	}
	
	public TypedNode createTypedNode(String nodeID, TypeNode type, Index index) {
		TypedNode n = (TypedNode) this.createNode(nodeID, TYPED_NODE, type);
		if(index!=null) n.appendIndex(index);
		graph.addTypedNode(n);
		return n;
	}
	
	public ValueNode createValueNode(String nodeID, Object value, DataTypeNode type) {
		ValueNode n = (ValueNode) this.createNode(nodeID, VALUE_NODE, value, type);
		graph.addValueNode(n);
		return n;
	}

	@Override
	protected IEdge createEdge(INode src, INode tar, String type, Object... argv) {
		IEdge edge = null;
		
		if(TYPED_EDGE.equals(type)) {
			TypedEdge e = new TypedEdge();
			e.setSource((TypedNode) src);
			e.setTarget((TypedNode) tar);
			e.setType((TypeEdge) argv[0]);
			edge = e;
		} else if(VALUE_EDGE.equals(type)) {
			ValueEdge e = new ValueEdge();
			e.setSource((TypedNode) src);
			e.setTarget( (ValueNode) tar);
			e.setType((PropertyEdge) argv[0]);
			edge = e;
		}
		
		return edge;
	}
	
	public TypedEdge createTypedEdge(String srcId, String tarId, TypeEdge edgeType) {
		return createTypedEdge(srcId, tarId, edgeType,null);
	}
	
	public TypedEdge createTypedEdge(String srcId, String tarId, TypeEdge edgeType, Index index) {
		TypedEdge e = (TypedEdge) this.createEdge(srcId, tarId, TYPED_EDGE, edgeType);
		if(index!=null) 
			e.appendIndex(index);
		graph.addTypedEdge(e);
		return e;
	}
	
	public ValueEdge createValueEdge(String srcId, String tarId, PropertyEdge edgeType) {
		return createValueEdge(srcId, tarId, edgeType, null);
	}
	
	public ValueEdge createValueEdge(String srcId, String tarId, PropertyEdge edgeType, Index index) {
		ValueEdge e = (ValueEdge) this.createEdge(srcId, tarId, VALUE_EDGE, edgeType);
		if(index!=null) 
			e.appendIndex(index);
		graph.addValueEdge(e);
		return e;
	}

	
	public void declare(String graphString) {
		// 1. split by ';'
		// 2. match by node/edge formats
		
		String[] statements = graphString.split(";");
		String nodeDeclPattern = "\\s*(\\w+)\\s*:\\s*(\\w+)\\s*";
		String propDeclPattern = "\\s*(\\w+)\\s*\\.\\s*(\\w+)\\s*=\\s*(\\d+|true|false|\".*\")\\s*";
		String edgeDeclPattern = "\\s*(\\w+)\\s*\\-\\s*(\\w+)\\s*->\\s*(\\w+)\\s*";
		
		Pattern ndp = Pattern.compile(nodeDeclPattern);
		Pattern pdp = Pattern.compile(propDeclPattern);
		Pattern edp = Pattern.compile(edgeDeclPattern);
		
		Matcher matcher = null;
		
		for(String stat : statements) {
			if((matcher=ndp.matcher(stat)).matches()) {
				String nodeName = matcher.group(1);
				String typeName = matcher.group(2);
				
				TypeNode type = this.graph.getTypeGraph().getTypeNode(typeName);
				this.createTypedNode(nodeName, type);
			} else if((matcher=pdp.matcher(stat)).matches()) {
				String sourceName = matcher.group(1);
				String featureName = matcher.group(2);
				String targetValue = matcher.group(3);
				
				TypedNode sn = (TypedNode) this.getNode(sourceName);
				PropertyEdge feature = this.graph.getTypeGraph().getPropertyEdge(sn.getType(), featureName);
				Object value = null;
				
				if(targetValue.equals("true")||targetValue.equals("false")) {
					value = Boolean.parseBoolean(targetValue);
				} else if(targetValue.startsWith("\"")) {
					value = targetValue.substring(1, targetValue.length()-1);
				} else 
					value = Integer.parseInt(targetValue);
				
				this.createValueNode(targetValue, value, feature.getTarget());
				this.createValueEdge(sourceName, targetValue, feature);
				
			} else if((matcher=edp.matcher(stat)).matches()) {
				String sourceName = matcher.group(1);
				String featureName = matcher.group(2);
				String targetName = matcher.group(3);
				
				TypedNode sn = (TypedNode) this.getNode(sourceName);
				TypeEdge feature = this.graph.getTypeGraph().getTypeEdge(sn.getType(), featureName);
				
				this.createTypedEdge(sourceName, targetName, feature);
			}
		}
	}
	
}
