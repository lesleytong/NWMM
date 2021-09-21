/**
 */
package fsm;

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
 *   <li>{@link fsm.State#getStateMachine <em>State Machine</em>}</li>
 *   <li>{@link fsm.State#getAssociationStateStatedst <em>Association State Statedst</em>}</li>
 *   <li>{@link fsm.State#getAssociationStateStatesrc <em>Association State Statesrc</em>}</li>
 * </ul>
 *
 * @see fsm.FsmPackage#getState()
 * @model
 * @generated
 */
public interface State extends MgaObject {
	/**
	 * Returns the value of the '<em><b>State Machine</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link fsm.StateMachine#getState <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State Machine</em>' container reference.
	 * @see #setStateMachine(StateMachine)
	 * @see fsm.FsmPackage#getState_StateMachine()
	 * @see fsm.StateMachine#getState
	 * @model opposite="state" required="true" transient="false" ordered="false"
	 * @generated
	 */
	StateMachine getStateMachine();

	/**
	 * Sets the value of the '{@link fsm.State#getStateMachine <em>State Machine</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>State Machine</em>' container reference.
	 * @see #getStateMachine()
	 * @generated
	 */
	void setStateMachine(StateMachine value);

	/**
	 * Returns the value of the '<em><b>Association State Statedst</b></em>' reference list.
	 * The list contents are of type {@link fsm.AssociationStateState}.
	 * It is bidirectional and its opposite is '{@link fsm.AssociationStateState#getDstTransition <em>Dst Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Association State Statedst</em>' reference list.
	 * @see fsm.FsmPackage#getState_AssociationStateStatedst()
	 * @see fsm.AssociationStateState#getDstTransition
	 * @model opposite="dstTransition" required="true" ordered="false"
	 * @generated
	 */
	EList<AssociationStateState> getAssociationStateStatedst();

	/**
	 * Returns the value of the '<em><b>Association State Statesrc</b></em>' reference list.
	 * The list contents are of type {@link fsm.AssociationStateState}.
	 * It is bidirectional and its opposite is '{@link fsm.AssociationStateState#getSrcTransition <em>Src Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Association State Statesrc</em>' reference list.
	 * @see fsm.FsmPackage#getState_AssociationStateStatesrc()
	 * @see fsm.AssociationStateState#getSrcTransition
	 * @model opposite="srcTransition" required="true" ordered="false"
	 * @generated
	 */
	EList<AssociationStateState> getAssociationStateStatesrc();

} // State
