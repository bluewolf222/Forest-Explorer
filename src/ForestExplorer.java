public class ForestExplorer {
    private static final int GRID_SIZE = 8;
    private static final int TOTAL_MOVES = 63;
    private static boolean[][] visited = new boolean[GRID_SIZE][GRID_SIZE];
    private static int totalPaths = 0;

    public static void main(String[] args) {
        String input = "***************************************************************";

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
        if (moveIndex == TOTAL_MOVES) {
            if (row == 7 && col == 0) totalPaths++;
            return;
        }

        char direction = path.charAt(moveIndex);
        if (direction == '*') {
            for (char dir : new char[]{'D', 'U', 'L', 'R'}) {
                move(row, col, dir, path, moveIndex);
            }
        } else {
            move(row, col, direction, path, moveIndex);
        }
    }

    private static void move(int row, int col, char direction, String path, int moveIndex) {
        int newRow = row, newCol = col;

        switch (direction) {
            case 'D': newRow++; break;
            case 'U': newRow--; break;
            case 'L': newCol--; break;
            case 'R': newCol++; break;
        }

        if (newRow >= 0 && newRow < GRID_SIZE && newCol >= 0 && newCol < GRID_SIZE && !visited[newRow][newCol]) {
            visited[newRow][newCol] = true;
            explorePaths(path, newRow, newCol, moveIndex + 1);
            visited[newRow][newCol] = false;  // Backtrack
        }
    }

}