package edu.ustb.sei.mde.graph.pattern.solving;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.ustb.sei.mde.graph.type.ITypeNode;
import edu.ustb.sei.mde.graph.type.TypeGraph;
import edu.ustb.sei.mde.graph.type.TypeNode;
import edu.ustb.sei.mde.graph.typedGraph.ITypedEdge;
import edu.ustb.sei.mde.graph.typedGraph.ITypedNode;
import edu.ustb.sei.mde.graph.typedGraph.TypedNode;
import edu.ustb.sei.mde.graph.typedGraph.ValueNode;

public abstract class Constraint {
	protected ConstraintPriority priority;
	protected List<Variable<?>> allVariables = new ArrayList<Variable<?>>();
	public ConstraintPriority getPriority() {
		return this.priority;
	}
	
	public void apply(Decision decision, SolverContext c) {
		apply(null,null,decision,c);
	};
	
	abstract public void apply(Variable<?> var, List<?> removedValues, Decision decision, SolverContext c);
	
	public List<Variable<?>> getVariables() {
		return allVariables;
	}

	abstract public boolean satisfy(SolverContext solverContext);

	public static Constraint nodeType(Variable<ITypedNode> v, ITypeNode type, TypeGraph typeGraph) {
		NodeTypeConstraint c = new NodeTypeConstraint(type,v,typeGraph);
		return c;
	}
	
	public static Constraint link(Variable<ITypedNode> src, Variable<ITypedNode> tar, Variable<ITypedEdge> edge) {
		ConnectionConstraint c = new ConnectionConstraint(src,tar,edge);
		return c;
	}
}

class NodeTypeConstraint extends Constraint {
	protected ITypeNode type;
	protected Variable<ITypedNode> variable;
	protected TypeGraph typeGraph;
	
	public NodeTypeConstraint(ITypeNode type, Variable<ITypedNode> variable, TypeGraph typeGraph) {
		this.priority = ConstraintPriority.INIT;
		this.variable = variable;
		this.type = type;
		this.typeGraph = typeGraph;
		
		this.allVariables.add(this.variable);
	}

	@Override
	public void apply(Variable<?> var, List<?> removedValues, Decision decision,SolverContext c) {
		ValueDomainIterator<ITypedNode> domain = c.getDomainIterator(variable, decision);

		List<ITypedNode> removed = new ArrayList<ITypedNode>();
		
		if(this.type instanceof TypeNode) {
			while(domain.hasNext()) {
				ITypedNode v = domain.next();
				if(!this.typeGraph.isSuperTypeOf(v.getType(), this.type)) {
					removed.add(v);
					domain.remove();
				}
			}
		} else {
			while(domain.hasNext()) {
				ITypedNode v = domain.next();
				if(v.getType()!=this.type) {
					removed.add(v);
					domain.remove();
				}
			}			
		}
		
		if(removed.isEmpty()==false)
			decision.notify(this.variable, removed);
	}

	@Override
	public boolean satisfy(SolverContext solverContext) {
		try {
			TypedNode value = solverContext.getValue(this.variable);
			return this.typeGraph.isSuperTypeOf(value.getType(), this.type);
		} catch(NoValueException e) {
			
		}
		return false;
	}
}

class ConnectionConstraint extends Constraint {
	protected Variable<ITypedNode> source;
	public ConnectionConstraint(Variable<ITypedNode> source, Variable<ITypedNode> target, Variable<ITypedEdge> edge) {
		super();
		this.source = source;
		this.target = target;
		this.edge = edge;
		this.priority = ConstraintPriority.NORMAL;
		
		this.allVariables.add(this.source);
		this.allVariables.add(this.target);
		this.allVariables.add(this.edge);
	}


	protected Variable<ITypedNode> target;
	protected Variable<ITypedEdge> edge;


