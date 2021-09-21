package edu.ustb.sei.mde.bxcore.dsl.jvmmodel

import edu.ustb.sei.mde.bxcore.SourceType
import edu.ustb.sei.mde.bxcore.dsl.bXCore.AllInstanceExpression
import edu.ustb.sei.mde.bxcore.dsl.bXCore.BXCorePackage
import edu.ustb.sei.mde.bxcore.dsl.bXCore.ContextVarExpression
import edu.ustb.sei.mde.bxcore.dsl.bXCore.DashedPathType
import edu.ustb.sei.mde.bxcore.dsl.bXCore.DeleteElementExpression
import edu.ustb.sei.mde.bxcore.dsl.bXCore.EnforcementExpression
import edu.ustb.sei.mde.bxcore.dsl.bXCore.ExpressionConversion
import edu.ustb.sei.mde.bxcore.dsl.bXCore.InsertElementExpression
import edu.ustb.sei.mde.bxcore.dsl.bXCore.MatchExpression
import edu.ustb.sei.mde.bxcore.dsl.bXCore.ModificationExpressionBlock
import edu.ustb.sei.mde.bxcore.dsl.bXCore.NavigationExpression
import edu.ustb.sei.mde.bxcore.dsl.bXCore.NewInstanceExpression
import edu.ustb.sei.mde.bxcore.structures.GraphModification
import edu.ustb.sei.mde.bxcore.structures.GraphPath
import edu.ustb.sei.mde.bxcore.structures.Index
import edu.ustb.sei.mde.bxcore.structures.IndexPath
import edu.ustb.sei.mde.graph.typedGraph.ITypedEdge
import edu.ustb.sei.mde.graph.typedGraph.TypedEdge
import edu.ustb.sei.mde.graph.typedGraph.TypedNode
import edu.ustb.sei.mde.graph.typedGraph.ValueEdge
import java.util.List
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EDataType
import org.eclipse.emf.ecore.EEnum
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtext.diagnostics.Severity
import org.eclipse.xtext.validation.EObjectDiagnosticImpl
import org.eclipse.xtext.xbase.typesystem.computation.ITypeComputationState
import org.eclipse.xtext.xbase.typesystem.computation.XbaseTypeComputer
import org.eclipse.xtext.xbase.typesystem.conformance.ConformanceFlags
import org.eclipse.xtext.xbase.typesystem.references.LightweightTypeReference
import org.eclipse.xtext.xbase.validation.IssueCodes

class XmuCoreTypeComputer extends XbaseTypeComputer {
	
