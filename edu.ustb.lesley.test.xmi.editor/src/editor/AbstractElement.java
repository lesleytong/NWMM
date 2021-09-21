/**
 */
package editor;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link editor.AbstractElement#getType <em>Type</em>}</li>
 *   <li>{@link editor.AbstractElement#getFormat <em>Format</em>}</li>
 * </ul>
 *
 * @see editor.EditorPackage#getAbstractElement()
 * @model abstract="true"
 * @generated
 */
public interface AbstractElement extends LocatedElement {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see editor.EditorPackage#getAbstractElement_Type()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link editor.AbstractElement#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

	/**
	 * Returns the value of the '<em><b>Format</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Format</em>' containment reference.
	 * @see #setFormat(Format)
	 * @see editor.EditorPackage#getAbstractElement_Format()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	Format getFormat();

	/**
	 * Sets the value of the '{@link editor.AbstractElement#getFormat <em>Format</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Format</em>' containment reference.
	 * @see #getFormat()
	 * @generated
	 */
	void setFormat(Format value);

} // AbstractElement
