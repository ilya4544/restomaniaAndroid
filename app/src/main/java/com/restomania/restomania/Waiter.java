package com.restomania.restomania;

/**
 * Created by Freemahn on 18.10.2014.
 */
public class Waiter {
    int id;
    String name;
    float rating;

    public Waiter(int id, String name, float rating) {
        this.id = id;
        this.name = name;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return name + " " + rating;
    }
}
