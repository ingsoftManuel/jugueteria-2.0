package cue.edu.co.repositories.impl;

import cue.edu.co.config.DatabaseConnection;
import cue.edu.co.model.Sale;
import cue.edu.co.model.SaleDetail;
import cue.edu.co.repositories.SaleRepository;
import cue.edu.co.repositories.ToyRepository;
import cue.edu.co.repositories.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaleRepositoryJDBCImpl implements SaleRepository {
  private ToyRepository toyRepository;
  private UserRepository userRepository;
  private Connection connection;

  public SaleRepositoryJDBCImpl(ToyRepository toyRepository, UserRepository userRepository) {
    this.toyRepository = toyRepository;
    this.userRepository = userRepository;
    this.connection = DatabaseConnection.getConnection();
  }


  @Override
  public void save(Sale sale) {
    try{
      try(PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO sales (employee_id, customer_id, date) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
        preparedStatement.setLong(1, sale.getEmployee().getId());
        preparedStatement.setLong(2, sale.getCustomer().getId());
        preparedStatement.setDate(3, new Date(sale.getDate().getTime()));
        preparedStatement.executeUpdate();
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
          sale.setId(generatedKeys.getLong(1));
        }
        saveSaleDetails(sale);
        updateStock(sale);
      }

    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }

  }

  @Override
  public List<Sale> findAll() {
    try{
      ResultSet resultSet =  connection.createStatement().executeQuery("SELECT * FROM sales");
      List<Sale> sales = new ArrayList<>();
      while (resultSet.next()){
        Sale sale = mapSale(resultSet);
        sales.add(sale);
      }
      return sales;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private Sale mapSale(ResultSet resultSet) throws SQLException {
    Sale sale = new Sale();
    sale.setId(resultSet.getLong("id"));
    sale.setDate(resultSet.getDate("date"));
    sale.setEmployee(userRepository.findById(resultSet.getLong("employee_id")).get());
    sale.setCustomer(userRepository.findById(resultSet.getLong("customer_id")).get());
    sale.setDetails(findSaleDetails(sale.getId()));
    return sale;
  }

  @Override
  public List<Sale> findByUserId(Long userId) {
    try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM sales WHERE employee_id = ?")){
       preparedStatement.setLong(1, userId);
       ResultSet resultSet = preparedStatement.executeQuery();
        List<Sale> sales = new ArrayList<>();
       while (resultSet.next()){
         Sale sale = mapSale(resultSet);
          sales.add(sale);
       };
        return sales;
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }
  }

  private void saveSaleDetails(Sale sale){
    sale.getDetails().forEach(saleDetail -> {
      try(PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO sale_details (sale_id, toy_id, quantity) VALUES (?, ?, ?)")){
        preparedStatement.setLong(1, sale.getId());
        preparedStatement.setLong(2, saleDetail.getToy().getId());
        preparedStatement.setInt(3, saleDetail.getQuantity());
        preparedStatement.executeUpdate();
      } catch (SQLException ex) {
        throw new RuntimeException(ex);
      }
    });
  }

  private List<SaleDetail> findSaleDetails(Long saleId){
    try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM sale_details WHERE sale_id = ?")){
      preparedStatement.setLong(1, saleId);
      ResultSet resultSet = preparedStatement.executeQuery();
      List<SaleDetail> saleDetails = new ArrayList<>();
      while (resultSet.next()){
        SaleDetail saleDetail = new SaleDetail();
        saleDetail.setToy(toyRepository.findById(resultSet.getLong("toy_id")).get());
        saleDetail.setQuantity(resultSet.getInt("quantity"));
        saleDetails.add(saleDetail);
      }
      return saleDetails;
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }
  }

  private void updateStock(Sale sale){
    sale.getDetails().forEach(saleDetail -> {
      saleDetail.getToy().setAmount(saleDetail.getToy().getAmount() - saleDetail.getQuantity());
      toyRepository.update(saleDetail.getToy());
    });
  }
}
