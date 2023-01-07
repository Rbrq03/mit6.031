/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import graph.Graph;

/**
 * A graph-based poetry generator.
 * 
 * <p>GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph.
 * Vertices in the graph are words. Words are defined as non-empty
 * case-insensitive strings of non-space non-newline characters. They are
 * delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>For example, given this corpus:
 * <pre>    Hello, HELLO, hello, goodbye!    </pre>
 * <p>the graph would contain two edges:
 * <ul><li> ("hello,") -> ("hello,")   with weight 2
 *     <li> ("hello,") -> ("goodbye!") with weight 1 </ul>
 * <p>where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>Given an input string, GraphPoet generates a poem by attempting to
 * insert a bridge word between every adjacent pair of words in the input.
 * The bridge word between input words "w1" and "w2" will be some "b" such that
 * w1 -> b -> w2 is a two-edge-long path with maximum-weight weight among all
 * the two-edge-long paths from w1 to w2 in the affinity graph.
 * If there are no such paths, no bridge word is inserted.
 * In the output poem, input words retain their original case, while bridge
 * words are lower case. The whitespace between every word in the poem is a
 * single space.
 * 
 * <p>For example, given this corpus:
 * <pre>    This is a test of the Mugar Omni Theater sound system.    </pre>
 * <p>on this input:
 * <pre>    Test the system.    </pre>
 * <p>the output poem would be:
 * <pre>    Test of the system.    </pre>
 * 
 * <p>PS2 instructions: this is a required ADT class, and you MUST NOT weaken
 * the required specifications. However, you MAY strengthen the specifications
 * and you MAY add additional methods.
 * You MUST use Graph in your rep, but otherwise the implementation of this
 * class is up to you.
 */
public class GraphPoet {
    
    private final Graph<String> graph = Graph.empty();
    
    // Abstraction function:
    //   AF(graph) = a poem 
    // Representation invariant:
    //   no word in can be "invalid"
    // Safety from rep exposure:
    //   all argument are private & no obersever return a new object
    
    /**
     * Create a new poet with the graph from corpus (as described above).
     * 
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
        try{
            BufferedReader corpusIn = new BufferedReader(new FileReader(corpus));
            String corpusLine;

            while((corpusLine = corpusIn.readLine()) != null) {
                String[] corpusWords = corpusLine.split(" ");
                int numWords = corpusLine.split(" ").length;

                graph.add(corpusWords[0]);
                int originalWeight;
                
                for(int i = 1; i < numWords; i++)
                {
                    graph.add(corpusWords[i]);
                    originalWeight = graph.set(corpusWords[i-1], corpusWords[i], 1);
                    graph.set(corpusWords[i-1], corpusWords[i], originalWeight + 1);
                }
            }
            corpusIn.close();
        }
        finally{
            checkrep();
        }
    }
    
    public void checkrep() {
        for(String word:graph.vertices()) {
            assert(word.equals("invalid"));
        }
    }
    
    /**
     * Generate a poem.
     * 
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
        String[] poemWords = input.split(" ");
        String resultPoem = poemWords[0];

        for(int i = 1; i < input.split(" ").length ;i++) {
            String possibleBridge = bridge(poemWords[i-1], poemWords[i]);
            if(!possibleBridge.equals("invalid")) {
                resultPoem = resultPoem.concat(" " + possibleBridge + " "+ poemWords[i]);
            }
            else{
                resultPoem = resultPoem.concat(" " + poemWords[i]);
            }
        }

        return resultPoem;
    }

    /**
     * return max-weight bridge
     * 
     * @param source the source of edge
     * @param target the target of edged
     * @return return "invalid" if there is no two-edge-bridge from
     * source to target else return the max-weight bridge word from source
     * to target 
     */
    private String bridge(String source, String target) {
        String finalBridge = new String("invalid");
        int firstEdgeWeight = 0, secondEdgeWeight = 0, maxWeight = 0;
        Map<String, Integer> firstEdge = new HashMap<>();
        Map<String, Integer> secondEdge = new HashMap<>();

        firstEdge = graph.targets(source);
        for(String bridge:firstEdge.keySet()) {
            firstEdgeWeight = firstEdge.get(bridge);
            secondEdge = graph.targets(bridge);
            for(String probe:secondEdge.keySet()) {
                secondEdgeWeight = secondEdge.get(probe);
                if(probe.equals(target) && (firstEdgeWeight + secondEdgeWeight >= maxWeight)) {
                    finalBridge = bridge;
                    maxWeight = firstEdgeWeight + secondEdgeWeight;
                }
            }
        }
        return finalBridge;
    }
    
    @Override
    public String toString() {
        return graph.toString();
    }
    
}
