package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Payroll {
    private static final String PAYMENT_FILE = "Payment.txt";
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    // assuming format Username,Date,Amount,Method

    public void processEmployeePayment(Employee employee, float amount, PaymentMethod method) {
        Date currentDate = new Date();
        String dateString = dateFormat.format(currentDate);

        // Create a temporary Payment object to validate details
        Payment payment = new Payment(employee.getPersonID() + "_" + dateString, amount, currentDate, method);

        savePaymentToFile(employee.getPersonID(), dateString, amount, method.toString());
    }

    private void savePaymentToFile(String username, String date, float amount, String method) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PAYMENT_FILE, true))) {
            // Mapping format to match PaymentHistoryUI expectations
            String record = String.format("%s,%s,%.2f,%s", username, date, amount, method);
            writer.write(record);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to payment file: " + e.getMessage());
        }
    }

    public float calculateNetPay(float grossPay, float taxRate) {
        return grossPay * (1 - taxRate);
    }
}