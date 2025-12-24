package swea.pro.pro_three_kingdoms_dynasty_game;

import java.util.*;

class UnionFind {
    int[] parent, rank;
    int size;

    public UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        Arrays.fill(rank, 1);
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        size = n;
    }

    public int findParent(int x) {
        if (parent[x] != x) {
            parent[x] = findParent(parent[x]);
        }
        return parent[x];
    }

    public void union(int x, int y) {
        int xParent = findParent(x);
        int yParent = findParent(y);
        if (rank[xParent] >= rank[yParent]) {
            parent[yParent] = xParent;
            rank[xParent] += rank[yParent];
        } else if (rank[xParent] < rank[yParent]) {
            parent[xParent] = yParent;
            rank[yParent] += rank[xParent];
        }
    }

    // 영주 사망 시 연결 끊고 새로운 부모 영주 return
    public int disconnect(int x) {
        int xPar = findParent(x);
        if(xPar == x){ // 새로운 부모 할당 필요
            int tempRank = rank[xPar]; // 기존 rank
            tempRank--; // x를 제외한 새로운 rank
            rank[xPar] = 1;
            parent[x] = x;

            if(tempRank == 0) return x;
            // 나머지에 새로운 부모 할당
            int cnt = 0;
            int[] leftover = new int[tempRank];
            for(int i = 0; i < size; i++){
                if(i == x) continue;
                if(parent[i] == xPar){
                    leftover[cnt++] = i;
                }
            }

            for(int i : leftover){
                parent[i] = leftover[0];
                if(i == leftover[0]){
                    rank[i] = tempRank;
                } else {
                    rank[i] = 1;
                }
            }
            return leftover[0];
        } else { // 기존 부모와의 연결만 끊으면 됨
            rank[xPar]--;
            parent[x] = x;
            return xPar;
        }

    }

    public boolean isConnected(int x, int y) {
        return findParent(x) == findParent(y);
    }

    // 유니온에 속하는 모든 영주 idx 반환
    public int[] getAllNodes(int x) {
        int xPar = findParent(x);
        int[] nodes = new int[rank[xPar]];
        int cnt = 0;
        for (int i = 0; i < size; i++) {
            if (findParent(i) == xPar) {
                nodes[cnt++] = i;
            }
        }
        return nodes;
    }
}

class UserSolution {

