package test03;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import edu.ustb.sei.mde.bxcore.util.EcoreModelUtil;
import edu.ustb.sei.mde.graph.type.PropertyEdge;
import edu.ustb.sei.mde.graph.type.TypeEdge;
import edu.ustb.sei.mde.graph.type.TypeGraph;
import nway.EvaluateEngine;
import sql.SqlPackage;

public class TestSql_3 {

	static ResourceSet resourceSet = new ResourceSetImpl();

	public static void main(String[] args) {

		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		EPackage ep = SqlPackage.eINSTANCE;

		URI baseURI = URI
				.createFileURI("E:\\eclipse-dsl-workspace\\edu.ustb.lesley.test.xmi.sql\\src\\test03\\sql.xmi");
		URI backupURI = URI.createFileURI(
				"E:\\eclipse-dsl-workspace\\edu.ustb.lesley.test.xmi.sql\\src\\test03\\sql_backup.xmi");
		URI m0URI = URI
				.createFileURI("E:\\eclipse-dsl-workspace\\edu.ustb.lesley.test.xmi.sql\\src\\test03\\sql_m0.xmi");
		URI m1URI = URI
				.createFileURI("E:\\eclipse-dsl-workspace\\edu.ustb.lesley.test.xmi.sql\\src\\test03\\sql_m1.xmi");
		URI m2URI = URI
				.createFileURI("E:\\eclipse-dsl-workspace\\edu.ustb.lesley.test.xmi.sql\\src\\test03\\sql_m2.xmi");

		Resource baseResource = resourceSet.getResource(baseURI, true);

		URI branch1URI = URI
				.createFileURI("E:\\eclipse-dsl-workspace\\edu.ustb.lesley.test.xmi.sql\\src\\test03\\sql1.xmi");
		URI branch2URI = URI
				.createFileURI("E:\\eclipse-dsl-workspace\\edu.ustb.lesley.test.xmi.sql\\src\\test03\\sql2.xmi");
		URI branch3URI = URI
				.createFileURI("E:\\eclipse-dsl-workspace\\edu.ustb.lesley.test.xmi.sql\\src\\test03\\sql3.xmi");

		List<URI> uriList = new ArrayList<>();
		uriList.add(baseURI);
		uriList.add(branch1URI);
		uriList.add(branch2URI);
		uriList.add(branch3URI);

		TypeGraph typeGraph = EcoreModelUtil.load(ep);

		// ????????????????????(TypeEdge)
		List<TypeEdge> typeEdgeList = new ArrayList<>();
		typeEdgeList.addAll(typeGraph.getAllTypeEdges());

//		EvaluateEngine.getM0(baseResource, m0URI, 3);					
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
