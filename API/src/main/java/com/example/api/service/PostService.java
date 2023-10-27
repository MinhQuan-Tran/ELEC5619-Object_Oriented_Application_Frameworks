package com.example.api.service;

import com.example.api.dto.CommentDTO;
import com.example.api.dto.CommentUserDTO;
import com.example.api.dto.PostDTO;
import com.example.api.model.Comment;
import com.example.api.model.Post;
import com.example.api.model.User;
import com.example.api.repository.CommentRepository;
import com.example.api.repository.PostRepository;
import com.example.api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    @Autowired
    public PostService(PostRepository postRepository, CommentRepository commentRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    public boolean existsById(Integer postId) {
        return postRepository.existsById(postId);
    }

    public PostDTO createPost(Post post){
        Integer uid = post.getUid();
        User user = userRepository.findById(uid).orElse(null);
        post.setUser(user);
        postRepository.save(post);

        PostDTO postDTO = convertToPostDTO(post);
        return postDTO;
    }

    public boolean deletePost(Integer postId, Integer userId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isPresent() && post.get().getUid().equals(userId)) {
            postRepository.deleteById(postId);
            return true;
        }
        return false;
    }

    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::convertToPostDTO).collect(Collectors.toList());
    }

    public PostDTO getPostDetails(Integer pid) {
        Post post = postRepository.findById(pid).orElse(null);
        if(post == null){
            return null;
        }
        List<Comment> comments = commentRepository.findByPidAndParentCommentIdIsNullAndIfDeletedFalse(post.getPid());

        CommentUserDTO commentUserDTO = new CommentUserDTO();
        commentUserDTO.setUsername(post.getUser().getUsername());
        commentUserDTO.setProfileImageURL(post.getUser().getAvatarPath());
        commentUserDTO.setId(post.getUser().getUid());

        PostDTO dto = new PostDTO();
        dto.setId(post.getPid());
        dto.setUser(commentUserDTO);
        dto.setContent(post.getContent());
        dto.setComments(comments.stream().map(this::convertToCommentDTO).collect(Collectors.toList()));
        return dto;
    }

    private PostDTO convertToPostDTO(Post post) {
        if (post.getUser() == null) {
            throw new IllegalStateException("Post must have an associated User");
        }
        PostDTO postDTO = new PostDTO();
        postDTO.setDate(post.getPostDate());
        postDTO.setContent(post.getContent());
        postDTO.setId(post.getPid());

        CommentUserDTO commentUserDTO = new CommentUserDTO();
        commentUserDTO.setUsername(post.getUser().getUsername());
        commentUserDTO.setProfileImageURL(post.getUser().getAvatarPath());
        commentUserDTO.setId(post.getUser().getUid());

        postDTO.setUser(commentUserDTO);
        return postDTO;
    }


    private CommentDTO convertToCommentDTO(Comment comment) {
        User user = userRepository.findById(comment.getUid()).orElse(null);
        CommentUserDTO commentUserDTO = new CommentUserDTO();
        commentUserDTO.setUsername(user.getUsername());
        commentUserDTO.setProfileImageURL(user.getAvatarPath());
        commentUserDTO.setId(user.getUid());
        CommentDTO dto = new CommentDTO();

        dto.setId(comment.getCid());
        dto.setUser(commentUserDTO);
        dto.setContent(comment.getContext());
        dto.setDate(comment.getCommentDate());

        dto.setReplies(commentRepository.findByParentCommentIdAndIfDeletedFalse(comment.getCid())
                .stream().map(this::convertToCommentDTO).collect(Collectors.toList()));

        return dto;
    }





}
