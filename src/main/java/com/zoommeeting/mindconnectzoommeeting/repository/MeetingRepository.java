package com.zoommeeting.mindconnectzoommeeting.repository;

import org.springframework.stereotype.Repository;
import com.zoommeeting.mindconnectzoommeeting.entitites.Quiz1;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface MeetingRepository extends JpaRepository<Quiz1, Long>{

}
