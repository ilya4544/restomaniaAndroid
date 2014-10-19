package com.restomania.restomania;

/**
 * Created by Freemahn on 18.10.2014.
 */
public class Waiter {
    int id;
    String name;
    float rating;
    String organization;


    public Waiter(int id, String name, float rating, String organization) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.organization = organization;

    }

    @Override
    public String toString() {
        return "Имя:" + name + "\n" + "Рейтинг:" + rating + " \n" + "Компания:" + organization;
    }
}
