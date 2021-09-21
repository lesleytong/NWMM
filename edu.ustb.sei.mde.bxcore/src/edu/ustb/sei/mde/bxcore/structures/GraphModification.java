package edu.ustb.sei.mde.bxcore.structures;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ustb.sei.mde.bxcore.SourceType;
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

public class GraphModification {
	public GraphModification(ContextGraph data) {
		super();
		this.data = data;
		this.varMap = new HashMap<String, Object>();
	}

	private ContextGraph data;
	private Map<String, Object> varMap;
	
	public GraphModification addTypedNodeAs(String id, String type) {
		TypedGraph graph = data.getGraph();
		TypeGraph typeGraph = graph.getTypeGraph();
		Context context = data.getContext();
		
		TypeNode tn = typeGraph.getTypeNode(type);
		TypedNode node = new TypedNode(tn);
		
		Index idx = null;
		
		try {
			idx = (Index) context.getValue(id);
		} catch (Exception e) {
		}
		
		if(idx!=null) node.appendIndex(idx);
		graph.addTypedNode(node);
		varMap.put(id, node);
		
		return this;
	}
	public GraphModification addTypedEdge(String sid, String tid, String edge) {
		return addTypedEdge(null, sid, tid, edge);
	}
	
	public GraphModification addTypedEdge(String eid, String sid, String tid, String edge) {
		TypedNode src = (TypedNode) varMap.get(sid);
		TypedNode tar = (TypedNode) varMap.get(tid);
		
		TypedGraph graph = data.getGraph();
		TypeGraph typeGraph = graph.getTypeGraph();
		
		TypeEdge te = typeGraph.getTypeEdge(src.getType(), edge);
		TypedEdge l = new TypedEdge(src, tar, te);

		Context context = data.getContext();
		Index ind = null;
		
		if(eid!=null) {
			try {
				ind = (Index) context.getValue(eid);
			} catch (Exception e) {
			}
		}
		
		if(ind!=null) l.appendIndex(ind);
		
		graph.addTypedEdge(l);
		
		return this;
	}
	
	public GraphModification addValueNodeAs(String id, Object value, String type) {
		TypedGraph graph = data.getGraph();
		TypeGraph typeGraph = graph.getTypeGraph();
		DataTypeNode tn = typeGraph.getDataTypeNode(type);
		ValueNode node = ValueNode.createConstantNode(value, tn);
		graph.addValueNode(node);
		varMap.put(id, node);
		return this;
	}
	
	public GraphModification insertValueNode(ValueNode node) {
		if(node==null || node.getType()==null) {
			throw new RuntimeException("Cannot insert an invalid node");
		}
		data.getGraph().addValueNode(node);
		return this;
	}
	
	public GraphModification insertValueNode(List<ValueNode> nodes) {
		for(ValueNode n : nodes)
			insertValueNode(n);
		return this;
	}
	
	public GraphModification insertTypedNode(TypedNode node) {
		if(node==null || node.getType()==null || node.getType()==TypeNode.ANY_TYPE) {
			throw new RuntimeException("Cannot insert an invalid node");
		}
		data.getGraph().addTypedNode(node);
		return this;
	}
	
	public GraphModification insertTypedNode(List<TypedNode> nodes) {
		for(TypedNode n : nodes)
			insertTypedNode(n);
		return this;
	}
	
	public GraphModification insertTypedEdge(TypedEdge edge) {
		if(edge==null || edge.getType()==null || edge.getSource()==null || edge.getTarget()==null) {
			throw new RuntimeException("Cannot insert an invalid edge");
		}
		data.getGraph().addTypedEdge(edge);
		return this;
	}
	
	public GraphModification insertTypedEdge(List<TypedEdge> edges) {
		for(TypedEdge e : edges) {
			insertTypedEdge(e);
		}
		return this;
	}
	
	public GraphModification insertTypedEdgeBefore(TypedEdge edge, TypedEdge anchor) {
		insertTypedEdge(edge);

		List<TypedEdge> allTypedEdges = data.getGraph().getAllTypedEdges();
		
		int ancPos = data.getGraph().indexOf(allTypedEdges, anchor);
		int edgePos = data.getGraph().indexOf(allTypedEdges, edge);
		
		if(edgePos>ancPos) 
			data.getGraph().moveTypedEdgeTo(edgePos, ancPos);
		
		this.data.getGraph().getOrder().add(edge.getIndex(), anchor.getIndex());
		
		return this;
	}
	
