package com.example.socialnetwork.service;

import com.example.socialnetwork.dto.PostDTO;
import com.example.socialnetwork.entity.ImageModel;
import com.example.socialnetwork.entity.Post;
import com.example.socialnetwork.entity.User;
import com.example.socialnetwork.repository.ImageRepository;
import com.example.socialnetwork.repository.PostRepository;
import com.example.socialnetwork.repository.UserRepository;
import com.example.socialnetwork.exceptions.PostNotFoundException;
import com.example.socialnetwork.service.helper.UserHelperService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    public static final Logger LOG = LoggerFactory.getLogger(PostService.class);

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    private final UserHelperService userHelperService;

    public Post createPost(PostDTO postDTO, Principal principal) {

        User user = userHelperService.getUserByPrincipal(principal);
        Post post = new Post();
        post.setUser(user);
        post.setCaption(postDTO.getCaption());
        post.setLocation(postDTO.getLocation());
        post.setTitle(postDTO.getTitle());
        post.setLikes(0);

        LOG.info("Saving Post for User: {} ", user.getEmail());

        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByCreatedDateDesc();
    }

    public Post getPostById(Long postId, Principal principal) {
        User user = userHelperService.getUserByPrincipal(principal);
        return postRepository.findPostByIdAndUser(postId, user)
                .orElseThrow(() -> new PostNotFoundException("Post cannot be found by username " + user.getEmail()));
    }

    public List<Post> getAllPostForUser(Principal principal) {
        User user = userHelperService.getUserByPrincipal(principal);
        return postRepository.findAllByUserOrderByCreatedDateDesc(user);
    }

    public Post likePost(Long postId, String username) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post cannot be found"));

        Optional<User> userLiked = post.getLikedUsers()
                .stream()
                .filter(u -> u.getUsername().equals(username)).findAny();

        if (userLiked.isPresent()) {
            post.setLikes(post.getLikes() - 1);
            post.getLikedUsers().remove(userLiked.get());
        } else {
            User user = userRepository.findUserByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username " + username));
            post.setLikes(post.getLikes() + 1);
            post.getLikedUsers().add(user);
        }
        return postRepository.save(post);
    }

    public void deletePost(Long postId, Principal principal){
        Post post = getPostById(postId, principal);
        Optional<ImageModel> imageModel = imageRepository.findByPostId(post.getId());
        postRepository.delete(post);
        imageModel.ifPresent(imageRepository::delete);

    }
}
