package edu.ustb.sei.mde.structure;

//��Ԫ��
public class Tuple2<F, S> {
	
	public final F first;	
	// lyt-Ϊ�˼��ǿ����Ļ���ͻ����ʱȥ��final���η�
	public S second;
	
	public Tuple2(F first, S second) {
		super();
		this.first = first;
		this.second = second;
	}
	
	static public <F,S> Tuple2<F,S> make(F f, S s) {
		return new Tuple2<F, S>(f, s);
	}
	
	@Override
	public int hashCode() {
		int firstCode = first==null ? 0 : first.hashCode();
		int secondCode = second==null ? 0 : second.hashCode();
		return ((firstCode&0xFFFF)<<16) & (secondCode&0xFFFF);
	}
	
	//�ж�����Ԫ����ȵķ���<a,b>=<c, d>���ҽ���a=c��b=d
	@SuppressWarnings("rawtypes")
	public boolean equals(Object o) {
		if(o==null || ! (o instanceof Tuple2))
			return false;
		else return ((first!=null && first.equals(((Tuple2)o).first)) || (first==null && ((Tuple2)o).first==null))
				&& ((second!=null && second.equals(((Tuple2)o).second)) || (second==null && ((Tuple2)o).second==null));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	static private final Tuple2 EMPTY = new Tuple2(null, null);
	
	@SuppressWarnings("unchecked")
	static public <X,Y> Tuple2<X,Y> emptyTuple() {
		return (Tuple2<X,Y>)EMPTY;
	}

	
	public String toString() {
		return "<"+first+","+second+">";
	}
	
	public Tuple2<F,S> replaceFirst(F f) {
		return Tuple2.make(f, second);
	}
	
	// ע��replaceSecond�Ƿ���һ���¶���
	public Tuple2<F,S> replaceSecond(S s) {
		return Tuple2.make(first, s);
	}
}
