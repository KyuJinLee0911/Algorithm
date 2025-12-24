package swea.pro.three_kingdoms_8th;

import java.util.*;

class Node {
    Node prev, next;
    int id, idx, allyIdx;

    public Node(int id, int idx, int allyIdx) {
        this.id = id;
        this.idx = idx;
        this.allyIdx = allyIdx;
        prev = this;
        next = null;
    }
}

class UserSolution {
    class Ally {
        Node first, last;
        int id;
        int cnt, groupId;
        Set<Integer> enemy;

        public Ally(int id) {
            this.id = id;
            this.first = mons[id];
            this.last = mons[id];
            cnt = 1;
            enemy = new HashSet<>();
        }

        public void union(Ally b) {
            // 마지막 노드랑 첫번째 노드 연결
            last.next = b.first;
            b.first.prev = last;

            // 기존 리스트의 마지막 노드와 새로 연결되는 리스트의 첫번째 노드 변경
            last = b.last;
            b.first = first;
            b.id = id;
            int temp = b.cnt;
            b.cnt += cnt;
            cnt += temp;

            // 적대관계 모두 추가
            enemy.addAll(b.enemy);
            b.enemy.addAll(enemy);
        }

        public boolean isEnemy(int allyId) {
            return enemy.contains(allyId);
        }

        public void addToEnemy(int enemyAllyId) {
            enemy.add(enemyAllyId);
        }

        public Node getFirst() {
            while (first.prev != first) {
                first = first.prev;
            }

            return first;
        }
    }

    int N;
    Map<String, Integer> name2Id; // 이름을 id로 변경
    int[] soldiers; // 위치 별 병사 수
    Node[] mons;
    List<Ally> allyList;
    Map<Integer, HashSet<Integer>> group;