    String char2Str(char[] name) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < name.length; i++) {
            if (name[i] == '\0') {
                break;
            }
            sb.append(name[i]);
        }

        return sb.toString();
    }

    int N;
    Map<String, Integer> name2idx; // 이름을 인덱스로 변환한 값을 담고있는 맵
    UnionFind unions; // 동맹관계를 담고 있는 유니온
    Map<Integer, Set<Integer>> enemyUnion; // 적대 관계에 있는 유니온의 쌍 (a, b), (b, a)모두 포함
    int[] soldiers;

    // 두 영주가 인접 영주인지 판단
    // 매개변수는 두 영주의 좌표를 압축한 수
    // 8방으로 판단
    public boolean isNear(int a, int b) {
        int quotientA = a / N;
        int modA = a % N;
        int quotientB = b / N;
        int modB = b % N;

        // 상하
        if (modA == modB && Math.abs(quotientA - quotientB) == 1) {
            return true;
        }

        // 좌우
        if (quotientA == quotientB && Math.abs(modA - modB) == 1) {
            return true;
        }

        // 대각선
        if (Math.abs(quotientA - quotientB) == 1 && Math.abs(modA - modB) == 1)
            return true;

        return false;
    }

    void init(int N, int mSoldier[][], char mMonarch[][][]) {
        this.N = N;
        name2idx = new HashMap<>();
        unions = new UnionFind(N * N);
        enemyUnion = new HashMap<>();
        soldiers = new int[N * N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                String name = char2Str(mMonarch[i][j]);
                int idx = i * N + j;
                name2idx.put(name, idx);
                soldiers[idx] = mSoldier[i][j];
            }
        }
    }

    void destroy() {

    }

    int ally(char mMonarchA[], char mMonarchB[]) {
        String aName = char2Str(mMonarchA);
        String bName = char2Str(mMonarchB);
        int aIdx = name2idx.get(aName);
        int bIdx = name2idx.get(bName);
        int aPar = unions.findParent(aIdx);
        int bPar = unions.findParent(bIdx);
        if (aPar == bPar) {
            return -1;
        } else {
            Set<Integer> newParentsA = new HashSet<>(); // 갱신될 부모를 담을 set
            Set<Integer> newParentsB = new HashSet<>(); // 갱신될 부모를 담을 set

            for (int i : enemyUnion.getOrDefault(aPar, new HashSet<>())) {
                int newPar = unions.parent[i];
                newParentsA.add(newPar);
            }
            for (int i : enemyUnion.getOrDefault(bPar, new HashSet<>())) {
                int newPar = unions.parent[i];
                newParentsB.add(newPar);
            }

            enemyUnion.put(aPar, newParentsA);
            enemyUnion.put(bPar, newParentsB);

            if (enemyUnion.get(aPar).contains(bPar) || enemyUnion.get(bPar).contains(aPar)) {
                return -2;
            }
        }
        unions.union(aIdx, bIdx);
        return 1;
    }

    int attack(char mMonarchA[], char mMonarchB[], char mGeneral[]) {
        String aName = char2Str(mMonarchA);
        String bName = char2Str(mMonarchB);
        int aIdx = name2idx.get(aName);
        int bIdx = name2idx.get(bName);
        int aPar = unions.findParent(aIdx);
        int bPar = unions.findParent(bIdx);
        if (aPar == bPar) {
            return -1;
        }

        // 동맹 중 인접 영주가 있는지 판단
        int[] aAlly = unions.getAllNodes(aPar);
        int[] bAlly = unions.getAllNodes(bPar);
        int cntA = 0; // 인접한 A동맹 영주의 수
        int cntB = 0; // 인접한 B동맹 영주의 수
        int[] nearA = new int[8]; // 최대 8명의 영주
        int[] nearB = new int[8]; // 최대 8명의 영주

        Arrays.fill(nearA, -1); // 초기값 -1로 채움
        Arrays.fill(nearB, -1);
        // A 동맹 중 B와 인접한 영주 추가
        for (int i : aAlly) {
            if (isNear(i, bIdx)) { // 인접해있다면
                nearA[cntA++] = i; // 추가
            }
        }
        // B 동맹 중 A와 인접한 영주 추가
        for (int i : bAlly) {
            if (isNear(i, bIdx)) {
                nearB[cntB++] = i;
            }
        }

        // 공격 동맹 중에 인접 영주가 없으면 return -2
        if (cntA == 0) {
            return -2;
        }

        int att = 0;
        int def = soldiers[bIdx];

        for (int aNearMon : nearA) {
            if (aNearMon == -1) break;

            int attSol = soldiers[aNearMon] / 2; // soldiers[allyIdx] = 15일 경우 7
            att += attSol;
            soldiers[aNearMon] -= attSol; // 15일 경우 8이 남음
        }

        for (int bNearMon : nearB) {
            if (bNearMon == -1) break;

            int defSol = soldiers[bNearMon] / 2;
            def += defSol;
            soldiers[bNearMon] -= defSol;
        }

        int leftSol = Math.abs(att - def); // 남은 병사
        soldiers[bIdx] = leftSol; // 이기든 지든 그 위치의 병사는 남은 병사 and union 상의 idx에는 변화 없음
        int isSuccess = 0; // 공격 승리 시 1, 공격 실패 시 0

        // 공격이 승리 => 영주 변경 = 맵에 영주 추가
        if (att > def) {
            String genName = char2Str(mGeneral);
            name2idx.put(genName, bIdx); // 장군을 새로 영주로 추대

            int newBAllyCaptain = unions.disconnect(bIdx); // 사망한 영주 해당 동맹에서 삭제
            name2idx.remove(bName); // b영주 맵에서 삭제
            // 새로 추가된 친구도 유니온에 추가합니다
            unions.union(aPar, bIdx);

            // 살아남은 나머지 동맹끼리는 적대관계가 됨
            enemyUnion.putIfAbsent(aPar, new HashSet<>());
            enemyUnion.putIfAbsent(newBAllyCaptain, new HashSet<>());
            enemyUnion.get(aPar).add(newBAllyCaptain);
            enemyUnion.get(newBAllyCaptain).add(aPar);
            isSuccess = 1;
        } else { // 방어가 승리 => 원수가 되었습니다
            //맞짱뜸 => 적대는 방어가 이겼을 때만 적용됨
            enemyUnion.putIfAbsent(aPar, new HashSet<>());
            enemyUnion.putIfAbsent(bPar, new HashSet<>());
            enemyUnion.get(aPar).add(bPar);
            enemyUnion.get(bPar).add(aPar);
        }

        return isSuccess;
    }

    int recruit(char mMonarch[], int mNum, int mOption) {
        String name = char2Str(mMonarch);
        int idx = name2idx.get(name);
        if (mOption == 0) {
            soldiers[idx] += mNum;
            return soldiers[idx];
        } else if (mOption == 1) {
            int[] mons = unions.getAllNodes(idx);
            int ret = 0;
            for (int i : mons) {
                soldiers[i] += mNum;
                ret += soldiers[i];
            }
            return ret;
        }
        return -1;
    }


}