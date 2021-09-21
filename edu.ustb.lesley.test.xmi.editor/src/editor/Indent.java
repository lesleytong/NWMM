/**
 */
package editor;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Indent</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link editor.Indent#getRetrait <em>Retrait</em>}</li>
 *   <li>{@link editor.Indent#getGroupElementIndent <em>Group Element Indent</em>}</li>
 * </ul>
 *
 * @see editor.EditorPackage#getIndent()
 * @model
 * @generated
 */
public interface Indent extends LocatedElement {
	/**
	 * Returns the value of the '<em><b>Retrait</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Retrait</em>' attribute.
	 * @see #setRetrait(String)
	 * @see editor.EditorPackage#getIndent_Retrait()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	String getRetrait();

	/**
	 * Sets the value of the '{@link editor.Indent#getRetrait <em>Retrait</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Retrait</em>' attribute.
	 * @see #getRetrait()
	 * @generated
	 */
	void setRetrait(String value);

	/**
	 * Returns the value of the '<em><b>Group Element Indent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Group Element Indent</em>' reference.
	 * @see #setGroupElementIndent(GroupElement)
	 * @see editor.EditorPackage#getIndent_GroupElementIndent()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	GroupElement getGroupElementIndent();

	/**
	 * Sets the value of the '{@link editor.Indent#getGroupElementIndent <em>Group Element Indent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Group Element Indent</em>' reference.
	 * @see #getGroupElementIndent()
	 * @generated
	 */
	void setGroupElementIndent(GroupElement value);

} // Indent
