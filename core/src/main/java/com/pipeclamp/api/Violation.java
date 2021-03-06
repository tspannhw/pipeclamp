package com.pipeclamp.api;

/**
 * Simple tuple relating a constraint and an error message it generated.
 *
 * @author Brian Remedios
 */
public class Violation {

	public final Constraint<?> constraint;
	public final String message;

	/**
	 *
	 * @param theConstraint
	 * @param theMessage
	 */
	public Violation(Constraint<?> theConstraint, String theMessage) {
		constraint = theConstraint;
		message = theMessage;
	}

	@Override
	public String toString() {
		return message;
	}
}
