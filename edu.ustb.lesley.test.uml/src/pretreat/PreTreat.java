package pretreat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.PackageImport;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.internal.impl.ClassImpl;
import org.eclipse.uml2.uml.internal.impl.ModelImpl;
import org.eclipse.uml2.uml.internal.impl.PackageImpl;
import org.eclipse.uml2.uml.internal.impl.PrimitiveTypeImpl;
import org.eclipse.uml2.uml.resource.UMLResource;

import nway.ChangeTool;

public class PreTreat {

	public static void main(String[] args) {

		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION,
				UMLResource.Factory.INSTANCE);

		// 奇怪的bug，如果没有这行，只能识别成AnyType
		// 然而ep根本没用上
		// 猜想是隐式对应上了EPackage？
		EPackage ep = UMLPackage.eINSTANCE;

		URI originURI = URI
				.createFileURI("E:\\eclipse-dsl-workspace\\edu.ustb.lesley.test.uml\\src\\pretreat\\futur_origin.uml");

		URI baseURI = URI.createFileURI("E:\\eclipse-dsl-workspace\\edu.ustb.lesley.test.uml\\src\\pretreat\\futur.uml");

		Resource originResource = resourceSet.getResource(originURI, true);

		pretreat(originResource, baseURI);

	}

	public static void pretreat(Resource resource, URI out) {

		HashMap<Object, Object> internalTypeMap = new HashMap<>();

		resource.getContents().forEach(model -> {
			extractType(internalTypeMap, model);
		});

		resource.getContents().forEach(model -> {
			setInternalType(internalTypeMap, model);
		});

		ChangeTool.save(resource, out);
		System.out.println("done");
	}

	public static void pretreatIter(Resource resource, URI out) {

		HashMap<Object, Object> internalTypeMap = new HashMap<>();

		resource.getContents().forEach(model -> {
			model.eContents().forEach(e -> {
				if (e instanceof ModelImpl) {
					extractType(internalTypeMap, e);
				}
			});
		});

		resource.getContents().forEach(model -> {
			model.eContents().forEach(e -> {
				if (e instanceof ModelImpl) {
					setInternalType(internalTypeMap, e);
				}
			});
		});

		ChangeTool.save(resource, out);
		System.out.println("done");
	}

	public static void extractType(HashMap<Object, Object> internalTypeMap, EObject model) {
		Set<Type> pType = new HashSet<>();
		Set<Type> cType = new HashSet<>();
		model.eAllContents().forEachRemaining(c -> {
			if (c instanceof PackageImport) {	// for genmodel's .uml
				EcoreUtil.remove(c);
			} else if (c instanceof ClassImpl) {
				ClassImpl classImpl = (ClassImpl) c;

				EList<Property> ownedAttributes = classImpl.getOwnedAttributes();
				ownedAttributes.forEach(a -> {
					Type type = a.getType();

					if (type instanceof PrimitiveTypeImpl) {
						pType.add(type);
					} else if (type instanceof ClassImpl) {
						ClassImpl ct = (ClassImpl) type;
						URI eProxyURI = ct.eProxyURI();
						if (eProxyURI != null) {
							cType.add(type);
						}
					}
				});

				EList<Operation> ownedOperations = classImpl.getOwnedOperations();
				ownedOperations.forEach(o -> {
					Type type = o.getType();

					if (type instanceof PrimitiveTypeImpl) {
						pType.add(type);
					} else if (type instanceof ClassImpl) {
						ClassImpl ct = (ClassImpl) type;
						URI eProxyURI = ct.eProxyURI();
						if (eProxyURI != null) {
							cType.add(type);
						}
					}

					EList<Parameter> ownedParameters = o.getOwnedParameters();
					ownedParameters.forEach(p -> {
						Type parameterType = p.getType();
						if (parameterType instanceof PrimitiveTypeImpl) {
							pType.add(parameterType);
						} else if (parameterType instanceof ClassImpl) {
							ClassImpl ct = (ClassImpl) parameterType;
							URI eProxyURI = ct.eProxyURI();
							if (eProxyURI != null) {
								cType.add(parameterType);
							}
						}
					});

				});

			} else if (c instanceof PackageImpl) {
				c.eAllContents().forEachRemaining(sub -> {
					if (sub instanceof ClassImpl) {
						ClassImpl classImpl = (ClassImpl) sub;

						EList<Property> ownedAttributes = classImpl.getOwnedAttributes();
						ownedAttributes.forEach(a -> {
							Type type = a.getType();

							if (type instanceof PrimitiveTypeImpl) {
								pType.add(type);
							} else if (type instanceof ClassImpl) {
								ClassImpl ct = (ClassImpl) type;
								URI eProxyURI = ct.eProxyURI();
								if (eProxyURI != null) {
									cType.add(type);
								}
							}
						});

						EList<Operation> ownedOperations = classImpl.getOwnedOperations();
						ownedOperations.forEach(o -> {
							Type type = o.getType();

							if (type instanceof PrimitiveTypeImpl) {
								pType.add(type);
							} else if (type instanceof ClassImpl) {
								ClassImpl ct = (ClassImpl) type;
								URI eProxyURI = ct.eProxyURI();
								if (eProxyURI != null) {
									cType.add(type);
								}
							}

							EList<Parameter> ownedParameters = o.getOwnedParameters();
							ownedParameters.forEach(p -> {
								Type parameterType = p.getType();
								if (parameterType instanceof PrimitiveTypeImpl) {
									pType.add(parameterType);
								} else if (parameterType instanceof ClassImpl) {
									ClassImpl ct = (ClassImpl) parameterType;
									URI eProxyURI = ct.eProxyURI();
									if (eProxyURI != null) {
										cType.add(parameterType);
									}
								}
							});
						});
					}
				});
			}
		});

		pType.forEach(type -> {
			PrimitiveTypeImpl pt = ((PrimitiveTypeImpl) type);
			URI eProxyURI = pt.eProxyURI();
			String fragment = eProxyURI.fragment();
			PrimitiveType createPrimitiveType = UMLFactory.eINSTANCE.createPrimitiveType();
			// for genmodel .uml
			if (fragment.contains("/")) {
				fragment = fragment.replace("/", "");
			}
			createPrimitiveType.setName(fragment);
			internalTypeMap.put(type, createPrimitiveType);
			((ModelImpl) model).getOwnedTypes().add(createPrimitiveType);	// 从这里看出，只有ModelImpl才能add
		});

		cType.forEach(type -> {
			ClassImpl ct = (ClassImpl) type;
			URI eProxyURI = ct.eProxyURI();
			Class createClass = UMLFactory.eINSTANCE.createClass();
			createClass.setName(eProxyURI.fragment());
			((ModelImpl) model).getPackagedElements().add(createClass);
			internalTypeMap.put(type, createClass);
		});
	}

	public static void setInternalType(HashMap<Object, Object> internalTypeMap, EObject model) {
		model.eAllContents().forEachRemaining(c -> {
			if (c instanceof ClassImpl) {
				ClassImpl classImpl = (ClassImpl) c;

				EList<Property> ownedAttributes = classImpl.getOwnedAttributes();
				ownedAttributes.forEach(a -> {
					Type type = a.getType();
					Object t = internalTypeMap.get(type);
					if (t != null) {
						a.setType((Type) t);
					}

				});

				EList<Operation> ownedOperations = classImpl.getOwnedOperations();
				ownedOperations.forEach(o -> {
					Type type = o.getType();
					Object t = internalTypeMap.get(type);
					if (t != null) {
						o.setType((Type) t);
					}

					EList<Parameter> ownedParameters = o.getOwnedParameters();
					ownedParameters.forEach(p -> {
						Type parameterType = p.getType();
						Object pt = internalTypeMap.get(parameterType);
						if (pt != null) {
							p.setType((Type) pt);
						}
					});

				});

			} else if (c instanceof PackageImpl) {
				c.eAllContents().forEachRemaining(sub -> {
					if (sub instanceof ClassImpl) {
						ClassImpl classImpl = (ClassImpl) sub;

						EList<Property> ownedAttributes = classImpl.getOwnedAttributes();
						ownedAttributes.forEach(a -> {
							Type type = a.getType();
							Object t = internalTypeMap.get(type);
							if (t != null) {
								a.setType((Type) t);
							}

						});

						EList<Operation> ownedOperations = classImpl.getOwnedOperations();
						ownedOperations.forEach(o -> {
							Type type = o.getType();
							Object t = internalTypeMap.get(type);
							if (t != null) {
								o.setType((Type) t);
							}

							EList<Parameter> ownedParameters = o.getOwnedParameters();
							ownedParameters.forEach(p -> {
								Type parameterType = p.getType();
								Object pt = internalTypeMap.get(parameterType);
								if (pt != null) {
									p.setType((Type) pt);
								}
							});
						});

					}
				});
			}
		});
	}

}
