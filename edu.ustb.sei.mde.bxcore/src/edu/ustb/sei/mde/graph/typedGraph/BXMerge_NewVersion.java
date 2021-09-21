package edu.ustb.sei.mde.graph.typedGraph;
/**
 * ����������join()
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

	/** ��·�ϲ� */
	public static TypedGraph merge(TypedGraph baseGraph, TypedGraph... branchGraphs) throws NothingReturnedException {

		TypedGraph resultGraph = baseGraph.getCopy();

		Thread addTypedNodesThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Profiler.begin();
				// �¼�TypedNodes
				for (TypedGraph branchGraph : branchGraphs) {
					for (TypedNode n : branchGraph.allTypedNodes) { // ���ڷ�֧ͼ��ÿ��TypedNode���͵Ľڵ�n
						try {
							// ����n���������һ���ͼ��������Ӧ�Ķ����������������
							baseGraph.getElementByIndexObject(n.getIndex());
						} catch (NothingReturnedException e) { // ����ڻ���ͼ��û���ҵ���Ӧ�Ķ���
							TypedNode rn = null;
							try {
								// ����n����������resultͼ��������Ӧ�Ķ�������о͸�ֵ��rn
								rn = resultGraph.getElementByIndexObject(n.getIndex());
							} catch (NothingReturnedException e1) { // ���resultͼ������Ӧ�Ķ�����n������ӵ�resultͼ��List<TypedNode>��������rn=null
								resultGraph.addTypedNode(n);
								rn = null;
							} finally {

								if (rn != null) { // ���rn!=null��˵������n�������ҵ���resultͼ����Ӧ�Ķ����Ҹ�ֵ����rn
									TypeNode lt = rn.getType();
									TypeNode rt = n.getType();
									TypeNode ct = baseGraph.getTypeGraph().computeSubtype(lt, rt); // ���յ�����

									if (ct == TypeNode.NULL_TYPE) {
										try {
											throw new NothingReturnedException(); // incompatible
										} catch (NothingReturnedException e2) {
											e2.printStackTrace();
										}
									} else {
										rn.setType(ct); // �����ݣ���rn��type����Ϊct
										rn.mergeIndex(n);
										resultGraph.reindexing(rn); // lyt-�޸�
									}
								}
							}
						}
					}
				}
				System.out.println("*****�¼�TypedNodes: " + Profiler.end() + "ms");
			}

		});
		addTypedNodesThread.start();

		Thread addValueNodesThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Profiler.begin();
				// �¼�ValueNodes
				for (TypedGraph branch : branchGraphs) {
					branch.allValueNodes.forEach(v -> {
						resultGraph.addValueNode(v);
					});
				}
				System.out.println("*****�¼�ValueNodes: " + Profiler.end() + "ms");
			}
		});
		addValueNodesThread.start();

		// ɾ��TypedEdges
		HashMap<TypedEdge, Tuple3<TypedNode, TypedNode, TypeEdge>> changeTypedEdgeMap = new HashMap<>();
		Thread deleteTypedEdgesThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Profiler.begin();
				TypedEdge[] typedEdgeImages = new TypedEdge[branchGraphs.length];
				for (TypedEdge baseEdge : baseGraph.allTypedEdges) { // ���ڻ���ͼ��ÿһ��TypedEdge���͵ı�baseEdge

					// �����֧ͼ�ȷֱ�ͻ���ͼ���Ƚϣ�baseEdge������ֱ�洢��typedEdgeImages[i]�У�������baseEdge��imageEdge��null
					for (int i = 0; i < branchGraphs.length; i++) {
						typedEdgeImages[i] = TypedGraph.computeImage(baseEdge, baseGraph, branchGraphs[i]);
					}

					try {
						// �ٸ���typedEdgeImages[]�е������ȷ��finalEdgeInfo
						Tuple3<TypedNode, TypedNode, TypeEdge> finalEdgeInfo = TypedGraph.computeEdge(baseEdge,
								typedEdgeImages);

						if (finalEdgeInfo == null) {
							resultGraph.remove(baseEdge);
						} else {
							// ����ͼ�������֧ͼ��baseEdgeһ�£����߲�ͬ��֧ͼ�������޸ĵ��໥����
							changeTypedEdgeMap.put(baseEdge, finalEdgeInfo);
						}
					} catch (NothingReturnedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("*****ɾ��TypedEdges: " + Profiler.end() + "ms");
			}
		});
		deleteTypedEdgesThread.start();

		// ɾ��ValueEdges
		HashMap<ValueEdge, Tuple3<TypedNode, ValueNode, PropertyEdge>> changeValueEdgeMap = new HashMap<>();
		Thread deleteValueEdgesThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Profiler.begin();
				ValueEdge[] valueEdgeImages = new ValueEdge[branchGraphs.length];
				for (ValueEdge baseEdge : baseGraph.allValueEdges) { // ���ڻ���ͼ��ÿһ����ValueEdge���͵ı�
					// ������֧ͼ�Ⱥͻ���ͼ���Ƚϣ�baseEdge������ֱ�洢��valueEdgeImages[]�У�������null��baseEdge��imageEdge
					for (int i = 0; i < branchGraphs.length; i++) {
						valueEdgeImages[i] = TypedGraph.computeImage(baseEdge, baseGraph, branchGraphs[i]);
					}

					try {
						// �ٸ���valueEdgeImages[]�е������ȷ��finalEdgeInfo
						Tuple3<TypedNode, ValueNode, PropertyEdge> finalEdgeInfo = TypedGraph.computeEdge(baseEdge,
								valueEdgeImages);

						if (finalEdgeInfo == null) {
							resultGraph.remove(baseEdge);
						} else {
							// ����ͼ�������֧ͼ��baseEdgeһ�£����߲�ͬ��֧ͼ�������޸ĵ��໥����
							changeValueEdgeMap.put(baseEdge, finalEdgeInfo);
						}
					} catch (NothingReturnedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("*****ɾ��ValueEdges: " + Profiler.end() + "ms");
			}
		});
		deleteValueEdgesThread.start();

		// ɾ�����޸�TypedNodes
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
				TypeNode[] nodeImages = new TypeNode[branchGraphs.length]; // ����length=2
				for (TypedNode baseNode : baseGraph.allTypedNodes) { // ���ڻ���ͼ��ÿһ��TypedNode�ڵ�

					for (int i = 0; i < branchGraphs.length; i++) {
						// ������֧ͼ�ȷֱ�ͻ���ͼ���Ƚϣ�baseNode������ֱ�洢��nodeImages[i]�С�������NULL��ANY���޸ĺ������
						nodeImages[i] = TypedGraph.computeImage(baseNode, baseGraph, branchGraphs[i]);
					}

					try {
						// �ٸ���nodeImages[]�е������ȷ��baseNode��finalType
						TypeNode finalType = TypedGraph.computeType(nodeImages, baseGraph.getTypeGraph());

						if (finalType == TypeNode.NULL_TYPE) { // ��ĳһ��֧��ɾ����������֧��ɾ��������resultͼ��Ҳɾ��
							deleteTypedNodesList.add(baseNode);
							resultGraph.remove2(baseNode); // ��ʱֻɾ���ڵ㣬��δ�������ڱ�
						} else {

							if (finalType == TypeNode.ANY_TYPE) // �˽ڵ���������֧ͼ�ͻ���ͼ�е�����һ��
								finalType = baseNode.getType();

							TypedNode n = new TypedNode(); // ������Ľڵ�
							n.setType(finalType); // �����½ڵ�n�����ͣ��ͻ���ͼ��һ�������ͻ����滻�������
							for (TypedGraph branch : branchGraphs) {
								// ���½ڵ�n��������֧ͼ�ж�Ӧ��baseNode���������ϲ�
								n.mergeIndex(branch.getElementByIndexObject(baseNode.getIndex()));
							}
							// ��resultͼ�е�baseNode�滻Ϊn�����������resultͼ���滻��ɾ�����ڱ�
							changeTypedNodesMap.put(baseNode, n);
							resultGraph.replaceWith2(baseNode, n); // ��ʱֻ�滻�ڵ㣬��δ�������ڱ�
						}

					} catch (NothingReturnedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("*****ɾ�����޸�TypedNodes: " + Profiler.end() + "ms");
			}
		});
		deleteAndChangeTypedNodesThread.start();

		// �޸�TypedEdges
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
						TypedEdge edge = new TypedEdge(); // ������Ķ���edge
						edge.setSource(source);
						edge.setTarget(target);
						edge.setType(value.third);

						for (TypedGraph image : branchGraphs) { // �ϲ�������֧��������
							edge.mergeIndex(image.getElementByIndexObject(key.getIndex()));
						}

						resultGraph.replaceWith(key, edge); // ��baseEdge�滻Ϊedge

					} catch (NothingReturnedException e) {
						e.printStackTrace();
					}
				});
				System.out.println("*****�޸�TypedEdges: " + Profiler.end() + "ms");
			}
		});
		changeTypedEdgesThread.start();

		// �޸�ValueEdges
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
				System.out.println("*****�޸�ValueEdges: " + Profiler.end() + "ms");
			}
		});
		changeValueEdgesThread.start();

		// �������ڵ�TypedEdge��
		Thread adjacentTypedEdgesThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Profiler.begin();
				try {
					changeTypedEdgesThread.join();
				} catch (InterruptedException e) {
				}
				deleteTypedNodesList.forEach(n -> {
					// ����˽ڵ㻹��ĳ��TypedEdge���ͱ�e��source��target�˵㣬ɾ��������e
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
						// �������ڵ�TypedEdge���͵ı�
						List<TypedEdge> removedTypedEdges = new ArrayList<TypedEdge>();
						resultGraph.allTypedEdges.replaceAll(e -> { // ����resultͼ��List<TypedEdge>��ÿ��Ԫ��e
							TypeEdge edgeType = e.getType(); // �Ȼ�ȡe��type
							// �ٸ���edgeType���Ի��sourceType��targetType
							TypeNode sourceType = edgeType.getSource();
							TypeNode targetType = edgeType.getTarget();

							if (e.getSource() == nr && e.getTarget() == nr) { // 1.���e��source��target����nr�ڵ�
								// ����n��type(nodeType)��e��sourceType��targetType��������
								if (resultGraph.typeGraph.isSuperTypeOf(nodeType, sourceType)
										&& resultGraph.typeGraph.isSuperTypeOf(nodeType, targetType)) {
									TypedEdge res = new TypedEdge();
									res.setSource(n);
									res.setTarget(n);
									res.setType(edgeType);
									res.mergeIndex(e);
									resultGraph.reindexing(res);
									return res; // ��e�滻Ϊres
								} else { // ����n��type����e��sourceType |(&) targetType��������
									removedTypedEdges.add(e); // ��e��ӵ�removedTypedEdges������
									resultGraph.clearIndex(e); // ��Ԫ��e����������e��ӳ���ϵ����indexToObjectMap�����
									return e; // ��������ʱe->e
								}
							} else if (e.getSource() == nr) { // 2. ��e��source��nr��e��target����nr
								// ����n��type(nodeType)��e��sourceType��������
								if (resultGraph.typeGraph.isSuperTypeOf(nodeType, sourceType)) {
									TypedEdge res = new TypedEdge();
									res.setSource(n);
									res.setTarget(e.getTarget());
									res.setType(edgeType);
									res.mergeIndex(e);
									resultGraph.reindexing(res);
									return res;
								} else { // ����n��type����e��sourceType��������
									removedTypedEdges.add(e);
									resultGraph.clearIndex(e);
									return e;
								}
							} else if (e.getTarget() == nr) { // 3. ��e��target��nr��e��source����nr
								// ����n��type(nodeType)��e��targetType��������
								if (resultGraph.typeGraph.isSuperTypeOf(nodeType, targetType)) {
									TypedEdge res = new TypedEdge();
									res.setSource(e.getSource());
									res.setTarget(n);
									res.setType(edgeType);
									res.mergeIndex(e);
									resultGraph.reindexing(res);
									return res;
								} else { // ����n��type(nodeType)����e��targetType��������
									removedTypedEdges.add(e);
									resultGraph.clearIndex(e);
									return e;
								}
							} else // 4. e��source����nr��targetҲ����nr
								return e;
						});
						resultGraph.allTypedEdges.removeAll(removedTypedEdges); // ���ϲ���removeAll()��A <- A-B������A������ɾ��B����
					}
				});
				System.out.println("*****�������ڵ�TypedEdge��: " + Profiler.end() + "ms");
			}
		});
		adjacentTypedEdgesThread.start();

		// �������ڵ�ValueEdge��
		Thread adjacentValueEdgesThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Profiler.begin();
				try {
					changeValueEdgesThread.join();
				} catch (InterruptedException e) {
				}

				deleteTypedNodesList.forEach(n -> {
					// ����˽ڵ㻹��ĳ��ValueEdge���ͱ�e��source�˵㣬ɾ��������e
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
						// �������ڵ�ValueEdge���͵ı�
						List<ValueEdge> removedValueEdges = new ArrayList<ValueEdge>();
						resultGraph.allValueEdges.replaceAll(e -> { // ����resultͼ��List<ValueEdge>��ÿһ����e
							PropertyEdge edgeType = e.getType();
							TypeNode sourceType = edgeType.getSource();

							if (e.getSource() == nr) { // ���e��source��nr
								// ����n��type��e��sourceType�������ͣ���e�滻Ϊres
								if (resultGraph.typeGraph.isSuperTypeOf(nodeType, sourceType)) {
									ValueEdge res = new ValueEdge();
									res.setSource(n);
									res.setTarget(e.getTarget());
									res.setType(edgeType);
									res.mergeIndex(e);
									resultGraph.reindexing(res);
									return res;
								} else {
									// ����n��type��nr��type�����ݣ���ɾ��e
									removedValueEdges.add(e);
									resultGraph.clearIndex(e);
									return e;
								}
							} else
								return e; // ���e��source����nr������
						});
						resultGraph.allValueEdges.removeAll(removedValueEdges);
					}
				});
				System.out.println("*****�������ڵ�ValueEdge��: " + Profiler.end() + "ms");
			}
		});
		adjacentValueEdgesThread.start();

		// �¼�TypedEdges
		Thread addTypedEdgesThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Profiler.begin();
				try {
					adjacentTypedEdgesThread.join();
				} catch (InterruptedException e) {
				}

				for (TypedGraph image : branchGraphs) {
					for (TypedEdge e : image.allTypedEdges) { // ���ڷ�֧ͼ��ÿһ��TypedEdge��e
						try { // ����e���������һ���ͼ��������Ӧ�Ķ����������������
							baseGraph.getElementByIndexObject(e.getIndex());
						} catch (Exception ex) { // ����ͼ��û���ҵ���Ӧ�Ķ���
							TypedEdge re = null;
							try {
								// ����e����������resultͼ��������Ӧ�Ķ����������ֵ��re
								re = resultGraph.getElementByIndexObject(e.getIndex());
							} catch (Exception ex2) { // ����e������û���ҵ�resultͼ����Ӧ�Ķ�����Ӧ���e��resultͼ��List<TypedEdge>��

								TypedNode source = null;
								TypedNode target = null;
								try {
									source = resultGraph.getElementByIndexObject(e.getSource().getIndex());
									target = resultGraph.getElementByIndexObject(e.getTarget().getIndex());
								} catch (NothingReturnedException e1) {
									e1.printStackTrace();
								}

								if (e.getSource() != source || e.getTarget() != target) { // ˵��e��source����target�ǻ���ͼ���еģ�resultͼ�ϲ�������ͼ��������������ͬһ����
									TypedEdge ne = new TypedEdge();
									ne.setSource(source);
									ne.setTarget(target);
									ne.setType(e.getType());
									ne.mergeIndex(e);
									resultGraph.addTypedEdge(ne);
								} else // ˵��e��source��target���ڷ�֧ͼ������ӵ�TypedNode���ͽڵ㣬�����ڷ�֧ͼ��resultͼ����ͬһ����
									resultGraph.addTypedEdge(e);

								re = null;

							} finally {

								// ����e�������ҵ���resultͼ����Ӧ�Ķ��󣬲���ֵ����re
								// ˵�������֧ͼ�������ͬ�ı�
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
										resultGraph.reindexing(re); // lyt�޸�
									}
								}
							}
						}
					}
				}
				System.out.println("*****�¼�TypedEdges: " + Profiler.end() + "ms");
			}
		});
		addTypedEdgesThread.start();

		// �¼�ValueEdges
		// ��TypedNode���¼ӡ�ɾ�����滻ִ���꣨���ڵ�ValueEdge�ߴ����꣩����ValueNode���¼�ִ����
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
					for (ValueEdge e : image.allValueEdges) { // ���ڷ�֧ͼ�е�ÿһ��ValueEdge��e
						try {
							// ����e���������һ���ͼ��������Ӧ�Ķ���������򲻴���
							baseGraph.getElementByIndexObject(e.getIndex());
						} catch (Exception ex) { // �������ͼ��û����Ӧ�Ķ���
							ValueEdge re = null;
							try {
								// ����e����������resultͼ��������Ӧ�Ķ����������ֵ��re
								re = resultGraph.getElementByIndexObject(e.getIndex());
							} catch (Exception ex2) { // ����e������û���ҵ�resultͼ����Ӧ�Ķ�������Ҫ���e��ͼ��List<ValueEdge>��

								TypedNode source = null;
								try {
									source = resultGraph.getElementByIndexObject(e.getSource().getIndex());
								} catch (NothingReturnedException e1) {
									e1.printStackTrace();
								}
//										ValueNode target = e.getTarget();

								if (e.getSource() != source) { // source�ǻ���ͼ���еģ�resultͼ�ϲ�������ͼ����������������ͬһ����
									ValueEdge ne = new ValueEdge();
									ne.setSource(source);
									ne.setTarget(e.getTarget());
									ne.setType(e.getType());
									ne.mergeIndex(e);
									resultGraph.addValueEdge(ne);
								} else // e��source��resultͼ����ͬһ����˵��������ӵ�TypedNode����
									resultGraph.addValueEdge(e);

								re = null;
							} finally {

								if (re != null) { // re!=null��˵��resultͼ���ҵ�����Ӧ�Ķ��󣬲��Ҹ�ֵ����re
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
										resultGraph.reindexing(re); // lyt�޸�
									}
								}
							}
						}
					}
				}
				System.out.println("*****�¼�ValueEdges: " + Profiler.end() + "ms");
			}
		});
		addValueEdgesThread.start();

		OrderInformation[] orders = new OrderInformation[branchGraphs.length];
		for (int i = 0; i < branchGraphs.length; i++)
			orders[i] = branchGraphs[i].order;
		resultGraph.order.merge(orders); // ��orders[i]�ϲ���result��order��

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
