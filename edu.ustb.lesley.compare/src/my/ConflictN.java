/**
 */
package my;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.compare.ConflictKind;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Conflict N</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link my.ConflictN#getDifferences <em>Differences</em>}</li>
 *   <li>{@link my.ConflictN#getKind <em>Kind</em>}</li>
 * </ul>
 *
 * @see my.MyPackage#getConflictN()
 * @model
 * @generated
 */
public interface ConflictN extends EObject {
	/**
	 * Returns the value of the '<em><b>Differences</b></em>' reference list.
	 * The list contents are of type {@link my.DiffN}.
	 * It is bidirectional and its opposite is '{@link my.DiffN#getConflict <em>Conflict</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Differences</em>' reference list.
	 * @see my.MyPackage#getConflictN_Differences()
	 * @see my.DiffN#getConflict
	 * @model opposite="conflict" lower="2"
	 * @generated
	 */
	EList<DiffN> getDifferences();

	/**
	 * Returns the value of the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Kind</em>' attribute.
	 * @see #setKind(ConflictKind)
	 * @see my.MyPackage#getConflictN_Kind()
	 * @model dataType="my.ConflictKind"
	 * @generated
	 */
	ConflictKind getKind();

	/**
	 * Sets the value of the '{@link my.ConflictN#getKind <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Kind</em>' attribute.
	 * @see #getKind()
	 * @generated
	 */
	void setKind(ConflictKind value);

} // ConflictN
