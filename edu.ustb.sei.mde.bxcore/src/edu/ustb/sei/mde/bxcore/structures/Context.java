package edu.ustb.sei.mde.bxcore.structures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import edu.ustb.sei.mde.bxcore.Trace;
import edu.ustb.sei.mde.bxcore.TraceSystem;
import edu.ustb.sei.mde.bxcore.XmuCoreUtils;
import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.bxcore.exceptions.UninitializedException;
import edu.ustb.sei.mde.graph.typedGraph.IndexableElement;
import edu.ustb.sei.mde.graph.typedGraph.ValueNode;
import edu.ustb.sei.mde.structure.Tuple2;


public class Context {
	
	private ContextType type;
	
	public ContextType getType() {
		return type;
	}

	private boolean willCreateElement = false;

	public boolean willCreateElement() {
		return willCreateElement;
	}

	public void setWillCreateElement(boolean willCreateElement) {
		this.willCreateElement = willCreateElement;
	}

	protected ContextMapping upstreamMapping;
	
	protected List<ContextMapping> downstreamMappings = new ArrayList<>();
	
	public void setUpstream(Context c) {
		upstreamMapping =  ContextMapping.expend(c, this);
	}
	
	public void setUpstream(Context c, Tuple2<String,String>[] keyMappings) {
		upstreamMapping = ContextMapping.transform(c, this, keyMappings);
	}
	
	public void setUpstream(ContextType upType, Tuple2<String,String>[] keyMappings) {
		upstreamMapping = ContextMapping.transform(upType, this, keyMappings);
	}
	
	public void submit() {
		upstreamMapping.upstream.downstreamMappings.add(upstreamMapping);
	}
	
	

	public Context(ContextType type) {
		valueMap = new HashMap<FieldDef<?>, Optional<?>>();
		this.type = type;
		
		if(type!=null) {
			type.fields().forEach(f->register(f));
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null || !(obj instanceof Context)) return false;
		if(this==obj) return true;
		return isIdentifical(this, (Context)obj);
	}
		
	private Map<FieldDef<?>, Optional<?>> valueMap;
	
	public <T> void setValue(String key, T value) {
		FieldDef<?> field = type.getField(key);
		internalSetValue(field, value);
	}
	
	public <T> void setValue(FieldDef<?> key, T value) {
		setValue(key.getName(), value);
	}
	
	@SuppressWarnings("unchecked")
	private <T> void internalSetValue(FieldDef<?> key, T value) {
		if(key!=null) {
			if(key.isCollection()) {
				List<Object> values = (List<Object>) key.getDefaultValue(value!=null);
				if(value!=null) {
					if(value instanceof List) {
						values.addAll((List<Object>) value);
					} else {
						values.add(value);
					}
				}
				
//				foreach i do values.set(i, unwrapValue(values.get(i)));				
				valueMap.put(key, Optional.of(unwrapValue(value)));
			} else {
				valueMap.put(key, Optional.of(unwrapValue(value)));
			}
		} else {
			XmuCoreUtils.failure("You are trying to map a value onto an unregistered key in the context");
		}
	}
	
	private Object unwrapValue(Object value) {
		if(value!=null) {
			if(value instanceof GraphPath) {
				return ((GraphPath) value).toIndexPath();
			} else if(value instanceof java.util.UUID) {
				return Index.freshIndex(value);
			} else if(value instanceof List<?>) {
				List<Object> result = new ArrayList<>();
				for(Object v : (List<?>)value) {
					result.add(unwrapValue(v));
				}
				return result;
			} else {
				if(value instanceof IndexableElement) {
					if(((IndexableElement) value).isIndexable()) {
						return ((IndexableElement) value).getIndex();
					} else { // the only un-indexable element is  value node
						return ((ValueNode)value).getValue();
					}
				} else return value;
			}
		} else
			return value;
	}

	public void register(FieldDef<?> key) {
		if(isRegistered(key)==false)
			valueMap.put(key, Optional.empty());
	}
	
	public <T> T getValue(String key) throws UninitializedException, NothingReturnedException {
		FieldDef<?> t = type.getField(key);
		if(t==null) 
			throw new NothingReturnedException(key+" was not registered as a field name in "+type);
		return internalGetValue(t);
	}
	
