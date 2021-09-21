package edu.ustb.sei.mde.bxcore.tests;

import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.graph.type.TypeGraph;
import edu.ustb.sei.mde.graph.typedGraph.BXMerge3;
import edu.ustb.sei.mde.graph.typedGraph.GraphChangeTool;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;
import edu.ustb.sei.mde.graph.typedGraph.Profiler2;

public class TestAutomatic_1 {

	static TypedGraph baseGraph = null;
	static TypedGraph aGraph = null;
	static TypedGraph bGraph = null;
	static TypedGraph resultGraph = null;

	public static void main(String[] args) {

		build_baseGraph();
		build_aGraph();
		build_bGraph();

		try {

			long start = System.currentTimeMillis();
//			resultGraph = BXMerge3.merge_origin(baseGraph, aGraph, bGraph);
//			resultGraph = BXMerge3.merge(baseGraph, aGraph, bGraph);
			resultGraph = BXMerge3.mergeParallel(baseGraph, aGraph, bGraph);
			long end = System.currentTimeMillis();
			System.out.println("耗时：" + (end - start) + "ms");
			
			System.out.println("********************************合并后的resultGraph:");
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
		baseGraph.declare("a1:A;" + "a2:A;" + "a3:A;" + "a4:A;" + "a5:A;" + "a6:A;"

				+ "b1:B;" + "b2:B;" + "b3:B;" + "b4:B;" + "b5:B;" + "b6:B;"

				+ "c1:C;" + "c2:C;" + "c3:C;" + "c4:C;" + "c5:C;" + "c6:C;" + "c7:C;" + "c8:C;" + "c9:C;" + "c10:C;"
				+ "c11:C;" + "c12:C;"

				+ "d1:D;" + "d2:D;" + "d3:D;" + "d4:D;" + "d5:D;" + "d6:D;" + "d7:D;" + "d8:D;" + "d9:D;" + "d10:D;"
				+ "d11:D;" + "d12:D;"

				+ "a1-a2b->b1;" + "a1-a2b->b2;" + "a2-a2b->b3;" + "a3-a2b->b4;"

				+ "b1-b2c->c1;" + "b2-b2c->c2;" + "b4-b2c->c3;"

				+ "c4-c2d->d4;" + "c5-c2d->d5;" + "c6-c2d->d6;" + "c7-c2d->d7;" + "c8-c2d->d8;" + "c9-c2d->d9;"
				+ "c10-c2d->d10;" + "c11-c2d->d11;" + "c12-c2d->d12;"

				+ "a4.a2S=\"str1\";" + "a4.a2S=\"str2\";" + "a4.a2S=\"str3\";"

				+ "c1.c2S=\"str4\";" + "c1.c2S=\"str5\";" + "c1.c2S=\"str6\";"

				+ "d1.d2S=\"str7\";" + "d1.d2S=\"str8\";" + "d1.d2S=\"str9\";"

		);

		for (int i = 0; i < 5000; i++) {
			String str = GraphChangeTool.getRandomString(5, true);
			baseGraph.declare("a1:A;" + "b1:B;" + "a1-a2b->b1;" + "b1.b2S=\"" + str + "\";");
		}

		print(baseGraph);

	}

	private static void build_aGraph() {

		aGraph = baseGraph.getCopy();

		// add z TypedEdges and 2*z TypedNodes
		GraphChangeTool.addTypedNodesAndTypedEdges(aGraph, 50);

		// add z ValueEdges and z TypedNodes and z ValueNodes
		GraphChangeTool.addNodesAndValueEdges(aGraph, 50);

		// change TypedEdges
		// '起始点'与从其下标相差1的边交换序；删除30个；注意还替换了一个
		GraphChangeTool.changeTypedEdges(aGraph, 0, 50);

		// change ValueEdges
		GraphChangeTool.changeValueEdges(aGraph, 50); // 删除30个

		// change TypedNodes
		GraphChangeTool.changeTypedNodes(aGraph, 50, 50); // 替换30个；删除30个

	}

	private static void build_bGraph() {

		bGraph = baseGraph.getCopy();

		// add z TypedEdges and 2*z TypedNodes
		GraphChangeTool.addTypedNodesAndTypedEdges(bGraph, 50);

		// add z ValueEdges and z TypedNodes and z ValueNodes
		GraphChangeTool.addNodesAndValueEdges(bGraph, 50);

		// change TypedEdges
		// '起始点'与从其下标相差1的边交换；删除20个；注意还替换了1个
		GraphChangeTool.changeTypedEdges(bGraph, 0, 50);

		// change ValueEdges
		GraphChangeTool.changeValueEdges(bGraph, 50); // 删除20个

		// change TypedNodes
		GraphChangeTool.changeTypedNodes(bGraph, 50, 50); // 替换20个；删除20个

	}

	private static void print(TypedGraph typedGraph) {
		System.out.println("TypedNodes: " + typedGraph.getAllTypedNodes().size());
		System.out.println("ValueNodes: " + typedGraph.getAllValueNodes().size());
		System.out.println("TypedEdges: " + typedGraph.getAllTypedEdges().size());
		System.out.println("ValueEdges: " + typedGraph.getAllValueEdges().size());
		System.out.println("*********************************************************************");
		System.out.println();
	}

}
