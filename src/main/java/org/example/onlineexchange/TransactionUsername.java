package org.example.onlineexchange;

public class TransactionUsername {
    private String currency;
    private double transaction;
    private double amount;
    private String sellOrBuy;
    private String username;
    private String date;

    public TransactionUsername(String currency, double transaction, double amount, String sellOrBuy,String username, String date) {
        this.currency = currency;
        this.transaction = transaction;
        this.amount = amount;
        this.sellOrBuy = sellOrBuy;
        this.username = username;
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
    public String getUsername(){return username; }

    public String getDate() {
        return date;
    }
}
