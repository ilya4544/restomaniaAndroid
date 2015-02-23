package com.restomania.restomania;

/**
 * Created by Freemahn on 19.10.2014.
 */
public class User {
    String id;
    String name;
    String login;
    String hash;
    String token;
    float balance;

    User() {

    }

    public User(String id, String name, String login, String hash, float balance) {
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
