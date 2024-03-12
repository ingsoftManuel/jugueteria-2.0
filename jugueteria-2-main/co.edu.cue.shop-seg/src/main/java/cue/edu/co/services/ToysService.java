package cue.edu.co.services;

import cue.edu.co.dtos.ToysDTO;
import cue.edu.co.model.Toys;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ToysService {
    List<ToysDTO> addToy(ToysDTO toysDTO) throws Exception;

    <ToysDTO> List<ToysDTO> addToy(ToysDTO toysDTO) throws Exception;
    <ToysDTO> List<ToysDTO> listToys();

    Optional<Toys> findById(Long id);


    <ToysDTO> ToysDTO search(String name) throws Exception;
    Map.Entry<Type,Integer> maxToy() throws Exception;
    Map.Entry<Type,Integer> minToy() throws Exception;
    List<ToysDTO> increase(ToysDTO toysDTO, int amount) throws Exception;
    List<ToysDTO> decrease(ToysDTO toysDTO, int amount) throws Exception;
    <Category> Map<Category, Integer> showByType() throws Exception;
    Map<Type,Integer> sort() throws Exception;
    <ToysDTO> List<ToysDTO> showToysAbove(double value) throws Exception;
    Boolean verifyExist(String name);
    Integer totalToys() throws Exception;
//    void  update(ToysDTO toysDTO) throws Exception;


}
