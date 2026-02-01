
package view;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Admin;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AddEmployeeUI extends Application {
    private Admin currentAdmin;

    private static final String FILE_PATH = "Persons.txt";
    public AddEmployeeUI(Admin currentAdmin) {
        this.currentAdmin = currentAdmin;
    }

    public void start(Stage stage) {


        Label nameLabel = new Label("Name:");
        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");
        Label roleLabel = new Label("Role:");


        TextField nameField = new TextField();
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();

        ComboBox<String> roleBox = new ComboBox<>();
        roleBox.getItems().addAll("admin", "manager", "employee");

        Button addButton = new Button("Add Employee");


        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);

        grid.add(usernameLabel, 0, 1);
        grid.add(usernameField, 1, 1);

        grid.add(passwordLabel, 0, 2);
        grid.add(passwordField, 1, 2);

        grid.add(roleLabel, 0, 3);
        grid.add(roleBox, 1, 3);

        grid.add(addButton, 1, 4);


        addButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            String role = roleBox.getValue();

            if (name.isEmpty() || username.isEmpty() || password.isEmpty() || role == null) {
                showAlert("Error", "Please fill all fields.");
                return;
            }

            this.currentAdmin.saveEmployeeToFile(name, username, password, role);

            nameField.clear();
            usernameField.clear();
            passwordField.clear();
            roleBox.setValue(null);

            showAlert("Success", "Employee added successfully!");
        });

        stage.setTitle("Add Employee");
        stage.setScene(new Scene(grid, 350, 300));
        stage.show();
    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
