package edu.ustb.sei.mde.bxcore.dsl.jvmmodel

import edu.ustb.sei.mde.bxcore.dsl.bXCore.BXCorePackage
import edu.ustb.sei.mde.bxcore.dsl.bXCore.BXProgram
import edu.ustb.sei.mde.bxcore.dsl.bXCore.ContextAwareCondition
import edu.ustb.sei.mde.bxcore.dsl.bXCore.ContextAwareDerivationAction
import edu.ustb.sei.mde.bxcore.dsl.bXCore.ContextAwareUnidirectionalAction
import edu.ustb.sei.mde.bxcore.dsl.bXCore.ContextExpression
import edu.ustb.sei.mde.bxcore.dsl.bXCore.ContextVarExpression
import edu.ustb.sei.mde.bxcore.dsl.bXCore.ExpressionConversion
import edu.ustb.sei.mde.bxcore.dsl.bXCore.NavigationExpression
import edu.ustb.sei.mde.bxcore.dsl.bXCore.Pattern
import edu.ustb.sei.mde.bxcore.dsl.bXCore.PatternDefinitionReference
import edu.ustb.sei.mde.bxcore.dsl.bXCore.PatternTypeLiteral
import edu.ustb.sei.mde.bxcore.dsl.bXCore.SideEnum
import edu.ustb.sei.mde.bxcore.dsl.bXCore.TypeLiteral
import edu.ustb.sei.mde.bxcore.dsl.bXCore.XmuCoreAlign
import edu.ustb.sei.mde.bxcore.dsl.bXCore.XmuCoreDependencyView
import edu.ustb.sei.mde.bxcore.dsl.bXCore.XmuCoreDeriveSource
import edu.ustb.sei.mde.bxcore.dsl.bXCore.XmuCoreStatement
import edu.ustb.sei.mde.bxcore.dsl.infer.InferManager
import edu.ustb.sei.mde.bxcore.dsl.infer.TypeInferenceException
import edu.ustb.sei.mde.bxcore.dsl.structure.TupleType
import edu.ustb.sei.mde.structure.Tuple2
import java.util.ArrayList
import java.util.HashMap
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject

class ModelInferrerUtils {
	static def groupTypeLiterals(BXProgram program) {
		val literals = new ArrayList;
		val itr = program.eAllContents;
		while(itr.hasNext()) {
			val e = itr.next;
			if(e.eIsProxy) {
				throw new TypeInferenceException(e,null,null,null);
			} else {
				if(e instanceof TypeLiteral) {
					literals.add((e as TypeLiteral)->TupleType.make(e as TypeLiteral));
				}
			}
		}
		
		val groups = literals.groupBy[it.value];
		val result = new HashMap;
		
		groups.forEach[k,v,id|
			val pair = Tuple2.make(k,id);
			v.forEach[p|result.put(p.key, pair);];
		];
		
		return result;
	}
	
