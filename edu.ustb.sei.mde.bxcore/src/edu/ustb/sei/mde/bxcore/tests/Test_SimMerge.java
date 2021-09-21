package edu.ustb.sei.mde.bxcore.tests;

import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.graph.type.TypeGraph;
import edu.ustb.sei.mde.graph.typedGraph.SimMerge;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;
import edu.ustb.sei.mde.graph.typedGraph.TypedNode;
import edu.ustb.sei.mde.graph.typedGraph.ValueEdge;
import edu.ustb.sei.mde.graph.typedGraph.ValueNode;

public class Test_SimMerge {

	static TypedGraph baseGraph = null;
	static TypedGraph aGraph = null;
	static TypedGraph bGraph = null;
	static TypedGraph resultGraph = null;
	
	public static void main(String[] args) throws NothingReturnedException {
		build_baseGraph();
		build_aGraph();
		build_bGraph();
		
//		resultGraph = SimMerge.nWayMerge(baseGraph, aGraph);
		resultGraph = SimMerge.nWayMerge(baseGraph, bGraph);
		
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
				"a1:AddressBook;"
				+"p1:Person;"
				+"p2:Person;"
				+"c1:College;"
				+"a1-persons->p1;"	
				+"a1-persons->p2;"
				+"p1-college->c1;"
				+"a1.name=\"ClassOne\";"
				+"p1.name=\"Lesley\";"
				+"p1.tel=\"123\";"
				+"p2.name=\"Mars\";"
				+"p2.tel=\"111\";"
				+"c1.name=\"USTB\";"
				);	
		
		System.out.println("baseGraph: ");
		print(baseGraph);	
		
	}
	
	private static void build_aGraph() {
		
		aGraph = baseGraph.getCopy();	// 浅复制，直接set会影响基础图
		
		/** 修改ValueNode中的“Lesley”为“Leslie”*/
		// 新加ValueNode"Leslie"，这里没有删除Lesley。
		ValueNode vn1 = aGraph.getAllValueNodes().get(0); // new ValueNode()是private修饰符
		ValueNode vn4 = vn1.createConstantNode("Leslie", vn1.getType());
		aGraph.addValueNode(vn4);
		// 修改ve1的target为"Leslie"
		ValueEdge ve1 = aGraph.getAllValueEdges().get(1); 
		ValueEdge ve = new ValueEdge();
		ve.setType(ve1.getType());
		ve.setSource(ve1.getSource());
		ve.setTarget(aGraph.getAllValueNodes().get(6));
		aGraph.replaceWith(ve1, ve);

		// 删除p2（Person）		
		// 这里没有删除Mars和111，但是对于emf来说好像留着无用
		TypedNode p2 = aGraph.getAllTypedNodes().get(2);
		aGraph.remove(p2);

		// 新加AddressBook，叫“ClassTwo”
		aGraph.declare("a1:AddressBook;"
				       +"a1.name=\"ClassTwo\";");
		
		System.out.println("aGraph: ");
		print(aGraph);
		
	}
	
	private static void build_bGraph() {
		
		bGraph = baseGraph.getCopy();	// 浅复制，直接set会影响基础图
		
		/** p1.tel和p2.tel互换 */
		// 修改p1.tel的target为"111"
		ValueEdge ve1 = bGraph.getAllValueEdges().get(2); 
		ValueEdge ve1_new = new ValueEdge();
		ve1_new.setType(ve1.getType());
		ve1_new.setSource(ve1.getSource());
		ve1_new.setTarget(bGraph.getAllValueNodes().get(4));
		bGraph.replaceWith(ve1, ve1_new);
		
		// 修改p2.tel的target为"123"
		ValueEdge ve2 = bGraph.getAllValueEdges().get(4); 
		ValueEdge ve2_new = new ValueEdge();
		ve2_new.setType(ve2.getType());
		ve2_new.setSource(ve2.getSource());
		ve2_new.setTarget(bGraph.getAllValueNodes().get(2));
		bGraph.replaceWith(ve2, ve2_new);		

		// 新加AddressBook，叫“ClassTwo”
		bGraph.declare("a1:AddressBook;"
				       +"a1.name=\"ClassTwo\";");
		
		System.out.println("bGraph: ");
		print(bGraph);
		
		// 交换一下baseGraph中Person节点的序，测试是不是结构多的先去匹配
		TypedNode typedNode = baseGraph.getAllTypedNodes().get(2);
		baseGraph.getAllTypedNodes().remove(typedNode);
		baseGraph.getAllTypedNodes().add(1, typedNode);
				
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
