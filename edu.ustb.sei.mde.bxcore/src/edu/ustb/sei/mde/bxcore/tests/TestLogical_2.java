package edu.ustb.sei.mde.bxcore.tests;

import edu.ustb.sei.mde.graph.type.TypeEdge;
import edu.ustb.sei.mde.graph.type.TypeGraph;
import edu.ustb.sei.mde.graph.typedGraph.BXMerge;
import edu.ustb.sei.mde.graph.typedGraph.TypedEdge;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;
import edu.ustb.sei.mde.graph.typedGraph.TypedNode;
/**
 * 测试二向合并中的循环包含冲突
 * @author 10242
 *
 */
public class TestLogical_2 {

	static TypedGraph baseGraph = null;
	static TypedGraph aGraph = null;
	static TypedGraph resultGraph = null;
	
	public static void main(String[] args){
		
		build_baseGraph();
		build_aGraph();
			
		resultGraph = BXMerge.additiveMerge(baseGraph, aGraph);
		System.out.println("resultGraph: ");
		print(resultGraph);
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
		typeGraph.declare("b2a:B->A");	// 我新加的
		typeGraph.declare("b2c:B->C");
		typeGraph.declare("c2d:C->D");
		// add property edges
		typeGraph.declare("a2S:A->String#");
		
		baseGraph = new TypedGraph(typeGraph);
		baseGraph.declare(	
				"a1:A;"
				+"b1:B;"
				+"a1-a2b->b1;"			//e1
				+"a1.a2S=\"str1\";"
				+"a1.a2S=\"str2\";"
				+"a1.a2S=\"str3\";");	
		
		TypeEdge e1Type = baseGraph.getAllTypedEdges().get(0).getType();
		e1Type.setContainment(true);
		
		System.out.println("baseGraph: ");
		print(baseGraph);
	
		
	}
	
			
	private static void build_aGraph() {
		
		aGraph = baseGraph.getCopy();
		
		aGraph.declare(	
				"a2:A;"
				+"b2:B;"
				+"b2-b2a->a2;");	// 为了利用此typeEdge
		
		TypeEdge typeEdge = aGraph.getAllTypedEdges().get(1).getType();
		typeEdge.setContainment(true);
		
		TypedEdge e2 = new TypedEdge();
		e2.setType(typeEdge);
		
		TypedNode a1 = aGraph.getAllTypedNodes().get(0);
		TypedNode b1 = aGraph.getAllTypedNodes().get(1);
		e2.setSource(b1);
		e2.setTarget(a1);
		aGraph.addTypedEdge(e2);
		
		System.out.println("aGraph: ");
		print(aGraph);
		
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
