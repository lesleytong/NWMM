/**
 */
package make;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rule Dep</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link make.RuleDep#getRuledep <em>Ruledep</em>}</li>
 * </ul>
 *
 * @see make.MakePackage#getRuleDep()
 * @model
 * @generated
 */
public interface RuleDep extends Dependency {
	/**
	 * Returns the value of the '<em><b>Ruledep</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ruledep</em>' reference.
	 * @see #setRuledep(Rule)
	 * @see make.MakePackage#getRuleDep_Ruledep()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	Rule getRuledep();

	/**
	 * Sets the value of the '{@link make.RuleDep#getRuledep <em>Ruledep</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ruledep</em>' reference.
	 * @see #getRuledep()
	 * @generated
	 */
	void setRuledep(Rule value);

} // RuleDep
