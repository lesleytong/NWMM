/**
 */
package compare.impl;

import compare.AttributeChange;
import compare.CompareFactory;
import compare.ComparePackage;
import compare.Comparison;
import compare.Conflict;
import compare.ConflictKind;
import compare.Diff;
import compare.DifferenceKind;
import compare.DifferenceSource;
import compare.DifferenceState;
import compare.Equivalence;
import compare.FeatureMapChange;
import compare.Match;
import compare.MatchResource;
import compare.ReferenceChange;
import compare.ResourceAttachmentChange;
import compare.ResourceLocationChange;

import my.MyPackage;

import my.impl.MyPackageImpl;

import org.eclipse.emf.common.util.Diagnostic;

import org.eclipse.emf.compare.utils.IEqualityHelper;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EGenericType;
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
public class ComparePackageImpl extends EPackageImpl implements ComparePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass comparisonEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass matchResourceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass matchEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass diffEClass = null;

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
	private EClass conflictEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass equivalenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum differenceKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum differenceSourceEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum differenceStateEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum conflictKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType eIterableEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType iEqualityHelperEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType diagnosticEDataType = null;

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
	 * @see compare.ComparePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ComparePackageImpl() {
		super(eNS_URI, CompareFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ComparePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ComparePackage init() {
		if (isInited) return (ComparePackage)EPackage.Registry.INSTANCE.getEPackage(ComparePackage.eNS_URI);

		// Obtain or create and register package
		Object registeredComparePackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		ComparePackageImpl theComparePackage = registeredComparePackage instanceof ComparePackageImpl ? (ComparePackageImpl)registeredComparePackage : new ComparePackageImpl();

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(MyPackage.eNS_URI);
		MyPackageImpl theMyPackage = (MyPackageImpl)(registeredPackage instanceof MyPackageImpl ? registeredPackage : MyPackage.eINSTANCE);

		// Create package meta-data objects
		theComparePackage.createPackageContents();
		theMyPackage.createPackageContents();

		// Initialize created meta-data
		theComparePackage.initializePackageContents();
		theMyPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theComparePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ComparePackage.eNS_URI, theComparePackage);
		return theComparePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getComparison() {
		return comparisonEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getComparison_MatchedResources() {
		return (EReference)comparisonEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getComparison_Matches() {
		return (EReference)comparisonEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getComparison_Conflicts() {
		return (EReference)comparisonEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getComparison_Equivalences() {
		return (EReference)comparisonEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getComparison_ThreeWay() {
		return (EAttribute)comparisonEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getComparison_Diagnostic() {
		return (EAttribute)comparisonEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getComparison__GetDifferences() {
		return comparisonEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getComparison__GetDifferences__EObject() {
		return comparisonEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getComparison__GetMatch__EObject() {
		return comparisonEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getComparison__GetEqualityHelper() {
		return comparisonEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMatchResource() {
		return matchResourceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMatchResource_LeftURI() {
		return (EAttribute)matchResourceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMatchResource_RightURI() {
		return (EAttribute)matchResourceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMatchResource_OriginURI() {
		return (EAttribute)matchResourceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMatchResource_Left() {
		return (EAttribute)matchResourceEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMatchResource_Right() {
		return (EAttribute)matchResourceEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMatchResource_Origin() {
		return (EAttribute)matchResourceEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMatchResource_Comparison() {
		return (EReference)matchResourceEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMatchResource_LocationChanges() {
		return (EReference)matchResourceEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMatch() {
		return matchEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMatch_Submatches() {
		return (EReference)matchEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMatch_Differences() {
		return (EReference)matchEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMatch_Left() {
		return (EReference)matchEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMatch_Right() {
		return (EReference)matchEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMatch_Origin() {
		return (EReference)matchEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getMatch__GetComparison() {
		return matchEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getMatch__GetAllSubmatches() {
		return matchEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getMatch__GetAllDifferences() {
		return matchEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDiff() {
		return diffEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDiff_Match() {
		return (EReference)diffEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDiff_Requires() {
		return (EReference)diffEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDiff_RequiredBy() {
		return (EReference)diffEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDiff_Implies() {
		return (EReference)diffEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDiff_ImpliedBy() {
		return (EReference)diffEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDiff_Refines() {
		return (EReference)diffEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDiff_RefinedBy() {
		return (EReference)diffEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDiff_PrimeRefining() {
		return (EReference)diffEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDiff_Kind() {
		return (EAttribute)diffEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDiff_Source() {
		return (EAttribute)diffEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDiff_State() {
		return (EAttribute)diffEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDiff_Equivalence() {
		return (EReference)diffEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDiff_Conflict() {
		return (EReference)diffEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getDiff__CopyRightToLeft() {
		return diffEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getDiff__CopyLeftToRight() {
		return diffEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getDiff__Discard() {
		return diffEClass.getEOperations().get(2);
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
	public EClass getConflict() {
		return conflictEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getConflict_Kind() {
		return (EAttribute)conflictEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getConflict_Differences() {
		return (EReference)conflictEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getConflict__GetLeftDifferences() {
		return conflictEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getConflict__GetRightDifferences() {
		return conflictEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEquivalence() {
		return equivalenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEquivalence_Differences() {
		return (EReference)equivalenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getDifferenceKind() {
		return differenceKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getDifferenceSource() {
		return differenceSourceEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getDifferenceState() {
		return differenceStateEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getConflictKind() {
		return conflictKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getEIterable() {
		return eIterableEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getIEqualityHelper() {
		return iEqualityHelperEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getDiagnostic() {
		return diagnosticEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CompareFactory getCompareFactory() {
		return (CompareFactory)getEFactoryInstance();
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
		comparisonEClass = createEClass(COMPARISON);
		createEReference(comparisonEClass, COMPARISON__MATCHED_RESOURCES);
		createEReference(comparisonEClass, COMPARISON__MATCHES);
		createEReference(comparisonEClass, COMPARISON__CONFLICTS);
		createEReference(comparisonEClass, COMPARISON__EQUIVALENCES);
		createEAttribute(comparisonEClass, COMPARISON__THREE_WAY);
		createEAttribute(comparisonEClass, COMPARISON__DIAGNOSTIC);
		createEOperation(comparisonEClass, COMPARISON___GET_DIFFERENCES);
		createEOperation(comparisonEClass, COMPARISON___GET_DIFFERENCES__EOBJECT);
		createEOperation(comparisonEClass, COMPARISON___GET_MATCH__EOBJECT);
		createEOperation(comparisonEClass, COMPARISON___GET_EQUALITY_HELPER);

		matchResourceEClass = createEClass(MATCH_RESOURCE);
		createEAttribute(matchResourceEClass, MATCH_RESOURCE__LEFT_URI);
		createEAttribute(matchResourceEClass, MATCH_RESOURCE__RIGHT_URI);
		createEAttribute(matchResourceEClass, MATCH_RESOURCE__ORIGIN_URI);
		createEAttribute(matchResourceEClass, MATCH_RESOURCE__LEFT);
		createEAttribute(matchResourceEClass, MATCH_RESOURCE__RIGHT);
		createEAttribute(matchResourceEClass, MATCH_RESOURCE__ORIGIN);
		createEReference(matchResourceEClass, MATCH_RESOURCE__COMPARISON);
		createEReference(matchResourceEClass, MATCH_RESOURCE__LOCATION_CHANGES);

		matchEClass = createEClass(MATCH);
		createEReference(matchEClass, MATCH__SUBMATCHES);
		createEReference(matchEClass, MATCH__DIFFERENCES);
		createEReference(matchEClass, MATCH__LEFT);
		createEReference(matchEClass, MATCH__RIGHT);
		createEReference(matchEClass, MATCH__ORIGIN);
		createEOperation(matchEClass, MATCH___GET_COMPARISON);
		createEOperation(matchEClass, MATCH___GET_ALL_SUBMATCHES);
		createEOperation(matchEClass, MATCH___GET_ALL_DIFFERENCES);

		diffEClass = createEClass(DIFF);
		createEReference(diffEClass, DIFF__MATCH);
		createEReference(diffEClass, DIFF__REQUIRES);
		createEReference(diffEClass, DIFF__REQUIRED_BY);
		createEReference(diffEClass, DIFF__IMPLIES);
		createEReference(diffEClass, DIFF__IMPLIED_BY);
		createEReference(diffEClass, DIFF__REFINES);
		createEReference(diffEClass, DIFF__REFINED_BY);
		createEReference(diffEClass, DIFF__PRIME_REFINING);
		createEAttribute(diffEClass, DIFF__KIND);
		createEAttribute(diffEClass, DIFF__SOURCE);
		createEAttribute(diffEClass, DIFF__STATE);
		createEReference(diffEClass, DIFF__EQUIVALENCE);
		createEReference(diffEClass, DIFF__CONFLICT);
		createEOperation(diffEClass, DIFF___COPY_RIGHT_TO_LEFT);
		createEOperation(diffEClass, DIFF___COPY_LEFT_TO_RIGHT);
		createEOperation(diffEClass, DIFF___DISCARD);

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

		conflictEClass = createEClass(CONFLICT);
		createEAttribute(conflictEClass, CONFLICT__KIND);
		createEReference(conflictEClass, CONFLICT__DIFFERENCES);
		createEOperation(conflictEClass, CONFLICT___GET_LEFT_DIFFERENCES);
		createEOperation(conflictEClass, CONFLICT___GET_RIGHT_DIFFERENCES);

		equivalenceEClass = createEClass(EQUIVALENCE);
		createEReference(equivalenceEClass, EQUIVALENCE__DIFFERENCES);

		// Create enums
		differenceKindEEnum = createEEnum(DIFFERENCE_KIND);
		differenceSourceEEnum = createEEnum(DIFFERENCE_SOURCE);
		differenceStateEEnum = createEEnum(DIFFERENCE_STATE);
		conflictKindEEnum = createEEnum(CONFLICT_KIND);

		// Create data types
		eIterableEDataType = createEDataType(EITERABLE);
		iEqualityHelperEDataType = createEDataType(IEQUALITY_HELPER);
		diagnosticEDataType = createEDataType(DIAGNOSTIC);
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
		addETypeParameter(eIterableEDataType, "T");

		// Set bounds for type parameters

		// Add supertypes to classes
		resourceAttachmentChangeEClass.getESuperTypes().add(this.getDiff());
		resourceLocationChangeEClass.getESuperTypes().add(this.getDiff());
		referenceChangeEClass.getESuperTypes().add(this.getDiff());
		attributeChangeEClass.getESuperTypes().add(this.getDiff());
		featureMapChangeEClass.getESuperTypes().add(this.getDiff());

		// Initialize classes, features, and operations; add parameters
		initEClass(comparisonEClass, Comparison.class, "Comparison", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getComparison_MatchedResources(), this.getMatchResource(), this.getMatchResource_Comparison(), "matchedResources", null, 0, -1, Comparison.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComparison_Matches(), this.getMatch(), null, "matches", null, 0, -1, Comparison.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComparison_Conflicts(), this.getConflict(), null, "conflicts", null, 0, -1, Comparison.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComparison_Equivalences(), this.getEquivalence(), null, "equivalences", null, 0, -1, Comparison.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComparison_ThreeWay(), ecorePackage.getEBoolean(), "threeWay", null, 0, 1, Comparison.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComparison_Diagnostic(), this.getDiagnostic(), "diagnostic", null, 0, 1, Comparison.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getComparison__GetDifferences(), this.getDiff(), "getDifferences", 0, -1, IS_UNIQUE, IS_ORDERED);

		EOperation op = initEOperation(getComparison__GetDifferences__EObject(), this.getDiff(), "getDifferences", 0, -1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEObject(), "element", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getComparison__GetMatch__EObject(), this.getMatch(), "getMatch", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEObject(), "element", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEOperation(getComparison__GetEqualityHelper(), this.getIEqualityHelper(), "getEqualityHelper", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(matchResourceEClass, MatchResource.class, "MatchResource", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMatchResource_LeftURI(), ecorePackage.getEString(), "leftURI", null, 1, 1, MatchResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMatchResource_RightURI(), ecorePackage.getEString(), "rightURI", null, 1, 1, MatchResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMatchResource_OriginURI(), ecorePackage.getEString(), "originURI", null, 0, 1, MatchResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMatchResource_Left(), theEcorePackage.getEResource(), "left", null, 0, 1, MatchResource.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMatchResource_Right(), theEcorePackage.getEResource(), "right", null, 0, 1, MatchResource.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMatchResource_Origin(), theEcorePackage.getEResource(), "origin", null, 0, 1, MatchResource.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMatchResource_Comparison(), this.getComparison(), this.getComparison_MatchedResources(), "comparison", null, 0, 1, MatchResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMatchResource_LocationChanges(), this.getResourceLocationChange(), null, "locationChanges", null, 0, 2, MatchResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(matchEClass, Match.class, "Match", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMatch_Submatches(), this.getMatch(), null, "submatches", null, 0, -1, Match.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMatch_Differences(), this.getDiff(), null, "differences", null, 0, -1, Match.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMatch_Left(), ecorePackage.getEObject(), null, "left", null, 0, 1, Match.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMatch_Right(), ecorePackage.getEObject(), null, "right", null, 0, 1, Match.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMatch_Origin(), ecorePackage.getEObject(), null, "origin", null, 0, 1, Match.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getMatch__GetComparison(), this.getComparison(), "getComparison", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getMatch__GetAllSubmatches(), null, "getAllSubmatches", 0, 1, IS_UNIQUE, IS_ORDERED);
		EGenericType g1 = createEGenericType(this.getEIterable());
		EGenericType g2 = createEGenericType(this.getMatch());
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		op = initEOperation(getMatch__GetAllDifferences(), null, "getAllDifferences", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(this.getEIterable());
		g2 = createEGenericType(this.getDiff());
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		initEClass(diffEClass, Diff.class, "Diff", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDiff_Match(), this.getMatch(), null, "match", null, 0, 1, Diff.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDiff_Requires(), this.getDiff(), this.getDiff_RequiredBy(), "requires", null, 0, -1, Diff.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDiff_RequiredBy(), this.getDiff(), this.getDiff_Requires(), "requiredBy", null, 0, -1, Diff.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDiff_Implies(), this.getDiff(), this.getDiff_ImpliedBy(), "implies", null, 0, -1, Diff.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDiff_ImpliedBy(), this.getDiff(), this.getDiff_Implies(), "impliedBy", null, 0, -1, Diff.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDiff_Refines(), this.getDiff(), this.getDiff_RefinedBy(), "refines", null, 0, -1, Diff.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDiff_RefinedBy(), this.getDiff(), this.getDiff_Refines(), "refinedBy", null, 0, -1, Diff.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDiff_PrimeRefining(), this.getDiff(), null, "primeRefining", null, 0, 1, Diff.class, IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDiff_Kind(), this.getDifferenceKind(), "kind", null, 1, 1, Diff.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDiff_Source(), this.getDifferenceSource(), "source", null, 1, 1, Diff.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDiff_State(), this.getDifferenceState(), "state", null, 1, 1, Diff.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDiff_Equivalence(), this.getEquivalence(), this.getEquivalence_Differences(), "equivalence", null, 0, 1, Diff.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDiff_Conflict(), this.getConflict(), this.getConflict_Differences(), "conflict", null, 0, 1, Diff.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getDiff__CopyRightToLeft(), null, "copyRightToLeft", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEOperation(getDiff__CopyLeftToRight(), null, "copyLeftToRight", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEOperation(getDiff__Discard(), null, "discard", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(resourceAttachmentChangeEClass, ResourceAttachmentChange.class, "ResourceAttachmentChange", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getResourceAttachmentChange_ResourceURI(), ecorePackage.getEString(), "resourceURI", null, 1, 1, ResourceAttachmentChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(resourceLocationChangeEClass, ResourceLocationChange.class, "ResourceLocationChange", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getResourceLocationChange_BaseLocation(), ecorePackage.getEString(), "baseLocation", null, 1, 1, ResourceLocationChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getResourceLocationChange_ChangedLocation(), ecorePackage.getEString(), "changedLocation", null, 1, 1, ResourceLocationChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(referenceChangeEClass, ReferenceChange.class, "ReferenceChange", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getReferenceChange_Reference(), theEcorePackage.getEReference(), null, "reference", null, 1, 1, ReferenceChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getReferenceChange_Value(), theEcorePackage.getEObject(), null, "value", null, 0, 1, ReferenceChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(attributeChangeEClass, AttributeChange.class, "AttributeChange", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAttributeChange_Attribute(), theEcorePackage.getEAttribute(), null, "attribute", null, 1, 1, AttributeChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAttributeChange_Value(), theEcorePackage.getEJavaObject(), "value", null, 0, 1, AttributeChange.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(featureMapChangeEClass, FeatureMapChange.class, "FeatureMapChange", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFeatureMapChange_Attribute(), theEcorePackage.getEAttribute(), null, "attribute", null, 1, 1, FeatureMapChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFeatureMapChange_Value(), theEcorePackage.getEJavaObject(), "value", null, 0, 1, FeatureMapChange.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(conflictEClass, Conflict.class, "Conflict", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getConflict_Kind(), this.getConflictKind(), "kind", null, 1, 1, Conflict.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConflict_Differences(), this.getDiff(), this.getDiff_Conflict(), "differences", null, 2, -1, Conflict.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getConflict__GetLeftDifferences(), this.getDiff(), "getLeftDifferences", 1, -1, IS_UNIQUE, IS_ORDERED);

		initEOperation(getConflict__GetRightDifferences(), this.getDiff(), "getRightDifferences", 1, -1, IS_UNIQUE, IS_ORDERED);

		initEClass(equivalenceEClass, Equivalence.class, "Equivalence", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEquivalence_Differences(), this.getDiff(), this.getDiff_Equivalence(), "differences", null, 2, -1, Equivalence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(differenceKindEEnum, DifferenceKind.class, "DifferenceKind");
		addEEnumLiteral(differenceKindEEnum, DifferenceKind.ADD);
		addEEnumLiteral(differenceKindEEnum, DifferenceKind.DELETE);
		addEEnumLiteral(differenceKindEEnum, DifferenceKind.CHANGE);
		addEEnumLiteral(differenceKindEEnum, DifferenceKind.MOVE);

		initEEnum(differenceSourceEEnum, DifferenceSource.class, "DifferenceSource");
		addEEnumLiteral(differenceSourceEEnum, DifferenceSource.LEFT);
		addEEnumLiteral(differenceSourceEEnum, DifferenceSource.RIGHT);

		initEEnum(differenceStateEEnum, DifferenceState.class, "DifferenceState");
		addEEnumLiteral(differenceStateEEnum, DifferenceState.UNRESOLVED);
		addEEnumLiteral(differenceStateEEnum, DifferenceState.MERGED);
		addEEnumLiteral(differenceStateEEnum, DifferenceState.DISCARDED);
		addEEnumLiteral(differenceStateEEnum, DifferenceState.MERGING);

		initEEnum(conflictKindEEnum, ConflictKind.class, "ConflictKind");
		addEEnumLiteral(conflictKindEEnum, ConflictKind.REAL);
		addEEnumLiteral(conflictKindEEnum, ConflictKind.PSEUDO);

		// Initialize data types
		initEDataType(eIterableEDataType, Iterable.class, "EIterable", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(iEqualityHelperEDataType, IEqualityHelper.class, "IEqualityHelper", !IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(diagnosticEDataType, Diagnostic.class, "Diagnostic", !IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //ComparePackageImpl
