package swea.pro.pro_AI_robot;

// 아이디어 - 지능 지수를 원래 수로 가지고 있지 말고, 상대적인 지능으로 가지고 있는다
// 즉, 센터에 계속 있었던 로봇 대비 나간 로봇이 지능 지수가 얼마나 떨어지는지 기록하고, 쿼리 시 현재 시간을 더하면 실제 지능이 된다
// 상대 지능 계산법 = 현재 지능 - (들어온 시간(현재 시간) - 나간 시간)
// 센터에 계속 있던 로봇들의 지능을 계속 증가시키는게 아니라, 센터에 계속 있던 로봇들의 지능은 변함이 없고, 일을 나간 로봇들의 지능이 나가있던 만큼 감소하는 것.
// 또한, 수리가 완료된 로봇은 그 시점에 지능이 0이 된다 => 시간이 0일때 나갔다가 수리가 완료된 시점에 들어온 것과 같음 (왜? 현재 시간 기준 지능이 0이라는건, 센터에 계속 있던 로봇 대비 지능이 -현재시간 만큼 낮다는 뜻이므로)
// 0시에 나갔다가 20시에 들어온 로봇의 지능 = -20


class Robot {
    int id, iq, wId, begin, maxIdx, minIdx;
    boolean broken;

    public Robot(int id, int iq, int cTime) {
        this.id = id;
        this.iq = iq;
        wId = 0;
        this.begin = cTime;
        broken = false;
    }
}

class Job {
    Robot[] occupied;
    int cnt;

    public Job() {
        cnt = 0;
        occupied = new Robot[0];
    }
}

interface RobotComparator {
    void set(Robot a, int idx);

    int compare(Robot a, Robot b);
}

class MaxComp implements RobotComparator {
    @Override
    public void set(Robot a, int idx) {
        a.maxIdx = idx;
    }

    @Override
    public int compare(Robot o1, Robot o2) {
        return o1.iq == o2.iq ? Integer.compare(o1.id, o2.id) : Integer.compare(o2.iq, o1.iq);
    }
}

class MinComp implements RobotComparator {
    @Override
    public void set(Robot a, int idx) {
        a.minIdx = idx;
    }

    @Override
    public int compare(Robot o1, Robot o2) {
        return o1.iq == o2.iq ? Integer.compare(o1.id, o2.id) : Integer.compare(o1.iq, o2.iq);
    }
}

class Que<C extends RobotComparator> {
    private static final int MAXN = 100_001; // 2 * 50_000 + 1;

    Robot[] arr = new Robot[MAXN];
    int size;
    C comp;

    public void init(C comp) {
        size = 0;
        this.comp = comp;
    }

    public void init(int n, C comp, Robot[] heapified) {
        this.comp = comp;
        for (int i = 0; i < n; ++i) {
            arr[i] = heapified[i + 1];
            this.comp.set(arr[i], i);
        }

        size = n;
    }

