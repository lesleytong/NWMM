package edu.ustb.sei.mde.bxcore.tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.graph.type.DataTypeNode;
import edu.ustb.sei.mde.graph.type.TypeGraph;
import edu.ustb.sei.mde.graph.typedGraph.BXMerge;
import edu.ustb.sei.mde.graph.typedGraph.BXMerge3;
import edu.ustb.sei.mde.graph.typedGraph.BXMerge_NewVersion2;
import edu.ustb.sei.mde.graph.typedGraph.SimMerge;
import edu.ustb.sei.mde.graph.typedGraph.TypedEdge;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;
import edu.ustb.sei.mde.graph.typedGraph.ValueEdge;
import edu.ustb.sei.mde.graph.typedGraph.ValueNode;

public class Test_SimMerge_Topology {

	static TypedGraph baseGraph = null;
	static TypedGraph aGraph = null;
	static TypedGraph bGraph = null;
	static TypedGraph resultGraph = null;
	
	public static void main(String[] args) throws NothingReturnedException{
		
		build_baseGraph();		
		SimMerge.nWayMerge(baseGraph, null);
		
	}
	

	private static void build_baseGraph() {
		
		TypeGraph typeGraph = new TypeGraph();
		// add type nodes
		typeGraph.declare("A");
		typeGraph.declare("B");
		typeGraph.declare("C");
		typeGraph.declare("D");
		typeGraph.declare("E");
		typeGraph.declare("F");
		typeGraph.declare("G");
		typeGraph.declare("H");
		typeGraph.declare("I");
		typeGraph.declare("J");
		// add data type nodes
		typeGraph.declare("String:java.lang.String");
		// add type edges
		typeGraph.declare("a2b:A->B*");
		typeGraph.declare("a2c:A->C");
		typeGraph.declare("b2c:B->C");	// 类型图可以是图
		typeGraph.declare("b2e:B->E");	
		typeGraph.declare("e2f:E->F");	
		typeGraph.declare("e2g:E->G");	
		typeGraph.declare("c2d:C->D");
		typeGraph.declare("h2i:H->I");
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
				+"c1:C;"
				+"d1:D;"
				+"e1:E;"
				+"f1:F;"
				+"g1:G;"
				+"h1:H;"
				+"i1:I;"
				+"j1:J;"
				+"a1-a2b->b1;"		
				+"a1-a2b->b2;"	// 测试成功：会识别出是A->B	
				+"a1-a2c->c1;"
				+"c1-c2d->d1;"	// 实例图为树结构（是为了吻合emf吗）
				+"b1-b2e->e1;"	
				+"e1-e2f->f1;"
				+"e1-e2g->g1;"
				+"h1-h2i->i1;"
				);	
		
		System.out.println("baseGraph: ");
		print(baseGraph);	
		
	}
	
			
	private static void build_aGraph() {
		
		aGraph = baseGraph.getCopy();
		
		// 新加d1
		aGraph.declare("d1:D;");
			
		System.out.println("aGraph: ");
		print(aGraph);

	}
	
	private static void build_bGraph() {
		
		bGraph = baseGraph.getCopy();

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
