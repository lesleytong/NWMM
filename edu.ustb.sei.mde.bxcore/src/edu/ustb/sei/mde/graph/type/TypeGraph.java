package edu.ustb.sei.mde.graph.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.ustb.sei.mde.bxcore.XmuCoreUtils;
import edu.ustb.sei.mde.graph.Derived;
import edu.ustb.sei.mde.graph.IEdge;
import edu.ustb.sei.mde.graph.IGraph;
import edu.ustb.sei.mde.graph.INode;

public class TypeGraph implements IGraph {

	private ArrayList<INode> nodes;
	private ArrayList<IEdge> edges;

	public TypeGraph() {
		nodes = new ArrayList<>();
		edges = new ArrayList<>();
	}
	
	@Derived
	private List<TypeNode> typeNodes = null;

	@Derived
	public List<TypeNode> getAllTypeNodes() {
		if (typeNodes == null) {
			typeNodes = new ArrayList<TypeNode>();
			nodes.forEach((n) -> {
				if (n instanceof TypeNode)
					typeNodes.add((TypeNode) n);
			});
		}
		return typeNodes;
	}

	@Derived
	List<DataTypeNode> dataTypeNodes = null;

	@Derived
	public List<DataTypeNode> getAllDataTypeNodes() {
		if (dataTypeNodes == null) {
			dataTypeNodes = new ArrayList<DataTypeNode>();
			nodes.forEach((n) -> {
				if (n instanceof DataTypeNode)
					dataTypeNodes.add((DataTypeNode) n);
			});
		}
		return dataTypeNodes;
	}

	@Derived
	List<TypeEdge> typeEdges = null;

	@Derived
	public List<TypeEdge> getAllTypeEdges() {
		if (typeEdges == null) {
			typeEdges = new ArrayList<TypeEdge>();
			edges.forEach((n) -> {
				if (n instanceof TypeEdge)
					typeEdges.add((TypeEdge) n);
			});
		}
		return typeEdges;
	}

	@Derived
	List<GeneralizationEdge> generalizationEdges = null;

	@Derived
	public List<GeneralizationEdge> getAllGeneralizationEdges() {
		if (generalizationEdges == null) {
			generalizationEdges = new ArrayList<GeneralizationEdge>();
			edges.forEach((n) -> {
				if (n instanceof GeneralizationEdge)
					generalizationEdges.add((GeneralizationEdge) n);
			});
		}
		return generalizationEdges;
	}

	@Derived
	List<PropertyEdge> propertyEdges = null;

	@Derived
	public List<PropertyEdge> getAllPropertyEdges() {
		if (propertyEdges == null) {
			propertyEdges = new ArrayList<PropertyEdge>();
			edges.forEach((n) -> {
				if (n instanceof PropertyEdge)
					propertyEdges.add((PropertyEdge) n);
			});
		}
		return propertyEdges;
	}

	@Override
	public List<INode> getNodes() {
		return nodes;
	}

	@Override
	public List<IEdge> getEdges() {
		return edges;
	}

	@Derived
	HashMap<TypeNode, List<TypeNode>> superTypeMap = new HashMap<TypeNode, List<TypeNode>>();

	@Derived
	public List<TypeNode> getAllSuperTypes(TypeNode n) {
		List<TypeNode> st = superTypeMap.get(n);
		if (st == null) {
			st = new ArrayList<TypeNode>();
			superTypeMap.put(n, st);
			List<GeneralizationEdge> elist = this.getAllGeneralizationEdges();
			for (GeneralizationEdge g : elist) {
				if (g.getSource() == n) {
					st.add(g.getTarget());
					XmuCoreUtils.addAllUnique(st, this.getAllSuperTypes(g.getTarget()));
				}
			}
		}

		return st;
	}

	/** child是否为parent的子类型 */
	public boolean isSuperTypeOf(ITypeNode child, ITypeNode parent) {
		if (parent == TypeNode.ANY_TYPE || child == DataTypeNode.NULL_TYPE // NULL value
				|| child == TypeNode.NULL_TYPE) // NULL value
			return true;
		else if (child == parent)
			return true;
		else if (child instanceof TypeNode)
			return this.getAllSuperTypes((TypeNode) child).contains(parent);
		else
			return false;
	}

	@Derived
	HashMap<TypeNode, List<PropertyEdge>> propertyEdgeMap = new HashMap<TypeNode, List<PropertyEdge>>();

