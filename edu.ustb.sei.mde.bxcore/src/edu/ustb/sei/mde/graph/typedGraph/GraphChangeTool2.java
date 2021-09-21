package edu.ustb.sei.mde.graph.typedGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.swing.text.ChangedCharSetException;

import edu.ustb.sei.mde.graph.type.DataTypeNode;
import edu.ustb.sei.mde.graph.type.PropertyEdge;
import edu.ustb.sei.mde.graph.type.TypeEdge;
import edu.ustb.sei.mde.graph.type.TypeNode;

public class GraphChangeTool2 {
	static boolean deleteTypedNodesFlag = true;
	static boolean changeEdgesFlag = true;
	static int sizeTypedEdges = 0;
	static int sizeValueEdges = 0;
	
	/** ���Address-Person��Person-name��Person-tel*/
	public static void addNodesAndEdges(TypedGraph graph, int z) {
		
		sizeTypedEdges = graph.getAllTypedEdges().size();
		sizeValueEdges = graph.getAllValueEdges().size();

		List<PropertyEdge> allPropertyEdges = graph.getTypeGraph().getAllPropertyEdges();

		for (int i = 0; i < z; i++) {
			graph.declare("a:AddressBook;" + "p:Person;" + "a-persons->p;");

			TypedNode personNode = graph.getAllTypedNodes().get(graph.getAllTypedNodes().size() - 1);

			String name = getRandomString(5, true);
			String tel = getRandomString(5, false);

			// ����ValueNode
			ValueNode v0 = graph.getAllValueNodes().get(0); // û��ֱ��new ValueNode()����
			
			ValueNode valueNodeName = v0.createConstantNode(name, v0.getType());
			graph.addValueNode(valueNodeName);
			
			ValueNode valueNodeTel = v0.createConstantNode(tel, v0.getType());
			graph.addValueNode(valueNodeTel);
		
			
			// ����ValueEdge
			ValueEdge valueEdgeName = new ValueEdge();
			valueEdgeName.setType(allPropertyEdges.get(0));
			valueEdgeName.setSource(personNode);
			valueEdgeName.setTarget(valueNodeName);
			graph.addValueEdge(valueEdgeName);
			
			ValueEdge valueEdgeTel = new ValueEdge();
			valueEdgeTel.setType(allPropertyEdges.get(1));
			valueEdgeTel.setSource(personNode);
			valueEdgeTel.setTarget(valueNodeTel);
			graph.addValueEdge(valueEdgeTel);
			
		
		}
	}
	
	/** �ı�y��TypedEdge��source�Լ�y��ValueEdge��target(name)�����ı�quarterSize���� */
	public static void changeEdges(TypedGraph graph, int y) {

		int exchangeStart = 0, exchangeEnd = 0;
		int quarterSize = sizeTypedEdges / 4;
		int halfSize = sizeTypedEdges / 2;
		int threeQuarterSize = quarterSize + halfSize;

		if (changeEdgesFlag == true) {
			exchangeStart = 0;
			exchangeEnd = (y >= quarterSize) ? (quarterSize - 1) : (y - 1);
			
			// ��ValueEdge��target
			List<PropertyEdge> allPropertyEdges = graph.getTypeGraph().getAllPropertyEdges();
			PropertyEdge propertyEdgeName = allPropertyEdges.get(0);
			for (int i = exchangeStart; i <= exchangeEnd; i++) {
				ValueEdge er = graph.getAllValueEdges().get(i);
				if (er.getType() == propertyEdgeName) {
					ValueEdge e = new ValueEdge();
					e.setType(er.getType());
					e.setSource(er.getSource());
					// �������ValueNode
					ValueNode v0 = graph.getAllValueNodes().get(0); // û��ֱ��new ValueNode()����
					ValueNode v = v0.createConstantNode("replace_name", v0.getType());
					e.setTarget(v);
					// ��er�滻Ϊe
					graph.replaceWith(er, e);
				}
			}
			
		} else {
			exchangeStart = halfSize;
			exchangeEnd = (y >= quarterSize) ? (threeQuarterSize - 1) : (exchangeStart + y - 1);
			
			// ��TypedEdge��source
			TypedNode typedNode = graph.getAllTypedNodes().get(graph.getAllTypedNodes().size() - 2);
			TypeNode typeAddressBook = graph.getTypeGraph().getTypeNode("AddressBook");
			if (typedNode.getType() == typeAddressBook) {
				for (int i = exchangeStart; i <= exchangeEnd; i++) {
					TypedEdge er = graph.getAllTypedEdges().get(i);
					TypedEdge e = new TypedEdge();
					e.setType(er.getType());
					e.setSource(typedNode);
					e.setTarget(er.getTarget());
					// ��er�滻Ϊe
					graph.replaceWith(er, e);								
				}
			}
		}

		changeEdgesFlag = !changeEdgesFlag;

	}

	
	/** ɾ��y��TypedNodes�����ɾ��quarterSize���� */
	public static void deleteTypedNodes(TypedGraph graph, int y) {

		int deleteStart = 0, deleteEnd = 0;

		int quarterSize = sizeTypedEdges / 4;
		int halfSize = sizeTypedEdges / 2;
		int threeQuarterSize = quarterSize + halfSize;

		if (deleteTypedNodesFlag == true) {
			deleteStart = quarterSize;
			deleteEnd = (deleteStart + y) >= halfSize ? (halfSize - 1) : (deleteStart + y - 1);
		} else {
			deleteStart = threeQuarterSize;
			deleteEnd = (deleteStart + y) >= sizeTypedEdges ? (sizeTypedEdges - 1) : (deleteStart + y - 1);
		}

		TypeNode typePerson = graph.getTypeGraph().getTypeNode("Person");
		ArrayList<TypedNode> removeList = new ArrayList<>();
		for (int i = deleteStart; i <= deleteEnd; i++) {
			TypedNode nd = graph.getAllTypedNodes().get(i);
			/**
			 * ��Ϊɾ��Address�Ļ���Person�Ტ�У�EMF Compare��ʶ����¼� ������ֱ��ֻɾ��Person���ͽڵ���
			 * ��Personɾ���Ļ������ӻ������µ�ValueNodeҲ��ɾ���� Ϊ�˱�֤�ϲ�ʱ����һ�£�������ҲҪɾ�����µ�ValueNode
			 * ��ͼ�ϲ��㷨ֻ���¼ӵ�ValueNode ���Կ������������ϲ����Ʋ�̫һ�� EMF Compare���ʶ���ValueNode��ɾ��
			 */
			if (nd.getType() == typePerson) {
				removeList.add(nd);
			}
		}
		
		if(removeList.size() > 0) {
			removeList.forEach(e -> graph.remove(e));
		}
		
		deleteTypedNodesFlag = !deleteTypedNodesFlag;

	}

	/** �������ⳤ�ȵ�����ַ�����ȫ��ĸ��ȫ���� */
	public static String getRandomString(int length, boolean letter) {
		String str = null;
		int len = 0;
		if (letter) {
			str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
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
