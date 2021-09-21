package edu.ustb.sei.mde.bxcore;

import java.util.logging.Level;

import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.bxcore.exceptions.UninitializedException;
import edu.ustb.sei.mde.bxcore.structures.Context;
import edu.ustb.sei.mde.bxcore.structures.ContextType;
import edu.ustb.sei.mde.bxcore.structures.FieldDef;
import edu.ustb.sei.mde.bxcore.structures.Index;

public class IndexingFunction {

	public IndexingFunction() {
	}
	
	public Context computeBackwardIndices(Context source, Context view) throws UninitializedException, NothingReturnedException {
		Context result = source.getType().createInstance();
		
		StringBuilder sb = new StringBuilder();
		
		for(FieldDef<?> sf : source.getType().orderedFields()) {
			Object o = source.getValue(sf);
			if(o==null) {
				sb.append("null");
			}
			else if(o instanceof Index) {
				sb.append(((Index) o).getFixedIndex());
			} else {
				sb.append(o);
			}
			sb.append("--");
		}
		
		for(FieldDef<?> vf : view.getType().orderedFields()) {
			Object o = view.getValue(vf);
			if(o==null) {
				sb.append("null");
			}
			else if(o instanceof Index) {
				sb.append(((Index) o).getFixedIndex());
			} else {
				sb.append(o);
			}
			sb.append("--");
		}
		
		String string = sb.toString();
		for(FieldDef<?> sf : source.getType().orderedFields()) {
			Object o = source.getValue(sf);
			if(o instanceof Index && ((Index)o).isFreshIndex())
				result.setValue(sf, Index.freshIndex(string+"@"+sf.getName()));
			else result.setValue(sf, o);
		}
		
		return result;
	}
	
	public Context computeForwardIndices(Context source, ContextType viewType) throws UninitializedException, NothingReturnedException {
		Context result = viewType.createInstance();
		
		StringBuilder sb = new StringBuilder();
		for(FieldDef<?> sf : source.getType().orderedFields()) {
			Object o = source.getValue(sf);
			if(o==null) {
				sb.append("null");
			}
			else if(o instanceof Index) {
				sb.append(((Index) o).getFixedIndex());
			} else {
				sb.append(o);
			}
			sb.append("--");
		}
		
		String string = sb.toString();
		
		for(FieldDef<?> vf : viewType.orderedFields()) {
			if(vf.isElementType()) {
				result.setValue(vf, Index.freshIndex(string+"@"+vf.getName()));
			} else {
//				throw new NothingReturnedException();
				XmuCoreUtils.log(Level.INFO, "IndexFunction ignores one view index", null);
			}
		}
		
		return result;
	}
	
	@Deprecated
	public Context computeForwardIndices(Context source, Context view) throws UninitializedException, NothingReturnedException {
		Context result = view.getType().createInstance();
		
		StringBuilder sb = new StringBuilder();
		for(FieldDef<?> sf : source.getType().orderedFields()) {
			Object o = source.getValue(sf);
			if(o==null) {
				sb.append("null");
			}
			else if(o instanceof Index) {
				sb.append(((Index) o).getFixedIndex());
			} else {
				sb.append(o);
			}
			sb.append("--");
		}
		
		String string = sb.toString();
		
		for(FieldDef<?> vf : view.getType().orderedFields()) {
			if(vf.isElementType()) {
				try {
					Object o = view.getValue(vf);
					if(o instanceof Index && ((Index)o).isFreshIndex())
						result.setValue(vf, Index.freshIndex(string+"@"+vf.getName()));
					else result.setValue(vf, o);
				} catch (Exception e) {
					result.setValue(vf, Index.freshIndex(string+"@"+vf.getName()));
				}
			} else {
				try {
					Object o = view.getValue(vf);
					result.setValue(vf, o);
				} catch (Exception e) {
				}
			}
		}
		
		return result;
	}

}
