/**
 */
package sharego;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Business Object</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link sharego.BusinessObject#getOperations <em>Operations</em>}</li>
 * </ul>
 *
 * @see sharego.SharegoPackage#getBusinessObject()
 * @model
 * @generated
 */
public interface BusinessObject extends Classifier {
	/**
	 * Returns the value of the '<em><b>Operations</b></em>' containment reference list.
	 * The list contents are of type {@link sharego.BusinessObjectOperation}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operations</em>' containment reference list.
	 * @see sharego.SharegoPackage#getBusinessObject_Operations()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<BusinessObjectOperation> getOperations();

} // BusinessObject
