package ru.maria.domain;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

/**
 * Created by mariafedotova on 18.11.2017.
 */
public class Client {


    private String name;
    private BigInteger dollars;
    private Map<Paper,BigInteger> papers;

    public Client(String name, BigInteger dollars, Map<Paper, BigInteger> papers) {
        this.name = name;
        this.dollars = dollars;
        this.papers = papers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getDollars() {
        return dollars;
    }

    public void setDollars(BigInteger dollars) {
        this.dollars = dollars;
    }

    public Map<Paper, BigInteger> getPapers() {
        return papers;
    }

    public void setPapers(Map<Paper, BigInteger> papers) {
        this.papers = papers;
    }
}
