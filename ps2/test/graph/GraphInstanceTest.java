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
        Graph<String> testGraph = emptyInstance();
        assertEquals("nonexistence single vertex add1", true, testGraph.add("rbrq"));
        assertEquals("nonexistence single vertex add2", true, testGraph.add("1"));
        assertEquals("nonexistence single vertex add3", true, testGraph.add("2"));
        assertEquals("successful add", true, testGraph.vertices().contains("1"));
        assertEquals("successful add", 3, testGraph.vertices().size());
    }

    @Test
    public void testExistenceAdd() {
        Graph<String> testGraph = emptyInstance();
        testGraph.add("rbrq");
        assertEquals("return false while add exist vertex", false, testGraph.add("rbrq"));
    }

    @Test
    public void testAddEdge() {
        Graph<String> testGraph = emptyInstance();
        testGraph.add("rbrq");
        testGraph.add("1");
        assertEquals("nonexisetence edge", 0, testGraph.set("rbrq", "1", 1));
    }
    
    @Test
    public void testAddEdge2() {
        Graph<String> testGraph = emptyInstance();
        testGraph.add("rbrq");
        testGraph.add("1");
        testGraph.set("rbrq", "1", 1);
        assertEquals("nonexisetence edge", 1, testGraph.set("rbrq", "1", 1));
    }
    
    @Test
    public void testRemove() {
        Graph<String> testGraph = emptyInstance();
        testGraph.add("rbrq");
        testGraph.add("1");
        testGraph.add("2");
        testGraph.set("rbrq", "1", 2);
        testGraph.set("1", "2", 2);
        testGraph.set("rbrq", "2", 2);
        assertEquals("remove edge", true, testGraph.remove("rbrq"));
        assertEquals("remove vertex", false, testGraph.vertices().contains("rbrq"));
        assertEquals("remove edge", 0, testGraph.set("rbrq", "1", 1));
        assertEquals("remove edge", 0, testGraph.set("rbrq", "2", 1));
        assertEquals("remove nonexistence vertex", false, testGraph.remove("rbrq"));
    }

    @Test
    public void testSources() {
        Graph<String> testGraph = emptyInstance();
        testGraph.add("rbrq");
        testGraph.add("1");
        testGraph.add("2");
        testGraph.set("rbrq", "1", 2);
        testGraph.set("1", "2", 2);
        testGraph.set("rbrq", "2", 3);
        Map<String, Integer> resultMap = testGraph.sources("2");
        assertEquals("contianer", 2, resultMap.keySet().size());
        assertEquals("check rbrq --> 2", Integer.valueOf("3"), resultMap.get("rbrq"));
        assertEquals("check 1 --> 2", Integer.valueOf("2"), resultMap.get("1"));
    }

    @Test
    public void testTarget() {
        Graph<String> testGraph = emptyInstance();
        testGraph.add("rbrq");
        testGraph.add("1");
        testGraph.add("2");
        testGraph.set("rbrq", "1", 2);
        testGraph.set("1", "2", 2);
        testGraph.set("rbrq", "2", 3);
        Map<String, Integer> targetMap = testGraph.targets("rbrq");
        assertEquals("contain number", 2, targetMap.keySet().size());
        assertEquals("check rbrq --> 1", Integer.valueOf("2"), targetMap.get("1"));
        assertEquals("check rbrq --> 2", Integer.valueOf("3"), targetMap.get("2"));
    }
    
}
