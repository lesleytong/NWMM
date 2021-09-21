/**
 */
package my.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import my.ComparisonN;
import my.ConflictN;
import my.DiffN;
import my.MatchN;
import my.MyPackage;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Comparison N</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link my.impl.ComparisonNImpl#getMatches <em>Matches</em>}</li>
 *   <li>{@link my.impl.ComparisonNImpl#getConflicts <em>Conflicts</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ComparisonNImpl extends MinimalEObjectImpl.Container implements ComparisonN {
	/**
	 * The cached value of the '{@link #getMatches() <em>Matches</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMatches()
	 * @generated
	 * @ordered
	 */
	protected EList<MatchN> matches;
	/**
	 * The cached value of the '{@link #getConflicts() <em>Conflicts</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConflicts()
	 * @generated
	 * @ordered
	 */
	protected EList<ConflictN> conflicts;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
//	 * @generated lyt: 改了可见性
	 */
	public ComparisonNImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MyPackage.Literals.COMPARISON_N;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<MatchN> getMatches() {
		if (matches == null) {
			matches = new EObjectContainmentEList<MatchN>(MatchN.class, this, MyPackage.COMPARISON_N__MATCHES);
		}
		return matches;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ConflictN> getConflicts() {
		if (conflicts == null) {
			conflicts = new EObjectContainmentEList<ConflictN>(ConflictN.class, this, MyPackage.COMPARISON_N__CONFLICTS);
		}
		return conflicts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<DiffN> getDifferences() {
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
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MyPackage.COMPARISON_N__MATCHES:
				return ((InternalEList<?>)getMatches()).basicRemove(otherEnd, msgs);
			case MyPackage.COMPARISON_N__CONFLICTS:
				return ((InternalEList<?>)getConflicts()).basicRemove(otherEnd, msgs);
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
			case MyPackage.COMPARISON_N__MATCHES:
				return getMatches();
			case MyPackage.COMPARISON_N__CONFLICTS:
				return getConflicts();
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
			case MyPackage.COMPARISON_N__MATCHES:
				getMatches().clear();
				getMatches().addAll((Collection<? extends MatchN>)newValue);
				return;
			case MyPackage.COMPARISON_N__CONFLICTS:
				getConflicts().clear();
				getConflicts().addAll((Collection<? extends ConflictN>)newValue);
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
			case MyPackage.COMPARISON_N__MATCHES:
				getMatches().clear();
				return;
			case MyPackage.COMPARISON_N__CONFLICTS:
				getConflicts().clear();
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
			case MyPackage.COMPARISON_N__MATCHES:
				return matches != null && !matches.isEmpty();
			case MyPackage.COMPARISON_N__CONFLICTS:
				return conflicts != null && !conflicts.isEmpty();
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
			case MyPackage.COMPARISON_N___GET_DIFFERENCES:
				return getDifferences();
		}
		return super.eInvoke(operationID, arguments);
	}

} //ComparisonNImpl
