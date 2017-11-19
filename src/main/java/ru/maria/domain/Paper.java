package ru.maria.domain;

/**
 * Created by mariafedotova on 19.11.2017.
 */
public enum Paper {
    A(2), B(3), C(4), D(5);

    private int position;

    Paper(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
