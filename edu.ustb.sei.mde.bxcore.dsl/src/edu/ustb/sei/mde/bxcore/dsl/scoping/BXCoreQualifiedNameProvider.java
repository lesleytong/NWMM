package edu.ustb.sei.mde.bxcore.dsl.scoping;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.xbase.scoping.XbaseQualifiedNameProvider;

import edu.ustb.sei.mde.bxcore.dsl.bXCore.DashedPathType;

@SuppressWarnings("restriction")
public class BXCoreQualifiedNameProvider extends XbaseQualifiedNameProvider {

	public BXCoreQualifiedNameProvider() {
		super();
	}
	
	@Override
	public QualifiedName getFullyQualifiedName(EObject obj) {
		if(obj instanceof DashedPathType) { // we must filter out no-name elements
			return QualifiedName.EMPTY;
		} else
			return super.getFullyQualifiedName(obj);
	}

}
