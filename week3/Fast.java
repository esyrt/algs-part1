import java.util.Arrays;

public class Fast {
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

                   Point[] aux = new Point[N];

                   for (int i = 0; i < N-3; i++)
                   {
                           for (int j = i; j < N; j++)
                                   aux[j] = p[j];
                           
                           Arrays.sort(aux, i+1, N, aux[i].SLOPE_ORDER);
                           Arrays.sort(aux, 0, i, aux[i].SLOPE_ORDER);

                           int pre = i+1;
                           int end = i+2;
                           int pHead = 0;
                           while (end < N)
                           {
                                   while (end < N && aux[i].slopeTo(aux[pre]) == aux[i].slopeTo(aux[end]))
                                           end++;
                                   
                                   //use binary search here
                                   //if (Arrays.binarySearch(aux, 0, i, aux[pre], aux[i].SLOPE_ORDER) == 0)
                                   //     break;

                                   if (end-pre >= 3)
                                   {
                                       double pSlope = Double.NEGATIVE_INFINITY;
                                       while (pHead < i) {
                                               pSlope = aux[i].slopeTo(aux[pHead]);
                                               if (pSlope < aux[i].slopeTo(aux[pre])) 
                                                       pHead++;
                                               else                    
                                                       break;
                                       }
                                       if (pSlope != aux[i].slopeTo(aux[pre])) {
                                       
                                           aux[i].drawTo(aux[end-1]);
                                           String output = aux[i].toString() + " -> ";
                                           for (int k = pre; k < end-1; k++)
                                               output += aux[k].toString() + " -> ";
                                           output += aux[end-1].toString();
                                           StdOut.println(output);
                                       }
                                   }
                                   pre = end;
                                   end++;
                           }

                   }

                   StdDraw.show(0);
           }
}
