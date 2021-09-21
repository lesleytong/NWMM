/**
 */
package reqtify;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Requirement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link reqtify.AbstractRequirement#getSection <em>Section</em>}</li>
 *   <li>{@link reqtify.AbstractRequirement#getIsContained <em>Is Contained</em>}</li>
 *   <li>{@link reqtify.AbstractRequirement#getCoverLinks <em>Cover Links</em>}</li>
 *   <li>{@link reqtify.AbstractRequirement#getAttribute <em>Attribute</em>}</li>
 * </ul>
 *
 * @see reqtify.ReqtifyPackage#getAbstractRequirement()
 * @model abstract="true"
 * @generated
 */
public interface AbstractRequirement extends TextElement {
	/**
	 * Returns the value of the '<em><b>Section</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link reqtify.Section#getRequirements <em>Requirements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Section</em>' container reference.
	 * @see #setSection(Section)
	 * @see reqtify.ReqtifyPackage#getAbstractRequirement_Section()
	 * @see reqtify.Section#getRequirements
	 * @model opposite="requirements" transient="false" ordered="false"
	 * @generated
	 */
	Section getSection();

	/**
	 * Sets the value of the '{@link reqtify.AbstractRequirement#getSection <em>Section</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Section</em>' container reference.
	 * @see #getSection()
	 * @generated
	 */
	void setSection(Section value);

	/**
	 * Returns the value of the '<em><b>Is Contained</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link reqtify.MacroRequirement#getContains <em>Contains</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Contained</em>' container reference.
	 * @see #setIsContained(MacroRequirement)
	 * @see reqtify.ReqtifyPackage#getAbstractRequirement_IsContained()
	 * @see reqtify.MacroRequirement#getContains
	 * @model opposite="contains" transient="false" ordered="false"
	 * @generated
	 */
	MacroRequirement getIsContained();

	/**
	 * Sets the value of the '{@link reqtify.AbstractRequirement#getIsContained <em>Is Contained</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Contained</em>' container reference.
	 * @see #getIsContained()
	 * @generated
	 */
	void setIsContained(MacroRequirement value);

	/**
	 * Returns the value of the '<em><b>Cover Links</b></em>' containment reference list.
	 * The list contents are of type {@link reqtify.CoverLink}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cover Links</em>' containment reference list.
	 * @see reqtify.ReqtifyPackage#getAbstractRequirement_CoverLinks()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<CoverLink> getCoverLinks();

	/**
	 * Returns the value of the '<em><b>Attribute</b></em>' containment reference list.
	 * The list contents are of type {@link reqtify.Attribute}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute</em>' containment reference list.
	 * @see reqtify.ReqtifyPackage#getAbstractRequirement_Attribute()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Attribute> getAttribute();

} // AbstractRequirement
