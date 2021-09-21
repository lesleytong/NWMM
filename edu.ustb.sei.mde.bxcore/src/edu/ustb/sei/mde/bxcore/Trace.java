package edu.ustb.sei.mde.bxcore;

import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.bxcore.exceptions.UninitializedException;
import edu.ustb.sei.mde.bxcore.structures.Context;

public class Trace {
	public Object key;
	public Trace(Object key, Context source, Context view, Context sourcePost) {
		super();
		this.key = key;
		this.source = source;
		this.view = view;
		this.sourcePost = sourcePost;
	}
	
	public Trace(Object key, Context source, Context view) {
		super();
		this.key = key;
		this.source = source;
		this.view = view;
		this.sourcePost = source;
	}
	
	
	public Context source;
	public Context view;
	public Context sourcePost;
	
	public boolean equals(Object right) {
		if(right==null || !(right instanceof Trace)) return false;
		Trace rk = (Trace)right;
		if((key==null && rk.key!=null) || (key!=null && !key.equals(rk.key))) return false;
		if(!source.equals(rk.source)) return false;
		if(!view.equals(rk.view)) return false;
		if(!sourcePost.equals(rk.sourcePost)) return false;
		return true;
	}
	
	public int hashCode() {
		int k = key==null ? 0 : key.hashCode();
		int sk = source.hashCode();
		int vk = view.hashCode();
		return (k&0xFF) | (sk&0xFF<<8)| (vk&0xFF<<16);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(key.toString()+"[");
		source.keys().forEach(k->{
			try {
				sb.append(k+":"+source.getValue(k));
			} catch (UninitializedException | NothingReturnedException e) {
				sb.append(k+": UNDEFINED");
			}
			sb.append(",");
		});
		
		sb.append("]=>[");
		
		view.keys().forEach(k->{
			try {
				sb.append(k+":"+view.getValue(k));
			} catch (UninitializedException | NothingReturnedException e) {
				sb.append(k+": UNDEFINED");
			}
			sb.append(",");
		});
		
		sb.append("]");
		
		return sb.toString();
	}
}