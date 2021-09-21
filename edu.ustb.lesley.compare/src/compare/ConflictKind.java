/**
 */
package compare;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Conflict Kind</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * Conflicts detected through EMF Compare can be of a number of different types.
 * <ul>
 * 	<li>REAL are the conflicts that cannot be merged automatically,</li>
 * 	<li>PSEUDO are the "conflicts" that represent the same change made on the two sides, which can thus be automatically merged.</li>
 * </ul>
 * <!-- end-model-doc -->
 * @see compare.ComparePackage#getConflictKind()
 * @model
 * @generated
 */
public enum ConflictKind implements Enumerator {
	/**
	 * The '<em><b>REAL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * conflicts that cannot be merged automatically as they represent incompatible differences on the two sides.
	 * <!-- end-model-doc -->
	 * @see #REAL_VALUE
	 * @generated
	 * @ordered
	 */
	REAL(0, "REAL", "REAL"),

	/**
	 * The '<em><b>PSEUDO</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If the same change has been made on the two sides since the common ancestor, these changes will be marked as "pseudo-conflicting" changes : there are changes, but they can be merged automatically.
	 * <!-- end-model-doc -->
	 * @see #PSEUDO_VALUE
	 * @generated
	 * @ordered
	 */
	PSEUDO(1, "PSEUDO", "PSEUDO");

	/**
	 * The '<em><b>REAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * conflicts that cannot be merged automatically as they represent incompatible differences on the two sides.
	 * <!-- end-model-doc -->
	 * @see #REAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int REAL_VALUE = 0;

	/**
	 * The '<em><b>PSEUDO</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If the same change has been made on the two sides since the common ancestor, these changes will be marked as "pseudo-conflicting" changes : there are changes, but they can be merged automatically.
	 * <!-- end-model-doc -->
	 * @see #PSEUDO
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PSEUDO_VALUE = 1;

	/**
	 * An array of all the '<em><b>Conflict Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ConflictKind[] VALUES_ARRAY =
		new ConflictKind[] {
			REAL,
			PSEUDO,
		};

	/**
	 * A public read-only list of all the '<em><b>Conflict Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<ConflictKind> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Conflict Kind</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ConflictKind get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ConflictKind result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Conflict Kind</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ConflictKind getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ConflictKind result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Conflict Kind</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ConflictKind get(int value) {
		switch (value) {
			case REAL_VALUE: return REAL;
			case PSEUDO_VALUE: return PSEUDO;
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
	private ConflictKind(int value, String name, String literal) {
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
	
} //ConflictKind
