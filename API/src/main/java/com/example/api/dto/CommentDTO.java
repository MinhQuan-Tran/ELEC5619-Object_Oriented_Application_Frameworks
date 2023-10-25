package com.example.api.dto;

import java.util.Date;
import java.util.List;

public class CommentDTO {

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getComment_date() {
        return commentDate;
    }

    public void setComment_date(Date commentDate) {
        this.commentDate = commentDate;
    }

    public List<CommentDTO> getReplies() {
        return replies;
    }

    public void setReplies(List<CommentDTO> replies) {
        this.replies = replies;
    }

    private Integer cid;
    private Integer uid;
    private String content;
    private Date commentDate;
    private List<CommentDTO> replies;
}
