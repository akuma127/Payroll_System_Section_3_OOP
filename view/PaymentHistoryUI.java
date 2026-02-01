package view;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PaymentHistoryUI extends Application {

    private Employee currentEmployee;
    private TableView<String[]> table = new TableView<>();

    public PaymentHistoryUI(Employee employee) {
        this.currentEmployee = employee;
    }

    @Override
    public void start(Stage stage) {
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #f4f7f6;");

        HBox header = new HBox(20);
        Label title = new Label("Payment History");
        title.setFont(Font.font("System", FontWeight.BOLD, 22));

        Button refreshBtn = new Button("Refresh Data");
        refreshBtn.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white;");
        refreshBtn.setOnAction(e -> table.setItems(this.currentEmployee.loadPaymentData()));

        header.getChildren().addAll(title, refreshBtn);

        TableColumn<String[], String> colDate = new TableColumn<>("Date");
        colDate.setCellValueFactory(d -> new SimpleStringProperty(d.getValue()[1]));

        TableColumn<String[], String> colAmount = new TableColumn<>("Amount Paid");
        colAmount.setCellValueFactory(d -> new SimpleStringProperty("Birr " + d.getValue()[2]));

        TableColumn<String[], String> colMethod = new TableColumn<>("Method");
        colMethod.setCellValueFactory(d -> new SimpleStringProperty(d.getValue()[3]));

        table.getColumns().addAll(colDate, colAmount, colMethod);
        table.setItems(this.currentEmployee.loadPaymentData());
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        root.getChildren().addAll(header, table);

        Scene scene = new Scene(root, 600, 450);
        stage.setTitle("Financials: " + currentEmployee.getName());
        stage.setScene(scene);
        stage.show();
    }

}