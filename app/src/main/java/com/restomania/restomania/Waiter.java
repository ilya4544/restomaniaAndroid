package com.restomania.restomania;

/**
 * Created by Freemahn on 18.10.2014.
 */
public class Waiter {
    int id;
    String name;
    float rating;
    String organization;
    int countRating;


    public Waiter(int id, String name, float rating, String organization, int countRating) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.organization = organization;
        this.countRating = countRating;

    }

    @Override
    public String toString() {
        return "Имя:" + name + "\n" + "Компания:" + organization + " [" + id + ", " + rating + ", " + countRating;
    }
}
