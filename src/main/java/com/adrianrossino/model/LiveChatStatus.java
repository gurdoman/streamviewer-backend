package com.adrianrossino.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LiveChatStatus {
	
	@Id
	private String chatId;
	private String nextPageToken;
	
	public LiveChatStatus() {
		super();
	}

	public LiveChatStatus(String chatId, String nextPageToken) {
		super();
		this.chatId = chatId;
		this.nextPageToken = nextPageToken;
	}

	public String getChatId() {
		return chatId;
	}

	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

	public String getNextPageToken() {
		return nextPageToken;
	}

	public void setNextPageToken(String nextPageToken) {
		this.nextPageToken = nextPageToken;
	}
}