	@Derived
	public List<PropertyEdge> getAllPropertyEdges(TypeNode n) {
		List<PropertyEdge> result = propertyEdgeMap.get(n);
		if (result == null) {
			result = new ArrayList<PropertyEdge>();
			propertyEdgeMap.put(n, result);
			List<TypeNode> superTypesOfN = this.getAllSuperTypes(n);

			for (TypeNode s : superTypesOfN) {
				List<PropertyEdge> tt = this.getAllPropertyEdges(s);
				XmuCoreUtils.addAllUnique(result, tt);
			}

			List<PropertyEdge> allProperties = this.getAllPropertyEdges();

			for (PropertyEdge g : allProperties) {
				if (g.getSource() == n) {
					XmuCoreUtils.addUnique(result, g);
				}
			}
		}

		return result;
	}

	@Derived
	HashMap<TypeNode, List<TypeEdge>> outgoingTypeEdgeMap = new HashMap<TypeNode, List<TypeEdge>>();

	@Derived
	public List<TypeEdge> getAllOutgoingTypeEdges(TypeNode n) {
		List<TypeEdge> result = outgoingTypeEdgeMap.get(n);
		if (result == null) {
			result = new ArrayList<TypeEdge>();
			outgoingTypeEdgeMap.put(n, result);
			List<TypeNode> superTypesOfN = this.getAllSuperTypes(n);

			for (TypeNode s : superTypesOfN) {
				List<TypeEdge> tt = this.getAllOutgoingTypeEdges(s);
				XmuCoreUtils.addAllUnique(result, tt);
			}

			List<TypeEdge> allEdges = this.getAllTypeEdges();

			for (TypeEdge g : allEdges) {
				if (g.getSource() == n) {
					XmuCoreUtils.addUnique(result, g);
				}
			}

		}
		return result;
	}

	@Derived
	HashMap<TypeNode, List<TypeEdge>> incomingTypeEdgeMap = new HashMap<TypeNode, List<TypeEdge>>();

	@Derived
	public List<TypeEdge> getAllIncomingTypeEdges(TypeNode n) {
		List<TypeEdge> result = incomingTypeEdgeMap.get(n);
		if (result == null) {
			result = new ArrayList<TypeEdge>();
			incomingTypeEdgeMap.put(n, result);
			List<TypeNode> superTypesOfN = this.getAllSuperTypes(n);

			for (TypeNode s : superTypesOfN) {
				List<TypeEdge> tt = this.getAllIncomingTypeEdges(s);
				XmuCoreUtils.addAllUnique(result, tt);
			}

			List<TypeEdge> allEdges = this.getAllTypeEdges();

			for (TypeEdge g : allEdges) {
				if (g.getTarget() == n) {
					XmuCoreUtils.addUnique(result, g);
				}
			}

		}
		return result;
	}

	public void addNode(INode n) {
		this.nodes.add(n);
		((ITypeGraphItem) n).setTypeGraph(this);

		if (n instanceof TypeNode)
			this.typeNodes = null;
		else
			this.dataTypeNodes = null;
	}

	public void addEdge(IEdge e) {
		this.edges.add(e);
		if (!(e instanceof GeneralizationEdge))
			((ITypeGraphItem) e).setTypeGraph(this);
		if (e instanceof TypeEdge)
			this.typeEdges = null;
		else if (e instanceof GeneralizationEdge)
			this.generalizationEdges = null;
		else
			this.propertyEdges = null;
	}

	protected void initDataTypeNodes() {
		DataTypeNode n = null;

		n = new DataTypeNode();
		n.setDataType("Integer", int.class);
		this.addNode(n);

		n = new DataTypeNode();
		n.setDataType("Boolean", boolean.class);
		this.addNode(n);

		n = new DataTypeNode();
		n.setDataType("String", String.class);
		this.addNode(n);

		n = new DataTypeNode();
		n.setDataType("Char", char.class);
		this.addNode(n);
	}

//	public DataTypeNode getDataType(String name) {
//		return this.getAllDataTypeNodes().stream().filter(d->d.getName().equals(name)).findAny().get();
//	}

	static public TypeNode createTypeNode(String name, boolean abs) {
		TypeNode n = new TypeNode();
		n.setName(name);
		n.setAbstract(abs);
		return n;
	}

	static public GeneralizationEdge createGeneralizationEdge(TypeNode parent, TypeNode child) {
		GeneralizationEdge e = new GeneralizationEdge();
		e.setSource(child);
		e.setTarget(parent);
		return e;
	}

	static public IStructuralFeatureEdge createFeatureEdge(String name, TypeNode source, INode target, boolean many) {
		IStructuralFeatureEdge res = null;

		if (target instanceof TypeNode) {
			TypeEdge e = new TypeEdge();
			e.setName(name);
			e.setSource(source);
			e.setTarget((TypeNode) target);
			e.setMany(many);
			res = e;
		} else {
			PropertyEdge e = new PropertyEdge();
			e.setName(name);
			e.setSource(source);
			e.setTarget((DataTypeNode) target);
			e.setMany(many);
		}

		return res;
	}

