import java.util.HashMap;

public class ForestExplorerDFSVer2 {
    private static final int GRID_SIZE = 3;  // 8x8 grid
    private static int totalPaths = 0;
    private static HashMap<String, Integer> memo = new HashMap<>();  // Memoization map

    public static void main(String[] args) {
        String input = "********";  // Input path, length 63

        long startTime = System.currentTimeMillis();
        dfs(input, 0, 0, 0, 1L);  // Start DFS from (0, 0) with only the starting cell visited
        long endTime = System.currentTimeMillis();

        System.out.println("Total paths: " + totalPaths);
        System.out.println("Time (ms): " + (endTime - startTime));
    }

    private static void dfs(String path, int row, int col, int moveIndex, long visitedBitmask) {
        // If we've made the total number of moves, check if we're at the destination
        if (moveIndex == path.length()) {
            if (row == GRID_SIZE - 1 && col == 0) {  // Target position (7, 0)
                totalPaths++;  // Increment valid path count
            }
            return;
        }

        // Create a memoization key to store the result of subproblems
        String key = row + "," + col + "," + visitedBitmask;
        if (memo.containsKey(key)) {
            return;  // Skip this state if we've already computed it
        }

        // Mark the current state as visited in the memoization map
        memo.put(key, totalPaths);

        char direction = path.charAt(moveIndex);

        // If it's a wildcard ('*'), try all directions
        if (direction == '*') {
            for (char dir : new char[]{'D', 'U', 'L', 'R'}) {
                exploreMove(row, col, dir, path, moveIndex, visitedBitmask);
            }
        } else {
            // Otherwise, follow the given direction
            exploreMove(row, col, direction, path, moveIndex, visitedBitmask);
        }
    }

    private static void exploreMove(int row, int col, char direction, String path, int moveIndex, long visitedBitmask) {
        int newRow = row, newCol = col;

        // Apply movement based on the direction
        switch (direction) {
            case 'D': newRow++; break;
            case 'U': newRow--; break;
            case 'L': newCol--; break;
            case 'R': newCol++; break;
        }

        // Ensure the move is within bounds and hasn't been visited yet
        if (newRow >= 0 && newRow < GRID_SIZE && newCol >= 0 && newCol < GRID_SIZE) {
            long cellBit = 1L << (newRow * GRID_SIZE + newCol);  // Calculate bit for the new cell

            if ((visitedBitmask & cellBit) == 0) {  // If the cell hasn't been visited yet
                // Mark the new cell as visited
                long newVisitedBitmask = visitedBitmask | cellBit;

                // Recurse for the next move
                dfs(path, newRow, newCol, moveIndex + 1, newVisitedBitmask);
            }
        }
    }
}
