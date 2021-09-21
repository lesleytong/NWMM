package edu.ustb.sei.mde.bxcore.dsl.infer;

import java.util.List;

import org.chocosolver.solver.constraints.Propagator;
import org.chocosolver.solver.constraints.PropagatorPriority;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.ESat;
import org.chocosolver.util.objects.setDataStructures.iterable.IntIterableRangeSet;
import org.chocosolver.util.objects.setDataStructures.iterable.IntIterableSet;

public class PropTypeCast extends Propagator<IntVar> {
	public PropTypeCast(IntVar left, IntVar right, List<Object> typeList, boolean[][] superTypeTable, int sort) {
		super(new IntVar[] {left, right}, PropagatorPriority.LINEAR, false);
		this.left = left;
		this.right = right;
		this.typeList = typeList;
		this.sort = sort;
		this.superTypeTable = superTypeTable;
	}

	private IntVar left;
	private IntVar right;
	
	@SuppressWarnings("unused")
	private List<Object> typeList;
	private int sort;
	private boolean[][] superTypeTable;

	@Override
	public ESat isEntailed() {
		if(left.isInstantiated() && right.isInstantiated()) {
			if(sort==TypeEqual.EQUAL) {
				if(left.getValue()==right.getValue()) return ESat.TRUE;
			} else if(sort==TypeEqual.LEFT_ABSTRACT) {
				if(superTypeTable[left.getValue()][right.getValue()]) return ESat.TRUE;
			} else if(sort==TypeEqual.RIGHT_ABSTRACT) {
				if(superTypeTable[right.getValue()][left.getValue()]) return ESat.TRUE;
			}
			return ESat.FALSE;
		} else {
			if(left.isInstantiated()) {
				int lv = left.getValue();
				if(sort==TypeEqual.EQUAL) {
					if(!right.contains(lv)) return ESat.FALSE;
				} else if(sort==TypeEqual.LEFT_ABSTRACT) {
					for(int rv = right.getLB();rv<=right.getUB();rv = right.nextValue(rv)) {
						if(superTypeTable[lv][rv])
							return ESat.UNDEFINED;
					}
					return ESat.FALSE;
				} else if(sort==TypeEqual.RIGHT_ABSTRACT) {
					for(int rv = right.getLB();rv<=right.getUB();rv = right.nextValue(rv)) {
						if(superTypeTable[rv][lv])
							return ESat.UNDEFINED;
					}
					return ESat.FALSE;
				}
			} else if(right.isInstantiated()) {
				int rv = right.getValue();
				
				if(sort==TypeEqual.EQUAL) {
					if(!left.contains(rv)) return ESat.FALSE;
				} else if(sort==TypeEqual.LEFT_ABSTRACT) {
					for(int lv = left.getLB(); lv<=left.getUB(); lv = left.nextValue(lv)) {
						if(superTypeTable[lv][rv]) return ESat.UNDEFINED;
					}
					return ESat.FALSE;
				} else if(sort==TypeEqual.RIGHT_ABSTRACT) {
					for(int lv = left.getLB(); lv<=left.getUB(); lv = left.nextValue(lv)) {
						if(superTypeTable[rv][lv]) return ESat.UNDEFINED;
					}
					return ESat.FALSE;
				}
			} else {
				if(sort==TypeEqual.EQUAL) {
					if(left.getLB() > right.getUB() || right.getLB() > left.getUB()) 
						return ESat.FALSE;
				}
			}
			return ESat.UNDEFINED;
		}
	}

	@Override
	public void propagate(int vid) throws ContradictionException {
		if(vid==0) {
			if (left.isInstantiated()) {
				int lv = left.getValue();
				if (sort == TypeEqual.EQUAL) {
					right.instantiateTo(lv, this);
				} else if (sort == TypeEqual.LEFT_ABSTRACT) {
					IntIterableSet removed = new IntIterableRangeSet(right.getLB(), right.getUB());
					for (int rv = right.getLB(); rv <= right.getUB(); rv = right.nextValue(rv)) {
						if (superTypeTable[lv][rv])
							removed.remove(rv);
					}
					right.removeValues(removed, this);
				} else if (sort == TypeEqual.RIGHT_ABSTRACT) {
					IntIterableSet removed = new IntIterableRangeSet(right.getLB(), right.getUB());
					for (int rv = right.getLB(); rv <= right.getUB(); rv = right.nextValue(rv)) {
						if (superTypeTable[rv][lv])
							removed.remove(rv);
					}
					right.removeValues(removed, this);
				}
			}
		} else {
			if (right.isInstantiated()) {
				int rv = right.getValue();
				if (sort == TypeEqual.EQUAL) {
					left.instantiateTo(rv, this);
				} else if (sort == TypeEqual.LEFT_ABSTRACT) {
					IntIterableSet removed = new IntIterableRangeSet(left.getLB(), left.getUB());
					for (int lv = left.getLB(); lv <= left.getUB(); lv = left.nextValue(lv)) {
						if (superTypeTable[lv][rv])
							removed.remove(lv);
					}
					left.removeValues(removed, this);
				} else if (sort == TypeEqual.RIGHT_ABSTRACT) {
					IntIterableSet removed = new IntIterableRangeSet(left.getLB(), left.getUB());
					for (int lv = left.getLB(); lv <= left.getUB(); lv = left.nextValue(lv)) {
						if (superTypeTable[rv][lv])
							removed.remove(lv);
					}
					left.removeValues(removed, this);
				}
			}
		}
	}

}
