package com.hendisantika.postgres.repository;

import com.hendisantika.postgres.entity.HouseFeature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HouseFeatureRepository extends JpaRepository<HouseFeature, Long> {
    List<HouseFeature> findByIdIn(List<Long> featureIds);
}