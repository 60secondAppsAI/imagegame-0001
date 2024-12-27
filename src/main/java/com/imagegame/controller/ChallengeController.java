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

import com.imagegame.domain.Challenge;
import com.imagegame.dto.ChallengeDTO;
import com.imagegame.dto.ChallengeSearchDTO;
import com.imagegame.dto.ChallengePageDTO;
import com.imagegame.service.ChallengeService;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/challenge")
@RestController
public class ChallengeController {

	private final static Logger logger = LoggerFactory.getLogger(ChallengeController.class);

	@Autowired
	ChallengeService challengeService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Challenge> getAll() {

		List<Challenge> challenges = challengeService.findAll();
		
		return challenges;	
	}

	@GetMapping(value = "/{challengeId}")
	@ResponseBody
	public ChallengeDTO getChallenge(@PathVariable Integer challengeId) {
		
		return (challengeService.getChallengeDTOById(challengeId));
	}

 	@RequestMapping(value = "/addChallenge", method = RequestMethod.POST)
	public ResponseEntity<?> addChallenge(@RequestBody ChallengeDTO challengeDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = challengeService.addChallenge(challengeDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/challenges")
	public ResponseEntity<ChallengePageDTO> getChallenges(ChallengeSearchDTO challengeSearchDTO) {
 
		return challengeService.getChallenges(challengeSearchDTO);
	}	

	@RequestMapping(value = "/updateChallenge", method = RequestMethod.POST)
	public ResponseEntity<?> updateChallenge(@RequestBody ChallengeDTO challengeDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = challengeService.updateChallenge(challengeDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
