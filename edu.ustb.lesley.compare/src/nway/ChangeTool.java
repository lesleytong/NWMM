package nway;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.RandomStringUtils;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.Extend;
import org.eclipse.uml2.uml.GeneralOrdering;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.internal.impl.ActivityImpl;
import org.eclipse.uml2.uml.internal.impl.ActorImpl;
import org.eclipse.uml2.uml.internal.impl.AssociationImpl;
import org.eclipse.uml2.uml.internal.impl.BehaviorExecutionSpecificationImpl;
import org.eclipse.uml2.uml.internal.impl.ClassImpl;
import org.eclipse.uml2.uml.internal.impl.DecisionNodeImpl;
import org.eclipse.uml2.uml.internal.impl.ExtendImpl;
import org.eclipse.uml2.uml.internal.impl.IncludeImpl;
import org.eclipse.uml2.uml.internal.impl.InteractionImpl;
import org.eclipse.uml2.uml.internal.impl.LifelineImpl;
import org.eclipse.uml2.uml.internal.impl.MergeNodeImpl;
import org.eclipse.uml2.uml.internal.impl.MessageImpl;
import org.eclipse.uml2.uml.internal.impl.ModelImpl;
import org.eclipse.uml2.uml.internal.impl.OpaqueActionImpl;
import org.eclipse.uml2.uml.internal.impl.UseCaseImpl;

public class ChangeTool {

	static int changeSize = 0;
	static int addSize = 0;
	static boolean moveFlag = false;
	private static int start = 0;

	public static void changeForUML(Resource resource, URI m0URI) {

		changeSize = 0;
		addSize = 0;
		moveFlag = false;

		ArrayList<EObject> dList = new ArrayList<>();
		EObject model = resource.getContents().get(0);
		Random random = new Random();

		// 删除
		model.eAllContents().forEachRemaining(e -> {
			if (e instanceof ClassImpl) {
				ClassImpl c = (ClassImpl) e;
				if (c.isAbstract() == false) {
					for (Property p : c.getOwnedAttributes()) {
						if (p.getAssociation() == null && random.nextDouble() >= 0.85) {
							dList.add(p);
						}
					}

				}
			}
		});

		EcoreUtil.deleteAll(dList, true);

		// 再遍历一次
		model.eAllContents().forEachRemaining(e -> {
			if (e instanceof ClassImpl) {
				ClassImpl c = (ClassImpl) e;

				if (c.isAbstract() == false) {

					EList<Property> ownedAttributes = c.getOwnedAttributes();
					EList<Operation> ownedOperations = c.getOwnedOperations();

					// 修改Property
					for (Property p : ownedAttributes) {
						if (p.isDerived() || p.isReadOnly()) {
							continue;
						}
						if (random.nextDouble() >= 0.85) {
							p.setName(p.getName() + "_");
							changeSize++;
							System.out.println("Property修改后的名称为：" + p.getName());
						}
					}

					// 新加Property
					if (random.nextDouble() >= 0.85) {
						Property createProperty = UMLFactory.eINSTANCE.createProperty();
						createProperty.setName(RandomStringUtils.randomAlphanumeric(6));
						ownedAttributes.add(createProperty);
						addSize++;
						System.out.println("新加的Property: " + createProperty.getName());
					}

//					// 移动序(Property)
//					if(random.nextDouble() >= 0.85) {
//						int N = ownedAttributes.size();
//						if(N >= 3) {
//							for (int i = 0; i < N; i++) {
//								int randomIndexToSwap = random.nextInt(N);
//								ownedAttributes.move(randomIndexToSwap, i);
//							}
//							moveFlag = true;
//						}
//					}

					// 修改Operation
					ownedOperations.forEach(o -> {
						if (random.nextDouble() >= 0.9) {
							o.setName(o.getName() + "_");
							System.out.println("Operation修改后的名称为：" + o.getName());
						}
					});

					// 新加Operation
					if (random.nextDouble() >= 0.85) {
						Operation createOperation = UMLFactory.eINSTANCE.createOperation();
						createOperation.setName(RandomStringUtils.randomAlphanumeric(6));
						ownedOperations.add(createOperation);
						addSize++;
						System.out.println("新加的Operation: " + createOperation.getName());
					}

					// 移动序(Operation)
					if (random.nextDouble() >= 0.85) {
						int N = ownedOperations.size();
						if (N >= 3) {
							for (int i = 0; i < N; i++) {
								int randomIndexToSwap = random.nextInt(N);
								ownedOperations.move(randomIndexToSwap, i);
							}
							moveFlag = true;
						}
					}

				}
			}
		});

		System.out.println("\n");
		System.out.println("总共删除的个数：" + dList.size());
		System.out.println("总共新加的个数：" + addSize);
		System.out.println("总共修改的个数：" + changeSize);
		if (moveFlag == true) {
			System.out.println("存在序的移动");
		}
		save(resource, m0URI);

	}