	public <T> T getValue(FieldDef<?> key) throws UninitializedException, NothingReturnedException {
		return getValue(key.getName());
	}
	
	private <T> T internalGetValue(FieldDef<?> key) throws UninitializedException, NothingReturnedException {
		@SuppressWarnings("unchecked")
		Optional<T> value = (Optional<T>) valueMap.get(key);
		
		if(value==null) {
			throw new NothingReturnedException();
		} else if(value==Optional.empty()) 
			throw new UninitializedException();
		else 
			return value.get();
	}
	
	public Index getIndexValue(FieldDef<?> key) throws UninitializedException, NothingReturnedException {
		return getValue(key);
	}
	
	public <T> T getPrimitiveValue(FieldDef<?> key) throws UninitializedException, NothingReturnedException {
		return getValue(key);
	}
	
	public <T> List<T> getListValue(FieldDef<?> key) throws UninitializedException, NothingReturnedException {
		return getValue(key);
	}
	
	public boolean isRegistered(FieldDef<?> def) {
		return valueMap.containsKey(def);
	}
	
	public Collection<FieldDef<?>> keys() {
		return this.valueMap.keySet();
	}

	public static boolean isIdentifical(Context left, Context right) {
		Collection<FieldDef<?>> leftKeys = left.keys();
//		Collection<FieldDef<?>> rightKeys = right.keys();
		
		if(left.getType().equals(right.getType())) {
			return leftKeys.stream().allMatch(k->{
				try {
					Object lv = left.getValue(k);
					Object rv = right.getValue(k);
					return lv==rv || (lv!=null && lv.equals(rv));
				} catch(Exception e) {
					return false;
				}
			});
		}
		else
			return false;
	}
	
	
	public boolean verticalCorrectness(TraceSystem traceSys) {
		if(this.willCreateElement()==false) 
			return true;
		
		// for each <k,v> in bottom
		//   find the top-most context u where v occurs first
		//   find the nearest context w, starting from u, where v is created 
		//   check whether there exists x between u and w, there v is synchronized and there exists y upon w where v is also synchronized
		
		List<Tuple2<FieldDef<?>, Context>> eqSets = new ArrayList<>();
		collectEquivalentNodeSetBottomUp(this, null, eqSets);
		List<List<Trace>> traceSet = new ArrayList<>();
		eqSets.forEach(eqSet->traceSet.add(collectSynchronizationTracesBottomUp(eqSet.first,eqSet.second, traceSys)));
		
		for(int l = 0; l<eqSets.size();l++) {
			Tuple2<FieldDef<?>, Context> leftFirst = eqSets.get(l);
			try {
				Object leftValue = leftFirst.second.getValue(leftFirst.first);
				for(int r = l+1 ; r<eqSets.size();r++) {
					Tuple2<FieldDef<?>, Context> rightFirst = eqSets.get(r);
					try {
						Object rightValue = rightFirst.second.getValue(rightFirst.first);
						if(isTheSameNode(leftValue, rightValue)) { // shared node!!
							List<Trace> leftTrace = traceSet.get(l);
							List<Trace> rightTrace = traceSet.get(r);
							
							if(XmuCoreUtils.traceSetDisjoint(leftTrace, rightTrace)) {
								XmuCoreUtils.warning("Shared view nodes: "+leftFirst.first+"=>"+leftTrace+", "+rightFirst.first+"=>"+rightTrace);
								
								return false;
							}
						}
					} catch (UninitializedException | NothingReturnedException e) {}
				}
			} catch (UninitializedException | NothingReturnedException e) {}
		}
		
		
		return true;
		
	}

	public boolean isTheSameNode(Object leftValue, Object rightValue) {
		return leftValue.equals(rightValue);
	}
	
	/**
	 * collect all synchronizations bottom-up about rk from root
	 * @param rk
	 * @param root
	 * @param traceSys
	 * @return
	 */
	private static List<Trace> collectSynchronizationTracesBottomUp(FieldDef<?> rk, Context root, TraceSystem traceSys) {
		List<Trace> result = null;
		
		ContextMapping upstreamMapping = root.upstreamMapping;
		
		// navigate to the top context
		if(upstreamMapping!=null) {
			FieldDef<?> upKey = upstreamMapping.upKey(rk);
			if(upKey!=null)
				result = collectSynchronizationTracesBottomUp(upKey, upstreamMapping.upstream, traceSys);
			else result = new ArrayList<>();
		} else result = new ArrayList<>();
		
		// check current level
		for(Trace t : traceSys.allTraces()) {
			if(t.view.isDownstreamOf(root)) { // special shape for index?
				FieldDef<?> dk = t.view.upstreamMapping.downKey(rk);
				if(dk!=null) {
					result.add(t);
				}				
			}
			
		}
		
		return result;
	}
	
