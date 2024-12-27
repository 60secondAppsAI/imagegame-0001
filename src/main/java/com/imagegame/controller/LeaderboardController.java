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

import com.imagegame.domain.Leaderboard;
import com.imagegame.dto.LeaderboardDTO;
import com.imagegame.dto.LeaderboardSearchDTO;
import com.imagegame.dto.LeaderboardPageDTO;
import com.imagegame.service.LeaderboardService;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/leaderboard")
@RestController
public class LeaderboardController {

	private final static Logger logger = LoggerFactory.getLogger(LeaderboardController.class);

	@Autowired
	LeaderboardService leaderboardService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Leaderboard> getAll() {

		List<Leaderboard> leaderboards = leaderboardService.findAll();
		
		return leaderboards;	
	}

	@GetMapping(value = "/{leaderboardId}")
	@ResponseBody
	public LeaderboardDTO getLeaderboard(@PathVariable Integer leaderboardId) {
		
		return (leaderboardService.getLeaderboardDTOById(leaderboardId));
	}

 	@RequestMapping(value = "/addLeaderboard", method = RequestMethod.POST)
	public ResponseEntity<?> addLeaderboard(@RequestBody LeaderboardDTO leaderboardDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = leaderboardService.addLeaderboard(leaderboardDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/leaderboards")
	public ResponseEntity<LeaderboardPageDTO> getLeaderboards(LeaderboardSearchDTO leaderboardSearchDTO) {
 
		return leaderboardService.getLeaderboards(leaderboardSearchDTO);
	}	

	@RequestMapping(value = "/updateLeaderboard", method = RequestMethod.POST)
	public ResponseEntity<?> updateLeaderboard(@RequestBody LeaderboardDTO leaderboardDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = leaderboardService.updateLeaderboard(leaderboardDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
