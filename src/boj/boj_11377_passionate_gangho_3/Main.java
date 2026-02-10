package boj.boj_11377_passionate_gangho_3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static class Edge{
        int to, reverse;
        int capacity;
        public Edge(int t, int r, int c){
            to = t;
            reverse = r;
            capacity = c;
        }
    }
    static List<Edge>[] adj;
    static int[] level;
    static int[] it;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());


        int s = 1;
        int x = 2;
        int basePerson = 3;
        int baseJob = basePerson + n;
        int T = baseJob + m;
        int v = T;

        adj = new ArrayList[v + 1];
        for(int i = 1; i <= v; i++) adj[i] = new ArrayList<>();
        level = new int[v + 1];
        it = new int[v + 1];

        for(int i = 0; i < n; i++){
            addEdge(s, basePerson + i, 1);
        }

        addEdge(s, x, k);
        for(int i = 0; i < n; i++){
            addEdge(x, basePerson + i, 1);
        }

        for(int i = 0; i < m; i++){
            addEdge(baseJob + i, T, 1);
        }

        for(int i = 0; i < n; i++){
            st = new StringTokenizer(br.readLine());
            int cnt = Integer.parseInt(st.nextToken());
            int personNode = basePerson + i;
            for(int j = 0; j < cnt; j++){
                int work = Integer.parseInt(st.nextToken());
                int workNode = baseJob + work - 1;
                addEdge(personNode, workNode, 1);
            }
        }

        System.out.println(maxFlow(s, T));
    }

    static void addEdge(int u, int v, int cap){
        Edge forward = new Edge(v, adj[v].size(), cap);
        Edge backward = new Edge(u, adj[u].size(), 0);
        adj[u].add(forward);
        adj[v].add(backward);
    }

    static boolean bfs(int s, int t){
        Arrays.fill(level, -1);
        ArrayDeque<Integer> q = new ArrayDeque<>();
        level[s] = 0;
        q.add(s);

        while(!q.isEmpty()){
            int u = q.poll();
            for(Edge e : adj[u]){
                if(e.capacity <= 0) continue; // 남은 용량이 0이면 갈 수 없는 간선
                if(level[e.to] != -1) continue; // level이 -1이 아니라면 e.to는 이미 갔던 정점(중복 방문 방지)
                level[e.to] = level[u] + 1; // 간선의 도착지점은 시작지점보다 level이 1 높음(레벨 그래프에서 다음 정점은 현재 정점보다 한 단계 아래)
                q.add(e.to);
            }
        }

        return level[t] != -1; // bfs를 통해 t까지 갈 수 있는지 아닌지 판별(잔여 용량이 남은 간선들만 사용했을 때 t에 도달 가능한지)
    }

    static int dfs(int u, int t, int pushed){
        if(pushed == 0) return 0;
        if(u == t) return pushed;

        for(int i = it[u]; i < adj[u].size(); i++){
            it[u] = i; // 정점 u와 연결된 간선들 중에, i번째 간선까지는 이미 봤으니까, 다음 dfs에서는 거기서부터 시작해야 함을 기록(단, 같은 bfs 라운드 내에서만 기억)
            Edge e = adj[u].get(i);
            if(e.capacity <= 0) continue; // 남은 용량이 0보다 작으면 갈 수 없음
            if(level[e.to] != level[u] + 1) continue; // 다음에 바로 연결되는 간선이 아니라면 갈 수 없음

            int tr = dfs(e.to, t, Math.min(pushed, e.capacity)); // 이번 경로로 보낼 수 있는 유량의 상한
            if(tr == 0) continue; // 보낸 유량이 0이면 실패 -> 다른 간선 시도

            e.capacity -= tr; // 현재 간선의 잔여 용량 감소
            adj[e.to].get(e.reverse).capacity += tr; // 역간선의 잔여 용량 증가(필요한 경우 다시 되돌아 갈 수 있음)
            return tr; // u에서 t까지 보낸 유량 return
        }
        return 0; // 이 경로로는 보낼 수 없음 - 다른 간선 시도
    }

    static int maxFlow(int s, int t){
        int flow = 0;
        while(bfs(s, t)){ // s에서 t까지 갈 수 있는 동안
            Arrays.fill(it, 0); // 현재 단계에서 it를 0으로 초기화
            while(true){
                int pushed = dfs(s, t, Integer.MAX_VALUE); //
                if(pushed == 0) break; // s에서 t까지 갈 수 없다면 break 후 다음 bfs 라운드로 이동
                flow += pushed; // 갈 수 있다면 flow에 pushed만큼 추가(배정된 일의 수 증가)
            }
        }
        return flow;
    }
}

