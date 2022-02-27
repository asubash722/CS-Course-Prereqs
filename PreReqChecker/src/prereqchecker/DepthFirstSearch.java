package prereqchecker;
import java.util.*;

// Performs Depth First Search
public class DepthFirstSearch {
    private HashSet<String> marked = new HashSet<String>();
    public void dfs(HashMap<String, HashSet<String>> adjList, String prereq) {
        marked.add(prereq);
            if(adjList.get(prereq) != null) {
                for(String p : adjList.get(prereq)) {
                    if(!marked.contains(p)) {
                        dfs(adjList, p);
                    }
                }
            }
    }

    public HashSet<String> getMarked() {
        return marked;
    }
}
