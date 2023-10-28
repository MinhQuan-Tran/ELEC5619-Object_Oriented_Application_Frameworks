package com.example.api.service;

import com.example.api.dto.EmotionRecordDTO;
import com.example.api.model.EmotionRecord;
import com.example.api.repository.EmotionRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmotionRecordService {

    @Autowired
    private EmotionRecordRepository emotionRecordRepository;

    // Convert EmotionRecord to EmotionRecordDTO
    public EmotionRecordDTO convertToDto(EmotionRecord emotionRecord) {
        EmotionRecordDTO emotionRecordDTO = new EmotionRecordDTO();
        emotionRecordDTO.setEid(emotionRecord.getEid());
        emotionRecordDTO.setEmotion(emotionRecord.getEmotion());
        emotionRecordDTO.setEmotionRecordDate(emotionRecord.getEmotionRecordDate());
        emotionRecordDTO.setUid(emotionRecord.getUid());
        return emotionRecordDTO;
    }

    // Add or update an emotion record
    public EmotionRecordDTO addOrUpdateEmotionRecord(EmotionRecord emotionRecord) {
        EmotionRecord savedRecord = emotionRecordRepository.save(emotionRecord);
        return convertToDto(savedRecord);
    }

    // Get all emotion records by user ID
    public List<EmotionRecordDTO> getAllEmotionRecordsByUserId(Integer uid) {
        return emotionRecordRepository.findByUid(uid).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Get an emotion record by its ID
    public EmotionRecordDTO getEmotionRecordById(Integer eid) {
        return emotionRecordRepository.findById(eid).map(this::convertToDto).orElse(null);
    }

    // Delete an emotion record by its ID
    public void deleteEmotionRecordById(Integer eid) {
        emotionRecordRepository.deleteById(eid);
    }

    // Delete all emotion records by user ID
    public void deleteAllEmotionRecordsByUserId(Integer uid) {
        emotionRecordRepository.deleteByUid(uid);
    }
}