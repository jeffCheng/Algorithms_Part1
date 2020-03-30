import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Board {

	int[][] tiles;
	int dimension;

	// create a board from an n-by-n array of tiles,
	// where tiles[row][col] = tile at (row, col)
	public Board(int[][] tiles) {
		if (tiles == null || tiles.length == 0)
			throw new IllegalArgumentException();
		this.dimension = tiles.length;
		tiles = new int[dimension][];
		for (int i = 0; i < dimension; i++) {
			this.tiles[i] = Arrays.copyOf(tiles[i], dimension);
		}
	}

	// string representation of this board
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(dimension + "\n");
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				s.append(String.format("%2d ", tiles[i][j]));
			}
			s.append("\n");
		}
		return s.toString();
	}

	// board dimension n
	public int dimension() {
		return dimension;
	}

	// number of tiles out of place
	public int hamming() {
		int count = 0;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (tiles[i][j] != i * dimension + j + 1 && !isBlank(i, j))
					count++;
			}
		}
		return count;
	}

	private boolean isBlank(int row, int col) {
		if (row < 0 || row >= dimension || col < 0 || col >= dimension)
			return true;
		else if (tiles[row][col] == 0)
			return true;
		else
			return false;
	}

	// sum of Manhattan distances between tiles and goal
	public int manhattan() {
		// calculate all the distance of the number
		int count = 0;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (tiles[i][j] != i * dimension + j + 1 && !isBlank(i, j)) {
					int correctRow = (tiles[i][j] - 1) / dimension;
					int correctCol = (tiles[i][j] - 1) % dimension;
					count += Math.abs(correctRow - i) + Math.abs(correctCol - j);
				}
			}
		}
		return count;
	}

	// is this board the goal board?
	public boolean isGoal() {
		return hamming() == 0;
	}

	// does this board equal y?
	public boolean equals(Object y) {
		if (this == y)
			return true;
		else if (y == null)
			return false;
		else if (y.getClass() != this.getClass())
			return false;
		Board that = (Board) y;
		if (that.dimension != dimension)
			return false;
		for (int i = 0; i < this.tiles[dimension].length; i++) {
			if (!Arrays.equals(this.tiles[i], that.tiles[i])) {
				return false;
			}
		}
		return true;
	}

	// all neighboring boards
	public Iterable<Board> neighbors() {
		List<Board> list = new LinkedList<Board>();
		int blankRow = 0;
		int blankCol = 0;
		// find the blank row/col
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (tiles[i][j] == 0) {
					blankRow = i;
					blankCol = j;
					break;
				}
			}
		}
		// move zero blank
		// left
		if (blankRow - 1 >= 0) {
			Board board = moveBlank(blankRow - 1, blankCol, blankRow, blankCol);
			list.add(board);
		}
		// right
		if (blankRow + 1 < dimension) {
			Board board = moveBlank(blankRow + 1, blankCol, blankRow, blankCol);
			list.add(board);
		}
		// up
		if (blankCol - 1 >= 0) {
			Board board = moveBlank(blankRow, blankCol - 1, blankRow, blankCol);
			list.add(board);
		}
		// down
		if (blankCol + 1 < dimension) {
			Board board = moveBlank(blankRow, blankCol + 1, blankRow, blankCol);
			list.add(board);
		}
		return list;
	}

	private Board moveBlank(int blankRow, int blankCol, int row, int col) {
		int[][] newTiles = new int[dimension][dimension];
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				newTiles[i][j] = this.tiles[i][j];
			}
		}
		// swap
		int temp = newTiles[blankRow][blankCol];
		newTiles[blankRow][blankCol] = newTiles[row][col];
		tiles[row][col] = temp;
		Board board = new Board(newTiles);
		return board;
	}

	private int[][] arrayCopy(int[][] array) {
		int n = array.length;
		int[][] out = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++)
				out[i][j] = array[i][j];
		}
		return out;
	}

	private void swapArrVal(int[][] arr, int row1, int col1, int row2, int col2) {
		int temp = arr[row1][col1];
		arr[row1][col1] = arr[row2][col2];
		arr[row2][col2] = temp;
	}

	// a board that is obtained by exchanging any pair of tiles
	public Board twin() {
		int[][] twinBlock = arrayCopy(tiles);
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (!isBlank(i, j) && !isBlank(i, j + 1)) {
					swapArrVal(twinBlock, i, j, i, j + 1);
					Board twinBoard = new Board(twinBlock);
					return twinBoard;
				}
			}
		}
		return null;
	}

	// unit testing (not graded)
	public static void main(String[] args) {
		
	}

}