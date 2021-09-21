package edu.ustb.sei.mde.graph.typedGraph;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import edu.ustb.sei.mde.graph.type.TypeEdge;
import edu.ustb.sei.mde.graph.type.TypeNode;

public class GraphChangeTool {
	static boolean changeTypedNodesFlag = true;
	static boolean changeTypedEdgesFlag = true;
	static boolean changeValueEdgesFlag = true;

	static boolean addTypedEdgesFlag = true;
	static boolean addValueEdgesFlag = true;

	// 交换序，删y个
	public static void changeTypedEdges(TypedGraph graph, int x, int y) {

		int exchangeStart = 0, exchangeEnd = 0;
		int deleteStart = 0, deleteEnd = 0;

		int allSize = graph.getAllTypedEdges().size();
		int quarterSize = allSize / 4;
		int halfSize = allSize / 2;
		int threeQuarterSize = quarterSize + halfSize;

		if (changeTypedEdgesFlag == true) {
			exchangeStart = 0;
			exchangeEnd = (x >= quarterSize) ? (quarterSize - 1) : x; // 修改为x了，因为是相差'起点'x
			deleteStart = quarterSize;
			deleteEnd = (deleteStart + y) >= halfSize ? (halfSize - 1) : (deleteStart + y - 1);
		} else {
			exchangeStart = halfSize;
			exchangeEnd = (x >= quarterSize) ? (threeQuarterSize - 1) : (exchangeStart + x);
			deleteStart = threeQuarterSize;
			deleteEnd = (deleteStart + y) >= allSize ? (allSize - 1) : (deleteStart + y - 1);
		}

		if (exchangeEnd > exchangeStart) {
			// 交换一次序，可以改策略
			TypedEdge e = graph.getAllTypedEdges().get(exchangeEnd);
			graph.getAllTypedEdges().remove(e);
			graph.getAllTypedEdges().add(exchangeStart, e);
		}

		// 删除
		HashMap<Integer, TypedEdge> map = new HashMap<>();
		for (int i = deleteStart; i <= deleteEnd; i++) {
			TypedEdge ed = graph.getAllTypedEdges().get(i);
			map.put(i, ed); // 先保存下来
		}
		for (int i = deleteStart; i <= deleteEnd; i++) {
			TypedEdge ed = map.get(i);
			graph.remove(ed);
		}

		// 替换type无意义的话，就保证替换边的两个端点与原type符合，改变source或target。
		if (changeTypedEdgesFlag == true) {
			// 找到第一个类型为a2b的边e，如果没有就不进行替换
			TypeEdge type = graph.getTypeGraph().getTypeEdge(graph.getTypeGraph().getTypeNode("A"), "a2b");
			TypedEdge e = new TypedEdge();
			TypedEdge er = null;	
			boolean helpFlag = false;
			for (int i = exchangeStart; i < exchangeEnd; i++) {
				er = graph.getAllTypedEdges().get(i);	// 被替换的边
				if (er.getType() == type) {
					e.setType(type);
					e.setSource(er.getSource());
					helpFlag = true;
					break;
				}
			}
			
			if(helpFlag == true) {
				// 找到第一个没有incomingEdge的B类型的节点b
				TypeNode typeB = graph.getTypeGraph().getTypeNode("B");
				for (int i = exchangeStart; i < exchangeEnd; i++) {
					TypedNode n = graph.getAllTypedNodes().get(i);
					if (n.getType() == typeB && graph.getIncomingEdges(n).size() == 0) {
						// 设置e的target是n
						e.setTarget(n);
						// 将er替换为e
						graph.replaceWith(er, e);
						break;
					}
				}
			}

		} else {
			// 找到第一个类型为c2d的边e，如果没有就不进行替换
			TypeEdge type = graph.getTypeGraph().getTypeEdge(graph.getTypeGraph().getTypeNode("C"), "c2d");
			TypedEdge e = new TypedEdge();
			TypedEdge er = null;	
			boolean helpFlag = false;
			for (int i = exchangeStart; i < exchangeEnd; i++) {
				er = graph.getAllTypedEdges().get(i);	// 被替换的边
				if (er.getType() == type) {
					e.setType(type);
					e.setSource(er.getSource());
					helpFlag = true;
					break;
				}
			}
			
			if(helpFlag == true) {
				// 找到第一个没有incomingEdge的D类型的节点d
				TypeNode typeD = graph.getTypeGraph().getTypeNode("D");
				for (int i = exchangeStart; i < exchangeEnd; i++) {
					TypedNode n = graph.getAllTypedNodes().get(i);
					if (n.getType() == typeD && graph.getIncomingEdges(n).size() == 0) {
						// 设置e的target是n
						e.setTarget(n);
						// 将er替换为e
						graph.replaceWith(er, e);
						break;
					}
				}
			}
		}

//		System.out.println("更改后TypedEdges规模: " + graph.getAllTypedEdges().size());
//		System.out.println(graph.getAllTypedEdges());

		changeTypedEdgesFlag = (changeTypedEdgesFlag == true) ? false : true;

	}
	
