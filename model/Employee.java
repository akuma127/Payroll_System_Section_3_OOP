package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
//Encapsulation
public class Employee extends Person {
    private String jobTitle;
    private String EMPLOYEE_FILE_PATH = "Persons.txt";
    private String PAY_FILE_PATH = "Payment.txt";

    public Employee(String name, String personID, String password, String jobTitle, String EMPLOYEE_FILE_PATH) {
        super(name, personID, password);
        this.jobTitle = jobTitle;
        this.EMPLOYEE_FILE_PATH = EMPLOYEE_FILE_PATH;
    }
    public Employee(String name, String personID, String password, String jobTitle) {
        super(name, personID, password);
        this.jobTitle = jobTitle;
    }
    //Exception
    public String getJobTitle() {
        return jobTitle;
    }
    public void updateSelfInFile(String newName, String newPass) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(this.EMPLOYEE_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                // Match based on username (currentEmployee.getPersonID is used as username here)
                if (parts.length >= 4 && parts[1].equals(this.getPersonID())) {
                    // Keeps original username and role, updates name and password
                    lines.add(newName + "," + parts[1] + "," + newPass + "," + parts[3]);
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.EMPLOYEE_FILE_PATH))) {
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ObservableList<String[]> loadPaymentData() {
        ObservableList<String[]> data = FXCollections.observableArrayList();
        try (BufferedReader reader = new BufferedReader(new FileReader(PAY_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && parts[0].equalsIgnoreCase(this.getPersonID())) {
                    data.add(parts);
                }
            }
        } catch (IOException e) {
            System.err.println("Payment file not found.");
        }
        return data;
    }
}
