/**
 */
package reqtify;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Macro Requirement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link reqtify.MacroRequirement#getContains <em>Contains</em>}</li>
 * </ul>
 *
 * @see reqtify.ReqtifyPackage#getMacroRequirement()
 * @model
 * @generated
 */
public interface MacroRequirement extends AbstractRequirement {
	/**
	 * Returns the value of the '<em><b>Contains</b></em>' containment reference list.
	 * The list contents are of type {@link reqtify.AbstractRequirement}.
	 * It is bidirectional and its opposite is '{@link reqtify.AbstractRequirement#getIsContained <em>Is Contained</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contains</em>' containment reference list.
	 * @see reqtify.ReqtifyPackage#getMacroRequirement_Contains()
	 * @see reqtify.AbstractRequirement#getIsContained
	 * @model opposite="isContained" containment="true" ordered="false"
	 * @generated
	 */
	EList<AbstractRequirement> getContains();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void newOperation1();

} // MacroRequirement
