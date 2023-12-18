//package com.gleb.vinnikov.social_network.files.services;
//
//import com.gleb.vinnikov.social_network.auth.services.UserDetailsServiceImpl;
//import com.gleb.vinnikov.social_network.db.entities.Post;
//import com.gleb.vinnikov.social_network.posting.api.PostingRequest;
//import com.gleb.vinnikov.social_network.db.repos.PostRepo;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//import java.util.UUID;
//
//import static com.gleb.vinnikov.social_network.utils.ExceptionUtils.buildIllegalArgumentException;
//
//@Service
//@RequiredArgsConstructor
//public class PostingService {
//
//    private final FileManagingService fileManagingService;
//    private final UserDetailsServiceImpl userService;
//    private final PostRepo postRepo;
//    @Value("${application.posing.header.max-length}")
//    private int headerMaxLength;
//
////    public Post postIn(String username, PostingRequest request) {
////        validate(request);
////        Post.PostBuilder builder = Post.builder()
////                .postText(request.getPostText())
////                .header(request.getHeader())
////                .author(userService.loadUserByUsername(username));
////        if (request.getImage() != null) {
////            Image saved = imageSavingService.saveImage(request.getImage());
////            builder.image(saved);
////        }
////        return postRepo.save(builder.build());
////    }
//
//    public Optional<Post> getPost(UUID postId) {
//        return postRepo.findById(postId);
//    }
//
//    private void validate(PostingRequest request) {
//        if (request.getHeader() == null && request.getImage() == null && request.getPostText() == null) {
//            throw buildIllegalArgumentException("posting.no.fields.provided");
//        }
//        if (request.getHeader() != null) {
//            validateHeader(request.getHeader());
//        }
//    }
//
//    private void validateHeader(String header) {
//        if (header.length() > headerMaxLength) {
//            throw buildIllegalArgumentException("posting.header.is.too.long");
//        }
//    }
//
//}
