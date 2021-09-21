package edu.ustb.sei.mde.bxcore.tests;

import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.graph.type.TypeEdge;
import edu.ustb.sei.mde.graph.type.TypeGraph;
import edu.ustb.sei.mde.graph.type.TypeNode;
import edu.ustb.sei.mde.graph.typedGraph.BXMerge3;
import edu.ustb.sei.mde.graph.typedGraph.BXMerge_NewVersion2;
import edu.ustb.sei.mde.graph.typedGraph.TypedEdge;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;
import edu.ustb.sei.mde.graph.typedGraph.TypedNode;

public class Test_2 {

	static TypedGraph baseGraph = null;
	static TypedGraph aGraph = null;
	static TypedGraph bGraph = null;
	static TypedGraph resultGraph = null;
	
	public static void main(String[] args){
		
		build_baseGraph();
		build_aGraph();
		build_bGraph();
		
		try {
			resultGraph = BXMerge3.mergeSerial(baseGraph, aGraph, bGraph);
			System.out.println("\nresultGraph: ");
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
		
		// 删除b1
		aGraph.remove(aGraph.getAllTypedNodes().get(1));
		
		// 新加a1-b2
		TypeEdge type = aGraph.getTypeGraph().getAllTypeEdges().get(0);
		TypedEdge e3 = new TypedEdge();
		e3.setType(type);
		TypedNode a1 = aGraph.getAllTypedNodes().get(0);
		TypedNode b2 = aGraph.getAllTypedNodes().get(1);
		e3.setSource(a1);
		e3.setTarget(b2);
		aGraph.getAllTypedEdges().add(e3);
		
		System.out.println("aGraph: ");
		print(aGraph);

	}
	
	private static void build_bGraph() {
		
		bGraph = baseGraph.getCopy();
	
		// 修改b3为d1
		TypeNode type = bGraph.getTypeGraph().getAllTypeNodes().get(3);
		TypedNode d1 = new TypedNode();
		d1.setType(type);
		TypedNode b3 = bGraph.getAllTypedNodes().get(3);
		bGraph.replaceWith(b3, d1);
		
		// 新加d1-"hello"
		bGraph.declare(
				  "d2:D;"
				+ "d2.d2S=\"hello\";");
		
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
