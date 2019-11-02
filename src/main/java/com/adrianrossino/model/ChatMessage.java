package com.adrianrossino.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.google.api.client.util.DateTime;

@Entity
public class ChatMessage {

	@Id
	private String id;
	private String liveChatId;
	private String publishedAt;
	private String messageText;
	private String displayName;
	private Boolean isChatOwner;
	private String profileImageUrl;
	private String channelId;
	
	
	public ChatMessage() {
		super();
	}

	public ChatMessage(String id, String liveChatId, String publishedAt, String messageText, String displayName,
			Boolean isChatOwner, String profileImageUrl, String channelId) {
		super();
		this.id = id;
		this.liveChatId = liveChatId;
		this.publishedAt = publishedAt;
		this.messageText = messageText;
		this.displayName = displayName;
		this.isChatOwner = isChatOwner;
		this.profileImageUrl = profileImageUrl;
		this.channelId = channelId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLiveChatId() {
		return liveChatId;
	}

	public void setLiveChatId(String liveChatId) {
		this.liveChatId = liveChatId;
	}

	public String getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(String publishedAt) {
		this.publishedAt = publishedAt;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Boolean getIsChatOwner() {
		return isChatOwner;
	}

	public void setIsChatOwner(Boolean isChatOwner) {
		this.isChatOwner = isChatOwner;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	
}
