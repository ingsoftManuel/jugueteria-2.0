package cue.edu.co.services.impl;

import cue.edu.co.dtos.CreateUserDTO;
import cue.edu.co.mapper.UserMapper;
import cue.edu.co.model.Role;
import cue.edu.co.model.User;
import cue.edu.co.repositories.UserRepository;
import cue.edu.co.services.UserService;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;
  @Override
  public List<User> getAll() {
    return userRepository.findAll();
  }

  @Override
  public Optional<User> getById(Long id) {
    return userRepository.findById(id);
  }

  @Override
  public void save(CreateUserDTO user ) {
    User newUser = UserMapper.mapFrom(user);
    userRepository.save(newUser);
  }

  @Override
  public List<User> getByRole(Role role) {
    return userRepository.findByRole(role);
  }
}
