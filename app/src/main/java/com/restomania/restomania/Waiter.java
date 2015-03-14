package com.restomania.restomania;

/**
 * Created by Freemahn on 18.10.2014.
 */
public class Waiter {
    int id;
    String name;
    String rating;
    String organization;
    int countReviews;


    public Waiter(int id, String name, String rating, String organization, int countReviews) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.organization = organization;
        this.countReviews = countReviews;

    }

    @Override
    public String toString() {
        return "Имя:" + name + "\n" + "Компания:" + organization + " [" + id + ", " + rating + ", " + countReviews;
    }
}
