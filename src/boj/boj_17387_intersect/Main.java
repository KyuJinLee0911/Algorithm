package boj.boj_17387_intersect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static class Point{
        int x, y;
        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        Point[] points = new Point[4];
        int idx = 0;
        for(int i = 0; i < 2; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x1 = Integer.parseInt(st.nextToken());
            int y1 = Integer.parseInt(st.nextToken());
            int x2 = Integer.parseInt(st.nextToken());
            int y2 = Integer.parseInt(st.nextToken());
            points[idx++] = new Point(x1, y1);
            points[idx++] = new Point(x2, y2);
        }

        int i = isIntersect(points[0], points[1], points[2], points[3]) ? 1 : 0;
        System.out.println(i);
    }

    private static int ccw(Point a, Point b, Point c){
        long ccw = (long) (b.x - a.x) *(c.y - a.y) - (long) (b.y - a.y) * (c.x - a.x);

        if(ccw > 0) return 1;
        else if(ccw < 0) return -1;
        else return 0;
    }

    private static boolean isIntersect(Point a, Point b, Point c, Point d){
        int ccw1 = ccw(a, b, c) * ccw(a, b, d);
        int ccw2 = ccw(c, d, a) * ccw(c, d, b);

        if(ccw1 <= 0 && ccw2 <= 0){
            if(ccw1 == 0 && ccw2 == 0){
                return Math.min(a.x, b.x) <= Math.max(c.x, d.x) &&
                        Math.min(c.x, d.x) <= Math.max(a.x, b.x) &&
                        Math.min(a.y, b.y) <= Math.max(c.y, d.y) &&
                        Math.min(c.y, d.y) <= Math.max(a.y, b.y);
            }
            return true;
        }
        return false;
    }
}
