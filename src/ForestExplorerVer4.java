public class ForestExplorerVer4 {
    private static final int TOTAL_MOVES = 63;
    private static int totalPaths = 0;

    // IDDFS Iterative Deepening Depth-First Search
    public static void main(String[] args) {
        String path = "*****DR******R******R********************R*D************L******"; // Sample path input
        long startTime = System.currentTimeMillis();

        for (int depth = 1; depth <= TOTAL_MOVES; depth++) {
            explorePathsIteratively(path, 0, 0, 0, new boolean[8][8], depth); // Start with depth 1
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Total paths: " + totalPaths);
        System.out.println("Time (ms): " + (endTime - startTime));
    }

    // IDDFS method with state restoration
    private static void explorePathsIteratively(String path, int row, int col, int moveIndex, boolean[][] visited, int depthLimit) {
        if (moveIndex == depthLimit) {
            if (row == 7 && col == 0) {
                System.out.println("Found valid path! Total paths: " + totalPaths);
                totalPaths++;
            }
            return;
        }

        // Mark the current cell as visited
        visited[row][col] = true;

        char direction = path.charAt(moveIndex);
        if (direction == '*') {
            // Explore all four possible directions when we encounter '*'
            for (char dir : new char[]{'D', 'U', 'L', 'R'}) {
                move(row, col, dir, path, moveIndex, visited);
            }
        } else {
            // Explore the given direction
            move(row, col, direction, path, moveIndex, visited);
        }

        // Backtrack: Unmark the current cell as visited after exploration
        visited[row][col] = false;
    }

    private static void move(int row, int col, char direction, String path, int moveIndex, boolean[][] visited) {
        int newRow = row;
        int newCol = col;

        switch (direction) {
            case 'D': newRow = row + 1; break;
            case 'U': newRow = row - 1; break;
            case 'L': newCol = col - 1; break;
            case 'R': newCol = col + 1; break;
        }

        // Ensure the new position is within the grid bounds and not visited
        if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8 && !visited[newRow][newCol]) {
            explorePathsIteratively(path, newRow, newCol, moveIndex + 1, visited, TOTAL_MOVES);
        }
    }
}
