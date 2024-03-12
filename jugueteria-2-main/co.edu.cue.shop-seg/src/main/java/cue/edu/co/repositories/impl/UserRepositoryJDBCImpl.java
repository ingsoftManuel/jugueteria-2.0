package cue.edu.co.repositories.impl;

import cue.edu.co.config.DatabaseConnection;
import cue.edu.co.model.Role;
import cue.edu.co.model.User;
import cue.edu.co.repositories.UserRepository;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryJDBCImpl implements UserRepository {
  private final Connection connection;

  public UserRepositoryJDBCImpl() {
    this.connection = DatabaseConnection.getConnection();
  }

  @Override
  public List<User> findAll() {
    try{
      ResultSet resultSet =  connection.createStatement().executeQuery("SELECT * FROM users");
      List<User> users = new ArrayList<>();
      while(resultSet.next()){
        User user = mapUser(resultSet);
        users.add(user);
      }
      return users;

    } catch (Exception e) {
      throw new RuntimeException("Error getting users", e);
    }
  }

  @Override
  public Optional<User> findById(Long id) {

    try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE id = ?")){
      preparedStatement.setLong(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();
      if(resultSet.next()){
        User user = mapUser(resultSet);
        return Optional.of(user);
      }
      return Optional.empty();
    } catch (Exception e) {
      throw new RuntimeException("Error getting user", e);
    }
  }

  @Override
  public List<User> findByRole(Role role) {
    try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE role = ?")){
      preparedStatement.setString(1, role.name());
      ResultSet resultSet = preparedStatement.executeQuery();
      List<User> users = new ArrayList<>();
      while(resultSet.next()){
        User user = mapUser(resultSet);
        users.add(user);
      }
      return users;
    } catch (Exception e) {
      throw new RuntimeException("Error getting users", e);
    }
  }

  @Override
  public void save(User user) {
    try(PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (name, email, birth_date, role) VALUES (?, ?, ?, ?)")){
      preparedStatement.setString(1, user.getName());
      preparedStatement.setString(2, user.getEmail());
      preparedStatement.setDate(3, new java.sql.Date(user.getBirthDate().getTime()));
      preparedStatement.setString(4, user.getRole().name());
      preparedStatement.executeUpdate();
    } catch (Exception e) {
      throw new RuntimeException("Error saving user", e);
    }

  }

  private User mapUser(ResultSet resultSet) {
    User user = new User();
    try {
      user.setId(resultSet.getLong("id"));
      user.setName(resultSet.getString("name"));
      user.setEmail(resultSet.getString("email"));
      user.setBirthDate(resultSet.getDate("birth_date"));
      user.setRole(Role.valueOf(resultSet.getString("role")));
    } catch (Exception e) {
      throw new RuntimeException("Error mapping user", e);
    }
    return user;
  }
}
