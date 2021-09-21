/**
 */
package compare;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Resource Location Change</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * This particular kind of difference describes the change of a resource's location.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link compare.ResourceLocationChange#getBaseLocation <em>Base Location</em>}</li>
 *   <li>{@link compare.ResourceLocationChange#getChangedLocation <em>Changed Location</em>}</li>
 * </ul>
 *
 * @see compare.ComparePackage#getResourceLocationChange()
 * @model
 * @generated
 */
public interface ResourceLocationChange extends Diff {
	/**
	 * Returns the value of the '<em><b>Base Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Cannot be null. Represents the URI of the left resource of this mapping.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Base Location</em>' attribute.
	 * @see #setBaseLocation(String)
	 * @see compare.ComparePackage#getResourceLocationChange_BaseLocation()
	 * @model required="true"
	 * @generated
	 */
	String getBaseLocation();

	/**
	 * Sets the value of the '{@link compare.ResourceLocationChange#getBaseLocation <em>Base Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base Location</em>' attribute.
	 * @see #getBaseLocation()
	 * @generated
	 */
	void setBaseLocation(String value);

	/**
	 * Returns the value of the '<em><b>Changed Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Cannot be null. Represents the URI of the right resource of this mapping.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Changed Location</em>' attribute.
	 * @see #setChangedLocation(String)
	 * @see compare.ComparePackage#getResourceLocationChange_ChangedLocation()
	 * @model required="true"
	 * @generated
	 */
	String getChangedLocation();

	/**
	 * Sets the value of the '{@link compare.ResourceLocationChange#getChangedLocation <em>Changed Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Changed Location</em>' attribute.
	 * @see #getChangedLocation()
	 * @generated
	 */
	void setChangedLocation(String value);

} // ResourceLocationChange