	public GraphModification insertTypedEdgeAfter(TypedEdge edge, TypedEdge anchor) {
		insertTypedEdge(edge);

		List<TypedEdge> allTypedEdges = data.getGraph().getAllTypedEdges();
		
		int ancPos = data.getGraph().indexOf(allTypedEdges, anchor);
		int edgePos = data.getGraph().indexOf(allTypedEdges, edge);
		
		if(edgePos<ancPos) 
			data.getGraph().moveTypedEdgeTo(edgePos, ancPos);
		
		this.data.getGraph().getOrder().add(anchor.getIndex(), edge.getIndex());
		
		return this;
	}
	
	public GraphModification insertTypedEdgeFirst(TypedEdge edge) {
		insertTypedEdge(edge);

		List<TypedEdge> allTypedEdges = data.getGraph().getAllTypedEdges();
		
		int edgePos = data.getGraph().indexOf(allTypedEdges, edge);
		
		if(edgePos>0) 
			data.getGraph().moveTypedEdgeTo(edgePos, 0);
		
		allTypedEdges.forEach(e->{
			if(e!=edge)
				this.data.getGraph().getOrder().add(edge.getIndex(), e.getIndex());			
		});
		
		return this;
	}
	
	public GraphModification insertValueEdge(ValueEdge edge) {
		if(edge==null || edge.getType()==null || edge.getSource()==null || edge.getTarget()==null) {
			throw new RuntimeException("Cannot insert an invalid edge");
		}
		data.getGraph().addValueEdge(edge);
		return this;
	}
	
	public GraphModification insertValueEdge(List<ValueEdge> edges) {
		for(ValueEdge e : edges) {
			insertValueEdge(e);
		}
		return this;
	}
	
	public GraphModification insertValueEdgeBefore(ValueEdge edge, ValueEdge anchor) {
		insertValueEdge(edge);

		List<ValueEdge> allValueEdges = data.getGraph().getAllValueEdges();
		
		int ancPos = data.getGraph().indexOf(allValueEdges, anchor);
		int edgePos = data.getGraph().indexOf(allValueEdges, edge);
		
		if(edgePos>ancPos) 
			data.getGraph().moveValueEdgeTo(edgePos, ancPos);
		
		this.data.getGraph().getOrder().add(edge.getIndex(), anchor.getIndex());
		return this;
	}
	
	public GraphModification insertValueEdgeAfter(ValueEdge edge, ValueEdge anchor) {
		insertValueEdge(edge);

		List<ValueEdge> allValueEdges = data.getGraph().getAllValueEdges();
		
		int ancPos = data.getGraph().indexOf(allValueEdges, anchor);
		int edgePos = data.getGraph().indexOf(allValueEdges, edge);
		
		if(edgePos>ancPos) 
			data.getGraph().moveValueEdgeTo(edgePos, ancPos);
		
		this.data.getGraph().getOrder().add(anchor.getIndex(), edge.getIndex());
		
		return this;
	}
	
	public GraphModification insertValueEdgeFirst(ValueEdge edge) {
		insertValueEdge(edge);

		List<ValueEdge> allValueEdges = data.getGraph().getAllValueEdges();
		
		int edgePos = data.getGraph().indexOf(allValueEdges, edge);
		
		if(edgePos>0) 
			data.getGraph().moveValueEdgeTo(edgePos, 0);
		
		allValueEdges.forEach(e->{
			if(e!=edge)
				this.data.getGraph().getOrder().add(edge.getIndex(), e.getIndex());			
		});
		
		return this;
	}
	
//	public GraphModification insertTypedEdgeBefore(List<TypedEdge> edges, TypedEdge anchor) {
//		insertTypedEdge(edges);
//
//		List<TypedEdge> allTypedEdges = data.getGraph().getAllTypedEdges();
//		
//		int ancPos = data.getGraph().indexOf(allTypedEdges, anchor);
//		
//		List<Integer> edgePoss = new ArrayList<>(edges.size());
//		
//		for(TypedEdge edge : edges) {
//			int cur = data.getGraph().indexOf(allTypedEdges, edge);
//			if(cur<=ancPos) continue;
//			else edgePoss.add(cur);
//		}
//		
//		return this;
//	}
	
	public GraphModification addValueEdge(String sid, String tid, String edge) {
		return addValueEdge(sid, tid, edge);
	}
	
