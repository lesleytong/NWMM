package edu.ustb.sei.mde.graph.typedGraph;
/**
 * 并发，利用join()
 */
import java.io.ObjectOutputStream.PutField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.naming.spi.DirStateFactory.Result;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.bxcore.structures.Index;
import edu.ustb.sei.mde.graph.type.PropertyEdge;
import edu.ustb.sei.mde.graph.type.TypeEdge;
import edu.ustb.sei.mde.graph.type.TypeNode;
import edu.ustb.sei.mde.graph.typedGraph.constraint.GraphConstraint;
import edu.ustb.sei.mde.structure.Tuple2;
import edu.ustb.sei.mde.structure.Tuple3;

public class BXMerge_NewVersion {

	/** 多路合并 */
	public static TypedGraph merge(TypedGraph baseGraph, TypedGraph... branchGraphs) throws NothingReturnedException {

		TypedGraph resultGraph = baseGraph.getCopy();

		Thread addTypedNodesThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Profiler.begin();
				// 新加TypedNodes
				for (TypedGraph branchGraph : branchGraphs) {
					for (TypedNode n : branchGraph.allTypedNodes) { // 对于分支图中每个TypedNode类型的节点n
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
			}

		});
		addTypedNodesThread.start();

		Thread addValueNodesThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Profiler.begin();
				// 新加ValueNodes
				for (TypedGraph branch : branchGraphs) {
					branch.allValueNodes.forEach(v -> {
						resultGraph.addValueNode(v);
					});
				}
				System.out.println("*****新加ValueNodes: " + Profiler.end() + "ms");
			}
		});
		addValueNodesThread.start();

		// 删除TypedEdges
		HashMap<TypedEdge, Tuple3<TypedNode, TypedNode, TypeEdge>> changeTypedEdgeMap = new HashMap<>();
		Thread deleteTypedEdgesThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Profiler.begin();
				TypedEdge[] typedEdgeImages = new TypedEdge[branchGraphs.length];
				for (TypedEdge baseEdge : baseGraph.allTypedEdges) { // 对于基本图中每一个TypedEdge类型的边baseEdge

					// 多个分支图先分别和基本图作比较，baseEdge的情况分别存储在typedEdgeImages[i]中，可能是baseEdge、imageEdge、null
					for (int i = 0; i < branchGraphs.length; i++) {
						typedEdgeImages[i] = TypedGraph.computeImage(baseEdge, baseGraph, branchGraphs[i]);
					}

					try {
						// 再根据typedEdgeImages[]中的情况，确定finalEdgeInfo
						Tuple3<TypedNode, TypedNode, TypeEdge> finalEdgeInfo = TypedGraph.computeEdge(baseEdge,
								typedEdgeImages);

						if (finalEdgeInfo == null) {
							resultGraph.remove(baseEdge);
						} else {
							// 基础图与各个分支图的baseEdge一致；或者不同分支图中做了修改但相互兼容
							changeTypedEdgeMap.put(baseEdge, finalEdgeInfo);
						}
					} catch (NothingReturnedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("*****删除TypedEdges: " + Profiler.end() + "ms");
			}
		});
		deleteTypedEdgesThread.start();

		// 删除ValueEdges
		HashMap<ValueEdge, Tuple3<TypedNode, ValueNode, PropertyEdge>> changeValueEdgeMap = new HashMap<>();
		Thread deleteValueEdgesThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Profiler.begin();
				ValueEdge[] valueEdgeImages = new ValueEdge[branchGraphs.length];
				for (ValueEdge baseEdge : baseGraph.allValueEdges) { // 对于基本图中每一个条ValueEdge类型的边
					// 两个分支图先和基本图作比较，baseEdge的情况分别存储在valueEdgeImages[]中，可能是null、baseEdge、imageEdge
					for (int i = 0; i < branchGraphs.length; i++) {
						valueEdgeImages[i] = TypedGraph.computeImage(baseEdge, baseGraph, branchGraphs[i]);
					}

					try {
						// 再根据valueEdgeImages[]中的情况，确定finalEdgeInfo
						Tuple3<TypedNode, ValueNode, PropertyEdge> finalEdgeInfo = TypedGraph.computeEdge(baseEdge,
								valueEdgeImages);

						if (finalEdgeInfo == null) {
							resultGraph.remove(baseEdge);
						} else {
							// 基础图与各个分支图的baseEdge一致；或者不同分支图中做了修改但相互兼容
							changeValueEdgeMap.put(baseEdge, finalEdgeInfo);
						}
					} catch (NothingReturnedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("*****删除ValueEdges: " + Profiler.end() + "ms");
			}
		});
		deleteValueEdgesThread.start();

		// 删除和修改TypedNodes
		HashMap<TypedNode, TypedNode> changeTypedNodesMap = new HashMap<>();
		ArrayList<TypedNode> deleteTypedNodesList = new ArrayList<>();
		Thread deleteAndChangeTypedNodesThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Profiler.begin();
				try {
					addTypedNodesThread.join();
				} catch (InterruptedException e) {
				}
				TypeNode[] nodeImages = new TypeNode[branchGraphs.length]; // 比如length=2
				for (TypedNode baseNode : baseGraph.allTypedNodes) { // 对于基本图中每一个TypedNode节点

					for (int i = 0; i < branchGraphs.length; i++) {
						// 两个分支图先分别和基本图作比较，baseNode的情况分别存储在nodeImages[i]中。可能是NULL、ANY、修改后的类型
						nodeImages[i] = TypedGraph.computeImage(baseNode, baseGraph, branchGraphs[i]);
					}

					try {
						// 再根据nodeImages[]中的情况，确定baseNode的finalType
						TypeNode finalType = TypedGraph.computeType(nodeImages, baseGraph.getTypeGraph());

						if (finalType == TypeNode.NULL_TYPE) { // 在某一分支中删除或两个分支都删除，则在result图中也删除
							deleteTypedNodesList.add(baseNode);
							resultGraph.remove2(baseNode); // 暂时只删除节点，还未处理相邻边
						} else {

							if (finalType == TypeNode.ANY_TYPE) // 此节点在两个分支图和基本图中的类型一致
								finalType = baseNode.getType();

							TypedNode n = new TypedNode(); // 新申请的节点
							n.setType(finalType); // 设置新节点n的类型：和基本图中一样的类型或是替换后的类型
							for (TypedGraph branch : branchGraphs) {
								// 将新节点n和两个分支图中对应的baseNode的索引集合并
								n.mergeIndex(branch.getElementByIndexObject(baseNode.getIndex()));
							}
							// 将result图中的baseNode替换为n；这里可能在result图中替换或删除相邻边
							changeTypedNodesMap.put(baseNode, n);
							resultGraph.replaceWith2(baseNode, n); // 暂时只替换节点，还未处理相邻边
						}

					} catch (NothingReturnedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("*****删除和修改TypedNodes: " + Profiler.end() + "ms");
			}
		});
		deleteAndChangeTypedNodesThread.start();

		// 修改TypedEdges
		Thread changeTypedEdgesThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Profiler.begin();
				try {
					deleteTypedEdgesThread.join();
					deleteAndChangeTypedNodesThread.join();
				} catch (InterruptedException e1) {
				}
				changeTypedEdgeMap.forEach((key, value) -> {
					try {
						TypedNode source = resultGraph.getElementByIndexObject(value.first.getIndex());
						TypedNode target = resultGraph.getElementByIndexObject(value.second.getIndex());
						TypedEdge edge = new TypedEdge(); // 新申请的对象edge
						edge.setSource(source);
						edge.setTarget(target);
						edge.setType(value.third);

						for (TypedGraph image : branchGraphs) { // 合并两个分支的索引集
							edge.mergeIndex(image.getElementByIndexObject(key.getIndex()));
						}

						resultGraph.replaceWith(key, edge); // 将baseEdge替换为edge

					} catch (NothingReturnedException e) {
						e.printStackTrace();
					}
				});
				System.out.println("*****修改TypedEdges: " + Profiler.end() + "ms");
			}
		});
		changeTypedEdgesThread.start();

		// 修改ValueEdges
		Thread changeValueEdgesThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Profiler.begin();
				try {
					addValueNodesThread.join();
					deleteValueEdgesThread.join();
					deleteAndChangeTypedNodesThread.join();
				} catch (InterruptedException e) {
				}
				changeValueEdgeMap.forEach((key, value) -> {
					try {
						TypedNode source = resultGraph.getElementByIndexObject(value.first.getIndex());
						ValueNode target = value.second;
						ValueEdge edge = new ValueEdge();
						edge.setSource(source);
						edge.setTarget(target);
						edge.setType(value.third);

						for (TypedGraph image : branchGraphs) {
							edge.mergeIndex(image.getElementByIndexObject(key.getIndex()));
						}
						resultGraph.replaceWith(key, edge);
					} catch (NothingReturnedException e) {
						e.printStackTrace();
					}
				});
				System.out.println("*****修改ValueEdges: " + Profiler.end() + "ms");
			}
		});
		changeValueEdgesThread.start();

		// 处理相邻的TypedEdge边
		Thread adjacentTypedEdgesThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Profiler.begin();
				try {
					changeTypedEdgesThread.join();
				} catch (InterruptedException e) {
				}
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
				System.out.println("*****处理相邻的TypedEdge边: " + Profiler.end() + "ms");
			}
		});
		adjacentTypedEdgesThread.start();

		// 处理相邻的ValueEdge边
		Thread adjacentValueEdgesThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Profiler.begin();
				try {
					changeValueEdgesThread.join();
				} catch (InterruptedException e) {
				}

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
			}
		});
		adjacentValueEdgesThread.start();

		// 新加TypedEdges
		Thread addTypedEdgesThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Profiler.begin();
				try {
					adjacentTypedEdgesThread.join();
				} catch (InterruptedException e) {
				}

				for (TypedGraph image : branchGraphs) {
					for (TypedEdge e : image.allTypedEdges) { // 对于分支图中每一个TypedEdge边e
						try { // 根据e的索引查找基本图中有无相应的对象，如果有则不做处理
							baseGraph.getElementByIndexObject(e.getIndex());
						} catch (Exception ex) { // 基本图中没有找到相应的对象
							TypedEdge re = null;
							try {
								// 根据e的索引查找result图中有无相应的对象，如果有则赋值给re
								re = resultGraph.getElementByIndexObject(e.getIndex());
							} catch (Exception ex2) { // 根据e的索引没有找到result图中相应的对象，则应添加e到result图的List<TypedEdge>中

								TypedNode source = null;
								TypedNode target = null;
								try {
									source = resultGraph.getElementByIndexObject(e.getSource().getIndex());
									target = resultGraph.getElementByIndexObject(e.getTarget().getIndex());
								} catch (NothingReturnedException e1) {
									e1.printStackTrace();
								}

								if (e.getSource() != source || e.getTarget() != target) { // 说明e的source或者target是基本图中有的，result图合并过三个图的索引，但不是同一对象
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

										try {
											throw new NothingReturnedException();
										} catch (NothingReturnedException e2) {
											e2.printStackTrace();
										}
									} else {
										re.mergeIndex(e);
										resultGraph.reindexing(re); // lyt修改
									}
								}
							}
						}
					}
				}
				System.out.println("*****新加TypedEdges: " + Profiler.end() + "ms");
			}
		});
		addTypedEdgesThread.start();

		// 新加ValueEdges
		// 等TypedNode的新加、删除、替换执行完（相邻的ValueEdge边处理完），等ValueNode的新加执行完
		Thread addValueEdgesThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Profiler.begin();
				try {
					addValueNodesThread.join();
					adjacentValueEdgesThread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				for (TypedGraph image : branchGraphs) {
					for (ValueEdge e : image.allValueEdges) { // 对于分支图中的每一个ValueEdge边e
						try {
							// 根据e的索引查找基本图中有无相应的对象，如果有则不处理
							baseGraph.getElementByIndexObject(e.getIndex());
						} catch (Exception ex) { // 如果基本图中没有相应的对象
							ValueEdge re = null;
							try {
								// 根据e的索引查找result图中有无相应的对象，如果有则赋值给re
								re = resultGraph.getElementByIndexObject(e.getIndex());
							} catch (Exception ex2) { // 根据e的索引没有找到result图中相应的对象，则需要添加e到图的List<ValueEdge>中

								TypedNode source = null;
								try {
									source = resultGraph.getElementByIndexObject(e.getSource().getIndex());
								} catch (NothingReturnedException e1) {
									e1.printStackTrace();
								}
//										ValueNode target = e.getTarget();

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
										try {
											throw new NothingReturnedException();
										} catch (NothingReturnedException e2) {
											e2.printStackTrace();
										}

									} else {
										re.mergeIndex(e);
										resultGraph.reindexing(re); // lyt修改
									}
								}
							}
						}
					}
				}
				System.out.println("*****新加ValueEdges: " + Profiler.end() + "ms");
			}
		});
		addValueEdgesThread.start();

		OrderInformation[] orders = new OrderInformation[branchGraphs.length];
		for (int i = 0; i < branchGraphs.length; i++)
			orders[i] = branchGraphs[i].order;
		resultGraph.order.merge(orders); // 将orders[i]合并到result的order中

		List<GraphConstraint> cons = new ArrayList<>();
		cons.add(baseGraph.getConstraint());
		for (TypedGraph g : branchGraphs) {
			cons.add(g.constraint);
		}
		resultGraph.constraint = GraphConstraint.and(cons);
		// check

		try {
			addTypedEdgesThread.join();
			addValueEdgesThread.join();
		} catch (InterruptedException e) {
		}

		return resultGraph;
	}

}
