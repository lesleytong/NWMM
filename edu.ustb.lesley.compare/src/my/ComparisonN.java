/**
 */
package my;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Comparison N</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link my.ComparisonN#getMatches <em>Matches</em>}</li>
 *   <li>{@link my.ComparisonN#getConflicts <em>Conflicts</em>}</li>
 * </ul>
 *
 * @see my.MyPackage#getComparisonN()
 * @model
 * @generated
 */
public interface ComparisonN extends EObject {
	/**
	 * Returns the value of the '<em><b>Matches</b></em>' containment reference list.
	 * The list contents are of type {@link my.MatchN}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Matches</em>' containment reference list.
	 * @see my.MyPackage#getComparisonN_Matches()
	 * @model containment="true"
	 * @generated
	 */
	EList<MatchN> getMatches();

	/**
	 * Returns the value of the '<em><b>Conflicts</b></em>' containment reference list.
	 * The list contents are of type {@link my.ConflictN}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Conflicts</em>' containment reference list.
	 * @see my.MyPackage#getComparisonN_Conflicts()
	 * @model containment="true"
	 * @generated
	 */
	EList<ConflictN> getConflicts();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	EList<DiffN> getDifferences();

} // ComparisonN
