/**
 */
package amble;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Process</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link amble.Process#getMinId <em>Min Id</em>}</li>
 *   <li>{@link amble.Process#getMaxId <em>Max Id</em>}</li>
 *   <li>{@link amble.Process#getInstancesNb <em>Instances Nb</em>}</li>
 *   <li>{@link amble.Process#getStates <em>States</em>}</li>
 *   <li>{@link amble.Process#getTransitions <em>Transitions</em>}</li>
 *   <li>{@link amble.Process#getInitial <em>Initial</em>}</li>
 *   <li>{@link amble.Process#getVariables <em>Variables</em>}</li>
 *   <li>{@link amble.Process#getConnectedTo <em>Connected To</em>}</li>
 * </ul>
 *
 * @see amble.AmblePackage#getProcess()
 * @model
 * @generated
 */
public interface Process extends Element {
	/**
	 * Returns the value of the '<em><b>Min Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Min Id</em>' attribute.
	 * @see #setMinId(int)
	 * @see amble.AmblePackage#getProcess_MinId()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	int getMinId();

	/**
	 * Sets the value of the '{@link amble.Process#getMinId <em>Min Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min Id</em>' attribute.
	 * @see #getMinId()
	 * @generated
	 */
	void setMinId(int value);

	/**
	 * Returns the value of the '<em><b>Max Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Id</em>' attribute.
	 * @see #setMaxId(int)
	 * @see amble.AmblePackage#getProcess_MaxId()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	int getMaxId();

	/**
	 * Sets the value of the '{@link amble.Process#getMaxId <em>Max Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Id</em>' attribute.
	 * @see #getMaxId()
	 * @generated
	 */
	void setMaxId(int value);

	/**
	 * Returns the value of the '<em><b>Instances Nb</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Instances Nb</em>' attribute.
	 * @see #setInstancesNb(int)
	 * @see amble.AmblePackage#getProcess_InstancesNb()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	int getInstancesNb();

	/**
	 * Sets the value of the '{@link amble.Process#getInstancesNb <em>Instances Nb</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Instances Nb</em>' attribute.
	 * @see #getInstancesNb()
	 * @generated
	 */
	void setInstancesNb(int value);

	/**
	 * Returns the value of the '<em><b>States</b></em>' containment reference list.
	 * The list contents are of type {@link amble.State}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>States</em>' containment reference list.
	 * @see amble.AmblePackage#getProcess_States()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<State> getStates();

	/**
	 * Returns the value of the '<em><b>Transitions</b></em>' containment reference list.
	 * The list contents are of type {@link amble.Transition}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transitions</em>' containment reference list.
	 * @see amble.AmblePackage#getProcess_Transitions()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Transition> getTransitions();

	/**
	 * Returns the value of the '<em><b>Initial</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Initial</em>' containment reference.
	 * @see #setInitial(Action)
	 * @see amble.AmblePackage#getProcess_Initial()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	Action getInitial();

	/**
	 * Sets the value of the '{@link amble.Process#getInitial <em>Initial</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Initial</em>' containment reference.
	 * @see #getInitial()
	 * @generated
	 */
	void setInitial(Action value);

	/**
	 * Returns the value of the '<em><b>Variables</b></em>' containment reference list.
	 * The list contents are of type {@link amble.Variable}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variables</em>' containment reference list.
	 * @see amble.AmblePackage#getProcess_Variables()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Variable> getVariables();

	/**
	 * Returns the value of the '<em><b>Connected To</b></em>' reference list.
	 * The list contents are of type {@link amble.Network}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Connected To</em>' reference list.
	 * @see amble.AmblePackage#getProcess_ConnectedTo()
	 * @model ordered="false"
	 * @generated
	 */
	EList<Network> getConnectedTo();

} // Process
