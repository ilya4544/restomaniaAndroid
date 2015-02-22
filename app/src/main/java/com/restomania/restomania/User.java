package com.restomania.restomania;

/**
 * Created by Freemahn on 19.10.2014.
 */
public class User {
    private String id;
    private String login;
    private String hash;
    private String name;

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private int balance;

    public User(String id, String login, String hash, String name, int balance) {
        this.id = id;
        this.login = login;
        this.hash = hash;
        this.name = name;
        this.balance = balance;
    }

    public String toString() {
        return name + " " + id + " " + balance;
    }
}