	static public DataTypeNode createDataTypeNode(String name, Class<?> javaType) {
		DataTypeNode n = new DataTypeNode();
		n.setDataType(name, javaType);
		return n;
	}

	public TypeNode getTypeNode(String string) {
		try {
			return this.getAllTypeNodes().stream().filter(t -> t.getName().equals(string)).findFirst().get();
		} catch (Exception e) {
			return null;
		}
	}

	public DataTypeNode getDataTypeNode(String string) {
		try {
			return this.getAllDataTypeNodes().stream().filter(t -> t.getName().equals(string)).findFirst().get();
		} catch (Exception e) {
			return null;
		}
	}

	public TypeEdge getTypeEdge(TypeNode typeNode, String string) {
		try {
			return this.getAllOutgoingTypeEdges(typeNode).stream().filter(e -> e.getName().equals(string)).findFirst()
					.get();
		} catch (Exception e) {
			return null;
		}
	}

	public PropertyEdge getPropertyEdge(TypeNode typeNode, String string) {
		try {
			return this.getAllPropertyEdges(typeNode).stream().filter(e -> e.getName().equals(string)).findFirst()
					.get();
		} catch (Exception e) {
			return null;
		}
	}

	public String printGraph() {
		StringBuilder builder = new StringBuilder();

		int i = 0;
		List<TypeNode> allTypeNodes = this.getAllTypeNodes();
		for (i = 0; i < allTypeNodes.size(); i++) {
			TypeNode n = allTypeNodes.get(i);
			builder.append("TypeNode.");
			builder.append(i);
			builder.append(n.toString());
			builder.append("{");
			builder.append("supers:[");
			this.getAllSuperTypes(n).forEach(s -> {
				builder.append("TypeNode@" + allTypeNodes.indexOf(s));
				builder.append(",");
			});
			builder.append("],");
			builder.append("properties:[");
			this.getAllPropertyEdges(n).forEach(s -> {
				builder.append(s.toString());
				builder.append(",");
			});
			builder.append("],");
			builder.append("associations:[");
			this.getAllOutgoingTypeEdges(n).forEach(s -> {
				builder.append(s.toString());
				builder.append(",");
			});
			builder.append("],");
			builder.append("}");
			builder.append("\n");
		}

		return builder.toString();
	}

	/** 计算子类型 */
	public TypeNode computeSubtype(TypeNode left, TypeNode right) {
		if (left == right)
			return left;
		if (isSuperTypeOf(left, right)) // isSuperTypeOf(child, parent)
			return left;
		if (isSuperTypeOf(right, left))
			return right;

		// should go on
		return TypeNode.NULL_TYPE;
	}

	/** 用于buildTypeGraph */
	public void declare(String statement) {
		String typeNodeFormat = "\\s*(@?)(\\w+)\\s*((,\\w+)*)\\s*";
		String dataNodeFormat = "\\s*(\\w+)\\s*:\\s*([\\w\\.]+)\\s*";
		String edgeFormat = "\\s*(@?)\\s*(\\w+)\\s*:\\s*(\\w+)\\s*->\\s*(\\w+)([\\*#]?)\\s*";
		
		Pattern typeNodePat = Pattern.compile(typeNodeFormat);
		Pattern dataNodePat = Pattern.compile(dataNodeFormat);
		Pattern edgePat = Pattern.compile(edgeFormat);

		String[] stats = statement.split(";");
		for (String stat : stats) {
			Matcher matcher = null;

			if ((matcher = typeNodePat.matcher(stat)).matches()) {
				String abs = matcher.group(1);
				String name = matcher.group(2);
				String ext = matcher.group(3);

				TypeNode tn = new TypeNode();
				tn.setAbstract(abs.isEmpty() == false);
				tn.setName(name);

				this.addNode(tn);

				if (ext != null) {
					String[] pts = ext.split(",");
					for (String p : pts) {
						if (p.isEmpty())
							continue;
						TypeNode pn = this.getTypeNode(p);

						GeneralizationEdge ge = new GeneralizationEdge();
						ge.setSource(tn);
						ge.setTarget(pn);

						this.addEdge(ge);
					}
				}
			} else if ((matcher = dataNodePat.matcher(stat)).matches()) {
				String name = matcher.group(1);
				String javaName = matcher.group(2);

				DataTypeNode dn = new DataTypeNode();
				try {
					dn.setDataType(name, convertToJavaClass(javaName));
					this.addNode(dn);
				} catch (ClassNotFoundException e) {
					XmuCoreUtils.log(Level.SEVERE,
							"error in declaring DataTypeNode: the Java type name is invalid => " + javaName, e);
				}
			} else if ((matcher = edgePat.matcher(stat)).matches()) {
				String containment = matcher.group(1);
				String name = matcher.group(2);
				String src = matcher.group(3);
				String tar = matcher.group(4);
				String mul = matcher.group(5);
				Boolean uni = mul == null || mul.equals("*");

				TypeNode sn = this.getTypeNode(src);
				if (sn == null)
					continue;
				INode tn = this.getTypeNode(tar);
				if (tn == null)
					tn = this.getDataTypeNode(tar);
				if (tn == null)
					continue;

				if (tn instanceof TypeNode) {
					TypeEdge e = new TypeEdge();
					e.setName(name);
					e.setSource(sn);
					e.setTarget((TypeNode) tn);
					e.setMany(mul.isEmpty() == false);
					e.setUnique(uni);
					e.setContainment(!containment.isEmpty());
					this.addEdge(e);
				} else if (tn instanceof DataTypeNode) {
					PropertyEdge e = new PropertyEdge();
					e.setName(name);
					e.setSource(sn);
					e.setTarget((DataTypeNode) tn);
					e.setMany(mul.isEmpty() == false);
					e.setUnique(uni);
					this.addEdge(e);
				}
			} else {
				XmuCoreUtils.log(Level.SEVERE, "unhandled declaration:" + stat, null);
			}
		}
	}

