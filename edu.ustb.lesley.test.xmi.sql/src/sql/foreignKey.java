/**
 */
package sql;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>foreign Key</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link sql.foreignKey#getName <em>Name</em>}</li>
 *   <li>{@link sql.foreignKey#getReftable <em>Reftable</em>}</li>
 *   <li>{@link sql.foreignKey#getRef <em>Ref</em>}</li>
 * </ul>
 *
 * @see sql.SqlPackage#getforeignKey()
 * @model
 * @generated
 */
public interface foreignKey extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see sql.SqlPackage#getforeignKey_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link sql.foreignKey#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Reftable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reftable</em>' reference.
	 * @see #setReftable(Table)
	 * @see sql.SqlPackage#getforeignKey_Reftable()
	 * @model
	 * @generated
	 */
	Table getReftable();

	/**
	 * Sets the value of the '{@link sql.foreignKey#getReftable <em>Reftable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reftable</em>' reference.
	 * @see #getReftable()
	 * @generated
	 */
	void setReftable(Table value);

	/**
	 * Returns the value of the '<em><b>Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ref</em>' reference.
	 * @see #setRef(column)
	 * @see sql.SqlPackage#getforeignKey_Ref()
	 * @model
	 * @generated
	 */
	column getRef();

	/**
	 * Sets the value of the '{@link sql.foreignKey#getRef <em>Ref</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ref</em>' reference.
	 * @see #getRef()
	 * @generated
	 */
	void setRef(column value);

} // foreignKey
