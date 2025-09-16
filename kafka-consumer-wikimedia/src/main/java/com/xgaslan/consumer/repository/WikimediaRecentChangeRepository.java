package com.xgaslan.consumer.repository;

import com.xgaslan.consumer.entity.WikimediaRecentChange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WikimediaRecentChangeRepository extends JpaRepository<WikimediaRecentChange, Long> {
}
