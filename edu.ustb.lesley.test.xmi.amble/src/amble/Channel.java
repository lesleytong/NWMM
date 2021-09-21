/**
 */
package amble;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Channel</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link amble.Channel#getMessages <em>Messages</em>}</li>
 *   <li>{@link amble.Channel#getSource <em>Source</em>}</li>
 *   <li>{@link amble.Channel#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @see amble.AmblePackage#getChannel()
 * @model
 * @generated
 */
public interface Channel extends Element {
	/**
	 * Returns the value of the '<em><b>Messages</b></em>' containment reference list.
	 * The list contents are of type {@link amble.Message}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Messages</em>' containment reference list.
	 * @see amble.AmblePackage#getChannel_Messages()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Message> getMessages();

	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(amble.Process)
	 * @see amble.AmblePackage#getChannel_Source()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	amble.Process getSource();

	/**
	 * Sets the value of the '{@link amble.Channel#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(amble.Process value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(amble.Process)
	 * @see amble.AmblePackage#getChannel_Target()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	amble.Process getTarget();

	/**
	 * Sets the value of the '{@link amble.Channel#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(amble.Process value);

} // Channel
