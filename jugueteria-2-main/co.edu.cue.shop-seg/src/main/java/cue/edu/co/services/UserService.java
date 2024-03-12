package cue.edu.co.services;

import cue.edu.co.dtos.CreateUserDTO;
import cue.edu.co.model.Role;
import cue.edu.co.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
  List<User> getAll();
  Optional<User> getById(Long id);
  void save(CreateUserDTO user);
  List<User> getByRole(Role role);
}
