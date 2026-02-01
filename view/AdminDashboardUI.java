package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.Admin;
import model.Person;
import model.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AdminDashboardUI extends Application {

    private static final String PERSONS_FILE = "Persons.txt";
    private Admin currentAdmin;

    public AdminDashboardUI(Admin admin) {
        this.currentAdmin = admin;
    }

    @Override
    public void start(Stage primaryStage) {
        if (currentAdmin == null) {
            currentAdmin = new Admin("Admin User", "admin", "admin", "boss");
        }

        BorderPane root = new BorderPane();
        root.setPrefSize(950, 650);

        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #2c3e50;");
        sidebar.setPrefWidth(220);

        Label title = new Label("ADMIN PANEL");
        title.setTextFill(Color.WHITE);
        title.setFont(Font.font("System", FontWeight.BOLD, 18));

        Button btnAdd = createSidebarButton("Add Employee");
        Button btnUpdate = createSidebarButton("Update Employee");
        Button btnRemove = createSidebarButton("Remove Employee");
        Button btnPayroll = createSidebarButton("Process Payroll");
        Button btnLogout = createSidebarButton("Logout");


        btnAdd.setOnAction(e -> new AddEmployeeUI(currentAdmin).start(new Stage()));
        btnUpdate.setOnAction(e -> new UpdateEmployeeUI(currentAdmin).start(new Stage()));
        btnRemove.setOnAction(e -> new RemoveEmployeeUI(currentAdmin).start(new Stage()));
        btnPayroll.setOnAction(e -> new ProcessPayrollUI().start(new Stage()));

        btnLogout.setStyle("-fx-background-color: #c0392b; -fx-text-fill: white; -fx-alignment: CENTER_LEFT;");
        btnLogout.setOnAction(e -> {
            primaryStage.close();
            new LoginUI().start(new Stage());
        });

        sidebar.getChildren().addAll(title, new Separator(), btnAdd, btnUpdate, btnRemove, btnPayroll, new Region(), btnLogout);
        VBox.setVgrow(sidebar.getChildren().get(6), Priority.ALWAYS);

        VBox mainContent = new VBox(25);
        mainContent.setPadding(new Insets(30));
        mainContent.setStyle("-fx-background-color: #f4f7f6;");

        Label welcomeLabel = new Label("Administrator Dashboard");
        welcomeLabel.setFont(Font.font("System", FontWeight.BOLD, 26));

        HBox statsCards = new HBox(20);
        statsCards.getChildren().addAll(
                createStatCard("Total Employees", String.valueOf(countEmployees()), "#2980b9"),
                createStatCard("System Status", "Online", "#27ae60")
        );

        mainContent.getChildren().addAll(welcomeLabel, new Label("Welcome, " + currentAdmin.getName()), statsCards);

        root.setLeft(sidebar);
        root.setCenter(mainContent);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Admin Portal");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private int countEmployees() {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(PERSONS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty() && !line.startsWith("[")) count++;
            }
        } catch (IOException e) { e.printStackTrace(); }
        return count;
    }

    private Button createSidebarButton(String text) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-alignment: CENTER_LEFT; -fx-cursor: hand;");
        return btn;
    }

    private VBox createStatCard(String title, String value, String color) {
        VBox card = new VBox(5);
        card.setPadding(new Insets(15));
        card.setPrefWidth(220);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");
        Label lblTitle = new Label(title); lblTitle.setTextFill(Color.GRAY);
        Label lblValue = new Label(value); lblValue.setFont(Font.font("System", FontWeight.BOLD, 22));
        lblValue.setTextFill(Color.web(color));
        card.getChildren().addAll(lblTitle, lblValue);
        return card;
    }
}