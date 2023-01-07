/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {
    
    // Testing strategy
    //   a 
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testPoem() {
        File corpus= new File("mugar-omni-theater.txt");
        try{
            GraphPoet testCorpus = new GraphPoet(corpus);
            String poemString = testCorpus.poem("Test the system.");
            assertEquals("Test of the system.", poemString);
        }
        catch(IOException ie) {System.out.println("read file fail");}
        finally{}
    }
}
