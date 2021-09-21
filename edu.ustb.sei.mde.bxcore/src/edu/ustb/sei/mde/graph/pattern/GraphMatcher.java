package edu.ustb.sei.mde.graph.pattern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import edu.ustb.sei.mde.bxcore.XmuCoreUtils;
import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.bxcore.exceptions.UninitializedException;
import edu.ustb.sei.mde.bxcore.structures.Context;
import edu.ustb.sei.mde.bxcore.structures.ContextGraph;
import edu.ustb.sei.mde.bxcore.structures.ContextType;
import edu.ustb.sei.mde.bxcore.structures.FieldDef;
import edu.ustb.sei.mde.bxcore.structures.GraphPath;
import edu.ustb.sei.mde.bxcore.structures.Index;
import edu.ustb.sei.mde.bxcore.structures.IndexPath;
import edu.ustb.sei.mde.graph.IEdge;
import edu.ustb.sei.mde.graph.INode;
import edu.ustb.sei.mde.graph.type.DashedPathType;
import edu.ustb.sei.mde.graph.type.DashedPathTypeSegment;
import edu.ustb.sei.mde.graph.type.DataTypeNode;
import edu.ustb.sei.mde.graph.type.IPathType;
import edu.ustb.sei.mde.graph.type.IStructuralFeatureEdge;
import edu.ustb.sei.mde.graph.type.ITypeNode;
import edu.ustb.sei.mde.graph.type.PropertyEdge;
import edu.ustb.sei.mde.graph.type.TypeEdge;
import edu.ustb.sei.mde.graph.type.TypeGraph;
import edu.ustb.sei.mde.graph.type.TypeNode;
import edu.ustb.sei.mde.graph.typedGraph.ITypedEdge;
import edu.ustb.sei.mde.graph.typedGraph.ITypedNode;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;
import edu.ustb.sei.mde.graph.typedGraph.TypedNode;
import edu.ustb.sei.mde.graph.typedGraph.ValueNode;
import edu.ustb.sei.mde.structure.Tuple2;

public class GraphMatcher {
	public GraphMatcher(Pattern pattern) {
		super();
		this.pattern = pattern;
		this.type = pattern.getTypeGraph();
	}

	private Pattern pattern;
	
	@SuppressWarnings("unused")
	private TypeGraph type;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object wrap(PatternElement<?> def, Object value, TypedGraph graph) throws NothingReturnedException {
		if(value==null){
			return ValueNode.createConstantNode(null, (DataTypeNode) def.getElementType());
		} else if(value instanceof Index) {
			return graph.getElementByIndexObject((Index)value);
		} else if(value instanceof List) {
			List result = new ArrayList<>();
			for(Object v : (List) value) {
				result.add(wrap(def, v, graph));
			}
			return result;
		} else if(value instanceof IndexPath) {
			return ((IndexPath) value).toGraphPath(graph);
		} else {
			return ValueNode.createConstantNode(value, (DataTypeNode) def.getElementType());
		}
	}

	private Map<String, Object> convertContext(Context con, TypedGraph graph) {
		// con should not contain collection values
		Map<String, Object> map = new HashMap<String, Object>();

		pattern.getNodes().forEach(n -> {
			try {
				String name = ((PatternElement<?>) n).getName();
				Object value = con.getValue(name);
				map.put(name, wrap(((PatternElement<?>) n), value, graph));
			} catch (Exception e) {
			}
		});

		pattern.getEdges().forEach(n -> {
			try {
				String name = ((PatternElement<?>) n).getName();
				Object value = con.getValue(name);
				map.put(name, wrap(((PatternElement<?>) n), value, graph));
			} catch (Exception e) {
			}
		});

		return map;
	}

