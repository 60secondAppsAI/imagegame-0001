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

import com.imagegame.domain.UserPreferences;
import com.imagegame.dto.UserPreferencesDTO;
import com.imagegame.dto.UserPreferencesSearchDTO;
import com.imagegame.dto.UserPreferencesPageDTO;
import com.imagegame.service.UserPreferencesService;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/userPreferences")
@RestController
public class UserPreferencesController {

	private final static Logger logger = LoggerFactory.getLogger(UserPreferencesController.class);

	@Autowired
	UserPreferencesService userPreferencesService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<UserPreferences> getAll() {

		List<UserPreferences> userPreferencess = userPreferencesService.findAll();
		
		return userPreferencess;	
	}

	@GetMapping(value = "/{userPreferencesId}")
	@ResponseBody
	public UserPreferencesDTO getUserPreferences(@PathVariable Integer userPreferencesId) {
		
		return (userPreferencesService.getUserPreferencesDTOById(userPreferencesId));
	}

 	@RequestMapping(value = "/addUserPreferences", method = RequestMethod.POST)
	public ResponseEntity<?> addUserPreferences(@RequestBody UserPreferencesDTO userPreferencesDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = userPreferencesService.addUserPreferences(userPreferencesDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/userPreferencess")
	public ResponseEntity<UserPreferencesPageDTO> getUserPreferencess(UserPreferencesSearchDTO userPreferencesSearchDTO) {
 
		return userPreferencesService.getUserPreferencess(userPreferencesSearchDTO);
	}	

	@RequestMapping(value = "/updateUserPreferences", method = RequestMethod.POST)
	public ResponseEntity<?> updateUserPreferences(@RequestBody UserPreferencesDTO userPreferencesDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = userPreferencesService.updateUserPreferences(userPreferencesDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
