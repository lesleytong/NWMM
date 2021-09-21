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
 * 采用基于相似性的匹配
 * 
 * @author 10242
 *
 */
public class SimMerge {

	/** PENDING: 添加行合并 */

	/** 多路合并 */
	public static TypedGraph nWayMerge(TypedGraph baseGraph, TypedGraph... branchGraphs)
			throws NothingReturnedException {

		TypedGraph resultGraph = new TypedGraph(baseGraph.typeGraph);
		
		Map<TypedNode, TypedNode> globalMap = new HashMap<>();	// baseNode与resultNode的对应关系

		/** 根据基础图，排出一个遍历节点的顺序 */
		LinkedList<TypeNode> topologyList = topology(baseGraph);

		/** 顺序排出来后，需要把baseGraph中的TypedNode分类。否则，需要多次遍历allTypedNodes */
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

		/** 处理基础图的TypedNode节点 */
		HashMap<TypedNode, ArrayList<TypedNode>> matchTypedNodeMap = new HashMap<>();	// 必须放在全局		
		// 按照遍历节点的顺序，进行匹配工作
		for (TypeNode typeNode : topologyList) {
			ArrayList<TypedNode> baseTypedNodesList = nodeMap.get(typeNode);

			// 按照getOutgoingEdges个数再为baseNode排个序，这样避免结构少的先匹配
			for (int k = 1; k < baseTypedNodesList.size(); k++) {
				for (int j = 0; j < baseTypedNodesList.size() - k; j++) {
					int size1 = baseGraph.getOutgoingEdges(baseTypedNodesList.get(j)).size();
					int size2 = baseGraph.getOutgoingEdges(baseTypedNodesList.get(j + 1)).size();
					if (size1 < size2) {
						// 交换位置
						TypedNode tmp = baseTypedNodesList.get(j + 1);
						baseTypedNodesList.remove(j + 1);
						baseTypedNodesList.add(j, tmp);
					}
				}

			}
			
			// 对于此类型的每一个baseNode节点
			for (TypedNode baseNode : baseTypedNodesList) {
				
				boolean fla = false;
				int count = 0; // 为了判断是否添加到结果图
				ArrayList<TypedNode> matchNodeList = new ArrayList<>(); // 用于记录此baseNode和所有分支图匹配结果
				Map<PropertyEdge, ValueNode> updateMap = new HashMap<>(); // 用于检测都修改冲突

				// 与多个分支图进行匹配
				for (int i = 0; i < branchGraphs.length; i++) {
					// 对于此分支图的所有TypedNode节点
					int maxWeight = 0;
					TypedNode matchNode = null;
					for (TypedNode branchNode : branchGraphs[i].allTypedNodes) {
						
						// 只看相同类型的节点
						if (baseNode.getType() == branchNode.getType()) {
														
							// 如果此分支节点已被匹配，则跳过
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
							// 首先看属性边
							currentWeight += valueEdgeScore(baseNode, branchNode, baseGraph, branchGraphs[i]);
							// 其次看关联边，有incomingEdgeMap和outgoingEdgeMap
							List<TypedEdge> baseIncomingList = baseGraph.getIncomingEdges(baseNode);
							List<TypedEdge> branchIncomingList = branchGraphs[i].getIncomingEdges(branchNode);
							// 树结构，非根节点只有一个父节点
							if (baseIncomingList.size() != 0 && branchIncomingList.size() != 0) {
								TypedNode baseSource = baseIncomingList.get(0).getSource();
								TypedNode branchSource = branchIncomingList.get(0).getSource();										
								if(matchTypedNodeMap.get(baseSource).get(i) == branchSource) {
									currentWeight++;
								}								
							}
							// 递归方法
							currentWeight += typedEdgeScore(baseNode, branchNode, baseGraph, branchGraphs[i]);
							System.out.println("*****currentWeight: " + currentWeight);

							if (currentWeight > maxWeight) {
								maxWeight = currentWeight;
								matchNode = branchNode;
							}

						}
					} // 此baseNode与此分支图中的所有同类型的branchNode计算

					if(matchNode == null) {
						matchNodeList.add(null);	// 这样下标是对应分支图的匹配节点
					}else {																		
						// 将此分支图匹配上的节点添加到baseNode的列表
						matchNodeList.add(matchNode);
					}
												
				} // 所有分支图的TypedNode
				
				// TypedNode没有修改状态
				matchTypedNodeMap.put(baseNode, matchNodeList);
				if(matchNodeList.contains(null) == false) {
					TypedNode resultNode = new TypedNode();                          
					resultNode.setType(baseNode.getType());
					resultGraph.addTypedNode(resultNode);
					globalMap.put(baseNode, resultNode);
				}
				
			} // 基础图所有此类型的TypedNode
			
		}	// 基础图所有类型的TypedNode
		
	
		/** 处理基础图原有的、分支图新加的ValueNode */
		baseGraph.allValueNodes.parallelStream().forEach(v -> {
			resultGraph.addValueNode(v);
		});
		for (TypedGraph branchGraph : branchGraphs) {
			branchGraph.allValueNodes.parallelStream().forEach(v -> {
				resultGraph.addValueNode(v);
			});
		}
				
		/** 处理基础图的TypedEdge */
		for(TypedEdge baseEdge : baseGraph.allTypedEdges) {			
			
			STATUS preStatus = STATUS.UNMATCH;
			
			for(int i=0; i<branchGraphs.length; i++) {				
				
				boolean flag = false;	// 为了检测删除
				
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
								preStatus = STATUS.UNCHANGE;	// TypedEdge没有修改的状态；
																// 注意DELETE不会到UNCHANGE
							}
						}						
					}
					
				}	// 此分支图的所有TypedEdge		
				
				if(flag == false) {
					preStatus = STATUS.DELETE;
				}
				
			}	// 所有分支图的所有TypedEdge			
			
			if(preStatus != STATUS.DELETE) {
				TypedEdge resultEdge = new TypedEdge();
				resultEdge.setType(baseEdge.getType());
				resultEdge.setSource(globalMap.get(baseEdge.getSource()));
				resultEdge.setTarget(globalMap.get(baseEdge.getTarget()));
				resultGraph.simAddTypedEdge(resultEdge);	// 对于基础图的关联边用simply add
			}
			
		}	// 基础图的所有TypedEdge
		
		/** 处理基础图的ValueEdge */
		for(ValueEdge baseEdge : baseGraph.allValueEdges) {
						
			STATUS preStatus = STATUS.UNMATCH;
			ValueNode updateNode = null;
			
			for(int i=0; i<branchGraphs.length; i++) {
				boolean flag = false;	// 为了检测删除和冲突
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
								// 有删除修改冲突
								if(preStatus == STATUS.DELETE) {
									throw new NothingReturnedException(baseEdge + "存在删除-修改冲突");
								}
								// 都修改冲突
								if(preStatus == STATUS.UPDATE 
										&& updateNode.toString().equals(branchTarget.toString())==false) {
									throw new NothingReturnedException(baseEdge + "存在都修改冲突");
								}
								preStatus = STATUS.UPDATE;
								updateNode = branchTarget;
							}
						}
					}										
				}	// 此分支图的所有ValueEdge
				if(preStatus == STATUS.UPDATE && flag == false) {
					throw new NothingReturnedException(baseEdge + "存在删除-修改冲突");
				}				
				if(flag == false) {
					preStatus = STATUS.DELETE;
				}
											
			}	// 所有分支图的所有ValueEdge
				
			// 记得添加到结果图中
			if(preStatus == STATUS.UNCHANGE) {
				ValueEdge resultEdge = new ValueEdge();
				resultEdge.setType(baseEdge.getType());
				resultEdge.setSource(globalMap.get(baseEdge.getSource()));
				resultEdge.setTarget(baseEdge.getTarget());
				resultGraph.simAddValueEdge(resultEdge);	// 对于基础图的属性边，用simply add
			}else if(preStatus == STATUS.UPDATE) {
				ValueEdge resultEdge = new ValueEdge();
				resultEdge.setType(baseEdge.getType());
				resultEdge.setSource(globalMap.get(baseEdge.getSource()));
				resultEdge.setTarget(updateNode);
				resultGraph.simAddValueEdge(resultEdge);	// 对于基础图的属性边，用simply add
			}
			
		}	// 基础图的所有ValueEdge
		
		
		
		/** 对于分支图新加的TypedNode */
		for(int i=0; i<branchGraphs.length; i++) {
			for(TypedNode typedNode : branchGraphs[i].allTypedNodes) {
				
				
				
			}
		}
		
		
		
		
		
		return resultGraph;
	}

	/** 计算属性边的target是否变了 
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
							throw new NothingReturnedException(baseNode + "存在都修改冲突");
						}else {
							
						}						
						flag = false;
					}
				}
			}
		}
		return flag;
	}

	/** 计算关联边的加分 */
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
					// 先看属性边
					score += valueEdgeScore(baseTarget, branchTarget, baseGraph, branchGraph);
					// 再看关联边，用了递归
					score += typedEdgeScore(baseTarget, branchTarget, baseGraph, branchGraph);
				}
			}
		}
		return score;
	}

	/** 计算属性边的加分 */
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
				// 只有type和target都一样才加分
				if (baseType == branchType && baseTarget.toString().equals(branchTarget.toString())) {
					score++;
				}
			}
		}
		return score;
	}

	/** 根据基础图，排出一个遍历节点的顺序 */
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

		// 按照tupleList排一个遍历结点的顺序
		HashMap<TypeNode, Integer> inDegreeMap = new HashMap<>();
		for (Tuple2<TypeNode, TypeNode> tuple : tupleList) {
			inDegreeMap.put(tuple.second, 1); // 树结构，入度至多为1 --- 如果不是树结构这里需要改！！
		}
		LinkedList<TypeNode> topologyList = new LinkedList<>();
		for (Tuple2<TypeNode, TypeNode> tuple : tupleList) {
			if (inDegreeMap.get(tuple.first) == null) {
				// 已存在于列表中还需要判断
				if (topologyList.contains(tuple.first) == false) {
					topologyList.addLast(tuple.first);
				}
				inDegreeMap.remove(tuple.second); // 由于入度至多为1，这里直接删除也可
			}
		}
		// 未作为tuple.first的加入topologyList
		for (Tuple2<TypeNode, TypeNode> tuple : tupleList) {
			if (topologyList.contains(tuple.second) == false) {
				topologyList.addLast(tuple.second);
			}
		}
		// 还有单独的TypedNode点
		for (TypedNode baseNode : baseGraph.allTypedNodes) {
			TypeNode typeNode = baseNode.getType();
			if (topologyList.contains(typeNode) == false) {
				topologyList.addLast(typeNode);
			}
		}

		return topologyList;

	}

	// 计算编辑代价
	private static int editionDistance(String str1, String str2) {

		int dc = 1; // 删除代价
		int rc = 1; // 替换代价
		int ic = 1; // 插入代价

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
