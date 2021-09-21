package edu.ustb.sei.mde.bxcore.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hamcrest.core.IsEqual;

import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.graph.type.TypeEdge;
import edu.ustb.sei.mde.graph.type.TypeGraph;
import edu.ustb.sei.mde.graph.typedGraph.BXMerge;
import edu.ustb.sei.mde.graph.typedGraph.TypedEdge;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;
import edu.ustb.sei.mde.graph.typedGraph.TypedNode;
import edu.ustb.sei.mde.structure.Tuple2;

/**
 * 替换
 * 
 * @author 10242
 */
public class TestMergeSort_3 {

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
//			System.out.println("调用前：");
//			System.out.println(resultGraph.getAllTypedEdges());
//			BXMerge.mergeSort(resultGraph.getAllTypedEdges(), baseGraph, forceOrd, aGraph, bGraph);
//			System.out.println("调用后：");
//			System.out.println(resultGraph.getAllTypedEdges());
//			System.out.println("###");
			
			List<TypedEdge> mergeList = BXMerge.threeOrder4(baseGraph, resultGraph, forceOrd, aGraph, bGraph);
			System.out.println("调用后: ");
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
		baseGraph.declare("a1:A;" + "b1:B;" + "c1:C;" + "d1:D;" + "d2:D;" + "a1-a2b->b1;" // e1-e2-e3
				+ "b1-b2c->c1;" + "c1-c2d->d1;" + "a1.a2S=\"str1\";" + "a1.a2S=\"str2\";" + "a1.a2S=\"str3\";");

		System.out.println("baseGraph: ");
		print(baseGraph);

	}

	private static void build_aGraph() {

		aGraph = baseGraph.getCopy();

		aGraph.declare("b3:B;" + "c3:C;" + "b3-b2c->c3;");

		// e4-e2-e3
		TypedEdge e4 = aGraph.getAllTypedEdges().get(3);
		aGraph.getAllTypedEdges().remove(e4); // 交换序用列表的remove方法

		TypedEdge e1 = aGraph.getAllTypedEdges().get(0);
		aGraph.remove(e1); // 删除用图的remove方法，能清除indexToObject

		aGraph.getAllTypedEdges().add(0, e4);

		System.out.println("aGraph: ");
		print(aGraph);

	}

	private static void build_bGraph() {

		bGraph = baseGraph.getCopy();

		// e1-e3'-e2
		TypedNode d2 = bGraph.getAllTypedNodes().get(4);
		TypedEdge e3base = bGraph.getAllTypedEdges().get(2);

		// 申请新对象
		TypedEdge e3new = new TypedEdge();
		e3new.setType(e3base.getType());
		e3new.setSource(e3base.getSource());
		e3new.setTarget(d2);
		// 替换之前
		System.out.println("***替换前e3new内部索引集的hashCode：" + e3new.getIndex().getInternalIndices().hashCode());
		System.out.println("***替换前e3base内部索引集的hashCode：" + e3base.getIndex().getInternalIndices().hashCode());
		bGraph.replaceWith(e3base, e3new);
		// 替换之后，内部索引集的hashCode是一样的
		System.out.println("***替换后e3new内部索引集的hashCode：" + e3new.getIndex().getInternalIndices().hashCode());
		System.out.println("***替换后e3base内部索引集的hashCode：" + e3base.getIndex().getInternalIndices().hashCode());

//		// 测试内部索引集只是有交集
//		TypedEdge e0 = bGraph.getAllTypedEdges().get(0);
//		System.out.println("***替换前e0内部索引集的hashCode：" + e0.getIndex().getInternalIndices().hashCode());
//		System.out.println("***替换前e3base内部索引集的hashCode：" + e3base.getIndex().getInternalIndices().hashCode());
//		e0.setType(e3base.getType());
//		e0.setSource(e3base.getSource());
//		e0.setTarget(d2);
//		bGraph.replaceWith(e3base, e0);
//		System.out.println("***替换后e0内部索引集的hashCode：" + e0.getIndex().getInternalIndices().hashCode());
//		System.out.println("***替换后e3base内部索引集的hashCode：" + e3base.getIndex().getInternalIndices().hashCode());

		TypedEdge e2 = bGraph.getAllTypedEdges().get(1);
		bGraph.getAllTypedEdges().remove(e2);
		bGraph.getAllTypedEdges().add(e2);

		System.out.println();

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
