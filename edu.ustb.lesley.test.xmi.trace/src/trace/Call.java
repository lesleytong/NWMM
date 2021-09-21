/**
 */
package trace;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Call</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link trace.Call#getLevel <em>Level</em>}</li>
 *   <li>{@link trace.Call#getIndexes <em>Indexes</em>}</li>
 *   <li>{@link trace.Call#getMethodName <em>Method Name</em>}</li>
 *   <li>{@link trace.Call#getDBAccessesNumber <em>DB Accesses Number</em>}</li>
 *   <li>{@link trace.Call#getDBRowsNumber <em>DB Rows Number</em>}</li>
 *   <li>{@link trace.Call#getCPUTime <em>CPU Time</em>}</li>
 * </ul>
 *
 * @see trace.TracePackage#getCall()
 * @model
 * @generated
 */
public interface Call extends EObject {
	/**
	 * Returns the value of the '<em><b>Level</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link trace.Level#getCalls <em>Calls</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Level</em>' container reference.
	 * @see #setLevel(Level)
	 * @see trace.TracePackage#getCall_Level()
	 * @see trace.Level#getCalls
	 * @model opposite="calls" required="true" transient="false" ordered="false"
	 * @generated
	 */
	Level getLevel();

	/**
	 * Sets the value of the '{@link trace.Call#getLevel <em>Level</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Level</em>' container reference.
	 * @see #getLevel()
	 * @generated
	 */
	void setLevel(Level value);

	/**
	 * Returns the value of the '<em><b>Indexes</b></em>' reference list.
	 * The list contents are of type {@link trace.Index}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Indexes</em>' reference list.
	 * @see trace.TracePackage#getCall_Indexes()
	 * @model
	 * @generated
	 */
	EList<Index> getIndexes();

	/**
	 * Returns the value of the '<em><b>Method Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Method Name</em>' attribute.
	 * @see #setMethodName(String)
	 * @see trace.TracePackage#getCall_MethodName()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	String getMethodName();

	/**
	 * Sets the value of the '{@link trace.Call#getMethodName <em>Method Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Method Name</em>' attribute.
	 * @see #getMethodName()
	 * @generated
	 */
	void setMethodName(String value);

	/**
	 * Returns the value of the '<em><b>DB Accesses Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>DB Accesses Number</em>' attribute.
	 * @see #setDBAccessesNumber(int)
	 * @see trace.TracePackage#getCall_DBAccessesNumber()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	int getDBAccessesNumber();

	/**
	 * Sets the value of the '{@link trace.Call#getDBAccessesNumber <em>DB Accesses Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>DB Accesses Number</em>' attribute.
	 * @see #getDBAccessesNumber()
	 * @generated
	 */
	void setDBAccessesNumber(int value);

	/**
	 * Returns the value of the '<em><b>DB Rows Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>DB Rows Number</em>' attribute.
	 * @see #setDBRowsNumber(int)
	 * @see trace.TracePackage#getCall_DBRowsNumber()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	int getDBRowsNumber();

	/**
	 * Sets the value of the '{@link trace.Call#getDBRowsNumber <em>DB Rows Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>DB Rows Number</em>' attribute.
	 * @see #getDBRowsNumber()
	 * @generated
	 */
	void setDBRowsNumber(int value);

	/**
	 * Returns the value of the '<em><b>CPU Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>CPU Time</em>' attribute.
	 * @see #setCPUTime(int)
	 * @see trace.TracePackage#getCall_CPUTime()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	int getCPUTime();

	/**
	 * Sets the value of the '{@link trace.Call#getCPUTime <em>CPU Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>CPU Time</em>' attribute.
	 * @see #getCPUTime()
	 * @generated
	 */
	void setCPUTime(int value);

} // Call
