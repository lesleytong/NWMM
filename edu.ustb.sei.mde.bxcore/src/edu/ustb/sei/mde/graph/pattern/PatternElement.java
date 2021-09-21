package edu.ustb.sei.mde.graph.pattern;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

import edu.ustb.sei.mde.bxcore.structures.Context;
import edu.ustb.sei.mde.bxcore.structures.FieldDef;
import edu.ustb.sei.mde.bxcore.structures.Index;
import edu.ustb.sei.mde.graph.INode;
import edu.ustb.sei.mde.graph.type.IType;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;
import edu.ustb.sei.mde.graph.typedGraph.TypedNode;
import edu.ustb.sei.mde.graph.typedGraph.ValueNode;

abstract public class PatternElement<T extends IType> extends FieldDef<IType> {

	public PatternElement() {
	}
	
	public PatternElement(String name, IType type) {
		super(name, type);
	}
	
	@SuppressWarnings("unchecked")
	public T getElementType() {
		return (T) getType().getElementType();
	}
	
	abstract public boolean isMatchOf(Context match, TypedGraph graph);
	
	static protected boolean isValueEqual(ValueNode valueNode, Object value) {
		if(valueNode.getValue()==null) return value==null;
		else return valueNode.getValue().equals(value);
	}
	
	static protected boolean isNodeEqual(TypedNode node, Index index) {
		return node.getIndex().equals(index);
	}

	static protected boolean isINodeEqual(INode node, Object o) {
		if(node instanceof TypedNode) {
			if(o instanceof Index)
				return isNodeEqual((TypedNode) node, (Index) o);
			else return false;
		}
		else return isValueEqual((ValueNode)node, o);
	}
	
	
	
	static protected boolean isINodeSetEqual(Set<INode> nodes, List<Object> values) {
		return nodes.stream().allMatch(n->{
			return values.stream().anyMatch(v->isINodeEqual(n,v));
		}) && values.stream().allMatch(v->{
			return nodes.stream().anyMatch(n->isINodeEqual(n, v));
		});
	}
	
	static protected boolean isINodeSetEqual(Set<INode> nodes, Object value) {
		return nodes.stream().allMatch(n->isINodeEqual(n,value));
	}
	
	
	static protected boolean isNodeSetEqual(Set<TypedNode> nodes, List<Index> indices) {
		Set<Index> idSet = new HashSet<>(indices);
		if(nodes.size()!=indices.size()) return false;
		return nodes.stream().allMatch(n->idSet.contains(n.getIndex()));
	}
	
	static protected boolean isNodeSetEqual(Set<TypedNode> nodes, Index index) {
		return nodes.stream().allMatch(n->n.getIndex().equals(index));
	}
	
	static protected boolean isValueNodeSetEqual(Set<ValueNode> nodes, List<Object> values) {
		boolean hasNull = values.stream().anyMatch(v->v==null);
		
		if(!hasNull) {
			Set<Object> idSet = new HashSet<>(values);
			if(nodes.size()!=idSet.size()) return false;
			return nodes.stream().allMatch(n->{
				return n.getValue()!=null && idSet.contains(n.getValue()); 
			});			
		} else {
			return nodes.stream().allMatch(n->{
				if(n.getValue()==null) return true;
				else return values.stream().anyMatch(v->n.getValue().equals(v));
			}) && values.stream().allMatch(v->{
				return nodes.stream().anyMatch(n->{
					if(v==null) return n.getValue()==null;
					else return v.equals(n.getValue());
				});
			});
		}
	}
	
	static protected boolean isValueNodeSetEqual(Set<ValueNode> nodes, Object value) {
		if(value==null) return nodes.stream().allMatch(n->n.getValue()==null);
		else return nodes.stream().allMatch(n->value.equals(n.getValue()));
		
	}
	
	static final public BiFunction<Object, Integer, Object> LIST = (l,i) -> ((List<?>)l).get(i);
	static final public BiFunction<Object, Integer, Object> ELEMENT = (l,i) -> l;
	
	
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	static protected BiFunction<INode,Set<INode>,Boolean> LIST = (o,s)->{
//		Set<Index> v = new HashSet((List<Index>)o);
//		return s.equals(v);
//	};
//	static protected BiFunction<Object,Set<Index>,Boolean> ELEMENT = (o,s)->{
//		return s.
//	};
}
