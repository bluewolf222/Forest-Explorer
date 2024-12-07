public class ForestExplorer {
    private static final int GRID_SIZE = 6;
    private static final int TOTAL_MOVES = 35;
    private static boolean[][] visited = new boolean[GRID_SIZE][GRID_SIZE];
    private static int totalPaths = 0;

    public static void main(String[] args) {
        String input = "***********************************";

        // Validate input
        if (input.length() != TOTAL_MOVES || !input.matches("[UDLR*]+")) {
            System.out.println("Invalid input");
            return;
        }

        long startTime = System.currentTimeMillis();
        visited[0][0] = true;  // Start at (0, 0)
        explorePaths(input, 0, 0, 0);
        long endTime = System.currentTimeMillis();

        System.out.println("Total paths: " + totalPaths);
        System.out.println("Time (ms): " + (endTime - startTime));
    }

    private static void explorePaths(String path, int row, int col, int moveIndex) {
        //System.out.println("Exploring: row=" + row + ", col=" + col + ", moveIndex=" + moveIndex);
        if (moveIndex == TOTAL_MOVES) {
            if (row == 5 && col == 0) {
                //System.out.println("_______________THE RESULT IS: row=" + row + ", col=" + col + ", moveIndex=" + moveIndex);
                System.out.println("Found valid path! Total paths: " + totalPaths);
                totalPaths++;
            }
            return;
        }

        // Late pruning strategy to avoid early cutoff
//        if (moveIndex >= TOTAL_MOVES - 10) {
//            if (isDeadEnd(row, col) || !canReach(row, col, TOTAL_MOVES - moveIndex)) {
//                return;  // Prune paths if unreachable or in dead-end later
//            }
//        }

        char direction = path.charAt(moveIndex);
        if (direction == '*') {
            for (char dir : new char[]{'D', 'U', 'L', 'R'}) {
                move(row, col, dir, path, moveIndex);
            }
        } else {
            move(row, col, direction, path, moveIndex);
        }
    }

    private static boolean isDeadEnd(int row, int col) {
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (newRow >= 0 && newRow < GRID_SIZE && newCol >= 0 && newCol < GRID_SIZE && !visited[newRow][newCol]) {
                return false; // There is at least one unvisited neighbor
            }
        }
        return true; // All neighbors are visited or out of bounds
    }

    private static boolean canReach(int row, int col, int remainingMoves) {
        int distanceToGoal = Math.abs(7 - row) + Math.abs(0 - col);
        return distanceToGoal <= remainingMoves;
    }

    private static void move(int row, int col, char direction, String path, int moveIndex) {
        int newRow = row, newCol = col;

        switch (direction) {
            case 'D': newRow++; break;
            case 'U': newRow--; break;
            case 'L': newCol--; break;
            case 'R': newCol++; break;
        }

        //System.out.println("Trying move: " + direction + " to row=" + newRow + ", col=" + newCol);

        if (newRow >= 0 && newRow < GRID_SIZE && newCol >= 0 && newCol < GRID_SIZE && !visited[newRow][newCol]) {
            visited[newRow][newCol] = true;
            explorePaths(path, newRow, newCol, moveIndex + 1);
            visited[newRow][newCol] = false;  // Backtrack
        }
    }
}
