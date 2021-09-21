/**
 */
package fsm.impl;

import fsm.AssociationStateState;
import fsm.FsmPackage;
import fsm.StateMachine;
import fsm.Transition;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Transition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link fsm.impl.TransitionImpl#getStateMachine <em>State Machine</em>}</li>
 *   <li>{@link fsm.impl.TransitionImpl#getAssociationStateState <em>Association State State</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TransitionImpl extends MgaObjectImpl implements Transition {
	/**
	 * The cached value of the '{@link #getAssociationStateState() <em>Association State State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAssociationStateState()
	 * @generated
	 * @ordered
	 */
	protected AssociationStateState associationStateState;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TransitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FsmPackage.Literals.TRANSITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateMachine getStateMachine() {
		if (eContainerFeatureID() != FsmPackage.TRANSITION__STATE_MACHINE) return null;
		return (StateMachine)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStateMachine(StateMachine newStateMachine, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newStateMachine, FsmPackage.TRANSITION__STATE_MACHINE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStateMachine(StateMachine newStateMachine) {
		if (newStateMachine != eInternalContainer() || (eContainerFeatureID() != FsmPackage.TRANSITION__STATE_MACHINE && newStateMachine != null)) {
			if (EcoreUtil.isAncestor(this, newStateMachine))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newStateMachine != null)
				msgs = ((InternalEObject)newStateMachine).eInverseAdd(this, FsmPackage.STATE_MACHINE__TRANSITION, StateMachine.class, msgs);
			msgs = basicSetStateMachine(newStateMachine, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FsmPackage.TRANSITION__STATE_MACHINE, newStateMachine, newStateMachine));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AssociationStateState getAssociationStateState() {
		if (associationStateState != null && associationStateState.eIsProxy()) {
			InternalEObject oldAssociationStateState = (InternalEObject)associationStateState;
			associationStateState = (AssociationStateState)eResolveProxy(oldAssociationStateState);
			if (associationStateState != oldAssociationStateState) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FsmPackage.TRANSITION__ASSOCIATION_STATE_STATE, oldAssociationStateState, associationStateState));
			}
		}
		return associationStateState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AssociationStateState basicGetAssociationStateState() {
		return associationStateState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAssociationStateState(AssociationStateState newAssociationStateState, NotificationChain msgs) {
		AssociationStateState oldAssociationStateState = associationStateState;
		associationStateState = newAssociationStateState;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FsmPackage.TRANSITION__ASSOCIATION_STATE_STATE, oldAssociationStateState, newAssociationStateState);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAssociationStateState(AssociationStateState newAssociationStateState) {
		if (newAssociationStateState != associationStateState) {
			NotificationChain msgs = null;
			if (associationStateState != null)
				msgs = ((InternalEObject)associationStateState).eInverseRemove(this, FsmPackage.ASSOCIATION_STATE_STATE__TRANSITION, AssociationStateState.class, msgs);
			if (newAssociationStateState != null)
				msgs = ((InternalEObject)newAssociationStateState).eInverseAdd(this, FsmPackage.ASSOCIATION_STATE_STATE__TRANSITION, AssociationStateState.class, msgs);
			msgs = basicSetAssociationStateState(newAssociationStateState, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FsmPackage.TRANSITION__ASSOCIATION_STATE_STATE, newAssociationStateState, newAssociationStateState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case FsmPackage.TRANSITION__STATE_MACHINE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetStateMachine((StateMachine)otherEnd, msgs);
			case FsmPackage.TRANSITION__ASSOCIATION_STATE_STATE:
				if (associationStateState != null)
					msgs = ((InternalEObject)associationStateState).eInverseRemove(this, FsmPackage.ASSOCIATION_STATE_STATE__TRANSITION, AssociationStateState.class, msgs);
				return basicSetAssociationStateState((AssociationStateState)otherEnd, msgs);
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
			case FsmPackage.TRANSITION__STATE_MACHINE:
				return basicSetStateMachine(null, msgs);
			case FsmPackage.TRANSITION__ASSOCIATION_STATE_STATE:
				return basicSetAssociationStateState(null, msgs);
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
			case FsmPackage.TRANSITION__STATE_MACHINE:
				return eInternalContainer().eInverseRemove(this, FsmPackage.STATE_MACHINE__TRANSITION, StateMachine.class, msgs);
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
			case FsmPackage.TRANSITION__STATE_MACHINE:
				return getStateMachine();
			case FsmPackage.TRANSITION__ASSOCIATION_STATE_STATE:
				if (resolve) return getAssociationStateState();
				return basicGetAssociationStateState();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case FsmPackage.TRANSITION__STATE_MACHINE:
				setStateMachine((StateMachine)newValue);
				return;
			case FsmPackage.TRANSITION__ASSOCIATION_STATE_STATE:
				setAssociationStateState((AssociationStateState)newValue);
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
			case FsmPackage.TRANSITION__STATE_MACHINE:
				setStateMachine((StateMachine)null);
				return;
			case FsmPackage.TRANSITION__ASSOCIATION_STATE_STATE:
				setAssociationStateState((AssociationStateState)null);
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
			case FsmPackage.TRANSITION__STATE_MACHINE:
				return getStateMachine() != null;
			case FsmPackage.TRANSITION__ASSOCIATION_STATE_STATE:
				return associationStateState != null;
		}
		return super.eIsSet(featureID);
	}

} //TransitionImpl
