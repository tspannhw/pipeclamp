package com.pipeclamp;

import java.util.Objects;

import com.pipeclamp.api.RegisteredItem;

/**
 * Convenient base class for things we are likely to keep in maps and browsed.
 *
 * @author Brian Remedios
 */
public abstract class AbstractRegisteredItem implements RegisteredItem {

	private final String id;
	private String description;

	protected AbstractRegisteredItem(String theId, String theDescription) {
		super();
		
		id = theId;
		description = theDescription;
	}

	@Override
	public String id() { return id; }

	@Override
	public String description() { return description; }
	
	protected void pDescription(String theDescription) { description = theDescription; }
	
	@Override
	public boolean equals(Object other) {
		
		if (this == other) return true;
		if (other == null) return false;
		
		if (!(other instanceof AbstractRegisteredItem)) return false;
		
		AbstractRegisteredItem ari = AbstractRegisteredItem.class.cast(other);
		
		return Objects.deepEquals(id, ari.id) &&
				Objects.deepEquals(description, ari.description);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, description);
	}
	
	@Override
	public String toString() {
		return id + "\t" + description;
	}
}