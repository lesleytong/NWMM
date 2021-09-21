package edu.ustb.sei.mde.bxcore.tests;

import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.graph.type.TypeGraph;
import edu.ustb.sei.mde.graph.typedGraph.BXMerge3;
import edu.ustb.sei.mde.graph.typedGraph.TypedEdge;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;
import edu.ustb.sei.mde.graph.typedGraph.ValueEdge;
import edu.ustb.sei.mde.graph.typedGraph.ValueNode;

public class Test_1 {

	static TypedGraph baseGraph = null;
	static TypedGraph aGraph = null;
	static TypedGraph bGraph = null;
	static TypedGraph resultGraph = null;
	
	public static void main(String[] args){
		
		build_baseGraph();
		build_aGraph();
		build_bGraph();
		
		try {
			resultGraph = BXMerge3.mergeOrigin(baseGraph, aGraph, bGraph);
			System.out.println("resultGraph: ");
			print(resultGraph);
									
		} catch (NothingReturnedException e) {
			e.printStackTrace();
		}
				
		
	}
	

	private static void build_baseGraph() {
		
		TypeGraph typeGraph = new TypeGraph();
		// add type nodes
		typeGraph.declare("A");
		typeGraph.declare("B");
		typeGraph.declare("C");
		typeGraph.declare("D");
		// add data type nodes
		typeGraph.declare("String:java.lang.String");
		// add type edges
		typeGraph.declare("a2b:A->B*");
		typeGraph.declare("b2c:B->C");
		typeGraph.declare("c2d:C->D");
		// add property edges
		typeGraph.declare("a2S:A->String#");
		typeGraph.declare("b2S:B->String#");
		typeGraph.declare("c2S:C->String#");
		typeGraph.declare("d2S:D->String#");
		
		baseGraph = new TypedGraph(typeGraph);
		baseGraph.declare(	
				"a1:A;"
				+"b1:B;"
				+"b2:B;"
				+"b3:B;"
				+"c1:C;"
				+"a1-a2b->b1;"		
				+"b3-b2c->c1;"
				+"a1.a2S=\"str1\";"
				+"a1.a2S=\"str2\";"
				+"a1.a2S=\"str3\";");	
		
		System.out.println("baseGraph: ");
		print(baseGraph);	
		
	}
	
			
	private static void build_aGraph() {
		
		aGraph = baseGraph.getCopy();
		
		// 新加d1
		aGraph.declare("d1:D;");
		
		// 修改e1的target为b2
		TypedEdge e1 = aGraph.getAllTypedEdges().get(0);
		TypedEdge e = new TypedEdge();
		e.setType(e1.getType());
		e.setSource(e1.getSource());
		e.setTarget(aGraph.getAllTypedNodes().get(2));
		aGraph.replaceWith(e1, e);
		
		// 新加ValueNode"hello"
		ValueNode vn1 = aGraph.getAllValueNodes().get(0); // 没有直接new ValueNode()方法，
		ValueNode vn4 = vn1.createConstantNode("hello", vn1.getType());
		aGraph.addValueNode(vn4);
		
		// 修改ve1的target为"hello"
		ValueEdge ve1 = aGraph.getAllValueEdges().get(0); 
		ValueEdge ve = new ValueEdge();
		ve.setType(ve1.getType());
		ve.setSource(ve1.getSource());
		ve.setTarget(aGraph.getAllValueNodes().get(3));
		aGraph.replaceWith(ve1, ve);
		
		System.out.println("aGraph: ");
		print(aGraph);

	}
	
	private static void build_bGraph() {
		
		bGraph = baseGraph.getCopy();
		
		// 新加d2
		bGraph.declare("d2:D;");
		
		// 删除e2
		bGraph.remove(bGraph.getAllTypedEdges().get(1));
		
		// 新加ValueNode"world"
		ValueNode v0 = bGraph.getAllValueNodes().get(0); // 没有直接new ValueNode()方法，
		ValueNode v = v0.createConstantNode("world", v0.getType());
		bGraph.addValueNode(v);
		
		// 删除ve2
		bGraph.remove(bGraph.getAllValueEdges().get(1));
		
		System.out.println("bGraph: ");
		print(bGraph);
		
	}
	
	private static void print(TypedGraph typedGraph) {
		
		System.out.println("TypedNodes: " + typedGraph.getAllTypedNodes().toString());
		System.out.println("ValueNodes: " + typedGraph.getAllValueNodes().toString());
		System.out.println("TypedEdges: " + typedGraph.getAllTypedEdges().toString());
		System.out.println("ValueEdges: " + typedGraph.getAllValueEdges().toString());
		System.out.println("*********************************************************************");
		System.out.println();
	}

}
