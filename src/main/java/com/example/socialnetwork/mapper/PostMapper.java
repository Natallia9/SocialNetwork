package com.example.socialnetwork.mapper;

import com.example.socialnetwork.dto.PostDTO;
import com.example.socialnetwork.entity.Post;
import com.example.socialnetwork.entity.User;
import org.mapstruct.*;

import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostMapper {

    PostDTO toDto(Post post);

//    @Mappings({
//            @Mapping(target = "id", source = "postDTO.id"),
//            @Mapping(target = "title", source = "postDTO.title"),
//            @Mapping(target = "caption", source = "postDTO.caption"),
//            @Mapping(target = "location", source = "postDTO.location"),
//            @Mapping(target = "likes", source = "postDTO.likes"),
//            @Mapping(target = "user", ignore = true), // Возможно, потребуется маппировать user вручную позже
//            @Mapping(target = "likedUsers", ignore = true), // Обработать, если нужно
//            @Mapping(target = "comments", ignore = true), // Обработать, если нужно
//            @Mapping(target = "createdDate", ignore = true) // Обработать, если нужно
//    })
//    Post toEntity(PostDTO postDTO);
//
//    @Mappings({
//            @Mapping(target = "id", source = "post.id"),
//            @Mapping(target = "title", source = "post.title"),
//            @Mapping(target = "caption", source = "post.caption"),
//            @Mapping(target = "location", source = "post.location"),
//            @Mapping(target = "username", source = "post.user.username"),
//            @Mapping(target = "likes", source = "post.likes"),
//            @Mapping(target = "usersLiked", expression = "java(mapUsersToUsernames(post.getLikedUsers()))")
//    })
//    PostDTO toDTO(Post post);
//
//    // Вспомогательный метод для маппирования likedUsers (User -> String)
//    default Set<String> mapUsersToUsernames(Set<User> users) {
//        if (users == null) {
//            return null;
//        }
//        Set<String> usernames = new HashSet<>();
//        for (User user : users) {
//            usernames.add(user.getUsername());
//        }
//        return usernames;
//    }
}
//    default Set<String> mapUsersToUsernames(Set<User> users) {
//        if (users == null) {
//            return new HashSet<>();
//        }
//        Set<String> usernames = new HashSet<>();
//        for (User user : users) {
//            usernames.add(user.getUsername());
//        }
//        return usernames;
//    }

