package prereqchecker;
import java.util.*;

// Performs topological sort
public class TopologicalSort {
    private HashSet<String> marked = new HashSet<String>();
    private Stack<String> reversePostOrder = new Stack<String>();

    public void topologicalSort(HashMap<String, HashSet<String>> adjList, String course) {
        marked.add(course);
            if(adjList.get(course) != null) {
                for(String p : adjList.get(course)) {
                    if(!marked.contains(p)) {
                        topologicalSort(adjList, p);
                    }
                }
            }
            reversePostOrder.push(course);
    }

    public Stack<String> getReversePostOrder() {
        return reversePostOrder;
    }
}
