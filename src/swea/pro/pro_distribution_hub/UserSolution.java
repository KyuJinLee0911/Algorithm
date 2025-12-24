package swea.pro.pro_distribution_hub;

import java.util.*;

class Edge implements Comparable<Edge> {
    int to, cost;

    Edge(int to, int cost) {
        this.to = to;
        this.cost = cost;
    }

    @Override
    public int compareTo(Edge o) {
        return Integer.compare(this.cost, o.cost);
    }
}

class UserSolution {
    Map<Integer, Integer> id2idx;
    List<Edge>[] graph, reverseGraph;
    int maxCityCnt;

    public int init(int N, int sCity[], int eCity[], int mCost[]) {
        id2idx = new HashMap<>();
        graph = new ArrayList[601];
        reverseGraph = new ArrayList[601];

        for(int i = 0; i < 601; i++){
            graph[i] = new ArrayList<>();
            reverseGraph[i] = new ArrayList<>();
        }

        for (int i = 0; i < N; i++) {
            id2idx.putIfAbsent(sCity[i], id2idx.size());
            id2idx.putIfAbsent(eCity[i], id2idx.size());
            graph[id2idx.get(sCity[i])].add(new Edge(id2idx.get(eCity[i]), mCost[i]));
            reverseGraph[id2idx.get(eCity[i])].add(new Edge(id2idx.get(sCity[i]), mCost[i]));
        }

        maxCityCnt = id2idx.size();

        return id2idx.size();
    }

    public void add(int sCity, int eCity, int mCost) {
        graph[id2idx.get(sCity)].add(new Edge(id2idx.get(eCity), mCost));
        reverseGraph[id2idx.get(eCity)].add(new Edge(id2idx.get(sCity), mCost));

        return;
    }

    public int cost(int mHub) {
        int ans = 0;
        int x = id2idx.get(mHub);
        int[] dists = dijkstra(reverseGraph, x);
        int[] reverseDists = dijkstra(graph, x);
        for(int i = 0; i < dists.length; i++){
            if(i == x || dists[i] == Integer.MAX_VALUE) continue;
            ans += dists[i];
        }

        for(int i = 0; i < reverseDists.length; i++){
            if(i == x || reverseDists[i] == Integer.MAX_VALUE) continue;
            ans += reverseDists[i];
        }
        return ans;
    }

    public int[] dijkstra(List<Edge>[] gr, int start) {
        int[] dist = new int[maxCityCnt];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.add(new Edge(start, 0));

        while (!pq.isEmpty()) {
            Edge cur = pq.poll();

            if (cur.cost > dist[cur.to]) {
                continue;
            }

            for (Edge e : gr[cur.to]) {
                int nextDest = e.to;
                int nextCost = dist[cur.to] + e.cost;


                if (nextCost < dist[nextDest]) {
                    dist[nextDest] = nextCost;
                    pq.add(new Edge(nextDest, nextCost));
                }

            }
        }

        return dist;
    }
}