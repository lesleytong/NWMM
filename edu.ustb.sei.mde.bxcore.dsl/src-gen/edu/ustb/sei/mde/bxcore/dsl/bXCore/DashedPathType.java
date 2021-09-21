/**
 * generated by Xtext 2.18.0.M3
 */
package edu.ustb.sei.mde.bxcore.dsl.bXCore;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Dashed Path Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.ustb.sei.mde.bxcore.dsl.bXCore.DashedPathType#getSegment <em>Segment</em>}</li>
 *   <li>{@link edu.ustb.sei.mde.bxcore.dsl.bXCore.DashedPathType#getNext <em>Next</em>}</li>
 * </ul>
 *
 * @see edu.ustb.sei.mde.bxcore.dsl.bXCore.BXCorePackage#getDashedPathType()
 * @model
 * @generated
 */
public interface DashedPathType extends EObject
{
  /**
   * Returns the value of the '<em><b>Segment</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Segment</em>' containment reference.
   * @see #setSegment(DashedPathTypeSegment)
   * @see edu.ustb.sei.mde.bxcore.dsl.bXCore.BXCorePackage#getDashedPathType_Segment()
   * @model containment="true"
   * @generated
   */
  DashedPathTypeSegment getSegment();

  /**
   * Sets the value of the '{@link edu.ustb.sei.mde.bxcore.dsl.bXCore.DashedPathType#getSegment <em>Segment</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Segment</em>' containment reference.
   * @see #getSegment()
   * @generated
   */
  void setSegment(DashedPathTypeSegment value);

  /**
   * Returns the value of the '<em><b>Next</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Next</em>' containment reference.
   * @see #setNext(DashedPathType)
   * @see edu.ustb.sei.mde.bxcore.dsl.bXCore.BXCorePackage#getDashedPathType_Next()
   * @model containment="true"
   * @generated
   */
  DashedPathType getNext();

  /**
   * Sets the value of the '{@link edu.ustb.sei.mde.bxcore.dsl.bXCore.DashedPathType#getNext <em>Next</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Next</em>' containment reference.
   * @see #getNext()
   * @generated
   */
  void setNext(DashedPathType value);

} // DashedPathType