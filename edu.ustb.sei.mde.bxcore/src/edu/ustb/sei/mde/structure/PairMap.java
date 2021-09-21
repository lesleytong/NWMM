package edu.ustb.sei.mde.structure;

import java.util.concurrent.ConcurrentHashMap;

//��Ԫ��
public class PairMap<F, S, V> {
	
	private ConcurrentHashMap<F, ConcurrentHashMap<S, V>> pairMap = new ConcurrentHashMap<>();
	
	public V get(F f, S s) {
		ConcurrentHashMap<S, V> map = pairMap.get(f);	//���ݼ�����ȡֵ
		if(map==null)
			return null;
		else 
			return map.get(s);	//���ݼ�����ȡֵ
	}
	
	public void put(F f, S s, V v) {
		ConcurrentHashMap<S, V> map = pairMap.get(f);
		if(map==null) {
			synchronized (pairMap) {
				map = pairMap.get(f);
				if(map==null) {
					map = new ConcurrentHashMap<S, V>();
					pairMap.put(f, map);
				}
			}
		}
		map.put(s, v);
	}
	
	public void clear() {
		pairMap.clear();
	}
}
