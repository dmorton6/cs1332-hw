
import java.util.*;

public class GraphSearch {

    /**
     * Searches the Graph passed in as an AdjcencyList(adjList) to find if a
     * path exists from the start node to the goal node using General Graph
     * Search.
     *
     * Assume the AdjacencyList contains adjacent nodes of each node in the
     * order they should be visited.
     *
     * The structure(struct) passed in is an empty structure may behave as a
     * Stack or Queue and the corresponding search function should execute
     * DFS(Stack) or BFS(Queue) on the graph.
     *
     * @param start
     * @param struct
     * @param adjList
     * @param goal
     * @return true if path exists false otherwise
     */
    public static <T> boolean search(T start, Structure<T> struct,
            Map<T, List<T>> adjList, T goal) {

        ArrayList<T> visitedNodes = new ArrayList<>(adjList.size());
        T curNode;

        // add all keys to the set
        for (int i = 0; i < adjList.size(); i++) {
            T[] keys = (T[]) adjList.keySet().toArray();
            for (T key : keys) {
                struct.add(key);
            }
        }

        return searchHelper(start, adjList, goal, visitedNodes);
    }

    private static <T> boolean searchHelper(T node, Map<T, List<T>> adjList,
            T goal, ArrayList<T> visitedNodes) {

        List<T> neighbors = adjList.get(node);
        List<T> toRemove = (List<T>) new ArrayList<>();

        for (T neighbor : neighbors) {
            if (visitedNodes.contains(neighbor)) {
                toRemove.add(neighbor);
            }
        }

        for (T alreadyVisited : toRemove) {
            neighbors.remove(alreadyVisited);
        }

        if (neighbors == null) {
            // All neighbors have been already visited
            return false;
        }

        for (T neighbor : neighbors) {
            visitedNodes.add(neighbor);
            if (neighbor == goal) {
                return true;
            }
            return searchHelper(neighbor, adjList, goal, visitedNodes);
        }

        return false;
    }

    /**
     * Find the shortest distance between the start node and the goal node in
     * the given a weighted graph in the form of an adjacency list where the
     * edges only have positive weights Return the aforementioned shortest
     * distance if there exists a path between the start and goal,-1* otherwise.
     *
     * Assume the AdjacencyList contains adjacent nodes of each node in the
     * order they should be visited. There are no negative edge weights in the
     * graph.
     *
     * @param start
     * @param adjList
     * @param goal
     * @return the shortest distance between the start and the goal node
     */
    public static <T> int dsp(T start, Map<T, List<Pair<T, Integer>>> adjList,
            T goal) {

        Structure<T> sq = (Structure<T>) new StructureQueue<>();
        Map<T, List<T>> mapForQueue = (Map<T, List<T>>) new HashMap<T, List<T>>();
        List<T> listForQueue = (List<T>) new ArrayList<>();
        T[] keys = (T[]) adjList.keySet().toArray();

        for (int i = 0; i < adjList.size(); i++) {
            List<Pair<T, Integer>> values = adjList.get(keys[i]);
            T value = values.get(i).a;
            listForQueue.add(value);
            mapForQueue.put(keys[i], listForQueue);
        }

        if (search(start, sq, mapForQueue, goal) == false) {
            // the start and goal items are not connected
            return -1;
        }


        HashMap<T, Integer>[] distances;
        distances = (HashMap<T, Integer>[]) new Object[adjList.size()];
        
        // set all values to infinity initially
        for (int i = 0; i < distances.length; i++) {
            distances[i].put(keys[i], Integer.MAX_VALUE);
        }
        
        // find start node and set distance to 0
        for (HashMap<T, Integer> hashMap : distances) {
            if (hashMap.containsKey(start)) {
                hashMap.put(start, 0);
            }
        }
        
        // goes through each node 
        Collection<T> unvisitedNodes = (Collection<T>) adjList.keySet();
        Map[] maps = (Map[]) adjList.values().toArray();
        int i = 0;
        
        /*
        while (!unvisitedNodes.isEmpty()) {
            if (i > maps.length - 1) {
                // reset i
                i = 0;
            }
            
            Collection<Pair<T, Integer>> neighbors = maps[i].values();
            for (Pair<T, Integer> neighbor : neighbors) {
                if (distances[i].)
            }
        }
        */


        return 2;
        //return dspHelper(start, adjList, goal);
    }

}
