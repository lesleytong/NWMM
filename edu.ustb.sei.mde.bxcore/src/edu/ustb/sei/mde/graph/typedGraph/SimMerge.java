package edu.ustb.sei.mde.graph.typedGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.graph.type.PropertyEdge;
import edu.ustb.sei.mde.graph.type.TypeEdge;
import edu.ustb.sei.mde.graph.type.TypeNode;
import edu.ustb.sei.mde.structure.Tuple2;

/**
 * ���û��������Ե�ƥ��
 * 
 * @author 10242
 *
 */
public class SimMerge {

	/** PENDING: ����кϲ� */

	/** ��·�ϲ� */
	public static TypedGraph nWayMerge(TypedGraph baseGraph, TypedGraph... branchGraphs)
			throws NothingReturnedException {

		TypedGraph resultGraph = new TypedGraph(baseGraph.typeGraph);
		
		Map<TypedNode, TypedNode> globalMap = new HashMap<>();	// baseNode��resultNode�Ķ�Ӧ��ϵ

		/** ���ݻ���ͼ���ų�һ�������ڵ��˳�� */
		LinkedList<TypeNode> topologyList = topology(baseGraph);

		/** ˳���ų�������Ҫ��baseGraph�е�TypedNode���ࡣ������Ҫ��α���allTypedNodes */
		Map<TypeNode, ArrayList<TypedNode>> nodeMap = new HashMap<>();
		for (TypedNode baseNode : baseGraph.allTypedNodes) {
			TypeNode baseNodeType = baseNode.getType();
			if (nodeMap.get(baseNodeType) == null) {
				ArrayList<TypedNode> list = new ArrayList<>();
				list.add(baseNode);
				nodeMap.put(baseNodeType, list);
			} else {
				nodeMap.get(baseNodeType).add(baseNode);
			}
		}

		/** �������ͼ��TypedNode�ڵ� */
		HashMap<TypedNode, ArrayList<TypedNode>> matchTypedNodeMap = new HashMap<>();	// �������ȫ��		
		// ���ձ����ڵ��˳�򣬽���ƥ�乤��
		for (TypeNode typeNode : topologyList) {
			ArrayList<TypedNode> baseTypedNodesList = nodeMap.get(typeNode);

			// ����getOutgoingEdges������ΪbaseNode�Ÿ�����������ṹ�ٵ���ƥ��
			for (int k = 1; k < baseTypedNodesList.size(); k++) {
				for (int j = 0; j < baseTypedNodesList.size() - k; j++) {
					int size1 = baseGraph.getOutgoingEdges(baseTypedNodesList.get(j)).size();
					int size2 = baseGraph.getOutgoingEdges(baseTypedNodesList.get(j + 1)).size();
					if (size1 < size2) {
						// ����λ��
						TypedNode tmp = baseTypedNodesList.get(j + 1);
						baseTypedNodesList.remove(j + 1);
						baseTypedNodesList.add(j, tmp);
					}
				}

			}
			
			// ���ڴ����͵�ÿһ��baseNode�ڵ�
			for (TypedNode baseNode : baseTypedNodesList) {
				
				boolean fla = false;
				int count = 0; // Ϊ���ж��Ƿ���ӵ����ͼ
				ArrayList<TypedNode> matchNodeList = new ArrayList<>(); // ���ڼ�¼��baseNode�����з�֧ͼƥ����
				Map<PropertyEdge, ValueNode> updateMap = new HashMap<>(); // ���ڼ�ⶼ�޸ĳ�ͻ

				// ������֧ͼ����ƥ��
				for (int i = 0; i < branchGraphs.length; i++) {
					// ���ڴ˷�֧ͼ������TypedNode�ڵ�
					int maxWeight = 0;
					TypedNode matchNode = null;
					for (TypedNode branchNode : branchGraphs[i].allTypedNodes) {
						
						// ֻ����ͬ���͵Ľڵ�
						if (baseNode.getType() == branchNode.getType()) {
														
							// ����˷�֧�ڵ��ѱ�ƥ�䣬������
							boolean flag = false;
							for (Map.Entry<TypedNode, ArrayList<TypedNode>> entry : matchTypedNodeMap.entrySet()) {
								if(entry.getKey().getType() == branchNode.getType()) {
									if(entry.getValue().get(i) == branchNode) {
										flag = true;
										break;
									}
								}
							}
							if(flag == true) {
								continue;
							}
							
							int currentWeight = 0;
							// ���ȿ����Ա�
							currentWeight += valueEdgeScore(baseNode, branchNode, baseGraph, branchGraphs[i]);
							// ��ο������ߣ���incomingEdgeMap��outgoingEdgeMap
							List<TypedEdge> baseIncomingList = baseGraph.getIncomingEdges(baseNode);
							List<TypedEdge> branchIncomingList = branchGraphs[i].getIncomingEdges(branchNode);
							// ���ṹ���Ǹ��ڵ�ֻ��һ�����ڵ�
							if (baseIncomingList.size() != 0 && branchIncomingList.size() != 0) {
								TypedNode baseSource = baseIncomingList.get(0).getSource();
								TypedNode branchSource = branchIncomingList.get(0).getSource();										
								if(matchTypedNodeMap.get(baseSource).get(i) == branchSource) {
									currentWeight++;
								}								
							}
							// �ݹ鷽��
							currentWeight += typedEdgeScore(baseNode, branchNode, baseGraph, branchGraphs[i]);
							System.out.println("*****currentWeight: " + currentWeight);

							if (currentWeight > maxWeight) {
								maxWeight = currentWeight;
								matchNode = branchNode;
							}

						}
					} // ��baseNode��˷�֧ͼ�е�����ͬ���͵�branchNode����

					if(matchNode == null) {
						matchNodeList.add(null);	// �����±��Ƕ�Ӧ��֧ͼ��ƥ��ڵ�
					}else {																		
						// ���˷�֧ͼƥ���ϵĽڵ���ӵ�baseNode���б�
						matchNodeList.add(matchNode);
					}
												
				} // ���з�֧ͼ��TypedNode
				
				// TypedNodeû���޸�״̬
				matchTypedNodeMap.put(baseNode, matchNodeList);
				if(matchNodeList.contains(null) == false) {
					TypedNode resultNode = new TypedNode();                          
					resultNode.setType(baseNode.getType());
					resultGraph.addTypedNode(resultNode);
					globalMap.put(baseNode, resultNode);
				}
				
			} // ����ͼ���д����͵�TypedNode
			
		}	// ����ͼ�������͵�TypedNode
		
	
		/** �������ͼԭ�еġ���֧ͼ�¼ӵ�ValueNode */
		baseGraph.allValueNodes.parallelStream().forEach(v -> {
			resultGraph.addValueNode(v);
		});
		for (TypedGraph branchGraph : branchGraphs) {
			branchGraph.allValueNodes.parallelStream().forEach(v -> {
				resultGraph.addValueNode(v);
			});
		}
				
		/** �������ͼ��TypedEdge */
		for(TypedEdge baseEdge : baseGraph.allTypedEdges) {			
			
			STATUS preStatus = STATUS.UNMATCH;
			
			for(int i=0; i<branchGraphs.length; i++) {				
				
				boolean flag = false;	// Ϊ�˼��ɾ��
				
				for(TypedEdge branchEdge : branchGraphs[i].allTypedEdges) {					
					if(baseEdge.getType() == branchEdge.getType()) {
						TypedNode baseSource = baseEdge.getSource();
						TypedNode baseTarget = baseEdge.getTarget();
						TypedNode branchSource = branchEdge.getSource();
						TypedNode branchTarget = branchEdge.getTarget();
						if(matchTypedNodeMap.get(baseSource).get(i) == branchSource 
								&& matchTypedNodeMap.get(baseTarget).get(i) == branchTarget) {
							flag = true;
							if(preStatus == STATUS.UNMATCH) {
								preStatus = STATUS.UNCHANGE;	// TypedEdgeû���޸ĵ�״̬��
																// ע��DELETE���ᵽUNCHANGE
							}
						}						
					}
					
				}	// �˷�֧ͼ������TypedEdge		
				
				if(flag == false) {
					preStatus = STATUS.DELETE;
				}
				
			}	// ���з�֧ͼ������TypedEdge			
			
			if(preStatus != STATUS.DELETE) {
				TypedEdge resultEdge = new TypedEdge();
				resultEdge.setType(baseEdge.getType());
				resultEdge.setSource(globalMap.get(baseEdge.getSource()));
				resultEdge.setTarget(globalMap.get(baseEdge.getTarget()));
				resultGraph.simAddTypedEdge(resultEdge);	// ���ڻ���ͼ�Ĺ�������simply add
			}
			
		}	// ����ͼ������TypedEdge
		
		/** �������ͼ��ValueEdge */
		for(ValueEdge baseEdge : baseGraph.allValueEdges) {
						
			STATUS preStatus = STATUS.UNMATCH;
			ValueNode updateNode = null;
			
			for(int i=0; i<branchGraphs.length; i++) {
				boolean flag = false;	// Ϊ�˼��ɾ���ͳ�ͻ
				for(ValueEdge branchEdge : branchGraphs[i].allValueEdges) {
					
					if(baseEdge.getType() == branchEdge.getType()) {
						TypedNode baseSource = baseEdge.getSource();
						TypedNode branchSource = branchEdge.getSource();
						if(matchTypedNodeMap.get(baseSource).get(i) == branchSource) {
							flag = true;
							ValueNode baseTarget = baseEdge.getTarget();
							ValueNode branchTarget = branchEdge.getTarget();
							if(baseTarget.toString().equals(branchTarget.toString())) {
								if(preStatus == STATUS.UNMATCH) {
									preStatus = STATUS.UNCHANGE;
								}
							} else {
								// ��ɾ���޸ĳ�ͻ
								if(preStatus == STATUS.DELETE) {
									throw new NothingReturnedException(baseEdge + "����ɾ��-�޸ĳ�ͻ");
								}
								// ���޸ĳ�ͻ
								if(preStatus == STATUS.UPDATE 
										&& updateNode.toString().equals(branchTarget.toString())==false) {
									throw new NothingReturnedException(baseEdge + "���ڶ��޸ĳ�ͻ");
								}
								preStatus = STATUS.UPDATE;
								updateNode = branchTarget;
							}
						}
					}										
				}	// �˷�֧ͼ������ValueEdge
				if(preStatus == STATUS.UPDATE && flag == false) {
					throw new NothingReturnedException(baseEdge + "����ɾ��-�޸ĳ�ͻ");
				}				
				if(flag == false) {
					preStatus = STATUS.DELETE;
				}
											
			}	// ���з�֧ͼ������ValueEdge
				
			// �ǵ���ӵ����ͼ��
			if(preStatus == STATUS.UNCHANGE) {
				ValueEdge resultEdge = new ValueEdge();
				resultEdge.setType(baseEdge.getType());
				resultEdge.setSource(globalMap.get(baseEdge.getSource()));
				resultEdge.setTarget(baseEdge.getTarget());
				resultGraph.simAddValueEdge(resultEdge);	// ���ڻ���ͼ�����Աߣ���simply add
			}else if(preStatus == STATUS.UPDATE) {
				ValueEdge resultEdge = new ValueEdge();
				resultEdge.setType(baseEdge.getType());
				resultEdge.setSource(globalMap.get(baseEdge.getSource()));
				resultEdge.setTarget(updateNode);
				resultGraph.simAddValueEdge(resultEdge);	// ���ڻ���ͼ�����Աߣ���simply add
			}
			
		}	// ����ͼ������ValueEdge
		
		
		
		/** ���ڷ�֧ͼ�¼ӵ�TypedNode */
		for(int i=0; i<branchGraphs.length; i++) {
			for(TypedNode typedNode : branchGraphs[i].allTypedNodes) {
				
				
				
			}
		}
		
		
		
		
		
		return resultGraph;
	}

