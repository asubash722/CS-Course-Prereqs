package prereqchecker;

import java.util.*;

/**
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * AdjListInputFile name is passed through the command line as args[0]
 * Read from AdjListInputFile with the format:
 * 1. a (int): number of courses in the graph
 * 2. a lines, each with 1 course ID
 * 3. b (int): number of edges in the graph
 * 4. b lines, each with a source ID
 * 
 * Step 2:
 * EligibleInputFile name is passed through the command line as args[1]
 * Read from EligibleInputFile with the format:
 * 1. c (int): Number of courses
 * 2. c lines, each with 1 course ID
 * 
 * Step 3:
 * EligibleOutputFile name is passed through the command line as args[2]
 * Output to EligibleOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class Eligible {
    // Hashset for courses from input file to go into which will store all courses taken
    public HashSet<String> getAllCourses(HashMap<String, HashSet<String>> adjList, HashSet<String> inputCourses) {
        HashSet<String> prereqs = new HashSet<String>();
        Iterator<String> c = inputCourses.iterator();
        while(c.hasNext()) {
            prereqs.add(c.next());
        }
        // Retreiving all prereqs and adding them to the hashset of courses taken
        Iterator<String> vals = inputCourses.iterator();
        DepthFirstSearch dfs = new DepthFirstSearch();
        while(vals.hasNext()) {
            dfs.dfs(adjList, vals.next());
            for(String prereq : dfs.getMarked()) {
                if(!prereqs.contains(prereq)) {
                    prereqs.add(prereq);
                }
            }
        }
        return prereqs;
    }   
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.Eligible <adjacency list INput file> <eligible INput file> <eligible OUTput file>");
            return;
        }

        // PLAN: Get all the courses taken (the ones given in the input file + all the prereqs of the courses taken)
        // Check all courses that are not in that list to see if all their respective prereqs are in there
        // If a course has all of its prereqs in the list of all classes, it can be taken
        // Finally, add them to a separate array with all of the other classes they are eligible to take
        MakeAdjList list = new MakeAdjList();
        HashMap<String, HashSet<String>> adjList = list.makeList(args[0]);
        StdIn.setFile(args[1]);
        StdOut.setFile(args[2]);
        HashSet<String> inputCourses = new HashSet<String>();
        while(!StdIn.isEmpty()) {
            inputCourses.add(StdIn.readLine());
        }
        // Checking what courses have all of their prereqs satisfied
        Eligible eligible = new Eligible();
        HashSet<String> prereqs = eligible.getAllCourses(adjList, inputCourses);
        int count = 0;
        HashSet<String> courses = new HashSet<String>();
        for(String key : adjList.keySet()) {
            if(!prereqs.contains(key)) {
                count = 0;
                if(adjList.get(key) != null) {
                    for(String course : adjList.get(key)) {
                        if(!prereqs.contains(course)) {
                            break;
                        }
                        count++;
                    }
                    if(count == adjList.get(key).size()) {
                        courses.add(key);
                    }
                }
                else {
                    if(!courses.contains(key)) {
                        courses.add(key);
                    }
                }
            }
        }

        // Writing the courses into the output file
        Iterator<String> traverse = courses.iterator();
        while(traverse.hasNext()) {
            StdOut.println(traverse.next());
        } 
    }
}
