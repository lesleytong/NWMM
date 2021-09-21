/**
 */
package amble;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Program</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link amble.Program#getProcesses <em>Processes</em>}</li>
 *   <li>{@link amble.Program#getNetworks <em>Networks</em>}</li>
 * </ul>
 *
 * @see amble.AmblePackage#getProgram()
 * @model
 * @generated
 */
public interface Program extends Element {
	/**
	 * Returns the value of the '<em><b>Processes</b></em>' containment reference list.
	 * The list contents are of type {@link amble.Process}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Processes</em>' containment reference list.
	 * @see amble.AmblePackage#getProgram_Processes()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<amble.Process> getProcesses();

	/**
	 * Returns the value of the '<em><b>Networks</b></em>' containment reference list.
	 * The list contents are of type {@link amble.Network}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Networks</em>' containment reference list.
	 * @see amble.AmblePackage#getProgram_Networks()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Network> getNetworks();

} // Program
