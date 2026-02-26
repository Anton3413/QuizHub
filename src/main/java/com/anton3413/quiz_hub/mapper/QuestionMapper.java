package com.anton3413.quiz_hub.mapper;

import com.anton3413.quiz_hub.dto.option.OptionRequest;
import com.anton3413.quiz_hub.dto.question.CreateQuestionRequest;
import com.anton3413.quiz_hub.model.Option;
import com.anton3413.quiz_hub.model.Question;
import com.anton3413.quiz_hub.model.Quiz;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    default List<Question> toEntities(List<CreateQuestionRequest> requests, Quiz quiz) {
        return requests.stream()
                .map(req -> {
                    Question question = toEntity(req);
                    question.setQuiz(quiz);
                    return question;
                })
                .toList();
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "quiz", ignore = true)
    Question toEntity(CreateQuestionRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "question", ignore = true)
    Option toOptionEntity(OptionRequest request);

    @AfterMapping
    default void linkOptions(@MappingTarget Question question) {
        if (question.getOptions() != null) {
            question.getOptions().forEach(option -> option.setQuestion(question));
        }
    }
}