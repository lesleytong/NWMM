package edu.ustb.sei.mde.bxcore;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.ustb.sei.mde.bxcore.structures.ContextType;
import edu.ustb.sei.mde.bxcore.structures.FieldDef;
import edu.ustb.sei.mde.graph.type.DataTypeNode;
import edu.ustb.sei.mde.structure.Tuple2;

public final class XmuCoreUtils {
	final public static String COMPONENT_NAME = "edu.ustb.sei.mde.bxcore";
	final public static String ANY_INDEX = "*";
	
	final public static Logger logger = java.util.logging.Logger.getLogger(XmuCoreUtils.COMPONENT_NAME);

	static public Set<FieldDef<?>> findDownKeys(FieldDef<?> uk, Tuple2<String,String>[] mappings, ContextType downType) {
		Set<FieldDef<?>> dks = new HashSet<>();
		for(Tuple2<String, String> m : mappings) {
			if(m.first.equals(uk.getName())) dks.add(downType.getField(m.second));
		}
		return dks;
	}
	
	static public FieldDef<?> findUpKey(FieldDef<?> dk, Tuple2<String, String>[] mappings, ContextType upType) {
		for(Tuple2<String, String> m : mappings) {
			if(m.second.equals(dk.getName())) 
				return upType.getField(m.first);
		}
		return null;
	}
	
	static public void log(Level level, String message, Throwable e) {
		if(e==null)
			logger.log(level, message);
		else 
			logger.log(level, message, e);
	}
	
	static public void warning(String message, Throwable e) {
		log(Level.WARNING, message, e);
	}
	
	static public void warning(String message) {
		warning(message,null);
	}
	
	static public void failure(String message, Throwable e) {
		log(Level.SEVERE, message, e);
	}
	
	static public void failure(String message) {
		failure(message,null);
	}
	
	static public void info(String message, Throwable e) {
		log(Level.INFO, message, e);
	}
	
	static public void info(String message) {
		failure(message,null);
	}

	public static boolean traceSetDisjoint(List<Trace> leftTrace, List<Trace> rightTrace) {
		return Collections.disjoint(leftTrace, rightTrace);
	}

	static public <T> void addUnique(List<T> col, T e) {
		for(T c : col) {
			if(c.equals(e)) return;
		}
		col.add(e);
	}

	static public <T> void addAllUnique(List<T> col, List<T> app) {
		for(T a : app)
			addUnique(col,a);
	}

	public static Object defaultValue(Object type) {
		if(type instanceof DataTypeNode) {
			Class<?> cls = ((DataTypeNode) type).getDataType();
			if(cls==int.class || cls==Integer.class) return 0;
			else if(cls==boolean.class || cls==Boolean.class) return (short)0;
			else if(cls==short.class || cls==Short.class) return (short)0;
			else if(cls==byte.class || cls==Byte.class) return (byte)0;
			else if(cls==char.class || cls==Character.class) return (char)0;
			else if(cls==long.class || cls==Long.class) return 0L;
			else if(cls==float.class || cls==Float.class) return 0.0f;
			else if(cls==double.class || cls==Double.class) return 0.0;
			else if(cls==String.class) return "";
		}
		return null;
	}
}
