import java.util.List;
import java.util.Scanner;

public class Main {
	static char[][] board; // Represent the board as a 2D char array
	static public Graph g;

	public static void main(String[] args) {
		int knight_row = 0; // initialize variables
		int knight_column = 0;
		int gold_row = 0;
		int gold_column = 0;
		boolean validInput = true; // to check if characters are valid

		Scanner sc = new Scanner(System.in);
		String text = sc.next();
		int row = Character.getNumericValue(text.charAt(0));
		int col = Character.getNumericValue(text.charAt(1));

		board = new char[row][col];
		int index = 2; // starts at 2 to read what cell's include

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (index < text.length()) {
					board[i][j] = text.charAt(index++);
					if (board[i][j] == 'K') {
						knight_row = i; // if character is K assign i and j to knight's row and column
						knight_column = j;
					}
					if (board[i][j] == 'G') {
						gold_row = i; // if character is G assign i and j to gold's row and column
						gold_column = j;
					}
				}
			}
		}

		for (int i = 0; i < row; i++) { // if input includes invalid character give a warning
			for (int j = 0; j < col; j++) {
				if (board[i][j] != 'G' && board[i][j] != '.' && board[i][j] != 'K' && board[i][j] != 'T') {
					System.out.println("Wrong input");
					validInput = false;
					break;
				}

			}

			if (!validInput) { // break the outer loop
				break;
			}
		}

		if (!validInput) { // if input is invalid quit the program
			return;
		}

		Graph g = new Graph(row, col); // create a graph g
		List<String> path = g.bfs(knight_row, knight_column, gold_row, gold_column); // create the shortest path list
																						// with bfs

		if (g.path == null || g.path.isEmpty()) {     //if path is empty
			System.out.println("There is no path");
		} else {

		StringBuilder result = new StringBuilder();
			for (int i = 0; i < path.size(); i++) {
				result.append("c");  // add c in front of each position
			    result.append(path.get(i));
			    if (i < path.size() - 1) {  //if i is not in the last position
			        result.append(" -> "); 
			    }
			}
			System.out.println("Shortest path: " + result.toString());

		}
	}
}
