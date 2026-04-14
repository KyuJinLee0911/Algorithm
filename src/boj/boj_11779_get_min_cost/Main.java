package boj.boj_11779_get_min_cost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static List<Edge>[] graph;
    static int n, m;
    static List<Integer> route;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        m = Integer.parseInt(br.readLine());
        graph = new ArrayList[n + 1];
        route = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        StringTokenizer st;
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            graph[from].add(new Edge(to, cost));
        }

        st = new StringTokenizer(br.readLine());
        int start = Integer.parseInt(st.nextToken());
        int end = Integer.parseInt(st.nextToken());

        int dist = dijkstra(start, end);
        StringBuilder sb = new StringBuilder();
        sb.append(dist).append("\n");
        sb.append(route.size()).append("\n");
        for (int i = route.size() - 1; i >= 0; i--) {
            sb.append(route.get(i)).append(" ");
        }

        System.out.println(sb.toString().trim());
    }

    static int dijkstra(int start, int end) {
        int[] dist = new int[n + 1];
        int[] prev = new int[n + 1];
        prev[start] = -1;

        Arrays.fill(dist, Integer.MAX_VALUE);

        dist[start] = 0;

        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.add(new Edge(start, 0));

        while (!pq.isEmpty()) {
            Edge cur = pq.poll();

            if (cur.cost > dist[cur.dest]) continue;
            if (cur.dest == end) break;

            for (Edge e : graph[cur.dest]) {
                int nextDest = e.dest;
                int nextCost = dist[cur.dest] + e.cost;

                if (nextCost < dist[nextDest]) {
                    dist[nextDest] = nextCost;
                    prev[nextDest] = cur.dest;
                    pq.add(new Edge(nextDest, nextCost));
                }
            }
        }

        int cur = end;
        route.add(cur);
        while (cur != start) {
            route.add(prev[cur]);
            cur = prev[cur];
        }

        return dist[end];
    }

    static class Edge implements Comparable<Edge> {
        int dest, cost;

        public Edge(int dest, int cost) {
            this.dest = dest;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(cost, o.cost);
        }
    }
}
