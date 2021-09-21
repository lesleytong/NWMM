package statistic;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.MessageSort;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.internal.impl.ExecutionSpecificationImpl;
import org.eclipse.uml2.uml.internal.impl.LifelineImpl;
import org.eclipse.uml2.uml.internal.impl.MessageImpl;
import org.eclipse.uml2.uml.resource.UMLResource;

public class StatisticForSequence {

	static int lifelineSize = 0;
	static int callSize = 0;
	static int replySize = 0;
	static int executionSize = 0;

	public static void main(String[] args) {

		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION,
				UMLResource.Factory.INSTANCE);

		EPackage ep = UMLPackage.eINSTANCE;

		URI baseURI = URI.createFileURI("E:\\eclipse-dsl-workspace\\edu.ustb.lesley.test.uml\\src\\pretreat\\survey_sequence.uml");

		Resource baseResource = resourceSet.getResource(baseURI, true);
		
		baseResource.getContents().forEach(model -> {
			model.eAllContents().forEachRemaining(e -> {
				System.out.println(e);
			});
		});

		baseResource.getContents().forEach(model -> {
			model.eAllContents().forEachRemaining(e -> {
				if(e instanceof LifelineImpl) {
					lifelineSize++;
				} else if(e instanceof MessageImpl) {
					MessageImpl m = (MessageImpl) e;
					MessageSort messageSort = m.getMessageSort();
					if(messageSort==MessageSort.SYNCH_CALL_LITERAL || messageSort==MessageSort.ASYNCH_CALL_LITERAL) {
						callSize++;
					} else if(messageSort == MessageSort.REPLY_LITERAL) {
						replySize++;
					}
				} else if(e instanceof ExecutionSpecificationImpl) {
					executionSize++;
				}
			});
		});
		
		System.out.println("lifelineSize: " + lifelineSize);
		System.out.println("callSize: " + callSize);
		System.out.println("replySize: " + replySize);
		System.out.println("executionSize: " + executionSize);

	}

}
