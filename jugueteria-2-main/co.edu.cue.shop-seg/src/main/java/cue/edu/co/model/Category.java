package cue.edu.co.model;

import java.util.Arrays;

public enum Category {
    MALE(0),
    FEMALE(1),
    UNISEX(2);

    private  final int value;

    Category(int value){this.value = value;}
    public  static Category fromName(int value) {
        return Arrays.stream(Category.values()).filter(e -> e.value==value).findFirst().orElseThrow();
    }
}
