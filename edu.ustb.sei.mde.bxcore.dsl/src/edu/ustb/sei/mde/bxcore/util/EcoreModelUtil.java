package edu.ustb.sei.mde.bxcore.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EAnnotationImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import edu.ustb.sei.mde.bxcore.XmuCoreUtils;
import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.bxcore.structures.Index;
import edu.ustb.sei.mde.graph.type.DataTypeNode;
import edu.ustb.sei.mde.graph.type.PropertyEdge;
import edu.ustb.sei.mde.graph.type.TypeEdge;
import edu.ustb.sei.mde.graph.type.TypeGraph;
import edu.ustb.sei.mde.graph.type.TypeNode;
import edu.ustb.sei.mde.graph.typedGraph.TypedEdge;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;
import edu.ustb.sei.mde.graph.typedGraph.TypedNode;
import edu.ustb.sei.mde.graph.typedGraph.ValueEdge;
import edu.ustb.sei.mde.graph.typedGraph.ValueNode;

public class EcoreModelUtil {

	public EcoreModelUtil() {

	}

	static public TypeGraph load(EPackage pack) {
		TypeGraph graph = new TypeGraph();

		Set<EClass> eclasses = new HashSet<>();
		Set<EDataType> edatatypes = new HashSet<>();
		Set<EReference> ereferences = new HashSet<>();
		Set<EAttribute> eattributes = new HashSet<>();

		pack.eAllContents().forEachRemaining(o -> {		
			if (o instanceof EClass)
				eclasses.add((EClass) o);
			else if (o instanceof EReference) {
				eclasses.add(((EReference) o).getEReferenceType());
				ereferences.add((EReference) o);
			} else if (o instanceof EDataType)
				edatatypes.add((EDataType) o);
			else if (o instanceof EAttribute) {
				edatatypes.add(((EAttribute) o).getEAttributeType());
				eattributes.add((EAttribute) o);
			}
		});

		List<EClass> orderedClasses = new ArrayList<>();
		eclasses.forEach(c -> insertInOrder(eclasses, c, orderedClasses));

		orderedClasses.forEach(c -> {
			String code = c.isAbstract() ? "@" : "";
			code = code + c.getName();
			for (EClass s : c.getESuperTypes()) {
				code += ("," + s.getName());
			}
			graph.declare(code);
		});

		edatatypes.forEach(d -> {
			String code = d.getName() + ":";
			if (d instanceof EEnum) {
				code += "java.lang.String";
			} else {
				code += d.getInstanceClass().getCanonicalName();
			}
			graph.declare(code);
		});

		ereferences.forEach(r -> {
			
			// lyt
			if(r.eContainer() instanceof EAnnotationImpl) {
				
			} else {
				String code = r.getName() + ":" + ((EClass) r.eContainer()).getName() + "->"
						+ r.getEReferenceType().getName();
				if (r.isMany()) {
					if (r.isUnique())
						code += "*";
					else
						code += "#";
				}
				graph.declare(code);
			}
			
//			String code = r.getName() + ":" + ((EClass) r.eContainer()).getName() + "->"
//					+ r.getEReferenceType().getName();
//			if (r.isMany()) {
//				if (r.isUnique())
//					code += "*";
//				else
//					code += "#";
//			}
//			graph.declare(code);
		});

		eattributes.forEach(a -> {
			
			// lyt
			if(a.eContainer() instanceof EAnnotationImpl) {
				
			} else {
				String code = a.getName() + ":" + ((EClass) a.eContainer()).getName() + "->"
						+ a.getEAttributeType().getName();
				if (a.isUnique())
					code += "*";
				else
					code += "#";
				graph.declare(code);
			}
//			String code = a.getName() + ":" + ((EClass) a.eContainer()).getName() + "->"
//					+ a.getEAttributeType().getName();
//			if (a.isUnique())
//				code += "*";
//			else
//				code += "#";
//			graph.declare(code);
		});

		return graph;
	}

	static private void insertInOrder(Set<EClass> eclasses, EClass c, List<EClass> orderedClasses) {
		if (orderedClasses.contains(c))
			return;
		else {
			// lyt
			c.getESuperTypes().forEach(s -> insertInOrder(eclasses, s, orderedClasses));
			orderedClasses.add(c);

//			if(!eclasses.contains(c)) {
//				c.getESuperTypes().forEach(s->insertInOrder(eclasses, s, orderedClasses));
//				orderedClasses.add(c);
//			}
		}
	}

	static public TypedGraph load(List<EObject> roots, TypeGraph typeGraph) {
		Map<EObject, TypedNode> nodeMap = new HashMap<>();
		TypedGraph graph = new TypedGraph(typeGraph);
		roots.forEach(root -> addTypedNode(root, typeGraph, nodeMap, graph));
		roots.forEach(root -> root.eAllContents().forEachRemaining(o -> addTypedNode(o, typeGraph, nodeMap, graph)));
		roots.forEach(root -> addTypedEdges(root, typeGraph, nodeMap, graph));
		return graph;
	}

