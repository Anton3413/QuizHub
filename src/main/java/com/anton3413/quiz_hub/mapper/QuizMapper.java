package com.anton3413.quiz_hub.mapper;

import com.anton3413.quiz_hub.dto.option.OptionResponse;
import com.anton3413.quiz_hub.dto.question.QuestionResponse;
import com.anton3413.quiz_hub.dto.quiz.CreateQuizRequest;
import com.anton3413.quiz_hub.dto.quiz.QuizDetailResponse;
import com.anton3413.quiz_hub.dto.quiz.QuizResponse;
import com.anton3413.quiz_hub.dto.quiz.QuizSummaryResponse;
import com.anton3413.quiz_hub.model.Option;
import com.anton3413.quiz_hub.model.Question;
import com.anton3413.quiz_hub.model.Quiz;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface QuizMapper {

    Quiz fromCreateQuizRequestToEntity(CreateQuizRequest createQuizRequest);

    @Mapping(source = "author.username", target = "username" )
    QuizResponse fromEntityToCreateQuizResponse(Quiz quiz);


    QuizSummaryResponse fromEntityToQuizSummaryResponse(Quiz quiz);

    @Mapping(target = "authorName", source = "author.username")
    QuizDetailResponse toDetailResponse(Quiz quiz);

    QuestionResponse toQuestionResponse(Question question);

    OptionResponse toOptionResponse(Option option);
}
