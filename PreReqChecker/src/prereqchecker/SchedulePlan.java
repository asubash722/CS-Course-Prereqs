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
 * SchedulePlanInputFile name is passed through the command line as args[1]
 * Read from SchedulePlanInputFile with the format:
 * 1. One line containing a course ID
 * 2. c (int): number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * SchedulePlanOutputFile name is passed through the command line as args[2]
 * Output to SchedulePlanOutputFile with the format:
 * 1. One line containing an int c, the number of semesters required to take the course
 * 2. c lines, each with space separated course ID's
 */
public class SchedulePlan {
    private static boolean eligible(HashMap<String, HashSet<String>> adjList, String course, HashSet<String> coursesTaken) {
        int count = 0;
        for(String prereq : adjList.get(course)) {
            if(!coursesTaken.contains(prereq)) {
                return false;
            }
            count++;
        }
        if(count == adjList.get(course).size()) {
            return true;
        }
        return false;
    }
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.SchedulePlan <adjacency list INput file> <schedule plan INput file> <schedule plan OUTput file>");
            return;
        }
        // PLAN: Get all the courses that we still need to take
        // Loop through and increment # of semesters when we cannot take any more classes for that semester
        MakeAdjList list = new MakeAdjList();
        HashMap<String, HashSet<String>> adjList = list.makeList(args[0]);
        StdIn.setFile(args[1]);
        StdOut.setFile(args[2]);
        String target = StdIn.readLine();
        HashSet<String> inputCourses = new HashSet<String>();
        while(!StdIn.isEmpty()) {
            inputCourses.add(StdIn.readLine());
        }
        HashSet<String> coursesTaken = new HashSet<String>();
        Iterator<String> it = inputCourses.iterator();
        while(it.hasNext()) {
            TopologicalSort tps = new TopologicalSort();
            tps.topologicalSort(adjList, it.next());
            Stack<String> outputCourses = tps.getReversePostOrder();
            for(String course : outputCourses) {
                if(!coursesTaken.contains(course)) {
                    coursesTaken.add(course);
                }
            }
        }

        TopologicalSort ts = new TopologicalSort();
        ts.topologicalSort(adjList, target);
        Stack<String> totake = ts.getReversePostOrder();
        ArrayList<String> needToTake = new ArrayList<String>();
        for(String course : totake) {
            if(!coursesTaken.contains(course)) {
                needToTake.add(course);
            }
        }
        System.out.println(needToTake);

        int semesterCount = 0;
        ArrayList<String> needToTake2 = new ArrayList<String>();
        
        for(String course : needToTake) {
            needToTake2.add(course);
        }
        HashSet<String> coursesTaken2 = new HashSet<String>();
        for(String course : coursesTaken) {
            coursesTaken2.add(course);
        }
        boolean removed = false;
        for(int i = 0; i < needToTake.size() - 1; i++) {
            removed = false;
            HashSet<String> special2 = new HashSet<String>();
            if(adjList.get(needToTake.get(i + 1)) != null) {
                if(adjList.get(needToTake.get(i + 1)).contains(needToTake.get(i))) {
                    for(int j = i + 1; j < needToTake.size(); j++) {
                        if(eligible(adjList, needToTake.get(j), coursesTaken)) {
                            special2.add(needToTake.get(j));
                            System.out.println(needToTake.get(j));
                            needToTake.remove(j);
                        }
                    }
                    if(!removed) {
                        semesterCount++;
                        removed = true;
                    }
                    if(!special2.isEmpty()) {
                        for(String course : special2) {
                            coursesTaken.add(course);
                        }
                    }
                    coursesTaken.add(needToTake.get(i));
                }
                else {
                    coursesTaken.add(needToTake.get(i));
                }
            }
        }
        boolean printedLine = false;
        StdOut.println(semesterCount);
        for(int i = 0; i < needToTake2.size() - 1; i++) {
            HashSet<String> special = new HashSet<String>();
            printedLine = false;
            if(adjList.get(needToTake2.get(i + 1)) != null) {
                if(adjList.get(needToTake2.get(i + 1)).contains(needToTake.get(i))) {
                    StdOut.print(needToTake2.get(i) + " ");
                    for(int j = i + 1; j < needToTake2.size(); j++) {
                        if(eligible(adjList, needToTake2.get(j), coursesTaken2)) {
                            StdOut.print(needToTake2.get(j) + " ");
                            special.add(needToTake2.get(j));
                            System.out.println(needToTake2.get(j));
                            needToTake2.remove(j);
                        }
                        else {
                            if(!printedLine) {
                                StdOut.println();
                                printedLine = true;
                            }
                        }

                        if(!special.isEmpty()) {
                            for(String course : special) {
                                coursesTaken2.add(course);
                            }
                        }
                        coursesTaken2.add(needToTake2.get(i));
                    }
                }
                else {
                    coursesTaken2.add(needToTake2.get(i));
                    StdOut.print(needToTake2.get(i) + " ");
                }
            }

            else {
                StdOut.print(needToTake.get(i) + " ");
            }
        }        
    }
}
