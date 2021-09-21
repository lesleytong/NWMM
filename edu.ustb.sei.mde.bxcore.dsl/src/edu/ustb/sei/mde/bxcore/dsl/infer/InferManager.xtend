package edu.ustb.sei.mde.bxcore.dsl.infer

import edu.ustb.sei.mde.bxcore.dsl.bXCore.BXProgram
import edu.ustb.sei.mde.bxcore.dsl.jvmmodel.ModelInferrerUtils
import java.util.HashMap
import java.util.Map
import org.eclipse.emf.ecore.resource.Resource
import edu.ustb.sei.mde.bxcore.XmuCoreUtils
import java.util.logging.Level
import edu.ustb.sei.mde.bxcore.dsl.bXCore.PatternTypeLiteral
import edu.ustb.sei.mde.bxcore.dsl.bXCore.PatternPathEdge

class InferManager {
	static public Map<Resource, InferData> inferredDataMap = new HashMap;
	
	static def InferData getInferredTypeModel(Resource resource) throws Exception {
		val data = inferredDataMap.get(resource);
		if(data===null) {
			return createInfer(resource);
		} else {
			if(resource.modified) { // incremental
				resource.modified=false;
				return createInfer(resource);
			} else return data;
		}
	}
	
	static def safeGetInferredTypeModel(Resource resource) throws Exception {
		inferredDataMap.get(resource);
	}
	
	protected def static createInfer(Resource resource) throws Exception {
		if(resource.trackingModification===false) 
			resource.trackingModification=true;
		inferredDataMap.remove(resource);
		val program = resource.contents.get(0) as BXProgram;
		val literalMap = ModelInferrerUtils.groupTypeLiterals(program);
		val infer = TypeModel.buildTypeInfers(program, literalMap);
		infer.first.solveNames;
		infer.first.solveTypes;
		infer.second.solveNames;
		infer.second.solveTypes;
		
		val data = new InferData();
		data.setLiteralMap(literalMap);
		data.setSourceInfer(infer.first);
		data.setViewInfer(infer.second);
		
		val patternLiterals = program.eAllContents.filter[it instanceof PatternTypeLiteral].map [it as PatternTypeLiteral].indexed.toList;
		data.patternLiterals = patternLiterals;
		
		val pathTypes = program.eAllContents.filter[it instanceof PatternPathEdge].map[(it as PatternPathEdge).path].indexed.toList;
		data.pathTypes = pathTypes;
		
		inferredDataMap.put(resource,data);
		XmuCoreUtils.log(Level.INFO, "infer created",null);
		return data;
	}
}