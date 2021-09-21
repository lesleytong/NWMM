package nway;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.collections4.map.MultiKeyMap;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.DifferenceKind;
import org.eclipse.emf.compare.DifferenceSource;
import org.eclipse.emf.compare.DifferenceState;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.internal.spec.AttributeChangeSpec;
import org.eclipse.emf.compare.internal.spec.ReferenceChangeSpec;
import org.eclipse.emf.compare.match.IMatchEngine;
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryImpl;
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryRegistryImpl;
import org.eclipse.emf.compare.merge.BatchMerger;
import org.eclipse.emf.compare.merge.IBatchMerger;
import org.eclipse.emf.compare.merge.IMerger;
import org.eclipse.emf.compare.scope.DefaultComparisonScope;
import org.eclipse.emf.compare.scope.IComparisonScope;
import org.eclipse.emf.compare.utils.UseIdentifiers;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.graph.type.PropertyEdge;
import edu.ustb.sei.mde.graph.type.TypeEdge;
import edu.ustb.sei.mde.graph.type.TypeGraph;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;
import my.MatchN;

public class EvaluateEngine {

	// 统计真正的moveSize
	static int moveSize = 0;

	static IBatchMerger merger = new BatchMerger(IMerger.RegistryImpl.createStandaloneInstance());

	/** true is changeForXMI, false is changeForEcore */
	public static void getM0(Resource baseResource, URI m0URI, int choice) {
		switch (choice) {
		case 1:
			ChangeTool.changeForUML(baseResource, m0URI);
			break;
		case 2:
			ChangeTool.changeForEcore(baseResource, m0URI);
			break;
		case 3:
			ChangeTool.changeForAll(baseResource, m0URI);
			break;			
		case 4:
			ChangeTool.changeForUsecase(baseResource, m0URI);
		case 5:
			ChangeTool.changeForSequence(baseResource, m0URI);
		case 6:
			ChangeTool.changeForActivity(baseResource, m0URI);		
		default:
			break;
		}
		System.out.println("done");
	}

