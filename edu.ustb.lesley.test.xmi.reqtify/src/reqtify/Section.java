/**
 */
package reqtify;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Section</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link reqtify.Section#getDocument <em>Document</em>}</li>
 *   <li>{@link reqtify.Section#getSectionChildren <em>Section Children</em>}</li>
 *   <li>{@link reqtify.Section#getSectionParent <em>Section Parent</em>}</li>
 *   <li>{@link reqtify.Section#getRequirements <em>Requirements</em>}</li>
 * </ul>
 *
 * @see reqtify.ReqtifyPackage#getSection()
 * @model
 * @generated
 */
public interface Section extends TextElement {
	/**
	 * Returns the value of the '<em><b>Document</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link reqtify.Document#getSections <em>Sections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Document</em>' container reference.
	 * @see #setDocument(Document)
	 * @see reqtify.ReqtifyPackage#getSection_Document()
	 * @see reqtify.Document#getSections
	 * @model opposite="sections" transient="false" ordered="false"
	 * @generated
	 */
	Document getDocument();

	/**
	 * Sets the value of the '{@link reqtify.Section#getDocument <em>Document</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Document</em>' container reference.
	 * @see #getDocument()
	 * @generated
	 */
	void setDocument(Document value);

	/**
	 * Returns the value of the '<em><b>Section Children</b></em>' containment reference list.
	 * The list contents are of type {@link reqtify.Section}.
	 * It is bidirectional and its opposite is '{@link reqtify.Section#getSectionParent <em>Section Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Section Children</em>' containment reference list.
	 * @see reqtify.ReqtifyPackage#getSection_SectionChildren()
	 * @see reqtify.Section#getSectionParent
	 * @model opposite="sectionParent" containment="true" ordered="false"
	 * @generated
	 */
	EList<Section> getSectionChildren();

	/**
	 * Returns the value of the '<em><b>Section Parent</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link reqtify.Section#getSectionChildren <em>Section Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Section Parent</em>' container reference.
	 * @see #setSectionParent(Section)
	 * @see reqtify.ReqtifyPackage#getSection_SectionParent()
	 * @see reqtify.Section#getSectionChildren
	 * @model opposite="sectionChildren" transient="false" ordered="false"
	 * @generated
	 */
	Section getSectionParent();

	/**
	 * Sets the value of the '{@link reqtify.Section#getSectionParent <em>Section Parent</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Section Parent</em>' container reference.
	 * @see #getSectionParent()
	 * @generated
	 */
	void setSectionParent(Section value);

	/**
	 * Returns the value of the '<em><b>Requirements</b></em>' containment reference list.
	 * The list contents are of type {@link reqtify.AbstractRequirement}.
	 * It is bidirectional and its opposite is '{@link reqtify.AbstractRequirement#getSection <em>Section</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Requirements</em>' containment reference list.
	 * @see reqtify.ReqtifyPackage#getSection_Requirements()
	 * @see reqtify.AbstractRequirement#getSection
	 * @model opposite="section" containment="true" ordered="false"
	 * @generated
	 */
	EList<AbstractRequirement> getRequirements();

} // Section
