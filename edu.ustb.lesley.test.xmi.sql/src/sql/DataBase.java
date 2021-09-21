/**
 */
package sql;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Data Base</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link sql.DataBase#getTables <em>Tables</em>}</li>
 * </ul>
 *
 * @see sql.SqlPackage#getDataBase()
 * @model
 * @generated
 */
public interface DataBase extends EObject {
	/**
	 * Returns the value of the '<em><b>Tables</b></em>' containment reference list.
	 * The list contents are of type {@link sql.Table}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tables</em>' containment reference list.
	 * @see sql.SqlPackage#getDataBase_Tables()
	 * @model containment="true"
	 * @generated
	 */
	EList<Table> getTables();

} // DataBase
