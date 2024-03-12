package cue.edu.co.repositories;

import cue.edu.co.model.Sale;

import java.util.List;

public interface SaleRepository {
  void save(Sale sale);
  List<Sale> findAll();
  List<Sale> findByUserId(Long userId);

}
