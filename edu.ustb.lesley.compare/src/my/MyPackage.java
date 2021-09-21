/**
 */
package my;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see my.MyFactory
 * @model kind="package"
 * @generated
 */
public interface MyPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "my";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "https://edu/ustb/lesley/my";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "edu.ustb.lesley.my";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	MyPackage eINSTANCE = my.impl.MyPackageImpl.init();

	/**
	 * The meta object id for the '{@link my.impl.ComparisonNImpl <em>Comparison N</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see my.impl.ComparisonNImpl
	 * @see my.impl.MyPackageImpl#getComparisonN()
	 * @generated
	 */
	int COMPARISON_N = 0;

	/**
	 * The feature id for the '<em><b>Matches</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARISON_N__MATCHES = 0;

	/**
	 * The feature id for the '<em><b>Conflicts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARISON_N__CONFLICTS = 1;

	/**
	 * The number of structural features of the '<em>Comparison N</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARISON_N_FEATURE_COUNT = 2;

	/**
	 * The operation id for the '<em>Get Differences</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARISON_N___GET_DIFFERENCES = 0;

	/**
	 * The number of operations of the '<em>Comparison N</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARISON_N_OPERATION_COUNT = 1;


	/**
	 * The meta object id for the '{@link my.impl.MatchNImpl <em>Match N</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see my.impl.MatchNImpl
	 * @see my.impl.MyPackageImpl#getMatchN()
	 * @generated
	 */
	int MATCH_N = 1;

	/**
	 * The feature id for the '<em><b>Differences</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATCH_N__DIFFERENCES = 0;

	/**
	 * The feature id for the '<em><b>Base</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATCH_N__BASE = 1;

	/**
	 * The feature id for the '<em><b>Branches</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATCH_N__BRANCHES = 2;

	/**
	 * The number of structural features of the '<em>Match N</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATCH_N_FEATURE_COUNT = 3;

	/**
	 * The operation id for the '<em>Get Comparison N</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATCH_N___GET_COMPARISON_N = 0;

	/**
	 * The number of operations of the '<em>Match N</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATCH_N_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link my.impl.DiffNImpl <em>Diff N</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see my.impl.DiffNImpl
	 * @see my.impl.MyPackageImpl#getDiffN()
	 * @generated
	 */
	int DIFF_N = 2;

	/**
	 * The feature id for the '<em><b>Conflict</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFF_N__CONFLICT = 0;

	/**
	 * The feature id for the '<em><b>Match</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFF_N__MATCH = 1;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFF_N__KIND = 2;

	/**
	 * The feature id for the '<em><b>State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFF_N__STATE = 3;

	/**
	 * The number of structural features of the '<em>Diff N</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFF_N_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Diff N</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFF_N_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link my.impl.ConflictNImpl <em>Conflict N</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see my.impl.ConflictNImpl
	 * @see my.impl.MyPackageImpl#getConflictN()
	 * @generated
	 */
	int CONFLICT_N = 3;

	/**
	 * The feature id for the '<em><b>Differences</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFLICT_N__DIFFERENCES = 0;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFLICT_N__KIND = 1;

	/**
	 * The number of structural features of the '<em>Conflict N</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFLICT_N_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Conflict N</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFLICT_N_OPERATION_COUNT = 0;


	/**
	 * The meta object id for the '{@link my.impl.ResourceAttachmentChangeImpl <em>Resource Attachment Change</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see my.impl.ResourceAttachmentChangeImpl
	 * @see my.impl.MyPackageImpl#getResourceAttachmentChange()
	 * @generated
	 */
	int RESOURCE_ATTACHMENT_CHANGE = 4;

	/**
	 * The feature id for the '<em><b>Conflict</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_ATTACHMENT_CHANGE__CONFLICT = DIFF_N__CONFLICT;

	/**
	 * The feature id for the '<em><b>Match</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_ATTACHMENT_CHANGE__MATCH = DIFF_N__MATCH;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_ATTACHMENT_CHANGE__KIND = DIFF_N__KIND;

	/**
	 * The feature id for the '<em><b>State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_ATTACHMENT_CHANGE__STATE = DIFF_N__STATE;

	/**
	 * The feature id for the '<em><b>Resource URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_ATTACHMENT_CHANGE__RESOURCE_URI = DIFF_N_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Resource Attachment Change</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_ATTACHMENT_CHANGE_FEATURE_COUNT = DIFF_N_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Resource Attachment Change</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_ATTACHMENT_CHANGE_OPERATION_COUNT = DIFF_N_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link my.impl.ResourceLocationChangeImpl <em>Resource Location Change</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see my.impl.ResourceLocationChangeImpl
	 * @see my.impl.MyPackageImpl#getResourceLocationChange()
	 * @generated
	 */
	int RESOURCE_LOCATION_CHANGE = 5;

	/**
	 * The feature id for the '<em><b>Conflict</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_LOCATION_CHANGE__CONFLICT = DIFF_N__CONFLICT;

	/**
	 * The feature id for the '<em><b>Match</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_LOCATION_CHANGE__MATCH = DIFF_N__MATCH;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_LOCATION_CHANGE__KIND = DIFF_N__KIND;

	/**
	 * The feature id for the '<em><b>State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_LOCATION_CHANGE__STATE = DIFF_N__STATE;

	/**
	 * The feature id for the '<em><b>Base Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_LOCATION_CHANGE__BASE_LOCATION = DIFF_N_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Changed Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_LOCATION_CHANGE__CHANGED_LOCATION = DIFF_N_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Resource Location Change</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_LOCATION_CHANGE_FEATURE_COUNT = DIFF_N_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Resource Location Change</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_LOCATION_CHANGE_OPERATION_COUNT = DIFF_N_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link my.impl.ReferenceChangeImpl <em>Reference Change</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see my.impl.ReferenceChangeImpl
	 * @see my.impl.MyPackageImpl#getReferenceChange()
	 * @generated
	 */
	int REFERENCE_CHANGE = 6;

	/**
	 * The feature id for the '<em><b>Conflict</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_CHANGE__CONFLICT = DIFF_N__CONFLICT;

	/**
	 * The feature id for the '<em><b>Match</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_CHANGE__MATCH = DIFF_N__MATCH;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_CHANGE__KIND = DIFF_N__KIND;

	/**
	 * The feature id for the '<em><b>State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_CHANGE__STATE = DIFF_N__STATE;

	/**
	 * The feature id for the '<em><b>Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_CHANGE__REFERENCE = DIFF_N_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_CHANGE__VALUE = DIFF_N_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Reference Change</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_CHANGE_FEATURE_COUNT = DIFF_N_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Reference Change</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_CHANGE_OPERATION_COUNT = DIFF_N_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link my.impl.AttributeChangeImpl <em>Attribute Change</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see my.impl.AttributeChangeImpl
	 * @see my.impl.MyPackageImpl#getAttributeChange()
	 * @generated
	 */
	int ATTRIBUTE_CHANGE = 7;

	/**
	 * The feature id for the '<em><b>Conflict</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CHANGE__CONFLICT = DIFF_N__CONFLICT;

	/**
	 * The feature id for the '<em><b>Match</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CHANGE__MATCH = DIFF_N__MATCH;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CHANGE__KIND = DIFF_N__KIND;

	/**
	 * The feature id for the '<em><b>State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CHANGE__STATE = DIFF_N__STATE;

	/**
	 * The feature id for the '<em><b>Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CHANGE__ATTRIBUTE = DIFF_N_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CHANGE__VALUE = DIFF_N_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Attribute Change</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CHANGE_FEATURE_COUNT = DIFF_N_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Attribute Change</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CHANGE_OPERATION_COUNT = DIFF_N_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link my.impl.FeatureMapChangeImpl <em>Feature Map Change</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see my.impl.FeatureMapChangeImpl
	 * @see my.impl.MyPackageImpl#getFeatureMapChange()
	 * @generated
	 */
	int FEATURE_MAP_CHANGE = 8;

	/**
	 * The feature id for the '<em><b>Conflict</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_MAP_CHANGE__CONFLICT = DIFF_N__CONFLICT;

	/**
	 * The feature id for the '<em><b>Match</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_MAP_CHANGE__MATCH = DIFF_N__MATCH;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_MAP_CHANGE__KIND = DIFF_N__KIND;

	/**
	 * The feature id for the '<em><b>State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_MAP_CHANGE__STATE = DIFF_N__STATE;

	/**
	 * The feature id for the '<em><b>Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_MAP_CHANGE__ATTRIBUTE = DIFF_N_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_MAP_CHANGE__VALUE = DIFF_N_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Feature Map Change</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_MAP_CHANGE_FEATURE_COUNT = DIFF_N_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Feature Map Change</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_MAP_CHANGE_OPERATION_COUNT = DIFF_N_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '<em>Conflict Kind</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.compare.ConflictKind
	 * @see my.impl.MyPackageImpl#getConflictKind()
	 * @generated
	 */
	int CONFLICT_KIND = 9;


	/**
	 * The meta object id for the '<em>Difference Kind</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.compare.DifferenceKind
	 * @see my.impl.MyPackageImpl#getDifferenceKind()
	 * @generated
	 */
	int DIFFERENCE_KIND = 10;

	/**
	 * The meta object id for the '<em>Difference State</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.compare.DifferenceState
	 * @see my.impl.MyPackageImpl#getDifferenceState()
	 * @generated
	 */
	int DIFFERENCE_STATE = 11;


	/**
	 * Returns the meta object for class '{@link my.ComparisonN <em>Comparison N</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Comparison N</em>'.
	 * @see my.ComparisonN
	 * @generated
	 */
	EClass getComparisonN();

	/**
	 * Returns the meta object for the containment reference list '{@link my.ComparisonN#getMatches <em>Matches</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Matches</em>'.
	 * @see my.ComparisonN#getMatches()
	 * @see #getComparisonN()
	 * @generated
	 */
	EReference getComparisonN_Matches();

	/**
	 * Returns the meta object for the containment reference list '{@link my.ComparisonN#getConflicts <em>Conflicts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Conflicts</em>'.
	 * @see my.ComparisonN#getConflicts()
	 * @see #getComparisonN()
	 * @generated
	 */
	EReference getComparisonN_Conflicts();

	/**
	 * Returns the meta object for the '{@link my.ComparisonN#getDifferences() <em>Get Differences</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Differences</em>' operation.
	 * @see my.ComparisonN#getDifferences()
	 * @generated
	 */
	EOperation getComparisonN__GetDifferences();

	/**
	 * Returns the meta object for class '{@link my.MatchN <em>Match N</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Match N</em>'.
	 * @see my.MatchN
	 * @generated
	 */
	EClass getMatchN();

	/**
	 * Returns the meta object for the containment reference list '{@link my.MatchN#getDifferences <em>Differences</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Differences</em>'.
	 * @see my.MatchN#getDifferences()
	 * @see #getMatchN()
	 * @generated
	 */
	EReference getMatchN_Differences();

	/**
	 * Returns the meta object for the reference '{@link my.MatchN#getBase <em>Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base</em>'.
	 * @see my.MatchN#getBase()
	 * @see #getMatchN()
	 * @generated
	 */
	EReference getMatchN_Base();

	/**
	 * Returns the meta object for the reference list '{@link my.MatchN#getBranches <em>Branches</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Branches</em>'.
	 * @see my.MatchN#getBranches()
	 * @see #getMatchN()
	 * @generated
	 */
	EReference getMatchN_Branches();

	/**
	 * Returns the meta object for the '{@link my.MatchN#getComparisonN() <em>Get Comparison N</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Comparison N</em>' operation.
	 * @see my.MatchN#getComparisonN()
	 * @generated
	 */
	EOperation getMatchN__GetComparisonN();

	/**
	 * Returns the meta object for class '{@link my.DiffN <em>Diff N</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Diff N</em>'.
	 * @see my.DiffN
	 * @generated
	 */
	EClass getDiffN();

	/**
	 * Returns the meta object for the container reference '{@link my.DiffN#getMatch <em>Match</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Match</em>'.
	 * @see my.DiffN#getMatch()
	 * @see #getDiffN()
	 * @generated
	 */
	EReference getDiffN_Match();

	/**
	 * Returns the meta object for the attribute '{@link my.DiffN#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see my.DiffN#getKind()
	 * @see #getDiffN()
	 * @generated
	 */
	EAttribute getDiffN_Kind();

	/**
	 * Returns the meta object for the attribute '{@link my.DiffN#getState <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>State</em>'.
	 * @see my.DiffN#getState()
	 * @see #getDiffN()
	 * @generated
	 */
	EAttribute getDiffN_State();

	/**
	 * Returns the meta object for the reference '{@link my.DiffN#getConflict <em>Conflict</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Conflict</em>'.
	 * @see my.DiffN#getConflict()
	 * @see #getDiffN()
	 * @generated
	 */
	EReference getDiffN_Conflict();

	/**
	 * Returns the meta object for class '{@link my.ConflictN <em>Conflict N</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Conflict N</em>'.
	 * @see my.ConflictN
	 * @generated
	 */
	EClass getConflictN();

	/**
	 * Returns the meta object for the reference list '{@link my.ConflictN#getDifferences <em>Differences</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Differences</em>'.
	 * @see my.ConflictN#getDifferences()
	 * @see #getConflictN()
	 * @generated
	 */
	EReference getConflictN_Differences();

	/**
	 * Returns the meta object for the attribute '{@link my.ConflictN#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see my.ConflictN#getKind()
	 * @see #getConflictN()
	 * @generated
	 */
	EAttribute getConflictN_Kind();

	/**
	 * Returns the meta object for class '{@link my.ResourceAttachmentChange <em>Resource Attachment Change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Resource Attachment Change</em>'.
	 * @see my.ResourceAttachmentChange
	 * @generated
	 */
	EClass getResourceAttachmentChange();

	/**
	 * Returns the meta object for the attribute '{@link my.ResourceAttachmentChange#getResourceURI <em>Resource URI</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Resource URI</em>'.
	 * @see my.ResourceAttachmentChange#getResourceURI()
	 * @see #getResourceAttachmentChange()
	 * @generated
	 */
	EAttribute getResourceAttachmentChange_ResourceURI();

	/**
	 * Returns the meta object for class '{@link my.ResourceLocationChange <em>Resource Location Change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Resource Location Change</em>'.
	 * @see my.ResourceLocationChange
	 * @generated
	 */
	EClass getResourceLocationChange();

	/**
	 * Returns the meta object for the attribute '{@link my.ResourceLocationChange#getBaseLocation <em>Base Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Base Location</em>'.
	 * @see my.ResourceLocationChange#getBaseLocation()
	 * @see #getResourceLocationChange()
	 * @generated
	 */
	EAttribute getResourceLocationChange_BaseLocation();

	/**
	 * Returns the meta object for the attribute '{@link my.ResourceLocationChange#getChangedLocation <em>Changed Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Changed Location</em>'.
	 * @see my.ResourceLocationChange#getChangedLocation()
	 * @see #getResourceLocationChange()
	 * @generated
	 */
	EAttribute getResourceLocationChange_ChangedLocation();

	/**
	 * Returns the meta object for class '{@link my.ReferenceChange <em>Reference Change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Reference Change</em>'.
	 * @see my.ReferenceChange
	 * @generated
	 */
	EClass getReferenceChange();

	/**
	 * Returns the meta object for the reference '{@link my.ReferenceChange#getReference <em>Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Reference</em>'.
	 * @see my.ReferenceChange#getReference()
	 * @see #getReferenceChange()
	 * @generated
	 */
	EReference getReferenceChange_Reference();

	/**
	 * Returns the meta object for the reference '{@link my.ReferenceChange#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see my.ReferenceChange#getValue()
	 * @see #getReferenceChange()
	 * @generated
	 */
	EReference getReferenceChange_Value();

	/**
	 * Returns the meta object for class '{@link my.AttributeChange <em>Attribute Change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute Change</em>'.
	 * @see my.AttributeChange
	 * @generated
	 */
	EClass getAttributeChange();

	/**
	 * Returns the meta object for the reference '{@link my.AttributeChange#getAttribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Attribute</em>'.
	 * @see my.AttributeChange#getAttribute()
	 * @see #getAttributeChange()
	 * @generated
	 */
	EReference getAttributeChange_Attribute();

	/**
	 * Returns the meta object for the attribute '{@link my.AttributeChange#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see my.AttributeChange#getValue()
	 * @see #getAttributeChange()
	 * @generated
	 */
	EAttribute getAttributeChange_Value();

	/**
	 * Returns the meta object for class '{@link my.FeatureMapChange <em>Feature Map Change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Feature Map Change</em>'.
	 * @see my.FeatureMapChange
	 * @generated
	 */
	EClass getFeatureMapChange();

	/**
	 * Returns the meta object for the reference '{@link my.FeatureMapChange#getAttribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Attribute</em>'.
	 * @see my.FeatureMapChange#getAttribute()
	 * @see #getFeatureMapChange()
	 * @generated
	 */
	EReference getFeatureMapChange_Attribute();

	/**
	 * Returns the meta object for the attribute '{@link my.FeatureMapChange#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see my.FeatureMapChange#getValue()
	 * @see #getFeatureMapChange()
	 * @generated
	 */
	EAttribute getFeatureMapChange_Value();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.compare.ConflictKind <em>Conflict Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Conflict Kind</em>'.
	 * @see org.eclipse.emf.compare.ConflictKind
	 * @model instanceClass="org.eclipse.emf.compare.ConflictKind"
	 * @generated
	 */
	EDataType getConflictKind();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.compare.DifferenceKind <em>Difference Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Difference Kind</em>'.
	 * @see org.eclipse.emf.compare.DifferenceKind
	 * @model instanceClass="org.eclipse.emf.compare.DifferenceKind"
	 * @generated
	 */
	EDataType getDifferenceKind();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.compare.DifferenceState <em>Difference State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Difference State</em>'.
	 * @see org.eclipse.emf.compare.DifferenceState
	 * @model instanceClass="org.eclipse.emf.compare.DifferenceState"
	 * @generated
	 */
	EDataType getDifferenceState();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	MyFactory getMyFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link my.impl.ComparisonNImpl <em>Comparison N</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see my.impl.ComparisonNImpl
		 * @see my.impl.MyPackageImpl#getComparisonN()
		 * @generated
		 */
		EClass COMPARISON_N = eINSTANCE.getComparisonN();

		/**
		 * The meta object literal for the '<em><b>Matches</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPARISON_N__MATCHES = eINSTANCE.getComparisonN_Matches();

		/**
		 * The meta object literal for the '<em><b>Conflicts</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPARISON_N__CONFLICTS = eINSTANCE.getComparisonN_Conflicts();

		/**
		 * The meta object literal for the '<em><b>Get Differences</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPARISON_N___GET_DIFFERENCES = eINSTANCE.getComparisonN__GetDifferences();

		/**
		 * The meta object literal for the '{@link my.impl.MatchNImpl <em>Match N</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see my.impl.MatchNImpl
		 * @see my.impl.MyPackageImpl#getMatchN()
		 * @generated
		 */
		EClass MATCH_N = eINSTANCE.getMatchN();

		/**
		 * The meta object literal for the '<em><b>Differences</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MATCH_N__DIFFERENCES = eINSTANCE.getMatchN_Differences();

		/**
		 * The meta object literal for the '<em><b>Base</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MATCH_N__BASE = eINSTANCE.getMatchN_Base();

		/**
		 * The meta object literal for the '<em><b>Branches</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MATCH_N__BRANCHES = eINSTANCE.getMatchN_Branches();

		/**
		 * The meta object literal for the '<em><b>Get Comparison N</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation MATCH_N___GET_COMPARISON_N = eINSTANCE.getMatchN__GetComparisonN();

		/**
		 * The meta object literal for the '{@link my.impl.DiffNImpl <em>Diff N</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see my.impl.DiffNImpl
		 * @see my.impl.MyPackageImpl#getDiffN()
		 * @generated
		 */
		EClass DIFF_N = eINSTANCE.getDiffN();

		/**
		 * The meta object literal for the '<em><b>Match</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIFF_N__MATCH = eINSTANCE.getDiffN_Match();

		/**
		 * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIFF_N__KIND = eINSTANCE.getDiffN_Kind();

		/**
		 * The meta object literal for the '<em><b>State</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIFF_N__STATE = eINSTANCE.getDiffN_State();

		/**
		 * The meta object literal for the '<em><b>Conflict</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIFF_N__CONFLICT = eINSTANCE.getDiffN_Conflict();

		/**
		 * The meta object literal for the '{@link my.impl.ConflictNImpl <em>Conflict N</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see my.impl.ConflictNImpl
		 * @see my.impl.MyPackageImpl#getConflictN()
		 * @generated
		 */
		EClass CONFLICT_N = eINSTANCE.getConflictN();

		/**
		 * The meta object literal for the '<em><b>Differences</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFLICT_N__DIFFERENCES = eINSTANCE.getConflictN_Differences();

		/**
		 * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFLICT_N__KIND = eINSTANCE.getConflictN_Kind();

		/**
		 * The meta object literal for the '{@link my.impl.ResourceAttachmentChangeImpl <em>Resource Attachment Change</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see my.impl.ResourceAttachmentChangeImpl
		 * @see my.impl.MyPackageImpl#getResourceAttachmentChange()
		 * @generated
		 */
		EClass RESOURCE_ATTACHMENT_CHANGE = eINSTANCE.getResourceAttachmentChange();

		/**
		 * The meta object literal for the '<em><b>Resource URI</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RESOURCE_ATTACHMENT_CHANGE__RESOURCE_URI = eINSTANCE.getResourceAttachmentChange_ResourceURI();

		/**
		 * The meta object literal for the '{@link my.impl.ResourceLocationChangeImpl <em>Resource Location Change</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see my.impl.ResourceLocationChangeImpl
		 * @see my.impl.MyPackageImpl#getResourceLocationChange()
		 * @generated
		 */
		EClass RESOURCE_LOCATION_CHANGE = eINSTANCE.getResourceLocationChange();

		/**
		 * The meta object literal for the '<em><b>Base Location</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RESOURCE_LOCATION_CHANGE__BASE_LOCATION = eINSTANCE.getResourceLocationChange_BaseLocation();

		/**
		 * The meta object literal for the '<em><b>Changed Location</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RESOURCE_LOCATION_CHANGE__CHANGED_LOCATION = eINSTANCE.getResourceLocationChange_ChangedLocation();

		/**
		 * The meta object literal for the '{@link my.impl.ReferenceChangeImpl <em>Reference Change</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see my.impl.ReferenceChangeImpl
		 * @see my.impl.MyPackageImpl#getReferenceChange()
		 * @generated
		 */
		EClass REFERENCE_CHANGE = eINSTANCE.getReferenceChange();

		/**
		 * The meta object literal for the '<em><b>Reference</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REFERENCE_CHANGE__REFERENCE = eINSTANCE.getReferenceChange_Reference();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REFERENCE_CHANGE__VALUE = eINSTANCE.getReferenceChange_Value();

		/**
		 * The meta object literal for the '{@link my.impl.AttributeChangeImpl <em>Attribute Change</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see my.impl.AttributeChangeImpl
		 * @see my.impl.MyPackageImpl#getAttributeChange()
		 * @generated
		 */
		EClass ATTRIBUTE_CHANGE = eINSTANCE.getAttributeChange();

		/**
		 * The meta object literal for the '<em><b>Attribute</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE_CHANGE__ATTRIBUTE = eINSTANCE.getAttributeChange_Attribute();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE_CHANGE__VALUE = eINSTANCE.getAttributeChange_Value();

		/**
		 * The meta object literal for the '{@link my.impl.FeatureMapChangeImpl <em>Feature Map Change</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see my.impl.FeatureMapChangeImpl
		 * @see my.impl.MyPackageImpl#getFeatureMapChange()
		 * @generated
		 */
		EClass FEATURE_MAP_CHANGE = eINSTANCE.getFeatureMapChange();

		/**
		 * The meta object literal for the '<em><b>Attribute</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE_MAP_CHANGE__ATTRIBUTE = eINSTANCE.getFeatureMapChange_Attribute();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_MAP_CHANGE__VALUE = eINSTANCE.getFeatureMapChange_Value();

		/**
		 * The meta object literal for the '<em>Conflict Kind</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.compare.ConflictKind
		 * @see my.impl.MyPackageImpl#getConflictKind()
		 * @generated
		 */
		EDataType CONFLICT_KIND = eINSTANCE.getConflictKind();

		/**
		 * The meta object literal for the '<em>Difference Kind</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.compare.DifferenceKind
		 * @see my.impl.MyPackageImpl#getDifferenceKind()
		 * @generated
		 */
		EDataType DIFFERENCE_KIND = eINSTANCE.getDifferenceKind();

		/**
		 * The meta object literal for the '<em>Difference State</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.compare.DifferenceState
		 * @see my.impl.MyPackageImpl#getDifferenceState()
		 * @generated
		 */
		EDataType DIFFERENCE_STATE = eINSTANCE.getDifferenceState();

	}

} //MyPackage
