package edu.ustb.sei.mde.graph.type;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public interface IPathType extends IElementType {
	IStructuralFeatureEdge getSingleEdge();
}


class PathCheckCache {
	private Map<Object,Map<Integer, Boolean>> cache = new HashMap<>();
	public Boolean check(Object type, int index) {
		Map<Integer, Boolean> map = cache.getOrDefault(type, Collections.emptyMap());
		return map.get(index);
	}
	public void setCache(Object type, int index, boolean value) {
		Map<Integer, Boolean> map = cache.get(type);
		if(map==null) {
			map = new HashMap<>();
			cache.put(type, map);
		}
		map.put(index, value);
	}
}