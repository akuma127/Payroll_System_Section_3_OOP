package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Admin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RemoveEmployeeUI extends Application {
    private Admin currentAdmin;
    public RemoveEmployeeUI(Admin currentAdmin) {
        this.currentAdmin = currentAdmin;

    }

    @Override
    public void start(Stage stage) {


        Label usernameLabel = new Label("Username to Remove:");
        Label statusLabel = new Label("Status: Ready");


        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter exact username");

        Button removeButton = new Button("Remove Employee");
        removeButton.setStyle("-fx-background-color: #c0392b; -fx-text-fill: white;");


        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(removeButton, 1, 1);
        grid.add(statusLabel, 1, 2);


        removeButton.setOnAction(e -> {
            String targetUser = usernameField.getText().trim();

            if (targetUser.isEmpty()) {
                showAlert("Error", "Please enter a username.");
                return;
            }

            boolean removed = currentAdmin.removeEmployeeFromFile(targetUser);

            if (removed) {
                showAlert("Success", "Employee " + targetUser + " removed successfully!");
                usernameField.clear();
            } else {
                showAlert("Error", "Employee not found.");
            }
        });

        stage.setTitle("Remove Employee");
        stage.setScene(new Scene(grid, 400, 200));
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