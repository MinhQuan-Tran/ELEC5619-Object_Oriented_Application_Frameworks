package com.example.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.api.service.EmotionRecordService;
import com.example.api.dto.EmotionRecordDTO;
import java.util.List;

@RestController
@RequestMapping("/api/emotion")
public class EmotionRecordController {

    @Autowired
    private EmotionRecordService emotionRecordService;

    @PostMapping("/add")
    public EmotionRecordDTO addEmotionRecord(@RequestBody EmotionRecordDTO emotionRecordDTO) {
        return emotionRecordService.addEmotionRecord(emotionRecordDTO);
    }

    @GetMapping("/getAllByUid/{uid}")
    public List<EmotionRecordDTO> getAllEmotionRecordsByUid(@PathVariable int uid) {
        return emotionRecordService.getAllEmotionRecordsByUid(uid);
    }

    @GetMapping("/getById/{eid}")
    public EmotionRecordDTO getEmotionRecordById(@PathVariable int eid) {
        return emotionRecordService.getEmotionRecordById(eid);
    }

    @DeleteMapping("/delete/{eid}")
    public void deleteEmotionRecord(@PathVariable int eid) {
        emotionRecordService.deleteEmotionRecord(eid);
    }

    @PutMapping("/update")
    public EmotionRecordDTO updateEmotionRecord(@RequestBody EmotionRecordDTO emotionRecordDTO) {
        return emotionRecordService.updateEmotionRecord(emotionRecordDTO);
    }

}
