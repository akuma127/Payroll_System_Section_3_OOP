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
import model.Employee;

public class EmployeeDashboardUI extends Application {

    private Employee currentEmployee;

    public EmployeeDashboardUI(Employee employee) {
        this.currentEmployee = employee;
    }

    @Override
    public void start(Stage primaryStage) {
        if (currentEmployee == null) {
            currentEmployee = new Employee("Guest User", "guest", "N/A", "General Staff");
        }

        BorderPane root = new BorderPane();
        root.setPrefSize(950, 650);

        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #2c3e50;");
        sidebar.setPrefWidth(220);

        Label title = new Label("EMP-SYS v1.0");
        title.setTextFill(Color.WHITE);
        title.setFont(Font.font("System", FontWeight.BOLD, 18));


        Button btnPayroll = createSidebarButton("Payment History");
        Button btnSettings = createSidebarButton("Account Settings");
        Button btnLogout = createSidebarButton("Logout");


        btnPayroll.setOnAction(e -> new PaymentHistoryUI(currentEmployee).start(new Stage()));
        btnSettings.setOnAction(e -> new AccountSettingUI(currentEmployee).start(new Stage()));

        btnLogout.setStyle("-fx-background-color: #c0392b; -fx-text-fill: white; -fx-alignment: CENTER_LEFT;");
        btnLogout.setOnAction(e -> {
            primaryStage.close();
            new LoginUI().start(new Stage());
        });

        sidebar.getChildren().addAll(title, new Separator(),new Region(), btnPayroll, btnSettings, new Region(), btnLogout);
        VBox.setVgrow(sidebar.getChildren().get(5), Priority.ALWAYS);

        VBox mainContent = new VBox(25);
        mainContent.setPadding(new Insets(30));
        mainContent.setStyle("-fx-background-color: #f4f7f6;");

        Label welcomeLabel = new Label("Welcome back, " + currentEmployee.getName());
        welcomeLabel.setFont(Font.font("System", FontWeight.BOLD, 26));

        HBox statsCards = new HBox(20);
        statsCards.getChildren().addAll(
                createStatCard("Status", "Active", "#27ae60"),
                createStatCard("Role", currentEmployee.getJobTitle(), "#2980b9")
        );

        mainContent.getChildren().addAll(welcomeLabel, statsCards);
        root.setLeft(sidebar);
        root.setCenter(mainContent);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Employee Dashboard - " + currentEmployee.getPersonID());
        primaryStage.setScene(scene);
        primaryStage.show();
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
        Label lblValue = new Label(value); lblValue.setFont(Font.font("System", FontWeight.BOLD, 18));
        lblValue.setTextFill(Color.web(color));
        card.getChildren().addAll(lblTitle, lblValue);
        return card;
    }
}