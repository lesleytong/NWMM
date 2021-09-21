/**
 */
package sql;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Table</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link sql.Table#getName <em>Name</em>}</li>
 *   <li>{@link sql.Table#getColumns <em>Columns</em>}</li>
 *   <li>{@link sql.Table#getPrimaryKey <em>Primary Key</em>}</li>
 *   <li>{@link sql.Table#getForeignkeys <em>Foreignkeys</em>}</li>
 * </ul>
 *
 * @see sql.SqlPackage#getTable()
 * @model
 * @generated
 */
public interface Table extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see sql.SqlPackage#getTable_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link sql.Table#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Columns</b></em>' containment reference list.
	 * The list contents are of type {@link sql.column}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Columns</em>' containment reference list.
	 * @see sql.SqlPackage#getTable_Columns()
	 * @model containment="true"
	 * @generated
	 */
	EList<column> getColumns();

	/**
	 * Returns the value of the '<em><b>Primary Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Primary Key</em>' containment reference.
	 * @see #setPrimaryKey(primaryKey)
	 * @see sql.SqlPackage#getTable_PrimaryKey()
	 * @model containment="true"
	 * @generated
	 */
	primaryKey getPrimaryKey();

	/**
	 * Sets the value of the '{@link sql.Table#getPrimaryKey <em>Primary Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Primary Key</em>' containment reference.
	 * @see #getPrimaryKey()
	 * @generated
	 */
	void setPrimaryKey(primaryKey value);

	/**
	 * Returns the value of the '<em><b>Foreignkeys</b></em>' containment reference list.
	 * The list contents are of type {@link sql.foreignKey}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Foreignkeys</em>' containment reference list.
	 * @see sql.SqlPackage#getTable_Foreignkeys()
	 * @model containment="true"
	 * @generated
	 */
	EList<foreignKey> getForeignkeys();

} // Table