	private static List<Trace> collectSynchronizationTracesTopDown(FieldDef<?> rk, Context root, TraceSystem traceSys) {
		List<Trace> result = new ArrayList<>();
		
		for(Trace t : traceSys.allTraces()) {
			if(t.view.isDownstreamOf(root)) {
				FieldDef<?> dk = t.view.upstreamMapping.downKey(rk);
				if(dk!=null) {
					result.add(t);
				}
			}
		}
		
		for(ContextMapping downstreamMapping : root.downstreamMappings) {
			FieldDef<?> downKey = downstreamMapping.downKey(rk);
			if(downKey!=null)
				result.addAll(collectSynchronizationTracesTopDown(downKey,downstreamMapping.downstream,traceSys));
		}
		
		return result;
	}
	
	/**
	 * Compute the direct variable mappings between contexts based on upstreamMappings bottom-up
	 * Such variables should be mapped onto the same value.
	 * @param current
	 * @param below
	 * @param result
	 */
	private static void collectEquivalentNodeSetBottomUp(Context current, Context below,  List<Tuple2<FieldDef<?>, Context>> result) {
		if(below==null) {
			for(FieldDef<?> k : current.keys()) {
				if(!k.isElementType() || !k.isSharable()) continue;
				result.add(new Tuple2<FieldDef<?>, Context>(k, current));
			}
		} else {
			ContextMapping m = below.upstreamMapping;
			for(FieldDef<?> k : current.keys()) {
				if(!k.isElementType() || !k.isSharable()) continue;
				FieldDef<?> dk = m.downKey(k);
				if(dk==null) {
					result.add(new Tuple2<FieldDef<?>, Context>(k, current));
				} else {
				}
			}
		}
		
		if(current.upstreamMapping!=null)
			collectEquivalentNodeSetBottomUp(current.upstreamMapping.upstream, current, result);
	}

	public boolean horizontalCorrnectness(Context[] parallelViews, TraceSystem[] traceSys) {
		// collect eqSets for each parallelView 
		List<Set<Tuple2<FieldDef<?>, Context>>> sets = new ArrayList<>();
		
		for(Context view : parallelViews) {
			Set<Tuple2<FieldDef<?>, Context>> nodeSet = new HashSet<>();
			collectEquivalentNodeSetTopDown(this, view, nodeSet);
			sets.add(nodeSet);
		}
		
		Map<Tuple2<FieldDef<?>, Context>, List<Trace>> traceSet = new HashMap<>();
		for(int i=0;i<sets.size();i++) {
			Set<Tuple2<FieldDef<?>, Context>> eqSet = sets.get(i);
			TraceSystem trace = traceSys[i];
			eqSet.forEach(t->{
				traceSet.put(t, collectSynchronizationTracesTopDown(t.first, t.second,trace));
			});
		}
		
//		sets.forEach(eqSet->{
//			eqSet.forEach(t->{
//				traceSet.put(t, collectSynchronizationTracesTopDown(t.first, t.second,traceSys));
//			});
//		});
		
		for(int l=0;l<sets.size();l++) {
			Set<Tuple2<FieldDef<?>, Context>> lNodes = sets.get(l);
			for(int r=l+1;r<sets.size();r++) {
				Set<Tuple2<FieldDef<?>, Context>> rNodes = sets.get(r);
				
				for(Tuple2<FieldDef<?>, Context> left : lNodes) {
					try {
						Object leftValue = left.second.getValue(left.first);
						for(Tuple2<FieldDef<?>, Context> right : rNodes) {
							try {
								Object rightValue = right.second.getValue(right.first);
								
								if(isTheSameNode(leftValue, rightValue)) {
									List<Trace> leftTrace = traceSet.get(left);
									List<Trace> rightTrace = traceSet.get(right);
									
									if(Collections.disjoint(leftTrace, rightTrace)) {
										return false;
									}
								}
							} catch (UninitializedException | NothingReturnedException e) {
							}
						}
					} catch (UninitializedException | NothingReturnedException e) {
					}
				}
				
			}
		}
		
		
		return true;
	}
	
