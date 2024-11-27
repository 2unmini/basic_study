package com.example.study1.entity;

import com.example.study1.dto.UpdateUserResponseDTO;
import com.example.study1.dto.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column
    private String name;
    @Column
    private String password;
    @Column
    private String uuid;
    public User(UserDTO userDto) {
        this.name=userDto.getName();
        this.password=userDto.getPassword();
    }

    public void createUUID(){
        this.uuid= UUID.randomUUID().toString();
    }
    public void updateUserInfo(UserDTO updateUserResponseDTO){
        this.name=updateUserResponseDTO.getName();
        this.password=updateUserResponseDTO.getPassword();
    }
}
