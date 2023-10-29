package com.example.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.api.model.EmotionRecord;
import java.util.List;

@Repository
public interface EmotionRecordRepository extends JpaRepository<EmotionRecord, Integer> {

    List<EmotionRecord> findByUid(int uid);

    EmotionRecord findByEidAndUid(int eid, int uid);

}