	public static void getBranches(Resource baseResource, Resource m0Resource, Resource backupResource,
			List<URI> uriList) {

		Random random = new Random();

		int N = uriList.size();

		IComparisonScope scope = new DefaultComparisonScope(baseResource, m0Resource, null);

		// never use identifiers
		IMatchEngine.Factory.Registry registry = MatchEngineFactoryRegistryImpl.createStandaloneInstance();
		final MatchEngineFactoryImpl matchEngineFactory = new MatchEngineFactoryImpl(UseIdentifiers.NEVER);
		matchEngineFactory.setRanking(20);
		registry.add(matchEngineFactory);
		Comparison comparison = EMFCompare.builder().setMatchEngineFactoryRegistry(registry).build().compare(scope);

		// 得到m0与base之间的diffs
		EList<Diff> diffs = comparison.getDifferences();
		// tmp
		diffs.forEach(d -> {
			System.out.println(d);
		});

		// tmp
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		diffs.forEach(d -> {
			if (d.getKind() == DifferenceKind.MOVE) {
				System.out.println(d);
			}
		});

		Map<EObject, EObject> eObjectMap = new HashMap<>();
		Map<EObject, Diff> eObjectDiffMap = new HashMap<>();
		Map<List<EObject>, List<EObject>> targetsMap = new HashMap<>();

		MultiKeyMap<Object, List<EObject>> moveForTargets = new MultiKeyMap<>();
		MultiKeyMap<Object, List<EObject>> leftTargetsMap = new MultiKeyMap<>();
		MultiKeyMap<Object, Boolean> checkNotMap = new MultiKeyMap<>();
		MultiKeyMap<Object, Boolean> checkExistMap = new MultiKeyMap<>();

		// tmp: 试一下跳过
		for (Diff d : diffs) {
			if (d.getKind() == DifferenceKind.MOVE) {
				moveSize++;
				if (d instanceof AttributeChangeSpec) {
					// PENDING: 还未处理AttributeChangeSpec的MOVE
				} else if (d instanceof ReferenceChangeSpec) {

					EObject rightContainer = d.getMatch().getRight();
					if (rightContainer == null) {
						System.out.println("line 127");
						continue;
					}

					ReferenceChangeSpec dr = (ReferenceChangeSpec) d;
					// 适用所有的reference
					EReference r = dr.getReference();

					if (r.getName().contains("ownedEnd")) {
						System.out.println("line 136");
						continue;
					}

					Match match = d.getMatch().getComparison().getMatch(dr.getValue());
					EObject left = match.getLeft(); // baseResource中的元素
					EObject right = match.getRight(); // m0中的元素
					eObjectMap.put(left, right);
					eObjectDiffMap.put(left, d);

					EObject leftContainer = d.getMatch().getLeft();
					// 保存baseResource中move的元素
					List<EObject> list = (List<EObject>) moveForTargets.get(leftContainer, r);
					if (list == null) {
						list = new ArrayList<>();
						moveForTargets.put(leftContainer, r, list);
					}
					list.add(left);

					List<EObject> leftTargets = (List<EObject>) leftContainer.eGet(r);
					leftTargetsMap.put(leftContainer, r, leftTargets);

					List<EObject> rightTargets = (List<EObject>) rightContainer.eGet(r);

					targetsMap.put(leftTargets, rightTargets);

				}
			}
		}

//		diffs.forEach(d -> {

//			if (d.getKind() == DifferenceKind.MOVE) {
//
//				moveSize++;
//
//				if (d instanceof AttributeChangeSpec) {
//					// PENDING: 还未处理AttributeChangeSpec的MOVE
//				} else if (d instanceof ReferenceChangeSpec) {
//					ReferenceChangeSpec dr = (ReferenceChangeSpec) d;
//					// 适用所有的reference
//					EReference r = dr.getReference();
//					Match match = d.getMatch().getComparison().getMatch(dr.getValue());
//					EObject left = match.getLeft(); // baseResource中的元素
//					EObject right = match.getRight(); // m0中的元素
//					eObjectMap.put(left, right);
//					eObjectDiffMap.put(left, d);
//
//					EObject leftContainer = d.getMatch().getLeft();
//					// 保存baseResource中move的元素
//					List<EObject> list = (List<EObject>) moveForTargets.get(leftContainer, r);
//					if (list == null) {
//						list = new ArrayList<>();
//						moveForTargets.put(leftContainer, r, list);
//					}
//					list.add(left);
//
//					List<EObject> leftTargets = (List<EObject>) leftContainer.eGet(r);
//					leftTargetsMap.put(leftContainer, r, leftTargets);
//
//					EObject rightContainer = d.getMatch().getRight();
//					List<EObject> rightTargets = (List<EObject>) rightContainer.eGet(r);
//
//					targetsMap.put(leftTargets, rightTargets);
//
//				}
//			}
//		});

		if (moveForTargets.size() != 0) {

			moveForTargets.forEach((key, value) -> {

				for (int i = 0; i < value.size() - 1; i++) {
					Object container = key.getKey(0);
					Object r = key.getKey(1);
					EObject first = value.get(i);
					List<EObject> leftTargets = leftTargetsMap.get(container, r);
					Integer firstIndexInBase = leftTargets.indexOf(first);
					List<EObject> rightTargets = targetsMap.get(leftTargets);
					Integer firstIndexInM0 = rightTargets.indexOf(eObjectMap.get(first));

					for (int j = i + 1; j < value.size(); j++) {
						EObject second = value.get(j);
						Integer secondIndexInBase = leftTargets.indexOf(second);
						Integer secondIndexInM0 = rightTargets.indexOf(eObjectMap.get(second));
						// base和m0中都是<x, y>，之后检查每个分支都不存在<y, x>
						// base中是<x, y>，m0中是<y, x>，之后检查任一分支中存在<y, x>
						int fsInBase = firstIndexInBase - secondIndexInBase;
						int fsInM0 = firstIndexInM0 - secondIndexInM0;
						if (fsInBase < 0 && fsInM0 < 0) {
							checkNotMap.put(container, r, first, second, true);
						} else if (fsInBase > 0 && fsInM0 > 0) {
							checkNotMap.put(container, r, second, first, true);
						} else if (fsInBase < 0 && fsInM0 > 0) {
							checkExistMap.put(container, r, second, first, false);
						} else if (fsInBase > 0 && fsInM0 < 0) {
							checkExistMap.put(container, r, first, second, false);
						}

					}
				}

			});

		}

		// 分配diffs
		Collection<Collection<Diff>> collections = new HashSet<>();
		Collection<Diff> other = new HashSet<>();

		diffs.forEach(d -> {
			EList<Diff> requires = d.getRequires();
			EList<Diff> requiredBy = d.getRequiredBy();

			if (requires.size() != 0) {
				Collection<Diff> group = new HashSet<>();
				group.add(d);
				group.addAll(requires);
				collections.add(group);
			} else if (requiredBy.size() == 0) { // 必须用else if
				other.add(d);
			}
		});

		// diffList的初始化
		List<List<Diff>> diffList = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			List<Diff> diff = new ArrayList<>();
			diffList.add(diff);
		}

