/**
 */
package sharego;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Business Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link sharego.BusinessRule#getExpr <em>Expr</em>}</li>
 * </ul>
 *
 * @see sharego.SharegoPackage#getBusinessRule()
 * @model
 * @generated
 */
public interface BusinessRule extends ModelElement {
	/**
	 * Returns the value of the '<em><b>Expr</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expr</em>' attribute.
	 * @see #setExpr(String)
	 * @see sharego.SharegoPackage#getBusinessRule_Expr()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	String getExpr();

	/**
	 * Sets the value of the '{@link sharego.BusinessRule#getExpr <em>Expr</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expr</em>' attribute.
	 * @see #getExpr()
	 * @generated
	 */
	void setExpr(String value);

} // BusinessRule
