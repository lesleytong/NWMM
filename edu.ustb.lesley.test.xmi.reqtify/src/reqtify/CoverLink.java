/**
 */
package reqtify;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Cover Link</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link reqtify.CoverLink#getLinkWith <em>Link With</em>}</li>
 * </ul>
 *
 * @see reqtify.ReqtifyPackage#getCoverLink()
 * @model
 * @generated
 */
public interface CoverLink extends TypedElement {
	/**
	 * Returns the value of the '<em><b>Link With</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Link With</em>' reference.
	 * @see #setLinkWith(AbstractRequirement)
	 * @see reqtify.ReqtifyPackage#getCoverLink_LinkWith()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	AbstractRequirement getLinkWith();

	/**
	 * Sets the value of the '{@link reqtify.CoverLink#getLinkWith <em>Link With</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Link With</em>' reference.
	 * @see #getLinkWith()
	 * @generated
	 */
	void setLinkWith(AbstractRequirement value);

} // CoverLink
