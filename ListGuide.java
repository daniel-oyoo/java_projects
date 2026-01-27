package com.daniel.collections;

import java.util.*;

/**
 * Complete guide to Java Lists
 * Demonstrates ArrayList, LinkedList, and their common operations
 */
public class ListGuide {
    
    public static void main(String[] args) {
        System.out.println("=== JAVA LISTS COMPLETE GUIDE ===");
        
        // SECTION 1: ARRAYLIST (Most Common)
        System.out.println("\n--- 1. ARRAYLIST ---");
        
        // Line 1: Create ArrayList (Most common list type)
        // ArrayList uses dynamic array internally, fast for random access
        List<String> arrayList = new ArrayList<>();
        
        // Line 2: Add elements to list
        // add() appends to end, returns true if successful
        arrayList.add("Apple");
        arrayList.add("Banana");
        arrayList.add("Cherry");
        arrayList.add("Date");
        arrayList.add("Elderberry");
        
        // Line 3: Print list (toString() is automatically called)
        // ArrayList has a nice toString() implementation
        System.out.println("ArrayList: " + arrayList);
        
        // Line 4: Get element by index (FAST - O(1))
        // ArrayList stores elements in array, direct index access
        String secondElement = arrayList.get(1);
        System.out.println("Element at index 1: " + secondElement);
        
        // Line 5: Update element at index
        // set() returns the old element at that position
        String oldValue = arrayList.set(2, "Cranberry");
        System.out.println("Replaced '" + oldValue + "' with 'Cranberry'");
        
        // Line 6: Check if list contains element
        // Uses equals() method to check equality
        boolean hasApple = arrayList.contains("Apple");
        System.out.println("Contains 'Apple': " + hasApple);
        
        // Line 7: Find index of element
        // Returns first occurrence, -1 if not found
        int appleIndex = arrayList.indexOf("Apple");
        System.out.println("Index of 'Apple': " + appleIndex);
        
        // Line 8: Remove element by index
        // Removing shifts all elements after index
        String removed = arrayList.remove(0);
        System.out.println("Removed element at index 0: " + removed);
        
        // Line 9: Remove element by value
        // Removes first occurrence, returns true if found
        boolean removedBanana = arrayList.remove("Banana");
        System.out.println("Removed 'Banana': " + removedBanana);
        
        // Line 10: Check list size (number of elements)
        int size = arrayList.size();
        System.out.println("Current size: " + size);
        
        // Line 11: Check if list is empty
        boolean isEmpty = arrayList.isEmpty();
        System.out.println("Is empty: " + isEmpty);
        
        // Line 12: Iterate using for-each loop
        // Most common way to traverse lists
        System.out.print("Elements (for-each): ");
        for (String fruit : arrayList) {
            System.out.print(fruit + " ");
        }
        System.out.println();
        
        // Line 13: Iterate using traditional for loop
        // Use when you need index
        System.out.print("Elements (for with index): ");
        for (int i = 0; i < arrayList.size(); i++) {
            System.out.print(arrayList.get(i) + " ");
        }
        System.out.println();
        
        // Line 14: Iterate using Iterator
        // Useful for removing elements while iterating
        System.out.print("Elements (Iterator): ");
        Iterator<String> iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println();
        
        // Line 15: Iterate using ListIterator (bidirectional)
        // Can go forward and backward
        ListIterator<String> listIterator = arrayList.listIterator();
        while (listIterator.hasNext()) {
            String current = listIterator.next();
            if (current.equals("Cranberry")) {
                listIterator.set("Cantaloupe"); // Modify during iteration
            }
        }
        
        // Line 16: Sublist (view, not copy)
        // Changes to sublist affect original list
        List<String> subList = arrayList.subList(0, 2);
        System.out.println("Sublist (index 0-2): " + subList);
        
        // Line 17: Convert list to array
        String[] array = arrayList.toArray(new String[0]);
        System.out.println("Array length: " + array.length);
        
        // Line 18: Clear all elements
        arrayList.clear();
        System.out.println("After clear, size: " + arrayList.size());
        
        // SECTION 2: LINKEDLIST (When you need fast insert/delete at ends)
        System.out.println("\n--- 2. LINKEDLIST ---");
        
        // Line 19: Create LinkedList (Doubly linked list)
        // Good for frequent insertions/deletions at ends
        List<String> linkedList = new LinkedList<>();
        linkedList.add("First");
        linkedList.add("Second");
        linkedList.add("Third");
        linkedList.add("Fourth");
        
        System.out.println("LinkedList: " + linkedList);
        
        // Line 20: Add at beginning (FAST in LinkedList)
        linkedList.add(0, "New First");
        System.out.println("After adding at beginning: " + linkedList);
        
        // Line 21: Add at end
        linkedList.add("New Last");
        System.out.println("After adding at end: " + linkedList);
        
        // Line 22: Remove from beginning (FAST in LinkedList)
        linkedList.remove(0);
        System.out.println("After removing from beginning: " + linkedList);
        
        // Line 23: Get first and last elements
        // LinkedList has special methods for ends
        LinkedList<String> ll = (LinkedList<String>) linkedList;
        System.out.println("First element: " + ll.getFirst());
        System.out.println("Last element: " + ll.getLast());
        
        // SECTION 3: COMMON LIST OPERATIONS
        System.out.println("\n--- 3. COMMON OPERATIONS ---");
        
        // Line 24: Sorting
        List<Integer> numbers = new ArrayList<>();
        numbers.add(5);
        numbers.add(1);
        numbers.add(9);
        numbers.add(3);
        numbers.add(7);
        
        System.out.println("Before sort: " + numbers);
        Collections.sort(numbers); // Natural order (ascending)
        System.out.println("After sort: " + numbers);
        
        // Line 25: Reverse order
        Collections.reverse(numbers);
        System.out.println("After reverse: " + numbers);
        
        // Line 26: Shuffle (random order)
        Collections.shuffle(numbers);
        System.out.println("After shuffle: " + numbers);
        
        // Line 27: Binary search (list must be sorted first)
        Collections.sort(numbers);
        int index = Collections.binarySearch(numbers, 7);
        System.out.println("Binary search for 7 found at index: " + index);
        
        // Line 28: Find min and max
        int min = Collections.min(numbers);
        int max = Collections.max(numbers);
        System.out.println("Min: " + min + ", Max: " + max);
        
        // Line 29: Copy list
        List<Integer> copy = new ArrayList<>(numbers);
        System.out.println("Copy: " + copy);
        
        // Line 30: Fill list with same value
        List<String> filled = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            filled.add("");
        }
        Collections.fill(filled, "Default");
        System.out.println("Filled list: " + filled);
        
