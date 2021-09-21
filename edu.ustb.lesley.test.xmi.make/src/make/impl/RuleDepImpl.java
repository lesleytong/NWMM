/**
 */
package make.impl;

import make.MakePackage;
import make.Rule;
import make.RuleDep;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rule Dep</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link make.impl.RuleDepImpl#getRuledep <em>Ruledep</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RuleDepImpl extends DependencyImpl implements RuleDep {
	/**
	 * The cached value of the '{@link #getRuledep() <em>Ruledep</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRuledep()
	 * @generated
	 * @ordered
	 */
	protected Rule ruledep;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RuleDepImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MakePackage.Literals.RULE_DEP;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rule getRuledep() {
		if (ruledep != null && ruledep.eIsProxy()) {
			InternalEObject oldRuledep = (InternalEObject)ruledep;
			ruledep = (Rule)eResolveProxy(oldRuledep);
			if (ruledep != oldRuledep) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MakePackage.RULE_DEP__RULEDEP, oldRuledep, ruledep));
			}
		}
		return ruledep;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rule basicGetRuledep() {
		return ruledep;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRuledep(Rule newRuledep) {
		Rule oldRuledep = ruledep;
		ruledep = newRuledep;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MakePackage.RULE_DEP__RULEDEP, oldRuledep, ruledep));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MakePackage.RULE_DEP__RULEDEP:
				if (resolve) return getRuledep();
				return basicGetRuledep();
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
			case MakePackage.RULE_DEP__RULEDEP:
				setRuledep((Rule)newValue);
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
			case MakePackage.RULE_DEP__RULEDEP:
				setRuledep((Rule)null);
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
			case MakePackage.RULE_DEP__RULEDEP:
				return ruledep != null;
		}
		return super.eIsSet(featureID);
	}

} //RuleDepImpl
