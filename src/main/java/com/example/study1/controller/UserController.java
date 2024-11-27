package com.example.study1.controller;

import com.example.study1.dto.ReadUserResponseDTO;
import com.example.study1.dto.UpdateUserResponseDTO;
import com.example.study1.dto.UserDTO;
import com.example.study1.entity.User;
import com.example.study1.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/session")
    public String test(HttpServletRequest request) {
        HttpSession returnString = request.getSession(false);
        if(request.getSession(false)==null){
            return "세션이 없습니다";
        }
        String sessionUuid = returnString.getAttribute("SESSION_KEY").toString();
        return "유저 " + userService.checkUserBySession(sessionUuid).getName() + "이 접속했습니다";

    }
    @PostMapping("/session")
    public String createSession(@RequestBody UserDTO userDto,HttpServletRequest request)  {
        HttpSession session = request.getSession(true);
        String uuid = userService.login(userDto);
        session.setAttribute("SESSION_KEY",uuid);
        return "세션이 생성되었습니다.";
    }

    @PostMapping
    public User createUser(@RequestBody UserDTO userDto) {
        return userService.createUser(userDto);
    }

    @GetMapping
    public ResponseEntity<List<ReadUserResponseDTO>> findUser() {
        List<ReadUserResponseDTO> readUserResponseDTOList = userService.findUser();
        return new ResponseEntity<>(readUserResponseDTOList, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UpdateUserResponseDTO> updateUser(@RequestBody UserDTO updateUserDto,HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String uuid = session.getAttribute("SESSION_KEY").toString();
        UpdateUserResponseDTO updateUserResponseDTO = userService.updateUser(updateUserDto, uuid);
        return new ResponseEntity<>(updateUserResponseDTO,HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String uuid = session.getAttribute("SESSION_KEY").toString();
        userService.deleteUser(uuid);
        session.invalidate();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