	def dispatch void computeTypes(ExpressionConversion cvar, ITypeComputationState state) {
		val valueTypeResult = state.withNonVoidExpectation.computeTypes(cvar.expression);
		state.acceptActualType(valueTypeResult.actualExpressionType);
	}
	
	
	def dispatch void computeTypes(ContextVarExpression cvar, ITypeComputationState state) {
		val side = cvar.side;
		val varName = cvar.name;
		
		val tupleType = ModelInferrerUtils.context(cvar, side);
		if(tupleType===null) {
			state.acceptActualType(getRawTypeForName(Object, state))
		} else {
			val typeDef = tupleType.tuples.findFirst[it.first.equals(varName)];
			if(typeDef===null) {
				state.addDiagnostic(
					new EObjectDiagnosticImpl(Severity.ERROR,
						IssueCodes.INVALID_IDENTIFIER, varName+" is undefined in the "+side,
						cvar, BXCorePackage.Literals.CONTEXT_VAR_EXPRESSION__NAME, -1, #[EcoreUtil.getURI(cvar).toString()]))
			}
			else {
				val type = typeDef.second;
				val contextOnly = ModelInferrerUtils.isContextOnly(cvar);
				val isList = typeDef.third;
				
				state.acceptActualType(actualType(contextOnly, type, state, isList))
			}
		}
    }
				
	protected def LightweightTypeReference actualType(boolean contextOnly, Object type,
		ITypeComputationState state, boolean isList) {

		val elementType = if (contextOnly) {
				if (type instanceof EClass)
					getRawTypeForName(Index, state)
				else if (type instanceof EEnum)
					getRawTypeForName(String, state)
				else if (type instanceof EDataType)
					getRawTypeForName(type.instanceClass, state)
				else if (type instanceof EReference)
					getRawTypeForName(Index, state)
				else if(type instanceof EAttribute) getRawTypeForName(Index, state) 
				else if(type instanceof DashedPathType) getRawTypeForName(IndexPath,state)
				else getRawTypeForName(Object,state)
			} else {
				if (type instanceof EClass)
					getRawTypeForName(TypedNode, state)
				else if (type instanceof EEnum)
					getRawTypeForName(String, state)
				else if (type instanceof EDataType)
					getRawTypeForName(type.instanceClass, state)
				else if (type instanceof EReference)
					getRawTypeForName(TypedEdge, state)
				else if(type instanceof EAttribute) getRawTypeForName(ValueEdge, state)
				else if(type instanceof DashedPathType) getRawTypeForName(GraphPath,state) 
				else getRawTypeForName(Object, state)
			}

		if (isList) {
			val owner = state.getReferenceOwner();
			val array = owner.newParameterizedTypeReference(owner.newReferenceTo(List).type);
			array.addTypeArgument(elementType);
			array
		} else
			elementType
	}
    
    def dispatch void computeTypes(NavigationExpression pathExp, ITypeComputationState state) {
    	val host = pathExp.host;
    	val path = pathExp.pathName;
    	
    	val hostType = state.withNonVoidExpectation.computeTypes(host);
    	
    	val ecoreType = ModelInferrerUtils.navEcoreType(pathExp);
    	
    	if(ecoreType===null) {
    		state.addDiagnostic(
				new EObjectDiagnosticImpl(Severity.ERROR, IssueCodes.INVALID_IDENTIFIER, "cannot navigate to " + path,
					pathExp, BXCorePackage.Literals.NAVIGATION_EXPRESSION__PATH_NAME, -1,
					#[EcoreUtil.getURI(pathExp).toString()]))
    	} else {
    		val owner = state.getReferenceOwner();
			val componmentType = if (ecoreType.key instanceof EClass)
					getRawTypeForName(TypedNode, state)
				else if (ecoreType.key instanceof EEnum)
					getRawTypeForName(String, state)
				else if (ecoreType.key instanceof EDataType)
					getRawTypeForName((ecoreType.key as EDataType).instanceClass, state)
				else if(ecoreType.key instanceof EAttribute)
					getRawTypeForName(ValueEdge, state)
				else if(ecoreType.key instanceof EReference)
					getRawTypeForName(TypedEdge, state) 
				else 
					getRawTypeForName(Object, state);
					
    		val type = if(ecoreType.value) {
    			val array = owner.newParameterizedTypeReference(owner.newReferenceTo(List).type);
    			array.addTypeArgument(componmentType);
    			array
    		} else {
    			componmentType
    		}
    		state.acceptActualType(type);
    	}
    }
    
    def dispatch void computeTypes(ModificationExpressionBlock modification, ITypeComputationState state) {
    	modification.expressions.forEach[e| state.withoutExpectation.computeTypes(e)];
    	state.acceptActualType(getRawTypeForName(SourceType, state),ConformanceFlags.NO_IMPLICIT_RETURN);
    }
    
    def dispatch void computeTypes(EnforcementExpression modification, ITypeComputationState state) {
    	modification.valueMappings.forEach[e|state.withNonVoidExpectation.computeTypes(e.expression)];
    	state.acceptActualType(getRawTypeForName(GraphModification, state));
    }
    
//    def boolean isCondition(EObject e) {
//    	if(!(e.eContainer instanceof XExpression)) true
//    	else if(e.eContainmentFeature===XbasePackage.Literals.XIF_EXPRESSION__IF 
//    		|| e.eContainmentFeature===XbasePackage.Literals.XCASE_PART__CASE
//    	) true
//    	else if(e.eContainer instanceof ModificationExpressionBlock) false
//    	else isCondition(e.eContainer)
//    }
    
    def dispatch void computeTypes(MatchExpression match, ITypeComputationState state) {
    	match.valueMappings.forEach[e|state.withNonVoidExpectation.computeTypes(e.expression)];
    	val mt = getRawTypeForName(GraphModification, state)
    	if(match.then!==null) state.withExpectation(mt).computeTypes(match.then);
    	if(match.otherwise!==null) state.withExpectation(mt).computeTypes(match.otherwise);
		state.acceptActualType(mt);
    }
    
    def dispatch void computeTypes(DeleteElementExpression modification, ITypeComputationState state) {
    	state.withNonVoidExpectation.computeTypes(modification.element);
    	state.acceptActualType(getRawTypeForName(GraphModification, state));
    }
    
    def dispatch void computeTypes(NewInstanceExpression instance, ITypeComputationState state) {
    	val typedNodeRef = getRawTypeForName(TypedNode, state);
    	
    	val elementType = if (instance.type.feature === null) {
				typedNodeRef
			} else {
				if(instance.type.feature instanceof EAttribute) getRawTypeForName(ValueEdge, state) 
				else getRawTypeForName(TypedEdge, state)
			}

    	if(instance.size!==null)
    		state.withExpectation(getRawTypeForName(int,state)).computeTypes(instance.size)
    	else if(instance.sourceValue!==null) {
    		if(elementType.isType(TypedNode)) {
    		} else if(elementType.isType(TypedEdge)) {
				state.withExpectation(typedNodeRef).computeTypes(instance.sourceValue);
    			state.withExpectation(typedNodeRef).computeTypes(instance.targetValue);
    		} else {
    			state.withExpectation(typedNodeRef).computeTypes(instance.sourceValue);
    			state.withNonVoidExpectation.computeTypes(instance.targetValue);
    		}
    		// TODO: if new path instance, extend here
    	}
    		
		
		if(instance.size!==null) {
			val owner = state.getReferenceOwner();
			val array = owner.newParameterizedTypeReference(owner.newReferenceTo(List).type);
			array.addTypeArgument(elementType);
			state.acceptActualType(array)
		} else {
			state.acceptActualType(elementType)
		}
    }
    
    def dispatch void computeTypes(AllInstanceExpression instance, ITypeComputationState state) {
    	val elementType = if (instance.type.feature === null) {
				getRawTypeForName(TypedNode, state)
			} else {
				if(instance.type.feature instanceof EAttribute) getRawTypeForName(ValueEdge, state) 
				else getRawTypeForName(TypedEdge, state)
			}
		val owner = state.getReferenceOwner();
			val array = owner.newParameterizedTypeReference(owner.newReferenceTo(List).type);
			array.addTypeArgument(elementType);
			state.acceptActualType(array)
    }
    
    def dispatch void computeTypes(InsertElementExpression insert, ITypeComputationState state) {
    	val iTypedEdgeRef = getRawTypeForName(ITypedEdge, state);
    	if(insert.anchor!==null) {
	    	state.withExpectation(iTypedEdgeRef).computeTypes(insert.element);
			state.withExpectation(iTypedEdgeRef).computeTypes(insert.anchor);
    	} else {
    		state.withNonVoidExpectation.computeTypes(insert.element);
//			state.withExpectation(iTypedEdgeRef).computeTypes(insert.element);
    	}
    	state.acceptActualType(getRawTypeForName(GraphModification, state));
    }
    
}