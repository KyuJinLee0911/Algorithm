package boj.boj_2467_solution;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int left = 0;
        int right = n - 1;
        int[] solutions = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 0; i < n; i++){
            solutions[i] = Integer.parseInt(st.nextToken());
        }
        int minPh = Integer.MAX_VALUE;
        int[] used = new int[2];
        while(left < right){
            int sum = solutions[left] + solutions[right];

            if(Math.abs(sum) < Math.abs(minPh)){
                minPh = sum;
                used[0] = solutions[left];
                used[1] = solutions[right];
            }

            if(sum > 0){
                right--;
            } else if(sum < 0){
                left++;
            } else {
                break;
            }
        }

        System.out.println(used[0] + " " + used[1]);
    }
}

// 전체를 다 더하는건 시간초과 100,000 * 100,000 = 10,000,000,000 = 10억 = 10초
// 맨 왼쪽을 기준으로 맨 오른쪽과의 합이 가장 작은 수
// 맨 오른쪽을 기준으로 맨 왼쪽과의 합이 가장 작은 수 => 둘 다 양수일 경우에만 가능한 얘기
// 두 수의 합이 음수라면 = 오른쪽을 계속 줄여나가도 맨 오른쪽이 가장 절댓값이 작음 => 왼쪽 증가시켜도 됨
// 두 수의 합이 양수라면 = 적어도 음수가 되는 지점까지는 비교해 봐야 함, 음수가 되는 지점에서의 가장 작은 절댓값이 가장 작은 값
// 즉, 두 수의 합이 양수라면 음수가 되는 지점까지 왼쪽을 줄여나가야 함

// 그 다음은?
// -99 -2 -1 4 98 104
// -99 104
// -99 98
// -2 98 // 이게 가능한 이유? => 왼쪽 수가 더 작은 수이기 때문에 어쩄든 두 수의 합이 더 클 수 밖에 없음
// -2 4
// -2 -1

// 극단적으로, 모두 양수 or 모두 음수라면? => 오른쪽 or 왼쪽만 바뀜
// 1 2 3 4 5 6

// -2 -1 1 3 4
// -2 4
// -2 3
// -2 1
// -1 1
