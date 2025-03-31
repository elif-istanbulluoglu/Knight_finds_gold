import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Graph {

	public int row2;
	public int column;
	public LinkedList<int[]>[][] neighbours;
	LinkedList<String> path;
    
    @SuppressWarnings("unchecked")   
	public Graph(int row2, int column) { // Create the constructor
		this.row2 = row2;
		this.column = column;
		this.neighbours = new LinkedList[row2][column];

		for (int i = 0; i < row2; i++) {
			for (int j = 0; j < column; j++) {
				neighbours[i][j] = new LinkedList<>();
			}
		}

	}

	public void addEdge(int i, int j) {
		int[][] edges = { { -2, 1 }, { -1, 2 }, { 1, 2 }, { 2, 1 }, { 2, -1 }, { 1, -2 }, { -1, -2 }, { -2, -1 } }; // defining
																													// Knight's
																													// moves

		for (int[] edge : edges) { // create edges according to moves
			int x = i + edge[0]; // create row edge
			int y = j + edge[1]; // create column edge

			if (x >= 0 && x < row2 && y >= 0 && y < column && Main.board[x][y] != 'T') { // Check the boundaries and the
																							// obstacles(trees)
				neighbours[i][j].add(new int[] { x, y });
			}
		}
	}

	public LinkedList<int[]> getNeighbours(int x, int y) { // for accessing neighbors
		return neighbours[x][y];
	}

	// Using bfs for achieving the shortest path
	public List<String> bfs(int start_x, int start_y, int end_x, int end_y) {
		HashMap<String, String> edgeTo = new HashMap<>(); // create edgeTo to keep track previous cell
		HashMap<String, Boolean> visited = new HashMap<>();
		LinkedList<int[]> queue = new LinkedList<>();

		String start = start_x + "," + start_y; // separate x and y for identify positions
		String end = end_x + "," + end_y;

		queue.add(new int[] { start_x, start_y });
		visited.put(start, true); // mark knight as visited
		edgeTo.put(start, null); // add knight to the edgeTo

		while (!queue.isEmpty()) {
			int[] v = queue.remove();
			int vx = v[0], vy = v[1];
			String vkey = vx + "," + vy; // separate v to x and y

			if (vkey.equals(end)) { // if v equals to gold add it to path
				path = (LinkedList<String>) addPath(start_x, start_y, end_x, end_y, edgeTo);
				return path;
			}

			if (Main.board[vx][vy] != 'T') { // if v equals to tree skip it
				addEdge(vx, vy);

				for (int[] w : getNeighbours(vx, vy)) { // visit v's neighbors
					int wx = w[0], wy = w[1];
					String wkey = wx + "," + wy;

					if (!visited.containsKey(wkey)) { // if w is not visited before
						visited.put(wkey, true); // mark as visited and add to v's edge
						edgeTo.put(wkey, vkey);
						queue.add(w);
					}
				}
			}
		}

		return null;  // if there is no path return null
	}

	public List<String> addPath(int startX, int startY, int endX, int endY, HashMap<String, String> edgeTo) {
		LinkedList<String> path = new LinkedList<>();
		String end = endX + "," + endY; // separate to x and y
		String start = startX + "," + startY;

		while (end != null && !end.equals(start)) {
			int posX = Integer.parseInt(end.split(",")[0]); // split to x and y to add 1
			int posY = Integer.parseInt(end.split(",")[1]);
			path.addFirst((posX + 1) + "," + (posY + 1)); // add one to represent as board
			end = edgeTo.get(end); // go back to parent
		}
		path.addFirst((startX + 1) + "," + (startY + 1)); // add one to represent as board
		return path;
	}

}

