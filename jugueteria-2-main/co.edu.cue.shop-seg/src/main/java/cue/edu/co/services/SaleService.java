package cue.edu.co.services;

import cue.edu.co.model.Sale;

import java.util.List;

public interface SaleService {
  void save(Sale sale);
  List<Sale> getAll();
  List<Sale> getByUserId(Long userId);
}