		int branchSize = N - 1;
		if (branchSize == 3) {
			// 3个分支
			collections.forEach(group -> {
				double flag = random.nextDouble();
				if (flag >= 0.7) {
					diffList.get(1).addAll(group);
				} else if (flag <= 0.3) {
					diffList.get(2).addAll(group);
				} else {
					diffList.get(3).addAll(group);
				}
			});

			// 将other中的diff随机分配给分支版本
			other.forEach(d -> {
				double flag = random.nextDouble();
				if (flag >= 0.7) {
					diffList.get(1).add(d);
				} else if (flag <= 0.3) {
					diffList.get(2).add(d);
				} else {
					diffList.get(3).add(d);
				}
			});
		} else if (branchSize == 5) {
			// 5个分支
			collections.forEach(group -> {
				double flag = random.nextDouble();
				if (flag >= 0.8) {
					diffList.get(1).addAll(group);
				} else if (flag >= 0.6) {
					diffList.get(2).addAll(group);
				} else if (flag >= 0.4) {
					diffList.get(3).addAll(group);
				} else if (flag >= 0.2) {
					diffList.get(4).addAll(group);
				} else {
					diffList.get(5).addAll(group);
				}
			});

			// 将other中的diff随机分配给分支版本
			other.forEach(d -> {
				double flag = random.nextDouble();
				if (flag >= 0.8) {
					diffList.get(1).add(d);
				} else if (flag >= 0.6) {
					diffList.get(2).add(d);
				} else if (flag >= 0.4) {
					diffList.get(3).add(d);
				} else if (flag >= 0.2) {
					diffList.get(4).add(d);
				} else {
					diffList.get(5).add(d);
				}
			});
		} else if (branchSize == 7) {
			// 7个分支
			collections.forEach(group -> {
				double flag = random.nextDouble();
				if (flag >= 0.86) {
					diffList.get(1).addAll(group);
				} else if (flag >= 0.72) {
					diffList.get(2).addAll(group);
				} else if (flag >= 0.58) {
					diffList.get(3).addAll(group);
				} else if (flag >= 0.44) {
					diffList.get(4).addAll(group);
				} else if (flag >= 0.3) {
					diffList.get(5).addAll(group);
				} else if (flag >= 0.16) {
					diffList.get(6).addAll(group);
				} else {
					diffList.get(7).addAll(group);
				}
			});

			// 将other中的diff随机分配给分支版本
			other.forEach(d -> {
				double flag = random.nextDouble();
				if (flag >= 0.86) {
					diffList.get(1).add(d);
				} else if (flag >= 0.72) {
					diffList.get(2).add(d);
				} else if (flag >= 0.58) {
					diffList.get(3).add(d);
				} else if (flag >= 0.44) {
					diffList.get(4).add(d);
				} else if (flag >= 0.3) {
					diffList.get(5).add(d);
				} else if (flag >= 0.16) {
					diffList.get(6).add(d);
				} else {
					diffList.get(7).add(d);
				}
			});
		} else if (branchSize == 9) {
			// 9个分支
			collections.forEach(group -> {
				double flag = random.nextDouble();
				if (flag >= 0.89) {
					diffList.get(1).addAll(group);
				} else if (flag >= 0.78) {
					diffList.get(2).addAll(group);
				} else if (flag >= 0.67) {
					diffList.get(3).addAll(group);
				} else if (flag >= 0.56) {
					diffList.get(4).addAll(group);
				} else if (flag >= 0.45) {
					diffList.get(5).addAll(group);
				} else if (flag >= 0.34) {
					diffList.get(6).addAll(group);
				} else if (flag >= 0.23) {
					diffList.get(7).addAll(group);
				} else if (flag >= 0.12) {
					diffList.get(8).addAll(group);
				} else {
					diffList.get(9).addAll(group);
				}
			});

			// 将other中的diff随机分配给分支版本
			other.forEach(d -> {
				double flag = random.nextDouble();
				if (flag >= 0.89) {
					diffList.get(1).add(d);
				} else if (flag >= 0.78) {
					diffList.get(2).add(d);
				} else if (flag >= 0.67) {
					diffList.get(3).add(d);
				} else if (flag >= 0.56) {
					diffList.get(4).add(d);
				} else if (flag >= 0.45) {
					diffList.get(5).add(d);
				} else if (flag >= 0.34) {
					diffList.get(6).add(d);
				} else if (flag >= 0.23) {
					diffList.get(7).add(d);
				} else if (flag >= 0.12) {
					diffList.get(8).add(d);
				} else {
					diffList.get(9).add(d);
				}
			});
		} else if (branchSize == 11) {
			// 11个分支
			collections.forEach(group -> {
				double flag = random.nextDouble();
				if (flag >= 0.91) {
					diffList.get(1).addAll(group);
				} else if (flag >= 0.82) {
					diffList.get(2).addAll(group);
				} else if (flag >= 0.73) {
					diffList.get(3).addAll(group);
				} else if (flag >= 0.64) {
					diffList.get(4).addAll(group);
				} else if (flag >= 0.55) {
					diffList.get(5).addAll(group);
				} else if (flag >= 0.46) {
					diffList.get(6).addAll(group);
				} else if (flag >= 0.37) {
					diffList.get(7).addAll(group);
				} else if (flag >= 0.28) {
					diffList.get(8).addAll(group);
				} else if (flag >= 0.19) {
					diffList.get(9).addAll(group);
				} else if (flag >= 0.1) {
					diffList.get(10).addAll(group);
				} else {
					diffList.get(11).addAll(group);
				}
			});

			// 将other中的diff随机分配给分支版本
			other.forEach(d -> {
				double flag = random.nextDouble();
				if (flag >= 0.91) {
					diffList.get(1).add(d);
				} else if (flag >= 0.82) {
					diffList.get(2).add(d);
				} else if (flag >= 0.73) {
					diffList.get(3).add(d);
				} else if (flag >= 0.64) {
					diffList.get(4).add(d);
				} else if (flag >= 0.55) {
					diffList.get(5).add(d);
				} else if (flag >= 0.46) {
					diffList.get(6).add(d);
				} else if (flag >= 0.37) {
					diffList.get(7).add(d);
				} else if (flag >= 0.28) {
					diffList.get(8).add(d);
				} else if (flag >= 0.19) {
					diffList.get(9).add(d);
				} else if (flag >= 0.1) {
					diffList.get(10).add(d);
				} else {
					diffList.get(11).add(d);
				}
			});
		} else {
			collections.forEach(group -> {
				diffList.get(1).addAll(group);
			});
			other.forEach(d -> {
				diffList.get(1).add(d);
			});
		}