	/** �������Աߵ�target�Ƿ���� 
	 * @throws NothingReturnedException */
	private static boolean TargetUnchange(TypedNode baseNode, TypedNode branchNode, TypedGraph baseGraph,
			TypedGraph branchGraph, Map<PropertyEdge, ValueNode> updateMap) throws NothingReturnedException {
		boolean flag = true;
		List<ValueEdge> baseValueEdgeList = baseGraph.getValueEdges(baseNode);
		List<ValueEdge> branchValueEdgeList = branchGraph.getValueEdges(branchNode);
		for (ValueEdge baseEdge : baseValueEdgeList) {
			PropertyEdge baseType = baseEdge.getType();
			for (ValueEdge branchEdge : branchValueEdgeList) {
				PropertyEdge branchType = branchEdge.getType();
				if (baseType == branchType) {
					ValueNode baseTarget = baseEdge.getTarget();
					ValueNode branchTarget = branchEdge.getTarget();
					if (baseTarget.toString().equals(branchTarget.toString())) {
						continue;
					} else {
						ValueNode valueNode = updateMap.get(baseType);
						if(valueNode == null) {
							updateMap.put(baseType, branchTarget);													
						}else if(branchTarget.toString().equals(valueNode.toString()) == false) {
							throw new NothingReturnedException(baseNode + "���ڶ��޸ĳ�ͻ");
						}else {
							
						}						
						flag = false;
					}
				}
			}
		}
		return flag;
	}

