package com.adrianrossino.repository;

import org.springframework.data.repository.CrudRepository;

import com.adrianrossino.model.ChatMessage;

public interface ChatMessageRepository extends CrudRepository<ChatMessage, String>{

}
