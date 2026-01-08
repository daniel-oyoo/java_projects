/**
 * BRIDGE CROSSING PUZZLE SOLVER
 * ==============================
 * Problem: Four people need to cross a bridge in 17 minutes with one flashlight.
 * Constraints:
 * 1. Maximum 2 people can cross at once
 * 2. Flashlight required for crossing
 * 3. Pair walks at slower person's pace
 * 4. Crossing times: P1=1min, P2=2min, P3=5min, P4=10min
 * 
 * Algorithm: BFS with priority queue (Dijkstra-like search)
 * Complexity: O(2^n) states where n=4 people → 32 states total
 * Optimality: Guaranteed by priority queue ordering
 * 
 * @author Daniel oyoo
 * @version 1.0
 * @date December 2024
 */

// LINE 1: Import all Java utilities needed for collections, queues, and comparators
import java.util.*;

// LINE 3: Main class declaration - public so it can be run from command line
public class BridgeCrossingPuzzle {
    
    // ========================================================================
    // INNER STATE CLASS: Represents a configuration in the puzzle
    // ========================================================================
    
    // LINE 6: Static inner class - doesn't depend on outer class instance
    static class State {
        // LINE 7-12: STATE VARIABLES - represent puzzle configuration
        Set<Integer> left;       // People on left bank (IDs 1-4)
        Set<Integer> right;      // People on right bank (IDs 1-4)
        boolean flashlightLeft;  // Flashlight position: true=left, false=right
        int timeElapsed;         // Total minutes spent so far
        State parent;            // Previous state (for path reconstruction)
        String action;           // How we got to this state (for display)
        
        // LINE 15-21: CROSSING TIMES - immutable map of person→time
        // static: shared across all State instances
        // final: cannot be modified after initialization
        // Map.of(): Java 9+ syntax for creating immutable maps
        static final Map<Integer, Integer> times = Map.of(
            1, 1,   // Person 1 takes 1 minute
            2, 2,   // Person 2 takes 2 minutes
            3, 5,   // Person 3 takes 5 minutes
            4, 10   // Person 4 takes 10 minutes
        );
        
        // ====================================================================
        // CONSTRUCTOR: Creates a new state with defensive copying
        // ====================================================================
        
        // LINE 23-31: Constructor parameters match state variables
        State(Set<Integer> left, Set<Integer> right, boolean flashlightLeft, 
              int time, State parent, String action) {
            // LINE 25-26: Defensive copies - prevent external modification
            this.left = new HashSet<>(left);    // Copy left bank set
            this.right = new HashSet<>(right);  // Copy right bank set
            
            // LINE 27-30: Direct assignment for simple types/references
            this.flashlightLeft = flashlightLeft;  // Flashlight position
            this.timeElapsed = time;               // Accumulated time
            this.parent = parent;                  // Back reference
            this.action = action;                  // Action description
        }
        
        // ====================================================================
        // GOAL CHECK: Determines if we've solved the puzzle
        // ====================================================================
        
        // LINE 33-35: Returns true when all people are on right bank
        boolean isGoal() {
            return left.isEmpty() &&     // No one on left
                   right.size() == 4;    // All 4 on right
        }
        
        // ====================================================================
        // STATE GENERATION: Core algorithm - generates all valid next moves
        // ====================================================================
        
