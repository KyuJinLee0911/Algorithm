package boj.boj_17143_fishing_king;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[][] board; // 1-based indexing;
    static int[][] dir = {{0, 0}, {-1, 0}, {1, 0}, {0, 1}, {0, -1}};
    static int[][] sharks;
    static int[] speed;
    static int[] size;
    static int[] direction;
    static boolean[] dead;
    static int r, c;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        board = new int [r + 1][c + 1];
        sharks = new int[m + 1][2];
        speed = new int[m + 1];
        size = new int[m + 1];
        direction = new int[m + 1];
        dead = new boolean[m + 1];
        for(int i = 1; i <= m; i++){
            st = new StringTokenizer(br.readLine());
            int row = Integer.parseInt(st.nextToken());
            int col = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken()); // 속도
            int d = Integer.parseInt(st.nextToken()); // 1, 2, 3, 4 순서대로 상하좌우
            int z = Integer.parseInt(st.nextToken()); // 크기
            sharks[i][0] = row;
            sharks[i][1] = col;
            speed[i] = s;
            direction[i] = d;
            size[i] = z;
            board[row][col] = i;
        }

        boolean[] caught = new boolean[m + 1];

        int king = 0;
        while(king < c){
            // 한 칸 이동
            king++;

            // 가장 가까운 상어 잡기
            int minDist = 201;
            int minIdx = -1;
            for(int i = 1; i <= r; i++){
                if(board[i][king] == 0) continue;

                int id = board[i][king];
                if(caught[id]) continue;
                if(dead[id]) continue;

                int sr = sharks[id][0];
                int sc = sharks[id][1];
                int dist = getDistance(king, sr, sc);
                if(dist < minDist){
                    minDist = dist;
                    minIdx = id;
                }
            }
            if(minIdx != -1){
                caught[minIdx] = true;
                int caughtRow = sharks[minIdx][0];
                int caughtCol = sharks[minIdx][1];
                board[caughtRow][caughtCol] = 0;
            }


            // 상어 이동
            for(int i = 1; i <= m; i++){
                if(caught[i]) continue;
                if(dead[i]) continue;
                sharkMoveCheck(i);
            }
        }

        int ans = 0;
        for(int i = 1; i <= m; i++){
            if(!caught[i]) continue;

            ans += size[i];
        }
        System.out.println(ans);
    }

    private static int getDistance(int king, int sr, int sc) {
        return sr + Math.abs(sc - king);
    }

    private static void sharkMoveCheck(int id){
        int[] changeDir = {0, 2, 1, 4, 3};

        int cr = sharks[id][0];
        int cc = sharks[id][1];
        int d = direction[id];
        int s = speed[id];
        int nr = cr;
        int nc = cc;

        if(s == 0){
            sharkMove(id, nr, nc, cr, cc);
            return;
        }

        int distToBorder = -1;
        int turnCount = 0;
        int lastMove = 0;
        switch(d){
            case 1:
                distToBorder = cr - 1;
                if(distToBorder < s){ // s는 총 이동거리, 총 이동거리보다 경계까지의 거리가 작으면 방향 전환이 일어남
                    turnCount++;
                    s -= distToBorder;
                    turnCount += s / (r - 1);
                    lastMove = s % (r - 1); // turnCount만큼 방향 전환을 하고, 경계에서 마지막으로 이동하는 거리
                    if(turnCount % 2 != 0){ // 홀수 번의 방향 전환 = 방향이 반대가 됨
                        d = changeDir[d];
                        direction[id] = d;
                        nr = 1 + lastMove; // 위쪽 방향으로 가던 상태에서 홀수 번의 방향 전환이 일어나면 0 + last가 최종 위치
                    } else {
                        nr = (r) - lastMove;
                    }
                } else { // 총 이동 거리가 경계까지의 거리보다 작으면
                    nr = cr + s * dir[d][0]; // 바라보는 방향으로 이동
                }
                break;
            case 2:
                distToBorder = r - cr;
                if(distToBorder < s){
                    turnCount++;
                    s -= distToBorder;
                    turnCount += s / (r - 1);
                    lastMove = s % (r - 1);
                    if(turnCount % 2 != 0){ // 홀수 번의 방향 전환 = 방향이 반대가 됨
                        d = changeDir[d];
                        direction[id] = d;
                        nr = (r) - lastMove; // 아래 방향으로 가던 상태에서 홀수 번의 방향 전환이 일어나면 r - last가 최종 위치
                    } else {
                        nr = 1 + lastMove;
                    }
                } else {
                    nr = cr + s * dir[d][0];
                }
                break;
            case 3:
                distToBorder = c - cc;
                if(distToBorder < s){ // s는 총 이동거리, 총 이동거리보다 경계까지의 거리가 작으면 방향 전환이 일어남
                    turnCount++;
                    s -= distToBorder;
                    turnCount += s / (c - 1);
                    lastMove = s % (c - 1); // turnCount만큼 방향 전환을 하고, 경계에서 마지막으로 이동하는 거리
                    if(turnCount % 2 != 0){ // 홀수 번의 방향 전환 = 방향이 반대가 됨
                        d = changeDir[d];
                        direction[id] = d;
                        nc = (c) - lastMove; // 오른쪽 방향으로 가던 상태에서 홀수 번의 방향 전환이 일어나면 0 + last가 최종 위치
                    } else {
                        nc = 1 + lastMove;
                    }
                } else { // 총 이동 거리가 경계까지의 거리보다 작으면
                    nc = cc + s * dir[d][1]; // 바라보는 방향으로 이동
                }
                break;
            case 4:
                distToBorder = cc - 1;
                if(distToBorder < s){ // s는 총 이동거리, 총 이동거리보다 경계까지의 거리가 작으면 방향 전환이 일어남
                    turnCount++;
                    s -= distToBorder;
                    turnCount += s / (c - 1);
                    lastMove = s % (c - 1); // turnCount만큼 방향 전환을 하고, 경계에서 마지막으로 이동하는 거리
                    if(turnCount % 2 != 0){ // 홀수 번의 방향 전환 = 방향이 반대가 됨
                        d = changeDir[d];
                        direction[id] = d;
                        nc = 1 + lastMove; // 왼쪽 방향으로 가던 상태에서 홀수 번의 방향 전환이 일어나면 0 + last가 최종 위치
                    } else {
                        nc = (c) - lastMove;
                    }
                } else { // 총 이동 거리가 경계까지의 거리보다 작으면
                    nc = cc + s * dir[d][1]; // 바라보는 방향으로 이동
                }
                break;
        }

        sharkMove(id, nr, nc, cr, cc);
    }

    private static void sharkMove(int id, int nr, int nc, int cr, int cc){
        int curBoard = board[nr][nc];
        if(curBoard == id){
            return;
        }

        if(board[cr][cc] == id){
            board[cr][cc] = 0;
        }

        if(curBoard == 0){ // 비어있으면 이동
            board[nr][nc] = id;
            sharks[id][0] = nr;
            sharks[id][1] = nc;
        } else {
            if(curBoard < id){ // 이전에 먼저 이동한 상어가 있으면
                int sizePrev = size[curBoard];
                int sizeNext = size[id];
                if(sizeNext > sizePrev){ // 크기 비교 후 작은상어가 잡아먹힘
                    board[nr][nc] = id;
                    sharks[id][0] = nr;
                    sharks[id][1] = nc;
                    dead[curBoard] = true;
                } else if(sizeNext < sizePrev) {
                    dead[id] = true;
                }
            } else {
                board[nr][nc] = id;
                sharks[id][0] = nr;
                sharks[id][1] = nc;
            }
        }
    }
}

// a d f g h
// 0 3 5 6 7