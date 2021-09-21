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

		// ɾ��
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

		// �ٱ���һ��
		model.eAllContents().forEachRemaining(e -> {
			if (e instanceof ClassImpl) {
				ClassImpl c = (ClassImpl) e;

				if (c.isAbstract() == false) {

					EList<Property> ownedAttributes = c.getOwnedAttributes();
					EList<Operation> ownedOperations = c.getOwnedOperations();

					// �޸�Property
					for (Property p : ownedAttributes) {
						if (p.isDerived() || p.isReadOnly()) {
							continue;
						}
						if (random.nextDouble() >= 0.85) {
							p.setName(p.getName() + "_");
							changeSize++;
							System.out.println("Property�޸ĺ������Ϊ��" + p.getName());
						}
					}

					// �¼�Property
					if (random.nextDouble() >= 0.85) {
						Property createProperty = UMLFactory.eINSTANCE.createProperty();
						createProperty.setName(RandomStringUtils.randomAlphanumeric(6));
						ownedAttributes.add(createProperty);
						addSize++;
						System.out.println("�¼ӵ�Property: " + createProperty.getName());
					}

//					// �ƶ���(Property)
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

					// �޸�Operation
					ownedOperations.forEach(o -> {
						if (random.nextDouble() >= 0.9) {
							o.setName(o.getName() + "_");
							System.out.println("Operation�޸ĺ������Ϊ��" + o.getName());
						}
					});

					// �¼�Operation
					if (random.nextDouble() >= 0.85) {
						Operation createOperation = UMLFactory.eINSTANCE.createOperation();
						createOperation.setName(RandomStringUtils.randomAlphanumeric(6));
						ownedOperations.add(createOperation);
						addSize++;
						System.out.println("�¼ӵ�Operation: " + createOperation.getName());
					}

					// �ƶ���(Operation)
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
		System.out.println("�ܹ�ɾ���ĸ�����" + dList.size());
		System.out.println("�ܹ��¼ӵĸ�����" + addSize);
		System.out.println("�ܹ��޸ĵĸ�����" + changeSize);
		if (moveFlag == true) {
			System.out.println("��������ƶ�");
		}
		save(resource, m0URI);

	}

	public static void changeForEcore(Resource resource, URI m0URI) {

		changeSize = 0;
		addSize = 0;
		moveFlag = false;

		ArrayList<EObject> dList = new ArrayList<>();
		Random random = new Random();
		// resource�¶��package
		for (EObject ep : resource.getContents()) {
			ep.eContents().forEach(e -> {

				if (e instanceof EClass) {

					EClass c = (EClass) e;

					if (random.nextBoolean() == true) {

						// �޸��������
						if (random.nextDouble() >= 0.9) {
							c.setName(c.getName() + "_");
							System.out.println("�޸ĺ�Class������Ϊ��" + c.getName());
							changeSize++;
						}

						// ɾ��ĳЩ����
						c.getEAttributes().forEach(a -> {
							if (random.nextDouble() >= 0.9) {
								dList.add(a);
								System.out.println("ɾ����AttributeΪ��" + a);
							}
						});

						// �޸����õ�����
						c.getEReferences().forEach(r -> {
							if (random.nextDouble() >= 0.9) {
								r.setName(r.getName() + "_" + RandomStringUtils.randomAlphanumeric(3));
								System.out.println("�޸ĺ�Reference������Ϊ��" + r.getName());
								changeSize++;
							}
						});

						// ɾ��ĳЩ����
						c.getEOperations().forEach(o -> {
							if (random.nextDouble() >= 0.9) {
								dList.add(o);
								System.out.println("ɾ����OperationΪ��" + o);
							}
						});

						// �¼����ԣ������Ҫָ�����͵Ļ���֮ǰ��Ҫ������EDataType
						if (random.nextDouble() >= 0.9) {
							EAttribute createEAttribute = EcoreFactory.eINSTANCE.createEAttribute();
							createEAttribute.setName(RandomStringUtils.randomAlphanumeric(5));
							c.getEStructuralFeatures().add(createEAttribute);
							System.out.println("�¼ӵ�Attribute��" + createEAttribute.getName());
							addSize++;
						}

						// �¼Ӳ���
						if (random.nextDouble() >= 0.9) {
							EOperation createEOperation = EcoreFactory.eINSTANCE.createEOperation();
							createEOperation.setName(RandomStringUtils.randomAlphanumeric(5));
							c.getEOperations().add(createEOperation);
							System.out.println("�¼ӵ�Opertation��" + createEOperation.getName());
							addSize++;
						}

					} else { // һ������ֻ��ѡ���������ɾ�ģ�����������ƶ���

						// �ƶ������Ժ�����
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

						// �ƶ��򣺲���
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

		// ����EcoreUtil�ķ�������ɾ��
		EcoreUtil.removeAll(dList);

		System.out.println("\n");
		System.out.println("�ܹ�ɾ���ĸ�����" + dList.size());
		System.out.println("�ܹ��¼ӵĸ�����" + addSize);
		System.out.println("�ܹ��޸ĵĸ�����" + changeSize);
		if (moveFlag == true) {
			System.out.println("��������ƶ�");
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

//		// �µ�ɾ������
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

		// �ٱ���һ��
		root.eAllContents().forEachRemaining(e -> {

			EClass eClass = e.eClass();

			// �޸�
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

						// �ƶ�
						int N = targets.size();
						if (N >= 4) {
							for (int i = 0; i < N; i++) {
								if (random.nextDouble() >= 0.85) {
									int randomIndexToSwap = random.nextInt(N);
									targets.move(randomIndexToSwap, i);
									moveFlag = true;
								}
							}
						} else if (random.nextDouble() >= 0.85) { // �¼�
							try {
								EClass type = r.getEReferenceType(); // ���õ���һ��
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
		System.out.println("�ܹ�ɾ���ĸ�����" + dList.size());
		System.out.println("�ܹ��¼ӵĸ�����" + addSize);
		System.out.println("�ܹ��޸�����ֵ�ĸ�����" + changeSize);
		if (moveFlag == true) {
			System.out.println("��������ƶ�");
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
//			// �¼�
//			UseCase createUseCase = UMLFactory.eINSTANCE.createUseCase();
//			createUseCase.setName(RandomStringUtils.randomAlphanumeric(6));
//			packagedElements.add(createUseCase);
//			addSize++;
//		}

		// ����һ��
		eObject.eAllContents().forEachRemaining(e -> {

			if (e instanceof ActorImpl) {
				ActorImpl actor = (ActorImpl) e;
				actor.setName(actor.getName() + "_");
				changeSize++;
				System.out.println("�޸ĺ�ActorImpl������Ϊ: " + actor.getName());
			} else if (e instanceof UseCaseImpl) {
				UseCaseImpl u = (UseCaseImpl) e;

				if (random.nextDouble() >= 0.9) {
					u.setName(u.getName() + "_");
					changeSize++;
					System.out.println("�޸ĺ�UseCaseImpl������Ϊ��" + u.getName());
				} else if (random.nextDouble() >= 0.9) {
					// �¼�
					Extend createExtend = UMLFactory.eINSTANCE.createExtend();
					createExtend.setExtendedCase(u);
					u.getExtends().add(createExtend);
					addSize++;
					System.out.println("�¼�ExtendImpl: " + createExtend);
				} else if (random.nextDouble() >= 0.9) {
					// �¼�
					u.createExtensionPoint(RandomStringUtils.randomAlphanumeric(6));
					addSize++;

				}
			} else if (e instanceof ExtendImpl) {
				if (random.nextDouble() >= 0.9) {
					dList.add(e);
					System.out.println("ɾ����ExtendImpl: " + e);
				}

			} else if (e instanceof IncludeImpl) {
				if (random.nextDouble() >= 0.9) {
					dList.add(e);
					System.out.println("ɾ����IncludeImpl: " + e);
				}
			}

		});

//		// �ƶ���have component
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

		// �ƶ���no component
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

		// ����EcoreUtil�ķ�������ɾ��
		EcoreUtil.removeAll(dList);

		System.out.println("\n");
		System.out.println("�ܹ�ɾ���ĸ�����" + dList.size());
		System.out.println("�ܹ��¼ӵĸ�����" + addSize);
		System.out.println("�ܹ��޸ĵĸ�����" + changeSize);
		if (moveFlag == true) {
			System.out.println("��������ƶ�");
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

				// �¼�
				if (random.nextDouble() >= 0.9) {
					b.createGeneralOrdering(RandomStringUtils.randomAlphanumeric(6));
					addSize++;
				}

				// �޸�
				generalOrderings.forEach(g -> {
					if (random.nextDouble() >= 0.9) {
						g.setName(RandomStringUtils.randomAlphanumeric(6));
						changeSize++;
						System.out.println("GeneralOrdering�޸ĺ������Ϊ: " + g.getName());
					}
				});

				// �ƶ�
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
				// �޸�
				if (random.nextDouble() >= 0.9) {
					MessageImpl message = (MessageImpl) e;
					message.setName(message.getName() + "_");
					changeSize++;
					System.out.println("�޸ĺ�MessageImpl������Ϊ�� " + message.getName());

				}
			} else if (e instanceof LifelineImpl) {
				// �޸�
				if (random.nextDouble() >= 0.9) {
					LifelineImpl l = (LifelineImpl) e;
					l.setName(l.getName() + "_");
					changeSize++;
					System.out.println("�޸ĺ�MessageImpl������Ϊ�� " + l.getName());

				}
			}
		});

		eObject.eContents().forEach(e -> {
			if (e instanceof InteractionImpl) {
				// �¼�
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
		System.out.println("�ܹ�ɾ���ĸ�����" + dList.size());
		System.out.println("�ܹ��¼ӵĸ�����" + addSize);
		System.out.println("�ܹ��޸ĵĸ�����" + changeSize);
		if (moveFlag == true) {
			System.out.println("��������ƶ�");
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

//		// ɾ��
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

		// �ٱ���һ��
		eObject.eContents().forEach(e -> {

			if (e instanceof ActivityImpl) {

				ActivityImpl activity = (ActivityImpl) e;

//				if (random.nextBoolean() == true) {
				EList<ActivityEdge> edges = activity.getEdges(); // obtain ControFlowImpl

				edges.forEach(edge -> {
					// �޸�
					if (random.nextDouble() >= 0.9) {
						edge.setSource(null);
						changeSize++;
					} else if (random.nextDouble() >= 0.9) {
						edge.setTarget(null);
						changeSize++;
					}
				});

				// �ƶ�
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
						// �޸�
						if (random.nextDouble() >= 0.9) {
							n.setName(n.getName() + "_");
							changeSize++;
							System.out.println("�޸ĺ�����ƣ�" + n.getName());
						}

					} else if (mergeFlag == true && decisionFlag == true && n instanceof DecisionNodeImpl) {
						// �¼�
						if (random.nextDouble() >= 0.9) {
							decision = n.eClass();
							addSize++;
							decisionFlag = false;
						}
					} else if (decisionFlag == true && mergeFlag == true && n instanceof MergeNodeImpl) {
						// �¼�
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
		System.out.println("�ܹ�ɾ���ĸ�����" + dList.size());
		System.out.println("�ܹ��¼ӵĸ�����" + addSize);
		System.out.println("�ܹ��޸ĵĸ�����" + changeSize);
		if (moveFlag == true) {
			System.out.println("��������ƶ�");
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

	/** �޸Ļ������¼ӵ����� */
	public static void setAttribute(EObject e, EAttribute a) {
		if (a.isMany() == false) { // ���a�ǵ�ֵ����
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
					e.eSet(a, random.nextInt(10000) + random.nextInt(10000));	// ��ֹ�����ظ���ֵ
				}
			}
		}
		// PENDING����ֵ����
	}

	// ����Ϊxmi
	public static void save(Resource resource, URI out) {
		try {
			resource.setURI(out);
			resource.save(null);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
