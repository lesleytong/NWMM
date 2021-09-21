/**
 */
package sharego.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import sharego.BusinessRule;
import sharego.Service2BusinessObjectFlow;
import sharego.ServiceOperation;
import sharego.SharegoPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Service Operation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link sharego.impl.ServiceOperationImpl#getBOFlows <em>BO Flows</em>}</li>
 *   <li>{@link sharego.impl.ServiceOperationImpl#getRules <em>Rules</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ServiceOperationImpl extends OperationImpl implements ServiceOperation {
	/**
	 * The cached value of the '{@link #getBOFlows() <em>BO Flows</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBOFlows()
	 * @generated
	 * @ordered
	 */
	protected EList<Service2BusinessObjectFlow> boFlows;

	/**
	 * The cached value of the '{@link #getRules() <em>Rules</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRules()
	 * @generated
	 * @ordered
	 */
	protected EList<BusinessRule> rules;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ServiceOperationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SharegoPackage.Literals.SERVICE_OPERATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Service2BusinessObjectFlow> getBOFlows() {
		if (boFlows == null) {
			boFlows = new EObjectContainmentEList<Service2BusinessObjectFlow>(Service2BusinessObjectFlow.class, this, SharegoPackage.SERVICE_OPERATION__BO_FLOWS);
		}
		return boFlows;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<BusinessRule> getRules() {
		if (rules == null) {
			rules = new EObjectContainmentEList<BusinessRule>(BusinessRule.class, this, SharegoPackage.SERVICE_OPERATION__RULES);
		}
		return rules;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SharegoPackage.SERVICE_OPERATION__BO_FLOWS:
				return ((InternalEList<?>)getBOFlows()).basicRemove(otherEnd, msgs);
			case SharegoPackage.SERVICE_OPERATION__RULES:
				return ((InternalEList<?>)getRules()).basicRemove(otherEnd, msgs);
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
			case SharegoPackage.SERVICE_OPERATION__BO_FLOWS:
				return getBOFlows();
			case SharegoPackage.SERVICE_OPERATION__RULES:
				return getRules();
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
			case SharegoPackage.SERVICE_OPERATION__BO_FLOWS:
				getBOFlows().clear();
				getBOFlows().addAll((Collection<? extends Service2BusinessObjectFlow>)newValue);
				return;
			case SharegoPackage.SERVICE_OPERATION__RULES:
				getRules().clear();
				getRules().addAll((Collection<? extends BusinessRule>)newValue);
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
			case SharegoPackage.SERVICE_OPERATION__BO_FLOWS:
				getBOFlows().clear();
				return;
			case SharegoPackage.SERVICE_OPERATION__RULES:
				getRules().clear();
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
			case SharegoPackage.SERVICE_OPERATION__BO_FLOWS:
				return boFlows != null && !boFlows.isEmpty();
			case SharegoPackage.SERVICE_OPERATION__RULES:
				return rules != null && !rules.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ServiceOperationImpl
