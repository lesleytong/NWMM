/**
 */
package compare.impl;

import compare.ComparePackage;
import compare.Comparison;
import compare.Conflict;
import compare.Diff;
import compare.Equivalence;
import compare.Match;
import compare.MatchResource;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.compare.utils.IEqualityHelper;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Comparison</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link compare.impl.ComparisonImpl#getMatchedResources <em>Matched Resources</em>}</li>
 *   <li>{@link compare.impl.ComparisonImpl#getMatches <em>Matches</em>}</li>
 *   <li>{@link compare.impl.ComparisonImpl#getConflicts <em>Conflicts</em>}</li>
 *   <li>{@link compare.impl.ComparisonImpl#getEquivalences <em>Equivalences</em>}</li>
 *   <li>{@link compare.impl.ComparisonImpl#isThreeWay <em>Three Way</em>}</li>
 *   <li>{@link compare.impl.ComparisonImpl#getDiagnostic <em>Diagnostic</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ComparisonImpl extends MinimalEObjectImpl.Container implements Comparison {
	/**
	 * The cached value of the '{@link #getMatchedResources() <em>Matched Resources</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMatchedResources()
	 * @generated
	 * @ordered
	 */
	protected EList<MatchResource> matchedResources;

	/**
	 * The cached value of the '{@link #getMatches() <em>Matches</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMatches()
	 * @generated
	 * @ordered
	 */
	protected EList<Match> matches;

	/**
	 * The cached value of the '{@link #getConflicts() <em>Conflicts</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConflicts()
	 * @generated
	 * @ordered
	 */
	protected EList<Conflict> conflicts;

	/**
	 * The cached value of the '{@link #getEquivalences() <em>Equivalences</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEquivalences()
	 * @generated
	 * @ordered
	 */
	protected EList<Equivalence> equivalences;

	/**
	 * The default value of the '{@link #isThreeWay() <em>Three Way</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isThreeWay()
	 * @generated
	 * @ordered
	 */
	protected static final boolean THREE_WAY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isThreeWay() <em>Three Way</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isThreeWay()
	 * @generated
	 * @ordered
	 */
	protected boolean threeWay = THREE_WAY_EDEFAULT;

	/**
	 * The default value of the '{@link #getDiagnostic() <em>Diagnostic</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDiagnostic()
	 * @generated
	 * @ordered
	 */
	protected static final Diagnostic DIAGNOSTIC_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDiagnostic() <em>Diagnostic</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDiagnostic()
	 * @generated
	 * @ordered
	 */
	protected Diagnostic diagnostic = DIAGNOSTIC_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComparisonImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ComparePackage.Literals.COMPARISON;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<MatchResource> getMatchedResources() {
		if (matchedResources == null) {
			matchedResources = new EObjectContainmentWithInverseEList<MatchResource>(MatchResource.class, this, ComparePackage.COMPARISON__MATCHED_RESOURCES, ComparePackage.MATCH_RESOURCE__COMPARISON);
		}
		return matchedResources;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Match> getMatches() {
		if (matches == null) {
			matches = new EObjectContainmentEList<Match>(Match.class, this, ComparePackage.COMPARISON__MATCHES);
		}
		return matches;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Conflict> getConflicts() {
		if (conflicts == null) {
			conflicts = new EObjectContainmentEList<Conflict>(Conflict.class, this, ComparePackage.COMPARISON__CONFLICTS);
		}
		return conflicts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Equivalence> getEquivalences() {
		if (equivalences == null) {
			equivalences = new EObjectContainmentEList<Equivalence>(Equivalence.class, this, ComparePackage.COMPARISON__EQUIVALENCES);
		}
		return equivalences;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isThreeWay() {
		return threeWay;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setThreeWay(boolean newThreeWay) {
		boolean oldThreeWay = threeWay;
		threeWay = newThreeWay;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComparePackage.COMPARISON__THREE_WAY, oldThreeWay, threeWay));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Diagnostic getDiagnostic() {
		return diagnostic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDiagnostic(Diagnostic newDiagnostic) {
		Diagnostic oldDiagnostic = diagnostic;
		diagnostic = newDiagnostic;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComparePackage.COMPARISON__DIAGNOSTIC, oldDiagnostic, diagnostic));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Diff> getDifferences() {
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
	public EList<Diff> getDifferences(EObject element) {
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
	public Match getMatch(EObject element) {
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
	public IEqualityHelper getEqualityHelper() {
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
			case ComparePackage.COMPARISON__MATCHED_RESOURCES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getMatchedResources()).basicAdd(otherEnd, msgs);
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
			case ComparePackage.COMPARISON__MATCHED_RESOURCES:
				return ((InternalEList<?>)getMatchedResources()).basicRemove(otherEnd, msgs);
			case ComparePackage.COMPARISON__MATCHES:
				return ((InternalEList<?>)getMatches()).basicRemove(otherEnd, msgs);
			case ComparePackage.COMPARISON__CONFLICTS:
				return ((InternalEList<?>)getConflicts()).basicRemove(otherEnd, msgs);
			case ComparePackage.COMPARISON__EQUIVALENCES:
				return ((InternalEList<?>)getEquivalences()).basicRemove(otherEnd, msgs);
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
			case ComparePackage.COMPARISON__MATCHED_RESOURCES:
				return getMatchedResources();
			case ComparePackage.COMPARISON__MATCHES:
				return getMatches();
			case ComparePackage.COMPARISON__CONFLICTS:
				return getConflicts();
			case ComparePackage.COMPARISON__EQUIVALENCES:
				return getEquivalences();
			case ComparePackage.COMPARISON__THREE_WAY:
				return isThreeWay();
			case ComparePackage.COMPARISON__DIAGNOSTIC:
				return getDiagnostic();
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
			case ComparePackage.COMPARISON__MATCHED_RESOURCES:
				getMatchedResources().clear();
				getMatchedResources().addAll((Collection<? extends MatchResource>)newValue);
				return;
			case ComparePackage.COMPARISON__MATCHES:
				getMatches().clear();
				getMatches().addAll((Collection<? extends Match>)newValue);
				return;
			case ComparePackage.COMPARISON__CONFLICTS:
				getConflicts().clear();
				getConflicts().addAll((Collection<? extends Conflict>)newValue);
				return;
			case ComparePackage.COMPARISON__EQUIVALENCES:
				getEquivalences().clear();
				getEquivalences().addAll((Collection<? extends Equivalence>)newValue);
				return;
			case ComparePackage.COMPARISON__THREE_WAY:
				setThreeWay((Boolean)newValue);
				return;
			case ComparePackage.COMPARISON__DIAGNOSTIC:
				setDiagnostic((Diagnostic)newValue);
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
			case ComparePackage.COMPARISON__MATCHED_RESOURCES:
				getMatchedResources().clear();
				return;
			case ComparePackage.COMPARISON__MATCHES:
				getMatches().clear();
				return;
			case ComparePackage.COMPARISON__CONFLICTS:
				getConflicts().clear();
				return;
			case ComparePackage.COMPARISON__EQUIVALENCES:
				getEquivalences().clear();
				return;
			case ComparePackage.COMPARISON__THREE_WAY:
				setThreeWay(THREE_WAY_EDEFAULT);
				return;
			case ComparePackage.COMPARISON__DIAGNOSTIC:
				setDiagnostic(DIAGNOSTIC_EDEFAULT);
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
			case ComparePackage.COMPARISON__MATCHED_RESOURCES:
				return matchedResources != null && !matchedResources.isEmpty();
			case ComparePackage.COMPARISON__MATCHES:
				return matches != null && !matches.isEmpty();
			case ComparePackage.COMPARISON__CONFLICTS:
				return conflicts != null && !conflicts.isEmpty();
			case ComparePackage.COMPARISON__EQUIVALENCES:
				return equivalences != null && !equivalences.isEmpty();
			case ComparePackage.COMPARISON__THREE_WAY:
				return threeWay != THREE_WAY_EDEFAULT;
			case ComparePackage.COMPARISON__DIAGNOSTIC:
				return DIAGNOSTIC_EDEFAULT == null ? diagnostic != null : !DIAGNOSTIC_EDEFAULT.equals(diagnostic);
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
			case ComparePackage.COMPARISON___GET_DIFFERENCES:
				return getDifferences();
			case ComparePackage.COMPARISON___GET_DIFFERENCES__EOBJECT:
				return getDifferences((EObject)arguments.get(0));
			case ComparePackage.COMPARISON___GET_MATCH__EOBJECT:
				return getMatch((EObject)arguments.get(0));
			case ComparePackage.COMPARISON___GET_EQUALITY_HELPER:
				return getEqualityHelper();
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
		result.append(" (threeWay: ");
		result.append(threeWay);
		result.append(", diagnostic: ");
		result.append(diagnostic);
		result.append(')');
		return result.toString();
	}

} //ComparisonImpl
