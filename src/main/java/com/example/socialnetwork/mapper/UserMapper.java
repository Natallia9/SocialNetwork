package com.example.socialnetwork.mapper;

import com.example.socialnetwork.dto.UserDTO;
import com.example.socialnetwork.entity.User;
import org.mapstruct.*;

import java.util.List;


//@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
//public interface UserMapper {

//    @Mappings({
//            @Mapping(target = "id", source = "userDTO.id"),
//            @Mapping(target = "firstName", source = "userDTO.firstname"),
//            @Mapping(target = "lastName", source = "userDTO.lastname"),
//            @Mapping(target = "userName", source = "userDTO.username"),
//            @Mapping(target = "email", ignore = true),
//            @Mapping(target = "password", ignore = true),
//            @Mapping(target = "bio", source = "userDTO.bio"),
//            @Mapping(target = "roles", ignore = true),
//            @Mapping(target = "posts", ignore = true),
//            @Mapping(target = "createdDate", ignore = true),
//            @Mapping(target = "authorities", ignore = true)
//    })
//    User toEntity(UserDTO userDTO);
//
//    @Mappings({
//            @Mapping(target = "id", source = "user.id"),
//            @Mapping(target = "firstname", source = "user.firstName"),
//            @Mapping(target = "lastname", source = "user.lastName"),
//            @Mapping(target = "username", source = "user.userName"),
//            @Mapping(target = "bio", source = "user.bio")
//    })
//    UserDTO toDto(User user);
//}



@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserDTO toDto(User user);
    List<UserDTO> toUserDTOList(List<User> users);
}
