package edu.ustb.sei.mde.bxcore.structures;

import java.util.ArrayList;
import java.util.Collections;


import edu.ustb.sei.mde.bxcore.XmuCoreUtils;
import edu.ustb.sei.mde.bxcore.exceptions.InitializationException;
import edu.ustb.sei.mde.graph.IEdge;
import edu.ustb.sei.mde.graph.INamedElement;
import edu.ustb.sei.mde.graph.type.ICollectionType;
import edu.ustb.sei.mde.graph.type.IElementType;
import edu.ustb.sei.mde.graph.type.IType;
import edu.ustb.sei.mde.graph.type.ListType;
import edu.ustb.sei.mde.graph.type.IPathType;
import edu.ustb.sei.mde.graph.type.PropertyEdge;
import edu.ustb.sei.mde.graph.type.TypeEdge;
import edu.ustb.sei.mde.graph.type.TypeNode;
import edu.ustb.sei.mde.graph.typedGraph.IndexSystem;


public class FieldDef<T extends IType> implements INamedElement {

	public FieldDef() {
	}
	
	public FieldDef(String name, T type) {
		this.name = name;
		this.type = type;
	}
	
	private T type;
	private String name;
//	private boolean many;

	public T getType() {
		return type;
	}

	public void setType(T type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isCollection() {
		return type instanceof ICollectionType;
	}
	
	@SuppressWarnings("unchecked")
	public void setCollection(boolean flag) {
		if(flag==false) {
			if(isCollection()) throw new InitializationException("Cannot change a collection type to a single-valued type");
			else return;
		} else if(isCollection()) 
			return;
		else {
			ListType col = ListType.makeList((IElementType) this.type);
			this.type = (T) col;
		}
	}
	
	@Override
	public int hashCode() {
		return (name.hashCode()<<16) | (type.hashCode() & 0xFF);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj!=null && obj instanceof FieldDef<?>) {
			return name.equals(((FieldDef<?>)obj).getName()) 
					&& type.equals(((FieldDef<?>)obj).type);
		}
		else return false;
	}
	
	/**
	 * @return whether this type represents an model element/relationship that requires indices
	 */
	public boolean isElementType() {
		return type instanceof TypeNode || type instanceof TypeEdge || type instanceof PropertyEdge;
	}
	
	public boolean isPathType() {
		return type instanceof IPathType;
	}
	
	public boolean isSharable() {
		return type instanceof TypeNode;
	}
	
	public String toString() {
		return name;
	}
	
	
	final static public GraphPath EMPTY_PATH = new GraphPath(new IEdge[0], null);
	
	/**
	 * get a default of this FieldDef
	 * @param generate
	 * @return the default value (i.e., Index, Index[0], ArrayList<>, primitive value) 
	 */
	public Object getDefaultValue(boolean generate) {
		if(isElementType()) {
			if(generate)
//				return Index.freshIndex(IndexSystem.generateUUID());
				return IndexSystem.generateFreshIndex();
			else 
				return null;
		} else {
			if(getType() instanceof IPathType) {
				return EMPTY_PATH;
			} else if(getType() instanceof ICollectionType) {
				if(generate)
					return new ArrayList<>();
				else 
					return Collections.emptyList();
			} else {
				// it should be data type node, but we didn't check
				return XmuCoreUtils.defaultValue(getType());				
			}
		}
	}

}
