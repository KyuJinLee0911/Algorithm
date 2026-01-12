package boj.boj_28707_sorting_array;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int[] arr1, arr2;
    static int n;
    static String FINAL_STATE;
    static Map<Integer, Integer> prices;
    static class Edge implements Comparable<Edge> {
        int price;
        int[] state;

        public Edge(int price, int[] state) {

            this.price = price;
            this.state = state;
        }

        @Override
        public int compareTo(Edge e) {
            return Integer.compare(price, e.price);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        arr1 = new int[n + 1];
        arr2 = new int[n + 1];
        prices = new HashMap<>();
        for (int i = 1; i <= n; i++) {
            arr1[i] = Integer.parseInt(st.nextToken());
            arr2[i] = arr1[i];
        }
        Arrays.sort(arr2);
        FINAL_STATE = getKey(arr2);

        int m = Integer.parseInt(br.readLine());

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int price = Integer.parseInt(st.nextToken());

            int hash = from * 10000 + to;
            prices.put(hash, Math.min(prices.getOrDefault(hash, Integer.MAX_VALUE), price));
        }
        String start = getKey(arr1);

        int sum = dijkstra(start);

        System.out.println(sum);
    }

    private static int dijkstra(String start) {
        Map<String, Edge> dist = new HashMap<>();
        dist.put(start, new Edge(0, arr1));

        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.add(new Edge(0, arr1));

        while (!pq.isEmpty()) {
            Edge cur = pq.poll();
            int curPrice = cur.price;
            int[] curState = cur.state;
            String curKey = getKey(curState);

            if (dist.get(curKey) != null && dist.get(curKey).price < curPrice) continue;
            if (curKey.equals(FINAL_STATE)) break;

            for(int hash : prices.keySet()){
                int a = hash / 10000;
                int b = hash % 10000;
                int price = prices.get(hash);
                int nextPrice = curPrice + price;
                int[] nextState = Arrays.copyOf(curState, curState.length);
                int temp = nextState[a];
                nextState[a] = nextState[b];
                nextState[b] = temp;

                String nextKey = getKey(nextState);
                if(dist.containsKey(nextKey)){
                    if(nextPrice < dist.get(nextKey).price){
                        Edge next = new Edge(nextPrice, nextState);
                        dist.put(nextKey, next);
                        pq.add(next);
                    }
                } else {
                    Edge next = new Edge(nextPrice, nextState);
                    dist.put(nextKey, next);
                    pq.add(next);
                }
            }
        }

        return dist.keySet().contains(FINAL_STATE) ? dist.get(FINAL_STATE).price : -1;
    }

    private static String getKey(int[] arr){
        StringBuilder sb = new StringBuilder();
        for(int i = 1; i <= n; i++){
            sb.append(arr[i]);
            if(i < n){
                sb.append(",");
            }
        }
        return sb.toString();
    }
}