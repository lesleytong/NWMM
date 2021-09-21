/**
 */
package sharego;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Flow</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link sharego.Flow#getSource <em>Source</em>}</li>
 *   <li>{@link sharego.Flow#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @see sharego.SharegoPackage#getFlow()
 * @model abstract="true"
 * @generated
 */
public interface Flow extends ModelElement {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' attribute.
	 * @see #setSource(String)
	 * @see sharego.SharegoPackage#getFlow_Source()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	String getSource();

	/**
	 * Sets the value of the '{@link sharego.Flow#getSource <em>Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' attribute.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(String value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' attribute.
	 * @see #setTarget(String)
	 * @see sharego.SharegoPackage#getFlow_Target()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	String getTarget();

	/**
	 * Sets the value of the '{@link sharego.Flow#getTarget <em>Target</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' attribute.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(String value);

} // Flow