	static public void save(URI uri, TypedGraph graph, TypedGraph originalGraph, EPackage pack) {

		Collection<EObject> roots = save(graph, originalGraph, pack);
		Resource resource = privateResourceSet.createResource(uri);
		resource.getContents().addAll(roots);

		Map<String, Object> map = new HashMap<>();
		map.put(XMIResource.OPTION_SCHEMA_LOCATION, true);
		try {
			resource.save(map);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static public Collection<EObject> save(TypedGraph graph, TypedGraph originalGraph, EPackage pack) {
		Map<TypedNode, EObject> nodeMap = new HashMap<>();

		Map<String, EClass> eclasses = new HashMap<>();
		Map<String, EDataType> edatatypes = new HashMap<>();

		pack.eAllContents().forEachRemaining(o -> {
			if (o instanceof EClass)
				eclasses.put(((EClass) o).getName(), (EClass) o);
			else if (o instanceof EReference) {
				eclasses.put(((EReference) o).getEReferenceType().getName(), ((EReference) o).getEReferenceType());
			} else if (o instanceof EDataType) {
				edatatypes.put(((EDataType) o).getName(), (EDataType) o);
			} else if (o instanceof EAttribute) {
				edatatypes.put(((EAttribute) o).getEAttributeType().getName(), ((EAttribute) o).getEAttributeType());
			}
		});

		// lyt: 注释掉，否则里面的planOrder会造成影响
//		try {
//			graph.enforceOrder();
//		} catch (NothingReturnedException e1) {
//			e1.printStackTrace();
//			return Collections.emptyList();
//		}

		graph.getAllTypedNodes().forEach(n -> {
			EClass tc = eclasses.get(n.getType().getName());
			EObject obj = EcoreUtil.create(tc);
			nodeMap.put(n, obj);

			tc.getEAllAttributes().forEach(a -> {
				if (a.isChangeable() == false || a.isDerived() || a.isTransient())
					return;

				PropertyEdge edge = graph.getTypeGraph().getPropertyEdge(n.getType(), a.getName());

				if (a.isMany()) {
					List<Object> values = new ArrayList<>();
					if (a.getEAttributeType() instanceof EEnum) {
						graph.getValueEdges(n, edge).forEach(l -> {
							values.add(EcoreUtil.createFromString(a.getEAttributeType(),
									l.getTarget().getValue().toString()));
						});
					} else {
						graph.getValueEdges(n, edge).forEach(l -> {
							values.add(l.getTarget().getValue());
						});
					}
					obj.eSet(a, values);
				} else {
					List<ValueEdge> values = graph.getValueEdges(n, edge);
					if (values.isEmpty() == false) {
						Object value = values.get(0).getTarget().getValue();
						if (a.getEAttributeType() instanceof EEnum) {
							if (value != null)
								value = EcoreUtil.createFromString(a.getEAttributeType(), value.toString());
						}
						obj.eSet(a, value);
					} else {
						// currently, we do not set default value
//						if(a.isChangeable())
//							obj.eSet(a, );
					}

				}
			});
		});

		graph.getAllTypedNodes().forEach(n -> {
			EClass tc = eclasses.get(n.getType().getName());
			EObject src = nodeMap.get(n);
			tc.getEAllReferences().forEach(r -> {
				if (r.isChangeable() == false || r.isDerived() || r.isTransient())
					return;

				TypeEdge edge = graph.getTypeGraph().getTypeEdge(n.getType(), r.getName());
				if (r.isMany()) {
					List<EObject> values = new ArrayList<>();
					graph.getOutgoingEdges(n, edge).forEach(l -> {
						values.add(nodeMap.get(l.getTarget()));
					});
//					src.eSet(r, values);	// lyt: Enumberation不能这样设置
					
					try {
						if(r.getName().equals("eGenericSuperTypes")) {
							// lyt: 避免第二次覆盖掉eSuperTypes的继承了
						} else {
							src.eSet(r, values);
						}
					} catch (Exception e) {
						System.out.println("EcoreModelUtil line 277");
					}
					
					
				} else {
					List<TypedEdge> values = graph.getOutgoingEdges(n, edge);
					if (values.isEmpty() == false) {
						EObject tar = nodeMap.get(values.get(0).getTarget());
						src.eSet(r, tar);
					}
				}
			});
		});

		List<EObject> roots = nodeMap.values().stream().filter(n -> n.eContainer() == null)
				.collect(Collectors.toList());

		if (roots.size() != 1 && originalGraph != null)
			roots = nodeMap.entrySet().stream().filter(entry -> {
				Index ind = entry.getKey().getIndex();
				TypedNode oldNode = null;
				try {
					oldNode = originalGraph.getElementByIndexObject(ind);
				} catch (NothingReturnedException e) {
					oldNode = null;
				}

				boolean oldRoot = oldNode == null || originalGraph.getIncomingEdges(oldNode).stream().allMatch(l -> {
					EClass sc = eclasses.get(l.getSource().getType().getName());
					EStructuralFeature feature = sc.getEStructuralFeature(l.getType().getName());
					return feature instanceof EAttribute || !((EReference) feature).isContainment();
				});

				return entry.getValue().eContainer() == null && oldRoot;
			}).map(entry -> entry.getValue()).collect(Collectors.toList());

		return roots;
	}

	@SuppressWarnings("unchecked")
	private static void addTypedEdges(EObject root, TypeGraph typeGraph, Map<EObject, TypedNode> nodeMap,
			TypedGraph graph) {
		EClass cls = root.eClass();
		TypeNode tn = typeGraph.getTypeNode(cls.getName());

		cls.getEAllReferences().forEach(r -> {
			TypeEdge typeEdge = typeGraph.getTypeEdge(tn, r.getName());
			if (r.isMany()) {
				Collection<EObject> targets = (Collection<EObject>) root.eGet(r);
				targets.forEach(t -> {
					TypedNode targetNode = nodeMap.get(t);
					if (targetNode == null) {
						XmuCoreUtils.log(Level.WARNING,
								"The target node is not registered. The loader will ignore this edge: " + r, null);
					} else {
						TypedEdge edge = new TypedEdge(nodeMap.get(root), targetNode, typeEdge);
						graph.addTypedEdge(edge);
						if (r.isContainment())
							addTypedEdges(t, typeGraph, nodeMap, graph);
					}
				});
			} else {
				EObject t = (EObject) root.eGet(r);
				if (t != null) {
					TypedNode targetNode = nodeMap.get(t);
					if (targetNode == null) {
						XmuCoreUtils.log(Level.WARNING,
								"The target node is not registered. The loader will ignore this edge: " + r, null);
					} else {
						TypedEdge edge = new TypedEdge(nodeMap.get(root), targetNode, typeEdge);
						graph.addTypedEdge(edge);
						if (r.isContainment())
							addTypedEdges(t, typeGraph, nodeMap, graph);
					}
				}
			}
		});
	}

	private static void addTypedNode(EObject node, TypeGraph typeGraph, Map<EObject, TypedNode> nodeMap,
			TypedGraph graph) {

		TypeNode typeNode = typeGraph.getTypeNode(node.eClass().getName());
		TypedNode rootNode = new TypedNode(typeNode);
		graph.addTypedNode(rootNode);
		nodeMap.put(node, rootNode);

		EClass cls = node.eClass();
		cls.getEAllAttributes().forEach(a -> {
			DataTypeNode dataTypeNode = typeGraph.getDataTypeNode(a.getEAttributeType().getName());
			PropertyEdge propertyEdge = typeGraph.getPropertyEdge(typeNode, a.getName());

			if (a.isMany()) {
				@SuppressWarnings("unchecked")
				Collection<Object> values = (Collection<Object>) node.eGet(a);
				values.forEach(v -> {
					if (a.getEAttributeType() instanceof EEnum) {
						if (v instanceof Enumerator)
							v = ((Enumerator) v).getLiteral();
						else
							v = v.toString();
					}

					ValueNode vn = ValueNode.createConstantNode(v, dataTypeNode);
					graph.addValueNode(vn);
					graph.addValueEdge(new ValueEdge(rootNode, vn, propertyEdge));
				});
			} else {
				Object v = node.eGet(a);
				if (v != null) {
					if (a.getEAttributeType() instanceof EEnum) {
						if (v instanceof Enumerator)
							v = ((Enumerator) v).getLiteral();
						else
							v = v.toString();
					}
					ValueNode vn = ValueNode.createConstantNode(v, dataTypeNode);
					graph.addValueNode(vn);
					graph.addValueEdge(new ValueEdge(rootNode, vn, propertyEdge));
				}

			}
		});
	}

	static private ResourceSet privateResourceSet = new ResourceSetImpl();
	static {
		privateResourceSet.getPackageRegistry().put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
		privateResourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
	}

	static public EPackage loadPacakge(URI uri) {
		Resource resource = privateResourceSet.getResource(uri, true);
		if (resource != null) {
			EPackage pkg = (EPackage) resource.getContents().get(0);
			registerPacakge(pkg);
			return pkg;
		}
		return null;
	}

	public static void registerPacakge(EPackage pkg) {
		privateResourceSet.getPackageRegistry().put(pkg.getNsURI(), pkg);
	}

	public static void save(URI uri, List<EObject> objects) {
		Resource resource = privateResourceSet.getResource(uri, false);
		if (resource != null)
			resource.unload();
		resource = privateResourceSet.createResource(uri);
		resource.getContents().addAll(objects);

		Map<String, Object> map = new HashMap<>();
		map.put(XMIResource.OPTION_SCHEMA_LOCATION, true);
		try {
			resource.save(map);
			resource.unload();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static public List<EObject> load(URI uri) {
		Resource resource = privateResourceSet.getResource(uri, false);
		if (resource != null) {
			// we must unload this resource because it may be updated outsides
			resource.unload();
		}
		resource = privateResourceSet.getResource(uri, true);
		if (resource != null) {
			return resource.getContents();
		}
		return null;
	}

}
