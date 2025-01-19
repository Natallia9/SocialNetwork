package com.example.socialnetwork.service;

import com.example.socialnetwork.dto.CommentDTO;
import com.example.socialnetwork.entity.Comment;
import com.example.socialnetwork.entity.Post;
import com.example.socialnetwork.entity.User;
import com.example.socialnetwork.exceptions.PostNotFoundException;
import com.example.socialnetwork.repository.CommentRepository;
import com.example.socialnetwork.repository.PostRepository;
import com.example.socialnetwork.service.helper.UserHelperService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    public static final Logger LOG = LoggerFactory.getLogger(CommentService.class);

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    private final UserHelperService userHelperService;

    public Comment saveComment(Long postId, CommentDTO commentDTO, Principal principal){

        User user = userHelperService.getUserByPrincipal(principal);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post cannot be found for username " + user.getEmail()));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUserId(user.getId());
        comment.setUsername(user.getUsername());
        comment.setMessage(commentDTO.getMessage());

        LOG.info("Saving Comment for Post: {} ", post.getId());

        return commentRepository.save(comment);
    }

    public List<Comment> getAllCommentsForPost(Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post cannot be found "));

        List<Comment> comments = commentRepository.findAllByPost(post);

        return comments;

    }

    public void deleteComment(Long commentId){
        Optional<Comment> comment = commentRepository.findById(commentId);
        comment.ifPresent(commentRepository::delete);
    }
}
