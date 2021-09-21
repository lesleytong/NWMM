package edu.ustb.sei.mde.bxcore.structures;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import edu.ustb.sei.mde.bxcore.SourceType;
import edu.ustb.sei.mde.bxcore.ViewType;
import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.bxcore.exceptions.UninitializedException;
import edu.ustb.sei.mde.graph.pattern.Pattern;
import edu.ustb.sei.mde.graph.type.DataTypeNode;
import edu.ustb.sei.mde.graph.type.PropertyEdge;
import edu.ustb.sei.mde.graph.type.TypeEdge;
import edu.ustb.sei.mde.graph.type.TypeGraph;
import edu.ustb.sei.mde.graph.type.TypeNode;
import edu.ustb.sei.mde.graph.typedGraph.IndexableElement;
import edu.ustb.sei.mde.graph.typedGraph.TypedEdge;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;
import edu.ustb.sei.mde.graph.typedGraph.TypedNode;
import edu.ustb.sei.mde.graph.typedGraph.ValueEdge;
import edu.ustb.sei.mde.graph.typedGraph.ValueNode;
import edu.ustb.sei.mde.structure.Tuple2;

public interface ContextGraph {
	class ContextGraphImpl implements ContextGraph {
		public ContextGraphImpl(TypedGraph graph, Context context) {
			super();
			this.graph = graph;
			this.context = context;
		}
		private Context context;
		private TypedGraph graph;
		
		@Override
		public Context getContext() {
			return context;
		}
		
		@Override
		public TypedGraph getGraph() {
			return graph;
		}
	}
	
	default ContextGraph replaceContext(Context c) {
		if(this instanceof SourceType)
			return ((SourceType)this).replaceSecond(c);
		else if(this instanceof ViewType)
			return ((ViewType)this).replaceSecond(c);
		else if(this instanceof ContextGraphImpl) {
			((ContextGraphImpl)this).context = c;
			return this;
		} else {
			return makeContextGraph(getGraph(), c);
		}
	}
	
	static public ContextGraph makeContextGraph(TypedGraph g, Context c) {
		return new ContextGraphImpl(g, c);
	}
	
	default Context getContext() {
		if(this instanceof SourceType)
			return ((SourceType)this).second;
		else return 
				((ViewType)this).second;
	}
	default TypedGraph getGraph() {
		if(this instanceof SourceType)
			return ((SourceType)this).first;
		else return 
				((ViewType)this).first;
	}
	
	default GraphModification modification() {
		if(this instanceof SourceType) {
			SourceType updated = ((SourceType)this).replaceFirst(((SourceType)this).first.getCopy());
			GraphModification modify = new GraphModification(updated);
			return modify;
		} else {
			return null;
		}
	}
	
	static public TypedNode newTypedNode(ContextGraph graph, String typeName) {
		TypeNode type = typeName!=null ? graph.getTypeNode(typeName) : null;
		TypedNode node;
		if(type==null)
			node = new TypedNode(TypeNode.ANY_TYPE);
		else node = new TypedNode(type);
		
		node.autoAppendIndex();
		return node;
	}
	
	static public TypedEdge newTypedEdge(ContextGraph graph, String typeName, String featureName) {
		TypeNode type = typeName!=null? graph.getTypeNode(typeName) : null; 
		TypeEdge feature = type!=null && featureName!=null ? graph.getTypeEdge(type, featureName) : null;
		
		TypedEdge edge;
		
		if(feature==null) edge = new TypedEdge();
		else edge = new TypedEdge(null, null, feature);
		
		edge.autoAppendIndex();
		return edge;
	}
	
	static public TypedEdge newTypedEdge(ContextGraph graph, String typeName, String featureName, TypedNode source, TypedNode target) {
		TypeNode type = typeName!=null? graph.getTypeNode(typeName) : null;
		TypeEdge feature = type!=null && featureName!=null ? graph.getTypeEdge(type, featureName) : null;
		
		TypedEdge edge;
		
		if(feature==null) edge = new TypedEdge();
		else edge = new TypedEdge(source, target, feature);
		
		edge.autoAppendIndex();
		return edge;
	}
	
	static public ValueEdge newValueEdge(ContextGraph graph, String typeName, String featureName) {
		TypeNode type = typeName!=null? graph.getTypeNode(typeName) : null;
		PropertyEdge feature = type!=null && featureName!=null ? graph.getPropertyEdge(type, featureName) : null;
		
		ValueEdge edge;
		
		if(feature==null) edge = new ValueEdge();
		else edge = new ValueEdge(null, null, feature);
		
		edge.autoAppendIndex();
		return edge;
	}
	
	static public ValueEdge newValueEdge(ContextGraph graph, String typeName, String featureName, TypedNode source, Object target) {
		TypeNode type = typeName!=null? graph.getTypeNode(typeName) : null;
		PropertyEdge feature = type!=null && featureName!=null ? graph.getPropertyEdge(type, featureName) : null;
		
		ValueEdge edge;
		
		if(feature==null) edge = new ValueEdge();
		else {
			ValueNode value = null;
			if(target instanceof ValueNode) value = (ValueNode) target;
			else value = ValueNode.createConstantNode(target, feature.getTarget());
			
			edge = new ValueEdge(source, value, feature);
		}
		
		edge.autoAppendIndex();
		return edge;
	}
	
