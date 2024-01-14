package com.gleb.vinnikov.venue.posting.api;

import com.gleb.vinnikov.venue.posting.services.PostingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(path = "post")
public class PostingController {

    private final PostingService postingService;
    private final ModelMapper modelMapper;


    @PostMapping(value = "/make-post",
    consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostingResponse> makePost(
            Principal principal,
            @RequestParam("postInfo") PostingRequest postInfo) {
        return ResponseEntity.ok(new PostingResponse(UUID.randomUUID()));
    }

//    @GetMapping(
//            value = "/{postId}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<PostDto> getPost(
//            Principal principal,
//            @PathVariable String postId) {
//        Optional<Post> post = postingService.getPost(UUID.fromString(postId));
//        return post.map(this::convertToDto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
//    }
//
//    private PostDto convertToDto(Post post) {
//        return modelMapper.map(post, PostDto.class);
//    }

}
