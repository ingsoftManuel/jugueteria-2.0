package cue.edu.co.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SaleDetail {
  private Long id;
  private Sale sale;
  private Toys toy;
  private int quantity;
}
