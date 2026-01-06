package boj.boj_9328_keys;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        int[][] moveTo = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        for(int tc = 0; tc < T; tc++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int h = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            char[][] map = new char[h][w];
            boolean[] keys = new boolean[26];
            int cnt = 0;
            boolean[][] visited = new boolean[h][w];
            Deque<Integer> pq = new ArrayDeque<>();
            Queue<Integer>[] waiting = new ArrayDeque[26];
            for(int i = 0; i < h; i++){
                String str = br.readLine();
                for(int j = 0; j < w; j++){
                    map[i][j] = str.charAt(j);
                }
            }

            for(int i = 0; i < 26; i++){
                waiting[i] = new ArrayDeque<>();
            }
            String having = br.readLine();
            if(!having.equals("0")) {
                for (int i = 0; i < having.length(); i++) {
                    char key = having.charAt(i);
                    int keyIdx = key - 'a';
                    keys[keyIdx] = true;
                }
            }

            for(int y = 0; y < h; y++){
                for(int x = 0; x < w; x++){
                    if(y != 0 && y != h - 1 && x != 0 && x != w - 1) continue;
                    char ch = map[y][x];
                    if(ch == '*') continue;
                    if(ch >= 'A' && ch <= 'Z' && !keys[ch - 'A']) {
                        waiting[ch - 'A'].add(y * w + x);
                        continue;
                    }

                    if(ch >= 'a' && ch <= 'z'){
                        keys[ch - 'a'] = true;
                        while(!waiting[ch - 'a'].isEmpty()){
                            pq.addFirst(waiting[ch - 'a'].poll());
                        }
                    } else if(ch == '$'){
                        cnt++;
                        map[y][x] = '.';
                    }
                    pq.add(y * w + x);
                }
            }

            if(pq.isEmpty()){
                System.out.println(0);
                continue;
            }

            int first = pq.pollFirst();
            visited[first/w][first%w] = true;

            pq.add(first);
            while(!pq.isEmpty()){
                int cur = pq.poll();
                int curY = cur / w;
                int curX = cur % w;

                for(int i = 0; i < 4; i++){
                    int nextY = curY + moveTo[i][0];
                    int nextX = curX + moveTo[i][1];
                    if(nextY < 0 || nextY >= h || nextX < 0 || nextX >= w) continue;
                    if(visited[nextY][nextX]) continue;


                    char next = map[nextY][nextX];
                    if(next == '*') continue;
                    if(next >= 'A' && next <= 'Z' && !keys[next - 'A']) {
                        waiting[next - 'A'].add(nextY * w + nextX);
                        continue;
                    }

                    int nextPos = nextY * w + nextX;
                    visited[nextY][nextX] = true;
                    pq.add(nextPos);
                    if(next >= 'a' && next <= 'z') {
                        if(!keys[next - 'a']){
                            keys[next - 'a'] = true;
                            while(!waiting[next - 'a'].isEmpty()){
                                pq.addFirst(waiting[next - 'a'].poll());
                            }
                        }
                    } else if(next == '$'){
                        cnt++;
                        map[nextY][nextX] = '.';
                    }
                }
            }
            System.out.println(cnt);
        }
    }
}
