/**
 */
package my;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Resource Attachment Change</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link my.ResourceAttachmentChange#getResourceURI <em>Resource URI</em>}</li>
 * </ul>
 *
 * @see my.MyPackage#getResourceAttachmentChange()
 * @model
 * @generated
 */
public interface ResourceAttachmentChange extends DiffN {
	/**
	 * Returns the value of the '<em><b>Resource URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resource URI</em>' attribute.
	 * @see #setResourceURI(String)
	 * @see my.MyPackage#getResourceAttachmentChange_ResourceURI()
	 * @model required="true"
	 * @generated
	 */
	String getResourceURI();

	/**
	 * Sets the value of the '{@link my.ResourceAttachmentChange#getResourceURI <em>Resource URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resource URI</em>' attribute.
	 * @see #getResourceURI()
	 * @generated
	 */
	void setResourceURI(String value);

} // ResourceAttachmentChange
