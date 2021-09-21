/**
 */
package fsm;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Association State State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fsm.AssociationStateState#getTransition <em>Transition</em>}</li>
 *   <li>{@link fsm.AssociationStateState#getDstTransition <em>Dst Transition</em>}</li>
 *   <li>{@link fsm.AssociationStateState#getSrcTransition <em>Src Transition</em>}</li>
 * </ul>
 *
 * @see fsm.FsmPackage#getAssociationStateState()
 * @model
 * @generated
 */
public interface AssociationStateState extends EObject {
	/**
	 * Returns the value of the '<em><b>Transition</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link fsm.Transition#getAssociationStateState <em>Association State State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transition</em>' reference.
	 * @see #setTransition(Transition)
	 * @see fsm.FsmPackage#getAssociationStateState_Transition()
	 * @see fsm.Transition#getAssociationStateState
	 * @model opposite="associationStateState" required="true" ordered="false"
	 * @generated
	 */
	Transition getTransition();

	/**
	 * Sets the value of the '{@link fsm.AssociationStateState#getTransition <em>Transition</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Transition</em>' reference.
	 * @see #getTransition()
	 * @generated
	 */
	void setTransition(Transition value);

	/**
	 * Returns the value of the '<em><b>Dst Transition</b></em>' reference list.
	 * The list contents are of type {@link fsm.State}.
	 * It is bidirectional and its opposite is '{@link fsm.State#getAssociationStateStatedst <em>Association State Statedst</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dst Transition</em>' reference list.
	 * @see fsm.FsmPackage#getAssociationStateState_DstTransition()
	 * @see fsm.State#getAssociationStateStatedst
	 * @model opposite="associationStateStatedst" required="true" ordered="false"
	 * @generated
	 */
	EList<State> getDstTransition();

	/**
	 * Returns the value of the '<em><b>Src Transition</b></em>' reference list.
	 * The list contents are of type {@link fsm.State}.
	 * It is bidirectional and its opposite is '{@link fsm.State#getAssociationStateStatesrc <em>Association State Statesrc</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Src Transition</em>' reference list.
	 * @see fsm.FsmPackage#getAssociationStateState_SrcTransition()
	 * @see fsm.State#getAssociationStateStatesrc
	 * @model opposite="associationStateStatesrc" required="true" ordered="false"
	 * @generated
	 */
	EList<State> getSrcTransition();

} // AssociationStateState
