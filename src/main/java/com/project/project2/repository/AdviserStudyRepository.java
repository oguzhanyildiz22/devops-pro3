package com.project.project2.repository;

import com.project.project2.model.AdviserStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdviserStudyRepository extends JpaRepository<AdviserStudy,Integer> {
}
