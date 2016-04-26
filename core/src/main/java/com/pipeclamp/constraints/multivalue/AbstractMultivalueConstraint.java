package com.pipeclamp.constraints.multivalue;

import java.util.Map;

import com.pipeclamp.api.PathBuilder;
import com.pipeclamp.constraints.AbstractConstraint;
import com.pipeclamp.params.PathParameter;
import com.pipeclamp.path.Path;

/**
 *
 * @author Brian Remedios
 */
public abstract class AbstractMultivalueConstraint<T extends Object> extends AbstractConstraint implements MultiValueConstraint<T> {


	protected static Path pathIn(Map<String, String> values, PathParameter param, String regexDelimiter, PathBuilder builder) {
		
		String pathStr = values.remove(param.id());
		if (pathStr == null) return null;
		
		String[] parts = pathStr.split(regexDelimiter);
		return builder.pathFor(parts);
	}
	
	protected AbstractMultivalueConstraint(String theId, boolean allowsNulls) {
		super(theId, allowsNulls);
	}
	
	protected static Comparison comparisonIn(Map values, ComparisonParameter compParam) {
		// TODO Auto-generated method stub
		return null;
	}
}