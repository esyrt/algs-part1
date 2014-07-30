import java.util.TreeSet;
import java.util.Iterator;

public class PointSET {
        private TreeSet<Point2D> pointts;

        public PointSET()                               // construct an empty set of points
        {
                pointts = new TreeSet<Point2D>();

        }
        public boolean isEmpty()                        // is the set empty?
        {
                return pointts.isEmpty();
        }
        public int size()                               // number of points in the set
        {
                return pointts.size();
        }
        public void insert(Point2D p)                   // add the point p to the set (if it is not already in the set)
        {
                if (p == null)
                        throw new java.lang.NullPointerException("input cannot be null");
                pointts.add(p);
        }
        public boolean contains(Point2D p)              // does the set contain the point p?
        {
                return pointts.contains(p);
        }
        public void draw()                              // draw all of the points to standard draw
        {
                Iterator it = pointts.iterator(); 
                while (it.hasNext())
                {
                        Point2D p = (Point2D) it.next();
                        p.draw();
                }
        }
        public Iterable<Point2D> range(RectHV rect)     // all points in the set that are inside the rectangle
        {
                Stack<Point2D> pointsk = new Stack<Point2D>();
                Iterator it = pointts.iterator();
                while (it.hasNext())
                {
                        Point2D p = (Point2D) it.next();
                        if (rect.contains(p))
                                pointsk.push(p);
                }
                return pointsk;
        }
        public Point2D nearest(Point2D p)               // a nearest neighbor in the set to p; null if set is empty
        {
                if (pointts.isEmpty())
                        return null;
                double mindist = Double.MAX_VALUE;
                Iterator it = pointts.iterator();
                Point2D minp = null;
                while (it.hasNext())
                {
                        Point2D tp = (Point2D) it.next();
                        if (p.distanceTo(tp) < mindist)
                        {
                                mindist = p.distanceTo(tp);
                                minp = tp;
                        }
                }
                return minp;
        }
}

