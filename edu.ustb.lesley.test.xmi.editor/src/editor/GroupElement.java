/**
 */
package editor;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Group Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link editor.GroupElement#getIndent <em>Indent</em>}</li>
 *   <li>{@link editor.GroupElement#getElement <em>Element</em>}</li>
 * </ul>
 *
 * @see editor.EditorPackage#getGroupElement()
 * @model
 * @generated
 */
public interface GroupElement extends AbstractElement {
	/**
	 * Returns the value of the '<em><b>Indent</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Indent</em>' containment reference.
	 * @see #setIndent(Indent)
	 * @see editor.EditorPackage#getGroupElement_Indent()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	Indent getIndent();

	/**
	 * Sets the value of the '{@link editor.GroupElement#getIndent <em>Indent</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Indent</em>' containment reference.
	 * @see #getIndent()
	 * @generated
	 */
	void setIndent(Indent value);

	/**
	 * Returns the value of the '<em><b>Element</b></em>' containment reference list.
	 * The list contents are of type {@link editor.Element}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Element</em>' containment reference list.
	 * @see editor.EditorPackage#getGroupElement_Element()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Element> getElement();

} // GroupElement
