package com.example.socialnetwork.service;

import com.example.socialnetwork.entity.ImageModel;
import com.example.socialnetwork.entity.Post;
import com.example.socialnetwork.entity.User;
import com.example.socialnetwork.exceptions.ImageNotFoundException;
import com.example.socialnetwork.repository.ImageRepository;
import com.example.socialnetwork.service.helper.ByteCompressionHelper;
import com.example.socialnetwork.service.helper.UserHelperService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageService {

    public static final Logger LOG = LoggerFactory.getLogger(ImageService.class);

    private final ImageRepository imageRepository;

    private final UserHelperService userHelperService;

    private final ByteCompressionHelper byteCompressionHelper;

    public ImageModel uploadImageToUser(MultipartFile multipartFile, Principal principal) throws IOException{
        User user = userHelperService.getUserByPrincipal(principal);

        LOG.info("Uploading image profile to User {} ", user.getUsername());

        ImageModel userProfileImage = imageRepository.findByUserId(user.getId()).orElse(null);
        if(!ObjectUtils.isEmpty(userProfileImage)){
            imageRepository.delete(userProfileImage);
        }
        ImageModel imageModel = new ImageModel();
        imageModel.setUserId(user.getId());
        imageModel.setImageBytes(byteCompressionHelper.compressBytes(multipartFile.getBytes()));
        imageModel.setName(multipartFile.getOriginalFilename());

        return imageRepository.save(imageModel);
    }
    public ImageModel uploadImageToPost(MultipartFile multipartFile, Principal principal, Long postId) throws IOException{
        User user = userHelperService.getUserByPrincipal(principal);
        Post post = user.getPosts()
                .stream()
                .filter(p -> p.getId().equals(postId))
                .collect(toSinglePostCollector());

        ImageModel imageModel = new ImageModel();
        imageModel.setPostId(post.getId());
        imageModel.setImageBytes(multipartFile.getBytes());
        imageModel.setImageBytes(byteCompressionHelper.compressBytes(multipartFile.getBytes()));
        imageModel.setName(multipartFile.getOriginalFilename());

        LOG.info("Uploading image to post {} ", post.getId());

        return imageRepository.save(imageModel);
    }

    public ImageModel getImageToUser(Principal principal){
        User user = userHelperService.getUserByPrincipal(principal);
        return imageRepository.findByUserId(user.getId())
                .map(image -> {
                    image.setImageBytes(byteCompressionHelper.decompressBytes(image.getImageBytes()));
                    return image;
                })
                .orElse(null);
    }

    public ImageModel getImageToPost(Long postId){
        ImageModel imageModel = imageRepository.findByPostId(postId)
                .orElseThrow(() -> new ImageNotFoundException("Cannot found image to Post: " + postId));
        if(!ObjectUtils.isEmpty(imageModel)){
            imageModel.setImageBytes(byteCompressionHelper.decompressBytes(imageModel.getImageBytes()));
        }
        return imageModel;
    }

    private <T>Collector<T, ?, T> toSinglePostCollector(){
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() != 1) {
                        throw new IllegalStateException("Expected one element but found: " + list.size());
                    }
                    return list.get(0);
                }
        );
    }
}
