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

import javax.sound.midi.SysexMessage;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {
    
    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();
    
    // Abstraction function:
    //   AF(vertices, edges) = a graph with vertices & edges
    // Representation invariant:
    //   source & target of edge should be a member of vertices
    //   there is no repeats in edges
    // Safety from rep exposure:
    //    all argument are final & private 
    //    all class in Set or List all immutable
    
    /**
     * constructor of class ConcreteEdgesGraph
     */
    public ConcreteEdgesGraph() {

    }
    
    /**
     * checkrep function
     */
    public void checkrep() {

        for(Edge<L> edge:edges) {
            assert(vertices.contains(edge.getSource()) && vertices.contains(edge.getTarget()));
        }

        for(int i = 0; i < edges.size() ; i++)
            for(int j = i + 1; j < edges.size(); j++) {
                assert(!edges.get(i).equals(edges.get(j)));
            }

    }
    
    @Override public boolean add(L vertex) {
        if (vertices.contains(vertex))
            return false;
        
        vertices.add(vertex);
        checkrep();
        return true;
    }
    
    @Override public int set(L source, L target, int weight) {
        int originalWeight = 0;
        Edge<L> newEdge = new Edge<L>(source, target, weight);

        for(Edge<L> edge:edges)
        {
            if(edge.equals(newEdge))
            {
                originalWeight = edge.getWeight();
                edges.remove(edge);
                edges.add(newEdge);
            }
        }

        if(originalWeight == 0)
            edges.add(newEdge);
        
        return originalWeight;

    }
    
    @Override public boolean remove(L vertex) {
        if(!vertices.contains(vertex))
            return false;
        
        vertices.remove(vertex);

        Iterator<Edge<L>> i = edges.iterator();

        while(i.hasNext()) {
            Edge<L> ie = i.next();
            if(ie.getSource().equals(vertex) || ie.getTarget().equals(vertex))
                i.remove();
        }

        return true;
    }
    
    @Override public Set<L> vertices() {
        return new HashSet<L>(vertices);
    }
    
    @Override public Map<L, Integer> sources(L target) {
        Map<L, Integer> sourcesMap = new HashMap<>();

        for(Edge<L> edge:edges)
        {
            if(edge.getTarget().equals(target))
                sourcesMap.put(edge.getSource(), edge.getWeight());
        }

        return sourcesMap;
    }
    
    @Override public Map<L, Integer> targets(L source) {
        Map<L, Integer> targetMap = new HashMap<>();

        for(Edge<L> edge:edges)
        {
            if(edge.getSource().equals(source))
                targetMap.put(edge.getTarget(), edge.getWeight());
        }

        return targetMap;
    }
    
    @Override
    public String toString() {
        String graphString = new String("Vertex: ");

        for(L vertex:vertices)
        {
            graphString = graphString.concat(vertex + ", ");
        }

        graphString = graphString.concat("\nEdge:\n");

        for(Edge<L> edge:edges)
        graphString = graphString.concat(edge.toString() + "\n");

        return graphString;
    }
    
}

/**
 * rep a edge from source to target with weight w;
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge<L> {
    
    private L source = null;
    private L target = null;
    private int weight = 0;
    
    // Abstraction function:
    //   AF(source, target, weight) = edge
    // Representation invariant:
    //   source is not equal with target
    //   weight is not equal to zero
    // Safety from rep exposure:
    //   all variable is final & String is inmutable


    /**
     * constructer of class edge
     * 
     * @param source rep the label of source vertex of a edge
     * @param target rep the label of target vertex of a edge
     * @param weight rep the weight of a edge
     */
    public Edge(L source, L target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
        checkrep();
    }
    
    /**
     * check RI of class edge
     * 
     * @parm empty
     * @return whether RI of class edge match
     */
    private void checkrep() {
        assert (!this.source.equals(this.target) && this.weight != 0);
    }
    
    /**
     * check one edge is equal with other edge
     * 
     * @param o should be a class Edge
     * @return this is equal to o or not
     */
    @Override public boolean equals(Object o) {
        assert (o instanceof Edge);

        Edge<L> e = (Edge<L>) o;
        return this.source.equals(e.getSource()) &&
                this.target.equals(e.getTarget()) &&
                (this.weight == e.getWeight());
    }

    /**
     * calc the hashcode of obj this
     * 
     * @param empty
     * @return hashCode of this
     */
    @Override
    public int hashCode() {
        return this.source.hashCode() + this.target.hashCode() + weight;
    }
    
    /**
     * public source extraction interface
     * 
     * @return new obj which equals to this.source
     */
    public L getSource() {
        return this.source;
    }

    /**
     * public target extraction interface
     * 
     * @return new obj which equals to this.target
     */
    public L getTarget() {
        return this.target;
    }

    /**
     * public weight extraction interface
     * 
     * @return this.weight
     */
    public int getWeight() {
        return this.weight;
    }

    /**
     * return String rep class Edge
     * 
     * @return return "his.soure --> this.target weight: this.weight"
     */
    @Override public String toString() {
        String edgeString = new String(this.source + " --> " + this.target + " weight: " + this.weight);
        return edgeString;
    }
    
    
}
