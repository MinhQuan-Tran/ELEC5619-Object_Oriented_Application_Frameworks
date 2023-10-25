package com.example.api.service;

import com.example.api.dto.CommentDTO;
import com.example.api.dto.PostDTO;
import com.example.api.dto.UserDTO;
import com.example.api.model.Comment;
import com.example.api.model.Post;
import com.example.api.model.User;
import com.example.api.repository.CommentRepository;
import com.example.api.repository.PostRepository;
import com.example.api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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
        Post post = postRepository.findById(pid).orElseThrow(() -> new EntityNotFoundException("Post not found"));
        List<Comment> comments = commentRepository.findByPidAndParentCommentIdIsNullAndIfDeletedFalse(post.getPid());

        PostDTO dto = new PostDTO();
        dto.setPid(post.getPid());
//        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setComments(comments.stream().map(this::convertToCommentDTO).collect(Collectors.toList()));
        return dto;
    }

    private PostDTO convertToPostDTO(Post post) {
        if (post.getUser() == null) {
            throw new IllegalStateException("Post must have an associated User");
        }
        PostDTO postDTO = new PostDTO();
        postDTO.setPostDate(post.getPostDate());
        postDTO.setContent(post.getContent());
        postDTO.setPid(post.getPid());

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(post.getUser().getUsername());
        userDTO.setAvatarPath(post.getUser().getAvatarPath());
        userDTO.setUid(post.getUser().getUid());

        postDTO.setUser(userDTO);
        return postDTO;
    }


    private CommentDTO convertToCommentDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setCid(comment.getCid());
        dto.setUid(comment.getUid());
        dto.setContent(comment.getContext());
        dto.setComment_date(comment.getCommentDate());

        dto.setReplies(commentRepository.findByParentCommentIdAndIfDeletedFalse(comment.getCid())
                .stream().map(this::convertToCommentDTO).collect(Collectors.toList()));

        return dto;
    }





}
