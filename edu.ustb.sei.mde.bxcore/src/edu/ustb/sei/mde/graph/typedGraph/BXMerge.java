package edu.ustb.sei.mde.graph.typedGraph;

/**
 * ���кϲ���
 * ���ͼ���Ȼ´����ͼ��
 * ��ͨ�õ�computeOrd()������
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.naming.spi.DirStateFactory.Result;
import javax.print.attribute.standard.Chromaticity;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.bxcore.structures.Index;
import edu.ustb.sei.mde.graph.type.PropertyEdge;
import edu.ustb.sei.mde.graph.type.TypeEdge;
import edu.ustb.sei.mde.graph.type.TypeNode;
import edu.ustb.sei.mde.graph.typedGraph.constraint.GraphConstraint;
import edu.ustb.sei.mde.structure.Tuple2;
import edu.ustb.sei.mde.structure.Tuple3;

public class BXMerge {

	/** ����Ժϲ� */
	public static TypedGraph additiveMerge(TypedGraph first, TypedGraph graph) {
		// resultͼΪ����ͼ�ĸ���
		TypedGraph result = first.getCopy();

		long start = System.currentTimeMillis();
		// ���ڵڶ���ͼgraph�е�����ӵ�ValueNode���͵Ľڵ�n��ֱ���ӵ�resultͼ��
		graph.getAllValueNodes().forEach(n -> {
			result.addValueNode(n);
		});
		System.out.println("����ValueNodes��" + (System.currentTimeMillis() - start));

		// ���ڵڶ���ͼgraph�е�ÿ��TypedNode���͵Ľڵ�n
		start = System.currentTimeMillis();
		graph.getAllTypedNodes().forEach(n -> {
			try {
				// ����n����������resultͼ���Ƿ�����Ӧ�Ķ�������ҵ����丳ֵ��nr
				TypedNode nr = result.getElementByIndexObject(n.getIndex());
				// ���nr==n����������
				// ���nr!=n����nr�滻Ϊn����˵���ڵ㱻�滻�ˣ�����ͬ�����������ϲ��ˣ�
				if (nr != n) {
					result.replaceWith(nr, n);
				}
			} catch (NothingReturnedException e) {
				// �������n�������ڻ���ͼ��û���ҵ���˵��n�ǵڶ���ͼgraph������ӵĽڵ㣬������ӵ�resultͼ��
				result.addTypedNode(n);
			}
		});
		System.out.println("����TypedNodes: " + (System.currentTimeMillis() - start));
		

		// ���ڵڶ���ͼgraph��ÿ��TypedEdge���͵ı�e
		start = System.currentTimeMillis();
		graph.getAllTypedEdges().forEach(e -> {
			try {
				// ����e������������resultͼ�е�TypedEdge���͵ıߣ�����ҵ���ֵ��er
				TypedEdge er = result.getElementByIndexObject(e.getIndex());
				// ���er==e�������������er!=e����er�滻Ϊe
				if (er != e) {
					result.replaceWith(er, e);
				}
			} catch (NothingReturnedException ex) {
				// �������e������û���ҵ�resultͼ�еıߣ�˵��e���ݻ�ͼ������ӵģ�������ӵ�resultͼ��
				result.addTypedEdge(e);
			}
		});
		System.out.println("����TypedEdges: " + (System.currentTimeMillis() - start));
		

		// ���ڵڶ���ͼgraph��ÿ��ValueEdge���͵ı�e
		start = System.currentTimeMillis();
		graph.getAllValueEdges().forEach(e -> {
			try {
				// ����e������������resultͼ�еıߣ�����ҵ���ֵ��er
				ValueEdge er = result.getElementByIndexObject(e.getIndex());
				// ���er==e�������������er!=e����er�滻Ϊe
				if (er != e) {
					result.replaceWith(er, e);
				}
			} catch (NothingReturnedException ex) {
				// �������e������û���ҵ�resultͼ�еıߣ�˵��e��graphͼ���¼ӵģ������e��resultͼ��
				result.addValueEdge(e);
			}
		});
		System.out.println("����ValueEdges: " + (System.currentTimeMillis() - start));

//		result.order.merge(graph.order);
//		result.constraint = GraphConstraint.and(first.constraint, graph.constraint);

		return result;
	}

	/** ��·�ϲ� */
	public static TypedGraph merge(TypedGraph first, TypedGraph... interSources) throws NothingReturnedException {

		TypedGraph result = first.getCopy();

		// �¼�TypedNodes
		for (TypedGraph image : interSources) {
			for (TypedNode n : image.allTypedNodes) { // ���ڷ�֧ͼ��ÿ��TypedNode���͵Ľڵ�n
				try {
					// ����n���������һ���ͼ��������Ӧ�Ķ����������������
					first.getElementByIndexObject(n.getIndex());
				} catch (NothingReturnedException e) { // ����ڻ���ͼ��û���ҵ���Ӧ�Ķ���
					TypedNode rn = null;
					try {
						// ����n����������resultͼ��������Ӧ�Ķ�������о͸�ֵ��rn
						rn = result.getElementByIndexObject(n.getIndex());
					} catch (NothingReturnedException e1) { // ���resultͼ������Ӧ�Ķ�����n������ӵ�resultͼ��List<TypedNode>��������rn=null
						result.addTypedNode(n);
						rn = null;
					} finally {

						if (rn != null) { // ���rn!=null��˵������n�������ҵ���resultͼ����Ӧ�Ķ����Ҹ�ֵ����rn
							TypeNode lt = rn.getType();
							TypeNode rt = n.getType();
							TypeNode ct = first.getTypeGraph().computeSubtype(lt, rt); // ���յ�����

							if (ct == TypeNode.NULL_TYPE) {
								throw new NothingReturnedException(); // incompatible
							} else {
								rn.setType(ct); // �����ݣ���rn��type����Ϊct
								rn.mergeIndex(n);
								result.reindexing(rn); // lyt-�޸�
							}
						}
					}
				}
			}
		}

		
		
		// �¼�ValueNodes
		for (TypedGraph image : interSources) {
			image.allValueNodes.forEach(v -> {
				result.addValueNode(v);
			});
		}

		
		
		// ɾ�����޸�TypedEdges
		TypedEdge[] typedEdgeImages = new TypedEdge[interSources.length];
		for (TypedEdge baseEdge : first.allTypedEdges) { // ���ڻ���ͼ��ÿһ��TypedEdge���͵ı�baseEdge

			// �����֧ͼ�ȷֱ�ͻ���ͼ���Ƚϣ�baseEdge������ֱ�洢��typedEdgeImages[i]�У�������baseEdge��imageEdge��null
			for (int i = 0; i < interSources.length; i++) {
				typedEdgeImages[i] = TypedGraph.computeImage(baseEdge, first, interSources[i]);
			}

			try {
				// �ٸ���typedEdgeImages[]�е������ȷ��finalEdgeInfo
				Tuple3<TypedNode, TypedNode, TypeEdge> finalEdgeInfo = TypedGraph.computeEdge(baseEdge,
						typedEdgeImages);

				if (finalEdgeInfo == null) {
					result.remove(baseEdge);
				} else {
					// �¼�TypedNodes������TypedEdge֮ǰ
					TypedNode source = result.getElementByIndexObject(finalEdgeInfo.first.getIndex());
					TypedNode target = result.getElementByIndexObject(finalEdgeInfo.second.getIndex());
					TypedEdge edge = new TypedEdge(); // ������Ķ���edge
					edge.setSource(source);
					edge.setTarget(target);
					edge.setType(finalEdgeInfo.third);

					for (TypedGraph image : interSources) { // �ϲ�������֧��������
						edge.mergeIndex(image.getElementByIndexObject(baseEdge.getIndex()));
					}

					result.replaceWith(baseEdge, edge); // ��baseEdge�滻Ϊedge
				}
			} catch (NothingReturnedException e) {
				throw e; // ��׽���쳣���׳�
			}
		}
		
		

		// ɾ�����޸�ValueEdges
		ValueEdge[] valueEdgeImages = new ValueEdge[interSources.length];
		for (ValueEdge baseEdge : first.allValueEdges) { // ���ڻ���ͼ��ÿһ����ValueEdge���͵ı�

			// ������֧ͼ�Ⱥͻ���ͼ���Ƚϣ�baseEdge������ֱ�洢��valueEdgeImages[]�У�������null��baseEdge��imageEdge
			for (int i = 0; i < interSources.length; i++) {
				valueEdgeImages[i] = TypedGraph.computeImage(baseEdge, first, interSources[i]);
			}

			try {
				// �ٸ���valueEdgeImages[]�е������ȷ��finalEdgeInfo
				Tuple3<TypedNode, ValueNode, PropertyEdge> finalEdgeInfo = TypedGraph.computeEdge(baseEdge,
						valueEdgeImages);

				if (finalEdgeInfo == null) {
					result.remove(baseEdge);
				} else {
					TypedNode source = result.getElementByIndexObject(finalEdgeInfo.first.getIndex());
					ValueNode target = finalEdgeInfo.second;
					ValueEdge edge = new ValueEdge();
					edge.setSource(source);
					edge.setTarget(target);
					edge.setType(finalEdgeInfo.third);

					for (TypedGraph image : interSources) {
						edge.mergeIndex(image.getElementByIndexObject(baseEdge.getIndex()));
					}

					result.replaceWith(baseEdge, edge);
				}
			} catch (NothingReturnedException e) {
				throw e;
			}
		}

		
		
		// ɾ�����޸�TypedNodes
		TypeNode[] nodeImages = new TypeNode[interSources.length]; // ����length=2
		for (TypedNode baseNode : first.allTypedNodes) { // ���ڻ���ͼ��ÿһ��TypedNode�ڵ�

			for (int i = 0; i < interSources.length; i++) {
				// ������֧ͼ�ȷֱ�ͻ���ͼ���Ƚϣ�baseNode������ֱ�洢��nodeImages[i]�С�������NULL��ANY���޸ĺ������
				nodeImages[i] = TypedGraph.computeImage(baseNode, first, interSources[i]);
			}

			try {
				// �ٸ���nodeImages[]�е������ȷ��baseNode��finalType
				TypeNode finalType = TypedGraph.computeType(nodeImages, first.getTypeGraph());

				if (finalType == TypeNode.NULL_TYPE) { // ��ĳһ��֧��ɾ����������֧��ɾ��������resultͼ��Ҳɾ��
					result.remove(baseNode);
				} else {

					if (finalType == TypeNode.ANY_TYPE) // �˽ڵ���������֧ͼ�ͻ���ͼ�е�����һ��
						finalType = baseNode.getType();

					TypedNode n = new TypedNode(); // ������Ľڵ�
					n.setType(finalType); // �����½ڵ�n�����ͣ��ͻ���ͼ��һ�������ͻ����滻�������

					for (TypedGraph image : interSources) {
						// ���½ڵ�n��������֧ͼ�ж�Ӧ��baseNode���������ϲ�
						n.mergeIndex(image.getElementByIndexObject(baseNode.getIndex()));
					}
					// ��resultͼ�е�baseNode�滻Ϊn�����������resultͼ���滻��ɾ�����ڱ�
					result.replaceWith(baseNode, n);
				}

			} catch (NothingReturnedException e) {
				throw e; // ��׽���쳣���׳�
			}
		}
		

		// �¼�TypedEdges
		for (TypedGraph image : interSources) {
			for (TypedEdge e : image.allTypedEdges) { // ���ڷ�֧ͼ��ÿһ��TypedEdge��e
				try { // ����e���������һ���ͼ��������Ӧ�Ķ����������������
					first.getElementByIndexObject(e.getIndex());
				} catch (Exception ex) { // ����ͼ��û���ҵ���Ӧ�Ķ���
					TypedEdge re = null;
					try {
						// ����e����������resultͼ��������Ӧ�Ķ����������ֵ��re
						re = result.getElementByIndexObject(e.getIndex());
					} catch (Exception ex2) { // ����e������û���ҵ�resultͼ����Ӧ�Ķ�����Ӧ���e��resultͼ��List<TypedEdge>��

						TypedNode source = result.getElementByIndexObject(e.getSource().getIndex());
						TypedNode target = result.getElementByIndexObject(e.getTarget().getIndex());

						if (e.getSource() != source || e.getTarget() != target) { // ˵��e��source��target�ǻ���ͼ���еģ�resultͼ�ϲ�������ͼ��������������ͬһ����
							TypedEdge ne = new TypedEdge();
							ne.setSource(source);
							ne.setTarget(target);
							ne.setType(e.getType());
							ne.mergeIndex(e);
							result.addTypedEdge(ne);
						} else // ˵��e��source��target���ڷ�֧ͼ������ӵ�TypedNode���ͽڵ㣬�����ڷ�֧ͼ��resultͼ����ͬһ����
							result.addTypedEdge(e);

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
								result.reindexing(re); // lyt�޸�
							}
						}
					}
				}
			}
		}
		
		
		// �¼�ValueEdges
		for (TypedGraph image : interSources) {
			// �¼����ValueEdge
			for (ValueEdge e : image.allValueEdges) { // ���ڷ�֧ͼ�е�ÿһ��ValueEdge��e
				try {
					// ����e���������һ���ͼ��������Ӧ�Ķ���������򲻴���
					first.getElementByIndexObject(e.getIndex());
				} catch (Exception ex) { // �������ͼ��û����Ӧ�Ķ���
					ValueEdge re = null;
					try {
						// ����e����������resultͼ��������Ӧ�Ķ����������ֵ��re
						re = result.getElementByIndexObject(e.getIndex());
					} catch (Exception ex2) { // ����e������û���ҵ�resultͼ����Ӧ�Ķ�������Ҫ���e��ͼ��List<ValueEdge>��

						TypedNode source = result.getElementByIndexObject(e.getSource().getIndex());
//								ValueNode target = e.getTarget();

						if (e.getSource() != source) { // source�ǻ���ͼ���еģ�resultͼ�ϲ�������ͼ����������������ͬһ����
							ValueEdge ne = new ValueEdge();
							ne.setSource(source);
							ne.setTarget(e.getTarget());
							ne.setType(e.getType());
							ne.mergeIndex(e);
							result.addValueEdge(ne);
						} else // e��source��resultͼ����ͬһ����˵��������ӵ�TypedNode����
							result.addValueEdge(e);

						re = null;
					} finally {

						if (re != null) { // re!=null��˵��resultͼ���ҵ�����Ӧ�Ķ��󣬲��Ҹ�ֵ����re
							if (re.getType() != e.getType()
									|| !re.getSource().getIndex().equals(e.getSource().getIndex())
									|| re.getTarget().equals(e.getTarget()) == false) {
								throw new NothingReturnedException();
							} else {
								re.mergeIndex(e);
								result.reindexing(re); // lyt�޸�
							}
						}
					}
				}
			}
		}

		
		
