package edu.ustb.sei.mde.bxcore.tests;

import java.util.HashSet;
import java.util.Set;

import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.graph.type.TypeGraph;
import edu.ustb.sei.mde.graph.typedGraph.BXMerge;
import edu.ustb.sei.mde.graph.typedGraph.TypedEdge;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;
import edu.ustb.sei.mde.structure.Tuple2;

/**
 * 测试序中构成环的冲突
 * 
 * @author 10242
 */
public class TestForceOrder_1 {

	static TypedGraph baseGraph = null;
	static TypedGraph aGraph = null;

	static TypedGraph resultGraph = null;
	static Set<Tuple2<TypedEdge, TypedEdge>> orders = new HashSet<>();

	public static void main(String[] args) {

		build_baseGraph();
		build_aGraph();

		try {

			// 添加性合并
			resultGraph = BXMerge.additiveMerge(baseGraph, aGraph);
			System.out.println("resultGraph: ");
			print(resultGraph);

			// 检查强制序是否合法
			BXMerge.checkForceOrd(resultGraph, orders);

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

		baseGraph = new TypedGraph(typeGraph);
		baseGraph.declare("a1:A;" + "b1:B;" + "b2:B;" + "b3:B;" + "c1:C;" + "a1-a2b->b1;" // e1-e2-e3
				+ "a1-a2b->b2;" + "b3-b2c->c1;" + "a1.a2S=\"str1\";" + "a1.a2S=\"str2\";" + "a1.a2S=\"str3\";");

		System.out.println("baseGraph: ");
		print(baseGraph);

	}

	private static void build_aGraph() {

		aGraph = baseGraph.getCopy();

		// e1-e4-e2-e3
		aGraph.declare("c2:C;" + "d1:D;" + "c2-c2d->d1;");
		TypedEdge e4 = aGraph.getAllTypedEdges().get(3);
		aGraph.getAllTypedEdges().remove(e4);
		aGraph.getAllTypedEdges().add(1, e4);

		System.out.println("aGraph: ");
		print(aGraph);

		TypedEdge e1 = aGraph.getAllTypedEdges().get(0);
		e4 = aGraph.getAllTypedEdges().get(1);
		TypedEdge e2 = aGraph.getAllTypedEdges().get(2);
		TypedEdge e3 = aGraph.getAllTypedEdges().get(3);

		// {<e4, e1>, <e3, e4>, <e1, e2>, <e2, e3>}有冲突
		Tuple2<TypedEdge, TypedEdge> order1 = Tuple2.make(e4, e1);
		orders.add(order1);

		Tuple2<TypedEdge, TypedEdge> order2 = Tuple2.make(e3, e4);
		orders.add(order2);

		Tuple2<TypedEdge, TypedEdge> order3 = Tuple2.make(e1, e2);
		orders.add(order3);

		Tuple2<TypedEdge, TypedEdge> order4 = Tuple2.make(e2, e3);
		orders.add(order4);

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