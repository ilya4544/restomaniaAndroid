package com.restomania.restomania;

/**
 * Created by Freemahn on 18.10.2014.
 */
public class Waiter {
    String name;
    double rate;

    public Waiter(String name, double rate) {
        this.name = name;
        this.rate = rate;
    }

    @Override
    public String toString() {
        return name + " " + rate;
    }
}