	public GraphModification addValueEdge(String eid, String sid, String tid, String edge) {
		TypedNode src = (TypedNode) varMap.get(sid);
		ValueNode tar = (ValueNode) varMap.get(tid);
		
		TypedGraph graph = data.getGraph();
		TypeGraph typeGraph = graph.getTypeGraph();
		
		PropertyEdge te = typeGraph.getPropertyEdge(src.getType(), edge);
		
		ValueEdge l = new ValueEdge(src, tar, te);
		
		Context context = data.getContext();
		Index ind = null;
		
		if(eid!=null) {
			try {
				ind = (Index) context.getValue(eid);
			} catch (Exception e) {
			}
		}
		
		if(ind!=null) l.appendIndex(ind);
		
		graph.addValueEdge(l);
		
		return this;
	}
	
	public GraphModification remove(String id) {
		TypedGraph graph = data.getGraph();
		Context context = data.getContext();
		Index ind = null;
		try {
			ind = (Index) context.getValue(id);
		} catch (Exception e) {
		}
		if (ind != null) {
			try {
				IndexableElement e = graph.getElementByIndexObject(ind);
				remove(e);
			} catch (NothingReturnedException e) {
			}
		}
		return this;
	}
	
	public GraphModification remove(List<? extends IndexableElement> e) {
		GraphModification m = this;
		for(IndexableElement x : e) {
			m = m.remove(x);
		}
		return m;
	}
	
	public GraphModification remove(IndexableElement e) {
		TypedGraph graph = data.getGraph();
		if(e instanceof TypedNode)
			graph.remove((TypedNode)e);
		else if(e instanceof TypedEdge)
			graph.remove((TypedEdge)e);
		else if(e instanceof ValueEdge)
			graph.remove((ValueEdge)e);
		
		return this;
	}
	
	public GraphModification remove(Index id) {
		TypedGraph graph = data.getGraph();
		try {
			IndexableElement e = graph.getElementByIndexObject(id);
			return remove(e);
		} catch (NothingReturnedException e1) {
			return this;
		}
	}
	
	public GraphModification match(Pattern pat, Tuple2<String, Object>[] valueMaps) {
		if(data instanceof SourceType) {
			List<Context> matches = data.match(pat, valueMaps);
			if(matches.size()!=1) {
				return this;
			} else {
				return new GraphModification(data.replaceContext(matches.get(0)));
			}
		} else {
			return null;
		}
	}
	
	public GraphModification enforce(Pattern pat, Tuple2<String, Object>[] valueMaps) {
		if(data instanceof SourceType) {
			TypedGraph delta;
			
			ContextType updatedType = new ContextType();
			pat.getType().fields().forEach(f->updatedType.addField(f));
			data.getContext().getType().fields().forEach(f->{
				if(!pat.getType().isDefined(f.getName()))
					updatedType.addField(f);
			});
			
			
			try {
				Context updatedContext = data.getContext().createDownstreamContext(updatedType); // it is used to return 
				Context patInstance = pat.getType().createInstance(); // it is used to create the delta. May be removed in the future!!
				
				for(FieldDef<?> f : patInstance.getType().fields()) {					
					Tuple2<String, Object> tuple = search(f, valueMaps);
					
					if(tuple==null) {
						FieldDef<?> up = data.getContext().getType().getField(f.getName());
						if (up == null) {
							Object value = f.getDefaultValue(true);
							patInstance.setValue(f, value);
							updatedContext.setValue(f, value);
						} else {
							Object value = data.getContext().getValue(up);
							patInstance.setValue(f, value);
							updatedContext.setValue(f, value);
						}
					} else { // if tuple!=null, user must have provided a value
						patInstance.setValue(f, tuple.second);
						updatedContext.setValue(f, tuple.second);
					}
				}
				
				delta = pat.construct(data.getGraph(), patInstance);
				SourceType updated = SourceType.makeSource(((SourceType)data).first.additiveMerge(delta),
						updatedContext, ((SourceType)data).third);
				
				GraphModification modify = new GraphModification(updated);
				return modify;
			} catch (UninitializedException | NothingReturnedException e) {
				return null;
			}
		} else {
			return null;
		}
	}
	protected Tuple2<String, Object> search(FieldDef<?> f, Tuple2<String, Object>[] valueMaps) {
		for(Tuple2<String, Object> t : valueMaps) {
			if(t.first.equals(f.getName())) {
				return t;
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends ContextGraph> T get() {
		try {
			data.getGraph().enforceOrder();
		} catch (NothingReturnedException e) {
			e.printStackTrace();
		}
		return (T) data;
	}
}