		for (int i = 1; i < N; i++) {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>diffBranch" + i);

			// 应用diff到相应的分支
			List<Diff> diffBranch = diffList.get(i);
			Set<Diff> help = new HashSet<>();
			diffBranch.forEach(d -> {
				if (d.getState() == DifferenceState.DISCARDED) {
					help.add(d);
				}
			});
			applyDiff(diffBranch, baseResource, uriList.get(i));

			if (moveForTargets.size() != 0) {

				// 检查不能存在的
				checkNotMap.forEach((key, value) -> {
					EObject container = (EObject) key.getKey(0);
					EReference r = (EReference) key.getKey(1);
					EObject first = (EObject) key.getKey(2);
					EObject second = (EObject) key.getKey(3);
					// targets就是apply后baseResource中的序
					List<EObject> targets = leftTargetsMap.get(container, r);
					Integer firstIndex = targets.indexOf(first);
					Integer secondIndex = targets.indexOf(second);
					if (firstIndex > secondIndex) {
						Diff boundDiff1 = eObjectDiffMap.get(first);
						Diff boundDiff2 = eObjectDiffMap.get(second);
						if (diffBranch.contains(boundDiff1) == false) {
							boundDiff1.setState(org.eclipse.emf.compare.DifferenceState.UNRESOLVED);
							diffBranch.add(boundDiff1);
						} else if (help.contains(boundDiff1)) {
							boundDiff1.setState(org.eclipse.emf.compare.DifferenceState.UNRESOLVED);
						}

						if (diffBranch.contains(boundDiff2) == false) {
							boundDiff2.setState(org.eclipse.emf.compare.DifferenceState.UNRESOLVED);
							diffBranch.add(boundDiff2);
						} else if (help.contains(boundDiff2)) {
							boundDiff2.setState(org.eclipse.emf.compare.DifferenceState.UNRESOLVED);
						}
					}
				});

				applyDiff(diffBranch, baseResource, uriList.get(i));

				// 检查需要在任一分支中存在的
				checkExistMap.forEach((key, value) -> {
					// value为true后，之后就不用检查此键值对
					if (value == false) {
						EObject container = (EObject) key.getKey(0);
						EReference r = (EReference) key.getKey(1);
						EObject first = (EObject) key.getKey(2);
						EObject second = (EObject) key.getKey(3);
						// targets就是apply后baseResource中的序
						List<EObject> targets = leftTargetsMap.get(container, r);
						Integer firstIndex = targets.indexOf(first);
						Integer secondIndex = targets.indexOf(second);
						if (firstIndex < secondIndex) {
							checkExistMap.put(key, true);
						}
					}
				});

			}

			// 恢复
			if (i != N - 1) {
				backup(baseResource, backupResource);
			}

		}

