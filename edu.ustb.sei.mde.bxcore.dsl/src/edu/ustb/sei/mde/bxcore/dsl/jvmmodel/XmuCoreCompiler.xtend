package edu.ustb.sei.mde.bxcore.dsl.jvmmodel

import edu.ustb.sei.mde.bxcore.SourceType
import edu.ustb.sei.mde.bxcore.dsl.bXCore.ContextExpression
import edu.ustb.sei.mde.bxcore.dsl.bXCore.ContextVarExpression
import edu.ustb.sei.mde.bxcore.dsl.bXCore.DeleteElementExpression
import edu.ustb.sei.mde.bxcore.dsl.bXCore.EnforcementExpression
import edu.ustb.sei.mde.bxcore.dsl.bXCore.ExpressionConversion
import edu.ustb.sei.mde.bxcore.dsl.bXCore.ModificationExpression
import edu.ustb.sei.mde.bxcore.dsl.bXCore.ModificationExpressionBlock
import edu.ustb.sei.mde.bxcore.dsl.bXCore.NavigationExpression
import edu.ustb.sei.mde.bxcore.dsl.bXCore.NewInstanceExpression
import edu.ustb.sei.mde.bxcore.dsl.bXCore.Pattern
import edu.ustb.sei.mde.bxcore.dsl.bXCore.PatternDefinitionReference
import edu.ustb.sei.mde.bxcore.dsl.bXCore.PatternTypeLiteral
import edu.ustb.sei.mde.bxcore.dsl.bXCore.SideEnum
import edu.ustb.sei.mde.bxcore.dsl.infer.InferManager
import edu.ustb.sei.mde.bxcore.dsl.structure.ExceptionSafeInferface
import edu.ustb.sei.mde.bxcore.structures.ContextGraph
import edu.ustb.sei.mde.bxcore.structures.GraphModification
import edu.ustb.sei.mde.bxcore.structures.Index
import edu.ustb.sei.mde.graph.typedGraph.TypedEdge
import edu.ustb.sei.mde.graph.typedGraph.TypedNode
import edu.ustb.sei.mde.graph.typedGraph.ValueEdge
import edu.ustb.sei.mde.structure.Tuple2
import java.util.ArrayList
import java.util.List
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.xbase.XBlockExpression
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XIfExpression
import org.eclipse.xtext.xbase.compiler.XbaseCompiler
import org.eclipse.xtext.xbase.compiler.output.ITreeAppendable
import org.eclipse.xtext.xbase.typesystem.references.LightweightTypeReference
import edu.ustb.sei.mde.bxcore.dsl.bXCore.AllInstanceExpression
import edu.ustb.sei.mde.bxcore.dsl.bXCore.InsertElementExpression
import org.eclipse.xtext.common.types.JvmParameterizedTypeReference
import org.eclipse.xtext.xbase.XbasePackage
import edu.ustb.sei.mde.bxcore.dsl.bXCore.MatchExpression

class XmuCoreCompiler extends XbaseCompiler {
	def patternAccessor(Pattern p) {
		try {
			val data = InferManager.getInferredTypeModel(p.eResource);
			if(data===null) {
				println('null');
			}
			if (p instanceof PatternTypeLiteral) {
				val lit = data.patternLiterals.findFirst[it.value===p]
				if(lit===null) {
					println('null');
				}
				'getPattern_' + lit.key
			}
			else if(p instanceof PatternDefinitionReference)
				p.pattern.name
			else '/* ERROR: unknown pattern type */'
		} catch (Exception e) {
			'''/* ERROR: «e.message»*/'''
		}
	}
	
