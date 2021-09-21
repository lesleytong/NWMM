package ame_activity;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;

import edu.ustb.sei.mde.bxcore.util.EcoreModelUtil;
import edu.ustb.sei.mde.graph.type.PropertyEdge;
import edu.ustb.sei.mde.graph.type.TypeEdge;
import edu.ustb.sei.mde.graph.type.TypeGraph;
import nway.EvaluateEngine;

public class TestAME_7 {
	static ResourceSet resourceSet = new ResourceSetImpl();

	public static void main(String[] args) {

		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION,
				UMLResource.Factory.INSTANCE);

		EPackage ep = UMLPackage.eINSTANCE;

		URI baseURI = URI.createFileURI("E:\\eclipse-dsl-workspace\\edu.ustb.lesley.test.uml\\src\\ame_activity\\ame_activity.uml");
		URI backupURI = URI.createFileURI("E:\\eclipse-dsl-workspace\\edu.ustb.lesley.test.uml\\src\\ame_activity\\ame_activity_backup.uml");
		URI m0URI = URI.createFileURI("E:\\eclipse-dsl-workspace\\edu.ustb.lesley.test.uml\\src\\ame_activity\\ame_activity_m0.uml");
		URI m1URI = URI.createFileURI("E:\\eclipse-dsl-workspace\\edu.ustb.lesley.test.uml\\src\\ame_activity\\ame_activity_m1.uml");
		URI m2URI = URI.createFileURI("E:\\eclipse-dsl-workspace\\edu.ustb.lesley.test.uml\\src\\ame_activity\\ame_activity_m2.uml");

		Resource baseResource = resourceSet.getResource(baseURI, true);

		URI branch1URI = URI.createFileURI("E:\\eclipse-dsl-workspace\\edu.ustb.lesley.test.uml\\src\\ame_activity\\ame_activity1.uml");
		URI branch2URI = URI.createFileURI("E:\\eclipse-dsl-workspace\\edu.ustb.lesley.test.uml\\src\\ame_activity\\ame_activity2.uml");
		URI branch3URI = URI.createFileURI("E:\\eclipse-dsl-workspace\\edu.ustb.lesley.test.uml\\src\\ame_activity\\ame_activity3.uml");
		URI branch4URI = URI.createFileURI("E:\\eclipse-dsl-workspace\\edu.ustb.lesley.test.uml\\src\\ame_activity\\ame_activity4.uml");
		URI branch5URI = URI.createFileURI("E:\\eclipse-dsl-workspace\\edu.ustb.lesley.test.uml\\src\\ame_activity\\ame_activity5.uml");
		URI branch6URI = URI.createFileURI("E:\\eclipse-dsl-workspace\\edu.ustb.lesley.test.uml\\src\\ame_activity\\ame_activity6.uml");
		URI branch7URI = URI.createFileURI("E:\\eclipse-dsl-workspace\\edu.ustb.lesley.test.uml\\src\\ame_activity\\ame_activity7.uml");

		List<URI> uriList = new ArrayList<>();
		uriList.add(baseURI);
		uriList.add(branch1URI);
		uriList.add(branch2URI);
		uriList.add(branch3URI);
		uriList.add(branch4URI);
		uriList.add(branch5URI);
		uriList.add(branch6URI);
		uriList.add(branch7URI);

		TypeGraph typeGraph = EcoreModelUtil.load(ep);
				
		// 指定需要排序的边类型(TypeEdge)
		List<TypeEdge> typeEdgeList = new ArrayList<>();
		TypeEdge typeEdge1 = typeGraph.getTypeEdge(typeGraph.getTypeNode("Activity"), "edge");
		typeEdgeList.add(typeEdge1);
	
//		EvaluateEngine.getM0(baseResource, m0URI, 6);					
//		getBranches(baseResource, backupURI, m0URI, uriList);		

//		testMerge(uriList, typeGraph, typeEdgeList, null, m1URI, ep);
//		testEquality(m0URI, m1URI);

//		testEMFCompare(uriList, m2URI);
		testEquality(m0URI, m2URI);

	}
	
	public static void testEquality(URI m0URI, URI m1URI) {
		Resource m0Resource = resourceSet.getResource(m0URI, true);
		Resource m1Resource = resourceSet.getResource(m1URI, true);
		EvaluateEngine.testEquality(m0Resource, m1Resource);
	}

	public static void testEMFCompare(List<URI> uriList, URI m2URI) {
		List<Resource> resourceList = new ArrayList<>();
		for (int i = 0; i < uriList.size(); i++) {
			Resource resource = resourceSet.getResource(uriList.get(i), true);
			resourceList.add(resource);
		}
		EvaluateEngine.testEMFCompare(resourceList, m2URI);
	}

	public static void testMerge(List<URI> uriList, TypeGraph typeGraph,
			List<TypeEdge> typeEdgeList, List<PropertyEdge> propertyEdgeList, URI m1URI, EPackage ep) {

		List<Resource> resourceList = new ArrayList<>();
		for (int i = 0; i < uriList.size(); i++) {
			Resource resource = resourceSet.getResource(uriList.get(i), true);
			resourceList.add(resource);
		}
		
		EvaluateEngine.testMerge(typeGraph, resourceList, typeEdgeList, propertyEdgeList, m1URI, ep);

	}

	public static void getBranches(Resource baseResource, URI backupURI, URI m0URI, List<URI> uriList) {

		Resource backupResource = resourceSet.getResource(backupURI, true);
		Resource m0Resource = resourceSet.getResource(m0URI, true);

		EvaluateEngine.getBranches(baseResource, m0Resource, backupResource, uriList);

	}

}
