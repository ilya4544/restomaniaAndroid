package com.restomania.restomania;

/**
 * Created by Freemahn on 19.10.2014.
 */
public class User {
    String id;
    String login;
    String hash;
    String name;
    int balance;

    public User(String id, String login, String hash, String name, int balance) {
        this.id = id;
        this.login = login;
        this.hash = hash;
        this.name = name;
        this.balance = balance;
    }
}