	public static void changeForEcore(Resource resource, URI m0URI) {

		changeSize = 0;
		addSize = 0;
		moveFlag = false;

		ArrayList<EObject> dList = new ArrayList<>();
		Random random = new Random();
		// resource下多个package
		for (EObject ep : resource.getContents()) {
			ep.eContents().forEach(e -> {

				if (e instanceof EClass) {

					EClass c = (EClass) e;

					if (random.nextBoolean() == true) {

						// 修改类的名称
						if (random.nextDouble() >= 0.9) {
							c.setName(c.getName() + "_");
							System.out.println("修改后Class的名称为：" + c.getName());
							changeSize++;
						}

						// 删除某些属性
						c.getEAttributes().forEach(a -> {
							if (random.nextDouble() >= 0.9) {
								dList.add(a);
								System.out.println("删除的Attribute为：" + a);
							}
						});

						// 修改引用的名称
						c.getEReferences().forEach(r -> {
							if (random.nextDouble() >= 0.9) {
								r.setName(r.getName() + "_" + RandomStringUtils.randomAlphanumeric(3));
								System.out.println("修改后Reference的名称为：" + r.getName());
								changeSize++;
							}
						});

						// 删除某些操作
						c.getEOperations().forEach(o -> {
							if (random.nextDouble() >= 0.9) {
								dList.add(o);
								System.out.println("删除的Operation为：" + o);
							}
						});

						// 新加属性，如果还要指定类型的话，之前需要缓存下EDataType
						if (random.nextDouble() >= 0.9) {
							EAttribute createEAttribute = EcoreFactory.eINSTANCE.createEAttribute();
							createEAttribute.setName(RandomStringUtils.randomAlphanumeric(5));
							c.getEStructuralFeatures().add(createEAttribute);
							System.out.println("新加的Attribute：" + createEAttribute.getName());
							addSize++;
						}

						// 新加操作
						if (random.nextDouble() >= 0.9) {
							EOperation createEOperation = EcoreFactory.eINSTANCE.createEOperation();
							createEOperation.setName(RandomStringUtils.randomAlphanumeric(5));
							c.getEOperations().add(createEOperation);
							System.out.println("新加的Opertation：" + createEOperation.getName());
							addSize++;
						}

					} else { // 一个类下只能选择上面的增删改，或者下面的移动序

						// 移动序：属性和引用
						if (random.nextDouble() >= 0.9) {
							EList<EStructuralFeature> eStructuralFeatures = c.getEStructuralFeatures();
							int N = eStructuralFeatures.size();
							if (N >= 3) {
								for (int i = 0; i < N; i++) {
									int randomIndexToSwap = random.nextInt(N);
									eStructuralFeatures.move(randomIndexToSwap, i);
								}
								moveFlag = true;
							}
						}

						// 移动序：操作
						if (random.nextDouble() >= 0.9) {
							EList<EOperation> eOperations = c.getEOperations();
							int M = eOperations.size();
							if (M >= 3) {
								for (int i = 0; i < M; i++) {
									int randomIndexToSwap = random.nextInt(M);
									eOperations.move(randomIndexToSwap, i);
								}
								moveFlag = true;
							}
						}
					}
				}
			});
		}

		// 调用EcoreUtil的方法进行删除
		EcoreUtil.removeAll(dList);

		System.out.println("\n");
		System.out.println("总共删除的个数：" + dList.size());
		System.out.println("总共新加的个数：" + addSize);
		System.out.println("总共修改的个数：" + changeSize);
		if (moveFlag == true) {
			System.out.println("存在序的移动");
		}
		save(resource, m0URI);

	}

