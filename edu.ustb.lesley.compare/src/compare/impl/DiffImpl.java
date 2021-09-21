/**
 */
package compare.impl;

import compare.ComparePackage;
import compare.Conflict;
import compare.Diff;
import compare.DifferenceKind;
import compare.DifferenceSource;
import compare.DifferenceState;
import compare.Equivalence;
import compare.Match;

import java.lang.reflect.InvocationTargetException;

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
 * An implementation of the model object '<em><b>Diff</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link compare.impl.DiffImpl#getMatch <em>Match</em>}</li>
 *   <li>{@link compare.impl.DiffImpl#getRequires <em>Requires</em>}</li>
 *   <li>{@link compare.impl.DiffImpl#getRequiredBy <em>Required By</em>}</li>
 *   <li>{@link compare.impl.DiffImpl#getImplies <em>Implies</em>}</li>
 *   <li>{@link compare.impl.DiffImpl#getImpliedBy <em>Implied By</em>}</li>
 *   <li>{@link compare.impl.DiffImpl#getRefines <em>Refines</em>}</li>
 *   <li>{@link compare.impl.DiffImpl#getRefinedBy <em>Refined By</em>}</li>
 *   <li>{@link compare.impl.DiffImpl#getPrimeRefining <em>Prime Refining</em>}</li>
 *   <li>{@link compare.impl.DiffImpl#getKind <em>Kind</em>}</li>
 *   <li>{@link compare.impl.DiffImpl#getSource <em>Source</em>}</li>
 *   <li>{@link compare.impl.DiffImpl#getState <em>State</em>}</li>
 *   <li>{@link compare.impl.DiffImpl#getEquivalence <em>Equivalence</em>}</li>
 *   <li>{@link compare.impl.DiffImpl#getConflict <em>Conflict</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DiffImpl extends MinimalEObjectImpl.Container implements Diff {
	/**
	 * The cached value of the '{@link #getRequires() <em>Requires</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRequires()
	 * @generated
	 * @ordered
	 */
	protected EList<Diff> requires;

	/**
	 * The cached value of the '{@link #getRequiredBy() <em>Required By</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRequiredBy()
	 * @generated
	 * @ordered
	 */
	protected EList<Diff> requiredBy;

	/**
	 * The cached value of the '{@link #getImplies() <em>Implies</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplies()
	 * @generated
	 * @ordered
	 */
	protected EList<Diff> implies;

	/**
	 * The cached value of the '{@link #getImpliedBy() <em>Implied By</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImpliedBy()
	 * @generated
	 * @ordered
	 */
	protected EList<Diff> impliedBy;

	/**
	 * The cached value of the '{@link #getRefines() <em>Refines</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRefines()
	 * @generated
	 * @ordered
	 */
	protected EList<Diff> refines;

	/**
	 * The cached value of the '{@link #getRefinedBy() <em>Refined By</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRefinedBy()
	 * @generated
	 * @ordered
	 */
	protected EList<Diff> refinedBy;

	/**
	 * The cached value of the '{@link #getPrimeRefining() <em>Prime Refining</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrimeRefining()
	 * @generated
	 * @ordered
	 */
	protected Diff primeRefining;

	/**
	 * The default value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected static final DifferenceKind KIND_EDEFAULT = DifferenceKind.ADD;

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
	 * The default value of the '{@link #getSource() <em>Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected static final DifferenceSource SOURCE_EDEFAULT = DifferenceSource.LEFT;

	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected DifferenceSource source = SOURCE_EDEFAULT;

	/**
	 * The default value of the '{@link #getState() <em>State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getState()
	 * @generated
	 * @ordered
	 */
	protected static final DifferenceState STATE_EDEFAULT = DifferenceState.UNRESOLVED;

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
	 * The cached value of the '{@link #getEquivalence() <em>Equivalence</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEquivalence()
	 * @generated
	 * @ordered
	 */
	protected Equivalence equivalence;

	/**
	 * The cached value of the '{@link #getConflict() <em>Conflict</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConflict()
	 * @generated
	 * @ordered
	 */
	protected Conflict conflict;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DiffImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ComparePackage.Literals.DIFF;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Match getMatch() {
		Match match = basicGetMatch();
		return match != null && match.eIsProxy() ? (Match)eResolveProxy((InternalEObject)match) : match;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Match basicGetMatch() {
		// TODO: implement this method to return the 'Match' reference
		// -> do not perform proxy resolution
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMatch(Match newMatch) {
		// TODO: implement this method to set the 'Match' reference
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Diff> getRequires() {
		if (requires == null) {
			requires = new EObjectWithInverseResolvingEList.ManyInverse<Diff>(Diff.class, this, ComparePackage.DIFF__REQUIRES, ComparePackage.DIFF__REQUIRED_BY);
		}
		return requires;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Diff> getRequiredBy() {
		if (requiredBy == null) {
			requiredBy = new EObjectWithInverseResolvingEList.ManyInverse<Diff>(Diff.class, this, ComparePackage.DIFF__REQUIRED_BY, ComparePackage.DIFF__REQUIRES);
		}
		return requiredBy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Diff> getImplies() {
		if (implies == null) {
			implies = new EObjectWithInverseResolvingEList.ManyInverse<Diff>(Diff.class, this, ComparePackage.DIFF__IMPLIES, ComparePackage.DIFF__IMPLIED_BY);
		}
		return implies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Diff> getImpliedBy() {
		if (impliedBy == null) {
			impliedBy = new EObjectWithInverseResolvingEList.ManyInverse<Diff>(Diff.class, this, ComparePackage.DIFF__IMPLIED_BY, ComparePackage.DIFF__IMPLIES);
		}
		return impliedBy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Diff> getRefines() {
		if (refines == null) {
			refines = new EObjectWithInverseResolvingEList.ManyInverse<Diff>(Diff.class, this, ComparePackage.DIFF__REFINES, ComparePackage.DIFF__REFINED_BY);
		}
		return refines;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Diff> getRefinedBy() {
		if (refinedBy == null) {
			refinedBy = new EObjectWithInverseResolvingEList.ManyInverse<Diff>(Diff.class, this, ComparePackage.DIFF__REFINED_BY, ComparePackage.DIFF__REFINES);
		}
		return refinedBy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Diff getPrimeRefining() {
		if (primeRefining != null && primeRefining.eIsProxy()) {
			InternalEObject oldPrimeRefining = (InternalEObject)primeRefining;
			primeRefining = (Diff)eResolveProxy(oldPrimeRefining);
			if (primeRefining != oldPrimeRefining) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ComparePackage.DIFF__PRIME_REFINING, oldPrimeRefining, primeRefining));
			}
		}
		return primeRefining;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Diff basicGetPrimeRefining() {
		return primeRefining;
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
		kind = newKind == null ? KIND_EDEFAULT : newKind;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComparePackage.DIFF__KIND, oldKind, kind));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DifferenceSource getSource() {
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSource(DifferenceSource newSource) {
		DifferenceSource oldSource = source;
		source = newSource == null ? SOURCE_EDEFAULT : newSource;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComparePackage.DIFF__SOURCE, oldSource, source));
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
		state = newState == null ? STATE_EDEFAULT : newState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComparePackage.DIFF__STATE, oldState, state));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Equivalence getEquivalence() {
		if (equivalence != null && equivalence.eIsProxy()) {
			InternalEObject oldEquivalence = (InternalEObject)equivalence;
			equivalence = (Equivalence)eResolveProxy(oldEquivalence);
			if (equivalence != oldEquivalence) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ComparePackage.DIFF__EQUIVALENCE, oldEquivalence, equivalence));
			}
		}
		return equivalence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Equivalence basicGetEquivalence() {
		return equivalence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEquivalence(Equivalence newEquivalence, NotificationChain msgs) {
		Equivalence oldEquivalence = equivalence;
		equivalence = newEquivalence;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ComparePackage.DIFF__EQUIVALENCE, oldEquivalence, newEquivalence);
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
	public void setEquivalence(Equivalence newEquivalence) {
		if (newEquivalence != equivalence) {
			NotificationChain msgs = null;
			if (equivalence != null)
				msgs = ((InternalEObject)equivalence).eInverseRemove(this, ComparePackage.EQUIVALENCE__DIFFERENCES, Equivalence.class, msgs);
			if (newEquivalence != null)
				msgs = ((InternalEObject)newEquivalence).eInverseAdd(this, ComparePackage.EQUIVALENCE__DIFFERENCES, Equivalence.class, msgs);
			msgs = basicSetEquivalence(newEquivalence, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComparePackage.DIFF__EQUIVALENCE, newEquivalence, newEquivalence));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Conflict getConflict() {
		if (conflict != null && conflict.eIsProxy()) {
			InternalEObject oldConflict = (InternalEObject)conflict;
			conflict = (Conflict)eResolveProxy(oldConflict);
			if (conflict != oldConflict) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ComparePackage.DIFF__CONFLICT, oldConflict, conflict));
			}
		}
		return conflict;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Conflict basicGetConflict() {
		return conflict;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConflict(Conflict newConflict, NotificationChain msgs) {
		Conflict oldConflict = conflict;
		conflict = newConflict;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ComparePackage.DIFF__CONFLICT, oldConflict, newConflict);
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
	public void setConflict(Conflict newConflict) {
		if (newConflict != conflict) {
			NotificationChain msgs = null;
			if (conflict != null)
				msgs = ((InternalEObject)conflict).eInverseRemove(this, ComparePackage.CONFLICT__DIFFERENCES, Conflict.class, msgs);
			if (newConflict != null)
				msgs = ((InternalEObject)newConflict).eInverseAdd(this, ComparePackage.CONFLICT__DIFFERENCES, Conflict.class, msgs);
			msgs = basicSetConflict(newConflict, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComparePackage.DIFF__CONFLICT, newConflict, newConflict));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void copyRightToLeft() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void copyLeftToRight() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void discard() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
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
			case ComparePackage.DIFF__REQUIRES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getRequires()).basicAdd(otherEnd, msgs);
			case ComparePackage.DIFF__REQUIRED_BY:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getRequiredBy()).basicAdd(otherEnd, msgs);
			case ComparePackage.DIFF__IMPLIES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getImplies()).basicAdd(otherEnd, msgs);
			case ComparePackage.DIFF__IMPLIED_BY:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getImpliedBy()).basicAdd(otherEnd, msgs);
			case ComparePackage.DIFF__REFINES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getRefines()).basicAdd(otherEnd, msgs);
			case ComparePackage.DIFF__REFINED_BY:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getRefinedBy()).basicAdd(otherEnd, msgs);
			case ComparePackage.DIFF__EQUIVALENCE:
				if (equivalence != null)
					msgs = ((InternalEObject)equivalence).eInverseRemove(this, ComparePackage.EQUIVALENCE__DIFFERENCES, Equivalence.class, msgs);
				return basicSetEquivalence((Equivalence)otherEnd, msgs);
			case ComparePackage.DIFF__CONFLICT:
				if (conflict != null)
					msgs = ((InternalEObject)conflict).eInverseRemove(this, ComparePackage.CONFLICT__DIFFERENCES, Conflict.class, msgs);
				return basicSetConflict((Conflict)otherEnd, msgs);
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
			case ComparePackage.DIFF__REQUIRES:
				return ((InternalEList<?>)getRequires()).basicRemove(otherEnd, msgs);
			case ComparePackage.DIFF__REQUIRED_BY:
				return ((InternalEList<?>)getRequiredBy()).basicRemove(otherEnd, msgs);
			case ComparePackage.DIFF__IMPLIES:
				return ((InternalEList<?>)getImplies()).basicRemove(otherEnd, msgs);
			case ComparePackage.DIFF__IMPLIED_BY:
				return ((InternalEList<?>)getImpliedBy()).basicRemove(otherEnd, msgs);
			case ComparePackage.DIFF__REFINES:
				return ((InternalEList<?>)getRefines()).basicRemove(otherEnd, msgs);
			case ComparePackage.DIFF__REFINED_BY:
				return ((InternalEList<?>)getRefinedBy()).basicRemove(otherEnd, msgs);
			case ComparePackage.DIFF__EQUIVALENCE:
				return basicSetEquivalence(null, msgs);
			case ComparePackage.DIFF__CONFLICT:
				return basicSetConflict(null, msgs);
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
			case ComparePackage.DIFF__MATCH:
				if (resolve) return getMatch();
				return basicGetMatch();
			case ComparePackage.DIFF__REQUIRES:
				return getRequires();
			case ComparePackage.DIFF__REQUIRED_BY:
				return getRequiredBy();
			case ComparePackage.DIFF__IMPLIES:
				return getImplies();
			case ComparePackage.DIFF__IMPLIED_BY:
				return getImpliedBy();
			case ComparePackage.DIFF__REFINES:
				return getRefines();
			case ComparePackage.DIFF__REFINED_BY:
				return getRefinedBy();
			case ComparePackage.DIFF__PRIME_REFINING:
				if (resolve) return getPrimeRefining();
				return basicGetPrimeRefining();
			case ComparePackage.DIFF__KIND:
				return getKind();
			case ComparePackage.DIFF__SOURCE:
				return getSource();
			case ComparePackage.DIFF__STATE:
				return getState();
			case ComparePackage.DIFF__EQUIVALENCE:
				if (resolve) return getEquivalence();
				return basicGetEquivalence();
			case ComparePackage.DIFF__CONFLICT:
				if (resolve) return getConflict();
				return basicGetConflict();
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
			case ComparePackage.DIFF__MATCH:
				setMatch((Match)newValue);
				return;
			case ComparePackage.DIFF__REQUIRES:
				getRequires().clear();
				getRequires().addAll((Collection<? extends Diff>)newValue);
				return;
			case ComparePackage.DIFF__REQUIRED_BY:
				getRequiredBy().clear();
				getRequiredBy().addAll((Collection<? extends Diff>)newValue);
				return;
			case ComparePackage.DIFF__IMPLIES:
				getImplies().clear();
				getImplies().addAll((Collection<? extends Diff>)newValue);
				return;
			case ComparePackage.DIFF__IMPLIED_BY:
				getImpliedBy().clear();
				getImpliedBy().addAll((Collection<? extends Diff>)newValue);
				return;
			case ComparePackage.DIFF__REFINES:
				getRefines().clear();
				getRefines().addAll((Collection<? extends Diff>)newValue);
				return;
			case ComparePackage.DIFF__REFINED_BY:
				getRefinedBy().clear();
				getRefinedBy().addAll((Collection<? extends Diff>)newValue);
				return;
			case ComparePackage.DIFF__KIND:
				setKind((DifferenceKind)newValue);
				return;
			case ComparePackage.DIFF__SOURCE:
				setSource((DifferenceSource)newValue);
				return;
			case ComparePackage.DIFF__STATE:
				setState((DifferenceState)newValue);
				return;
			case ComparePackage.DIFF__EQUIVALENCE:
				setEquivalence((Equivalence)newValue);
				return;
			case ComparePackage.DIFF__CONFLICT:
				setConflict((Conflict)newValue);
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
			case ComparePackage.DIFF__MATCH:
				setMatch((Match)null);
				return;
			case ComparePackage.DIFF__REQUIRES:
				getRequires().clear();
				return;
			case ComparePackage.DIFF__REQUIRED_BY:
				getRequiredBy().clear();
				return;
			case ComparePackage.DIFF__IMPLIES:
				getImplies().clear();
				return;
			case ComparePackage.DIFF__IMPLIED_BY:
				getImpliedBy().clear();
				return;
			case ComparePackage.DIFF__REFINES:
				getRefines().clear();
				return;
			case ComparePackage.DIFF__REFINED_BY:
				getRefinedBy().clear();
				return;
			case ComparePackage.DIFF__KIND:
				setKind(KIND_EDEFAULT);
				return;
			case ComparePackage.DIFF__SOURCE:
				setSource(SOURCE_EDEFAULT);
				return;
			case ComparePackage.DIFF__STATE:
				setState(STATE_EDEFAULT);
				return;
			case ComparePackage.DIFF__EQUIVALENCE:
				setEquivalence((Equivalence)null);
				return;
			case ComparePackage.DIFF__CONFLICT:
				setConflict((Conflict)null);
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
			case ComparePackage.DIFF__MATCH:
				return basicGetMatch() != null;
			case ComparePackage.DIFF__REQUIRES:
				return requires != null && !requires.isEmpty();
			case ComparePackage.DIFF__REQUIRED_BY:
				return requiredBy != null && !requiredBy.isEmpty();
			case ComparePackage.DIFF__IMPLIES:
				return implies != null && !implies.isEmpty();
			case ComparePackage.DIFF__IMPLIED_BY:
				return impliedBy != null && !impliedBy.isEmpty();
			case ComparePackage.DIFF__REFINES:
				return refines != null && !refines.isEmpty();
			case ComparePackage.DIFF__REFINED_BY:
				return refinedBy != null && !refinedBy.isEmpty();
			case ComparePackage.DIFF__PRIME_REFINING:
				return primeRefining != null;
			case ComparePackage.DIFF__KIND:
				return kind != KIND_EDEFAULT;
			case ComparePackage.DIFF__SOURCE:
				return source != SOURCE_EDEFAULT;
			case ComparePackage.DIFF__STATE:
				return state != STATE_EDEFAULT;
			case ComparePackage.DIFF__EQUIVALENCE:
				return equivalence != null;
			case ComparePackage.DIFF__CONFLICT:
				return conflict != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case ComparePackage.DIFF___COPY_RIGHT_TO_LEFT:
				copyRightToLeft();
				return null;
			case ComparePackage.DIFF___COPY_LEFT_TO_RIGHT:
				copyLeftToRight();
				return null;
			case ComparePackage.DIFF___DISCARD:
				discard();
				return null;
		}
		return super.eInvoke(operationID, arguments);
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
		result.append(", source: ");
		result.append(source);
		result.append(", state: ");
		result.append(state);
		result.append(')');
		return result.toString();
	}

} //DiffImpl
