package edu.ustb.sei.mde.bxcore.structures;

import java.util.Collection;

import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.bxcore.exceptions.UninitializedException;
import edu.ustb.sei.mde.structure.Tuple2;

/**
 * We concern the mappings of indexable elements only 
 * @author hexiao
 *
 */
public class ContextMapping {
	public ContextMapping(Context upstream, Context downstream, Tuple2<FieldDef<?>,FieldDef<?>>[] keyMappings) {
		super();
		this.upstream = upstream;
		this.downstream = downstream;
		this.keyMappings = keyMappings;
	}
	protected Context downstream;
	protected Context upstream;
	protected Tuple2<FieldDef<?>,FieldDef<?>>[] keyMappings;

	public Object up(FieldDef<?> downKey) throws UninitializedException, NothingReturnedException {
		for(Tuple2<FieldDef<?>, FieldDef<?>> m : keyMappings) {
			if(downKey.equals(m.second)) 
				return this.upstream.getValue(m.first);
		}
		throw new NothingReturnedException();
	}


	public Object down(FieldDef<?> upKey) throws UninitializedException, NothingReturnedException {
		for(Tuple2<FieldDef<?>, FieldDef<?>> m : keyMappings) {
			if(upKey.equals(m.first)) 
				return this.downstream.getValue(m.second);
		}
		throw new NothingReturnedException();
	}

	public FieldDef<?> upKey(FieldDef<?> downKey) {
		for(Tuple2<FieldDef<?>,FieldDef<?>> m : this.keyMappings) {
			if(m.second.equals(downKey))
				return m.first;
		}
		return null;
	}

	public FieldDef<?> downKey(FieldDef<?> upKey) {
		for(Tuple2<FieldDef<?>,FieldDef<?>> m : this.keyMappings) {
			if(m.first.equals(upKey))
				return m.second;
		}
		return null;
	}
	
	static public ContextMapping expend(Context up, Context down) {
		ContextType upType = up.getType();
		Collection<FieldDef<?>> fields = upType.fields();

		@SuppressWarnings("unchecked")
		Tuple2<FieldDef<?>,FieldDef<?>>[] maps = new Tuple2[fields.size()];
		
		ContextType downType = down.getType();
		
		int i=0;
		for(FieldDef<?> f : fields) {
			maps[i] = new Tuple2<FieldDef<?>, FieldDef<?>>(f, downType.getField(f.getName()));
			i++;
		}
		
		return new ContextMapping(up, down, maps);
	}
	
	static public ContextMapping transform(Context up, Context down, Tuple2<String, String>[] keys) {
		ContextType upType = up.getType();
		@SuppressWarnings("unchecked")
		Tuple2<FieldDef<?>,FieldDef<?>>[] maps = new Tuple2[keys.length];
		ContextType downType = down.getType();
		for(int i=0;i<keys.length;i++) {
			maps[i] = new Tuple2<FieldDef<?>, FieldDef<?>>(upType.getField(keys[i].first), downType.getField(keys[i].second));
		}
		return new ContextMapping(up, down, maps);
	}

	public static ContextMapping transform(ContextType upType, Context down, Tuple2<String, String>[] keys) {
		@SuppressWarnings("unchecked")
		Tuple2<FieldDef<?>,FieldDef<?>>[] maps = new Tuple2[keys.length];
		ContextType downType = down.getType();
		for(int i=0;i<keys.length;i++) {
			maps[i] = new Tuple2<FieldDef<?>, FieldDef<?>>(upType.getField(keys[i].first), downType.getField(keys[i].second));
		}
		return new ContextMapping(null, down, maps);
	}


	public static ContextMapping include(Context up, Context down) {
		ContextType downType = down.getType();
		Collection<FieldDef<?>> fields = downType.fields();

		@SuppressWarnings("unchecked")
		Tuple2<FieldDef<?>,FieldDef<?>>[] maps = new Tuple2[fields.size()];
		
		ContextType upType = up.getType();
		
		int i=0;
		for(FieldDef<?> f : fields) {
			maps[i] = new Tuple2<FieldDef<?>, FieldDef<?>>(upType.getField(f.getName()),f);
			i++;
		}
		
		return new ContextMapping(up, down, maps);
	}
}