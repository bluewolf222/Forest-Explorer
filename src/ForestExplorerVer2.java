import java.util.HashMap;
import java.util.Map;

public class ForestExplorerVer2 {
    private static final int GRID_SIZE = 3;
    private static final int TOTAL_MOVES = 8;
    private static boolean[][] visited = new boolean[GRID_SIZE][GRID_SIZE];
    private static int totalPaths = 0;
    private static Map<String, Integer> memo = new HashMap<>();

    public static void main(String[] args) {
        String input = "********";  // Test input

        // Validate input
        if (input.length() != TOTAL_MOVES || !input.matches("[UDLR*]+")) {
            System.out.println("Invalid input");
            return;
        }

        long startTime = System.currentTimeMillis();
        visited[0][0] = true;  // Start at (0, 0)
        explorePaths(input, 0, 0, 0);  // Start recursive exploration
        long endTime = System.currentTimeMillis();

        System.out.println("Total paths: " + totalPaths);
        System.out.println("Time (ms): " + (endTime - startTime));
    }

    private static int explorePaths(String path, int row, int col, int moveIndex) {
        // Debugging statement to track recursion
        System.out.println("Exploring: row=" + row + ", col=" + col + ", moveIndex=" + moveIndex);

        // If we've reached the total number of moves
        if (moveIndex == TOTAL_MOVES) {
            if (row == 2 && col == 0) {
                totalPaths++;  // If we're at the destination, count the path
                System.out.println("Found valid path! Total paths: " + totalPaths);
                return 1;  // Found a valid path
            }
            return 0;  // Invalid path
        }

        // Memoization key
        String key = row + "," + col + "," + moveIndex;
        if (memo.containsKey(key)) {
            return memo.get(key);  // Return memoized result
        }

        int pathCount = 0;
        char direction = path.charAt(moveIndex);

        // If the direction is a wildcard '*', explore all possible moves
        if (direction == '*') {
            for (char dir : new char[]{'D', 'U', 'L', 'R'}) {
                pathCount += move(row, col, dir, path, moveIndex);
            }
        } else {
            // Otherwise, just follow the given direction
            pathCount += move(row, col, direction, path, moveIndex);
        }

        // Store the result in the memoization map
        memo.put(key, pathCount);
        return pathCount;
    }

    private static int move(int row, int col, char direction, String path, int moveIndex) {
        int newRow = row, newCol = col;

        // Apply movement based on the direction
        switch (direction) {
            case 'D': newRow++; break;
            case 'U': newRow--; break;
            case 'L': newCol--; break;
            case 'R': newCol++; break;
        }

        // Debugging statement to track moves
        System.out.println("Trying move: " + direction + " to row=" + newRow + ", col=" + newCol);

        // Check if the move is within bounds and the cell is not visited yet
        if (newRow >= 0 && newRow < GRID_SIZE && newCol >= 0 && newCol < GRID_SIZE && !visited[newRow][newCol]) {
            visited[newRow][newCol] = true;  // Mark this cell as visited

            // Explore the next move recursively
            int result = explorePaths(path, newRow, newCol, moveIndex + 1);  // Recursive call

            visited[newRow][newCol] = false;  // Backtrack and unmark the visited cell
            return result;  // Return the result (1 for valid path, 0 for invalid)
        }

        // If the move is out of bounds or revisits a visited cell
        return 0;  // Invalid move
    }
}
