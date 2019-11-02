package com.adrianrossino.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class YouTubeVideo {
	
	@Id
	private String id;
	private String channelId;
	private String title;
	private String description;
	private String publishedAt;
	private String thumbnailUrl;
	private String liveChatId;
	private String status;
	private String embedHtml;
	
	public YouTubeVideo() {
		super();
	}
	
	public YouTubeVideo(String id, String liveChatId, String status) {
		super();
		this.id = id;
		this.liveChatId = liveChatId;
		this.status = status;
	}

	public YouTubeVideo(String id, String channelId, String title, String description, String publishedAt,
			String thumbnailUrl, String liveChatId, String status, String embedHtml) {
		super();
		this.id = id;
		this.channelId = channelId;
		this.title = title;
		this.description = description;
		this.publishedAt = publishedAt;
		this.thumbnailUrl = thumbnailUrl;
		this.liveChatId = liveChatId;
		this.status = status;
		this.embedHtml = embedHtml;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(String publishedAt) {
		this.publishedAt = publishedAt;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getLiveChatId() {
		return liveChatId;
	}

	public void setLiveChatId(String liveChatId) {
		this.liveChatId = liveChatId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmbedHtml() {
		return embedHtml;
	}

	public void setEmbedHtml(String embedHtml) {
		this.embedHtml = embedHtml;
	}

}
