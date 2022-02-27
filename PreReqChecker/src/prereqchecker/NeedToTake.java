package prereqchecker;

import java.nio.file.attribute.DosFileAttributeView;
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
 * NeedToTakeInputFile name is passed through the command line as args[1]
 * Read from NeedToTakeInputFile with the format:
 * 1. One line, containing a course ID
 * 2. c (int): Number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * NeedToTakeOutputFile name is passed through the command line as args[2]
 * Output to NeedToTakeOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class NeedToTake {
    public HashSet<String> toTake(HashMap<String, HashSet<String>> adjList, String target, HashSet<String> coursesTaken) {
        // Getting the required prereqs for the target course
        DepthFirstSearch dfs = new DepthFirstSearch();
        dfs.dfs(adjList, target);
        HashSet<String> prereqsOfTarget = dfs.getMarked();

        HashSet<String> coursesToTake = new HashSet<String>();
        for(String prereq : prereqsOfTarget) {
            if(!coursesTaken.contains(prereq) && !prereq.equals(target)) {
                coursesToTake.add(prereq);
            }
        }
        return coursesToTake;
    }
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java NeedToTake <adjacency list INput file> <need to take INput file> <need to take OUTput file>");
            return;
        }

        // PLAN: Get all the prereqs of the required course
        // Get all the courses taken so far
        // Find which courses are in the required prereqs but not in the courses taken so far

        MakeAdjList list = new MakeAdjList();
        HashMap<String, HashSet<String>> adjList = list.makeList(args[0]);
        StdIn.setFile(args[1]);
        StdOut.setFile(args[2]);

        String target = StdIn.readLine();

        // Getting all the courses taken so far
        HashSet<String> inputCourses = new HashSet<String>();
        while(!StdIn.isEmpty()) {
            inputCourses.add(StdIn.readLine());
        }
        Eligible eligible = new Eligible();
        HashSet<String> outputCourses = eligible.getAllCourses(adjList, inputCourses);

        // Checking which courses are missing
        NeedToTake needToTake = new NeedToTake();
        HashSet<String> coursesToTake = needToTake.toTake(adjList, target, outputCourses);

        // Writing courses to take to output file
        Iterator<String> courses = coursesToTake.iterator();
        while(courses.hasNext()) {
            StdOut.println(courses.next());
        }
    }
}
