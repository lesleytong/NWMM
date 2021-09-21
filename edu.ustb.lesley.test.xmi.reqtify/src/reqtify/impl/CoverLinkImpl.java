/**
 */
package reqtify.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import reqtify.AbstractRequirement;
import reqtify.CoverLink;
import reqtify.ReqtifyPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Cover Link</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link reqtify.impl.CoverLinkImpl#getLinkWith <em>Link With</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CoverLinkImpl extends TypedElementImpl implements CoverLink {
	/**
	 * The cached value of the '{@link #getLinkWith() <em>Link With</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLinkWith()
	 * @generated
	 * @ordered
	 */
	protected AbstractRequirement linkWith;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CoverLinkImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ReqtifyPackage.Literals.COVER_LINK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbstractRequirement getLinkWith() {
		if (linkWith != null && linkWith.eIsProxy()) {
			InternalEObject oldLinkWith = (InternalEObject)linkWith;
			linkWith = (AbstractRequirement)eResolveProxy(oldLinkWith);
			if (linkWith != oldLinkWith) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ReqtifyPackage.COVER_LINK__LINK_WITH, oldLinkWith, linkWith));
			}
		}
		return linkWith;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbstractRequirement basicGetLinkWith() {
		return linkWith;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLinkWith(AbstractRequirement newLinkWith) {
		AbstractRequirement oldLinkWith = linkWith;
		linkWith = newLinkWith;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ReqtifyPackage.COVER_LINK__LINK_WITH, oldLinkWith, linkWith));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ReqtifyPackage.COVER_LINK__LINK_WITH:
				if (resolve) return getLinkWith();
				return basicGetLinkWith();
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
			case ReqtifyPackage.COVER_LINK__LINK_WITH:
				setLinkWith((AbstractRequirement)newValue);
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
			case ReqtifyPackage.COVER_LINK__LINK_WITH:
				setLinkWith((AbstractRequirement)null);
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
			case ReqtifyPackage.COVER_LINK__LINK_WITH:
				return linkWith != null;
		}
		return super.eIsSet(featureID);
	}

} //CoverLinkImpl
