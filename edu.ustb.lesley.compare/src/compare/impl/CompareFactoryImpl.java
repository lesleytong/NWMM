/**
 */
package compare.impl;

import compare.*;

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
public class CompareFactoryImpl extends EFactoryImpl implements CompareFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CompareFactory init() {
		try {
			CompareFactory theCompareFactory = (CompareFactory)EPackage.Registry.INSTANCE.getEFactory(ComparePackage.eNS_URI);
			if (theCompareFactory != null) {
				return theCompareFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new CompareFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompareFactoryImpl() {
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
			case ComparePackage.COMPARISON: return createComparison();
			case ComparePackage.MATCH_RESOURCE: return createMatchResource();
			case ComparePackage.MATCH: return createMatch();
			case ComparePackage.DIFF: return createDiff();
			case ComparePackage.RESOURCE_ATTACHMENT_CHANGE: return createResourceAttachmentChange();
			case ComparePackage.RESOURCE_LOCATION_CHANGE: return createResourceLocationChange();
			case ComparePackage.REFERENCE_CHANGE: return createReferenceChange();
			case ComparePackage.ATTRIBUTE_CHANGE: return createAttributeChange();
			case ComparePackage.FEATURE_MAP_CHANGE: return createFeatureMapChange();
			case ComparePackage.CONFLICT: return createConflict();
			case ComparePackage.EQUIVALENCE: return createEquivalence();
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
			case ComparePackage.DIFFERENCE_KIND:
				return createDifferenceKindFromString(eDataType, initialValue);
			case ComparePackage.DIFFERENCE_SOURCE:
				return createDifferenceSourceFromString(eDataType, initialValue);
			case ComparePackage.DIFFERENCE_STATE:
				return createDifferenceStateFromString(eDataType, initialValue);
			case ComparePackage.CONFLICT_KIND:
				return createConflictKindFromString(eDataType, initialValue);
			case ComparePackage.EITERABLE:
				return createEIterableFromString(eDataType, initialValue);
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
			case ComparePackage.DIFFERENCE_KIND:
				return convertDifferenceKindToString(eDataType, instanceValue);
			case ComparePackage.DIFFERENCE_SOURCE:
				return convertDifferenceSourceToString(eDataType, instanceValue);
			case ComparePackage.DIFFERENCE_STATE:
				return convertDifferenceStateToString(eDataType, instanceValue);
			case ComparePackage.CONFLICT_KIND:
				return convertConflictKindToString(eDataType, instanceValue);
			case ComparePackage.EITERABLE:
				return convertEIterableToString(eDataType, instanceValue);
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
	public Comparison createComparison() {
		ComparisonImpl comparison = new ComparisonImpl();
		return comparison;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MatchResource createMatchResource() {
		MatchResourceImpl matchResource = new MatchResourceImpl();
		return matchResource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Match createMatch() {
		MatchImpl match = new MatchImpl();
		return match;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Diff createDiff() {
		DiffImpl diff = new DiffImpl();
		return diff;
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
	@Override
	public Conflict createConflict() {
		ConflictImpl conflict = new ConflictImpl();
		return conflict;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Equivalence createEquivalence() {
		EquivalenceImpl equivalence = new EquivalenceImpl();
		return equivalence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DifferenceKind createDifferenceKindFromString(EDataType eDataType, String initialValue) {
		DifferenceKind result = DifferenceKind.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDifferenceKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DifferenceSource createDifferenceSourceFromString(EDataType eDataType, String initialValue) {
		DifferenceSource result = DifferenceSource.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDifferenceSourceToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DifferenceState createDifferenceStateFromString(EDataType eDataType, String initialValue) {
		DifferenceState result = DifferenceState.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDifferenceStateToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConflictKind createConflictKindFromString(EDataType eDataType, String initialValue) {
		ConflictKind result = ConflictKind.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertConflictKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Iterable<?> createEIterableFromString(EDataType eDataType, String initialValue) {
		return (Iterable<?>)super.createFromString(initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertEIterableToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ComparePackage getComparePackage() {
		return (ComparePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ComparePackage getPackage() {
		return ComparePackage.eINSTANCE;
	}

} //CompareFactoryImpl
