/**
 */
package my.util;

import my.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see my.MyPackage
 * @generated
 */
public class MyAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static MyPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MyAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = MyPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MySwitch<Adapter> modelSwitch =
		new MySwitch<Adapter>() {
			@Override
			public Adapter caseComparisonN(ComparisonN object) {
				return createComparisonNAdapter();
			}
			@Override
			public Adapter caseMatchN(MatchN object) {
				return createMatchNAdapter();
			}
			@Override
			public Adapter caseDiffN(DiffN object) {
				return createDiffNAdapter();
			}
			@Override
			public Adapter caseConflictN(ConflictN object) {
				return createConflictNAdapter();
			}
			@Override
			public Adapter caseResourceAttachmentChange(ResourceAttachmentChange object) {
				return createResourceAttachmentChangeAdapter();
			}
			@Override
			public Adapter caseResourceLocationChange(ResourceLocationChange object) {
				return createResourceLocationChangeAdapter();
			}
			@Override
			public Adapter caseReferenceChange(ReferenceChange object) {
				return createReferenceChangeAdapter();
			}
			@Override
			public Adapter caseAttributeChange(AttributeChange object) {
				return createAttributeChangeAdapter();
			}
			@Override
			public Adapter caseFeatureMapChange(FeatureMapChange object) {
				return createFeatureMapChangeAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link my.ComparisonN <em>Comparison N</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see my.ComparisonN
	 * @generated
	 */
	public Adapter createComparisonNAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link my.MatchN <em>Match N</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see my.MatchN
	 * @generated
	 */
	public Adapter createMatchNAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link my.DiffN <em>Diff N</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see my.DiffN
	 * @generated
	 */
	public Adapter createDiffNAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link my.ConflictN <em>Conflict N</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see my.ConflictN
	 * @generated
	 */
	public Adapter createConflictNAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link my.ResourceAttachmentChange <em>Resource Attachment Change</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see my.ResourceAttachmentChange
	 * @generated
	 */
	public Adapter createResourceAttachmentChangeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link my.ResourceLocationChange <em>Resource Location Change</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see my.ResourceLocationChange
	 * @generated
	 */
	public Adapter createResourceLocationChangeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link my.ReferenceChange <em>Reference Change</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see my.ReferenceChange
	 * @generated
	 */
	public Adapter createReferenceChangeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link my.AttributeChange <em>Attribute Change</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see my.AttributeChange
	 * @generated
	 */
	public Adapter createAttributeChangeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link my.FeatureMapChange <em>Feature Map Change</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see my.FeatureMapChange
	 * @generated
	 */
	public Adapter createFeatureMapChangeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //MyAdapterFactory
