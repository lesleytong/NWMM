package edu.ustb.sei.mde.graph.typedGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import edu.ustb.sei.mde.bxcore.XmuCoreUtils;
import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.bxcore.structures.Index;
import edu.ustb.sei.mde.graph.Derived;
import edu.ustb.sei.mde.graph.IEdge;
import edu.ustb.sei.mde.graph.IGraph;
import edu.ustb.sei.mde.graph.INode;
import edu.ustb.sei.mde.graph.type.DataTypeNode;
import edu.ustb.sei.mde.graph.type.IStructuralFeatureEdge;
import edu.ustb.sei.mde.graph.type.PropertyEdge;
import edu.ustb.sei.mde.graph.type.TypeEdge;
import edu.ustb.sei.mde.graph.type.TypeGraph;
import edu.ustb.sei.mde.graph.type.TypeNode;
import edu.ustb.sei.mde.graph.typedGraph.constraint.GraphConstraint;
import edu.ustb.sei.mde.structure.PairMap;
import edu.ustb.sei.mde.structure.Tuple3;

@SuppressWarnings("unused")
public class TypedGraph extends IndexSystem implements IGraph {

	OrderInformation order;
	GraphConstraint constraint;
	List<TypedNode> allTypedNodes;
	List<ValueNode> allValueNodes;
	List<TypedEdge> allTypedEdges;
	List<ValueEdge> allValueEdges;

	@Derived
	private HashMap<TypedNode, List<TypedEdge>> outgoingEdgeMap;
	@Derived
	private HashMap<TypedNode, List<TypedEdge>> incomingEdgeMap;
	@Derived
	private HashMap<TypedNode, List<ValueEdge>> valueEdgeMap;
	@Derived
	private HashMap<ValueNode, List<ValueEdge>> valueReferenceMap;

	TypeGraph typeGraph; // 有类型图的类型图，相当于模型的元模型

	/** TypedGraph构造方法中调用init() */
	public void init() {
		allTypedNodes = new ArrayList<TypedNode>();
		allValueNodes = new ArrayList<ValueNode>();
		allTypedEdges = new ArrayList<TypedEdge>();
		allValueEdges = new ArrayList<ValueEdge>();
		valueEdgeMap = new HashMap<TypedNode, List<ValueEdge>>();
		valueReferenceMap = new HashMap<>();
		incomingEdgeMap = new HashMap<TypedNode, List<TypedEdge>>();
		outgoingEdgeMap = new HashMap<TypedNode, List<TypedEdge>>();

		order = new OrderInformation();

		constraint = GraphConstraint.TRUE;
	}

	public List<TypedNode> getAllTypedNodes() {
		return allTypedNodes;
	}

	public List<ValueNode> getAllValueNodes() {
		return allValueNodes;
	}

	public List<TypedEdge> getAllTypedEdges() {
		return allTypedEdges;
	}

	public List<ValueEdge> getAllValueEdges() {
		return allValueEdges;
	}

	public OrderInformation getOrder() {
		return order;
	}

	public TypeGraph getTypeGraph() {
		return this.typeGraph;
	}

//	private Environment environment;
//	
//	public Environment getEnvironment() {
//		return environment;
//	}
//
//	public void setEnvironment(Environment environment) {
//		this.environment = environment;
//	}

	@Override
	@Derived
	public List<INode> getNodes() {
		List<INode> nodes = new ArrayList<INode>(allTypedNodes.size() + allValueNodes.size());
		nodes.addAll(allTypedNodes);
		nodes.addAll(allValueNodes);
		return nodes;
	}

	@Override
	public List<IEdge> getEdges() {
		List<IEdge> edges = new ArrayList<IEdge>(allTypedEdges.size() + allValueEdges.size());
		edges.addAll(allTypedEdges);
		edges.addAll(allValueEdges);
		return edges;
	}

	public void addTypedNode(TypedNode n) {
		allTypedNodes.add(n);
		indexing(n); // 给对象生成索引、更新图的indexToObjectMap
	}

	public void addValueNode(ValueNode n) {
		if (allValueNodes.contains(n) == false)
			allValueNodes.add(n);

		indexing(n);
	}

	public void moveTypedEdgeTo(int n, int i) {
		moveEdgeTo(allTypedEdges, n, i);
	}

	public void moveValueEdgeTo(int n, int i) {
		moveEdgeTo(allValueEdges, n, i);
	}

	public <T> void moveEdgeTo(List<T> edges, int cur, int i) {
		T n = edges.get(cur);

		if (cur == i || cur == -1)
			return;
		else {
			if (cur < i) {
				for (int j = cur; j < i; j++) {
					edges.set(j, edges.get(j + 1));
				}
			} else {
				for (int j = cur; j > i; j--) {
					edges.set(j, edges.get(j - 1));
				}
			}
			edges.set(i, n);
		}
	}

//	public <T> void moveEdgeTo(List<T> edges, List<Integer> cur, int i) {
//		if(cur.isEmpty()) return;
//		Collections.sort(cur);
//		
//		if(cur.get(0)>i) {
//			
//		} else if(cur.get(cur.size()-1)<i) {
//			
//		}
//	}

