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

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import reqtify.AbstractRequirement;
import reqtify.Document;
import reqtify.ReqtifyPackage;
import reqtify.Section;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Section</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link reqtify.impl.SectionImpl#getDocument <em>Document</em>}</li>
 *   <li>{@link reqtify.impl.SectionImpl#getSectionChildren <em>Section Children</em>}</li>
 *   <li>{@link reqtify.impl.SectionImpl#getSectionParent <em>Section Parent</em>}</li>
 *   <li>{@link reqtify.impl.SectionImpl#getRequirements <em>Requirements</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SectionImpl extends TextElementImpl implements Section {
	/**
	 * The cached value of the '{@link #getSectionChildren() <em>Section Children</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSectionChildren()
	 * @generated
	 * @ordered
	 */
	protected EList<Section> sectionChildren;

	/**
	 * The cached value of the '{@link #getRequirements() <em>Requirements</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRequirements()
	 * @generated
	 * @ordered
	 */
	protected EList<AbstractRequirement> requirements;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SectionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ReqtifyPackage.Literals.SECTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Document getDocument() {
		if (eContainerFeatureID() != ReqtifyPackage.SECTION__DOCUMENT) return null;
		return (Document)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDocument(Document newDocument, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newDocument, ReqtifyPackage.SECTION__DOCUMENT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDocument(Document newDocument) {
		if (newDocument != eInternalContainer() || (eContainerFeatureID() != ReqtifyPackage.SECTION__DOCUMENT && newDocument != null)) {
			if (EcoreUtil.isAncestor(this, newDocument))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newDocument != null)
				msgs = ((InternalEObject)newDocument).eInverseAdd(this, ReqtifyPackage.DOCUMENT__SECTIONS, Document.class, msgs);
			msgs = basicSetDocument(newDocument, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ReqtifyPackage.SECTION__DOCUMENT, newDocument, newDocument));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Section> getSectionChildren() {
		if (sectionChildren == null) {
			sectionChildren = new EObjectContainmentWithInverseEList<Section>(Section.class, this, ReqtifyPackage.SECTION__SECTION_CHILDREN, ReqtifyPackage.SECTION__SECTION_PARENT);
		}
		return sectionChildren;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Section getSectionParent() {
		if (eContainerFeatureID() != ReqtifyPackage.SECTION__SECTION_PARENT) return null;
		return (Section)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSectionParent(Section newSectionParent, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newSectionParent, ReqtifyPackage.SECTION__SECTION_PARENT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSectionParent(Section newSectionParent) {
		if (newSectionParent != eInternalContainer() || (eContainerFeatureID() != ReqtifyPackage.SECTION__SECTION_PARENT && newSectionParent != null)) {
			if (EcoreUtil.isAncestor(this, newSectionParent))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newSectionParent != null)
				msgs = ((InternalEObject)newSectionParent).eInverseAdd(this, ReqtifyPackage.SECTION__SECTION_CHILDREN, Section.class, msgs);
			msgs = basicSetSectionParent(newSectionParent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ReqtifyPackage.SECTION__SECTION_PARENT, newSectionParent, newSectionParent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AbstractRequirement> getRequirements() {
		if (requirements == null) {
			requirements = new EObjectContainmentWithInverseEList<AbstractRequirement>(AbstractRequirement.class, this, ReqtifyPackage.SECTION__REQUIREMENTS, ReqtifyPackage.ABSTRACT_REQUIREMENT__SECTION);
		}
		return requirements;
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
			case ReqtifyPackage.SECTION__DOCUMENT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetDocument((Document)otherEnd, msgs);
			case ReqtifyPackage.SECTION__SECTION_CHILDREN:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getSectionChildren()).basicAdd(otherEnd, msgs);
			case ReqtifyPackage.SECTION__SECTION_PARENT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetSectionParent((Section)otherEnd, msgs);
			case ReqtifyPackage.SECTION__REQUIREMENTS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getRequirements()).basicAdd(otherEnd, msgs);
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
			case ReqtifyPackage.SECTION__DOCUMENT:
				return basicSetDocument(null, msgs);
			case ReqtifyPackage.SECTION__SECTION_CHILDREN:
				return ((InternalEList<?>)getSectionChildren()).basicRemove(otherEnd, msgs);
			case ReqtifyPackage.SECTION__SECTION_PARENT:
				return basicSetSectionParent(null, msgs);
			case ReqtifyPackage.SECTION__REQUIREMENTS:
				return ((InternalEList<?>)getRequirements()).basicRemove(otherEnd, msgs);
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
			case ReqtifyPackage.SECTION__DOCUMENT:
				return eInternalContainer().eInverseRemove(this, ReqtifyPackage.DOCUMENT__SECTIONS, Document.class, msgs);
			case ReqtifyPackage.SECTION__SECTION_PARENT:
				return eInternalContainer().eInverseRemove(this, ReqtifyPackage.SECTION__SECTION_CHILDREN, Section.class, msgs);
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
			case ReqtifyPackage.SECTION__DOCUMENT:
				return getDocument();
			case ReqtifyPackage.SECTION__SECTION_CHILDREN:
				return getSectionChildren();
			case ReqtifyPackage.SECTION__SECTION_PARENT:
				return getSectionParent();
			case ReqtifyPackage.SECTION__REQUIREMENTS:
				return getRequirements();
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
			case ReqtifyPackage.SECTION__DOCUMENT:
				setDocument((Document)newValue);
				return;
			case ReqtifyPackage.SECTION__SECTION_CHILDREN:
				getSectionChildren().clear();
				getSectionChildren().addAll((Collection<? extends Section>)newValue);
				return;
			case ReqtifyPackage.SECTION__SECTION_PARENT:
				setSectionParent((Section)newValue);
				return;
			case ReqtifyPackage.SECTION__REQUIREMENTS:
				getRequirements().clear();
				getRequirements().addAll((Collection<? extends AbstractRequirement>)newValue);
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
			case ReqtifyPackage.SECTION__DOCUMENT:
				setDocument((Document)null);
				return;
			case ReqtifyPackage.SECTION__SECTION_CHILDREN:
				getSectionChildren().clear();
				return;
			case ReqtifyPackage.SECTION__SECTION_PARENT:
				setSectionParent((Section)null);
				return;
			case ReqtifyPackage.SECTION__REQUIREMENTS:
				getRequirements().clear();
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
			case ReqtifyPackage.SECTION__DOCUMENT:
				return getDocument() != null;
			case ReqtifyPackage.SECTION__SECTION_CHILDREN:
				return sectionChildren != null && !sectionChildren.isEmpty();
			case ReqtifyPackage.SECTION__SECTION_PARENT:
				return getSectionParent() != null;
			case ReqtifyPackage.SECTION__REQUIREMENTS:
				return requirements != null && !requirements.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //SectionImpl
