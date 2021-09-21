/**
 */
package Repository;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Subsystem</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link Repository.Subsystem#getClasses <em>Classes</em>}</li>
 *   <li>{@link Repository.Subsystem#getRelations <em>Relations</em>}</li>
 * </ul>
 *
 * @see Repository.RepositoryPackage#getSubsystem()
 * @model
 * @generated
 */
public interface Subsystem extends EObject {
	/**
	 * Returns the value of the '<em><b>Classes</b></em>' containment reference list.
	 * The list contents are of type {@link Repository.Class}.
	 * It is bidirectional and its opposite is '{@link Repository.Class#getParentSubsystem <em>Parent Subsystem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Classes</em>' containment reference list.
	 * @see Repository.RepositoryPackage#getSubsystem_Classes()
	 * @see Repository.Class#getParentSubsystem
	 * @model opposite="parentSubsystem" containment="true" ordered="false"
	 * @generated
	 */
	EList<Repository.Class> getClasses();

	/**
	 * Returns the value of the '<em><b>Relations</b></em>' containment reference list.
	 * The list contents are of type {@link Repository.Relation}.
	 * It is bidirectional and its opposite is '{@link Repository.Relation#getSubsystem <em>Subsystem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Relations</em>' containment reference list.
	 * @see Repository.RepositoryPackage#getSubsystem_Relations()
	 * @see Repository.Relation#getSubsystem
	 * @model opposite="subsystem" containment="true" ordered="false"
	 * @generated
	 */
	EList<Relation> getRelations();

} // Subsystem