// 이 문제에서 유량이 의미하는 것은 최종적으로 배정된 일의 개수
// s -> (사람 / 추가 풀) -> 사람 -> 일 -> t (이때, 일->t 간선의 용량 = 1)
// 다시 말해, 일 -> t를 1만큼 통과했다 = 그 일 하나가 누군가에게 배정됐다.
// 한 개의 일은 t로 2 이상 흘러갈 수 없다 -> 한 개의 일은 한 명의 사람에게만 배정
// S -> T로 유량이 1 흘렀다 = 일 하나가 배정됐다
// s, t는 여러 명의 사람과 여러 개의 일을 하나로 묶기 위한 정점들

// 여기서, 한 사람이 일을 두 개 한다는 것은 유량으로 표시하면 그 사람 노드를 두 번 통과하는 서로 다른 경로가 있다
// 즉, S -> 사람 -> 일1 -> T와 S -> 추가 풀 -> 사람 -> 일2 -> T가 존재한다
// 유량의 관점에서는 서로 다른 두 단위의 flow

// 이미 하나의 일을 배정한 상태에서, 더 최적의 일 배정을 위해 다시 그 일을 취소하고 다른 일에 할당
// P1 -> J1, J2, P2 -> J1인 경우에 P1을 J1으로 이미 할당했을 때,
// P2를 J1에 다시 할당하기 위해서는 P1을 J2로 옮겨야 함
// 즉, S -> P2 -> J1 -> P1 -> J2 -> T의 흐름을 거쳐야 하는데, 여기서 J1->P1이 역간선
// 이미 P1을 J1에 할당할 떄 역간선에 capacity를 추가해 주었기 때문에 다시 되돌아가 다른 일에 할당할 수 있음
// 결과적으로 P1 -> J1, P2 -> 없음에서 P1 -> J2으로 재할당이 가능하기 때문에 P1 -> J2, P2 -> J1 으로 할당이 가능함
// 이를 위해 역간선과 순방향 간선의 capacity를 증가,감소 시키는 것

// bfs, dfs 전체 흐름
// 첫 번째 bfs 라운드
// bfs가 만드는 level => s = 0, P1, P2 = 1, J1, J2 = 2, T = 3
// 1. S -> P1 -> J1 -> T (P1 -> J1 capacity는 1 -> 0으로 감소, J1 -> P1 capacity는 0 -> 1로 증가), return 1, flow = 1
// 2. S -> P2(갈 수 있음) -> J1 (갈 수 있음) -> P1(갈 수 없음 -> J1의 level은 2, P1의 level은 1 => 레벨이 1 증가하는 방향이 아니라 1 감소하는 방향) return 0, flow = 1

// 두 번째 bfs 라운드
// bfs가 만드는 level => s = 0, P2 = 1, J1 = 2, P1 = 3, J1 = 4, T = 5 (이미 s -> P1 간선은 첫 번째 bfs에서 capacity를 소모했기 때문에 0이라서 다른 간선을 타고 가야함)
// 1. S -> P2 -> J1 -> P1(이제 갈 수 있음 -> capacity = 1이고, level도 증가하는 방향이기 때문) -> J1 -> T, return 1, flow = 2