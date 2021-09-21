package edu.ustb.sei.mde.graph.type;

import edu.ustb.sei.mde.graph.GraphCreator;
import edu.ustb.sei.mde.graph.IEdge;
import edu.ustb.sei.mde.graph.INode;

public class TypeGraphCreator extends GraphCreator {
	
	public TypeGraph graph;
	
	public TypeGraphCreator(TypeGraph g) {
		graph = g;
	}

	private static final String PROPERTY_EDGE = PropertyEdge.class.getSimpleName();
	private static final String TYPE_EDGE = TypeEdge.class.getSimpleName();
	private static final String GENERALIZATION_EDGE = GeneralizationEdge.class.getSimpleName();
	private static final String DATA_TYPE_NODE = DataTypeNode.class.getSimpleName();
	private static final String TYPE_NODE = TypeNode.class.getSimpleName();

	@Override
	protected INode createNode(String type, Object... argv) {
		INode node = null;
		if(TYPE_NODE.equals(type)) {
			TypeNode n = new TypeNode();
			n.setName((String)argv[0]);
			n.setAbstract((Boolean)argv[1]);
			node = n;
		} else if(DATA_TYPE_NODE.equals(type)) {
			DataTypeNode n = new DataTypeNode();
			n.setDataType((String)argv[0], (Class<?>)argv[1]);
			node = n;
		}
		return node;
	}
	
	public TypeNode createTypeNode(String nodeVar, String typeName, Boolean isAbstract) {
		TypeNode n = (TypeNode) this.createNode(nodeVar, TYPE_NODE, typeName, isAbstract);
		graph.addNode(n);
		return n;
	}
	
	public DataTypeNode createDataTypeNode(String nodeVar, String typeName, Class<?> javaType) {
		DataTypeNode n = (DataTypeNode) this.createNode(nodeVar, DATA_TYPE_NODE, typeName, javaType);
		graph.addNode(n);
		return n;
	}

	@Override
	protected IEdge createEdge(INode src, INode tar, String type, Object... argv) {
		IEdge edge = null;
		if(GENERALIZATION_EDGE.equals(type)) {
			GeneralizationEdge e = new GeneralizationEdge();
			e.setSource((TypeNode)src);
			e.setTarget((TypeNode)tar);
			edge = e;
		} else if(TYPE_EDGE.equals(type)) {
			TypeEdge e = new TypeEdge();
			e.setSource((TypeNode)src);
			e.setTarget((TypeNode)tar);
			e.setName((String)argv[0]);
			e.setMany((Boolean)argv[1]);
			e.setUnique((Boolean)argv[2]);
			edge = e;
		} else if(PROPERTY_EDGE.equals(type)) {
			PropertyEdge e = new PropertyEdge();
			e.setSource((TypeNode)src);
			e.setTarget((DataTypeNode)tar);
			e.setName((String)argv[0]);
			e.setMany((Boolean)argv[1]);
			e.setUnique((Boolean)argv[2]);
			edge = e;
		}
		return edge;
	}
	
	public GeneralizationEdge createGeneralizationEdge(String child, String parent) {
		GeneralizationEdge e = (GeneralizationEdge) this.createEdge(child, parent, GENERALIZATION_EDGE);
		graph.addEdge(e);
		return e;
	}
	
	public TypeEdge createTypeEdge(String src,String tar,String edgeName, Boolean many, Boolean unqiue) {
		TypeEdge e = (TypeEdge) this.createEdge(src, tar, TYPE_EDGE, edgeName, many, unqiue);
		graph.addEdge(e);
		return e;
	}
	
	public TypeEdge createTypeEdge(String src,String tar,String edgeName, Boolean many) {
		return createTypeEdge(src,tar,edgeName,many, true);
	}
	
	public PropertyEdge createPropertyEdge(String src,String tar,String edgeName, Boolean many, Boolean unqiue) {
		PropertyEdge e = (PropertyEdge) this.createEdge(src, tar, PROPERTY_EDGE, edgeName, many, unqiue);
		graph.addEdge(e);
		return e;
	}
	
	public PropertyEdge createPropertyEdge(String src,String tar,String edgeName, Boolean many) {
		return createPropertyEdge(src,tar,edgeName,many, true);
	}

}
