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

import com.imagegame.domain.GameSession;
import com.imagegame.dto.GameSessionDTO;
import com.imagegame.dto.GameSessionSearchDTO;
import com.imagegame.dto.GameSessionPageDTO;
import com.imagegame.service.GameSessionService;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/gameSession")
@RestController
public class GameSessionController {

	private final static Logger logger = LoggerFactory.getLogger(GameSessionController.class);

	@Autowired
	GameSessionService gameSessionService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<GameSession> getAll() {

		List<GameSession> gameSessions = gameSessionService.findAll();
		
		return gameSessions;	
	}

	@GetMapping(value = "/{gameSessionId}")
	@ResponseBody
	public GameSessionDTO getGameSession(@PathVariable Integer gameSessionId) {
		
		return (gameSessionService.getGameSessionDTOById(gameSessionId));
	}

 	@RequestMapping(value = "/addGameSession", method = RequestMethod.POST)
	public ResponseEntity<?> addGameSession(@RequestBody GameSessionDTO gameSessionDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = gameSessionService.addGameSession(gameSessionDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/gameSessions")
	public ResponseEntity<GameSessionPageDTO> getGameSessions(GameSessionSearchDTO gameSessionSearchDTO) {
 
		return gameSessionService.getGameSessions(gameSessionSearchDTO);
	}	

	@RequestMapping(value = "/updateGameSession", method = RequestMethod.POST)
	public ResponseEntity<?> updateGameSession(@RequestBody GameSessionDTO gameSessionDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = gameSessionService.updateGameSession(gameSessionDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
