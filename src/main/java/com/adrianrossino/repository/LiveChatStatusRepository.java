package com.adrianrossino.repository;

import org.springframework.data.repository.CrudRepository;

import com.adrianrossino.model.LiveChatStatus;

public interface LiveChatStatusRepository extends CrudRepository<LiveChatStatus, String>{

}
