package edu.ustb.sei.mde.bxcore;

import java.util.HashSet;
import java.util.Set;

import edu.ustb.sei.mde.bxcore.structures.Context;

public class TraceSystem {

	public TraceSystem() {
		traces = new HashSet<>();
	}
	
	private Set<Trace> traces;
	
	public void trace(Object key, Context source, Context view, Context sourcePost) {
		Trace t = new Trace(key, source, view, sourcePost);
		traces.add(t);
	}
	
	public void additiveMerge(TraceSystem ts) {
		traces.addAll(ts.traces);
	}

	public TraceSystem getCopy() {
		TraceSystem copy = new TraceSystem();
		copy.traces.addAll(traces);
		return copy;
	}

	public void trace(Trace trace) {
		traces.add(trace);
	}

	public static TraceSystem merge(TraceSystem[] interTraces) {
		TraceSystem ts = new TraceSystem();
		for(TraceSystem t : interTraces)
			ts.additiveMerge(t);
		return ts;
	}

	public Set<Trace> allTraces() {
		return this.traces;
	}

}
