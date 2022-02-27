package prereqchecker;
import java.util.HashMap;
import java.util.HashSet;

public class MakeAdjList {
    public HashMap<String, HashSet<String>> makeList(String inputFile) {
        HashMap<String, HashSet<String>> adjList = new HashMap<String, HashSet<String>>();
        StdIn.setFile(inputFile);
        int lineCount = 1;
        int numOfCourses = 0;
        while(!StdIn.isEmpty()) {
            if(lineCount == 1) {
                numOfCourses = Integer.parseInt(StdIn.readLine());
            }

            else if(lineCount < numOfCourses + 2) {
                String cor = StdIn.readLine();
                adjList.put(cor, null);
            }
            else if(lineCount > numOfCourses + 2) {
                String line = StdIn.readLine();
                String course = "";
                HashSet<String> prerequisites = new HashSet<String>();
                boolean added = false;
                for(char c : line.toCharArray()) {
                    if(c != ' ') {
                        course += c;
                    }
                    else{
                        if(line.substring(line.indexOf(" ")).length() > 0) {
                            String str = line.substring(line.indexOf(" ") + 1);
                            while(str.indexOf(" ") != -1) {
                                String prerequisite = str.substring(0, str.indexOf(" "));
                                if(adjList.get(course) == null) {
                                    prerequisites.add(prerequisite);
                                }
                                else {
                                    adjList.get(course).add(prerequisite);
                                }
                                str = str.substring(str.indexOf(" ") + 1);
                                if(str.indexOf(" ") == -1) {
                                    prerequisites.add(prerequisite);
                                }
                            }
                            if(!str.equals("")) {
                                if(adjList.get(course) == null) {
                                    prerequisites.add(str);
                                }
                                else {
                                    adjList.get(course).add(str);
                                    added = true;
                                }
                            }
                            break;
                        }
                    }
                }
                if(added == false) {
                    adjList.put(course, prerequisites);
                }
            }
            else {
                StdIn.readLine();
            }
            lineCount++;
        }
        return adjList;
    }
    
}