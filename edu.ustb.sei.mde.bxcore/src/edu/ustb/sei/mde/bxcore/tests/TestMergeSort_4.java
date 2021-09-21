package edu.ustb.sei.mde.bxcore.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.graph.type.TypeGraph;
import edu.ustb.sei.mde.graph.typedGraph.BXMerge;
import edu.ustb.sei.mde.graph.typedGraph.TypedEdge;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;
import edu.ustb.sei.mde.structure.Tuple2;

/**
 * ����TypedEdge���� �Ѿ���ǰ�棬�����ƶ�
 * 
 * @author 10242
 */
public class TestMergeSort_4 {

	static TypedGraph baseGraph = null;
	static TypedGraph aGraph = null;
	static TypedGraph bGraph = null;
	static TypedGraph resultGraph = null;
	static Set<Tuple2<TypedEdge, TypedEdge>> orders = new HashSet<>();

	public static void main(String[] args) {

		build_baseGraph();
		build_aGraph();
		build_bGraph();

		try {
			resultGraph = BXMerge.merge(baseGraph, aGraph, bGraph);
			System.out.println("resultGraph: ");
			print(resultGraph);

			HashMap<TypedEdge, TypedEdge> forceOrd = BXMerge.checkForceOrd(resultGraph, orders);

//			System.out.println("###");
//			System.out.println("����ǰ��");
//			System.out.println(resultGraph.getAllTypedEdges());
//			BXMerge.mergeSort(resultGraph.getAllTypedEdges(), baseGraph, forceOrd, aGraph, bGraph);
//			System.out.println("���ú�");
//			System.out.println(resultGraph.getAllTypedEdges());
//			System.out.println("###");
			
			List<TypedEdge> mergeList = BXMerge.threeOrder4(baseGraph, resultGraph, forceOrd, aGraph, bGraph);
			System.out.println("���ú�: ");
			System.out.println(mergeList);

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
		baseGraph.declare(
				"a1:A;" + "b1:B;" + "b2:B;" + "b3:B;" + "c1:C;" + "c2:C;" + "c3:C;" + "d1:D;" + "d2:D;" + "a1-a2b->b1;" // e1-e2-e3-e4
						+ "a1-a2b->b2;" + "b3-b2c->c1;" + "c2-c2d->d1;" + "a1.a2S=\"str1\";" + "a1.a2S=\"str2\";"
						+ "a1.a2S=\"str3\";");

		System.out.println("baseGraph: ");
		print(baseGraph);

	}

	private static void build_aGraph() {

		aGraph = baseGraph.getCopy();

		// e4-e1-e2-e3
		TypedEdge e4 = aGraph.getAllTypedEdges().get(3);
		aGraph.getAllTypedEdges().remove(e4);
		aGraph.getAllTypedEdges().add(0, e4);

		System.out.println("aGraph: ");
		print(aGraph);

	}

	private static void build_bGraph() {

		bGraph = baseGraph.getCopy();

		// e4-e1-e3-e2
		TypedEdge e4 = bGraph.getAllTypedEdges().get(3);
		TypedEdge e3 = bGraph.getAllTypedEdges().get(2);
		bGraph.getAllTypedEdges().remove(e4);
		bGraph.getAllTypedEdges().remove(e3);
		bGraph.getAllTypedEdges().add(0, e4);
		bGraph.getAllTypedEdges().add(2, e3);

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
