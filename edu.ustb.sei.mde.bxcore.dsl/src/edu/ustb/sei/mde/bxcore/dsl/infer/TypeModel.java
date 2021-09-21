package edu.ustb.sei.mde.bxcore.dsl.infer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.constraints.Propagator;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.SetVar;
import org.chocosolver.util.ESat;
import org.chocosolver.util.objects.setDataStructures.ISetIterator;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.xbase.XExpression;

import edu.ustb.sei.mde.bxcore.dsl.bXCore.BXFunctionDefinition;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.BXProgram;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.DashedPathType;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.DefinedContextTypeRef;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.ImportSection;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.Pattern;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.PatternDefinitionReference;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.TypeDefinition;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.TypeLiteral;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.XmuCoreStatement;
import edu.ustb.sei.mde.bxcore.dsl.structure.TupleType;
import edu.ustb.sei.mde.bxcore.util.ListWithCustomizedCompare;
import edu.ustb.sei.mde.bxcore.util.PathTypeUtil;
import edu.ustb.sei.mde.bxcore.util.SetWithCustomizedCompare;
import edu.ustb.sei.mde.structure.Tuple2;
import edu.ustb.sei.mde.structure.Tuple3;

public abstract class TypeModel {
	protected Set<TupleType> types;
	protected List<TypeConstraint> constraints;
	protected List<String> nameList;
	
	private Map<TupleType, SetVar> typeVarMap;
	protected Set<ImportSection> imports;
	
	// could be shared
	private BXProgram program;
	protected List<Object> typeList;
	protected boolean[][] superTypeTable;
	
	// derived from input
	public Map<XmuCoreStatement, UnsolvedTupleType> unsolvedTupleTypeMap;
	
	// input
	public Map<TypeLiteral, Tuple2<TupleType, Integer>> literalMap;
	
	
	protected Map<TypeConstraint, EObject> causeMap;
	protected void linkCause(TypeConstraint c, EObject obj) {
		causeMap.put(c, obj);
	}
	
	
	public TypeModel(BXProgram program, Map<TypeLiteral, Tuple2<TupleType, Integer>> literalMap) {
		this.program = program;
		this.literalMap = literalMap;
		this.types = new HashSet<>();
		this.constraints = new ArrayList<>();
		this.unsolvedTupleTypeMap = new HashMap<>();
		this.causeMap = new HashMap<>();

		extractConstraint(program);
		
		imports = summarizeImport();
	}
	
	protected void extractConstraint(BXProgram program) {
		TreeIterator<EObject> itr = null;
		itr = program.eAllContents();
		int id = 0;
		while(itr.hasNext()) {
			EObject e = itr.next();
			if(e instanceof XmuCoreStatement) {
				UnsolvedTupleType st = new UnsolvedTupleType();
				st.info = e.eClass().getName()+"(xmu"+id+")";
				id++;
				unsolvedTupleTypeMap.put((XmuCoreStatement)e, st);
			} else if(e instanceof Pattern || e instanceof TypeLiteral || e instanceof XExpression) {
				itr.prune();
			}
			
		}
		
		literalMap.values().forEach(v->types.add(v.first));
		unsolvedTupleTypeMap.values().forEach(v->types.add(v));
		
		itr = program.eAllContents();
		while(itr.hasNext()) {
			EObject e = itr.next();
			if(e instanceof BXFunctionDefinition) {
				if(((BXFunctionDefinition) e).getTypeIndicator()!=null)
					handleTypeIndicator((BXFunctionDefinition)e);
			} else if(e instanceof XmuCoreStatement) {
				extractConstraint((XmuCoreStatement)e);
				if(((XmuCoreStatement) e).getTypeIndicator()!=null)
					handleTypeIndicator((XmuCoreStatement)e);
			} else if(e instanceof Pattern || e instanceof TypeLiteral || e instanceof XExpression) {
				itr.prune();
			}
		}
	}
	
	protected abstract void handleTypeIndicator(BXFunctionDefinition e);
	protected abstract void handleTypeIndicator(XmuCoreStatement e);

	protected abstract void extractConstraint(XmuCoreStatement e);

