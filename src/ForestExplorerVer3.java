public class ForestExplorerVer3 {
    private static final int TOTAL_MOVES = 63;
    private static int totalPaths = 0;
    // Pruning with Heuristics
    // Main function to start the process
    public static void main(String[] args) {
        String path = "*****DR******R******R********************R*D************L******"; // Sample path input
        long startTime = System.currentTimeMillis();

        explorePaths(path, 0, 0, 0, 0L);  // Starting at (0, 0) with 0 moves and no visited cells

        long endTime = System.currentTimeMillis();
        System.out.println("Total paths: " + totalPaths);
        System.out.println("Time (ms): " + (endTime - startTime));
    }

    // Heuristic-based path exploration with pruning
    private static void explorePaths(String path, int row, int col, int moveIndex, long visited) {
        if (moveIndex == TOTAL_MOVES) {
            // If we've reached the final position (7, 0), count it as a valid path
            if (row == 7 && col == 0) {
                System.out.println("Found valid path! Total paths: " + totalPaths);
                totalPaths++;
            }
            return;
        }

        // Heuristic: Calculate the Manhattan distance from current position (row, col) to the goal (7, 0)
        int remainingMoves = TOTAL_MOVES - moveIndex;
        int minDistanceToEnd = Math.abs(7 - row) + Math.abs(0 - col);

        // Prune if there are not enough remaining moves to reach (7, 0)
        if (remainingMoves < minDistanceToEnd) {
            return;  // This path will never succeed, so terminate early
        }

        // Get the direction for this move
        char direction = path.charAt(moveIndex);
        if (direction == '*') {
            // If the direction is '*', try all possible directions (D, U, L, R)
            for (char dir : new char[]{'D', 'U', 'L', 'R'}) {
                move(row, col, dir, path, moveIndex, visited);
            }
        } else {
            // If the direction is fixed (D, U, L, or R), proceed with that direction
            move(row, col, direction, path, moveIndex, visited);
        }
    }

    // Helper function to apply the move
    private static void move(int row, int col, char direction, String path, int moveIndex, long visited) {
        int newRow = row;
        int newCol = col;
        long newVisited = visited;

        // Apply the move depending on the direction
        switch (direction) {
            case 'D': newRow = row + 1; break;
            case 'U': newRow = row - 1; break;
            case 'L': newCol = col - 1; break;
            case 'R': newCol = col + 1; break;
        }

        // Check if the move is valid: stay within bounds and don't revisit cells
        if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8 && !isVisited(newVisited, newRow, newCol)) {
            // Mark the cell as visited
            newVisited = markVisited(newVisited, newRow, newCol);

            // Recur to the next move
            explorePaths(path, newRow, newCol, moveIndex + 1, newVisited);
        }
    }

    // Check if a cell has already been visited (using bitmasking)
    private static boolean isVisited(long visited, int row, int col) {
        int cellIndex = row * 8 + col;
        return (visited & (1L << cellIndex)) != 0;
    }

    // Mark a cell as visited using bitmasking
    private static long markVisited(long visited, int row, int col) {
        int cellIndex = row * 8 + col;
        return visited | (1L << cellIndex);
    }
}
