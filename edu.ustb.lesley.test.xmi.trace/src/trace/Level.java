/**
 */
package trace;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Level</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link trace.Level#getTrace <em>Trace</em>}</li>
 *   <li>{@link trace.Level#getCalls <em>Calls</em>}</li>
 *   <li>{@link trace.Level#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see trace.TracePackage#getLevel()
 * @model
 * @generated
 */
public interface Level extends EObject {
	/**
	 * Returns the value of the '<em><b>Trace</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link trace.Trace#getLevels <em>Levels</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Trace</em>' container reference.
	 * @see #setTrace(Trace)
	 * @see trace.TracePackage#getLevel_Trace()
	 * @see trace.Trace#getLevels
	 * @model opposite="levels" required="true" transient="false" ordered="false"
	 * @generated
	 */
	Trace getTrace();

	/**
	 * Sets the value of the '{@link trace.Level#getTrace <em>Trace</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Trace</em>' container reference.
	 * @see #getTrace()
	 * @generated
	 */
	void setTrace(Trace value);

	/**
	 * Returns the value of the '<em><b>Calls</b></em>' containment reference list.
	 * The list contents are of type {@link trace.Call}.
	 * It is bidirectional and its opposite is '{@link trace.Call#getLevel <em>Level</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Calls</em>' containment reference list.
	 * @see trace.TracePackage#getLevel_Calls()
	 * @see trace.Call#getLevel
	 * @model opposite="level" containment="true"
	 * @generated
	 */
	EList<Call> getCalls();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see trace.TracePackage#getLevel_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link trace.Level#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // Level
