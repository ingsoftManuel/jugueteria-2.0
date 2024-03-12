package cue.edu.co.dtos;


import cue.edu.co.model.Category;

public record ToysDTO(String name, Category Type, double price, double amount) {
}
