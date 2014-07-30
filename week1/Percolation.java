public class Percolation {
        private WeightedQuickUnionUF grid, addGrid;
        private boolean[] stats;
        private int N;

        public Percolation(int N)              // create N-by-N grid, with all sites blocked
        {
                if (N <= 0)
                        throw new java.lang.IllegalArgumentException("N is illegal!");
                int siteNum = N*N;
                this.N = N;

                grid = new WeightedQuickUnionUF(siteNum+2);
                addGrid = new WeightedQuickUnionUF(siteNum+1);
                stats = new boolean[siteNum+2];

                for (int i = 1; i <= siteNum; i++)
                {
                        stats[i] = false;
                }
                // Initialize top and bottom virtual sites open respectively
                stats[0]  = true;
                stats[siteNum+1] = true;
        }

        private int convertIndex(int i, int j)
        {
                if (i <= 0 || i > N)
                        throw new IndexOutOfBoundsException("row i out of bound");
                if (j <= 0 || j > N)
                        throw new IndexOutOfBoundsException("col j out of bound");

                return (i-1)*N+j;
        }

        public void open(int i, int j)         // open site (row i, column j) if it is not already
        {
                int indx = convertIndex(i, j);
                stats[indx] = true;

                if (i != 1 && isOpen(i-1, j))
                {
                        grid.union(indx, convertIndex(i-1, j));
                        addGrid.union(indx, convertIndex(i-1, j));
                }
                if (i != N && isOpen(i+1, j))
                {
                        grid.union(indx, convertIndex(i+1, j));
                        addGrid.union(indx, convertIndex(i+1, j));
                }
                if (j != 1 && isOpen(i, j-1))
                {
                        grid.union(indx, convertIndex(i, j-1));
                        addGrid.union(indx, convertIndex(i, j-1));
                }
                if (j != N && isOpen(i, j+1))
                {
                        grid.union(indx, convertIndex(i, j+1));
                        addGrid.union(indx, convertIndex(i, j+1));
                }

                if (indx <= N)
                {
                        grid.union(indx, 0);
                        addGrid.union(indx, 0);
                }
                if (indx >= (N*(N-1)+1))
                {
                        grid.union(indx, N*N+1);
                }
        }     
        public boolean isOpen(int i, int j)    // is site (row i, column j) open?
        {
                int indx = convertIndex(i, j);
                return stats[indx];
        }
        public boolean isFull(int i, int j)    // is site (row i, column j) full? check is this site is connect with top
        {
                int indx = convertIndex(i, j);
                return grid.connected(indx, 0) && addGrid.connected(indx, 0);
        }
                               
        public boolean percolates()            // does the system percolate?
        {
                return grid.connected(0, N*N+1);
        }
}
