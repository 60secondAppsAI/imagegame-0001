package com.imagegame.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;


import com.imagegame.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.imagegame.domain.Achievement;
import com.imagegame.dto.AchievementDTO;
import com.imagegame.dto.AchievementSearchDTO;
import com.imagegame.dto.AchievementPageDTO;
import com.imagegame.service.AchievementService;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/achievement")
@RestController
public class AchievementController {

	private final static Logger logger = LoggerFactory.getLogger(AchievementController.class);

	@Autowired
	AchievementService achievementService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Achievement> getAll() {

		List<Achievement> achievements = achievementService.findAll();
		
		return achievements;	
	}

	@GetMapping(value = "/{achievementId}")
	@ResponseBody
	public AchievementDTO getAchievement(@PathVariable Integer achievementId) {
		
		return (achievementService.getAchievementDTOById(achievementId));
	}

 	@RequestMapping(value = "/addAchievement", method = RequestMethod.POST)
	public ResponseEntity<?> addAchievement(@RequestBody AchievementDTO achievementDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = achievementService.addAchievement(achievementDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/achievements")
	public ResponseEntity<AchievementPageDTO> getAchievements(AchievementSearchDTO achievementSearchDTO) {
 
		return achievementService.getAchievements(achievementSearchDTO);
	}	

	@RequestMapping(value = "/updateAchievement", method = RequestMethod.POST)
	public ResponseEntity<?> updateAchievement(@RequestBody AchievementDTO achievementDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = achievementService.updateAchievement(achievementDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