	public TupleType getType(EObject o) {
		if(o instanceof XmuCoreStatement) return unsolvedTupleTypeMap.get(o);
		else if(o instanceof BXFunctionDefinition) return unsolvedTupleTypeMap.get(((BXFunctionDefinition) o).getStatement());
		else if(o instanceof TypeLiteral) return literalMap.get(o).first;
		else if(o instanceof DefinedContextTypeRef) return literalMap.get(((DefinedContextTypeRef) o).getType().getLiteral()).first;
		else if(o instanceof TypeDefinition) return literalMap.get(((TypeDefinition) o).getLiteral()).first;
		else if(o instanceof PatternDefinitionReference) return literalMap.get(((PatternDefinitionReference) o).getPattern().getLiteral()).first;
		else return null;
	}
	
	public void solveNames() {
		if(this.constraints.isEmpty()) return;
		Model model = new Model("name infer");
		Set<String> names = new HashSet<>();
		types.forEach(t->{t.tuples.stream().map(k->k.first).forEach(n->names.add(n));});
		nameList = new ArrayList<>(names);
		
		int[] min = new int[0];
		int[] max = new int[nameList.size()];
		for(int i=0;i<max.length;i++) max[i] = i;
		
		Map<Object, EObject> reasonMap = new HashMap<>();
		
		typeVarMap = new HashMap<>();
		types.forEach(t->{
			if(t instanceof UnsolvedTupleType) {
				SetVar var;
				if(((UnsolvedTupleType) t).info!=null)
					var = model.setVar(((UnsolvedTupleType) t).info, min, max);
				else var = model.setVar(min, max);
				typeVarMap.put(t, var);
			} else {
				int[] vals = new int[t.tuples.size()];
				for(int i=0;i<vals.length;i++) vals[i] = nameList.indexOf(t.tuples.get(i).first);
				SetVar var = model.setVar(vals);
				typeVarMap.put(t, var);
			}
		});
		
		constraints.forEach(c->{
			if(c instanceof TypeEqual) {
				SetVar left = typeVarMap.get(((TypeEqual) c).left);
				SetVar right = typeVarMap.get(((TypeEqual) c).right);
				
				if(left==null || right==null) throw new RuntimeException();
				
				if(((TypeEqual) c).mappings!=null) {
					List<Tuple2<Integer,Integer>> idMaps = ((TypeEqual) c).mappings.stream().map(m->{
						return Tuple2.make(nameList.indexOf(m.getFrom()), nameList.indexOf(m.getTo()));
					}).collect(Collectors.toList());
					Constraint cons = new Constraint(model.generateName(), new PropSetMapping(left, right, idMaps));
					model.post(cons);
					addReason(reasonMap, cons, causeMap.get(c));
				} else {
					Constraint cons = model.allEqual(left,right);
					addReason(reasonMap, cons, causeMap.get(c));
					cons.post();
				}
			} else if(c instanceof TypeUnion) {
				SetVar union = typeVarMap.get(((TypeUnion)c).unionType);
				SetVar[] elements = ((TypeUnion) c).types.stream().map(t->typeVarMap.get(t)).toArray(i->new SetVar[i]);
				if(union==null) throw new RuntimeException("Type inference error");
				for(int i=0;i<elements.length;i++) {
					if(elements[i]==null)
						throw new RuntimeException("Type inference error");
				}
				if(((TypeUnion) c).subset) {
					for(SetVar st : elements) {
						Constraint cons = model.subsetEq(st, union);
						cons.post();
						addReason(reasonMap, cons, causeMap.get(c));
					}
				} else {
					Constraint cons = model.union(elements, union);
					cons.post();
					addReason(reasonMap, cons, causeMap.get(c));
				}
			} else if(c instanceof TypeSubset) {
				SetVar complete = typeVarMap.get(((TypeSubset) c).complete);
				SetVar subset = typeVarMap.get(((TypeSubset) c).subset);
				Constraint cons = model.subsetEq(subset, complete);
				cons.post();
				addReason(reasonMap, cons, causeMap.get(c));
			} else if(c instanceof TypeDisjoint) {
				SetVar left = typeVarMap.get(((TypeDisjoint) c).left);
				SetVar right = typeVarMap.get(((TypeDisjoint) c).right);
				Constraint cons = model.disjoint(left, right);
				cons.post();
				addReason(reasonMap, cons, causeMap.get(c));
			}
		});
		
		if(model.getSolver().solve()) {
			types.forEach(t->{
				if(t instanceof UnsolvedTupleType) {
					SetVar s = typeVarMap.get(t);
					ISetIterator iter = s.getValue().iterator();
					while(iter.hasNext()) {
						int v = iter.nextInt();
						String key = nameList.get(v);
						((UnsolvedTupleType) t).candidates.add(key);
					}
				}
			});
		} else {
			ContradictionException ex = model.getSolver().getContradictionException();
			if(ex!=null) {
				EObject o = reasonMap.getOrDefault(ex.c, program);
				throw new TypeInferenceException(getName()+":an error occurs in inferring names", o, ex, model, this);				
			} else {
				throw new TypeInferenceException(getName()+":an error occurs in inferring names", program, ex, model, this);
			}
		}
	}


