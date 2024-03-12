package cue.edu.co.dtos;

import cue.edu.co.model.Role;

import java.util.Date;

public record CreateUserDTO(
    String name,
    String email,
    Date birthDate,
    Role role
) {
}