	/**
	 *  <p>Precondition : we assume that there is no isolated node in the pattern</p>
	 *  <p>Precondition : base should not collection values</p>
	 *  
	 *  <p> Pattern matching first treats a pattern as a plain graph (with path support) and find matches.
	 *  Then, it groups the matches to get collection values; 
	 *  Afterwards, it computes the additional variables.
	 *  At last, it filters out the match that does not satisfy the filter condition. </p> 
	 */
	public List<Context> match(TypedGraph graph, Context base) {
		Map<String, Object> initPoint = convertContext(base, graph);
		Map<String, Object> startingPoint = new HashMap<String, Object>();
		
		// find matches of plain pattern
		List<PatternNode> search = pattern.getNodes().stream().filter(n->n instanceof PatternNode)
				.map(n->(PatternNode)n).collect(Collectors.toList());
		List<Map<String, Object>> matchResult = recursiveMatchNodes(0, search, startingPoint, initPoint, graph);
		
		// grouping
		group(matchResult);
		
		// convert Map back to context and put missing default values
		// check and compute additional variables
		// check filter condition
		List<Context> result = buildMatchContext(matchResult, base, graph);
		
		// order matches if necessary
		result = orderMatches(graph, result);
		
		// if pivot is defined, select match according to pivot
		result = checkPivot(result);

		return result;
	}
	
	private List<Context> checkPivot(List<Context> matches) {
		FieldDef<?> pivot;
		
		if(pattern.getOrderBy()!=null && (pivot=pattern.getPivot())!=null 
				&& matches.isEmpty()==false) {
			int i = 0;
			for(;i<matches.size();i++) {
				Context mc = matches.get(i);
				try {
					Object pv = mc.getValue(pivot);
					Object ov = mc.getValue(pattern.getOrderBy());
					if(pv==ov || (pv!=null && pv.equals(ov))) {
						break;
					}
				} catch (UninitializedException | NothingReturnedException e) {
					return Collections.emptyList();
				}
			}
			
			if(i==matches.size()) { // pivot is out of the scope, return first or last
				if(pattern.isAfterPivot()) return Collections.singletonList(matches.get(0));
				else return Collections.singletonList(matches.get(matches.size()-1));
			} else { // return the value before or after pivot
				int rc = pattern.isAfterPivot() ? i + 1 : i - 1;
				if(rc==-1 || rc==matches.size()) return Collections.emptyList();
				else return Collections.singletonList(matches.get(rc));
			}
		} else return matches;
	}
	
	private List<Context> buildMatchContext(List<Map<String, Object>> matches, Context base, TypedGraph graph) {
		ContextGraph contextGraph = ContextGraph.makeContextGraph(graph, base);
		
		List<Context> result = new ArrayList<>();
		ContextType contextType = pattern.getType();
		matches.forEach(match->{
			Context context = contextType.createInstance();
			contextType.fields().forEach(field->{
				try {
					Object fixedValue = base.getValue(field.getName());
					context.setValue(field, fixedValue);
					// we do not check the following property! the algorithm should assure this property
					// Object valueInMatch = match.get(field.getName());
					// assert valueInMatch !=null => valueInMatch == fixedValue
				} catch (UninitializedException | NothingReturnedException e) {
					Object valueInMatch = match.get(field.getName());
					if(valueInMatch==null) {
						if(pattern.getAdditionalField(field.getName())==null)
							return;
					} else 
						context.setValue(field, valueInMatch);
				}
			});
			
			contextGraph.replaceContext(context);
			boolean valueFilter = checkAndComputeAdditionalVariables(contextGraph, context);
			if(valueFilter && (pattern.getFilter()==null || pattern.getFilter().apply(contextGraph)))
				result.add(context);
		});
		return result;
	}
	
	private List<Context> orderMatches(TypedGraph typedGraph, List<Context> matches) {
		PatternElement<?> orderBy = pattern.getOrderBy();
		if(orderBy!=null) {
			ContextType contextType = pattern.getType();
			List<Index> set = new ArrayList<>();
			Map<Index, Context> map = new HashMap<>();
			FieldDef<?> field = contextType.getField(orderBy.getName());
			for(Context m : matches) {
				try {
					Index idx = m.getIndexValue(field);
					set.add(idx);
					map.put(idx, m);
				} catch (UninitializedException | NothingReturnedException e) {
				}				
			}
			
			if(set.size()!=matches.size()) {
				XmuCoreUtils.failure("Matches were discarded due to invalid order");
				return Collections.emptyList();
			}
			
			try {
				Index[] ordered =  typedGraph.getOrder().planOrder(set);
				
				List<Context> result = new ArrayList<>(matches.size());
				for(Index i : ordered)
					result.add(map.get(i));
				
				return result;
			} catch (NothingReturnedException e) {
				XmuCoreUtils.failure("Matches were discarded due to invalid order");
				return Collections.emptyList();
			}
			
		} else 
			return matches;
	}

