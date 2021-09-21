/**
 */
package reqtify;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see reqtify.ReqtifyFactory
 * @model kind="package"
 * @generated
 */
public interface ReqtifyPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "reqtify";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///reqtify";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "reqtify";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ReqtifyPackage eINSTANCE = reqtify.impl.ReqtifyPackageImpl.init();

	/**
	 * The meta object id for the '{@link reqtify.impl.ProjectImpl <em>Project</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see reqtify.impl.ProjectImpl
	 * @see reqtify.impl.ReqtifyPackageImpl#getProject()
	 * @generated
	 */
	int PROJECT = 0;

	/**
	 * The feature id for the '<em><b>Documents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT__DOCUMENTS = 0;

	/**
	 * The number of structural features of the '<em>Project</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Project</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link reqtify.impl.TypedElementImpl <em>Typed Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see reqtify.impl.TypedElementImpl
	 * @see reqtify.impl.ReqtifyPackageImpl#getTypedElement()
	 * @generated
	 */
	int TYPED_ELEMENT = 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_ELEMENT__TYPE = 0;

	/**
	 * The number of structural features of the '<em>Typed Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Typed Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link reqtify.impl.ElementWithILImpl <em>Element With IL</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see reqtify.impl.ElementWithILImpl
	 * @see reqtify.impl.ReqtifyPackageImpl#getElementWithIL()
	 * @generated
	 */
	int ELEMENT_WITH_IL = 2;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_WITH_IL__TYPE = TYPED_ELEMENT__TYPE;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_WITH_IL__NAME = TYPED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_WITH_IL__LABEL = TYPED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Element With IL</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_WITH_IL_FEATURE_COUNT = TYPED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Element With IL</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_WITH_IL_OPERATION_COUNT = TYPED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link reqtify.impl.DocumentImpl <em>Document</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see reqtify.impl.DocumentImpl
	 * @see reqtify.impl.ReqtifyPackageImpl#getDocument()
	 * @generated
	 */
	int DOCUMENT = 3;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT__TYPE = ELEMENT_WITH_IL__TYPE;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT__NAME = ELEMENT_WITH_IL__NAME;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT__LABEL = ELEMENT_WITH_IL__LABEL;

	/**
	 * The feature id for the '<em><b>Project</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT__PROJECT = ELEMENT_WITH_IL_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Sections</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT__SECTIONS = ELEMENT_WITH_IL_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Document</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_FEATURE_COUNT = ELEMENT_WITH_IL_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Document</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_OPERATION_COUNT = ELEMENT_WITH_IL_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link reqtify.impl.TextElementImpl <em>Text Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see reqtify.impl.TextElementImpl
	 * @see reqtify.impl.ReqtifyPackageImpl#getTextElement()
	 * @generated
	 */
	int TEXT_ELEMENT = 4;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_ELEMENT__TYPE = ELEMENT_WITH_IL__TYPE;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_ELEMENT__NAME = ELEMENT_WITH_IL__NAME;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_ELEMENT__LABEL = ELEMENT_WITH_IL__LABEL;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_ELEMENT__DESCRIPTION = ELEMENT_WITH_IL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Text Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_ELEMENT_FEATURE_COUNT = ELEMENT_WITH_IL_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Text Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_ELEMENT_OPERATION_COUNT = ELEMENT_WITH_IL_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link reqtify.impl.SectionImpl <em>Section</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see reqtify.impl.SectionImpl
	 * @see reqtify.impl.ReqtifyPackageImpl#getSection()
	 * @generated
	 */
	int SECTION = 5;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECTION__TYPE = TEXT_ELEMENT__TYPE;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECTION__NAME = TEXT_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECTION__LABEL = TEXT_ELEMENT__LABEL;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECTION__DESCRIPTION = TEXT_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Document</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECTION__DOCUMENT = TEXT_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Section Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECTION__SECTION_CHILDREN = TEXT_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Section Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECTION__SECTION_PARENT = TEXT_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Requirements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECTION__REQUIREMENTS = TEXT_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Section</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECTION_FEATURE_COUNT = TEXT_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Section</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECTION_OPERATION_COUNT = TEXT_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link reqtify.impl.AbstractRequirementImpl <em>Abstract Requirement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see reqtify.impl.AbstractRequirementImpl
	 * @see reqtify.impl.ReqtifyPackageImpl#getAbstractRequirement()
	 * @generated
	 */
	int ABSTRACT_REQUIREMENT = 6;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_REQUIREMENT__TYPE = TEXT_ELEMENT__TYPE;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_REQUIREMENT__NAME = TEXT_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_REQUIREMENT__LABEL = TEXT_ELEMENT__LABEL;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_REQUIREMENT__DESCRIPTION = TEXT_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Section</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_REQUIREMENT__SECTION = TEXT_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Is Contained</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_REQUIREMENT__IS_CONTAINED = TEXT_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Cover Links</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_REQUIREMENT__COVER_LINKS = TEXT_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Attribute</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_REQUIREMENT__ATTRIBUTE = TEXT_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Abstract Requirement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_REQUIREMENT_FEATURE_COUNT = TEXT_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Abstract Requirement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_REQUIREMENT_OPERATION_COUNT = TEXT_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link reqtify.impl.RequirementImpl <em>Requirement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see reqtify.impl.RequirementImpl
	 * @see reqtify.impl.ReqtifyPackageImpl#getRequirement()
	 * @generated
	 */
	int REQUIREMENT = 7;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT__TYPE = ABSTRACT_REQUIREMENT__TYPE;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT__NAME = ABSTRACT_REQUIREMENT__NAME;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT__LABEL = ABSTRACT_REQUIREMENT__LABEL;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT__DESCRIPTION = ABSTRACT_REQUIREMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Section</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT__SECTION = ABSTRACT_REQUIREMENT__SECTION;

	/**
	 * The feature id for the '<em><b>Is Contained</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT__IS_CONTAINED = ABSTRACT_REQUIREMENT__IS_CONTAINED;

	/**
	 * The feature id for the '<em><b>Cover Links</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT__COVER_LINKS = ABSTRACT_REQUIREMENT__COVER_LINKS;

	/**
	 * The feature id for the '<em><b>Attribute</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT__ATTRIBUTE = ABSTRACT_REQUIREMENT__ATTRIBUTE;

	/**
	 * The number of structural features of the '<em>Requirement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT_FEATURE_COUNT = ABSTRACT_REQUIREMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Requirement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT_OPERATION_COUNT = ABSTRACT_REQUIREMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link reqtify.impl.MacroRequirementImpl <em>Macro Requirement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see reqtify.impl.MacroRequirementImpl
	 * @see reqtify.impl.ReqtifyPackageImpl#getMacroRequirement()
	 * @generated
	 */
	int MACRO_REQUIREMENT = 8;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACRO_REQUIREMENT__TYPE = ABSTRACT_REQUIREMENT__TYPE;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACRO_REQUIREMENT__NAME = ABSTRACT_REQUIREMENT__NAME;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACRO_REQUIREMENT__LABEL = ABSTRACT_REQUIREMENT__LABEL;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACRO_REQUIREMENT__DESCRIPTION = ABSTRACT_REQUIREMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Section</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACRO_REQUIREMENT__SECTION = ABSTRACT_REQUIREMENT__SECTION;

	/**
	 * The feature id for the '<em><b>Is Contained</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACRO_REQUIREMENT__IS_CONTAINED = ABSTRACT_REQUIREMENT__IS_CONTAINED;

	/**
	 * The feature id for the '<em><b>Cover Links</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACRO_REQUIREMENT__COVER_LINKS = ABSTRACT_REQUIREMENT__COVER_LINKS;

	/**
	 * The feature id for the '<em><b>Attribute</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACRO_REQUIREMENT__ATTRIBUTE = ABSTRACT_REQUIREMENT__ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Contains</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACRO_REQUIREMENT__CONTAINS = ABSTRACT_REQUIREMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Macro Requirement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACRO_REQUIREMENT_FEATURE_COUNT = ABSTRACT_REQUIREMENT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>New Operation1</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACRO_REQUIREMENT___NEW_OPERATION1 = ABSTRACT_REQUIREMENT_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Macro Requirement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACRO_REQUIREMENT_OPERATION_COUNT = ABSTRACT_REQUIREMENT_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link reqtify.impl.CoverLinkImpl <em>Cover Link</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see reqtify.impl.CoverLinkImpl
	 * @see reqtify.impl.ReqtifyPackageImpl#getCoverLink()
	 * @generated
	 */
	int COVER_LINK = 9;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COVER_LINK__TYPE = TYPED_ELEMENT__TYPE;

	/**
	 * The feature id for the '<em><b>Link With</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COVER_LINK__LINK_WITH = TYPED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Cover Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COVER_LINK_FEATURE_COUNT = TYPED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Cover Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COVER_LINK_OPERATION_COUNT = TYPED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link reqtify.impl.AttributeImpl <em>Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see reqtify.impl.AttributeImpl
	 * @see reqtify.impl.ReqtifyPackageImpl#getAttribute()
	 * @generated
	 */
	int ATTRIBUTE = 10;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__TYPE = TYPED_ELEMENT__TYPE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__VALUE = TYPED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_FEATURE_COUNT = TYPED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_OPERATION_COUNT = TYPED_ELEMENT_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link reqtify.Project <em>Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Project</em>'.
	 * @see reqtify.Project
	 * @generated
	 */
	EClass getProject();

	/**
	 * Returns the meta object for the containment reference list '{@link reqtify.Project#getDocuments <em>Documents</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Documents</em>'.
	 * @see reqtify.Project#getDocuments()
	 * @see #getProject()
	 * @generated
	 */
	EReference getProject_Documents();

	/**
	 * Returns the meta object for class '{@link reqtify.TypedElement <em>Typed Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Typed Element</em>'.
	 * @see reqtify.TypedElement
	 * @generated
	 */
	EClass getTypedElement();

	/**
	 * Returns the meta object for the attribute '{@link reqtify.TypedElement#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see reqtify.TypedElement#getType()
	 * @see #getTypedElement()
	 * @generated
	 */
	EAttribute getTypedElement_Type();

	/**
	 * Returns the meta object for class '{@link reqtify.ElementWithIL <em>Element With IL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Element With IL</em>'.
	 * @see reqtify.ElementWithIL
	 * @generated
	 */
	EClass getElementWithIL();

	/**
	 * Returns the meta object for the attribute '{@link reqtify.ElementWithIL#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see reqtify.ElementWithIL#getName()
	 * @see #getElementWithIL()
	 * @generated
	 */
	EAttribute getElementWithIL_Name();

	/**
	 * Returns the meta object for the attribute '{@link reqtify.ElementWithIL#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see reqtify.ElementWithIL#getLabel()
	 * @see #getElementWithIL()
	 * @generated
	 */
	EAttribute getElementWithIL_Label();

	/**
	 * Returns the meta object for class '{@link reqtify.Document <em>Document</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document</em>'.
	 * @see reqtify.Document
	 * @generated
	 */
	EClass getDocument();

	/**
	 * Returns the meta object for the container reference '{@link reqtify.Document#getProject <em>Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Project</em>'.
	 * @see reqtify.Document#getProject()
	 * @see #getDocument()
	 * @generated
	 */
	EReference getDocument_Project();

	/**
	 * Returns the meta object for the containment reference list '{@link reqtify.Document#getSections <em>Sections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sections</em>'.
	 * @see reqtify.Document#getSections()
	 * @see #getDocument()
	 * @generated
	 */
	EReference getDocument_Sections();

	/**
	 * Returns the meta object for class '{@link reqtify.TextElement <em>Text Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Text Element</em>'.
	 * @see reqtify.TextElement
	 * @generated
	 */
	EClass getTextElement();

	/**
	 * Returns the meta object for the attribute '{@link reqtify.TextElement#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see reqtify.TextElement#getDescription()
	 * @see #getTextElement()
	 * @generated
	 */
	EAttribute getTextElement_Description();

	/**
	 * Returns the meta object for class '{@link reqtify.Section <em>Section</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Section</em>'.
	 * @see reqtify.Section
	 * @generated
	 */
	EClass getSection();

	/**
	 * Returns the meta object for the container reference '{@link reqtify.Section#getDocument <em>Document</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Document</em>'.
	 * @see reqtify.Section#getDocument()
	 * @see #getSection()
	 * @generated
	 */
	EReference getSection_Document();

	/**
	 * Returns the meta object for the containment reference list '{@link reqtify.Section#getSectionChildren <em>Section Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Section Children</em>'.
	 * @see reqtify.Section#getSectionChildren()
	 * @see #getSection()
	 * @generated
	 */
	EReference getSection_SectionChildren();

	/**
	 * Returns the meta object for the container reference '{@link reqtify.Section#getSectionParent <em>Section Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Section Parent</em>'.
	 * @see reqtify.Section#getSectionParent()
	 * @see #getSection()
	 * @generated
	 */
	EReference getSection_SectionParent();

	/**
	 * Returns the meta object for the containment reference list '{@link reqtify.Section#getRequirements <em>Requirements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Requirements</em>'.
	 * @see reqtify.Section#getRequirements()
	 * @see #getSection()
	 * @generated
	 */
	EReference getSection_Requirements();

	/**
	 * Returns the meta object for class '{@link reqtify.AbstractRequirement <em>Abstract Requirement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Requirement</em>'.
	 * @see reqtify.AbstractRequirement
	 * @generated
	 */
	EClass getAbstractRequirement();

	/**
	 * Returns the meta object for the container reference '{@link reqtify.AbstractRequirement#getSection <em>Section</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Section</em>'.
	 * @see reqtify.AbstractRequirement#getSection()
	 * @see #getAbstractRequirement()
	 * @generated
	 */
	EReference getAbstractRequirement_Section();

	/**
	 * Returns the meta object for the container reference '{@link reqtify.AbstractRequirement#getIsContained <em>Is Contained</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Is Contained</em>'.
	 * @see reqtify.AbstractRequirement#getIsContained()
	 * @see #getAbstractRequirement()
	 * @generated
	 */
	EReference getAbstractRequirement_IsContained();

	/**
	 * Returns the meta object for the containment reference list '{@link reqtify.AbstractRequirement#getCoverLinks <em>Cover Links</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Cover Links</em>'.
	 * @see reqtify.AbstractRequirement#getCoverLinks()
	 * @see #getAbstractRequirement()
	 * @generated
	 */
	EReference getAbstractRequirement_CoverLinks();

	/**
	 * Returns the meta object for the containment reference list '{@link reqtify.AbstractRequirement#getAttribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attribute</em>'.
	 * @see reqtify.AbstractRequirement#getAttribute()
	 * @see #getAbstractRequirement()
	 * @generated
	 */
	EReference getAbstractRequirement_Attribute();

	/**
	 * Returns the meta object for class '{@link reqtify.Requirement <em>Requirement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Requirement</em>'.
	 * @see reqtify.Requirement
	 * @generated
	 */
	EClass getRequirement();

	/**
	 * Returns the meta object for class '{@link reqtify.MacroRequirement <em>Macro Requirement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Macro Requirement</em>'.
	 * @see reqtify.MacroRequirement
	 * @generated
	 */
	EClass getMacroRequirement();

	/**
	 * Returns the meta object for the containment reference list '{@link reqtify.MacroRequirement#getContains <em>Contains</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Contains</em>'.
	 * @see reqtify.MacroRequirement#getContains()
	 * @see #getMacroRequirement()
	 * @generated
	 */
	EReference getMacroRequirement_Contains();

	/**
	 * Returns the meta object for the '{@link reqtify.MacroRequirement#newOperation1() <em>New Operation1</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>New Operation1</em>' operation.
	 * @see reqtify.MacroRequirement#newOperation1()
	 * @generated
	 */
	EOperation getMacroRequirement__NewOperation1();

	/**
	 * Returns the meta object for class '{@link reqtify.CoverLink <em>Cover Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cover Link</em>'.
	 * @see reqtify.CoverLink
	 * @generated
	 */
	EClass getCoverLink();

	/**
	 * Returns the meta object for the reference '{@link reqtify.CoverLink#getLinkWith <em>Link With</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Link With</em>'.
	 * @see reqtify.CoverLink#getLinkWith()
	 * @see #getCoverLink()
	 * @generated
	 */
	EReference getCoverLink_LinkWith();

	/**
	 * Returns the meta object for class '{@link reqtify.Attribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute</em>'.
	 * @see reqtify.Attribute
	 * @generated
	 */
	EClass getAttribute();

	/**
	 * Returns the meta object for the attribute '{@link reqtify.Attribute#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see reqtify.Attribute#getValue()
	 * @see #getAttribute()
	 * @generated
	 */
	EAttribute getAttribute_Value();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ReqtifyFactory getReqtifyFactory();

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
		 * The meta object literal for the '{@link reqtify.impl.ProjectImpl <em>Project</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see reqtify.impl.ProjectImpl
		 * @see reqtify.impl.ReqtifyPackageImpl#getProject()
		 * @generated
		 */
		EClass PROJECT = eINSTANCE.getProject();

		/**
		 * The meta object literal for the '<em><b>Documents</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT__DOCUMENTS = eINSTANCE.getProject_Documents();

		/**
		 * The meta object literal for the '{@link reqtify.impl.TypedElementImpl <em>Typed Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see reqtify.impl.TypedElementImpl
		 * @see reqtify.impl.ReqtifyPackageImpl#getTypedElement()
		 * @generated
		 */
		EClass TYPED_ELEMENT = eINSTANCE.getTypedElement();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPED_ELEMENT__TYPE = eINSTANCE.getTypedElement_Type();

		/**
		 * The meta object literal for the '{@link reqtify.impl.ElementWithILImpl <em>Element With IL</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see reqtify.impl.ElementWithILImpl
		 * @see reqtify.impl.ReqtifyPackageImpl#getElementWithIL()
		 * @generated
		 */
		EClass ELEMENT_WITH_IL = eINSTANCE.getElementWithIL();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ELEMENT_WITH_IL__NAME = eINSTANCE.getElementWithIL_Name();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ELEMENT_WITH_IL__LABEL = eINSTANCE.getElementWithIL_Label();

		/**
		 * The meta object literal for the '{@link reqtify.impl.DocumentImpl <em>Document</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see reqtify.impl.DocumentImpl
		 * @see reqtify.impl.ReqtifyPackageImpl#getDocument()
		 * @generated
		 */
		EClass DOCUMENT = eINSTANCE.getDocument();

		/**
		 * The meta object literal for the '<em><b>Project</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT__PROJECT = eINSTANCE.getDocument_Project();

		/**
		 * The meta object literal for the '<em><b>Sections</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT__SECTIONS = eINSTANCE.getDocument_Sections();

		/**
		 * The meta object literal for the '{@link reqtify.impl.TextElementImpl <em>Text Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see reqtify.impl.TextElementImpl
		 * @see reqtify.impl.ReqtifyPackageImpl#getTextElement()
		 * @generated
		 */
		EClass TEXT_ELEMENT = eINSTANCE.getTextElement();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEXT_ELEMENT__DESCRIPTION = eINSTANCE.getTextElement_Description();

		/**
		 * The meta object literal for the '{@link reqtify.impl.SectionImpl <em>Section</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see reqtify.impl.SectionImpl
		 * @see reqtify.impl.ReqtifyPackageImpl#getSection()
		 * @generated
		 */
		EClass SECTION = eINSTANCE.getSection();

		/**
		 * The meta object literal for the '<em><b>Document</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SECTION__DOCUMENT = eINSTANCE.getSection_Document();

		/**
		 * The meta object literal for the '<em><b>Section Children</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SECTION__SECTION_CHILDREN = eINSTANCE.getSection_SectionChildren();

		/**
		 * The meta object literal for the '<em><b>Section Parent</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SECTION__SECTION_PARENT = eINSTANCE.getSection_SectionParent();

		/**
		 * The meta object literal for the '<em><b>Requirements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SECTION__REQUIREMENTS = eINSTANCE.getSection_Requirements();

		/**
		 * The meta object literal for the '{@link reqtify.impl.AbstractRequirementImpl <em>Abstract Requirement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see reqtify.impl.AbstractRequirementImpl
		 * @see reqtify.impl.ReqtifyPackageImpl#getAbstractRequirement()
		 * @generated
		 */
		EClass ABSTRACT_REQUIREMENT = eINSTANCE.getAbstractRequirement();

		/**
		 * The meta object literal for the '<em><b>Section</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_REQUIREMENT__SECTION = eINSTANCE.getAbstractRequirement_Section();

		/**
		 * The meta object literal for the '<em><b>Is Contained</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_REQUIREMENT__IS_CONTAINED = eINSTANCE.getAbstractRequirement_IsContained();

		/**
		 * The meta object literal for the '<em><b>Cover Links</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_REQUIREMENT__COVER_LINKS = eINSTANCE.getAbstractRequirement_CoverLinks();

		/**
		 * The meta object literal for the '<em><b>Attribute</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_REQUIREMENT__ATTRIBUTE = eINSTANCE.getAbstractRequirement_Attribute();

		/**
		 * The meta object literal for the '{@link reqtify.impl.RequirementImpl <em>Requirement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see reqtify.impl.RequirementImpl
		 * @see reqtify.impl.ReqtifyPackageImpl#getRequirement()
		 * @generated
		 */
		EClass REQUIREMENT = eINSTANCE.getRequirement();

		/**
		 * The meta object literal for the '{@link reqtify.impl.MacroRequirementImpl <em>Macro Requirement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see reqtify.impl.MacroRequirementImpl
		 * @see reqtify.impl.ReqtifyPackageImpl#getMacroRequirement()
		 * @generated
		 */
		EClass MACRO_REQUIREMENT = eINSTANCE.getMacroRequirement();

		/**
		 * The meta object literal for the '<em><b>Contains</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MACRO_REQUIREMENT__CONTAINS = eINSTANCE.getMacroRequirement_Contains();

		/**
		 * The meta object literal for the '<em><b>New Operation1</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation MACRO_REQUIREMENT___NEW_OPERATION1 = eINSTANCE.getMacroRequirement__NewOperation1();

		/**
		 * The meta object literal for the '{@link reqtify.impl.CoverLinkImpl <em>Cover Link</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see reqtify.impl.CoverLinkImpl
		 * @see reqtify.impl.ReqtifyPackageImpl#getCoverLink()
		 * @generated
		 */
		EClass COVER_LINK = eINSTANCE.getCoverLink();

		/**
		 * The meta object literal for the '<em><b>Link With</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COVER_LINK__LINK_WITH = eINSTANCE.getCoverLink_LinkWith();

		/**
		 * The meta object literal for the '{@link reqtify.impl.AttributeImpl <em>Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see reqtify.impl.AttributeImpl
		 * @see reqtify.impl.ReqtifyPackageImpl#getAttribute()
		 * @generated
		 */
		EClass ATTRIBUTE = eINSTANCE.getAttribute();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE__VALUE = eINSTANCE.getAttribute_Value();

	}

} //ReqtifyPackage
