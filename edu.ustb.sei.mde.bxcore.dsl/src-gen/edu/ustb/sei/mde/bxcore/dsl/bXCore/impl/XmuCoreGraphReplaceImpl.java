/**
 * generated by Xtext 2.18.0.M3
 */
package edu.ustb.sei.mde.bxcore.dsl.bXCore.impl;

import edu.ustb.sei.mde.bxcore.dsl.bXCore.BXCorePackage;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.Conversion;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.Pattern;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.XmuCoreGraphReplace;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Xmu Core Graph Replace</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.ustb.sei.mde.bxcore.dsl.bXCore.impl.XmuCoreGraphReplaceImpl#getSource <em>Source</em>}</li>
 *   <li>{@link edu.ustb.sei.mde.bxcore.dsl.bXCore.impl.XmuCoreGraphReplaceImpl#getView <em>View</em>}</li>
 *   <li>{@link edu.ustb.sei.mde.bxcore.dsl.bXCore.impl.XmuCoreGraphReplaceImpl#getConversions <em>Conversions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class XmuCoreGraphReplaceImpl extends XmuCoreCompositionChildStatementImpl implements XmuCoreGraphReplace
{
  /**
   * The cached value of the '{@link #getSource() <em>Source</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSource()
   * @generated
   * @ordered
   */
  protected Pattern source;

  /**
   * The cached value of the '{@link #getView() <em>View</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getView()
   * @generated
   * @ordered
   */
  protected Pattern view;

  /**
   * The cached value of the '{@link #getConversions() <em>Conversions</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getConversions()
   * @generated
   * @ordered
   */
  protected EList<Conversion> conversions;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected XmuCoreGraphReplaceImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return BXCorePackage.Literals.XMU_CORE_GRAPH_REPLACE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Pattern getSource()
  {
    return source;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetSource(Pattern newSource, NotificationChain msgs)
  {
    Pattern oldSource = source;
    source = newSource;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BXCorePackage.XMU_CORE_GRAPH_REPLACE__SOURCE, oldSource, newSource);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setSource(Pattern newSource)
  {
    if (newSource != source)
    {
      NotificationChain msgs = null;
      if (source != null)
        msgs = ((InternalEObject)source).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BXCorePackage.XMU_CORE_GRAPH_REPLACE__SOURCE, null, msgs);
      if (newSource != null)
        msgs = ((InternalEObject)newSource).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BXCorePackage.XMU_CORE_GRAPH_REPLACE__SOURCE, null, msgs);
      msgs = basicSetSource(newSource, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, BXCorePackage.XMU_CORE_GRAPH_REPLACE__SOURCE, newSource, newSource));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Pattern getView()
  {
    return view;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetView(Pattern newView, NotificationChain msgs)
  {
    Pattern oldView = view;
    view = newView;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BXCorePackage.XMU_CORE_GRAPH_REPLACE__VIEW, oldView, newView);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setView(Pattern newView)
  {
    if (newView != view)
    {
      NotificationChain msgs = null;
      if (view != null)
        msgs = ((InternalEObject)view).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BXCorePackage.XMU_CORE_GRAPH_REPLACE__VIEW, null, msgs);
      if (newView != null)
        msgs = ((InternalEObject)newView).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BXCorePackage.XMU_CORE_GRAPH_REPLACE__VIEW, null, msgs);
      msgs = basicSetView(newView, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, BXCorePackage.XMU_CORE_GRAPH_REPLACE__VIEW, newView, newView));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<Conversion> getConversions()
  {
    if (conversions == null)
    {
      conversions = new EObjectContainmentEList<Conversion>(Conversion.class, this, BXCorePackage.XMU_CORE_GRAPH_REPLACE__CONVERSIONS);
    }
    return conversions;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case BXCorePackage.XMU_CORE_GRAPH_REPLACE__SOURCE:
        return basicSetSource(null, msgs);
      case BXCorePackage.XMU_CORE_GRAPH_REPLACE__VIEW:
        return basicSetView(null, msgs);
      case BXCorePackage.XMU_CORE_GRAPH_REPLACE__CONVERSIONS:
        return ((InternalEList<?>)getConversions()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case BXCorePackage.XMU_CORE_GRAPH_REPLACE__SOURCE:
        return getSource();
      case BXCorePackage.XMU_CORE_GRAPH_REPLACE__VIEW:
        return getView();
      case BXCorePackage.XMU_CORE_GRAPH_REPLACE__CONVERSIONS:
        return getConversions();
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
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case BXCorePackage.XMU_CORE_GRAPH_REPLACE__SOURCE:
        setSource((Pattern)newValue);
        return;
      case BXCorePackage.XMU_CORE_GRAPH_REPLACE__VIEW:
        setView((Pattern)newValue);
        return;
      case BXCorePackage.XMU_CORE_GRAPH_REPLACE__CONVERSIONS:
        getConversions().clear();
        getConversions().addAll((Collection<? extends Conversion>)newValue);
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
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case BXCorePackage.XMU_CORE_GRAPH_REPLACE__SOURCE:
        setSource((Pattern)null);
        return;
      case BXCorePackage.XMU_CORE_GRAPH_REPLACE__VIEW:
        setView((Pattern)null);
        return;
      case BXCorePackage.XMU_CORE_GRAPH_REPLACE__CONVERSIONS:
        getConversions().clear();
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
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case BXCorePackage.XMU_CORE_GRAPH_REPLACE__SOURCE:
        return source != null;
      case BXCorePackage.XMU_CORE_GRAPH_REPLACE__VIEW:
        return view != null;
      case BXCorePackage.XMU_CORE_GRAPH_REPLACE__CONVERSIONS:
        return conversions != null && !conversions.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //XmuCoreGraphReplaceImpl