package com.project.project2.repository;

import com.project.project2.model.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyRepository extends JpaRepository<Study,Integer> {
    @Query("SELECT a FROM Study AS a ORDER BY a.id ASC")
    Iterable<Study> findAllAscById();
}