        // SECTION 4: REAL-WORLD EXAMPLES
        System.out.println("\n--- 4. REAL-WORLD EXAMPLES ---");
        
        // Example 1: Shopping Cart
        System.out.println("\nExample 1: Shopping Cart");
        List<String> shoppingCart = new ArrayList<>();
        shoppingCart.add("Milk");
        shoppingCart.add("Bread");
        shoppingCart.add("Eggs");
        shoppingCart.add("Cheese");
        
        System.out.println("Cart: " + shoppingCart);
        
        // Remove item
        shoppingCart.remove("Bread");
        System.out.println("After removing Bread: " + shoppingCart);
        
        // Add at specific position
        shoppingCart.add(1, "Butter");
        System.out.println("After adding Butter at index 1: " + shoppingCart);
        
        // Check total items
        System.out.println("Total items: " + shoppingCart.size());
        
        // Example 2: To-Do List
        System.out.println("\nExample 2: To-Do List");
        List<String> todoList = new LinkedList<>();
        todoList.add("Wake up");
        todoList.add("Brush teeth");
        todoList.add("Have breakfast");
        todoList.add("Go to work");
        
        System.out.println("To-Do: " + todoList);
        
        // Complete first task
        String completed = todoList.remove(0);
        System.out.println("Completed: " + completed);
        System.out.println("Remaining: " + todoList);
        
        // Add urgent task at beginning
        todoList.add(0, "Check email");
        System.out.println("After adding urgent task: " + todoList);
        
        // Example 3: Student Grades
        System.out.println("\nExample 3: Student Grades");
        List<Double> grades = new ArrayList<>();
        grades.add(85.5);
        grades.add(92.0);
        grades.add(78.5);
        grades.add(95.0);
        grades.add(88.0);
        
        System.out.println("Grades: " + grades);
        
        // Calculate average
        double sum = 0;
        for (Double grade : grades) {
            sum += grade;
        }
        double average = sum / grades.size();
        System.out.println("Average grade: " + average);
        
        // Find highest grade
        double highest = Collections.max(grades);
        System.out.println("Highest grade: " + highest);
        
        // Sort grades
        Collections.sort(grades);
        System.out.println("Sorted grades: " + grades);
        
        // Example 4: Playlist (LinkedList is perfect)
        System.out.println("\nExample 4: Music Playlist");
        LinkedList<String> playlist = new LinkedList<>();
        playlist.add("Song 1 - Artist A");
        playlist.add("Song 2 - Artist B");
        playlist.add("Song 3 - Artist C");
        playlist.add("Song 4 - Artist D");
        
        System.out.println("Playlist: " + playlist);
        
        // Play next song (remove from front)
        String currentSong = playlist.pollFirst();
        System.out.println("Now playing: " + currentSong);
        System.out.println("Up next: " + playlist.getFirst());
        
        // Add song to queue (add to end)
        playlist.addLast("Song 5 - Artist E");
        System.out.println("Added to queue: Song 5");
        
        // Skip to specific song
        int skipIndex = 2;
        if (skipIndex < playlist.size()) {
            String skippedSong = playlist.get(skipIndex);
            System.out.println("Skipping to: " + skippedSong);
        }
        
        // SECTION 5: PERFORMANCE TIPS
        System.out.println("\n--- 5. PERFORMANCE TIPS ---");
        
        // Tip 1: Pre-size ArrayList if you know approximate size
        List<String> optimizedList = new ArrayList<>(100); // Initial capacity 100
        
        // Tip 2: Use LinkedList when you frequently add/remove at ends
        // Tip 3: Use ArrayList when you frequently access by index
        // Tip 4: Avoid removing from middle of ArrayList in loops
        
        // Tip 5: Use for-each for simple traversal
        for (String item : playlist) {
            // Simple iteration
        }
        
        // Tip 6: Use traditional for when you need index
        for (int i = 0; i < playlist.size(); i++) {
            String item = playlist.get(i);
            // Access with index
        }
        
        // Tip 7: Use Iterator when removing while iterating
        Iterator<String> it = playlist.iterator();
        while (it.hasNext()) {
            String item = it.next();
            if (item.contains("skip")) {
                it.remove(); // Safe removal
            }
        }
        
        System.out.println("\n=== LIST GUIDE COMPLETE ===");
    }
}
