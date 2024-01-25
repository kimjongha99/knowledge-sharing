package com.hanghae.knowledgesharing.repository;


import com.hanghae.knowledgesharing.entity.Certification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, String> {

}
