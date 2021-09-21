/**
 */
package sharego;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Service</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link sharego.Service#getOperations <em>Operations</em>}</li>
 * </ul>
 *
 * @see sharego.SharegoPackage#getService()
 * @model
 * @generated
 */
public interface Service extends Classifier {
	/**
	 * Returns the value of the '<em><b>Operations</b></em>' containment reference list.
	 * The list contents are of type {@link sharego.ServiceOperation}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operations</em>' containment reference list.
	 * @see sharego.SharegoPackage#getService_Operations()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<ServiceOperation> getOperations();

} // Service