	private boolean checkAndComputeAdditionalVariables(ContextGraph contextGraph, Context context) {
		boolean valueFilter = pattern.getAdditionalFields().stream().allMatch(f->{
			try {
				Object bv = context.getValue(f.first);
				if(f.second!=null) {
					Object cv = f.second.apply(contextGraph);
					return cv==bv || (cv!=null && cv.equals(bv));
				} else return true;
			} catch (UninitializedException e) {
				if(f.second!=null) {
					context.setValue(f.first, f.second.apply(contextGraph));
					return true;
				} else return false;
			} catch (Exception e) {
				return false;
			}
		});
		return valueFilter;
	}

	protected List<Map<String, Object>> recursiveMatchNodes(int searchCur, List<PatternNode> search, Map<String, Object> startingPoint, Map<String, Object> initPoint, TypedGraph graph) {
		if(searchCur==search.size()) return Collections.singletonList(startingPoint);
		else {
			PatternNode searchNode = search.get(searchCur);
			@SuppressWarnings("rawtypes")
			List candidates = Arrays.asList(graph.getTypedNodesOf((TypeNode) searchNode.getElementType()));
			
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> internalResult = matchNode(searchNode, candidates, startingPoint, initPoint, graph, null);
			List<Map<String, Object>> result = null;
			if(internalResult.isEmpty()) {
				result =  Collections.emptyList();
			} else {
				result = new ArrayList<>(internalResult.size()*2);
				for(Map<String, Object> ms : internalResult) {
					result.addAll(recursiveMatchNodes(searchCur + 1, search, ms, initPoint, graph));
				}
			}
			return result;
			
		}
	}

	static boolean checkValue(PatternElement<?> element, List<?> candidates, Object value) {
		return candidates.contains(value);
	}

	protected List<Map<String, Object>> matchEdge(PatternElement<?> edgeInPattern, List<IEdge> expectedValue,
			Map<String, Object> startingPoint, Map<String, Object> initPoint, TypedGraph graph, PatternElement<?> fromNode) {
		if (expectedValue.size() == 0)
			return Collections.emptyList();
		else {
			boolean checked = false;
			Object existingValue = initPoint.get(edgeInPattern.getName());
			if (existingValue == null) {
				existingValue = startingPoint.get(edgeInPattern.getName());
				checked = existingValue != null;
			} else {
				checked = startingPoint.containsKey(edgeInPattern.getName());
			}

			if (checked) { // visited! just check the value
				if (checkValue(edgeInPattern, expectedValue, existingValue)) {
					return Collections.singletonList(startingPoint);
				} else {
					return Collections.emptyList();
				}
			} else { // unchecked
				List<Map<String, Object>> result = internalMatchEdge(edgeInPattern, existingValue, expectedValue,
						startingPoint, initPoint, graph, fromNode);
				return result;
			}
		}
	}

	protected List<Map<String, Object>> internalMatchEdge(PatternElement<?> edgeInPattern, Object existingValue,
			List<IEdge> expectedValue, Map<String, Object> startingPoint, Map<String, Object> initPoint,
			TypedGraph graph, PatternElement<?> fromNode) {
		PatternElement<?> source = (PatternElement<?>) ((IEdge) edgeInPattern).getSource();
		PatternElement<?> target = (PatternElement<?>) ((IEdge) edgeInPattern).getTarget();

		List<Map<String, Object>> result = Collections.emptyList();
		if (existingValue != null) { // has a predefined value, startingPoint does not contain existingValue
			if (checkValue(edgeInPattern, expectedValue, existingValue)) {
				// existingValue becomes an expectedValue
				startingPoint = new HashMap<>(startingPoint);
				startingPoint.put(edgeInPattern.getName(), existingValue);
				result = internalMatchSingleEdge(edgeInPattern, (IEdge) existingValue, startingPoint, initPoint, graph,
							fromNode, source, target);
			}
			// else no match found
		} else { // no predefined value
			List<Map<String, Object>> internalResult = new ArrayList<>();
			for (IEdge candidateEdge : expectedValue) {
				Map<String, Object> newStartingPoint = new HashMap<>(startingPoint);
				newStartingPoint.put(edgeInPattern.getName(), candidateEdge);
				internalResult.addAll(internalMatchSingleEdge(edgeInPattern, candidateEdge, newStartingPoint, initPoint, graph,
						fromNode, source, target));
			}
			result = internalResult;
		}
		return result;
	}

