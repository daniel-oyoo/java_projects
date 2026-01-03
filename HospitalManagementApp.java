/**
 * HOSPITAL MANAGEMENT SYSTEM
 * Demonstrates: Packages, Interfaces, Modifiers, and Data Cleaning/Formatting
 * 
 * Key Features:
 * 1. Data validation and cleaning for medical records
 * 2. Proper use of access modifiers (public, private, protected, package)
 * 3. Interface-based design for extensibility
 * 4. Static and final variables for constants
 * 5. Abstract classes for common hospital personnel
 */

// PACKAGE DECLARATION - organizes hospital-related classes
//package com.hospital.management;

// ==================== INTERFACES (Design Contracts) ====================

/**
 * INTERFACE: MedicalProfessional
 * Purpose: Defines contract for all medical personnel
 * Demonstrates: Interface with multiple abstract methods
 */
interface MedicalProfessional {
    // All interface methods are implicitly public and abstract
    String getMedicalLicense();
    void performDuty();
    boolean isAvailable();
}

/**
 * INTERFACE: DataCleanable
 * Purpose: Defines contract for cleaning and formatting data
 * Demonstrates: Interface for data processing functionality
 */
interface DataCleanable {
    String cleanAndFormat(String rawData);
    boolean validateData(String data);
    String standardizeFormat(String input);
}

/**
 * INTERFACE: Reportable
 * Purpose: Defines contract for generating reports
 * Demonstrates: Interface with default method (Java 8+ feature)
 */
interface Reportable {
    String generateReport();
    
    // DEFAULT METHOD - provides implementation in interface (Java 8+)
    default String getReportHeader() {
        return "=== HOSPITAL MANAGEMENT REPORT ===";
    }
}

// ==================== ABSTRACT BASE CLASS ====================

/**
 * ABSTRACT CLASS: HospitalPersonnel
 * Purpose: Base class for all hospital staff with common attributes
 * Demonstrates: Abstract class, protected fields, package-private access
 */
abstract class HospitalPersonnel {
    // PROTECTED: Accessible in this class and subclasses
    protected String name;
    protected String id;
    protected String department;
    
    // PACKAGE-PRIVATE: Accessible only within same package
    String hireDate;
    
    // PRIVATE: Only accessible within this class
    private String internalCode;
    private String contactNumber;
    
    // STATIC FINAL: Class constants
    public static final String HOSPITAL_NAME = "City General Hospital";
    protected static final int MAX_WORK_HOURS = 48;
    private static final String HOSPITAL_CODE = "CGH-2024";
    
    // CONSTRUCTOR - protected so only subclasses can use directly
    protected HospitalPersonnel(String name, String id, String department) {
        this.name = cleanName(name);          // Data cleaning on input
        this.id = formatId(id);               // Data formatting
        this.department = standardizeDept(department);
        this.internalCode = generateInternalCode();
    }
    
    // ABSTRACT METHOD - must be implemented by subclasses
    public abstract String getRole();
    
    // PUBLIC METHODS - accessible from anywhere
    public String getName() {
        return name;
    }
    
    public String getId() {
        return id;
    }
    
    public String getDepartment() {
        return department;
    }
    
    // PROTECTED METHODS - accessible in subclasses
    protected void setContactNumber(String number) {
        this.contactNumber = formatPhoneNumber(number);
    }
    
    protected String getContactNumber() {
        return contactNumber;
    }
    
    // PRIVATE METHODS - data cleaning/formatting helpers (encapsulated)
    private String cleanName(String rawName) {
        if (rawName == null || rawName.trim().isEmpty()) {
            return "UNKNOWN";
        }
        // Remove extra spaces, capitalize first letters
        String cleaned = rawName.trim().replaceAll("\\s+", " ");
        String[] parts = cleaned.split(" ");
        StringBuilder result = new StringBuilder();
        for (String part : parts) {
            if (!part.isEmpty()) {
                result.append(Character.toUpperCase(part.charAt(0)))
                      .append(part.substring(1).toLowerCase())
                      .append(" ");
            }
        }
        return result.toString().trim();
    }
    
