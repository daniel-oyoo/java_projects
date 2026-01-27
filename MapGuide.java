package com.daniel.collections;

import java.util.*;

/**
 * Complete guide to Java Maps
 * Demonstrates HashMap, LinkedHashMap, TreeMap and their operations
 */
public class MapGuide {
    
    // Define Product class as a static nested class (FIXED!)
    static class Product {
        String name;
        double price;
        int quantity;
        
        Product(String name, double price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }
        
        double getTotal() {
            return price * quantity;
        }
        
        @Override
        public String toString() {
            return String.format("%s: $%.2f x %d = $%.2f", 
                name, price, quantity, getTotal());
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== JAVA MAPS COMPLETE GUIDE ===");
        
        // SECTION 1: HASHMAP (Most Common)
        System.out.println("\n--- 1. HASHMAP ---");
        
        // Line 1: Create HashMap (Most common map)
        // Stores key-value pairs, fast O(1) operations
        Map<String, Integer> hashMap = new HashMap<>();
        
        // Line 2: Add key-value pairs
        // put() adds or updates if key exists
        hashMap.put("Alice", 25);
        hashMap.put("Bob", 30);
        hashMap.put("Charlie", 28);
        hashMap.put("Diana", 35);
        hashMap.put("Eve", 22);
        
        // Line 3: Print map
        // HashMap doesn't guarantee order
        System.out.println("HashMap: " + hashMap);
        
        // Line 4: Get value by key (FAST - O(1))
        // Returns null if key doesn't exist
        Integer aliceAge = hashMap.get("Alice");
        System.out.println("Alice's age: " + aliceAge);
        
        // Line 5: Get with default value
        // Avoid NullPointerException
        Integer unknownAge = hashMap.getOrDefault("Unknown", 0);
        System.out.println("Unknown's age (default 0): " + unknownAge);
        
        // Line 6: Check if key exists
        boolean hasBob = hashMap.containsKey("Bob");
        System.out.println("Has key 'Bob': " + hasBob);
        
        // Line 7: Check if value exists
        boolean hasAge25 = hashMap.containsValue(25);
        System.out.println("Has value 25: " + hasAge25);
        
        // Line 8: Update value
        // put() returns old value
        Integer oldAge = hashMap.put("Alice", 26);
        System.out.println("Alice's old age: " + oldAge + ", new age: " + hashMap.get("Alice"));
        
        // Line 9: Remove by key
        // Returns removed value, or null if key didn't exist
        Integer removedAge = hashMap.remove("Bob");
        System.out.println("Removed Bob's age: " + removedAge);
        
        // Line 10: Remove by key-value pair
        // Only removes if both match
        boolean removedCharlie = hashMap.remove("Charlie", 28);
        System.out.println("Removed Charlie (age 28): " + removedCharlie);
        
        // Line 11: Get map size
        int size = hashMap.size();
        System.out.println("Map size: " + size);
        
        // Line 12: Check if map is empty
        boolean isEmpty = hashMap.isEmpty();
        System.out.println("Is empty: " + isEmpty);
        
        // Line 13: Iterate through keys
        // KeySet returns all keys
        System.out.print("Keys: ");
        for (String key : hashMap.keySet()) {
            System.out.print(key + " ");
        }
        System.out.println();
        
        // Line 14: Iterate through values
        System.out.print("Values: ");
        for (Integer value : hashMap.values()) {
            System.out.print(value + " ");
        }
        System.out.println();
        
        // Line 15: Iterate through entries (Most efficient)
        // Entry contains both key and value
        System.out.println("Entries:");
        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }
        
        // Line 16: Using forEach with lambda (Java 8+)
        System.out.println("Using forEach:");
        hashMap.forEach((key, value) -> 
            System.out.println("  " + key + " = " + value)
        );
        
        // Line 17: Put if absent (don't overwrite existing)
        hashMap.putIfAbsent("Diana", 40); // Won't update (already exists)
        hashMap.putIfAbsent("Frank", 45); // Will add (new key)
        System.out.println("After putIfAbsent: " + hashMap);
        
        // Line 18: Compute value (transform existing)
        // compute() takes key and function
        hashMap.compute("Alice", (key, value) -> value + 1);
        System.out.println("After compute (Alice +1): " + hashMap.get("Alice"));
        
        // Line 19: Compute if absent (only if key doesn't exist)
        hashMap.computeIfAbsent("Grace", key -> 50);
        System.out.println("Added Grace: " + hashMap.get("Grace"));
        
        // Line 20: Compute if present (only if key exists)
        hashMap.computeIfPresent("Diana", (key, value) -> value + 5);
        System.out.println("Updated Diana: " + hashMap.get("Diana"));
        