	public static void changeForAll(Resource resource, URI m0URI) {

		addSize = 0;
		changeSize = 0;
		moveFlag = false;

		Random random = new Random();
		Collection<EObject> dList = new HashSet<>();

		EObject root = resource.getContents().get(0);

		TreeIterator<EObject> iter = root.eAllContents();

//		// 新的删除方法
//		iter.forEachRemaining(e -> {
//			EList<EObject> eContents = e.eContents();
//			int N = eContents.size();
//			if (N > 0) {
//				// obj.eClass().getEAllContainments().size()
//				// obj.eContents().size()
//				boolean anyMatch = eContents.stream().anyMatch(obj -> obj.eContents().size() > 0);
//				if (anyMatch == false) {
//					if (random.nextDouble() >= 0.85) {
//						int index1 = random.nextInt(N);
//						dList.add(eContents.get(index1));
//
//						int index2 = random.nextInt(N);
//						if (index2 != index1) {
//							dList.add(eContents.get(index2));
//						}
//						iter.prune();
//					}
//				}
//			}
//		});
//		EcoreUtil.deleteAll(dList, true);
		
		// for repository, petrinet, make
		iter.forEachRemaining(e -> {
			if (e.eContents().size() == 0) {
				EObject obj = e.eContainer();
				if (obj.eContainer() == null) {
					if (random.nextDouble() >= 0.85) {
						dList.add(e);
					}
				}
			}

		});
		EcoreUtil.deleteAll(dList, true);

		// 再遍历一次
		root.eAllContents().forEachRemaining(e -> {

			EClass eClass = e.eClass();

			// 修改
			if (eClass.getEAllContainments().size() > 0 && e.eContents().size() > 0) {
				eClass.getEAllAttributes().forEach(a -> {
					if (random.nextDouble() >= 0.85) {
						setAttribute(e, a);
					}
				});
			}

			boolean flag = true;
			for (EReference r : eClass.getEAllReferences()) {

				if (r.isChangeable() == false || r.isDerived() || r.isTransient()) {
					continue;
				} else if (r.isMany()) {
					if (r.isContainment()) {

						EList<EObject> targets = (EList<EObject>) e.eGet(r);
						boolean anyMatch = targets.stream().anyMatch(t -> t.eContents().size() > 0);
						if (anyMatch) {
							continue;
						}

						// 移动
						int N = targets.size();
						if (N >= 4) {
							for (int i = 0; i < N; i++) {
								if (random.nextDouble() >= 0.85) {
									int randomIndexToSwap = random.nextInt(N);
									targets.move(randomIndexToSwap, i);
									moveFlag = true;
								}
							}
						} else if (random.nextDouble() >= 0.85) { // 新加
							try {
								EClass type = r.getEReferenceType(); // 引用的另一方
								if (type != null && flag == true) {
									EObject create = EcoreUtil.create(type);
									type.getEAllAttributes().forEach(a -> {
										setAttribute(create, a);
									});
									Collection<EObject> targets1 = (Collection<EObject>) e.eGet(r);
									targets1.add(create);
									flag = false;
									addSize++;
								}
							} catch (Exception e2) {
								// do nothing
							}
						}
					}
				}

			}

		});

		System.out.println("\n\n\n");
		System.out.println("总共删除的个数：" + dList.size());
		System.out.println("总共新加的个数：" + addSize);
		System.out.println("总共修改属性值的个数：" + changeSize);
		if (moveFlag == true) {
			System.out.println("存在序的移动");
		}

		save(resource, m0URI);

	}

