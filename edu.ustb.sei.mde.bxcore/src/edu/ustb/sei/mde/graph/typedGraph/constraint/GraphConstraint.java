package edu.ustb.sei.mde.graph.typedGraph.constraint;

import java.util.List;

import edu.ustb.sei.mde.bxcore.structures.Context;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;

@FunctionalInterface
public interface GraphConstraint {
	
	// 接口中方法是public abstract
	public ConstraintStatus check(TypedGraph sourceGraph, Context sourceContext, TypedGraph viewGraph, Context viewContext);
	
	// Java 8，接口中可以有static方法
	static GraphConstraint and(GraphConstraint... cons) {
		return (gs,cs, gv, cv)-> {
			return java.util.stream.Stream.of(cons).map(cc->cc.check(gs,cs, gv, cv)).reduce(ConstraintStatus.sat, GraphConstraint::mergeStatus);
		};
	}
	
	// 
	static GraphConstraint and(List<GraphConstraint> cons) {
		return (gs,cs, gv, cv)->{
			return cons.stream().map(cc->cc.check(gs,cs, gv, cv)).reduce(ConstraintStatus.sat, GraphConstraint::mergeStatus);
		};
	}
	
	// 接口中数据域是public static final 
	static GraphConstraint TRUE = (gs,cs, gv, cv)->ConstraintStatus.sat;
	static GraphConstraint FALSE = (gs,cs, gv, cv)->ConstraintStatus.enforceable;
	static GraphConstraint UNENFORCEABLE = (gs,cs, gv, cv)->ConstraintStatus.unenforceable;
	
	// 
	static enum ConstraintStatus {
		sat, // the source and view satisfied this constraint
		enforceable, // the source and view do not satisfy this constraint, but the constraint is enforceable
		unenforceable // the source and view do not satisfy this constraint, but the constraint is not enforceable
	}
	
	public static ConstraintStatus mergeStatus(ConstraintStatus l, ConstraintStatus r) {
		if(l==ConstraintStatus.sat && r==ConstraintStatus.sat) return l;
		else if(l==ConstraintStatus.enforceable && r!=ConstraintStatus.unenforceable) return l;
		else if(r==ConstraintStatus.enforceable && l!=ConstraintStatus.unenforceable) return r;
		else return ConstraintStatus.unenforceable;
	}
}


