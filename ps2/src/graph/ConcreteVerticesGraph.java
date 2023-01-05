/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {
    
    private final List<Vertex<L>> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   AF(vertices) = graph , for each Vertex contain a label & edge 
    // Representation invariant:
    //   each Vertex o.label is not equal
    //   each Vertex o.taregtMap.keyset is subsets of vertices
    // Safety from rep exposure:
    //   vertices is final & private
    
    public ConcreteVerticesGraph() {
        
    }
    
    public void checkrep() {
        Set<L> labelSet = new HashSet<>();
        Set<L> targetSet = new HashSet<>();

        for(Vertex<L> vertex:vertices) {
            assert(!labelSet.contains(vertex.getLabel()));
            labelSet.add(vertex.getLabel());
            for(L target:vertex.getEdgeMap().keySet())
                targetSet.add(target);
        }

        for(L target:targetSet) {
            assert(labelSet.contains(target));
        }
    }
    
    @Override public boolean add(L vertex) {
        for(Vertex<L> vertexNode:vertices) {
            if(vertexNode.getLabel().equals(vertex))
                return false;
        }

        Vertex<L> newVertex = new Vertex<L>(vertex);
        vertices.add(newVertex);
        checkrep();
        return true;

    }
    
    @Override public int set(L source, L target, int weight) {
        int originalWeght = 0;

        for(Vertex<L> vertex:vertices) {
            if(vertex.getLabel().equals(source)) {
                for(L targetLabel:vertex.getEdgeMap().keySet()) {
                    if(targetLabel.equals(target))
                    {
                        originalWeght = vertex.getWeight(targetLabel);
                        vertex.set(targetLabel, weight);
                    }
                }
                if(originalWeght == 0) {
                    vertex.set(target, weight);
                }
            }
        }

        checkrep();
        return originalWeght;
    }
    
    @Override public boolean remove(L vertex) {
        boolean exitKey = false;
        Iterator<Vertex<L>> vertexIter = vertices.iterator();

        while(vertexIter.hasNext()) {
            Vertex<L> currentVertex = vertexIter.next();
            if(currentVertex.getLabel().equals(vertex))
            {
                exitKey = true;
                vertexIter.remove();
            }
            else if(currentVertex.getEdgeMap().containsKey(vertex))
                currentVertex.remove(vertex);
        }

        checkrep();
        return exitKey;
    }
    
    @Override public Set<L> vertices() {
        Set<L> result = new HashSet<>();
        for(Vertex<L> vertex:vertices) {
            result.add(vertex.getLabel());
        }

        return result;
    }
    
    @Override public Map<L, Integer> sources(L target) {
        Map<L, Integer> targetMap = new HashMap<>();
        for(Vertex<L> vertex:vertices) {
            if(vertex.getWeight(target) != 0) {
                targetMap.put(vertex.getLabel(), vertex.getWeight(target));
            }
        }

        return targetMap;
    }
    
    @Override public Map<L, Integer> targets(L source) {
        for(Vertex<L> vertex:vertices) {
            if(vertex.getLabel().equals(source))
                return vertex.getEdgeMap();
        }

        return null;
    }
    
    @Override
    public String toString() {
        String result = new String("Vertex: ");
        for(Vertex<L> vertex:vertices) {
            result = result.concat(vertex.getLabel() + ", ");
        }

        result = result.concat("\nEdge:\n");
        for(Vertex<L> vertex:vertices) {
            result = result.concat(vertex.toString());
        }

        return result;
    }
    
}

/**
 * Vertex & its edge follow by it;
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex<L> {

    private final Map<L, Integer> targetMap = new HashMap<>();
    private L label = null;
    
    // Abstraction function:
    //   AF(label, targetMap) = label rep the vertex, targetMap mapping edge : label --> key
    //   weight: weight : value 
    // Representation invariant:
    //   value in valueSet cannot be zero
    //   label in keySet cannot be equal to label;
    // Safety from rep exposure:
    //   label is immutable value, though Map is mutable but it's private
    
    public Vertex(L label) {
        this.label = label;
    }
    
    public void checkrep() {
        for(L label:targetMap.keySet()) {
            assert(!label.equals(this.label) && targetMap.get(label) != 0);
        }
    }
    
    /**
     * add a edge from this to another
     * 
     * @param targetLabel target of the edge
     * @param weight weight of the edge
     * @return return origin weight if vertex have include targetLabel
     *          else return zero
     */
    public int set(L targetLabel, int weight) {
        int originalWeight = 0;

        if(targetMap.keySet().contains(targetLabel))
            originalWeight = targetMap.get(targetLabel);
        
        targetMap.put(targetLabel, weight);
        checkrep();
        return originalWeight;
    }

    /**
     * get weight from this to targetLabel
     * 
     * @param targetLabel targetLabel of the edge
     * @return  weight of the edge from label to target if the edge exist
     *          else zero
     */
    public int getWeight(L targetLabel) {
        if(targetMap.containsKey(targetLabel))
            return targetMap.get(targetLabel);
        else
            return 0;
    }

    /**
     * remove a edge 
     * 
     * @param targetLabel targetLabel of edge
     * @return true if targetLabel exist in Map else false
     */
    public boolean remove(L targetLabel) {
        if(!targetMap.containsKey(targetLabel))
            return false;
        
        targetMap.remove(targetLabel);
        checkrep();
        return true;
    } 

    /** 
     * public label extraction function
     * 
     * @return return label of the vertex
     */
    public L getLabel() {
        return this.label;
    }

    /**
     * public edge extraction function
     * 
     * @return return edge map of the vertex
     */
    public Map<L, Integer> getEdgeMap() {
        return new HashMap<>(targetMap);
    }
    
    @Override
    public String toString() {
        String edgeString = new String("");

        for(L targetLabel:targetMap.keySet()) {
            edgeString = edgeString.concat(label + " --> " + targetLabel + " weight: " + targetMap.get(targetLabel)
                                            + "\n");
        }

        return edgeString;
    }
}
