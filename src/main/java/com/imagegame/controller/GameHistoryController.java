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

import com.imagegame.domain.GameHistory;
import com.imagegame.dto.GameHistoryDTO;
import com.imagegame.dto.GameHistorySearchDTO;
import com.imagegame.dto.GameHistoryPageDTO;
import com.imagegame.service.GameHistoryService;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/gameHistory")
@RestController
public class GameHistoryController {

	private final static Logger logger = LoggerFactory.getLogger(GameHistoryController.class);

	@Autowired
	GameHistoryService gameHistoryService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<GameHistory> getAll() {

		List<GameHistory> gameHistorys = gameHistoryService.findAll();
		
		return gameHistorys;	
	}

	@GetMapping(value = "/{gameHistoryId}")
	@ResponseBody
	public GameHistoryDTO getGameHistory(@PathVariable Integer gameHistoryId) {
		
		return (gameHistoryService.getGameHistoryDTOById(gameHistoryId));
	}

 	@RequestMapping(value = "/addGameHistory", method = RequestMethod.POST)
	public ResponseEntity<?> addGameHistory(@RequestBody GameHistoryDTO gameHistoryDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = gameHistoryService.addGameHistory(gameHistoryDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/gameHistorys")
	public ResponseEntity<GameHistoryPageDTO> getGameHistorys(GameHistorySearchDTO gameHistorySearchDTO) {
 
		return gameHistoryService.getGameHistorys(gameHistorySearchDTO);
	}	

	@RequestMapping(value = "/updateGameHistory", method = RequestMethod.POST)
	public ResponseEntity<?> updateGameHistory(@RequestBody GameHistoryDTO gameHistoryDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = gameHistoryService.updateGameHistory(gameHistoryDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
