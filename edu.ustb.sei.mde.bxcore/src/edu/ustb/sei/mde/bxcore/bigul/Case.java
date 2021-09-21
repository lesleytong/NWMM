package edu.ustb.sei.mde.bxcore.bigul;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import edu.ustb.sei.mde.bxcore.exceptions.BidirectionalTransformationDefinitionException;
import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.structure.Tuple2;
import edu.ustb.sei.mde.structure.Tuple3;

public class Case<S, V> extends BidirectionalTransformation<S, V> {
	
	List<Tuple3<BiFunction<S,V,Boolean>,BidirectionalTransformation<S,V>,Function<S,Boolean>>> branches = new ArrayList<Tuple3<BiFunction<S,V,Boolean>,BidirectionalTransformation<S,V>,Function<S,Boolean>>>();
	List<Tuple2<BiFunction<S,V,Boolean>,BiFunction<S,V,S>>> adaptions = new ArrayList<Tuple2<BiFunction<S,V,Boolean>,BiFunction<S,V,S>>>();
	
	@Override
	public V forward(S s) throws NothingReturnedException {
		try {
			for(int i=0;i<branches.size();i++) {
				Tuple3<BiFunction<S,V,Boolean>,BidirectionalTransformation<S,V>,Function<S,Boolean>> branch = branches.get(i);
				V v = null;
				try {
					v = getBranch(branch,s);
				} catch(NothingReturnedException e) {
					continue;
				}
				for(int j=0;j<i;j++) {
					Tuple3<BiFunction<S,V,Boolean>,BidirectionalTransformation<S,V>,Function<S,Boolean>> prevBranch = branches.get(j);
					if(prevBranch.first.apply(s, v)) 
						throw new BidirectionalTransformationDefinitionException();
				}
				return v;
			}
		} catch(BidirectionalTransformationDefinitionException e) {
		}
		// no branch can be applied
		throw new NothingReturnedException();
	}

	@Override
	public S backward(S s, V v) throws NothingReturnedException {
		return plainBackward(s, v, true);
	}
	
	protected S plainBackward(S s, V v, boolean adaption) throws NothingReturnedException {
		for(int i=0;i<branches.size();i++) {
			Tuple3<BiFunction<S,V,Boolean>,BidirectionalTransformation<S,V>,Function<S,Boolean>> branch = branches.get(i);
			if(branch.first.apply(s, v)) {
				S sp = branch.second.backward(s, v);
				if(branch.first.apply(sp, v) && (branch.third==null || branch.third.apply(sp))) {
					for(int j=0;j<i;j++) {
						Tuple3<BiFunction<S,V,Boolean>,BidirectionalTransformation<S,V>,Function<S,Boolean>> prevBranch = branches.get(j);
						try {
							@SuppressWarnings("unused")
							V vp = getBranch(prevBranch, sp);
						} catch(NothingReturnedException e) {
							continue;
						}
						throw new NothingReturnedException();
					}
					return sp;
				} else
					throw new NothingReturnedException();
			}
		}
		// no branch can be applied, try adaption
		if(adaption) {
			for(int i=0;i<this.adaptions.size();i++) {
				Tuple2<BiFunction<S,V,Boolean>,BiFunction<S,V,S>> adaptionBranch = adaptions.get(i);
				if(adaptionBranch.first.apply(s, v)) {
					S sa = adaptionBranch.second.apply(s, v);
					return plainBackward(sa, v, false);
				}
			}
		}
		
		throw new NothingReturnedException();
	}
	
	protected V getBranch(Tuple3<BiFunction<S,V,Boolean>,BidirectionalTransformation<S,V>,Function<S,Boolean>> branch, S s)
			throws NothingReturnedException {
		if(branch.third==null || branch.third.apply(s)) {
			V v = branch.second.forward(s);
			if(branch.first.apply(s, v)) 
				return v;
			else 
				throw new NothingReturnedException();
		} else 
			throw new NothingReturnedException();
	}

}
