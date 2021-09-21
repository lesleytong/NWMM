/**
 */
package reqtify.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import reqtify.AbstractRequirement;
import reqtify.Attribute;
import reqtify.CoverLink;
import reqtify.MacroRequirement;
import reqtify.ReqtifyPackage;
import reqtify.Section;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abstract Requirement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link reqtify.impl.AbstractRequirementImpl#getSection <em>Section</em>}</li>
 *   <li>{@link reqtify.impl.AbstractRequirementImpl#getIsContained <em>Is Contained</em>}</li>
 *   <li>{@link reqtify.impl.AbstractRequirementImpl#getCoverLinks <em>Cover Links</em>}</li>
 *   <li>{@link reqtify.impl.AbstractRequirementImpl#getAttribute <em>Attribute</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class AbstractRequirementImpl extends TextElementImpl implements AbstractRequirement {
	/**
	 * The cached value of the '{@link #getCoverLinks() <em>Cover Links</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCoverLinks()
	 * @generated
	 * @ordered
	 */
	protected EList<CoverLink> coverLinks;

	/**
	 * The cached value of the '{@link #getAttribute() <em>Attribute</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttribute()
	 * @generated
	 * @ordered
	 */
	protected EList<Attribute> attribute;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractRequirementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ReqtifyPackage.Literals.ABSTRACT_REQUIREMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Section getSection() {
		if (eContainerFeatureID() != ReqtifyPackage.ABSTRACT_REQUIREMENT__SECTION) return null;
		return (Section)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSection(Section newSection, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newSection, ReqtifyPackage.ABSTRACT_REQUIREMENT__SECTION, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSection(Section newSection) {
		if (newSection != eInternalContainer() || (eContainerFeatureID() != ReqtifyPackage.ABSTRACT_REQUIREMENT__SECTION && newSection != null)) {
			if (EcoreUtil.isAncestor(this, newSection))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newSection != null)
				msgs = ((InternalEObject)newSection).eInverseAdd(this, ReqtifyPackage.SECTION__REQUIREMENTS, Section.class, msgs);
			msgs = basicSetSection(newSection, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ReqtifyPackage.ABSTRACT_REQUIREMENT__SECTION, newSection, newSection));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MacroRequirement getIsContained() {
		if (eContainerFeatureID() != ReqtifyPackage.ABSTRACT_REQUIREMENT__IS_CONTAINED) return null;
		return (MacroRequirement)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIsContained(MacroRequirement newIsContained, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newIsContained, ReqtifyPackage.ABSTRACT_REQUIREMENT__IS_CONTAINED, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsContained(MacroRequirement newIsContained) {
		if (newIsContained != eInternalContainer() || (eContainerFeatureID() != ReqtifyPackage.ABSTRACT_REQUIREMENT__IS_CONTAINED && newIsContained != null)) {
			if (EcoreUtil.isAncestor(this, newIsContained))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newIsContained != null)
				msgs = ((InternalEObject)newIsContained).eInverseAdd(this, ReqtifyPackage.MACRO_REQUIREMENT__CONTAINS, MacroRequirement.class, msgs);
			msgs = basicSetIsContained(newIsContained, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ReqtifyPackage.ABSTRACT_REQUIREMENT__IS_CONTAINED, newIsContained, newIsContained));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<CoverLink> getCoverLinks() {
		if (coverLinks == null) {
			coverLinks = new EObjectContainmentEList<CoverLink>(CoverLink.class, this, ReqtifyPackage.ABSTRACT_REQUIREMENT__COVER_LINKS);
		}
		return coverLinks;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Attribute> getAttribute() {
		if (attribute == null) {
			attribute = new EObjectContainmentEList<Attribute>(Attribute.class, this, ReqtifyPackage.ABSTRACT_REQUIREMENT__ATTRIBUTE);
		}
		return attribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ReqtifyPackage.ABSTRACT_REQUIREMENT__SECTION:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetSection((Section)otherEnd, msgs);
			case ReqtifyPackage.ABSTRACT_REQUIREMENT__IS_CONTAINED:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetIsContained((MacroRequirement)otherEnd, msgs);
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
			case ReqtifyPackage.ABSTRACT_REQUIREMENT__SECTION:
				return basicSetSection(null, msgs);
			case ReqtifyPackage.ABSTRACT_REQUIREMENT__IS_CONTAINED:
				return basicSetIsContained(null, msgs);
			case ReqtifyPackage.ABSTRACT_REQUIREMENT__COVER_LINKS:
				return ((InternalEList<?>)getCoverLinks()).basicRemove(otherEnd, msgs);
			case ReqtifyPackage.ABSTRACT_REQUIREMENT__ATTRIBUTE:
				return ((InternalEList<?>)getAttribute()).basicRemove(otherEnd, msgs);
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
			case ReqtifyPackage.ABSTRACT_REQUIREMENT__SECTION:
				return eInternalContainer().eInverseRemove(this, ReqtifyPackage.SECTION__REQUIREMENTS, Section.class, msgs);
			case ReqtifyPackage.ABSTRACT_REQUIREMENT__IS_CONTAINED:
				return eInternalContainer().eInverseRemove(this, ReqtifyPackage.MACRO_REQUIREMENT__CONTAINS, MacroRequirement.class, msgs);
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
			case ReqtifyPackage.ABSTRACT_REQUIREMENT__SECTION:
				return getSection();
			case ReqtifyPackage.ABSTRACT_REQUIREMENT__IS_CONTAINED:
				return getIsContained();
			case ReqtifyPackage.ABSTRACT_REQUIREMENT__COVER_LINKS:
				return getCoverLinks();
			case ReqtifyPackage.ABSTRACT_REQUIREMENT__ATTRIBUTE:
				return getAttribute();
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
			case ReqtifyPackage.ABSTRACT_REQUIREMENT__SECTION:
				setSection((Section)newValue);
				return;
			case ReqtifyPackage.ABSTRACT_REQUIREMENT__IS_CONTAINED:
				setIsContained((MacroRequirement)newValue);
				return;
			case ReqtifyPackage.ABSTRACT_REQUIREMENT__COVER_LINKS:
				getCoverLinks().clear();
				getCoverLinks().addAll((Collection<? extends CoverLink>)newValue);
				return;
			case ReqtifyPackage.ABSTRACT_REQUIREMENT__ATTRIBUTE:
				getAttribute().clear();
				getAttribute().addAll((Collection<? extends Attribute>)newValue);
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
			case ReqtifyPackage.ABSTRACT_REQUIREMENT__SECTION:
				setSection((Section)null);
				return;
			case ReqtifyPackage.ABSTRACT_REQUIREMENT__IS_CONTAINED:
				setIsContained((MacroRequirement)null);
				return;
			case ReqtifyPackage.ABSTRACT_REQUIREMENT__COVER_LINKS:
				getCoverLinks().clear();
				return;
			case ReqtifyPackage.ABSTRACT_REQUIREMENT__ATTRIBUTE:
				getAttribute().clear();
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
			case ReqtifyPackage.ABSTRACT_REQUIREMENT__SECTION:
				return getSection() != null;
			case ReqtifyPackage.ABSTRACT_REQUIREMENT__IS_CONTAINED:
				return getIsContained() != null;
			case ReqtifyPackage.ABSTRACT_REQUIREMENT__COVER_LINKS:
				return coverLinks != null && !coverLinks.isEmpty();
			case ReqtifyPackage.ABSTRACT_REQUIREMENT__ATTRIBUTE:
				return attribute != null && !attribute.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //AbstractRequirementImpl
