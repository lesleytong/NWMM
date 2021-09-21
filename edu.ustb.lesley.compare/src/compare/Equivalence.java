/**
 */
package compare;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Equivalence</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Diffs are considered equivalent if merging one is enough to resolve both differences. For example, if a reference has an eOpposite, we will detect one diff for each side of the bidirectional reference, yet merging one of these diffs will automatically update the model in such a way that the second diff is "merged".
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link compare.Equivalence#getDifferences <em>Differences</em>}</li>
 * </ul>
 *
 * @see compare.ComparePackage#getEquivalence()
 * @model
 * @generated
 */
public interface Equivalence extends EObject {
	/**
	 * Returns the value of the '<em><b>Differences</b></em>' reference list.
	 * The list contents are of type {@link compare.Diff}.
	 * It is bidirectional and its opposite is '{@link compare.Diff#getEquivalence <em>Equivalence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * References the <i>n</i> diffs composing this equivalence. There are <u>at least</u> two diffs in this list.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Differences</em>' reference list.
	 * @see compare.ComparePackage#getEquivalence_Differences()
	 * @see compare.Diff#getEquivalence
	 * @model opposite="equivalence" lower="2"
	 * @generated
	 */
	EList<Diff> getDifferences();

} // Equivalence
