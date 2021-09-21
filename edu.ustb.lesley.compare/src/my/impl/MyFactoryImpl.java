/**
 */
package my.impl;

import my.*;
import org.eclipse.emf.compare.ConflictKind;
import org.eclipse.emf.compare.DifferenceKind;
import org.eclipse.emf.compare.DifferenceState;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MyFactoryImpl extends EFactoryImpl implements MyFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static MyFactory init() {
		try {
			MyFactory theMyFactory = (MyFactory)EPackage.Registry.INSTANCE.getEFactory(MyPackage.eNS_URI);
			if (theMyFactory != null) {
				return theMyFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new MyFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MyFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case MyPackage.COMPARISON_N: return createComparisonN();
			case MyPackage.MATCH_N: return createMatchN();
			case MyPackage.DIFF_N: return createDiffN();
			case MyPackage.CONFLICT_N: return createConflictN();
			case MyPackage.RESOURCE_ATTACHMENT_CHANGE: return createResourceAttachmentChange();
			case MyPackage.RESOURCE_LOCATION_CHANGE: return createResourceLocationChange();
			case MyPackage.REFERENCE_CHANGE: return createReferenceChange();
			case MyPackage.ATTRIBUTE_CHANGE: return createAttributeChange();
			case MyPackage.FEATURE_MAP_CHANGE: return createFeatureMapChange();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case MyPackage.CONFLICT_KIND:
				return createConflictKindFromString(eDataType, initialValue);
			case MyPackage.DIFFERENCE_KIND:
				return createDifferenceKindFromString(eDataType, initialValue);
			case MyPackage.DIFFERENCE_STATE:
				return createDifferenceStateFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case MyPackage.CONFLICT_KIND:
				return convertConflictKindToString(eDataType, instanceValue);
			case MyPackage.DIFFERENCE_KIND:
				return convertDifferenceKindToString(eDataType, instanceValue);
			case MyPackage.DIFFERENCE_STATE:
				return convertDifferenceStateToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ComparisonN createComparisonN() {
		ComparisonNImpl comparisonN = new ComparisonNImpl();
		return comparisonN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MatchN createMatchN() {
		MatchNImpl matchN = new MatchNImpl();
		return matchN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DiffN createDiffN() {
		DiffNImpl diffN = new DiffNImpl();
		return diffN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ConflictN createConflictN() {
		ConflictNImpl conflictN = new ConflictNImpl();
		return conflictN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceAttachmentChange createResourceAttachmentChange() {
		ResourceAttachmentChangeImpl resourceAttachmentChange = new ResourceAttachmentChangeImpl();
		return resourceAttachmentChange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocationChange createResourceLocationChange() {
		ResourceLocationChangeImpl resourceLocationChange = new ResourceLocationChangeImpl();
		return resourceLocationChange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ReferenceChange createReferenceChange() {
		ReferenceChangeImpl referenceChange = new ReferenceChangeImpl();
		return referenceChange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AttributeChange createAttributeChange() {
		AttributeChangeImpl attributeChange = new AttributeChangeImpl();
		return attributeChange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureMapChange createFeatureMapChange() {
		FeatureMapChangeImpl featureMapChange = new FeatureMapChangeImpl();
		return featureMapChange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConflictKind createConflictKindFromString(EDataType eDataType, String initialValue) {
		return (ConflictKind)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertConflictKindToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DifferenceKind createDifferenceKindFromString(EDataType eDataType, String initialValue) {
		return (DifferenceKind)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDifferenceKindToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DifferenceState createDifferenceStateFromString(EDataType eDataType, String initialValue) {
		return (DifferenceState)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDifferenceStateToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MyPackage getMyPackage() {
		return (MyPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static MyPackage getPackage() {
		return MyPackage.eINSTANCE;
	}

} //MyFactoryImpl
