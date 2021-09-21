/**
 */
package compare;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Conflict</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Instances of this class describe conflicts between one or more differences in the left model as compared to differences in the right model. Conflicts can only happen in the case of three-way comparisons, when we have a common ancestor of both sides.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link compare.Conflict#getKind <em>Kind</em>}</li>
 *   <li>{@link compare.Conflict#getDifferences <em>Differences</em>}</li>
 * </ul>
 *
 * @see compare.ComparePackage#getConflict()
 * @model
 * @generated
 */
public interface Conflict extends EObject {
	/**
	 * Returns the value of the '<em><b>Kind</b></em>' attribute.
	 * The literals are from the enumeration {@link compare.ConflictKind}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Describes the kind of this conflict, whether it is a real conflict or a pseudo-conflict.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Kind</em>' attribute.
	 * @see compare.ConflictKind
	 * @see #setKind(ConflictKind)
	 * @see compare.ComparePackage#getConflict_Kind()
	 * @model required="true"
	 * @generated
	 */
	ConflictKind getKind();

	/**
	 * Sets the value of the '{@link compare.Conflict#getKind <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Kind</em>' attribute.
	 * @see compare.ConflictKind
	 * @see #getKind()
	 * @generated
	 */
	void setKind(ConflictKind value);

	/**
	 * Returns the value of the '<em><b>Differences</b></em>' reference list.
	 * The list contents are of type {@link compare.Diff}.
	 * It is bidirectional and its opposite is '{@link compare.Diff#getConflict <em>Conflict</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This will reference all differences that are related to this conflict. The number of elements contained in this list is <u>at least</u> two.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Differences</em>' reference list.
	 * @see compare.ComparePackage#getConflict_Differences()
	 * @see compare.Diff#getConflict
	 * @model opposite="conflict" lower="2"
	 * @generated
	 */
	EList<Diff> getDifferences();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This can be used to retrieve the list of differences that were made in the left element.
	 * <!-- end-model-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	EList<Diff> getLeftDifferences();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This can be used to retrieve the list of differences that were made in the right element.
	 * <!-- end-model-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	EList<Diff> getRightDifferences();

} // Conflict
