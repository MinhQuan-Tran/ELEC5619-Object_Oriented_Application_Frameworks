
package com.example.api.service;

import com.example.api.dto.EmotionRecordDTO;
import com.example.api.model.EmotionRecord;
import com.example.api.repository.EmotionRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    // Basic CRUD methods
    public EmotionRecordDTO saveEmotionRecord(EmotionRecord emotionRecord) {
        EmotionRecord savedRecord = emotionRecordRepository.save(emotionRecord);
        return convertToDto(savedRecord);
    }

    public Optional<EmotionRecordDTO> getEmotionRecordById(Integer id) {
        Optional<EmotionRecord> record = emotionRecordRepository.findById(id);
        return record.map(this::convertToDto);
    }

    // Additional methods can be added as needed

}