    public void swap(Robot[] arr, int i, int j) {
        Robot temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public void push(Robot v) {
        arr[size] = v;
        comp.set(arr[size], size);
        int cur = size++;

        while (cur > 0) {
            int par = (cur - 1) / 2;
            if (comp.compare(arr[cur], arr[par]) <= 0) {
                break;
            }
            swap(arr, cur, par);
            comp.set(arr[cur], par);
            comp.set(arr[par], cur);
            cur = par;
        }
    }

    public Robot pop() {
        if (size == 0)
            return null;

        // 루트에서 가장 먼 리프 노드의 값을 임시 루트로 올림
        Robot ret = arr[0];
        arr[0] = arr[--size];
        comp.set(arr[0], 0);

        // 임시 루트노트가 양 자식들 중 더 우선순위가 높은 자식과 비교
        int cur = 0;
        while (cur * 2 + 1 < size) {
            int child = cur * 2 + 2 < size && comp.compare(arr[cur * 2 + 1], arr[cur * 2 + 2]) < 0 ? cur * 2 + 2 : cur * 2 + 1;

            // 자식 노드보다 우선순위가 높을 경우 break;
            if (comp.compare(arr[child], arr[cur]) <= 0)
                break;

            // 자식보다 우선순위가 더 낮을 경우 자리 교체
            swap(arr, cur, child);
            comp.set(arr[cur], cur);
            comp.set(arr[child], child);
            cur = child;
        }

        return ret;
    }

    public void remove(int idx) {
        if (idx >= size)
            return;

        // 삭제하고자 하는 위치에 리프 노드를 덮어씌움
        arr[idx] = arr[--size];
        comp.set(arr[size], idx);

        int cur = idx;

        // 덮어씌운 노드를 현 위치에서 자식과 비교하면서 위치 변경
        while (cur * 2 + 1 < size) {
            int child = cur * 2 + 2 < size && comp.compare(arr[cur * 2 + 1], arr[cur * 2 + 2]) <= 0 ?
                    cur * 2 + 2 : cur * 2 + 1;
            if (comp.compare(arr[cur], arr[child]) <= 0) {
                break;
            }
            swap(arr, cur, child);
            comp.set(arr[cur], child);
            comp.set(arr[child], child);
            cur = child;
        }

		// 다시 부모 노드로 타고 가면서 순서 바뀐거 없는지 판별
        while(cur > 0){
            int par = (cur - 1) / 2;
            if(comp.compare(arr[cur], arr[par]) <= 0)
                break;
            swap(arr, cur, par);
            comp.set(arr[cur], par);
            comp.set(arr[par], cur);
            cur = par;
        }
    }

}

class UserSolution {
    final int MAXN = 50001;
    final int MAXM = 50001;
    final int MAXK = 200001;

    Que<MaxComp> maxQue;
    Que<MinComp> minQue;
    Robot[] robots;
    Job[] jobs;

    public void init(int N) {
        robots = new Robot[MAXN];
        jobs = new Job[MAXM];
        maxQue = new Que<>();
        minQue = new Que<>();
        for(int i = 1; i <= N; i++){
            Robot r = new Robot(i, 0, 0);
            robots[i] = r;
        }

        for(int i = 0; i < MAXM; i++){
            jobs[i] = new Job();
        }

        maxQue.init(N, new MaxComp(), robots);
        minQue.init(N, new MinComp(), robots);
    }

    public int callJob(int cTime, int wID, int mNum, int mOpt) {
        jobs[wID].occupied = new Robot[mNum];
        jobs[wID].cnt = mNum;

        int ret = 0;
        if(mOpt == 0){
            int cnt = 0;
            while(cnt < mNum){
                Robot p = maxQue.pop();

                p.wId = wID;
                p.iq += cTime - p.begin;
                p.begin = cTime;

                jobs[wID].occupied[cnt++] = p;

                minQue.remove(p.minIdx);
                ret += p.id;
            }
        } else {
            int cnt = 0;
            while(cnt < mNum){
                Robot r = minQue.pop();
                r.wId = wID;
                r.iq += cTime - r.begin;
                r.begin = cTime;


                jobs[wID].occupied[cnt++] = r;

                maxQue.remove(r.maxIdx);
                ret += r.id;
            }
        }
        return ret;
    }

    public void returnJob(int cTime, int wID) {
        int n = jobs[wID].cnt;
        for(int i = 0; i < n; i++){
            Robot r = jobs[wID].occupied[i];
            if(r.broken || r.wId != wID){
                continue;
            }

            r.wId = 0;

            maxQue.push(r);
            minQue.push(r);
        }
    }

    public void broken(int cTime, int rID) {
        if(robots[rID].wId != 0){
            robots[rID].wId = 0;
            robots[rID].broken = true;
        }
    }

    public void repair(int cTime, int rID) {
        if(robots[rID].broken){
            robots[rID].broken = false;
            robots[rID].iq = 0;
            robots[rID].begin = cTime;

            maxQue.push(robots[rID]);
            minQue.push(robots[rID]);
        }
    }

    public int check(int cTime, int rID) {
        if(robots[rID].broken){
            return 0;
        } else if(robots[rID].wId != 0){
            return robots[rID].wId * -1;
        } else {
            return robots[rID].iq;
        }
    }
}