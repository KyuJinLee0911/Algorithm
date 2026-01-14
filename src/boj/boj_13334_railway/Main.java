package boj.boj_13334_railway;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static class Person {
        int min, max;
        int distance;
        public Person(int pos1, int pos2){
            this.max = Integer.max(pos1, pos2);
            this.min = Integer.min(pos1, pos2);
            distance = max - min;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        StringTokenizer st;
        List<Person> list = new ArrayList<>();
        for(int i = 0; i < n; i++){
            st = new StringTokenizer(br.readLine());
            int home = Integer.parseInt(st.nextToken());
            int office = Integer.parseInt(st.nextToken());
            list.add(new Person(home, office));
        }
        Collections.sort(list, Comparator.comparingInt(p -> p.max));

        int d = Integer.parseInt(br.readLine());
        int maxSize = 0;
        for(Person p : list){
            if(p.distance > d) continue;

            pq.add(p.min);
            int start = p.max - d;
            while(!pq.isEmpty() && pq.peek() < start){
                pq.poll();
            }
            maxSize = Integer.max(maxSize, pq.size());
        }

        System.out.println(maxSize);
    }
}
