// Import necessary classes
import java.util.ArrayList;
import java.util.List;

// Method 1: Extending Thread class
class Multithreading extends Thread {
    private int threadId;
    
    // Constructor to identify threads
    public Multithreading(int id) {
        this.threadId = id;
        this.setName("Thread-" + id); // Set thread name for identification
    }
    
    @Override
    public void run() {
        System.out.println("Thread " + threadId + " starting...");
        
        for(int i = 0; i < 5; i++) {
            System.out.println("Thread " + threadId + ": " + i);
            
            // Simulate some work
            try {
                Thread.sleep(500); // Sleep for 500ms between prints
            } catch(InterruptedException e) {
                // Handle interruption
                System.out.println("Thread " + threadId + " was interrupted!");
                return; // Exit thread if interrupted
            }
        }
        
        System.out.println("Thread " + threadId + " finished.");
    }
}

// Method 2: Implementing Runnable interface (alternative approach)
class MyRunnable implements Runnable {
    private int taskId;
    
    public MyRunnable(int id) {
        this.taskId = id;
    }
    
    @Override
    public void run() {
        System.out.println("Runnable Task " + taskId + " starting...");
        
        try {
            // Different work simulation
            for(int i = 0; i < 3; i++) {
                System.out.println("Task " + taskId + " - Step " + (i + 1));
                Thread.sleep(300);
            }
        } catch(InterruptedException e) {
            System.out.println("Task " + taskId + " interrupted!");
        }
        
        System.out.println("Runnable Task " + taskId + " completed.");
    }
}

// Main class with thread management
public class Main {
    public static void main(String[] args) {
        System.out.println("=== MULTITHREADING DEMONSTRATION ===\n");
        
        // PART 1: Basic Thread Creation
        System.out.println("1. Creating basic threads (Thread extension):");
        Multithreading mt1 = new Multithreading(1);
        Multithreading mt2 = new Multithreading(2);
        
        // Start threads (they run concurrently)
        mt1.start();
        mt2.start();
        
        // Wait for them to finish before continuing
        try {
            mt1.join();
            mt2.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        
        // PART 2: Creating multiple threads using a loop
        System.out.println("\n2. Creating 5 threads using a loop:");
        List<Thread> threadList = new ArrayList<>();
        
        for(int i = 0; i < 5; i++) {
            Thread thread = new Thread(new MyRunnable(i));
            threadList.add(thread);
            thread.start();
        }
        
        // PART 3: Print number of active threads
        System.out.println("\n3. Thread count information:");
        System.out.println("Active threads after starting: " + Thread.activeCount());
        System.out.println("Total threads created: " + (threadList.size() + 2));
        
        // PART 4: Make exception happen in one thread
        System.out.println("\n4. Demonstrating thread interruption:");
        
        // Create a special thread that will be interrupted
        Thread problematicThread = new Thread(() -> {
            try {
                System.out.println("Problematic thread starting long operation...");
                Thread.sleep(2000); // Simulate long operation
                System.out.println("Problematic thread completed successfully.");
            } catch(InterruptedException e) {
                System.out.println("Problematic thread got InterruptedException!");
                System.out.println("Exception message: " + e.getMessage());
            }
        });
        
        problematicThread.start();
        
        // Interrupt after a short delay
        try {
            Thread.sleep(500);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("Main thread interrupting problematic thread...");
        problematicThread.interrupt();
        
        // PART 5: Wait for all threads to complete
        System.out.println("\n5. Waiting for all threads to finish...");
        
        // Wait for loop-created threads
        for(Thread t : threadList) {
            try {
                t.join();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        // Wait for problematic thread
        try {
            problematicThread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        
        // PART 6: Demonstrating thread states and priority
        System.out.println("\n6. Thread states and priorities:");
        
        Thread lowPriorityThread = new Thread(() -> {
            System.out.println("Low priority thread running");
        });
        
        Thread highPriorityThread = new Thread(() -> {
            System.out.println("High priority thread running");
        });
        
        lowPriorityThread.setPriority(Thread.MIN_PRIORITY); // Priority 1
        highPriorityThread.setPriority(Thread.MAX_PRIORITY); // Priority 10
        
        lowPriorityThread.start();
        highPriorityThread.start();
        
        // Final thread count
        try {
            Thread.sleep(1000); // Wait a bit
            System.out.println("\nFinal active thread count: " + Thread.activeCount());
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\n=== PROGRAM COMPLETED ===");
    }
}
