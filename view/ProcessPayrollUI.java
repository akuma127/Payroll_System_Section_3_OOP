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
import model.Payroll;
import model.PaymentMethod;
import model.DataStore;
import model.Person;

import java.util.List;

public class ProcessPayrollUI extends Application {

    private Payroll payroll = new Payroll();

    @Override
    public void start(Stage stage) {
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #f4f7f6;");

        Label title = new Label("Process Employee Payroll");
        title.setFont(Font.font("System", FontWeight.BOLD, 20));

        // Search Section
        GridPane searchGrid = new GridPane();
        searchGrid.setHgap(10);
        TextField searchField = new TextField();
        searchField.setPromptText("Enter Employee Username");
        Button btnSearch = new Button("Find Employee");
        searchGrid.add(searchField, 0, 0);
        searchGrid.add(btnSearch, 1, 0);

        // Payment Form (Hidden until employee found)
        GridPane form = new GridPane();
        form.setHgap(15);
        form.setVgap(15);
        form.setPadding(new Insets(20));
        form.setStyle("-fx-background-color: white; -fx-background-radius: 8;");
        form.setVisible(false);

        Label nameDisplay = new Label();
        TextField grossPayField = new TextField();
        TextField taxRateField = new TextField("0.15");
        ComboBox<PaymentMethod> methodBox = new ComboBox<>();
        methodBox.getItems().setAll(PaymentMethod.values());

        form.add(new Label("Employee:"), 0, 0);
        form.add(nameDisplay, 1, 0);
        form.add(new Label("Gross Pay (Birr):"), 0, 1);
        form.add(grossPayField, 1, 1);
        form.add(new Label("Tax Rate (e.g. 0.15):"), 0, 2);
        form.add(taxRateField, 1, 2);
        form.add(new Label("Method:"), 0, 3);
        form.add(methodBox, 1, 3);

        Button btnProcess = new Button("Confirm Payment");
        btnProcess.setMaxWidth(Double.MAX_VALUE);
        btnProcess.setPrefHeight(40);
        btnProcess.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
        btnProcess.setVisible(false);

        final Employee[] selectedEmployee = {null};


        btnSearch.setOnAction(e -> {
            String username = searchField.getText().trim();
            List<Person> people = DataStore.loadPersons();

            for (Person p : people) {
                if (p.getPersonID().equalsIgnoreCase(username) && p instanceof Employee) {
                    selectedEmployee[0] = (Employee) p;
                    nameDisplay.setText(p.getName());
                    form.setVisible(true);
                    btnProcess.setVisible(true);
                    return;
                }
            }
            showAlert("Error", "Employee not found.");
        });


        btnProcess.setOnAction(e -> {
            try {
                float gross = Float.parseFloat(grossPayField.getText());
                float tax = Float.parseFloat(taxRateField.getText());
                PaymentMethod method = methodBox.getValue();

                if (method == null) {
                    showAlert("Error", "Please select a payment method.");
                    return;
                }

                float netPay = payroll.calculateNetPay(gross, tax);
                payroll.processEmployeePayment(selectedEmployee[0], netPay, method);

                showAlert("Success", "Payment of Birr " + String.format("%.2f", netPay) + " processed for " + selectedEmployee[0].getName());
                stage.close();
            } catch (NumberFormatException ex) {
                showAlert("Error", "Please enter valid numeric values for pay and tax.");
            }
        });

        root.getChildren().addAll(title, searchGrid, form, btnProcess);

        Scene scene = new Scene(root, 500, 500);
        stage.setTitle("Admin - Payroll Processing");
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