/**
 */
package amble.impl;

import amble.Action;
import amble.AmblePackage;
import amble.Network;
import amble.State;
import amble.Transition;
import amble.Variable;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Process</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link amble.impl.ProcessImpl#getMinId <em>Min Id</em>}</li>
 *   <li>{@link amble.impl.ProcessImpl#getMaxId <em>Max Id</em>}</li>
 *   <li>{@link amble.impl.ProcessImpl#getInstancesNb <em>Instances Nb</em>}</li>
 *   <li>{@link amble.impl.ProcessImpl#getStates <em>States</em>}</li>
 *   <li>{@link amble.impl.ProcessImpl#getTransitions <em>Transitions</em>}</li>
 *   <li>{@link amble.impl.ProcessImpl#getInitial <em>Initial</em>}</li>
 *   <li>{@link amble.impl.ProcessImpl#getVariables <em>Variables</em>}</li>
 *   <li>{@link amble.impl.ProcessImpl#getConnectedTo <em>Connected To</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProcessImpl extends ElementImpl implements amble.Process {
	/**
	 * The default value of the '{@link #getMinId() <em>Min Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMinId()
	 * @generated
	 * @ordered
	 */
	protected static final int MIN_ID_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getMinId() <em>Min Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMinId()
	 * @generated
	 * @ordered
	 */
	protected int minId = MIN_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getMaxId() <em>Max Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxId()
	 * @generated
	 * @ordered
	 */
	protected static final int MAX_ID_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getMaxId() <em>Max Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxId()
	 * @generated
	 * @ordered
	 */
	protected int maxId = MAX_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getInstancesNb() <em>Instances Nb</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInstancesNb()
	 * @generated
	 * @ordered
	 */
	protected static final int INSTANCES_NB_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getInstancesNb() <em>Instances Nb</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInstancesNb()
	 * @generated
	 * @ordered
	 */
	protected int instancesNb = INSTANCES_NB_EDEFAULT;

	/**
	 * The cached value of the '{@link #getStates() <em>States</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStates()
	 * @generated
	 * @ordered
	 */
	protected EList<State> states;

	/**
	 * The cached value of the '{@link #getTransitions() <em>Transitions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransitions()
	 * @generated
	 * @ordered
	 */
	protected EList<Transition> transitions;

	/**
	 * The cached value of the '{@link #getInitial() <em>Initial</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitial()
	 * @generated
	 * @ordered
	 */
	protected Action initial;

	/**
	 * The cached value of the '{@link #getVariables() <em>Variables</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariables()
	 * @generated
	 * @ordered
	 */
	protected EList<Variable> variables;

	/**
	 * The cached value of the '{@link #getConnectedTo() <em>Connected To</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConnectedTo()
	 * @generated
	 * @ordered
	 */
	protected EList<Network> connectedTo;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProcessImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AmblePackage.Literals.PROCESS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getMinId() {
		return minId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMinId(int newMinId) {
		int oldMinId = minId;
		minId = newMinId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AmblePackage.PROCESS__MIN_ID, oldMinId, minId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getMaxId() {
		return maxId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxId(int newMaxId) {
		int oldMaxId = maxId;
		maxId = newMaxId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AmblePackage.PROCESS__MAX_ID, oldMaxId, maxId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getInstancesNb() {
		return instancesNb;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInstancesNb(int newInstancesNb) {
		int oldInstancesNb = instancesNb;
		instancesNb = newInstancesNb;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AmblePackage.PROCESS__INSTANCES_NB, oldInstancesNb, instancesNb));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<State> getStates() {
		if (states == null) {
			states = new EObjectContainmentEList<State>(State.class, this, AmblePackage.PROCESS__STATES);
		}
		return states;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Transition> getTransitions() {
		if (transitions == null) {
			transitions = new EObjectContainmentEList<Transition>(Transition.class, this, AmblePackage.PROCESS__TRANSITIONS);
		}
		return transitions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Action getInitial() {
		return initial;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInitial(Action newInitial, NotificationChain msgs) {
		Action oldInitial = initial;
		initial = newInitial;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AmblePackage.PROCESS__INITIAL, oldInitial, newInitial);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInitial(Action newInitial) {
		if (newInitial != initial) {
			NotificationChain msgs = null;
			if (initial != null)
				msgs = ((InternalEObject)initial).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AmblePackage.PROCESS__INITIAL, null, msgs);
			if (newInitial != null)
				msgs = ((InternalEObject)newInitial).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AmblePackage.PROCESS__INITIAL, null, msgs);
			msgs = basicSetInitial(newInitial, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AmblePackage.PROCESS__INITIAL, newInitial, newInitial));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Variable> getVariables() {
		if (variables == null) {
			variables = new EObjectContainmentEList<Variable>(Variable.class, this, AmblePackage.PROCESS__VARIABLES);
		}
		return variables;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Network> getConnectedTo() {
		if (connectedTo == null) {
			connectedTo = new EObjectResolvingEList<Network>(Network.class, this, AmblePackage.PROCESS__CONNECTED_TO);
		}
		return connectedTo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AmblePackage.PROCESS__STATES:
				return ((InternalEList<?>)getStates()).basicRemove(otherEnd, msgs);
			case AmblePackage.PROCESS__TRANSITIONS:
				return ((InternalEList<?>)getTransitions()).basicRemove(otherEnd, msgs);
			case AmblePackage.PROCESS__INITIAL:
				return basicSetInitial(null, msgs);
			case AmblePackage.PROCESS__VARIABLES:
				return ((InternalEList<?>)getVariables()).basicRemove(otherEnd, msgs);
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
			case AmblePackage.PROCESS__MIN_ID:
				return getMinId();
			case AmblePackage.PROCESS__MAX_ID:
				return getMaxId();
			case AmblePackage.PROCESS__INSTANCES_NB:
				return getInstancesNb();
			case AmblePackage.PROCESS__STATES:
				return getStates();
			case AmblePackage.PROCESS__TRANSITIONS:
				return getTransitions();
			case AmblePackage.PROCESS__INITIAL:
				return getInitial();
			case AmblePackage.PROCESS__VARIABLES:
				return getVariables();
			case AmblePackage.PROCESS__CONNECTED_TO:
				return getConnectedTo();
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
			case AmblePackage.PROCESS__MIN_ID:
				setMinId((Integer)newValue);
				return;
			case AmblePackage.PROCESS__MAX_ID:
				setMaxId((Integer)newValue);
				return;
			case AmblePackage.PROCESS__INSTANCES_NB:
				setInstancesNb((Integer)newValue);
				return;
			case AmblePackage.PROCESS__STATES:
				getStates().clear();
				getStates().addAll((Collection<? extends State>)newValue);
				return;
			case AmblePackage.PROCESS__TRANSITIONS:
				getTransitions().clear();
				getTransitions().addAll((Collection<? extends Transition>)newValue);
				return;
			case AmblePackage.PROCESS__INITIAL:
				setInitial((Action)newValue);
				return;
			case AmblePackage.PROCESS__VARIABLES:
				getVariables().clear();
				getVariables().addAll((Collection<? extends Variable>)newValue);
				return;
			case AmblePackage.PROCESS__CONNECTED_TO:
				getConnectedTo().clear();
				getConnectedTo().addAll((Collection<? extends Network>)newValue);
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
			case AmblePackage.PROCESS__MIN_ID:
				setMinId(MIN_ID_EDEFAULT);
				return;
			case AmblePackage.PROCESS__MAX_ID:
				setMaxId(MAX_ID_EDEFAULT);
				return;
			case AmblePackage.PROCESS__INSTANCES_NB:
				setInstancesNb(INSTANCES_NB_EDEFAULT);
				return;
			case AmblePackage.PROCESS__STATES:
				getStates().clear();
				return;
			case AmblePackage.PROCESS__TRANSITIONS:
				getTransitions().clear();
				return;
			case AmblePackage.PROCESS__INITIAL:
				setInitial((Action)null);
				return;
			case AmblePackage.PROCESS__VARIABLES:
				getVariables().clear();
				return;
			case AmblePackage.PROCESS__CONNECTED_TO:
				getConnectedTo().clear();
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
			case AmblePackage.PROCESS__MIN_ID:
				return minId != MIN_ID_EDEFAULT;
			case AmblePackage.PROCESS__MAX_ID:
				return maxId != MAX_ID_EDEFAULT;
			case AmblePackage.PROCESS__INSTANCES_NB:
				return instancesNb != INSTANCES_NB_EDEFAULT;
			case AmblePackage.PROCESS__STATES:
				return states != null && !states.isEmpty();
			case AmblePackage.PROCESS__TRANSITIONS:
				return transitions != null && !transitions.isEmpty();
			case AmblePackage.PROCESS__INITIAL:
				return initial != null;
			case AmblePackage.PROCESS__VARIABLES:
				return variables != null && !variables.isEmpty();
			case AmblePackage.PROCESS__CONNECTED_TO:
				return connectedTo != null && !connectedTo.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (minId: ");
		result.append(minId);
		result.append(", maxId: ");
		result.append(maxId);
		result.append(", instancesNb: ");
		result.append(instancesNb);
		result.append(')');
		return result.toString();
	}

} //ProcessImpl
