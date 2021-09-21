package edu.ustb.sei.mde.structure;
/**
 * 三元组类
 */
public class Tuple3<F, S, T> {
	
	public final F first;
	public final S second;
	public final T third;
	
	/** 构造方法 */
	public Tuple3(F first, S second, T third) {
		super();
		this.first = first;
		this.second = second;
		this.third = third;
	}
	
	/** 泛型方法，创建三元组对象 */
	static public <F, S, T> Tuple3<F, S, T> make(F f, S s, T t) {
		return new Tuple3<F, S, T>(f, s, t);
	}
	
	public Tuple3<F, S, T> replaceFirst(F f) {
		return Tuple3.make(f, second, third);
	}
	
	public Tuple3<F, S, T> replaceSecond(S s) {
		return Tuple3.make(first, s, third);
	}
	
	public Tuple3<F, S, T> replaceThird(T t) {
		return Tuple3.make(first, second, t);
	}
	
	@Override
	public int hashCode() {
		int firstCode = first==null ? 0 : first.hashCode();
		int secondCode = second==null ? 0 : second.hashCode();
		int thirdCode = third==null ? 0 : third.hashCode();
		return ((firstCode&0xFFFF)<<20) & ((secondCode&0xFFFF)<<10) & (thirdCode&0xFFFF);
	}
	
	@SuppressWarnings("rawtypes")
	public boolean equals(Object o) {
		if(o==null || ! (o instanceof Tuple3))
			return false;
		else return ((first!=null && first.equals(((Tuple3)o).first)) || (first==null && ((Tuple3)o).first==null))
				&& ((second!=null && second.equals(((Tuple3)o).second)) || (second==null && ((Tuple3)o).second==null))
				&& ((third!=null && third.equals(((Tuple3)o).third)) || (third==null && ((Tuple3)o).third==null));
	}
}
