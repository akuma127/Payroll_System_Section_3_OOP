package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.Employee;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AccountSettingUI extends Application {

    private Employee currentEmployee;

    public AccountSettingUI(Employee employee) {
        this.currentEmployee = employee;
    }

    @Override
    public void start(Stage stage) {
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #f4f7f6;");

        Label title = new Label("Account Settings");
        title.setFont(Font.font("System", FontWeight.BOLD, 20));

        GridPane form = new GridPane();
        form.setHgap(15);
        form.setVgap(15);
        form.setPadding(new Insets(20));
        form.setStyle("-fx-background-color: white; -fx-background-radius: 8;");

        // Pre-filled with current employee data
        TextField nameField = new TextField(currentEmployee.getName());
        PasswordField passField = new PasswordField();
        passField.setPromptText("Enter new password");

        form.add(new Label("Full Name:"), 0, 0);
        form.add(nameField, 1, 0);
        form.add(new Label("New Password:"), 0, 1);
        form.add(passField, 1, 1);

        Button saveBtn = new Button("Update Profile");
        saveBtn.setMaxWidth(Double.MAX_VALUE);
        saveBtn.setPrefHeight(40);
        saveBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");

        saveBtn.setOnAction(e -> {
            String newName = nameField.getText().trim();
            String newPass = passField.getText().trim();

            if (newName.isEmpty() || newPass.isEmpty()) {
                showAlert("Error", "Fields cannot be empty.");
                return;
            }

            currentEmployee.updateSelfInFile(newName, newPass);
            showAlert("Success", "Account updated! Please log in again.");
            stage.close();
        });

        root.getChildren().addAll(title, form, saveBtn);

        Scene scene = new Scene(root, 400, 350);
        stage.setTitle("Settings - " + currentEmployee.getPersonID());
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
