
public class KdTree {

        private static class Node
        {
                private Point2D p;      // the point
                private RectHV rect;    // the axis-aligned rectangle corresponding to this node
                private Node lb;        // the left/bottom subtree
                private Node rt;        // the right/top subtree

                public Node(Point2D p, RectHV rect)
                {
                        RectHV r = rect;
                        if (r == null)
                                r = new RectHV(0, 0, 1, 1);

                        this.rect = r;
                        this.p = p;
                }
        }

        private Node root;

        private int N;

        public KdTree()                               // construct an empty set of points
        {
                root = null;
                N = 0;
        }

        public boolean isEmpty()                        // is the set empty?
        {
                return root == null;
        }

        public int size()                               // number of points in the set
        {
                return N;
        }

        private Node insert(Node x, Point2D p, RectHV rect, boolean orit) // orit==true comp x, orit==false comp y
        {
                if (x == null)
                {
                        N++;
                        return new Node(p, rect);
                }
                
                if (x.p.equals(p))  return x;

                if (orit)
                {
                        int cmp = Point2D.X_ORDER.compare(x.p, p);
                        RectHV rn;

                        if (cmp > 0)
                        {
                                if (x.lb == null)
                                {
                                        rn = new RectHV(rect.xmin(), rect.ymin(), x.p.x(), rect.ymax());
                                }
                                else
                                {
                                        rn = x.lb.rect;
                                }
                                x.lb = insert(x.lb, p, rn, false);
                        }
                        else
                        {
                                if (x.rt == null)
                                {
                                        rn = new RectHV(x.p.x(), rect.ymin(), rect.xmax(), rect.ymax());
                                }
                                else
                                {
                                        rn = x.rt.rect;
                                }
                                x.rt = insert(x.rt, p, rn, false);
                        }
                        return x;
                }
                else
                {
                        int cmp = Point2D.Y_ORDER.compare(x.p, p);
                        RectHV rn;

                        if (cmp > 0)
                        {
                                if (x.lb == null)
                                {
                                        rn = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), x.p.y());
                                }
                                else
                                {
                                        rn = x.lb.rect;
                                }
                                x.lb = insert(x.lb, p, rn, true);
                        }
                        else
                        {
                                if (x.rt == null)
                                {
                                        rn = new RectHV(rect.xmin(), x.p.y(), rect.xmax(), rect.ymax());
                                }
                                else
                                {
                                        rn = x.rt.rect;
                                }
                                x.rt = insert(x.rt, p, rn, true);
                        }
                        return x;
                }
        }
        public void insert(Point2D p)                   // add the point p to the set (if it is not already in the set)
        {
                if (root == null)
                        root = insert(root, p, null, true);
                else
                        root = insert(root, p, root.rect, true);
        }

        private boolean contains(Node x, Point2D p, boolean orit)
        {
                if (x == null) return false;
                if (x.p.equals(p)) return true;

                if (orit)
                {
                        int cmp = Point2D.X_ORDER.compare(x.p, p);

                        if (cmp > 0)
                        {
                                return contains(x.lb, p, false);
                        }
                        else
                        {
                                return contains(x.rt, p, false);
                        }
                }
                else
                {
                        int cmp = Point2D.Y_ORDER.compare(x.p, p);

                        if (cmp > 0)
                        {
                                return contains(x.lb, p, true);
                        }
                        else
                        {
                                return contains(x.rt, p, true);
                        }
                }
        }

        public boolean contains(Point2D p)              // does the set contain the point p?
        {
                return contains(root, p, true);
        }

        private void draw(Node x, boolean orit)
        {
                if (x.lb != null) draw(x.lb, !orit);
                if (x.rt != null) draw(x.rt, !orit);

                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius(.01);
                StdDraw.point(x.p.x(), x.p.y());

                double xmin, xmax, ymin, ymax;
                if (orit)
                {
                        StdDraw.setPenColor(StdDraw.RED);
                        xmin = x.p.x();
                        xmax = x.p.x();
                        ymin = x.rect.ymin();
                        ymax = x.rect.ymax();
                }
                else
                {
                        StdDraw.setPenColor(StdDraw.BLUE);
                        xmin = x.rect.xmin();
                        xmax = x.rect.xmax();
                        ymin = x.p.y();
                        ymax = x.p.y();
                }
                StdDraw.setPenRadius();
                StdDraw.line(xmin, ymin, xmax, ymax);
        }
        public void draw()                              // draw all of the points to standard draw
        {
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.rectangle(.5, .5, .5, .5);
                if (isEmpty()) return;
                draw(root, true);
        }

        private void range(Node x, RectHV rect, Queue<Point2D> pointset)
        {
                if (x == null)
                        return;
                if (!x.rect.intersects(rect))
                        return;
                else
                {
                        if (rect.contains(x.p))
                                pointset.enqueue(x.p);
                        range(x.lb, rect, pointset);
                        range(x.rt, rect, pointset);
                }
        }
        public Iterable<Point2D> range(RectHV rect)     // all points in the set that are inside the rectangle
        {
                if (root == null)
                        return null;

                Queue<Point2D> pointset = new Queue<Point2D>();
                range(root, rect, pointset);
                return pointset;
        }

        private Point2D nearest(Node x, Point2D p, Point2D minp, boolean orit)
        {
                Point2D minpoint = minp;
                if (x == null) return minpoint;

                if (p.distanceSquaredTo(minpoint) > p.distanceSquaredTo(x.p))
                        minpoint = x.p;

                if (orit)
                {
                        if (x.p.x() <= p.x())
                        {
                                minpoint = nearest(x.rt, p, minpoint, !orit);
                                if (x.lb != null && x.lb.rect.distanceSquaredTo(p) < p.distanceSquaredTo(minpoint))
                                        minpoint = nearest(x.lb, p, minpoint, !orit);
                        }
                        else
                        {
                                minpoint = nearest(x.lb, p, minpoint, !orit);
                                if (x.rt != null && x.rt.rect.distanceSquaredTo(p) < p.distanceSquaredTo(minpoint))
                                        minpoint = nearest(x.rt, p, minpoint, !orit);
                        }
                }
                else
                {
                        if (x.p.y() > p.y())
                        {
                                //go left
                                minpoint = nearest(x.lb, p, minpoint, !orit);
                                if (x.rt != null && x.rt.rect.distanceSquaredTo(p) < p.distanceSquaredTo(minpoint))
                                        minpoint = nearest(x.rt, p, minpoint, !orit);
                        }
                        else
                        {
                                minpoint = nearest(x.rt, p, minpoint, !orit);
                                if (x.lb != null && x.lb.rect.distanceSquaredTo(p) < p.distanceSquaredTo(minpoint))
                                        minpoint = nearest(x.lb, p, minpoint, !orit);
                        }
                }
                return minpoint;
        }

        public Point2D nearest(Point2D p)               // a nearest neighbor in the set to p; null if set is empty
        {
                if (isEmpty()) return null;
                return nearest(root, p, root.p, true);
        }

}

