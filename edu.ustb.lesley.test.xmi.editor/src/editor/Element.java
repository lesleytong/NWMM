/**
 */
package editor;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link editor.Element#getElement <em>Element</em>}</li>
 *   <li>{@link editor.Element#getGroupElement <em>Group Element</em>}</li>
 * </ul>
 *
 * @see editor.EditorPackage#getElement()
 * @model
 * @generated
 */
public interface Element extends LocatedElement {
	/**
	 * Returns the value of the '<em><b>Element</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Element</em>' attribute.
	 * @see #setElement(String)
	 * @see editor.EditorPackage#getElement_Element()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	String getElement();

	/**
	 * Sets the value of the '{@link editor.Element#getElement <em>Element</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Element</em>' attribute.
	 * @see #getElement()
	 * @generated
	 */
	void setElement(String value);

	/**
	 * Returns the value of the '<em><b>Group Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Group Element</em>' reference.
	 * @see #setGroupElement(GroupElement)
	 * @see editor.EditorPackage#getElement_GroupElement()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	GroupElement getGroupElement();

	/**
	 * Sets the value of the '{@link editor.Element#getGroupElement <em>Group Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Group Element</em>' reference.
	 * @see #getGroupElement()
	 * @generated
	 */
	void setGroupElement(GroupElement value);

} // Element
