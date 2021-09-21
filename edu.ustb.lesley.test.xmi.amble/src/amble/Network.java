/**
 */
package amble;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Network</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link amble.Network#getChannels <em>Channels</em>}</li>
 * </ul>
 *
 * @see amble.AmblePackage#getNetwork()
 * @model
 * @generated
 */
public interface Network extends Element {
	/**
	 * Returns the value of the '<em><b>Channels</b></em>' containment reference list.
	 * The list contents are of type {@link amble.Channel}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Channels</em>' containment reference list.
	 * @see amble.AmblePackage#getNetwork_Channels()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Channel> getChannels();

} // Network
