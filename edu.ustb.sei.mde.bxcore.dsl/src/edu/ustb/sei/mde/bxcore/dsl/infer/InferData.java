package edu.ustb.sei.mde.bxcore.dsl.infer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.xtext.xbase.lib.Pair;

import edu.ustb.sei.mde.bxcore.dsl.bXCore.DashedPathType;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.PatternTypeLiteral;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.TypeLiteral;
import edu.ustb.sei.mde.bxcore.dsl.structure.TupleType;
import edu.ustb.sei.mde.structure.Tuple2;

public class InferData {
	protected Map<TypeLiteral, Tuple2<TupleType, Integer>> literalMap;
	protected Set<Tuple2<TupleType, Integer>> tupleSet;
	protected List<Pair<Integer, PatternTypeLiteral>> patternLiterals;
	protected List<Pair<Integer, DashedPathType>> pathTypes;
	
	public List<Pair<Integer, DashedPathType>> getPathTypes() {
		return pathTypes;
	}
	public List<Pair<Integer, PatternTypeLiteral>> getPatternLiterals() {
		return patternLiterals;
	}
	public void setPatternLiterals(List<Pair<Integer, PatternTypeLiteral>>  patternLiterals) {
		this.patternLiterals = patternLiterals;
	}
	public Set<Tuple2<TupleType, Integer>> getTupleSet() {
		return tupleSet;
	}
	public Map<TypeLiteral, Tuple2<TupleType, Integer>> getLiteralMap() {
		return literalMap;
	}
	public void setLiteralMap(Map<TypeLiteral, Tuple2<TupleType, Integer>> literalMap) {
		this.literalMap = literalMap;
		this.tupleSet = this.literalMap.values().stream().collect(Collectors.toSet());
		
	}
	public SourceTypeModel getSourceInfer() {
		return sourceInfer;
	}
	public void setSourceInfer(SourceTypeModel sourceInfer) {
		this.sourceInfer = sourceInfer;
	}
	public ViewTypeModel getViewInfer() {
		return viewInfer;
	}
	public void setViewInfer(ViewTypeModel viewInfer) {
		this.viewInfer = viewInfer;
	}
	protected SourceTypeModel sourceInfer;
	protected ViewTypeModel viewInfer;
	
	
	protected Map<UnsolvedTupleType, Tuple2<TupleType, Integer>> unsolvedTypeMap;
	public Map<UnsolvedTupleType, Tuple2<TupleType, Integer>> getUnsolvedTypeMap() {
		if(unsolvedTypeMap==null) {
			unsolvedTypeMap = new HashMap<>();
			
			List<UnsolvedTupleType> allUnsovledTypes = new ArrayList<>();
			sourceInfer.unsolvedTupleTypeMap.values().forEach(t->{
				Tuple2<TupleType, Integer> sovled = findTupleType(t);
				if(sovled!=null) {
					unsolvedTypeMap.put(t, sovled);
				} else {
					allUnsovledTypes.add(t);
				}
			});
			viewInfer.unsolvedTupleTypeMap.values().forEach(t->{
				Tuple2<TupleType, Integer> sovled = findTupleType(t);
				if(sovled!=null) {
					unsolvedTypeMap.put(t, sovled);
				} else {
					allUnsovledTypes.add(t);
				}
			});
			
			for(int i=0;i<allUnsovledTypes.size();i++) {
				UnsolvedTupleType t = allUnsovledTypes.get(i);
				int j=0;
				for(j=0;j<i;j++) {
					UnsolvedTupleType tj = allUnsovledTypes.get(j);
					if(t.compare(tj)) {
						unsolvedTypeMap.put(t, Tuple2.make(tj, -j-1));
						break;
					}
				}
				if(i==j) unsolvedTypeMap.put(t, Tuple2.make(t, -i-1));
			}
		}
		return unsolvedTypeMap;
	}
	
	private Tuple2<TupleType, Integer> findTupleType(UnsolvedTupleType type) {
		Optional<Tuple2<TupleType, Integer>> res = this.tupleSet.stream().filter(t->type.compare(t.first)).findAny();
		if(res.isPresent()) return res.get();
		else return null;
	}
	
}
