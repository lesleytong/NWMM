/**
 */
package compare;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Resource Attachment Change</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * This particular kind of difference describes the fragmentation (or un-fragmentation) of a model element in its own (or back in its parent's) resource.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link compare.ResourceAttachmentChange#getResourceURI <em>Resource URI</em>}</li>
 * </ul>
 *
 * @see compare.ComparePackage#getResourceAttachmentChange()
 * @model
 * @generated
 */
public interface ResourceAttachmentChange extends Diff {
	/**
	 * Returns the value of the '<em><b>Resource URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * URI of the resource in which the parent mapping's element is now located.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Resource URI</em>' attribute.
	 * @see #setResourceURI(String)
	 * @see compare.ComparePackage#getResourceAttachmentChange_ResourceURI()
	 * @model required="true"
	 * @generated
	 */
	String getResourceURI();

	/**
	 * Sets the value of the '{@link compare.ResourceAttachmentChange#getResourceURI <em>Resource URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resource URI</em>' attribute.
	 * @see #getResourceURI()
	 * @generated
	 */
	void setResourceURI(String value);

} // ResourceAttachmentChange
