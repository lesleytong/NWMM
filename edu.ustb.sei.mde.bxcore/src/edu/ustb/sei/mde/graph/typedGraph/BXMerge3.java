package edu.ustb.sei.mde.graph.typedGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * �𲽹������ͼ��
 * Ч����߸����ˡ�
 */

import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.graph.type.PropertyEdge;
import edu.ustb.sei.mde.graph.type.TypeEdge;
import edu.ustb.sei.mde.graph.type.TypeNode;
import edu.ustb.sei.mde.structure.Tuple2;
import edu.ustb.sei.mde.structure.Tuple3;

public class BXMerge3 {

	/** ����Ժϲ� */
	public static TypedGraph additiveMerge(TypedGraph baseGraph, TypedGraph graph) {

		// resultͼΪ����ͼ�ĸ���
		TypedGraph resultGraph = baseGraph.getCopy();

		// ���ڵڶ���ͼgraph�е�����ӵ�ValueNode���͵Ľڵ�n��ֱ���ӵ�resultͼ��
		long start = System.currentTimeMillis();
		graph.getAllValueNodes().forEach(n -> {
			resultGraph.addValueNode(n);
		});
		System.out.println("����ValueNodes��" + (System.currentTimeMillis() - start));

		// ���ڵڶ���ͼgraph�е�ÿ��TypedNode���͵Ľڵ�n
		start = System.currentTimeMillis();
		HashMap<TypedNode, TypedNode> changeTypedNodesMap = new HashMap<>();
		graph.getAllTypedNodes().forEach(n -> {
			try {
				// ����n����������resultͼ���Ƿ�����Ӧ�Ķ�������ҵ����丳ֵ��nr
				TypedNode nr = resultGraph.getElementByIndexObject(n.getIndex());
				// ���nr==n����������
				// ���nr!=n����nr�滻Ϊn����˵���ڵ㱻�滻�ˣ�����ͬ�����������ϲ��ˣ�
				if (nr != n) {
					resultGraph.replaceWith2(nr, n);
					changeTypedNodesMap.put(nr, n);
				}
			} catch (NothingReturnedException e) {
				// �������n�������ڻ���ͼ��û���ҵ���˵��n�ǵڶ���ͼgraph������ӵĽڵ㣬������ӵ�resultͼ��
				resultGraph.addTypedNode(n);
			}
		});
		System.out.println("����TypedNodes: " + (System.currentTimeMillis() - start));

		// �����ڱߵĴ����õ�������
		start = System.currentTimeMillis();
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
		System.out.println("���д������ڱ�: " + (System.currentTimeMillis() - start));

		// ���ڵڶ���ͼgraph��ÿ��TypedEdge���͵ı�e
		start = System.currentTimeMillis();
		graph.getAllTypedEdges().forEach(e -> {
			try {
				// ����e������������resultͼ�е�TypedEdge���͵ıߣ�����ҵ���ֵ��er
				TypedEdge er = resultGraph.getElementByIndexObject(e.getIndex());
				// ���er==e�������������er!=e����er�滻Ϊe
				if (er != e) {
					resultGraph.replaceWith(er, e);
				}
			} catch (NothingReturnedException ex) {
				// �������e������û���ҵ�resultͼ�еıߣ�˵��e���ݻ�ͼ������ӵģ�������ӵ�resultͼ��
				resultGraph.addTypedEdge(e);
			}
		});
		System.out.println("����TypedEdges: " + (System.currentTimeMillis() - start));

		// ���ڵڶ���ͼgraph��ÿ��ValueEdge���͵ı�e
		start = System.currentTimeMillis();
		graph.getAllValueEdges().forEach(e -> {
			try {
				// ����e������������resultͼ�еıߣ�����ҵ���ֵ��er
				ValueEdge er = resultGraph.getElementByIndexObject(e.getIndex());
				// ���er==e�������������er!=e����er�滻Ϊe
				if (er != e) {
					resultGraph.replaceWith(er, e);
				}
			} catch (NothingReturnedException ex) {
				// �������e������û���ҵ�resultͼ�еıߣ�˵��e��graphͼ���¼ӵģ������e��resultͼ��
				resultGraph.addValueEdge(e);
			}
		});
		System.out.println("����ValueEdges: " + (System.currentTimeMillis() - start));

		return resultGraph;
	}

	public static List<TypedEdge> twoOrder(TypedGraph baseGraph, TypedGraph aGraph, TypedGraph resultGraph) {

		List<TypedEdge> aList = aGraph.getAllTypedEdges();
		ArrayList<TypedEdge> mergeList = new ArrayList<>(aList); // ����Ժϲ����滻��Ҳû�������¶���
		List<TypedEdge> resultList = resultGraph.getAllTypedEdges();
		List<TypedEdge> baseList = baseGraph.getAllTypedEdges();

		// ɾ��merge�еĲ���result�еıߣ���ΪaList�д��ڵı߲�һ����resultList�У�
		for (int i = 0; i < aList.size(); i++) {
			try {
				resultGraph.getElementByIndexObject(aList.get(i).getIndex());
			} catch (NothingReturnedException e) {
				mergeList.remove(aList.get(i));
			}
		}

		// ���˺����merge.size() == result.size()����ֱ�ӷ���merge
		if (mergeList.size() == resultList.size()) {
			return mergeList;
		}

		// ��baseList��ԭ�еı���ӽ�merge����aList��ɾ��������Ч��
		for (int i = 0; i < baseList.size(); i++) {

			try {
				aGraph.getElementByIndexObject(baseList.get(i).getIndex());
				// ���ҵ�������
				continue;
			} catch (NothingReturnedException e) {
				// �����ҵ���˵��Ҫ��ӽ�aList
				int j = 0;
				for (j = 0; j < mergeList.size(); j++) {
					try {
						TypedEdge ebase = baseGraph.getElementByIndexObject(mergeList.get(j).getIndex());
						if (i - baseList.indexOf(ebase) > 0) {
							continue;
						} else {
							mergeList.add(j, baseList.get(i)); // ����Ժϲ����¼Ӳ�û�������¶���
							break;
						}
					} catch (NothingReturnedException e1) {
					}
				}
				// ���Ѿ�����mergeList����ӵ�mergeList��β
				if (j == mergeList.size()) {
					mergeList.add(baseList.get(i));
				}
			}
		}

		return mergeList;
	}

