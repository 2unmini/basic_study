package com.example.study1.dto;

import com.example.study1.entity.User;
import lombok.Getter;

@Getter
public class UpdateUserResponseDTO {
    private Long id;
    private String name;

    public UpdateUserResponseDTO(User user) {
        this.id= user.getId();
        this.name= user.getName();
    }
}