//		OrderInformation[] orders = new OrderInformation[interSources.length];
//		for (int i = 0; i < interSources.length; i++)
//			orders[i] = interSources[i].order;
//		result.order.merge(orders); // ��orders[i]�ϲ���result��order��

//		// lyt-����ԭ�����򷽷�
//		System.out.println("ִ��enforceOrderǰ�� " + result.getAllTypedEdges());
//		result.enforceOrder();
//		System.out.println("ִ��enforceOrder��" + result.getAllTypedEdges());

//		List<GraphConstraint> cons = new ArrayList<>();
//		cons.add(first.getConstraint());
//		for (TypedGraph g : interSources) {
//			cons.add(g.constraint);
//		}
//		result.constraint = GraphConstraint.and(cons);
		// check

		return result;
	}

	// lyt-�鲢���򣬲����㴫����
	public static void mergeSort(List<TypedEdge> list, TypedGraph baseGraph, HashMap<TypedEdge, TypedEdge> forceOrd,
			TypedGraph... branchGraphs) {
		if (list.size() > 1) {
			List<TypedEdge> firstHalf = new ArrayList<>();
			firstHalf.addAll(list.subList(0, list.size() / 2));
			mergeSort(firstHalf, baseGraph, forceOrd, branchGraphs);

			List<TypedEdge> secondHalf = new ArrayList<>();
			secondHalf.addAll(list.subList(list.size() / 2, list.size()));
			mergeSort(secondHalf, baseGraph, forceOrd, branchGraphs);

			mergeTwoLists(firstHalf, secondHalf, list, baseGraph, forceOrd, branchGraphs);
		}
	}

	public static void mergeTwoLists(List<TypedEdge> list1, List<TypedEdge> list2, List<TypedEdge> temp,
			TypedGraph baseGraph, HashMap<TypedEdge, TypedEdge> forceOrd, TypedGraph... branchGraphs) {

		int current1 = 0;
		int current2 = 0;
		int current3 = 0;

		while (current1 < list1.size() && current2 < list2.size()) {
			Tuple2<Character, Character> computeOrd = computeOrd(list1.get(current1), list2.get(current2), baseGraph,
					forceOrd, branchGraphs);
			if (computeOrd.second == '<' || computeOrd.second == 'n') {
				temp.set(current3, list1.get(current1));
				current3++;
				current1++;
			} else {
				temp.set(current3, list2.get(current2));
				current3++;
				current2++;
			}
		}

		while (current1 < list1.size()) {
			temp.set(current3, list1.get(current1));
			current3++;
			current1++;
		}

		while (current2 < list2.size()) {
			temp.set(current3, list2.get(current2));
			current3++;
			current2++;
		}

	}

	public static Tuple2<Character, Character> computeOrd(TypedEdge ei, TypedEdge ej, TypedGraph baseGraph,
			HashMap<TypedEdge, TypedEdge> forceOrd, TypedGraph... branchGraphs) {

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

		List<Tuple2<Character, Character>> ord_k = new ArrayList<>();

		for (int k = 0; k < len; k++) {
			char t = 'S';
			char o = 'n';
			try {
				TypedEdge ei_k = branchGraphs[k].getElementByIndexObject(ei.getIndex());
				TypedEdge ej_k = branchGraphs[k].getElementByIndexObject(ej.getIndex());
				// ���ei��ej������ĳ����֧ͼ
				char t_k;
				char o_k;
				if (forceOrd.get(ei_k) == ej_k) {
					t_k = 'H';
					o_k = '<';
				} else if (forceOrd.get(ej_k) == ei_k) {
					t_k = 'H';
					o_k = '>';
				} else {
					t_k = 'S';
					int distance_k = branchFlag[k].get(ei_k) - branchFlag[k].get(ej_k);
					if (distance_k < 0) {
						o_k = '<';
					} else if (distance_k == 0) {
						o_k = '=';
					} else {
						o_k = '>';
					}
				}
				try {
					TypedEdge ei_b = baseGraph.getElementByIndexObject(ei.getIndex());
					TypedEdge ej_b = baseGraph.getElementByIndexObject(ej.getIndex());
					// ��ei��ej�������ڻ���ͼ
					if (forceOrd.get(ei_b) == ej_b) {
						t = 'H';
						o = '<';
					} else if (forceOrd.get(ej_b) == ei_b) {
						t = 'H';
						o = '>';
					} else {
						t = 'S';
						int distance_b = baseFlag.get(ei_b) - baseFlag.get(ej_b);
						if (distance_b < 0) {
							o = '<';
						} else if (distance_b == 0) {
							o = '=';
						} else {
							o = '>';
						}
					}

					if (o_k == o) {
						if (t_k == 'H' || t == 'H') {
							t = 'H';
						}
					} else if (t == 'S') { // o_k������o
						t = 'H';
						o = o_k;
					} else {
						throw new NothingReturnedException("@@@conflict");
					}
				} catch (NothingReturnedException e) {
					// ��ei��ej���Ƕ����ڻ���ͼ�����Է�֧ͼΪ׼
					t = t_k;
					o = o_k;
				}
			} catch (NothingReturnedException e) {
				// ���ei��ej���Ƕ�����ĳ����֧ͼ
			}
			ord_k.add(new Tuple2<Character, Character>(t, o));
		}

		// �ټ���ord
		Tuple2<Character, Character> t1 = ord_k.get(0);
		Tuple2<Character, Character> t2;
		// ����ei��ej��������
		for (int p = 1; p < ord_k.size(); p++) {
			t2 = ord_k.get(p);
			try {
				t1 = mergeOrd(t1, t2);
			} catch (NothingReturnedException e) {
				e.printStackTrace();
			}
		}
		return t1;
	}

	public static Tuple2<Character, Character> mergeOrd(Tuple2<Character, Character> c1,
			Tuple2<Character, Character> c2) throws NothingReturnedException {

		if (c1.first == 'H' && c2.first == 'S') {
			return new Tuple2<Character, Character>('H', c1.second);
		} else if (c1.first == 'S' && c2.first == 'H') {
			return new Tuple2<Character, Character>('H', c2.second);
		} else if (c1.first == 'H' && c2.first == 'H') {
			if (c1.second == c2.second) {
				return new Tuple2<Character, Character>('H', c1.second);
			} else {
				throw new NothingReturnedException("###conflict");
			}
		} else if (c1.first == 'S' && c2.first == 'S') {
			if (c1.second == 'n' && c2.second == 'n') {
				return new Tuple2<Character, Character>('S', c1.second);
			} else if (c1.second == 'n' && c2.second != 'n') {
				return new Tuple2<Character, Character>('S', c2.second);
			} else if (c1.second != 'n' && c2.second == 'n') {
				return new Tuple2<Character, Character>('S', c1.second);
			} else if (c1.second != 'n' && c2.second != 'n') {
				if (c1.second == c2.second) {
					return new Tuple2<Character, Character>('S', c1.second);
				} else {
					throw new NothingReturnedException("***conflict");
				}
			}
		} else {
			throw new NothingReturnedException("error");
		}
		return null;
	}

	/** ����ϲ����� */
	public static List<TypedEdge> twoOrder_origin(TypedGraph baseGraph, TypedGraph aGraph, TypedGraph resultGraph) {

		List<TypedEdge> base = baseGraph.getAllTypedEdges();
		System.out.println("base: " + base);

		List<TypedEdge> a = aGraph.getAllTypedEdges();
		System.out.println("a: " + a);

		List<TypedEdge> result = resultGraph.getAllTypedEdges();
		System.out.println("result: " + result);

		int round = 1;

		List<TypedEdge> merge = new ArrayList<>();

		// ����a��Ԫ�أ������������������resultGraph���ҵ����򲻻���ӽ�merge
		for (int i = 0; i < a.size(); i++) {

			try {
				// merge��a��ʼ��
				resultGraph.getElementByIndexObject(a.get(i).getIndex());
				merge.add(a.get(i));

			} catch (NothingReturnedException e) {

			}

		}

		Map<Index, Integer> baseFlag = new HashMap<>();
		for (int i = 0; i < base.size(); i++) {
			baseFlag.put(base.get(i).getIndex(), i);
		}

		// ����base�е�ÿ��Ԫ��
		for (int i = 0; i < base.size(); i++) {

			try {
				// �������base.get(i)����������aGraph���ҵ���������
				aGraph.getElementByIndexObject(base.get(i).getIndex());
				continue;

			} catch (NothingReturnedException e) { // �����aGraph�в����ҵ���˵��Ҫ��ӵ�merge��

				int j;
				for (j = 0; j < merge.size(); j++) {

					try {
						// �������merge(j)��������baseGraph���ҵ��ˣ��������²���
						baseGraph.getElementByIndexObject(merge.get(j).getIndex());

						int tmp = baseFlag.get(merge.get(j).getIndex()) - baseFlag.get(base.get(i).getIndex());

						if (tmp < 0) {
							continue;
						} else {
							merge.add(j, base.get(i));
							System.out.println("�غ�" + round++ + merge);
							break;
						}

					} catch (NothingReturnedException e2) {
						// �������merge(j)��������baseGraph��û�ҵ���������
						// continue; //���Բ�д
					}

				}

				if (j == merge.size()) {
					merge.add(j, base.get(i));
					System.out.println("�غ�" + round++ + merge);
				}

			}

		}

		System.out.println("*************���յ���: \n" + merge);
		return merge;

	}

	public static ArrayList<TypedEdge> twoOrder1(List<TypedEdge> baseList, List<TypedEdge> aList,
			List<TypedEdge> resultList) {

		HashMap<Index, Integer> resultFlag = new HashMap<>();
		for (int i = 0; i < resultList.size(); i++) {
			resultFlag.put(resultList.get(i).getIndex(), i);
		}

		ArrayList<TypedEdge> merge = new ArrayList<>(aList); // mergeList����aList

		// ɾ��merge�еĲ���result�еı�
		for (int i = 0; i < aList.size(); i++) {
			if (resultFlag.get(aList.get(i).getIndex()) != null) {
				continue;
			}
			merge.remove(aList.get(i));
		}

		// ���˺����merge.size() == result.size()����ֱ�ӷ���merge
		if (merge.size() == resultList.size()) {
			return merge;
		}

		HashMap<Index, Integer> baseFlag = new HashMap<>();
		for (int i = 0; i < baseList.size(); i++) {
			baseFlag.put(baseList.get(i).getIndex(), i);
		}

		HashMap<Index, Integer> mergeFlag = new HashMap<>();
		for (int i = 0; i < merge.size(); i++) {
			mergeFlag.put(merge.get(i).getIndex(), i);
		}

		// ��baseList��ԭ�еı���ӽ�merge����aList��ɾ��������Ч��
		// ע�⣬����Ժϲ���resultList��û����������߶���ȥ�ϲ�������
		for (int i = 0; i < baseList.size(); i++) {

			// �������merge���ҵ���������
			if (mergeFlag.get(baseList.get(i).getIndex()) != null) {
				continue;
			}
			// ��������ҵ���˵��Ҫ��ӽ�merge
			int baseDistance = 0;
			int j;
			for (j = 0; j < merge.size(); j++) {
				try {
					baseDistance = i - baseFlag.get(merge.get(j).getIndex());
					// �������base���ҵ��������baseDistanceȷ������λ��
					if (baseDistance > 0) {
						continue;
					}
					merge.add(j, baseList.get(i));
					break;
				} catch (NullPointerException e2) {
					// ���merge�е�Ԫ�ز�����base���ҵ���������
				}
			}

			// ���Ѿ�����mergeList����ӵ�merge��β
			if (j == merge.size()) {
				merge.add(baseList.get(i));
			}

		}

		return merge;
	}

	public static List<TypedEdge> twoOrder2(TypedGraph baseGraph, TypedGraph aGraph, TypedGraph resultGraph) {

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

	/** ����HashMap<Index, Integer> */
	public static List<TypedEdge> threeOrder_origin(TypedGraph baseGraph, TypedGraph aGraph, TypedGraph bGraph,
			TypedGraph resultGraph) {

		List<TypedEdge> base = baseGraph.getAllTypedEdges();
		System.out.println("base: " + base);

		List<TypedEdge> a = aGraph.getAllTypedEdges();
		System.out.println("a: " + a);

		List<TypedEdge> b = bGraph.getAllTypedEdges();
		System.out.println("b: " + b);

		List<TypedEdge> result = resultGraph.getAllTypedEdges();
		System.out.println("result: " + result);

		List<TypedEdge> merge = new ArrayList<>();
		boolean flag = true;
		int round = 1; // �غϼ���

		Map<Index, Integer> aFlag = new HashMap<>();
		for (int i = 0; i < a.size(); i++) {
			aFlag.put(a.get(i).getIndex(), i);
		}

		Map<Index, Integer> bFlag = new HashMap<>();
		for (int i = 0; i < b.size(); i++) {
			bFlag.put(b.get(i).getIndex(), i);
		}

		// ����base��ÿ��Ԫ��
		for (int i = 0; i < base.size(); i++) {

			try {
				// ����base.get(i)����������resultGraph������ҵ����򸳸�element
				TypedEdge element = resultGraph.getElementByIndexObject(base.get(i).getIndex());

				if (flag == false) { // �ǵ�һ��ִ��

					// ֮�����ڣ��������base.get(j)��������mergeFlag���Ѿ���front�ˣ����ö�
					Map<TypedEdge, String> mergeFlag = new HashMap<>();
					int position = merge.indexOf(element);
					for (int k = 0; k < position; k++) {
						mergeFlag.put(merge.get(k), "front");
					}

					for (int j = i + 1; j < base.size(); j++) {

						try {
							// ����base.get(j)������resultGraph�в��ң�����ҵ��򸳸�re
							TypedEdge re = resultGraph.getElementByIndexObject(base.get(j).getIndex());

							int aSubtract = aFlag.get(base.get(i).getIndex()) - aFlag.get(base.get(j).getIndex());

							int bSubtract = bFlag.get(base.get(i).getIndex()) - bFlag.get(base.get(j).getIndex());

							if (aSubtract < 0 && bSubtract < 0) {
								continue;
							} else {

								if (mergeFlag.get(re) == "front") {
									continue;
								} else {
									System.out.println("****************removeǰ��" + merge);
									merge.remove(re);
									System.out.println("****************remove��" + merge);
									merge.add(merge.indexOf(element), re);
									System.out.println("****************add��" + merge);
								}

							}

						} catch (NothingReturnedException e) { // �������base.get(j)��������resultGraph��û�ҵ���������
							continue;
						}

					}

				} else { // ��һ��ִ��

					merge.add(element);
					flag = false;

					for (int j = i + 1; j < base.size(); j++) {

						try {
							// ����base.get(j)������resultGraph�в��ң�����ҵ��򸳸�re
							TypedEdge re = resultGraph.getElementByIndexObject(base.get(j).getIndex());

							int aSubtract = aFlag.get(base.get(i).getIndex()) - aFlag.get(base.get(j).getIndex());

							int bSubtract = bFlag.get(base.get(i).getIndex()) - bFlag.get(base.get(j).getIndex());

							if (aSubtract < 0 && bSubtract < 0) {
								merge.add(re);
							} else {
								merge.add(merge.indexOf(element), re);
							}

						} catch (NothingReturnedException e) { // �������base.get(j)��������resultGraph��û�ҵ���������
							continue;
						}
					}

				}

			} catch (NothingReturnedException e) { // �������base.get(i)������resultGraph��û�ҵ���������
				continue;
			}

			System.out.println("�غ�" + round++ + "merge: " + merge);

		}

		// ����һ�¡���ʱ����merge�����ڴ���b��֧����ӵ�Ԫ�ص�if����
		// �Ƿ���Ż�����
		List<TypedEdge> intermediate = new ArrayList<>();
		intermediate.addAll(merge);

		// ����a������ӵ�Ԫ��
		for (int i = 0; i < a.size(); i++) {

			// ����a��ÿ��Ԫ�أ��������baseGraph�е���resultGraph�У�˵��������ӵ�Ԫ��
			try {
				// �����baseGraph�У�������
				baseGraph.getElementByIndexObject(a.get(i).getIndex());
				continue;
			} catch (NothingReturnedException e) { // �������baseGraph��

				try {
					// �������baseGraph�е���resultGraph�У��������²���
					TypedEdge element = resultGraph.getElementByIndexObject(a.get(i).getIndex());

					int k;
					for (k = 0; k < merge.size(); k++) {
						int tmp = aFlag.get(merge.get(k).getIndex()) - aFlag.get(a.get(i).getIndex());
						if (tmp < 0) {
							continue;
						} else {
							merge.add(k, element);
							break;
						}
					}

					if (k == merge.size()) {
						merge.add(element);
					}

					System.out.println("����a��һ������ӵ�Ԫ�غ�merge: " + merge);

				} catch (NothingReturnedException e1) {
					// �������baseGraph���ֲ���resultGraph�У�������
					continue;
				}

			}

		}

		// ����b������ӵ�Ԫ��
		for (int i = 0; i < b.size(); i++) {

			// ����b��ÿ��Ԫ�أ��������baseGraph�е���resultGraph�У�˵��������ӵ�Ԫ�ء�
			try {
				baseGraph.getElementByIndexObject(b.get(i).getIndex());
				continue;
			} catch (NothingReturnedException e) {

				// �������baseGraph��,����resultGraph�У��������²���
				try {
					TypedEdge element = resultGraph.getElementByIndexObject(b.get(i).getIndex());
					int k;
					for (k = 0; k < merge.size(); k++) {

						if (!intermediate.contains(merge.get(k))) {
							continue; // ˵����merge����ӽ���a��֧�е���Ԫ��
						}

						int tmp = bFlag.get(merge.get(k).getIndex()) - bFlag.get(b.get(i).getIndex());
						if (tmp < 0) {
							continue;
						} else {
							merge.add(k, element);
							break;
						}
					}

					if (k == merge.size()) {
						merge.add(element);
					}

					System.out.println("����b��һ������ӵ�Ԫ�غ�merge: " + merge);

				} catch (NothingReturnedException e1) {
					continue;
				}
			}
		}

		System.out.println("\n���������merge: " + merge);

		return merge;
	}

	/* ���벻�ô���TypedGraph�� */
	public static ArrayList<TypedEdge> threeOrder1(List<TypedEdge> baseTypedEdges, List<TypedEdge> aTypedEdges,
			List<TypedEdge> bTypedEdges, List<TypedEdge> resultTypedEdges) {

		List<TypedEdge> base = baseTypedEdges;
		System.out.println("base: " + base);

		List<TypedEdge> a = aTypedEdges;
		System.out.println("a: " + a);

		List<TypedEdge> b = bTypedEdges;
		System.out.println("b: " + b);

		List<TypedEdge> result = resultTypedEdges;
		System.out.println("result: " + result);

//		ArrayList<TypedEdge> merge = (ArrayList<TypedEdge>) result.clone(); // ������ͬ���ݵĲ�ͬ����
		ArrayList<TypedEdge> merge = new ArrayList<>(result);

		HashMap<Index, Integer> baseFlag = new HashMap<>();
		for (int i = 0; i < base.size(); i++) {
			baseFlag.put(base.get(i).getIndex(), i);
		}

		HashMap<Index, Integer> aFlag = new HashMap<>();
		for (int i = 0; i < a.size(); i++) {
			aFlag.put(a.get(i).getIndex(), i);
		}

		HashMap<Index, Integer> bFlag = new HashMap<>();
		for (int i = 0; i < b.size(); i++) {
			bFlag.put(b.get(i).getIndex(), i);
		}

		for (int i = 0; i < result.size() - 1; i++) {
			for (int j = i + 1; j < result.size(); j++) {
				int mergeDistance = 0; // �����жϣ�����Ѿ��Ǵ��ڣ����÷ŵ���ǰ
				int aDistance = 0;
				int bDistance = 0;
				int baseDistance = 0;
				try {
					mergeDistance = merge.indexOf(result.get(i)) - merge.indexOf(result.get(j));
					aDistance = aFlag.get(result.get(i).getIndex()) - aFlag.get(result.get(j).getIndex());
					bDistance = bFlag.get(result.get(i).getIndex()) - bFlag.get(result.get(j).getIndex());
					baseDistance = baseFlag.get(result.get(i).getIndex()) - baseFlag.get(result.get(j).getIndex());

					if ((aDistance < 0 && bDistance < 0) || (aDistance < 0 && bDistance > 0 && baseDistance > 0)
							|| (aDistance > 0 && bDistance < 0 && baseDistance > 0)) {
						continue;
					} else {
						// ���merge��Ϊ<ej, ei>
						if (mergeDistance > 0) {
							continue;
						}
						TypedEdge re = result.get(j);
						merge.remove(re);
						merge.add(merge.indexOf(result.get(i)), re);
					}

				} catch (NullPointerException e) {
					if (aDistance == 0) {
						try {
							bDistance = bFlag.get(result.get(i).getIndex()) - bFlag.get(result.get(j).getIndex());
							if (bDistance < 0) {
								continue;
							}
							// ���merge��Ϊ<ej, ei>
							if (mergeDistance > 0) {
								continue;
							}
							TypedEdge re = result.get(j);
							merge.remove(re);
							merge.add(merge.indexOf(result.get(i)), re);
						} catch (NullPointerException e2) {
							continue;
						}
					} else if (bDistance == 0) {
						if (aDistance < 0) {
							continue;
						}
						// ���merge��Ϊ<ej, ei>
						if (mergeDistance > 0) {
							continue;
						}
						TypedEdge re = result.get(j);
						merge.remove(re);
						merge.add(merge.indexOf(result.get(i)), re);
					}
				}
			}
		}
		return merge;
	}

	// �����ڶ�ͼ�����ϵ
	public static List<TypedEdge> threeOrder2(TypedGraph baseGraph, TypedGraph resultGraph,
			TypedGraph... branchGraphs) {

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

		List<TypedEdge> resultList = resultGraph.getAllTypedEdges();
		List<TypedEdge> mergeList = new ArrayList<>(resultList);

		for (int i = 0; i < resultList.size() - 1; i++) {
			for (int j = i + 1; j < resultList.size(); j++) {
				for (int k = 0; k < len; k++) {
					try {
						TypedEdge ei = branchGraphs[k].getElementByIndexObject(resultList.get(i).getIndex());
						TypedEdge ej = branchGraphs[k].getElementByIndexObject(resultList.get(j).getIndex());
						if (branchFlag[k].get(ei) - branchFlag[k].get(ej) < 0) {
							continue;
						} else {
							// ʵ���ϣ�resultList������baseList������Ϣ
							if (mergeList.indexOf(resultList.get(i)) > mergeList.indexOf(resultList.get(j))) {
								continue;
							} else {
								TypedEdge re = resultList.get(j);
								mergeList.remove(re);
								mergeList.add(mergeList.indexOf(resultList.get(i)), re);
								break;
							}

//							try {
//								TypedEdge efirstBase = baseGraph.getElementByIndexObject(resultList.get(i).getIndex());
//								TypedEdge esecondBase = baseGraph.getElementByIndexObject(resultList.get(j).getIndex());
//								// �ڻ�ͼ�����ҵ����ӻ�ͼ���������
//								int first = baseFlag.get(efirstBase);
//								int second = baseFlag.get(esecondBase);
//								if (first - second < 0) {
//									if (mergeList.indexOf(resultList.get(i)) > mergeList.indexOf(resultList.get(j))) {
//										continue;
//									} else {
//										TypedEdge re = resultList.get(j);
//										mergeList.remove(re);
//										mergeList.add(mergeList.indexOf(resultList.get(i)), re);
//										break;
//									}
//								}
//							
//							} catch (NothingReturnedException e) {
//								// �ڻ�ͼ�в����ҵ���˵�����¼ӵ�
//								if (mergeList.indexOf(resultList.get(i)) > mergeList.indexOf(resultList.get(j))) {
//									continue;
//								} else {
//									TypedEdge re = resultList.get(j);
//									mergeList.remove(re);
//									mergeList.add(mergeList.indexOf(resultList.get(i)), re);
//									break;
//								}
//							}
						}

					} catch (NothingReturnedException e) {
						// ˵�����Ǵ˷�֧�¼ӵģ�����
						// ����һ������Ƿ�֧ͼֻ������Ԫ���Լ������Ԫ�ص�������
					}
				}
			}
		}
		return mergeList;
	}

	// ʹ��LinkedList���ڵڶ���ѭ�������mergeFlag
	public static List<TypedEdge> threeOrder3(TypedGraph baseGraph, TypedGraph resultGraph,
			TypedGraph... branchGraphs) {

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

		List<TypedEdge> resultList = resultGraph.getAllTypedEdges();
		List<TypedEdge> mergeList = new LinkedList<>(resultList);
		HashMap<TypedEdge, Integer> mergeFlag = new HashMap<>();

		for (int i = 0; i < resultList.size() - 1; i++) {
			TypedEdge ei = resultList.get(i);
			// ����mergeList��λ��ӳ��
			int m = 0;
			for (TypedEdge typedEdge : mergeList) {
				mergeFlag.put(typedEdge, m++);
			}
			for (int j = i + 1; j < resultList.size(); j++) {
				TypedEdge ej = resultList.get(j);
				for (int k = 0; k < len; k++) {
					try {
						TypedEdge ei_k = branchGraphs[k].getElementByIndexObject(ei.getIndex());
						TypedEdge ej_k = branchGraphs[k].getElementByIndexObject(ej.getIndex());
						if (branchFlag[k].get(ei_k) - branchFlag[k].get(ej_k) < 0) {
							continue;
						} else {
							// ʵ���ϣ�resultList������baseList������Ϣ
							if (mergeFlag.get(ei) > mergeFlag.get(ej)) {
								continue;
							} else {
								mergeList.remove(ej);
								mergeList.add(mergeList.indexOf(ei), ej);
								break;
							}
						}

					} catch (NothingReturnedException e) {
						// ˵�����Ǵ˷�֧�¼ӵģ�����
						// ����һ������Ƿ�֧ͼֻ������Ԫ���Լ������Ԫ�ص�������
					}
				}
			}
		}
		return mergeList;
	}

	// ����computeOrd()
	public static List<TypedEdge> threeOrder4(TypedGraph baseGraph, TypedGraph resultGraph,
			HashMap<TypedEdge, TypedEdge> forceOrd, TypedGraph... branchGraphs) {

		List<TypedEdge> resultList = resultGraph.getAllTypedEdges();
		List<TypedEdge> mergeList = new LinkedList<>(resultList);
		HashMap<TypedEdge, Integer> mergeFlag = new HashMap<>();

		for (int i = 0; i < resultList.size() - 1; i++) {
			TypedEdge ei = resultList.get(i);
			// ����mergeList��λ��ӳ��
			int m = 0;
			for (TypedEdge typedEdge : mergeList) {
				mergeFlag.put(typedEdge, m++);
			}
			for (int j = i + 1; j < resultList.size(); j++) {
				TypedEdge ej = resultList.get(j);
				Tuple2<Character, Character> computeOrd = computeOrd(ei, ej, baseGraph, forceOrd, branchGraphs);
				if (computeOrd.second == 'n' || computeOrd.second == '<') {
					continue;
				} else {
					// ʵ���ϣ�resultList������baseList������Ϣ
					if (mergeFlag.get(ei) > mergeFlag.get(ej)) {
						continue;
					} else {
						mergeList.remove(ej);
						mergeList.add(mergeList.indexOf(ei), ej);
					}
				}
			}
		}
		return mergeList;
	}

	// ����ǿ�����ϵ
	public static List<TypedEdge> forceOrder1(List<TypedEdge> mergeList, Set<Tuple2<Index, Index>> orders)
			throws NothingReturnedException {

		// helper�����ж�����������ͻ
		Map<Index, Integer> helper = new HashMap<>();
		for (int i = 0; i < mergeList.size(); i++) {
			helper.put(mergeList.get(i).getIndex(), 0);
		}

		// ringFlag�����ж����޻���ͻ
		HashMap<Index, Index> ringFlag = new HashMap<>();
		Iterator<Tuple2<Index, Index>> iterator = orders.iterator();
		while (iterator.hasNext()) {
			Tuple2<Index, Index> order = iterator.next();
			if (helper.get(order.first) != null && helper.get(order.second) != null) {
				ringFlag.put(order.first, order.second);
			} else {
				// first��second�Ҳ����Ļ���ɾ����order
				iterator.remove();
			}
		}

		// �ȼ������ϵ���ϵĺϷ���
		for (Tuple2<Index, Index> order : orders) {

			int firstHelper = helper.get(order.first);
			int secondHelper = helper.get(order.second);

			switch (firstHelper) {
			case 0:
				helper.replace(order.first, 1); // �ĳ�1
				break;
			case 1:
				throw new NothingReturnedException("ǿ��������<x, y>&&<x, z>��ͻ");
			default:
				// ��firstΪ2ʱ��������£�
				break;
			}

			switch (secondHelper) {
			case 0:
				helper.replace(order.second, 2); // �ĳ�2
				break;
			case 2:
				throw new NothingReturnedException("ǿ��������<y, x>&&<z, x>��ͻ");
			default:
				// ��secondΪ1ʱ��������£�
				break;
			}

			// ��⻷��ͻ
			Tuple2<Index, Index> tmp = new Tuple2<>(order.first, order.second); // ���򣺲���ֱ��tmp = order������order������
			while (ringFlag.get(tmp.second) != null) {
				if (ringFlag.get(tmp.second).equals(order.first)) {
					throw new NothingReturnedException("ǿ�������л���ͻ");
				}
				tmp.second = ringFlag.get(tmp.second);
			}

		}

		// �Ϸ�����ִ��ǿ����
		for (Tuple2<Index, Index> order : orders) {

			HashMap<Index, Integer> mergeFlag = new HashMap<>();
			// ÿ��ѭ���ﶼҪ���¸���mergeFlag
			for (int i = 0; i < mergeList.size(); i++) {
				mergeFlag.put(mergeList.get(i).getIndex(), i);
			}

			// Index���hashCode()��equals()��д�ˣ�ֻҪIndex������ڲ��������н����������ҵ�
			int firstPosition = mergeFlag.get(order.first);
			int secondPosition = mergeFlag.get(order.second);

			// �д��жϣ��ɼ��ٲ���Ҫ����
			if (secondPosition == firstPosition + 1)
				continue;

			TypedEdge front = mergeList.get(firstPosition);
			TypedEdge back = mergeList.get(secondPosition);

			// ���helper��firstΪ1��secondΪ2����front������Ĭ�ϣ�
			// ���helper��firstΪ2��secondΪ2����frontҲ����
			if (helper.get(order.first) == 1 && helper.get(order.second) == 2
					|| helper.get(order.first) == 2 && helper.get(order.second) == 2) {
				mergeList.remove(back);
				mergeList.add(mergeList.indexOf(front) + 1, back);
			}
			// ���helper��firstΪ1��secondΪ1����back����
			else if (helper.get(order.first) == 1 && helper.get(order.second) == 1) {
				mergeList.remove(front);
				mergeList.add(mergeList.indexOf(back), front);
			}

			// ���helper��firstΪ2��secondΪ1����<back, back+1>����
			// ��<front-1, front>�Ƶ���ǰ��
			else if (helper.get(order.first) == 2 && helper.get(order.second) == 1) {
				TypedEdge previousFront = mergeList.get(firstPosition - 1);
				mergeList.remove(previousFront);
				mergeList.remove(front);
				mergeList.add(mergeList.indexOf(back), previousFront);
				mergeList.add(mergeList.indexOf(back), front);
			}

		}

		return mergeList;
	}

	// ��getElementByIndexObject()
	public static List<TypedEdge> forceOrder2(List<TypedEdge> mergeList, Set<Tuple2<TypedEdge, TypedEdge>> orders,
			TypedGraph resultGraph) throws NothingReturnedException {
	
		// helper�����ж�����������ͻ
		Map<TypedEdge, Integer> helper = new HashMap<>();
		for (int i = 0; i < mergeList.size(); i++) {
			helper.put(mergeList.get(i), 0);
		}
	
		// ringFlag�����ж����޻���ͻ
		HashMap<TypedEdge, TypedEdge> ringFlag = new HashMap<>();
		Iterator<Tuple2<TypedEdge, TypedEdge>> iterator = orders.iterator();
		while (iterator.hasNext()) {
			Tuple2<TypedEdge, TypedEdge> order = iterator.next();
			try {
				resultGraph.getElementByIndexObject(order.first.getIndex());
				resultGraph.getElementByIndexObject(order.second.getIndex());
				ringFlag.put(order.first, order.second);
			} catch (NothingReturnedException e) {
				// first��second�Ҳ����Ļ���ɾ����order
				iterator.remove();
			}
		}
	
		// �ȼ������ϵ���ϵĺϷ���
		for (Tuple2<TypedEdge, TypedEdge> order : orders) {
	
			TypedEdge efirst = resultGraph.getElementByIndexObject(order.first.getIndex());
			TypedEdge esecond = resultGraph.getElementByIndexObject(order.second.getIndex());
	
			int firstHelper = helper.get(efirst);
			int secondHelper = helper.get(esecond);
	
			switch (firstHelper) {
			case 0:
				helper.replace(efirst, 1); // �ĳ�1
				break;
			case 1:
				throw new NothingReturnedException("ǿ��������<x, y>&&<x, z>��ͻ");
			default:
				// ��firstΪ2ʱ��������£�
				break;
			}
	
			switch (secondHelper) {
			case 0:
				helper.replace(esecond, 2); // �ĳ�2
				break;
			case 2:
				throw new NothingReturnedException("ǿ��������<y, x>&&<z, x>��ͻ");
			default:
				// ��secondΪ1ʱ��������£�
				break;
			}
	
			// ��⻷��ͻ
			// ���򣺲���ֱ��tmp = order������order������
			Tuple2<TypedEdge, TypedEdge> tmp = new Tuple2<>(order.first, order.second);
			while (ringFlag.get(tmp.second) != null) {
				if (ringFlag.get(tmp.second).equals(order.first)) {
					throw new NothingReturnedException("ǿ�������л���ͻ");
				}
				tmp.second = ringFlag.get(tmp.second);
			}
	
		}
	
		// �Ϸ�����ִ��ǿ����
		for (Tuple2<TypedEdge, TypedEdge> order : orders) {
	
			TypedEdge front = resultGraph.getElementByIndexObject(order.first.getIndex());
			TypedEdge back = resultGraph.getElementByIndexObject(order.second.getIndex());
	
			// mergeList�ڱ�
			int firstPosition = mergeList.indexOf(front);
			int secondPosition = mergeList.indexOf(back);
	
			// �д��жϣ��ɼ��ٲ���Ҫ����
			if (secondPosition == firstPosition + 1)
				continue;
	
			// ���helper��firstΪ1��secondΪ2����front������Ĭ�ϣ�
			// ���helper��firstΪ2��secondΪ2����frontҲ����
			if (helper.get(front) == 1 && helper.get(back) == 2 || helper.get(front) == 2 && helper.get(back) == 2) {
				mergeList.remove(back);
				mergeList.add(mergeList.indexOf(front) + 1, back);
			}
			// ���helper��firstΪ1��secondΪ1����back����
			else if (helper.get(front) == 1 && helper.get(back) == 1) {
				mergeList.remove(front);
				mergeList.add(mergeList.indexOf(back), front);
			}
	
			// ���helper��firstΪ2��secondΪ1����<back, back+1>����
			// ��<front-1, front>�Ƶ���ǰ��
			else if (helper.get(front) == 2 && helper.get(back) == 1) {
				TypedEdge previousFront = mergeList.get(firstPosition - 1);
				mergeList.remove(previousFront);
				mergeList.remove(front);
				mergeList.add(mergeList.indexOf(back), previousFront);
				mergeList.add(mergeList.indexOf(back), front);
			}
	
		}
	
		return mergeList;
	}

	public static HashMap<TypedEdge, TypedEdge> checkForceOrd(TypedGraph resultGraph,
			Set<Tuple2<TypedEdge, TypedEdge>> orders) throws NothingReturnedException {

		HashMap<TypedEdge, TypedEdge> orderMap = new HashMap<>();

		if (orders.size() == 0) {
			return orderMap;
		}

		// ringFlag�����ж����޻���ͻ
		HashMap<TypedEdge, TypedEdge> ringFlag = new HashMap<>();
		Iterator<Tuple2<TypedEdge, TypedEdge>> iterator = orders.iterator();
		while (iterator.hasNext()) {
			Tuple2<TypedEdge, TypedEdge> order = iterator.next();
			try {
				resultGraph.getElementByIndexObject(order.first.getIndex());
				resultGraph.getElementByIndexObject(order.second.getIndex());
				ringFlag.put(order.first, order.second);
			} catch (NothingReturnedException e) {
				// first��second�Ҳ����Ļ���ɾ����order
				iterator.remove();
			}
		}

		// �ȼ������ϵ���ϵĺϷ���
		for (Tuple2<TypedEdge, TypedEdge> order : orders) {

			// ��⻷��ͻ
			// ���򣺲���ֱ��tmp = order������order������
			Tuple2<TypedEdge, TypedEdge> tmp = new Tuple2<>(order.first, order.second);
			while (ringFlag.get(tmp.second) != null) {
				if (ringFlag.get(tmp.second).equals(order.first)) {
					throw new NothingReturnedException("ǿ�������л���ͻ");
				}
				tmp.second = ringFlag.get(tmp.second);
			}

		}

		// ת��ΪHashMap
		for (Tuple2<TypedEdge, TypedEdge> order : orders) {
			orderMap.put(order.first, order.second);
		}
		return orderMap;
	}

}
