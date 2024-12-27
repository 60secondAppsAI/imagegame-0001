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

import com.imagegame.domain.Badge;
import com.imagegame.dto.BadgeDTO;
import com.imagegame.dto.BadgeSearchDTO;
import com.imagegame.dto.BadgePageDTO;
import com.imagegame.service.BadgeService;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/badge")
@RestController
public class BadgeController {

	private final static Logger logger = LoggerFactory.getLogger(BadgeController.class);

	@Autowired
	BadgeService badgeService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Badge> getAll() {

		List<Badge> badges = badgeService.findAll();
		
		return badges;	
	}

	@GetMapping(value = "/{badgeId}")
	@ResponseBody
	public BadgeDTO getBadge(@PathVariable Integer badgeId) {
		
		return (badgeService.getBadgeDTOById(badgeId));
	}

 	@RequestMapping(value = "/addBadge", method = RequestMethod.POST)
	public ResponseEntity<?> addBadge(@RequestBody BadgeDTO badgeDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = badgeService.addBadge(badgeDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/badges")
	public ResponseEntity<BadgePageDTO> getBadges(BadgeSearchDTO badgeSearchDTO) {
 
		return badgeService.getBadges(badgeSearchDTO);
	}	

	@RequestMapping(value = "/updateBadge", method = RequestMethod.POST)
	public ResponseEntity<?> updateBadge(@RequestBody BadgeDTO badgeDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = badgeService.updateBadge(badgeDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
