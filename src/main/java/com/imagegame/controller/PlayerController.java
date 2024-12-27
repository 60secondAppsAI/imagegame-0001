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

import com.imagegame.domain.Player;
import com.imagegame.dto.PlayerDTO;
import com.imagegame.dto.PlayerSearchDTO;
import com.imagegame.dto.PlayerPageDTO;
import com.imagegame.service.PlayerService;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/player")
@RestController
public class PlayerController {

	private final static Logger logger = LoggerFactory.getLogger(PlayerController.class);

	@Autowired
	PlayerService playerService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Player> getAll() {

		List<Player> players = playerService.findAll();
		
		return players;	
	}

	@GetMapping(value = "/{playerId}")
	@ResponseBody
	public PlayerDTO getPlayer(@PathVariable Integer playerId) {
		
		return (playerService.getPlayerDTOById(playerId));
	}

 	@RequestMapping(value = "/addPlayer", method = RequestMethod.POST)
	public ResponseEntity<?> addPlayer(@RequestBody PlayerDTO playerDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = playerService.addPlayer(playerDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/players")
	public ResponseEntity<PlayerPageDTO> getPlayers(PlayerSearchDTO playerSearchDTO) {
 
		return playerService.getPlayers(playerSearchDTO);
	}	

	@RequestMapping(value = "/updatePlayer", method = RequestMethod.POST)
	public ResponseEntity<?> updatePlayer(@RequestBody PlayerDTO playerDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = playerService.updatePlayer(playerDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
