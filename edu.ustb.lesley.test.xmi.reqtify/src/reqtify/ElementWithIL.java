/**
 */
package reqtify;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Element With IL</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link reqtify.ElementWithIL#getName <em>Name</em>}</li>
 *   <li>{@link reqtify.ElementWithIL#getLabel <em>Label</em>}</li>
 * </ul>
 *
 * @see reqtify.ReqtifyPackage#getElementWithIL()
 * @model abstract="true"
 * @generated
 */
public interface ElementWithIL extends TypedElement {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see reqtify.ReqtifyPackage#getElementWithIL_Name()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link reqtify.ElementWithIL#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see reqtify.ReqtifyPackage#getElementWithIL_Label()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the '{@link reqtify.ElementWithIL#getLabel <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);

} // ElementWithIL