		if (moveForTargets.size() != 0) {
			List<Diff> diffLast = diffList.get(N - 1);
			List<Diff> help = new ArrayList<>();

			// 若任何一个分支都不存在<y, x>，则在最后一个分支加入boundDiff
			checkExistMap.forEach((key, value) -> {
				if (value == false) {
					Diff boundDiff1 = eObjectDiffMap.get(key.getKey(2));
					Diff boundDiff2 = eObjectDiffMap.get(key.getKey(3));
					if (diffLast.contains(boundDiff1) == false) {
						boundDiff1.setState(org.eclipse.emf.compare.DifferenceState.UNRESOLVED);
						diffLast.add(boundDiff1);
						help.add(boundDiff1);
					}
					if (diffLast.contains(boundDiff2) == false) {
						boundDiff2.setState(org.eclipse.emf.compare.DifferenceState.UNRESOLVED);
						diffLast.add(boundDiff2);
						help.add(boundDiff2);
					}
				}
			});

			// 最后一个分支再次apply
			applyDiff(diffLast, baseResource, uriList.get(N - 1));

			// 最后一个分支还要检查不能存在的
			checkNotMap.forEach((key, value) -> {
				EObject container = (EObject) key.getKey(0);
				EReference r = (EReference) key.getKey(1);
				EObject first = (EObject) key.getKey(2);
				EObject second = (EObject) key.getKey(3);
				// targets就是最近apply后baseResource中的序
				List<EObject> targets = leftTargetsMap.get(container, r);
				Integer firstIndex = targets.indexOf(first);
				Integer secondIndex = targets.indexOf(second);

				if (firstIndex > secondIndex) {
					Diff boundDiff1 = eObjectDiffMap.get(key.getKey(2));
					Diff boundDiff2 = eObjectDiffMap.get(key.getKey(3));

					if (diffLast.contains(boundDiff1) == false) {
						boundDiff1.setState(org.eclipse.emf.compare.DifferenceState.UNRESOLVED);
						diffLast.add(boundDiff1);
					} else if (help.contains(boundDiff1) == false) {
						boundDiff1.setState(org.eclipse.emf.compare.DifferenceState.UNRESOLVED);
					}

					if (diffLast.contains(boundDiff2) == false) {
						boundDiff2.setState(org.eclipse.emf.compare.DifferenceState.UNRESOLVED);
						diffLast.add(boundDiff2);
					} else if (help.contains(boundDiff2) == false) {
						boundDiff2.setState(org.eclipse.emf.compare.DifferenceState.UNRESOLVED);
					}

				}

			});

			// 最后一个分支再次apply
			applyDiff(diffLast, baseResource, uriList.get(N - 1));

		}

