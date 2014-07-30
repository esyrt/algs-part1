import java.util.Comparator;

public class Solver {
            private SearchNode goal;
            private class SearchNode{
                    private int move;
                    private Board bd;
                    private SearchNode prenode;

                    public SearchNode(Board b)
                    {
                            move = 0;
                            prenode = null;
                            bd = b;
                    }
            }

            private class PriorityOrder implements Comparator<SearchNode>
            {
                    public int compare(SearchNode s1, SearchNode s2)
                    {
                            int p1 = s1.bd.manhattan() + s1.move;
                            int p2 = s2.bd.manhattan() + s2.move;

                            if (p1 > p2) return 1;
                            else if (p1 < p2) return -1;
                            else return 0;
                    }
            }
           
            public Solver(Board initial)            // find a solution to the initial board (using the A* algorithm)
            {
                    PriorityOrder po = new PriorityOrder();
                    SearchNode ini = new SearchNode(initial);
                    MinPQ<SearchNode> mpq = new MinPQ<SearchNode>(po);

                    PriorityOrder pot = new PriorityOrder();
                    SearchNode initwin = new SearchNode(initial.twin());
                    MinPQ<SearchNode> mpqt = new MinPQ<SearchNode>(pot);

                    mpq.insert(ini);
                    mpqt.insert(initwin);

                    SearchNode minNode = mpq.delMin();
                    SearchNode minTnode = mpqt.delMin();

                    while (!minNode.bd.isGoal() && !minTnode.bd.isGoal())
                    {
                            for (Board b : minNode.bd.neighbors())
                            {
                                    if (minNode.prenode == null || !minNode.prenode.bd.equals(b))
                                    {
                                            SearchNode innode = new SearchNode(b);
                                            innode.move = minNode.move+1;
                                            innode.prenode = minNode;
                                            mpq.insert(innode);
                                    }
                            }
                            for (Board b : minTnode.bd.neighbors())
                            {
                                    if (minTnode.prenode == null || !minTnode.prenode.bd.equals(b))
                                    {
                                            SearchNode innode = new SearchNode(b);
                                            innode.move = minTnode.move+1;
                                            innode.prenode = minTnode;
                                            mpqt.insert(innode);
                                    }
                            }
                            minNode = mpq.delMin();
                            minTnode = mpqt.delMin();
                    }

                    if (minNode.bd.isGoal())
                            goal = minNode;
                    else
                            goal = null;
            }

            public boolean isSolvable()             // is the initial board solvable?
            {
                    return goal != null;
            }
            public int moves()                      // min number of moves to solve initial board; -1 if no solution
            {
                    if (goal == null)
                            return -1;
                    return goal.move;
            }
            public Iterable<Board> solution()       // sequence of boards in a shortest solution; null if no solution
            {
                    if (goal == null)
                            return null;
                    Stack<Board> sol = new Stack<Board>();
                    SearchNode ite = goal;
                    while (ite != null)
                    {
                            sol.push(ite.bd);
                            ite = ite.prenode;
                    }
                    return sol;
            }
            public static void main(String[] args)  // solve a slider puzzle (given below)
            {
                        In in = new In(args[0]);
                        int N = in.readInt();
                        int[][] blocks = new int[N][N];
                        for (int i = 0; i < N; i++)
                                for (int j = 0; j < N; j++)
                                        blocks[i][j] = in.readInt();
                        Board initial = new Board(blocks);
                        
                        Solver solver = new Solver(initial);

                        if (!solver.isSolvable())
                                StdOut.println("No solution possible");
                        else 
                        {
                                StdOut.println("Minimum number of moves = " + solver.moves());
                                for (Board board : solver.solution())
                                        StdOut.println(board);
                        }
            }
}
