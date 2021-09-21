package edu.ustb.sei.mde.graph.type;

import java.util.Arrays;

import edu.ustb.sei.mde.graph.IEdge;

public class DashedPathTypeSegment {
	public DashedPathTypeSegment(IStructuralFeatureEdge[] edgeTypes, int min, int max) {
		super();
		this.edgeTypes = edgeTypes;
		this.min = min;
		this.max = max;
	}
	
	public DashedPathTypeSegment(int min, int max, IStructuralFeatureEdge... edgeTypes) {
		super();
		this.edgeTypes = edgeTypes;
		this.min = min;
		this.max = max;
	}
	
	static public DashedPathTypeSegment createOne(IStructuralFeatureEdge... edgeTypes) {
		return new DashedPathTypeSegment(1, 1, edgeTypes);
	}
	
	static public DashedPathTypeSegment createZeroOrOne(IStructuralFeatureEdge... edgeTypes) {
		return new DashedPathTypeSegment(0, 1, edgeTypes);
	}
	
	static public DashedPathTypeSegment createZeroOrMany(IStructuralFeatureEdge... edgeTypes) {
		return new DashedPathTypeSegment(0, -1, edgeTypes);
	}
	
	static public DashedPathTypeSegment createOneOrMany(IStructuralFeatureEdge... edgeTypes) {
		return new DashedPathTypeSegment(1, -1, edgeTypes);
	}
	
	
	private IStructuralFeatureEdge[] edgeTypes;
	private int min;
	private int max;
	
	public IStructuralFeatureEdge[] getEdgeTypes() {
		return edgeTypes;
	}
	public int getMin() {
		return min;
	}
	public int getMax() {
		return max;
	}
	
	public boolean isInstance(IEdge edge) {
		for(IStructuralFeatureEdge type : edgeTypes) {
			if(type.isInstance(edge)) return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("(");
		boolean flag = true;
		for(IStructuralFeatureEdge et : edgeTypes) {
			if(!flag) builder.append("|");
			flag = false;
			builder.append(et.getName());
		}
		builder.append(")[");
		builder.append(min);
		builder.append(",");
		builder.append(max);
		builder.append("]");
		return super.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj!=null && obj instanceof DashedPathTypeSegment) {
			return Arrays.equals(this.edgeTypes, ((DashedPathTypeSegment)obj).edgeTypes);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		for(IStructuralFeatureEdge e : edgeTypes) {
			hash += e.hashCode();
		}
		return hash;
	}
}