package edu.ustb.sei.mde.graph.pattern.solving;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.ustb.sei.mde.graph.pattern.solving.SolverContext.ContextState;

public class Solver {
	protected Decision currentDecision;
	protected List<Constraint> processCons;
	protected List<Constraint> postCons;
	protected Action action;
	protected Problem problem;
	protected SolverContext context;
	protected Decision rootDecision;
	
	public void begin(Problem problem, SolverContext context) {
		this.context = context;
		
		this.problem = problem;
		rootDecision = new Decision(context);
		currentDecision = rootDecision;		

		List<Constraint> initCons = problem.getInitConstraints();
		initCons.forEach(c->c.apply(rootDecision,context));

		processCons = problem.getProcessConstraints();
		postCons = problem.getPostConstraints();
		
		action = Action.GO_DOWN;
	}
	
	public List<Map<Variable<?>,Object>> solveAll() {
		return solveAll(SolutionBuilder.MAP_BUILDER);
	}
	
	public <T> List<T> solveAll(SolutionBuilder<T> builder) {
		List<T> result = new ArrayList<T>();
		T m = null;
		while((m=solveOne(builder))!=null) {
			result.add(m);
		}
		return result;
	}
	
	public Map<Variable<?>,Object> solveOne() {
		return solveOne(SolutionBuilder.MAP_BUILDER);
	}
	
	public <T> T solveOne(SolutionBuilder<T> builder) {
		SolverContext c = solve();
		if(c==null) return null;
		return c.getSolution(builder);
	}
	
	@SuppressWarnings("unchecked")
	public SolverContext solve() {
		while(true) {
			if(action==Action.GO_DOWN) {
				Variable<?> nextVariable = chooseNextVariable(problem, currentDecision);
				if(nextVariable==null) {
					action = Action.GO_UP;
				} else {
					currentDecision = new Decision(nextVariable, currentDecision);
					action = Action.GO_NEXT;
				}
			} else if(action==Action.GO_NEXT) {
				if(currentDecision.hasNext()) {
					currentDecision.restore();
					Object value = currentDecision.next();
					currentDecision.decide(value);
					
					if(isLastDecision(problem, currentDecision) ) {
						ContextState state = context.contextState(processCons,postCons);
						
						if(state==ContextState.SOLUTION) {
							// find next solution
							action = Action.GO_NEXT;
							return context;
						} else if(state==ContextState.NON_SOLUTION) {
							action = Action.GO_NEXT;
						} else {
							// state == ContextState.UNDECIDED
							action = Action.GO_DOWN;
						}
					} else {
						action = Action.GO_DOWN;
					}
				} else {
					action=Action.GO_UP;
				}
			} else if(action==Action.GO_UP) {
				if(currentDecision==rootDecision) break;
				
				currentDecision.restore();
				currentDecision = currentDecision.getParent();
				action = Action.GO_NEXT;
			}
		}
		return null;
	}
	
	enum Action {
		GO_NEXT,
		GO_DOWN,
		GO_UP
	}

	private Variable<?> chooseNextVariable(Problem problem, Decision currentDecision) {
		Variable<?> var = currentDecision.choice;
		int id = var==null ? id=-1 : problem.variables.indexOf(var);
		id++;
		if(id==problem.variables.size()) return null;
		else return problem.variables.get(id);
	}
	
	private boolean isLastDecision(Problem problem, Decision currentDecision) {
		return currentDecision.getDepth()==problem.variables.size();
	}
}
