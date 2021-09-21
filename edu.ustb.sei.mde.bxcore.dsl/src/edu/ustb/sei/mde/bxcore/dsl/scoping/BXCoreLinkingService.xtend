package edu.ustb.sei.mde.bxcore.dsl.scoping

import org.eclipse.xtext.linking.impl.DefaultLinkingService
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.nodemodel.INode
import org.eclipse.xtext.linking.impl.IllegalNodeException
import edu.ustb.sei.mde.bxcore.dsl.bXCore.BXCorePackage
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.resource.IEObjectDescription
import java.util.Collections
import org.eclipse.emf.ecore.EPackage
import org.eclipse.xtext.EcoreUtil2

class BXCoreLinkingService extends DefaultLinkingService {
	
	override getLinkedObjects(EObject context, EReference ref, INode node) throws IllegalNodeException {
		if(ref===BXCorePackage.Literals.IMPORT_SECTION__METAMODEL) {
			try {
				val String uri = node.crossRefNodeAsString;
				val IScope scope = getScope(context, ref);
				for (IEObjectDescription d : scope.allElements) {
					if (d.qualifiedName.firstSegment.equals(uri)) {
						return Collections.singletonList(d.EObjectOrProxy);
					}
				}
				val EPackage p = EcoreUtil2.loadEPackage(uri, this.class.classLoader);
				if(p !== null) {
					return Collections.singletonList(p);
				}

			} catch (Exception e) {
				return Collections.emptyList();
			}
		}
		else return super.getLinkedObjects(context, ref, node)
	}
	
}