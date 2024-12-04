public class ForestExplorerDFS {
    private static final int GRID_SIZE = 8;  // 3x3 grid
    private static final int TOTAL_MOVES = 63;  // 8 moves to make
    private static boolean[][] visited = new boolean[GRID_SIZE][GRID_SIZE];
    private static int totalPaths = 0;

    public static void main(String[] args) {
        String input = "***************************************************************";  // Example input path, change as needed

        long startTime = System.currentTimeMillis();
        visited[0][0] = true;  // Start at (0, 0)
        dfs(input, 0, 0, 0);   // Start DFS from (0, 0) with 0 moves
        long endTime = System.currentTimeMillis();

        System.out.println("Total paths: " + totalPaths);
        System.out.println("Time (ms): " + (endTime - startTime));
    }

    private static void dfs(String path, int row, int col, int moveIndex) {
        // If we've made the total number of moves, check if we're at the destination
        if (moveIndex == TOTAL_MOVES) {
            if (row == GRID_SIZE - 1 && col == 0) {  // Target position (2, 0)
                totalPaths++;  // Increment valid path count
                System.out.println("Found valid path! Total paths: " + totalPaths);
            }
            return;
        }

        char direction = path.charAt(moveIndex);

        // If it's a wildcard ('*'), try all directions
        if (direction == '*') {
            for (char dir : new char[]{'D', 'U', 'L', 'R'}) {
                exploreMove(row, col, dir, path, moveIndex);
            }
        } else {
            exploreMove(row, col, direction, path, moveIndex);
        }
    }

    private static void exploreMove(int row, int col, char direction, String path, int moveIndex) {
        int newRow = row, newCol = col;

        // Apply movement based on the direction
        switch (direction) {
            case 'D': newRow++; break;
            case 'U': newRow--; break;
            case 'L': newCol--; break;
            case 'R': newCol++; break;
        }

        // Ensure the move is within bounds and hasn't been visited yet
        if (newRow >= 0 && newRow < GRID_SIZE && newCol >= 0 && newCol < GRID_SIZE && !visited[newRow][newCol]) {
            visited[newRow][newCol] = true;  // Mark this cell as visited

            // Recurse for the next move
            dfs(path, newRow, newCol, moveIndex + 1);

            visited[newRow][newCol] = false;  // Backtrack (unmark this cell)
        }
    }
}
