package edu.ustb.sei.mde.bxcore;

import edu.ustb.sei.mde.bxcore.structures.ContextType;

public class IndexSignature {
	public IndexSignature(String signatureName, ContextType sourceType, ContextType viewType) {
		super();
		this.signatureName = signatureName;
		this.sourceType = sourceType;
		this.viewType = viewType;
	}
	
	private String signatureName;

	private ContextType sourceType;
	private ContextType viewType;
	
	public String getSignatureName() {
		return signatureName;
	}
	public void setSignatureName(String signatureName) {
		this.signatureName = signatureName;
	}
	public ContextType getSourceType() {
		return sourceType;
	}
	public void setSourceType(ContextType sourceType) {
		this.sourceType = sourceType;
	}
	public ContextType getViewType() {
		return viewType;
	}
	public void setViewType(ContextType viewType) {
		this.viewType = viewType;
	}
}
