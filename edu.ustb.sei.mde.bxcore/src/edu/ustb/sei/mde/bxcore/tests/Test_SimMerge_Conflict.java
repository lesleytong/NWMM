package edu.ustb.sei.mde.bxcore.tests;

import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.graph.type.TypeGraph;
import edu.ustb.sei.mde.graph.typedGraph.SimMerge;
import edu.ustb.sei.mde.graph.typedGraph.TypedEdge;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;
import edu.ustb.sei.mde.graph.typedGraph.TypedNode;
import edu.ustb.sei.mde.graph.typedGraph.ValueEdge;
import edu.ustb.sei.mde.graph.typedGraph.ValueNode;

public class Test_SimMerge_Conflict {

	static TypedGraph baseGraph = null;
	static TypedGraph aGraph = null;
	static TypedGraph bGraph = null;
	static TypedGraph cGraph = null;
	static TypedGraph dGraph = null;
	static TypedGraph eGraph = null;
	static TypedGraph resultGraph = null;
	
	public static void main(String[] args) throws NothingReturnedException {
		build_baseGraph();
		build_aGraph();
		build_bGraph();
		build_cGraph();
		build_dGraph();
		build_eGraph();
		
		resultGraph = SimMerge.nWayMerge(baseGraph, aGraph, eGraph, bGraph);
//		resultGraph = SimMerge.nWayMerge(baseGraph, aGraph, cGraph, dGraph);
//		resultGraph = SimMerge.nWayMerge(baseGraph, aGraph, cGraph, eGraph);
//		resultGraph = SimMerge.nWayMerge(baseGraph, eGraph, dGraph);
		
		print(resultGraph);
			
	}
	
	private static void build_baseGraph() {
		
		TypeGraph typeGraph = new TypeGraph();
    	// TypeNode
    	typeGraph.declare("AddressBook");
    	typeGraph.declare("Person");
    	typeGraph.declare("College");
    	// DataTypeNode
    	typeGraph.declare("EString:java.lang.String");	// EString映射为String
    	// TypeEdge
    	typeGraph.declare("  @persons:AddressBook->Person*");
    	typeGraph.declare("  @college:Person->College");
    	// PropertyEdge
    	typeGraph.declare("name:AddressBook->EString");	
    	typeGraph.declare("name:Person->EString");
    	typeGraph.declare("tel:Person->EString");
    	typeGraph.declare("name:College->EString");
		
		baseGraph = new TypedGraph(typeGraph);
		baseGraph.declare(	
				"p1:Person;"
				+"p1.name=\"Lesley\";"
				+"p1.tel=\"123\";"
				);	
		
		System.out.println("baseGraph: ");
		print(baseGraph);	
		
	}
	
	private static void build_aGraph() {
		
		aGraph = baseGraph.getCopy();	// 浅复制，直接set会影响基础图
		
		// 修改Lesley为Leslie
		// 新加ValueNode"Leslie"，这里没有删除Lesley。
		ValueNode vn1 = aGraph.getAllValueNodes().get(0); // new ValueNode()是private修饰符
		ValueNode vn2 = vn1.createConstantNode("Leslie", vn1.getType());
		aGraph.addValueNode(vn2);
		// 修改ve1的target为"Leslie"
		ValueEdge ve1 = aGraph.getAllValueEdges().get(0); 
		ValueEdge ve = new ValueEdge();
		ve.setType(ve1.getType());
		ve.setSource(ve1.getSource());
		ve.setTarget(aGraph.getAllValueNodes().get(2));
		aGraph.replaceWith(ve1, ve);
		
		System.out.println("aGraph: ");
		print(aGraph);
		
	}
	
	private static void build_bGraph() {
		
		bGraph = baseGraph.getCopy();	// 浅复制，直接set会影响基础图
		
		// 修改Lesley为Lee
		// 新加ValueNode"Lee"，这里没有删除Lesley。
		ValueNode vn1 = bGraph.getAllValueNodes().get(0); // new ValueNode()是private修饰符
		ValueNode vn2 = vn1.createConstantNode("Lee", vn1.getType());
		bGraph.addValueNode(vn2);
		// 修改ve1的target为"Lee"
		ValueEdge ve1 = bGraph.getAllValueEdges().get(0); 
		ValueEdge ve = new ValueEdge();
		ve.setType(ve1.getType());
		ve.setSource(ve1.getSource());
		ve.setTarget(bGraph.getAllValueNodes().get(2));
		bGraph.replaceWith(ve1, ve);
		
		System.out.println("bGraph: ");
		print(bGraph);

	}
	
	private static void build_cGraph() {
		
		cGraph = baseGraph.getCopy();	// 浅复制，直接set会影响基础图
		
		// 修改Lesley为Leslie
		// 新加ValueNode"Leslie"，这里没有删除Lesley。
		ValueNode vn1 = cGraph.getAllValueNodes().get(0); // new ValueNode()是private修饰符
		ValueNode vn2 = vn1.createConstantNode("Leslie", vn1.getType());
		cGraph.addValueNode(vn2);
		// 修改ve1的target为"Leslie"
		ValueEdge ve1 = cGraph.getAllValueEdges().get(0); 
		ValueEdge ve = new ValueEdge();
		ve.setType(ve1.getType());
		ve.setSource(ve1.getSource());
		ve.setTarget(cGraph.getAllValueNodes().get(2));
		cGraph.replaceWith(ve1, ve);
		
		System.out.println("cGraph: ");
		print(cGraph);
		
	}
	
	private static void build_dGraph() {
		
		dGraph = baseGraph.getCopy();	// 浅复制，直接set会影响基础图
		
		// 删除.name属性边
		ValueEdge deleteEdge = dGraph.getAllValueEdges().get(0);
		dGraph.remove(deleteEdge);
		
		System.out.println("dGraph: ");
		print(dGraph);
		
	}
	
	private static void build_eGraph() {
		
		eGraph = baseGraph.getCopy();	// 浅复制，直接set会影响基础图
		
		System.out.println("eGraph: ");
		print(eGraph);
		
	}
	
	
	private static void print(TypedGraph typedGraph) {
		
		System.out.println("TypedNodes: " + typedGraph.getAllTypedNodes());
		System.out.println("TypedEdges: " + typedGraph.getAllTypedEdges());
		System.out.println("ValueNodes: " + typedGraph.getAllValueNodes());
		System.out.println("ValueEdges: " + typedGraph.getAllValueEdges());
		System.out.println("*********************************************************************");
		System.out.println();
		
//		System.out.println("TypeNodes: " + typedGraph.getTypeGraph().getAllTypeNodes());
//		System.out.println("TypeEdges: " + typedGraph.getTypeGraph().getAllTypeEdges());
//		System.out.println("DataTypeNodes: " + typedGraph.getTypeGraph().getAllDataTypeNodes());
//		System.out.println("PropertyEdges: " + typedGraph.getTypeGraph().getAllPropertyEdges());
//		System.out.println("*********************************************************************");
//		System.out.println();
		
	}
	
}
