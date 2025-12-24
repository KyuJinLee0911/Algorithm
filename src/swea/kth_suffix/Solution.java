package swea.kth_suffix;

import java.io.*;
import java.util.*;

class TrieNode {
    TrieNode[] children = new TrieNode[26];
    boolean isEnd;

    TrieNode(){
        isEnd = false;
        for(int i = 0; i < 26; i++){
            children[i] = null;
        }
    }
}

class Trie {
    TrieNode root;

    Trie(){
        root = new TrieNode();
    }

    void insertWord(String s){
        TrieNode t = root;
        for(int i = 0; i < s.length(); i++){
            int index = s.charAt(i) - 'a';
            if(t.children[index] == null){
                t.children[index] = new TrieNode();
            }
            t = t.children[index];
        }
        t.isEnd = true;
    }

    String[] getAllSuffix(String s){
        TrieNode t = root;
        StringBuilder sb = new StringBuilder();
        String[] suffixes = new String[s.length()];
        for(int i = 0; i < s.length(); i++){
            int index = s.charAt(i) - 'a';
            sb.append(s.charAt(i));
            StringBuffer strBuf = new StringBuffer(sb.toString());
            suffixes[i] = strBuf.reverse().toString();
            t = t.children[index];
        }

        return suffixes;
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        int t = Integer.parseInt(br.readLine());
        for(int i = 1; i <= t; i++){
            int k = Integer.parseInt(br.readLine());
            StringBuffer strBuf = new StringBuffer(br.readLine());
            String str = strBuf.reverse().toString();
            Trie trie = new Trie();
            trie.insertWord(str);
            String[] suffixes = trie.getAllSuffix(str);
            Arrays.sort(suffixes);

            sb.append("#").append(i).append(" ");
            if(k < str.length()){
                sb.append(suffixes[k - 1]).append("\n");
            } else {
                sb.append("none");
            }
        }
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}
