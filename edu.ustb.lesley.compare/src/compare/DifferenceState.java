/**
 */
package compare;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Difference State</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * This will be used to represent the state of a detected diff.
 * <ul>
 * 	<li>UNRESOLVED if the Diff is still in its initial state and the two sides differ,</li>
 * 	<li>MERGED if the Diff has already been merged by the user,</li>
 * 	<li>DISCARDED if the user chose to ignore this Diff,</li>
 * 	<li>MERGING if the Diff is in the process of being merged.</li>
 * </ul>
 * <!-- end-model-doc -->
 * @see compare.ComparePackage#getDifferenceState()
 * @model
 * @generated
 */
public enum DifferenceState implements Enumerator {
	/**
	 * The '<em><b>UNRESOLVED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indicates that the Diff is still in its initial state.
	 * <!-- end-model-doc -->
	 * @see #UNRESOLVED_VALUE
	 * @generated
	 * @ordered
	 */
	UNRESOLVED(0, "UNRESOLVED", "UNRESOLVED"),

	/**
	 * The '<em><b>MERGED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indicates that the Diff has already been merged by the user.
	 * <!-- end-model-doc -->
	 * @see #MERGED_VALUE
	 * @generated
	 * @ordered
	 */
	MERGED(1, "MERGED", "MERGED"),

	/**
	 * The '<em><b>DISCARDED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indicates that the user chose to ignore this Diff.
	 * <!-- end-model-doc -->
	 * @see #DISCARDED_VALUE
	 * @generated
	 * @ordered
	 */
	DISCARDED(2, "DISCARDED", "DISCARDED"),

	/**
	 * The '<em><b>MERGING</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indicates that the Diff is in the process of being merged.
	 * <!-- end-model-doc -->
	 * @see #MERGING_VALUE
	 * @generated
	 * @ordered
	 */
	MERGING(3, "MERGING", "MERGING");

	/**
	 * The '<em><b>UNRESOLVED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indicates that the Diff is still in its initial state.
	 * <!-- end-model-doc -->
	 * @see #UNRESOLVED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int UNRESOLVED_VALUE = 0;

	/**
	 * The '<em><b>MERGED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indicates that the Diff has already been merged by the user.
	 * <!-- end-model-doc -->
	 * @see #MERGED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int MERGED_VALUE = 1;

	/**
	 * The '<em><b>DISCARDED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indicates that the user chose to ignore this Diff.
	 * <!-- end-model-doc -->
	 * @see #DISCARDED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int DISCARDED_VALUE = 2;

	/**
	 * The '<em><b>MERGING</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indicates that the Diff is in the process of being merged.
	 * <!-- end-model-doc -->
	 * @see #MERGING
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int MERGING_VALUE = 3;

	/**
	 * An array of all the '<em><b>Difference State</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final DifferenceState[] VALUES_ARRAY =
		new DifferenceState[] {
			UNRESOLVED,
			MERGED,
			DISCARDED,
			MERGING,
		};

	/**
	 * A public read-only list of all the '<em><b>Difference State</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<DifferenceState> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Difference State</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static DifferenceState get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DifferenceState result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Difference State</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static DifferenceState getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DifferenceState result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Difference State</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static DifferenceState get(int value) {
		switch (value) {
			case UNRESOLVED_VALUE: return UNRESOLVED;
			case MERGED_VALUE: return MERGED;
			case DISCARDED_VALUE: return DISCARDED;
			case MERGING_VALUE: return MERGING;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private DifferenceState(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //DifferenceState