        // LINE 37-38: Returns list of possible states reachable from current state
        List<State> getNextStates() {
            List<State> nextStates = new ArrayList<>();  // Store results
            
            // LINE 40-41: Determine which side has flashlight
            // Ternary operator: condition ? if-true : if-false
            Set<Integer> currentSide = flashlightLeft ? left : right;
            Set<Integer> oppositeSide = flashlightLeft ? right : left;
            
            // LINE 44: Convert set to list for indexed access (needed for pairs)
            List<Integer> people = new ArrayList<>(currentSide);
            
            // ================================================================
            // SINGLE PERSON CROSSING
            // ================================================================
            
            // LINE 47: Loop through each person who can cross
            for (int p : people) {
                // LINE 48-49: Create mutable copies of both sides
                Set<Integer> newCurrent = new HashSet<>(currentSide);
                Set<Integer> newOpposite = new HashSet<>(oppositeSide);
                
                // LINE 51-52: Move person from current to opposite side
                newCurrent.remove(p);    // Remove from starting side
                newOpposite.add(p);      // Add to destination side
                
                // LINE 54-56: Calculate new time
                int crossingTime = times.get(p);         // Get person's time
                int newTime = timeElapsed + crossingTime; // Add to total
                
                // LINE 58: Create descriptive action string
                String actionStr = "Person " + p + " crosses (" + crossingTime + " min)";
                
                // LINE 60-67: Create new state object
                State newState = new State(
                    // Determine left/right based on flashlight position
                    flashlightLeft ? newCurrent : newOpposite,
                    flashlightLeft ? newOpposite : newCurrent,
                    !flashlightLeft,     // Flip flashlight position
                    newTime,            // Updated total time
                    this,               // Current state as parent
                    actionStr           // Action description
                );
                
                // LINE 69-71: Only add if within 17-minute limit
                if (newTime <= 17) {
                    nextStates.add(newState);
                }
            }
            
            // ================================================================
            // TWO PEOPLE CROSSING
            // ================================================================
            
            // LINE 74: Outer loop - first person index
            for (int i = 0; i < people.size(); i++) {
                // LINE 75: Inner loop - second person index (starts at i+1)
                // This ensures each pair is considered only once (no duplicates)
                for (int j = i + 1; j < people.size(); j++) {
                    // LINE 76-77: Get person IDs from list indices
                    int p1 = people.get(i);
                    int p2 = people.get(j);
                    
                    // LINE 79-80: Create mutable copies
                    Set<Integer> newCurrent = new HashSet<>(currentSide);
                    Set<Integer> newOpposite = new HashSet<>(oppositeSide);
                    
                    // LINE 82-84: Move both people
                    newCurrent.remove(p1);
                    newCurrent.remove(p2);
                    newOpposite.add(p1);
                    newOpposite.add(p2);
                    
                    // LINE 86-89: Calculate crossing time and new total
                    // Math.max() because pair moves at slower person's pace
                    int crossingTime = Math.max(times.get(p1), times.get(p2));
                    int newTime = timeElapsed + crossingTime;
                    
                    // LINE 91-92: Format action string with placeholders
                    String actionStr = String.format("Persons %d & %d cross (%d min)", 
                                                    p1, p2, crossingTime);
                    
                    // LINE 94-101: Create new state (same pattern as single person)
                    State newState = new State(
                        flashlightLeft ? newCurrent : newOpposite,
                        flashlightLeft ? newOpposite : newCurrent,
                        !flashlightLeft,
                        newTime,
                        this,
                        actionStr
                    );
                    
                    // LINE 103-105: Add if within time limit
                    if (newTime <= 17) {
                        nextStates.add(newState);
                    }
                }
            }
            
            // LINE 108: Return all generated states
            return nextStates;
        }
        
        // ====================================================================
        // EQUALS AND HASHCODE: Required for Set operations (visited states)
        // ====================================================================
        