    private String formatId(String rawId) {
        if (rawId == null || rawId.trim().isEmpty()) {
            return "ID-UNASSIGNED";
        }
        // Format: Remove non-alphanumeric, ensure uppercase
        String cleaned = rawId.trim().toUpperCase().replaceAll("[^A-Z0-9-]", "");
        if (!cleaned.startsWith("EMP-")) {
            cleaned = "EMP-" + cleaned;
        }
        return cleaned;
    }
    
    private String standardizeDept(String dept) {
        // Standardize department names
        if (dept == null) return "GENERAL";
        
        String upperDept = dept.trim().toUpperCase();
        switch (upperDept) {
            case "ER": case "EMERGENCY": return "EMERGENCY_ROOM";
            case "ICU": case "INTENSIVE": return "INTENSIVE_CARE";
            case "OPD": case "OUTPATIENT": return "OUTPATIENT_DEPT";
            case "RAD": case "RADIOLOGY": return "RADIOLOGY";
            case "SURG": case "SURGERY": return "SURGERY";
            default: return upperDept.replaceAll("\\s+", "_");
        }
    }
    
    private String formatPhoneNumber(String phone) {
        if (phone == null) return "N/A";
        
        // Remove all non-digits
        String digits = phone.replaceAll("\\D", "");
        
        if (digits.length() == 10) {
            return "+1 (" + digits.substring(0, 3) + ") " + 
                   digits.substring(3, 6) + "-" + digits.substring(6);
        } else if (digits.length() == 11 && digits.startsWith("1")) {
            return "+1 (" + digits.substring(1, 4) + ") " + 
                   digits.substring(4, 7) + "-" + digits.substring(7);
        }
        return phone; // Return original if format not recognized
    }
    
    private String generateInternalCode() {
        return HOSPITAL_CODE + "-" + id + "-" + System.currentTimeMillis();
    }
    
    // STATIC METHOD - utility method for all hospital personnel
    public static String getHospitalInfo() {
        return HOSPITAL_NAME + " | Code: " + HOSPITAL_CODE;
    }
}

// ==================== CONCRETE CLASSES ====================

