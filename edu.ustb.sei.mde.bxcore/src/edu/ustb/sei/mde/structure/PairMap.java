package edu.ustb.sei.mde.structure;

import java.util.concurrent.ConcurrentHashMap;

//三元组
public class PairMap<F, S, V> {
	
	private ConcurrentHashMap<F, ConcurrentHashMap<S, V>> pairMap = new ConcurrentHashMap<>();
	
	public V get(F f, S s) {
		ConcurrentHashMap<S, V> map = pairMap.get(f);	//根据键，获取值
		if(map==null)
			return null;
		else 
			return map.get(s);	//根据键，获取值
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
