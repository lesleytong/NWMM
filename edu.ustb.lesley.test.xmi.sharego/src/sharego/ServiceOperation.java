/**
 */
package sharego;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Service Operation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link sharego.ServiceOperation#getBOFlows <em>BO Flows</em>}</li>
 *   <li>{@link sharego.ServiceOperation#getRules <em>Rules</em>}</li>
 * </ul>
 *
 * @see sharego.SharegoPackage#getServiceOperation()
 * @model
 * @generated
 */
public interface ServiceOperation extends Operation {
	/**
	 * Returns the value of the '<em><b>BO Flows</b></em>' containment reference list.
	 * The list contents are of type {@link sharego.Service2BusinessObjectFlow}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>BO Flows</em>' containment reference list.
	 * @see sharego.SharegoPackage#getServiceOperation_BOFlows()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Service2BusinessObjectFlow> getBOFlows();

	/**
	 * Returns the value of the '<em><b>Rules</b></em>' containment reference list.
	 * The list contents are of type {@link sharego.BusinessRule}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rules</em>' containment reference list.
	 * @see sharego.SharegoPackage#getServiceOperation_Rules()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<BusinessRule> getRules();

} // ServiceOperation
