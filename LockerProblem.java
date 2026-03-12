/**
 * The Classic Locker Problem
 * --------------------------
 * Problem: 100 lockers (numbered 1-100) are initially closed.
 * Student 1 toggles every locker (1,2,3,...,100)
 * Student 2 toggles every 2nd locker (2,4,6,...,100)
 * Student 3 toggles every 3rd locker (3,6,9,...,100)
 * ...and so on until student 100
 * 
 * Which lockers are open at the end?
 * (A locker is open if it's toggled an ODD number of times)
 * 
 * The mathematical answer: Only perfect squares remain open
 * This program demonstrates WHY by tracking each toggle
 *sample output
 ==========================================
     THE CLASSIC LOCKER PROBLEM
==========================================
Simulating 5 lockers and 5 students
Each student toggles every nth locker
==========================================


--- Student 1 ---
  Student 1 toggles Locker 1 (index 0)
  Student 1 toggles Locker 2 (index 1)
  Student 1 toggles Locker 3 (index 2)
  Student 1 toggles Locker 4 (index 3)
  Student 1 toggles Locker 5 (index 4)
  Student 1 toggled 5 lockers total

--- Student 2 ---
  Student 2 toggles Locker 2 (index 1)
  Student 2 toggles Locker 4 (index 3)
  Student 2 toggled 2 lockers total

--- Student 3 ---
  Student 3 toggles Locker 3 (index 2)
  Student 3 toggled 1 lockers total

--- Student 4 ---
  Student 4 toggles Locker 4 (index 3)
  Student 4 toggled 1 lockers total

--- Student 5 ---
  Student 5 toggles Locker 5 (index 4)
  Student 5 toggled 1 lockers total


============= SIMULATION RESULTS =============

1. Lockers toggled PER STUDENT:
   Student counts: [5, 2, 1, 1, 1]
   (Index 0 = Student 1, Index 1 = Student 2, etc.)

2. Complete toggle history (in order):
   [0, 1, 2, 3, 4, 1, 3, 2, 3, 4]
   (Each number is a LOCKER INDEX that was toggled)

3. Final toggle counts PER LOCKER:
   Locker:        1    2    3    4    5 
   Toggles:       1    2    2    3    2 
   (Odd counts = OPEN, Even counts = CLOSED)


============= OPEN LOCKERS =============
(Lockers toggled an ODD number of times)

 Locker 1 is OPEN (toggled 1 time)
  Locker 2 is CLOSED (toggled 2 times)
  Locker 3 is CLOSED (toggled 2 times)
 Locker 4 is OPEN (toggled 3 times)
  Locker 5 is CLOSED (toggled 2 times)


============= PATTERN RECOGNITION =============
Notice that open lockers are PERFECT SQUARES:
Locker 1 = 1?, Locker 4 = 2?, Locker 9 = 3?, etc.

Why? Perfect squares have an ODD number of factors!
Example: 6 has factors (1,2,3,6) ? 4 factors (even)
         9 has factors (1,3,9) ? 3 factors (odd)
 */

import java.util.ArrayList;
import java.util.Arrays;

public class Test04 {
    
    // ============ PROGRAM DATA ============
    
    /**
     * Tracks how many lockers EACH STUDENT toggles
     * Index 0 = student 1's count, index 1 = student 2's count, etc.
     */
    static ArrayList<Integer> studentToggleCounts = new ArrayList<>();
    
    /**
     * Records EVERY locker toggle in sequence
     * Each entry is the LOCKER INDEX (0-based) that was toggled
     * Example: [0,1,2,3,4,1,3,2,3,4] means:
     *   First toggle: locker 1 (index 0)
     *   Second toggle: locker 2 (index 1)
     *   Third toggle: locker 3 (index 2)
     *   etc.
     */
    static ArrayList<Integer> toggleHistory = new ArrayList<>();
    
    /**
     * Number of lockers in our simulation
     * (Using 5 for testing clarity, but logic works for any number)
     */
    static final int NUMBER_OF_LOCKERS = 5;
    
    /**
     * Array that stores how many times EACH LOCKER was toggled
     * Index 0 = locker 1's count, index 1 = locker 2's count, etc.
     */
    static int[] lockerToggleCounts;
    

    // ============ MAIN PROGRAM ============
    
    public static void main(String[] args) {
        
        // Display problem header
        printHeader();
        
        // Initialize our tracking array
        lockerToggleCounts = new int[NUMBER_OF_LOCKERS];
        
        // Run the locker simulation
        simulateLockerToggling();
        
        // Display results
        printSimulationResults();
        analyzeOpenLockers();
    }
    

    // ============ SIMULATION METHODS ============
    
