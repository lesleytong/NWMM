package edu.ustb.sei.mde.bxcore.tests;

import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.graph.type.TypeEdge;
import edu.ustb.sei.mde.graph.type.TypeGraph;
import edu.ustb.sei.mde.graph.typedGraph.BXMerge;
import edu.ustb.sei.mde.graph.typedGraph.BXMerge3;
import edu.ustb.sei.mde.graph.typedGraph.TypedEdge;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;
import edu.ustb.sei.mde.graph.typedGraph.TypedNode;
/**
 * ·ÇisMany
 * @author 10242
 *
 */
public class TestLogical_6 {

	static TypedGraph baseGraph = null;
	static TypedGraph aGraph = null;
	static TypedGraph bGraph = null;
	static TypedGraph cGraph = null;
	static TypedGraph resultGraph = null;
	
	public static void main(String[] args){
		
		build_baseGraph();
		build_aGraph();
		build_bGraph();
		build_cGraph();
				
		try {
			
			resultGraph = BXMerge3.mergeSerial(baseGraph, aGraph, bGraph, cGraph);
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
		typeGraph.declare("b2c:B->C");
		// add property edges
		typeGraph.declare("a2S:A->String#");
		
		baseGraph = new TypedGraph(typeGraph);
		baseGraph.declare(	
				 "b1:B;"
				+"c1:C;"
				);		
	}
				
	private static void build_aGraph() {
		
		aGraph = baseGraph.getCopy();

		TypedEdge e = new TypedEdge();
		TypedNode source = aGraph.getAllTypedNodes().get(0);
		TypedNode target = aGraph.getAllTypedNodes().get(1);
		TypeEdge type = aGraph.getTypeGraph().getAllTypeEdges().get(0);
		e.setSource(source);
		e.setTarget(target);
		e.setType(type);
		aGraph.addTypedEdge(e);
				
		System.out.println("aGraph: ");
		print(aGraph);
		
	}
	
	private static void build_bGraph() {
		
		bGraph = baseGraph.getCopy();
		
		bGraph.declare(	
				"c2:C;"
				);
		
		TypedEdge e = new TypedEdge();
		TypedNode source = bGraph.getAllTypedNodes().get(0);
		TypedNode target = bGraph.getAllTypedNodes().get(2);
		TypeEdge type = bGraph.getTypeGraph().getAllTypeEdges().get(0);
		e.setSource(source);
		e.setTarget(target);
		e.setType(type);
		bGraph.addTypedEdge(e);
		
		System.out.println("bGraph: ");
		print(bGraph);

	}
	
	private static void build_cGraph() {
		
		cGraph = baseGraph.getCopy();
		
		cGraph.declare(	
				"c3:C;"
				);
		
		TypedEdge e = new TypedEdge();
		TypedNode source = cGraph.getAllTypedNodes().get(0);
		TypedNode target = cGraph.getAllTypedNodes().get(2);
		TypeEdge type = cGraph.getTypeGraph().getAllTypeEdges().get(0);
		e.setSource(source);
		e.setTarget(target);
		e.setType(type);
		cGraph.addTypedEdge(e);
		
		System.out.println("cGraph: ");
		print(cGraph);

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
