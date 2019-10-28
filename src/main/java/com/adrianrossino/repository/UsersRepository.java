package com.adrianrossino.repository;

import org.springframework.data.repository.CrudRepository;

import com.adrianrossino.model.Users;

public interface UsersRepository  extends CrudRepository<Users, String>{

}
