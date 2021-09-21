package edu.ustb.sei.mde.bxcore.structures;

import java.util.HashSet;
import java.util.Set;

public class Environment {

	public Environment() {
		lockedView = new HashSet<>();
	}

	private Set<Object> lockedView;
	
	public void lock(Object... objects) {
		for(Object o : objects) 
			lockedView.add(o);
	}
	
	public void unlock(Object... objects) {
		for(Object o : objects) 
			lockedView.remove(o);
	}
}
