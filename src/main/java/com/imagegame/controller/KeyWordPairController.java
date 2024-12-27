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

import com.imagegame.domain.KeyWordPair;
import com.imagegame.dto.KeyWordPairDTO;
import com.imagegame.dto.KeyWordPairSearchDTO;
import com.imagegame.dto.KeyWordPairPageDTO;
import com.imagegame.service.KeyWordPairService;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/keyWordPair")
@RestController
public class KeyWordPairController {

	private final static Logger logger = LoggerFactory.getLogger(KeyWordPairController.class);

	@Autowired
	KeyWordPairService keyWordPairService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<KeyWordPair> getAll() {

		List<KeyWordPair> keyWordPairs = keyWordPairService.findAll();
		
		return keyWordPairs;	
	}

	@GetMapping(value = "/{keyWordPairId}")
	@ResponseBody
	public KeyWordPairDTO getKeyWordPair(@PathVariable Integer keyWordPairId) {
		
		return (keyWordPairService.getKeyWordPairDTOById(keyWordPairId));
	}

 	@RequestMapping(value = "/addKeyWordPair", method = RequestMethod.POST)
	public ResponseEntity<?> addKeyWordPair(@RequestBody KeyWordPairDTO keyWordPairDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = keyWordPairService.addKeyWordPair(keyWordPairDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/keyWordPairs")
	public ResponseEntity<KeyWordPairPageDTO> getKeyWordPairs(KeyWordPairSearchDTO keyWordPairSearchDTO) {
 
		return keyWordPairService.getKeyWordPairs(keyWordPairSearchDTO);
	}	

	@RequestMapping(value = "/updateKeyWordPair", method = RequestMethod.POST)
	public ResponseEntity<?> updateKeyWordPair(@RequestBody KeyWordPairDTO keyWordPairDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = keyWordPairService.updateKeyWordPair(keyWordPairDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
