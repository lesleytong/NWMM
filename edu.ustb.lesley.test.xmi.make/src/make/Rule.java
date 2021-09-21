/**
 */
package make;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link make.Rule#getDependencies <em>Dependencies</em>}</li>
 *   <li>{@link make.Rule#getShellLines <em>Shell Lines</em>}</li>
 * </ul>
 *
 * @see make.MakePackage#getRule()
 * @model
 * @generated
 */
public interface Rule extends Element {
	/**
	 * Returns the value of the '<em><b>Dependencies</b></em>' containment reference list.
	 * The list contents are of type {@link make.Dependency}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dependencies</em>' containment reference list.
	 * @see make.MakePackage#getRule_Dependencies()
	 * @model containment="true"
	 * @generated
	 */
	EList<Dependency> getDependencies();

	/**
	 * Returns the value of the '<em><b>Shell Lines</b></em>' containment reference list.
	 * The list contents are of type {@link make.ShellLine}.
	 * It is bidirectional and its opposite is '{@link make.ShellLine#getRuleShellLine <em>Rule Shell Line</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Shell Lines</em>' containment reference list.
	 * @see make.MakePackage#getRule_ShellLines()
	 * @see make.ShellLine#getRuleShellLine
	 * @model opposite="ruleShellLine" containment="true" required="true"
	 * @generated
	 */
	EList<ShellLine> getShellLines();

} // Rule