	public static void changeForUsecase(Resource resource, URI m0URI) {
		changeSize = 0;
		addSize = 0;
		moveFlag = false;

		ArrayList<EObject> dList = new ArrayList<>();
		EObject eObject = resource.getContents().get(0);
		Random random = new Random();

//		if (eObject instanceof ModelImpl) {
//			ModelImpl model = (ModelImpl) eObject;
//			EList<PackageableElement> packagedElements = model.getPackagedElements();
//			
//			// 新加
//			UseCase createUseCase = UMLFactory.eINSTANCE.createUseCase();
//			createUseCase.setName(RandomStringUtils.randomAlphanumeric(6));
//			packagedElements.add(createUseCase);
//			addSize++;
//		}

		// 遍历一次
		eObject.eAllContents().forEachRemaining(e -> {

			if (e instanceof ActorImpl) {
				ActorImpl actor = (ActorImpl) e;
				actor.setName(actor.getName() + "_");
				changeSize++;
				System.out.println("修改后ActorImpl的名称为: " + actor.getName());
			} else if (e instanceof UseCaseImpl) {
				UseCaseImpl u = (UseCaseImpl) e;

				if (random.nextDouble() >= 0.9) {
					u.setName(u.getName() + "_");
					changeSize++;
					System.out.println("修改后UseCaseImpl的名称为：" + u.getName());
				} else if (random.nextDouble() >= 0.9) {
					// 新加
					Extend createExtend = UMLFactory.eINSTANCE.createExtend();
					createExtend.setExtendedCase(u);
					u.getExtends().add(createExtend);
					addSize++;
					System.out.println("新加ExtendImpl: " + createExtend);
				} else if (random.nextDouble() >= 0.9) {
					// 新加
					u.createExtensionPoint(RandomStringUtils.randomAlphanumeric(6));
					addSize++;

				}
			} else if (e instanceof ExtendImpl) {
				if (random.nextDouble() >= 0.9) {
					dList.add(e);
					System.out.println("删除的ExtendImpl: " + e);
				}

			} else if (e instanceof IncludeImpl) {
				if (random.nextDouble() >= 0.9) {
					dList.add(e);
					System.out.println("删除的IncludeImpl: " + e);
				}
			}

		});

//		// 移动：have component
//		eObject.eAllContents().forEachRemaining(e -> {
//			if (e instanceof ComponentImpl) {
//				
//				ComponentImpl c = (ComponentImpl) e;
//				EList<UseCase> ownedUseCases = c.getOwnedUseCases();
//				int N = ownedUseCases.size();
//				if (N >= 3) {
//					boolean flag = ownedUseCases.parallelStream().anyMatch(u -> u.getOwnedElements().size() > 1);
//					if (flag == false) {
//						for (int i = 0; i < N; i++) {
//							int randomIndexToSwap = random.nextInt(N);
//							ownedUseCases.move(randomIndexToSwap, i);
//						}
//						moveFlag = true;
//					}
//				}
//				
//			} 
//		});

		// 移动：no component
		if (eObject instanceof ModelImpl) {
			ModelImpl model = (ModelImpl) eObject;
			EList<PackageableElement> packagedElements = model.getPackagedElements();

			Optional<PackageableElement> findFirst = packagedElements.stream()
					.filter(pe -> pe instanceof AssociationImpl).findFirst();
			int N = packagedElements.indexOf(findFirst.get());

			System.out.println("N: " + N);

			Stream<PackageableElement> filter = packagedElements.stream()
					.filter(pe -> pe instanceof UseCaseImpl && pe.getOwnedElements().size() > 1);
			filter.forEach(pe -> {
				int index = packagedElements.indexOf(pe);
				if (index < N && index > start) {
					start = index;
				}

			});

			System.out.println("start: " + start);

			if (N - start >= 3) {
				for (int i = start + 1; i < N; i++) {
					if (random.nextDouble() >= 0) { // may use N/2
						int randomIndexToSwap = start + (int) (Math.random() * ((N - start) + 1));
						packagedElements.move(randomIndexToSwap, i);
					}
				}
				moveFlag = true;
			}
		}

		System.out.println();

		// 调用EcoreUtil的方法进行删除
		EcoreUtil.removeAll(dList);

		System.out.println("\n");
		System.out.println("总共删除的个数：" + dList.size());
		System.out.println("总共新加的个数：" + addSize);
		System.out.println("总共修改的个数：" + changeSize);
		if (moveFlag == true) {
			System.out.println("存在序的移动");
		}

		save(resource, m0URI);

	}

