/**
 */
package Repository;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Feature</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link Repository.Feature#getTheClass <em>The Class</em>}</li>
 *   <li>{@link Repository.Feature#getType <em>Type</em>}</li>
 *   <li>{@link Repository.Feature#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see Repository.RepositoryPackage#getFeature()
 * @model
 * @generated
 */
public interface Feature extends EObject {
	/**
	 * Returns the value of the '<em><b>The Class</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link Repository.Class#getFeatures <em>Features</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>The Class</em>' container reference.
	 * @see #setTheClass(Repository.Class)
	 * @see Repository.RepositoryPackage#getFeature_TheClass()
	 * @see Repository.Class#getFeatures
	 * @model opposite="features" required="true" transient="false" ordered="false"
	 * @generated
	 */
	Repository.Class getTheClass();

	/**
	 * Sets the value of the '{@link Repository.Feature#getTheClass <em>The Class</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>The Class</em>' container reference.
	 * @see #getTheClass()
	 * @generated
	 */
	void setTheClass(Repository.Class value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(Type)
	 * @see Repository.RepositoryPackage#getFeature_Type()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	Type getType();

	/**
	 * Sets the value of the '{@link Repository.Feature#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(Type value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see Repository.RepositoryPackage#getFeature_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link Repository.Feature#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // Feature
