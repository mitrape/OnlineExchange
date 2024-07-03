package org.example.onlineexchange;

public class TransactionData {
    private String currency;
    private double transaction;
    private double amount;
    private String sellOrBuy;
    private String date;

    public TransactionData(String currency, double transaction, double amount, String sellOrBuy, String date) {
        this.currency = currency;
        this.transaction = transaction;
        this.amount = amount;
        this.sellOrBuy = sellOrBuy;
        this.date = date;
    }

    public String getCurrency() {
        return currency;
    }

    public double getTransaction() {
        return transaction;
    }

    public double getAmount() {
        return amount;
    }

    public String getSellOrBuy() {
        return sellOrBuy;
    }

    public String getDate() {
        return date;
    }
}
