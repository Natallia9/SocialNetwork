package com.example.socialnetwork.mapper;

import com.example.socialnetwork.dto.CommentDTO;
import com.example.socialnetwork.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    @Mappings({
            @Mapping(target = "id", source = "commentDTO.id"),
            @Mapping(target = "message", source = "commentDTO.message"),
            @Mapping(target = "userName", source = "commentDTO.username"),
            @Mapping(target = "userId", ignore = true),
            @Mapping(target = "post", ignore = true),
            @Mapping(target = "createdDate", ignore = true)
    })
    Comment toEntity(CommentDTO commentDTO);

    @Mappings({
            @Mapping(target = "id", source = "comment.id"),
            @Mapping(target = "message", source = "comment.message"),
            @Mapping(target = "username", source = "comment.userName")
    })
    CommentDTO toDto(Comment comment);
}
