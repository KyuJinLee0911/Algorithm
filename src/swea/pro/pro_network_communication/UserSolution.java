package swea.pro.pro_network_communication;

import java.util.*;

class Node implements Comparable<Node> {
    int to, dist;

    public Node(int to, int dist) {
        this.to = to;
        this.dist = dist;
    }

    @Override
    public int compareTo(Node o) {
        return Integer.compare(dist, o.dist);
    }
}

class UserSolution {
    final int ROOT_CNT = 3;
    final int REP_CNT = 3;
    final int NODE_CNT = 31;
    int graphSize;
    Node[][] graph; // root노드와 대표 노드만 담고있는 그래프
    Node[][][] subGroupGraph; // 긱 그룹 내부의 연결 -> 노드 사이에는 최대 한 번
    int[][] dist; // root 사이 거리

    public void init(int N, int K, int mNodeA[], int mNodeB[], int mTime[]) {
        graphSize = N * 3 + 3;
        graph = new Node[graphSize][graphSize]; // 0 1 2 -> root, 이후 각 idx / 300 = 그룹 번호, (idx % 300) + 1 = 노드 번호 900 -> 300번 그룹의 1번 대표 노드
        subGroupGraph = new Node[N + 1][NODE_CNT][NODE_CNT];
        dist = new int[4][4];
        for (int i = 0; i < K; i++) {
            int nodeA = mNodeA[i];
            int nodeB = mNodeB[i];
            int time = mTime[i];
            if (isRoot(nodeA)) {
                int rootIdx = nodeA - 1;
                int bIdx = getIdx(nodeB); // -> root 노드는 대표 노드밖에 연결이 안되므로, 하나가 root라면 b는 자동으로 대표 노드
                graph[rootIdx][bIdx] = new Node(bIdx, time); // 인덱스로 저장
                graph[bIdx][rootIdx] = new Node(rootIdx, time);
                continue;
            } else if (isRoot(nodeB)) {
                int rootIdx = nodeB - 1;
                int aIdx = getIdx(nodeA);
                graph[rootIdx][aIdx] = new Node(aIdx, time);
                graph[aIdx][rootIdx] = new Node(rootIdx, time);
                continue;
            }

            int groupA = nodeA / 100;
            int groupB = nodeB / 100;
            int nodeNumA = nodeA % 100;
            int nodeNumB = nodeB % 100;

            if (isRep(nodeA) && isRep(nodeB)) { // 두 노드 모두 대표 노드일 경우 대표 노드 그래프에 추가
                int aIdx = getIdx(nodeA);
                int bIdx = getIdx(nodeB);
                graph[aIdx][bIdx] = new Node(bIdx, time);
                graph[bIdx][aIdx] = new Node(aIdx, time);
            }

            if (groupA == groupB) { // 같은 그룹 내에 속해 있을 경우 일반 그래프에 추가
                subGroupGraph[groupA][nodeNumA][nodeNumB] = new Node(nodeNumB, time);
                subGroupGraph[groupA][nodeNumB][nodeNumA] = new Node(nodeNumA, time);
            }
        }

        for (int i = 1; i < N; i++) { // subGraph 내부의 dijkstra를 통해 미리 그래프를 그려둠
            int idx0 = i * 3;
            int idx1 = idx0 + 1;
            int idx2 = idx0 + 2;
            int zToo = dijkstra(subGroupGraph[i], 1, 2);
            int ztot = dijkstra(subGroupGraph[i], 1, 3);
            int otot = dijkstra(subGroupGraph[i], 2, 3);

            graph[idx0][idx1] = new Node(idx1, zToo);
            graph[idx1][idx0] = new Node(idx0, zToo);
            graph[idx0][idx2] = new Node(idx2, ztot);
            graph[idx2][idx0] = new Node(idx0, ztot);
            graph[idx1][idx2] = new Node(idx2, otot);
            graph[idx2][idx1] = new Node(idx1, otot);

        }

        for(int i = 1; i < 4; i++){
            for(int j = i + 1; j < 4; j++){
                dist[i][j] = dijkstra(graph, i - 1, j - 1);
                dist[j][i] = dist[i][j];
            }
        }
    }

    boolean isRoot(int nodeNum) {
        int groupNo = nodeNum / 100;
        if (groupNo == 0) {
            return true;
        }

        return false;
    }

    boolean isRep(int nodeNum) {
        int nodeNo = nodeNum % 100;
        if (nodeNo == 1 || nodeNo == 2 || nodeNo == 3) {
            return true;
        }
        return false;
    }

