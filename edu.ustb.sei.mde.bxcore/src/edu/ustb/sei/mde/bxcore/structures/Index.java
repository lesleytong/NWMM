package edu.ustb.sei.mde.bxcore.structures;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import edu.ustb.sei.mde.bxcore.XmuCoreUtils;

public class Index implements Cloneable {

	/**
	 * If freshIndex is true, it means that this index is a newly allocated index
	 * for a non-existing element
	 */
	private boolean freshIndex = false;
	private Set<Object> internalIndices = new HashSet<>();
	// lyt-�ò���������
//	private Set<Object> internalIndices = new CopyOnWriteArraySet<>();
	private Object fixedIndex = null;
	
	/**
	 * �ж�����index�Ƿ���ȣ���һ�пռ��׳��쳣���н����򷵻�true��û�н�����false
	 * �������l.getIndex().equals(r.getIndex())
	 * ������@Override
	 */
	@Override
	public boolean equals(Object index) {
		if (index instanceof Index) {
			if (this.internalIndices.isEmpty() || ((Index) index).internalIndices.isEmpty()) {
				XmuCoreUtils.warning("Cannot decide wether two Indexs that have empty internal index sets are equal");
			}
			// disjoint(c1: Collection, c1: Collection): boolean�����c1��c2û�й�ͬԪ�أ��򷵻�Ϊ��
			// ���c1��c2�й�ͬ��Ԫ�أ�disjoint����Ϊ�٣�!disjoint����Ϊ��
			return !Collections.disjoint(this.internalIndices, ((Index) index).internalIndices);
		} else
			return false;
	}

	
	// lyt-��дhashCode()-��һ���汾
//	@Override
//	public int hashCode() {
//		// Object��Ĭ���Ƿ��ض�����ڴ��ַ
//		// �������Ĭ�ϵĻ������ж�hashCode���ǲ�ͬ��û�к���ȥִ��equals
//		// ����HashMap��get�޷�ʵ��internalIndices�н������ҵ�
//		// ���������Ļ�ɢ�о�û�����ˣ�����ôд��hashCode����ֵֻ��Ϊint��
//		return 1;
//		
//	}
	
//	// lyt-��дhashCode()-�ڶ����汾
//	@Override
//	public int hashCode() {
//		return this.internalIndices.hashCode();
//	}
	
	public boolean isFreshIndex() {
		return this.freshIndex;
	}

	
//	public Set<Object> internalIndices() {
//		return internalIndices;
//	}
	// lyt-�ĳ�getInternalIndicies��������֤Index��hashCode
	public Set<Object> getInternalIndices() {
		return internalIndices;
	}

	// �������index.merge(e.getIndex())
	public void merge(Index index) {
		if (this == index)
			return; // �����ͬһ��index����ֱ�ӷ���

		if (this.freshIndex != index.freshIndex)
			this.freshIndex = true;

		if (this.fixedIndex == null)
			this.fixedIndex = index.fixedIndex;
		
//		// lyt-��ʱ��ӣ�Ϊ����֤�滻���ڲ���������hashCode
//		System.out.println(this.internalIndices.hashCode());
//		System.out.println(index.internalIndices.hashCode());

		internalIndices.addAll(index.internalIndices); // ���ϵĲ�
		
//		// lyt-��ʱ��ӣ�Ϊ����֤�滻���ڲ���������hashCode
//		System.out.println(this.internalIndices.hashCode());
//		System.out.println(index.internalIndices.hashCode());
		
	}

	private Index() {
		super();
	}

	private Index(Object internalIndex) {
		super();
		this.internalIndices.add(internalIndex);
	}

	public static Index index() {
		return new Index();
	}

	public static Index index(Object i) {
		if (i instanceof Index)
			throw new RuntimeException();

		Index idx = new Index(i);
		idx.fixedIndex = i;

		return idx;
	}

	public static Index freshIndex(Object i) {
		if (i instanceof Index)
			throw new RuntimeException();

		Index idx = new Index(i);
		idx.freshIndex = true;
		idx.fixedIndex = Index.class;

		return idx;
	}

	// ��¡����ԭ����һ���Ķ��󣬲����������ĵ�ַ�����µ����á�����ʵ��Cloneable�ӿڡ�
	public Index clone() {
		Index copy = new Index();
		copy.internalIndices.addAll(internalIndices);
		copy.freshIndex = freshIndex;
		copy.fixedIndex = fixedIndex;
		return copy;
	}

	/** ��ӽ�internalIndices���� */
	public void add(Object idx) {
		if (idx instanceof Index)
			throw new RuntimeException();
		if (this.fixedIndex == null)
			this.fixedIndex = idx;

		this.internalIndices.add(idx);
	}

	public boolean isEmpty() {
		return this.internalIndices.isEmpty();
	}

	public Object getFixedIndex() {
		return fixedIndex;
	}
	

	@Override
	public String toString() {
		return "@" + this.internalIndices;
	}

}
