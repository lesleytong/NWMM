package edu.ustb.sei.mde.graph.typedGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import edu.ustb.sei.mde.bxcore.Trace;
import edu.ustb.sei.mde.bxcore.TraceSystem;
import edu.ustb.sei.mde.bxcore.XmuCoreUtils;
import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.bxcore.exceptions.UninitializedException;
import edu.ustb.sei.mde.bxcore.structures.Context;
import edu.ustb.sei.mde.bxcore.structures.FieldDef;
import edu.ustb.sei.mde.bxcore.structures.Index;

public class IndexSystem {
	final static public Index INITIAL_INDEX = IndexSystem.generateFreshIndex();
	final static public Index TERMINAL_INDEX = IndexSystem.generateFreshIndex();

	protected Map<Object, IndexableElement> indexToObjectMap;

	public IndexSystem() {
		this.indexToObjectMap = new HashMap<>();
		// lyt-�ò���������
//		this.indexToObjectMap = new ConcurrentHashMap<>();
	}

	public void addIndex(Object index, IndexableElement obj) throws NothingReturnedException {
		if (index instanceof Index) {
			for (Object o : ((Index) index).getInternalIndices()) {
				addIndex(o, obj);
			}
		} else {
			registerIndex(index, obj);
			obj.appendIndexValue(index);
		}
//		Set<Object> indices = obj.getIndices();
//		indices.add(index);
	}

	/** ���������Ͷ����ӳ���ϵ����ӵ�ͼindexToObjectMap�� */
	public void registerIndex(Object index, IndexableElement obj) throws NothingReturnedException {
		Object prev = this.indexToObjectMap.put(index, obj); // put�ķ���������Value�����ͣ���IndexableElement���ͣ�

		if ((prev != null && prev != obj) || obj.isIndexable() == false)
			throw new NothingReturnedException("You are trying to map an index onto two elements");
	}

	/** ��e���ڲ���������eӳ���ϵ����indexToObjectMap����� */
	protected void clearIndex(IndexableElement e) {
		e.foreach(i -> {
			this.indexToObjectMap.remove(i);
		});
	}
	
	/** �����������ڲ���������ȡ���� */
	@SuppressWarnings("unchecked")
	public <T> T getElementByIndexObject(Index index) throws NothingReturnedException {
		T res = null;
		for (Object i : index.getInternalIndices()) {
			if ((res = (T) this.indexToObjectMap.get(i)) != null) { // ���ݼ���ȡֵ
				return res;
			}
		}

		// lyt-Ϊ�˿�����̨���ִ�������ʱ�䣬��ʱע�͵�
//		XmuCoreUtils.warning("No element is associated with this index");
		throw new NothingReturnedException();
	}

	static protected Object generateUUID() {
		return java.util.UUID.randomUUID();
	}

	static public Index generateFreshIndex() {
		return Index.freshIndex(generateUUID());
	}

	static public Index generateFreshViewIndex(String tkey, Context source, FieldDef<?> vkey, TraceSystem traceSys) {
		for (Trace trace : traceSys.allTraces()) {
			FieldDef<?> downKey = trace.view.getDownKeyFromUpstreamKey(vkey);
			if (downKey == null)
				continue;
			if ((tkey == null && trace.source.isDownstreamOf(source))
					|| (tkey != null && tkey.equals(trace.key) && trace.source.equals(source))) {
				try {
					return trace.view.getIndexValue(downKey);
				} catch (UninitializedException | NothingReturnedException e) {
					XmuCoreUtils.warning("A fresh new view index is generated while a traced index is expected", e);
					return generateFreshIndex();
				}
			}
		}
		return generateFreshIndex();
	}

	static public Index generateFreshSourceIndex(String tkey, Context source, Context view, FieldDef<?> skey,
			TraceSystem traceSys) {
		for (Trace trace : traceSys.allTraces()) {
			FieldDef<?> downKey = trace.sourcePost.getDownKeyFromUpstreamKey(skey);
			if (downKey == null)
				continue;
			if ((tkey == null && trace.source.isDownstreamOf(source) && trace.view.isDownstreamOf(view))
					|| (tkey != null && (tkey == XmuCoreUtils.ANY_INDEX || tkey.equals(trace.key))
							&& trace.source.equals(source) && trace.view.equals(view))) {
				try {
					return trace.sourcePost.getIndexValue(downKey);
				} catch (UninitializedException | NothingReturnedException e) {
					XmuCoreUtils.warning("A fresh new source index is generated while a traced index is expected", e);
					return generateFreshIndex();
				}
			}
		}
		return generateFreshIndex();
	}

}
