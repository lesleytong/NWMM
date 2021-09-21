/**
 */
package petrinet;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>PT Arc</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link petrinet.PTArc#getDst <em>Dst</em>}</li>
 *   <li>{@link petrinet.PTArc#getSrc <em>Src</em>}</li>
 * </ul>
 *
 * @see petrinet.PetrinetPackage#getPTArc()
 * @model
 * @generated
 */
public interface PTArc extends Arc {
	/**
	 * Returns the value of the '<em><b>Dst</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link petrinet.Transition#getIn <em>In</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dst</em>' reference.
	 * @see #setDst(Transition)
	 * @see petrinet.PetrinetPackage#getPTArc_Dst()
	 * @see petrinet.Transition#getIn
	 * @model opposite="in" required="true" ordered="false"
	 * @generated
	 */
	Transition getDst();

	/**
	 * Sets the value of the '{@link petrinet.PTArc#getDst <em>Dst</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dst</em>' reference.
	 * @see #getDst()
	 * @generated
	 */
	void setDst(Transition value);

	/**
	 * Returns the value of the '<em><b>Src</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link petrinet.Place#getOut <em>Out</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Src</em>' reference.
	 * @see #setSrc(Place)
	 * @see petrinet.PetrinetPackage#getPTArc_Src()
	 * @see petrinet.Place#getOut
	 * @model opposite="out" required="true" ordered="false"
	 * @generated
	 */
	Place getSrc();

	/**
	 * Sets the value of the '{@link petrinet.PTArc#getSrc <em>Src</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Src</em>' reference.
	 * @see #getSrc()
	 * @generated
	 */
	void setSrc(Place value);

} // PTArc
