/**
 */
package sharego;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see sharego.SharegoPackage
 * @generated
 */
public interface SharegoFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SharegoFactory eINSTANCE = sharego.impl.SharegoFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Operation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Operation</em>'.
	 * @generated
	 */
	Operation createOperation();

	/**
	 * Returns a new object of class '<em>Service</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Service</em>'.
	 * @generated
	 */
	Service createService();

	/**
	 * Returns a new object of class '<em>Business Object</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Business Object</em>'.
	 * @generated
	 */
	BusinessObject createBusinessObject();

	/**
	 * Returns a new object of class '<em>Service Operation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Service Operation</em>'.
	 * @generated
	 */
	ServiceOperation createServiceOperation();

	/**
	 * Returns a new object of class '<em>Business Object Operation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Business Object Operation</em>'.
	 * @generated
	 */
	BusinessObjectOperation createBusinessObjectOperation();

	/**
	 * Returns a new object of class '<em>Business Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Business Rule</em>'.
	 * @generated
	 */
	BusinessRule createBusinessRule();

	/**
	 * Returns a new object of class '<em>Service2 Business Object Flow</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Service2 Business Object Flow</em>'.
	 * @generated
	 */
	Service2BusinessObjectFlow createService2BusinessObjectFlow();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	SharegoPackage getSharegoPackage();

} //SharegoFactory
