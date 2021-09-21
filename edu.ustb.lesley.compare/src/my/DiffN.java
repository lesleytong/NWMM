/**
 */
package my;

import org.eclipse.emf.compare.DifferenceKind;
import org.eclipse.emf.compare.DifferenceState;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Diff N</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link my.DiffN#getConflict <em>Conflict</em>}</li>
 *   <li>{@link my.DiffN#getMatch <em>Match</em>}</li>
 *   <li>{@link my.DiffN#getKind <em>Kind</em>}</li>
 *   <li>{@link my.DiffN#getState <em>State</em>}</li>
 * </ul>
 *
 * @see my.MyPackage#getDiffN()
 * @model
 * @generated
 */
public interface DiffN extends EObject {
	/**
	 * Returns the value of the '<em><b>Match</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link my.MatchN#getDifferences <em>Differences</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Match</em>' container reference.
	 * @see #setMatch(MatchN)
	 * @see my.MyPackage#getDiffN_Match()
	 * @see my.MatchN#getDifferences
	 * @model opposite="differences" required="true" transient="false"
	 * @generated
	 */
	MatchN getMatch();

	/**
	 * Sets the value of the '{@link my.DiffN#getMatch <em>Match</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Match</em>' container reference.
	 * @see #getMatch()
	 * @generated
	 */
	void setMatch(MatchN value);

	/**
	 * Returns the value of the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Kind</em>' attribute.
	 * @see #setKind(DifferenceKind)
	 * @see my.MyPackage#getDiffN_Kind()
	 * @model dataType="my.DifferenceKind"
	 * @generated
	 */
	DifferenceKind getKind();

	/**
	 * Sets the value of the '{@link my.DiffN#getKind <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Kind</em>' attribute.
	 * @see #getKind()
	 * @generated
	 */
	void setKind(DifferenceKind value);

	/**
	 * Returns the value of the '<em><b>State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State</em>' attribute.
	 * @see #setState(DifferenceState)
	 * @see my.MyPackage#getDiffN_State()
	 * @model dataType="my.DifferenceState"
	 * @generated
	 */
	DifferenceState getState();

	/**
	 * Sets the value of the '{@link my.DiffN#getState <em>State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>State</em>' attribute.
	 * @see #getState()
	 * @generated
	 */
	void setState(DifferenceState value);

	/**
	 * Returns the value of the '<em><b>Conflict</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link my.ConflictN#getDifferences <em>Differences</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Conflict</em>' reference.
	 * @see #setConflict(ConflictN)
	 * @see my.MyPackage#getDiffN_Conflict()
	 * @see my.ConflictN#getDifferences
	 * @model opposite="differences"
	 * @generated
	 */
	ConflictN getConflict();

	/**
	 * Sets the value of the '{@link my.DiffN#getConflict <em>Conflict</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Conflict</em>' reference.
	 * @see #getConflict()
	 * @generated
	 */
	void setConflict(ConflictN value);

} // DiffN
