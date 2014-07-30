public class PercolationStats {
        private double[] thresholdx;

        public PercolationStats(int N, int T)    // perform T independent computational experiments on an N-by-N grid
        {
                thresholdx = new double[T];
                if (N <= 0 || T <= 0)
                        throw new IllegalArgumentException("Arguments out of bound");
                int row, col;
                double count;
                for (int i = 0; i < T; i++)
                {
                        Percolation exp = new Percolation(N);
                        count = 0;
                        do {
                            row = StdRandom.uniform(1, N+1);
                            col = StdRandom.uniform(1, N+1);
                            if (exp.isOpen(row, col))
                                    continue;
                            exp.open(row, col);
                            count++;
                        }
                        while(!exp.percolates());

                        thresholdx[i] = count/(N*N);

                }
        }

        public double mean()                     // sample mean of percolation threshold
        {
                int t = thresholdx.length;
                double sum = 0;
                for (int i = 0; i < t; i++)
                        sum += thresholdx[i];
                return sum/t;
        }
        public double stddev()                   // sample standard deviation of percolation threshold
        {
                int t = thresholdx.length;
                double u = mean();
                double sum = 0;
                for (int i = 0; i < t; i++)
                {
                        sum += (thresholdx[i]-u)*(thresholdx[i]-u);
                }
                return Math.sqrt(sum/(t-1));
        }                    
        public double confidenceLo()             // returns lower bound of the 95% confidence interval
        {
                return mean()-(1.96*stddev())/Math.sqrt(thresholdx.length);

        }   
        public double confidenceHi()             // returns upper bound of the 95% confidence interval
        {
                return mean()+(1.96*stddev())/Math.sqrt(thresholdx.length);
        }
        public static void main(String[] args)   // test client, described below
        {
                PercolationStats pls = new PercolationStats(Integer.parseInt(args[0]),
                                                    Integer.parseInt(args[1]));
                System.out.printf("mean                     = %f\n", pls.mean());
                System.out.printf("stddev                   = %f\n", pls.stddev());
                System.out.printf("95%% confidence Interval  = %f, %f\n",
                                                                pls.confidenceLo(), pls.confidenceHi());
        }
}
