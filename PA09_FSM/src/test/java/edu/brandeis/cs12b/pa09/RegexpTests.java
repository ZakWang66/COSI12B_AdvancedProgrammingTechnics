package edu.brandeis.cs12b.pa09;

import static org.junit.Assert.*;

import java.util.regex.Pattern;

import org.junit.Test;

import edu.brandeis.cs12b.pa09.Regexps;

public class RegexpTests {

	@Test
	public void testRegexp() {
		assertTrue(Pattern.matches(Regexps.emailAddressRegexp, "cbraunstein@brandeis.edu"));
		assertTrue(Pattern.matches(Regexps.emailAddressRegexp, "johndoe@gmail.com"));
		assertTrue(Pattern.matches(Regexps.emailAddressRegexp, "FalseAdvetising69@LIVESPOOKYDIESPOOKY.com"));
		assertFalse(Pattern.matches(Regexps.emailAddressRegexp, "@brandeis.edu"));
		assertTrue(Pattern.matches(Regexps.emailAddressRegexp, "FalseAdvetising69@LIVESPOOKYDIESPOOKY.com.SPOOKY"));
		
	}	

}
