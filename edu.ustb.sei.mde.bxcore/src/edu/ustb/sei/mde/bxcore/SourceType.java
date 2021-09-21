package edu.ustb.sei.mde.bxcore;

import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.bxcore.structures.Context;
import edu.ustb.sei.mde.bxcore.structures.ContextGraph;
import edu.ustb.sei.mde.bxcore.structures.FieldDef;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;
import edu.ustb.sei.mde.structure.Tuple2;
import edu.ustb.sei.mde.structure.Tuple3;

public class SourceType extends Tuple3<TypedGraph, Context, TraceSystem> implements ContextGraph {

	private SourceType(TypedGraph first, Context second, TraceSystem ts) {
		super(first, second, ts);
	}

	public static SourceType makeSource(TypedGraph first, Context second, TraceSystem ts) {
		return new SourceType(first, second, ts);
	}
	
	public SourceType replaceFirst(TypedGraph g) {
		return SourceType.makeSource(g, second, third);
	}
	
	public SourceType replaceSecond(Context s) {
		return SourceType.makeSource(first, s, third);
	}
	
	public SourceType replaceThird(TraceSystem t) {
		return SourceType.makeSource(first, second, t);
	}
	
	private static SourceType EMPTY_SOURCE = makeSource(null, null, null);
	static public SourceType empty() {
		return EMPTY_SOURCE;
	}
	
	private static SourceType DROP_SOURCE = makeSource(null, null, null);
	static public SourceType drop() {
		return DROP_SOURCE;
	}
	
	private static SourceType SKIP_SOURCE = makeSource(null, null, null);
	static public SourceType skip() {
		return SKIP_SOURCE;
	}
	
	static public Object summarize(SourceType[] results, FieldDef<?> sk, XmuCore stmt) throws NothingReturnedException {
		Object value = null;
		boolean initialized=false;
		for(SourceType v : results) {
			try {
				if(!initialized) {
					value = v.second.getValue(sk.getName());
					initialized = true;					
				} else if(value.equals(v.second.getValue(sk))==false)
					return stmt.internalError();
			} catch (NothingReturnedException e) {
			}catch (Exception e) {
				return stmt.nothing(e);
			}
		}
		if(!initialized) stmt.nothing();
		return value;
	}
	
	static public Object summarize(SourceType[] result,  FieldDef<?> sk, Tuple2<String, String>[][] allMappings, XmuCore stmt) throws NothingReturnedException {
		Object value = null;
		boolean initialized = false;
		
		
		for(int i=0;i<result.length;i++) {
			SourceType s = result[i];
			Tuple2<String, String>[] mappings = allMappings[i];
			
			for(Tuple2<String, String> m : mappings) {
				if(m.first.equals(sk.getName())==false) continue;
				try {
					Object downValue = s.second.getValue(m.second);
					if(!initialized) {
						value = downValue;
						initialized = true;
					} else if(value.equals(downValue)==false)
						return stmt.internalError();
				} catch (NothingReturnedException e) {
				} catch (Exception e) {
					return stmt.nothing(e);
				}
			}
		}
		
		if(initialized) return value;
		else return stmt.nothing();
	}
}