	protected Class<?> convertToJavaClass(String javaName) throws ClassNotFoundException {
		if (int.class.getCanonicalName().equals(javaName))
			return int.class;
		else if (boolean.class.getCanonicalName().equals(javaName))
			return boolean.class;
		else if (char.class.getCanonicalName().equals(javaName))
			return char.class;
		else if (short.class.getCanonicalName().equals(javaName))
			return short.class;
		else if (float.class.getCanonicalName().equals(javaName))
			return float.class;
		else if (double.class.getCanonicalName().equals(javaName))
			return double.class;
		else if (byte.class.getCanonicalName().equals(javaName))
			return byte.class;
		else if (long.class.getCanonicalName().equals(javaName))
			return long.class;
		else
			return Class.forName(javaName);
	}

	static final Pattern pathSegPat = Pattern
			.compile("\\s*\\((.+)\\s*\\)\\s*\\{\\s*(\\d+)\\s*,\\s*(-?\\d+)\\s*\\}\\s*");
	private HashMap<String, IPathType> pathMap = new HashMap<>();

	public IPathType resolvePathType(String typeString) {
		IPathType r = pathMap.get(typeString);
		if (r != null)
			return r;

		try {
			String[] buf;
			// typeString = nodeType :: path
			buf = typeString.split("::");

			String nodeType = buf[0].trim();
			String pathType = buf[1].trim();

			Set<TypeNode> startNodes = new HashSet<>();
			Set<TypeNode> endNodes = new HashSet<>();
			Set<IStructuralFeatureEdge> features = new java.util.LinkedHashSet<>();

			startNodes.add(this.getTypeNode(nodeType));

			buf = pathType.split("\\.");

			List<DashedPathTypeSegment> segs = new ArrayList<>();

			for (String pathSeg : buf) {
				features.clear();
				endNodes.clear();

				Matcher matcher = pathSegPat.matcher(pathSeg);
				if (matcher.matches() == false)
					throw new NullPointerException();
				String[] featureNames = matcher.group(1).split("\\|");
				int min = Integer.parseInt(matcher.group(2));
				int max = Integer.parseInt(matcher.group(3));

				for (String fn : featureNames) {
					for (TypeNode s : startNodes) {
						IStructuralFeatureEdge edge = this.getTypeEdge(s, fn);
						if (edge != null) {
							features.add(edge);
							endNodes.add((TypeNode) edge.getTarget());
						} else {
							edge = this.getPropertyEdge(s, fn);
							features.add(edge);
						}
					}
				}

				DashedPathTypeSegment seg = new DashedPathTypeSegment(min, max,
						features.stream().toArray((i) -> new IStructuralFeatureEdge[i]));
				segs.add(seg);

				Set<TypeNode> temp = startNodes;
				startNodes = endNodes;
				endNodes = temp;
			}

			DashedPathType result = new DashedPathType(segs.stream().toArray(i -> new DashedPathTypeSegment[i]));
			pathMap.put(typeString, result);
			return result;
		} catch (Exception e) {
			return null;
		}
	}
}
