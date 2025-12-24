package swea.pro.pro_protecting_island;

import java.util.*;

class Pair{
	int i, j;
	public Pair(int i, int j){
		this.i = i;
		this.j = j;
	}
}
class UserSolution
{
	class Data{
		int i, j, dir;
		public Data(int i, int j, int dir){
			this.i = i;
			this.j = j;
			this.dir = dir; // 0 - 우, 1 - 하, 2 - 좌, 3 - 상
		}
	}
	int[][] dif = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
	Map<Integer, Set<Data>> hashingMap;
	int[][] map;
	int N;
	public void init(int N, int mMap[][])
	{
		this.N = N;
		map = new int[N][N];
		hashingMap = new HashMap<>();
		for(int i = 0; i < N; i++){
			for(int j = 0; j < N; j++){
				map[i][j] = mMap[i][j];
				for(int k = 2; k <= 5; k++){
					for(int dir = 0; dir < 4; dir++){ // 네 방향

						int[] pattern = new int[k];
						boolean isOutOfRange = false;

						for(int d = 0; d < k; d++){ // 탐색 거리
							int newI = i + dif[dir][0] * d;
							int newJ = j + dif[dir][1] * d;

							if(newI < 0 || newI >= N || newJ < 0 || newJ >= N) {
								isOutOfRange = true;
								break;
							}

							pattern[d] = mMap[newI][newJ];
						}

						if(isOutOfRange) continue; // 진행 도중 범위를 벗어났으면 해싱 없이 무시

						int hashKey = hashing(pattern, k);
						hashingMap.putIfAbsent(hashKey, new HashSet<>());
						Set<Data> temp = hashingMap.get(hashKey);

						temp.add(new Data(i, j, dir));
					}
				}
			}
		}
	}

	public int numberOfCandidate(int M, int mStructure[])
	{
		if(M == 1) return 36;
		int hashingKey = structureHashing(mStructure, M);
		int res = hashingMap.getOrDefault(hashingKey, new HashSet<>()).size();
		// 패턴의 대칭성 판단
		boolean isSymmetry = true;
		if(M % 2 == 0){
			for(int m = 0; m < M / 2; m++){
				int left = M / 2 - (m + 1);
				int right = M / 2 + m;
				if(mStructure[left] != mStructure[right]){
					isSymmetry = false;
					break;
				}
			}
		} else {
			for(int m = 1; m <= M / 2; m++){
				int left = M / 2 - m;
				int right = M / 2 + m;
				if(mStructure[left] != mStructure[right]){
					isSymmetry = false;
					break;
				}
			}
		}
		return isSymmetry ? res / 2 : res;
	}

	public int maxArea(int M, int mStructure[], int mSeaLevel)
	{
		int key = structureHashing(mStructure, M);
		Set<Data> locations = hashingMap.get(key);
		int res = 0;
		if(M == 1){
			for(int i = 0; i < N; i++){
				for(int j = 0; j < N; j++){
					locations.add(new Data(i, j, 0));
				}
			}
		}
		if(locations == null){
			return -1;
		}
		for(Data data : locations){
			int[][] copyMap = new int[N][N];
			boolean[][] visited = new boolean[N][N];
			for(int i = 0; i < N; i++){
				for(int j = 0; j < N; j++){
					copyMap[i][j] = map[i][j];
				}
			}

			for(int d = 0; d < M; d++){
				int newI = data.i + dif[data.dir][0] * d;
				int newJ = data.j + dif[data.dir][1] * d;

				copyMap[newI][newJ] += mStructure[d];
			}
			int underWaterCnt = 0;
			for(int i = 0; i < N; i++){
				for(int j = 0; j < N; j++){
					if(copyMap[i][j] < 0) continue;
					if(copyMap[i][j] >= mSeaLevel) continue;
					if(i != 0 || i != N - 1){
						if(j != 0 && j != N - 1)
							continue;
					}
					Queue<Pair> q = new ArrayDeque<>();
					if(copyMap[i][j] < mSeaLevel){
						q.add(new Pair(i, j));
					}

					while(!q.isEmpty()){
						Pair p = q.poll();
						copyMap[p.i][p.j] -= mSeaLevel;


						for(int d = 0; d < 4; d++){
							int newI = p.i + dif[d][0];
							int newJ = p.j + dif[d][1];

							if(newI < 0 || newI >= N || newJ < 0 || newJ >= N) continue;

							if(copyMap[newI][newJ] < mSeaLevel && !visited[newI][newJ]){
								q.add(new Pair(newI, newJ));
								visited[newI][newJ] = true;
								underWaterCnt++;
							}
						}
					}
				}
			}

			res = Math.max(N * N - underWaterCnt, res);
		}
		return res;
	}

	public int structureHashing(int[] value, int M){
		int res = 0;
		for(int i = 1; i < M; i++){
			int newVal = 5 + (value[i - 1] - value[i]);
			res *= 10;
			res += newVal;
		}
		return res;
	}

	public int hashing(int[] value, int M){
		int res = 0;
		for(int i = 1; i < M; i++){
			int newVal = 5 + (value[i] - value[i - 1]);
			res *= 10;
			res += newVal;
		}
		return res;
	}
}