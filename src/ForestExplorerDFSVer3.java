import java.util.LinkedList;
import java.util.Queue;

public class ForestExplorerDFSVer3 {
    private static final int GRID_SIZE = 8;  // 8x8 grid
    private static final int TOTAL_MOVES = 63;  // 63 moves to make
    private static int totalPaths = 0;

    public static void main(String[] args) {
        String input = "***************************************************************";  // Example input path

        long startTime = System.currentTimeMillis();
        bfs(input);  // Start BFS
        long endTime = System.currentTimeMillis();

        System.out.println("Total paths: " + totalPaths);
        System.out.println("Time (ms): " + (endTime - startTime));
    }

    // BFS approach to explore all possible paths
    private static void bfs(String path) {
        Queue<State> queue = new LinkedList<>();
        long startBitmask = 1L;  // The starting cell (0, 0) is visited
        queue.add(new State(0, 0, 0, startBitmask));  // Start BFS from (0, 0) with initial visited state

        while (!queue.isEmpty()) {
            State current = queue.poll();

            // If we've made the total number of moves, check if we're at the destination
            if (current.moveIndex == TOTAL_MOVES) {
                if (current.row == GRID_SIZE - 1 && current.col == 0) {  // Target position (7, 0)
                    totalPaths++;  // Increment valid path count
                }
                continue;  // Continue exploring other paths
            }

            char direction = path.charAt(current.moveIndex);
            if (direction == '*') {
                // Try all directions if it's a wildcard '*'
                for (char dir : new char[]{'D', 'U', 'L', 'R'}) {
                    exploreMove(current, dir, path, queue);
                }
            } else {
                // Otherwise, follow the given direction
                exploreMove(current, direction, path, queue);
            }
        }
    }

    // Explore a move in a given direction and enqueue the new state
    private static void exploreMove(State current, char direction, String path, Queue<State> queue) {
        int newRow = current.row, newCol = current.col;

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

            if ((current.visitedBitmask & cellBit) == 0) {  // If the cell hasn't been visited yet
                // Mark the new cell as visited
                long newVisitedBitmask = current.visitedBitmask | cellBit;

                // Enqueue the new state for further exploration
                queue.add(new State(newRow, newCol, current.moveIndex + 1, newVisitedBitmask));
            }
        }
    }

    // State class to keep track of current position, move index, and visited cells
    static class State {
        int row, col, moveIndex;
        long visitedBitmask;

        State(int row, int col, int moveIndex, long visitedBitmask) {
            this.row = row;
            this.col = col;
            this.moveIndex = moveIndex;
            this.visitedBitmask = visitedBitmask;
        }
    }
}
