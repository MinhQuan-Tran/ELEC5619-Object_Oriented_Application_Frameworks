package com.example.api.repository;

import com.example.api.model.EmotionRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmotionRecordRepository extends CrudRepository<EmotionRecord, Integer> {

    // Find all emotion records by a specific user
    List<EmotionRecord> findByUid(Integer uid);

    // Delete all emotion records by a specific user
    void deleteByUid(Integer uid);

    // Additional custom queries can be added as needed
}