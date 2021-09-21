package edu.ustb.sei.mde.bxcore.dsl.infer;

import java.util.List;

import org.chocosolver.solver.constraints.Propagator;
import org.chocosolver.solver.constraints.PropagatorPriority;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.SetVar;
import org.chocosolver.solver.variables.delta.ISetDeltaMonitor;
import org.chocosolver.solver.variables.events.PropagatorEventType;
import org.chocosolver.solver.variables.events.SetEventType;
import org.chocosolver.util.ESat;
import org.chocosolver.util.objects.setDataStructures.ISetIterator;

import edu.ustb.sei.mde.structure.Tuple2;

public class PropSetMapping extends Propagator<SetVar> {

	private List<Tuple2<Integer,Integer>> mappings;
	
	public PropSetMapping(SetVar from, SetVar to, List<Tuple2<Integer,Integer>> mappings) {
		super(new SetVar[] {from, to}, PropagatorPriority.LINEAR, true);
		this.mappings = mappings;
		
		sdm = new ISetDeltaMonitor[2];
        for (int i = 0; i < 2; i++) {
            sdm[i] = this.vars[i].monitorDelta(this);
        }
//        elementForced = element -> {
//            for (int i = 0; i < n; i++) {
//                vars[i].force(element, this);
//            }
//        };
//        elementRemoved = element -> {
//            for (int i = 0; i < n; i++) {
//                vars[i].remove(element, this);
//            }
//        };
	}

	//***********************************************************************************
    // VARIABLES
    //***********************************************************************************

    private ISetDeltaMonitor[] sdm;

    //***********************************************************************************
    // METHODS
    //***********************************************************************************

    @Override
    public void propagate(int evtmask) throws ContradictionException {
        if (PropagatorEventType.isFullPropagation(evtmask)) {
			ISetIterator iter = null;
			
			iter = vars[0].getUB().iterator();
            while (iter.hasNext()){
                int j = iter.nextInt();
                if(!vars[1].getUB().contains(to(j))){
                	vars[0].remove(j, this);
                }
			}
            iter = vars[0].getLB().iterator();
            while (iter.hasNext()){
                int j = iter.nextInt();
                vars[1].force(to(j), this);
            }
            
            iter = vars[1].getUB().iterator();
            while (iter.hasNext()){
                int j = iter.nextInt();
                if(!vars[0].getUB().contains(from(j))){
                	vars[1].remove(j, this);
                }
			}
            iter = vars[1].getLB().iterator();
            while (iter.hasNext()){
                int j = iter.nextInt();
                vars[0].force(from(j), this);
            }
        }
        for (int i = 0; i < 2; i++) {
            sdm[i].unfreeze();
        }
    }

    private int from(int j) {
		for(Tuple2<Integer, Integer> t : this.mappings) {
			if(t.second==j) return t.first;
		}
		return -1;
	}

	private int to(int j) {
		for(Tuple2<Integer, Integer> t : this.mappings) {
			if(t.first==j) return t.second;
		}
		return -1;
	}

	@Override
    public void propagate(int idxVarInProp, int mask) throws ContradictionException {
        sdm[idxVarInProp].freeze();
        sdm[idxVarInProp].forEach(element->{
        	if(idxVarInProp==0) {
        		vars[0].force(element, this);
        		vars[1].force(to(element), this);
        	} else {
        		vars[0].force(from(element), this);
        		vars[1].force(element, this);
        	}
        }, SetEventType.ADD_TO_KER);
        sdm[idxVarInProp].forEach(element->{
        	if(idxVarInProp==0) {
        		vars[0].remove(element, this);
        		vars[1].remove(to(element), this);
        	} else {
        		vars[0].remove(from(element), this);
        		vars[1].remove(element, this);
        	}
        }, SetEventType.REMOVE_FROM_ENVELOPE);
        sdm[idxVarInProp].unfreeze();
    }

    @Override
    public ESat isEntailed() {
        boolean allInstantiated = true;
        if (!vars[0].isInstantiated()) {
        	allInstantiated = false;
        }
        if (!vars[1].isInstantiated()) {
        	allInstantiated = false;
        }

        ISetIterator iter = null;
        
        iter = vars[0].getLB().iterator();
        while (iter.hasNext()){
        	int j = iter.nextInt();
        	if (!vars[0].getUB().contains(j)) {
        		return ESat.FALSE;
        	}
        	if (!vars[1].getUB().contains(to(j))) {
        		return ESat.FALSE;
        	}
        }
        
        iter = vars[1].getLB().iterator();
        while (iter.hasNext()){
        	int j = iter.nextInt();
        	if (!vars[0].getUB().contains(from(j))) {
        		return ESat.FALSE;
        	}
        	if (!vars[1].getUB().contains(j)) {
        		return ESat.FALSE;
        	}
        }

        if (allInstantiated) {
            return ESat.TRUE;
        }
        return ESat.UNDEFINED;
    }
}