/**
 * CLASS: Doctor
 * Purpose: Represents a doctor with medical specialization
 * Demonstrates: Multiple interface implementation, final methods, 
 *               data cleaning inheritance
 * public
 */
 class Doctor extends HospitalPersonnel implements MedicalProfessional, DataCleanable, Reportable {
    // PRIVATE VARIABLES - encapsulated medical data
    private String specialization;
    private String medicalLicense;
    private boolean isOnCall;
    private int patientCount;
    
    // STATIC VARIABLES - shared across all Doctor instances
    private static int totalDoctors = 0;
    private static final int MAX_PATIENTS_PER_DOCTOR = 50;
    
    // FINAL VARIABLE - constant specific to Doctor
    public final String DEGREE = "MD";
    
    // CONSTRUCTOR
    public Doctor(String name, String id, String department, String specialization, String license) {
        super(name, id, department);  // Call parent constructor
        this.specialization = cleanSpecialization(specialization);
        this.medicalLicense = formatLicense(license);
        this.isOnCall = false;
        this.patientCount = 0;
        totalDoctors++;
    }
    
    // INTERFACE METHOD IMPLEMENTATION (MedicalProfessional)
    public String getMedicalLicense() {
        return medicalLicense;
    }
    
    public void performDuty() {
        System.out.println("Dr. " + name + " is performing medical duties in " + department);
    }
    
    public boolean isAvailable() {
        return !isOnCall && patientCount < MAX_PATIENTS_PER_DOCTOR;
    }
    
    // INTERFACE METHOD IMPLEMENTATION (DataCleanable)
    public String cleanAndFormat(String rawData) {
        if (rawData == null || rawData.trim().isEmpty()) {
            return "INVALID_DATA";
        }
        
        // Multiple cleaning operations
        String step1 = rawData.trim().replaceAll("\\s+", " ");
        String step2 = step1.replaceAll("[^a-zA-Z0-9\\s.,-]", "");
        String step3 = step2.replaceAll("\\b(?:null|nil|na|n/a)\\b", "UNKNOWN");
        
        // Capitalize medical terms
        String[] medicalTerms = {"covid", "hiv", "aids", "ct", "mri", "xray"};
        String result = step3;
        for (String term : medicalTerms) {
            result = result.replaceAll("(?i)\\b" + term + "\\b", term.toUpperCase());
        }
        
        return result;
    }
    
    public boolean validateData(String data) {
        if (data == null || data.trim().isEmpty()) {
            return false;
        }
        
        // Check for suspicious patterns
        String[] invalidPatterns = {
            "test", "demo", "sample", "dummy", "fake",
            "1234", "abcd", "xxxx", "asdf"
        };
        
        String lowerData = data.toLowerCase();
        for (String pattern : invalidPatterns) {
            if (lowerData.contains(pattern)) {
                return false;
            }
        }
        
        // Valid data should have meaningful length
        return data.trim().length() > 2 && data.trim().length() < 1000;
    }
    
    public String standardizeFormat(String input) {
        if (!validateData(input)) {
            return cleanAndFormat("Invalid input detected");
        }
        
        // Medical record standardization
        String standardized = cleanAndFormat(input);
        
        // Add timestamp and doctor info
        String timestamp = java.time.LocalDateTime.now().toString();
        return "MEDICAL_RECORD | Doctor: " + name + 
               " | License: " + medicalLicense.substring(0, Math.min(8, medicalLicense.length())) + 
               " | Time: " + timestamp + " | Data: " + standardized;
    }
    
    // INTERFACE METHOD IMPLEMENTATION (Reportable)
    public String generateReport() {
        String header = getReportHeader();  // Using default method
        return header + "\n" +
               "Doctor Report\n" +
               "-------------\n" +
               "Name: " + name + "\n" +
               "ID: " + id + "\n" +
               "Department: " + department + "\n" +
               "Specialization: " + specialization + "\n" +
               "License: " + medicalLicense + "\n" +
               "Patients: " + patientCount + "/" + MAX_PATIENTS_PER_DOCTOR + "\n" +
               "Available: " + (isAvailable() ? "Yes" : "No") + "\n" +
               "Generated: " + java.time.LocalDate.now();
    }
    
    // ABSTRACT METHOD IMPLEMENTATION (from HospitalPersonnel)
    public String getRole() {
        return "Doctor";
    }
    
    // PUBLIC METHODS
    public void addPatient() {
        if (patientCount < MAX_PATIENTS_PER_DOCTOR) {
            patientCount++;
            System.out.println("Patient added. Total: " + patientCount);
        } else {
            System.out.println("Cannot add more patients. Maximum reached.");
        }
    }
    
    public void setOnCall(boolean onCall) {
        this.isOnCall = onCall;
        if (onCall) {
            System.out.println("Dr. " + name + " is now on call duty");
        }
    }
    
    // STATIC METHODS
    public static int getTotalDoctors() {
        return totalDoctors;
    }
    
    public static void displayDoctorStats() {
        System.out.println("=== DOCTOR STATISTICS ===");
        System.out.println("Total Doctors: " + totalDoctors);
        System.out.println("Max Patients per Doctor: " + MAX_PATIENTS_PER_DOCTOR);
    }
    
    // FINAL METHOD - cannot be overridden
    public final String getSpecialization() {
        return specialization;
    }
    
    // PRIVATE HELPER METHOD - data cleaning for specialization
    private String cleanSpecialization(String spec) {
        if (spec == null || spec.trim().isEmpty()) {
            return "GENERAL_PRACTICE";
        }
        
        String cleaned = spec.trim().toUpperCase().replaceAll("\\s+", "_");
        
        // Map common variations to standard terms
        java.util.Map<String, String> specializationMap = new java.util.HashMap<>();
        specializationMap.put("CARDIOLOGY", "CARDIOLOGY");
        specializationMap.put("HEART", "CARDIOLOGY");
        specializationMap.put("NEUROLOGY", "NEUROLOGY");
        specializationMap.put("BRAIN", "NEUROLOGY");
        specializationMap.put("ORTHOPEDICS", "ORTHOPEDICS");
        specializationMap.put("BONE", "ORTHOPEDICS");
        
        return specializationMap.getOrDefault(cleaned, cleaned);
    }
    
    // PRIVATE HELPER METHOD - license formatting
    private String formatLicense(String license) {
        if (license == null || license.trim().isEmpty()) {
            return "LICENSE_PENDING";
        }
        
        String cleaned = license.trim().toUpperCase().replaceAll("[^A-Z0-9]", "");
        
        if (cleaned.length() < 6) {
            return "INVALID_" + cleaned;
        }
        
        // Format: MD-XXX-XXXX
        return "MD-" + cleaned.substring(0, 3) + "-" + cleaned.substring(3);
    }
}

