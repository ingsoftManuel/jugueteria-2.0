package cue.edu.co.repositories;

import cue.edu.co.model.Role;
import cue.edu.co.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
  List<User> findAll();
  Optional<User> findById(Long id);
  List<User> findByRole(Role role);
  void save(User user);
}
