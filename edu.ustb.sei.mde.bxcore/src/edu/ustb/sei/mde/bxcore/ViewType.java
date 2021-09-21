package edu.ustb.sei.mde.bxcore;

import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.bxcore.structures.Context;
import edu.ustb.sei.mde.bxcore.structures.ContextGraph;
import edu.ustb.sei.mde.bxcore.structures.FieldDef;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;
import edu.ustb.sei.mde.structure.Tuple2;

public class ViewType extends Tuple2<TypedGraph, Context> implements ContextGraph {

	private ViewType(TypedGraph first, Context second) {
		super(first, second);
	}

	public static ViewType makeView(TypedGraph first, Context second) {
		return new ViewType(first, second);
	}

	public ViewType replaceFirst(TypedGraph f) {
		return ViewType.makeView(f, second);
	}
	
	public ViewType replaceSecond(Context s) {
		return ViewType.makeView(first, s);
	}
	
	static private ViewType EMPTY_VIEW = makeView(null, null);
	static public ViewType empty() {
		return EMPTY_VIEW;
	}
	
	// used in align, parallel composition
	static public Object summarize(ViewType[] result, FieldDef<?> vk, XmuCore stmt) throws NothingReturnedException {
		boolean initialized = false;
		Object value = null;
		for(ViewType v : result) {
			if(v==null || v==ViewType.empty()) continue;
			try {
				if(initialized==false) {
					value = v.second.getValue(vk.getName());
					initialized = true;
				} else if(value.equals(v.second.getValue(vk.getName()))==false) {
					return stmt.internalError();					
				}
			} catch(NothingReturnedException e) { // to support non-isomorphic shapes
			} catch (Exception e) {
				return stmt.nothing(e);
			}
		}
		if(initialized) return value;
		return stmt.nothing();
	}
	
	// used in fork
	static public Object summarize(ViewType[] result, FieldDef<?> vk, Tuple2<String, String>[][] allMappings, XmuCore stmt) throws NothingReturnedException {
		Object value = null;
		boolean initialized = false;
		
		for(int i=0;i<result.length;i++) {
			ViewType v = result[i];
			if(v==null || v==ViewType.empty()) continue;
			Tuple2<String, String>[] mappings = allMappings[i];
			
			for(Tuple2<String, String> m : mappings) {
				if(m.first.equals(vk.getName())==false) continue;
				try {
					Object downValue = v.second.getValue(m.second);
					if(!initialized) {
						value = downValue;
						initialized = true;
					} else if(value.equals(downValue)==false)
						return stmt.internalError();
				} catch(NothingReturnedException e) {
				} catch (Exception e) {
					return stmt.nothing(e);
				}
			}
		}
		
		if(initialized) return value;
		else return stmt.nothing();
	}
}
