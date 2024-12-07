public class ForestExplorerVer6 {
    private static final int GRID_SIZE = 6;
    private static final int TOTAL_MOVES = 35;
    private static int totalPaths = 0;

    public static void main(String[] args) {
        String input = "***********************************";

        // Validate input
        if (input.length() != TOTAL_MOVES || !input.matches("[UDLR*]+")) {
            System.out.println("Invalid input");
            return;
        }

        long startTime = System.currentTimeMillis();
        int startBit = 0;  // Start at (0, 0), represented by bit 0
        explorePaths(input, 0, 0, 0, 1L << startBit);  // Initial visited state
        long endTime = System.currentTimeMillis();

        System.out.println("Total paths: " + totalPaths);
        System.out.println("Time (ms): " + (endTime - startTime));
    }

    private static void explorePaths(String path, int row, int col, int moveIndex, long visited) {
        if (moveIndex == TOTAL_MOVES) {
            if (row == 5 && col == 0){
                System.out.println("Found valid path! Total paths: " + totalPaths);
                totalPaths++;
            }
            return;
        }


        char direction = path.charAt(moveIndex);
        if (direction == '*') {
            for (char dir : new char[]{'D', 'U', 'L', 'R'}) {
                move(row, col, dir, path, moveIndex, visited);
            }
        } else {
            move(row, col, direction, path, moveIndex, visited);
        }
    }

    private static void move(int row, int col, char direction, String path, int moveIndex, long visited) {
        int newRow = row, newCol = col;
        switch (direction) {
            case 'D': newRow++; break;
            case 'U': newRow--; break;
            case 'L': newCol--; break;
            case 'R': newCol++; break;
        }

        if (newRow >= 0 && newRow < GRID_SIZE && newCol >= 0 && newCol < GRID_SIZE) {
            int newBit = newRow * GRID_SIZE + newCol;
            if ((visited & (1L << newBit)) == 0) {  // Check if not visited
                explorePaths(path, newRow, newCol, moveIndex + 1, visited | (1L << newBit));
            }
        }
    }
}
