package boj.boj_16928_snake_and_ladder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static List<Edge>[] graph;
    static int[] snake, ladder;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        graph = new ArrayList[101];
        snake = new int[101];
        ladder = new int[101];
        for (int i = 1; i <= 100; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 1; i <= 100; i++) {
            for (int j = 1; j <= 6; j++) {
                if (i + j > 100) break;

                graph[i].add(new Edge(i + j, 1));
            }
        }

        for (int i = 0; i < n + m; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            graph[from].add(new Edge(to, 0));
            if (i < n) {
                ladder[from] = to;
            } else {
                snake[from] = to;
            }
        }


        System.out.println(dijkstra());
    }

    static int dijkstra() {
        PriorityQueue<Edge> q = new PriorityQueue<>();
        int[] dist = new int[101];
        Arrays.fill(dist, 100);
        q.add(new Edge(1, 0));
        dist[1] = 0;
        while (!q.isEmpty()) {
            Edge cur = q.poll();
            if (cur.dest == 100) break;
            if (cur.dist > dist[cur.dest]) continue;

            for (Edge next : graph[cur.dest]) {
                int nextDest = next.dest;
                if (snake[nextDest] != 0) nextDest = snake[nextDest];
                else if (ladder[nextDest] != 0) nextDest = ladder[nextDest];
                int nextDist = dist[cur.dest] + next.dist;

                if (nextDist < dist[nextDest]) {
                    dist[nextDest] = nextDist;
                    q.add(new Edge(nextDest, nextDist));
                }
            }
        }

        return dist[100];
    }

    static class Edge implements Comparable<Edge> {
        int dest, dist;

        public Edge(int dest, int dist) {
            this.dest = dest;
            this.dist = dist;
        }

        @Override
        public int compareTo(Edge o) {
            return dist == o.dist ? Integer.compare(o.dest, dest) : Integer.compare(dist, o.dist);
        }
    }
}