	private List<Map<String, Object>> internalMatchSingleEdge(PatternElement<?> edgeInPattern, IEdge expectedValue,
			Map<String, Object> startingPoint, Map<String, Object> initPoint, TypedGraph graph,
			PatternElement<?> fromNode, PatternElement<?> source, PatternElement<?> target) {
		List<Map<String, Object>> result;
		INode sourceNode = expectedValue.getSource();
		INode targetNode = expectedValue.getTarget();

		if(fromNode==null) {
			List<Map<String, Object>> internalResult = new ArrayList<>();
			List<Map<String, Object>> matchesAfterSource = matchNode(source,
					Collections.singletonList(sourceNode), startingPoint, initPoint, graph, edgeInPattern);
			
			matchesAfterSource.forEach(ms -> {
				List<Map<String, Object>> matchesAfterTarget = matchNode(target,
						Collections.singletonList(targetNode), ms, initPoint, graph, edgeInPattern);
				internalResult.addAll(matchesAfterTarget);
			});						
			result = internalResult;
		} else {
			if(fromNode == source) {
				result  = matchNode(target, Collections.singletonList(targetNode), startingPoint, initPoint, graph, edgeInPattern);
			} else {
				result  = matchNode(source, Collections.singletonList(sourceNode), startingPoint, initPoint, graph, edgeInPattern);
			}
		}
		return result;
	}

	protected List<Map<String, Object>> matchNode(PatternElement<?> nodeInPattern, List<INode> expectedValue,
			Map<String, Object> startingPoint, Map<String, Object> initPoint, TypedGraph graph, PatternElement<?> fromEdge) {
		if (expectedValue.size() == 0)
			return Collections.emptyList();
		else {
			boolean checked = false;
			Object existingValue = initPoint.get(nodeInPattern.getName());
			if (existingValue == null) {
				existingValue = startingPoint.get(nodeInPattern.getName());
				checked = existingValue != null;
			} else {
				checked = startingPoint.containsKey(nodeInPattern.getName());
			}

			if (checked) { // visited! just check the value
				assert existingValue != null;
				if (checkValue(nodeInPattern, expectedValue, existingValue)) {
					return Collections.singletonList(startingPoint);
				} else {
					return Collections.emptyList();
				}
			} else {
				List<Map<String, Object>> result = internalMatchNode(nodeInPattern, existingValue, expectedValue,
						startingPoint, initPoint, graph, fromEdge);
				return result;
			}
		}
	}

