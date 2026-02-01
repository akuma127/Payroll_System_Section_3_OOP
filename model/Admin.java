package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Admin class extends Person and manages employee records stored in a text file
public class Admin extends Person {
    private String jobTitle; // Job title of the Admin
    String EMPLOYEE_FILE_PATH = "Persons.txt"; // Default file path for storing employee data

    // Constructor that allows specifying a custom file path
    public Admin(String name, String personID, String password, String jobTitle, String EMPLOYEE_FILE_PATH) {
        super(name, personID, password); // Call the Person constructor
        this.jobTitle = jobTitle;        // Set job title
        this.EMPLOYEE_FILE_PATH = EMPLOYEE_FILE_PATH; // Override default file path
    }

    // Constructor that uses the default file path
    public Admin(String name, String personID, String password, String jobTitle) {
        super(name, personID, password); // Call the Person constructor
        this.jobTitle = jobTitle;        // Set job title
    }

    // Getter method for job title
    public String getJobTitle() { return jobTitle; }

    // Save a new employee record to the file
    public void saveEmployeeToFile(String name, String username, String password, String role) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(this.EMPLOYEE_FILE_PATH, true))) { // Open file in append mode

            writer.newLine(); // Start a new line
            writer.write(name + "," + username + "," + password + "," + role); // Write employee data

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Remove an employee record from the file based on username
    public boolean removeEmployeeFromFile(String username) {
        List<String> remainingLines = new ArrayList<>(); // Store lines that are not removed
        boolean found = false; // Track if employee was found

        try (BufferedReader reader = new BufferedReader(new FileReader(this.EMPLOYEE_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // Skip empty lines

                // Assuming format: Name,Username,Password,Role
                String[] details = line.split(",");
                if (details.length >= 2 && details[1].equalsIgnoreCase(username)) {
                    found = true; // Skip this line to "remove" it
                } else {
                    remainingLines.add(line); // Keep other lines
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        // If employee was found, rewrite file without their record
        if (found) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.EMPLOYEE_FILE_PATH, false))) {
                for (int i = 0; i < remainingLines.size(); i++) {
                    writer.write(remainingLines.get(i)); // Write remaining employees
                    if (i < remainingLines.size() - 1) {
                        writer.newLine(); // Add newline except after last line
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return found; // Return true if employee was removed, false otherwise
    }

    // Find an employee record by username
    public String[] findEmployee(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(EMPLOYEE_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("[source")) continue; // Skip invalid lines
                String[] parts = line.split(",");
                if (parts.length >= 4 && parts[1].equalsIgnoreCase(username)) {
                    return parts; // Return employee details as array
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // Return null if employee not found
    }

    // Update an existing employee record in the file
    public void updateEmployeeInFile(String username, String name, String pass, String role) {
        List<String> lines = new ArrayList<>(); // Store updated file content
        try (BufferedReader reader = new BufferedReader(new FileReader(EMPLOYEE_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty() && !line.startsWith("[source")) {
                    String[] parts = line.split(",");
                    if (parts.length >= 4 && parts[1].equalsIgnoreCase(username)) {
                        // Replace old record with updated details
                        lines.add(name + "," + username + "," + pass + "," + role);
                        continue;
                    }
                }
                lines.add(line); // Keep other lines unchanged
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Rewrite file with updated content
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EMPLOYEE_FILE_PATH))) {
            for (String l : lines) {
                writer.write(l); // Write each line back to file
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}