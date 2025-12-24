package swea.pro.tkd_9th;

import java.util.*;

class UnionFind {
    List<Integer> parent, rank;
    Map<Integer, Set<Integer>> allyMap, enemyMap; // 각 동맹 별 id를 담고 있는 Map과 적대 관계 영주를 담고 있는 Map


    public UnionFind(int n) {
        parent = new ArrayList<>();
        rank = new ArrayList<>();
        allyMap = new HashMap<>();
        enemyMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            Set<Integer> temp = new HashSet<>();
            temp.add(i);
            allyMap.put(i, temp);
            parent.add(i);
            rank.add(1);
        }
    }

    public int findParent(int x) {
        if (parent.get(x) != x) {
            parent.set(x, findParent(parent.get(x)));
        }
        return parent.get(x);
    }

    public void union(int x, int y) {
        int xParent = findParent(x);
        int yParent = findParent(y);
        if(xParent == yParent) return;

        if (rank.get(xParent) >= rank.get(yParent)) {
            parent.set(yParent, xParent);
            int newRank = rank.get(yParent) + rank.get(xParent);
            rank.set(xParent, newRank);

            // 병합되는 하위 동맹을 상위 동맹에 전부 추가 후 하위 동맹 삭제
            Set<Integer> temp = allyMap.get(xParent);
            temp.addAll(allyMap.get(yParent));
            allyMap.put(xParent, temp);
            allyMap.remove(yParent);

            // 적대 관계도 병합 및 삭제 후 상대방에게도 추가
            Set<Integer> enemiesX = enemyMap.getOrDefault(xParent, new HashSet<>());
            Set<Integer> enemiesY = enemyMap.getOrDefault(yParent, new HashSet<>());
            enemiesX.addAll(enemiesY);
            enemyMap.remove(yParent);

            for(int enemyRoot : enemiesY){
                Set<Integer> enemySet = enemyMap.get(enemyRoot);
                if(enemySet != null){
                    enemySet.remove(yParent);
                    enemySet.add(xParent);
                }
            }

            enemyMap.put(xParent, enemiesX);
        } else if (rank.get(xParent) < rank.get(yParent)) {
            parent.set(xParent, yParent);
            int newRank = rank.get(yParent) + rank.get(xParent);
            rank.set(yParent, newRank);

            // 병합되는 하위 동맹을 상위 동맹에 전부 추가 후 하위 동맹 삭제
            Set<Integer> temp = allyMap.get(yParent);
            temp.addAll(allyMap.get(xParent));
            allyMap.put(yParent, temp);
            allyMap.remove(xParent);

            // 적대 관계도 병합 및 삭제 후 상대방에게도 추가
            Set<Integer> enemiesX = enemyMap.getOrDefault(xParent, new HashSet<>());
            Set<Integer> enemiesY = enemyMap.getOrDefault(yParent, new HashSet<>());
            enemiesY.addAll(enemiesX);
            enemyMap.remove(xParent);

            for(int enemyRoot : enemiesX){
                Set<Integer> enemySet = enemyMap.get(enemyRoot);
                if(enemySet != null){
                    enemySet.remove(xParent);
                    enemySet.add(yParent);
                }
            }

            enemyMap.put(yParent, enemiesY);
        }
    }

    public boolean isConnected(int x, int y) {
        return findParent(x) == findParent(y);
    }

    public Set<Integer> getAllies(int id){
        int parent = findParent(id);
        return allyMap.get(parent);
    }

    public void removeDeadMonarch(int id, int parentId){
        int curRank = rank.get(parentId);
        rank.set(parentId, --curRank);
        Set<Integer> temp = allyMap.getOrDefault(parentId, new HashSet<>());
        if(temp.contains(id)){
            temp.remove(id);
        }
        allyMap.put(parentId, temp);
    }

    public void addNewMonarch(int id, int parentId){
        parent.add(parentId);
        rank.add(1);
        int curRank = rank.get(parentId);
        rank.set(parentId, ++curRank);
        Set<Integer> temp = allyMap.getOrDefault(parentId, new HashSet<>());
        temp.add(id);
        allyMap.put(parentId, temp);
    }

    public boolean isEnemy(int x, int y){
        int xRoot = findParent(x);
        int yRoot = findParent(y);
        boolean flag = false;
        Set<Integer> xEnemy = enemyMap.get(xRoot);
        if(xEnemy != null){
            if(xEnemy.contains(yRoot)){
                flag = true;
            }
        }
        return flag;
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
    Map<Integer, Integer> idx2Pos; // 인덱스를 현재 영지의 위치로 변환하는 맵
    List<Boolean> isAlive; // 영주의 생존 여부를 판단하는 리스트 (parent의 생존 여부와 관계 없이 가장 조상을 유니온의 id값으로 가져가기 위함)
    // -> 위 세 개는 공격팀이 이겨서 새 영주가 추가될 때 새로운 원소 추가

    UnionFind unions; // 동맹관계를 담고 있는 유니온
//    Map<Integer, Set<Integer>> enemyUnion; // 적대 관계에 있는 유니온의 쌍 (a, b), (b, a)모두 포함

    int[] soldiers; // 영지 별 병사의 수

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
        unions = new UnionFind(N * N); // 초기에 parent, rank의 크기는 N * N
//        enemyUnion = new HashMap<>(); // 초기에는 적대 관계 없음
        soldiers = new int[N * N];
        isAlive = new ArrayList<>();
        idx2Pos = new HashMap<>();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                String name = char2Str(mMonarch[i][j]);
                int idx = i * N + j;
                name2idx.put(name, idx);
                soldiers[idx] = mSoldier[i][j];
                idx2Pos.put(idx, idx); // 초기에 위치와 idx는 같음
                isAlive.add(true); // 초기에는 전부 생존
            }
        }
    }

    void destroy() {

    }

    int ally(char mMonarchA[], char mMonarchB[]) {
        String aName = char2Str(mMonarchA);
        String bName = char2Str(mMonarchB);
        if(aName.equals(bName)){ // 동일한 영주면 return -1
            return -1;
        }

        int aIdx = name2idx.get(aName);
        int bIdx = name2idx.get(bName);

        int aPar = unions.findParent(aIdx);
        int bPar = unions.findParent(bIdx);

        if (aPar == bPar) {
            return -1;
        } else {
            if (unions.isEnemy(aPar, bPar)) {
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
        int aPos = idx2Pos.get(aIdx);
        int bPos = idx2Pos.get(bIdx);
        int aPar = unions.findParent(aIdx);
        int bPar = unions.findParent(bIdx);

        if (aPar == bPar) {
            return -1;
        }

        // 동맹 중 인접 영주가 있는지 판단
        Set<Integer> aAlly = unions.getAllies(aIdx);
        Set<Integer> bAlly = unions.getAllies(bIdx);

        int cntA = 0; // 인접한 A동맹 영주의 수
        int cntB = 0; // 인접한 B동맹 영주의 수

        int[] nearA = new int[8]; // 최대 8명의 영주
        int[] nearB = new int[8]; // 최대 8명의 영주

        Arrays.fill(nearA, -1); // 초기값 -1로 채움
        Arrays.fill(nearB, -1);
        // A 동맹 중 B와 인접한 영주 추가
        for (int i : aAlly) {
            int pos = idx2Pos.get(i);
            if (isNear(pos, bPos)) { // 인접해있다면
                nearA[cntA++] = pos; // 해당하는 위치 추가
            }
        }
        // B 동맹 중 A와 인접한 영주 추가
        for (int i : bAlly) {
            int pos = idx2Pos.get(i);
            if (isNear(pos, bPos)) {
                nearB[cntB++] = pos;
            }
        }

        // 공격 동맹 중에 인접 영주가 없으면 return -2
        if (cntA == 0) {
            return -2;
        }

        int att = 0;
        int def = soldiers[bPos];

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
        soldiers[bPos] = leftSol; // 이기든 지든 그 위치의 병사는 남은 병사 and union 상의 idx에는 변화 없음
        int isSuccess = 0; // 공격 승리 시 1, 공격 실패 시 0

        // 공격이 승리 => 일단 처형하고 새로 임명하기
        if (att > def) {
            // 패배한 영주 처형
            unions.removeDeadMonarch(bIdx, bPar); // 동맹 map에서 삭제 후 해당 동맹 rank 감소
            isAlive.set(bIdx, false); // 죽었음 ㅠㅠ

            // 장군을 영주로
            String genName = char2Str(mGeneral);
            int idx = name2idx.size(); // 새로 추가되는 영주의 idx 설정 -> 맨 뒤에 추가되므로 name2idx의 사이즈가 새로 추가되는 영주의 idx
            name2idx.put(genName, idx); // name -> idx에 추가
            idx2Pos.put(idx, bPos); // idx -> pos에 추가. 추가되는 위치는 b영주의 위치와 동일
            isAlive.add(true);

            // 새로 생긴 장군 동맹에 추가
            unions.addNewMonarch(idx, aPar);

            isSuccess = 1;
        }
        // 공격의 성공 여부와 관계없이 일단 원수가 됨
        Set<Integer> tempA = unions.enemyMap.getOrDefault(aPar, new HashSet<>());
        Set<Integer> tempB = unions.enemyMap.getOrDefault(bPar, new HashSet<>());
        tempA.add(bPar);
        tempB.add(aPar);
        unions.enemyMap.put(aPar, tempA);
        unions.enemyMap.put(bPar, tempB);

        return isSuccess;
    }

    int recruit(char mMonarch[], int mNum, int mOption) {
        String name = char2Str(mMonarch);
        int idx = name2idx.get(name);
        int pos = idx2Pos.get(idx);
        if (mOption == 0) {
            soldiers[pos] += mNum;
            return soldiers[pos];
        } else if (mOption == 1) {
            Set<Integer> mons = unions.getAllies(idx);
            int ret = 0;
            for (int i : mons) {
                int i2pos = idx2Pos.get(i);
                soldiers[i2pos] += mNum;
                ret += soldiers[i2pos];
            }
            return ret;
        }
        return -1;
    }
}