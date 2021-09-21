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

	// ������ɾy��
	public static void changeTypedEdges(TypedGraph graph, int x, int y) {

		int exchangeStart = 0, exchangeEnd = 0;
		int deleteStart = 0, deleteEnd = 0;

		int allSize = graph.getAllTypedEdges().size();
		int quarterSize = allSize / 4;
		int halfSize = allSize / 2;
		int threeQuarterSize = quarterSize + halfSize;

		if (changeTypedEdgesFlag == true) {
			exchangeStart = 0;
			exchangeEnd = (x >= quarterSize) ? (quarterSize - 1) : x; // �޸�Ϊx�ˣ���Ϊ�����'���'x
			deleteStart = quarterSize;
			deleteEnd = (deleteStart + y) >= halfSize ? (halfSize - 1) : (deleteStart + y - 1);
		} else {
			exchangeStart = halfSize;
			exchangeEnd = (x >= quarterSize) ? (threeQuarterSize - 1) : (exchangeStart + x);
			deleteStart = threeQuarterSize;
			deleteEnd = (deleteStart + y) >= allSize ? (allSize - 1) : (deleteStart + y - 1);
		}

		if (exchangeEnd > exchangeStart) {
			// ����һ���򣬿��ԸĲ���
			TypedEdge e = graph.getAllTypedEdges().get(exchangeEnd);
			graph.getAllTypedEdges().remove(e);
			graph.getAllTypedEdges().add(exchangeStart, e);
		}

		// ɾ��
		HashMap<Integer, TypedEdge> map = new HashMap<>();
		for (int i = deleteStart; i <= deleteEnd; i++) {
			TypedEdge ed = graph.getAllTypedEdges().get(i);
			map.put(i, ed); // �ȱ�������
		}
		for (int i = deleteStart; i <= deleteEnd; i++) {
			TypedEdge ed = map.get(i);
			graph.remove(ed);
		}

		// �滻type������Ļ����ͱ�֤�滻�ߵ������˵���ԭtype���ϣ��ı�source��target��
		if (changeTypedEdgesFlag == true) {
			// �ҵ���һ������Ϊa2b�ı�e�����û�оͲ������滻
			TypeEdge type = graph.getTypeGraph().getTypeEdge(graph.getTypeGraph().getTypeNode("A"), "a2b");
			TypedEdge e = new TypedEdge();
			TypedEdge er = null;	
			boolean helpFlag = false;
			for (int i = exchangeStart; i < exchangeEnd; i++) {
				er = graph.getAllTypedEdges().get(i);	// ���滻�ı�
				if (er.getType() == type) {
					e.setType(type);
					e.setSource(er.getSource());
					helpFlag = true;
					break;
				}
			}
			
			if(helpFlag == true) {
				// �ҵ���һ��û��incomingEdge��B���͵Ľڵ�b
				TypeNode typeB = graph.getTypeGraph().getTypeNode("B");
				for (int i = exchangeStart; i < exchangeEnd; i++) {
					TypedNode n = graph.getAllTypedNodes().get(i);
					if (n.getType() == typeB && graph.getIncomingEdges(n).size() == 0) {
						// ����e��target��n
						e.setTarget(n);
						// ��er�滻Ϊe
						graph.replaceWith(er, e);
						break;
					}
				}
			}

		} else {
			// �ҵ���һ������Ϊc2d�ı�e�����û�оͲ������滻
			TypeEdge type = graph.getTypeGraph().getTypeEdge(graph.getTypeGraph().getTypeNode("C"), "c2d");
			TypedEdge e = new TypedEdge();
			TypedEdge er = null;	
			boolean helpFlag = false;
			for (int i = exchangeStart; i < exchangeEnd; i++) {
				er = graph.getAllTypedEdges().get(i);	// ���滻�ı�
				if (er.getType() == type) {
					e.setType(type);
					e.setSource(er.getSource());
					helpFlag = true;
					break;
				}
			}
			
			if(helpFlag == true) {
				// �ҵ���һ��û��incomingEdge��D���͵Ľڵ�d
				TypeNode typeD = graph.getTypeGraph().getTypeNode("D");
				for (int i = exchangeStart; i < exchangeEnd; i++) {
					TypedNode n = graph.getAllTypedNodes().get(i);
					if (n.getType() == typeD && graph.getIncomingEdges(n).size() == 0) {
						// ����e��target��n
						e.setTarget(n);
						// ��er�滻Ϊe
						graph.replaceWith(er, e);
						break;
					}
				}
			}
		}

//		System.out.println("���ĺ�TypedEdges��ģ: " + graph.getAllTypedEdges().size());
//		System.out.println(graph.getAllTypedEdges());

		changeTypedEdgesFlag = (changeTypedEdgesFlag == true) ? false : true;

	}
	
	// ɾy��
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

		// ɾ��
		HashMap<Integer, ValueEdge> map = new HashMap<>();
		for (int i = deleteStart; i <= deleteEnd; i++) {
			ValueEdge ed = graph.getAllValueEdges().get(i);
			map.put(i, ed); // �ȱ�������
		}
		for (int i = deleteStart; i <= deleteEnd; i++) {
			ValueEdge ed = map.get(i);
			graph.remove(ed);
		}

