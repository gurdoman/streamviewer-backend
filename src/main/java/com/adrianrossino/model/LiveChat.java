package com.adrianrossino.model;

import java.util.List;

public class LiveChat {

	private LiveChatStatus status;
	private List<ChatMessage> messages;
	
	public LiveChat() {
		super();
	}
	public LiveChat(LiveChatStatus status, List<ChatMessage> messages) {
		super();
		this.status = status;
		this.messages = messages;
	}
	public LiveChatStatus getStatus() {
		return status;
	}
	public void setStatus(LiveChatStatus status) {
		this.status = status;
	}
	public List<ChatMessage> getMessages() {
		return messages;
	}
	public void setMessages(List<ChatMessage> messages) {
		this.messages = messages;
	}
}
