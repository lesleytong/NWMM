package edu.ustb.sei.mde.graph.type;

import edu.ustb.sei.mde.graph.Nullable;

public class DataTypeNode implements ITypeNode {
	
	@Nullable(false) 
	private Class<?> dataType;	//规范化后的基本类型、或引用类型
	@Nullable(false) 
	private String name;
	
	private TypeGraph typeGraph;
	
	static public final DataTypeNode NULL_TYPE;
	
	static {
		NULL_TYPE = new DataTypeNode();
		NULL_TYPE.setDataType("BOTTOM", null);
	}
	
	public Class<?> getDataType() {
		return dataType;
	}

	@Override
	public Class<?> getJavaType() {	//此方法和上面的重复了？？
		return getDataType();
	}
	
	void setDataType(String name, Class<?> dataType) {
		this.name = name;
		this.dataType = normalizeDateType(dataType);	//dataType赋值为规范化后的数据类型
	}

	static public Class<?> normalizeDateType(Class<?> clazz) {
		if(clazz==int.class 
				|| clazz==short.class
				|| clazz==byte.class
				|| clazz==long.class)
			return java.lang.Long.class;
		else if(clazz==float.class
				|| clazz==double.class)
			return java.lang.Double.class;
		else if(clazz==char.class)
			return Character.class;
		else if(clazz==boolean.class)
			return Boolean.class;
		return clazz;	//引用类型直接返回其类型
	}

	@Override
	public String getName() {
		return name;
	}
	
	public TypeGraph getTypeGraph() {
		return typeGraph;
	}

	public void setTypeGraph(TypeGraph typeGraph) {
		this.typeGraph = typeGraph;
	}

	public String toString() {
		return "("+"name:"+name+", javaType:"+dataType.getTypeName()+")";
	}
	
	@Override
	public boolean isInstance(Object value) {
		// TODO FIXME
		return false;
	}
}
