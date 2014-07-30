import java.util.Arrays;

public class Brute {
           public static void main(String[] args)
           {
                   StdDraw.setXscale(0, 32768);
                   StdDraw.setYscale(0, 32768);
                   StdDraw.show(0);

                   String filename = args[0];
                   In file = new In(filename);
                   int N = file.readInt();

                   Point[] p = new Point[N];
                   for (int i = 0; i < N; i++)
                   {
                           int x = file.readInt();
                           int y = file.readInt();
                           p[i] = new Point(x, y);
                           p[i].draw();
                   }

                   Arrays.sort(p);

                   for (int i = 0; i < N; i++)
                           for (int j = i+1; j < N; j++)
                                   for (int k = j+1; k < N; k++)
                                           for (int l = k+1; l < N; l++)
                                           {
                                                   if (p[i].slopeTo(p[j]) == p[i].slopeTo(p[k]) && p[i].slopeTo(p[j]) == p[i].slopeTo(p[l]))
                                                   {
                                                           StdOut.println(p[i].toString() + " -> "
                                                                           + p[j].toString() + " -> " + p[k].toString()
                                                                           + " -> " + p[l].toString());
                                                           p[i].drawTo(p[l]);
                                                   }
                                           }
                   StdDraw.show(0);
           }
}
