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

import com.imagegame.domain.UserBadge;
import com.imagegame.dto.UserBadgeDTO;
import com.imagegame.dto.UserBadgeSearchDTO;
import com.imagegame.dto.UserBadgePageDTO;
import com.imagegame.service.UserBadgeService;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/userBadge")
@RestController
public class UserBadgeController {

	private final static Logger logger = LoggerFactory.getLogger(UserBadgeController.class);

	@Autowired
	UserBadgeService userBadgeService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<UserBadge> getAll() {

		List<UserBadge> userBadges = userBadgeService.findAll();
		
		return userBadges;	
	}

	@GetMapping(value = "/{userBadgeId}")
	@ResponseBody
	public UserBadgeDTO getUserBadge(@PathVariable Integer userBadgeId) {
		
		return (userBadgeService.getUserBadgeDTOById(userBadgeId));
	}

 	@RequestMapping(value = "/addUserBadge", method = RequestMethod.POST)
	public ResponseEntity<?> addUserBadge(@RequestBody UserBadgeDTO userBadgeDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = userBadgeService.addUserBadge(userBadgeDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/userBadges")
	public ResponseEntity<UserBadgePageDTO> getUserBadges(UserBadgeSearchDTO userBadgeSearchDTO) {
 
		return userBadgeService.getUserBadges(userBadgeSearchDTO);
	}	

	@RequestMapping(value = "/updateUserBadge", method = RequestMethod.POST)
	public ResponseEntity<?> updateUserBadge(@RequestBody UserBadgeDTO userBadgeDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = userBadgeService.updateUserBadge(userBadgeDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
