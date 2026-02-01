package model;

import java.util.Date;

public class Payment {


    private String paymentID;
    private float amount;
    private Date date;
    private PaymentMethod method;

    public Payment(String paymentID, float amount, Date date, PaymentMethod method) {
        this.paymentID = paymentID;
        this.amount = amount;
        this.date = date;
        this.method = method;
    }


    public String getPaymentID() {
        return paymentID;
    }

    public float getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public PaymentMethod getMethod() {
        return method;
    }


    public String getDetails() {
        return "model.Payment ID: " + paymentID +
                "\nAmount: " + amount +
                "\nDate: " + date +
                "\nmodel.Payment Method: " + method;
    }
}

