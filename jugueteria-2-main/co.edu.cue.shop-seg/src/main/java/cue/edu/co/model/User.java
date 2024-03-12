package cue.edu.co.model;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
  private Long id;
  private String name;
  private String email;
  private Date birthDate;
  private Role role;

}
