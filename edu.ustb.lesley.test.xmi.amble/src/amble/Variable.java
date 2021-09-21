/**
 */
package amble;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Variable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link amble.Variable#getType <em>Type</em>}</li>
 *   <li>{@link amble.Variable#getInitValue <em>Init Value</em>}</li>
 * </ul>
 *
 * @see amble.AmblePackage#getVariable()
 * @model
 * @generated
 */
public interface Variable extends Element {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see amble.AmblePackage#getVariable_Type()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link amble.Variable#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

	/**
	 * Returns the value of the '<em><b>Init Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Init Value</em>' attribute.
	 * @see #setInitValue(String)
	 * @see amble.AmblePackage#getVariable_InitValue()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	String getInitValue();

	/**
	 * Sets the value of the '{@link amble.Variable#getInitValue <em>Init Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Init Value</em>' attribute.
	 * @see #getInitValue()
	 * @generated
	 */
	void setInitValue(String value);

} // Variable
