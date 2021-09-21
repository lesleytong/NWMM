/**
 */
package amble.impl;

import amble.AmblePackage;
import amble.Message;
import amble.Transall;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Transall</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link amble.impl.TransallImpl#getWaitFor <em>Wait For</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TransallImpl extends TransitionImpl implements Transall {
	/**
	 * The cached value of the '{@link #getWaitFor() <em>Wait For</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWaitFor()
	 * @generated
	 * @ordered
	 */
	protected Message waitFor;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TransallImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AmblePackage.Literals.TRANSALL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Message getWaitFor() {
		if (waitFor != null && waitFor.eIsProxy()) {
			InternalEObject oldWaitFor = (InternalEObject)waitFor;
			waitFor = (Message)eResolveProxy(oldWaitFor);
			if (waitFor != oldWaitFor) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, AmblePackage.TRANSALL__WAIT_FOR, oldWaitFor, waitFor));
			}
		}
		return waitFor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Message basicGetWaitFor() {
		return waitFor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWaitFor(Message newWaitFor) {
		Message oldWaitFor = waitFor;
		waitFor = newWaitFor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AmblePackage.TRANSALL__WAIT_FOR, oldWaitFor, waitFor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AmblePackage.TRANSALL__WAIT_FOR:
				if (resolve) return getWaitFor();
				return basicGetWaitFor();
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
			case AmblePackage.TRANSALL__WAIT_FOR:
				setWaitFor((Message)newValue);
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
			case AmblePackage.TRANSALL__WAIT_FOR:
				setWaitFor((Message)null);
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
			case AmblePackage.TRANSALL__WAIT_FOR:
				return waitFor != null;
		}
		return super.eIsSet(featureID);
	}

} //TransallImpl
