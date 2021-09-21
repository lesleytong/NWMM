/**
 */
package sharego.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import sharego.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SharegoFactoryImpl extends EFactoryImpl implements SharegoFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SharegoFactory init() {
		try {
			SharegoFactory theSharegoFactory = (SharegoFactory)EPackage.Registry.INSTANCE.getEFactory(SharegoPackage.eNS_URI);
			if (theSharegoFactory != null) {
				return theSharegoFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new SharegoFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SharegoFactoryImpl() {
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
			case SharegoPackage.OPERATION: return createOperation();
			case SharegoPackage.SERVICE: return createService();
			case SharegoPackage.BUSINESS_OBJECT: return createBusinessObject();
			case SharegoPackage.SERVICE_OPERATION: return createServiceOperation();
			case SharegoPackage.BUSINESS_OBJECT_OPERATION: return createBusinessObjectOperation();
			case SharegoPackage.BUSINESS_RULE: return createBusinessRule();
			case SharegoPackage.SERVICE2_BUSINESS_OBJECT_FLOW: return createService2BusinessObjectFlow();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Operation createOperation() {
		OperationImpl operation = new OperationImpl();
		return operation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Service createService() {
		ServiceImpl service = new ServiceImpl();
		return service;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BusinessObject createBusinessObject() {
		BusinessObjectImpl businessObject = new BusinessObjectImpl();
		return businessObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ServiceOperation createServiceOperation() {
		ServiceOperationImpl serviceOperation = new ServiceOperationImpl();
		return serviceOperation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BusinessObjectOperation createBusinessObjectOperation() {
		BusinessObjectOperationImpl businessObjectOperation = new BusinessObjectOperationImpl();
		return businessObjectOperation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BusinessRule createBusinessRule() {
		BusinessRuleImpl businessRule = new BusinessRuleImpl();
		return businessRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Service2BusinessObjectFlow createService2BusinessObjectFlow() {
		Service2BusinessObjectFlowImpl service2BusinessObjectFlow = new Service2BusinessObjectFlowImpl();
		return service2BusinessObjectFlow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SharegoPackage getSharegoPackage() {
		return (SharegoPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static SharegoPackage getPackage() {
		return SharegoPackage.eINSTANCE;
	}

} //SharegoFactoryImpl
