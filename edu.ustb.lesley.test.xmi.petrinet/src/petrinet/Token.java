/**
 */
package petrinet;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Token</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link petrinet.Token#getName <em>Name</em>}</li>
 *   <li>{@link petrinet.Token#getPlace <em>Place</em>}</li>
 * </ul>
 *
 * @see petrinet.PetrinetPackage#getToken()
 * @model
 * @generated
 */
public interface Token extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see petrinet.PetrinetPackage#getToken_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link petrinet.Token#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Place</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link petrinet.Place#getToken <em>Token</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Place</em>' container reference.
	 * @see #setPlace(Place)
	 * @see petrinet.PetrinetPackage#getToken_Place()
	 * @see petrinet.Place#getToken
	 * @model opposite="token" required="true" transient="false" ordered="false"
	 * @generated
	 */
	Place getPlace();

	/**
	 * Sets the value of the '{@link petrinet.Token#getPlace <em>Place</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Place</em>' container reference.
	 * @see #getPlace()
	 * @generated
	 */
	void setPlace(Place value);

} // Token
