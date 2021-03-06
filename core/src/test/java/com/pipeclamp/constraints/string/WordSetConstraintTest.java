package com.pipeclamp.constraints.string;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Map;

import org.apache.avro.Schema;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pipeclamp.api.Constraint;
import com.pipeclamp.api.ConstraintBuilder;
import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.api.Violation;
import com.pipeclamp.constraints.AbstractConstraintTest;
import com.pipeclamp.constraints.collections.CollectionContentConstraint;

public class WordSetConstraintTest extends AbstractConstraintTest {

	private static final String[] BadWords = new String[] { "jerk", "idiot" };

	private static final String[] RequiredWords = new String[] { "red", "white", "blue" };

	@Test
	public void testBuilder() {

		Map<String,String> paramsByKey = asParams(
				WordSetConstraint.Function, WordRestriction.CannotHave, 
				WordSetConstraint.WORDS, "jerk idiot");

		Collection<Constraint<?>> vcs = WordSetConstraint.Builder.constraintsFrom(Schema.Type.ARRAY, false, paramsByKey);

		assertNotNull(vcs);
		assertTrue(paramsByKey.isEmpty());
		
		paramsByKey = asParams(
				  CollectionContentConstraint.CHOICES, "jerk idiot");

		vcs = WordSetConstraint.Builder.constraintsFrom(Schema.Type.ARRAY, false, paramsByKey);

		assertNull(vcs);
		assertFalse(paramsByKey.isEmpty());	// unwanted param not consumed
	}

	@Test
	public void typedErrorForCannotHave() {

		WordSetConstraint wsc = new WordSetConstraint("", true, BadWords, WordRestriction.CannotHave);

		Violation v = wsc.errorFor(null);
		Assert.assertNull(v);

		v = wsc.errorFor("Bob is an idiot");
		Assert.assertNotNull(v);

		v = wsc.errorFor("Bob is an nice guy");
		Assert.assertNull(v);
	}

	@Test
	public void typedErrorForMustHaveAll() {

		WordSetConstraint wsc = new WordSetConstraint("", false, RequiredWords, WordRestriction.MustHaveAll);

		Violation v = wsc.errorFor(null);		// nulls not allowed
		Assert.assertNotNull(v);

		v = wsc.errorFor("The American flag has the colours: red, white, and blue");
		Assert.assertNull(v);

		v = wsc.errorFor("The Canadian flag has red and white");
		Assert.assertNotNull(v);

		v = wsc.errorFor("The Canadian flag is nice");
		Assert.assertNotNull(v);
	}
	
	@Override
	protected ConstraintBuilder<?> sampleBuilder() { return WordSetConstraint.Builder; }

	@Override
	protected ValueConstraint<?> sampleConstraint() { return new WordSetConstraint("", false, new String[] { "a", "b" }, WordRestriction.MustHaveAll); }
}
