package com.example.api.controller;

import com.example.api.dto.EmotionRecordDTO;
import com.example.api.model.EmotionRecord;
import com.example.api.service.EmotionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/emotion")
public class EmotionRecordController {

    @Autowired
    private EmotionRecordService emotionRecordService;

    // Create or Update an EmotionRecord
    @PostMapping
    public EmotionRecordDTO saveOrUpdateEmotionRecord(@RequestBody EmotionRecord emotionRecord) {
        return emotionRecordService.addOrUpdateEmotionRecord(emotionRecord);
    }

    // Get all EmotionRecords by user ID
    @GetMapping("/user/{uid}")
    public List<EmotionRecordDTO> getAllEmotionRecordsByUserId(@PathVariable Integer uid) {
        return emotionRecordService.getAllEmotionRecordsByUserId(uid);
    }

    // Get an EmotionRecord by its ID
    @GetMapping("/{eid}")
    public EmotionRecordDTO getEmotionRecordById(@PathVariable Integer eid) {
        return emotionRecordService.getEmotionRecordById(eid);
    }

    // Delete an EmotionRecord by its ID
    @DeleteMapping("/{eid}")
    public void deleteEmotionRecordById(@PathVariable Integer eid) {
        emotionRecordService.deleteEmotionRecordById(eid);
    }

    // Delete all EmotionRecords by user ID
    @DeleteMapping("/user/{uid}")
    public void deleteAllEmotionRecordsByUserId(@PathVariable Integer uid) {
        emotionRecordService.deleteAllEmotionRecordsByUserId(uid);
    }
}