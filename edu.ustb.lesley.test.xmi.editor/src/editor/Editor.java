/**
 */
package editor;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Editor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link editor.Editor#getExtension <em>Extension</em>}</li>
 *   <li>{@link editor.Editor#getAbstractElements <em>Abstract Elements</em>}</li>
 * </ul>
 *
 * @see editor.EditorPackage#getEditor()
 * @model
 * @generated
 */
public interface Editor extends LocatedElement {
	/**
	 * Returns the value of the '<em><b>Extension</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extension</em>' attribute.
	 * @see #setExtension(String)
	 * @see editor.EditorPackage#getEditor_Extension()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	String getExtension();

	/**
	 * Sets the value of the '{@link editor.Editor#getExtension <em>Extension</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extension</em>' attribute.
	 * @see #getExtension()
	 * @generated
	 */
	void setExtension(String value);

	/**
	 * Returns the value of the '<em><b>Abstract Elements</b></em>' containment reference list.
	 * The list contents are of type {@link editor.AbstractElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Abstract Elements</em>' containment reference list.
	 * @see editor.EditorPackage#getEditor_AbstractElements()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<AbstractElement> getAbstractElements();

} // Editor