	protected List<Map<String, Object>> internalMatchNode(PatternElement<?> nodeInPattern, Object existingValue,
			List<INode> expectedValue, Map<String, Object> startingPoint, Map<String, Object> initPoint,
			TypedGraph graph, PatternElement<?> fromEdge) {
		
		List<PatternElement<?>> relatedOutGoingPatternEdges = pattern.getEdges().stream()
				.filter(e -> e!=fromEdge && e.getSource() == nodeInPattern).map(e -> (PatternElement<?>) e)
				.collect(Collectors.toList());

		List<PatternElement<?>> relatedInComingPatternEdges = pattern.getEdges().stream()
				.filter(e -> e!=fromEdge && e.getTarget() == nodeInPattern).map(e -> (PatternElement<?>) e)
				.collect(Collectors.toList());

		List<Map<String, Object>> result = Collections.emptyList();
		if (existingValue != null) { // has predefined value
			if (checkValue(nodeInPattern, expectedValue, existingValue)) { // the existing value is consistent with the expected value
				startingPoint = new HashMap<>(startingPoint);
				startingPoint.put(nodeInPattern.getName(), existingValue);
				result = recursiveMatchEdges(Tuple2.make(relatedOutGoingPatternEdges.iterator(), relatedInComingPatternEdges.iterator()),
						existingValue, startingPoint, initPoint, graph, nodeInPattern);
			} else {
				return Collections.emptyList();
			}
		} else { // no predefined value
			result = new ArrayList<>();
			for (INode candidate : expectedValue) {
				Map<String, Object> newStartingPoint = new HashMap<>(startingPoint);
				newStartingPoint.put(nodeInPattern.getName(), candidate);
				result.addAll(recursiveMatchEdges(Tuple2.make(relatedOutGoingPatternEdges.iterator(), relatedInComingPatternEdges.iterator()),
						candidate, newStartingPoint, initPoint, graph, nodeInPattern));
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> recursiveMatchEdges(
			Tuple2<Iterator<PatternElement<?>>, Iterator<PatternElement<?>>> relatedPatternEdges, Object startingNode,
			Map<String, Object> startingPoint, Map<String, Object> initPoint, TypedGraph graph, PatternElement<?> fromNode) {

		if (relatedPatternEdges.first.hasNext()==false && relatedPatternEdges.second.hasNext()==false) {
			return Collections.singletonList(startingPoint);
		}
		if (relatedPatternEdges.first.hasNext()) {
			PatternElement<?> edgeInPattern = relatedPatternEdges.first.next();
			List<Map<String, Object>> result = new ArrayList<>();
			if (edgeInPattern instanceof PatternEdge || edgeInPattern instanceof PatternValueEdge) {
				@SuppressWarnings("rawtypes")
				List candidates = computeOutgoingEdgeCandidates(graph, (TypedNode) startingNode, 
						(IStructuralFeatureEdge) edgeInPattern.getElementType(), 
						(ITypeNode) ((PatternElement<?>)((IEdge) edgeInPattern).getTarget()).getElementType());
				List<Map<String, Object>> matchesAfterEdge = matchEdge(edgeInPattern, (List<IEdge>) candidates,
						startingPoint, initPoint, graph, fromNode);
				matchesAfterEdge.forEach(ms -> {
					List<Map<String, Object>> matchesRest = recursiveMatchEdges(relatedPatternEdges, startingNode, ms,
							initPoint, graph, fromNode);
					result.addAll(matchesRest);
				});
			} else if (edgeInPattern instanceof PatternPathEdge) {
				@SuppressWarnings("rawtypes")
				List candidates = computeOutgoingPathCandidates(graph, (TypedNode) startingNode,
						((PatternPathEdge) edgeInPattern).getElementType(),
						(ITypeNode) ((PatternElement<?>) ((IEdge) edgeInPattern).getTarget()).getElementType());
				List<Map<String, Object>> matchesAfterEdge = matchEdge(edgeInPattern, (List<IEdge>) candidates,
						startingPoint, initPoint, graph, fromNode);
				matchesAfterEdge.forEach(ms -> {
					List<Map<String, Object>> matchesRest = recursiveMatchEdges(relatedPatternEdges, startingNode, ms,
							initPoint, graph, fromNode);
					result.addAll(matchesRest);
				});
				
			}
			return result;
		} else {
			PatternElement<?> edgeInPattern = relatedPatternEdges.second.next();
			List<Map<String, Object>> result = new ArrayList<>();
			if (edgeInPattern instanceof PatternEdge || edgeInPattern instanceof PatternValueEdge) {
				@SuppressWarnings("rawtypes")
				List candidates = computeIncomingEdgeCandidates(graph, (ITypedNode) startingNode, 
						(IStructuralFeatureEdge) edgeInPattern.getElementType() , 
						(TypeNode) ((PatternElement<?>)((IEdge) edgeInPattern).getSource()).getElementType());
				List<Map<String, Object>> matchesAfterEdge = matchEdge(edgeInPattern, (List<IEdge>) candidates,
						startingPoint, initPoint, graph, fromNode);
				matchesAfterEdge.forEach(ms -> {
					List<Map<String, Object>> matchesRest = recursiveMatchEdges(relatedPatternEdges, startingNode, ms,
							initPoint, graph, fromNode);
					result.addAll(matchesRest);
				});
			} else if (edgeInPattern instanceof PatternPathEdge) {
				@SuppressWarnings("rawtypes")
				List candidates = computeIncomingPathCandidates(graph, (ITypedNode) startingNode,
						((PatternPathEdge) edgeInPattern).getElementType(),
						(TypeNode) ((PatternElement<?>) ((IEdge) edgeInPattern).getSource()).getElementType());
				List<Map<String, Object>> matchesAfterEdge = matchEdge(edgeInPattern, (List<IEdge>) candidates,
						startingPoint, initPoint, graph, fromNode);
				matchesAfterEdge.forEach(ms -> {
					List<Map<String, Object>> matchesRest = recursiveMatchEdges(relatedPatternEdges, startingNode, ms,
							initPoint, graph, fromNode);
					result.addAll(matchesRest);
				});
			}
			return result;
		}
	}
	
	private List<? extends IEdge> computeOutgoingEdgeCandidates(TypedGraph graph, TypedNode source, IStructuralFeatureEdge edgeType, ITypeNode targetType) {
		if(edgeType instanceof TypeEdge) {
			return graph.getOutgoingEdges(source, (TypeEdge) edgeType, (TypeNode) targetType);
		} else {
			return graph.getValueEdges(source, (PropertyEdge) edgeType);
		}
	}
	
	private List<? extends IEdge> computeIncomingEdgeCandidates(TypedGraph graph, ITypedNode target, IStructuralFeatureEdge edgeType, TypeNode sourceType) {
		if(edgeType instanceof TypeEdge) {
			if(target instanceof TypedNode)
				return graph.getIncomingEdges((TypedNode) target, (TypeEdge) edgeType, sourceType);
			else return Collections.emptyList();
		}
		else {
			if(target instanceof ValueNode)
				return graph.getValueEdgesTo((ValueNode) target, (PropertyEdge) edgeType, sourceType);
			else return Collections.emptyList();
		}
	}
	
	private void group(List<Map<String,Object>> matches) {
		ContextType contextType = pattern.getType();
		List<FieldDef<?>> singleValuedFields = contextType.singleValuedFields();
		if(singleValuedFields.size()==contextType.fields().size()) {
			return;
		} else {
			List<Map<String,Object>> results = new ArrayList<>();
			for(Map<String,Object> c : matches) {
				Optional<Map<String,Object>> equal = results.stream().filter(x->isEqualForSingleValuedFields(x, c)).findFirst();
				if(equal.isPresent()) {
					mergeMultiValuedFields(equal.get(), c);
				} else {
					results.add(castToCollection(c));
				}
			}
			matches.clear();
			matches.addAll(results);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map<String, Object> castToCollection(Map<String, Object> c) {
		ContextType contextType = pattern.getType();
		for(FieldDef<?> f : contextType.fields()) {
			if(f.isCollection()) {
				Object v = c.get(f.getName());
				if(v==null) {
					c.put(f.getName(), new ArrayList<>());
				} else {
					if(!(v instanceof List)) {
						List col = new ArrayList<>();
						col.add(v);
						c.put(f.getName(), col);
					}
				}
			}
		}
		return c;
	}

	private boolean isEqualForSingleValuedFields(Map<String, Object> left, Map<String, Object> right) {
		ContextType contextType = pattern.getType();
		for(FieldDef<?> f : contextType.singleValuedFields()) {
			Object lv = null;
			Object rv = null;
			lv = left.get(f.getName());
			rv = right.get(f.getName());			
			if(lv==rv || (lv!=null && lv.equals(rv))) continue;
			else return false;
		}
		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void mergeMultiValuedFields(Map<String, Object> left, Map<String, Object> right) {
		ContextType contextType = pattern.getType();
		for(FieldDef<?> f : contextType.fields()) {
			if(f.isCollection()) {
				Object lv = left.get(f.getName());
				List col = null;
				if(lv==null) col = new ArrayList<>();
				else if(lv instanceof List) col = (List) lv;
				else {
					col = new ArrayList<>();
					col.add(lv);
				}
				Object rv = right.get(f.getName());
				if(rv!=null) {
					if(rv instanceof List) {
						col.addAll((List)rv);
					} else {
						col.add(rv);
					}
				}
				left.put(f.getName(), col);
			}
		}
	}
	
	// TODO:
	/**
	 * Compute all paths from a particular source to a node whose type is (a child of) targetType. 
	 * Any returned path must conform to pathType.
	 * This method is a dual of <code>computeIncomingPathCandidates</code>.
	 * @param graph A typed graph to be searched.
	 * @param source The starting point of any returned path.
	 * @param pathType The type (shape) of returned paths, which is a regular path.
	 * @param targetType The type of the end node of any returned path
	 * @return All paths that meet the requirement
	 */
	private List<GraphPath> computeOutgoingPathCandidates(TypedGraph graph, TypedNode source, IPathType pathType, ITypeNode targetType) {
		if(pathType instanceof DashedPathType) {
			PathTrace start = new PathTrace(source);
			List<PathTrace> traces = computeOutgoingPathCandidates(graph, start, (DashedPathType) pathType, 0, targetType);
			
			List<GraphPath> result = new ArrayList<>();
			
			for(PathTrace trace : traces) {
				IEdge[] path = trace.reduce(true);
				if(path.length>0)
					result.add(new GraphPath(path, pathType));
			}
			
			return result;
		} else {
			throw new UnsupportedOperationException("Do not support regular path type now");
		}
	}
	
	private List<PathTrace> computeOutgoingPathCandidates(TypedGraph graph, PathTrace start, DashedPathType pathType, int segIndex, ITypeNode targetType) {
		DashedPathTypeSegment[] segments = pathType.getSegments();
		
		if(segIndex==segments.length) {
			if(targetType.isInstance(start.node)) return Collections.singletonList(start);
			else return Collections.emptyList();
		}
		
		DashedPathTypeSegment curSeg = segments[segIndex];
		
		List<PathTrace> seeds = new ArrayList<>();
		seeds.add(start);
		
		// search min
		for(int min=0;min<curSeg.getMin() && !seeds.isEmpty(); min++) {
			List<PathTrace> nextSeeds = new ArrayList<>();
			for(PathTrace seed : seeds) {
				ITypedNode target = seed.node;
				if(target instanceof TypedNode) {
					for(IStructuralFeatureEdge edgeType : curSeg.getEdgeTypes()) {
						List<? extends IEdge> edges = computeOutgoingEdgeCandidates(graph, ((TypedNode)target), edgeType, null);
						for(IEdge edge : edges) {
							if(!seed.checkOccur(edge.getTarget()))
								nextSeeds.add(new PathTrace((ITypedNode) edge.getTarget(), (ITypedEdge) edge, seed));
						}
					}
				}
			}
			seeds = nextSeeds;
		}
		
		if(seeds.isEmpty()) return Collections.emptyList();
		
		// search max
		List<PathTrace> maxResult = new ArrayList<>(); // maxResult contains all traces that meet the min and max requirement
		maxResult.addAll(seeds);
		for(int max=curSeg.getMin(); (curSeg.getMax()<0 || max<curSeg.getMax()) && !seeds.isEmpty() ; max++) {
			List<PathTrace> nextSeeds = new ArrayList<>();
			for(PathTrace seed : seeds) {
				ITypedNode target = seed.node;
				if(target instanceof TypedNode) {
					for(IStructuralFeatureEdge edgeType : curSeg.getEdgeTypes()) {
						List<? extends IEdge> edges = computeOutgoingEdgeCandidates(graph, ((TypedNode)target), edgeType, null);
						for(IEdge edge : edges) {
							if(!seed.checkOccur(edge.getTarget()))
								nextSeeds.add(new PathTrace((ITypedNode) edge.getTarget(), (ITypedEdge) edge, seed));
						}
					}
				}
			}
			maxResult.addAll(nextSeeds);
			seeds = nextSeeds;
		}
		
		List<PathTrace> result = new ArrayList<>();
		for(PathTrace internal : maxResult) {
			result.addAll(computeOutgoingPathCandidates(graph, internal, pathType, segIndex + 1, targetType));
		}
		return result;
	}
	
	final class PathTrace {
		public PathTrace(ITypedNode node, ITypedEdge edge, PathTrace prev) {
			this.node = node;
			this.edge = edge;
			this.prev = prev;
		}
		
		public PathTrace(ITypedNode node) {
			this.node = node;
			this.edge = null;
			this.prev = null;
		}
		final public ITypedNode node;
		final public ITypedEdge edge;
		final public PathTrace prev;
		
		public boolean checkOccur(INode node) {
			PathTrace trace = this;
			while(trace!=null) {
				if(trace.node==node) return true;
				trace = trace.prev;
			}
			return false;
		}
		
		public IEdge[] reduce(boolean reverse) {
			return reduce(0, reverse);
		}
		
		private IEdge[] reduce(int index, boolean reverse) {
			IEdge[] result = null;
			if(this.prev==null) {
				result = new IEdge[index];
			} else {
				result = this.prev.reduce(index + 1, reverse);
				if(reverse) {
					result[result.length - index - 1] = this.edge;
				} else {
					result[index] = this.edge;  
				}
			}
			
			return result;
			
		}
	}
	
	// TODO:
	/**
	 * Compute all paths to a particular target to a node whose type is (a child of) sourceType. 
	 * Any returned path must conform to pathType.
	 * This method is a dual of <code>computeOutgoingPathCandidates</code>.
	 * @param graph A typed graph to be searched.
	 * @param target The starting point of any returned path.
	 * @param pathType The type (shape) of returned paths, which is a regular path.
	 * @param sourceType The type of the start node of any returned path
	 * @return All paths that meet the requirement
	 */
	private List<GraphPath> computeIncomingPathCandidates(TypedGraph graph, ITypedNode target, IPathType pathType, TypeNode sourceType) {
		if(pathType instanceof DashedPathType) {
			PathTrace start = new PathTrace(target);
			List<PathTrace> traces = computeIncomingPathCandidates(graph, start, (DashedPathType) pathType, 
					((DashedPathType) pathType).getSegments().length - 1, sourceType);
			
			List<GraphPath> result = new ArrayList<>();
			
			for(PathTrace trace : traces) {
				IEdge[] path = trace.reduce(false);
				if(path.length>0)
					result.add(new GraphPath(path, pathType));
			}
			
			return result;
		} else {
			throw new UnsupportedOperationException("Do not support regular path type now");
		}
	}
	
	private List<PathTrace> computeIncomingPathCandidates(TypedGraph graph, PathTrace start, DashedPathType pathType, int segIndex, TypeNode sourceType) {
		DashedPathTypeSegment[] segments = pathType.getSegments();
		
		if(segIndex==-1) {
			if(sourceType.isInstance(start.node)) return Collections.singletonList(start);
			else return Collections.emptyList();
		}
		
		DashedPathTypeSegment curSeg = segments[segIndex];
		
		List<PathTrace> seeds = new ArrayList<>();
		seeds.add(start);
		
		// search min
		for(int min=0;min<curSeg.getMin() && !seeds.isEmpty(); min++) {
			List<PathTrace> nextSeeds = new ArrayList<>();
			for(PathTrace seed : seeds) {
				ITypedNode source = seed.node;
				for(IStructuralFeatureEdge edgeType : curSeg.getEdgeTypes()) {
					List<? extends IEdge> edges = computeIncomingEdgeCandidates(graph, source, edgeType, null);
					for(IEdge edge : edges) {
						if(!seed.checkOccur(edge.getSource()))
							nextSeeds.add(new PathTrace((ITypedNode) edge.getSource(), (ITypedEdge) edge, seed));
					}
				}
			}
			seeds = nextSeeds;
		}
		
		if(seeds.isEmpty()) return Collections.emptyList();
		
		// search max
		List<PathTrace> maxResult = new ArrayList<>(); // maxResult contains all traces that meet the min and max requirement
		maxResult.addAll(seeds);
		for(int max=curSeg.getMin(); (curSeg.getMax()<0 || max<curSeg.getMax()) && !seeds.isEmpty() ; max++) {
			List<PathTrace> nextSeeds = new ArrayList<>();
			for(PathTrace seed : seeds) {
				ITypedNode source = seed.node;
				for(IStructuralFeatureEdge edgeType : curSeg.getEdgeTypes()) {
					List<? extends IEdge> edges = computeIncomingEdgeCandidates(graph, source, edgeType, null);
					for(IEdge edge : edges) {
						if(!seed.checkOccur(edge.getTarget()))
							nextSeeds.add(new PathTrace((ITypedNode) edge.getSource(), (ITypedEdge) edge, seed));
					}
				}
			}
			maxResult.addAll(nextSeeds);
			seeds = nextSeeds;
		}
		
		List<PathTrace> result = new ArrayList<>();
		for(PathTrace internal : maxResult) {
			result.addAll(computeIncomingPathCandidates(graph, internal, pathType, segIndex - 1, sourceType));
		}
		return result;
	}
}
