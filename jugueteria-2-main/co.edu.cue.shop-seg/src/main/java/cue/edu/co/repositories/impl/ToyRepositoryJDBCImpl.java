package cue.edu.co.repositories.impl;

import cue.edu.co.config.DatabaseConnection;
import cue.edu.co.model.Category;
import cue.edu.co.model.Toys;
import cue.edu.co.repositories.ToyRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ToyRepositoryJDBCImpl implements ToyRepository {
  private Connection connection;
  public ToyRepositoryJDBCImpl(){
    this.connection = DatabaseConnection.getConnection();
  }
  @Override
  public List<Toys> findAll() {
    try{
      ResultSet resultSet =  connection.createStatement().executeQuery("SELECT * FROM toys");
      List<Toys> toys = new ArrayList<>();
      while (resultSet.next()){
        Toys toy = mapToy(resultSet);
        toys.add(toy);
      }
      return toys;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Optional<Toys> findById(Long id) {
    try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM toys WHERE id = ?")){
      preparedStatement.setLong(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();
      if(resultSet.next()){
        Toys toy = mapToy(resultSet);
        return Optional.of(toy);
      }
      return Optional.empty();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void save(Toys toy) {
    try(PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO toys (name, price, stock, category) VALUES (?, ?, ?, ?)")){
      preparedStatement.setString(1, toy.getName());
      preparedStatement.setDouble(2, toy.getPrice());
      preparedStatement.setDouble(3, toy.getAmount());
      preparedStatement.setString(4, toy.getType().toString());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public void update(Toys toy) {
    try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE toys SET name = ?, price = ?, stock = ?, category = ? WHERE id = ?")){
      preparedStatement.setString(1, toy.getName());
      preparedStatement.setDouble(2, toy.getPrice());
      preparedStatement.setDouble(3, toy.getAmount());
      preparedStatement.setString(4, toy.getType().name());
      preparedStatement.setLong(5, toy.getId());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }
  private Toys mapToy(ResultSet resultSet) throws SQLException {
    Toys toy = new Toys();
    try {
      toy.setId(resultSet.getLong("id"));
      toy.setName(resultSet.getString("name"));
      toy.setPrice(resultSet.getDouble("price"));
      toy.setAmount(resultSet.getDouble("stock"));
      toy.setType(Category.valueOf(resultSet.getString("category")));
      return toy;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }
}
