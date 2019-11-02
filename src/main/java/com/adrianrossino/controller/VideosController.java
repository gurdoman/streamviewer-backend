package com.adrianrossino.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrianrossino.model.YouTubeVideo;
import com.adrianrossino.repository.YouTubeVideoRepository;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("videos")
public class VideosController {
	
	@Autowired
	YouTubeVideoRepository ytRepo;
	
	@GetMapping
	public List<YouTubeVideo> getYoutube(){
		try {
			List<YouTubeVideo> videos = ytRepo.findTop5ByOrderByPublishedAtDesc();
			if(videos != null) {
				return videos;
			}
		    return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
