package boj.boj_20149_intersect3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.StringTokenizer;

public class Main {
    static class Point{
        int x, y;
        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            Point other = (Point) obj;
            return x == other.x && y == other.y;
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
        boolean intersect = isIntersect(points[0], points[1], points[2], points[3]);
        int i = intersect ? 1 : 0;
        System.out.println(i);
        if(intersect){
            BigDecimal x, y;
            if(keepIntersecting(points[0], points[1], points[2], points[3])){
                return;
            } else {
                x = getIntersectX(points[0], points[1], points[2], points[3]);
                y = getIntersectY(points[0], points[1], points[2], points[3]);
            }

            System.out.println(x + " " + y);
        }
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

    private static BigDecimal getIntersectX(Point a, Point b, Point c, Point d){
        int ccw1 = ccw(a, b, c) * ccw(a, b, d);
        int ccw2 = ccw(c, d, a) * ccw(c, d, b);
        if(ccw1 == 0 && ccw2 == 0){
            if(a.equals(c) || a.equals(d)){
                return new BigDecimal(a.x);
            } else if(b.equals(c) || b.equals(d)){
                return new BigDecimal(b.x);
            }
        }
        BigDecimal ax = new BigDecimal(a.x);
        BigDecimal ay = new BigDecimal(a.y);
        BigDecimal bx = new BigDecimal(b.x);
        BigDecimal by = new BigDecimal(b.y);
        BigDecimal cx = new BigDecimal(c.x);
        BigDecimal cy = new BigDecimal(c.y);
        BigDecimal dx = new BigDecimal(d.x);
        BigDecimal dy = new BigDecimal(d.y);

        BigDecimal term1 = ax.multiply(by).subtract(ay.multiply(bx))
                .multiply(cx.subtract(dx));
        BigDecimal term2 = ax.subtract(bx)
                .multiply(cx.multiply(dy).subtract(cy.multiply(dx)));
        BigDecimal dom = term1.subtract(term2);
        BigDecimal num = ax.subtract(bx).multiply(cy.subtract(dy))
                .subtract(ay.subtract(by).multiply(cx.subtract(dx)));

        return dom.divide(num, 10, RoundingMode.HALF_UP);
    }

    private static BigDecimal getIntersectY(Point a, Point b, Point c, Point d){
        int ccw1 = ccw(a, b, c) * ccw(a, b, d);
        int ccw2 = ccw(c, d, a) * ccw(c, d, b);
        if(ccw1 == 0 && ccw2 == 0){
            if(a.equals(c) || a.equals(d)){
                return new BigDecimal(a.y);
            } else if(b.equals(c) || b.equals(d)){
                return new BigDecimal(b.y);
            }
        }

        BigDecimal ax = new BigDecimal(a.x);
        BigDecimal ay = new BigDecimal(a.y);
        BigDecimal bx = new BigDecimal(b.x);
        BigDecimal by = new BigDecimal(b.y);
        BigDecimal cx = new BigDecimal(c.x);
        BigDecimal cy = new BigDecimal(c.y);
        BigDecimal dx = new BigDecimal(d.x);
        BigDecimal dy = new BigDecimal(d.y);

        BigDecimal term1 = ax.multiply(by).subtract(ay.multiply(bx)).multiply(cy.subtract(dy));
        BigDecimal term2 = ay.subtract(by).multiply(cx.multiply(dy).subtract(cy.multiply(dx)));
        BigDecimal dom = term1.subtract(term2);
        BigDecimal num = ax.subtract(bx).multiply(cy.subtract(dy))
                .subtract(ay.subtract(by).multiply(cx.subtract(dx)));

        return dom.divide(num, 10, RoundingMode.HALF_UP);
    }

    private static boolean keepIntersecting(Point a, Point b, Point c, Point d){
        int ccw1 = ccw(a, b, c) * ccw(a, b, d);
        int ccw2 = ccw(c, d, a) * ccw(c, d, b);
        if(ccw1 == 0 && ccw2 == 0){
            // 두 직선이 확실히 같은 선 상에 있는지 판별
            if(ccw(a, b, c) == 0 && ccw(a, b, d) == 0){
                int minAB_x = Math.min(a.x, b.x);
                int maxAB_x = Math.max(a.x, b.x);
                int minAB_y = Math.min(a.y, b.y);
                int maxAB_y = Math.max(a.y, b.y);
                int minCD_x = Math.min(c.x, d.x);
                int maxCD_x = Math.max(c.x, d.x);
                int minCD_y = Math.min(c.y, d.y);
                int maxCD_y = Math.max(c.y, d.y);

                boolean overlap_x = (minAB_x <= maxCD_x) && (minCD_x <= maxAB_x);
                boolean overlap_y = (minAB_y <= maxCD_y) && (minCD_y <= maxAB_y);

                if(overlap_x && overlap_y){
                    int overlapMinX = Math.max(minAB_x, minCD_x);
                    int overlapMaxX = Math.min(maxAB_x, maxCD_x);
                    int overlapMinY = Math.max(minAB_y, minCD_y);
                    int overlapMaxY = Math.min(maxAB_y, maxCD_y);

                    boolean onePointX = overlapMinX == overlapMaxX;
                    boolean onePointY = overlapMinY == overlapMaxY;

                    if(onePointX && onePointY){
                        return false;
                    } else {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
