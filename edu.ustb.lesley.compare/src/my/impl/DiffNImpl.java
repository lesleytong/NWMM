/**
 */
package my.impl;

import my.ConflictN;
import my.DiffN;
import my.MatchN;
import my.MyPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.compare.DifferenceKind;
import org.eclipse.emf.compare.DifferenceState;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Diff N</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link my.impl.DiffNImpl#getConflict <em>Conflict</em>}</li>
 *   <li>{@link my.impl.DiffNImpl#getMatch <em>Match</em>}</li>
 *   <li>{@link my.impl.DiffNImpl#getKind <em>Kind</em>}</li>
 *   <li>{@link my.impl.DiffNImpl#getState <em>State</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DiffNImpl extends MinimalEObjectImpl.Container implements DiffN {
	/**
	 * The cached value of the '{@link #getConflict() <em>Conflict</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConflict()
	 * @generated
	 * @ordered
	 */
	protected ConflictN conflict;

	/**
	 * The default value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected static final DifferenceKind KIND_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected DifferenceKind kind = KIND_EDEFAULT;
	/**
	 * The default value of the '{@link #getState() <em>State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getState()
	 * @generated
	 * @ordered
	 */
	protected static final DifferenceState STATE_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getState() <em>State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getState()
	 * @generated
	 * @ordered
	 */
	protected DifferenceState state = STATE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DiffNImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MyPackage.Literals.DIFF_N;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MatchN getMatch() {
		if (eContainerFeatureID() != MyPackage.DIFF_N__MATCH) return null;
		return (MatchN)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMatch(MatchN newMatch, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newMatch, MyPackage.DIFF_N__MATCH, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMatch(MatchN newMatch) {
		if (newMatch != eInternalContainer() || (eContainerFeatureID() != MyPackage.DIFF_N__MATCH && newMatch != null)) {
			if (EcoreUtil.isAncestor(this, newMatch))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newMatch != null)
				msgs = ((InternalEObject)newMatch).eInverseAdd(this, MyPackage.MATCH_N__DIFFERENCES, MatchN.class, msgs);
			msgs = basicSetMatch(newMatch, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MyPackage.DIFF_N__MATCH, newMatch, newMatch));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DifferenceKind getKind() {
		return kind;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setKind(DifferenceKind newKind) {
		DifferenceKind oldKind = kind;
		kind = newKind;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MyPackage.DIFF_N__KIND, oldKind, kind));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DifferenceState getState() {
		return state;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setState(DifferenceState newState) {
		DifferenceState oldState = state;
		state = newState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MyPackage.DIFF_N__STATE, oldState, state));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MyPackage.DIFF_N__CONFLICT:
				if (conflict != null)
					msgs = ((InternalEObject)conflict).eInverseRemove(this, MyPackage.CONFLICT_N__DIFFERENCES, ConflictN.class, msgs);
				return basicSetConflict((ConflictN)otherEnd, msgs);
			case MyPackage.DIFF_N__MATCH:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetMatch((MatchN)otherEnd, msgs);
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
			case MyPackage.DIFF_N__CONFLICT:
				return basicSetConflict(null, msgs);
			case MyPackage.DIFF_N__MATCH:
				return basicSetMatch(null, msgs);
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
			case MyPackage.DIFF_N__MATCH:
				return eInternalContainer().eInverseRemove(this, MyPackage.MATCH_N__DIFFERENCES, MatchN.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ConflictN getConflict() {
		if (conflict != null && conflict.eIsProxy()) {
			InternalEObject oldConflict = (InternalEObject)conflict;
			conflict = (ConflictN)eResolveProxy(oldConflict);
			if (conflict != oldConflict) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MyPackage.DIFF_N__CONFLICT, oldConflict, conflict));
			}
		}
		return conflict;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConflictN basicGetConflict() {
		return conflict;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConflict(ConflictN newConflict, NotificationChain msgs) {
		ConflictN oldConflict = conflict;
		conflict = newConflict;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MyPackage.DIFF_N__CONFLICT, oldConflict, newConflict);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setConflict(ConflictN newConflict) {
		if (newConflict != conflict) {
			NotificationChain msgs = null;
			if (conflict != null)
				msgs = ((InternalEObject)conflict).eInverseRemove(this, MyPackage.CONFLICT_N__DIFFERENCES, ConflictN.class, msgs);
			if (newConflict != null)
				msgs = ((InternalEObject)newConflict).eInverseAdd(this, MyPackage.CONFLICT_N__DIFFERENCES, ConflictN.class, msgs);
			msgs = basicSetConflict(newConflict, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MyPackage.DIFF_N__CONFLICT, newConflict, newConflict));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MyPackage.DIFF_N__CONFLICT:
				if (resolve) return getConflict();
				return basicGetConflict();
			case MyPackage.DIFF_N__MATCH:
				return getMatch();
			case MyPackage.DIFF_N__KIND:
				return getKind();
			case MyPackage.DIFF_N__STATE:
				return getState();
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
			case MyPackage.DIFF_N__CONFLICT:
				setConflict((ConflictN)newValue);
				return;
			case MyPackage.DIFF_N__MATCH:
				setMatch((MatchN)newValue);
				return;
			case MyPackage.DIFF_N__KIND:
				setKind((DifferenceKind)newValue);
				return;
			case MyPackage.DIFF_N__STATE:
				setState((DifferenceState)newValue);
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
			case MyPackage.DIFF_N__CONFLICT:
				setConflict((ConflictN)null);
				return;
			case MyPackage.DIFF_N__MATCH:
				setMatch((MatchN)null);
				return;
			case MyPackage.DIFF_N__KIND:
				setKind(KIND_EDEFAULT);
				return;
			case MyPackage.DIFF_N__STATE:
				setState(STATE_EDEFAULT);
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
			case MyPackage.DIFF_N__CONFLICT:
				return conflict != null;
			case MyPackage.DIFF_N__MATCH:
				return getMatch() != null;
			case MyPackage.DIFF_N__KIND:
				return KIND_EDEFAULT == null ? kind != null : !KIND_EDEFAULT.equals(kind);
			case MyPackage.DIFF_N__STATE:
				return STATE_EDEFAULT == null ? state != null : !STATE_EDEFAULT.equals(state);
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
		result.append(" (kind: ");
		result.append(kind);
		result.append(", state: ");
		result.append(state);
		result.append(')');
		return result.toString();
	}

} //DiffNImpl
