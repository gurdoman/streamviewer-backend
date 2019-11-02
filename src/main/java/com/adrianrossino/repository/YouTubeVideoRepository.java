package com.adrianrossino.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.adrianrossino.model.YouTubeVideo;

public interface YouTubeVideoRepository  extends CrudRepository<YouTubeVideo, String>{

	List<YouTubeVideo> findTop10ByOrderByPublishedAtDesc();
	
}