/**
 * CLASS: Nurse (package-private class)
 * Demonstrates: Package access, different interface implementations
 */
class Nurse extends HospitalPersonnel implements MedicalProfessional, DataCleanable {
    // PRIVATE VARIABLES
    private String nurseLevel;
    private String[] assignedWards;
    private int shiftHours;
    
    // STATIC FINAL - class constants
    private static final String[] VALID_LEVELS = {"RN", "LPN", "CNA", "NP"};
    private static final int MAX_SHIFT_HOURS = 12;
    
    public Nurse(String name, String id, String department, String level) {
        super(name, id, department);
        this.nurseLevel = validateLevel(level);
        this.assignedWards = new String[5];
        this.shiftHours = 8;
    }
    
    // INTERFACE METHOD IMPLEMENTATION (MedicalProfessional)
    public String getMedicalLicense() {
        return "NURSE-" + id;
    }
    
    public void performDuty() {
        System.out.println("Nurse " + name + " is providing patient care");
    }
    
    public boolean isAvailable() {
        return shiftHours < MAX_SHIFT_HOURS;
    }
    
    // INTERFACE METHOD IMPLEMENTATION (DataCleanable)
    public String cleanAndFormat(String rawData) {
        // Nurse-specific cleaning for patient vitals
        if (rawData == null) return "NO_DATA";
        
        String cleaned = rawData.trim();
        
        // Clean vital signs data
        cleaned = cleaned.replaceAll("[^0-9./]", "");
        cleaned = cleaned.replaceAll("/+", "/");
        
        return cleaned;
    }
    
    public boolean validateData(String data) {
        if (data == null || data.trim().isEmpty()) return false;
        
        // Validate vital signs format (e.g., 120/80, 98.6, 60)
        return data.matches("^[0-9]{1,3}(\\.[0-9])?(/[0-9]{1,3})?$");
    }
    
    public String standardizeFormat(String input) {
        String cleaned = cleanAndFormat(input);
        return "VITAL_SIGNS | Nurse: " + name + 
               " | Level: " + nurseLevel + " | Data: " + cleaned;
    }
    
    // ABSTRACT METHOD IMPLEMENTATION
    public String getRole() {
        return "Nurse";
    }
    
    // PACKAGE-PRIVATE METHOD - only accessible within same package
    void assignToWard(String ward) {
        for (int i = 0; i < assignedWards.length; i++) {
            if (assignedWards[i] == null) {
                assignedWards[i] = ward;
                System.out.println(name + " assigned to " + ward);
                return;
            }
        }
        System.out.println("Cannot assign to more wards");
    }
    
    // PRIVATE HELPER METHOD - level validation
    private String validateLevel(String level) {
        String upperLevel = level.trim().toUpperCase();
        for (String valid : VALID_LEVELS) {
            if (valid.equals(upperLevel)) {
                return upperLevel;
            }
        }
        return "RN"; // Default to Registered Nurse
    }
}

