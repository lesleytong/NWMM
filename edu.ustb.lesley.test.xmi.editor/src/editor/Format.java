/**
 */
package editor;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Format</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link editor.Format#getColor <em>Color</em>}</li>
 *   <li>{@link editor.Format#getFont <em>Font</em>}</li>
 * </ul>
 *
 * @see editor.EditorPackage#getFormat()
 * @model
 * @generated
 */
public interface Format extends LocatedElement {
	/**
	 * Returns the value of the '<em><b>Color</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Color</em>' containment reference.
	 * @see #setColor(Color)
	 * @see editor.EditorPackage#getFormat_Color()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	Color getColor();

	/**
	 * Sets the value of the '{@link editor.Format#getColor <em>Color</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Color</em>' containment reference.
	 * @see #getColor()
	 * @generated
	 */
	void setColor(Color value);

	/**
	 * Returns the value of the '<em><b>Font</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Font</em>' containment reference.
	 * @see #setFont(Font)
	 * @see editor.EditorPackage#getFormat_Font()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	Font getFont();

	/**
	 * Sets the value of the '{@link editor.Format#getFont <em>Font</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Font</em>' containment reference.
	 * @see #getFont()
	 * @generated
	 */
	void setFont(Font value);

} // Format
