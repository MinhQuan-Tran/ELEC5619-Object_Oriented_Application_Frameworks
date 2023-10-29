package com.example.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.api.repository.EmotionRecordRepository;
import com.example.api.model.EmotionRecord;
import com.example.api.dto.EmotionRecordDTO;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmotionRecordService {

    @Autowired
    private EmotionRecordRepository emotionRecordRepository;

    public EmotionRecordDTO addEmotionRecord(EmotionRecordDTO emotionRecordDTO) {
        EmotionRecord emotionRecord = convertToModel(emotionRecordDTO);
        emotionRecord = emotionRecordRepository.save(emotionRecord);
        return convertToDTO(emotionRecord);
    }

    public List<EmotionRecordDTO> getAllEmotionRecordsByUid(int uid) {
        List<EmotionRecord> records = emotionRecordRepository.findByUid(uid);
        return records.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public EmotionRecordDTO getEmotionRecordById(int eid) {
        Optional<EmotionRecord> recordOpt = emotionRecordRepository.findById(eid);
        return recordOpt.map(this::convertToDTO).orElse(null);
    }

    public void deleteEmotionRecord(int eid) {
        emotionRecordRepository.deleteById(eid);
    }

    public EmotionRecordDTO updateEmotionRecord(EmotionRecordDTO emotionRecordDTO) {
        if(emotionRecordRepository.existsById(emotionRecordDTO.getEid())) {
            EmotionRecord emotionRecord = convertToModel(emotionRecordDTO);
            emotionRecord = emotionRecordRepository.save(emotionRecord);
            return convertToDTO(emotionRecord);
        }
        return null;
    }

    private EmotionRecord convertToModel(EmotionRecordDTO dto) {
        EmotionRecord record = new EmotionRecord();
        record.setEid(dto.getEid());
        record.setEmotion(dto.getEmotion());
        record.setEmotionRecordDate(dto.getEmotionRecordDate());
        record.setUid(dto.getUid());
        return record;
    }

    private EmotionRecordDTO convertToDTO(EmotionRecord record) {
        EmotionRecordDTO dto = new EmotionRecordDTO();
        dto.setEid(record.getEid());
        dto.setEmotion(record.getEmotion());
        dto.setEmotionRecordDate(record.getEmotionRecordDate());
        dto.setUid(record.getUid());
        return dto;
    }

}