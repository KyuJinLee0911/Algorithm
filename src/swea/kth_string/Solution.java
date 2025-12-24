package swea.kth_string;

import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        int t = Integer.parseInt(br.readLine());
        for(int i = 1; i <= t; i++){
            int k = Integer.parseInt(br.readLine());
            String s = br.readLine();
            sb.append("#").append(i).append(" ");
            int len = s.length();
            Set<String> subStrings = new HashSet<>();
            for(int j = 0; j < len; j++){
                StringBuilder strBuild = new StringBuilder();
                for(int a = j; a < len; a++){
                    strBuild.append(s.charAt(a));
                    subStrings.add(strBuild.toString());
                }
            }
            if(k > subStrings.size()){
                sb.append("none").append("\n");
                continue;
            }
            String[] strs = subStrings.toArray(new String[0]);
            Arrays.sort(strs);
            sb.append(strs[k-1]).append("\n");
        }
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}
