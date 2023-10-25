package com.example.api.dto;

import com.example.api.model.Comment;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDTO {
    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date date) {
        this.postDate = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    private Integer pid;
    private Date postDate;
    private String content;
    private UserDTO user;
    private List<CommentDTO> comments;

}
