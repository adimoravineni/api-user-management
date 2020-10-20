package com.ing.task.apiuser.repository;

import com.ing.task.apiuser.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
