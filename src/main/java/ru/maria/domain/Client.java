package ru.maria.domain;

import java.math.BigInteger;
import java.util.Map;

/**
 * Created by mariafedotova on 18.11.2017.
 */
public class Client {


    private String name;
    private BigInteger dollars;
    private Map<String, BigInteger> papers;

    public Client(String name, BigInteger dollars, Map<String, BigInteger> papers) {
        this.name = name;
        this.dollars = dollars;
        this.papers = papers;
    }

    public String getName() {
        return name;
    }

    public BigInteger getDollars() {
        return dollars;
    }

    public void setDollars(BigInteger dollars) {
        this.dollars = dollars;
    }

    public Map<String, BigInteger> getPapers() {
        return papers;
    }

}
