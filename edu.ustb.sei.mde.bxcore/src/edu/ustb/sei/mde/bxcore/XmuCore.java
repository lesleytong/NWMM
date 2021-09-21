package edu.ustb.sei.mde.bxcore;


import java.util.List;

import edu.ustb.sei.mde.bxcore.bigul.BidirectionalTransformation;
import edu.ustb.sei.mde.bxcore.exceptions.BidirectionalTransformationDefinitionException;
import edu.ustb.sei.mde.bxcore.exceptions.InternalBidirectionalTransformationError;
import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.bxcore.structures.Context;
import edu.ustb.sei.mde.bxcore.structures.ContextType;
import edu.ustb.sei.mde.bxcore.structures.Environment;
import edu.ustb.sei.mde.graph.typedGraph.constraint.GraphConstraint;



public abstract class XmuCore extends BidirectionalTransformation<SourceType, ViewType> {
	
	private Environment environment;
	
	protected void checkWellDefinedness() throws BidirectionalTransformationDefinitionException {
	}

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
	
	
	public XmuCore(Object key, ContextType sourceDef, ContextType viewDef) throws BidirectionalTransformationDefinitionException{
		this.key = key;
		this.sourceDef = sourceDef;
		this.viewDef = viewDef;
	}
	
	private Object key;
	
	public Object getSerializationKey() {
		return key;
	}
	
	public ContextType getSourceDef() {
		return sourceDef;
	}

	public ContextType getViewDef() {
		return viewDef;
	}

	private ContextType sourceDef;
	
	private ContextType viewDef;
	
	
	public boolean checkSourceContextStructure(Context c) {
		return sourceDef.isTypeOf(c);
	}
	public boolean checkViewContextStructure(Context c) {
		return viewDef.isTypeOf(c);
	}
	
	public Context createSourceContext() {
		return sourceDef.createInstance();
	}
	
	public Context createViewContext() {
		return viewDef.createInstance();
	}
	
	public void submit(Context... contexts) {
		for(Context c : contexts) 
			c.submit();
	}
	
	public void submit(List<Context> contexts) {
		for(Context c : contexts) 
			c.submit();
	}
	
	
	private GraphConstraint constraint; 
	public GraphConstraint getConsistencyConstraint() {
		if(constraint==null)
			constraint = generateConsistencyConstraint();
		return constraint;
	}
	
	abstract protected GraphConstraint generateConsistencyConstraint();
	
	@Override
	public <T> T nothing() throws NothingReturnedException {
		throw new NothingReturnedException(this.key.toString());
	}
	
	@Override
	public <T> T nothing(Exception e) throws NothingReturnedException {
		throw new NothingReturnedException(this.key.toString(),e);
	}
	
	@Override
	public <T> T internalError() throws InternalBidirectionalTransformationError {
		throw new InternalBidirectionalTransformationError(key.toString());
	}
	
	@Override
	public <T> T internalError(Exception e) throws InternalBidirectionalTransformationError {
		throw new InternalBidirectionalTransformationError(key.toString(),e);
	}
}