	private void addReason(Map<Object, EObject> reasonMap, Constraint cons, EObject eObject) {
		reasonMap.put(cons, eObject);
		for(Propagator<?> p : cons.getPropagators()) {
			reasonMap.put(p, eObject);
		}
	}


	protected Set<ImportSection> summarizeImport() {
		Set<ImportSection> imports = new HashSet<>();
		
		constraints.forEach(c->{
			if(c instanceof TypeEqual) {
				if(((TypeEqual) c).left.importSection!=null) 
					imports.add(((TypeEqual) c).left.importSection);
				if(((TypeEqual) c).right.importSection!=null)
					imports.add(((TypeEqual) c).right.importSection);
			} else if(c instanceof TypeUnion) {
				if(((TypeUnion) c).unionType.importSection!=null) 
					imports.add(((TypeUnion) c).unionType.importSection);
				
				((TypeUnion) c).types.forEach(cc->{
					if(cc.importSection!=null) imports.add(cc.importSection);
				});
			} else if(c instanceof TypeSubset) {
				if(((TypeSubset) c).complete.importSection!=null) 
					imports.add(((TypeSubset) c).complete.importSection);
				if(((TypeSubset) c).subset.importSection!=null)
					imports.add(((TypeSubset) c).subset.importSection);
			} else if(c instanceof TypeDisjoint) {
				if(((TypeDisjoint) c).left.importSection!=null) 
					imports.add(((TypeDisjoint) c).left.importSection);
				if(((TypeDisjoint) c).left.importSection!=null)
					imports.add(((TypeDisjoint) c).left.importSection);
			}
		});
		
		return imports;
	}
	
	static protected Tuple2<List<Object>, boolean[][]> buildTypeTable(BXProgram program) {
		List<Object> typeList = new ListWithCustomizedCompare<>();
		boolean[][] superTypeTable = null;
		
		Set<Object> typeSet = new SetWithCustomizedCompare<>();
		
		// search imported types
		program.getImports().forEach(sec->{
			sec.getMetamodel().eAllContents().forEachRemaining(e->{
				if(e instanceof EClassifier) typeSet.add(e);
				else if(e instanceof EStructuralFeature) {
					typeSet.add(e);
					typeSet.add(((EStructuralFeature) e).getEType());
				}
			});
		});
		
		// search path types
		TreeIterator<EObject> itr = program.eAllContents();
		while(itr.hasNext()) {
			EObject e = itr.next();
			if(e instanceof DashedPathType) {
				typeSet.add(e);
				itr.prune();
			}
		}
		
		typeList.addAll(typeSet);
		superTypeTable = new boolean[typeList.size()][];
		
		for(int i=0;i<typeList.size();i++) {
			superTypeTable[i] = new boolean[typeList.size()];
			boolean[] tableForI = superTypeTable[i];
			Object typeI = typeList.get(i);
			
			if(typeI instanceof EStructuralFeature || typeI instanceof EDataType) {
				for(int j=0;j<typeList.size();j++) {
					tableForI[j] = (i==j);
				}			
			} else if(typeI instanceof EClass) {
				for(int j=0;j<typeList.size();j++) {
					Object typeJ = typeList.get(j);
					if(typeI==typeJ) tableForI[j] = true;
					else if(!(typeJ instanceof EClass)) tableForI[j] = false;
					else tableForI[j] = ((EClass)typeI).isSuperTypeOf((EClass)typeJ);
				}
			} else if(typeI instanceof DashedPathType) {
				for(int j=0;j<typeList.size();j++) {
					Object typeJ = typeList.get(j);
					if(typeI==typeJ) tableForI[j] = true;
					else tableForI[j] = PathTypeUtil.isEqual((DashedPathType)typeI, typeJ);
				}
			}
		}
		return Tuple2.make(typeList, superTypeTable);
	}
	