	public static void changeForSequence(Resource resource, URI m0URI) {

		changeSize = 0;
		addSize = 0;
		moveFlag = false;

		ArrayList<EObject> dList = new ArrayList<>();
		EObject eObject = resource.getContents().get(0);
		Random random = new Random();

		eObject.eAllContents().forEachRemaining(e -> {

			if (e instanceof BehaviorExecutionSpecificationImpl) {
				BehaviorExecutionSpecificationImpl b = (BehaviorExecutionSpecificationImpl) e;
				EList<GeneralOrdering> generalOrderings = b.getGeneralOrderings();

				// 新加
				if (random.nextDouble() >= 0.9) {
					b.createGeneralOrdering(RandomStringUtils.randomAlphanumeric(6));
					addSize++;
				}

				// 修改
				generalOrderings.forEach(g -> {
					if (random.nextDouble() >= 0.9) {
						g.setName(RandomStringUtils.randomAlphanumeric(6));
						changeSize++;
						System.out.println("GeneralOrdering修改后的名称为: " + g.getName());
					}
				});

				// 移动
				if (random.nextDouble() >= 0.9) {
					int N = generalOrderings.size();
					if (N >= 2) {
						for (int i = 0; i < N; i++) {
							int randomIndexToSwap = random.nextInt(N);
							generalOrderings.move(randomIndexToSwap, i);
							moveFlag = true;
						}
					}
				}

			} else if (e instanceof MessageImpl) {
				// 修改
				if (random.nextDouble() >= 0.9) {
					MessageImpl message = (MessageImpl) e;
					message.setName(message.getName() + "_");
					changeSize++;
					System.out.println("修改后MessageImpl的名称为： " + message.getName());

				}
			} else if (e instanceof LifelineImpl) {
				// 修改
				if (random.nextDouble() >= 0.9) {
					LifelineImpl l = (LifelineImpl) e;
					l.setName(l.getName() + "_");
					changeSize++;
					System.out.println("修改后MessageImpl的名称为： " + l.getName());

				}
			}
		});

		eObject.eContents().forEach(e -> {
			if (e instanceof InteractionImpl) {
				// 新加
				InteractionImpl inter = (InteractionImpl) e;
				if (random.nextDouble() >= 0.9) {
					inter.createMessage(RandomStringUtils.randomAlphanumeric(6) + "()");
					addSize++;
				}
				if (random.nextDouble() >= 0.9) {
					inter.createLifeline(RandomStringUtils.randomAlphanumeric(6));
					addSize++;
				}

			}
		});

		System.out.println("\n");
		System.out.println("总共删除的个数：" + dList.size());
		System.out.println("总共新加的个数：" + addSize);
		System.out.println("总共修改的个数：" + changeSize);
		if (moveFlag == true) {
			System.out.println("存在序的移动");
		}

		save(resource, m0URI);

	}

