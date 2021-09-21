/**
 */
package amble;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link amble.State#isIsInitial <em>Is Initial</em>}</li>
 *   <li>{@link amble.State#getIncoming <em>Incoming</em>}</li>
 *   <li>{@link amble.State#getOutgoing <em>Outgoing</em>}</li>
 * </ul>
 *
 * @see amble.AmblePackage#getState()
 * @model
 * @generated
 */
public interface State extends Element {
	/**
	 * Returns the value of the '<em><b>Is Initial</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Initial</em>' attribute.
	 * @see #setIsInitial(boolean)
	 * @see amble.AmblePackage#getState_IsInitial()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	boolean isIsInitial();

	/**
	 * Sets the value of the '{@link amble.State#isIsInitial <em>Is Initial</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Initial</em>' attribute.
	 * @see #isIsInitial()
	 * @generated
	 */
	void setIsInitial(boolean value);

	/**
	 * Returns the value of the '<em><b>Incoming</b></em>' reference list.
	 * The list contents are of type {@link amble.Transition}.
	 * It is bidirectional and its opposite is '{@link amble.Transition#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Incoming</em>' reference list.
	 * @see amble.AmblePackage#getState_Incoming()
	 * @see amble.Transition#getTarget
	 * @model opposite="target" ordered="false"
	 * @generated
	 */
	EList<Transition> getIncoming();

	/**
	 * Returns the value of the '<em><b>Outgoing</b></em>' reference list.
	 * The list contents are of type {@link amble.Transition}.
	 * It is bidirectional and its opposite is '{@link amble.Transition#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outgoing</em>' reference list.
	 * @see amble.AmblePackage#getState_Outgoing()
	 * @see amble.Transition#getSource
	 * @model opposite="source" ordered="false"
	 * @generated
	 */
	EList<Transition> getOutgoing();

} // State
