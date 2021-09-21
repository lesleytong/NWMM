/**
 */
package fsm;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Transition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fsm.Transition#getStateMachine <em>State Machine</em>}</li>
 *   <li>{@link fsm.Transition#getAssociationStateState <em>Association State State</em>}</li>
 * </ul>
 *
 * @see fsm.FsmPackage#getTransition()
 * @model
 * @generated
 */
public interface Transition extends MgaObject {
	/**
	 * Returns the value of the '<em><b>State Machine</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link fsm.StateMachine#getTransition <em>Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State Machine</em>' container reference.
	 * @see #setStateMachine(StateMachine)
	 * @see fsm.FsmPackage#getTransition_StateMachine()
	 * @see fsm.StateMachine#getTransition
	 * @model opposite="transition" required="true" transient="false" ordered="false"
	 * @generated
	 */
	StateMachine getStateMachine();

	/**
	 * Sets the value of the '{@link fsm.Transition#getStateMachine <em>State Machine</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>State Machine</em>' container reference.
	 * @see #getStateMachine()
	 * @generated
	 */
	void setStateMachine(StateMachine value);

	/**
	 * Returns the value of the '<em><b>Association State State</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link fsm.AssociationStateState#getTransition <em>Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Association State State</em>' reference.
	 * @see #setAssociationStateState(AssociationStateState)
	 * @see fsm.FsmPackage#getTransition_AssociationStateState()
	 * @see fsm.AssociationStateState#getTransition
	 * @model opposite="transition" required="true" ordered="false"
	 * @generated
	 */
	AssociationStateState getAssociationStateState();

	/**
	 * Sets the value of the '{@link fsm.Transition#getAssociationStateState <em>Association State State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Association State State</em>' reference.
	 * @see #getAssociationStateState()
	 * @generated
	 */
	void setAssociationStateState(AssociationStateState value);

} // Transition
