
package com.example.api.controller;

import com.example.api.dto.EmotionRecordDTO;
import com.example.api.model.EmotionRecord;
import com.example.api.service.EmotionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/emotion")
public class EmotionRecordController {

    @Autowired
    private EmotionRecordService emotionRecordService;

    // Convert EmotionRecord to EmotionRecordDTO
    private EmotionRecordDTO convertToDto(EmotionRecord emotionRecord) {
        EmotionRecordDTO emotionRecordDTO = new EmotionRecordDTO();
        emotionRecordDTO.setEid(emotionRecord.getEid());
        emotionRecordDTO.setEmotion(emotionRecord.getEmotion());
        emotionRecordDTO.setEmotionRecordDate(emotionRecord.getEmotionRecordDate());
        emotionRecordDTO.setUid(emotionRecord.getUid());
        return emotionRecordDTO;
    }

    // Add a new emotion record
    @PostMapping
    public EmotionRecordDTO addEmotionRecord(@RequestBody EmotionRecordDTO emotionRecordDTO) {
        EmotionRecord emotionRecord = new EmotionRecord();
        emotionRecord.setEmotion(emotionRecordDTO.getEmotion());
        emotionRecord.setEmotionRecordDate(emotionRecordDTO.getEmotionRecordDate());
        emotionRecord.setUid(emotionRecordDTO.getUid());
        return convertToDto(emotionRecordService.addEmotionRecord(emotionRecord));
    }

    // Get an emotion record by ID
    @GetMapping("/{id}")
    public EmotionRecordDTO getEmotionRecordById(@PathVariable Integer id) {
        return emotionRecordService.getEmotionRecordById(id).map(this::convertToDto).orElse(null);
    }

    // Update an emotion record
    @PutMapping("/{id}")
    public EmotionRecordDTO updateEmotionRecord(@PathVariable Integer id, @RequestBody EmotionRecordDTO emotionRecordDTO) {
        EmotionRecord updatedEmotionRecord = new EmotionRecord();
        updatedEmotionRecord.setEmotion(emotionRecordDTO.getEmotion());
        updatedEmotionRecord.setEmotionRecordDate(emotionRecordDTO.getEmotionRecordDate());
        updatedEmotionRecord.setUid(emotionRecordDTO.getUid());
        return convertToDto(emotionRecordService.updateEmotionRecord(id, updatedEmotionRecord));
    }

    // Delete an emotion record
    @DeleteMapping("/{id}")
    public void deleteEmotionRecord(@PathVariable Integer id) {
        emotionRecordService.deleteEmotionRecord(id);
    }
}
