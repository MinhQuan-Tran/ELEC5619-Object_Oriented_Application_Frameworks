
package com.example.api.repository;

import com.example.api.model.EmotionRecord;
import org.springframework.data.repository.CrudRepository;

public interface EmotionRecordRepository extends CrudRepository<EmotionRecord, Integer> {
    // Basic CRUD operations are provided by CrudRepository
    // Additional custom queries can be added here if needed
}
