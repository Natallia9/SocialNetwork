package com.example.socialnetwork.mapper;

import com.example.socialnetwork.dto.PostDTO;
import com.example.socialnetwork.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mappings({
            @Mapping(target = "id", source = "postDTO.id"),
            @Mapping(target = "title", source = "postDTO.title"),
            @Mapping(target = "caption", source = "postDTO.caption"),
            @Mapping(target = "location", source = "postDTO.location"),
            @Mapping(target = "likes", source = "postDTO.likes"),
            @Mapping(target = "user", ignore = true),
            @Mapping(target = "likedUsers", ignore = true),
            @Mapping(target = "comments", ignore = true),
            @Mapping(target = "createdDate", ignore = true)
    })
    Post toEntity(PostDTO postDTO);

    @Mappings({
            @Mapping(target = "id", source = "post.id"),
            @Mapping(target = "title", source = "post.title"),
            @Mapping(target = "caption", source = "post.caption"),
            @Mapping(target = "location", source = "post.location"),
            @Mapping(target = "username", source = "post.user.username"),
            @Mapping(target = "likes", source = "post.likes"),
            @Mapping(target = "usersLiked", expression = "java(mapUsersToUsernames(post.getLikedUsers()))")
    })
    PostDTO toDto(Post post);

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
}
