/**
 */
package fsm;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Root Folder</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fsm.RootFolder#getName <em>Name</em>}</li>
 *   <li>{@link fsm.RootFolder#getStateMachine <em>State Machine</em>}</li>
 * </ul>
 *
 * @see fsm.FsmPackage#getRootFolder()
 * @model
 * @generated
 */
public interface RootFolder extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see fsm.FsmPackage#getRootFolder_Name()
	 * @model unique="false" ordered="false"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link fsm.RootFolder#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>State Machine</b></em>' containment reference list.
	 * The list contents are of type {@link fsm.StateMachine}.
	 * It is bidirectional and its opposite is '{@link fsm.StateMachine#getRootFolder <em>Root Folder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State Machine</em>' containment reference list.
	 * @see fsm.FsmPackage#getRootFolder_StateMachine()
	 * @see fsm.StateMachine#getRootFolder
	 * @model opposite="rootFolder" containment="true" ordered="false"
	 * @generated
	 */
	EList<StateMachine> getStateMachine();

} // RootFolder