        // Line 21: Merge maps
        Map<String, Integer> anotherMap = new HashMap<>();
        anotherMap.put("Alice", 10);
        anotherMap.put("Henry", 60);
        
        anotherMap.forEach((key, value) -> 
            hashMap.merge(key, value, (oldVal, newVal) -> oldVal + newVal)
        );
        System.out.println("After merge: " + hashMap);
        
        // Line 22: Clear map
        hashMap.clear();
        System.out.println("After clear, size: " + hashMap.size());
        
        // SECTION 2: LINKEDHASHMAP (Maintains insertion order)
        System.out.println("\n--- 2. LINKEDHASHMAP ---");
        
        // Line 23: Create LinkedHashMap
        // Maintains order of insertion
        Map<String, String> linkedHashMap = new LinkedHashMap<>();
        
        linkedHashMap.put("Zebra", "Animal");
        linkedHashMap.put("Apple", "Fruit");
        linkedHashMap.put("Carrot", "Vegetable");
        linkedHashMap.put("Banana", "Fruit");
        
        System.out.println("LinkedHashMap (insertion order): " + linkedHashMap);
        
        // Access order version (LRU cache)
        Map<String, String> accessOrderMap = new LinkedHashMap<>(16, 0.75f, true);
        accessOrderMap.put("A", "1");
        accessOrderMap.put("B", "2");
        accessOrderMap.put("C", "3");
        
        accessOrderMap.get("A"); // Access moves to end
        System.out.println("Access-ordered: " + accessOrderMap);
        
        // SECTION 3: TREEMAP (Sorted by keys)
        System.out.println("\n--- 3. TREEMAP ---");
        
        // Line 24: Create TreeMap (sorted by natural order)
        Map<String, Integer> treeMap = new TreeMap<>();
        
        treeMap.put("Zebra", 1);
        treeMap.put("Apple", 2);
        treeMap.put("Banana", 3);
        treeMap.put("Carrot", 4);
        
        System.out.println("TreeMap (sorted keys): " + treeMap);
        
        // Line 25: Reverse order TreeMap
        Map<String, Integer> reverseTreeMap = new TreeMap<>(Collections.reverseOrder());
        reverseTreeMap.put("Apple", 1);
        reverseTreeMap.put("Banana", 2);
        reverseTreeMap.put("Carrot", 3);
        
        System.out.println("Reverse TreeMap: " + reverseTreeMap);
        
        // Line 26: Get first and last entries
        TreeMap<String, Integer> tm = (TreeMap<String, Integer>) treeMap;
        System.out.println("First key: " + tm.firstKey());
        System.out.println("Last key: " + tm.lastKey());
        System.out.println("First entry: " + tm.firstEntry());
        
        // Line 27: Get head and tail maps
        Map<String, Integer> headMap = tm.headMap("Carrot"); // Keys < "Carrot"
        Map<String, Integer> tailMap = tm.tailMap("Banana"); // Keys >= "Banana"
        
        System.out.println("Head map (before Carrot): " + headMap);
        System.out.println("Tail map (from Banana): " + tailMap);
        
        // SECTION 4: REAL-WORLD EXAMPLES
        System.out.println("\n--- 4. REAL-WORLD EXAMPLES ---");
        
        // Example 1: Word Counter
        System.out.println("\nExample 1: Word Frequency Counter");
        String text = "hello world hello java world hello spring";
        Map<String, Integer> wordCount = new HashMap<>();
        
        for (String word : text.split(" ")) {
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }
        
        System.out.println("Word frequencies: " + wordCount);
        
