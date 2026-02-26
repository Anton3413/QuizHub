package com.anton3413.quiz_hub.repository;

import com.anton3413.quiz_hub.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    int countByQuiz_Id(UUID quizId);
}
