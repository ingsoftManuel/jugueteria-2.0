package cue.edu.co.repositories;

import cue.edu.co.model.Toys;

import java.util.List;
import java.util.Optional;

public interface ToyRepository {
  List<Toys> findAll();
  Optional<Toys> findById(Long id);
  void save(Toys toy);
  void update(Toys toy);
}
