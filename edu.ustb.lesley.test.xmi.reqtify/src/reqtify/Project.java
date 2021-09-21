/**
 */
package reqtify;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Project</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link reqtify.Project#getDocuments <em>Documents</em>}</li>
 * </ul>
 *
 * @see reqtify.ReqtifyPackage#getProject()
 * @model
 * @generated
 */
public interface Project extends EObject {
	/**
	 * Returns the value of the '<em><b>Documents</b></em>' containment reference list.
	 * The list contents are of type {@link reqtify.Document}.
	 * It is bidirectional and its opposite is '{@link reqtify.Document#getProject <em>Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Documents</em>' containment reference list.
	 * @see reqtify.ReqtifyPackage#getProject_Documents()
	 * @see reqtify.Document#getProject
	 * @model opposite="project" containment="true" required="true" ordered="false"
	 * @generated
	 */
	EList<Document> getDocuments();

} // Project
