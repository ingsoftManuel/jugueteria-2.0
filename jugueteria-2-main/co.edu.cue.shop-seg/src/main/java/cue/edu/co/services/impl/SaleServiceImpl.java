package cue.edu.co.services.impl;

import cue.edu.co.exceptions.SaleException;
import cue.edu.co.model.Sale;
import cue.edu.co.repositories.SaleRepository;
import cue.edu.co.services.SaleService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class SaleServiceImpl implements SaleService {
  private final SaleRepository saleRepository;
  @Override
  public void save(Sale sale) {
    validateSaleDetailStock(sale);
    saleRepository.save(sale);
  }

  @Override
  public List<Sale> getAll() {
    return saleRepository.findAll();
  }

  @Override
  public List<Sale> getByUserId(Long userId) {
    return saleRepository.findByUserId(userId);
  }

  private void validateSaleDetailStock(Sale sale) {
    sale.getDetails().forEach(detail -> {
      if (detail.getQuantity() > detail.getToy().getAmount()) {
        throw new SaleException("Insufficient stock for toy " + detail.getToy().getName());
      }
    });
  }
}
