package prereqchecker;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Step 1:
 * AdjListInputFile name is passed through the command line as args[0]
 * Read from AdjListInputFile with the format:
 * 1. a (int): number of courses in the graph
 * 2. a lines, each with 1 course ID
 * 3. b (int): number of edges in the graph
 * 4. b lines, each with a source ID
 * 
 * Step 2:
 * AdjListOutputFile name is passed through the command line as args[1]
 * Output to AdjListOutputFile with the format:
 * 1. c lines, each starting with a different course ID, then 
 *    listing all of that course's prerequisites (space separated)
 */
public class AdjList {
    public static void main(String[] args) {

        if ( args.length < 2 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.AdjList <adjacency list INput file> <adjacency list OUTput file>");
            return;
        }

        // Plan: first put all courses as keys into hashmap with null values
        // then, once we get to the prereq line, we start isolating the prereqs and store them in a hashset
        // then we put that hashset into the hashmap, corresponding to the given course key
        MakeAdjList list = new MakeAdjList();
        HashMap<String, HashSet<String>> adjList = list.makeList(args[0]);
        StdOut.setFile(args[1]);
        for(String key : adjList.keySet()) {
            StdOut.print(key + " ");
            if(adjList.get(key) != null) {
                Iterator<String> courses = adjList.get(key).iterator();
                while(courses.hasNext()) {
                    StdOut.print(courses.next() + " ");
                }
            }
            StdOut.print("\n");
        }
    }
}