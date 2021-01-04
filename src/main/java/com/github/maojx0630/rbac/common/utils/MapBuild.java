package com.github.maojx0630.rbac.common.utils;

import java.util.HashMap;

/**
 * 用于快速构建 java.util.HashMap
 * <br/>
 * @author MaoJiaXing
 * @date 2020-12-29 11:41 
 */
public class MapBuild<K,V> {

	private HashMap<K,V> map;

	private MapBuild() {
		map=new HashMap<>();
	}

	public static <K,V> MapBuild<K,V> createKv(){
		return new MapBuild<>();
	}

	public static <V> MapBuild<String,V> createStr(){
		return new MapBuild<>();
	}

	public static MapBuild<String,Object> create(){
		return new MapBuild<>();
	}

	public HashMap<K,V> build(){
		return map;
	}

	public MapBuild<K,V> put(K k, V v){
		map.put(k,v);
		return this;
	}

	public MapBuild<K,V> remove(K k){
		map.remove(k);
		return this;
	}
}
