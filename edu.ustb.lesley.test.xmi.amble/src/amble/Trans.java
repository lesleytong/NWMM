/**
 */
package amble;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Trans</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link amble.Trans#getWaitFor <em>Wait For</em>}</li>
 * </ul>
 *
 * @see amble.AmblePackage#getTrans()
 * @model
 * @generated
 */
public interface Trans extends Transition {
	/**
	 * Returns the value of the '<em><b>Wait For</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Wait For</em>' reference.
	 * @see #setWaitFor(Message)
	 * @see amble.AmblePackage#getTrans_WaitFor()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	Message getWaitFor();

	/**
	 * Sets the value of the '{@link amble.Trans#getWaitFor <em>Wait For</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Wait For</em>' reference.
	 * @see #getWaitFor()
	 * @generated
	 */
	void setWaitFor(Message value);

} // Trans
