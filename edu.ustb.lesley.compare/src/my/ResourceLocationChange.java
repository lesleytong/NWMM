/**
 */
package my;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Resource Location Change</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link my.ResourceLocationChange#getBaseLocation <em>Base Location</em>}</li>
 *   <li>{@link my.ResourceLocationChange#getChangedLocation <em>Changed Location</em>}</li>
 * </ul>
 *
 * @see my.MyPackage#getResourceLocationChange()
 * @model
 * @generated
 */
public interface ResourceLocationChange extends DiffN {
	/**
	 * Returns the value of the '<em><b>Base Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Base Location</em>' attribute.
	 * @see #setBaseLocation(String)
	 * @see my.MyPackage#getResourceLocationChange_BaseLocation()
	 * @model required="true"
	 * @generated
	 */
	String getBaseLocation();

	/**
	 * Sets the value of the '{@link my.ResourceLocationChange#getBaseLocation <em>Base Location</em>}' attribute.
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
	 * @return the value of the '<em>Changed Location</em>' attribute.
	 * @see #setChangedLocation(String)
	 * @see my.MyPackage#getResourceLocationChange_ChangedLocation()
	 * @model required="true"
	 * @generated
	 */
	String getChangedLocation();

	/**
	 * Sets the value of the '{@link my.ResourceLocationChange#getChangedLocation <em>Changed Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Changed Location</em>' attribute.
	 * @see #getChangedLocation()
	 * @generated
	 */
	void setChangedLocation(String value);

} // ResourceLocationChange
