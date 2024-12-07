import java.util.HashMap;
import java.util.Map;

public class ForestExplorerVer2 {
    private static final int GRID_SIZE = 6;
    private static final int TOTAL_MOVES = 35;
    private static boolean[][] visited = new boolean[GRID_SIZE][GRID_SIZE];
    private static int totalPaths = 0;
    private static Map<String, Integer> memo = new HashMap<>();

    public static void main(String[] args) {
        String input = "***********************************";  // Test input

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
        //System.out.println("Exploring: row=" + row + ", col=" + col + ", moveIndex=" + moveIndex);

        // If we've reached the total number of moves
        if (moveIndex == TOTAL_MOVES) {
            if (row == 5 && col == 0) {  // Destination at (5, 0)
                totalPaths++;  // Increment path count
                System.out.println("Found valid path!: " + totalPaths);

                // Reset the visited cells for backtracking
                resetVisited();

                return 1;  // Found a valid path
            }
            return 0;  // Invalid path if we don't reach the destination
        }

        // Memoization key (position, move index, visited state)
        String key = row + "," + col + "," + moveIndex + "," + visitedStateHash();

        // Check if the result for this state has already been computed
        if (memo.containsKey(key)) {
            //System.out.println("Memo hit: " + key);
            return memo.get(key);  // Return the result (valid or invalid)
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
        //System.out.println("Memo added: " + key + " -> " + pathCount);

        return pathCount;
    }

    private static void resetVisited() {
        // Reset all visited cells to false
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                visited[i][j] = false;
            }
        }
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

        // Check if the move is within bounds and the cell is not visited yet
        if (newRow >= 0 && newRow < GRID_SIZE && newCol >= 0 && newCol < GRID_SIZE && !visited[newRow][newCol]) {
            visited[newRow][newCol] = true;  // Mark this cell as visited

            // Explore the next move recursively
            int result = explorePaths(path, newRow, newCol, moveIndex + 1);

            visited[newRow][newCol] = false;  // Backtrack and unmark the visited cell
            return result;  // Return the result (1 for valid path, 0 for invalid)
        }

        // If the move is out of bounds or revisits a visited cell
        return 0;  // Invalid move
    }

    // This method generates the unique state of the visited grid as a bitstring
    private static String visitedStateHash() {
        int visitedState = 0;

        // Iterate through the grid and set bits for visited cells
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (visited[i][j]) {
                    visitedState |= (1 << (i * GRID_SIZE + j));  // Set the bit for the cell
                }
            }
        }

        return Integer.toBinaryString(visitedState); // Return the hashed state as a binary string
    }

    private static String generateMemoKey(int row, int col, int moveIndex) {
        // Start constructing the key with the position (row, col) and the moveIndex
        StringBuilder key = new StringBuilder();
        key.append(row).append(",").append(col).append(",").append(moveIndex).append(",");

        // Add the visited grid state to the key
        long visitedState = 0;

        // Iterate through the grid and set bits for visited cells
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (visited[i][j]) {
                    visitedState |= (1L << (i * GRID_SIZE + j));  // Set the bit for the visited cell
                }
            }
        }

        // Append the binary string representation of the visited state to the key
        key.append(visitedState);

        return key.toString();  // Return the generated memo key as a string
    }
}
