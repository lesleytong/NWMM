package edu.ustb.sei.mde.graph.pattern;

import java.util.List;
import java.util.function.BiFunction;

import edu.ustb.sei.mde.bxcore.structures.Context;
import edu.ustb.sei.mde.bxcore.structures.Index;
import edu.ustb.sei.mde.graph.IEdge;
import edu.ustb.sei.mde.graph.type.PropertyEdge;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;
import edu.ustb.sei.mde.graph.typedGraph.ValueEdge;

public class PatternValueEdge extends PatternElement<PropertyEdge> implements IEdge {
	
	private PatternNode source;
	private PatternValueNode target;
	
	
	public PatternNode getSource() {
		return source;
	}
	public void setSource(PatternNode source) {
		this.source = source;
		if(source.isCollection()) this.setCollection(true);
	}
	public PatternValueNode getTarget() {
		return target;
	}
	public void setTarget(PatternValueNode target) {
		this.target = target;
		if(target.isCollection()) this.setCollection(true);
	}
	
	public String toString() {
		return this.getName()+(isCollection() ? "[]" : "")+":"+source.getName()+(source.isCollection() ? "[]" : "")+"->"+target.getName()+(target.isCollection() ? "[]" : "");
	}
	
	@Override
	public boolean isMatchOf(Context match, TypedGraph graph) {
		try {
			PatternNode sn = (PatternNode) getSource();
			PatternValueNode tn = (PatternValueNode) getTarget();
			Object sv = match.getValue(sn.getName());
			Object tv = match.getValue(tn.getName());
			
			if(isCollection()) {				
				List<Index> edgeIndices = match.getValue(getName());
				BiFunction<Object,Integer,Object> sid = getSource().isCollection() ? LIST : ELEMENT;
				BiFunction<Object,Integer,Object> tid = getTarget().isCollection() ? LIST : ELEMENT;

				for(int i=0;i<edgeIndices.size();i++) {
					Index idx = edgeIndices.get(i);
					ValueEdge edge = graph.getElementByIndexObject(idx);
					if(getElementType().isInstance(edge)){
						if(!(isNodeEqual(edge.getSource(), (Index) sid.apply(sv, i)) 
								&& isValueEqual(edge.getTarget(), tid.apply(tv,i)))) return false;
					} else return false;
				}
				return true;
			} else {
				Index edgeIndex = match.getValue(getName());
				ValueEdge edge = graph.getElementByIndexObject(edgeIndex);
				if(getElementType().isInstance(edge)) {
					return isNodeEqual(edge.getSource(),(Index) sv) 
							&& isValueEqual(edge.getTarget(), tv);
				} else return false;
			}
		} catch(Exception e) {
			return false;
		}
	}

}
