package cue.edu.co.mapper;

import cue.edu.co.dtos.CreateUserDTO;
import cue.edu.co.model.User;

public class UserMapper {
    public static User mapFrom(CreateUserDTO createUserDTO){
        return new User(null,createUserDTO.name(),createUserDTO.email(),createUserDTO.birthDate(),createUserDTO.role());
    }
    public static CreateUserDTO mapFrom(User user){
        return new CreateUserDTO(user.getName(),user.getEmail(),user.getBirthDate(),user.getRole());
    }
}
