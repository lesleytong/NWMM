package statistic;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.internal.impl.ControlFlowImpl;
import org.eclipse.uml2.uml.internal.impl.DecisionNodeImpl;
import org.eclipse.uml2.uml.internal.impl.MergeNodeImpl;
import org.eclipse.uml2.uml.internal.impl.OpaqueActionImpl;
import org.eclipse.uml2.uml.resource.UMLResource;

public class StatisticForActivity {

	static int decisionNodeSize = 0;
	static int mergeNodeSize = 0;
	static int controlFlowSize = 0;
	static int opaqueActionSize = 0;

	public static void main(String[] args) {

		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION,
				UMLResource.Factory.INSTANCE);

		EPackage ep = UMLPackage.eINSTANCE;

		URI baseURI = URI.createFileURI("E:\\eclipse-dsl-workspace\\edu.ustb.lesley.test.uml\\src\\pretreat\\prediction_activity.uml");

		Resource baseResource = resourceSet.getResource(baseURI, true);

		baseResource.getContents().forEach(model -> {
			model.eAllContents().forEachRemaining(e -> {
				if(e instanceof DecisionNodeImpl) {
					decisionNodeSize++;
				} else if(e instanceof MergeNodeImpl) {
					mergeNodeSize++;
				} else if(e instanceof ControlFlowImpl) {
					controlFlowSize++;
				} else if(e instanceof OpaqueActionImpl) {
					opaqueActionSize++;
				} 
			});
		});
		
		System.out.println("controlFlowSize: " + controlFlowSize);
		System.out.println("opaqueActionSize: " + opaqueActionSize);
		System.out.println("decisionNodeSize: " + decisionNodeSize);
		System.out.println("mergeNodeSize: " + mergeNodeSize);

	}

}
