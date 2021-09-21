/**
 */
package my.util;

import my.*;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see my.MyPackage
 * @generated
 */
public class MySwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static MyPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MySwitch() {
		if (modelPackage == null) {
			modelPackage = MyPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case MyPackage.COMPARISON_N: {
				ComparisonN comparisonN = (ComparisonN)theEObject;
				T result = caseComparisonN(comparisonN);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MyPackage.MATCH_N: {
				MatchN matchN = (MatchN)theEObject;
				T result = caseMatchN(matchN);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MyPackage.DIFF_N: {
				DiffN diffN = (DiffN)theEObject;
				T result = caseDiffN(diffN);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MyPackage.CONFLICT_N: {
				ConflictN conflictN = (ConflictN)theEObject;
				T result = caseConflictN(conflictN);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MyPackage.RESOURCE_ATTACHMENT_CHANGE: {
				ResourceAttachmentChange resourceAttachmentChange = (ResourceAttachmentChange)theEObject;
				T result = caseResourceAttachmentChange(resourceAttachmentChange);
				if (result == null) result = caseDiffN(resourceAttachmentChange);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MyPackage.RESOURCE_LOCATION_CHANGE: {
				ResourceLocationChange resourceLocationChange = (ResourceLocationChange)theEObject;
				T result = caseResourceLocationChange(resourceLocationChange);
				if (result == null) result = caseDiffN(resourceLocationChange);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MyPackage.REFERENCE_CHANGE: {
				ReferenceChange referenceChange = (ReferenceChange)theEObject;
				T result = caseReferenceChange(referenceChange);
				if (result == null) result = caseDiffN(referenceChange);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MyPackage.ATTRIBUTE_CHANGE: {
				AttributeChange attributeChange = (AttributeChange)theEObject;
				T result = caseAttributeChange(attributeChange);
				if (result == null) result = caseDiffN(attributeChange);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MyPackage.FEATURE_MAP_CHANGE: {
				FeatureMapChange featureMapChange = (FeatureMapChange)theEObject;
				T result = caseFeatureMapChange(featureMapChange);
				if (result == null) result = caseDiffN(featureMapChange);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Comparison N</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Comparison N</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseComparisonN(ComparisonN object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Match N</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Match N</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMatchN(MatchN object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Diff N</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Diff N</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDiffN(DiffN object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Conflict N</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Conflict N</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConflictN(ConflictN object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Resource Attachment Change</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Resource Attachment Change</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseResourceAttachmentChange(ResourceAttachmentChange object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Resource Location Change</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Resource Location Change</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseResourceLocationChange(ResourceLocationChange object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Reference Change</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Reference Change</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReferenceChange(ReferenceChange object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attribute Change</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attribute Change</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttributeChange(AttributeChange object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Feature Map Change</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Feature Map Change</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFeatureMapChange(FeatureMapChange object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //MySwitch
