package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataStore {
    // Constant file name where all person data is stored
    private static final String FILE_NAME = "Persons.txt";

    // Static method to load all persons (Admins or Employees) from the file
    public static List<Person> loadPersons() {
        // Create an empty list to hold Person objects
        List<Person> personList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Skip lines that are empty (avoid processing blank lines)
                if (line.trim().isEmpty()) continue;

                // Each line is expected to be in the format: name,id,password,jobTitle
                String[] parts = line.split(",");

                if (parts.length >= 4) {
                    String name = parts[0].trim();
                    String id = parts[1].trim();
                    String pass = parts[2].trim();
                    String title = parts[3].trim();

                    // Decide whether to create an Admin or Employee object
                    // If the job title contains "admin", create an Admin
                    // Otherwise, create an Employee
                    if (title.toLowerCase().contains("admin")) {
                        personList.add(new Admin(name, id, pass, title));
                    } else {
                        personList.add(new Employee(name, id, pass, title));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading Persons.txt: " + e.getMessage());
            e.printStackTrace();
        }
       // Return the list of persons loaded from the file
        return personList;
    }
}