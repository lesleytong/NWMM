/**
 */
package editor;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Font</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link editor.Font#getFont <em>Font</em>}</li>
 *   <li>{@link editor.Font#isBold <em>Bold</em>}</li>
 *   <li>{@link editor.Font#isItalic <em>Italic</em>}</li>
 * </ul>
 *
 * @see editor.EditorPackage#getFont()
 * @model
 * @generated
 */
public interface Font extends LocatedElement {
	/**
	 * Returns the value of the '<em><b>Font</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Font</em>' attribute.
	 * @see #setFont(String)
	 * @see editor.EditorPackage#getFont_Font()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	String getFont();

	/**
	 * Sets the value of the '{@link editor.Font#getFont <em>Font</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Font</em>' attribute.
	 * @see #getFont()
	 * @generated
	 */
	void setFont(String value);

	/**
	 * Returns the value of the '<em><b>Bold</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bold</em>' attribute.
	 * @see #setBold(boolean)
	 * @see editor.EditorPackage#getFont_Bold()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	boolean isBold();

	/**
	 * Sets the value of the '{@link editor.Font#isBold <em>Bold</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Bold</em>' attribute.
	 * @see #isBold()
	 * @generated
	 */
	void setBold(boolean value);

	/**
	 * Returns the value of the '<em><b>Italic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Italic</em>' attribute.
	 * @see #setItalic(boolean)
	 * @see editor.EditorPackage#getFont_Italic()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	boolean isItalic();

	/**
	 * Sets the value of the '{@link editor.Font#isItalic <em>Italic</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Italic</em>' attribute.
	 * @see #isItalic()
	 * @generated
	 */
	void setItalic(boolean value);

} // Font
