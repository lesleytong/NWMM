/**
 */
package reqtify;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Document</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link reqtify.Document#getProject <em>Project</em>}</li>
 *   <li>{@link reqtify.Document#getSections <em>Sections</em>}</li>
 * </ul>
 *
 * @see reqtify.ReqtifyPackage#getDocument()
 * @model
 * @generated
 */
public interface Document extends ElementWithIL {
	/**
	 * Returns the value of the '<em><b>Project</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link reqtify.Project#getDocuments <em>Documents</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Project</em>' container reference.
	 * @see #setProject(Project)
	 * @see reqtify.ReqtifyPackage#getDocument_Project()
	 * @see reqtify.Project#getDocuments
	 * @model opposite="documents" required="true" transient="false" ordered="false"
	 * @generated
	 */
	Project getProject();

	/**
	 * Sets the value of the '{@link reqtify.Document#getProject <em>Project</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Project</em>' container reference.
	 * @see #getProject()
	 * @generated
	 */
	void setProject(Project value);

	/**
	 * Returns the value of the '<em><b>Sections</b></em>' containment reference list.
	 * The list contents are of type {@link reqtify.Section}.
	 * It is bidirectional and its opposite is '{@link reqtify.Section#getDocument <em>Document</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sections</em>' containment reference list.
	 * @see reqtify.ReqtifyPackage#getDocument_Sections()
	 * @see reqtify.Section#getDocument
	 * @model opposite="document" containment="true" ordered="false"
	 * @generated
	 */
	EList<Section> getSections();

} // Document
