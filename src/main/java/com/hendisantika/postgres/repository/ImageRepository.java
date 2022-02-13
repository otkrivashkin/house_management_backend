package com.hendisantika.postgres.repository;

import com.hendisantika.postgres.entity.Image;
import com.hendisantika.postgres.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByIdIn(List<Long> imageIds);
}