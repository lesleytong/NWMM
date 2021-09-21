/**
 */
package make.impl;

import make.MakePackage;
import make.Rule;
import make.ShellLine;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Shell Line</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link make.impl.ShellLineImpl#getCommand <em>Command</em>}</li>
 *   <li>{@link make.impl.ShellLineImpl#isDisplay <em>Display</em>}</li>
 *   <li>{@link make.impl.ShellLineImpl#getRuleShellLine <em>Rule Shell Line</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ShellLineImpl extends MinimalEObjectImpl.Container implements ShellLine {
	/**
	 * The default value of the '{@link #getCommand() <em>Command</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommand()
	 * @generated
	 * @ordered
	 */
	protected static final String COMMAND_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCommand() <em>Command</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommand()
	 * @generated
	 * @ordered
	 */
	protected String command = COMMAND_EDEFAULT;

	/**
	 * The default value of the '{@link #isDisplay() <em>Display</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDisplay()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DISPLAY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDisplay() <em>Display</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDisplay()
	 * @generated
	 * @ordered
	 */
	protected boolean display = DISPLAY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ShellLineImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MakePackage.Literals.SHELL_LINE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCommand(String newCommand) {
		String oldCommand = command;
		command = newCommand;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MakePackage.SHELL_LINE__COMMAND, oldCommand, command));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDisplay() {
		return display;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDisplay(boolean newDisplay) {
		boolean oldDisplay = display;
		display = newDisplay;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MakePackage.SHELL_LINE__DISPLAY, oldDisplay, display));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rule getRuleShellLine() {
		if (eContainerFeatureID() != MakePackage.SHELL_LINE__RULE_SHELL_LINE) return null;
		return (Rule)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRuleShellLine(Rule newRuleShellLine, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newRuleShellLine, MakePackage.SHELL_LINE__RULE_SHELL_LINE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRuleShellLine(Rule newRuleShellLine) {
		if (newRuleShellLine != eInternalContainer() || (eContainerFeatureID() != MakePackage.SHELL_LINE__RULE_SHELL_LINE && newRuleShellLine != null)) {
			if (EcoreUtil.isAncestor(this, newRuleShellLine))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newRuleShellLine != null)
				msgs = ((InternalEObject)newRuleShellLine).eInverseAdd(this, MakePackage.RULE__SHELL_LINES, Rule.class, msgs);
			msgs = basicSetRuleShellLine(newRuleShellLine, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MakePackage.SHELL_LINE__RULE_SHELL_LINE, newRuleShellLine, newRuleShellLine));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MakePackage.SHELL_LINE__RULE_SHELL_LINE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetRuleShellLine((Rule)otherEnd, msgs);
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
			case MakePackage.SHELL_LINE__RULE_SHELL_LINE:
				return basicSetRuleShellLine(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case MakePackage.SHELL_LINE__RULE_SHELL_LINE:
				return eInternalContainer().eInverseRemove(this, MakePackage.RULE__SHELL_LINES, Rule.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MakePackage.SHELL_LINE__COMMAND:
				return getCommand();
			case MakePackage.SHELL_LINE__DISPLAY:
				return isDisplay();
			case MakePackage.SHELL_LINE__RULE_SHELL_LINE:
				return getRuleShellLine();
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
			case MakePackage.SHELL_LINE__COMMAND:
				setCommand((String)newValue);
				return;
			case MakePackage.SHELL_LINE__DISPLAY:
				setDisplay((Boolean)newValue);
				return;
			case MakePackage.SHELL_LINE__RULE_SHELL_LINE:
				setRuleShellLine((Rule)newValue);
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
			case MakePackage.SHELL_LINE__COMMAND:
				setCommand(COMMAND_EDEFAULT);
				return;
			case MakePackage.SHELL_LINE__DISPLAY:
				setDisplay(DISPLAY_EDEFAULT);
				return;
			case MakePackage.SHELL_LINE__RULE_SHELL_LINE:
				setRuleShellLine((Rule)null);
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
			case MakePackage.SHELL_LINE__COMMAND:
				return COMMAND_EDEFAULT == null ? command != null : !COMMAND_EDEFAULT.equals(command);
			case MakePackage.SHELL_LINE__DISPLAY:
				return display != DISPLAY_EDEFAULT;
			case MakePackage.SHELL_LINE__RULE_SHELL_LINE:
				return getRuleShellLine() != null;
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
		result.append(" (command: ");
		result.append(command);
		result.append(", display: ");
		result.append(display);
		result.append(')');
		return result.toString();
	}

} //ShellLineImpl
