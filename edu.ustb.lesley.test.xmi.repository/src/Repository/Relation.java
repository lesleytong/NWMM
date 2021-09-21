/**
 */
package Repository;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Relation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link Repository.Relation#getSubsystem <em>Subsystem</em>}</li>
 *   <li>{@link Repository.Relation#getClasses <em>Classes</em>}</li>
 *   <li>{@link Repository.Relation#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see Repository.RepositoryPackage#getRelation()
 * @model
 * @generated
 */
public interface Relation extends EObject {
	/**
	 * Returns the value of the '<em><b>Subsystem</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link Repository.Subsystem#getRelations <em>Relations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subsystem</em>' container reference.
	 * @see #setSubsystem(Subsystem)
	 * @see Repository.RepositoryPackage#getRelation_Subsystem()
	 * @see Repository.Subsystem#getRelations
	 * @model opposite="relations" required="true" transient="false" ordered="false"
	 * @generated
	 */
	Subsystem getSubsystem();

	/**
	 * Sets the value of the '{@link Repository.Relation#getSubsystem <em>Subsystem</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subsystem</em>' container reference.
	 * @see #getSubsystem()
	 * @generated
	 */
	void setSubsystem(Subsystem value);

	/**
	 * Returns the value of the '<em><b>Classes</b></em>' reference list.
	 * The list contents are of type {@link Repository.Class}.
	 * It is bidirectional and its opposite is '{@link Repository.Class#getRelations <em>Relations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Classes</em>' reference list.
	 * @see Repository.RepositoryPackage#getRelation_Classes()
	 * @see Repository.Class#getRelations
	 * @model opposite="relations" lower="2" upper="2" ordered="false"
	 * @generated
	 */
	EList<Repository.Class> getClasses();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see Repository.RepositoryPackage#getRelation_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link Repository.Relation#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // Relation
