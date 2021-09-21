/**
 */
package petrinet;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Place</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link petrinet.Place#getName <em>Name</em>}</li>
 *   <li>{@link petrinet.Place#getNet <em>Net</em>}</li>
 *   <li>{@link petrinet.Place#getOut <em>Out</em>}</li>
 *   <li>{@link petrinet.Place#getIn <em>In</em>}</li>
 *   <li>{@link petrinet.Place#getToken <em>Token</em>}</li>
 * </ul>
 *
 * @see petrinet.PetrinetPackage#getPlace()
 * @model
 * @generated
 */
public interface Place extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see petrinet.PetrinetPackage#getPlace_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link petrinet.Place#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Net</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link petrinet.Net#getPlace <em>Place</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Net</em>' container reference.
	 * @see #setNet(Net)
	 * @see petrinet.PetrinetPackage#getPlace_Net()
	 * @see petrinet.Net#getPlace
	 * @model opposite="place" required="true" transient="false" ordered="false"
	 * @generated
	 */
	Net getNet();

	/**
	 * Sets the value of the '{@link petrinet.Place#getNet <em>Net</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Net</em>' container reference.
	 * @see #getNet()
	 * @generated
	 */
	void setNet(Net value);

	/**
	 * Returns the value of the '<em><b>Out</b></em>' reference list.
	 * The list contents are of type {@link petrinet.PTArc}.
	 * It is bidirectional and its opposite is '{@link petrinet.PTArc#getSrc <em>Src</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Out</em>' reference list.
	 * @see petrinet.PetrinetPackage#getPlace_Out()
	 * @see petrinet.PTArc#getSrc
	 * @model opposite="src"
	 * @generated
	 */
	EList<PTArc> getOut();

	/**
	 * Returns the value of the '<em><b>In</b></em>' reference list.
	 * The list contents are of type {@link petrinet.TPArc}.
	 * It is bidirectional and its opposite is '{@link petrinet.TPArc#getDst <em>Dst</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>In</em>' reference list.
	 * @see petrinet.PetrinetPackage#getPlace_In()
	 * @see petrinet.TPArc#getDst
	 * @model opposite="dst"
	 * @generated
	 */
	EList<TPArc> getIn();

	/**
	 * Returns the value of the '<em><b>Token</b></em>' containment reference list.
	 * The list contents are of type {@link petrinet.Token}.
	 * It is bidirectional and its opposite is '{@link petrinet.Token#getPlace <em>Place</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Token</em>' containment reference list.
	 * @see petrinet.PetrinetPackage#getPlace_Token()
	 * @see petrinet.Token#getPlace
	 * @model opposite="place" containment="true"
	 * @generated
	 */
	EList<Token> getToken();

} // Place
