
package com.example.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class EmotionRecord {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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
