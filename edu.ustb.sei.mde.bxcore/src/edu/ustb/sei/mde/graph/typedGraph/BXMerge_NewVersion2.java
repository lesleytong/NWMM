package edu.ustb.sei.mde.graph.typedGraph;
/**
 * 按照类型划分
 */
import java.io.ObjectOutputStream.PutField;
/**
 * 串行
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.naming.spi.DirStateFactory.Result;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.bxcore.structures.Index;
import edu.ustb.sei.mde.graph.type.DataTypeNode;
import edu.ustb.sei.mde.graph.type.PropertyEdge;
import edu.ustb.sei.mde.graph.type.TypeEdge;
import edu.ustb.sei.mde.graph.type.TypeNode;
import edu.ustb.sei.mde.graph.typedGraph.constraint.GraphConstraint;
import edu.ustb.sei.mde.structure.Tuple2;
import edu.ustb.sei.mde.structure.Tuple3;

public class BXMerge_NewVersion2 {

	public static TypedGraph prepare(TypedGraph baseGraph, TypedGraph... branchGraphs) throws NothingReturnedException {

		TypedGraph resultGraph = baseGraph.getCopy();

		// 得到TypedNodes的所有类型
		List<TypeNode> allTypeNodes = baseGraph.getTypeGraph().getAllTypeNodes();

		// 划分基础图的TypedNodes
		HashMap<TypeNode, ArrayList<TypedNode>> baseTypeNodeMap = new HashMap<>();
		for (int i = 0; i < allTypeNodes.size(); i++) {
			ArrayList<TypedNode> typedNodes = new ArrayList<>();
			baseTypeNodeMap.put(allTypeNodes.get(i), typedNodes);
		}
		for (TypedNode n : baseGraph.allTypedNodes) {
			baseTypeNodeMap.get(n.getType()).add(n);
		}

		// 划分所有分支图的TypedNodes
		HashMap<TypeNode, ArrayList<TypedNode>> branchTypeNodeMap = new HashMap<>();
		for (int i = 0; i < allTypeNodes.size(); i++) {
			ArrayList<TypedNode> typedNodes = new ArrayList<>();
			branchTypeNodeMap.put(allTypeNodes.get(i), typedNodes);
		}
		for (TypedGraph branchGraph : branchGraphs) {
			for (TypedNode n : branchGraph.allTypedNodes) {
				branchTypeNodeMap.get(n.getType()).add(n);
			}
		}

		// 新加TypedNodes
		Profiler.begin();
		for (int i = 0; i < allTypeNodes.size(); i++) {
			for (TypedNode n : branchTypeNodeMap.get(allTypeNodes.get(i))) {
				try {
					// 根据n的索引查找基本图中有无相应的对象，如果有则不做处理
					baseGraph.getElementByIndexObject(n.getIndex());
				} catch (NothingReturnedException e) { // 如果在基本图中没有找到相应的对象
					TypedNode rn = null;
					try {
						// 根据n的索引查找result图中有无相应的对象，如果有就赋值给rn
						rn = resultGraph.getElementByIndexObject(n.getIndex());
					} catch (NothingReturnedException e1) { // 如果result图中无相应的对象，则将n对象添加到result图的List<TypedNode>，并且令rn=null
						resultGraph.addTypedNode(n);
						rn = null;
					} finally {

						if (rn != null) { // 如果rn!=null，说明根据n的索引找到了result图中相应的对象，且赋值给了rn
							TypeNode lt = rn.getType();
							TypeNode rt = n.getType();
							TypeNode ct = baseGraph.getTypeGraph().computeSubtype(lt, rt); // 最终的类型

							if (ct == TypeNode.NULL_TYPE) {
								try {
									throw new NothingReturnedException(); // incompatible
								} catch (NothingReturnedException e2) {
									e2.printStackTrace();
								}
							} else {
								rn.setType(ct); // 若兼容，则rn的type设置为ct
								rn.mergeIndex(n);
								resultGraph.reindexing(rn); // lyt-修改
							}
						}
					}
				}
			}
		}
		System.out.println("*****新加TypedNodes: " + Profiler.end() + "ms");

		// 新加ValueNodes
		Profiler.begin();
		for (TypedGraph branchGraph : branchGraphs) {
			branchGraph.allValueNodes.forEach(v -> {
				resultGraph.addValueNode(v);
			});
		}
		System.out.println("*******新加ValueNodes：" + Profiler.end() + "ms");

		// 删除和修改TypedNodes，暂时不处理相邻的边。
		Profiler.begin();
		ArrayList<TypedNode> deleteTypedNodesList = new ArrayList<>();
		HashMap<TypedNode, TypedNode> changeTypedNodesMap = new HashMap<>();
		for (int i = 0; i < allTypeNodes.size(); i++) {
			TypeNode[] nodeImages = new TypeNode[branchGraphs.length];
			for (TypedNode baseNode : baseTypeNodeMap.get(allTypeNodes.get(i))) {
				for (int j = 0; j < branchGraphs.length; j++) {
					// 两个分支图先分别和基本图作比较，baseNode的情况分别存储在nodeImages[i]中。可能是NULL、ANY、修改后的类型
					nodeImages[j] = TypedGraph.computeImage(baseNode, baseGraph, branchGraphs[j]);
				}
				try {
					// 再根据nodeImages[]中的情况，确定baseNode的finalType
					TypeNode finalType = TypedGraph.computeType(nodeImages, baseGraph.getTypeGraph());

					if (finalType == TypeNode.NULL_TYPE) { // 在某一分支中删除或两个分支都删除，则在result图中也删除
						resultGraph.remove2(baseNode); // 暂时不处理相邻边
						deleteTypedNodesList.add(baseNode);
					} else {

						if (finalType == TypeNode.ANY_TYPE) // 此节点在两个分支图和基本图中的类型一致
							finalType = baseNode.getType();

						TypedNode n = new TypedNode(); // 新申请的节点
						n.setType(finalType); // 设置新节点n的类型：和基本图中一样的类型或是替换后的类型

						for (TypedGraph image : branchGraphs) {
							// 将新节点n和两个分支图中对应的baseNode的索引集合并
							n.mergeIndex(image.getElementByIndexObject(baseNode.getIndex()));
						}
						// 将result图中的baseNode替换为n；这里可能在result图中替换或删除相邻边
						resultGraph.replaceWith2(baseNode, n); // 暂时不处理相邻边
						changeTypedNodesMap.put(baseNode, n);
					}

				} catch (NothingReturnedException e) {
					throw e; // 捕捉到异常后抛出
				}
			}
		}
		System.out.println("*******删除和修改TypedNodes：" + Profiler.end() + "ms");

		// 得到TypedEdges的所有类型
		List<TypeEdge> allTypeEdges = baseGraph.getTypeGraph().getAllTypeEdges();

		// 划分基础图的TypedEdges
		HashMap<TypeEdge, ArrayList<TypedEdge>> baseTypeEdgeMap = new HashMap<>();
		for (int i = 0; i < allTypeEdges.size(); i++) {
			ArrayList<TypedEdge> typedEdges = new ArrayList<>();
			baseTypeEdgeMap.put(allTypeEdges.get(i), typedEdges);
		}
		for (TypedEdge e : baseGraph.allTypedEdges) {
			baseTypeEdgeMap.get(e.getType()).add(e);
		}

		// 划分所有分支图的TypedEdges
		HashMap<TypeEdge, ArrayList<TypedEdge>> branchTypeEdgeMap = new HashMap<>();
		for (int i = 0; i < allTypeEdges.size(); i++) {
			ArrayList<TypedEdge> typedEdges = new ArrayList<>();
			branchTypeEdgeMap.put(allTypeEdges.get(i), typedEdges);
		}
		for (TypedGraph branchGraph : branchGraphs) {
			for (TypedEdge e : branchGraph.allTypedEdges) {
				branchTypeEdgeMap.get(e.getType()).add(e);
			}
		}

		// 删除和修改TypedEdges
		Profiler.begin();
		for (int i = 0; i < allTypeEdges.size(); i++) {
			TypedEdge[] typedEdgeImages = new TypedEdge[branchGraphs.length];
			for (TypedEdge baseEdge : baseTypeEdgeMap.get(allTypeEdges.get(i))) {

				// 多个分支图先分别和基本图作比较，baseEdge的情况分别存储在typedEdgeImages[i]中，可能是baseEdge、imageEdge、null
				for (int j = 0; j < branchGraphs.length; j++) {
					typedEdgeImages[j] = TypedGraph.computeImage(baseEdge, baseGraph, branchGraphs[j]);
				}

				try {
					// 再根据typedEdgeImages[]中的情况，确定finalEdgeInfo
					Tuple3<TypedNode, TypedNode, TypeEdge> finalEdgeInfo = TypedGraph.computeEdge(baseEdge,
							typedEdgeImages);

					if (finalEdgeInfo == null) {
						resultGraph.remove(baseEdge);
					} else {
						// 新加TypedNodes必须在TypedEdge之前
						TypedNode source = resultGraph.getElementByIndexObject(finalEdgeInfo.first.getIndex());
						TypedNode target = resultGraph.getElementByIndexObject(finalEdgeInfo.second.getIndex());
						TypedEdge edge = new TypedEdge(); // 新申请的对象edge
						edge.setSource(source);
						edge.setTarget(target);
						edge.setType(finalEdgeInfo.third);

						for (TypedGraph image : branchGraphs) { // 合并两个分支的索引集
							edge.mergeIndex(image.getElementByIndexObject(baseEdge.getIndex()));
						}

						resultGraph.replaceWith(baseEdge, edge); // 将baseEdge替换为edge
					}
				} catch (NothingReturnedException e) {
					throw e; // 捕捉到异常后抛出
				}
			}
		}
		System.out.println("*****删除和修改TypedEdges: " + Profiler.end() + "ms");

		// 得到ValueEdges的所有类型
		List<PropertyEdge> allPropertyEdges = baseGraph.getTypeGraph().getAllPropertyEdges();

		// 划分基础图的ValueEdges
		HashMap<PropertyEdge, ArrayList<ValueEdge>> basePropertyEdgeMap = new HashMap<>();
		for (int i = 0; i < allPropertyEdges.size(); i++) {
			ArrayList<ValueEdge> valueEdges = new ArrayList<>();
			basePropertyEdgeMap.put(allPropertyEdges.get(i), valueEdges);
		}
		for (ValueEdge e : baseGraph.allValueEdges) {
			basePropertyEdgeMap.get(e.getType()).add(e);
		}

		// 划分所有分支图的ValueEdges
		HashMap<PropertyEdge, ArrayList<ValueEdge>> branchPropertyEdgeMap = new HashMap<>();
		for (int i = 0; i < allPropertyEdges.size(); i++) {
			ArrayList<ValueEdge> valueEdges = new ArrayList<>();
			branchPropertyEdgeMap.put(allPropertyEdges.get(i), valueEdges);
		}
		for (TypedGraph branchGraph : branchGraphs) {
			for (ValueEdge e : branchGraph.allValueEdges) {
				branchPropertyEdgeMap.get(e.getType()).add(e);
			}
		}

		// 删除和修改ValueEdges
		Profiler.begin();
		for (int i = 0; i < allPropertyEdges.size(); i++) {
			ValueEdge[] valueEdgeImages = new ValueEdge[branchGraphs.length];
			for (ValueEdge baseEdge : basePropertyEdgeMap.get(allPropertyEdges.get(i))) { // 对于基本图中每一个条ValueEdge类型的边

				// 两个分支图先和基本图作比较，baseEdge的情况分别存储在valueEdgeImages[]中，可能是null、baseEdge、imageEdge
				for (int j = 0; j < branchGraphs.length; j++) {
					valueEdgeImages[j] = TypedGraph.computeImage(baseEdge, baseGraph, branchGraphs[j]);
				}

				try {
					// 再根据valueEdgeImages[]中的情况，确定finalEdgeInfo
					Tuple3<TypedNode, ValueNode, PropertyEdge> finalEdgeInfo = TypedGraph.computeEdge(baseEdge,
							valueEdgeImages);

					if (finalEdgeInfo == null) {
						resultGraph.remove(baseEdge);
					} else {
						TypedNode source = resultGraph.getElementByIndexObject(finalEdgeInfo.first.getIndex());
						ValueNode target = finalEdgeInfo.second;
						ValueEdge edge = new ValueEdge();
						edge.setSource(source);
						edge.setTarget(target);
						edge.setType(finalEdgeInfo.third);

						for (TypedGraph image : branchGraphs) {
							edge.mergeIndex(image.getElementByIndexObject(baseEdge.getIndex()));
						}

						resultGraph.replaceWith(baseEdge, edge);
					}
				} catch (NothingReturnedException e) {
					throw e;
				}
			}
		}
		System.out.println("*******删除和修改ValueEdges：" + Profiler.end() + "ms");

		// TypedNodes删除后，处理相邻的TypedEdge边
		Profiler.begin();
		deleteTypedNodesList.forEach(n -> {
			// 如果此节点还是某条TypedEdge类型边e的source或target端点，删除这条边e
			for (int i = resultGraph.allTypedEdges.size() - 1; i >= 0; i--) {
				TypedEdge e = resultGraph.allTypedEdges.get(i);
				if (resultGraph.isEqual(e.getSource(), n) || resultGraph.isEqual(e.getTarget(), n)) {
					resultGraph.allTypedEdges.remove(i);
					resultGraph.clearIndex(e);
				}
			}
		});
		System.out.println("*****TypedNodes删除后，处理相邻的TypedEdge边: " + Profiler.end() + "ms");

		// TypedNodes删除后，处理相邻的ValueEdge边
		Profiler.begin();
		deleteTypedNodesList.forEach(n -> {
			// 如果此节点还是某条ValueEdge类型边e的source端点，删除这条边e
			for (int i = resultGraph.allValueEdges.size() - 1; i >= 0; i--) {
				ValueEdge e = resultGraph.allValueEdges.get(i);
				if (resultGraph.isEqual(e.getSource(), n)) {
					resultGraph.allValueEdges.remove(i);
					resultGraph.clearIndex(e);
				}
			}
		});
		System.out.println("*****TypedNodes删除后，处理相邻的ValueEdge边: " + Profiler.end() + "ms");

		// TypedNodes修改后，处理相邻的TypedEdge边
		Profiler.begin();
		changeTypedNodesMap.forEach((nr, n) -> {
			if (nr != n) {
				TypeNode nodeType = n.getType();
				// 处理相邻的TypedEdge类型的边
				List<TypedEdge> removedTypedEdges = new ArrayList<TypedEdge>();
				resultGraph.allTypedEdges.replaceAll(e -> { // 对于result图中List<TypedEdge>的每个元素e
					TypeEdge edgeType = e.getType(); // 先获取e的type
					// 再根据edgeType可以获得sourceType和targetType
					TypeNode sourceType = edgeType.getSource();
					TypeNode targetType = edgeType.getTarget();

					if (e.getSource() == nr && e.getTarget() == nr) { // 1.如果e的source和target都是nr节点
						// 并且n的type(nodeType)是e的sourceType、targetType的子类型
						if (resultGraph.typeGraph.isSuperTypeOf(nodeType, sourceType)
								&& resultGraph.typeGraph.isSuperTypeOf(nodeType, targetType)) {
							TypedEdge res = new TypedEdge();
							res.setSource(n);
							res.setTarget(n);
							res.setType(edgeType);
							res.mergeIndex(e);
							resultGraph.reindexing(res);
							return res; // 将e替换为res
						} else { // 并且n的type不是e的sourceType |(&) targetType的子类型
							removedTypedEdges.add(e); // 将e添加到removedTypedEdges集合中
							resultGraph.clearIndex(e); // 将元素e的索引集和e的映射关系，从indexToObjectMap中清除
							return e; // 这里先暂时e->e
						}
					} else if (e.getSource() == nr) { // 2. 仅e的source是nr，e的target不是nr
						// 并且n的type(nodeType)是e的sourceType的子类型
						if (resultGraph.typeGraph.isSuperTypeOf(nodeType, sourceType)) {
							TypedEdge res = new TypedEdge();
							res.setSource(n);
							res.setTarget(e.getTarget());
							res.setType(edgeType);
							res.mergeIndex(e);
							resultGraph.reindexing(res);
							return res;
						} else { // 并且n的type不是e的sourceType的子类型
							removedTypedEdges.add(e);
							resultGraph.clearIndex(e);
							return e;
						}
					} else if (e.getTarget() == nr) { // 3. 仅e的target是nr，e的source不是nr
						// 并且n的type(nodeType)是e的targetType的子类型
						if (resultGraph.typeGraph.isSuperTypeOf(nodeType, targetType)) {
							TypedEdge res = new TypedEdge();
							res.setSource(e.getSource());
							res.setTarget(n);
							res.setType(edgeType);
							res.mergeIndex(e);
							resultGraph.reindexing(res);
							return res;
						} else { // 并且n的type(nodeType)不是e的targetType的子类型
							removedTypedEdges.add(e);
							resultGraph.clearIndex(e);
							return e;
						}
					} else // 4. e的source不是nr、target也不是nr
						return e;
				});
				resultGraph.allTypedEdges.removeAll(removedTypedEdges); // 集合操作removeAll()：A <- A-B，即从A集合中删除B集合
			}
		});
		System.out.println("*****TypedNodes修改后，处理相邻的TypedEdge边: " + Profiler.end() + "ms");

		// TypedNodes修改后，处理相邻的ValueEdge边
		Profiler.begin();
		changeTypedNodesMap.forEach((nr, n) -> {
			if (nr != n) {
				TypeNode nodeType = n.getType();
				// 处理相邻的ValueEdge类型的边
				List<ValueEdge> removedValueEdges = new ArrayList<ValueEdge>();
				resultGraph.allValueEdges.replaceAll(e -> { // 对于result图中List<ValueEdge>的每一个边e
					PropertyEdge edgeType = e.getType();
					TypeNode sourceType = edgeType.getSource();

					if (e.getSource() == nr) { // 如果e的source是nr
						// 并且n的type是e的sourceType的子类型，则将e替换为res
						if (resultGraph.typeGraph.isSuperTypeOf(nodeType, sourceType)) {
							ValueEdge res = new ValueEdge();
							res.setSource(n);
							res.setTarget(e.getTarget());
							res.setType(edgeType);
							res.mergeIndex(e);
							resultGraph.reindexing(res);
							return res;
						} else {
							// 并且n的type与nr的type不兼容，则删除e
							removedValueEdges.add(e);
							resultGraph.clearIndex(e);
							return e;
						}
					} else
						return e; // 如果e的source不是nr，则保留
				});
				resultGraph.allValueEdges.removeAll(removedValueEdges);
			}
		});
		System.out.println("*****处理相邻的ValueEdge边: " + Profiler.end() + "ms");

		// 新加TypedEdges
		Profiler.begin();
		for (int i = 0; i < allTypeEdges.size(); i++) {
			for (TypedEdge e : branchTypeEdgeMap.get(allTypeEdges.get(i))) {
				try { // 根据e的索引查找基本图中有无相应的对象，如果有则不做处理
					baseGraph.getElementByIndexObject(e.getIndex());
				} catch (Exception ex) { // 基本图中没有找到相应的对象
					TypedEdge re = null;
					try {
						// 根据e的索引查找result图中有无相应的对象，如果有则赋值给re
						re = resultGraph.getElementByIndexObject(e.getIndex());
					} catch (Exception ex2) { // 根据e的索引没有找到result图中相应的对象，则应添加e到result图的List<TypedEdge>中

						TypedNode source = resultGraph.getElementByIndexObject(e.getSource().getIndex());
						TypedNode target = resultGraph.getElementByIndexObject(e.getTarget().getIndex());

						if (e.getSource() != source || e.getTarget() != target) { // 说明e的source和target是基本图中有的，result图合并过三个图的索引，但不是同一对象
							TypedEdge ne = new TypedEdge();
							ne.setSource(source);
							ne.setTarget(target);
							ne.setType(e.getType());
							ne.mergeIndex(e);
							resultGraph.addTypedEdge(ne);
						} else // 说明e的source和target是在分支图中新添加的TypedNode类型节点，所以在分支图和result图中是同一对象
							resultGraph.addTypedEdge(e);

						re = null;

					} finally {

						// 根据e的索引找到了result图中相应的对象，并赋值给了re
						// 说明多个分支图添加了相同的边
						if (re != null) {
							if (re.getType() != e.getType()
									|| !re.getSource().getIndex().equals(e.getSource().getIndex())
									|| !re.getTarget().getIndex().equals(e.getTarget().getIndex())) {
								throw new NothingReturnedException();
							} else {
								re.mergeIndex(e);
								resultGraph.reindexing(re); // lyt修改
							}
						}
					}
				}
			}
		}
		System.out.println("*******新加TypedEdges：" + Profiler.end() + "ms");

		// 新加ValueEdges
		Profiler.begin();
		for (int i = 0; i < allPropertyEdges.size(); i++) {
			for (ValueEdge e : branchPropertyEdgeMap.get(allPropertyEdges.get(i))) {
				try {
					// 根据e的索引查找基本图中有无相应的对象，如果有则不处理
					baseGraph.getElementByIndexObject(e.getIndex());
				} catch (Exception ex) { // 如果基本图中没有相应的对象
					ValueEdge re = null;
					try {
						// 根据e的索引查找result图中有无相应的对象，如果有则赋值给re
						re = resultGraph.getElementByIndexObject(e.getIndex());
					} catch (Exception ex2) { // 根据e的索引没有找到result图中相应的对象，则需要添加e到图的List<ValueEdge>中

						TypedNode source = resultGraph.getElementByIndexObject(e.getSource().getIndex());
//								ValueNode target = e.getTarget();

						if (e.getSource() != source) { // source是基本图中有的，result图合并过三个图的索引集，但不是同一对象
							ValueEdge ne = new ValueEdge();
							ne.setSource(source);
							ne.setTarget(e.getTarget());
							ne.setType(e.getType());
							ne.mergeIndex(e);
							resultGraph.addValueEdge(ne);
						} else // e的source和result图中是同一对象，说明是新添加的TypedNode对象
							resultGraph.addValueEdge(e);

						re = null;
					} finally {
						if (re != null) { // re!=null，说明result图中找到了相应的对象，并且赋值给了re
							if (re.getType() != e.getType()
									|| !re.getSource().getIndex().equals(e.getSource().getIndex())
									|| re.getTarget().equals(e.getTarget()) == false) {
								throw new NothingReturnedException();
							} else {
								re.mergeIndex(e);
								resultGraph.reindexing(re); // lyt修改
							}
						}
					}
				}
			}
		}
		System.out.println("*******新加TypedEdges：" + Profiler.end() + "ms");

		return resultGraph;

	}

	public static TypedGraph prepareCon(TypedGraph baseGraph, TypedGraph... branchGraphs)
			throws NothingReturnedException {

		TypedGraph resultGraph = baseGraph.getCopy();

		// 得到TypedNodes的所有类型
		List<TypeNode> allTypeNodes = baseGraph.getTypeGraph().getAllTypeNodes();

		// 划分基础图的TypedNodes
		HashMap<TypeNode, ArrayList<TypedNode>> baseTypeNodeMap = new HashMap<>();
		for (int i = 0; i < allTypeNodes.size(); i++) {
			ArrayList<TypedNode> typedNodes = new ArrayList<>();
			baseTypeNodeMap.put(allTypeNodes.get(i), typedNodes);
		}
		for (TypedNode n : baseGraph.allTypedNodes) {
			baseTypeNodeMap.get(n.getType()).add(n);
		}

		// 划分所有分支图的TypedNodes
		HashMap<TypeNode, ArrayList<TypedNode>> branchTypeNodeMap = new HashMap<>();
		for (int i = 0; i < allTypeNodes.size(); i++) {
			ArrayList<TypedNode> typedNodes = new ArrayList<>();
			branchTypeNodeMap.put(allTypeNodes.get(i), typedNodes);
		}
		for (TypedGraph branchGraph : branchGraphs) {
			for (TypedNode n : branchGraph.allTypedNodes) {
				branchTypeNodeMap.get(n.getType()).add(n);
			}
		}

		// 新加TypedNodes
		Profiler.begin();
		HashMap<TypeNode, Thread> addTypedNodesMap = new HashMap<>();
		for (int i = 0; i < allTypeNodes.size(); i++) {
			TypeNode typeNode = allTypeNodes.get(i);
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					for (TypedNode n : branchTypeNodeMap.get(typeNode)) {
						try {
							// 根据n的索引查找基本图中有无相应的对象，如果有则不做处理
							baseGraph.getElementByIndexObject(n.getIndex());
						} catch (NothingReturnedException e) { // 如果在基本图中没有找到相应的对象
							TypedNode rn = null;
							try {
								// 根据n的索引查找result图中有无相应的对象，如果有就赋值给rn
								rn = resultGraph.getElementByIndexObject(n.getIndex());
							} catch (NothingReturnedException e1) { // 如果result图中无相应的对象，则将n对象添加到result图的List<TypedNode>，并且令rn=null
								resultGraph.addTypedNode(n);
								rn = null;
							} finally {

								if (rn != null) { // 如果rn!=null，说明根据n的索引找到了result图中相应的对象，且赋值给了rn
									TypeNode lt = rn.getType();
									TypeNode rt = n.getType();
									TypeNode ct = baseGraph.getTypeGraph().computeSubtype(lt, rt); // 最终的类型

									if (ct == TypeNode.NULL_TYPE) {
										try {
											throw new NothingReturnedException(); // incompatible
										} catch (NothingReturnedException e2) {
											e2.printStackTrace();
										}
									} else {
										rn.setType(ct); // 若兼容，则rn的type设置为ct
										rn.mergeIndex(n);
										resultGraph.reindexing(rn); // lyt-修改
									}
								}
							}
						}
					}
				}
			});
			addTypedNodesMap.put(typeNode, thread);
			thread.start();
		}
		System.out.println("*****新加TypedNodes: " + Profiler.end() + "ms");

		// 新加ValueNodes
		Profiler.begin();
		for (TypedGraph branchGraph : branchGraphs) {
			branchGraph.allValueNodes.forEach(v -> {
				resultGraph.addValueNode(v);
			});
		}
		System.out.println("*******新加ValueNodes：" + Profiler.end() + "ms");


		// 删除和修改TypedNodes，暂时不处理相邻的边。
		Profiler.begin();
		ArrayList<TypedNode> deleteTypedNodesList = new ArrayList<>();
		HashMap<TypedNode, TypedNode> changeTypedNodesMap = new HashMap<>();
		HashMap<TypeNode, Thread> deleteAndChangeTypedNodesMap = new HashMap<>();
		for (int i = 0; i < allTypeNodes.size(); i++) {
			
			TypeNode typeNode = allTypeNodes.get(i);
			
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						addTypedNodesMap.get(typeNode).join();
					} catch (InterruptedException e1) {
					}
					
					TypeNode[] nodeImages = new TypeNode[branchGraphs.length];
					for (TypedNode baseNode : baseTypeNodeMap.get(typeNode)) {
						for (int j = 0; j < branchGraphs.length; j++) {
							// 两个分支图先分别和基本图作比较，baseNode的情况分别存储在nodeImages[i]中。可能是NULL、ANY、修改后的类型
							nodeImages[j] = TypedGraph.computeImage(baseNode, baseGraph, branchGraphs[j]);
						}
						try {
							// 再根据nodeImages[]中的情况，确定baseNode的finalType
							TypeNode finalType = TypedGraph.computeType(nodeImages, baseGraph.getTypeGraph());

							if (finalType == TypeNode.NULL_TYPE) { // 在某一分支中删除或两个分支都删除，则在result图中也删除
								resultGraph.remove2(baseNode); // 暂时不处理相邻边
								deleteTypedNodesList.add(baseNode);
							} else {

								if (finalType == TypeNode.ANY_TYPE) // 此节点在两个分支图和基本图中的类型一致
									finalType = baseNode.getType();

								TypedNode n = new TypedNode(); // 新申请的节点
								n.setType(finalType); // 设置新节点n的类型：和基本图中一样的类型或是替换后的类型

								for (TypedGraph image : branchGraphs) {
									// 将新节点n和两个分支图中对应的baseNode的索引集合并
									n.mergeIndex(image.getElementByIndexObject(baseNode.getIndex()));
								}
								// 将result图中的baseNode替换为n；这里可能在result图中替换或删除相邻边
								resultGraph.replaceWith2(baseNode, n); // 暂时不处理相邻边
								changeTypedNodesMap.put(baseNode, n);
							}

						} catch (NothingReturnedException e) {
							e.printStackTrace();
						}
					}
					System.out.println("*******删除和修改" + typeNode + "TypedNodes：" + Profiler.end() + "ms");
				}
			});
			deleteAndChangeTypedNodesMap.put(typeNode, thread);
			thread.start();

		}
		
		// main wait
		for (int i = 0; i < allTypeNodes.size(); i++) {
			try {
				deleteAndChangeTypedNodesMap.get(allTypeNodes.get(i)).join();
			} catch (InterruptedException e1) {
			}
		}
		

		// 得到TypedEdges的所有类型
		List<TypeEdge> allTypeEdges = baseGraph.getTypeGraph().getAllTypeEdges();

		// 划分基础图的TypedEdges
		HashMap<TypeEdge, ArrayList<TypedEdge>> baseTypeEdgeMap = new HashMap<>();
		for (int i = 0; i < allTypeEdges.size(); i++) {
			ArrayList<TypedEdge> typedEdges = new ArrayList<>();
			baseTypeEdgeMap.put(allTypeEdges.get(i), typedEdges);
		}
		for (TypedEdge e : baseGraph.allTypedEdges) {
			baseTypeEdgeMap.get(e.getType()).add(e);
		}

		// 划分所有分支图的TypedEdges
		HashMap<TypeEdge, ArrayList<TypedEdge>> branchTypeEdgeMap = new HashMap<>();
		for (int i = 0; i < allTypeEdges.size(); i++) {
			ArrayList<TypedEdge> typedEdges = new ArrayList<>();
			branchTypeEdgeMap.put(allTypeEdges.get(i), typedEdges);
		}
		for (TypedGraph branchGraph : branchGraphs) {
			for (TypedEdge e : branchGraph.allTypedEdges) {
				branchTypeEdgeMap.get(e.getType()).add(e);
			}
		}

		// 删除和修改TypedEdges
		Profiler.begin();
		for (int i = 0; i < allTypeEdges.size(); i++) {
			TypedEdge[] typedEdgeImages = new TypedEdge[branchGraphs.length];
			for (TypedEdge baseEdge : baseTypeEdgeMap.get(allTypeEdges.get(i))) {

				// 多个分支图先分别和基本图作比较，baseEdge的情况分别存储在typedEdgeImages[i]中，可能是baseEdge、imageEdge、null
				for (int j = 0; j < branchGraphs.length; j++) {
					typedEdgeImages[j] = TypedGraph.computeImage(baseEdge, baseGraph, branchGraphs[j]);
				}

				try {
					// 再根据typedEdgeImages[]中的情况，确定finalEdgeInfo
					Tuple3<TypedNode, TypedNode, TypeEdge> finalEdgeInfo = TypedGraph.computeEdge(baseEdge,
							typedEdgeImages);

					if (finalEdgeInfo == null) {
						resultGraph.remove(baseEdge);
					} else {
						// 新加TypedNodes必须在TypedEdge之前
						TypedNode source = resultGraph.getElementByIndexObject(finalEdgeInfo.first.getIndex());
						TypedNode target = resultGraph.getElementByIndexObject(finalEdgeInfo.second.getIndex());
						TypedEdge edge = new TypedEdge(); // 新申请的对象edge
						edge.setSource(source);
						edge.setTarget(target);
						edge.setType(finalEdgeInfo.third);

						for (TypedGraph image : branchGraphs) { // 合并两个分支的索引集
							edge.mergeIndex(image.getElementByIndexObject(baseEdge.getIndex()));
						}

						resultGraph.replaceWith(baseEdge, edge); // 将baseEdge替换为edge
					}
				} catch (NothingReturnedException e) {
					throw e; // 捕捉到异常后抛出
				}
			}
		}
		System.out.println("*****删除和修改TypedEdges: " + Profiler.end() + "ms");

		// 得到ValueEdges的所有类型
		List<PropertyEdge> allPropertyEdges = baseGraph.getTypeGraph().getAllPropertyEdges();

		// 划分基础图的ValueEdges
		HashMap<PropertyEdge, ArrayList<ValueEdge>> basePropertyEdgeMap = new HashMap<>();
		for (int i = 0; i < allPropertyEdges.size(); i++) {
			ArrayList<ValueEdge> valueEdges = new ArrayList<>();
			basePropertyEdgeMap.put(allPropertyEdges.get(i), valueEdges);
		}
		for (ValueEdge e : baseGraph.allValueEdges) {
			basePropertyEdgeMap.get(e.getType()).add(e);
		}

		// 划分所有分支图的ValueEdges
		HashMap<PropertyEdge, ArrayList<ValueEdge>> branchPropertyEdgeMap = new HashMap<>();
		for (int i = 0; i < allPropertyEdges.size(); i++) {
			ArrayList<ValueEdge> valueEdges = new ArrayList<>();
			branchPropertyEdgeMap.put(allPropertyEdges.get(i), valueEdges);
		}
		for (TypedGraph branchGraph : branchGraphs) {
			for (ValueEdge e : branchGraph.allValueEdges) {
				branchPropertyEdgeMap.get(e.getType()).add(e);
			}
		}

		// 删除和修改ValueEdges
		Profiler.begin();
		for (int i = 0; i < allPropertyEdges.size(); i++) {
			ValueEdge[] valueEdgeImages = new ValueEdge[branchGraphs.length];
			for (ValueEdge baseEdge : basePropertyEdgeMap.get(allPropertyEdges.get(i))) { // 对于基本图中每一个条ValueEdge类型的边

				// 两个分支图先和基本图作比较，baseEdge的情况分别存储在valueEdgeImages[]中，可能是null、baseEdge、imageEdge
				for (int j = 0; j < branchGraphs.length; j++) {
					valueEdgeImages[j] = TypedGraph.computeImage(baseEdge, baseGraph, branchGraphs[j]);
				}

				try {
					// 再根据valueEdgeImages[]中的情况，确定finalEdgeInfo
					Tuple3<TypedNode, ValueNode, PropertyEdge> finalEdgeInfo = TypedGraph.computeEdge(baseEdge,
							valueEdgeImages);

					if (finalEdgeInfo == null) {
						resultGraph.remove(baseEdge);
					} else {
						TypedNode source = resultGraph.getElementByIndexObject(finalEdgeInfo.first.getIndex());
						ValueNode target = finalEdgeInfo.second;
						ValueEdge edge = new ValueEdge();
						edge.setSource(source);
						edge.setTarget(target);
						edge.setType(finalEdgeInfo.third);

						for (TypedGraph image : branchGraphs) {
							edge.mergeIndex(image.getElementByIndexObject(baseEdge.getIndex()));
						}

						resultGraph.replaceWith(baseEdge, edge);
					}
				} catch (NothingReturnedException e) {
					throw e;
				}
			}
		}
		System.out.println("*******删除和修改ValueEdges：" + Profiler.end() + "ms");

		// TypedNodes删除后，处理相邻的TypedEdge边
		Profiler.begin();
		deleteTypedNodesList.forEach(n -> {
			// 如果此节点还是某条TypedEdge类型边e的source或target端点，删除这条边e
			for (int i = resultGraph.allTypedEdges.size() - 1; i >= 0; i--) {
				TypedEdge e = resultGraph.allTypedEdges.get(i);
				if (resultGraph.isEqual(e.getSource(), n) || resultGraph.isEqual(e.getTarget(), n)) {
					resultGraph.allTypedEdges.remove(i);
					resultGraph.clearIndex(e);
				}
			}
		});
		System.out.println("*****TypedNodes删除后，处理相邻的TypedEdge边: " + Profiler.end() + "ms");

		// TypedNodes删除后，处理相邻的ValueEdge边
		Profiler.begin();
		deleteTypedNodesList.forEach(n -> {
			// 如果此节点还是某条ValueEdge类型边e的source端点，删除这条边e
			for (int i = resultGraph.allValueEdges.size() - 1; i >= 0; i--) {
				ValueEdge e = resultGraph.allValueEdges.get(i);
				if (resultGraph.isEqual(e.getSource(), n)) {
					resultGraph.allValueEdges.remove(i);
					resultGraph.clearIndex(e);
				}
			}
		});
		System.out.println("*****TypedNodes删除后，处理相邻的ValueEdge边: " + Profiler.end() + "ms");

		// TypedNodes修改后，处理相邻的TypedEdge边
		Profiler.begin();
		changeTypedNodesMap.forEach((nr, n) -> {
			if (nr != n) {
				TypeNode nodeType = n.getType();
				// 处理相邻的TypedEdge类型的边
				List<TypedEdge> removedTypedEdges = new ArrayList<TypedEdge>();
				resultGraph.allTypedEdges.replaceAll(e -> { // 对于result图中List<TypedEdge>的每个元素e
					TypeEdge edgeType = e.getType(); // 先获取e的type
					// 再根据edgeType可以获得sourceType和targetType
					TypeNode sourceType = edgeType.getSource();
					TypeNode targetType = edgeType.getTarget();

					if (e.getSource() == nr && e.getTarget() == nr) { // 1.如果e的source和target都是nr节点
						// 并且n的type(nodeType)是e的sourceType、targetType的子类型
						if (resultGraph.typeGraph.isSuperTypeOf(nodeType, sourceType)
								&& resultGraph.typeGraph.isSuperTypeOf(nodeType, targetType)) {
							TypedEdge res = new TypedEdge();
							res.setSource(n);
							res.setTarget(n);
							res.setType(edgeType);
							res.mergeIndex(e);
							resultGraph.reindexing(res);
							return res; // 将e替换为res
						} else { // 并且n的type不是e的sourceType |(&) targetType的子类型
							removedTypedEdges.add(e); // 将e添加到removedTypedEdges集合中
							resultGraph.clearIndex(e); // 将元素e的索引集和e的映射关系，从indexToObjectMap中清除
							return e; // 这里先暂时e->e
						}
					} else if (e.getSource() == nr) { // 2. 仅e的source是nr，e的target不是nr
						// 并且n的type(nodeType)是e的sourceType的子类型
						if (resultGraph.typeGraph.isSuperTypeOf(nodeType, sourceType)) {
							TypedEdge res = new TypedEdge();
							res.setSource(n);
							res.setTarget(e.getTarget());
							res.setType(edgeType);
							res.mergeIndex(e);
							resultGraph.reindexing(res);
							return res;
						} else { // 并且n的type不是e的sourceType的子类型
							removedTypedEdges.add(e);
							resultGraph.clearIndex(e);
							return e;
						}
					} else if (e.getTarget() == nr) { // 3. 仅e的target是nr，e的source不是nr
						// 并且n的type(nodeType)是e的targetType的子类型
						if (resultGraph.typeGraph.isSuperTypeOf(nodeType, targetType)) {
							TypedEdge res = new TypedEdge();
							res.setSource(e.getSource());
							res.setTarget(n);
							res.setType(edgeType);
							res.mergeIndex(e);
							resultGraph.reindexing(res);
							return res;
						} else { // 并且n的type(nodeType)不是e的targetType的子类型
							removedTypedEdges.add(e);
							resultGraph.clearIndex(e);
							return e;
						}
					} else // 4. e的source不是nr、target也不是nr
						return e;
				});
				resultGraph.allTypedEdges.removeAll(removedTypedEdges); // 集合操作removeAll()：A <- A-B，即从A集合中删除B集合
			}
		});
		System.out.println("*****TypedNodes修改后，处理相邻的TypedEdge边: " + Profiler.end() + "ms");

		// TypedNodes修改后，处理相邻的ValueEdge边
		Profiler.begin();
		changeTypedNodesMap.forEach((nr, n) -> {
			if (nr != n) {
				TypeNode nodeType = n.getType();
				// 处理相邻的ValueEdge类型的边
				List<ValueEdge> removedValueEdges = new ArrayList<ValueEdge>();
				resultGraph.allValueEdges.replaceAll(e -> { // 对于result图中List<ValueEdge>的每一个边e
					PropertyEdge edgeType = e.getType();
					TypeNode sourceType = edgeType.getSource();

					if (e.getSource() == nr) { // 如果e的source是nr
						// 并且n的type是e的sourceType的子类型，则将e替换为res
						if (resultGraph.typeGraph.isSuperTypeOf(nodeType, sourceType)) {
							ValueEdge res = new ValueEdge();
							res.setSource(n);
							res.setTarget(e.getTarget());
							res.setType(edgeType);
							res.mergeIndex(e);
							resultGraph.reindexing(res);
							return res;
						} else {
							// 并且n的type与nr的type不兼容，则删除e
							removedValueEdges.add(e);
							resultGraph.clearIndex(e);
							return e;
						}
					} else
						return e; // 如果e的source不是nr，则保留
				});
				resultGraph.allValueEdges.removeAll(removedValueEdges);
			}
		});
		System.out.println("*****处理相邻的ValueEdge边: " + Profiler.end() + "ms");

		// 新加TypedEdges
		Profiler.begin();
		for (int i = 0; i < allTypeEdges.size(); i++) {
			for (TypedEdge e : branchTypeEdgeMap.get(allTypeEdges.get(i))) {
				try { // 根据e的索引查找基本图中有无相应的对象，如果有则不做处理
					baseGraph.getElementByIndexObject(e.getIndex());
				} catch (Exception ex) { // 基本图中没有找到相应的对象
					TypedEdge re = null;
					try {
						// 根据e的索引查找result图中有无相应的对象，如果有则赋值给re
						re = resultGraph.getElementByIndexObject(e.getIndex());
					} catch (Exception ex2) { // 根据e的索引没有找到result图中相应的对象，则应添加e到result图的List<TypedEdge>中

						TypedNode source = resultGraph.getElementByIndexObject(e.getSource().getIndex());
						TypedNode target = resultGraph.getElementByIndexObject(e.getTarget().getIndex());

						if (e.getSource() != source || e.getTarget() != target) { // 说明e的source和target是基本图中有的，result图合并过三个图的索引，但不是同一对象
							TypedEdge ne = new TypedEdge();
							ne.setSource(source);
							ne.setTarget(target);
							ne.setType(e.getType());
							ne.mergeIndex(e);
							resultGraph.addTypedEdge(ne);
						} else // 说明e的source和target是在分支图中新添加的TypedNode类型节点，所以在分支图和result图中是同一对象
							resultGraph.addTypedEdge(e);

						re = null;

					} finally {

						// 根据e的索引找到了result图中相应的对象，并赋值给了re
						// 说明多个分支图添加了相同的边
						if (re != null) {
							if (re.getType() != e.getType()
									|| !re.getSource().getIndex().equals(e.getSource().getIndex())
									|| !re.getTarget().getIndex().equals(e.getTarget().getIndex())) {
								throw new NothingReturnedException();
							} else {
								re.mergeIndex(e);
								resultGraph.reindexing(re); // lyt修改
							}
						}
					}
				}
			}
		}
		System.out.println("*******新加TypedEdges：" + Profiler.end() + "ms");

		// 新加ValueEdges
		Profiler.begin();
		for (int i = 0; i < allPropertyEdges.size(); i++) {
			for (ValueEdge e : branchPropertyEdgeMap.get(allPropertyEdges.get(i))) {
				try {
					// 根据e的索引查找基本图中有无相应的对象，如果有则不处理
					baseGraph.getElementByIndexObject(e.getIndex());
				} catch (Exception ex) { // 如果基本图中没有相应的对象
					ValueEdge re = null;
					try {
						// 根据e的索引查找result图中有无相应的对象，如果有则赋值给re
						re = resultGraph.getElementByIndexObject(e.getIndex());
					} catch (Exception ex2) { // 根据e的索引没有找到result图中相应的对象，则需要添加e到图的List<ValueEdge>中

						TypedNode source = resultGraph.getElementByIndexObject(e.getSource().getIndex());
						// ValueNode target = e.getTarget();

						if (e.getSource() != source) { // source是基本图中有的，result图合并过三个图的索引集，但不是同一对象
							ValueEdge ne = new ValueEdge();
							ne.setSource(source);
							ne.setTarget(e.getTarget());
							ne.setType(e.getType());
							ne.mergeIndex(e);
							resultGraph.addValueEdge(ne);
						} else // e的source和result图中是同一对象，说明是新添加的TypedNode对象
							resultGraph.addValueEdge(e);

						re = null;
					} finally {
						if (re != null) { // re!=null，说明result图中找到了相应的对象，并且赋值给了re
							if (re.getType() != e.getType()
									|| !re.getSource().getIndex().equals(e.getSource().getIndex())
									|| re.getTarget().equals(e.getTarget()) == false) {
								throw new NothingReturnedException();
							} else {
								re.mergeIndex(e);
								resultGraph.reindexing(re); // lyt修改
							}
						}
					}
				}
			}
		}
		System.out.println("*******新加TypedEdges：" + Profiler.end() + "ms");

		return resultGraph;

	}
	

}
