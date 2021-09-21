/**
 */
package compare;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Reference Change</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * This particular kind of difference will be used to describe changes within a reference, whether the feature is multi-valued or not. In the case of a MOVE difference, this could actually mean two things : either the value has been moved from one index to another (moving the value at another position of the same reference), or the value has been moved from one container to another. This second case will only happen with containment references.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link compare.ReferenceChange#getReference <em>Reference</em>}</li>
 *   <li>{@link compare.ReferenceChange#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see compare.ComparePackage#getReferenceChange()
 * @model
 * @generated
 */
public interface ReferenceChange extends Diff {
	/**
	 * Returns the value of the '<em><b>Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The reference in which "something" changed. If this difference is a MOVE from one reference to another, this will point towards the left reference.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Reference</em>' reference.
	 * @see #setReference(EReference)
	 * @see compare.ComparePackage#getReferenceChange_Reference()
	 * @model required="true"
	 * @generated
	 */
	EReference getReference();

	/**
	 * Sets the value of the '{@link compare.ReferenceChange#getReference <em>Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reference</em>' reference.
	 * @see #getReference()
	 * @generated
	 */
	void setReference(EReference value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * References the actual value on which a difference has been detected.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Value</em>' reference.
	 * @see #setValue(EObject)
	 * @see compare.ComparePackage#getReferenceChange_Value()
	 * @model
	 * @generated
	 */
	EObject getValue();

	/**
	 * Sets the value of the '{@link compare.ReferenceChange#getValue <em>Value</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' reference.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(EObject value);

} // ReferenceChange
