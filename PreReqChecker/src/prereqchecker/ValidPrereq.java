package prereqchecker;
import java.util.*;
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
 * ValidPreReqInputFile name is passed through the command line as args[1]
 * Read from ValidPreReqInputFile with the format:
 * 1. 1 line containing the proposed advanced course
 * 2. 1 line containing the proposed prereq to the advanced course
 * 
 * Step 3:
 * ValidPreReqOutputFile name is passed through the command line as args[2]
 * Output to ValidPreReqOutputFile with the format:
 * 1. 1 line, containing either the word "YES" or "NO"
 */
public class ValidPrereq {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.ValidPrereq <adjacency list INput file> <valid prereq INput file> <valid prereq OUTput file>");
            return;
        }
        // PLAN: The result will be invalid if the given parent is a direct/indirect prerequisite of the child
        // since that would create a cycle
        // So we have to verify if the parent is a prereq of the child
        // To do this, we must first check if the given parent is equal to any of the child's direct prereqs
        // If it is, return NO, else continue
        // Then, we must perform a dfs on each of the direct prereqs to see if the parent is an indirect prereq
        // If at any point along the way, we find that the parent is an indirect prereq, return NO, else return YES 
        MakeAdjList list = new MakeAdjList();
        HashMap<String, HashSet<String>> adjList = list.makeList(args[0]);
        StdIn.setFile(args[1]);
        StdOut.setFile(args[2]);
        String course = StdIn.readLine();
        String prereq = StdIn.readLine();
        if(prereq.equals(course)) {
            StdOut.print("NO");
            return;
        }
        if(adjList.get(prereq) != null) {
            if(adjList.get(prereq).contains(course)) {
                StdOut.print("NO");
                return;
            }
        }
        DepthFirstSearch dfs = new DepthFirstSearch();
        dfs.dfs(adjList, prereq);
        if(dfs.getMarked().contains(course)) {
            StdOut.print("NO");
        }
        else {
            StdOut.print("YES");
        }
    }
}
