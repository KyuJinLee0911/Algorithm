package boj.boj_9252_LCS2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str1 = br.readLine();
        String str2 = br.readLine();
        int len1 = str1.length();
        int len2 = str2.length();

        int[][] lcsCheck = new int[len1 + 1][len2 + 1];
        for(int i = 1; i <= len1; i++){
            for(int j = 1; j <= len2; j++){
                if(str1.charAt(i - 1) == str2.charAt(j - 1)){
                    lcsCheck[i][j] = lcsCheck[i - 1][j - 1] + 1;
                } else {
                    lcsCheck[i][j] = Math.max(lcsCheck[i][j - 1], lcsCheck[i - 1][j]);
                }
            }
        }

        int lcsLength = lcsCheck[len1][len2];
        int row = len1;
        int col = len2;
        int val = lcsCheck[row][col];
        char[] lcsArray = new char[lcsLength];
        int idx = lcsLength - 1;
        while(val != 0){
            int valLeft = lcsCheck[row][col - 1];
            int valUp = lcsCheck[row - 1][col];

            if(valUp == val){
                row--;
            } else if(valLeft == val){
                col--;
            } else {
                lcsArray[idx] = str1.charAt(row - 1);
                row--;
                col--;
                idx--;
            }

            val = lcsCheck[row][col];
        }

        StringBuilder lcs = new StringBuilder();
        for(int i = 0; i < lcsLength; i++){
            lcs.append(lcsArray[i]);
        }

        System.out.println(lcsLength);
        System.out.println(lcs);
    }
}
