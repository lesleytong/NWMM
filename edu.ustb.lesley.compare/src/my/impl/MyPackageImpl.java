/**
 */
package my.impl;

import compare.ComparePackage;
import compare.impl.ComparePackageImpl;
import my.AttributeChange;
import my.ComparisonN;
import my.ConflictN;
import my.DiffN;
import my.FeatureMapChange;
import my.MatchN;
import my.MyFactory;
import my.MyPackage;
import my.ReferenceChange;
import my.ResourceAttachmentChange;
import my.ResourceLocationChange;
import org.eclipse.emf.compare.ConflictKind;
import org.eclipse.emf.compare.DifferenceKind;
import org.eclipse.emf.compare.DifferenceState;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MyPackageImpl extends EPackageImpl implements MyPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass comparisonNEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass matchNEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass diffNEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass conflictNEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass resourceAttachmentChangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass resourceLocationChangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass referenceChangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributeChangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass featureMapChangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType conflictKindEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType differenceKindEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType differenceStateEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see my.MyPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private MyPackageImpl() {
		super(eNS_URI, MyFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link MyPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static MyPackage init() {
		if (isInited) return (MyPackage)EPackage.Registry.INSTANCE.getEPackage(MyPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredMyPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		MyPackageImpl theMyPackage = registeredMyPackage instanceof MyPackageImpl ? (MyPackageImpl)registeredMyPackage : new MyPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(ComparePackage.eNS_URI);
		ComparePackageImpl theComparePackage = (ComparePackageImpl)(registeredPackage instanceof ComparePackageImpl ? registeredPackage : ComparePackage.eINSTANCE);

		// Create package meta-data objects
		theMyPackage.createPackageContents();
		theComparePackage.createPackageContents();

		// Initialize created meta-data
		theMyPackage.initializePackageContents();
		theComparePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theMyPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(MyPackage.eNS_URI, theMyPackage);
		return theMyPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getComparisonN() {
		return comparisonNEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getComparisonN_Matches() {
		return (EReference)comparisonNEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getComparisonN_Conflicts() {
		return (EReference)comparisonNEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getComparisonN__GetDifferences() {
		return comparisonNEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMatchN() {
		return matchNEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMatchN_Differences() {
		return (EReference)matchNEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMatchN_Base() {
		return (EReference)matchNEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMatchN_Branches() {
		return (EReference)matchNEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getMatchN__GetComparisonN() {
		return matchNEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDiffN() {
		return diffNEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDiffN_Match() {
		return (EReference)diffNEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDiffN_Kind() {
		return (EAttribute)diffNEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDiffN_State() {
		return (EAttribute)diffNEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDiffN_Conflict() {
		return (EReference)diffNEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getConflictN() {
		return conflictNEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getConflictN_Differences() {
		return (EReference)conflictNEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getConflictN_Kind() {
		return (EAttribute)conflictNEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getResourceAttachmentChange() {
		return resourceAttachmentChangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getResourceAttachmentChange_ResourceURI() {
		return (EAttribute)resourceAttachmentChangeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getResourceLocationChange() {
		return resourceLocationChangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getResourceLocationChange_BaseLocation() {
		return (EAttribute)resourceLocationChangeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getResourceLocationChange_ChangedLocation() {
		return (EAttribute)resourceLocationChangeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getReferenceChange() {
		return referenceChangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getReferenceChange_Reference() {
		return (EReference)referenceChangeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getReferenceChange_Value() {
		return (EReference)referenceChangeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAttributeChange() {
		return attributeChangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAttributeChange_Attribute() {
		return (EReference)attributeChangeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAttributeChange_Value() {
		return (EAttribute)attributeChangeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFeatureMapChange() {
		return featureMapChangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFeatureMapChange_Attribute() {
		return (EReference)featureMapChangeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFeatureMapChange_Value() {
		return (EAttribute)featureMapChangeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getConflictKind() {
		return conflictKindEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getDifferenceKind() {
		return differenceKindEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getDifferenceState() {
		return differenceStateEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MyFactory getMyFactory() {
		return (MyFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		comparisonNEClass = createEClass(COMPARISON_N);
		createEReference(comparisonNEClass, COMPARISON_N__MATCHES);
		createEReference(comparisonNEClass, COMPARISON_N__CONFLICTS);
		createEOperation(comparisonNEClass, COMPARISON_N___GET_DIFFERENCES);

		matchNEClass = createEClass(MATCH_N);
		createEReference(matchNEClass, MATCH_N__DIFFERENCES);
		createEReference(matchNEClass, MATCH_N__BASE);
		createEReference(matchNEClass, MATCH_N__BRANCHES);
		createEOperation(matchNEClass, MATCH_N___GET_COMPARISON_N);

		diffNEClass = createEClass(DIFF_N);
		createEReference(diffNEClass, DIFF_N__CONFLICT);
		createEReference(diffNEClass, DIFF_N__MATCH);
		createEAttribute(diffNEClass, DIFF_N__KIND);
		createEAttribute(diffNEClass, DIFF_N__STATE);

		conflictNEClass = createEClass(CONFLICT_N);
		createEReference(conflictNEClass, CONFLICT_N__DIFFERENCES);
		createEAttribute(conflictNEClass, CONFLICT_N__KIND);

		resourceAttachmentChangeEClass = createEClass(RESOURCE_ATTACHMENT_CHANGE);
		createEAttribute(resourceAttachmentChangeEClass, RESOURCE_ATTACHMENT_CHANGE__RESOURCE_URI);

		resourceLocationChangeEClass = createEClass(RESOURCE_LOCATION_CHANGE);
		createEAttribute(resourceLocationChangeEClass, RESOURCE_LOCATION_CHANGE__BASE_LOCATION);
		createEAttribute(resourceLocationChangeEClass, RESOURCE_LOCATION_CHANGE__CHANGED_LOCATION);

		referenceChangeEClass = createEClass(REFERENCE_CHANGE);
		createEReference(referenceChangeEClass, REFERENCE_CHANGE__REFERENCE);
		createEReference(referenceChangeEClass, REFERENCE_CHANGE__VALUE);

		attributeChangeEClass = createEClass(ATTRIBUTE_CHANGE);
		createEReference(attributeChangeEClass, ATTRIBUTE_CHANGE__ATTRIBUTE);
		createEAttribute(attributeChangeEClass, ATTRIBUTE_CHANGE__VALUE);

		featureMapChangeEClass = createEClass(FEATURE_MAP_CHANGE);
		createEReference(featureMapChangeEClass, FEATURE_MAP_CHANGE__ATTRIBUTE);
		createEAttribute(featureMapChangeEClass, FEATURE_MAP_CHANGE__VALUE);

		// Create data types
		conflictKindEDataType = createEDataType(CONFLICT_KIND);
		differenceKindEDataType = createEDataType(DIFFERENCE_KIND);
		differenceStateEDataType = createEDataType(DIFFERENCE_STATE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		resourceAttachmentChangeEClass.getESuperTypes().add(this.getDiffN());
		resourceLocationChangeEClass.getESuperTypes().add(this.getDiffN());
		referenceChangeEClass.getESuperTypes().add(this.getDiffN());
		attributeChangeEClass.getESuperTypes().add(this.getDiffN());
		featureMapChangeEClass.getESuperTypes().add(this.getDiffN());

		// Initialize classes, features, and operations; add parameters
		initEClass(comparisonNEClass, ComparisonN.class, "ComparisonN", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getComparisonN_Matches(), this.getMatchN(), null, "matches", null, 0, -1, ComparisonN.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComparisonN_Conflicts(), this.getConflictN(), null, "conflicts", null, 0, -1, ComparisonN.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getComparisonN__GetDifferences(), this.getDiffN(), "getDifferences", 0, -1, IS_UNIQUE, IS_ORDERED);

		initEClass(matchNEClass, MatchN.class, "MatchN", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMatchN_Differences(), this.getDiffN(), this.getDiffN_Match(), "differences", null, 0, -1, MatchN.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMatchN_Base(), ecorePackage.getEObject(), null, "base", null, 0, 1, MatchN.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMatchN_Branches(), ecorePackage.getEObject(), null, "branches", null, 0, -1, MatchN.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getMatchN__GetComparisonN(), this.getComparisonN(), "getComparisonN", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(diffNEClass, DiffN.class, "DiffN", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDiffN_Conflict(), this.getConflictN(), this.getConflictN_Differences(), "conflict", null, 0, 1, DiffN.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDiffN_Match(), this.getMatchN(), this.getMatchN_Differences(), "match", null, 1, 1, DiffN.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDiffN_Kind(), this.getDifferenceKind(), "kind", null, 0, 1, DiffN.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDiffN_State(), this.getDifferenceState(), "state", null, 0, 1, DiffN.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(conflictNEClass, ConflictN.class, "ConflictN", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConflictN_Differences(), this.getDiffN(), this.getDiffN_Conflict(), "differences", null, 2, -1, ConflictN.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConflictN_Kind(), this.getConflictKind(), "kind", null, 0, 1, ConflictN.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(resourceAttachmentChangeEClass, ResourceAttachmentChange.class, "ResourceAttachmentChange", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getResourceAttachmentChange_ResourceURI(), theEcorePackage.getEString(), "resourceURI", null, 1, 1, ResourceAttachmentChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(resourceLocationChangeEClass, ResourceLocationChange.class, "ResourceLocationChange", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getResourceLocationChange_BaseLocation(), theEcorePackage.getEString(), "baseLocation", null, 1, 1, ResourceLocationChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getResourceLocationChange_ChangedLocation(), theEcorePackage.getEString(), "changedLocation", null, 1, 1, ResourceLocationChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(referenceChangeEClass, ReferenceChange.class, "ReferenceChange", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getReferenceChange_Reference(), theEcorePackage.getEReference(), null, "reference", null, 1, 1, ReferenceChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getReferenceChange_Value(), theEcorePackage.getEObject(), null, "value", null, 0, 1, ReferenceChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(attributeChangeEClass, AttributeChange.class, "AttributeChange", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAttributeChange_Attribute(), theEcorePackage.getEAttribute(), null, "attribute", null, 1, 1, AttributeChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAttributeChange_Value(), theEcorePackage.getEJavaObject(), "value", null, 0, 1, AttributeChange.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(featureMapChangeEClass, FeatureMapChange.class, "FeatureMapChange", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFeatureMapChange_Attribute(), theEcorePackage.getEAttribute(), null, "attribute", null, 1, 1, FeatureMapChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFeatureMapChange_Value(), theEcorePackage.getEJavaObject(), "value", null, 0, 1, FeatureMapChange.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize data types
		initEDataType(conflictKindEDataType, ConflictKind.class, "ConflictKind", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(differenceKindEDataType, DifferenceKind.class, "DifferenceKind", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(differenceStateEDataType, DifferenceState.class, "DifferenceState", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //MyPackageImpl
