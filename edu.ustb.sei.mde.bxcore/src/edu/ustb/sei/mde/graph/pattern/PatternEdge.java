package edu.ustb.sei.mde.graph.pattern;

import java.util.List;
import java.util.function.BiFunction;

import edu.ustb.sei.mde.bxcore.structures.Context;
import edu.ustb.sei.mde.bxcore.structures.Index;
import edu.ustb.sei.mde.graph.IEdge;
import edu.ustb.sei.mde.graph.type.TypeEdge;
import edu.ustb.sei.mde.graph.typedGraph.TypedEdge;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;

public class PatternEdge extends PatternElement<TypeEdge> implements IEdge {
	private PatternNode source;
	private PatternNode target;
	
	
	public PatternNode getSource() {
		return source;
	}
	public void setSource(PatternNode source) {
		this.source = source;
		if(source.isCollection()) this.setCollection(true);
	}
	public PatternNode getTarget() {
		return target;
	}
	public void setTarget(PatternNode target) {
		this.target = target;
		if(target.isCollection()) this.setCollection(true);
	}
	
	public String toString() {
		return this.getName() + (isCollection() ? "[]" : "") + ":" + source.getName()
				+ (source.isCollection() ? "[]" : "") + "->" + target.getName() + (target.isCollection() ? "[]" : "");
	}

	@Override
	public boolean isMatchOf(Context match, TypedGraph graph) {
		try {
			PatternNode sn = (PatternNode) getSource();
			PatternNode tn = (PatternNode) getTarget();
			Object sv = match.getValue(sn.getName());
			Object tv = match.getValue(tn.getName());
			
			if(isCollection()) {
				List<Index> edgeIndices = match.getValue(getName());
				BiFunction<Object,Integer,Object> sid = getSource().isCollection() ? LIST : ELEMENT;
				BiFunction<Object,Integer,Object> tid = getTarget().isCollection() ? LIST : ELEMENT;

				for(int i=0;i<edgeIndices.size();i++) {
					Index idx = edgeIndices.get(i);
					TypedEdge edge = graph.getElementByIndexObject(idx);
					if(getElementType().isInstance(edge)){
						if(!(isNodeEqual(edge.getSource(), (Index) sid.apply(sv, i)) 
								&& isNodeEqual(edge.getTarget(), (Index) tid.apply(tv,i)))) return false;
					} else return false;
				}
				return true;
			} else {
				Index edgeIndex = match.getValue(getName());
				TypedEdge edge = graph.getElementByIndexObject(edgeIndex);
				if(getElementType().isInstance(edge)) {
					return isNodeEqual(edge.getSource(),(Index)sv) 
							&& isNodeEqual(edge.getTarget(), (Index)tv);
				} else return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
}