	public int indexOf(List<? extends Object> list, Object o) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == o)
				return i;
		}
		return -1;
	}

	/** 泛型方法，为对象生成索引，并将对象和索引集的映射关系添加到图的indexToObject中 */
	private <T extends IndexableElement> void indexing(T n) {
		if (n.isIndexable() == false)
			return;

		try {
			// 如果对象还没有索引
			if (n.getIndex().isEmpty()) {
				// 生成全局唯一的索引，并添加到对象n的内部索引集中
				n.appendIndexValue(IndexSystem.generateUUID());
				// lyt-为了看控制台输出，暂时注释掉
//				XmuCoreUtils.warning("An object without index was found");
			}
		} catch (Exception e) {
			XmuCoreUtils.failure("Unknown error", e);
			return;
		}

		n.foreach(i -> {
			try {
				// 将内部索引集和对象的映射关系注册到图的indexToObjectMap中
				this.registerIndex(i, n);
			} catch (NothingReturnedException e) {
				java.util.logging.Logger.getLogger(XmuCoreUtils.COMPONENT_NAME).log(Level.SEVERE,
						"Two objects are mapped onto the same index", e);
			}
		});

	}

	/** 更新图的indexToObjectMap，我修改了private */
	public void reindexing(IndexableElement n) {
		n.foreach(i -> {
			this.indexToObjectMap.put(i, n);
		});
	}

	// lyt: simply add
	public void simAddTypedEdge(TypedEdge e) {
		allTypedEdges.add(e);
		indexing(e);
	}

	// lyt: additional information: int start
	public void addTypedEdge(TypedEdge e, int start) {
		if (e.getType().isMany()) {
			if (e.getType().isUnique()) {
				int i;
				for (i = start; i < allTypedEdges.size(); i++) {
					TypedEdge tmp = allTypedEdges.get(i);
					if (tmp.getType() == e.getType() && tmp.getSource().getIndex().equals(e.getSource().getIndex())
							&& tmp.getTarget().getIndex().equals(e.getTarget().getIndex())) {
						e.mergeIndex(tmp);
						reindexing(e);
						allTypedEdges.set(i, e);
						break;
					}
				}
				if (i == allTypedEdges.size()) {
					allTypedEdges.add(e);
					indexing(e);
				}
			} else {
				allTypedEdges.add(e);
				indexing(e);
			}
		} else {

			boolean replaced = false; // 每次都初始化为false

			for (int i = start; i < allTypedEdges.size(); i++) { // 新加只会与新加的冲突，start的作用
				TypedEdge tmp = allTypedEdges.get(i);
				if (tmp.getSource() == e.getSource() && tmp.getType() == e.getType()) {

					e.mergeIndex(tmp); // 如果不是isMany，但添加了多条边，会合并它们的索引集
					reindexing(e);

					if (replaced) { // reduce redundant, 感觉不会有此情况
						allTypedEdges.remove(i);
						i--;
						XmuCoreUtils.failure("Redundant edges " + tmp);
					} else {
						allTypedEdges.set(i, e); // 设置为后来的边对象
						replaced = true;
					}
				}
			}
			// 如果replaced是false，说明此单值边是第一次添加
			if (!replaced) {
				allTypedEdges.add(e);
				indexing(e);
			}
		}
		this.outgoingEdgeMap.remove(e.getSource());
		this.incomingEdgeMap.remove(e.getTarget());
	}

	/** 在图中添加TypedEdge类型的边对象 */
	public void addTypedEdge(TypedEdge e) {
		if (e.getType().isMany()) { // 如果e的TypeEdge是isMany
			if (e.getType().isUnique()) { // 如果是isMany，并且是isUnique
				TypedEdge err = null;
				try {
					// type、source、target都一致
					err = allTypedEdges.stream().filter(er -> { // 改成for循环，有位置信息
						return er.getType() == e.getType() && er.getSource().getIndex().equals(e.getSource().getIndex())
								&& er.getTarget().getIndex().equals(e.getTarget().getIndex());
					}).findAny().get();
				} catch (Exception ex2) { // 如果没找到，在allTypedEdges中添加e
					allTypedEdges.add(e);
					indexing(e);
				}
				if (err != null) // 如果找到了，将err替换为e
					replaceWith(err, e);
			} else { // 如果是isMany，但不是isUnique，直接添加e
				allTypedEdges.add(e);
				indexing(e);
			}
		} else { // 如果不是isMany

			boolean replaced = false; // 每次都初始化为false

			for (int i = 0; i < allTypedEdges.size(); i++) { // 新加只会新加的冲突
				TypedEdge tmp = allTypedEdges.get(i);
				if (tmp.getSource() == e.getSource() && tmp.getType() == e.getType()) {

					e.mergeIndex(tmp); // 如果不是isMany，但添加了多条边，会合并它们的索引集
					reindexing(e);

					if (replaced) { // reduce redundant, 感觉不会有此情况
						allTypedEdges.remove(i);
						i--;
						XmuCoreUtils.failure("Redundant edges " + tmp);
					} else {
						allTypedEdges.set(i, e); // 设置为后来的边对象
						replaced = true;
					}
				}
			}
			// 如果replaced是false，说明此单值边是第一次添加
			if (!replaced) {
				allTypedEdges.add(e);
				indexing(e);
			}
		}

		this.outgoingEdgeMap.remove(e.getSource());
		this.incomingEdgeMap.remove(e.getTarget());
	}

	// lyt: simply add
	public void simAddValueEdge(ValueEdge e) {
		allValueEdges.add(e);
		indexing(e);
	}

	// lyt: additional information: int start
	public void addValueEdge(ValueEdge e, int start) {
		if (e.getType().isMany()) {
			if (e.getType().isUnique()) {
				int i;
				for (i = start; i < allValueEdges.size(); i++) {
					ValueEdge tmp = allValueEdges.get(i);

					if (tmp.getType() == e.getType() && tmp.getSource().getIndex().equals(e.getSource().getIndex())
							&& tmp.getTarget().equals(e.getTarget())) { // 我有毒，将这里誊错：竟加了getIndex()
						e.mergeIndex(tmp);
						reindexing(e);
						allValueEdges.set(i, e);
						break;
					}
				}
				if (i == allValueEdges.size()) {
					allValueEdges.add(e);
					indexing(e);
				}

			} else {
				allValueEdges.add(e);
				indexing(e);
			}

		} else {
			boolean replaced = false;
			for (int i = start; i < allValueEdges.size(); i++) {
				ValueEdge tmp = allValueEdges.get(i);
				if (tmp.getSource() == e.getSource() && tmp.getType() == e.getType()) {

					e.mergeIndex(tmp);
					reindexing(e);

					if (replaced) { // reduce redundant
						allValueEdges.remove(i);
						i--;
						XmuCoreUtils.failure("Redundant edges " + tmp);
					} else {
						allValueEdges.set(i, e);
						replaced = true;
					}
				}
			}
			if (!replaced) { // 如果replace还是false
				allValueEdges.add(e);
				indexing(e);
			}
		}

		this.valueEdgeMap.remove(e.getSource());
		this.valueReferenceMap.remove(e.getTarget());
	}

	/** 在图中添加ValueEdge类型的边对象 */
	public void addValueEdge(ValueEdge e) {
		if (e.getType().isMany()) { // 如果e的PropertyEdge是isMany
			if (e.getType().isUnique()) { // 如果是isMany，并且还是isUnique
				ValueEdge err = null;
				try {
					err = allValueEdges.stream().filter(er -> {
						return er.getType() == e.getType() && er.getSource().getIndex().equals(e.getSource().getIndex())
								&& er.getTarget().equals(e.getTarget());
					}).findAny().get();
				} catch (Exception ex2) {
					allValueEdges.add(e);
					indexing(e);
				}
				if (err != null)
					replaceWith(err, e);
			} else { // 如果是isMany，但不是isUnique
				allValueEdges.add(e);
				indexing(e);
			}
		} else { // 如果不是isMany
			boolean replaced = false;

			for (int i = 0; i < allValueEdges.size(); i++) {
				ValueEdge tmp = allValueEdges.get(i);
				if (tmp.getSource() == e.getSource() && tmp.getType() == e.getType()) {

					e.mergeIndex(tmp);
					reindexing(e);

					if (replaced) { // reduce redundant
						allValueEdges.remove(i);
						i--;
						XmuCoreUtils.failure("Redundant edges " + tmp);
					} else {
						allValueEdges.set(i, e);
						replaced = true;
					}
				}
			}
			if (!replaced) { // 如果replace还是false
				allValueEdges.add(e);
				indexing(e);
			}
		}

		this.valueEdgeMap.remove(e.getSource());
		this.valueReferenceMap.remove(e.getTarget());
	}

	public TypedGraph(TypeGraph typeGraph) {
		super();
		this.typeGraph = typeGraph;
		init();
	}

	public TypedNode[] getTypedNodesOf(TypeNode type) {
		TypeGraph typeGraph = this.getTypeGraph();
		return allTypedNodes.stream().filter(n -> {
			return typeGraph.isSuperTypeOf(n.getType(), type);
		}).toArray(i -> new TypedNode[i]);
	}

	public ValueNode[] getValueNodesOf(DataTypeNode type) {
		TypeGraph typeGraph = this.getTypeGraph();
		return allValueNodes.stream().filter(n -> {
			return typeGraph.isSuperTypeOf(n.getType(), type);
		}).toArray(i -> new ValueNode[i]);
	}

	public TypedEdge[] getTypedEdgesOf(TypeEdge type) {
		return allTypedEdges.stream().filter(n -> {
			return n.getType() == type;
		}).toArray(i -> new TypedEdge[i]);
	}

	public ValueEdge[] getValueEdgesOf(PropertyEdge type) {
		return allValueEdges.stream().filter(n -> {
			return n.getType() == type;
		}).toArray(i -> new ValueEdge[i]);
	}

	@Derived
	public List<TypedEdge> getOutgoingEdges(TypedNode source) {
		List<TypedEdge> result = this.outgoingEdgeMap.get(source);
		if (result == null) {
			result = new ArrayList<TypedEdge>();
			this.outgoingEdgeMap.put(source, result);

			for (TypedEdge e : this.getAllTypedEdges()) {
				if (e.getSource() == source) {
					result.add(e);
				}
			}
		}

		return result;
	}

	@Derived
	public List<TypedEdge> getOutgoingEdges(TypedNode source, TypeEdge feature) {
		List<TypedEdge> result = new ArrayList<TypedEdge>();

		for (TypedEdge e : this.getOutgoingEdges(source)) {
			if (e.getType() == feature) {
				result.add(e);
			}
		}
		return result;
	}

	@Derived
	public List<TypedEdge> getOutgoingEdges(TypedNode source, TypeEdge feature, TypeNode targetType) {
		List<TypedEdge> result = new ArrayList<TypedEdge>();

		for (TypedEdge e : this.getOutgoingEdges(source)) {
			if (e.getType() == feature && (targetType == null || targetType.isSuperTypeOf(e.getTarget().getType()))) {
				result.add(e);
			}
		}
		return result;
	}

	@Derived
	public List<TypedEdge> getIncomingEdges(TypedNode target) {
		List<TypedEdge> result = this.incomingEdgeMap.get(target);
		if (result == null) {
			result = new ArrayList<TypedEdge>();
			this.incomingEdgeMap.put(target, result);

			for (TypedEdge e : this.getAllTypedEdges()) {
				if (e.getTarget() == target) {
					result.add(e);
				}
			}
		}
		return result;
	}

	@Derived
	public List<TypedEdge> getIncomingEdges(TypedNode target, TypeEdge feature) {
		List<TypedEdge> result = new ArrayList<TypedEdge>();

		for (TypedEdge e : this.getIncomingEdges(target)) {
			if (e.getType() == feature) {
				result.add(e);
			}
		}
		return result;
	}

	@Derived
	public List<TypedEdge> getIncomingEdges(TypedNode target, TypeEdge feature, TypeNode sourceType) {
		List<TypedEdge> result = new ArrayList<TypedEdge>();

		for (TypedEdge e : this.getIncomingEdges(target)) {
			if (e.getType() == feature && (sourceType == null || sourceType.isSuperTypeOf(e.getSource().getType()))) {
				result.add(e);
			}
		}
		return result;
	}

	@Derived
	public List<ValueEdge> getValueEdges(TypedNode source) {
		List<ValueEdge> result = this.valueEdgeMap.get(source); // 根据key(TypedNode)找到value(List<ValueEdge>)

		if (result == null) {
			result = new ArrayList<ValueEdge>();
			this.valueEdgeMap.put(source, result); // 如果没有找到，则将(source, result)添加到valueEdgeMap中

			for (ValueEdge e : this.getAllValueEdges()) {
				if (e.getSource() == source) {
					result.add(e);
				}
			}
		}
		return result;
	}

	public List<ValueEdge> getValueReferences(ValueNode target) {
		List<ValueEdge> result = this.valueReferenceMap.get(target);
		if (result == null) {
			result = new ArrayList<ValueEdge>();
			this.valueReferenceMap.put(target, result);

			for (ValueEdge e : this.getAllValueEdges()) {
				if (e.getTarget() == target) {
					result.add(e);
				}
			}
		}

		return result;
	}

	@Derived
	public List<ValueEdge> getValueEdges(TypedNode source, PropertyEdge feature) {
		List<ValueEdge> result = new ArrayList<ValueEdge>();

		for (ValueEdge e : this.getValueEdges(source)) { // 根据key(TypedNode)获取value(List<ValueEdge>)
			if (e.getType() == feature) {
				result.add(e); // 再将feature相符的添加到result中
			}
		}
		return result;
	}

	public List<ValueEdge> getValueEdgesTo(ValueNode target, PropertyEdge type, TypeNode sourceType) {
		List<ValueEdge> result = new ArrayList<ValueEdge>();
		for (ValueEdge e : this.getValueReferences(target)) {
			if (e.getType() == type && (sourceType == null || sourceType.isSuperTypeOf(e.getSource().getType()))) {
				result.add(e);
			}
		}
		return result;
	}

	@Derived
	List<? extends ITypedEdge> getFeature(TypedNode source, IStructuralFeatureEdge type) {
		if (type instanceof TypeEdge) {
			return this.getOutgoingEdges(source, (TypeEdge) type);
		} else if (type instanceof PropertyEdge) {
			return this.getValueEdges(source, (PropertyEdge) type);
		} else {
			return Collections.emptyList();
		}
	}

	public String printGraph() {
		StringBuilder builder = new StringBuilder();

		List<TypedNode> allTypedNodes = this.getAllTypedNodes();
		allTypedNodes.forEach(n -> {
			builder.append(n.toString());
			builder.append("{");
			this.getValueEdges(n).forEach(v -> {
				builder.append(v.toString());
				builder.append(",");
			});
			builder.append("}");
			builder.append("\n");
		});

		this.getAllTypedEdges().forEach(e -> {
			builder.append(e.toString());
			builder.append("\n");
		});

		return builder.toString();
	}

	public boolean conformanceCheck() {
		PairMap<ITypedNode, IStructuralFeatureEdge, Integer> counts = new PairMap<>();

		for (TypedNode n : this.getAllTypedNodes()) {
			if (n.getType().isAbstract())
				return false;
			if (!this.getTypeGraph().getAllTypeNodes().contains(n.getType()))
				return false;
		}

		for (ValueNode n : this.getAllValueNodes()) {
			if (!this.getTypeGraph().getAllDataTypeNodes().contains(n.getType()))
				return false;
		}

		for (TypedEdge e : this.getAllTypedEdges()) {
			TypeEdge type = e.getType();
			if (!this.getTypeGraph().getAllTypeEdges().contains(type))
				return false;
			if (!this.getTypeGraph().isSuperTypeOf(e.getSource().getType(), type.getSource()))
				return false;
			if (!this.getTypeGraph().isSuperTypeOf(e.getTarget().getType(), type.getTarget()))
				return false;

			Integer i = counts.get(e.getSource(), type);
			if (i == null)
				i = 0;
			counts.put(e.getSource(), type, i++);
			if (i > 1 && !type.isMany())
				return false;
		}

		for (ValueEdge e : this.getAllValueEdges()) {
			PropertyEdge type = e.getType();
			if (!this.getTypeGraph().getAllPropertyEdges().contains(type))
				return false;
			if (!this.getTypeGraph().isSuperTypeOf(e.getSource().getType(), type.getSource()))
				return false;
			if (!this.getTypeGraph().isSuperTypeOf(e.getTarget().getType(), type.getTarget()))
				return false;

			Integer i = counts.get(e.getSource(), type);
			if (i == null)
				i = 0;
			counts.put(e.getSource(), type, i++);
			if (i > 1 && !type.isMany())
				return false;
		}

		return true;
	}

	/** TypedGraph类型的图的复制 */
	public TypedGraph getCopy() {

		TypedGraph copy = new TypedGraph(this.typeGraph);
		// addAll(c: Collection<? extends E>): boolean 将集合c中的所有元素添加到该集合中。引用类型就复制对象的引用

		copy.getAllTypedNodes().addAll(this.getAllTypedNodes());
		copy.getAllValueNodes().addAll(this.getAllValueNodes());
		copy.getAllTypedEdges().addAll(this.getAllTypedEdges());
		copy.getAllValueEdges().addAll(this.getAllValueEdges());

		copy.indexToObjectMap.putAll(this.indexToObjectMap);

		copy.order = this.order.getCopy();

		copy.constraint = this.constraint;

		return copy;
	}

	public TypedGraph additiveMerge(TypedGraph graph) {

//		return BXMerge.additiveMerge(this, graph);

		TypedGraph result = this.getCopy();

		graph.getAllTypedNodes().forEach(n -> {
			try {
				TypedNode nr = result.getElementByIndexObject(n.getIndex());
				if (nr != n) {
					// 如果两个对象的地址不同，将nr替换为n
					result.replaceWith(nr, n);
				}
			} catch (NothingReturnedException e) { // 如果根据索引在result图中不能找到，说明是graph图中新添加的节点
				result.addTypedNode(n);
			}
		});

		graph.getAllValueNodes().forEach(n -> {
			result.addValueNode(n);
		});

		graph.getAllTypedEdges().forEach(e -> {
			try {
				TypedEdge er = result.getElementByIndexObject(e.getIndex());
				if (er != e) {
					result.replaceWith(er, e);
				}
			} catch (NothingReturnedException ex) {
				result.addTypedEdge(e);
			}
		});

		graph.getAllValueEdges().forEach(e -> {
			try {
				ValueEdge er = result.getElementByIndexObject(e.getIndex());
				if (er != e) {
					result.replaceWith(er, e);
				}
			} catch (NothingReturnedException ex) {
				result.addValueEdge(e);
			}
		});

		result.order.merge(graph.order);

		result.constraint = GraphConstraint.and(this.constraint, graph.constraint);
		// check

		return result;
	}

	// static public boolean isIdentifical(TypedGraph left, TypedGraph right) {
	// return false;
	// }

	public TypedGraph merge(TypedGraph... interSources) throws NothingReturnedException {

		// return BXMerge.merge(this, interSources);

		TypedGraph result = this.getCopy();

		// each typed node n in result, collect images in interSources (null means
		// deleted, Any means default, T means required to be changed to T type)
		// if all the images of n are compatible, (1) delete or (2) change to the common
		// sub-type

		TypeNode[] nodeImages = new TypeNode[interSources.length];
		for (TypedNode baseNode : this.getAllTypedNodes()) {
			for (int i = 0; i < interSources.length; i++) {
				nodeImages[i] = computeImage(baseNode, this, interSources[i]);
			}

			try {
				TypeNode finalType = computeType(nodeImages, this.getTypeGraph());

				if (finalType == TypeNode.NULL_TYPE) {
					result.remove(baseNode);
				} else {
					if (finalType == TypeNode.ANY_TYPE)
						finalType = baseNode.getType();

					TypedNode n = new TypedNode();
					n.setType(finalType);

					for (TypedGraph image : interSources) {
						n.mergeIndex(image.getElementByIndexObject(baseNode.getIndex()));
					}

					result.replaceWith(baseNode, n); // 参考BXMerge
				}

			} catch (NothingReturnedException e) {
				throw e;
			}
		}

		for (TypedGraph image : interSources) {
			image.getAllValueNodes().forEach(v -> {
				result.addValueNode(v);
			});
		}

		// each typed edge e in the result, collect images in interSources (null means
		// deleted, Any means default, <s,t>:T means required)
		TypedEdge[] typedEdgeImages = new TypedEdge[interSources.length];
		for (TypedEdge baseEdge : this.getAllTypedEdges()) {
			for (int i = 0; i < interSources.length; i++) {
				typedEdgeImages[i] = computeImage(baseEdge, this, interSources[i]);
			}
			try {
				Tuple3<TypedNode, TypedNode, TypeEdge> finalEdgeInfo = computeEdge(baseEdge, typedEdgeImages);
				if (finalEdgeInfo == null) {
					result.remove(baseEdge);
				} else {
					TypedNode source = result.getElementByIndexObject(finalEdgeInfo.first.getIndex());
					TypedNode target = result.getElementByIndexObject(finalEdgeInfo.second.getIndex());
					TypedEdge edge = new TypedEdge();
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

		// each typed edge e in the result, collect images in interSources (null means
		// deleted, Any means default, <s,t>:T means required)
		ValueEdge[] valueEdgeImages = new ValueEdge[interSources.length];
		for (ValueEdge baseEdge : this.getAllValueEdges()) {
			for (int i = 0; i < interSources.length; i++) {
				valueEdgeImages[i] = computeImage(baseEdge, this, interSources[i]);
			}
			try {
				Tuple3<TypedNode, ValueNode, PropertyEdge> finalEdgeInfo = computeEdge(baseEdge, valueEdgeImages);
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

		// add extra nodes and edges
		for (TypedGraph image : interSources) {

			for (TypedNode n : image.allTypedNodes) {
				try {
					this.getElementByIndexObject(n.getIndex()); // baseGraph去调用的merge方法，this就是baseGraph
				} catch (NothingReturnedException e) {
					TypedNode rn = null;
					try {
						rn = result.getElementByIndexObject(n.getIndex());
					} catch (NothingReturnedException e1) {
						result.addTypedNode(n);
						rn = null;
					} finally {
						if (rn != null) {
							TypeNode lt = rn.getType();
							TypeNode rt = n.getType();
							TypeNode ct = this.getTypeGraph().computeSubtype(lt, rt);
							if (ct == TypeNode.NULL_TYPE) {
								// incompatible
								throw new NothingReturnedException();
							} else {
								// TypedNode res = new TypedNode();
								// res.setType(ct);;
								// res.mergeIndices(rn);
								// res.mergeIndices(n);
								// result.replaceWith(rn, res);
								rn.mergeIndex(n);
								rn.setType(ct);
								reindexing(rn); // lyt-更改
							}
						}
					}
				}
			}

			for (TypedEdge e : image.allTypedEdges) {
				try {
					this.getElementByIndexObject(e.getIndex());
				} catch (Exception ex) {
					TypedEdge re = null;
					try {
						re = result.getElementByIndexObject(e.getIndex());
					} catch (Exception ex2) {
						TypedNode source = result.getElementByIndexObject(e.getSource().getIndex());
						TypedNode target = result.getElementByIndexObject(e.getTarget().getIndex());
						if (e.getSource() != source || e.getTarget() != target) {
							TypedEdge ne = new TypedEdge();
							ne.setSource(source);
							ne.setTarget(target);
							ne.setType(e.getType());
							ne.mergeIndex(e);
							result.addTypedEdge(ne);
						} else
							result.addTypedEdge(e);
						re = null;
					} finally {
						if (re != null) {
							if (re.getType() != e.getType()
									|| !re.getSource().getIndex().equals(e.getSource().getIndex())
									|| !re.getTarget().getIndex().equals(e.getTarget().getIndex())) {
								throw new NothingReturnedException();
							} else {
								re.mergeIndex(e);
								reindexing(re); // lyt
							}
						}
					}
				}
			}

			for (ValueEdge e : image.allValueEdges) {
				try {
					this.getElementByIndexObject(e.getIndex());
				} catch (Exception ex) {
					ValueEdge re = null;
					try {
						re = result.getElementByIndexObject(e.getIndex());
					} catch (Exception ex2) {
						TypedNode source = result.getElementByIndexObject(e.getSource().getIndex());
						// ValueNode target = e.getTarget();
						if (e.getSource() != source) {
							ValueEdge ne = new ValueEdge();
							ne.setSource(source);
							ne.setTarget(e.getTarget());
							ne.setType(e.getType());
							ne.mergeIndex(e);
							result.addValueEdge(ne);
						} else
							result.addValueEdge(e);
						// result.addValueEdge(e);
						re = null;
					} finally {
						if (re != null) {
							if (re.getType() != e.getType()
									|| !re.getSource().getIndex().equals(e.getSource().getIndex())
									|| re.getTarget().equals(e.getTarget()) == false) {
								throw new NothingReturnedException();
							} else {
								re.mergeIndex(e);
								reindexing(re); // lyt
							}
						}
					}
				}
			}
		}

		OrderInformation[] orders = new OrderInformation[interSources.length];
		for (int i = 0; i < interSources.length; i++)
			orders[i] = interSources[i].order;
		result.order.merge(orders);

		List<GraphConstraint> cons = new ArrayList<>();
		cons.add(this.getConstraint());
		for (TypedGraph g : interSources) {
			cons.add(g.constraint);
		}
		result.constraint = GraphConstraint.and(cons);
		// check

		return result;
	}

	// 判断两个元素是否相等
	static boolean isEqual(IndexableElement l, IndexableElement r) {
		return l == r || (l != null && l.isIndexable() && r.isIndexable() && l.getIndex().equals(r.getIndex()));
	}

	/** 将图中的TypedEdge边er替换为e */
	// lyt - 修改为了public，为了在TestThreeOrder里面使用
	public void replaceWith(TypedEdge er, TypedEdge e) {
		// 先找到allTypedEdges中的er对象，替换为e对象
		this.allTypedEdges.replaceAll(x -> isEqual(x, er) ? e : x);
		e.mergeIndex(er);
		reindexing(e);
	}

	/** 将图中的ValueEdge边er替换为e */
	// lyt-修改为了public，为了在AddressBookRunnerOrigin中使用
	public void replaceWith(ValueEdge er, ValueEdge e) {
		this.allValueEdges.replaceAll(x -> isEqual(x, er) ? e : x);
		e.mergeIndex(er);
		reindexing(e);
	}

	/** 将图中的TypedNode节点nr替换为n，注意还要考虑相邻的TypedEdge边和ValueEdge边 */
	// lyt - default修改成了public，为了在TestLogical_2中使用
	public void replaceWith(TypedNode nr, TypedNode n) {

		// 找到图的List<TypedNode>中的nr对象，替换为n对象
		this.allTypedNodes.replaceAll(e -> isEqual(e, nr) ? n : e);
		// 合并索引集
		n.mergeIndex(nr);
		// 更新图的indexToObjectMap
		reindexing(n);

		if (nr != n) {

			TypeNode nodeType = n.getType();
			// 处理相邻的TypedEdge类型的边
			List<TypedEdge> removedTypedEdges = new ArrayList<TypedEdge>();
			this.allTypedEdges.replaceAll(e -> { // 对于result图中List<TypedEdge>的每个元素e
				TypeEdge edgeType = e.getType(); // 先获取e的type
				// 再根据edgeType可以获得sourceType和targetType
				TypeNode sourceType = edgeType.getSource();
				TypeNode targetType = edgeType.getTarget();

				if (e.getSource() == nr && e.getTarget() == nr) { // 1.如果e的source和target都是nr节点
					// 并且n的type(nodeType)是e的sourceType、targetType的子类型
					if (this.typeGraph.isSuperTypeOf(nodeType, sourceType)
							&& this.typeGraph.isSuperTypeOf(nodeType, targetType)) {
						TypedEdge res = new TypedEdge();
						res.setSource(n);
						res.setTarget(n);
						res.setType(edgeType);
						res.mergeIndex(e);
						reindexing(res);
						return res; // 将e替换为res
					} else { // 并且n的type不是e的sourceType |(&) targetType的子类型
						removedTypedEdges.add(e); // 将e添加到removedTypedEdges集合中
						clearIndex(e); // 将元素e的索引集和e的映射关系，从indexToObjectMap中清除
						return e; // 这里先暂时e->e
					}
				} else if (e.getSource() == nr) { // 2. 仅e的source是nr，e的target不是nr
					// 并且n的type(nodeType)是e的sourceType的子类型
					if (this.typeGraph.isSuperTypeOf(nodeType, sourceType)) {
						TypedEdge res = new TypedEdge();
						res.setSource(n);
						res.setTarget(e.getTarget());
						res.setType(edgeType);
						res.mergeIndex(e);
						reindexing(res);
						return res;
					} else { // 并且n的type不是e的sourceType的子类型
						removedTypedEdges.add(e);
						clearIndex(e);
						return e;
					}
				} else if (e.getTarget() == nr) { // 3. 仅e的target是nr，e的source不是nr
					// 并且n的type(nodeType)是e的targetType的子类型
					if (this.typeGraph.isSuperTypeOf(nodeType, targetType)) {
						TypedEdge res = new TypedEdge();
						res.setSource(e.getSource());
						res.setTarget(n);
						res.setType(edgeType);
						res.mergeIndex(e);
						reindexing(res);
						return res;
					} else { // 并且n的type(nodeType)不是e的targetType的子类型
						removedTypedEdges.add(e);
						clearIndex(e);
						return e;
					}
				} else // 4. e的source不是nr、target也不是nr
					return e;
			});
			this.allTypedEdges.removeAll(removedTypedEdges); // 集合操作removeAll()：A <- A-B，即从A集合中删除B集合

			// 处理相邻的ValueEdge类型的边
			List<ValueEdge> removedValueEdges = new ArrayList<ValueEdge>();
			this.allValueEdges.replaceAll(e -> { // 对于result图中List<ValueEdge>的每一个边e
				PropertyEdge edgeType = e.getType();
				TypeNode sourceType = edgeType.getSource();

				if (e.getSource() == nr) { // 如果e的source是nr
					// 并且n的type是e的sourceType的子类型，则将e替换为res
					if (this.typeGraph.isSuperTypeOf(nodeType, sourceType)) {
						ValueEdge res = new ValueEdge();
						res.setSource(n);
						res.setTarget(e.getTarget());
						res.setType(edgeType);
						res.mergeIndex(e);
						reindexing(res);
						return res;
					} else {
						// 并且n的type与nr的type不兼容，则删除e
						removedValueEdges.add(e);
						clearIndex(e);
						return e;
					}
				} else
					return e; // 如果e的source不是nr，则保留
			});
			this.allValueEdges.removeAll(removedValueEdges);
		}

	}

	// lyt-先替换，之后再删除相邻边
	public void replaceWith2(TypedNode nr, TypedNode n) {
		// 找到图的List<TypedNode>中的nr对象，替换为n对象
		this.allTypedNodes.replaceAll(e -> isEqual(e, nr) ? n : e);
		// 合并索引集
		n.mergeIndex(nr);
		// 更新图的indexToObjectMap
		reindexing(n);

	}

//	static public boolean isIdentifical(TypedGraph left, TypedGraph right) {
//		return false;
//	}

	/**
	 * 再根据valueEdgeImages[]中的情况，确定finalEdgeInfo三元组 1)
	 * 返回baseEdge三元组信息：a==baseEdge并且b==baseEdge 2) 返回null：a==baseEdge并且b==null |
	 * a==null并且b==baseEdge | a==null并且b==null 3) 抛出异常：a==null并且b==imageEdge |
	 * a==imageEdge并且b==null | a==imageEdge并且b==imageEdge但不兼容 4)
	 * 返回imageEdge三元组信息：a==baseEdge并且b==imageEdge | a==imageEdge并且b==baseEdge |
	 * a==imageEdge并且b==imageEdge而且兼容
	 */
	static Tuple3<TypedNode, ValueNode, PropertyEdge> computeEdge(ValueEdge baseEdge, ValueEdge[] valueEdgeImages)
			throws NothingReturnedException {
		Tuple3<TypedNode, ValueNode, PropertyEdge> any = new Tuple3<TypedNode, ValueNode, PropertyEdge>(
				baseEdge.getSource(), baseEdge.getTarget(), baseEdge.getType());
		Tuple3<TypedNode, ValueNode, PropertyEdge> tuple = any; // 初始化为baseEdge的信息

		for (ValueEdge e : valueEdgeImages) {
			if (e == baseEdge)
				continue;
			else if (e == null) {
				if (tuple == null || isImage(tuple, baseEdge))
					tuple = null;
				else
					throw new NothingReturnedException("删除修改冲突"); // incompatible: a==imageEdge并且b==null
			} else {

				if (tuple == null)
					throw new NothingReturnedException("删除修改冲突"); // incompatible: a==null并且b==imageEdge
				else if (isImage(tuple, baseEdge))
					tuple = new Tuple3<TypedNode, ValueNode, PropertyEdge>(e.getSource(), e.getTarget(), e.getType());
				else {

					if (isImage(tuple, e)) // a==imageEdge并且b==imageEdge时，判断是否兼容
						continue;
					else {
						throw new NothingReturnedException("都修改冲突"); // incompatible: a==imageEdge并且b==imageEdge而且不兼容
					}
				}
			}
		}

		return tuple;
	}

	/** 判断两个ValueEdge是否兼容，三元组tuple是第一个ValueEdge的信息 */
	static private boolean isImage(Tuple3<TypedNode, ValueNode, PropertyEdge> tuple, ValueEdge e) {
		return tuple.third == e.getType() && tuple.first.getIndex().equals(e.getSource().getIndex()) // 看TypedNode类型对象的index是否有交集
				&& tuple.second.equals(e.getTarget()); // 看ValueNode类型对象是否为同一对象
	}

	/** 两个分支图先和基本图作比较，baseEdge的情况分别存储在imageGraph[]中，可能是null、baseEdge、imageEdge */
	static ValueEdge computeImage(ValueEdge baseEdge, TypedGraph baseGraph, TypedGraph imageGraph) {
		try {
			// 找到了
			ValueEdge imageEdge = imageGraph.getElementByIndexObject(baseEdge.getIndex());
			if (imageEdge.getType() == baseEdge.getType()
					&& imageEdge.getSource().getIndex().equals(baseEdge.getSource().getIndex())
					&& imageEdge.getTarget().equals(baseEdge.getTarget()))
				return baseEdge;
			else
				return imageEdge;

		} catch (Exception e) { // 没找到，说明在分支图中被删了
			return null;
		}
	}

	/** 在图中删除TypedNode类型的节点 */
	public void remove(TypedNode n) {

		// 找到List<TypedNodes>中与索引集相交的点，删除
		this.allTypedNodes.removeIf(x -> isEqual(x, n));
		this.clearIndex(n);

		// 如果此节点还是某条TypedEdge类型边e的source或target端点，删除这条边e
		for (int i = this.allTypedEdges.size() - 1; i >= 0; i--) {
			TypedEdge e = this.allTypedEdges.get(i);
			if (isEqual(e.getSource(), n) || isEqual(e.getTarget(), n)) {
				this.allTypedEdges.remove(i);
				this.clearIndex(e);
			}
		}

		// 如果此节点还是某条ValueEdge类型边e的source端点，删除这条边e
		for (int i = this.allValueEdges.size() - 1; i >= 0; i--) {
			ValueEdge e = this.allValueEdges.get(i);
			if (isEqual(e.getSource(), n)) {
				this.allValueEdges.remove(i);
				this.clearIndex(e);
			}
		}

	}

	/** lyt-之后再删除相邻边 */
	public void remove2(TypedNode n) {
		// 找到List<TypedNodes>中与索引集相交的点，删除
		this.allTypedNodes.removeIf(x -> isEqual(x, n));
		this.clearIndex(n);

	}

	/** 删除图中的TypedEdge类型边 */
	public void remove(TypedEdge baseEdge) {
		this.allTypedEdges.removeIf(x -> isEqual(x, baseEdge));
		this.clearIndex(baseEdge);
		// compute pre-deleted elements when baseEdge is containment, or not?
	}

	/** 删除图中的ValueEdge类型边 */
	public void remove(ValueEdge baseEdge) {
		this.allValueEdges.removeIf(x -> isEqual(x, baseEdge));
		this.clearIndex(baseEdge);
	}

	/**
	 * 再根据typedEdgeImages中的情况，确定finalEdgeInfo三元组信息
	 * 1)返回baseEdge三元组信息：a==baseEdge并且b==baseEdge 2) 返回null：a==baseEdge并且b==null
	 * |a==null并且b==baseEdge | a==null并且b==null 3) 抛出异常：a==null并且b==imageEdge |
	 * a==imageEdge并且b==null | a==imageEdge并且b==imageEdge但不兼容 4)
	 * 返回imageEdge三元组信息：a==baseEdge并且b==imageEdge | a==imageEdge并且b==baseEdge
	 * |a==imageEdge并且b==imageEdge而且兼容
	 */
	static Tuple3<TypedNode, TypedNode, TypeEdge> computeEdge(TypedEdge baseEdge, TypedEdge[] typedEdgeImages)
			throws NothingReturnedException {

		Tuple3<TypedNode, TypedNode, TypeEdge> any = new Tuple3<TypedNode, TypedNode, TypeEdge>(baseEdge.getSource(),
				baseEdge.getTarget(), baseEdge.getType());
		Tuple3<TypedNode, TypedNode, TypeEdge> tuple = any; // 结果初始化为baseEdge的信息

		for (TypedEdge e : typedEdgeImages) {

			if (e == baseEdge) // 没被替换，对象相同
				continue;
			else if (e == null) {
				if (tuple == null || isImage(tuple, baseEdge))
					tuple = null;
				else
					throw new NothingReturnedException(); // incompatible: a==imageEdge并且b==null
			} else { // 被替换了，对象不同

				if (tuple == null)
					throw new NothingReturnedException(); // incompatible: a==null并且b==imageEdge
				else if (isImage(tuple, baseEdge)) {
					tuple = new Tuple3<TypedNode, TypedNode, TypeEdge>(e.getSource(), e.getTarget(), e.getType());
				} else {
					if (isImage(tuple, e)) // a==imageEdge并且b==imageEdge时，如果兼容则跳过
						continue;
					else {
						throw new NothingReturnedException("都修改冲突"); // incompatible: a==imageEdge并且b==imageEdge，但不兼容
					}
				}
			}
		}

		return tuple;
	}

	/** 比较两个TypedEdge是否兼容，tuple是第一条边的三元组信息 */
	static private boolean isImage(Tuple3<TypedNode, TypedNode, TypeEdge> tuple, TypedEdge e) {
		return tuple.third == e.getType() && tuple.first.getIndex().equals(e.getSource().getIndex())
				&& tuple.second.getIndex().equals(e.getTarget().getIndex());
	}

	/**
	 * 两个分支图先分别和基本图作比较，baseEdge的情况分别存储在typedEdgeImages[i]中，可能是baseEdge、imageEdge、null
	 */
	static TypedEdge computeImage(TypedEdge baseEdge, TypedGraph baseGraph, TypedGraph imageGraph) {
		try {
			// 根据baseEdge的索引集在分支图中找到了
			TypedEdge imageEdge = imageGraph.getElementByIndexObject(baseEdge.getIndex());
			// 如果baseEdge在分支图和基本图中一致(type & source & target)，则返回baseEdge
			if (imageEdge.getType() == baseEdge.getType()
					&& imageEdge.getSource().getIndex().equals(baseEdge.getSource().getIndex())
					&& imageEdge.getTarget().getIndex().equals(baseEdge.getTarget().getIndex()))
				return baseEdge;
			else {
				return imageEdge; // 如果不一致，则返回修改后的imageEdge
			}

		} catch (Exception e) { // 根据baseEdge的索引在分支图中没有找到，说明被删了，返回null
			return null;
		}
	}

	/**
	 * 
	 * 比如调用：TypeNode finalType = TypedGraph.computeType(nodeImages,
	 * first.getTypeGraph())
	 * 当两个分支和base比较完后，再根据两分支baseNode的情况（NULL、ANY、修改后的类型）确定finalType; 1)返回ANY：a、b是ANY
	 * 2)返回NULL：a是ANY、b是NULL | a是NULL、b是ANY | a是NULL、b是NULL 3)抛出异常：a是NULL、b改了 |
	 * a改了、b是NULL | a改了b也改了，但不兼容 4)返回修改后的类型：a是ANY、b改了 | a改了、b是ANY | a改了b也改了，但兼容
	 */
	static TypeNode computeType(TypeNode[] images, TypeGraph typeGraph) throws NothingReturnedException {
		TypeNode finalType = TypeNode.ANY_TYPE;

		for (TypeNode n : images) {
			if (n == TypeNode.ANY_TYPE)
				continue;
			else if (n == TypeNode.NULL_TYPE) {
				if (finalType == TypeNode.ANY_TYPE || finalType == TypeNode.NULL_TYPE)
					finalType = TypeNode.NULL_TYPE;
				else
					throw new NothingReturnedException(); // incompatibe：a改了、b是NULL
			} else {
				if (finalType == TypeNode.NULL_TYPE)
					throw new NothingReturnedException(); // incompatible：a是NULL、b改了
				else if (finalType == TypeNode.ANY_TYPE)
					finalType = n;
				else {
					// a相对于base改了，b相对于base也改了。第一次finalType是a中的类型，第二次会进入此else语句块，确定finalType最终的类型。
					finalType = typeGraph.computeSubtype(finalType, n);
					if (finalType == TypeNode.NULL_TYPE)
						throw new NothingReturnedException("类型不兼容");
				}

			}
		}
		return finalType;
	}

	/** 节点在分支图和基本图中的比较，可能返回imageNode的type、ANY、NULL */
	static TypeNode computeImage(TypedNode baseNode, TypedGraph baseGraph, TypedGraph imageGraph) {
		try {
			// 在分支图中根据索引查找对应的baseNode，如果找到则赋值给imageNode
			TypedNode imageNode = imageGraph.getElementByIndexObject(baseNode.getIndex());

			// 如果此节点在baseGraph和imageGraph中的类型不一致，则返回imageNode
			if (imageNode.getType() != baseNode.getType())
				return imageNode.getType();
			else { // 如果此节点在baseGraph和imageGraph中的类型一致，则返回ANY
//				if (isTouched(imageNode, imageGraph, baseNode, baseGraph)) { // lyt-可以省略？
//					return imageNode.getType();
//				} else
//					return TypeNode.ANY_TYPE;

				return TypeNode.ANY_TYPE;
			}

		} catch (NothingReturnedException e) {
			return TypeNode.NULL_TYPE; // imageGraph中没有找到相应的baseNode，则返回NULL_TYPE给nodeImage[0]
		}
	}

	/** 可以省略？？ */
	private static boolean isTouched(TypedNode imageNode, TypedGraph imageGraph, TypedNode baseNode,
			TypedGraph baseGraph) {
		// may be omitted

		try {
			List<TypedEdge> imageEdges = imageGraph.getOutgoingEdges(imageNode);
			List<TypedEdge> baseEdges = baseGraph.getOutgoingEdges(baseNode);

			if (imageEdges.size() > baseEdges.size())
				return true;
			for (TypedEdge imageEdge : imageEdges) {
				if (baseEdges.stream().noneMatch(baseEdge -> {
					TypedNode imageTarget = imageEdge.getTarget();
					TypedNode baseTarget = baseEdge.getTarget();

					if (imageEdge.getType() == baseEdge.getType()) {
						if (!baseTarget.getIndex().equals(imageTarget.getIndex()))
							return false; // target inconsistent
						if (imageEdge.getType().isUnique()) {
							return baseEdge.getIndex().equals(imageEdge.getIndex()); // for a non-unique reference, the
																						// edge index determines the
																						// result
						} else
							return true; // for a unique reference, the edge index is determined by the source and
											// target indices
					} else {
						return false; // type inconsistent
					}
				}))
					return true; // we found that imageEdge is a new edge
			} // end for
		} catch (Exception e) {
			return true;
		}

		try {
			List<ValueEdge> imageEdges = imageGraph.getValueEdges(imageNode);
			List<ValueEdge> baseEdges = baseGraph.getValueEdges(baseNode);

			if (imageEdges.size() > baseEdges.size())
				return true;

			for (ValueEdge imageEdge : imageEdges) {
				if (baseEdges.stream().noneMatch(baseEdge -> {
					ValueNode imageTarget = imageEdge.getTarget();
					ValueNode baseTarget = baseEdge.getTarget();

					if (imageEdge.getType() == baseEdge.getType()) {
						if (baseTarget != imageTarget)
							return false; // target inconsistent
						if (imageEdge.getType().isUnique()) {
							return baseEdge.getIndex().equals(imageEdge.getIndex()); // for a non-unique reference, the
																						// edge index determines the
																						// result
						} else
							return true; // for a unique reference, the edge index is determined by the source and
											// target indices
					} else {
						return false; // type inconsistent
					}
				}))
					return true; // we found that imageEdge is a new edge
			} // end for
		} catch (Exception e) {
			return true;
		}

		return false;
	}

	public void setConstraint(GraphConstraint c) {
		this.constraint = c;
	}

	public GraphConstraint getConstraint() {
		return this.constraint;
	}

	/** 用于buildTypedGraph */
	public void declare(String graphString) {
		// 1. split by ';'
		// 2. match by node/edge formats

		Map<String, TypedNode> map = new HashMap<>();

		String[] statements = graphString.split(";");
		String nodeDeclPattern = "\\s*(\\w+)\\s*:\\s*(\\w+)\\s*";
		String propDeclPattern = "\\s*(\\w+)\\s*\\.\\s*(\\w+)\\s*=\\s*(\\d+|true|false|\".*\")\\s*";
		String edgeDeclPattern = "\\s*(\\w+)\\s*\\-\\s*(\\w+)\\s*->\\s*(\\w+)\\s*";

		Pattern ndp = Pattern.compile(nodeDeclPattern);
		Pattern pdp = Pattern.compile(propDeclPattern);
		Pattern edp = Pattern.compile(edgeDeclPattern);

		Matcher matcher = null;

		for (String stat : statements) {
			if ((matcher = ndp.matcher(stat)).matches()) {
				String nodeName = matcher.group(1);
				String typeName = matcher.group(2);

				TypeNode type = this.typeGraph.getTypeNode(typeName);

				TypedNode n = new TypedNode();
				n.setType(type);
				this.addTypedNode(n);
				map.put(nodeName, n);
			} else if ((matcher = pdp.matcher(stat)).matches()) {
				String sourceName = matcher.group(1);
				String featureName = matcher.group(2);
				String targetValue = matcher.group(3);

				TypedNode sn = (TypedNode) map.get(sourceName);
				PropertyEdge feature = this.typeGraph.getPropertyEdge(sn.getType(), featureName);
				Object value = null;

				if (targetValue.equals("true") || targetValue.equals("false")) {
					value = Boolean.parseBoolean(targetValue);
				} else if (targetValue.startsWith("\"")) {
					value = targetValue.substring(1, targetValue.length() - 1);
				} else
					value = Integer.parseInt(targetValue);

				ValueNode n = ValueNode.createConstantNode(value, feature.getTarget());
				this.addValueNode(n);

				ValueEdge e = new ValueEdge();
				e.setSource(sn);
				e.setTarget(n);
				e.setType(feature);
				this.addValueEdge(e);
			} else if ((matcher = edp.matcher(stat)).matches()) {
				String sourceName = matcher.group(1);
				String featureName = matcher.group(2);
				String targetName = matcher.group(3);

				TypedNode sn = (TypedNode) map.get(sourceName);
				TypedNode tn = (TypedNode) map.get(targetName);
				TypeEdge feature = this.typeGraph.getTypeEdge(sn.getType(), featureName);

				TypedEdge e = new TypedEdge();
				e.setSource(sn);
				e.setTarget(tn);
				e.setType(feature);

				this.addTypedEdge(e);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void enforceOrder() throws NothingReturnedException {
		List<? extends ITypedEdge> results = null;

		results = reorderEdges(this.allTypedEdges);
		this.allTypedEdges = (List<TypedEdge>) results;

		results = reorderEdges(this.allValueEdges);
		this.allValueEdges = (List<ValueEdge>) results;
	}

	private List<? extends ITypedEdge> reorderEdges(List<? extends ITypedEdge> edges) throws NothingReturnedException {
		List<Index> indices = edges.stream().map(e -> ((IndexableElement) e).getIndex()).collect(Collectors.toList());

		Index[] orderedIndices = this.order.planOrder(indices);

		List<? extends ITypedEdge> results = new ArrayList<>();

		for (Index i : orderedIndices) {
			results.add(this.getElementByIndexObject(i));
		}
		return results;
	}

}
