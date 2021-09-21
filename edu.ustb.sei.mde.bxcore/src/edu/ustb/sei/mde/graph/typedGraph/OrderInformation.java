package edu.ustb.sei.mde.graph.typedGraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.bxcore.structures.Index;
import edu.ustb.sei.mde.structure.PairMap;
import edu.ustb.sei.mde.structure.Tuple2;

public class OrderInformation {
	
	private Set<Tuple2<Index,Index>> order = new HashSet<>();
	private PairMap<Index, Index, Boolean> cache = new PairMap<>();
	
	public void add(Index from, Index to) {
		order.add(Tuple2.make(from, to));
		cache.clear();
	}
	
	public boolean path(Index from, Index to) {
		Boolean result = null;
		if((result=cache.get(from, to))!=null) return result;	//返回值是Boolean
		Set<Index> visited = new HashSet<>();
		return path(from, to, visited);
	}
	
	private boolean path(Index from, Index to, Set<Index> visited) {
		if(visited.contains(from)) return false;	// 如果visited集合包含from，返回false
		visited.add(from);
		
		Boolean result = null;
		if((result=cache.get(from, to))!=null) return result;	//返回值是Boolean
		
		result = order.stream().anyMatch(o -> o.first.equals(from) && (o.second.equals(to) || path(o.second, to, visited)) );
		cache.put(from, to, result);
		
		return result;
	}
	
	public OrderInformation getCopy() {
		OrderInformation oi = new OrderInformation();
		oi.order.addAll(order);
		return oi;
	}
	
	public void merge(OrderInformation... merges) {
		for(OrderInformation oi : merges) 
			order.addAll(oi.order);	//Set集合的addAll()方法
	}
	
	public boolean validate() {
		List<Index> indices = order.stream().map(o -> o.first).distinct().collect(Collectors.toList());
		try {
			planOrder(indices);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
		
	public Index[] planOrder(List<Index> indices) throws NothingReturnedException {
		Model model = new Model();
		
		Map<Index, IntVar> varMap = new HashMap<>();
		
		indices.forEach(i -> varMap.put(i, model.intVar(0, indices.size()-1)));

		IntVar diff = model.intVar("diff", 0, indices.size()*indices.size());
		IntVar[] diffs = new IntVar[indices.size()];
		{
			int i=0;
			for(i=0;i<indices.size();i++) {
				diffs[i] = model.intAbsView(model.intOffsetView(varMap.get(indices.get(i)), -i));
			}
		}
		
		indices.forEach(f->{
			indices.forEach(t->{
				if(f==t) return;
				if(path(f,t)) {
					model.arithm(varMap.get(f), "<", varMap.get(t)).post();
				}
			});
		});
		
		model.allDifferent(varMap.values().toArray(new IntVar[varMap.size()])).post();
		
		model.sum(diffs, "=", diff).post();
		model.setObjective(false, diff);
		
		if(model.getSolver().solve()) {
			Index[] result = new Index[indices.size()];
			indices.forEach(i->{
				result[varMap.get(i).getValue()] = i;
			});
			return result;
		} else {
			throw new NothingReturnedException("Conflict in orders");
		}
	}
}