	/** ��������ߵļӷ� */
	private static int typedEdgeScore(TypedNode baseNode, TypedNode branchNode, TypedGraph baseGraph,
			TypedGraph branchGraph) {
		int score = 0;
		List<TypedEdge> baseOutgoingList = baseGraph.getOutgoingEdges(baseNode);
		List<TypedEdge> branchOutgoingList = branchGraph.getOutgoingEdges(branchNode);
		for (TypedEdge baseEdge : baseOutgoingList) {
			TypeEdge baseType = baseEdge.getType();
			TypedNode baseTarget = baseEdge.getTarget();
			for (TypedEdge branchEdge : branchOutgoingList) {
				TypeEdge branchType = branchEdge.getType();
				TypedNode branchTarget = branchEdge.getTarget();
				if (baseType == branchType) {
					// �ȿ����Ա�
					score += valueEdgeScore(baseTarget, branchTarget, baseGraph, branchGraph);
					// �ٿ������ߣ����˵ݹ�
					score += typedEdgeScore(baseTarget, branchTarget, baseGraph, branchGraph);
				}
			}
		}
		return score;
	}

	/** �������Աߵļӷ� */
	private static int valueEdgeScore(TypedNode baseNode, TypedNode branchNode, TypedGraph baseGraph,
			TypedGraph branchGraph) {
		int score = 0;
		List<ValueEdge> baseValueEdgeList = baseGraph.getValueEdges(baseNode);
		List<ValueEdge> branchValueEdgeList = branchGraph.getValueEdges(branchNode);
		for (ValueEdge baseEdge : baseValueEdgeList) {
			PropertyEdge baseType = baseEdge.getType();
			ValueNode baseTarget = baseEdge.getTarget();
			for (ValueEdge branchEdge : branchValueEdgeList) {
				PropertyEdge branchType = branchEdge.getType();
				ValueNode branchTarget = branchEdge.getTarget();
				// ֻ��type��target��һ���żӷ�
				if (baseType == branchType && baseTarget.toString().equals(branchTarget.toString())) {
					score++;
				}
			}
		}
		return score;
	}

