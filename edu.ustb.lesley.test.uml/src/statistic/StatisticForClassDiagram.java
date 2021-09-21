package statistic;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.internal.impl.AssociationImpl;
import org.eclipse.uml2.uml.internal.impl.ClassImpl;
import org.eclipse.uml2.uml.internal.impl.OperationImpl;
import org.eclipse.uml2.uml.internal.impl.PropertyImpl;
import org.eclipse.uml2.uml.resource.UMLResource;

public class StatisticForClassDiagram {

	static int classSize = 0;
	static int attributeSize = 0;
	static int associationSize = 0;
	static int operationSize = 0;

	public static void main(String[] args) {

		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION,
				UMLResource.Factory.INSTANCE);

		EPackage ep = UMLPackage.eINSTANCE;

		URI baseURI = URI.createFileURI("E:\\eclipse-dsl-workspace\\edu.ustb.lesley.test.uml\\src\\pretreat\\futur.uml");

		Resource baseResource = resourceSet.getResource(baseURI, true);

		baseResource.getContents().forEach(model -> {
			model.eAllContents().forEachRemaining(e -> {
				if (e instanceof ClassImpl) {
					classSize++;
				} 
				else if (e instanceof PropertyImpl) {
					System.out.println("Attribute: " + e);
					attributeSize++;
				}
				else if(e instanceof AssociationImpl) {
					associationSize++;
				}
				else if(e instanceof OperationImpl) {
					operationSize++;
				}
			});
		});
		
		System.out.println("classSize: " + classSize);
		System.out.println("attributeSize: " + attributeSize);
		System.out.println("associationSize: " + associationSize);
		System.out.println("operationSize: " + operationSize);

	}

}
