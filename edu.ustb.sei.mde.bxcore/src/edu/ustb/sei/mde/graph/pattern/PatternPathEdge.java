package edu.ustb.sei.mde.graph.pattern;

import java.util.List;
import java.util.function.BiFunction;

import edu.ustb.sei.mde.bxcore.exceptions.InitializationException;
import edu.ustb.sei.mde.bxcore.structures.Context;
import edu.ustb.sei.mde.bxcore.structures.GraphPath;
import edu.ustb.sei.mde.bxcore.structures.IndexPath;
import edu.ustb.sei.mde.graph.IEdge;
import edu.ustb.sei.mde.graph.INode;
import edu.ustb.sei.mde.graph.type.IPathType;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;

public class PatternPathEdge extends PatternElement<IPathType> implements IEdge {
	private PatternNode source;
	private PatternElement<?> target;
	
	@Override
	public INode getSource() {
		return source;
	}
	
	public void setSource(PatternNode source) {
		this.source = source;
		if(source.isCollection()) this.setCollection(true);
	}
	
	public INode getTarget() {
		return (INode) target;
	}
	public void setTarget(INode target) {
		if((target instanceof PatternNode) || (target instanceof PatternValueNode)) {
			this.target = (PatternElement<?>) target;
			if(((PatternElement<?>) target).isCollection()) 
				this.setCollection(true);			
		} else 
			throw new InitializationException("Invalid target of PatternPathEdge");
		
	}

	@Override
	public boolean isMatchOf(Context match, TypedGraph graph) {
		try {
			PatternNode sn = (PatternNode) getSource();
			PatternElement<?> tn = (PatternElement<?>) getTarget();
			Object sv = match.getValue(sn.getName());
			Object tv = match.getValue(tn.getName());
			
			if(isCollection()) {
				List<IndexPath> indexPaths = match.getValue(getName());
				BiFunction<Object,Integer,Object> sid = ((PatternElement<?>)getSource()).isCollection() ? LIST : ELEMENT;
				BiFunction<Object,Integer,Object> tid = ((PatternElement<?>)getTarget()).isCollection() ? LIST : ELEMENT;
				for(int i=0;i<indexPaths.size();i++) {
					GraphPath path = indexPaths.get(i).toGraphPath(graph);
					if(getElementType().isInstance(path)){
						if(!(isINodeEqual(path.getSource(), sid.apply(sv, i)) 
								&& isINodeEqual(path.getTarget(), tid.apply(tv,i)))) 
							return false;
					} else return false;
				}
				return true;
			} else {
				IndexPath indexPath = match.getValue(getName());
				GraphPath path = indexPath.toGraphPath(graph);
				if(this.getType().isInstance(path)) {
					if(!(isINodeEqual(path.getSource(),sv) 
							&& isINodeEqual(path.getTarget(), tv)))
						return false;
					else 
						return true;
				} else 
					return false;
			}
			
		} catch(Exception e) {
			return false;			
		}
	}

}
