package com.example.api.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.api.dto.PostDTO;
import com.example.api.model.Post;
import com.example.api.model.User;
import com.example.api.repository.CommentRepository;
import com.example.api.repository.PostRepository;
import com.example.api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPostDetailsPostExists() {
        // Arrange
        Integer postId = 1;
        Post post = new Post();
        post.setPid(postId);
        post.setContent("Test Content");
        User user = new User();
        user.setUid(1);
        post.setUser(user);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(commentRepository.findByPidAndParentCommentIdIsNullAndIfDeletedFalse(postId)).thenReturn(List.of());

        // Act
        PostDTO result = postService.getPostDetails(postId);

        // Assert
        assertNotNull(result, "Post details should not be null");
        assertEquals(post.getContent(), result.getContent(), "Post content should match");
    }

    @Test
    public void testGetPostDetailsPostDoesNotExist() {
        // Arrange
        Integer postId = 1;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // Act
        PostDTO result = postService.getPostDetails(postId);

        // Assert
        assertNull(result, "Post details should be null for non-existent post");
    }

    // Other tests can be added here
}
