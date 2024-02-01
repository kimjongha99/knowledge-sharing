package com.hanghae.knowledgesharing.repository;

import com.hanghae.knowledgesharing.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
  

}
