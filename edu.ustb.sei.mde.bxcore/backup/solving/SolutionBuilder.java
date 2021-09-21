package edu.ustb.sei.mde.graph.pattern.solving;

import java.util.HashMap;
import java.util.Map;

import edu.ustb.sei.mde.bxcore.structures.Context;
import edu.ustb.sei.mde.graph.pattern.InjectiveMapping;
import edu.ustb.sei.mde.graph.pattern.Matching;
import edu.ustb.sei.mde.graph.pattern.Pattern;
import edu.ustb.sei.mde.graph.pattern.PatternEdge;
import edu.ustb.sei.mde.graph.pattern.PatternElement;
import edu.ustb.sei.mde.graph.pattern.PatternNode;
import edu.ustb.sei.mde.graph.pattern.PatternValueEdge;
import edu.ustb.sei.mde.graph.pattern.PatternValueNode;
import edu.ustb.sei.mde.graph.typedGraph.IndexableElement;
import edu.ustb.sei.mde.graph.typedGraph.TypedEdge;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;
import edu.ustb.sei.mde.graph.typedGraph.TypedNode;
import edu.ustb.sei.mde.graph.typedGraph.ValueEdge;
import edu.ustb.sei.mde.graph.typedGraph.ValueNode;

public abstract class SolutionBuilder<T> {
	protected T solution;
	protected TypedGraph graph;
	
	public void setTypedGraph(TypedGraph graph) {
		this.graph = graph;
	}
	
	
	public abstract void begin();
	public T end() {
		return solution;
	}
//	public abstract void addValue(Variable<?> var, Object value);
//	
	static final public SolutionBuilder<Map<Variable<?>,Object>> MAP_BUILDER = null;
//	
//	static public SolutionBuilder<Matching> createMatchingBuilder(Pattern pattern) {
//		return new MatchingSolutionBuilder(pattern);
//	}
//	
//	static public SolutionBuilder<Context> createContextBuilder(Pattern pattern) {
//		return new ContextSolutionBuilder(pattern);
//	}
}

//class MapSolutionBuilder extends SolutionBuilder<Map<Variable<?>,Object>> {
//	@Override
//	public void begin() {
//		solution = new HashMap<>();
//	}
//	
//	@Override
//	public void addValue(Variable<?> var, Object value) {
//		solution.put(var, value);
//	}
//}
//
//class MatchingSolutionBuilder extends SolutionBuilder<Matching> {
//	private Pattern pattern;
//	private InjectiveMapping<PatternElement<?>, Variable<?>> varMapping;
//
//	public MatchingSolutionBuilder(Pattern pattern) {
//		super();
//		this.pattern = pattern;
//		this.varMapping = InjectiveMapping.create(pattern.getVarMap());
//	}
//
//	@Override
//	public void begin() {
//		solution = new Matching();
//		solution.setPattern(this.pattern);
//	}
//
//	@Override
//	public void addValue(Variable<?> var, Object value) {
//		PatternElement<?> pe = varMapping.inverse(var);
//		
//		if(pe instanceof PatternNode) {
//			solution.addMapping((PatternNode)pe, (TypedNode)value);
//		} else if(pe instanceof PatternValueNode) {
//			solution.addMapping((PatternValueNode)pe, (ValueNode)value);
//		} else if(pe instanceof PatternEdge) {
//			solution.addMapping((PatternEdge)pe, (TypedEdge)value);
//		} else if(pe instanceof PatternValueEdge) {
//			solution.addMapping((PatternValueEdge)pe, (ValueEdge)value);
//		}
//	}
//}
//
//class ContextSolutionBuilder extends SolutionBuilder<Context> {
//	private Pattern pattern;
//
//	public ContextSolutionBuilder(Pattern pattern) {
//		super();
//		this.pattern = pattern;
//	}
//
//	@Override
//	public void begin() {
//		solution = pattern.contextType().createInstance();
//	}
//
//	@Override
//	public void addValue(Variable<?> var, Object value) {
//		if(value instanceof TypedNode 
//				|| value instanceof TypedEdge 
//				|| value instanceof ValueEdge) {
//			solution.setValue(var.name, ((IndexableElement)value).getAnyIndex());
//			
//		} else if(value instanceof ValueNode){
//			solution.setValue(var.name, ((ValueNode) value).getValue());
//		}
//	}
//	
//}
