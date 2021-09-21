/**
 */
package editor.impl;

import editor.EditorPackage;
import editor.GroupElement;
import editor.Indent;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Indent</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link editor.impl.IndentImpl#getRetrait <em>Retrait</em>}</li>
 *   <li>{@link editor.impl.IndentImpl#getGroupElementIndent <em>Group Element Indent</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IndentImpl extends LocatedElementImpl implements Indent {
	/**
	 * The default value of the '{@link #getRetrait() <em>Retrait</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRetrait()
	 * @generated
	 * @ordered
	 */
	protected static final String RETRAIT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRetrait() <em>Retrait</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRetrait()
	 * @generated
	 * @ordered
	 */
	protected String retrait = RETRAIT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getGroupElementIndent() <em>Group Element Indent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroupElementIndent()
	 * @generated
	 * @ordered
	 */
	protected GroupElement groupElementIndent;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IndentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EditorPackage.Literals.INDENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRetrait() {
		return retrait;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRetrait(String newRetrait) {
		String oldRetrait = retrait;
		retrait = newRetrait;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EditorPackage.INDENT__RETRAIT, oldRetrait, retrait));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GroupElement getGroupElementIndent() {
		if (groupElementIndent != null && groupElementIndent.eIsProxy()) {
			InternalEObject oldGroupElementIndent = (InternalEObject)groupElementIndent;
			groupElementIndent = (GroupElement)eResolveProxy(oldGroupElementIndent);
			if (groupElementIndent != oldGroupElementIndent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, EditorPackage.INDENT__GROUP_ELEMENT_INDENT, oldGroupElementIndent, groupElementIndent));
			}
		}
		return groupElementIndent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GroupElement basicGetGroupElementIndent() {
		return groupElementIndent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGroupElementIndent(GroupElement newGroupElementIndent) {
		GroupElement oldGroupElementIndent = groupElementIndent;
		groupElementIndent = newGroupElementIndent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EditorPackage.INDENT__GROUP_ELEMENT_INDENT, oldGroupElementIndent, groupElementIndent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EditorPackage.INDENT__RETRAIT:
				return getRetrait();
			case EditorPackage.INDENT__GROUP_ELEMENT_INDENT:
				if (resolve) return getGroupElementIndent();
				return basicGetGroupElementIndent();
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
			case EditorPackage.INDENT__RETRAIT:
				setRetrait((String)newValue);
				return;
			case EditorPackage.INDENT__GROUP_ELEMENT_INDENT:
				setGroupElementIndent((GroupElement)newValue);
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
			case EditorPackage.INDENT__RETRAIT:
				setRetrait(RETRAIT_EDEFAULT);
				return;
			case EditorPackage.INDENT__GROUP_ELEMENT_INDENT:
				setGroupElementIndent((GroupElement)null);
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
			case EditorPackage.INDENT__RETRAIT:
				return RETRAIT_EDEFAULT == null ? retrait != null : !RETRAIT_EDEFAULT.equals(retrait);
			case EditorPackage.INDENT__GROUP_ELEMENT_INDENT:
				return groupElementIndent != null;
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
		result.append(" (retrait: ");
		result.append(retrait);
		result.append(')');
		return result.toString();
	}

} //IndentImpl
