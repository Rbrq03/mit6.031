/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Map;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
    // Testing strategy
    //     add:
    //       a nonexistence single vertex add
    //       existence vertex add
    //     set:
    //       a nonexistence edge
    //       a existence edeg
    //     remove:
    //       remove vertex check
    //       remove edge check
    //       remove nonexistence vertex

    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        // TODO you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }

    @Test
    public void testNormalAdd() {
        assertEquals("nonexistence single vertex add", true, emptyInstance().add("rbrq"));
        assertEquals("nonexistence single vertex add", true, emptyInstance().add("1"));
        assertEquals("nonexistence single vertex add", true, emptyInstance().add("2"));
        assertEquals("successful add", true, emptyInstance().vertices().contains("rbrq"));
        assertEquals("successful add", 3, emptyInstance().vertices().size());
    }

    @Test
    public void testExistenceAdd() {
        emptyInstance().add("rbrq");
        assertEquals("return false while add exist vertex", false, emptyInstance().add("rbrq"));
    }

    @Test
    public void testAddEdge() {
        emptyInstance().add("rbrq");
        emptyInstance().add("1");
        assertEquals("nonexisetence edge", 0, emptyInstance().set("rbrq", "1", 1));
    }
    
    @Test
    public void testAddEdge2() {
        emptyInstance().add("rbrq");
        emptyInstance().add("1");
        emptyInstance().set("rbrq", "1", 1);
        assertEquals("nonexisetence edge", 1, emptyInstance().set("rbrq", "1", 1));
    }
    
    @Test
    public void testRemove() {
        emptyInstance().add("rbrq");
        emptyInstance().add("1");
        emptyInstance().add("2");
        emptyInstance().set("rbrq", "1", 2);
        emptyInstance().set("1", "2", 2);
        emptyInstance().set("rbrq", "2", 2);
        assertEquals("remove edge", true, emptyInstance().remove("rbrq"));
        assertEquals("remove vertex", false, emptyInstance().vertices().contains("rbrq"));
        assertEquals("remove edge", 0, emptyInstance().set("rbrq", "1", 1));
        assertEquals("remove edge", 0, emptyInstance().set("rbrq", "2", 1));
        assertEquals("remove nonexistence vertex", false, emptyInstance().remove("rbrq"));
    }

    @Test
    public void testSources() {
        emptyInstance().add("rbrq");
        emptyInstance().add("1");
        emptyInstance().add("2");
        emptyInstance().set("rbrq", "1", 2);
        emptyInstance().set("1", "2", 2);
        emptyInstance().set("rbrq", "2", 3);
        Map<String, Integer> resultMap = emptyInstance().sources("rbrq");
        assertEquals("contianer", 2, resultMap.keySet().size());
        assertEquals("check rbrq --> 1", Integer.valueOf("2"), resultMap.get("1"));
        assertEquals("check rbrq --> 2", Integer.valueOf("3"), resultMap.get("2"));
    }

    @Test
    public void testTarget() {
        emptyInstance().add("rbrq");
        emptyInstance().add("1");
        emptyInstance().add("2");
        emptyInstance().set("rbrq", "1", 2);
        emptyInstance().set("1", "2", 2);
        emptyInstance().set("rbrq", "2", 3);
        Map<String, Integer> targetMap = emptyInstance().targets("2");
        assertEquals("contain number", 2, targetMap.keySet().size());
        assertEquals("check rbrq --> 1", Integer.valueOf("2"), targetMap.get("1"));
        assertEquals("check rbrq --> 2", Integer.valueOf("3"), targetMap.get("rbrq"));
    }

    
}