	override protected doInternalToJavaStatement(XExpression e, ITreeAppendable a, boolean isReferenced) {
		if (e instanceof ContextVarExpression) {
			val expectedType = getType(e);
			if (isReferenced) {
				val name = a.declareSyntheticVariable(e, "_contextValue");
				a.newLine.append('''«expectedType.qualifiedName» «name» = ''');
				a.append('''((«expectedType.qualifiedName») «ExceptionSafeInferface.canonicalName».getValue(«e.side.literal»,"«e.name»"))''')
				a.append(";")
			} else {
				a.append('''((«expectedType.qualifiedName») «ExceptionSafeInferface.canonicalName».getValue(«e.side.literal»,"«e.name»"))''')
				a.append(";")
			}
		} else if(e instanceof ExpressionConversion) {
			val expectedType = getType(e);
			e.expression.doInternalToJavaStatement(a,true);
			
			if (isReferenced) {
				val name = a.declareSyntheticVariable(e, "_expValue");
				a.newLine.append('''«expectedType.qualifiedName» «name» = ''');
				e.expression.internalToJavaExpression(a);
				a.append(";")
			} else {
				e.expression.internalToJavaExpression(a);
				a.append(";")
			}
		} else if(e instanceof NavigationExpression) {
			e.host.doInternalToJavaStatement(a,true);
			a.newLine;
			val expectedType = getType(e);
			val ecoreType = ModelInferrerUtils.navEcoreType(e);
			val navEdge = e.navOp.equals('@');
			
			if(isReferenced) {
				val name = a.declareSyntheticVariable(e, "_navExp");
				a.newLine.append('''«expectedType.qualifiedName» «name» = «ExceptionSafeInferface.canonicalName».navigate(«e.side.literal», ''');
				e.host.internalToJavaExpression(a);
				a.append(''', "«e.pathName»", «ecoreType.key instanceof EClass || ecoreType.key instanceof EReference», «ecoreType.value», «navEdge»);''');
			} else {
				a.newLine.append('''«ExceptionSafeInferface.canonicalName».navigate(«e.side.literal», ''');
				e.host.internalToJavaExpression(a);
				a.append(''', "«e.pathName»", «ecoreType.key instanceof EClass || ecoreType.key instanceof EReference», «ecoreType.value», «navEdge»);''');
			}
		} else if(e instanceof ModificationExpressionBlock) {
			a.newLine;
			val mo = a.declareSyntheticVariable(e, '_modStart');
			a.append('''«GraphModification.canonicalName» «mo» = source.modification();''');
			
			e.expressions.forEach[me|
				me.internalToJavaStatement(a, false);
			];
			if(isReferenced) {
//				val v = a.declareSyntheticVariable(e, '_modRes');
//				a.append('''«SourceType.canonicalName» «v» = ''');
//				e.internalToJavaExpression(a);
//				a.append(''';''');
			} else {
				a.append('''return ''');
				e.internalToJavaExpression(a);
				a.append(''';''');
			}
		} else if(e instanceof ModificationExpression) {
			val block = e.block;
			val blockVar = getVarName(block, a);
			val cur = a.declareSyntheticVariable(e, '_mod');
			
			if(e instanceof EnforcementExpression) {
				e.valueMappings.forEach[it.expression.internalToJavaStatement(a,true)];
				a.newLine.append('''«GraphModification.canonicalName» «cur» = «blockVar».enforce(«e.pattern.patternAccessor»(), new edu.ustb.sei.mde.structure.Tuple2[] {''');
				e.valueMappings.forEach [ m, id |
					if(id > 0) a.append(',');
					a.append('''edu.ustb.sei.mde.structure.Tuple2.make("«m.varName»",''');
					m.expression.internalToJavaExpression(a);
					a.append(''')''');
				];
				a.append('''});''').newLine;
			} else if(e instanceof DeleteElementExpression) {
				e.element.internalToJavaStatement(a, true);
				a.newLine.append('''«GraphModification.canonicalName» «cur» = «blockVar».remove(''');
				e.element.internalToJavaExpression(a);
				a.append(''');''').newLine;
			} else if(e instanceof InsertElementExpression) {
				e.element.internalToJavaStatement(a, true);
				val elemType = getType(e.element);
				
				if(e.anchor!==null) {
					e.anchor.internalToJavaStatement(a, true);
				}
				
				val actType = if(elemType.qualifiedName.startsWith('java.util.List')) {
					(elemType as JvmParameterizedTypeReference).arguments.get(0)
				} else elemType;
				
				a.newLine.append('''«GraphModification.canonicalName» «cur» = «blockVar».insert«actType.simpleName»«IF e.position!==null»«e.position.toFirstUpper»«ENDIF»(''');
				e.element.internalToJavaExpression(a);
				if(e.anchor!==null) {
					a.append(',');
					e.anchor.internalToJavaExpression(a);
				}
				a.append(');')
			} else if(e instanceof MatchExpression) {
				e.valueMappings.forEach[it.expression.internalToJavaStatement(a,true)];
				a.newLine.append('''«GraphModification.canonicalName» «cur» = «blockVar».match(«e.pattern.patternAccessor»(), new edu.ustb.sei.mde.structure.Tuple2[] {''');
				e.valueMappings.forEach [ m, id |
					if(id > 0) a.append(',');
					a.append('''edu.ustb.sei.mde.structure.Tuple2.make("«m.varName»",''');
					m.expression.internalToJavaExpression(a);
					a.append(''')''');
				];
				a.append('''});''').newLine;
				a.append('''boolean «cur»_changed = «blockVar»!=«cur»;''').newLine;
				a.append('''«blockVar» = «cur»;''').newLine;
				
				a.append('''if(«cur»_changed) {''').newLine;
				if(e.then!==null) {
					e.then.internalToJavaStatement(a,true);
					a.append('''«cur» = ''');
					e.then.internalToJavaExpression(a);
					a.append(''';''').newLine;
				}
				a.append('} else {').newLine;
				if(e.otherwise!==null) {
					e.otherwise.internalToJavaStatement(a,true);
					a.append('''«cur» = ''');
					e.otherwise.internalToJavaExpression(a);
					a.append(''';''').newLine;
				}
				a.append('}')
				
			}
			a.append('''«blockVar» = «cur»;'''); // new scope, we must reset
		} else if(e instanceof NewInstanceExpression) {
			val componentType = if (e.type.feature === null)
					TypedNode
				else if(e.type.feature instanceof EReference) TypedEdge else ValueEdge;

			val v = a.declareSyntheticVariable(e, 'newInstance');
			if (e.size !== null) {
				e.size.internalToJavaStatement(a, true);
				val iv = a.declareUniqueNameVariable(e, 'it');
				a.newLine.append('''java.util.List<«componentType.canonicalName»> «v» = new java.util.ArrayList<>();''');
				a.newLine.append('''for(int «iv»=0;«iv»<''');
				e.size.internalToJavaExpression(a);
				a.append(''';«iv»++) «v».add(«ContextGraph.canonicalName».new«componentType.simpleName»(«e.type.side.literal», "«e.type.type.name»"«IF e.type.feature!==null», "«e.type.feature.name»"«ENDIF»));''')
			} else {
				if(e.sourceValue!==null) {
					e.sourceValue.internalToJavaStatement(a, true);
					e.targetValue.internalToJavaStatement(a, true);
				}
				a.newLine.append('''«componentType.canonicalName» «v» = «ContextGraph.canonicalName».new«componentType.simpleName»(«e.type.side.literal», "«e.type.type.name»"''');
				if(e.type.feature!==null) {
					a.append(''', "«e.type.feature.name»"''');
					if(e.sourceValue!==null) {
						a.append(',');
						e.sourceValue.internalToJavaExpression(a);
						a.append(',');
						e.targetValue.internalToJavaExpression(a);
					}
				}
				a.append(');');
			}
		} else if(e instanceof AllInstanceExpression) {
			val componentType = if (e.type.feature === null)
					TypedNode
				else if(e.type.feature instanceof EReference) TypedEdge else ValueEdge;
			
			if(isReferenced) {
				val v = a.declareSyntheticVariable(e, 'allInstances');
				a.newLine.append('''java.util.List<«componentType.canonicalName»> «v» = «e.type.side.literal».all«componentType.simpleName»s("«e.type.type.name»"«IF e.type.feature!==null»,"«e.type.feature.name»"«ENDIF»);''')
			} else {
				a.newLine.append('''«e.type.side.literal».all«componentType.simpleName»s("«e.type.type.name»"«IF e.type.feature!==null»,"«e.type.feature.name»"«ENDIF»);''');
			}
			
		} else
			super.doInternalToJavaStatement(e, a, isReferenced)
	}
	