	/**
	 * Compute the direct variable mappings between contexts based on upstreamMappings top-down
	 * Such variables should be mapped onto the same value.
	 * @param current
	 * @param below
	 * @param result
	 */
	static private void collectEquivalentNodeSetTopDown(Context upstream, Context downstream,  Set<Tuple2<FieldDef<?>, Context>> result) {
		for(FieldDef<?> dk : downstream.keys()) {
			if(!dk.isElementType() || !dk.isSharable()) continue;
			if(downstream.upstreamMapping.upKey(dk)==null) {
				result.add(new Tuple2<FieldDef<?>, Context>(dk, downstream));
			}
		}
		
		for(ContextMapping dm : downstream.downstreamMappings) {
			collectEquivalentNodeSetTopDown(downstream, dm.downstream, result);
		}
	}
	
	public FieldDef<?> getDownKeyFromUpstreamKey(FieldDef<?> ukey) {
		return this.upstreamMapping.downKey(ukey);
	}
	
	public FieldDef<?> getUpKeyFromDownstreamKey(FieldDef<?> dkey) {
		return this.upstreamMapping.upKey(dkey);
	}

	public boolean isDownstreamOf(Context source) {
		return this.upstreamMapping!=null && this.upstreamMapping.upstream==source;
	}

	public void initWith(Context second) {
		if(this.getType()!=second.getType())
			throw new RuntimeException("Cannot init a context with another of a different type");
		
		second.getType().fields().forEach(f->{
			try {
				this.setValue(f, second.getValue(f));
			} catch (UninitializedException | NothingReturnedException e) {}
		});
		
	}
	
	static public Context emptyContext() {
		return ContextType.EMPTY_TYPE.createInstance();
	}
	
	public Context createUpstreamContext(ContextType upstreamType, Tuple2<String,String>[] mappings) {
		Context context = upstreamType.createInstance();
		
		for(Tuple2<String,String> m : mappings) {
			try {
				context.setValue(m.first, this.getValue(m.second));
			} catch (NullPointerException|UninitializedException|NothingReturnedException e) {
			}
		}
		
		return context;
	}
	
	public Context createUpstreamContext(ContextType upstreamType) {
		Context context = upstreamType.createInstance();
		
		for(FieldDef<?> k : upstreamType.fields()) {
			try {
				context.setValue(k, this.getValue(k.getName()));
			} catch (NullPointerException|UninitializedException|NothingReturnedException e) {
			}
		}
		
		return context;
	}
	
	public Context createDownstreamContext(ContextType downstreamType, Tuple2<String,String>[] mappings) {
		Context context = downstreamType.createInstance();
		
		for(Tuple2<String,String> m : mappings) {
			try {
				context.setValue(m.second, this.getValue(m.first));
			} catch (NullPointerException|UninitializedException|NothingReturnedException e) {
			}
		}
		
		return context;
	}
	
	public Context createDownstreamContext(ContextType downstreamType) {
		Context context = downstreamType.createInstance();
		
		for(FieldDef<?> k : this.getType().fields()) {
			try {
				context.setValue(k.getName(), this.getValue(k));
			} catch (NullPointerException|UninitializedException|NothingReturnedException e) {
			}
		}
		
		return context;
	}
	
	public Context createDownstreamContext(ContextType downstreamType, Tuple2<String,String>[] mappings, boolean ignore) throws NothingReturnedException {
		Context downstream = downstreamType.createInstance();
		
		for(Tuple2<String, String> m : mappings) {
			try {
				downstream.setValue(m.second, this.getValue(m.first));
			} catch (UninitializedException|NothingReturnedException e) {
				if(ignore) continue;
				else throw new NothingReturnedException(e);
			}
		}
		return downstream;
	}
	
	public Context getCopy() {
		Context copy = this.getType().createInstance();
		for(FieldDef<?> f : this.getType().fields()) {
			try {
				copy.setValue(f, this.getValue(f));
			} catch (UninitializedException | NothingReturnedException e) {
			}
		}
		return copy;
	}
	
	
}