	/** ��·�ϲ� */
	public static TypedGraph mergeOrigin(TypedGraph baseGraph, TypedGraph... interSources)
			throws NothingReturnedException {

		TypedGraph resultGraph = new TypedGraph(baseGraph.getTypeGraph());

		// �¼�TypedNodes
		Profiler.begin();
		for (TypedGraph image : interSources) {
			for (TypedNode n : image.allTypedNodes) { // ���ڷ�֧ͼ��ÿ��TypedNode���͵Ľڵ�n
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
								throw new NothingReturnedException(); // incompatible
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
		System.out.println("*******�����¼�TypedNodes��" + Profiler.end() + "ms");

		// �¼�ValueNodes
		Profiler.begin();
		baseGraph.allValueNodes.forEach(v -> {
			resultGraph.addValueNode(v);
		});
		for (TypedGraph image : interSources) {
			image.allValueNodes.forEach(v -> {
				resultGraph.addValueNode(v);
			});
		}
		System.out.println("*******�����¼�ValueNodes��" + Profiler.end() + "ms");

		// ��������޸ĵ�TypedNodes��ӵ����ͼ��
		Profiler.begin();
		TypeNode[] nodeImages = new TypeNode[interSources.length];
		for (TypedNode baseNode : baseGraph.allTypedNodes) { // ���ڻ���ͼ��ÿһ��TypedNode�ڵ�

			for (int i = 0; i < interSources.length; i++) {
				// ������֧ͼ�ȷֱ�ͻ���ͼ���Ƚϣ�baseNode������ֱ�洢��nodeImages[i]�С�������NULL��ANY���޸ĺ������
				nodeImages[i] = TypedGraph.computeImage(baseNode, baseGraph, interSources[i]);
			}

			try {
				// �ٸ���nodeImages[]�е������ȷ��baseNode��finalType
				TypeNode finalType = TypedGraph.computeType(nodeImages, baseGraph.getTypeGraph());

				if (finalType != TypeNode.NULL_TYPE) {
					if (finalType == TypeNode.ANY_TYPE) {
						finalType = baseNode.getType();
					}

					TypedNode n = new TypedNode(); // ������Ľڵ�
					n.setType(finalType); // �����½ڵ�n�����ͣ��ͻ���ͼ��һ�������ͻ����޸ĺ������

					for (TypedGraph image : interSources) {
						// ���½ڵ�n�����з�֧ͼ�ж�Ӧ��baseNode���������ϲ�
						n.mergeIndex(image.getElementByIndexObject(baseNode.getIndex()));
					}

					resultGraph.addTypedNode(n);
				}

			} catch (NothingReturnedException e) {
				throw e; // ��׽���쳣���׳�
			}
		}
		System.out.println("*******���н�������޸ĵ�TypedNodes��ӵ����ͼ�У�" + Profiler.end() + "ms");

		// ��������޸ĵ�TypedEdges��ӵ����ͼ��
		Profiler.begin();
		long[] array = { 0, 0, 0 };
		long start;
		TypedEdge[] typedEdgeImages = new TypedEdge[interSources.length];
		for (TypedEdge baseEdge : baseGraph.allTypedEdges) { // ���ڻ���ͼ��ÿһ��TypedEdge���͵ı�baseEdge

			start = System.currentTimeMillis();
			// �����֧ͼ�ȷֱ�ͻ���ͼ���Ƚϣ�baseEdge������ֱ�洢��typedEdgeImages[i]�У�������baseEdge��imageEdge��null
			for (int i = 0; i < interSources.length; i++) {
				typedEdgeImages[i] = TypedGraph.computeImage(baseEdge, baseGraph, interSources[i]);
			}
			array[0] += System.currentTimeMillis() - start;

			try {
				start = System.currentTimeMillis();
				// �ٸ���typedEdgeImages[]�е������ȷ��finalEdgeInfo
				Tuple3<TypedNode, TypedNode, TypeEdge> finalEdgeInfo = TypedGraph.computeEdge(baseEdge,
						typedEdgeImages);
				array[1] += System.currentTimeMillis() - start;

				if (finalEdgeInfo != null) {
					TypedNode source = resultGraph.getElementByIndexObject(finalEdgeInfo.first.getIndex());
					TypedNode target = resultGraph.getElementByIndexObject(finalEdgeInfo.second.getIndex());
					TypedEdge edge = new TypedEdge(); // ������Ķ���edge
					edge.setSource(source);
					edge.setTarget(target);
					edge.setType(finalEdgeInfo.third);

					for (TypedGraph image : interSources) { // �ϲ����з�֧ͼ��������
						edge.mergeIndex(image.getElementByIndexObject(baseEdge.getIndex()));
					}
					start = System.currentTimeMillis();
					resultGraph.addTypedEdge(edge);
					array[2] += System.currentTimeMillis() - start;
				}

			} catch (NothingReturnedException e) {
				throw e; // ��׽���쳣���׳�
			}
		}
		System.out.println("*******���н�������޸ĵ�TypedEdges��ӵ����ͼ�У�" + Profiler.end() + "ms");

		System.out.println("array[0]: " + array[0]);
		System.out.println("array[1]: " + array[1]);
		System.out.println("array[2]: " + array[2]);

		// ��������޸ĵ�ValueEdges��ӵ����ͼ��
		Profiler.begin();
		ValueEdge[] valueEdgeImages = new ValueEdge[interSources.length];
		for (ValueEdge baseEdge : baseGraph.allValueEdges) { // ���ڻ���ͼ��ÿһ����ValueEdge���͵ı�

			// ������֧ͼ�Ⱥͻ���ͼ���Ƚϣ�baseEdge������ֱ�洢��valueEdgeImages[]�У�������null��baseEdge��imageEdge
			for (int i = 0; i < interSources.length; i++) {
				valueEdgeImages[i] = TypedGraph.computeImage(baseEdge, baseGraph, interSources[i]);
			}

			try {
				// �ٸ���valueEdgeImages[]�е������ȷ��finalEdgeInfo
				Tuple3<TypedNode, ValueNode, PropertyEdge> finalEdgeInfo = TypedGraph.computeEdge(baseEdge,
						valueEdgeImages);

				if (finalEdgeInfo != null) {
					TypedNode source = resultGraph.getElementByIndexObject(finalEdgeInfo.first.getIndex());
					ValueNode target = finalEdgeInfo.second;
					ValueEdge edge = new ValueEdge();
					edge.setSource(source);
					edge.setTarget(target);
					edge.setType(finalEdgeInfo.third);

					for (TypedGraph image : interSources) {
						edge.mergeIndex(image.getElementByIndexObject(baseEdge.getIndex()));
					}

					resultGraph.addValueEdge(edge);
				}

			} catch (NothingReturnedException e) {
				throw e;
			}
		}
		System.out.println("*******���н�������޸ĵ�ValueEdges��ӵ����ͼ�У�" + Profiler.end() + "ms");

		// �¼�TypedEdges
		Profiler.begin();
		for (TypedGraph image : interSources) {
			for (TypedEdge e : image.allTypedEdges) { // ���ڷ�֧ͼ��ÿһ��TypedEdge��e
				try { // ����e���������һ���ͼ��������Ӧ�Ķ����������������
					baseGraph.getElementByIndexObject(e.getIndex());
				} catch (Exception ex) { // ����ͼ��û���ҵ���Ӧ�Ķ���
					TypedEdge re = null;
					try {
						// ����e����������resultͼ��������Ӧ�Ķ����������ֵ��re
						re = resultGraph.getElementByIndexObject(e.getIndex());
					} catch (Exception ex2) { // ����e������û���ҵ�resultͼ����Ӧ�Ķ�����Ӧ���e��resultͼ��allTypedEdges��

						TypedNode source = resultGraph.getElementByIndexObject(e.getSource().getIndex());
						TypedNode target = resultGraph.getElementByIndexObject(e.getTarget().getIndex());

						if (e.getSource() != source || e.getTarget() != target) { // ˵��e��source����target�ǻ���ͼ���еģ�resultͼ�ϲ�������ͼ��������������ͬһ����
							TypedEdge ne = new TypedEdge();
							ne.setSource(source);
							ne.setTarget(target);
							ne.setType(e.getType());
							ne.mergeIndex(e);
							resultGraph.addTypedEdge(ne);
						} else // ˵��e��source��target�����ڷ�֧ͼ������ӵ�TypedNode���ͽڵ㣬�����ڷ�֧ͼ��resultͼ����ͬһ����
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
		System.out.println("*******�����¼�TypedEdges��" + Profiler.end() + "ms");

		// �¼�ValueEdges
		Profiler.begin();
		for (TypedGraph image : interSources) {
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

						TypedNode source = resultGraph.getElementByIndexObject(e.getSource().getIndex());

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
		System.out.println("*******�����¼�ValueEdges��" + Profiler.end() + "ms");

		return resultGraph;
	}

	/** ��·�ϲ� */
	public static TypedGraph mergeSerial(TypedGraph baseGraph, TypedGraph... interSources)
			throws NothingReturnedException {

		TypedGraph resultGraph = new TypedGraph(baseGraph.getTypeGraph());

		// �¼�TypedNodes
		Profiler.begin();
		for (TypedGraph image : interSources) {
			for (TypedNode n : image.allTypedNodes) { // ���ڷ�֧ͼ��ÿ��TypedNode���͵Ľڵ�n
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
								throw new NothingReturnedException(); // incompatible
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
		System.out.println("*******�����¼�TypedNodes��" + Profiler.end() + "ms");

		// �¼�ValueNodes
		Profiler.begin();
		baseGraph.allValueNodes.forEach(v -> {
			resultGraph.addValueNode(v);
		});

		for (TypedGraph image : interSources) {
			image.allValueNodes.forEach(v -> {
				resultGraph.addValueNode(v);
			});
		}
		System.out.println("*******�����¼�ValueNodes��" + Profiler.end() + "ms");

		// ��������޸ĵ�TypedNodes��ӵ����ͼ��
		Profiler.begin();
		TypeNode[] nodeImages = new TypeNode[interSources.length];
		for (TypedNode baseNode : baseGraph.allTypedNodes) { // ���ڻ���ͼ��ÿһ��TypedNode�ڵ�

			for (int i = 0; i < interSources.length; i++) {
				// ������֧ͼ�ȷֱ�ͻ���ͼ���Ƚϣ�baseNode������ֱ�洢��nodeImages[i]�С�������NULL��ANY���޸ĺ������
				nodeImages[i] = TypedGraph.computeImage(baseNode, baseGraph, interSources[i]);
			}

			try {
				// �ٸ���nodeImages[]�е������ȷ��baseNode��finalType
				TypeNode finalType = TypedGraph.computeType(nodeImages, baseGraph.getTypeGraph());

				if (finalType != TypeNode.NULL_TYPE) {
					if (finalType == TypeNode.ANY_TYPE) {
						finalType = baseNode.getType();
					}

					TypedNode n = new TypedNode(); // ������Ľڵ�
					n.setType(finalType); // �����½ڵ�n�����ͣ��ͻ���ͼ��һ�������ͻ����޸ĺ������

					for (TypedGraph image : interSources) {
						// ���½ڵ�n�����з�֧ͼ�ж�Ӧ��baseNode���������ϲ�
						n.mergeIndex(image.getElementByIndexObject(baseNode.getIndex()));
					}

					resultGraph.addTypedNode(n);
				}

			} catch (NothingReturnedException e) {
				throw e; // ��׽���쳣���׳�
			}
		}
		System.out.println("*******���н�������޸ĵ�TypedNodes��ӵ����ͼ�У�" + Profiler.end() + "ms");

		// ��������޸ĵ�TypedEdges��ӵ����ͼ��
		Profiler.begin();
		long[] array = { 0, 0, 0 };
		long start;
		TypedEdge[] typedEdgeImages = new TypedEdge[interSources.length];
		for (TypedEdge baseEdge : baseGraph.allTypedEdges) { // ���ڻ���ͼ��ÿһ��TypedEdge���͵ı�baseEdge

			start = System.currentTimeMillis();
			// �����֧ͼ�ȷֱ�ͻ���ͼ���Ƚϣ�baseEdge������ֱ�洢��typedEdgeImages[i]�У�������baseEdge��imageEdge��null
			for (int i = 0; i < interSources.length; i++) {
				typedEdgeImages[i] = TypedGraph.computeImage(baseEdge, baseGraph, interSources[i]);
			}
			array[0] += System.currentTimeMillis() - start;

			try {
				start = System.currentTimeMillis();

				// �ٸ���typedEdgeImages[]�е������ȷ��finalEdgeInfo
				Tuple3<TypedNode, TypedNode, TypeEdge> finalEdgeInfo = TypedGraph.computeEdge(baseEdge,
						typedEdgeImages);
				array[1] += System.currentTimeMillis() - start;

				if (finalEdgeInfo != null) {
					TypedNode source = resultGraph.getElementByIndexObject(finalEdgeInfo.first.getIndex());
					TypedNode target = resultGraph.getElementByIndexObject(finalEdgeInfo.second.getIndex());
					TypedEdge edge = new TypedEdge(); // ������Ķ���edge
					edge.setSource(source);
					edge.setTarget(target);
					edge.setType(finalEdgeInfo.third);

					for (TypedGraph image : interSources) { // �ϲ����з�֧ͼ��������
						edge.mergeIndex(image.getElementByIndexObject(baseEdge.getIndex()));
					}
					start = System.currentTimeMillis();
					// simply add: 1) edge exists in all versions 2) edge in all branch versions is
					// syntactically correct.
					resultGraph.simAddTypedEdge(edge);
					array[2] += System.currentTimeMillis() - start;
				}

			} catch (NothingReturnedException e) {
				throw e; // ��׽���쳣���׳�
			}
		}
		System.out.println("*******���н�������޸ĵ�TypedEdges��ӵ����ͼ�У�" + Profiler.end() + "ms");

		System.out.println("array[0]: " + array[0]);
		System.out.println("array[1]: " + array[1]);
		System.out.println("array[2]: " + array[2]);

		// ��������޸ĵ�ValueEdges��ӵ����ͼ��
		Profiler.begin();
		ValueEdge[] valueEdgeImages = new ValueEdge[interSources.length];
		for (ValueEdge baseEdge : baseGraph.allValueEdges) { // ���ڻ���ͼ��ÿһ����ValueEdge���͵ı�

			// ������֧ͼ�Ⱥͻ���ͼ���Ƚϣ�baseEdge������ֱ�洢��valueEdgeImages[]�У�������null��baseEdge��imageEdge
			for (int i = 0; i < interSources.length; i++) {
				valueEdgeImages[i] = TypedGraph.computeImage(baseEdge, baseGraph, interSources[i]);
			}

			try {
				// �ٸ���valueEdgeImages[]�е������ȷ��finalEdgeInfo
				Tuple3<TypedNode, ValueNode, PropertyEdge> finalEdgeInfo = TypedGraph.computeEdge(baseEdge,
						valueEdgeImages);

				if (finalEdgeInfo != null) {
					TypedNode source = resultGraph.getElementByIndexObject(finalEdgeInfo.first.getIndex());
					ValueNode target = finalEdgeInfo.second;
					ValueEdge edge = new ValueEdge();
					edge.setSource(source);
					edge.setTarget(target);
					edge.setType(finalEdgeInfo.third);

					for (TypedGraph image : interSources) {
						edge.mergeIndex(image.getElementByIndexObject(baseEdge.getIndex()));
					}
					// simply add
					resultGraph.simAddValueEdge(edge);
				}

			} catch (NothingReturnedException e) {
				throw e;
			}
		}
		System.out.println("*******���н�������޸ĵ�ValueEdges��ӵ����ͼ�У�" + Profiler.end() + "ms");

		// �¼�TypedEdges
		Profiler.begin();
		int typedEdgeSize = resultGraph.allTypedEdges.size();
		for (TypedGraph image : interSources) {
			for (TypedEdge e : image.allTypedEdges) { // ���ڷ�֧ͼ��ÿһ��TypedEdge��e
				try { // ����e���������һ���ͼ��������Ӧ�Ķ����������������
					baseGraph.getElementByIndexObject(e.getIndex());
				} catch (Exception ex) { // ����ͼ��û���ҵ���Ӧ�Ķ���
					TypedEdge re = null;
					try {
						// ����e����������resultͼ��������Ӧ�Ķ����������ֵ��re
						re = resultGraph.getElementByIndexObject(e.getIndex());
					} catch (Exception ex2) { // ����e������û���ҵ�resultͼ����Ӧ�Ķ�����Ӧ���e��resultͼ��allTypedEdges��

						TypedNode source = resultGraph.getElementByIndexObject(e.getSource().getIndex());
						TypedNode target = resultGraph.getElementByIndexObject(e.getTarget().getIndex());

						if (e.getSource() != source || e.getTarget() != target) { // ˵��e��source����target�ǻ���ͼ���еģ�resultͼ�ϲ�������ͼ��������������ͬһ����
							TypedEdge ne = new TypedEdge();
							ne.setSource(source);
							ne.setTarget(target);
							ne.setType(e.getType());
							ne.mergeIndex(e);
							resultGraph.addTypedEdge(ne, typedEdgeSize);
						} else // ˵��e��source��target�����ڷ�֧ͼ������ӵ�TypedNode���ͽڵ㣬�����ڷ�֧ͼ��resultͼ����ͬһ����
							resultGraph.addTypedEdge(e, typedEdgeSize);
						re = null;

					} finally {
						// ����e�������ҵ���resultͼ����Ӧ�Ķ��󣬲���ֵ����re
						// ˵�������֧ͼ�������ͬ�����ı�
						if (re != null) {
							if (re.getType() != e.getType()
									|| !re.getSource().getIndex().equals(e.getSource().getIndex())
									|| !re.getTarget().getIndex().equals(e.getTarget().getIndex())) {
								throw new NothingReturnedException("�¼�TypedEdge�߳�ͻ");
							} else {
								re.mergeIndex(e);
								resultGraph.reindexing(re); // lyt�޸�
							}
						}
					}
				}
			}
		}
		System.out.println("*******�����¼�TypedEdges��" + Profiler.end() + "ms");

		// �¼�ValueEdges
		Profiler.begin();
		int valueEdgeSize = resultGraph.allValueEdges.size();
		for (TypedGraph image : interSources) {
			for (ValueEdge e : image.allValueEdges) { // ���ڷ�֧ͼ�е�ÿһ��ValueEdge��e
				try {
					// ����e���������һ���ͼ��������Ӧ�Ķ���������򲻴���
					baseGraph.getElementByIndexObject(e.getIndex());
				} catch (Exception ex) { // �������ͼ��û����Ӧ�Ķ���
					ValueEdge re = null;
					try {
						// ����e����������resultͼ��������Ӧ�Ķ����������ֵ��re
						re = resultGraph.getElementByIndexObject(e.getIndex());
					} catch (Exception ex2) { // ����e������û���ҵ�resultͼ����Ӧ�Ķ����������Ҫ���e��ͼ��List<ValueEdge>��

						TypedNode source = resultGraph.getElementByIndexObject(e.getSource().getIndex());

						if (e.getSource() != source) { // source�ǻ���ͼ���еģ�resultͼ�ϲ�������ͼ����������������ͬһ����
							ValueEdge ne = new ValueEdge();
							ne.setSource(source);
							ne.setTarget(e.getTarget());
							ne.setType(e.getType()); // ������Ҫ����
							ne.mergeIndex(e);
							resultGraph.addValueEdge(ne, valueEdgeSize);

						} else // e��source��resultͼ����ͬһ����˵��������ӵ�TypedNode����

							resultGraph.addValueEdge(e, valueEdgeSize);

						re = null;
					} finally {

						if (re != null) { // re!=null��˵��resultͼ���ҵ�����Ӧ�Ķ��󣬲��Ҹ�ֵ����re
							if (re.getType() != e.getType()
									|| !re.getSource().getIndex().equals(e.getSource().getIndex())
									|| re.getTarget().equals(e.getTarget()) == false) {
								// tmp
								System.out.println("BXMerge3 line 772");

								throw new NothingReturnedException("�¼�Ԫ�صĳ�ͻ");
							} else {
								re.mergeIndex(e);
								resultGraph.reindexing(re); // lyt�޸�
							}
						}
					}
				}
			}
		}
		System.out.println("*******�����¼�ValueEdges��" + Profiler.end() + "ms");

		return resultGraph;
	}

	/** ��·�ϲ� */
	public static TypedGraph mergeParallel(TypedGraph baseGraph, TypedGraph... interSources)
			throws NothingReturnedException {

		TypedGraph resultGraph = new TypedGraph(baseGraph.getTypeGraph());

		List<TypedEdge> allTypedEdges = resultGraph.allTypedEdges;
		List<ValueEdge> allValueEdges = resultGraph.allValueEdges;

		resultGraph.allTypedNodes = Collections.synchronizedList(resultGraph.allTypedNodes);
		resultGraph.allTypedEdges = Collections.synchronizedList(resultGraph.allTypedEdges);
		resultGraph.allValueNodes = Collections.synchronizedList(resultGraph.allValueNodes);
		resultGraph.allValueEdges = Collections.synchronizedList(resultGraph.allValueEdges);
		resultGraph.indexToObjectMap = new ConcurrentHashMap<>();

		// �¼�TypedNodes
		long start = System.currentTimeMillis();
		for (TypedGraph image : interSources) {
			image.allTypedNodes.parallelStream().forEach(n -> {
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
			});
		}
		long end = System.currentTimeMillis();
		System.out.println("*******parallelStream�¼�TypedNodes��" + (end - start) + "ms");

		// ����ͼԭ�еġ���֧ͼ�¼ӵ�ValueNodes
		start = System.currentTimeMillis();
		baseGraph.allValueNodes.parallelStream().forEach(v -> {
			resultGraph.addValueNode(v);
		});
		for (TypedGraph image : interSources) {
			image.allValueNodes.parallelStream().forEach(v -> {
				resultGraph.addValueNode(v);
			});
		}
		end = System.currentTimeMillis();
		System.out.println("*******parallelStream�¼�ValueNodes��" + (end - start) + "ms");

		// ��������޸ĵ�TypedNodes��ӵ����ͼ��
		start = System.currentTimeMillis();
		ConcurrentHashMap<Thread, TypeNode[]> map1 = new ConcurrentHashMap<>();
		baseGraph.allTypedNodes.parallelStream().forEach(baseNode -> {

			TypeNode[] nodeImages;
			if ((nodeImages = map1.get(Thread.currentThread())) != null) {

			} else {
				nodeImages = new TypeNode[interSources.length];
				map1.put(Thread.currentThread(), nodeImages);
			}

			for (int i = 0; i < interSources.length; i++) {
				// �����֧ͼ�ȷֱ�ͻ���ͼ���Ƚϣ�baseNode������ֱ�洢��nodeImages[i]�С�������NULL��ANY���޸ĺ������
				nodeImages[i] = TypedGraph.computeImage(baseNode, baseGraph, interSources[i]);
			}

			try {
				// �ٸ���nodeImages[]�е������ȷ��baseNode��finalType
				TypeNode finalType = TypedGraph.computeType(nodeImages, baseGraph.getTypeGraph());

				if (finalType != TypeNode.NULL_TYPE) {

					if (finalType == TypeNode.ANY_TYPE) {
						finalType = baseNode.getType();
					}

					TypedNode n = new TypedNode(); // ������Ľڵ�
					n.setType(finalType); // �����½ڵ�n�����ͣ��ͻ���ͼ��һ�������ͻ����޸ĺ������

					for (TypedGraph image : interSources) {
						// ���½ڵ�n�����з�֧ͼ�ж�Ӧ��baseNode���������ϲ�
						n.mergeIndex(image.getElementByIndexObject(baseNode.getIndex()));
					}

					resultGraph.addTypedNode(n);
				}

			} catch (NothingReturnedException e) {
				e.printStackTrace();
			}

		});
		end = System.currentTimeMillis();
		System.out.println("*******parallelStream��������޸ĵ�TypedNodes��ӵ����ͼ�У�" + (end - start) + "ms");

		// ��������޸ĵ�TypedEdges��ӵ����ͼ��
		start = System.currentTimeMillis();
		ConcurrentHashMap<Thread, TypedEdge[]> map2 = new ConcurrentHashMap<>();
		baseGraph.allTypedEdges.parallelStream().forEach(baseEdge -> {

			TypedEdge[] typedEdgeImages;
			if ((typedEdgeImages = map2.get(Thread.currentThread())) != null) {

			} else {
				typedEdgeImages = new TypedEdge[interSources.length];
				map2.put(Thread.currentThread(), typedEdgeImages);
			}

			// �����֧ͼ�ȷֱ�ͻ���ͼ���Ƚϣ�baseEdge������ֱ�洢��typedEdgeImages[i]�У�������baseEdge��imageEdge��null
			for (int i = 0; i < interSources.length; i++) {
				typedEdgeImages[i] = TypedGraph.computeImage(baseEdge, baseGraph, interSources[i]);
			}

			try {
				// �ٸ���typedEdgeImages[]�е������ȷ��finalEdgeInfo
				Tuple3<TypedNode, TypedNode, TypeEdge> finalEdgeInfo = TypedGraph.computeEdge(baseEdge,
						typedEdgeImages);

				if (finalEdgeInfo != null) {
					TypedNode source = resultGraph.getElementByIndexObject(finalEdgeInfo.first.getIndex());
					TypedNode target = resultGraph.getElementByIndexObject(finalEdgeInfo.second.getIndex());
					TypedEdge edge = new TypedEdge(); // ������Ķ���edge
					edge.setSource(source);
					edge.setTarget(target);
					edge.setType(finalEdgeInfo.third);

					for (TypedGraph image : interSources) { // �ϲ����з�֧ͼ��������
						edge.mergeIndex(image.getElementByIndexObject(baseEdge.getIndex()));
					}
					// simply add
					resultGraph.simAddTypedEdge(edge);
				}

			} catch (NothingReturnedException e) {
				e.printStackTrace();
			}
		});
		end = System.currentTimeMillis();
		System.out.println("*******parallelStream��������޸ĵ�TypedEdges��ӵ����ͼ�У�" + (end - start) + "ms");

		// ��������޸ĵ�ValueEdges��ӵ����ͼ��
		start = System.currentTimeMillis();
		ConcurrentHashMap<Thread, ValueEdge[]> map3 = new ConcurrentHashMap<>();
		baseGraph.allValueEdges.parallelStream().forEach(baseEdge -> {

			ValueEdge[] valueEdgeImages;
			if ((valueEdgeImages = map3.get(Thread.currentThread())) != null) {

			} else {
				valueEdgeImages = new ValueEdge[interSources.length];
				map3.put(Thread.currentThread(), valueEdgeImages);
			}

			// ������֧ͼ�Ⱥͻ���ͼ���Ƚϣ�baseEdge������ֱ�洢��valueEdgeImages[]�У�������null��baseEdge��imageEdge
			for (int i = 0; i < interSources.length; i++) {
				valueEdgeImages[i] = TypedGraph.computeImage(baseEdge, baseGraph, interSources[i]);
			}

			try {
				// �ٸ���valueEdgeImages[]�е������ȷ��finalEdgeInfo
				Tuple3<TypedNode, ValueNode, PropertyEdge> finalEdgeInfo = TypedGraph.computeEdge(baseEdge,
						valueEdgeImages);

				if (finalEdgeInfo != null) {
					TypedNode source = resultGraph.getElementByIndexObject(finalEdgeInfo.first.getIndex());
					ValueNode target = finalEdgeInfo.second;
					ValueEdge edge = new ValueEdge();
					edge.setSource(source);
					edge.setTarget(target);
					edge.setType(finalEdgeInfo.third);

					for (TypedGraph image : interSources) {
						edge.mergeIndex(image.getElementByIndexObject(baseEdge.getIndex()));
					}
					// simply add
					resultGraph.simAddValueEdge(edge);
				}

			} catch (NothingReturnedException e) {
				e.printStackTrace();
			}
		});
		end = System.currentTimeMillis();
		System.out.println("*******parallelStream��������޸ĵ�ValueEdges��ӵ����ͼ�У�" + (end - start) + "ms");

		resultGraph.allTypedEdges = allTypedEdges;
		int typedEdgeSize = resultGraph.allTypedEdges.size();
		// �¼�TypedEdges
		start = System.currentTimeMillis();
		for (TypedGraph image : interSources) {
			image.allTypedEdges.forEach(e -> {
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

						if (e.getSource() != source || e.getTarget() != target) { // ˵��e��source��target�ǻ���ͼ���еģ�resultͼ�ϲ�������ͼ��������������ͬһ����
							TypedEdge ne = new TypedEdge();
							ne.setSource(source);
							ne.setTarget(target);
							ne.setType(e.getType());
							ne.mergeIndex(e);
							resultGraph.addTypedEdge(ne, typedEdgeSize); // �¼�ʱ���Ͳ���simplyAdd��

						} else // ˵��e��source��target���ڷ�֧ͼ������ӵ�TypedNode���ͽڵ㣬�����ڷ�֧ͼ��resultͼ����ͬһ����
							resultGraph.addTypedEdge(e, typedEdgeSize);

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
			});
		}
		end = System.currentTimeMillis();
		System.out.println("*******�¼�TypedEdges��" + (end - start) + "ms");

		resultGraph.allValueEdges = allValueEdges;
		int valueEdgeSize = resultGraph.allValueEdges.size();
		// �¼�ValueEdges
		start = System.currentTimeMillis();
		for (TypedGraph image : interSources) {
			image.allValueEdges.forEach(e -> {
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

						if (e.getSource() != source) { // source�ǻ���ͼ���еģ�resultͼ�ϲ�������ͼ����������������ͬһ����
							ValueEdge ne = new ValueEdge();
							ne.setSource(source);
							ne.setTarget(e.getTarget());
							ne.setType(e.getType());
							ne.mergeIndex(e);
							resultGraph.addValueEdge(ne, valueEdgeSize);
						} else // e��source��resultͼ����ͬһ����˵��������ӵ�TypedNode����
							resultGraph.addValueEdge(e, valueEdgeSize);

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
			});

		}
		end = System.currentTimeMillis();
		System.out.println("*******�¼�ValueEdges��" + (end - start) + "ms");

		return resultGraph;
	}

	/** �ߵ��������� */
	public static void topoOrder(TypedGraph baseGraph, TypedGraph resultGraph, HashMap<TypedEdge, TypedEdge> forceOrd,
			List<TypeEdge> typeEdgeList, TypedGraph... branchGraphs) {

		// ���û��ָ����Ҫ����ıߣ��Ͳ���������
		if (typeEdgeList == null || typeEdgeList.size() == 0) {
			return;
		}

		List<TypedEdge> resultList = resultGraph.getAllTypedEdges();

		// ����Flag���ŵ����ֻputһ��
		HashMap<TypedEdge, Integer> baseFlag = new HashMap<>();
		List<TypedEdge> baseList = baseGraph.getAllTypedEdges();
		for (int i = 0; i < baseList.size(); i++) {
			baseFlag.put(baseList.get(i), i);
		}

		int len = branchGraphs.length;
		HashMap<TypedEdge, Integer>[] branchFlag = new HashMap[len];
		for (int i = 0; i < len; i++) {
			List<TypedEdge> branchList = branchGraphs[i].getAllTypedEdges();
			branchFlag[i] = new HashMap<>();
			for (int j = 0; j < branchList.size(); j++) {
				branchFlag[i].put(branchList.get(j), j);
			}
		}

		// ָ������Ҫ����ıߣ���ͬ���ͣ�
		Map<TypeEdge, Map<TypedNode, List<TypedEdge>>> groups = new HashMap<>();
		// ����Ҫ����ı�
		List<TypedEdge> others = new ArrayList<>();
		// ���з���
		grouping(typeEdgeList, resultList, groups, others);

		resultList.clear();
		groups.values().forEach(m -> {
			m.values().forEach(list -> {
				// �����������򷽷�
				List<TypedEdge> topoEveryList = topoEveryList(baseGraph, forceOrd, baseFlag, branchFlag, list,
						branchGraphs);
				resultList.addAll(topoEveryList);
			});
		});
		resultList.addAll(others);

	}

	/** �ߵ��������� */
	public static void topoOrder2(TypedGraph baseGraph, TypedGraph resultGraph, HashMap<ValueEdge, ValueEdge> forceOrd,
			List<PropertyEdge> propertyEdgeList, TypedGraph... branchGraphs) {

		// ���û��ָ����Ҫ����ıߣ��Ͳ���������
		if (propertyEdgeList == null || propertyEdgeList.size() == 0) {
			return;
		}

		List<ValueEdge> resultList = resultGraph.getAllValueEdges();

		// ����Flag���ŵ����ֻputһ��
		HashMap<ValueEdge, Integer> baseFlag = new HashMap<>();
		List<ValueEdge> baseList = baseGraph.getAllValueEdges();
		for (int i = 0; i < baseList.size(); i++) {
			baseFlag.put(baseList.get(i), i);
		}

		int len = branchGraphs.length;
		HashMap<ValueEdge, Integer>[] branchFlag = new HashMap[len];
		for (int i = 0; i < len; i++) {
			List<ValueEdge> branchList = branchGraphs[i].getAllValueEdges();
			branchFlag[i] = new HashMap<>();
			for (int j = 0; j < branchList.size(); j++) {
				branchFlag[i].put(branchList.get(j), j);
			}
		}

		// ָ������Ҫ����ıߣ���ͬ���ͣ�
		Map<PropertyEdge, Map<TypedNode, List<ValueEdge>>> groups = new HashMap<>();
		// ����Ҫ����ı�
		List<ValueEdge> others = new ArrayList<>();
		// ���з���
		grouping2(propertyEdgeList, resultList, groups, others);

		resultList.clear();
		groups.values().forEach(m -> {
			m.values().forEach(list -> {
				// �����������򷽷�
				List<ValueEdge> topoEveryList = topoEveryList2(baseGraph, forceOrd, baseFlag, branchFlag, list,
						branchGraphs);
				resultList.addAll(topoEveryList);
			});
		});
		resultList.addAll(others);

	}

	/** ��ÿ��list������������ */
	public static List<TypedEdge> topoEveryList(TypedGraph baseGraph, HashMap<TypedEdge, TypedEdge> forceOrd,
			HashMap<TypedEdge, Integer> baseFlag, HashMap<TypedEdge, Integer>[] branchFlag, List<TypedEdge> list,
			TypedGraph... branchGraphs) {

		int size = list.size();
		TopoGraph g = new TopoGraph(size);
		for (int i = 0; i < size; i++) {
			TypedEdge ei = list.get(i);
			for (int j = i + 1; j < size; j++) {
				TypedEdge ej = list.get(j);
				Order computeOrd = null;
				try {
					computeOrd = computeOrd(ei, ej, baseFlag, branchFlag, baseGraph, forceOrd, branchGraphs);
				} catch (NothingReturnedException e) {
					System.out.println("����������Ԫ�ص�������ͻ��");
					System.out.println(ei + " " + ej);
					e.printStackTrace();
					// PENDING: �������ͻ����ô����
				}
				if (computeOrd == Order.less) {
					g.addEdge(i, j);
				} else if (computeOrd == Order.greater) {
					g.addEdge(j, i);
				}
			}
		}

		ArrayList<Integer> topologicalSort;
		List<TypedEdge> mergeList = new ArrayList<>();
		try {
			topologicalSort = g.topologicalSort();
			for (int i : topologicalSort) {
				mergeList.add(list.get(i));
			}
		} catch (NothingReturnedException e) {
			e.printStackTrace();
		}

		return mergeList;
	}

	/** ��ÿ��list������������ */
	public static List<ValueEdge> topoEveryList2(TypedGraph baseGraph, HashMap<ValueEdge, ValueEdge> forceOrd,
			HashMap<ValueEdge, Integer> baseFlag, HashMap<ValueEdge, Integer>[] branchFlag, List<ValueEdge> list,
			TypedGraph... branchGraphs) {

		int size = list.size();
		TopoGraph g = new TopoGraph(size);
		for (int i = 0; i < size; i++) {
			ValueEdge ei = list.get(i);
			
			// tmp
			if(ei.getTarget().getValue().equals("AEU") || ei.getTarget().getValue().equals("SIQ")) {
				System.out.println("BXMerge3 line 1271");
			}
			
			for (int j = i + 1; j < size; j++) {
				ValueEdge ej = list.get(j);
				Order computeOrd = null;
				try {
					computeOrd = computeOrd2(ei, ej, baseFlag, branchFlag, baseGraph, forceOrd, branchGraphs);
				} catch (NothingReturnedException e) {
					System.out.println("����������Ԫ�ص�������ͻ��");
					System.out.println(ei + " " + ej);
					e.printStackTrace();
					// PENDING: �������ͻ����ô����
				}
				if (computeOrd == Order.less) {
					g.addEdge(i, j);
				} else if (computeOrd == Order.greater) {
					g.addEdge(j, i);
				}
			}
		}

		ArrayList<Integer> topologicalSort;
		List<ValueEdge> mergeList = new ArrayList<>();
		try {
			topologicalSort = g.topologicalSort();
			for (int i : topologicalSort) {
				mergeList.add(list.get(i));
			}
		} catch (NothingReturnedException e) {
			e.printStackTrace();
		}

		return mergeList;
	}

	/** ����Ҫ����ı߽��з��� */
	public static void grouping(List<TypeEdge> typeEdgeList, List<TypedEdge> resultList,
			Map<TypeEdge, Map<TypedNode, List<TypedEdge>>> groups, List<TypedEdge> others) {

		typeEdgeList.forEach(typeEdge -> {
			groups.put(typeEdge, new HashMap<>());
		});

		resultList.forEach(edge -> {
			Map<TypedNode, List<TypedEdge>> map = groups.get(edge.getType());
			if (map != null) {
				// edge.getSource()��Ϊkey�ǱȽϺõģ�����һ����ڵ�������ô������
				List<TypedEdge> list = map.get(edge.getSource());
				if (list == null) { // ��listû�д��������ȴ���
					list = new ArrayList<>();
					map.put(edge.getSource(), list);
				}
				// ����������ӵ���Ӧ��list�У������Ѿ������ˣ�ֱ����ӵ���Ӧ��list��
				list.add(edge);
			} else {
				// ˵���˱߲���Ҫ��������
				others.add(edge);
			}
		});
	}

	/** ����Ҫ����ı߽��з��� */
	public static void grouping2(List<PropertyEdge> propertyEdgeList, List<ValueEdge> resultList,
			Map<PropertyEdge, Map<TypedNode, List<ValueEdge>>> groups, List<ValueEdge> others) {

		propertyEdgeList.forEach(typeEdge -> {
			groups.put(typeEdge, new HashMap<>());
		});

		resultList.forEach(valueEdge -> {
			Map<TypedNode, List<ValueEdge>> map = groups.get(valueEdge.getType());
			if (map != null) {
				// edge.getSource()��Ϊkey�ǱȽϺõģ�����һ����ڵ�������ô������
				List<ValueEdge> list = map.get(valueEdge.getSource());
				if (list == null) { // ��listû�д��������ȴ���
					list = new ArrayList<>();
					map.put(valueEdge.getSource(), list);
				}
				// ����������ӵ���Ӧ��list�У������Ѿ������ˣ�ֱ����ӵ���Ӧ��list��
				list.add(valueEdge);
				
				if(valueEdge.getTarget().getValue().equals("AEU")) {
					System.out.println("BXMerge3 line 1354");
				}
				
			} else {
				// ˵���˱߲���Ҫ��������
				others.add(valueEdge);
			}
		});
	}

	/** ������������Ԫ��ei��ej�����ϵ */
	public static Order computeOrd(TypedEdge ei, TypedEdge ej, HashMap<TypedEdge, Integer> baseFlag,
			HashMap<TypedEdge, Integer>[] branchFlag, TypedGraph baseGraph, HashMap<TypedEdge, TypedEdge> forceOrd,
			TypedGraph... branchGraphs) throws NothingReturnedException {

		int len = branchGraphs.length;
		List<Tuple2<Force, Order>> ord_k = new ArrayList<>();
		boolean flag = true;
		TypedEdge ei_b = null;
		TypedEdge ej_b = null;

		for (int k = 0; k < len; k++) {
			Force t = Force.soft;
			Order o = Order.unkown;
			try {
				TypedEdge ei_k = branchGraphs[k].getElementByIndexObject(ei.getIndex());
				TypedEdge ej_k = branchGraphs[k].getElementByIndexObject(ej.getIndex());
				// ���ei��ej������ĳ����֧ͼ
				Force t_k;
				Order o_k;
				if (forceOrd.get(ei_k) == ej_k) {
					t_k = Force.hard;
					o_k = Order.less;
				} else if (forceOrd.get(ej_k) == ei_k) {
					t_k = Force.hard;
					o_k = Order.greater;
				} else {
					t_k = Force.soft;
					int distance_k = branchFlag[k].get(ei_k) - branchFlag[k].get(ej_k);
					if (distance_k < 0) {
						o_k = Order.less;
					} else if (distance_k == 0) {
						o_k = Order.equal;
					} else {
						o_k = Order.greater;
					}
				}
				try {
					if (flag == true) {
						ei_b = baseGraph.getElementByIndexObject(ei.getIndex());
						ej_b = baseGraph.getElementByIndexObject(ej.getIndex());
						flag = false;
					}
					// ��ei��ej�������ڻ���ͼ
					if (forceOrd.get(ei_b) == ej_b) {
						t = Force.hard;
						o = Order.less;
					} else if (forceOrd.get(ej_b) == ei_b) {
						t = Force.hard;
						o = Order.greater;
					} else {
						t = Force.soft;
						int distance_b = baseFlag.get(ei_b) - baseFlag.get(ej_b);
						if (distance_b < 0) {
							o = Order.less;
						} else if (distance_b == 0) {
							o = Order.equal;
						} else {
							o = Order.greater;
						}
					}

					if (o_k == o) {
						if (t_k == Force.hard || t == Force.hard) {
							t = Force.hard;
						}
					} else if (t == Force.soft) { // o_k������o���Է�֧����Ϊ׼
						t = Force.hard;
						o = o_k;
					} else {
						throw new NothingReturnedException("@@@conflict");
					}

				} catch (NothingReturnedException e) {
					// ��ei��ej���Ƕ����ڻ���ͼ�����Է�֧����Ϊ׼
					t = t_k;
					o = o_k;
				}
			} catch (NothingReturnedException e) {
				// ���ei��ej���Ƕ�����ĳ����֧ͼ
			}
			ord_k.add(new Tuple2<Force, Order>(t, o));
		}

		// �ټ���ord
		Tuple2<Force, Order> t1 = ord_k.get(0);
		Tuple2<Force, Order> t2;
		// ����ei��ej��������
		for (int p = 1; p < ord_k.size(); p++) {
			t2 = ord_k.get(p);
			try {
				t1 = mergeOrd(t1, t2);
			} catch (NothingReturnedException e) {
				throw e;
			}
		}

		return t1.second;

	}

	/** ������������Ԫ��ei��ej�����ϵ */
	public static Order computeOrd2(ValueEdge ei, ValueEdge ej, HashMap<ValueEdge, Integer> baseFlag,
			HashMap<ValueEdge, Integer>[] branchFlag, TypedGraph baseGraph, HashMap<ValueEdge, ValueEdge> forceOrd,
			TypedGraph... branchGraphs) throws NothingReturnedException {

		int len = branchGraphs.length;
		List<Tuple2<Force, Order>> ord_k = new ArrayList<>();
		boolean flag = true;
		ValueEdge ei_b = null;
		ValueEdge ej_b = null;

		for (int k = 0; k < len; k++) {
			Force t = Force.soft;
			Order o = Order.unkown;
			try {
				ValueEdge ei_k = branchGraphs[k].getElementByIndexObject(ei.getIndex());
				ValueEdge ej_k = branchGraphs[k].getElementByIndexObject(ej.getIndex());
				// ���ei��ej������ĳ����֧ͼ
				Force t_k;
				Order o_k;
				if (forceOrd.get(ei_k) == ej_k) {
					t_k = Force.hard;
					o_k = Order.less;
				} else if (forceOrd.get(ej_k) == ei_k) {
					t_k = Force.hard;
					o_k = Order.greater;
				} else {
					t_k = Force.soft;
					int distance_k = branchFlag[k].get(ei_k) - branchFlag[k].get(ej_k);
					if (distance_k < 0) {
						o_k = Order.less;
					} else if (distance_k == 0) {
						o_k = Order.equal;
					} else {
						o_k = Order.greater;
					}
				}
				try {
					if (flag == true) {
						ei_b = baseGraph.getElementByIndexObject(ei.getIndex());
						ej_b = baseGraph.getElementByIndexObject(ej.getIndex());
						flag = false;
					}
					// ��ei��ej�������ڻ���ͼ
					if (forceOrd.get(ei_b) == ej_b) {
						t = Force.hard;
						o = Order.less;
					} else if (forceOrd.get(ej_b) == ei_b) {
						t = Force.hard;
						o = Order.greater;
					} else {
						t = Force.soft;
						int distance_b = baseFlag.get(ei_b) - baseFlag.get(ej_b);
						if (distance_b < 0) {
							o = Order.less;
						} else if (distance_b == 0) {
							o = Order.equal;
						} else {
							o = Order.greater;
						}
					}

					if (o_k == o) {
						if (t_k == Force.hard || t == Force.hard) {
							t = Force.hard;
						}
					} else if (t == Force.soft) { // o_k������o���Է�֧����Ϊ׼
						t = Force.hard;
						o = o_k;
					} else {
						throw new NothingReturnedException("@@@conflict\n");
					}

				} catch (NothingReturnedException e) {
					// ��ei��ej���Ƕ����ڻ���ͼ�����Է�֧����Ϊ׼
					t = t_k;
					o = o_k;
				}
			} catch (NothingReturnedException e) {
				// ���ei��ej���Ƕ�����ĳ����֧ͼ
			}
			ord_k.add(new Tuple2<Force, Order>(t, o));
		}

		// �ټ���ord
		Tuple2<Force, Order> t1 = ord_k.get(0);
		Tuple2<Force, Order> t2;
		// ����ei��ej��������
		for (int p = 1; p < ord_k.size(); p++) {
			t2 = ord_k.get(p);
			try {
				t1 = mergeOrd(t1, t2);
			} catch (NothingReturnedException e) {
				throw e;
			}
		}

		return t1.second;

	}

	public static Tuple2<Force, Order> mergeOrd(Tuple2<Force, Order> c1, Tuple2<Force, Order> c2)
			throws NothingReturnedException {

		if (c1.first == Force.hard && c2.first == Force.soft) {
			return new Tuple2<Force, Order>(Force.hard, c1.second);
		} else if (c1.first == Force.soft && c2.first == Force.hard) {
			return new Tuple2<Force, Order>(Force.hard, c2.second);
		} else if (c1.first == Force.hard && c2.first == Force.hard) {
			if (c1.second == c2.second) {
				return new Tuple2<Force, Order>(Force.hard, c1.second);
			} else {
				throw new NothingReturnedException("###conflict: ����Force.hard���Ҳ���ͬ\n");
			}
		} else if (c1.first == Force.soft && c2.first == Force.soft) {
			if (c1.second == Order.unkown && c2.second == Order.unkown) {
				return new Tuple2<Force, Order>(Force.soft, c1.second);
			} else if (c1.second == Order.unkown && c2.second != Order.unkown) {
				return new Tuple2<Force, Order>(Force.soft, c2.second);
			} else if (c1.second != Order.unkown && c2.second == Order.unkown) {
				return new Tuple2<Force, Order>(Force.soft, c1.second);
			} else if (c1.second != Order.unkown && c2.second != Order.unkown) {
				if (c1.second == c2.second) {
					return new Tuple2<Force, Order>(Force.soft, c1.second);
				} else {
					throw new NothingReturnedException("***conflict: ����Force.soft���Ҳ���ͬ\n");
				}
			}
		} else {
			throw new NothingReturnedException("error\n");
		}
		return null;
	}

}

enum Force {
	soft, hard
}

enum Order {
	less, greater, equal, unkown
}