	def SideEnum side(ContextExpression expression) {
		if(expression instanceof ContextVarExpression) expression.getSide
		else if(expression instanceof ExpressionConversion) expression.getSide
		else if(expression instanceof NavigationExpression) expression.host.side
		else null;
	}
	
	override protected internalToConvertedExpression(XExpression e, ITreeAppendable a) {
		if (e instanceof ContextVarExpression) {
			if(a.hasName(e)) {
				a.trace(e, false).append(getVarName(e, a))
			} else {
				val expectedType = getType(e);
				a.append('''((«expectedType.qualifiedName») «ExceptionSafeInferface.canonicalName».getValue(«e.side.literal»,"«e.name»"))''')
			}
		} else if (e instanceof ExpressionConversion) {
			if(a.hasName(e)){
				a.trace(e, false).append(getVarName(e, a))
			} else {
				e.expression.doInternalToJavaStatement(a,true);
				e.expression.internalToJavaExpression(a);
			}
		} else if(e instanceof NavigationExpression) {
			if(a.hasName(e)){
				a.trace(e, false).append(getVarName(e, a))
			} else {
				e.host.doInternalToJavaStatement(a,true);
				val ecoreType = ModelInferrerUtils.navEcoreType(e);
				val navEdge = e.navOp.equals('@');
				a.newLine.append('''«ExceptionSafeInferface.canonicalName».navigate(«e.side.literal», ''');
				e.host.internalToJavaExpression(a);
				a.append(''', "«e.pathName»", «ecoreType.key instanceof EClass || ecoreType.key instanceof EReference», «ecoreType.value», «navEdge»)''');
			}
		}  else if(e instanceof ModificationExpression) {
			a.append(getVarName(e, a))
		} else if(e instanceof ModificationExpressionBlock) {
			val lastResult = getVarName(e, a);
			a.append('''«lastResult».get()''')
		} else if(e instanceof NewInstanceExpression) {
			val v = getVarName(e,a);
			a.append(v);
		} else if(e instanceof AllInstanceExpression) {
			if(a.hasName(e)){
				a.trace(e, false).append(getVarName(e, a))
			} else {
				val componentType = if (e.type.feature === null) TypedNode
				else if(e.type.feature instanceof EReference) TypedEdge else ValueEdge;
				a.newLine.append('''«e.type.side.literal».all«componentType.simpleName»s("«e.type.type.name»"«IF e.type.feature!==null»,"«e.type.feature.name»"«ENDIF»)''');
			}
		} else
			super.internalToConvertedExpression(e, a)
	}
	
	def ModificationExpressionBlock block(XExpression e) {
		if(e===null) null
		else if(e instanceof ModificationExpressionBlock) e
		else (e.eContainer as XExpression).block
	}

//	def boolean isCondition(EObject e) {
//    	if(!(e.eContainer instanceof XExpression)) true
//    	else if(e.eContainmentFeature===XbasePackage.Literals.XIF_EXPRESSION__IF 
//    		|| e.eContainmentFeature===XbasePackage.Literals.XCASE_PART__CASE
//    	) true
//    	else if(e.eContainer instanceof ModificationExpressionBlock) false
//    	else isCondition(e.eContainer)
//    }
}