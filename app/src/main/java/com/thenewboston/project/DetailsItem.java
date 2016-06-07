package com.thenewboston.project;

/**
 * Created by Anurata on 06-Jun-16.
 */
public class DetailsItem {
    int serial;
    String path;

    @Override
    public String toString() {
        return path;
    }

    public DetailsItem(String path) {
        this.path = path;
    }
}
