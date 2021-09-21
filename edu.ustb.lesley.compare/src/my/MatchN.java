/**
 */
package my;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Match N</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link my.MatchN#getDifferences <em>Differences</em>}</li>
 *   <li>{@link my.MatchN#getBase <em>Base</em>}</li>
 *   <li>{@link my.MatchN#getBranches <em>Branches</em>}</li>
 * </ul>
 *
 * @see my.MyPackage#getMatchN()
 * @model
 * @generated
 */
public interface MatchN extends EObject {
	/**
	 * Returns the value of the '<em><b>Differences</b></em>' containment reference list.
	 * The list contents are of type {@link my.DiffN}.
	 * It is bidirectional and its opposite is '{@link my.DiffN#getMatch <em>Match</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Differences</em>' containment reference list.
	 * @see my.MyPackage#getMatchN_Differences()
	 * @see my.DiffN#getMatch
	 * @model opposite="match" containment="true"
	 * @generated
	 */
	EList<DiffN> getDifferences();

	/**
	 * Returns the value of the '<em><b>Base</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Base</em>' reference.
	 * @see #setBase(EObject)
	 * @see my.MyPackage#getMatchN_Base()
	 * @model
	 * @generated
	 */
	EObject getBase();

	/**
	 * Sets the value of the '{@link my.MatchN#getBase <em>Base</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base</em>' reference.
	 * @see #getBase()
	 * @generated
	 */
	void setBase(EObject value);

	/**
	 * Returns the value of the '<em><b>Branches</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EObject}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Branches</em>' reference list.
	 * @see my.MyPackage#getMatchN_Branches()
	 * @model
	 * @generated
	 */
	EList<EObject> getBranches();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	ComparisonN getComparisonN();

} // MatchN
