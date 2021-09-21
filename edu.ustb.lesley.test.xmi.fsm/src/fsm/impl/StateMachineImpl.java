/**
 */
package fsm.impl;

import fsm.FsmPackage;
import fsm.RootFolder;
import fsm.State;
import fsm.StateMachine;
import fsm.Transition;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State Machine</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link fsm.impl.StateMachineImpl#getRootFolder <em>Root Folder</em>}</li>
 *   <li>{@link fsm.impl.StateMachineImpl#getState <em>State</em>}</li>
 *   <li>{@link fsm.impl.StateMachineImpl#getTransition <em>Transition</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StateMachineImpl extends MgaObjectImpl implements StateMachine {
	/**
	 * The cached value of the '{@link #getState() <em>State</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getState()
	 * @generated
	 * @ordered
	 */
	protected EList<State> state;

	/**
	 * The cached value of the '{@link #getTransition() <em>Transition</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransition()
	 * @generated
	 * @ordered
	 */
	protected EList<Transition> transition;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StateMachineImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FsmPackage.Literals.STATE_MACHINE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RootFolder getRootFolder() {
		if (eContainerFeatureID() != FsmPackage.STATE_MACHINE__ROOT_FOLDER) return null;
		return (RootFolder)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRootFolder(RootFolder newRootFolder, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newRootFolder, FsmPackage.STATE_MACHINE__ROOT_FOLDER, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRootFolder(RootFolder newRootFolder) {
		if (newRootFolder != eInternalContainer() || (eContainerFeatureID() != FsmPackage.STATE_MACHINE__ROOT_FOLDER && newRootFolder != null)) {
			if (EcoreUtil.isAncestor(this, newRootFolder))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newRootFolder != null)
				msgs = ((InternalEObject)newRootFolder).eInverseAdd(this, FsmPackage.ROOT_FOLDER__STATE_MACHINE, RootFolder.class, msgs);
			msgs = basicSetRootFolder(newRootFolder, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FsmPackage.STATE_MACHINE__ROOT_FOLDER, newRootFolder, newRootFolder));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<State> getState() {
		if (state == null) {
			state = new EObjectContainmentWithInverseEList<State>(State.class, this, FsmPackage.STATE_MACHINE__STATE, FsmPackage.STATE__STATE_MACHINE);
		}
		return state;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Transition> getTransition() {
		if (transition == null) {
			transition = new EObjectContainmentWithInverseEList<Transition>(Transition.class, this, FsmPackage.STATE_MACHINE__TRANSITION, FsmPackage.TRANSITION__STATE_MACHINE);
		}
		return transition;
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
			case FsmPackage.STATE_MACHINE__ROOT_FOLDER:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetRootFolder((RootFolder)otherEnd, msgs);
			case FsmPackage.STATE_MACHINE__STATE:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getState()).basicAdd(otherEnd, msgs);
			case FsmPackage.STATE_MACHINE__TRANSITION:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getTransition()).basicAdd(otherEnd, msgs);
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
			case FsmPackage.STATE_MACHINE__ROOT_FOLDER:
				return basicSetRootFolder(null, msgs);
			case FsmPackage.STATE_MACHINE__STATE:
				return ((InternalEList<?>)getState()).basicRemove(otherEnd, msgs);
			case FsmPackage.STATE_MACHINE__TRANSITION:
				return ((InternalEList<?>)getTransition()).basicRemove(otherEnd, msgs);
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
			case FsmPackage.STATE_MACHINE__ROOT_FOLDER:
				return eInternalContainer().eInverseRemove(this, FsmPackage.ROOT_FOLDER__STATE_MACHINE, RootFolder.class, msgs);
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
			case FsmPackage.STATE_MACHINE__ROOT_FOLDER:
				return getRootFolder();
			case FsmPackage.STATE_MACHINE__STATE:
				return getState();
			case FsmPackage.STATE_MACHINE__TRANSITION:
				return getTransition();
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
			case FsmPackage.STATE_MACHINE__ROOT_FOLDER:
				setRootFolder((RootFolder)newValue);
				return;
			case FsmPackage.STATE_MACHINE__STATE:
				getState().clear();
				getState().addAll((Collection<? extends State>)newValue);
				return;
			case FsmPackage.STATE_MACHINE__TRANSITION:
				getTransition().clear();
				getTransition().addAll((Collection<? extends Transition>)newValue);
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
			case FsmPackage.STATE_MACHINE__ROOT_FOLDER:
				setRootFolder((RootFolder)null);
				return;
			case FsmPackage.STATE_MACHINE__STATE:
				getState().clear();
				return;
			case FsmPackage.STATE_MACHINE__TRANSITION:
				getTransition().clear();
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
			case FsmPackage.STATE_MACHINE__ROOT_FOLDER:
				return getRootFolder() != null;
			case FsmPackage.STATE_MACHINE__STATE:
				return state != null && !state.isEmpty();
			case FsmPackage.STATE_MACHINE__TRANSITION:
				return transition != null && !transition.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //StateMachineImpl
