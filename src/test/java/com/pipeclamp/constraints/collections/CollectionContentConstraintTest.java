package com.pipeclamp.constraints.collections;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.apache.avro.Schema;
import org.junit.Assert;
import org.testng.annotations.Test;

import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.api.Violation;
import com.pipeclamp.constraints.AbstractConstraintTest;
import com.pipeclamp.constraints.collections.CollectionContentConstraint;

public class CollectionContentConstraintTest extends AbstractConstraintTest {

	@Test
	public void testBuilder() {

		Map<String,String> paramsByKey = asParams(
				  CollectionContentConstraint.Function, CollectionRestriction.Required, 
				  CollectionContentConstraint.Options, "frank bob");

		Collection<ValueConstraint<?>> vc = CollectionContentConstraint.Builder.constraintsFrom(Schema.Type.ARRAY, false, paramsByKey);

		assertNotNull(vc);
		assertTrue(paramsByKey.isEmpty());
	}

  @Test
  public void errorForRequired() {

	  Map<String,String> paramsByKey = asParams(
			  CollectionContentConstraint.Function, CollectionRestriction.Required, 
			  CollectionContentConstraint.Options, "frank bob");

	  Collection<ValueConstraint<?>> vcs = CollectionContentConstraint.Builder.constraintsFrom(Schema.Type.ARRAY, false, paramsByKey);

	  ValueConstraint<?> vc = vcs.iterator().next();

	  Violation v = vc.errorFor( Arrays.asList("frank", "bob", "brian", "eddie") );
	  assertNull(v);

	  v = vc.errorFor( Arrays.asList("frank", "sue") );
	  assertNotNull(v);

  }

  @Test
  public void errorForOneOf() {

	  Map<String,String> paramsByKey = asParams(
			  CollectionContentConstraint.Function, CollectionRestriction.OneOf, 
			  CollectionContentConstraint.Options, "frank bob");

	  Collection<ValueConstraint<?>> vcs = CollectionContentConstraint.Builder.constraintsFrom(Schema.Type.ARRAY, false, paramsByKey);

	  ValueConstraint<?> vc = vcs.iterator().next();

	  Violation v = vc.errorFor( Arrays.asList("brian", "eddie") );
	  assertNotNull(v);

	  v = vc.errorFor( Arrays.asList("frank", "sue") );
	  assertNull(v);
	  
	  v = vc.errorFor( Arrays.asList("frank", "bob", "sue", "bob") );
	  Assert.assertNotNull(v);
  }
 
  @Test
  public void errorForNoneOf() {

	  Map<String,String> paramsByKey = asParams(
			  CollectionContentConstraint.Function, CollectionRestriction.NoneOf, 
			  CollectionContentConstraint.Options, "frank bob");

	  Collection<ValueConstraint<?>> vcs = CollectionContentConstraint.Builder.constraintsFrom(Schema.Type.ARRAY, false, paramsByKey);

	  ValueConstraint<?> vc = vcs.iterator().next();

	  Violation v = vc.errorFor( Arrays.asList("brian", "eddie") );
	  assertNull(v);

	  v = vc.errorFor( Arrays.asList("frank", "sue") );
	  assertNotNull(v);
  }

  @Test
  public void errorForAllUnique() {

	  Map<String,String> paramsByKey = asParams(CollectionContentConstraint.Function, CollectionRestriction.AllUnique);

	  Collection<ValueConstraint<?>> vcs = CollectionContentConstraint.Builder.constraintsFrom(Schema.Type.ARRAY, false, paramsByKey);

	  ValueConstraint<?> vc = vcs.iterator().next();

	  Violation v = vc.errorFor( Arrays.asList("brian", "eddie") );
	  Assert.assertNull(v);

	  v = vc.errorFor( Arrays.asList("frank", "sue", "frank") );
	  Assert.assertNotNull(v);
  }

  @Test
  public void errorForAnyOf() {

	  Map<String,String> paramsByKey = asParams(
			  CollectionContentConstraint.Function, CollectionRestriction.AnyOf, 
			  CollectionContentConstraint.Options, "frank bob");

	  Collection<ValueConstraint<?>> vcs = CollectionContentConstraint.Builder.constraintsFrom(Schema.Type.ARRAY, false, paramsByKey);

	  ValueConstraint<?> vc = vcs.iterator().next();

	  Violation v = vc.errorFor( Arrays.asList("brian", "eddie", "bob") );
	  Assert.assertNull(v);

	  v = vc.errorFor( Arrays.asList("bill", "sue", "john") );
	  Assert.assertNotNull(v);
  }
}
