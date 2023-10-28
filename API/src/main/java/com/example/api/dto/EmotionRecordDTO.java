
package com.example.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmotionRecordDTO {
    private Integer eid;
    private String emotion;
    private Date emotionRecordDate;
    private Integer uid;  // User ID

    // Getters and Setters for each attribute
    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public Date getEmotionRecordDate() {
        return emotionRecordDate;
    }

    public void setEmotionRecordDate(Date emotionRecordDate) {
        this.emotionRecordDate = emotionRecordDate;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
}
