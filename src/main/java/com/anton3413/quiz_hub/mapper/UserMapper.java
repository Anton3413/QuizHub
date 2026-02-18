package com.anton3413.quiz_hub.mapper;

import com.anton3413.quiz_hub.dto.auth.CreateUserRequest;
import com.anton3413.quiz_hub.dto.auth.CreateUserResponse;
import com.anton3413.quiz_hub.model.User;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    // @Mapping(target = "attempts", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "authoredQuizzes", ignore = true)
    @Mapping(target = "verificationToken", ignore = true)
    @Mapping(target = "activated", constant = "false")
    User fromCreateUserRequestToEntity(CreateUserRequest createUserRequest);


    @Mapping(source = "id", target = "userId")
    CreateUserResponse fromEntityToCreateUserResponse(User user);
}
