/**
 */
package sql;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see sql.SqlPackage
 * @generated
 */
public interface SqlFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SqlFactory eINSTANCE = sql.impl.SqlFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Data Base</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Data Base</em>'.
	 * @generated
	 */
	DataBase createDataBase();

	/**
	 * Returns a new object of class '<em>Table</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Table</em>'.
	 * @generated
	 */
	Table createTable();

	/**
	 * Returns a new object of class '<em>column</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>column</em>'.
	 * @generated
	 */
	column createcolumn();

	/**
	 * Returns a new object of class '<em>primary Key</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>primary Key</em>'.
	 * @generated
	 */
	primaryKey createprimaryKey();

	/**
	 * Returns a new object of class '<em>foreign Key</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>foreign Key</em>'.
	 * @generated
	 */
	foreignKey createforeignKey();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	SqlPackage getSqlPackage();

} //SqlFactory
