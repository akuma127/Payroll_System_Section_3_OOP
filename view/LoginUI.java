package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.DataStore;
import model.Person;
import model.Admin;
import model.Employee;
import java.util.List;

public class LoginUI extends Application {

    @Override
    public void start(Stage stage) {
        Label titleLabel = new Label("Payroll System Login");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label idLabel = new Label("User ID:");
        Label passwordLabel = new Label("Password:");

        TextField idField = new TextField();
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Login");
        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: red;");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        grid.add(idLabel, 0, 0);
        grid.add(idField, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(loginButton, 1, 2);

        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(titleLabel, grid, messageLabel);

        loginButton.setOnAction(e -> {
            String inputId = idField.getText();
            String inputPass = passwordField.getText();
            boolean found = false;

            List<Person> people = DataStore.loadPersons();

            for (Person p : people) {
                if (p.getPersonID().equals(inputId) && p.login(inputPass)) {
                    found = true;

                    if (p instanceof Admin) {
                        openAdminDashboard((Admin) p);
                    } else if (p instanceof Employee) {
                        openEmployeeDashboard((Employee) p);
                    }
                    stage.close();
                    break;
                }
            }

            if (!found) {
                messageLabel.setText("Invalid ID or Password");
            }
        });

        Scene scene = new Scene(root, 350, 300);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }


    private void openAdminDashboard(Admin admin) {
        try {
            Stage adminStage = new Stage();
            AdminDashboardUI adminApp = new AdminDashboardUI(admin);
            adminApp.start(adminStage);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void openEmployeeDashboard(Employee emp) {
        try {
            Stage empStage = new Stage();
            EmployeeDashboardUI empApp = new EmployeeDashboardUI(emp);
            empApp.start(empStage);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}