package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.Admin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UpdateEmployeeUI extends Application {
    private Admin currentAdmin;

    public UpdateEmployeeUI(Admin currentAdmin) {
        this.currentAdmin = currentAdmin;

    }

    @Override
    public void start(Stage stage) {
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #f4f7f6;");

        Label title = new Label("Update Employee Details");
        title.setFont(Font.font("System", FontWeight.BOLD, 20));

        // Search Bar
        HBox topBar = new HBox(10);
        TextField searchField = new TextField();
        searchField.setPromptText("Enter Username...");
        Button loadBtn = new Button("Load Data");
        loadBtn.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white;");
        topBar.getChildren().addAll(searchField, loadBtn);

        // Edit Form
        GridPane form = new GridPane();
        form.setHgap(15);
        form.setVgap(15);
        form.setPadding(new Insets(20));
        form.setStyle("-fx-background-color: white; -fx-background-radius: 8;");

        TextField nameField = new TextField();
        TextField passwordField = new TextField();
        ComboBox<String> roleBox = new ComboBox<>();
        roleBox.getItems().addAll("admin", "manager", "employee");

        form.add(new Label("Full Name:"), 0, 0);
        form.add(nameField, 1, 0);
        form.add(new Label("Password:"), 0, 1);
        form.add(passwordField, 1, 1);
        form.add(new Label("Role:"), 0, 2);
        form.add(roleBox, 1, 2);

        Button saveBtn = new Button("Update Information");
        saveBtn.setMaxWidth(Double.MAX_VALUE);
        saveBtn.setPrefHeight(40);
        saveBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");


        loadBtn.setOnAction(e -> {
            String targetUser = searchField.getText().trim();
            String[] data = currentAdmin.findEmployee(targetUser);
            if (data != null) {
                nameField.setText(data[0]);
                passwordField.setText(data[2]);
                roleBox.setValue(data[3]);
                showAlert("Loaded", "Employee data found.");
            } else {
                showAlert("Error", "Employee not found.");
            }
        });


        saveBtn.setOnAction(e -> {
            String username = searchField.getText().trim();
            String newName = nameField.getText().trim();
            String newPass = passwordField.getText().trim();
            String newRole = roleBox.getValue();

            if (username.isEmpty() || newName.isEmpty() || newPass.isEmpty() || newRole == null) {
                showAlert("Error", "All fields must be filled.");
                return;
            }

            this.currentAdmin.updateEmployeeInFile(username, newName, newPass, newRole);
            showAlert("Success", "Employee updated successfully.");
        });

        root.getChildren().addAll(title, topBar, form, saveBtn);

        Scene scene = new Scene(root, 500, 450);
        stage.setTitle("Admin - Edit Employee");
        stage.setScene(scene);
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