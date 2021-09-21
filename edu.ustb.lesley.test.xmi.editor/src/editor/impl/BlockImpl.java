/**
 */
package editor.impl;

import editor.Block;
import editor.EditorPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Block</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link editor.impl.BlockImpl#getBlockbegin <em>Blockbegin</em>}</li>
 *   <li>{@link editor.impl.BlockImpl#getBlockend <em>Blockend</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BlockImpl extends AbstractElementImpl implements Block {
	/**
	 * The default value of the '{@link #getBlockbegin() <em>Blockbegin</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBlockbegin()
	 * @generated
	 * @ordered
	 */
	protected static final String BLOCKBEGIN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBlockbegin() <em>Blockbegin</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBlockbegin()
	 * @generated
	 * @ordered
	 */
	protected String blockbegin = BLOCKBEGIN_EDEFAULT;

	/**
	 * The default value of the '{@link #getBlockend() <em>Blockend</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBlockend()
	 * @generated
	 * @ordered
	 */
	protected static final String BLOCKEND_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBlockend() <em>Blockend</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBlockend()
	 * @generated
	 * @ordered
	 */
	protected String blockend = BLOCKEND_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BlockImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EditorPackage.Literals.BLOCK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getBlockbegin() {
		return blockbegin;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBlockbegin(String newBlockbegin) {
		String oldBlockbegin = blockbegin;
		blockbegin = newBlockbegin;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EditorPackage.BLOCK__BLOCKBEGIN, oldBlockbegin, blockbegin));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getBlockend() {
		return blockend;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBlockend(String newBlockend) {
		String oldBlockend = blockend;
		blockend = newBlockend;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EditorPackage.BLOCK__BLOCKEND, oldBlockend, blockend));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EditorPackage.BLOCK__BLOCKBEGIN:
				return getBlockbegin();
			case EditorPackage.BLOCK__BLOCKEND:
				return getBlockend();
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
			case EditorPackage.BLOCK__BLOCKBEGIN:
				setBlockbegin((String)newValue);
				return;
			case EditorPackage.BLOCK__BLOCKEND:
				setBlockend((String)newValue);
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
			case EditorPackage.BLOCK__BLOCKBEGIN:
				setBlockbegin(BLOCKBEGIN_EDEFAULT);
				return;
			case EditorPackage.BLOCK__BLOCKEND:
				setBlockend(BLOCKEND_EDEFAULT);
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
			case EditorPackage.BLOCK__BLOCKBEGIN:
				return BLOCKBEGIN_EDEFAULT == null ? blockbegin != null : !BLOCKBEGIN_EDEFAULT.equals(blockbegin);
			case EditorPackage.BLOCK__BLOCKEND:
				return BLOCKEND_EDEFAULT == null ? blockend != null : !BLOCKEND_EDEFAULT.equals(blockend);
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
		result.append(" (blockbegin: ");
		result.append(blockbegin);
		result.append(", blockend: ");
		result.append(blockend);
		result.append(')');
		return result.toString();
	}

} //BlockImpl
