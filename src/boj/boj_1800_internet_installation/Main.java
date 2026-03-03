package boj.boj_1800_internet_installation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static List<Edge>[] graph;
    static int n, p, k;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        p = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < p; i++) {
            st = new StringTokenizer(br.readLine());
            int v1 = Integer.parseInt(st.nextToken());
            int v2 = Integer.parseInt(st.nextToken());
            int price = Integer.parseInt(st.nextToken());
            graph[v1].add(new Edge(v2, price));
            graph[v2].add(new Edge(v1, price));
        }

        int l = 0, r = 1_000_000;
        while (l < r) {
            int mid = (l + r) / 2;

            if (dijkstra(mid)) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }

        if (dijkstra(l)) {
            System.out.println(l);
        } else {
            System.out.println(-1);
        }
    }


    static boolean dijkstra(int x) {
        int[] dist = new int[n + 1];
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.add(new Edge(1, 0));
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[1] = 0;
        while (!pq.isEmpty()) {
            Edge cur = pq.poll();
            if (cur.dist > dist[cur.to]) continue;
            for (Edge e : graph[cur.to]) {
                int nextTo = e.to;
                int nextDist = 0;
                if (e.dist <= x) {
                    nextDist = dist[cur.to];
                } else {
                    nextDist = dist[cur.to] + 1;
                }

                if (dist[nextTo] > nextDist) {
                    dist[nextTo] = nextDist;
                    pq.add(new Edge(nextTo, nextDist));
                }
            }
        }

        return dist[n] <= k;
    }


    static class Edge implements Comparable<Edge> {
        int to, dist;

        public Edge(int to, int dist) {
            this.to = to;
            this.dist = dist;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(dist, o.dist);
        }
    }
}