/**
 * CLASS: PatientDataCleaner
 * Purpose: Specialized class for cleaning and formatting patient data
 * Demonstrates: Standalone utility class, static methods, final class
 * public
 */
 final class PatientDataCleaner implements DataCleanable {
    // PRIVATE CONSTRUCTOR - cannot be instantiated (utility class pattern)
    private PatientDataCleaner() {}
    
    // STATIC INSTANCE - singleton pattern
    private static final PatientDataCleaner INSTANCE = new PatientDataCleaner();
    
    // STATIC METHOD to get instance
    public static PatientDataCleaner getInstance() {
        return INSTANCE;
    }
    
    // INTERFACE METHOD IMPLEMENTATION
    public String cleanAndFormat(String rawData) {
        if (rawData == null) return "NULL_DATA";
        
        StringBuilder result = new StringBuilder();
        String[] lines = rawData.split("\n");
        
        for (String line : lines) {
            String cleanedLine = cleanLine(line);
            if (!cleanedLine.isEmpty()) {
                result.append(cleanedLine).append("\n");
            }
        }
        
        return result.toString().trim();
    }
    
    public boolean validateData(String data) {
        if (data == null) return false;
        
        // Check for minimum required patient data
        String[] requiredFields = {"name:", "age:", "condition:"};
        String lowerData = data.toLowerCase();
        
        for (String field : requiredFields) {
            if (!lowerData.contains(field)) {
                return false;
            }
        }
        
        return true;
    }
    
    public String standardizeFormat(String input) {
        if (!validateData(input)) {
            return "INVALID_PATIENT_DATA_FORMAT";
        }
        
        String cleaned = cleanAndFormat(input);
        String[] lines = cleaned.split("\n");
        
        StringBuilder standardized = new StringBuilder();
        standardized.append("=== PATIENT RECORD ===\n");
        standardized.append("Date: ").append(java.time.LocalDate.now()).append("\n");
        standardized.append("Time: ").append(java.time.LocalTime.now()).append("\n");
        standardized.append("----------------------\n");
        
        for (String line : lines) {
            standardized.append(formatPatientField(line)).append("\n");
        }
        
        return standardized.toString();
    }
    
    // PRIVATE HELPER METHODS
    private String cleanLine(String line) {
        if (line == null || line.trim().isEmpty()) {
            return "";
        }
        
        String cleaned = line.trim();
        
        // Remove invalid characters but keep medical abbreviations
        cleaned = cleaned.replaceAll("[^a-zA-Z0-9\\s:.,-]", "");
        
        // Fix common data entry errors
        cleaned = cleaned.replaceAll("\\s+", " ")
                         .replaceAll("\\s*:\\s*", ": ")
                         .replaceAll("\\s*,\\s*", ", ");
        
        return cleaned;
    }
    
    private String formatPatientField(String field) {
        if (!field.contains(":")) {
            return "  " + field;
        }
        
        String[] parts = field.split(":", 2);
        if (parts.length != 2) return field;
        
        String key = parts[0].trim();
        String value = parts[1].trim();
        
        // Format key with padding
        String formattedKey = String.format("%-15s", key + ":");
        
        return formattedKey + value;
    }
    
    // STATIC UTILITY METHODS
    public static String anonymizeData(String data) {
        if (data == null) return "";
        
        // Anonymize sensitive information
        return data.replaceAll("\\b[A-Z][a-z]+\\s+[A-Z][a-z]+\\b", "*** ***")  // Names
                   .replaceAll("\\b\\d{3}-\\d{2}-\\d{4}\\b", "***-**-****")    // SSN
                   .replaceAll("\\b\\d{16}\\b", "****************")            // Credit cards
                   .replaceAll("\\b\\d{10}\\b", "**********");                 // Phone numbers
    }
}

// ==================== MAIN APPLICATION CLASS ====================

/**
 * CLASS: HospitalManagementApp
 * Purpose: Demonstrates hospital management with data cleaning
 * Demonstrates: All concepts from notes in practical application
 */
