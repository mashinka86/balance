package ru.maria.domain;

import java.math.BigInteger;

/**
 * Created by mariafedotova on 22.11.2017.
 */
public class Operation {

    private String paper;

    private BigInteger price;

    private BigInteger count;

    private String clientName;


    public Operation(String paper, BigInteger price, BigInteger count, String clientName) {
        this.paper = paper;
        this.price = price;
        this.count = count;
        this.clientName = clientName;
    }

    public String getPaper() {
        return paper;
    }

    public BigInteger getPrice() {
        return price;
    }

    public BigInteger getCount() {
        return count;
    }

    public String getClientName() {
        return clientName;
    }

    @Override
    public boolean equals(Object operation) {
        if (operation instanceof Operation) {
            Operation that = (Operation) operation;
            return this.paper.equals(that.paper) && this.price.equals(that.price) && this.count.equals(that.count);
        }
        return false;
    }
}
