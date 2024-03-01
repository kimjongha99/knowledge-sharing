package com.hanghae.knowledgesharing.hashtag.repository;

import com.hanghae.knowledgesharing.common.entity.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HashTagRepository extends JpaRepository<HashTag,Long> {

    Optional<HashTag> findByTagName(String tagName);

}
