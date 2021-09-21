/**
 */
package editor;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Color</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link editor.Color#getRed <em>Red</em>}</li>
 *   <li>{@link editor.Color#getGreen <em>Green</em>}</li>
 *   <li>{@link editor.Color#getBlue <em>Blue</em>}</li>
 * </ul>
 *
 * @see editor.EditorPackage#getColor()
 * @model
 * @generated
 */
public interface Color extends LocatedElement {
	/**
	 * Returns the value of the '<em><b>Red</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Red</em>' attribute.
	 * @see #setRed(int)
	 * @see editor.EditorPackage#getColor_Red()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	int getRed();

	/**
	 * Sets the value of the '{@link editor.Color#getRed <em>Red</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Red</em>' attribute.
	 * @see #getRed()
	 * @generated
	 */
	void setRed(int value);

	/**
	 * Returns the value of the '<em><b>Green</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Green</em>' attribute.
	 * @see #setGreen(int)
	 * @see editor.EditorPackage#getColor_Green()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	int getGreen();

	/**
	 * Sets the value of the '{@link editor.Color#getGreen <em>Green</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Green</em>' attribute.
	 * @see #getGreen()
	 * @generated
	 */
	void setGreen(int value);

	/**
	 * Returns the value of the '<em><b>Blue</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Blue</em>' attribute.
	 * @see #setBlue(int)
	 * @see editor.EditorPackage#getColor_Blue()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	int getBlue();

	/**
	 * Sets the value of the '{@link editor.Color#getBlue <em>Blue</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Blue</em>' attribute.
	 * @see #getBlue()
	 * @generated
	 */
	void setBlue(int value);

} // Color
