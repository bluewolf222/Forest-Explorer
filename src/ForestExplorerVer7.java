import java.util.ArrayList;
import java.util.List;

public class ForestExplorerVer7 {

    private static final int GRID_SIZE = 8;
    private static final int PART1_ROWS = 6;  // Rows 0 to 5
    private static final int TOTAL_MOVES = 63;
    private static int totalPaths = 0;

    public static void main(String[] args) {
        String input = "***************************************************************";

        // Validate input
        if (input.length() != TOTAL_MOVES || !input.matches("[UDLR*]+")) {
            System.out.println("Invalid input");
            return;
        }

        long startTime = System.currentTimeMillis();

        // Step 1: Generate paths for the 6x8 grid
        List<PathResult> part1Results = new ArrayList<>();
        explorePaths6x8(input, 0, 0, 0, 1L, part1Results); // Start at (0, 0) with bitmask visited

        // Step 2: Precompute paths for the 2x8 grid
        List<String> part2Paths = precompute2x8Paths();

        // Step 3: Combine the results
        combinePaths(part1Results, part2Paths);

        long endTime = System.currentTimeMillis();
        System.out.println("Total paths: " + totalPaths);
        System.out.println("Time (ms): " + (endTime - startTime));
    }

    // Step 1: Generate paths for the 6x8 grid
    private static void explorePaths6x8(String path, int row, int col, int moveIndex, long visited, List<PathResult> results) {
        if (row == PART1_ROWS - 1) { // Stop at row 5
            results.add(new PathResult(path.substring(0, moveIndex), row, col, visited));
            return;
        }

        char direction = path.charAt(moveIndex);
        if (direction == '*') {
            for (char dir : new char[]{'D', 'U', 'L', 'R'}) {
                move(row, col, dir, path, moveIndex, visited, results);
            }
        } else {
            move(row, col, direction, path, moveIndex, visited, results);
        }
    }

    private static void move(int row, int col, char direction, String path, int moveIndex, long visited, List<PathResult> results) {
        int newRow = row, newCol = col;
        switch (direction) {
            case 'D': newRow++; break;
            case 'U': newRow--; break;
            case 'L': newCol--; break;
            case 'R': newCol++; break;
        }

        if (newRow >= 0 && newRow < PART1_ROWS && newCol >= 0 && newCol < GRID_SIZE) {
            int newBit = newRow * GRID_SIZE + newCol;
            if ((visited & (1L << newBit)) == 0) {  // Check if not visited
                explorePaths6x8(path, newRow, newCol, moveIndex + 1, visited | (1L << newBit), results);
            }
        }
    }

    // Step 2: Precompute paths for the 2x8 grid
    private static List<String> precompute2x8Paths() {
        List<String> paths = new ArrayList<>();
        boolean[][] visited = new boolean[2][GRID_SIZE]; // Tracks visited cells for the 2x8 grid
        generatePaths2x8Recursive(6, 0, "", visited, paths); // Start at (6, 0)
        return paths;
    }

    private static void generatePaths2x8Recursive(int row, int col, String currentPath, boolean[][] visited, List<String> paths) {
        // Base case: if we've visited all 16 cells, add the path to the list
        if (currentPath.length() == 16) { // 16 cells in the 2x8 grid
            paths.add(currentPath);
            return;
        }

        // Mark the current cell as visited
        visited[row - 6][col] = true;

        // Define movement directions
        int[] dRow = {1, -1, 0, 0}; // Down, Up, Right, Left
        int[] dCol = {0, 0, 1, -1};
        char[] directions = {'D', 'U', 'R', 'L'};

        // Explore all possible moves
        for (int i = 0; i < 4; i++) {
            int newRow = row + dRow[i];
            int newCol = col + dCol[i];

            // Check if the new cell is within the 2x8 grid bounds and not visited
            if (newRow >= 6 && newRow < 8 && newCol >= 0 && newCol < GRID_SIZE && !visited[newRow - 6][newCol]) {
                generatePaths2x8Recursive(newRow, newCol, currentPath + directions[i], visited, paths);
            }
        }

        // Backtrack: unmark the current cell
        visited[row - 6][col] = false;
    }

    // Step 3: Combine paths from the two parts
    private static void combinePaths(List<PathResult> part1Results, List<String> part2Paths) {
        for (PathResult part1 : part1Results) {
            for (String part2 : part2Paths) {
                if (isCompatible(part1, part2)) {
                    totalPaths++;
                }
            }
        }
    }

    // Check compatibility between part1 and part2 paths
    private static boolean isCompatible(PathResult part1, String part2) {
        // Compatibility logic: ensure part2 starts where part1 ends
        int part2StartBit = part2StartBit(part2);
        return (part1.visited & (1L << part2StartBit)) == 0;  // No overlap
    }

    private static int part2StartBit(String part2) {
        // Map part2 starting position to its bit index (row 5, any column)
        return 5 * GRID_SIZE; // Modify logic if precomputed paths differ
    }

    // Helper class to store results of the first part
    private static class PathResult {
        String path;
        int row, col;
        long visited;

        PathResult(String path, int row, int col, long visited) {
            this.path = path;
            this.row = row;
            this.col = col;
            this.visited = visited;
        }
    }
}