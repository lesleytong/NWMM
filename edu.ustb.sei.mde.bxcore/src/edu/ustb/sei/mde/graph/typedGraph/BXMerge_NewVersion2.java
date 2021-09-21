package edu.ustb.sei.mde.graph.typedGraph;
/**
 * �������ͻ���
 */
import java.io.ObjectOutputStream.PutField;
/**
 * ����
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

		// �õ�TypedNodes����������
		List<TypeNode> allTypeNodes = baseGraph.getTypeGraph().getAllTypeNodes();

		// ���ֻ���ͼ��TypedNodes
		HashMap<TypeNode, ArrayList<TypedNode>> baseTypeNodeMap = new HashMap<>();
		for (int i = 0; i < allTypeNodes.size(); i++) {
			ArrayList<TypedNode> typedNodes = new ArrayList<>();
			baseTypeNodeMap.put(allTypeNodes.get(i), typedNodes);
		}
		for (TypedNode n : baseGraph.allTypedNodes) {
			baseTypeNodeMap.get(n.getType()).add(n);
		}

		// �������з�֧ͼ��TypedNodes
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

		// �¼�TypedNodes
		Profiler.begin();
		for (int i = 0; i < allTypeNodes.size(); i++) {
			for (TypedNode n : branchTypeNodeMap.get(allTypeNodes.get(i))) {
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

		// �¼�ValueNodes
		Profiler.begin();
		for (TypedGraph branchGraph : branchGraphs) {
			branchGraph.allValueNodes.forEach(v -> {
				resultGraph.addValueNode(v);
			});
		}
		System.out.println("*******�¼�ValueNodes��" + Profiler.end() + "ms");

		// ɾ�����޸�TypedNodes����ʱ���������ڵıߡ�
		Profiler.begin();
		ArrayList<TypedNode> deleteTypedNodesList = new ArrayList<>();
		HashMap<TypedNode, TypedNode> changeTypedNodesMap = new HashMap<>();
		for (int i = 0; i < allTypeNodes.size(); i++) {
			TypeNode[] nodeImages = new TypeNode[branchGraphs.length];
			for (TypedNode baseNode : baseTypeNodeMap.get(allTypeNodes.get(i))) {
				for (int j = 0; j < branchGraphs.length; j++) {
					// ������֧ͼ�ȷֱ�ͻ���ͼ���Ƚϣ�baseNode������ֱ�洢��nodeImages[i]�С�������NULL��ANY���޸ĺ������
					nodeImages[j] = TypedGraph.computeImage(baseNode, baseGraph, branchGraphs[j]);
				}
				try {
					// �ٸ���nodeImages[]�е������ȷ��baseNode��finalType
					TypeNode finalType = TypedGraph.computeType(nodeImages, baseGraph.getTypeGraph());

					if (finalType == TypeNode.NULL_TYPE) { // ��ĳһ��֧��ɾ����������֧��ɾ��������resultͼ��Ҳɾ��
						resultGraph.remove2(baseNode); // ��ʱ���������ڱ�
						deleteTypedNodesList.add(baseNode);
					} else {

						if (finalType == TypeNode.ANY_TYPE) // �˽ڵ���������֧ͼ�ͻ���ͼ�е�����һ��
							finalType = baseNode.getType();

						TypedNode n = new TypedNode(); // ������Ľڵ�
						n.setType(finalType); // �����½ڵ�n�����ͣ��ͻ���ͼ��һ�������ͻ����滻�������

						for (TypedGraph image : branchGraphs) {
							// ���½ڵ�n��������֧ͼ�ж�Ӧ��baseNode���������ϲ�
							n.mergeIndex(image.getElementByIndexObject(baseNode.getIndex()));
						}
						// ��resultͼ�е�baseNode�滻Ϊn�����������resultͼ���滻��ɾ�����ڱ�
						resultGraph.replaceWith2(baseNode, n); // ��ʱ���������ڱ�
						changeTypedNodesMap.put(baseNode, n);
					}

				} catch (NothingReturnedException e) {
					throw e; // ��׽���쳣���׳�
				}
			}
		}
		System.out.println("*******ɾ�����޸�TypedNodes��" + Profiler.end() + "ms");

		// �õ�TypedEdges����������
		List<TypeEdge> allTypeEdges = baseGraph.getTypeGraph().getAllTypeEdges();

		// ���ֻ���ͼ��TypedEdges
		HashMap<TypeEdge, ArrayList<TypedEdge>> baseTypeEdgeMap = new HashMap<>();
		for (int i = 0; i < allTypeEdges.size(); i++) {
			ArrayList<TypedEdge> typedEdges = new ArrayList<>();
			baseTypeEdgeMap.put(allTypeEdges.get(i), typedEdges);
		}
		for (TypedEdge e : baseGraph.allTypedEdges) {
			baseTypeEdgeMap.get(e.getType()).add(e);
		}

		// �������з�֧ͼ��TypedEdges
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

		// ɾ�����޸�TypedEdges
		Profiler.begin();
		for (int i = 0; i < allTypeEdges.size(); i++) {
			TypedEdge[] typedEdgeImages = new TypedEdge[branchGraphs.length];
			for (TypedEdge baseEdge : baseTypeEdgeMap.get(allTypeEdges.get(i))) {

				// �����֧ͼ�ȷֱ�ͻ���ͼ���Ƚϣ�baseEdge������ֱ�洢��typedEdgeImages[i]�У�������baseEdge��imageEdge��null
				for (int j = 0; j < branchGraphs.length; j++) {
					typedEdgeImages[j] = TypedGraph.computeImage(baseEdge, baseGraph, branchGraphs[j]);
				}

				try {
					// �ٸ���typedEdgeImages[]�е������ȷ��finalEdgeInfo
					Tuple3<TypedNode, TypedNode, TypeEdge> finalEdgeInfo = TypedGraph.computeEdge(baseEdge,
							typedEdgeImages);

					if (finalEdgeInfo == null) {
						resultGraph.remove(baseEdge);
					} else {
						// �¼�TypedNodes������TypedEdge֮ǰ
						TypedNode source = resultGraph.getElementByIndexObject(finalEdgeInfo.first.getIndex());
						TypedNode target = resultGraph.getElementByIndexObject(finalEdgeInfo.second.getIndex());
						TypedEdge edge = new TypedEdge(); // ������Ķ���edge
						edge.setSource(source);
						edge.setTarget(target);
						edge.setType(finalEdgeInfo.third);

						for (TypedGraph image : branchGraphs) { // �ϲ�������֧��������
							edge.mergeIndex(image.getElementByIndexObject(baseEdge.getIndex()));
						}

						resultGraph.replaceWith(baseEdge, edge); // ��baseEdge�滻Ϊedge
					}
				} catch (NothingReturnedException e) {
					throw e; // ��׽���쳣���׳�
				}
			}
		}
		System.out.println("*****ɾ�����޸�TypedEdges: " + Profiler.end() + "ms");

		// �õ�ValueEdges����������
		List<PropertyEdge> allPropertyEdges = baseGraph.getTypeGraph().getAllPropertyEdges();

		// ���ֻ���ͼ��ValueEdges
		HashMap<PropertyEdge, ArrayList<ValueEdge>> basePropertyEdgeMap = new HashMap<>();
		for (int i = 0; i < allPropertyEdges.size(); i++) {
			ArrayList<ValueEdge> valueEdges = new ArrayList<>();
			basePropertyEdgeMap.put(allPropertyEdges.get(i), valueEdges);
		}
		for (ValueEdge e : baseGraph.allValueEdges) {
			basePropertyEdgeMap.get(e.getType()).add(e);
		}

		// �������з�֧ͼ��ValueEdges
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

		// ɾ�����޸�ValueEdges
		Profiler.begin();
		for (int i = 0; i < allPropertyEdges.size(); i++) {
			ValueEdge[] valueEdgeImages = new ValueEdge[branchGraphs.length];
			for (ValueEdge baseEdge : basePropertyEdgeMap.get(allPropertyEdges.get(i))) { // ���ڻ���ͼ��ÿһ����ValueEdge���͵ı�

				// ������֧ͼ�Ⱥͻ���ͼ���Ƚϣ�baseEdge������ֱ�洢��valueEdgeImages[]�У�������null��baseEdge��imageEdge
				for (int j = 0; j < branchGraphs.length; j++) {
					valueEdgeImages[j] = TypedGraph.computeImage(baseEdge, baseGraph, branchGraphs[j]);
				}

				try {
					// �ٸ���valueEdgeImages[]�е������ȷ��finalEdgeInfo
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
		System.out.println("*******ɾ�����޸�ValueEdges��" + Profiler.end() + "ms");

		// TypedNodesɾ���󣬴������ڵ�TypedEdge��
		Profiler.begin();
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
		System.out.println("*****TypedNodesɾ���󣬴������ڵ�TypedEdge��: " + Profiler.end() + "ms");

		// TypedNodesɾ���󣬴������ڵ�ValueEdge��
		Profiler.begin();
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
		System.out.println("*****TypedNodesɾ���󣬴������ڵ�ValueEdge��: " + Profiler.end() + "ms");

		// TypedNodes�޸ĺ󣬴������ڵ�TypedEdge��
		Profiler.begin();
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
		System.out.println("*****TypedNodes�޸ĺ󣬴������ڵ�TypedEdge��: " + Profiler.end() + "ms");

		// TypedNodes�޸ĺ󣬴������ڵ�ValueEdge��
		Profiler.begin();
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

		// �¼�TypedEdges
		Profiler.begin();
		for (int i = 0; i < allTypeEdges.size(); i++) {
			for (TypedEdge e : branchTypeEdgeMap.get(allTypeEdges.get(i))) {
				try { // ����e���������һ���ͼ��������Ӧ�Ķ����������������
					baseGraph.getElementByIndexObject(e.getIndex());
				} catch (Exception ex) { // ����ͼ��û���ҵ���Ӧ�Ķ���
					TypedEdge re = null;
					try {
						// ����e����������resultͼ��������Ӧ�Ķ����������ֵ��re
						re = resultGraph.getElementByIndexObject(e.getIndex());
					} catch (Exception ex2) { // ����e������û���ҵ�resultͼ����Ӧ�Ķ�����Ӧ���e��resultͼ��List<TypedEdge>��

						TypedNode source = resultGraph.getElementByIndexObject(e.getSource().getIndex());
						TypedNode target = resultGraph.getElementByIndexObject(e.getTarget().getIndex());

						if (e.getSource() != source || e.getTarget() != target) { // ˵��e��source��target�ǻ���ͼ���еģ�resultͼ�ϲ�������ͼ��������������ͬһ����
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
								throw new NothingReturnedException();
							} else {
								re.mergeIndex(e);
								resultGraph.reindexing(re); // lyt�޸�
							}
						}
					}
				}
			}
		}
		System.out.println("*******�¼�TypedEdges��" + Profiler.end() + "ms");

		// �¼�ValueEdges
		Profiler.begin();
		for (int i = 0; i < allPropertyEdges.size(); i++) {
			for (ValueEdge e : branchPropertyEdgeMap.get(allPropertyEdges.get(i))) {
				try {
					// ����e���������һ���ͼ��������Ӧ�Ķ���������򲻴���
					baseGraph.getElementByIndexObject(e.getIndex());
				} catch (Exception ex) { // �������ͼ��û����Ӧ�Ķ���
					ValueEdge re = null;
					try {
						// ����e����������resultͼ��������Ӧ�Ķ����������ֵ��re
						re = resultGraph.getElementByIndexObject(e.getIndex());
					} catch (Exception ex2) { // ����e������û���ҵ�resultͼ����Ӧ�Ķ�������Ҫ���e��ͼ��List<ValueEdge>��

						TypedNode source = resultGraph.getElementByIndexObject(e.getSource().getIndex());
//								ValueNode target = e.getTarget();

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
								throw new NothingReturnedException();
							} else {
								re.mergeIndex(e);
								resultGraph.reindexing(re); // lyt�޸�
							}
						}
					}
				}
			}
		}
		System.out.println("*******�¼�TypedEdges��" + Profiler.end() + "ms");

		return resultGraph;

	}

	public static TypedGraph prepareCon(TypedGraph baseGraph, TypedGraph... branchGraphs)
			throws NothingReturnedException {

		TypedGraph resultGraph = baseGraph.getCopy();

		// �õ�TypedNodes����������
		List<TypeNode> allTypeNodes = baseGraph.getTypeGraph().getAllTypeNodes();

		// ���ֻ���ͼ��TypedNodes
		HashMap<TypeNode, ArrayList<TypedNode>> baseTypeNodeMap = new HashMap<>();
		for (int i = 0; i < allTypeNodes.size(); i++) {
			ArrayList<TypedNode> typedNodes = new ArrayList<>();
			baseTypeNodeMap.put(allTypeNodes.get(i), typedNodes);
		}
		for (TypedNode n : baseGraph.allTypedNodes) {
			baseTypeNodeMap.get(n.getType()).add(n);
		}

		// �������з�֧ͼ��TypedNodes
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

		// �¼�TypedNodes
		Profiler.begin();
		HashMap<TypeNode, Thread> addTypedNodesMap = new HashMap<>();
		for (int i = 0; i < allTypeNodes.size(); i++) {
			TypeNode typeNode = allTypeNodes.get(i);
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					for (TypedNode n : branchTypeNodeMap.get(typeNode)) {
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
			});
			addTypedNodesMap.put(typeNode, thread);
			thread.start();
		}
		System.out.println("*****�¼�TypedNodes: " + Profiler.end() + "ms");

		// �¼�ValueNodes
		Profiler.begin();
		for (TypedGraph branchGraph : branchGraphs) {
			branchGraph.allValueNodes.forEach(v -> {
				resultGraph.addValueNode(v);
			});
		}
		System.out.println("*******�¼�ValueNodes��" + Profiler.end() + "ms");


		// ɾ�����޸�TypedNodes����ʱ���������ڵıߡ�
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
							// ������֧ͼ�ȷֱ�ͻ���ͼ���Ƚϣ�baseNode������ֱ�洢��nodeImages[i]�С�������NULL��ANY���޸ĺ������
							nodeImages[j] = TypedGraph.computeImage(baseNode, baseGraph, branchGraphs[j]);
						}
						try {
							// �ٸ���nodeImages[]�е������ȷ��baseNode��finalType
							TypeNode finalType = TypedGraph.computeType(nodeImages, baseGraph.getTypeGraph());

							if (finalType == TypeNode.NULL_TYPE) { // ��ĳһ��֧��ɾ����������֧��ɾ��������resultͼ��Ҳɾ��
								resultGraph.remove2(baseNode); // ��ʱ���������ڱ�
								deleteTypedNodesList.add(baseNode);
							} else {

								if (finalType == TypeNode.ANY_TYPE) // �˽ڵ���������֧ͼ�ͻ���ͼ�е�����һ��
									finalType = baseNode.getType();

								TypedNode n = new TypedNode(); // ������Ľڵ�
								n.setType(finalType); // �����½ڵ�n�����ͣ��ͻ���ͼ��һ�������ͻ����滻�������

								for (TypedGraph image : branchGraphs) {
									// ���½ڵ�n��������֧ͼ�ж�Ӧ��baseNode���������ϲ�
									n.mergeIndex(image.getElementByIndexObject(baseNode.getIndex()));
								}
								// ��resultͼ�е�baseNode�滻Ϊn�����������resultͼ���滻��ɾ�����ڱ�
								resultGraph.replaceWith2(baseNode, n); // ��ʱ���������ڱ�
								changeTypedNodesMap.put(baseNode, n);
							}

						} catch (NothingReturnedException e) {
							e.printStackTrace();
						}
					}
					System.out.println("*******ɾ�����޸�" + typeNode + "TypedNodes��" + Profiler.end() + "ms");
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
		

		// �õ�TypedEdges����������
		List<TypeEdge> allTypeEdges = baseGraph.getTypeGraph().getAllTypeEdges();

		// ���ֻ���ͼ��TypedEdges
		HashMap<TypeEdge, ArrayList<TypedEdge>> baseTypeEdgeMap = new HashMap<>();
		for (int i = 0; i < allTypeEdges.size(); i++) {
			ArrayList<TypedEdge> typedEdges = new ArrayList<>();
			baseTypeEdgeMap.put(allTypeEdges.get(i), typedEdges);
		}
		for (TypedEdge e : baseGraph.allTypedEdges) {
			baseTypeEdgeMap.get(e.getType()).add(e);
		}

		// �������з�֧ͼ��TypedEdges
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

		// ɾ�����޸�TypedEdges
		Profiler.begin();
		for (int i = 0; i < allTypeEdges.size(); i++) {
			TypedEdge[] typedEdgeImages = new TypedEdge[branchGraphs.length];
			for (TypedEdge baseEdge : baseTypeEdgeMap.get(allTypeEdges.get(i))) {

				// �����֧ͼ�ȷֱ�ͻ���ͼ���Ƚϣ�baseEdge������ֱ�洢��typedEdgeImages[i]�У�������baseEdge��imageEdge��null
				for (int j = 0; j < branchGraphs.length; j++) {
					typedEdgeImages[j] = TypedGraph.computeImage(baseEdge, baseGraph, branchGraphs[j]);
				}

				try {
					// �ٸ���typedEdgeImages[]�е������ȷ��finalEdgeInfo
					Tuple3<TypedNode, TypedNode, TypeEdge> finalEdgeInfo = TypedGraph.computeEdge(baseEdge,
							typedEdgeImages);

					if (finalEdgeInfo == null) {
						resultGraph.remove(baseEdge);
					} else {
						// �¼�TypedNodes������TypedEdge֮ǰ
						TypedNode source = resultGraph.getElementByIndexObject(finalEdgeInfo.first.getIndex());
						TypedNode target = resultGraph.getElementByIndexObject(finalEdgeInfo.second.getIndex());
						TypedEdge edge = new TypedEdge(); // ������Ķ���edge
						edge.setSource(source);
						edge.setTarget(target);
						edge.setType(finalEdgeInfo.third);

						for (TypedGraph image : branchGraphs) { // �ϲ�������֧��������
							edge.mergeIndex(image.getElementByIndexObject(baseEdge.getIndex()));
						}

						resultGraph.replaceWith(baseEdge, edge); // ��baseEdge�滻Ϊedge
					}
				} catch (NothingReturnedException e) {
					throw e; // ��׽���쳣���׳�
				}
			}
		}
		System.out.println("*****ɾ�����޸�TypedEdges: " + Profiler.end() + "ms");

		// �õ�ValueEdges����������
		List<PropertyEdge> allPropertyEdges = baseGraph.getTypeGraph().getAllPropertyEdges();

		// ���ֻ���ͼ��ValueEdges
		HashMap<PropertyEdge, ArrayList<ValueEdge>> basePropertyEdgeMap = new HashMap<>();
		for (int i = 0; i < allPropertyEdges.size(); i++) {
			ArrayList<ValueEdge> valueEdges = new ArrayList<>();
			basePropertyEdgeMap.put(allPropertyEdges.get(i), valueEdges);
		}
		for (ValueEdge e : baseGraph.allValueEdges) {
			basePropertyEdgeMap.get(e.getType()).add(e);
		}

		// �������з�֧ͼ��ValueEdges
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

		// ɾ�����޸�ValueEdges
		Profiler.begin();
		for (int i = 0; i < allPropertyEdges.size(); i++) {
			ValueEdge[] valueEdgeImages = new ValueEdge[branchGraphs.length];
			for (ValueEdge baseEdge : basePropertyEdgeMap.get(allPropertyEdges.get(i))) { // ���ڻ���ͼ��ÿһ����ValueEdge���͵ı�

				// ������֧ͼ�Ⱥͻ���ͼ���Ƚϣ�baseEdge������ֱ�洢��valueEdgeImages[]�У�������null��baseEdge��imageEdge
				for (int j = 0; j < branchGraphs.length; j++) {
					valueEdgeImages[j] = TypedGraph.computeImage(baseEdge, baseGraph, branchGraphs[j]);
				}

				try {
					// �ٸ���valueEdgeImages[]�е������ȷ��finalEdgeInfo
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
		System.out.println("*******ɾ�����޸�ValueEdges��" + Profiler.end() + "ms");

		// TypedNodesɾ���󣬴������ڵ�TypedEdge��
		Profiler.begin();
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
		System.out.println("*****TypedNodesɾ���󣬴������ڵ�TypedEdge��: " + Profiler.end() + "ms");

		// TypedNodesɾ���󣬴������ڵ�ValueEdge��
		Profiler.begin();
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
		System.out.println("*****TypedNodesɾ���󣬴������ڵ�ValueEdge��: " + Profiler.end() + "ms");

		// TypedNodes�޸ĺ󣬴������ڵ�TypedEdge��
		Profiler.begin();
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
		System.out.println("*****TypedNodes�޸ĺ󣬴������ڵ�TypedEdge��: " + Profiler.end() + "ms");

		// TypedNodes�޸ĺ󣬴������ڵ�ValueEdge��
		Profiler.begin();
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

		// �¼�TypedEdges
		Profiler.begin();
		for (int i = 0; i < allTypeEdges.size(); i++) {
			for (TypedEdge e : branchTypeEdgeMap.get(allTypeEdges.get(i))) {
				try { // ����e���������һ���ͼ��������Ӧ�Ķ����������������
					baseGraph.getElementByIndexObject(e.getIndex());
				} catch (Exception ex) { // ����ͼ��û���ҵ���Ӧ�Ķ���
					TypedEdge re = null;
					try {
						// ����e����������resultͼ��������Ӧ�Ķ����������ֵ��re
						re = resultGraph.getElementByIndexObject(e.getIndex());
					} catch (Exception ex2) { // ����e������û���ҵ�resultͼ����Ӧ�Ķ�����Ӧ���e��resultͼ��List<TypedEdge>��

						TypedNode source = resultGraph.getElementByIndexObject(e.getSource().getIndex());
						TypedNode target = resultGraph.getElementByIndexObject(e.getTarget().getIndex());

						if (e.getSource() != source || e.getTarget() != target) { // ˵��e��source��target�ǻ���ͼ���еģ�resultͼ�ϲ�������ͼ��������������ͬһ����
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
								throw new NothingReturnedException();
							} else {
								re.mergeIndex(e);
								resultGraph.reindexing(re); // lyt�޸�
							}
						}
					}
				}
			}
		}
		System.out.println("*******�¼�TypedEdges��" + Profiler.end() + "ms");

		// �¼�ValueEdges
		Profiler.begin();
		for (int i = 0; i < allPropertyEdges.size(); i++) {
			for (ValueEdge e : branchPropertyEdgeMap.get(allPropertyEdges.get(i))) {
				try {
					// ����e���������һ���ͼ��������Ӧ�Ķ���������򲻴���
					baseGraph.getElementByIndexObject(e.getIndex());
				} catch (Exception ex) { // �������ͼ��û����Ӧ�Ķ���
					ValueEdge re = null;
					try {
						// ����e����������resultͼ��������Ӧ�Ķ����������ֵ��re
						re = resultGraph.getElementByIndexObject(e.getIndex());
					} catch (Exception ex2) { // ����e������û���ҵ�resultͼ����Ӧ�Ķ�������Ҫ���e��ͼ��List<ValueEdge>��

						TypedNode source = resultGraph.getElementByIndexObject(e.getSource().getIndex());
						// ValueNode target = e.getTarget();

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
								throw new NothingReturnedException();
							} else {
								re.mergeIndex(e);
								resultGraph.reindexing(re); // lyt�޸�
							}
						}
					}
				}
			}
		}
		System.out.println("*******�¼�TypedEdges��" + Profiler.end() + "ms");

		return resultGraph;

	}
	

}
