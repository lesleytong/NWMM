/**
 */
package fsm;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see fsm.FsmFactory
 * @model kind="package"
 * @generated
 */
public interface FsmPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "fsm";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///fsm";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "fsm";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	FsmPackage eINSTANCE = fsm.impl.FsmPackageImpl.init();

	/**
	 * The meta object id for the '{@link fsm.impl.MgaObjectImpl <em>Mga Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fsm.impl.MgaObjectImpl
	 * @see fsm.impl.FsmPackageImpl#getMgaObject()
	 * @generated
	 */
	int MGA_OBJECT = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MGA_OBJECT__NAME = 0;

	/**
	 * The feature id for the '<em><b>Position</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MGA_OBJECT__POSITION = 1;

	/**
	 * The number of structural features of the '<em>Mga Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MGA_OBJECT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Mga Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MGA_OBJECT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link fsm.impl.TransitionImpl <em>Transition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fsm.impl.TransitionImpl
	 * @see fsm.impl.FsmPackageImpl#getTransition()
	 * @generated
	 */
	int TRANSITION = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__NAME = MGA_OBJECT__NAME;

	/**
	 * The feature id for the '<em><b>Position</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__POSITION = MGA_OBJECT__POSITION;

	/**
	 * The feature id for the '<em><b>State Machine</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__STATE_MACHINE = MGA_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Association State State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__ASSOCIATION_STATE_STATE = MGA_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Transition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION_FEATURE_COUNT = MGA_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Transition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION_OPERATION_COUNT = MGA_OBJECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link fsm.impl.StateImpl <em>State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fsm.impl.StateImpl
	 * @see fsm.impl.FsmPackageImpl#getState()
	 * @generated
	 */
	int STATE = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__NAME = MGA_OBJECT__NAME;

	/**
	 * The feature id for the '<em><b>Position</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__POSITION = MGA_OBJECT__POSITION;

	/**
	 * The feature id for the '<em><b>State Machine</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__STATE_MACHINE = MGA_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Association State Statedst</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__ASSOCIATION_STATE_STATEDST = MGA_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Association State Statesrc</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__ASSOCIATION_STATE_STATESRC = MGA_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_FEATURE_COUNT = MGA_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_OPERATION_COUNT = MGA_OBJECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link fsm.impl.StateMachineImpl <em>State Machine</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fsm.impl.StateMachineImpl
	 * @see fsm.impl.FsmPackageImpl#getStateMachine()
	 * @generated
	 */
	int STATE_MACHINE = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__NAME = MGA_OBJECT__NAME;

	/**
	 * The feature id for the '<em><b>Position</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__POSITION = MGA_OBJECT__POSITION;

	/**
	 * The feature id for the '<em><b>Root Folder</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__ROOT_FOLDER = MGA_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>State</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__STATE = MGA_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Transition</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__TRANSITION = MGA_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>State Machine</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_FEATURE_COUNT = MGA_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>State Machine</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_OPERATION_COUNT = MGA_OBJECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link fsm.impl.RootFolderImpl <em>Root Folder</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fsm.impl.RootFolderImpl
	 * @see fsm.impl.FsmPackageImpl#getRootFolder()
	 * @generated
	 */
	int ROOT_FOLDER = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_FOLDER__NAME = 0;

	/**
	 * The feature id for the '<em><b>State Machine</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_FOLDER__STATE_MACHINE = 1;

	/**
	 * The number of structural features of the '<em>Root Folder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_FOLDER_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Root Folder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_FOLDER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link fsm.impl.AssociationStateStateImpl <em>Association State State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fsm.impl.AssociationStateStateImpl
	 * @see fsm.impl.FsmPackageImpl#getAssociationStateState()
	 * @generated
	 */
	int ASSOCIATION_STATE_STATE = 5;

	/**
	 * The feature id for the '<em><b>Transition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION_STATE_STATE__TRANSITION = 0;

	/**
	 * The feature id for the '<em><b>Dst Transition</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION_STATE_STATE__DST_TRANSITION = 1;

	/**
	 * The feature id for the '<em><b>Src Transition</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION_STATE_STATE__SRC_TRANSITION = 2;

	/**
	 * The number of structural features of the '<em>Association State State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION_STATE_STATE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Association State State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION_STATE_STATE_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link fsm.MgaObject <em>Mga Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mga Object</em>'.
	 * @see fsm.MgaObject
	 * @generated
	 */
	EClass getMgaObject();

	/**
	 * Returns the meta object for the attribute '{@link fsm.MgaObject#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see fsm.MgaObject#getName()
	 * @see #getMgaObject()
	 * @generated
	 */
	EAttribute getMgaObject_Name();

	/**
	 * Returns the meta object for the attribute '{@link fsm.MgaObject#getPosition <em>Position</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Position</em>'.
	 * @see fsm.MgaObject#getPosition()
	 * @see #getMgaObject()
	 * @generated
	 */
	EAttribute getMgaObject_Position();

	/**
	 * Returns the meta object for class '{@link fsm.Transition <em>Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Transition</em>'.
	 * @see fsm.Transition
	 * @generated
	 */
	EClass getTransition();

	/**
	 * Returns the meta object for the container reference '{@link fsm.Transition#getStateMachine <em>State Machine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>State Machine</em>'.
	 * @see fsm.Transition#getStateMachine()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_StateMachine();

	/**
	 * Returns the meta object for the reference '{@link fsm.Transition#getAssociationStateState <em>Association State State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Association State State</em>'.
	 * @see fsm.Transition#getAssociationStateState()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_AssociationStateState();

	/**
	 * Returns the meta object for class '{@link fsm.State <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State</em>'.
	 * @see fsm.State
	 * @generated
	 */
	EClass getState();

	/**
	 * Returns the meta object for the container reference '{@link fsm.State#getStateMachine <em>State Machine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>State Machine</em>'.
	 * @see fsm.State#getStateMachine()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_StateMachine();

	/**
	 * Returns the meta object for the reference list '{@link fsm.State#getAssociationStateStatedst <em>Association State Statedst</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Association State Statedst</em>'.
	 * @see fsm.State#getAssociationStateStatedst()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_AssociationStateStatedst();

	/**
	 * Returns the meta object for the reference list '{@link fsm.State#getAssociationStateStatesrc <em>Association State Statesrc</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Association State Statesrc</em>'.
	 * @see fsm.State#getAssociationStateStatesrc()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_AssociationStateStatesrc();

	/**
	 * Returns the meta object for class '{@link fsm.StateMachine <em>State Machine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State Machine</em>'.
	 * @see fsm.StateMachine
	 * @generated
	 */
	EClass getStateMachine();

	/**
	 * Returns the meta object for the container reference '{@link fsm.StateMachine#getRootFolder <em>Root Folder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Root Folder</em>'.
	 * @see fsm.StateMachine#getRootFolder()
	 * @see #getStateMachine()
	 * @generated
	 */
	EReference getStateMachine_RootFolder();

	/**
	 * Returns the meta object for the containment reference list '{@link fsm.StateMachine#getState <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>State</em>'.
	 * @see fsm.StateMachine#getState()
	 * @see #getStateMachine()
	 * @generated
	 */
	EReference getStateMachine_State();

	/**
	 * Returns the meta object for the containment reference list '{@link fsm.StateMachine#getTransition <em>Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Transition</em>'.
	 * @see fsm.StateMachine#getTransition()
	 * @see #getStateMachine()
	 * @generated
	 */
	EReference getStateMachine_Transition();

	/**
	 * Returns the meta object for class '{@link fsm.RootFolder <em>Root Folder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Root Folder</em>'.
	 * @see fsm.RootFolder
	 * @generated
	 */
	EClass getRootFolder();

	/**
	 * Returns the meta object for the attribute '{@link fsm.RootFolder#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see fsm.RootFolder#getName()
	 * @see #getRootFolder()
	 * @generated
	 */
	EAttribute getRootFolder_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link fsm.RootFolder#getStateMachine <em>State Machine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>State Machine</em>'.
	 * @see fsm.RootFolder#getStateMachine()
	 * @see #getRootFolder()
	 * @generated
	 */
	EReference getRootFolder_StateMachine();

	/**
	 * Returns the meta object for class '{@link fsm.AssociationStateState <em>Association State State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Association State State</em>'.
	 * @see fsm.AssociationStateState
	 * @generated
	 */
	EClass getAssociationStateState();

	/**
	 * Returns the meta object for the reference '{@link fsm.AssociationStateState#getTransition <em>Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Transition</em>'.
	 * @see fsm.AssociationStateState#getTransition()
	 * @see #getAssociationStateState()
	 * @generated
	 */
	EReference getAssociationStateState_Transition();

	/**
	 * Returns the meta object for the reference list '{@link fsm.AssociationStateState#getDstTransition <em>Dst Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Dst Transition</em>'.
	 * @see fsm.AssociationStateState#getDstTransition()
	 * @see #getAssociationStateState()
	 * @generated
	 */
	EReference getAssociationStateState_DstTransition();

	/**
	 * Returns the meta object for the reference list '{@link fsm.AssociationStateState#getSrcTransition <em>Src Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Src Transition</em>'.
	 * @see fsm.AssociationStateState#getSrcTransition()
	 * @see #getAssociationStateState()
	 * @generated
	 */
	EReference getAssociationStateState_SrcTransition();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	FsmFactory getFsmFactory();

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
		 * The meta object literal for the '{@link fsm.impl.MgaObjectImpl <em>Mga Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fsm.impl.MgaObjectImpl
		 * @see fsm.impl.FsmPackageImpl#getMgaObject()
		 * @generated
		 */
		EClass MGA_OBJECT = eINSTANCE.getMgaObject();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MGA_OBJECT__NAME = eINSTANCE.getMgaObject_Name();

		/**
		 * The meta object literal for the '<em><b>Position</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MGA_OBJECT__POSITION = eINSTANCE.getMgaObject_Position();

		/**
		 * The meta object literal for the '{@link fsm.impl.TransitionImpl <em>Transition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fsm.impl.TransitionImpl
		 * @see fsm.impl.FsmPackageImpl#getTransition()
		 * @generated
		 */
		EClass TRANSITION = eINSTANCE.getTransition();

		/**
		 * The meta object literal for the '<em><b>State Machine</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSITION__STATE_MACHINE = eINSTANCE.getTransition_StateMachine();

		/**
		 * The meta object literal for the '<em><b>Association State State</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSITION__ASSOCIATION_STATE_STATE = eINSTANCE.getTransition_AssociationStateState();

		/**
		 * The meta object literal for the '{@link fsm.impl.StateImpl <em>State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fsm.impl.StateImpl
		 * @see fsm.impl.FsmPackageImpl#getState()
		 * @generated
		 */
		EClass STATE = eINSTANCE.getState();

		/**
		 * The meta object literal for the '<em><b>State Machine</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__STATE_MACHINE = eINSTANCE.getState_StateMachine();

		/**
		 * The meta object literal for the '<em><b>Association State Statedst</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__ASSOCIATION_STATE_STATEDST = eINSTANCE.getState_AssociationStateStatedst();

		/**
		 * The meta object literal for the '<em><b>Association State Statesrc</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__ASSOCIATION_STATE_STATESRC = eINSTANCE.getState_AssociationStateStatesrc();

		/**
		 * The meta object literal for the '{@link fsm.impl.StateMachineImpl <em>State Machine</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fsm.impl.StateMachineImpl
		 * @see fsm.impl.FsmPackageImpl#getStateMachine()
		 * @generated
		 */
		EClass STATE_MACHINE = eINSTANCE.getStateMachine();

		/**
		 * The meta object literal for the '<em><b>Root Folder</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_MACHINE__ROOT_FOLDER = eINSTANCE.getStateMachine_RootFolder();

		/**
		 * The meta object literal for the '<em><b>State</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_MACHINE__STATE = eINSTANCE.getStateMachine_State();

		/**
		 * The meta object literal for the '<em><b>Transition</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_MACHINE__TRANSITION = eINSTANCE.getStateMachine_Transition();

		/**
		 * The meta object literal for the '{@link fsm.impl.RootFolderImpl <em>Root Folder</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fsm.impl.RootFolderImpl
		 * @see fsm.impl.FsmPackageImpl#getRootFolder()
		 * @generated
		 */
		EClass ROOT_FOLDER = eINSTANCE.getRootFolder();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ROOT_FOLDER__NAME = eINSTANCE.getRootFolder_Name();

		/**
		 * The meta object literal for the '<em><b>State Machine</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT_FOLDER__STATE_MACHINE = eINSTANCE.getRootFolder_StateMachine();

		/**
		 * The meta object literal for the '{@link fsm.impl.AssociationStateStateImpl <em>Association State State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fsm.impl.AssociationStateStateImpl
		 * @see fsm.impl.FsmPackageImpl#getAssociationStateState()
		 * @generated
		 */
		EClass ASSOCIATION_STATE_STATE = eINSTANCE.getAssociationStateState();

		/**
		 * The meta object literal for the '<em><b>Transition</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASSOCIATION_STATE_STATE__TRANSITION = eINSTANCE.getAssociationStateState_Transition();

		/**
		 * The meta object literal for the '<em><b>Dst Transition</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASSOCIATION_STATE_STATE__DST_TRANSITION = eINSTANCE.getAssociationStateState_DstTransition();

		/**
		 * The meta object literal for the '<em><b>Src Transition</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASSOCIATION_STATE_STATE__SRC_TRANSITION = eINSTANCE.getAssociationStateState_SrcTransition();

	}

} //FsmPackage
