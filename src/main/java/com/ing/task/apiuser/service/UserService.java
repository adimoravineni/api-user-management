package com.ing.task.apiuser.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatchException;
import com.ing.task.apiuser.dto.UserDTO;
import com.ing.task.apiuser.entity.User;
import com.ing.task.apiuser.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static com.ing.task.apiuser.util.ApplicationUtil.mergePatchJson;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, timeout = 5)
    public UserDTO getUser(Long id){

        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()){
            return modelMapper.map(user.get(), UserDTO.class);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User is not found");
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, timeout = 5)
    public UserDTO patchUser(Long id, String patch) throws JsonPatchException, JsonProcessingException {

        Optional<User> existingUser = userRepository.findById(id);

        if(existingUser.isPresent()){
            UserDTO existingUserDTO = modelMapper.map(existingUser.get(), UserDTO.class);
            UserDTO updatedUserDTO = mergePatchJson(existingUserDTO, patch, UserDTO.class);

            User updatedUser = userRepository.save(modelMapper.map(updatedUserDTO, User.class));

            return modelMapper.map(updatedUser, UserDTO.class);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User is not found");
        }
    }

}
