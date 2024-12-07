public class ForestExplorerVer5 {
    private static int totalPaths = 0;
    private static final int GOAL_ROW = 7;
    private static final int GOAL_COL = 0;
    private static final int MAX_MOVES = 63; // 8x8 grid, you want to reach 63 moves
    private static final int[] DR = {-1, 1, 0, 0}; // Directions for U, D, L, R
    private static final int[] DC = {0, 0, -1, 1};
    private static final char[] DIRECTION = {'U', 'D', 'L', 'R'}; // Directions in the path

    public static void main(String[] args) {
        String path = "*****DR******R******R**********"; // Example path input
        boolean[][] visitedCells = new boolean[8][8]; // Visited cells grid
        int depth = MAX_MOVES; // The depth of recursion (total number of moves)

        // Start the exploration from (0, 0), moveIndex = 0, and depth = 63
        explorePaths(path, 0, 0, 0, depth, visitedCells);

        System.out.println("Total paths found: " + totalPaths);
    }

    private static void explorePaths(String path, int row, int col, int moveIndex, int maxDepth, boolean[][] visitedCells) {
        // Base case: If we've reached the goal at moveIndex == MAX_MOVES and are at (7, 0), count it as a valid path
        if (moveIndex == maxDepth) {
            if (row == GOAL_ROW && col == GOAL_COL) {
                totalPaths++;
                System.out.println("Found valid path! Total paths: " + totalPaths);
            }
            return;
        }

        // If it's a dead end (out of bounds or already visited), return early
        if (!isValidMove(row, col) || visitedCells[row][col]) {
            return;
        }

        // Mark this cell as visited for the current path exploration
        visitedCells[row][col] = true;

        // Ensure that moveIndex doesn't exceed the path length
        if (moveIndex < path.length()) {
            char direction = path.charAt(moveIndex);
            //System.out.println("Visiting: (" + row + ", " + col + ") at move " + moveIndex + " Current path: " + path.substring(0, moveIndex + 1));

            // Handle * wildcard: try all 4 directions
            if (direction == '*') {
                // Try all four directions for a wildcard move
                for (int i = 0; i < 4; i++) {
                    int newRow = row + DR[i];
                    int newCol = col + DC[i];
                    // Debugging statement for exploring a wildcard
                    //System.out.println("Wildcard move to: (" + newRow + ", " + newCol + ")");
                    // Only explore if the new position is within bounds and not visited
                    if (isValidMove(newRow, newCol) && !visitedCells[newRow][newCol]) {
                        explorePaths(path, newRow, newCol, moveIndex + 1, maxDepth, visitedCells);
                    }
                }
            } else {
                // Handle specific direction (U, D, L, R)
                for (int i = 0; i < 4; i++) {
                    if (direction == DIRECTION[i]) {
                        int newRow = row + DR[i];
                        int newCol = col + DC[i];
                        // Debugging statement for specific direction moves
                        //System.out.println("Specific move " + direction + " to: (" + newRow + ", " + newCol + ")");
                        // Only explore if the new position is within bounds and not visited
                        if (isValidMove(newRow, newCol) && !visitedCells[newRow][newCol]) {
                            explorePaths(path, newRow, newCol, moveIndex + 1, maxDepth, visitedCells);
                        }
                        break;
                    }
                }
            }
        }

        // Reset the visited cell for backtracking (so the algorithm can explore other paths)
        visitedCells[row][col] = false;
    }

    // Helper function to check if a move is within bounds
    private static boolean isValidMove(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
}
