/**
 */
package fsm.impl;

import fsm.AssociationStateState;
import fsm.FsmPackage;
import fsm.State;
import fsm.Transition;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Association State State</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link fsm.impl.AssociationStateStateImpl#getTransition <em>Transition</em>}</li>
 *   <li>{@link fsm.impl.AssociationStateStateImpl#getDstTransition <em>Dst Transition</em>}</li>
 *   <li>{@link fsm.impl.AssociationStateStateImpl#getSrcTransition <em>Src Transition</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AssociationStateStateImpl extends MinimalEObjectImpl.Container implements AssociationStateState {
	/**
	 * The cached value of the '{@link #getTransition() <em>Transition</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransition()
	 * @generated
	 * @ordered
	 */
	protected Transition transition;

	/**
	 * The cached value of the '{@link #getDstTransition() <em>Dst Transition</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDstTransition()
	 * @generated
	 * @ordered
	 */
	protected EList<State> dstTransition;

	/**
	 * The cached value of the '{@link #getSrcTransition() <em>Src Transition</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSrcTransition()
	 * @generated
	 * @ordered
	 */
	protected EList<State> srcTransition;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AssociationStateStateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FsmPackage.Literals.ASSOCIATION_STATE_STATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Transition getTransition() {
		if (transition != null && transition.eIsProxy()) {
			InternalEObject oldTransition = (InternalEObject)transition;
			transition = (Transition)eResolveProxy(oldTransition);
			if (transition != oldTransition) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FsmPackage.ASSOCIATION_STATE_STATE__TRANSITION, oldTransition, transition));
			}
		}
		return transition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Transition basicGetTransition() {
		return transition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTransition(Transition newTransition, NotificationChain msgs) {
		Transition oldTransition = transition;
		transition = newTransition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FsmPackage.ASSOCIATION_STATE_STATE__TRANSITION, oldTransition, newTransition);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTransition(Transition newTransition) {
		if (newTransition != transition) {
			NotificationChain msgs = null;
			if (transition != null)
				msgs = ((InternalEObject)transition).eInverseRemove(this, FsmPackage.TRANSITION__ASSOCIATION_STATE_STATE, Transition.class, msgs);
			if (newTransition != null)
				msgs = ((InternalEObject)newTransition).eInverseAdd(this, FsmPackage.TRANSITION__ASSOCIATION_STATE_STATE, Transition.class, msgs);
			msgs = basicSetTransition(newTransition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FsmPackage.ASSOCIATION_STATE_STATE__TRANSITION, newTransition, newTransition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<State> getDstTransition() {
		if (dstTransition == null) {
			dstTransition = new EObjectWithInverseResolvingEList.ManyInverse<State>(State.class, this, FsmPackage.ASSOCIATION_STATE_STATE__DST_TRANSITION, FsmPackage.STATE__ASSOCIATION_STATE_STATEDST);
		}
		return dstTransition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<State> getSrcTransition() {
		if (srcTransition == null) {
			srcTransition = new EObjectWithInverseResolvingEList.ManyInverse<State>(State.class, this, FsmPackage.ASSOCIATION_STATE_STATE__SRC_TRANSITION, FsmPackage.STATE__ASSOCIATION_STATE_STATESRC);
		}
		return srcTransition;
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
			case FsmPackage.ASSOCIATION_STATE_STATE__TRANSITION:
				if (transition != null)
					msgs = ((InternalEObject)transition).eInverseRemove(this, FsmPackage.TRANSITION__ASSOCIATION_STATE_STATE, Transition.class, msgs);
				return basicSetTransition((Transition)otherEnd, msgs);
			case FsmPackage.ASSOCIATION_STATE_STATE__DST_TRANSITION:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getDstTransition()).basicAdd(otherEnd, msgs);
			case FsmPackage.ASSOCIATION_STATE_STATE__SRC_TRANSITION:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getSrcTransition()).basicAdd(otherEnd, msgs);
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
			case FsmPackage.ASSOCIATION_STATE_STATE__TRANSITION:
				return basicSetTransition(null, msgs);
			case FsmPackage.ASSOCIATION_STATE_STATE__DST_TRANSITION:
				return ((InternalEList<?>)getDstTransition()).basicRemove(otherEnd, msgs);
			case FsmPackage.ASSOCIATION_STATE_STATE__SRC_TRANSITION:
				return ((InternalEList<?>)getSrcTransition()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case FsmPackage.ASSOCIATION_STATE_STATE__TRANSITION:
				if (resolve) return getTransition();
				return basicGetTransition();
			case FsmPackage.ASSOCIATION_STATE_STATE__DST_TRANSITION:
				return getDstTransition();
			case FsmPackage.ASSOCIATION_STATE_STATE__SRC_TRANSITION:
				return getSrcTransition();
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
			case FsmPackage.ASSOCIATION_STATE_STATE__TRANSITION:
				setTransition((Transition)newValue);
				return;
			case FsmPackage.ASSOCIATION_STATE_STATE__DST_TRANSITION:
				getDstTransition().clear();
				getDstTransition().addAll((Collection<? extends State>)newValue);
				return;
			case FsmPackage.ASSOCIATION_STATE_STATE__SRC_TRANSITION:
				getSrcTransition().clear();
				getSrcTransition().addAll((Collection<? extends State>)newValue);
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
			case FsmPackage.ASSOCIATION_STATE_STATE__TRANSITION:
				setTransition((Transition)null);
				return;
			case FsmPackage.ASSOCIATION_STATE_STATE__DST_TRANSITION:
				getDstTransition().clear();
				return;
			case FsmPackage.ASSOCIATION_STATE_STATE__SRC_TRANSITION:
				getSrcTransition().clear();
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
			case FsmPackage.ASSOCIATION_STATE_STATE__TRANSITION:
				return transition != null;
			case FsmPackage.ASSOCIATION_STATE_STATE__DST_TRANSITION:
				return dstTransition != null && !dstTransition.isEmpty();
			case FsmPackage.ASSOCIATION_STATE_STATE__SRC_TRANSITION:
				return srcTransition != null && !srcTransition.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //AssociationStateStateImpl
