package com.anton3413.quiz_hub.repository;

import com.anton3413.quiz_hub.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, UUID> {

    List<Quiz> findAllByAuthor_Username(String authorUsername);

}
