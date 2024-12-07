public class Ver8 {
    private static final int GRID_SIZE = 8;
    private static int totalPaths = 0;

    public static void main(String[] args) {
        String input = "*****DR******R******R********************R*D************L******"; // Replace with your input
        if (!isValidInput(input)) {
            System.out.println("Invalid input. Ensure it is 63 characters of D, U, L, R, or *.");
            return;
        }

        long startTime = System.currentTimeMillis();

        boolean[][] visited = new boolean[GRID_SIZE][GRID_SIZE];
        visited[0][0] = true; // Start at (0, 0)

        explorePaths(input, 0, 0, 0, visited);

        long endTime = System.currentTimeMillis();

        System.out.println("Total paths: " + totalPaths);
        System.out.println("Time (ms): " + (endTime - startTime));
    }

    private static boolean isValidInput(String input) {
        if (input.length() != 63) return false;
        return input.chars().allMatch(c -> "DULR*".indexOf(c) != -1);
    }

    private static void explorePaths(String input, int row, int col, int moveIndex, boolean[][] visited) {
        // Base case
        if (moveIndex == 63) {
            if (row == 7 && col == 0) totalPaths++;
            return;
        }

        char direction = input.charAt(moveIndex);
        int[][] moves = getMoves(direction); // Possible moves based on direction or *

        for (int[] move : moves) {
            int newRow = row + move[0];
            int newCol = col + move[1];

            if (isValid(newRow, newCol, visited)) {
                visited[newRow][newCol] = true;
                explorePaths(input, newRow, newCol, moveIndex + 1, visited);
                visited[newRow][newCol] = false; // Backtrack
            }
        }
    }

    private static int[][] getMoves(char direction) {
        switch (direction) {
            case 'D': return new int[][] {{1, 0}};
            case 'U': return new int[][] {{-1, 0}};
            case 'L': return new int[][] {{0, -1}};
            case 'R': return new int[][] {{0, 1}};
            case '*': return new int[][] {{1, 0}, {-1, 0}, {0, -1}, {0, 1}};
            default: return new int[0][0]; // Should not reach here
        }
    }

    private static boolean isValid(int row, int col, boolean[][] visited) {
        return row >= 0 && row < GRID_SIZE && col >= 0 && col < GRID_SIZE && !visited[row][col];
    }
}