	static def TupleType context(EObject e, SideEnum side) {
    	try {
    		if (e === null) null
			else if (e instanceof Pattern) {
//				if (side === SideEnum.SELF) {
					val inferData = InferManager.getInferredTypeModel(e.eResource);
					if (e instanceof PatternDefinitionReference) {
						inferData.literalMap.get(e.pattern.literal).first
					} else if (e instanceof PatternTypeLiteral) {
						inferData.literalMap.get(e).first
					} else
						null
//				} else
//					null
			} else if(e instanceof ContextAwareCondition) {
				if(e.eContainingFeature===BXCorePackage.Literals.XMU_CORE_ALIGN__ALIGNMENT) {
					if(side===SideEnum.SOURCE) (e.eContainer as XmuCoreAlign).sourcePattern.context(SideEnum.SOURCE)
					else if(side===SideEnum.VIEW)  (e.eContainer as XmuCoreAlign).viewPattern.context(SideEnum.VIEW)
					else null
				} else if(e.eContainingFeature===BXCorePackage.Literals.PATTERN_TYPE_LITERAL__FILTER) {
					if(side===SideEnum.CONTEXT) e.eContainer.context(SideEnum.CONTEXT)
					else null
				}
				else e.eContainer.context(side)
			} else if(e instanceof ContextAwareUnidirectionalAction) {
				if(e.eContainingFeature===BXCorePackage.Literals.XMU_CORE_ALIGN__UNMATCH_S) {
					if(side===SideEnum.SOURCE) (e.eContainer as XmuCoreAlign).sourcePattern.context(SideEnum.SOURCE)
					else if(side===SideEnum.VIEW) (e.eContainer as XmuCoreAlign).context(SideEnum.VIEW)
					else null
				} else if(e.eContainingFeature===BXCorePackage.Literals.XMU_CORE_ALIGN__UNMATCH_V) {
					if(side===SideEnum.SOURCE) (e.eContainer as XmuCoreAlign).context(SideEnum.SOURCE)
					else if(side===SideEnum.VIEW) (e.eContainer as XmuCoreAlign).viewPattern.context(SideEnum.VIEW)
					else null
				} else e.eContainer.context(side)
			} else if(e instanceof ContextAwareDerivationAction) {
				val stmt = e.containingStatementOrPattern;
				if(side===SideEnum.SOURCE) {
					if(stmt instanceof XmuCoreDeriveSource) stmt.context(side)
					else null
				} else if(side===SideEnum.VIEW) {
					if(stmt instanceof XmuCoreDependencyView) 
						stmt.context(side)
					else null
				} else { // side===Context
					if(stmt instanceof PatternTypeLiteral)
						stmt.context(side)
					else null
				}
			} 
			else if (e instanceof XmuCoreStatement) {
				val inferData = InferManager.getInferredTypeModel(e.eResource);
				if(side===SideEnum.SOURCE) inferData.sourceInfer.unsolvedTupleTypeMap.get(e)
				else if(side===SideEnum.VIEW) inferData.viewInfer.unsolvedTupleTypeMap.get(e)
				else null
			} else e.eContainer.context(side)
    		
    	} catch(Exception exp) {
    		return null;
    	}
    }
    
    static def EObject containingStatementOrPattern(EObject e) {
    	if(e===null || e instanceof XmuCoreStatement || e instanceof Pattern) e
    	else e.eContainer.containingStatementOrPattern
    }
    
    static def boolean isContextOnly(EObject e) {
    	false
//		if(e===null) false
//		else if(e instanceof ContextAwareCondition) {
//			e.eContainingFeature===BXCorePackage.Literals.PATTERN_TYPE_LITERAL__FILTER 
//		} else if(e instanceof ContextAwareUnidirectionalAction) false
//		else e.eContainer.isContextOnly
	}
	
	static def Pair<Object, Boolean> navEcoreType(ContextExpression e) {
    	if(e instanceof ContextVarExpression) {
    		val side = e.side;
			val varName = e.name;
			try {
				val tupleType = ModelInferrerUtils.context(e, side);
				if (tupleType === null)
					null
				else {
					val typeDef = tupleType.tuples.findFirst[it.first.equals(varName)];
					if(typeDef === null) null 
					else {
						typeDef.second -> typeDef.third
					}
				}
			} catch (Exception x) {
				null
			}
    	} else if(e instanceof NavigationExpression) {
    		val hostType = e.host.navEcoreType;
    		val path = e.pathName;
    		val isNode = e.navOp.equals('@');
    		
    		if(hostType===null) null
    		else {
    			if(hostType.key instanceof EClass) {
    				val pathType = (hostType.key as EClass).getEStructuralFeature(path);
    				if(pathType===null) null
    				else {
    					if(isNode)
    						pathType.EType -> (hostType.value || pathType.isMany)
    					else pathType -> (hostType.value || pathType.isMany)
    				}
    			} else null
    		}
    	} else if(e instanceof ExpressionConversion) {
    		val type = e.type;
    		return type->e.many
    	}
    }
}