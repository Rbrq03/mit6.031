/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Map;

import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph();
    }
    
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
    
    @Test
    public void testToString()
    {
        Graph<String> testGraph = emptyInstance();
        testGraph.add("rbrq");
        testGraph.add("1");
        testGraph.add("2");
        testGraph.set("rbrq", "1", 2);
        testGraph.set("1", "2", 2);
        testGraph.set("rbrq", "2", 2);
        String correctString = new String("Vertex: 1, 2, rbrq, \nEdge:\nrbrq --> 1 weight: 2\n1 --> 2 weight: 2\nrbrq --> 2 weight: 2\n");
        assertEquals(correctString, testGraph.toString());
    }
    
    /*
     * Testing Edge...
     */
    
    @Test
    public void testEdgeToString() {
        Edge edge1 = new Edge("rbrq", "qrbr", 2);
        assertEquals("rbrq --> qrbr weight: 2", edge1.toString());
    }
    
}