	@Override
	public void apply(Variable<?> var, List<?> removedValues, Decision decision,SolverContext c) {
		if(var==source) {
			ValueDomainIterator<ITypedNode> sourceDomain = c.getDomainIterator(this.source, decision);
			ValueDomainIterator<ITypedEdge> edgeDomain = c.getDomainIterator(this.edge, decision);
			
			List<ITypedEdge> removedEdges = new ArrayList<ITypedEdge>();
			
			while(edgeDomain.hasNext()) {
				ITypedEdge e = edgeDomain.next();
				if(removedValues.contains(e.getSource()) 
						|| sourceDomain.exists(s->s==e.getSource())==false) {
					removedEdges.add(e);
					edgeDomain.remove();
				}
				sourceDomain.restart();
			}
			
			if(removedEdges.isEmpty()==false)
				decision.notify(this.edge, removedEdges);
		} else if(var==target) {
			ValueDomainIterator<ITypedNode> targetDomain = c.getDomainIterator(this.target, decision);
			ValueDomainIterator<ITypedEdge> edgeDomain = c.getDomainIterator(this.edge, decision);
			
			List<ITypedEdge> removedEdges = new ArrayList<ITypedEdge>();
			
			while(edgeDomain.hasNext()) {
				ITypedEdge e = edgeDomain.next();
				if(removedValues.contains(e.getTarget()) 
						|| targetDomain.exists(t->t==e.getTarget())==false) {
					removedEdges.add(e);
					edgeDomain.remove();
				}
				targetDomain.restart();
			}
			
			if(removedEdges.isEmpty()==false)
				decision.notify(this.edge,removedEdges);
		} else if(var==edge) {
			ValueDomainIterator<ITypedEdge> edgeDomain = c.getDomainIterator(this.edge, decision);
			
			
			ValueDomainIterator<ITypedNode> sourceDomain = c.getDomainIterator(this.source, decision);
			List<ITypedNode> removedSources = new ArrayList<ITypedNode>();

			while(sourceDomain.hasNext()) {
				ITypedNode s = sourceDomain.next();
				if(edgeDomain.exists(e->e.getSource()==s)==false) {
					removedSources.add(s);
					sourceDomain.remove();
				}
				edgeDomain.restart();
			}
			
			if(removedSources.isEmpty()==false)
				decision.notify(this.source,removedSources);
			
			ValueDomainIterator<ITypedNode> targetDomain = c.getDomainIterator(this.target, decision);
			List<ITypedNode> removedTargets = new ArrayList<ITypedNode>();
			
			while(targetDomain.hasNext()) {
				ITypedNode t = targetDomain.next();
				if(edgeDomain.exists(e->e.getTarget()==t)==false) {
					removedTargets.add(t);
					targetDomain.remove();
				}
				edgeDomain.restart();
			}
			
			if(removedTargets.isEmpty()==false)
				decision.notify(this.target,removedTargets);
		} else {
			assert(false);
		}
		
	}


	@Override
	public boolean satisfy(SolverContext solverContext) {
		try {
			ITypedNode sv = solverContext.getValue(this.source);
			ITypedNode tv = solverContext.getValue(this.target);
			ITypedEdge ev = solverContext.getValue(this.edge);
			
			return (ev.getSource()==sv && ev.getTarget()==tv);
			
		} catch(NoValueException e) {
			
		}
		return false;
	}
}

class AllDifferentConstraint extends Constraint {
	
	public AllDifferentConstraint(Variable<?>... vars) {
		for(Variable<?> v : vars)
			this.getVariables().add(v);
		
		this.priority = ConstraintPriority.LOW;
	}

	@Override
	public void apply(Variable<?> var, List<?> removedValues, Decision decision, SolverContext c) {
		try {
			Object value = c.getValue(var);
			List<Object> removedValue = Collections.singletonList(value);
			
			for(Variable<?> v : this.getVariables()) {
				if(v!=var) {
					boolean flag = false;
					ValueDomainIterator<?> vDomain = c.getDomainIterator(v, decision);
					while(vDomain.hasNext()) {
						if(vDomain.next()==value)  {
							vDomain.remove();
							flag = true;
						}
					}
					if(flag) decision.notify(v, removedValue);
				}
			}
		} catch(NoValueException e) {
		}
	}

	@Override
	public boolean satisfy(SolverContext solverContext) {
		Set<Object> allValues = new HashSet<>();
		
		try {
			for(Variable<?> v : this.getVariables()) {
				Object value = solverContext.getValue(v);
				if(value!=ValueNode.NULL) {
					if(allValues.contains(value)) return false;
					else allValues.add(value);
				}
			}
		} catch(NoValueException e) {
			return false;
		}
		return true;
	}
	
}