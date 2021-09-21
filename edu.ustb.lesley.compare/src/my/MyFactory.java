/**
 */
package my;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see my.MyPackage
 * @generated
 */
public interface MyFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	MyFactory eINSTANCE = my.impl.MyFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Comparison N</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Comparison N</em>'.
	 * @generated
	 */
	ComparisonN createComparisonN();

	/**
	 * Returns a new object of class '<em>Match N</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Match N</em>'.
	 * @generated
	 */
	MatchN createMatchN();

	/**
	 * Returns a new object of class '<em>Diff N</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Diff N</em>'.
	 * @generated
	 */
	DiffN createDiffN();

	/**
	 * Returns a new object of class '<em>Conflict N</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Conflict N</em>'.
	 * @generated
	 */
	ConflictN createConflictN();

	/**
	 * Returns a new object of class '<em>Resource Attachment Change</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Resource Attachment Change</em>'.
	 * @generated
	 */
	ResourceAttachmentChange createResourceAttachmentChange();

	/**
	 * Returns a new object of class '<em>Resource Location Change</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Resource Location Change</em>'.
	 * @generated
	 */
	ResourceLocationChange createResourceLocationChange();

	/**
	 * Returns a new object of class '<em>Reference Change</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Reference Change</em>'.
	 * @generated
	 */
	ReferenceChange createReferenceChange();

	/**
	 * Returns a new object of class '<em>Attribute Change</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attribute Change</em>'.
	 * @generated
	 */
	AttributeChange createAttributeChange();

	/**
	 * Returns a new object of class '<em>Feature Map Change</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Feature Map Change</em>'.
	 * @generated
	 */
	FeatureMapChange createFeatureMapChange();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	MyPackage getMyPackage();

} //MyFactory
