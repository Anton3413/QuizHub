package com.anton3413.quiz_hub.mapper;

import com.anton3413.quiz_hub.dto.quiz.CreateQuizRequest;
import com.anton3413.quiz_hub.dto.quiz.CreateQuizResponse;
import com.anton3413.quiz_hub.model.Quiz;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface QuizMapper {

    Quiz fromCreateQuizRequestToEntity(CreateQuizRequest createQuizRequest);

    @Mapping(source = "author.username", target = "username" )
    CreateQuizResponse fromEntityToCreateQuizResponse(Quiz quiz);
}
