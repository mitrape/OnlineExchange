package org.example.onlineexchange;

public class Transaction {
    private Double OpenRequests; // price
    private Double amount;
    private String SellOrBuy;

    public Transaction(Double openRequests, Double amount, String sellOrBuy) {
        this.OpenRequests = openRequests;
        this.amount = amount;
        this.SellOrBuy = sellOrBuy;
    }

    public Double getOpenRequests() {
        return OpenRequests;
    }

    public Double getAmount() {
        return amount;
    }

    public String getSellOrBuy() {
        return SellOrBuy;
    }
}