	/** ���ݻ���ͼ���ų�һ�������ڵ��˳�� */
	private static LinkedList<TypeNode> topology(TypedGraph baseGraph) {

		List<Tuple2<TypeNode, TypeNode>> tupleList = new ArrayList<>();

		for (TypedEdge baseEdge : baseGraph.allTypedEdges) {
			TypeNode sourceType = baseEdge.getSource().getType();
			TypeNode targetType = baseEdge.getTarget().getType();
			int i = 0;
			for (; i < tupleList.size(); i++) {

				if (sourceType == tupleList.get(i).first && targetType == tupleList.get(i).second) {
					break;
				}
			}
			if (i == tupleList.size()) {
				tupleList.add(new Tuple2<>(sourceType, targetType));
			}
		}

		// ����tupleList��һ����������˳��
		HashMap<TypeNode, Integer> inDegreeMap = new HashMap<>();
		for (Tuple2<TypeNode, TypeNode> tuple : tupleList) {
			inDegreeMap.put(tuple.second, 1); // ���ṹ���������Ϊ1 --- ����������ṹ������Ҫ�ģ���
		}
		LinkedList<TypeNode> topologyList = new LinkedList<>();
		for (Tuple2<TypeNode, TypeNode> tuple : tupleList) {
			if (inDegreeMap.get(tuple.first) == null) {
				// �Ѵ������б��л���Ҫ�ж�
				if (topologyList.contains(tuple.first) == false) {
					topologyList.addLast(tuple.first);
				}
				inDegreeMap.remove(tuple.second); // �����������Ϊ1������ֱ��ɾ��Ҳ��
			}
		}
		// δ��Ϊtuple.first�ļ���topologyList
		for (Tuple2<TypeNode, TypeNode> tuple : tupleList) {
			if (topologyList.contains(tuple.second) == false) {
				topologyList.addLast(tuple.second);
			}
		}
		// ���е�����TypedNode��
		for (TypedNode baseNode : baseGraph.allTypedNodes) {
			TypeNode typeNode = baseNode.getType();
			if (topologyList.contains(typeNode) == false) {
				topologyList.addLast(typeNode);
			}
		}

		return topologyList;

	}

	// ����༭����
	private static int editionDistance(String str1, String str2) {

		int dc = 1; // ɾ������
		int rc = 1; // �滻����
		int ic = 1; // �������

		if (str1 == null || str2 == null) {
			return 0;
		}
		char[] chs1 = str1.toCharArray();
		char[] chs2 = str2.toCharArray();
		int row = chs1.length + 1;
		int col = chs2.length + 1;
		int[][] dp = new int[row][col];
		for (int i = 1; i < row; i++) {
			dp[i][0] = dc * i;
		}
		for (int j = 1; j < col; j++) {
			dp[0][j] = ic * j;
		}
		for (int i = 1; i < row; i++) {
			for (int j = 1; j < col; j++) {
				if (chs1[i - 1] == chs2[j - 1]) {
					dp[i][j] = dp[i - 1][j - 1];
				} else {
					dp[i][j] = dp[i - 1][j - 1] + rc;
				}
				dp[i][j] = Math.min(dp[i][j], dp[i][j - 1] + ic);
				dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + dc);
			}
		}
		return dp[row - 1][col - 1];
	}

}