	default GraphModification enforce(Pattern pat, Tuple2<String, Object>[] valueMaps) {
		if(this instanceof SourceType) {
			TypedGraph delta;
			try {
				Context freshContext = pat.getType().createInstance();
				for(FieldDef<?> f : freshContext.getType().fields()) {
					FieldDef<?> up = this.getContext().getType().getField(f.getName());
					if(up==null) {
						Tuple2<String, Object> tuple = null;
						for(Tuple2<String, Object> t : valueMaps) {
							if(t.first.equals(f.getName())) {
								tuple = t;
								break;
							}
						}
						if(tuple==null) {
							freshContext.setValue(f, f.getDefaultValue(true));
//							if(f.isCollection()) {
//								freshContext.setValue(f, new ArrayList<>());
//							} else {
//								if(f.isElementType()) {
//									freshContext.setValue(f, IndexSystem.generateUUID());
//								} else {
//									freshContext.setValue(f, XmuCoreUtils.defaultValue(f.getType()));
//								}
//							}
						} else {
							freshContext.setValue(f, tuple.second);
						}
					} else {
						freshContext.setValue(f, this.getContext().getValue(up));
					}
				}
				
				delta = pat.construct(this.getGraph(), freshContext);
				SourceType updated = SourceType.makeSource(((SourceType)this).first.additiveMerge(delta),
						freshContext, ((SourceType)this).third); 
				GraphModification modify = new GraphModification(updated);
				return modify;
			} catch (UninitializedException | NothingReturnedException e) {
				return null;
			}
		} else {
			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	default <T extends IndexableElement> T get(String name) {
		Context con = getContext();
		TypedGraph graph = getGraph();
		try {
			Object value = con.getValue(name);
			if(value instanceof Index) {
				IndexableElement e = graph.getElementByIndexObject((Index)value);
				return (T) e;
			} else {
				return (T) value;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	static Tuple2<String, Object> search(FieldDef<?> f, Tuple2<String, Object>[] valueMaps) {
		for(Tuple2<String, Object> t : valueMaps) {
			if(t.first.equals(f.getName())) {
				return t;
			}
		}
		return null;
	}
	
	default List<Context> match(Pattern pat, Tuple2<String, Object>[] valueMaps) {
		ContextType baseType = new ContextType();
		getContext().getType().fields().forEach(f->baseType.addField(f));
		Arrays.stream(valueMaps).forEach(t->baseType.addField(pat.getType().getField(t.first)));
		Context freshContext = new Context(baseType);
		for(FieldDef<?> f : baseType.fields()) {					
			Tuple2<String, Object> tuple = search(f, valueMaps);
			if(tuple==null) {
				try {
					FieldDef<?> up = getContext().getType().getField(f.getName());
					if (up != null) {
						freshContext.setValue(f, getContext().getValue(up));
					}
				} catch (UninitializedException | NothingReturnedException e) {
				}
			} else { // if tuple!=null, user must have provided a value
				freshContext.setValue(f, tuple.second);
			}
		}
		List<Context> matches = pat.match(getGraph(), freshContext);
		return matches;
	}
	
	default boolean hasMatch(Pattern pat, Tuple2<String, Object>[] valueMaps) {
		return !match(pat, valueMaps).isEmpty();
	}
	
	default boolean hasUniqueMatch(Pattern pat, Tuple2<String, Object>[] valueMaps) {
		return match(pat, valueMaps).size()==1;
	}
	
	default TypeNode getTypeNode(String name) {
		TypedGraph graph = this.getGraph();
		TypeGraph type = graph.getTypeGraph();
		return type.getTypeNode(name);
	}
	
	default DataTypeNode getDataTypeNode(String name) {
		TypedGraph graph = this.getGraph();
		TypeGraph type = graph.getTypeGraph();
		return type.getDataTypeNode(name);
	}

	default TypeEdge getTypeEdge(TypeNode node, String name) {
		TypedGraph graph = this.getGraph();
		TypeGraph type = graph.getTypeGraph();
		return type.getTypeEdge(node, name);
	}
	
	default PropertyEdge getPropertyEdge(TypeNode node, String name) {
		TypedGraph graph = this.getGraph();
		TypeGraph type = graph.getTypeGraph();
		return type.getPropertyEdge(node, name);
	}
	
	default public List<TypedNode> allTypedNodes(String typeName) {
		TypeNode type = this.getTypeNode(typeName);
		if(type==null) return Collections.emptyList();
		else {
			return Arrays.asList(this.getGraph().getTypedNodesOf(type));
		}
	}
	
	default public List<TypedEdge> allTypedEdges(String typeName, String featureName) {
		TypeNode type = this.getTypeNode(typeName);
		if(type==null) return Collections.emptyList();
		else {
			TypeEdge feature = this.getTypeEdge(type, featureName);
			if(feature==null) return Collections.emptyList();
			return this.getGraph().getAllTypedEdges().stream().filter(e->e.getType()==feature).collect(Collectors.toList());
		}
	}
	
	default public List<ValueEdge> allValueEdges(String typeName, String featureName) {
		TypeNode type = this.getTypeNode(typeName);
		if(type==null) return Collections.emptyList();
		else {
			PropertyEdge feature = this.getPropertyEdge(type, featureName);
			if(feature==null) return Collections.emptyList();
			return this.getGraph().getAllValueEdges().stream().filter(e->e.getType()==feature).collect(Collectors.toList());
		}
	}
}
