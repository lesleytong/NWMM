/**
 */
package make;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Shell Line</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link make.ShellLine#getCommand <em>Command</em>}</li>
 *   <li>{@link make.ShellLine#isDisplay <em>Display</em>}</li>
 *   <li>{@link make.ShellLine#getRuleShellLine <em>Rule Shell Line</em>}</li>
 * </ul>
 *
 * @see make.MakePackage#getShellLine()
 * @model
 * @generated
 */
public interface ShellLine extends EObject {
	/**
	 * Returns the value of the '<em><b>Command</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Command</em>' attribute.
	 * @see #setCommand(String)
	 * @see make.MakePackage#getShellLine_Command()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	String getCommand();

	/**
	 * Sets the value of the '{@link make.ShellLine#getCommand <em>Command</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Command</em>' attribute.
	 * @see #getCommand()
	 * @generated
	 */
	void setCommand(String value);

	/**
	 * Returns the value of the '<em><b>Display</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Display</em>' attribute.
	 * @see #setDisplay(boolean)
	 * @see make.MakePackage#getShellLine_Display()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	boolean isDisplay();

	/**
	 * Sets the value of the '{@link make.ShellLine#isDisplay <em>Display</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Display</em>' attribute.
	 * @see #isDisplay()
	 * @generated
	 */
	void setDisplay(boolean value);

	/**
	 * Returns the value of the '<em><b>Rule Shell Line</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link make.Rule#getShellLines <em>Shell Lines</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule Shell Line</em>' container reference.
	 * @see #setRuleShellLine(Rule)
	 * @see make.MakePackage#getShellLine_RuleShellLine()
	 * @see make.Rule#getShellLines
	 * @model opposite="shellLines" required="true" transient="false" ordered="false"
	 * @generated
	 */
	Rule getRuleShellLine();

	/**
	 * Sets the value of the '{@link make.ShellLine#getRuleShellLine <em>Rule Shell Line</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule Shell Line</em>' container reference.
	 * @see #getRuleShellLine()
	 * @generated
	 */
	void setRuleShellLine(Rule value);

} // ShellLine