    int getIdx(int nodeNum) {
        if (nodeNum % 100 > 3) {
            return -1;
        }
        return (nodeNum / 100 * 3) + (nodeNum % 100 - 1);
    }

    public void addLine(int mNodeA, int mNodeB, int mTime) {
        int groupA = mNodeA / 100;
        int groupB = mNodeB / 100;
        int nodeNumA = mNodeA % 100;
        int nodeNumB = mNodeB % 100;
        if (isRep(mNodeA) && isRep(mNodeB)) { // 두 노드 모두 대표 노드일 경우 대표 노드 그래프에 추가
            int aIdx = getIdx(mNodeA);
            int bIdx = getIdx(mNodeB);
            graph[aIdx][bIdx] = new Node(bIdx, mTime);
            graph[bIdx][aIdx] = new Node(aIdx, mTime);
        }

        if (groupA == groupB) { // 같은 그룹 내에 속해 있을 경우 일반 그래프에 추가
            subGroupGraph[groupA][nodeNumA][nodeNumB] = new Node(nodeNumB, mTime);
            subGroupGraph[groupA][nodeNumB][nodeNumA] = new Node(nodeNumA, mTime);
            // subGroup이 변경되었으므로 graph의 해당 부분 update
            for (int j = 1; j < 4; j++) {
                for (int k = j + 1; k < 4; k++) {
                    int idxA = groupA * 3 + (j - 1);
                    int idxB = groupA * 3 + (k - 1);
                    int dist = dijkstra(subGroupGraph[groupA], j, k);
                    graph[idxA][idxB] = new Node(idxB, dist);
                    graph[idxB][idxA] = new Node(idxA, dist);
                }
            }
        }

        for(int i = 1; i < 4; i++){
            for(int j = i + 1; j < 4; j++){
                dist[i][j] = dijkstra(graph,i - 1, j - 1);
                dist[j][i] = dist[i][j];
            }
        }
    }

    public void removeLine(int mNodeA, int mNodeB) {
        int groupA = mNodeA / 100;
        int groupB = mNodeB / 100;
        int nodeNumA = mNodeA % 100;
        int nodeNumB = mNodeB % 100;
        boolean isChanged = false;
        if (isRep(mNodeA) && isRep(mNodeB)) { // 두 노드 모두 대표 노드일 경우 대표 노드 그래프에서 삭제처리
            int aIdx = getIdx(mNodeA);
            int bIdx = getIdx(mNodeB);
            if (graph[aIdx][bIdx] == null || graph[bIdx][aIdx] == null) return;
            graph[aIdx][bIdx] = null;
            graph[bIdx][aIdx] = null;
            isChanged = true;
        }

        if (groupA == groupB) { // 같은 그룹 내에 속해 있을 경우 일반 그래프에서 삭제 처리
            if (subGroupGraph[groupA][nodeNumA][nodeNumB] == null &&
                    subGroupGraph[groupA][nodeNumB][nodeNumA] == null) return;

            subGroupGraph[groupA][nodeNumA][nodeNumB] = null;
            subGroupGraph[groupA][nodeNumB][nodeNumA] = null;
            // subGroup이 변경되었으므로 그룹 내 최단거리 다시 계산
            for (int j = 1; j < 4; j++) {
                for (int k = j + 1; k < 4; k++) {
                    int idxA = groupA * 3 + (j - 1);
                    int idxB = groupA * 3 + (k - 1);
                    int dist = dijkstra(subGroupGraph[groupA], j, k);
                    graph[idxA][idxB] = new Node(idxB, dist);
                    graph[idxB][idxA] = new Node(idxA, dist);
                }
            }
            isChanged = true;
        }

        if(!isChanged) return;

        for(int i = 1; i < 4; i++){
            for(int j = i + 1; j < 4; j++){
                dist[i][j] = dijkstra(graph, i - 1, j - 1);
                dist[j][i] = dist[i][j];
            }
        }

    }

    public int checkTime(int mNodeA, int mNodeB) {
        return dist[mNodeA][mNodeB];
    }

    int dijkstra(Node[][] graph, int start, int end) {
        int[] dist = new int[graph.length + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(start, 0));
        while (!pq.isEmpty()) {
            Node curNode = pq.poll();
            int curPos = curNode.to;
            int curDist = curNode.dist;
            if (curPos == end) break;
            if (dist[curPos] < curDist) continue;
            for (Node n : graph[curPos]) {
                if(n == null) continue;;
                int nextPos = n.to;
                int nextDist = dist[curPos] + n.dist;
                if (nextDist < dist[nextPos]) {
                    dist[nextPos] = nextDist;
                    pq.add(new Node(nextPos, nextDist));
                }
            }
        }
        return dist[end];
    }
}