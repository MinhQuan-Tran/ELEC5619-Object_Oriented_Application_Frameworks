package com.example.api.service;

import com.example.api.model.Comment;
import com.example.api.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddComment() {
        // Arrange
        Integer pid = 1, uid = 1, parentCommentId = null;
        String context = "Test comment";
        Comment comment = new Comment();
        comment.setUid(1);
        comment.setContext("Test comment");
        comment.setPid(pid);
        comment.setUid(uid);
        comment.setContext(context);
        comment.setParentCommentId(parentCommentId);
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        // Act
        Comment savedComment = commentService.createComment(pid, uid, context, parentCommentId);

        // Assert
        assertNotNull(savedComment, "Saved comment should not be null");
        assertEquals(comment.getContext(), savedComment.getContext(), "Comment content should match");
    }

    @Test
    void testSoftDeleteCommentCommentExistsAndUserMatches() {
        // Arrange
        Integer commentId = 1, userId = 1;
        Comment comment = new Comment();
        comment.setCid(commentId);
        comment.setUid(userId);
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        // Act
        boolean result = commentService.softDeleteComment(commentId, userId);

        // Assert
        assertTrue(result, "Comment should be soft deleted");
        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    void testSoftDeleteCommentCommentDoesNotExist() {
        // Arrange
        Integer commentId = 1, userId = 1;
        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        // Act
        boolean result = commentService.softDeleteComment(commentId, userId);

        // Assert
        assertFalse(result, "Comment should not be soft deleted if it does not exist");
        verify(commentRepository, never()).save(any(Comment.class));
    }

    @Test
    void testSoftDeleteCommentUserDoesNotMatch() {
        // Arrange
        Integer commentId = 1, userId = 1, anotherUserId = 2;
        Comment comment = new Comment();
        comment.setCid(commentId);
        comment.setUid(anotherUserId);
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        // Act
        boolean result = commentService.softDeleteComment(commentId, userId);

        // Assert
        assertFalse(result, "Comment should not be soft deleted if the user does not match");
        verify(commentRepository, never()).save(any(Comment.class));
    }

}
