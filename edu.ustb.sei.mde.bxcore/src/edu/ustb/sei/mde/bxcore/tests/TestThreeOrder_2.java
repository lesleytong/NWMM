package edu.ustb.sei.mde.bxcore.tests;

import java.util.HashMap;

import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.graph.type.TypeGraph;
import edu.ustb.sei.mde.graph.typedGraph.BXMerge3;
import edu.ustb.sei.mde.graph.typedGraph.TypedEdge;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;

/**
 * 
 * base: e1, e2, e3, e4, e5, e6, e7
 *    a: e2, e3, e1, e4, e8, e7, e9
 *    b: e1, e2, e10,e4,e7, e8, e5, e11

 * merge(如提交强制序<e8, e7>，threeOrder): e2, e1, e10, e4, e8, e7, e5, e9, e11

 * @author 10242
 */
public class TestThreeOrder_2 {

	static TypedGraph baseGraph = null;
	static TypedGraph aGraph = null;
	static TypedGraph bGraph = null;
	static TypedGraph resultGraph = null;
	static HashMap<TypedEdge, TypedEdge> forceOrd = new HashMap<>();

	public static void main(String[] args) {

		build_baseGraph();
		build_aGraph();
		build_bGraph();

		try {
			resultGraph = BXMerge3.mergeOrigin(baseGraph, aGraph, bGraph);
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
		typeGraph.declare("E");
		typeGraph.declare("F");
		typeGraph.declare("G");
		typeGraph.declare("H");
		typeGraph.declare("I");
		typeGraph.declare("J");
		typeGraph.declare("K");
		typeGraph.declare("L");
		// add type edges
		typeGraph.declare("a2b:A->B"); 
		typeGraph.declare("b2c:B->C");
		typeGraph.declare("c2d:C->D");
		typeGraph.declare("d2e:D->E");
		typeGraph.declare("e2f:E->F");
		typeGraph.declare("f2g:F->G");
		typeGraph.declare("g2h:G->H");
		typeGraph.declare("h2i:H->I");
		typeGraph.declare("i2j:I->J");
		typeGraph.declare("j2k:J->K");
		typeGraph.declare("k2l:K->L");

		baseGraph = new TypedGraph(typeGraph);
		baseGraph.declare(
				"a:A;" + "b:B;" + "c:C;" + "d:D;" + "e:E;" + "f:F;" + "g:G;" + "h:H;" + "i:I;" + "j:J;" + "k:K;" + "l:L;" 
				+ "a-a2b->b;" + "b-b2c->c;" + "c-c2d->d;" + "d-d2e->e;" + "e-e2f->f;" + "f-f2g->g;" + "g-g2h->h;");

		System.out.println("baseGraph: ");
		print(baseGraph);

	}

	private static void build_aGraph() {

		aGraph = baseGraph.getCopy();
		
		aGraph.declare(
				"h:H;" + "i:I;" + "j:J;"
				+ "h-h2i->i;" + "i-i2j->j;");

		
		aGraph.remove(aGraph.getAllTypedEdges().get(5)); // 删除用图的remove
		
		TypedEdge e1 = aGraph.getAllTypedEdges().get(0);
		aGraph.getAllTypedEdges().remove(0); // 交换序用列表的remove
		aGraph.getAllTypedEdges().add(2, e1);
		
		TypedEdge e5 = aGraph.getAllTypedEdges().get(4);
		aGraph.getAllTypedEdges().remove(4); // 交换序用列表的remove
		aGraph.getAllTypedEdges().add(5, e5);
		
		TypedEdge e8 = aGraph.getAllTypedEdges().get(6);
		aGraph.getAllTypedEdges().remove(6); // 交换序用列表的remove
		aGraph.getAllTypedEdges().add(4, e8);

		System.out.println("aGraph: ");
		print(aGraph);
		
		// 如果aGraph提交强制序<e8, e7>，就不产生冲突吧
//		forceOrd.put(aGraph.getAllTypedEdges().get(4), aGraph.getAllTypedEdges().get(5));

	}

	private static void build_bGraph() {

		bGraph = baseGraph.getCopy();

		bGraph.declare(
				"j:J;" + "k:K;" + "l:L;"
				+ "j-j2k->k;" + "k-k2l->l;");
		
		bGraph.addTypedNode(aGraph.getAllTypedNodes().get(12));
		bGraph.addTypedNode(aGraph.getAllTypedNodes().get(13));
		bGraph.addTypedEdge(aGraph.getAllTypedEdges().get(4));
		
		bGraph.remove(bGraph.getAllTypedEdges().get(2)); // 删除用图的remove
		bGraph.remove(bGraph.getAllTypedEdges().get(4)); // 删除用图的remove
		
		TypedEdge e10 = bGraph.getAllTypedEdges().get(5);
		bGraph.getAllTypedEdges().remove(5); // 交换序用列表的remove
		bGraph.getAllTypedEdges().add(2, e10);
		
		TypedEdge e5 = bGraph.getAllTypedEdges().get(4);
		bGraph.getAllTypedEdges().remove(4); // 交换序用列表的remove
		bGraph.getAllTypedEdges().add(7, e5);
		
		TypedEdge e11 = bGraph.getAllTypedEdges().get(5);
		bGraph.getAllTypedEdges().remove(5); // 交换序用列表的remove
		bGraph.getAllTypedEdges().add(7, e11);
				
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