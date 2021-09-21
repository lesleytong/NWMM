/**
 */
package petrinet.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import petrinet.Net;
import petrinet.PTArc;
import petrinet.PetrinetPackage;
import petrinet.Place;
import petrinet.TPArc;
import petrinet.Token;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Place</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link petrinet.impl.PlaceImpl#getName <em>Name</em>}</li>
 *   <li>{@link petrinet.impl.PlaceImpl#getNet <em>Net</em>}</li>
 *   <li>{@link petrinet.impl.PlaceImpl#getOut <em>Out</em>}</li>
 *   <li>{@link petrinet.impl.PlaceImpl#getIn <em>In</em>}</li>
 *   <li>{@link petrinet.impl.PlaceImpl#getToken <em>Token</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PlaceImpl extends MinimalEObjectImpl.Container implements Place {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getOut() <em>Out</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOut()
	 * @generated
	 * @ordered
	 */
	protected EList<PTArc> out;

	/**
	 * The cached value of the '{@link #getIn() <em>In</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIn()
	 * @generated
	 * @ordered
	 */
	protected EList<TPArc> in;

	/**
	 * The cached value of the '{@link #getToken() <em>Token</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getToken()
	 * @generated
	 * @ordered
	 */
	protected EList<Token> token;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PlaceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PetrinetPackage.Literals.PLACE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PetrinetPackage.PLACE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Net getNet() {
		if (eContainerFeatureID() != PetrinetPackage.PLACE__NET) return null;
		return (Net)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNet(Net newNet, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newNet, PetrinetPackage.PLACE__NET, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNet(Net newNet) {
		if (newNet != eInternalContainer() || (eContainerFeatureID() != PetrinetPackage.PLACE__NET && newNet != null)) {
			if (EcoreUtil.isAncestor(this, newNet))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newNet != null)
				msgs = ((InternalEObject)newNet).eInverseAdd(this, PetrinetPackage.NET__PLACE, Net.class, msgs);
			msgs = basicSetNet(newNet, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PetrinetPackage.PLACE__NET, newNet, newNet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PTArc> getOut() {
		if (out == null) {
			out = new EObjectWithInverseResolvingEList<PTArc>(PTArc.class, this, PetrinetPackage.PLACE__OUT, PetrinetPackage.PT_ARC__SRC);
		}
		return out;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TPArc> getIn() {
		if (in == null) {
			in = new EObjectWithInverseResolvingEList<TPArc>(TPArc.class, this, PetrinetPackage.PLACE__IN, PetrinetPackage.TP_ARC__DST);
		}
		return in;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Token> getToken() {
		if (token == null) {
			token = new EObjectContainmentWithInverseEList<Token>(Token.class, this, PetrinetPackage.PLACE__TOKEN, PetrinetPackage.TOKEN__PLACE);
		}
		return token;
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
			case PetrinetPackage.PLACE__NET:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetNet((Net)otherEnd, msgs);
			case PetrinetPackage.PLACE__OUT:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOut()).basicAdd(otherEnd, msgs);
			case PetrinetPackage.PLACE__IN:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getIn()).basicAdd(otherEnd, msgs);
			case PetrinetPackage.PLACE__TOKEN:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getToken()).basicAdd(otherEnd, msgs);
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
			case PetrinetPackage.PLACE__NET:
				return basicSetNet(null, msgs);
			case PetrinetPackage.PLACE__OUT:
				return ((InternalEList<?>)getOut()).basicRemove(otherEnd, msgs);
			case PetrinetPackage.PLACE__IN:
				return ((InternalEList<?>)getIn()).basicRemove(otherEnd, msgs);
			case PetrinetPackage.PLACE__TOKEN:
				return ((InternalEList<?>)getToken()).basicRemove(otherEnd, msgs);
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
			case PetrinetPackage.PLACE__NET:
				return eInternalContainer().eInverseRemove(this, PetrinetPackage.NET__PLACE, Net.class, msgs);
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
			case PetrinetPackage.PLACE__NAME:
				return getName();
			case PetrinetPackage.PLACE__NET:
				return getNet();
			case PetrinetPackage.PLACE__OUT:
				return getOut();
			case PetrinetPackage.PLACE__IN:
				return getIn();
			case PetrinetPackage.PLACE__TOKEN:
				return getToken();
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
			case PetrinetPackage.PLACE__NAME:
				setName((String)newValue);
				return;
			case PetrinetPackage.PLACE__NET:
				setNet((Net)newValue);
				return;
			case PetrinetPackage.PLACE__OUT:
				getOut().clear();
				getOut().addAll((Collection<? extends PTArc>)newValue);
				return;
			case PetrinetPackage.PLACE__IN:
				getIn().clear();
				getIn().addAll((Collection<? extends TPArc>)newValue);
				return;
			case PetrinetPackage.PLACE__TOKEN:
				getToken().clear();
				getToken().addAll((Collection<? extends Token>)newValue);
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
			case PetrinetPackage.PLACE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case PetrinetPackage.PLACE__NET:
				setNet((Net)null);
				return;
			case PetrinetPackage.PLACE__OUT:
				getOut().clear();
				return;
			case PetrinetPackage.PLACE__IN:
				getIn().clear();
				return;
			case PetrinetPackage.PLACE__TOKEN:
				getToken().clear();
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
			case PetrinetPackage.PLACE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case PetrinetPackage.PLACE__NET:
				return getNet() != null;
			case PetrinetPackage.PLACE__OUT:
				return out != null && !out.isEmpty();
			case PetrinetPackage.PLACE__IN:
				return in != null && !in.isEmpty();
			case PetrinetPackage.PLACE__TOKEN:
				return token != null && !token.isEmpty();
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
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //PlaceImpl