        // Find most frequent word
        String mostFrequent = "";
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostFrequent = entry.getKey();
            }
        }
        System.out.println("Most frequent: '" + mostFrequent + "' appears " + maxCount + " times");
        
        // Example 2: Student Grades
        System.out.println("\nExample 2: Student Gradebook");
        Map<String, List<Integer>> gradebook = new HashMap<>();
        
        // Add student grades
        gradebook.put("Alice", new ArrayList<>(Arrays.asList(85, 90, 88)));
        gradebook.put("Bob", new ArrayList<>(Arrays.asList(78, 82, 80)));
        gradebook.put("Charlie", new ArrayList<>(Arrays.asList(92, 95, 90)));
        
        System.out.println("Gradebook: " + gradebook);
        
        // Calculate average for each student
        System.out.println("Averages:");
        for (Map.Entry<String, List<Integer>> entry : gradebook.entrySet()) {
            String student = entry.getKey();
            List<Integer> grades = entry.getValue();
            
            double sum = 0;
            for (Integer grade : grades) {
                sum += grade;
            }
            double average = sum / grades.size();
            System.out.println("  " + student + ": " + average);
        }
        
        // Example 3: Phone Directory
        System.out.println("\nExample 3: Phone Directory");
        Map<String, String> phonebook = new TreeMap<>(); // Sorted by name
        
        phonebook.put("Alice Johnson", "555-0101");
        phonebook.put("Bob Smith", "555-0102");
        phonebook.put("Charlie Brown", "555-0103");
        phonebook.put("Diana Prince", "555-0104");
        
        System.out.println("Phonebook (sorted):");
        phonebook.forEach((name, phone) -> 
            System.out.println("  " + name + ": " + phone)
        );
        
        // Search by partial name
        String search = "Smith";
        System.out.println("\nSearch results for '" + search + "':");
        for (String name : phonebook.keySet()) {
            if (name.toLowerCase().contains(search.toLowerCase())) {
                System.out.println("  Found: " + name + " - " + phonebook.get(name));
            }
        }
        
        // Example 4: Shopping Cart with Products (FIXED!)
        System.out.println("\nExample 4: Shopping Cart");
        Map<String, Product> cart = new LinkedHashMap<>(); // Maintain insertion order
        
        // Now Product class is accessible (defined as static class above)
        cart.put("MILK001", new Product("Milk", 3.99, 2));
        cart.put("BRD001", new Product("Bread", 2.49, 1));
        cart.put("EGG001", new Product("Eggs", 4.99, 1));
        
        System.out.println("Shopping Cart:");
        double total = 0;
        for (Product product : cart.values()) {
            System.out.println("  " + product);
            total += product.getTotal();
        }
        System.out.println("Total: $" + total);
        
        // Update quantity
        cart.get("MILK001").quantity = 3;
        System.out.println("After updating milk quantity:");
        System.out.println("  Milk: " + cart.get("MILK001"));
        
        // Example 5: Configuration Settings
        System.out.println("\nExample 5: Configuration Settings");
        Map<String, Object> config = new HashMap<>();
        
        config.put("app.name", "Library Management");
        config.put("app.version", "1.0.0");
        config.put("server.port", 8080);
        config.put("database.url", "jdbc:h2:mem:testdb");
        config.put("debug.mode", true);
        
        System.out.println("Configuration:");
        for (String key : new TreeSet<>(config.keySet())) { // Sorted keys
            System.out.println("  " + key + " = " + config.get(key));
        }
        
        // Get with type casting
        int port = (int) config.get("server.port");
        boolean debug = (boolean) config.get("debug.mode");
        System.out.println("Server port: " + port);
        System.out.println("Debug mode: " + debug);
        
        // SECTION 5: COMMON PATTERNS
        System.out.println("\n--- 5. COMMON PATTERNS ---");
        
        // Pattern 1: Grouping items
        List<String> fruits = Arrays.asList("apple", "banana", "apple", "orange", "banana", "apple");
        Map<String, List<String>> grouped = new HashMap<>();
        
        for (String fruit : fruits) {
            grouped.computeIfAbsent(fruit, k -> new ArrayList<>()).add(fruit);
        }
        System.out.println("Grouped fruits: " + grouped);
        
        // Pattern 2: Counting frequencies
        Map<Character, Integer> charCount = new HashMap<>();
        String str = "programming";
        
        for (char c : str.toCharArray()) {
            charCount.put(c, charCount.getOrDefault(c, 0) + 1);
        }
        System.out.println("Character frequencies: " + charCount);
        
        // Pattern 3: Invert map (swap keys and values)
        Map<Integer, String> original = new HashMap<>();
        original.put(1, "one");
        original.put(2, "two");
        original.put(3, "three");
        
        Map<String, Integer> inverted = new HashMap<>();
        for (Map.Entry<Integer, String> entry : original.entrySet()) {
            inverted.put(entry.getValue(), entry.getKey());
        }
        System.out.println("Inverted map: " + inverted);
        
        // Pattern 4: Filter map
        Map<String, Integer> scores = new HashMap<>();
        scores.put("Alice", 85);
        scores.put("Bob", 45);
        scores.put("Charlie", 92);
        scores.put("Diana", 65);
        
        Map<String, Integer> passingScores = new HashMap<>();
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            if (entry.getValue() >= 60) {
                passingScores.put(entry.getKey(), entry.getValue());
            }
        }
        System.out.println("Passing scores: " + passingScores);
        
        // Pattern 5: Merge two maps
        Map<String, Integer> map1 = new HashMap<>();
        map1.put("A", 1);
        map1.put("B", 2);
        
        Map<String, Integer> map2 = new HashMap<>();
        map2.put("B", 3);
        map2.put("C", 4);
        
        Map<String, Integer> merged = new HashMap<>(map1);
        map2.forEach((key, value) -> 
            merged.merge(key, value, Integer::sum)
        );
        System.out.println("Merged maps (sum values): " + merged);
        
        System.out.println("\n=== MAP GUIDE COMPLETE ===");
    }
}
