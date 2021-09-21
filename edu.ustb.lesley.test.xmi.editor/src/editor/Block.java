/**
 */
package editor;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Block</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link editor.Block#getBlockbegin <em>Blockbegin</em>}</li>
 *   <li>{@link editor.Block#getBlockend <em>Blockend</em>}</li>
 * </ul>
 *
 * @see editor.EditorPackage#getBlock()
 * @model
 * @generated
 */
public interface Block extends AbstractElement {
	/**
	 * Returns the value of the '<em><b>Blockbegin</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Blockbegin</em>' attribute.
	 * @see #setBlockbegin(String)
	 * @see editor.EditorPackage#getBlock_Blockbegin()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	String getBlockbegin();

	/**
	 * Sets the value of the '{@link editor.Block#getBlockbegin <em>Blockbegin</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Blockbegin</em>' attribute.
	 * @see #getBlockbegin()
	 * @generated
	 */
	void setBlockbegin(String value);

	/**
	 * Returns the value of the '<em><b>Blockend</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Blockend</em>' attribute.
	 * @see #setBlockend(String)
	 * @see editor.EditorPackage#getBlock_Blockend()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	String getBlockend();

	/**
	 * Sets the value of the '{@link editor.Block#getBlockend <em>Blockend</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Blockend</em>' attribute.
	 * @see #getBlockend()
	 * @generated
	 */
	void setBlockend(String value);

} // Block
