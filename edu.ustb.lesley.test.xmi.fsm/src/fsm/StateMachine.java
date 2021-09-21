/**
 */
package fsm;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>State Machine</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fsm.StateMachine#getRootFolder <em>Root Folder</em>}</li>
 *   <li>{@link fsm.StateMachine#getState <em>State</em>}</li>
 *   <li>{@link fsm.StateMachine#getTransition <em>Transition</em>}</li>
 * </ul>
 *
 * @see fsm.FsmPackage#getStateMachine()
 * @model
 * @generated
 */
public interface StateMachine extends MgaObject {
	/**
	 * Returns the value of the '<em><b>Root Folder</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link fsm.RootFolder#getStateMachine <em>State Machine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Root Folder</em>' container reference.
	 * @see #setRootFolder(RootFolder)
	 * @see fsm.FsmPackage#getStateMachine_RootFolder()
	 * @see fsm.RootFolder#getStateMachine
	 * @model opposite="stateMachine" required="true" transient="false" ordered="false"
	 * @generated
	 */
	RootFolder getRootFolder();

	/**
	 * Sets the value of the '{@link fsm.StateMachine#getRootFolder <em>Root Folder</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Root Folder</em>' container reference.
	 * @see #getRootFolder()
	 * @generated
	 */
	void setRootFolder(RootFolder value);

	/**
	 * Returns the value of the '<em><b>State</b></em>' containment reference list.
	 * The list contents are of type {@link fsm.State}.
	 * It is bidirectional and its opposite is '{@link fsm.State#getStateMachine <em>State Machine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State</em>' containment reference list.
	 * @see fsm.FsmPackage#getStateMachine_State()
	 * @see fsm.State#getStateMachine
	 * @model opposite="stateMachine" containment="true" ordered="false"
	 * @generated
	 */
	EList<State> getState();

	/**
	 * Returns the value of the '<em><b>Transition</b></em>' containment reference list.
	 * The list contents are of type {@link fsm.Transition}.
	 * It is bidirectional and its opposite is '{@link fsm.Transition#getStateMachine <em>State Machine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transition</em>' containment reference list.
	 * @see fsm.FsmPackage#getStateMachine_Transition()
	 * @see fsm.Transition#getStateMachine
	 * @model opposite="stateMachine" containment="true" ordered="false"
	 * @generated
	 */
	EList<Transition> getTransition();

} // StateMachine
