/**
 */
package Repository.impl;

import Repository.Feature;
import Repository.Relation;
import Repository.RepositoryPackage;
import Repository.Subsystem;

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

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Class</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link Repository.impl.ClassImpl#getName <em>Name</em>}</li>
 *   <li>{@link Repository.impl.ClassImpl#isIs_deferred <em>Is deferred</em>}</li>
 *   <li>{@link Repository.impl.ClassImpl#getFeatures <em>Features</em>}</li>
 *   <li>{@link Repository.impl.ClassImpl#getParentSubsystem <em>Parent Subsystem</em>}</li>
 *   <li>{@link Repository.impl.ClassImpl#getSubsystem <em>Subsystem</em>}</li>
 *   <li>{@link Repository.impl.ClassImpl#getRelations <em>Relations</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ClassImpl extends MinimalEObjectImpl.Container implements Repository.Class {
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
	 * The default value of the '{@link #isIs_deferred() <em>Is deferred</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIs_deferred()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_DEFERRED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIs_deferred() <em>Is deferred</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIs_deferred()
	 * @generated
	 * @ordered
	 */
	protected boolean is_deferred = IS_DEFERRED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFeatures() <em>Features</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeatures()
	 * @generated
	 * @ordered
	 */
	protected EList<Feature> features;

	/**
	 * The cached value of the '{@link #getSubsystem() <em>Subsystem</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubsystem()
	 * @generated
	 * @ordered
	 */
	protected Subsystem subsystem;

	/**
	 * The cached value of the '{@link #getRelations() <em>Relations</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRelations()
	 * @generated
	 * @ordered
	 */
	protected EList<Relation> relations;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ClassImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepositoryPackage.Literals.CLASS;
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
			eNotify(new ENotificationImpl(this, Notification.SET, RepositoryPackage.CLASS__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIs_deferred() {
		return is_deferred;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIs_deferred(boolean newIs_deferred) {
		boolean oldIs_deferred = is_deferred;
		is_deferred = newIs_deferred;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RepositoryPackage.CLASS__IS_DEFERRED, oldIs_deferred, is_deferred));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Feature> getFeatures() {
		if (features == null) {
			features = new EObjectContainmentWithInverseEList<Feature>(Feature.class, this, RepositoryPackage.CLASS__FEATURES, RepositoryPackage.FEATURE__THE_CLASS);
		}
		return features;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Subsystem getParentSubsystem() {
		if (eContainerFeatureID() != RepositoryPackage.CLASS__PARENT_SUBSYSTEM) return null;
		return (Subsystem)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParentSubsystem(Subsystem newParentSubsystem, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newParentSubsystem, RepositoryPackage.CLASS__PARENT_SUBSYSTEM, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentSubsystem(Subsystem newParentSubsystem) {
		if (newParentSubsystem != eInternalContainer() || (eContainerFeatureID() != RepositoryPackage.CLASS__PARENT_SUBSYSTEM && newParentSubsystem != null)) {
			if (EcoreUtil.isAncestor(this, newParentSubsystem))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newParentSubsystem != null)
				msgs = ((InternalEObject)newParentSubsystem).eInverseAdd(this, RepositoryPackage.SUBSYSTEM__CLASSES, Subsystem.class, msgs);
			msgs = basicSetParentSubsystem(newParentSubsystem, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RepositoryPackage.CLASS__PARENT_SUBSYSTEM, newParentSubsystem, newParentSubsystem));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Subsystem getSubsystem() {
		if (subsystem != null && subsystem.eIsProxy()) {
			InternalEObject oldSubsystem = (InternalEObject)subsystem;
			subsystem = (Subsystem)eResolveProxy(oldSubsystem);
			if (subsystem != oldSubsystem) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RepositoryPackage.CLASS__SUBSYSTEM, oldSubsystem, subsystem));
			}
		}
		return subsystem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Subsystem basicGetSubsystem() {
		return subsystem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSubsystem(Subsystem newSubsystem) {
		Subsystem oldSubsystem = subsystem;
		subsystem = newSubsystem;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RepositoryPackage.CLASS__SUBSYSTEM, oldSubsystem, subsystem));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Relation> getRelations() {
		if (relations == null) {
			relations = new EObjectWithInverseResolvingEList.ManyInverse<Relation>(Relation.class, this, RepositoryPackage.CLASS__RELATIONS, RepositoryPackage.RELATION__CLASSES);
		}
		return relations;
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
			case RepositoryPackage.CLASS__FEATURES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getFeatures()).basicAdd(otherEnd, msgs);
			case RepositoryPackage.CLASS__PARENT_SUBSYSTEM:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetParentSubsystem((Subsystem)otherEnd, msgs);
			case RepositoryPackage.CLASS__RELATIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getRelations()).basicAdd(otherEnd, msgs);
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
			case RepositoryPackage.CLASS__FEATURES:
				return ((InternalEList<?>)getFeatures()).basicRemove(otherEnd, msgs);
			case RepositoryPackage.CLASS__PARENT_SUBSYSTEM:
				return basicSetParentSubsystem(null, msgs);
			case RepositoryPackage.CLASS__RELATIONS:
				return ((InternalEList<?>)getRelations()).basicRemove(otherEnd, msgs);
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
			case RepositoryPackage.CLASS__PARENT_SUBSYSTEM:
				return eInternalContainer().eInverseRemove(this, RepositoryPackage.SUBSYSTEM__CLASSES, Subsystem.class, msgs);
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
			case RepositoryPackage.CLASS__NAME:
				return getName();
			case RepositoryPackage.CLASS__IS_DEFERRED:
				return isIs_deferred();
			case RepositoryPackage.CLASS__FEATURES:
				return getFeatures();
			case RepositoryPackage.CLASS__PARENT_SUBSYSTEM:
				return getParentSubsystem();
			case RepositoryPackage.CLASS__SUBSYSTEM:
				if (resolve) return getSubsystem();
				return basicGetSubsystem();
			case RepositoryPackage.CLASS__RELATIONS:
				return getRelations();
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
			case RepositoryPackage.CLASS__NAME:
				setName((String)newValue);
				return;
			case RepositoryPackage.CLASS__IS_DEFERRED:
				setIs_deferred((Boolean)newValue);
				return;
			case RepositoryPackage.CLASS__FEATURES:
				getFeatures().clear();
				getFeatures().addAll((Collection<? extends Feature>)newValue);
				return;
			case RepositoryPackage.CLASS__PARENT_SUBSYSTEM:
				setParentSubsystem((Subsystem)newValue);
				return;
			case RepositoryPackage.CLASS__SUBSYSTEM:
				setSubsystem((Subsystem)newValue);
				return;
			case RepositoryPackage.CLASS__RELATIONS:
				getRelations().clear();
				getRelations().addAll((Collection<? extends Relation>)newValue);
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
			case RepositoryPackage.CLASS__NAME:
				setName(NAME_EDEFAULT);
				return;
			case RepositoryPackage.CLASS__IS_DEFERRED:
				setIs_deferred(IS_DEFERRED_EDEFAULT);
				return;
			case RepositoryPackage.CLASS__FEATURES:
				getFeatures().clear();
				return;
			case RepositoryPackage.CLASS__PARENT_SUBSYSTEM:
				setParentSubsystem((Subsystem)null);
				return;
			case RepositoryPackage.CLASS__SUBSYSTEM:
				setSubsystem((Subsystem)null);
				return;
			case RepositoryPackage.CLASS__RELATIONS:
				getRelations().clear();
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
			case RepositoryPackage.CLASS__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case RepositoryPackage.CLASS__IS_DEFERRED:
				return is_deferred != IS_DEFERRED_EDEFAULT;
			case RepositoryPackage.CLASS__FEATURES:
				return features != null && !features.isEmpty();
			case RepositoryPackage.CLASS__PARENT_SUBSYSTEM:
				return getParentSubsystem() != null;
			case RepositoryPackage.CLASS__SUBSYSTEM:
				return subsystem != null;
			case RepositoryPackage.CLASS__RELATIONS:
				return relations != null && !relations.isEmpty();
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
		result.append(", is_deferred: ");
		result.append(is_deferred);
		result.append(')');
		return result.toString();
	}

} //ClassImpl