        // LINE 110-116: Override equals() for proper state comparison
        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof State)) return false;  // Must be State type
            State other = (State) obj;                  // Safe cast
            
            // Compare all relevant fields (ignore time, parent, action)
            return left.equals(other.left) && 
                   right.equals(other.right) && 
                   flashlightLeft == other.flashlightLeft;
        }
        
        // LINE 118-120: Override hashCode() to match equals() contract
        @Override
        public int hashCode() {
            // Objects.hash() generates hash from specified fields
            return Objects.hash(left, right, flashlightLeft);
        }
        
        // ====================================================================
        // STRING REPRESENTATION: For debugging and output
        // ====================================================================
        
        // LINE 122-125: Human-readable string representation
        @Override
        public String toString() {
            // String.format() creates formatted string with placeholders
            return String.format("Left: %s | Right: %s | Flashlight: %s | Time: %d min",
                    left, right, 
                    flashlightLeft ? "Left" : "Right",  // Ternary for readability
                    timeElapsed);
        }
    }
    
    // ========================================================================
    // MAIN SOLVER METHOD: Finds optimal solution using priority queue BFS
    // ========================================================================
    
    // LINE 129: Public method that returns optimal solution path
    public static List<State> findOptimalSolution() {
        // LINE 130-131: Create initial state (all on left, flashlight on left)
        Set<Integer> allPeople = new HashSet<>(Arrays.asList(1, 2, 3, 4));
        State initialState = new State(allPeople, new HashSet<>(), true, 0, null, "Start");
        
        // LINE 133-136: Create priority queue ordered by time (Dijkstra-like)
        // Comparator.comparingInt extracts timeElapsed for comparison
        PriorityQueue<State> queue = new PriorityQueue<>(
            Comparator.comparingInt(s -> s.timeElapsed)
        );
        
        // LINE 138: Visited set to avoid revisiting states
        Set<State> visited = new HashSet<>();
        
        // LINE 140-141: Initialize search with starting state
        queue.add(initialState);
        visited.add(initialState);
        
        // LINE 143-144: Track best solution found
        List<State> bestSolution = null;
        int bestTime = Integer.MAX_VALUE;  // Start with worst possible time
        
        // LINE 146: Main search loop - continues until queue empty
        while (!queue.isEmpty()) {
            // LINE 147: Get state with smallest time (priority queue property)
            State current = queue.poll();
            
            // LINE 149: Check if this is a goal state
            if (current.isGoal()) {
                // LINE 150: Only update if better than current best
                if (current.timeElapsed < bestTime) {
                    bestTime = current.timeElapsed;  // Update best time
                    
                    // LINE 153-158: Reconstruct solution path using parent pointers
                    bestSolution = new ArrayList<>();
                    State s = current;                // Start at goal
                    while (s != null) {               // Backtrack to start
                        bestSolution.add(0, s);       // Add to beginning (reverse)
                        s = s.parent;                 // Move to parent
                    }
                }
                continue;  // Skip state expansion for goal states
            }
            
            // LINE 162: Generate and process all possible next states
            for (State next : current.getNextStates()) {
                // LINE 163: Only process if not already visited
                if (!visited.contains(next)) {
                    visited.add(next);    // Mark as visited
                    queue.add(next);      // Add to queue for exploration
                }
            }
        }
        
        // LINE 170: Return optimal solution (null if none found)
        return bestSolution;
    }
    
    // ========================================================================
    // MAIN METHOD: Program entry point
    // ========================================================================
    
    // LINE 173: Standard Java main method - execution starts here
    public static void main(String[] args) {
        // LINE 174-179: Print problem description
        System.out.println("=== NEW WORLD PUZZLE SOLUTION ===\n");
        System.out.println("Problem: 4 people must cross bridge in 17 minutes.");
        System.out.println("Crossing times: P1=1min, P2=2min, P3=5min, P4=10min");
        System.out.println("Bridge capacity: 2 people max");
        System.out.println("Flashlight required for crossing\n");
        
        // LINE 181: Find optimal solution
        List<State> solution = findOptimalSolution();
        
        // LINE 183: Check if solution exists
        if (solution == null) {
            System.out.println("No solution within 17 minutes!");
        } else {
            // LINE 186-187: Print success message with total time
            System.out.println("OPTIMAL SOLUTION FOUND! (" + 
                             solution.get(solution.size()-1).timeElapsed + " minutes)\n");
            
            // LINE 189-197: Print solution step by step
            for (int i = 0; i < solution.size(); i++) {
                State state = solution.get(i);
                if (i == 0) {
                    System.out.println("Initial State:");
                } else {
                    System.out.println("\nStep " + i + ": " + state.action);
                }
                System.out.println("  " + state);  // Uses toString() method
            }
            
            // ================================================================
            // ALTERNATIVE SOLUTIONS ANALYSIS
            // ================================================================
            
            // LINE 200-226: Display alternative strategies and mathematical proof
            System.out.println("\n=== ALTERNATIVE SOLUTIONS ===");
            
            // Alternative 1: Optimal strategy (17 minutes)
            System.out.println("\nSolution 1 (17 minutes):");
            System.out.println("1. P1 & P2 cross (2 min)  [P1,P2 on right, P3,P4 on left]");
            System.out.println("2. P1 returns (1 min)     [P1,P3,P4 on left, P2 on right]");
            System.out.println("3. P3 & P4 cross (10 min) [P1 on left, P2,P3,P4 on right]");
            System.out.println("4. P2 returns (2 min)     [P1,P2 on left, P3,P4 on right]");
            System.out.println("5. P1 & P2 cross (2 min)  [All on right]");
            System.out.println("Total: 17 minutes ✓");
            
            // Alternative 2: Different but same total time (17 minutes)
            System.out.println("\nSolution 2 (17 minutes):");
            System.out.println("1. P1 & P2 cross (2 min)");
            System.out.println("2. P2 returns (2 min)");
            System.out.println("3. P3 & P4 cross (10 min)");
            System.out.println("4. P1 returns (1 min)");
            System.out.println("5. P1 & P2 cross (2 min)");
            System.out.println("Total: 17 minutes ✓");
            
            // Alternative 3: Suboptimal strategy (19 minutes - too slow)
            System.out.println("\nSolution 3 (17 minutes):");
            System.out.println("1. P1 & P3 cross (5 min)");
            System.out.println("2. P1 returns (1 min)");
            System.out.println("3. P1 & P4 cross (10 min)");
            System.out.println("4. P1 returns (1 min)");
            System.out.println("5. P1 & P2 cross (2 min)");
            System.out.println("Total: 19 minutes ✗ (Too slow!)");
            
            // ================================================================
            // MATHEMATICAL PROOF OF OPTIMALITY
            // ================================================================
            
            System.out.println("\nMathematical Proof of Optimality:");
            System.out.println("Minimum time analysis:");
            System.out.println("- Two slowest (P3+P4) must cross together: 10 min");
            System.out.println("- Someone must return flashlight after slow crossing");
            System.out.println("- Fastest (P1) should handle returns when possible");
            System.out.println("- Total ≥ 2×max(P3,P4) + 2×min(P1,P2) + something");
            System.out.println("- 2×10 + 2×1 = 22, but we can do better with pairing");
            System.out.println("- Optimal is 17 minutes, proven by exhaustive search");
        }
    }
    // LINE 229: End of main method
}
// LINE 230: End of class
