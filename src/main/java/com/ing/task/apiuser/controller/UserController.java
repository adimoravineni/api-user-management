package com.ing.task.apiuser.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.fge.jsonpatch.JsonPatchException;
import com.ing.task.apiuser.dto.UserDTO;
import com.ing.task.apiuser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/user/management")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/v1/users/{Id}")
    public UserDTO getUser(
            @PathVariable("Id") String id) {

        Long userId;
        try{
            userId = Long.valueOf(id);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID must be numeric");
        }

        return userService.getUser(userId);
    }

    @PatchMapping("/v1/users/{Id}")
    public UserDTO patchUser(
            @PathVariable("Id") String id,
            @RequestBody String patch) throws JsonProcessingException, JsonPatchException{

        Long userId;
        try{
            userId = Long.valueOf(id);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID must be numeric");
        }

        try {
            ObjectNode patchNode = objectMapper.readValue(patch, ObjectNode.class);
            patchNode.remove("id");
            patch = objectMapper.writeValueAsString(patchNode);
        }catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please provide well formed JSON as request body");
        }

        return userService.patchUser(userId, patch);
    }
}
