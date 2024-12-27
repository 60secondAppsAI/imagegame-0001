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

import com.imagegame.domain.GameSettings;
import com.imagegame.dto.GameSettingsDTO;
import com.imagegame.dto.GameSettingsSearchDTO;
import com.imagegame.dto.GameSettingsPageDTO;
import com.imagegame.service.GameSettingsService;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/gameSettings")
@RestController
public class GameSettingsController {

	private final static Logger logger = LoggerFactory.getLogger(GameSettingsController.class);

	@Autowired
	GameSettingsService gameSettingsService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<GameSettings> getAll() {

		List<GameSettings> gameSettingss = gameSettingsService.findAll();
		
		return gameSettingss;	
	}

	@GetMapping(value = "/{gameSettingsId}")
	@ResponseBody
	public GameSettingsDTO getGameSettings(@PathVariable Integer gameSettingsId) {
		
		return (gameSettingsService.getGameSettingsDTOById(gameSettingsId));
	}

 	@RequestMapping(value = "/addGameSettings", method = RequestMethod.POST)
	public ResponseEntity<?> addGameSettings(@RequestBody GameSettingsDTO gameSettingsDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = gameSettingsService.addGameSettings(gameSettingsDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/gameSettingss")
	public ResponseEntity<GameSettingsPageDTO> getGameSettingss(GameSettingsSearchDTO gameSettingsSearchDTO) {
 
		return gameSettingsService.getGameSettingss(gameSettingsSearchDTO);
	}	

	@RequestMapping(value = "/updateGameSettings", method = RequestMethod.POST)
	public ResponseEntity<?> updateGameSettings(@RequestBody GameSettingsDTO gameSettingsDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = gameSettingsService.updateGameSettings(gameSettingsDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
