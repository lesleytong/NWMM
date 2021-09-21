package edu.ustb.sei.mde.graph.type;

import java.util.ArrayList;

import edu.ustb.sei.mde.bxcore.exceptions.InitializationException;
import edu.ustb.sei.mde.bxcore.structures.GraphPath;
import edu.ustb.sei.mde.graph.IEdge;

/**
 * A dashed path type has a shape of (A|B|C)[min,max].(D|E)[min,max]...
 * It is a simplified version of the regular path type. 
 * @author hexiao
 *
 */
public class DashedPathType implements IPathType {
	public static DashedPathType create(DashedPathTypeSegment... segments) {
		return new DashedPathType(segments);
	}
	
	public DashedPathType(DashedPathTypeSegment... segments) {
		super();
		this.segments = segments;
	}
	public DashedPathTypeSegment[] getSegments() {
		return segments;
	}
	private DashedPathTypeSegment[] segments;
	
	
	@Override
	public Class<?> getJavaType() {
		return GraphPath.class;
	}
	@Override
	public boolean isInstance(Object value) {
		if(!(value instanceof GraphPath)) return false;
		GraphPath path = (GraphPath) value;
		return checkSegmentFrom(path, 0, 0, new PathCheckCache());
	}
	
	private boolean checkSegmentFrom(GraphPath path, int segIndex, int edgeIndex, PathCheckCache cache) {
		/*
		 * 1. Check min
		 * 2. Check max. If max<0, then max = path.lengh - edgeIndex.
		 * 3. Shortcut by checking cache.check(seg, edgeIndex).
		 * 4. For each possibility between min and max, check next seg 
		 */
		IEdge[] pathEdges = path.getPathEdges();
		
		if(segIndex==segments.length) {
			return edgeIndex==pathEdges.length;
		}
		if(edgeIndex==pathEdges.length) {
			return getMinLengthFrom(segIndex)==0;
		}

		DashedPathTypeSegment curSegment = segments[segIndex];
		
		Boolean cachedResult = cache.check(curSegment, edgeIndex);
		if(cachedResult!=null) return cachedResult;
		
		int min = curSegment.getMin();
		int max = curSegment.getMax();
		if(max<0 || max>pathEdges.length-edgeIndex) max = pathEdges.length-edgeIndex;
		
		if(max<min) {
			cache.setCache(curSegment, edgeIndex, false);
			return false;
		}
		
		for(int cur = 0; cur < min; cur++) {
			if(!curSegment.isInstance(pathEdges[edgeIndex + cur])) {
				cache.setCache(curSegment, edgeIndex, false);
				return false;
			}
		}
		
		ArrayList<Integer> posArr = new ArrayList<>();
		posArr.add(edgeIndex + min);
		for(int cur = min; cur < max; cur++) {
			int actualPos = edgeIndex + cur;
			posArr.add(actualPos + 1);
			if(curSegment.isInstance(pathEdges[actualPos])) continue;
			else break; // break the loop
		}
		
		for(Integer pos : posArr) {
			if(checkSegmentFrom(path, segIndex + 1, pos, cache)) {
				return true;
			}
		}
		
		cache.setCache(curSegment, edgeIndex, false);
		return false;
	}
	
	public int getMinLengthFrom(int segIndex) {
		int min = 0;
		for(DashedPathTypeSegment s : segments) {
			min += s.getMin();
		}
		return min;
	}
	public int getMaxLengthFrom(int segIndex) {
		int max = 0;
		for(DashedPathTypeSegment s : segments) {
			int sMax = s.getMax();
			if(sMax<0) return -1;
			max += sMax;
		}
		return max;
	}
	
	
	
	@Override
	public String getName() {
		return toString();
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		boolean first = true;
		for(DashedPathTypeSegment seg : segments) {
			if(!first) builder.append(".");
			first = false;
			builder.append(seg.toString());
		}
		return builder.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj!=null && obj instanceof DashedPathType) {
			if(this.segments.length!=((DashedPathType)obj).segments.length) return false;
			for(int i=0;i<this.segments.length;i++) {
				if(!this.segments[i].equals(((DashedPathType)obj).segments[i])) return false;
			}
			return true;
		}
		return false;
	}
	
	
	public IStructuralFeatureEdge getSingleEdge() {
		int min = this.getMinLengthFrom(0);
		if(min>1) return null;
		else {
			if(min==0) {
				return segments[0].getEdgeTypes()[0];
			} else {
				for(DashedPathTypeSegment s : segments) {
					if(s.getMin()==1) return s.getEdgeTypes()[0];
				}
				throw new InitializationException();
			}
		}
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		for(DashedPathTypeSegment s : segments) {
			hash += s.hashCode();
		}
		return hash;
	}
	
}