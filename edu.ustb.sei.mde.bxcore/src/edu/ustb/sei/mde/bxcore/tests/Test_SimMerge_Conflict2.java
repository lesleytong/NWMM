package edu.ustb.sei.mde.bxcore.tests;

import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.graph.type.TypeGraph;
import edu.ustb.sei.mde.graph.typedGraph.SimMerge;
import edu.ustb.sei.mde.graph.typedGraph.TypedEdge;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;
import edu.ustb.sei.mde.graph.typedGraph.TypedNode;
import edu.ustb.sei.mde.graph.typedGraph.ValueEdge;
import edu.ustb.sei.mde.graph.typedGraph.ValueNode;

public class Test_SimMerge_Conflict2 {

	static TypedGraph baseGraph = null;
	static TypedGraph aGraph = null;
	static TypedGraph bGraph = null;

	static TypedGraph resultGraph = null;
	
	public static void main(String[] args) throws NothingReturnedException {
		build_baseGraph();
		build_aGraph();
		build_bGraph();
		
		resultGraph = SimMerge.nWayMerge(baseGraph, aGraph, bGraph);
		
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
				+"a1-persons->p1;"	
				+"a1-persons->p2;"
				+"a1.name=\"ClassOne\";"
				+"p1.name=\"Lesley\";"
				+"p2.name=\"Mars\";"
				);	
		
		System.out.println("baseGraph: ");
		print(baseGraph);	
		
	}
	
	private static void build_aGraph() {
		
		aGraph = baseGraph.getCopy();	
				
		System.out.println("aGraph: ");
		print(aGraph);
		
	}
	
	private static void build_bGraph() {
		
		bGraph = baseGraph.getCopy();	// 浅复制，直接set会影响基础图
		
		// 删除一条关联边
		TypedEdge deleteEdge = bGraph.getAllTypedEdges().get(0);
		bGraph.remove(deleteEdge);
		
		System.out.println("bGraph: ");
		print(bGraph);

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
