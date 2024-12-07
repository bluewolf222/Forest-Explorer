import java.util.*;

public class HamiltonianPath {

    // Directions for Up, Down, Left, Right
    static int[] dr = {-1, 1, 0, 0}; // Row changes for U, D, L, R
    static int[] dc = {0, 0, -1, 1}; // Column changes for U, D, L, R
    static char[] direction = {'U', 'D', 'L', 'R'}; // Directions for output

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        // Initialize variables
        List<String> paths = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        StringBuilder path = new StringBuilder();

        // Generate paths starting from (0,0) in Grid 1
        generatePaths(0, 0, visited, paths, path);

        // Output the result
        System.out.println("Total valid paths: " + paths.size());
        System.out.println("Time taken (ms): " + (System.currentTimeMillis() - startTime));
    }

    // Recursive function to generate Hamiltonian paths
    public static void generatePaths(int r, int c, Set<String> visited, List<String> paths, StringBuilder path) {
        // Debugging output: print the current position and the current path
        System.out.println("Visiting: " + r + "," + c + " with path: " + path.toString());

        // Base case: if we reach the destination (row 7, column 0 of Grid 4)
        if (r == 7 && c == 0 && visited.size() == 63) {
            System.out.println("Found valid path! Total paths: " + paths.size());
            paths.add(path.toString());
            return;
        }

        // Try all four possible directions (Up, Down, Left, Right)
        for (int i = 0; i < 4; i++) {
            int newR = r + dr[i];  // Calculate the new row position based on direction
            int newC = c + dc[i];  // Calculate the new column position based on direction

            // Print the current direction and new position
            System.out.println("Attempting move to: " + newR + "," + newC);

            // Check if the new move is valid (within bounds and not visited)
            if (isValidMove(newR, newC, visited)) {
                String position = newR + "," + newC;  // Encode the new position
                visited.add(position);  // Mark the new cell as visited
                path.append(direction[i]);  // Add the direction to the path

                // Recurse to explore further moves from the new cell
                generatePaths(newR, newC, visited, paths, path);

                // Backtrack: remove the last added move and cell from visited set
                path.deleteCharAt(path.length() - 1);  // Remove the last direction
                visited.remove(position);  // Unmark the cell as visited
            }
        }
    }

    // Check if a move is valid: within bounds of the grid and not visited
    public static boolean isValidMove(int r, int c, Set<String> visited) {
        // Check if the move is within the bounds of the 8x8 grid (across all 4 2x8 grids)
        if (r < 0 || r > 7 || c < 0 || c > 7) {
            return false;  // Out of bounds
        }

        String position = r + "," + c;  // Encode position as a string
        return !visited.contains(position);  // Return true if not visited
    }
}
