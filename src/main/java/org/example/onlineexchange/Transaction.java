package org.example.onlineexchange;

public class Transaction {
    public Double OpenRequests ;//price
    public Double amount ;
    public String SellOrBuy ;
    public Transaction (Double openRequests , Double amount , String sellOrBuy){
        this.OpenRequests = openRequests;
        this.amount = amount;
        this.SellOrBuy = sellOrBuy;
    }
    public Transaction (){

    }
}
