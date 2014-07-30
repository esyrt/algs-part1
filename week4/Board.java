import java.util.Arrays;

public class Board {
            private int N;

            private int[] tiles; 
        
            public Board(int[][] blocks)           // construct a board from an N-by-N array of blocks
                                                               // (where blocks[i][j] = block in row i, column j)
            {
                    if (blocks == null)
                            throw new java.lang.NullPointerException("input cannot be null");
                    N = blocks.length;

                    tiles = new int[N*N];
                    for (int i = 0; i < N; i++)
                            for(int j = 0; j < N; j++)
                            {
                                    tiles[i*N+j] = blocks[i][j];
                            }

            }
            private Board(int[] blocks) 
            {
                    if (blocks == null)
                            throw new java.lang.NullPointerException("input cannot be null");
                    N = (int) Math.sqrt(blocks.length);

                    tiles = new int[N*N];
                    for (int i = 0; i < N*N; i++)
                            tiles[i] = blocks[i];
            }

            public int dimension()                 // board dimension N
            {
                    return N;
            }                                                   
            public int hamming()                   // number of blocks out of place
            {
                    int count = 0;
                    for (int i = 0; i < N*N; i++)
                    {
                            if (tiles[i] != 0 && tiles[i] != i+1)
                                    count+=1;
                    }
                    return count;
            }                                                   
            public int manhattan()                 // sum of Manhattan distances between blocks and goal
            {
                    int count = 0;
                    int row, col;
                    for (int i = 0; i < N*N; i++)
                    {
                           if (tiles[i] != 0 && tiles[i] != i+1)
                           {
                                   row = Math.abs(i / N - (tiles[i]-1) / N);
                                   col = Math.abs(i % N - (tiles[i]-1) % N);
                                   count += (row+col);
                           }

                    }
                    return count;
            }
            public boolean isGoal()                // is this board the goal board?
            {
                    for (int i = 0; i < N*N; i++)
                                    if (tiles[i] != 0 && tiles[i] != i+1)
                                            return false;
                    return true;
            }
                                                               
            public Board twin()                    // a board obtained by exchanging two adjacent blocks in the same row
            {
                    Board tBoard;
                    if (N <= 1)    return null;
                    tBoard = new Board(tiles);
                    tBoard.N = N;
                    if (tiles[0] != 0 && tiles[1] != 0)
                            swap(tBoard.tiles, 0, 1);
                    else
                            swap(tBoard.tiles, N, N+1);

                    return tBoard;

            }                  

            private void swap(int[] board, int a, int b)
            {
                    int tmp;
                    tmp = board[a];
                    board[a] = board[b];
                    board[b] = tmp;
            }
            public boolean equals(Object y)        // does this board equal y?
            {
                    if (y == null) return false;
                    if (y == this) return true;
                    if (y.getClass() != this.getClass())
                            return false;
                    Board yBoard = (Board) y;
                    return Arrays.equals(tiles, yBoard.tiles);
            }                            
            public Iterable<Board> neighbors()     // all neighboring boards
            {
                    int i;
                    Board neig;
                    Queue<Board> nbq = new Queue<Board>();

                    for (i = 0; i < N*N; i++)
                            if (tiles[i] == 0)
                                    break;

                    if (i >= N)
                    {
                            neig = new Board(tiles);
                            swap(neig.tiles, i, i-N);
                            nbq.enqueue(neig);
                    }

                    if (i < N*N-N)
                    {
                            neig = new Board(tiles);
                            swap(neig.tiles, i, i+N);
                            nbq.enqueue(neig);
                    }

                    if ((i+1) % N != 0)
                    {
                            neig = new Board(tiles);
                            swap(neig.tiles, i, i+1);
                            nbq.enqueue(neig);
                    }

                    if ( i % N != 0)
                    {
                            neig = new Board(tiles);
                            swap(neig.tiles, i, i-1);
                            nbq.enqueue(neig);
                    }

                    return nbq;
            }                                                  
            public String toString()               // string representation of the board (in the output format specified below)
            {
                    StringBuilder s = new StringBuilder();
                    int digit = 0;
                    String format;
                    s.append(N);
                    s.append("\n");

                    for (int n = tiles.length; n != 0; n /= 10)
                            digit++;
                    format = "%" + digit + "d ";
                    for (int i = 0; i < tiles.length; i++) {
                            s.append(String.format(format, tiles[i]));
                            if ((i+1) % N == 0)
                                    s.append("\n");
                    }
                    return s.toString();
            } 

            public static void main(String[] args) {
                    In in = new In(args[0]);
                    int N = in.readInt();
                    int[][] blocks = new int[N][N];

                    for (int i = 0; i < N; i++)
                            for (int j = 0; j < N; j++)
                                    blocks[i][j] = in.readInt();
                    Board initial = new Board(blocks);

                    StdOut.print(initial.toString());
                    StdOut.print(initial.twin().toString());
                    StdOut.println(initial.hamming());
                    StdOut.println(initial.manhattan());
                    StdOut.println(initial.dimension());
                    StdOut.println(initial.isGoal());

                    for (Board b : initial.neighbors()) {
                            StdOut.println(b.toString());
                            for (Board d : b.neighbors()) {
                                    StdOut.println("===========");
                                    StdOut.println(d.toString());
                                    StdOut.println("===========");
                            }
                    }
            }
}
