package cue.edu.co.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Toys {
    private Long id;
    private String name;
    private double price;
    private double amount;
    private Category type;

}
