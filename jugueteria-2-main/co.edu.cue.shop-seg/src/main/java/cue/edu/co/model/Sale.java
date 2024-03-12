package cue.edu.co.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class Sale {
  private Long id;
  private User employee;
  private User customer;
  private Date date;
  private List<SaleDetail> details;
}
