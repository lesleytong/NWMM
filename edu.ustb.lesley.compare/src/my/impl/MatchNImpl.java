/**
 */
package my.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import my.ComparisonN;
import my.DiffN;
import my.MatchN;
import my.MyPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Match N</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link my.impl.MatchNImpl#getDifferences <em>Differences</em>}</li>
 *   <li>{@link my.impl.MatchNImpl#getBase <em>Base</em>}</li>
 *   <li>{@link my.impl.MatchNImpl#getBranches <em>Branches</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MatchNImpl extends MinimalEObjectImpl.Container implements MatchN {
	/**
	 * The cached value of the '{@link #getDifferences() <em>Differences</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDifferences()
	 * @generated
	 * @ordered
	 */
	protected EList<DiffN> differences;

	/**
	 * The cached value of the '{@link #getBase() <em>Base</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBase()
	 * @generated
	 * @ordered
	 */
	protected EObject base;

	/**
	 * The cached value of the '{@link #getBranches() <em>Branches</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBranches()
	 * @generated
	 * @ordered
	 */
	protected EList<EObject> branches;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
//	 * @generated lyt: 改了可见性
	 */
	public MatchNImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MyPackage.Literals.MATCH_N;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<DiffN> getDifferences() {
		if (differences == null) {
			differences = new EObjectContainmentWithInverseEList<DiffN>(DiffN.class, this, MyPackage.MATCH_N__DIFFERENCES, MyPackage.DIFF_N__MATCH);
		}
		return differences;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject getBase() {
		if (base != null && base.eIsProxy()) {
			InternalEObject oldBase = (InternalEObject)base;
			base = eResolveProxy(oldBase);
			if (base != oldBase) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MyPackage.MATCH_N__BASE, oldBase, base));
			}
		}
		return base;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetBase() {
		return base;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBase(EObject newBase) {
		EObject oldBase = base;
		base = newBase;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MyPackage.MATCH_N__BASE, oldBase, base));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<EObject> getBranches() {
		if (branches == null) {
			branches = new EObjectResolvingEList<EObject>(EObject.class, this, MyPackage.MATCH_N__BRANCHES);
		}
		return branches;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ComparisonN getComparisonN() {
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
			case MyPackage.MATCH_N__DIFFERENCES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getDifferences()).basicAdd(otherEnd, msgs);
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
			case MyPackage.MATCH_N__DIFFERENCES:
				return ((InternalEList<?>)getDifferences()).basicRemove(otherEnd, msgs);
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
			case MyPackage.MATCH_N__DIFFERENCES:
				return getDifferences();
			case MyPackage.MATCH_N__BASE:
				if (resolve) return getBase();
				return basicGetBase();
			case MyPackage.MATCH_N__BRANCHES:
				return getBranches();
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
			case MyPackage.MATCH_N__DIFFERENCES:
				getDifferences().clear();
				getDifferences().addAll((Collection<? extends DiffN>)newValue);
				return;
			case MyPackage.MATCH_N__BASE:
				setBase((EObject)newValue);
				return;
			case MyPackage.MATCH_N__BRANCHES:
				getBranches().clear();
				getBranches().addAll((Collection<? extends EObject>)newValue);
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
			case MyPackage.MATCH_N__DIFFERENCES:
				getDifferences().clear();
				return;
			case MyPackage.MATCH_N__BASE:
				setBase((EObject)null);
				return;
			case MyPackage.MATCH_N__BRANCHES:
				getBranches().clear();
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
			case MyPackage.MATCH_N__DIFFERENCES:
				return differences != null && !differences.isEmpty();
			case MyPackage.MATCH_N__BASE:
				return base != null;
			case MyPackage.MATCH_N__BRANCHES:
				return branches != null && !branches.isEmpty();
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
			case MyPackage.MATCH_N___GET_COMPARISON_N:
				return getComparisonN();
		}
		return super.eInvoke(operationID, arguments);
	}

} //MatchNImpl
