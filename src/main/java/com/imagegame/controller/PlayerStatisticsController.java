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

import com.imagegame.domain.PlayerStatistics;
import com.imagegame.dto.PlayerStatisticsDTO;
import com.imagegame.dto.PlayerStatisticsSearchDTO;
import com.imagegame.dto.PlayerStatisticsPageDTO;
import com.imagegame.service.PlayerStatisticsService;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/playerStatistics")
@RestController
public class PlayerStatisticsController {

	private final static Logger logger = LoggerFactory.getLogger(PlayerStatisticsController.class);

	@Autowired
	PlayerStatisticsService playerStatisticsService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<PlayerStatistics> getAll() {

		List<PlayerStatistics> playerStatisticss = playerStatisticsService.findAll();
		
		return playerStatisticss;	
	}

	@GetMapping(value = "/{playerStatisticsId}")
	@ResponseBody
	public PlayerStatisticsDTO getPlayerStatistics(@PathVariable Integer playerStatisticsId) {
		
		return (playerStatisticsService.getPlayerStatisticsDTOById(playerStatisticsId));
	}

 	@RequestMapping(value = "/addPlayerStatistics", method = RequestMethod.POST)
	public ResponseEntity<?> addPlayerStatistics(@RequestBody PlayerStatisticsDTO playerStatisticsDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = playerStatisticsService.addPlayerStatistics(playerStatisticsDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/playerStatisticss")
	public ResponseEntity<PlayerStatisticsPageDTO> getPlayerStatisticss(PlayerStatisticsSearchDTO playerStatisticsSearchDTO) {
 
		return playerStatisticsService.getPlayerStatisticss(playerStatisticsSearchDTO);
	}	

	@RequestMapping(value = "/updatePlayerStatistics", method = RequestMethod.POST)
	public ResponseEntity<?> updatePlayerStatistics(@RequestBody PlayerStatisticsDTO playerStatisticsDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = playerStatisticsService.updatePlayerStatistics(playerStatisticsDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
