package boj.boj_10165_bus_route;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static class Route implements Comparable<Route> {
        int id;
        int start, end;

        public Route(int a, int b, int id) {
            start = a;
            end = b;
            this.id = id;
        }

        @Override
        public int compareTo(Route o) {
            return start != o.start ? Integer.compare(start, o.start) : Integer.compare(end, o.end);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int m = Integer.parseInt(br.readLine());
        boolean[] cancled = new boolean[m + 1];
        List<Route> routes = new ArrayList<>();
        StringTokenizer st;
        for (int i = 1; i <= m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            if(a > b){
                routes.add(new Route(a - n, b, i));
                routes.add(new Route(a, b + n, i));
            } else {
                routes.add(new Route(a, b, i));
            }
        }
        Collections.sort(routes);
        Route cur = routes.get(0);
        for (Route r : routes) {
            if(r == cur) continue;
            if(cancled[r.id]) continue;
            if(r.start >= cur.start && r.end <= cur.end){
                cancled[r.id] = true;
            } else {
                if(r.start <= cur.start && r.end >= cur.end){
                    cancled[cur.id] = true;
                }
                cur = r;
            }
        }

        StringBuilder sb = new StringBuilder();
        for(int i = 1; i <= m; i++){
            if(cancled[i]) continue;
            sb.append(i);
            if(i < m){
                sb.append(" ");
            }
        }

        System.out.println(sb);

    }
}
