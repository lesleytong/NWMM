/**
 */
package sharego;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Business Object Operation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link sharego.BusinessObjectOperation#getRules <em>Rules</em>}</li>
 * </ul>
 *
 * @see sharego.SharegoPackage#getBusinessObjectOperation()
 * @model
 * @generated
 */
public interface BusinessObjectOperation extends Operation {
	/**
	 * Returns the value of the '<em><b>Rules</b></em>' containment reference list.
	 * The list contents are of type {@link sharego.BusinessRule}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rules</em>' containment reference list.
	 * @see sharego.SharegoPackage#getBusinessObjectOperation_Rules()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<BusinessRule> getRules();

} // BusinessObjectOperation
