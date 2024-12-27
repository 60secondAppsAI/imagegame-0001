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

import com.imagegame.domain.ChallengeCompletion;
import com.imagegame.dto.ChallengeCompletionDTO;
import com.imagegame.dto.ChallengeCompletionSearchDTO;
import com.imagegame.dto.ChallengeCompletionPageDTO;
import com.imagegame.service.ChallengeCompletionService;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/challengeCompletion")
@RestController
public class ChallengeCompletionController {

	private final static Logger logger = LoggerFactory.getLogger(ChallengeCompletionController.class);

	@Autowired
	ChallengeCompletionService challengeCompletionService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<ChallengeCompletion> getAll() {

		List<ChallengeCompletion> challengeCompletions = challengeCompletionService.findAll();
		
		return challengeCompletions;	
	}

	@GetMapping(value = "/{challengeCompletionId}")
	@ResponseBody
	public ChallengeCompletionDTO getChallengeCompletion(@PathVariable Integer challengeCompletionId) {
		
		return (challengeCompletionService.getChallengeCompletionDTOById(challengeCompletionId));
	}

 	@RequestMapping(value = "/addChallengeCompletion", method = RequestMethod.POST)
	public ResponseEntity<?> addChallengeCompletion(@RequestBody ChallengeCompletionDTO challengeCompletionDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = challengeCompletionService.addChallengeCompletion(challengeCompletionDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/challengeCompletions")
	public ResponseEntity<ChallengeCompletionPageDTO> getChallengeCompletions(ChallengeCompletionSearchDTO challengeCompletionSearchDTO) {
 
		return challengeCompletionService.getChallengeCompletions(challengeCompletionSearchDTO);
	}	

	@RequestMapping(value = "/updateChallengeCompletion", method = RequestMethod.POST)
	public ResponseEntity<?> updateChallengeCompletion(@RequestBody ChallengeCompletionDTO challengeCompletionDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = challengeCompletionService.updateChallengeCompletion(challengeCompletionDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