//		System.out.println("�������ValueEdges�Ĺ�ģ��" + graph.getAllValueEdges().size());
//		System.out.println(graph.getAllValueEdges());

		changeValueEdgesFlag = (changeValueEdgesFlag == true) ? false : true;

	}
	
	// �滻x����ɾy��
	public static void changeTypedNodes(TypedGraph graph, int x, int y) {
		
//		System.out.println("TypedNodes��ʼǰ��TypedEdges: " + graph.getAllTypedEdges().size());
//		System.out.println(graph.getAllTypedEdges());
//		
//		System.out.println("TypedNodes��ʼǰ��ValueEdges: " + graph.getAllValueEdges().size());
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

		// �滻
		TypeNode typeD = graph.getTypeGraph().getTypeNode("D");
		for (int i = replaceStart; i <= replaceEnd; i++) {
			TypedNode n = new TypedNode();
			n.setType(typeD); // ���Ը��Ĳ���
			graph.replaceWith(graph.getAllTypedNodes().get(i), n);
		}

		// ɾ��
		HashMap<Integer, TypedNode> map = new HashMap<>();
		for (int i = deleteStart; i <= deleteEnd; i++) {
			TypedNode nd = graph.getAllTypedNodes().get(i);
			map.put(i, nd); // �ȱ�������
		}
		for (int i = deleteStart; i <= deleteEnd; i++) {
			TypedNode nd = map.get(i);
			graph.remove(nd);
		}

//		System.out.println("������TypedNodes��TypedNodes�Ĺ�ģ��" + graph.getAllTypedNodes().size());
//		System.out.println(graph.getAllTypedNodes());
//		System.out.println("������TypedNodes��TypedEdges�Ĺ�ģ��" + graph.getAllTypedEdges().size());
//		System.out.println(graph.getAllTypedEdges());
//		System.out.println("������TypedNodes��ValueEdges�Ĺ�ģ��" + graph.getAllValueEdges().size());
//		System.out.println(graph.getAllValueNodes());

		changeTypedNodesFlag = (changeTypedNodesFlag == true) ? false : true;

	}
	
	public static void addTypedNodesAndTypedEdges(TypedGraph graph, int z) {

		if (addTypedEdgesFlag == true) {
			for (int i = 0; i < z; i++) {
				graph.declare("a1:A;" + "b1:B;" + "a1-a2b->b1;"); // �¼ӵ�ȫ��a2b
			}
		} else {
			for (int i = 0; i < z; i++) {
				graph.declare("c1:C;" + "d1:D;" + "c1-c2d->d1;"); // �¼ӵ�ȫ��c2d
			}
		}

//		System.out.println("���TypedEdges���TypedEdges�Ĺ�ģ��" + graph.getAllTypedEdges().size());
//		System.out.println("���TypedEdges���TypedNodes�Ĺ�ģ��" + graph.getAllTypedNodes().size());

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

//		System.out.println("���ValueEdges���ValueEdges�Ĺ�ģ��" + graph.getAllValueEdges().size());
//		System.out.println("���ValueEdges���TypedNodes�Ĺ�ģ��" + graph.getAllTypedNodes().size());
//		System.out.println("���ValueEdges���ValueNodes�Ĺ�ģ��" + graph.getAllValueNodes().size());

		addValueEdgesFlag = (addValueEdgesFlag == true) ? false : true;

	}
	
	/** �������ⳤ�ȵ�����ַ�����ȫ��ĸ��ȫ���� */
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
