import java.util.*;

class Solution {

    List<Integer>[] graph;
    List<Integer>[] undirected;
    boolean[] suspicious;
    boolean[] visited;

    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {

        graph = new ArrayList[n];
        undirected = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
            undirected[i] = new ArrayList<>();
        }

        for (int[] edge : invocations) {
            int u = edge[0];
            int v = edge[1];

            graph[u].add(v);

            undirected[u].add(v);
            undirected[v].add(u);
        }

        suspicious = new boolean[n];
        visited = new boolean[n];

        // Mark all suspicious methods
        dfs(k);

        // Any non-suspicious method connected to suspicious ones
        // makes them impossible to remove.
        for (int i = 0; i < n; i++) {
            if (!suspicious[i] && !visited[i]) {
                dfs2(i);
            }
        }

        List<Integer> ans = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (!suspicious[i]) {
                ans.add(i);
            }
        }

        return ans;
    }

    private void dfs(int node) {
        suspicious[node] = true;

        for (int next : graph[node]) {
            if (!suspicious[next]) {
                dfs(next);
            }
        }
    }

    private void dfs2(int node) {
        visited[node] = true;

        for (int next : undirected[node]) {
            if (!visited[next]) {
                suspicious[next] = false;
                dfs2(next);
            }
        }
    }
}