    /**
     * Simulates each student toggling lockers according to the rules
     * Student 1 toggles all lockers
     * Student 2 toggles every 2nd locker
     * Student 3 toggles every 3rd locker, etc.
     */
    private static void simulateLockerToggling() {
        
        // Each student takes a turn (student numbers 1 through NUMBER_OF_LOCKERS)
        for (int studentNumber = 1; studentNumber <= NUMBER_OF_LOCKERS; studentNumber++) {
            
            int lockersToggledByThisStudent = 0;  // Reset counter for this student
            
            System.out.println("\n--- Student " + studentNumber + " ---");
            
            // Student checks each locker to see if they should toggle it
            for (int lockerIndex = 0; lockerIndex < NUMBER_OF_LOCKERS; lockerIndex++) {
                
                // Locker numbers are 1-based, but array indices are 0-based
                // So locker 1 is index 0, locker 2 is index 1, etc.
                int lockerNumber = lockerIndex + 1;
                
                // CRITICAL LOGIC: Student toggles locker if lockerNumber is divisible by studentNumber
                // Example: Student 2 toggles lockers 2,4,6,... because 2%2==0, 4%2==0, 6%2==0
                if (lockerNumber % studentNumber == 0) {
                    
                    // This student toggles this locker
                    lockersToggledByThisStudent++;
                    
                    // Record this toggle in our history
                    toggleHistory.add(lockerIndex);
                    
                    // Also update the running count for this locker
                    lockerToggleCounts[lockerIndex]++;
                    
                    // Display what happened (for debugging/learning)
                    System.out.printf("  Student %d toggles Locker %d (index %d)%n", 
                                      studentNumber, lockerNumber, lockerIndex);
                }
            }
            
            // Store how many lockers this student toggled
            studentToggleCounts.add(lockersToggledByThisStudent);
            System.out.printf("  Student %d toggled %d lockers total%n", 
                              studentNumber, lockersToggledByThisStudent);
        }
    }
    

    // ============ ANALYSIS METHODS ============
    
    /**
     * Displays all the data collected during simulation
     */
    private static void printSimulationResults() {
        
        System.out.println("\n\n============= SIMULATION RESULTS =============");
        
        // Show how many lockers each student toggled
        System.out.println("\n1. Lockers toggled PER STUDENT:");
        System.out.println("   Student counts: " + studentToggleCounts);
        System.out.println("   (Index 0 = Student 1, Index 1 = Student 2, etc.)");
        
        // Show the complete toggle history
        System.out.println("\n2. Complete toggle history (in order):");
        System.out.println("   " + toggleHistory);
        System.out.println("   (Each number is a LOCKER INDEX that was toggled)");
        
        // Show the final counts per locker
        System.out.println("\n3. Final toggle counts PER LOCKER:");
        System.out.print("   Locker:     ");
        for (int i = 0; i < NUMBER_OF_LOCKERS; i++) {
            System.out.printf("%4d ", i + 1);
        }
        System.out.print("\n   Toggles:    ");
        for (int i = 0; i < NUMBER_OF_LOCKERS; i++) {
            System.out.printf("%4d ", lockerToggleCounts[i]);
        }
        System.out.println("\n   (Odd counts = OPEN, Even counts = CLOSED)");
    }
    
    /**
     * Determines which lockers are open based on toggle counts
     * A locker is open if toggled an ODD number of times
     */
    private static void analyzeOpenLockers() {
        
        System.out.println("\n\n============= OPEN LOCKERS =============");
        System.out.println("(Lockers toggled an ODD number of times)\n");
        
        boolean foundAny = false;
        
        for (int lockerIndex = 0; lockerIndex < NUMBER_OF_LOCKERS; lockerIndex++) {
            
            int toggleCount = lockerToggleCounts[lockerIndex];
            int lockerNumber = lockerIndex + 1;
            
            // KEY INSIGHT: Odd number of toggles = open
            if (toggleCount % 2 == 1) {
                System.out.printf(" Locker %d is OPEN (toggled %d time%s)%n", 
                                  lockerNumber, 
                                  toggleCount, 
                                  toggleCount == 1 ? "" : "s");
                foundAny = true;
            } else {
                System.out.printf("  Locker %d is CLOSED (toggled %d times)%n", 
                                  lockerNumber, toggleCount);
            }
        }
        
        if (!foundAny) {
            System.out.println("No lockers are open!");
        }
        
        // Show the mathematical pattern
        System.out.println("\n\n============= PATTERN RECOGNITION =============");
        System.out.println("Notice that open lockers are PERFECT SQUARES:");
        System.out.println("Locker 1 = 1², Locker 4 = 2², Locker 9 = 3², etc.");
        System.out.println("\nWhy? Perfect squares have an ODD number of factors!");
        System.out.println("Example: 6 has factors (1,2,3,6) → 4 factors (even)");
        System.out.println("         9 has factors (1,3,9) → 3 factors (odd)");
    }
    
    /**
     * Displays a fancy header
     */
    private static void printHeader() {
        System.out.println("==========================================");
        System.out.println("     THE CLASSIC LOCKER PROBLEM");
        System.out.println("==========================================");
        System.out.printf("Simulating %d lockers and %d students\n", 
                          NUMBER_OF_LOCKERS, NUMBER_OF_LOCKERS);
        System.out.println("Each student toggles every nth locker");
        System.out.println("==========================================\n");
    }
}