public class HospitalManagementApp {
    public static void main(String[] args) {
        System.out.println("=== HOSPITAL MANAGEMENT SYSTEM ===\n");
        
        // Display hospital info using static method
        System.out.println(HospitalPersonnel.getHospitalInfo());
        System.out.println();
        
        // Create doctors with messy input data
        System.out.println("=== CREATING DOCTORS WITH DATA CLEANING ===");
        Doctor doctor1 = new Doctor(
            "  john   doe  ",      // Messy name
            "emp-123a",           // Inconsistent ID
            "emergency room",     // Department
            " cardiology ",       // Specialization
            "md123456"           // License
        );
        
        Doctor doctor2 = new Doctor(
            "mary-smith",         // Name with hyphen
            "xyz789",             // ID without prefix
            "ICU",               // Department abbreviation
            "neurology",         // Specialization
            "neuro7890"          // License
        );
        
        // Create nurse (same package, can access package-private class)
        Nurse nurse1 = new Nurse(
            "sarah jones  ",
            "nur-456",
            "Pediatrics",
            "RN"
        );
        
        // Demonstrate cleaned data
        System.out.println("\n=== CLEANED DATA DEMONSTRATION ===");
        System.out.println("Doctor 1 Name (cleaned): " + doctor1.getName());
        System.out.println("Doctor 1 ID (formatted): " + doctor1.getId());
        System.out.println("Doctor 1 License: " + doctor1.getMedicalLicense());
        System.out.println("Doctor 2 Specialization: " + doctor2.getSpecialization());
        
        // Demonstrate data cleaning interface
        System.out.println("\n=== DATA CLEANING DEMONSTRATION ===");
        
        // Raw, messy patient data
        String messyPatientData = """
            name: john   doe   
            age: 35
            condition:   high blood pressure   
            notes: patient has covid symptoms, needs mri
            vitals: 120/80,  98.6f,  60bpm
            """;
        
        System.out.println("Raw Data:\n" + messyPatientData);
        System.out.println("\nCleaned by Doctor:");
        String cleanedByDoctor = doctor1.cleanAndFormat(messyPatientData);
        System.out.println(cleanedByDoctor);
        
        System.out.println("\nStandardized Format:");
        String standardized = doctor1.standardizeFormat(messyPatientData);
        System.out.println(standardized);
        
        // Demonstrate PatientDataCleaner utility
        System.out.println("\n=== PATIENT DATA CLEANER UTILITY ===");
        PatientDataCleaner cleaner = PatientDataCleaner.getInstance();
        
        String messyRecord = """
            PATIENT RECORD
            Name: Alice Johnson
            Age: 45
            Condition: Fractured arm
            SSN: 123-45-6789
            Phone: 5551234567
            Notes: Needs surgery ASAP!!!
            """;
        
        System.out.println("Original Record:\n" + messyRecord);
        System.out.println("\nAnonymized:\n" + PatientDataCleaner.anonymizeData(messyRecord));
        System.out.println("\nCleaned and Formatted:\n" + cleaner.standardizeFormat(messyRecord));
        
        // Demonstrate interface references and polymorphism
        System.out.println("\n=== INTERFACE POLYMORPHISM ===");
        MedicalProfessional[] staff = {doctor1, doctor2, nurse1};
        
        for (MedicalProfessional professional : staff) {
            System.out.println("\n" + professional.getClass().getSimpleName() + ":");
            System.out.println("License: " + professional.getMedicalLicense());
            professional.performDuty();
            System.out.println("Available: " + professional.isAvailable());
        }
        
        // Demonstrate Reportable interface
        System.out.println("\n=== GENERATING REPORTS ===");
        Reportable reportableDoctor = doctor1;
        System.out.println(reportableDoctor.generateReport());
        
        // Demonstrate static methods and variables
        System.out.println("\n=== STATIC DATA ===");
        Doctor.displayDoctorStats();
        System.out.println("Total Doctors: " + Doctor.getTotalDoctors());
        System.out.println("Max Work Hours: " + HospitalPersonnel.MAX_WORK_HOURS);
        
        // Demonstrate access control
        System.out.println("\n=== ACCESS CONTROL DEMONSTRATION ===");
        
        // Public access - works
        System.out.println("Public access - Doctor name: " + doctor1.getName());
        
        // Protected access via public method - works
        System.out.println("Doctor department: " + doctor1.getDepartment());
        
        // Package-private access (Nurse in same package) - works
        nurse1.assignToWard("Ward A");
        
        // Private access - compilation error if attempted directly
        // doctor1.internalCode; // Would not compile!
        
        // Demonstrate final variables and methods
        System.out.println("\n=== FINAL ELEMENTS ===");
        System.out.println("Doctor degree (final): " + doctor1.DEGREE);
        System.out.println("Doctor specialization (final method): " + doctor1.getSpecialization());
        System.out.println("Hospital name (static final): " + HospitalPersonnel.HOSPITAL_NAME);
        
        // Demonstrate data validation
        System.out.println("\n=== DATA VALIDATION ===");
        System.out.println("Valid '120/80': " + nurse1.validateData("120/80"));
        System.out.println("Invalid 'test': " + nurse1.validateData("test"));
        System.out.println("Valid patient data: " + cleaner.validateData("name: test\nage: 30\ncondition: fever"));
        
        // Add patients to demonstrate instance methods
        System.out.println("\n=== PATIENT MANAGEMENT ===");
        doctor1.addPatient();
        doctor1.addPatient();
        doctor1.setOnCall(true);
        
        // Show updated availability
        System.out.println("Doctor 1 available: " + doctor1.isAvailable());
    }
}
