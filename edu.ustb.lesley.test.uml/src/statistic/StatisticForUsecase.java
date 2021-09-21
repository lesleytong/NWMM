package statistic;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.internal.impl.ActorImpl;
import org.eclipse.uml2.uml.internal.impl.AssociationImpl;
import org.eclipse.uml2.uml.internal.impl.ExtendImpl;
import org.eclipse.uml2.uml.internal.impl.ExtensionPointImpl;
import org.eclipse.uml2.uml.internal.impl.GeneralizationImpl;
import org.eclipse.uml2.uml.internal.impl.IncludeImpl;
import org.eclipse.uml2.uml.internal.impl.UseCaseImpl;
import org.eclipse.uml2.uml.resource.UMLResource;

public class StatisticForUsecase {

	static int actorSize = 0;
	static int usecaseSize = 0;
	static int associationSize = 0;
	static int operationSize = 0;
	static int extendSize = 0;
	static int includeSize = 0;
	static int extensionSize = 0;
	static int generalizationSize = 0;

	public static void main(String[] args) {

		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION,
				UMLResource.Factory.INSTANCE);

		EPackage ep = UMLPackage.eINSTANCE;

		URI baseURI = URI.createFileURI("E:\\eclipse-dsl-workspace\\edu.ustb.lesley.test.uml\\src\\pretreat\\busticket_usecase.uml");

		Resource baseResource = resourceSet.getResource(baseURI, true);

		baseResource.getContents().forEach(model -> {
			model.eAllContents().forEachRemaining(e -> {
				if(e instanceof ActorImpl) {
					actorSize++;
				} else if(e instanceof UseCaseImpl) {
					usecaseSize++;
				} else if(e instanceof AssociationImpl) {
					associationSize++;
				} else if(e instanceof ExtendImpl) {
					extendSize++;
				} else if(e instanceof IncludeImpl) {
					includeSize++;
				} else if(e instanceof ExtensionPointImpl) {
					extensionSize++;
				} else if (e instanceof GeneralizationImpl) {
					generalizationSize++;
				}
			});
		});
		
		System.out.println("actorSize: " + actorSize);
		System.out.println("usecaseSize: " + usecaseSize);
		System.out.println("associationSize: " + associationSize);
		System.out.println("extendSize: " + extendSize);
		System.out.println("includeSize: " + includeSize);
		System.out.println("extensionSize: " + extensionSize);
		System.out.println("generalizationSize: " + generalizationSize);

	}

}
