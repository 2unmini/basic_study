package com.example.study1.dto;

import com.example.study1.entity.User;
import lombok.Getter;

@Getter
public class ReadUserResponseDTO {
    private Long id;
    private String name;

    public ReadUserResponseDTO(Long id,String name) {
        this.id=id;
        this.name = name;
    }

    public static ReadUserResponseDTO toDto(User user) {
        return new ReadUserResponseDTO(user.getId(), user.getName());
    }
}
