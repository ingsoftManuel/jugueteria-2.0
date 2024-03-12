package cue.edu.co.mapper;


import cue.edu.co.dtos.ToysDTO;
import cue.edu.co.model.Toys;

public class ToysMapper {
    public static Toys mapFrom(ToysDTO toysDTO){
        return new Toys(null,toysDTO.name(),toysDTO.price(),toysDTO.amount(),toysDTO.Type());
    }


    public static ToysDTO mapFrom(Toys toy){
        return  new ToysDTO(toy.getName(),toy.getType(),toy.getPrice(),toy.getAmount()  );
    }
}
