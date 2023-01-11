/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Tests for the static methods of Commands.
 */
public class CommandsTest {

    // Testing strategy
    //   TODO
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    
    @Test
    public void testCommandSimplfy() {
        Map<String, Double>environment = new HashMap<>();
        environment.put("x", 5.0);
        assertEquals("125.0", Commands.simplify("x*x*x", environment));
        assertEquals("(((y*y)*y)+125.0)", Commands.simplify("x*x*x+y*y*y", environment));
        assertEquals("11.0", Commands.simplify("1+2*3+8*0.5", environment));
    }
    
}
