package edu.ustb.sei.mde.bxcore.dsl.structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EPackage;

import edu.ustb.sei.mde.bxcore.dsl.bXCore.AbstractPatternEdge;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.DashedPathType;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.EcoreTypeRef;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.FeatureTypeRef;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.ImportSection;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.OrderedTupleTypeLiteral;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.PatternEdge;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.PatternNode;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.PatternNodeRef;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.PatternPathEdge;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.PatternTypeLiteral;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.PatternValueCondition;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.PredefinedTypeLiteral;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.TypeLiteral;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.TypeRef;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.TypeVar;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.UnorderedTupleTypeLiteral;
import edu.ustb.sei.mde.bxcore.dsl.infer.UnsolvedTupleType;
import edu.ustb.sei.mde.bxcore.util.PathTypeUtil;
import edu.ustb.sei.mde.structure.Tuple3;

public class TupleType {
	public boolean ordered=false;
	public List<Tuple3<String,Object, Boolean>> tuples = new ArrayList<Tuple3<String,Object,Boolean>>();
	public ImportSection importSection = null;
	public String info;
	
	public boolean equals(Object o) {
		if(o instanceof TupleType && !(o instanceof UnsolvedTupleType)) return compare((TupleType)o);
		return false;
	}

	public boolean compare(TupleType o) {
		TupleType right = o;
		if ((this.tuples.isEmpty() || this.ordered == right.ordered) && this.tuples.size() == right.tuples.size()) {
			return this.tuples.stream().allMatch(t -> {
				return right.tuples.stream().anyMatch(r->compareTuple(t,r));
			});
		} else return false;
	}
	
	static final public TupleType empty = new TupleType();
	
	static public TupleType make(TypeLiteral literal) {
		if(literal instanceof PredefinedTypeLiteral) {
			// to support other types
			return empty;
		} else if(literal instanceof OrderedTupleTypeLiteral) {
			TupleType t = new TupleType();
			t.ordered = true;
			((OrderedTupleTypeLiteral) literal).getElements().forEach(e->t.tuples.add(Tuple3.make(e.getName(), getType(e.getType()), e.isMany())));
			t.importSection  = ((OrderedTupleTypeLiteral) literal).getSource();
			return t;
		}  else if(literal instanceof UnorderedTupleTypeLiteral) {
			TupleType t = new TupleType();
			t.ordered = false;
			((UnorderedTupleTypeLiteral) literal).getElements().forEach(e->t.tuples.add(Tuple3.make(e.getName(), getType(e.getType()), e.isMany())));
			t.tuples.sort((l,r)->l.first.compareTo(r.first));
			t.importSection  = ((UnorderedTupleTypeLiteral) literal).getSource();
			return t;
		} else if(literal instanceof PatternTypeLiteral) {
			TupleType t = new TupleType();
			t.ordered = false;
			literal.eAllContents().forEachRemaining(e->{
				if(e instanceof PatternNode) {
					t.tuples.add(Tuple3.make(((PatternNode)e).getName(), ((PatternNode)e).getType(), ((PatternNode)e).isMany()));
				} else if(e instanceof PatternEdge) {
					t.tuples.add(Tuple3.make(((PatternEdge)e).getName(), ((PatternEdge)e).getFeature(), isMany((PatternEdge)e)));
				} else if(e instanceof PatternPathEdge) {
					t.tuples.add(Tuple3.make(((PatternPathEdge)e).getName(), ((PatternPathEdge)e).getPath(), isMany((PatternPathEdge)e)));
				} else if(e instanceof TypeVar) {
					t.tuples.add(Tuple3.make(((TypeVar)e).getName(), getType(((TypeVar)e).getType()), ((TypeVar)e).isMany()));
				}
				
			});
			t.importSection  = ((PatternTypeLiteral) literal).getSource();
			t.tuples.sort((l,r)->l.first.compareTo(r.first));
			return t;
		} else return empty;
	}

	protected static boolean isMany(AbstractPatternEdge e) {
		PatternNode con = (PatternNode) e.eContainer();
		if(con.isMany()) return true;
		PatternValueCondition target = e.getValue();
		if(target instanceof PatternNode) {
			return ((PatternNode) target).isMany();
		} else if(target instanceof PatternNodeRef) {
			return ((PatternNodeRef) target).getNode().isMany();
		}
		return ((AbstractPatternEdge)e).isMany();
	}
	
	static public Object getType(TypeRef ref) {
		if(ref instanceof EcoreTypeRef) return ((EcoreTypeRef) ref).getType();
		else if(ref instanceof FeatureTypeRef) return ((FeatureTypeRef) ref).getFeature();
		return null;
	}
	
	static public EPackage getPackage(TypeRef ref) {
		if(ref instanceof EcoreTypeRef) return ((EcoreTypeRef) ref).getType().getEPackage();
		else if(ref instanceof FeatureTypeRef) return ((FeatureTypeRef) ref).getFeature().getEContainingClass().getEPackage();
		return null;
	}
	
	int hash = 0;
	@Override
	public int hashCode() {
		if(hash==0) 
			hash = tuples.stream().map(t->hashTuple(t)).reduce(0, (a,b)->a+b);
		return hash;
	}
	
	/**
	 * We need this customized hash method because the second element may be PathTypes in DSL.
	 * They cannot have a customized hashCode and equal.
	 * @param tuple
	 * @return
	 */
	private int hashTuple(Tuple3<String,Object, Boolean> tuple) {
		int firstCode = tuple.first==null ? 0 : tuple.first.hashCode();
		int secondCode = 0;
		
		if(tuple.second!=null) {
			if(tuple.second instanceof DashedPathType) {
				secondCode = PathTypeUtil.hash((DashedPathType) tuple.second);
			} else {
				secondCode = tuple.second.hashCode(); 
			}
		}
		
		int thirdCode = tuple.third==null ? 0 : tuple.third.hashCode();
		return ((firstCode&0xFFFF)<<20) & ((secondCode&0xFFFF)<<10) & (thirdCode&0xFFFF);
	}
	
	/**
	 * We need this customized compare method because the second element may be PathTypes in DSL.
	 * They cannot have a customized hashCode and equal.
	 * @param tuple
	 * @return
	 */
	private boolean compareTuple(Tuple3<String,Object, Boolean> l, Tuple3<String,Object, Boolean> r) {
		if(!(l.first==r.first || (l.first!=null && l.first.equals(r.first)))) return false;
		if(!(l.third==r.third || (l.third!=null && l.third.equals(r.third)))) return false;
		
		if(l.second == r.second) return true;
		else if(l.second!=null) {
			if(l.second instanceof DashedPathType) {
				return PathTypeUtil.isEqual((DashedPathType) l.second, r.second);
			} else {
				return l.second.equals(r.second);
			}
		} else 
			return false;
	}
	
	@Override
	public String toString() {
		return "("+tuples.stream().map(t->{
			return t.first + ":" + getTypeName(t.second) + (t.third ? "[]":"");
		}).reduce((l,r)->l+", "+r).get()+")";
	}
	
	static String getTypeName(Object type) {
		return ((ENamedElement)type).getName();
	}
}
