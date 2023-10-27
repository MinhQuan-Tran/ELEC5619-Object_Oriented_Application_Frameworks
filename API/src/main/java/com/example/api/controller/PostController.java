package com.example.api.controller;

import com.example.api.dto.PostDTO;
import com.example.api.dto.UserDTO;
import com.example.api.model.Post;
import com.example.api.model.Result;
import com.example.api.model.User;
import com.example.api.service.PostService;
import com.example.api.utils.JWTManager;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/api") // This means URL's start with /demo (after Application path)
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    private boolean checkToken(String token){
        if (token == null || token.isEmpty() || !token.contains("Bearer ")) {
            return false;
        }
        return true;
    }
    @PostMapping(value = "/posts", consumes = { "multipart/form-data" })
    public ResponseEntity<Result> submitPost(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@Valid Post post) {
        Result result = new Result();

        if (!checkToken(token)) {
            result.setResultFailed(4);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }

        PostDTO createdPost = postService.createPost(post);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("post",createdPost);
        result.setResultSuccess(0, resultMap);

        return new ResponseEntity<>(result, HttpStatus.OK);

    }


    @DeleteMapping("/posts/{pid}")
    public ResponseEntity<Result> deletePost(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                           @PathVariable Integer pid) {


        token = token.replace("Bearer ", "");
        UserDTO userDTO = JWTManager.getDataFromToken(token, "user", UserDTO.class);
        Result result = new Result();
        if (userDTO == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        boolean isDeleted = postService.deletePost(pid, userDTO.getUid());
        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        result.setResultSuccess(0);
        return new ResponseEntity<>(result,HttpStatus.NO_CONTENT);
    }


    @GetMapping("/posts")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/posts/{pid}")
    public ResponseEntity<PostDTO> getPostDetails(@PathVariable Integer pid) {
        PostDTO postDetails = postService.getPostDetails(pid);
        if (postDetails == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(postDetails, HttpStatus.OK);
    }
}