	public static void changeForActivity(Resource resource, URI m0URI) {

		changeSize = 0;
		addSize = 0;
		moveFlag = false;

		ArrayList<EObject> dList = new ArrayList<>();
		EObject eObject = resource.getContents().get(0);
		Random random = new Random();

//		// 删除
//		eObject.eContents().forEach(e -> {
//			if (e instanceof ActivityImpl) {
//				ActivityImpl activity = (ActivityImpl) e;
//				EList<ActivityEdge> edges = activity.getEdges(); // obtain ControFlowImpl
//				edges.forEach(edge -> {
//					if (random.nextDouble() >= 0.9) {
//						dList.add(edge);
//					}
//				});
//
//			}
//		});
//
//		EcoreUtil.deleteAll(dList, true);

		// 再遍历一次
		eObject.eContents().forEach(e -> {

			if (e instanceof ActivityImpl) {

				ActivityImpl activity = (ActivityImpl) e;

//				if (random.nextBoolean() == true) {
				EList<ActivityEdge> edges = activity.getEdges(); // obtain ControFlowImpl

				edges.forEach(edge -> {
					// 修改
					if (random.nextDouble() >= 0.9) {
						edge.setSource(null);
						changeSize++;
					} else if (random.nextDouble() >= 0.9) {
						edge.setTarget(null);
						changeSize++;
					}
				});

				// 移动
				int N = edges.size();
				for (int i = 0; i < N; i++) {
					if (random.nextDouble() >= 0.9) {
						int randomIndexToSwap = random.nextInt(N);
						edges.move(randomIndexToSwap, i);
						moveFlag = true;
					}
				}

//				} else {
				EList<ActivityNode> ownedNodes = activity.getOwnedNodes();
				EClass decision = null;
				EClass merge = null;

				boolean decisionFlag = true;
				boolean mergeFlag = true;

				for (ActivityNode n : ownedNodes) {
					if (n instanceof OpaqueActionImpl) {
						// 修改
						if (random.nextDouble() >= 0.9) {
							n.setName(n.getName() + "_");
							changeSize++;
							System.out.println("修改后的名称：" + n.getName());
						}

					} else if (mergeFlag == true && decisionFlag == true && n instanceof DecisionNodeImpl) {
						// 新加
						if (random.nextDouble() >= 0.9) {
							decision = n.eClass();
							addSize++;
							decisionFlag = false;
						}
					} else if (decisionFlag == true && mergeFlag == true && n instanceof MergeNodeImpl) {
						// 新加
						if (random.nextDouble() >= 0.9) {
							merge = n.eClass();
							addSize++;
							mergeFlag = false;
						}
					}
				}
				if (decisionFlag == false) {
					activity.createOwnedNode(RandomStringUtils.randomAlphanumeric(6), decision);
				}
				if (mergeFlag == false) {
					activity.createOwnedNode(RandomStringUtils.randomAlphanumeric(6), merge);
				}
			}

//			}

		});

		System.out.println("\n");
		System.out.println("总共删除的个数：" + dList.size());
		System.out.println("总共新加的个数：" + addSize);
		System.out.println("总共修改的个数：" + changeSize);
		if (moveFlag == true) {
			System.out.println("存在序的移动");
		}

		save(resource, m0URI);

	}

	public static void shuffle(List<EObject> targets) {
		int N = targets.size();
		Random random = new Random();
		// a list of non-repeating N random integers in range (a, b), where b is
		// exclusive
		List<Integer> randomNumbers = random.ints(0, N).distinct().limit(N).boxed().collect(Collectors.toList());

		List<EObject> shuffleList = new ArrayList<>();
		randomNumbers.forEach(i -> {
			shuffleList.add(targets.get(i));
		});
		// otherwise, it will report validating duplicate exception
		targets.clear();
		targets.addAll(shuffleList);
	}

	/** 修改或设置新加的属性 */
	public static void setAttribute(EObject e, EAttribute a) {
		if (a.isMany() == false) { // 如果a是单值属性
			String instanceTypeName = a.getEAttributeType().getInstanceTypeName();
			if (instanceTypeName.contains("String")) {
				Object eGet = e.eGet(a);
				if (eGet != null) {
					e.eSet(a, eGet + "_");
					changeSize++;
				} else {
					e.eSet(a, RandomStringUtils.randomAlphanumeric(6));
				}
			} else if (instanceTypeName.contains("int")) {
				Object eGet = e.eGet(a);
				if (eGet.equals(a.getDefaultValue())) {
					Random random = new Random();
					e.eSet(a, random.nextInt(10000) + random.nextInt(10000));	// 防止出现重复的值
				}
			}
		}
		// PENDING：多值属性
	}

	// 保存为xmi
	public static void save(Resource resource, URI out) {
		try {
			resource.setURI(out);
			resource.save(null);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
