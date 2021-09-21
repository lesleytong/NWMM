package edu.ustb.sei.mde.bxcore.tests;

import java.util.List;
import edu.ustb.sei.mde.graph.type.TypeGraph;
import edu.ustb.sei.mde.graph.typedGraph.TypedEdge;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;
/**
 * 添加性合并
 * 新添、交换序
 * @author 10242
 */
public class TestTwoOrder_1 {

	static TypedGraph baseGraph = null;
	static TypedGraph aGraph = null;
	static TypedGraph resultGraph = null;
	
	public static void main(String[] args){
		
		build_baseGraph();
		build_aGraph();
				
		
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
		
		baseGraph = new TypedGraph(typeGraph);
		baseGraph.declare(	
				"a1:A;"
				+"b1:B;"
				+"b2:B;"
				+"b3:B;"
				+"c1:C;"
				+"c2:C;"
				+"d1:D;"
				+"a1-a2b->b1;"		//e1-e2-e3-e4
				+"a1-a2b->b2;"
				+"b3-b2c->c1;"
				+"c2-c2d->d1;"
				+"a1.a2S=\"str1\";"
				+"a1.a2S=\"str2\";"
				+"a1.a2S=\"str3\";");	
		
		
		System.out.println("baseGraph: ");
		print(baseGraph);	
		
	}
	
			
	private static void build_aGraph() {
		
		aGraph = baseGraph.getCopy();
		
		aGraph.declare(
				    "c3:C;"		
				   +"d2:D;"
				   +"c3-c2d->d2;"
				   
				   +"c4:C;"		
				   +"d3:D;"
				   +"c4-c2d->d3;"
				   );
		
		// e3-e5-e1-e6
		TypedEdge e2 = aGraph.getAllTypedEdges().get(1);
		TypedEdge e4 = aGraph.getAllTypedEdges().get(3);
		aGraph.remove(e2);
		aGraph.remove(e4);
		TypedEdge e1 = aGraph.getAllTypedEdges().get(0);
		aGraph.getAllTypedEdges().remove(e1);
		aGraph.getAllTypedEdges().add(2, e1);
				
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
