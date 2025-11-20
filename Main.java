package test;
import java.util.List;
import java.util.ArrayList;

public class Main {
	
    public static boolean hasCircularDependency(int n, List<List<Integer>> dependencies) {

        // Build adjacency list
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (List<Integer> dep : dependencies) {
            int a = dep.get(0);
            int b = dep.get(1);

            // Self-loop = immediate cycle
            if (a == b) return true;

            graph.get(a).add(b);
        }

        // visiting = on recursion stack
        boolean[] visiting = new boolean[n];
        // visited = fully processed
        boolean[] visited = new boolean[n];

        // Try DFS from every node
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                if (dfs(graph, visiting, visited, i)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean dfs(List<List<Integer>> graph, boolean[] visiting, boolean[] visited, int node) {

        // If already on recursion stack => cycle
        if (visiting[node]) return true;

        // If fully processed earlier => no cycle through this node
        if (visited[node]) return false;

        // Mark as currently exploring
        visiting[node] = true;

        for (int next : graph.get(node)) {
            if (dfs(graph, visiting, visited, next)) {
                return true;
            }
        }

        // Done exploring neighbors: mark safe
        visiting[node] = false;
        visited[node] = true;

        return false;
    }

	public static void main(String[] args) {
	    // ------------------------------------------------------------
	    // TEST 1: No dependencies → no cycle
	    // Graph: (0) (1) (2)
	    // ------------------------------------------------------------
	    System.out.println("Test 1 (no edges) → Expected false: " +
	        hasCircularDependency(3, List.of())
	    );

	    // ------------------------------------------------------------
	    // TEST 2: Simple chain 0 → 1 → 2 → no cycle
	    // ------------------------------------------------------------
	    System.out.println("Test 2 (simple chain) → Expected false: " +
	        hasCircularDependency(
	            3,
	            List.of(
	                List.of(0, 1),
	                List.of(1, 2)
	            )
	        )
	    );

	    // ------------------------------------------------------------
	    // TEST 3: Direct 2-node cycle: 0 ↔ 1
	    // ------------------------------------------------------------
	    System.out.println("Test 3 (0→1→0 cycle) → Expected true: " +
	        hasCircularDependency(
	            2,
	            List.of(
	                List.of(0, 1),
	                List.of(1, 0)
	            )
	        )
	    );

	    // ------------------------------------------------------------
	    // TEST 4: Longer cycle 0 → 2 → 3 → 1 → 0
	    // ------------------------------------------------------------
	    System.out.println("Test 4 (long cycle) → Expected true: " +
	        hasCircularDependency(
	            4,
	            List.of(
	                List.of(0, 2),
	                List.of(2, 3),
	                List.of(3, 1),
	                List.of(1, 0)
	            )
	        )
	    );

	    // ------------------------------------------------------------
	    // TEST 5: Self-loop (0 → 0) → always a cycle
	    // ------------------------------------------------------------
	    System.out.println("Test 5 (self-loop) → Expected true: " +
	        hasCircularDependency(
	            1,
	            List.of(
	                List.of(0, 0)
	            )
	        )
	    );

	    // ------------------------------------------------------------
	    // TEST 6: Multiple components, one contains a cycle
	    //
	    // Component A: 0→1→2→0   (cycle)
	    // Component B: 3→4       (no cycle)
	    // ------------------------------------------------------------
	    System.out.println("Test 6 (mixed components, one cycle) → Expected true: " +
	        hasCircularDependency(
	            5,
	            List.of(
	                List.of(0, 1),
	                List.of(1, 2),
	                List.of(2, 0),
	                List.of(3, 4)
	            )
	        )
	    );

	    // ------------------------------------------------------------
	    // TEST 7: Branching graph with no cycle
	    //
	    //   0
	    //  / \
	    // 1   2 → 3
	    // ------------------------------------------------------------
	    System.out.println("Test 7 (branching no cycle) → Expected false: " +
	        hasCircularDependency(
	            4,
	            List.of(
	                List.of(0, 1),
	                List.of(0, 2),
	                List.of(2, 3)
	            )
	        )
	    );

	    // ------------------------------------------------------------
	    // TEST 8: Duplicate dependencies (allowed, should not cause cycle)
	    //
	    // 0→1 (twice)
	    // 1→2
	    // 2→3
	    // ------------------------------------------------------------
	    System.out.println("Test 8 (duplicate edges) → Expected false: " +
	        hasCircularDependency(
	            4,
	            List.of(
	                List.of(0, 1),
	                List.of(0, 1),
	                List.of(1, 2),
	                List.of(2, 3)
	            )
	        )
	    );
	}

}
