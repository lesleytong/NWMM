/**
 */
package Repository;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Class</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link Repository.Class#getName <em>Name</em>}</li>
 *   <li>{@link Repository.Class#isIs_deferred <em>Is deferred</em>}</li>
 *   <li>{@link Repository.Class#getFeatures <em>Features</em>}</li>
 *   <li>{@link Repository.Class#getParentSubsystem <em>Parent Subsystem</em>}</li>
 *   <li>{@link Repository.Class#getSubsystem <em>Subsystem</em>}</li>
 *   <li>{@link Repository.Class#getRelations <em>Relations</em>}</li>
 * </ul>
 *
 * @see Repository.RepositoryPackage#getClass_()
 * @model
 * @generated
 */
public interface Class extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see Repository.RepositoryPackage#getClass_Name()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link Repository.Class#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Is deferred</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is deferred</em>' attribute.
	 * @see #setIs_deferred(boolean)
	 * @see Repository.RepositoryPackage#getClass_Is_deferred()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	boolean isIs_deferred();

	/**
	 * Sets the value of the '{@link Repository.Class#isIs_deferred <em>Is deferred</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is deferred</em>' attribute.
	 * @see #isIs_deferred()
	 * @generated
	 */
	void setIs_deferred(boolean value);

	/**
	 * Returns the value of the '<em><b>Features</b></em>' containment reference list.
	 * The list contents are of type {@link Repository.Feature}.
	 * It is bidirectional and its opposite is '{@link Repository.Feature#getTheClass <em>The Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Features</em>' containment reference list.
	 * @see Repository.RepositoryPackage#getClass_Features()
	 * @see Repository.Feature#getTheClass
	 * @model opposite="theClass" containment="true" ordered="false"
	 * @generated
	 */
	EList<Feature> getFeatures();

	/**
	 * Returns the value of the '<em><b>Parent Subsystem</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link Repository.Subsystem#getClasses <em>Classes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent Subsystem</em>' container reference.
	 * @see #setParentSubsystem(Subsystem)
	 * @see Repository.RepositoryPackage#getClass_ParentSubsystem()
	 * @see Repository.Subsystem#getClasses
	 * @model opposite="classes" required="true" transient="false" ordered="false"
	 * @generated
	 */
	Subsystem getParentSubsystem();

	/**
	 * Sets the value of the '{@link Repository.Class#getParentSubsystem <em>Parent Subsystem</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent Subsystem</em>' container reference.
	 * @see #getParentSubsystem()
	 * @generated
	 */
	void setParentSubsystem(Subsystem value);

	/**
	 * Returns the value of the '<em><b>Subsystem</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subsystem</em>' reference.
	 * @see #setSubsystem(Subsystem)
	 * @see Repository.RepositoryPackage#getClass_Subsystem()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	Subsystem getSubsystem();

	/**
	 * Sets the value of the '{@link Repository.Class#getSubsystem <em>Subsystem</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subsystem</em>' reference.
	 * @see #getSubsystem()
	 * @generated
	 */
	void setSubsystem(Subsystem value);

	/**
	 * Returns the value of the '<em><b>Relations</b></em>' reference list.
	 * The list contents are of type {@link Repository.Relation}.
	 * It is bidirectional and its opposite is '{@link Repository.Relation#getClasses <em>Classes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Relations</em>' reference list.
	 * @see Repository.RepositoryPackage#getClass_Relations()
	 * @see Repository.Relation#getClasses
	 * @model opposite="classes" ordered="false"
	 * @generated
	 */
	EList<Relation> getRelations();

} // Class
