package swea.kth_suffix;

import java.io.*;

class TrieNodeS {
    TrieNode[] children = new TrieNode[26];
    boolean isEnd;

    TrieNodeS(){
        isEnd = false;
        for(int i = 0; i < 26; i++){
            children[i] = null;
        }
    }
}

class TrieSecond {
    TrieNode root;

    TrieSecond(){
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

//    String findKthSuffix(int k){
//
//    }


}

public class Solution_Trie {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        int t = Integer.parseInt(br.readLine());
        for(int i = 1; i <= t; i++){
            int k = Integer.parseInt(br.readLine());

            String str = br.readLine();
            Trie trie = new Trie();
            trie.insertWord(str);

            sb.append("#").append(i).append(" ");
            if(k < str.length()){
                sb.append("").append("\n");
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