	public void solveTypes() {
		if(this.constraints.isEmpty()) return;
		if(imports.isEmpty()) {
			throw new TypeInferenceException(getName()+": no import is used under the context", program, null, null, this);
		} else if(imports.size()!=1) {
			throw new TypeInferenceException(getName()+":multiple imports are used", program, null, null, this);
		}
		
		ImportSection im = imports.iterator().next();
		
		if(this.typeList==null || this.superTypeTable ==null) {
			Tuple2<List<Object>, boolean[][]> t2 = buildTypeTable(this.program);
			this.typeList = t2.first;
			this.superTypeTable = t2.second;
		}
		Map<Object, EObject> reasonMap = new HashMap<>();
		Map<TupleType, Map<String, IntVar>> varMap = new HashMap<>();
		Map<TupleType, Map<String, BoolVar>> multiMap = new HashMap<>();
		
		// build intVar
		Model model = new Model("type infer");
		types.forEach(t->{
			if(t instanceof UnsolvedTupleType) {
				Map<String, IntVar> map = new HashMap<>();
				varMap.put(t, map);
				Map<String, BoolVar> mmap = new HashMap<>();
				multiMap.put(t, mmap);

				((UnsolvedTupleType) t).candidates.forEach(s->{
					map.put(s, model.intVar(0, this.typeList.size()-1));
					mmap.put(s, model.boolVar());
				});
				
			} else {
				Map<String, IntVar> map = new HashMap<>();
				varMap.put(t, map);
				Map<String, BoolVar> mmap = new HashMap<>();
				multiMap.put(t, mmap);
				
				t.tuples.forEach(s->{
					map.put(s.first, model.intVar(this.typeList.indexOf(s.second)));
					mmap.put(s.first, model.boolVar(s.third));
				});
			}
		});
		
		// build constraint
		constraints.forEach(cons->{
			EObject causeObject = causeMap.get(cons);
			
			if(cons instanceof TypeEqual) {
				TupleType left = ((TypeEqual) cons).left;
				TupleType right = ((TypeEqual) cons).right;
				Map<String,IntVar> lv = varMap.get(left);
				Map<String,IntVar> rv = varMap.get(right);
				
				Map<String,BoolVar> lvm = multiMap.get(left);
				Map<String,BoolVar> rvm = multiMap.get(right);
				
				Stream<String> keys;
				if(left instanceof UnsolvedTupleType) keys = ((UnsolvedTupleType) left).candidates.stream();
				else keys = left.tuples.stream().map(s->s.first);
				
				keys.forEach(sk->{
					String vk = null;
					if (((TypeEqual) cons).mappings != null) {
						vk = ((TypeEqual) cons).mappings.stream().filter(m->m.getFrom().equals(sk)).findFirst().get().getTo();
					} else {
						vk = sk;
					}
					
					IntVar liv = lv.get(sk);
					IntVar riv = rv.get(vk);
					
					Constraint c = new Constraint(model.generateName(), new PropTypeCast(liv, riv, typeList, superTypeTable, ((TypeEqual) cons).sort));
					model.post(c);
					addReason(reasonMap, c, causeObject);
					
					Constraint c2 = model.allEqual(lvm.get(sk), rvm.get(vk));
					model.post(c2);
					addReason(reasonMap, c2, causeObject);
				});
			} else if(cons instanceof TypeUnion) {
				TupleType left = ((TypeUnion) cons).unionType;
				Map<String,IntVar> lv = varMap.get(left);
				Map<String,BoolVar> lvm = multiMap.get(left);
				for(TupleType right : ((TypeUnion) cons).types) {
					Map<String,IntVar> rv = varMap.get(right);
					Map<String,BoolVar> rvm = multiMap.get(right);
					Stream<String> keys;
					if(right instanceof UnsolvedTupleType) keys = ((UnsolvedTupleType) right).candidates.stream();
					else keys = right.tuples.stream().map(s->s.first);
					
					keys.forEach(vk->{
						IntVar liv = lv.get(vk);
						IntVar riv = rv.get(vk);
						Constraint c = new Constraint(model.generateName(), new PropTypeCast(liv, riv, typeList, superTypeTable, TypeEqual.LEFT_ABSTRACT));
						model.post(c);
						addReason(reasonMap, c, causeObject);
						
						Constraint c2 = model.allEqual(lvm.get(vk), rvm.get(vk));
						model.post(c2);
						addReason(reasonMap, c2, causeObject);
					});
				}
			} else if(cons instanceof TypeSubset) {
				TupleType left = ((TypeSubset) cons).complete;
				TupleType right = ((TypeSubset) cons).subset;
				
				Map<String,IntVar> lv = varMap.get(left);
				Map<String,BoolVar> lvm = multiMap.get(left);
				Map<String,IntVar> rv = varMap.get(right);
				Map<String,BoolVar> rvm = multiMap.get(right);
				Stream<String> keys;
				if(right instanceof UnsolvedTupleType) keys = ((UnsolvedTupleType) right).candidates.stream();
				else keys = right.tuples.stream().map(s->s.first);
				
				keys.forEach(vk->{
					IntVar liv = lv.get(vk);
					IntVar riv = rv.get(vk);
					Constraint c = new Constraint(model.generateName(), new PropTypeCast(liv, riv, typeList, superTypeTable, TypeEqual.EQUAL));
					model.post(c);
					addReason(reasonMap, c, causeObject);
					
					Constraint c2 = model.allEqual(lvm.get(vk), rvm.get(vk));
					model.post(c2);
					addReason(reasonMap, c2, causeObject);
				});
			}
		});
		
		if(model.getSolver().solve()) {
			types.forEach(t->{
				if(t instanceof UnsolvedTupleType) {
					Map<String, IntVar> map = varMap.get(t);
					Map<String, BoolVar> mmap = multiMap.get(t);
					((UnsolvedTupleType) t).candidates.forEach(tp->{
						IntVar var = map.get(tp);
						Object type = this.typeList.get(var.getValue());
						BoolVar mul = mmap.get(tp);
						
						t.tuples.add(Tuple3.make(tp, type, mul.getBooleanValue()==ESat.TRUE));
					});
					t.importSection = im;
				}
			});
			
		} else {
			ContradictionException ex = model.getSolver().getContradictionException();
			if(ex!=null) {
				EObject o = reasonMap.getOrDefault(ex.c, program);
				throw new TypeInferenceException(getName()+": an error occurs in inferring types", o, ex, model, this);				
			} else {
				throw new TypeInferenceException(getName()+": an error occurs in inferring types", program, ex, model, this);
			}
		}
	}
	
	protected abstract String getName();
	
	static public Tuple2<SourceTypeModel, ViewTypeModel> buildTypeInfers(BXProgram program, Map<TypeLiteral, Tuple2<TupleType, Integer>> literalMap) {
		Tuple2<List<Object>, boolean[][]> t2 = buildTypeTable(program);
		
		SourceTypeModel stm = new SourceTypeModel(program, literalMap);
		stm.typeList = t2.first;
		stm.superTypeTable = t2.second;
		
		ViewTypeModel vtm = new ViewTypeModel(program, literalMap);
		vtm.typeList = t2.first;
		vtm.superTypeTable = t2.second;
		
		return Tuple2.make(stm, vtm);
	}
}