	// 删y个
	public static void changeValueEdges(TypedGraph graph, int y) {

		int deleteStart = 0, deleteEnd = 0;

		int allSize = graph.getAllValueEdges().size();
		int quarterSize = allSize / 4;
		int halfSize = allSize / 2;
		int threeQuarterSize = quarterSize + halfSize;

		if (changeValueEdgesFlag == true) {
			deleteStart = quarterSize;
			deleteEnd = (deleteStart + y) >= halfSize ? (halfSize - 1) : (deleteStart + y - 1);
		} else {
			deleteStart = threeQuarterSize;
			deleteEnd = (deleteStart + y) >= allSize ? (allSize - 1) : (deleteStart + y - 1);

		}

		// 删除
		HashMap<Integer, ValueEdge> map = new HashMap<>();
		for (int i = deleteStart; i <= deleteEnd; i++) {
			ValueEdge ed = graph.getAllValueEdges().get(i);
			map.put(i, ed); // 先保存下来
		}
		for (int i = deleteStart; i <= deleteEnd; i++) {
			ValueEdge ed = map.get(i);
			graph.remove(ed);
		}

//		System.out.println("更改完后ValueEdges的规模：" + graph.getAllValueEdges().size());
//		System.out.println(graph.getAllValueEdges());

		changeValueEdgesFlag = (changeValueEdgesFlag == true) ? false : true;

	}
	
	// 替换x个，删y个
	public static void changeTypedNodes(TypedGraph graph, int x, int y) {
		
//		System.out.println("TypedNodes开始前的TypedEdges: " + graph.getAllTypedEdges().size());
//		System.out.println(graph.getAllTypedEdges());
//		
//		System.out.println("TypedNodes开始前的ValueEdges: " + graph.getAllValueEdges().size());
//		System.out.println(graph.getAllValueEdges());
		
		int replaceStart = 0, replaceEnd = 0;
		int deleteStart = 0, deleteEnd = 0;

		int allSize = graph.getAllTypedNodes().size();
		int quarterSize = allSize / 4;
		int halfSize = allSize / 2;
		int threeQuarterSize = quarterSize + halfSize;

		if (changeTypedNodesFlag == true) {
			replaceStart = 0;
			replaceEnd = (x >= quarterSize) ? (quarterSize - 1) : (x - 1);
			deleteStart = quarterSize;
			deleteEnd = (deleteStart + y) >= halfSize ? (halfSize - 1) : (deleteStart + y - 1);
		} else {
			replaceStart = halfSize;
			replaceEnd = (x >= quarterSize) ? (threeQuarterSize - 1) : (replaceStart + x - 1);
			deleteStart = threeQuarterSize;
			deleteEnd = (deleteStart + y) >= allSize ? (allSize - 1) : (deleteStart + y - 1);
		}

		// 替换
		TypeNode typeD = graph.getTypeGraph().getTypeNode("D");
		for (int i = replaceStart; i <= replaceEnd; i++) {
			TypedNode n = new TypedNode();
			n.setType(typeD); // 可以更改策略
			graph.replaceWith(graph.getAllTypedNodes().get(i), n);
		}

		// 删除
		HashMap<Integer, TypedNode> map = new HashMap<>();
		for (int i = deleteStart; i <= deleteEnd; i++) {
			TypedNode nd = graph.getAllTypedNodes().get(i);
			map.put(i, nd); // 先保存下来
		}
		for (int i = deleteStart; i <= deleteEnd; i++) {
			TypedNode nd = map.get(i);
			graph.remove(nd);
		}

//		System.out.println("更改完TypedNodes后TypedNodes的规模：" + graph.getAllTypedNodes().size());
//		System.out.println(graph.getAllTypedNodes());
//		System.out.println("更改完TypedNodes后TypedEdges的规模：" + graph.getAllTypedEdges().size());
//		System.out.println(graph.getAllTypedEdges());
//		System.out.println("更改完TypedNodes后ValueEdges的规模：" + graph.getAllValueEdges().size());
//		System.out.println(graph.getAllValueNodes());

		changeTypedNodesFlag = (changeTypedNodesFlag == true) ? false : true;

	}
	
	public static void addTypedNodesAndTypedEdges(TypedGraph graph, int z) {

		if (addTypedEdgesFlag == true) {
			for (int i = 0; i < z; i++) {
				graph.declare("a1:A;" + "b1:B;" + "a1-a2b->b1;"); // 新加的全是a2b
			}
		} else {
			for (int i = 0; i < z; i++) {
				graph.declare("c1:C;" + "d1:D;" + "c1-c2d->d1;"); // 新加的全是c2d
			}
		}

//		System.out.println("添加TypedEdges后的TypedEdges的规模：" + graph.getAllTypedEdges().size());
//		System.out.println("添加TypedEdges后的TypedNodes的规模：" + graph.getAllTypedNodes().size());

		addTypedEdgesFlag = (addTypedEdgesFlag == true) ? false : true;

	}
	
	public static void addNodesAndValueEdges(TypedGraph graph, int z) {

		if (addValueEdgesFlag == true) {
			for (int i = 0; i < z; i++) {
				String str = getRandomString(5, true);
				graph.declare("c1:C;" + "c1.c2S=\""+str+"\";"); 
			}
		} else {
			for (int i = 0; i < z; i++) {
				String str = getRandomString(5, true);
				graph.declare("b1:B;" + "b1.b2S=\""+str+"\";"); 
			}
		}

//		System.out.println("添加ValueEdges后的ValueEdges的规模：" + graph.getAllValueEdges().size());
//		System.out.println("添加ValueEdges后的TypedNodes的规模：" + graph.getAllTypedNodes().size());
//		System.out.println("添加ValueEdges后的ValueNodes的规模：" + graph.getAllValueNodes().size());

		addValueEdgesFlag = (addValueEdgesFlag == true) ? false : true;

	}
	
	/** 生成任意长度的随机字符串，全字母或全数字 */
	public static String getRandomString(int length, boolean letter) {
		String str = null;
		int len = 0;
		if (letter) {
			str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
			len = str.length();
		} else {
			str = "0123456789";
			len = str.length();
		}

		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int num = random.nextInt(len);
			sb.append(str.charAt(num));
		}
		return sb.toString();
	}

}
