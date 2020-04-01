import java.util.Deque;
import java.util.LinkedList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

	boolean isSolvable;
	private SearchNode solutionNode;
    private MinPQ<SearchNode> minPQ;

	// find a solution to the initial board (using the A* algorithm)
	public Solver(Board initial) {
		
        solutionNode = null;
        minPQ = new MinPQ<>();
        minPQ.insert(new SearchNode(initial, 0, null));

        while (true) {

            SearchNode currNode = minPQ.delMin();
            Board currBoard = currNode.getBoard();

            if (currBoard.isGoal()) {
                isSolvable = true;
                solutionNode = currNode;
                break;
            }
            if (currBoard.hamming() == 2 && currBoard.twin().isGoal()) {
                isSolvable = false;
                break;
            }

            // Insert each neighbor except the board of the previous search node
            int moves = currNode.getMoves();
            Board prevBoard = moves > 0 ? currNode.prev().getBoard() : null;

            for (Board nextBoard : currBoard.neighbors()) {
                if (prevBoard != null && nextBoard.equals(prevBoard)) {
                    continue;
                }
                minPQ.insert(new SearchNode(nextBoard, moves + 1, currNode));
            }
        }
	}

	// is the initial board solvable? (see below)
	public boolean isSolvable() {
		return isSolvable;
	}

	// min number of moves to solve initial board
	public int moves() {
		return isSolvable() ? solutionNode.getMoves() : -1;
	}

	// sequence of boards in a shortest solution
	public Iterable<Board> solution() {
        if (!isSolvable) {
            return null;
        }
        Deque<Board> solution = new LinkedList<>();
        SearchNode node = solutionNode;
        while (node != null) {
            solution.addFirst(node.getBoard());
            node = node.prev();
        }
        return solution;
	}

	private class SearchNode implements Comparable<SearchNode> {

		private SearchNode prev;
		private Board board;
		private int moves;

		SearchNode(Board board, int moves, SearchNode prev) {
			this.board = board;
			this.moves = moves;
			this.prev = prev;
		}

		@Override
		public int compareTo(SearchNode that) {
			return this.priority() - that.priority();
		}

		public int priority() {
			return board.manhattan() + moves;
		}
		
        public Board getBoard() {
            return board;
        }
        
        public int getMoves() {
            return moves;
        }

        public SearchNode prev() {
            return prev;
        }

	}
	// test client (see below)
	public static void main(String[] args) {
		// create initial board from file
		In in = new In(args[0]);
		int n = in.readInt();
		int[][] tiles = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				tiles[i][j] = in.readInt();
		Board initial = new Board(tiles);

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}
	}
}