		System.out.println("moveSize: " + moveSize);
		System.out.println("done");
	}

	/** 将分配给分支的diff应用到base上，得到branch */
	public static void applyDiff(List<Diff> diffs, Resource resource, URI branchURI) {
		diffs.forEach(d -> {
			if (d.getState() == DifferenceState.UNRESOLVED) {
				System.out.println(d);
			}
		});
		System.out.println("------------------------------");
		merger.copyAllRightToLeft(diffs, null);
		ChangeTool.save(resource, branchURI);
	}

	/** 恢复base */
	public static void backup(Resource baseResource, Resource backupResource) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>backup");
		IComparisonScope backupScope = new DefaultComparisonScope(baseResource, backupResource, null);

		// never use identifiers
		IMatchEngine.Factory.Registry registry = MatchEngineFactoryRegistryImpl.createStandaloneInstance();
		final MatchEngineFactoryImpl matchEngineFactory = new MatchEngineFactoryImpl(UseIdentifiers.NEVER);
		matchEngineFactory.setRanking(20);
		registry.add(matchEngineFactory);
		Comparison backupComparison = EMFCompare.builder().setMatchEngineFactoryRegistry(registry).build()
				.compare(backupScope);

		EList<Diff> backupDiffs = backupComparison.getDifferences();
		merger.copyAllRightToLeft(backupDiffs, null);
	}

	public static void testMerge(TypeGraph typeGraph, List<Resource> resourceList, List<TypeEdge> typeEdgeList,
			List<PropertyEdge> propertyEdgeList, URI m1URI, EPackage ep) {

		NWay nWay = new NWay(typeGraph);
		long start = System.currentTimeMillis(); // start from this point?
		List<MatchN> matches = nWay.nMatch(resourceList);
		long endMatch = System.currentTimeMillis();
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("匹配总耗时：" + (endMatch - start) + " ms.");

		long startMerge = System.currentTimeMillis();
		TypedGraph mergeModel = nWay.nMerge(matches, typeEdgeList, propertyEdgeList);
		long end = System.currentTimeMillis();
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("差分与合并的总耗时：" + (end - startMerge) + " ms.");

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("总耗时： " + (end - start) + " ms.");

		try {
			nWay.saveModel(m1URI, mergeModel, ep);
			System.out.println("done");
		} catch (NothingReturnedException e) {
			e.printStackTrace();
		}
	}

	public static void testEquality(ResourceSet resourceSet, URI leftURI, URI rightURI,
			Map<String, Boolean> filterDiffMap) {

		Resource leftResource = resourceSet.getResource(leftURI, true);
		Resource rightResource = resourceSet.getResource(rightURI, true);
		IComparisonScope scope = new DefaultComparisonScope(leftResource, rightResource, null);

		// never use identifiers
		IMatchEngine.Factory.Registry registry = MatchEngineFactoryRegistryImpl.createStandaloneInstance();
		final MatchEngineFactoryImpl matchEngineFactory = new MatchEngineFactoryImpl(UseIdentifiers.NEVER);
		matchEngineFactory.setRanking(20);
		registry.add(matchEngineFactory);
		Comparison comparison = EMFCompare.builder().setMatchEngineFactoryRegistry(registry).build().compare(scope);

		EList<Diff> diffs = comparison.getDifferences();
		List<Diff> reportDiffs = new ArrayList<>();

		diffs.forEach(d -> {
			Boolean flag = null;
			if (d instanceof ReferenceChangeSpec) {
				EReference reference = ((ReferenceChangeSpec) d).getReference();
				flag = filterDiffMap.get(reference.getName());
			} else if (d instanceof AttributeChangeSpec) {
				EAttribute attribute = ((AttributeChangeSpec) d).getAttribute();
				flag = filterDiffMap.get(attribute.getName());
			}
			if (flag == null) {
				reportDiffs.add(d);
			}
		});

		System.out.println("reportDiffs.size(): " + reportDiffs.size());
		reportDiffs.forEach(d -> {
			System.out.println(d);
		});

	}

	public static Resource threeWay(Resource leftResource, Resource rightResource, Resource baseResource) {

		IComparisonScope scope = new DefaultComparisonScope(leftResource, rightResource, baseResource);

		// never use identifiers
		IMatchEngine.Factory.Registry registry = MatchEngineFactoryRegistryImpl.createStandaloneInstance();
		final MatchEngineFactoryImpl matchEngineFactory = new MatchEngineFactoryImpl(UseIdentifiers.NEVER);
		matchEngineFactory.setRanking(20);
		registry.add(matchEngineFactory);
		Comparison comparison = EMFCompare.builder().setMatchEngineFactoryRegistry(registry).build().compare(scope);

		Collection<Diff> rightDiffs = new HashSet<>();
		comparison.getDifferences().forEach(d -> {
			if (d.getSource() == DifferenceSource.RIGHT) {
				rightDiffs.add(d);
			}
		});
		merger.copyAllRightToLeft(rightDiffs, null);
		return leftResource;
		
	}

	public static void testEquality(Resource m0Resource, Resource m1Resource) {

		IComparisonScope scope = new DefaultComparisonScope(m0Resource, m1Resource, null);
		// never use identifiers
		IMatchEngine.Factory.Registry registry = MatchEngineFactoryRegistryImpl.createStandaloneInstance();
		final MatchEngineFactoryImpl matchEngineFactory = new MatchEngineFactoryImpl(UseIdentifiers.NEVER);
		matchEngineFactory.setRanking(20);
		registry.add(matchEngineFactory);
		Comparison comparison = EMFCompare.builder().setMatchEngineFactoryRegistry(registry).build().compare(scope);
		
		EList<Diff> diffs = comparison.getDifferences();
		System.out.println("diffs.size(): " + diffs.size());
		diffs.forEach(d -> {
			System.out.println(d);
		});

	}

	public static void testEMFCompare(List<Resource> resourceList, URI m2URI) {
			
		long start = System.currentTimeMillis();
		Resource baseResource = resourceList.get(0);
		Resource leftResource = resourceList.get(1);		
		for(int i=2; i<resourceList.size(); i++) {
			Resource rightResource = resourceList.get(i);
			leftResource = threeWay(leftResource, rightResource, baseResource);			
		}
		long end = System.currentTimeMillis();
		System.out.println("迭代式二向合并耗时：" + (end-start) + " ms.");
		
		ChangeTool.save(leftResource, m2URI);
		System.out.println("done");
		
	}


}
