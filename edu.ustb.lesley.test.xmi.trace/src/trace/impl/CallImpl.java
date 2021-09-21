/**
 */
package trace.impl;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;

import trace.Call;
import trace.Index;
import trace.Level;
import trace.TracePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Call</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link trace.impl.CallImpl#getLevel <em>Level</em>}</li>
 *   <li>{@link trace.impl.CallImpl#getIndexes <em>Indexes</em>}</li>
 *   <li>{@link trace.impl.CallImpl#getMethodName <em>Method Name</em>}</li>
 *   <li>{@link trace.impl.CallImpl#getDBAccessesNumber <em>DB Accesses Number</em>}</li>
 *   <li>{@link trace.impl.CallImpl#getDBRowsNumber <em>DB Rows Number</em>}</li>
 *   <li>{@link trace.impl.CallImpl#getCPUTime <em>CPU Time</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CallImpl extends MinimalEObjectImpl.Container implements Call {
	/**
	 * The cached value of the '{@link #getIndexes() <em>Indexes</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndexes()
	 * @generated
	 * @ordered
	 */
	protected EList<Index> indexes;

	/**
	 * The default value of the '{@link #getMethodName() <em>Method Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMethodName()
	 * @generated
	 * @ordered
	 */
	protected static final String METHOD_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMethodName() <em>Method Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMethodName()
	 * @generated
	 * @ordered
	 */
	protected String methodName = METHOD_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getDBAccessesNumber() <em>DB Accesses Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDBAccessesNumber()
	 * @generated
	 * @ordered
	 */
	protected static final int DB_ACCESSES_NUMBER_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getDBAccessesNumber() <em>DB Accesses Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDBAccessesNumber()
	 * @generated
	 * @ordered
	 */
	protected int dbAccessesNumber = DB_ACCESSES_NUMBER_EDEFAULT;

	/**
	 * The default value of the '{@link #getDBRowsNumber() <em>DB Rows Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDBRowsNumber()
	 * @generated
	 * @ordered
	 */
	protected static final int DB_ROWS_NUMBER_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getDBRowsNumber() <em>DB Rows Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDBRowsNumber()
	 * @generated
	 * @ordered
	 */
	protected int dbRowsNumber = DB_ROWS_NUMBER_EDEFAULT;

	/**
	 * The default value of the '{@link #getCPUTime() <em>CPU Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCPUTime()
	 * @generated
	 * @ordered
	 */
	protected static final int CPU_TIME_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getCPUTime() <em>CPU Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCPUTime()
	 * @generated
	 * @ordered
	 */
	protected int cpuTime = CPU_TIME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CallImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TracePackage.Literals.CALL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Level getLevel() {
		if (eContainerFeatureID() != TracePackage.CALL__LEVEL) return null;
		return (Level)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLevel(Level newLevel, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newLevel, TracePackage.CALL__LEVEL, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLevel(Level newLevel) {
		if (newLevel != eInternalContainer() || (eContainerFeatureID() != TracePackage.CALL__LEVEL && newLevel != null)) {
			if (EcoreUtil.isAncestor(this, newLevel))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newLevel != null)
				msgs = ((InternalEObject)newLevel).eInverseAdd(this, TracePackage.LEVEL__CALLS, Level.class, msgs);
			msgs = basicSetLevel(newLevel, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TracePackage.CALL__LEVEL, newLevel, newLevel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Index> getIndexes() {
		if (indexes == null) {
			indexes = new EObjectResolvingEList<Index>(Index.class, this, TracePackage.CALL__INDEXES);
		}
		return indexes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMethodName(String newMethodName) {
		String oldMethodName = methodName;
		methodName = newMethodName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TracePackage.CALL__METHOD_NAME, oldMethodName, methodName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getDBAccessesNumber() {
		return dbAccessesNumber;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDBAccessesNumber(int newDBAccessesNumber) {
		int oldDBAccessesNumber = dbAccessesNumber;
		dbAccessesNumber = newDBAccessesNumber;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TracePackage.CALL__DB_ACCESSES_NUMBER, oldDBAccessesNumber, dbAccessesNumber));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getDBRowsNumber() {
		return dbRowsNumber;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDBRowsNumber(int newDBRowsNumber) {
		int oldDBRowsNumber = dbRowsNumber;
		dbRowsNumber = newDBRowsNumber;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TracePackage.CALL__DB_ROWS_NUMBER, oldDBRowsNumber, dbRowsNumber));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getCPUTime() {
		return cpuTime;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCPUTime(int newCPUTime) {
		int oldCPUTime = cpuTime;
		cpuTime = newCPUTime;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TracePackage.CALL__CPU_TIME, oldCPUTime, cpuTime));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TracePackage.CALL__LEVEL:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetLevel((Level)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TracePackage.CALL__LEVEL:
				return basicSetLevel(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case TracePackage.CALL__LEVEL:
				return eInternalContainer().eInverseRemove(this, TracePackage.LEVEL__CALLS, Level.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TracePackage.CALL__LEVEL:
				return getLevel();
			case TracePackage.CALL__INDEXES:
				return getIndexes();
			case TracePackage.CALL__METHOD_NAME:
				return getMethodName();
			case TracePackage.CALL__DB_ACCESSES_NUMBER:
				return getDBAccessesNumber();
			case TracePackage.CALL__DB_ROWS_NUMBER:
				return getDBRowsNumber();
			case TracePackage.CALL__CPU_TIME:
				return getCPUTime();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TracePackage.CALL__LEVEL:
				setLevel((Level)newValue);
				return;
			case TracePackage.CALL__INDEXES:
				getIndexes().clear();
				getIndexes().addAll((Collection<? extends Index>)newValue);
				return;
			case TracePackage.CALL__METHOD_NAME:
				setMethodName((String)newValue);
				return;
			case TracePackage.CALL__DB_ACCESSES_NUMBER:
				setDBAccessesNumber((Integer)newValue);
				return;
			case TracePackage.CALL__DB_ROWS_NUMBER:
				setDBRowsNumber((Integer)newValue);
				return;
			case TracePackage.CALL__CPU_TIME:
				setCPUTime((Integer)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case TracePackage.CALL__LEVEL:
				setLevel((Level)null);
				return;
			case TracePackage.CALL__INDEXES:
				getIndexes().clear();
				return;
			case TracePackage.CALL__METHOD_NAME:
				setMethodName(METHOD_NAME_EDEFAULT);
				return;
			case TracePackage.CALL__DB_ACCESSES_NUMBER:
				setDBAccessesNumber(DB_ACCESSES_NUMBER_EDEFAULT);
				return;
			case TracePackage.CALL__DB_ROWS_NUMBER:
				setDBRowsNumber(DB_ROWS_NUMBER_EDEFAULT);
				return;
			case TracePackage.CALL__CPU_TIME:
				setCPUTime(CPU_TIME_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case TracePackage.CALL__LEVEL:
				return getLevel() != null;
			case TracePackage.CALL__INDEXES:
				return indexes != null && !indexes.isEmpty();
			case TracePackage.CALL__METHOD_NAME:
				return METHOD_NAME_EDEFAULT == null ? methodName != null : !METHOD_NAME_EDEFAULT.equals(methodName);
			case TracePackage.CALL__DB_ACCESSES_NUMBER:
				return dbAccessesNumber != DB_ACCESSES_NUMBER_EDEFAULT;
			case TracePackage.CALL__DB_ROWS_NUMBER:
				return dbRowsNumber != DB_ROWS_NUMBER_EDEFAULT;
			case TracePackage.CALL__CPU_TIME:
				return cpuTime != CPU_TIME_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (methodName: ");
		result.append(methodName);
		result.append(", DBAccessesNumber: ");
		result.append(dbAccessesNumber);
		result.append(", DBRowsNumber: ");
		result.append(dbRowsNumber);
		result.append(", CPUTime: ");
		result.append(cpuTime);
		result.append(')');
		return result.toString();
	}

} //CallImpl
