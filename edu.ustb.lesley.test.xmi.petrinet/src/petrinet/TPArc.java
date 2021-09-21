/**
 */
package petrinet;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TP Arc</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link petrinet.TPArc#getSrc <em>Src</em>}</li>
 *   <li>{@link petrinet.TPArc#getDst <em>Dst</em>}</li>
 * </ul>
 *
 * @see petrinet.PetrinetPackage#getTPArc()
 * @model
 * @generated
 */
public interface TPArc extends Arc {
	/**
	 * Returns the value of the '<em><b>Src</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link petrinet.Transition#getOut <em>Out</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Src</em>' reference.
	 * @see #setSrc(Transition)
	 * @see petrinet.PetrinetPackage#getTPArc_Src()
	 * @see petrinet.Transition#getOut
	 * @model opposite="out" required="true" ordered="false"
	 * @generated
	 */
	Transition getSrc();

	/**
	 * Sets the value of the '{@link petrinet.TPArc#getSrc <em>Src</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Src</em>' reference.
	 * @see #getSrc()
	 * @generated
	 */
	void setSrc(Transition value);

	/**
	 * Returns the value of the '<em><b>Dst</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link petrinet.Place#getIn <em>In</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dst</em>' reference.
	 * @see #setDst(Place)
	 * @see petrinet.PetrinetPackage#getTPArc_Dst()
	 * @see petrinet.Place#getIn
	 * @model opposite="in" required="true" ordered="false"
	 * @generated
	 */
	Place getDst();

	/**
	 * Sets the value of the '{@link petrinet.TPArc#getDst <em>Dst</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dst</em>' reference.
	 * @see #getDst()
	 * @generated
	 */
	void setDst(Place value);

} // TPArc
