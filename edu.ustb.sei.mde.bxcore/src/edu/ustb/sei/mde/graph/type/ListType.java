package edu.ustb.sei.mde.graph.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class ListType implements ICollectionType {
	static private Map<IElementType, ListType> typeCache = new HashMap<>();
	
	private ListType(IElementType elementType) {
		this.elementType = elementType;
	}
	
	static public ListType makeList(IElementType elementType) {
		ListType type = typeCache.get(elementType);
		if(type==null) {
			type = new ListType(elementType);
			typeCache.put(elementType, type);
		}
		return type;
	}
	
	
	private IElementType elementType;

	@Override
	public IType getElementType() {
		return elementType;
	}
	
	public void setElementType(IElementType t) {
		elementType = t;
	}

	private String cachedName = null;
	@Override
	public String getName() {
		if(cachedName==null)
			cachedName = "["+elementType.getName()+"]";
		return cachedName;
	}
	
	@Override
	public Class<?> getJavaType() {
		return ArrayList.class; // ??
	}
	
	@Override
	public boolean isInstance(Object value) {
		if(value instanceof List) {
			return ((List<?>) value).stream().allMatch(v->this.getElementType().isInstance(v));
		} else if(value instanceof Stream<?>) {
			return ((Stream<?>) value).allMatch(v->this.getElementType().isInstance(v));
		}
		return false;
	}

}
