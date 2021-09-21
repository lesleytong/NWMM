package edu.ustb.sei.mde.bxcore.structures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import edu.ustb.sei.mde.bxcore.TraceSystem;
import edu.ustb.sei.mde.graph.type.IPathType;
import edu.ustb.sei.mde.graph.type.IStructuralFeatureEdge;
import edu.ustb.sei.mde.graph.type.IType;
import edu.ustb.sei.mde.graph.type.TypeNode;
import edu.ustb.sei.mde.graph.typedGraph.IndexSystem;
import edu.ustb.sei.mde.structure.Tuple2;

public class ContextType {
	public final static ContextType EMPTY_TYPE = new ContextType();
	private Map<String,FieldDef<?>> fieldDefs;
	private List<FieldDef<?>> orderedKeys;
	
	public ContextType() {
		fieldDefs = new HashMap<>();
		orderedKeys = new ArrayList<>();
	}
	
	public <T extends IType> void addField(FieldDef<?> field) {
		fieldDefs.put(field.getName(), field);
		orderedKeys.add(field);
	}
	
	public <T extends IType> void addField(String name, T type) {
		FieldDef<T> field = new FieldDef<T>(name, type);
		addField(field);
	}
	
	public <T extends IType> void addField(String name, T type, boolean collection) {
		FieldDef<T> field = new FieldDef<T>(name, type);
		if(collection) field.setCollection(collection);
		addField(field);
	}
	
	public FieldDef<?> getField(String name) {
		return fieldDefs.get(name);
	}
	
	public boolean isSharable(String key) {
		FieldDef<?> var = this.fieldDefs.get(key);
		if(var==null) return false;
		else if(var.getType() instanceof TypeNode) return true;
		else if(var.getType() instanceof IStructuralFeatureEdge) 
			return ((IStructuralFeatureEdge)var.getType()).isUnique()==false;
		else return false;
	}

	public boolean isDefined(String name) {
		return fieldDefs.containsKey(name);
	}
	
	public Collection<FieldDef<?>> fields() {
		return orderedKeys;
	}
	
	public List<FieldDef<?>> orderedFields() {
		return orderedKeys;
	}
	
	public boolean equals(Object r) {
		if(r==null || !(r instanceof ContextType)) 
			return false;
		return fieldDefs.equals(((ContextType)r).fieldDefs);
	}
	
	public boolean isSuperOf(ContextType child) {
		return this==child || child.fieldDefs.entrySet().containsAll(this.fieldDefs.entrySet());
	}
	
	public boolean isTypeOf(Context c) {
		return c.getType()==this;
	}
	
	public Context createInstance() {
		Context c = new Context(this);
		return c;
	}
	
	
	
	public void initializeSource(Context result, Context source, Context view, TraceSystem traceSys) {
		this.fieldDefs.forEach((k,t)->{
			try {
				result.getValue(k);
			} catch(Exception e) {
				if(t.isElementType())
					result.setValue(k, IndexSystem.generateFreshSourceIndex(null, source, view, t, traceSys));
				else if(t.isPathType()) {
					IndexPath indexPath = new IndexPath(new Index[] {IndexSystem.generateFreshIndex()}, (IPathType) t.getType());
					result.setValue(k, indexPath);
				}
			}
		});
	}
	
	public void initializeView(Context result, Context source, TraceSystem traceSys) {
		this.fieldDefs.forEach((k,t)->{
			try {
				result.getValue(k);
			} catch(Exception e) {
				if(t.isElementType())
					result.setValue(k, IndexSystem.generateFreshViewIndex(null, source, t, traceSys));
				else if(t.isPathType()) {
					IndexPath indexPath = new IndexPath(new Index[] {IndexSystem.generateFreshIndex()}, (IPathType) t.getType());
					result.setValue(k, indexPath);
				}
			}
		});
	}
	
	public boolean isElementType(String key) {
		return this.fieldDefs.get(key).isElementType();
	}
	
	public ContextType part(String string, String[] keys) {
		ContextType t = new ContextType();
		int i=0;
		for(String k : keys) {
			t.addField(string+i, this.fieldDefs.get(k).getType());
			i++;
		}
		return t;
	}
	
	public ContextType part(String[] keys) {
		ContextType t = new ContextType();
		for(String k : keys) {
			t.addField(k, this.fieldDefs.get(k).getType());
		}
		return t;
	}
	
	public ContextType part(Tuple2<String,String>[] mappings) {
		ContextType t = new ContextType();
		for(Tuple2<String, String> m : mappings) {
			t.addField(m.second, this.fieldDefs.get(m.first).getType());
		}
		return t;
	}

	public ContextType getCopy() {
		ContextType copy = new ContextType();
		this.fields().forEach(f->copy.addField(f.getName(), f.getType()));
		return copy;
	}

	private List<FieldDef<?>> singleValuedFields;
	public List<FieldDef<?>> singleValuedFields() {
		if(singleValuedFields==null) {
			singleValuedFields = fields().stream().filter(f->f.isCollection()==false).collect(Collectors.toList());
		}
		return singleValuedFields;
	}
}
