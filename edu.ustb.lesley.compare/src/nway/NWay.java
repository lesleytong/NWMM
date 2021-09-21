package nway;

/**
 * ��չEMF Compare
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.apache.commons.collections4.map.MultiKeyMap;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.DifferenceKind;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.internal.spec.ComparisonSpec;
import org.eclipse.emf.compare.internal.spec.MatchSpec;
import org.eclipse.emf.compare.internal.spec.ReferenceChangeSpec;
import org.eclipse.emf.compare.match.IMatchEngine;
import org.eclipse.emf.compare.match.eobject.EditionDistance;
import org.eclipse.emf.compare.match.eobject.ProximityEObjectMatcher.DistanceFunction;
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryImpl;
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryRegistryImpl;
import org.eclipse.emf.compare.scope.DefaultComparisonScope;
import org.eclipse.emf.compare.scope.IComparisonScope;
import org.eclipse.emf.compare.utils.UseIdentifiers;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;

import edu.ustb.sei.mde.bxcore.XmuCoreUtils;
import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.bxcore.util.EcoreModelUtil;
import edu.ustb.sei.mde.bxcore.util.XmuProgram;
import edu.ustb.sei.mde.graph.type.DataTypeNode;
import edu.ustb.sei.mde.graph.type.PropertyEdge;
import edu.ustb.sei.mde.graph.type.TypeEdge;
import edu.ustb.sei.mde.graph.type.TypeGraph;
import edu.ustb.sei.mde.graph.type.TypeNode;
import edu.ustb.sei.mde.graph.typedGraph.BXMerge3;
import edu.ustb.sei.mde.graph.typedGraph.TypedEdge;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;
import edu.ustb.sei.mde.graph.typedGraph.TypedNode;
import edu.ustb.sei.mde.graph.typedGraph.ValueEdge;
import edu.ustb.sei.mde.graph.typedGraph.ValueNode;
import my.ComparisonN;
import my.MatchN;
import my.impl.ComparisonNImpl;
import my.impl.MatchNImpl;

@SuppressWarnings("restriction")
public class NWay extends XmuProgram {

	private TypeGraph typeGraph = null;
	private ArrayList<Resource> resources = new ArrayList<>();

	public NWay(TypeGraph typeGraph) {
		this.typeGraph = typeGraph;
	}

	public List<MatchN> nMatch(List<Resource> resourceList) {
		
		long start = System.currentTimeMillis();

		Resource baseResource = resourceList.get(0);
		Resource branchResource1 = resourceList.get(1);

		// ���㴫�����ǵĺϲ�����
		resources.add(baseResource);
		resources.add(branchResource1);

		// never use identifiers
		IMatchEngine.Factory.Registry registry = MatchEngineFactoryRegistryImpl.createStandaloneInstance();
		final MatchEngineFactoryImpl matchEngineFactory = new MatchEngineFactoryImpl(UseIdentifiers.NEVER);
		matchEngineFactory.setRanking(20);
		registry.add(matchEngineFactory);
		IComparisonScope scope = new DefaultComparisonScope(branchResource1, baseResource, null);

		// ��comparison�ǵ�һ����֧��base�ȽϵĽ������Ϊȫ�ֵ�comparison��֮��������������Ϣ
		Comparison comparison = EMFCompare.builder().setMatchEngineFactoryRegistry(registry).build().compare(scope);
		
		// ��������ΪADD������diff
		Map<EObject, Diff> addDiffsMap = new HashMap<>();

		// Ϊ�˴���ADDԪ�أ�base���һ����֧�ģ�
		for (Diff diff : comparison.getDifferences()) {
			if (diff.getKind() == DifferenceKind.ADD) {
				try {
					ReferenceChangeSpec diffADD = (ReferenceChangeSpec) diff;
					EObject left = diffADD.getValue();
					// �Ѿ���ADD��diff�ˣ�EObject rightһ��Ϊnull�����ö��һ��ȥ�ж���
					// ��һ��������������µļ̳й�ϵ����û���¼���
					EObject right = diffADD.getMatch().getComparison().getMatch(left).getRight();
					if (right == null) { // ֻ���漰���¼�EObject�Ĳ��ܼ��뵽addDiffsMap
						addDiffsMap.put(left, diff); // ����һ�´�EObject��Ӧ��ADD diff
					}
				} catch (Exception e) {
					System.out.println("NWay line 118");
				}


			}
		}

		Comparison branchComparison = null;
		for (int j = 2; j < resourceList.size(); j++) {
			Resource branchResource = resourceList.get(j);

			resources.add(branchResource);

			scope = new DefaultComparisonScope(branchResource, baseResource, null);
			branchComparison = EMFCompare.builder().setMatchEngineFactoryRegistry(registry).build().compare(scope);

			List<Match> branchMatches = branchComparison.getMatches();
			List<Diff> branchDiffs = branchComparison.getDifferences();

			// Ϊ�˴���ADDԪ�أ�base��������֧�ģ�
			for (Diff diff : branchDiffs) { // ������branchComparison
				if (diff.getKind() == DifferenceKind.ADD) {
					try {
						ReferenceChangeSpec diffADD = (ReferenceChangeSpec) diff;
						EObject left = diffADD.getValue();
						// �Ѿ���ADD��diff�ˣ�EObject rightһ��Ϊnull�����ö��һ��ȥ�ж���
						// ��һ��������������µļ̳й�ϵ����û���¼���
						EObject right = diffADD.getMatch().getComparison().getMatch(left).getRight();
						if (right == null) { // ֻ���漰���¼�EObject�Ĳ��ܼ��뵽addDiffsMap
							addDiffsMap.put(left, diff); // ����һ�´�EObject��Ӧ��ADD diff
						}
					} catch (Exception e) {
						System.out.println("NWay line 150");
					}
				}
			}

			// ��ӵ�ȫ��diffs��matches��
			comparison.getDifferences().addAll(branchDiffs);

			// ����֣�����addAll��branchMatchesΪnull
			comparison.getMatches().addAll(branchMatches);

		}
		
		// ��comparisonN����base�������֧��Ԫ��ƥ����Ϣ
		ComparisonN comparisonN = new ComparisonNImpl();
		Map<EObject, List<EObject>> haveBaseMap = new HashMap<>();
		groupMatches(comparison.getMatches(), haveBaseMap);
		haveBaseMap.forEach((key, value) -> {
			MatchN matchN = new MatchNImpl();
			matchN.setBase(key);
			value.forEach(v -> {
				if (v != null) {
					matchN.getBranches().add(v);
				}
			});
			comparisonN.getMatches().add(matchN);
		});

		long end = System.currentTimeMillis();
		System.out.println("base�������֧��ƥ���ʱ��" + (end - start) + " ms.");
		
		start = System.currentTimeMillis();
		
		// ����haveBaseMap
		MultiKeyMap<Integer, List<Match>> pre = new MultiKeyMap<>();
		for (List<EObject> value : haveBaseMap.values()) {
			for (int i = 1; i < resourceList.size() - 1; i++) {
				EObject first = value.get(i - 1); // haveBaseMap�±�Ϊ0 -> ��Ӧbranch1
				for (int j = i + 1; j < resourceList.size(); j++) {
					EObject second = value.get(j - 1);
					List<Match> list = pre.get(i, j);
					if (list == null) {
						list = new ArrayList<>();
						pre.put(i, j, list);
					}
					Match match = new MatchSpec();
					match.setLeft(first);
					match.setRight(second);
					match.setOrigin(null);
					list.add(match);
				}
			}
		}

		// ����֮�����༭����
		// ʵ����<Resource, Resource, List<Match>>�����������һ������ʡ�ԣ�������Ͳ�һ��������ʽд����������д��Object
		MultiKeyMap<Resource, List<Match>> multiKeyMap = new MultiKeyMap<>();

		/** ADDԪ�ص�ƥ�� */
		if (addDiffsMap.size() == 1) { // ֻ��һ���¼ӣ�����Ҫ���㼫����
			MatchN matchN = new MatchNImpl();
			matchN.getBranches().addAll(addDiffsMap.keySet());
			comparisonN.getMatches().add(matchN);
		} else if (addDiffsMap.size() > 1) {
			Set<Match> allADDMatches = new HashSet<>();
			for (int i = 1; i < resourceList.size() - 1; i++) {

				Resource resourceI = resources.get(i);

				for (int j = i + 1; j < resourceList.size(); j++) {

					Resource resourceJ = resources.get(j);

					List<Match> preMatches = pre.get(i, j);

					// Ϊ��֮�����༭���ۣ�resourceI��resourceJ��Ϊ��
					multiKeyMap.put(resourceI, resourceJ, preMatches);
					multiKeyMap.put(resourceJ, resourceI, preMatches); // ����֮����Լ������нڵ�����

					scope = new DefaultComparisonScope(resourceI, resourceJ, null);
					NWayDefaultMatchEngine engine = (NWayDefaultMatchEngine) NWayDefaultMatchEngine
							.create(UseIdentifiers.NEVER);
					Comparison comparisonADD = engine.matchN(scope, preMatches, addDiffsMap, new BasicMonitor());

					// ���µõ��Ĺ���ADDԪ�ص�ƥ�䣬�ŵ�allADDMatches��
					filerADDMatches(allADDMatches, comparisonADD.getMatches(), preMatches);

				}
			}
			
			end = System.currentTimeMillis();
			System.out.println("����֧ADDԪ��ƥ���ʱ��" + (end - start) + " ms.");
			
			start = System.currentTimeMillis();

			/** ���� */
			// BKWithPivot
			MaximalCliquesWithPivot ff = new MaximalCliquesWithPivot();
			ff.initGraph(addDiffsMap.keySet(), allADDMatches);
			List<List<EObject>> maximalCliques = new ArrayList<>();
			ff.Bron_KerboschPivotExecute(maximalCliques);

			// ��EMF Compare�Դ��ı༭�������ÿ�������ŵķ���
			Map<Integer, Info> map = new HashMap<>();
			for (int i = 0; i < maximalCliques.size(); i++) {
				List<EObject> List = maximalCliques.get(i);
				int sumCost = sumEditionDistance(List, multiKeyMap); // ������ܵı༭����
				Info info = new Info(List.size(), sumCost);
				map.put(i, info);
			}

			// �ȱȽ�size��ֵ�����ǰ�棩����size��ͬʱ�ٱȽ�sumMinCost��ֵС����ǰ�棩
			List<Integer> sortedList = map.entrySet().stream()
					.sorted(Entry.comparingByValue(
							Comparator.comparing(Info::getSize).reversed().thenComparing(Info::getMinCost)))
					.map(Map.Entry::getKey).collect(Collectors.toList());

			// ����sortedList
			for (int i = 0; i < sortedList.size() - 1; i++) {
				List<EObject> preClique = maximalCliques.get(sortedList.get(i));
				for (int j = i + 1; j < sortedList.size(); j++) {
					List<EObject> sucClique = maximalCliques.get(sortedList.get(j));
					if (Collections.disjoint(preClique, sucClique) == false) { // ���������Ϊ�յĻ�
						sortedList.remove(j);
						j--; // ����remove��������ǰ����һ��
					}
				}
			}

			// ��comparisonN����һ��
			sortedList.forEach(i -> {
				// ���ݸ��º��sortedList��Ӧ��maximalCliques��
				List<EObject> List = maximalCliques.get(i);
				MatchN matchN = new MatchNImpl();
				matchN.getBranches().addAll(List);
				comparisonN.getMatches().add(matchN);
			});

		}
		
		end = System.currentTimeMillis();
		System.out.println("���ź�ʱ��" + (end - start) + " ms.");

		return comparisonN.getMatches();
	}

	/** MatchN�������ǵĺϲ�����������diff��merge */
	public TypedGraph nMerge(List<MatchN> matches, List<TypeEdge> typeEdgeList, List<PropertyEdge> propertyEdgeList) {

		Map<Resource, TypedGraph> typedGraphMap = new HashMap<>();
		HashMap<EObject, TypedNode> typedNodeMap = new HashMap<>();

		resources.forEach(r -> {
			typedGraphMap.put(r, new TypedGraph(typeGraph));
		});

		// EObject in MatchN -> new TypedNode
		for (MatchN m : matches) {
			EObject base = m.getBase();

			EList<EObject> branches = m.getBranches();
			if (base != null) {
				// TypedNode
				EClass cls = base.eClass();
				TypeNode typeNode = typeGraph.getTypeNode(cls.getName());
				final TypedNode baseNode = new TypedNode(typeNode);
				TypedGraph baseGraph = typedGraphMap.get(base.eResource());
				baseGraph.addTypedNode(baseNode);
				typedNodeMap.put(base, baseNode);

				// ValueEdge and ValueNode
				MultiKeyMap multiKeyMap = new MultiKeyMap();
				Map<PropertyEdge, ValueEdge> valueEdgeMap = new HashMap<>();
				addValueEdgesBase(base, baseNode, baseGraph, multiKeyMap, valueEdgeMap);

				// for branchGraph
				for (EObject b : branches) {
					// lyt: �����xmi:id����֧�Ľڵ�����޸�Ϊ��base��ͬ����
					TypedGraph branchGraph = typedGraphMap.get(b.eResource());
					EClass eClass = b.eClass();
					if (eClass == cls) {
						branchGraph.addTypedNode(baseNode);
						typedNodeMap.put(b, baseNode);

						addValueEdges(b, baseNode, branchGraph, multiKeyMap, valueEdgeMap);
					} else {
						typeNode = typeGraph.getTypeNode(eClass.getName());
						TypedNode branchNode = new TypedNode(typeNode);
						branchNode.mergeIndex(baseNode);
						branchGraph.addTypedNode(branchNode);
						typedNodeMap.put(b, branchNode);

						addValueEdges(b, branchNode, branchGraph, multiKeyMap, valueEdgeMap);
					}

				}

			} else { // without base

				// TypedNode
				EObject branchFirst = branches.get(0);
				TypeNode typeNode = typeGraph.getTypeNode(branchFirst.eClass().getName());
				TypedNode branchFirstNode = new TypedNode(typeNode);
				TypedGraph branchFirstGraph = typedGraphMap.get(branchFirst.eResource());
				branchFirstGraph.addTypedNode(branchFirstNode);
				typedNodeMap.put(branchFirst, branchFirstNode);

				// ValueEdge and ValueNode
				MultiKeyMap multiKeyMap = new MultiKeyMap();
				Map<PropertyEdge, ValueEdge> valueEdgeMap = new HashMap<>();
				addValueEdgesBase(branchFirst, branchFirstNode, branchFirstGraph, multiKeyMap, valueEdgeMap);

				// ����parallelStream
				for (int i = 1; i < branches.size(); i++) {
					EObject b = branches.get(i);
					TypedGraph branchGraph = typedGraphMap.get(b.eResource());
					branchGraph.addTypedNode(branchFirstNode);
					typedNodeMap.put(b, branchFirstNode);

					addValueEdges(b, branchFirstNode, branchGraph, multiKeyMap, valueEdgeMap);
				}

//				branches.parallelStream().skip(1).forEach(b -> {
//					// TypedNode
//					TypedGraph branchGraph = typedGraphMap.get(b.eResource());
//					branchGraph.addTypedNode(branchFirstNode);
//					typedNodeMap.put(b, branchFirstNode);
//
//					// ValueEdge and ValueNode
//					addValueEdges(b, branchFirstNode, branchGraph, multiKeyMap, valueEdgeMap);
//
//				});

			}

		}

		// TypedEdge�����±���MatchN
		for (MatchN m : matches) {
			EObject base = m.getBase();
			EList<EObject> branches = m.getBranches();

			if (base != null) {

				TypedNode baseNode = typedNodeMap.get(base);
				TypedGraph baseGraph = typedGraphMap.get(base.eResource());

				MultiKeyMap multiKeyMap = new MultiKeyMap();
				MultiKeyMap typedEdgeMap = new MultiKeyMap();
				addTypedEdgesBase(base, baseNode, baseGraph, typedNodeMap, multiKeyMap, typedEdgeMap);

				for (EObject b : branches) {
					TypedGraph branchGraph = typedGraphMap.get(b.eResource());
					addTypedEdges(b, baseNode, branchGraph, typedNodeMap, multiKeyMap, typedEdgeMap);
				}

			} else { // without base

				EObject branchFirst = branches.get(0);
				TypedNode branchFirstNode = typedNodeMap.get(branchFirst);
				TypedGraph branchFirstGraph = typedGraphMap.get(branchFirst.eResource());

				MultiKeyMap multiKeyMap = new MultiKeyMap();
				MultiKeyMap typedEdgeMap = new MultiKeyMap();
				addTypedEdgesBase(branchFirst, branchFirstNode, branchFirstGraph, typedNodeMap, multiKeyMap,
						typedEdgeMap);

				// ����parallelStream
				for (int i = 1; i < branches.size(); i++) {
					EObject b = branches.get(i);
					TypedGraph branchGraph = typedGraphMap.get(b.eResource());
					addTypedEdges(b, branchFirstNode, branchGraph, typedNodeMap, multiKeyMap, typedEdgeMap);
				}

//				// TypedEdge
//				branches.parallelStream().skip(1).forEach(b -> {
//					TypedGraph branchGraph = typedGraphMap.get(b.eResource());
//					addTypedEdges(b, branchFirstNode, branchGraph, typedNodeMap, multiKeyMap);
//				});

			}
		}

		try {

			TypedGraph baseGraph = typedGraphMap.get(resources.get(0));
			System.out.println("baseGraph: ");
			print(baseGraph);

			TypedGraph[] branchGraphs = new TypedGraph[resources.size() - 1];
			for (int i = 1; i < resources.size(); i++) {
				branchGraphs[i - 1] = typedGraphMap.get(resources.get(i));
				System.out.println("branchGraph[" + i + "]: ");
				print(branchGraphs[i - 1]);
			}

			TypedGraph resultGraph = BXMerge3.mergeSerial(baseGraph, branchGraphs);
//			TypedGraph resultGraph = BXMerge3.mergeParallel(baseGraph, branchGraphs);
			System.out.println("resultGraph: ");
			print(resultGraph);

			long start = System.currentTimeMillis();
			HashMap<TypedEdge, TypedEdge> forceOrd = new HashMap<>();
			BXMerge3.topoOrder(baseGraph, resultGraph, forceOrd, typeEdgeList, branchGraphs);
			long end = System.currentTimeMillis();
			long time1 = end - start;
			System.out.println("TypedEdge���ʱ��" + time1 + " ms.");

			start = System.currentTimeMillis();
			HashMap<ValueEdge, ValueEdge> forceOrd2 = new HashMap<>();
			BXMerge3.topoOrder2(baseGraph, resultGraph, forceOrd2, propertyEdgeList, branchGraphs);
			end = System.currentTimeMillis();
			long time2 = end - start;
			System.out.println("ValueEdge���ʱ��" + time2 + " ms.");

			System.out.println("---------------------------------------");
			System.out.println("���ܺ�ʱ��" + (time1 + time2) + " ms.");

			return resultGraph;

		} catch (NothingReturnedException e) {
			e.printStackTrace();
		}
		return null;

	}

	/** �������ܵı༭���� */
	public int sumEditionDistance(List<EObject> List, MultiKeyMap<Resource, List<Match>> multiKeyMap) {

		int sum = 0;
		DistanceFunction distanceFunction = new EditionDistance();

		for (int i = 0; i < List.size() - 1; i++) {
			EObject eObjectI = List.get(i);
			EObject eObjectJ = null;
			for (int j = i + 1; j < List.size(); j++) {
				eObjectJ = List.get(j);
				List<Match> preMatches = multiKeyMap.get(eObjectI.eResource(), eObjectJ.eResource());
				Comparison comparisonTmp = new ComparisonSpec();
				comparisonTmp.getMatches().addAll(preMatches);
				sum += distanceFunction.distance(comparisonTmp, eObjectI, eObjectJ);
			}
		}

		return sum;
	}

	/** ���ADDԪ�ص�ƥ�� */
	public void filerADDMatches(Set<Match> allADDMatches, List<Match> matches, List<Match> preMatches) {

		matches.forEach(match -> {
			if (preMatches.contains(match) == false) {
				// ������������ж�
				if (match.getLeft() != null && match.getRight() != null) {
					allADDMatches.add(match);
				}
			}
			// ���õݹ�
			match.getAllSubmatches().forEach(m -> {
				if (preMatches.contains(m) == false) {
					// ������������ж�
					if (m.getLeft() != null && m.getRight() != null) {
						allADDMatches.add(m);
					}
				}
			});
		});

	}

	/** �õ�Ԥƥ�� */
	public List<Match> getPreMatches(Map<EObject, List<EObject>> preMap) {
		List<Match> preMatches = new ArrayList<>();
		preMap.forEach((key, value) -> {
			for (int i = 0; i < value.size(); i++) {
				for (int j = i + 1; j < value.size(); j++) {
					Match match = new MatchSpec();
					match.setLeft(value.get(i));
					match.setRight(value.get(j));
					match.setOrigin(null);
					preMatches.add(match);
				}
			}
		});
		return preMatches;
	}

	/**
	 * preMap: û�����֧ɾ������� ������matchNûɶӰ�죬��Ϊ�κ�һ����֧ɾ�����ϲ��������ɾ�� List�ĸ������ˣ�Ӧ���൱�ں���ķ�֧ɾ����
	 */
	public void groupMatches(List<Match> matches, Map<EObject, List<EObject>> preMap) {
		for (Match match : matches) {
			EObject right = match.getRight(); // right��base�е�container
			EObject left = match.getLeft();
			if (right != null) {	// ������ɾ�������
				if (preMap.get(right) == null) {
					List<EObject> list = new ArrayList<>();
					list.add(left);
					preMap.put(right, list);
				} else {
					preMap.get(right).add(left);
				}
			}
			// ���õݹ�
			match.getAllSubmatches().forEach(m -> {
				EObject subRight = m.getRight();
				EObject subLeft = m.getLeft();
				if (subRight != null) {
					if (preMap.get(subRight) == null) {
						List<EObject> list = new ArrayList<>();
						list.add(subLeft);
						preMap.put(subRight, list);
					} else {
						preMap.get(subRight).add(subLeft);
					}
				}
			});

		}
	}

	/** ��ӡԪ��ƥ��Ľ�� */
	public void printMatches(List<Match> matches) {
		for (Match match : matches) {
			System.out.println(match);
			List<Match> submatches = match.getSubmatches();
			if (submatches != null) {
				printMatches(submatches);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void addTypedEdges(EObject b, final TypedNode baseNode, TypedGraph branchGraph,
			HashMap<EObject, TypedNode> typedNodeMap, MultiKeyMap multiKeyMap, MultiKeyMap typedEdgeMap) {
		EClass cls = b.eClass();
		TypeNode typeNode = typeGraph.getTypeNode(cls.getName());

		EList<EReference> tmp = cls.getEAllReferences();
		for (EReference r : tmp) {
			TypeEdge typeEdge = typeGraph.getTypeEdge(typeNode, r.getName());

			if (r.isMany()) { // multi-reference
				Collection<EObject> targets = (Collection<EObject>) b.eGet(r);
				targets.forEach(t -> {
					TypedNode targetNode = typedNodeMap.get(t);
					if (targetNode != null) {
						TypedEdge typedEdge = (TypedEdge) multiKeyMap.get(targetNode, typeEdge);
						if (typedEdge != null) {
							branchGraph.simAddTypedEdge(typedEdge);
						} else { // ���������޸�
							TypedEdge typedEdgeBranch = new TypedEdge(baseNode, targetNode, typeEdge);
							branchGraph.simAddTypedEdge(typedEdgeBranch);
						}
					}
				});
			} else { // single-reference
				EObject t = (EObject) b.eGet(r);
				if (t != null) {
					TypedNode targetNode = typedNodeMap.get(t);
					if (targetNode != null) {
						TypedNode targetNodeBase = (TypedNode) typedEdgeMap.get(typeEdge, targetNode.getType());
						TypedEdge typedEdge = (TypedEdge) multiKeyMap.get(targetNodeBase, typeEdge);
						if (typedEdge != null) {
							if (targetNode != targetNodeBase) { // ��Ϊ���޸�
								TypedEdge edge = new TypedEdge(baseNode, targetNode, typeEdge);
								branchGraph.simAddTypedEdge(edge); // ��addTypedEdge����mergeIndex
								edge.mergeIndex(typedEdge);
								branchGraph.reindexing(edge);
							} else { // ����
								branchGraph.simAddTypedEdge(typedEdge);
							}
						} else { // �¼�
							TypedEdge typedEdgeBranch = new TypedEdge(baseNode, targetNode, typeEdge);
							branchGraph.simAddTypedEdge(typedEdgeBranch);
						}
					}
				}
			}
		}

	}

	@SuppressWarnings("unchecked")
	public void addTypedEdgesBase(EObject base, TypedNode baseNode, TypedGraph baseGraph,
			HashMap<EObject, TypedNode> typedNodeMap, MultiKeyMap multiKeyMap, MultiKeyMap typedEdgeMap) {
		EClass cls = base.eClass();
		TypeNode typeNode = typeGraph.getTypeNode(cls.getName());

		EList<EReference> tmp = cls.getEAllReferences();
		for (EReference r : tmp) {

			TypeEdge typeEdge = typeGraph.getTypeEdge(typeNode, r.getName());

			if (r.isMany()) { // multi-reference

				Collection<EObject> targets = (Collection<EObject>) base.eGet(r);
				for (EObject t : targets) {
					TypedNode targetNode = typedNodeMap.get(t);
					if (targetNode == null) {
						XmuCoreUtils.log(Level.WARNING,
								"The target node is not registered. The loader will ignore this edge: " + r, null);
					} else {

						TypedEdge typedEdge = new TypedEdge(baseNode, targetNode, typeEdge);
						baseGraph.simAddTypedEdge(typedEdge);
						// record typedEdge
						multiKeyMap.put(targetNode, typeEdge, typedEdge);
					}
				}

			} else { // single-reference
				EObject t = (EObject) base.eGet(r);
				if (t != null) {
					TypedNode targetNode = typedNodeMap.get(t);
					if (targetNode == null) {
						XmuCoreUtils.log(Level.WARNING,
								"The target node is not registered. The loader will ignore this edge: " + r, null);
					} else {
						TypedEdge typedEdge = new TypedEdge(baseNode, targetNode, typeEdge);
						baseGraph.simAddTypedEdge(typedEdge);
						// record typedEdge
						multiKeyMap.put(targetNode, typeEdge, typedEdge);
						// for checking modification conflict
						typedEdgeMap.put(typeEdge, targetNode.getType(), targetNode);
					}
				}
			}
		}

	}

	public void addValueEdges(EObject b, TypedNode baseNode, TypedGraph branchGraph, MultiKeyMap multiKeyMap,
			Map<PropertyEdge, ValueEdge> valueEdgeMap) {
		EClass cls = b.eClass();
		TypeNode typeNode = typeGraph.getTypeNode(cls.getName());

		EList<EAttribute> tmp = cls.getEAllAttributes();
		for (EAttribute a : tmp) {
			DataTypeNode dataTypeNode = typeGraph.getDataTypeNode(a.getEAttributeType().getName());
			PropertyEdge propertyEdge = typeGraph.getPropertyEdge(typeNode, a.getName());

			if (a.isMany()) {
				@SuppressWarnings("unchecked")
				Collection<Object> values = (Collection<Object>) b.eGet(a);
				values.forEach(v -> {
					if (a.getEAttributeType() instanceof EEnum) {
						if (v instanceof Enumerator)
							v = ((Enumerator) v).getLiteral();
						else
							v = v.toString();
					}
					ValueNode vn = ValueNode.createConstantNode(v, dataTypeNode);
					ValueEdge valueEdge = (ValueEdge) multiKeyMap.get(vn, propertyEdge);
					if (valueEdge != null) {
						branchGraph.simAddValueEdge(valueEdge);
					} else { // ��ֵ���Բ��������޸�
						branchGraph.addValueNode(vn);
						ValueEdge branchValueEdge = new ValueEdge(baseNode, vn, propertyEdge);
						branchGraph.simAddValueEdge(branchValueEdge);
					}
				});
			} else { // single-valued
				Object v = b.eGet(a);
				if (v != null) {
					if (a.getEAttributeType() instanceof EEnum) {
						if (v instanceof Enumerator)
							v = ((Enumerator) v).getLiteral();
						else
							v = v.toString();
					}

					ValueEdge valueEdge = valueEdgeMap.get(propertyEdge);
					if (valueEdge != null) {
						// �����˸�toString()�ٱȽ�equals
						if (valueEdge.getTarget().getValue().toString().equals(v.toString())) {
							branchGraph.simAddValueEdge(valueEdge);
						} else { // ��ֵ���Կ������޸�

							ValueNode vn = ValueNode.createConstantNode(v, dataTypeNode);
							branchGraph.addValueNode(vn);
							ValueEdge edge = new ValueEdge(baseNode, vn, propertyEdge);
							branchGraph.simAddValueEdge(edge);
							edge.mergeIndex(valueEdge);
							branchGraph.reindexing(edge);
						}
					} else {
						// �����ValueEdge==null��˵���������ͣ��Ϳ�����֧�¼ӵ�valueEdge
						ValueNode vn = ValueNode.createConstantNode(v, dataTypeNode);
						branchGraph.addValueNode(vn); // ��һ����Ҫ������
						valueEdge = new ValueEdge(baseNode, vn, propertyEdge);
						branchGraph.simAddValueEdge(valueEdge);

					}
				}
			}
		}

	}

	// ValueEdge and ValueNode
	public void addValueEdgesBase(EObject base, TypedNode baseNode, TypedGraph baseGraph, MultiKeyMap multiKeyMap,
			Map<PropertyEdge, ValueEdge> valueEdgeMap) {
		EClass cls = base.eClass();
		TypeNode typeNode = typeGraph.getTypeNode(cls.getName());

		EList<EAttribute> tmp = cls.getEAllAttributes();
		for (EAttribute a : tmp) {
			DataTypeNode dataTypeNode = typeGraph.getDataTypeNode(a.getEAttributeType().getName());
			PropertyEdge propertyEdge = typeGraph.getPropertyEdge(typeNode, a.getName());

			if (a.isMany()) { // multi-valued
				@SuppressWarnings("unchecked")
				Collection<Object> values = (Collection<Object>) base.eGet(a);
				for (Object v : values) {
					if (a.getEAttributeType() instanceof EEnum) {
						if (v instanceof Enumerator) {
							v = ((Enumerator) v).getLiteral();
						} else {
							v = v.toString();
						}
					}

					ValueNode vn = ValueNode.createConstantNode(v, dataTypeNode);
					baseGraph.addValueNode(vn);
					ValueEdge valueEdge = new ValueEdge(baseNode, vn, propertyEdge);
					baseGraph.simAddValueEdge(valueEdge);

					// record valueEdge
					multiKeyMap.put(vn, propertyEdge, valueEdge);

				}

			} else { // single-valued
				Object v = base.eGet(a);
				if (v != null) {
					if (a.getEAttributeType() instanceof EEnum) {
						if (v instanceof Enumerator) {
							v = ((Enumerator) v).getLiteral();
						} else {
							v = v.toString();
						}
					}
					ValueNode vn = ValueNode.createConstantNode(v, dataTypeNode);

					baseGraph.addValueNode(vn);
					ValueEdge valueEdge = new ValueEdge(baseNode, vn, propertyEdge);
					baseGraph.simAddValueEdge(valueEdge);
					// record valueEdge
					valueEdgeMap.put(propertyEdge, valueEdge);
				}
			}
		}

	}

	public void saveModel(URI m1URI, TypedGraph graph, EPackage ep) throws NothingReturnedException {
		EcoreModelUtil.save(m1URI, graph, null, ep);
	}

	public void print(TypedGraph typedGraph) {
		System.out.println("TypedNodes: " + typedGraph.getAllTypedNodes().size());
		System.out.println("ValueNodes: " + typedGraph.getAllValueNodes().size());
		System.out.println("TypedEdges: " + typedGraph.getAllTypedEdges().size());
		System.out.println("ValueEdges: " + typedGraph.getAllValueEdges().size());
		System.out.println("*********************************************************************\n");
	}

}