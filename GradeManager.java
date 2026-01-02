/**
 * COMPREHENSIVE STUDENT GRADE MANAGEMENT SYSTEM
 * Demonstrates all concepts covered in the lesson
 */

// 1. CLASS DEFINITION WITH INHERITANCE
class Person {
    protected String name;  // Accessible in subclasses
    protected int id;
    
    // Constructor (not covered but practical)
    public Person(String name, int id) {
        this.name = name;
        this.id = id;
    }
}

// 2. MAIN CLASS EXTENDING PERSON
class Student extends Person {
    // 3. INSTANCE VARIABLES
    private int[] grades;           // Array of grades
    private String major;           // Student's major
    
    // 4. CLASS VARIABLES (STATIC)
    private static int studentCount = 0;      // Tracks all students
    public static final int MAX_COURSES = 10; // Constant
    
    // 5. CONSTRUCTOR
    public Student(String name, int id, String major, int[] grades) {
        super(name, id);            // Call parent constructor
        this.major = major;
        this.grades = grades;
        studentCount++;             // Increment class variable
    }
    
    // 6. INSTANCE METHOD WITH PARAMETERS AND RETURN VALUE
    public double calculateAverage() {
        if (grades.length == 0) return 0.0;
        
        int sum = 0;
        for (int i = 0; i < grades.length; i++) {
            sum += grades[i];      // Access instance variable
        }
        return (double) sum / grades.length;  // Return calculated value
    }
    
    // 7. METHOD DEMONSTRATING this KEYWORD
    public Student updateMajor(String newMajor) {
        this.major = newMajor;     // Explicit this reference
        return this;               // Return current object
    }
    
    // 8. METHOD WITH PASS-BY-REFERENCE BEHAVIOR
    public void curveGrades(int curveAmount) {
        for (int i = 0; i < this.grades.length; i++) {
            this.grades[i] += curveAmount;  // Modifies original array
            // Boundary check
            if (this.grades[i] > 100) this.grades[i] = 100;
        }
    }
    
    // 9. CLASS METHOD (STATIC)
    public static int getStudentCount() {
        return studentCount;       // Access class variable
    }
    
    // 10. OVERRIDDEN toString() METHOD
    public String toString() {
        return "Student: " + this.name + 
               " (ID: " + this.id + 
               ", Major: " + this.major + 
               ", Avg: " + this.calculateAverage() + ")";
    }
}

// 11. APPLICATION CLASS WITH main() METHOD
public class GradeManager {
    // 12. CONSTANTS
    private static final String[] MAJORS = {"CS", "ENG", "MATH", "BIOL"};
    
    // 13. UTILITY CLASS METHOD
    private static Student createSampleStudent(int index) {
        String name = "Student" + (index + 1);
        int id = 1000 + index;
        String major = MAJORS[index % MAJORS.length];
        
        // Create sample grades
        int[] grades = new int[4];
        for (int i = 0; i < grades.length; i++) {
            grades[i] = 70 + (int)(Math.random() * 30);  // 70-100
        }
        
        return new Student(name, id, major, grades);
    }
    
    // 14. MAIN APPLICATION ENTRY POINT
    public static void main(String[] args) {
        System.out.println("=== STUDENT GRADE MANAGEMENT SYSTEM ===\n");
        
        // 15. COMMAND-LINE ARGUMENT HANDLING
        int numStudents = 3;  // Default value
        if (args.length > 0) {
            try {
                numStudents = Integer.parseInt(args[0]);  // String to int
                System.out.println("Creating " + numStudents + " students (from args)\n");
            } catch (NumberFormatException e) {
                System.out.println("Invalid argument. Using default: 3 students\n");
            }
        }
        
        // 16. CREATE STUDENT ARRAY
        Student[] students = new Student[numStudents];
        
        // 17. INITIALIZE STUDENTS
        for (int i = 0; i < students.length; i++) {
            students[i] = createSampleStudent(i);
        }
        
        // 18. DEMONSTRATE INSTANCE METHOD CALLS
        System.out.println("STUDENT INFORMATION:");
        for (int i = 0; i < students.length; i++) {
            System.out.println((i+1) + ". " + students[i]);
            System.out.println("   Grades average: " + 
                             students[i].calculateAverage());
        }
        
        // 19. DEMONSTRATE PASS-BY-REFERENCE
        System.out.println("\nAPPLYING GRADE CURVE (+5 points):");
        students[0].curveGrades(5);  // Modifies original grades array
        System.out.println("Updated average for " + students[0].name + 
                          ": " + students[0].calculateAverage());
        
        // 20. DEMONSTRATE this KEYWORD USAGE
        System.out.println("\nUPDATING MAJOR:");
        Student s = students[1];
        System.out.println("Before: " + s);
        s.updateMajor("PHYSICS");  // Method returns this
        System.out.println("After: " + s);
        
        // 21. DEMONSTRATE CLASS METHOD
        System.out.println("\nCLASS STATISTICS:");
        System.out.println("Total students created: " + 
                          Student.getStudentCount());  // Static method call
        
        // 22. DEMONSTRATE CONSTANT USAGE
        System.out.println("\nSYSTEM CONSTANTS:");
        System.out.println("Maximum courses per student: " + 
                          Student.MAX_COURSES);
        
        // 23. ADDITIONAL ARGUMENT PROCESSING DEMONSTRATION
        if (args.length > 1) {
            System.out.println("\nADDITIONAL ARGUMENTS:");
            for (int i = 1; i < args.length; i++) {
                System.out.println("  Arg " + i + ": " + args[i]);
            }
        }
        
        System.out.println("\n=== PROGRAM COMPLETE ===");
    }
}
