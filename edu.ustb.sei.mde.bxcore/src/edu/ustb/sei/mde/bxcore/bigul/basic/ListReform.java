package edu.ustb.sei.mde.bxcore.bigul.basic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.ustb.sei.mde.bxcore.bigul.BijectiveTransformation;
import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.structure.Tuple2;

public class ListReform<T> extends BijectiveTransformation<List<T>,Tuple2<T,List<T>>> {

	@Override
	public List<T> backward(Tuple2<T, List<T>> v) throws NothingReturnedException {
		Tuple2<T, List<T>> empty = Tuple2.emptyTuple();
		if(v==empty) {
			return Collections.emptyList();
		} else {
			List<T> sum = new ArrayList<T>(v.second.size()+1);
			sum.add(v.first);
			sum.addAll(v.second);
			return sum;
		}
	}

	@Override
	public Tuple2<T, List<T>> forward(List<T> s) throws NothingReturnedException {
		if(s.isEmpty()) {
			return Tuple2.emptyTuple();
		} else {
			T head = s.get(0);
			List<T> tails = new ArrayList<T>(s.size()-1);
			for(int i=1;i<s.size();i++)
				tails.add(s.get(i));
			return new Tuple2<T,List<T>>(head,tails);
		}
	}

}