    private String char2String(char[] name) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < name.length; i++) {
            if (name[i] == '\0')
                break;
            sb.append(name[i]);
        }
        return sb.toString();
    }

    void init(int N, int mSoldier[][], char mMonarch[][][]) {
        this.N = N;
        name2Id = new HashMap<>();
        soldiers = new int[N * N];
        mons = new Node[N * N];
        allyList = new ArrayList<>();
        group = new HashMap<>();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int id = i * N + j; // 좌표 압축
                soldiers[id] = mSoldier[i][j];
                String name = char2String(mMonarch[i][j]);
                name2Id.put(name, id);
                mons[id] = new Node(id, 0, id);
                allyList.add(new Ally(id));
            }
        }
    }

    boolean isNear(int a, int b) { // a가 b 근처 8칸에 있는지 판별

        int aQuot = a / N;
        int bQuot = b / N;
        int aMod = a % N;
        int bMod = b % N;

        if (Math.abs(aQuot - bQuot) == 1 && aMod == bMod) // x좌표가 같고 y좌표가 1 차이 => 상하 인접
            return true;


        if (Math.abs(aMod - bMod) == 1 && aQuot == bQuot) // y좌표가 같고 x좌표가 1차이 => 좌우 인접
            return true;

        if (Math.abs(aQuot - bQuot) == 1 && Math.abs(aMod - bMod) == 1) // x, y좌표 모두 1 차이 => 대각선 인접
            return true;

        return false;
    }

    boolean isSame(Ally a, Ally b){
        Node aFirst = a.getFirst();
        Node bFirst = b.getFirst();

        return aFirst == bFirst;
    }

    void destroy() {

    }

    int ally(char mMonarchA[], char mMonarchB[]) {
        String aName = char2String(mMonarchA);
        String bName = char2String(mMonarchB);
        if (aName.equals(bName)) return -1; // 같은 영주라면 -1

        int aId = name2Id.get(aName);
        int bId = name2Id.get(bName);
        int aAllyIdx = mons[aId].allyIdx; // 실제 ally 객체에 접근할 수 있는 idx
        int bAllyIdx = mons[bId].allyIdx;
        Ally aAlly = allyList.get(aAllyIdx); // 속해있는 ally 객체
        Ally bAlly = allyList.get(bAllyIdx);

        if (isSame(aAlly, bAlly)) {
            return -1;
        }

        if (aAlly.isEnemy(bAlly.id) || bAlly.isEnemy(aAlly.id)) {
            return -2;
        }

        aAlly.union(bAlly);
        return 1;
    }

    int attack(char mMonarchA[], char mMonarchB[], char mGeneral[]) {
        int aId = name2Id.get(char2String(mMonarchA));
        int bId = name2Id.get(char2String(mMonarchB));
        Ally aAlly = allyList.get(mons[aId].allyIdx);
        Ally bAlly = allyList.get(mons[bId].allyIdx);
        if (aAlly.id == bAlly.id)
            return -1;

        Node curNode = aAlly.getFirst();
        int atkSldr = 0;
        while (curNode != null) { // 공격 영주와 그 동맹이 방어영주 근처에 있는지 판단 후 인접해있다면 절반을 공격에 보탬
            if (!isNear(curNode.id, bId)) continue;

            int atkSum = soldiers[curNode.id] / 2;
            atkSldr += atkSum;
            soldiers[curNode.id] -= atkSum;
            curNode = curNode.next; // 다음 영주로 이동
        }

        if (atkSldr == 0) { // 공격 병사 = 0 => 인접 영주 없음
            return -2;
        }

        int defSldr = soldiers[bId];
        Node defCurNode = bAlly.getFirst();
        while (defCurNode != null) {
            if (!isNear(defCurNode.id, bId) || defCurNode.id == bId) continue; // 근처에 없거나 b영주라면 무시

            int defSum = soldiers[defCurNode.id] / 2;
            defSldr += defSum;
            soldiers[defCurNode.id] -= defSum;
            defCurNode = defCurNode.next; // 다음 영주로 이동
        }

        int leftSoldier = Math.abs(atkSldr - defSldr);
        int res = 0;
        if (atkSldr > defSldr) { // 공격 승리
            res = 1;
            String genName = char2String(mGeneral);
            name2Id.put(genName, bId);
            soldiers[bId] = leftSoldier;
            Node genToMon = mons[bId];
            // 두 가지 경우(삭제되는 영주가 bAlly의 첫 번째, 마지막 노드인 경우 노드 변경)
            if (genToMon == bAlly.first) { // 맨 앞 노드인 경우, 현재 노드의 다음 노드가 맨 앞 노드가 됨
                bAlly.first = genToMon.next;
            } else if (genToMon == bAlly.last) { // 맨 뒤 노드인 경우, 현재 노드의 이전 노드가 맨 뒤 노드가 됨
                bAlly.last = genToMon.prev;
            }

            // b영주의 노드를 bAlly에서 연결 해제 -> 앞 뒤 노드를 연결해줌
            genToMon.allyIdx = mons[aId].allyIdx; // allyIdx 변경
            genToMon.prev.next = genToMon.next; // 앞 뒤 노드 연결
            genToMon.next.prev = genToMon.prev;


            // b영주의 노드를 aAlly의 끝에 추가
            genToMon.next = null; // 현재 노드의 앞 뒤를 각각 aAlly의 마지막, null 노드로 변경
            genToMon.prev = aAlly.last;
            aAlly.last = genToMon; // 현재 노드를 aAlly의 마지막 노드로 설정
            aAlly.cnt++; // aAlly에 1명 추가
            bAlly.cnt--; // bAlly에 1명 제거
            name2Id.remove(char2String(mMonarchB));
        }

        // 기존의 a, b동맹 사이에 적대관계 형성
        aAlly.addToEnemy(bAlly.id);
        bAlly.addToEnemy(aAlly.id);
        return res;
    }

    int recruit(char mMonarch[], int mNum, int mOption) {
        String name = char2String(mMonarch);
        int id = name2Id.get(name);
        int res = 0;
        if (mOption == 0) {
            soldiers[id] += mNum;
            res = soldiers[id];
        } else {
            Ally ally = allyList.get(mons[id].allyIdx);

            Node curNode = ally.getFirst();
            for (int i = 0; i < ally.cnt; i++) {
                soldiers[curNode.id] += mNum;
                res += soldiers[curNode.id];
                curNode = curNode.next;
            }
        }
        return res;
    }
}