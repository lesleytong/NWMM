/**
 */
package fsm.impl;

import fsm.AssociationStateState;
import fsm.FsmPackage;
import fsm.State;
import fsm.StateMachine;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link fsm.impl.StateImpl#getStateMachine <em>State Machine</em>}</li>
 *   <li>{@link fsm.impl.StateImpl#getAssociationStateStatedst <em>Association State Statedst</em>}</li>
 *   <li>{@link fsm.impl.StateImpl#getAssociationStateStatesrc <em>Association State Statesrc</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StateImpl extends MgaObjectImpl implements State {
	/**
	 * The cached value of the '{@link #getAssociationStateStatedst() <em>Association State Statedst</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAssociationStateStatedst()
	 * @generated
	 * @ordered
	 */
	protected EList<AssociationStateState> associationStateStatedst;

	/**
	 * The cached value of the '{@link #getAssociationStateStatesrc() <em>Association State Statesrc</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAssociationStateStatesrc()
	 * @generated
	 * @ordered
	 */
	protected EList<AssociationStateState> associationStateStatesrc;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FsmPackage.Literals.STATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateMachine getStateMachine() {
		if (eContainerFeatureID() != FsmPackage.STATE__STATE_MACHINE) return null;
		return (StateMachine)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStateMachine(StateMachine newStateMachine, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newStateMachine, FsmPackage.STATE__STATE_MACHINE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStateMachine(StateMachine newStateMachine) {
		if (newStateMachine != eInternalContainer() || (eContainerFeatureID() != FsmPackage.STATE__STATE_MACHINE && newStateMachine != null)) {
			if (EcoreUtil.isAncestor(this, newStateMachine))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newStateMachine != null)
				msgs = ((InternalEObject)newStateMachine).eInverseAdd(this, FsmPackage.STATE_MACHINE__STATE, StateMachine.class, msgs);
			msgs = basicSetStateMachine(newStateMachine, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FsmPackage.STATE__STATE_MACHINE, newStateMachine, newStateMachine));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AssociationStateState> getAssociationStateStatedst() {
		if (associationStateStatedst == null) {
			associationStateStatedst = new EObjectWithInverseResolvingEList.ManyInverse<AssociationStateState>(AssociationStateState.class, this, FsmPackage.STATE__ASSOCIATION_STATE_STATEDST, FsmPackage.ASSOCIATION_STATE_STATE__DST_TRANSITION);
		}
		return associationStateStatedst;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AssociationStateState> getAssociationStateStatesrc() {
		if (associationStateStatesrc == null) {
			associationStateStatesrc = new EObjectWithInverseResolvingEList.ManyInverse<AssociationStateState>(AssociationStateState.class, this, FsmPackage.STATE__ASSOCIATION_STATE_STATESRC, FsmPackage.ASSOCIATION_STATE_STATE__SRC_TRANSITION);
		}
		return associationStateStatesrc;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case FsmPackage.STATE__STATE_MACHINE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetStateMachine((StateMachine)otherEnd, msgs);
			case FsmPackage.STATE__ASSOCIATION_STATE_STATEDST:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getAssociationStateStatedst()).basicAdd(otherEnd, msgs);
			case FsmPackage.STATE__ASSOCIATION_STATE_STATESRC:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getAssociationStateStatesrc()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case FsmPackage.STATE__STATE_MACHINE:
				return basicSetStateMachine(null, msgs);
			case FsmPackage.STATE__ASSOCIATION_STATE_STATEDST:
				return ((InternalEList<?>)getAssociationStateStatedst()).basicRemove(otherEnd, msgs);
			case FsmPackage.STATE__ASSOCIATION_STATE_STATESRC:
				return ((InternalEList<?>)getAssociationStateStatesrc()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case FsmPackage.STATE__STATE_MACHINE:
				return eInternalContainer().eInverseRemove(this, FsmPackage.STATE_MACHINE__STATE, StateMachine.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case FsmPackage.STATE__STATE_MACHINE:
				return getStateMachine();
			case FsmPackage.STATE__ASSOCIATION_STATE_STATEDST:
				return getAssociationStateStatedst();
			case FsmPackage.STATE__ASSOCIATION_STATE_STATESRC:
				return getAssociationStateStatesrc();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case FsmPackage.STATE__STATE_MACHINE:
				setStateMachine((StateMachine)newValue);
				return;
			case FsmPackage.STATE__ASSOCIATION_STATE_STATEDST:
				getAssociationStateStatedst().clear();
				getAssociationStateStatedst().addAll((Collection<? extends AssociationStateState>)newValue);
				return;
			case FsmPackage.STATE__ASSOCIATION_STATE_STATESRC:
				getAssociationStateStatesrc().clear();
				getAssociationStateStatesrc().addAll((Collection<? extends AssociationStateState>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case FsmPackage.STATE__STATE_MACHINE:
				setStateMachine((StateMachine)null);
				return;
			case FsmPackage.STATE__ASSOCIATION_STATE_STATEDST:
				getAssociationStateStatedst().clear();
				return;
			case FsmPackage.STATE__ASSOCIATION_STATE_STATESRC:
				getAssociationStateStatesrc().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case FsmPackage.STATE__STATE_MACHINE:
				return getStateMachine() != null;
			case FsmPackage.STATE__ASSOCIATION_STATE_STATEDST:
				return associationStateStatedst != null && !associationStateStatedst.isEmpty();
			case FsmPackage.STATE__ASSOCIATION_STATE_STATESRC:
				return associationStateStatesrc != null && !associationStateStatesrc.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //StateImpl
