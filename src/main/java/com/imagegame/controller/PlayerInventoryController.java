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

import com.imagegame.domain.PlayerInventory;
import com.imagegame.dto.PlayerInventoryDTO;
import com.imagegame.dto.PlayerInventorySearchDTO;
import com.imagegame.dto.PlayerInventoryPageDTO;
import com.imagegame.service.PlayerInventoryService;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/playerInventory")
@RestController
public class PlayerInventoryController {

	private final static Logger logger = LoggerFactory.getLogger(PlayerInventoryController.class);

	@Autowired
	PlayerInventoryService playerInventoryService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<PlayerInventory> getAll() {

		List<PlayerInventory> playerInventorys = playerInventoryService.findAll();
		
		return playerInventorys;	
	}

	@GetMapping(value = "/{playerInventoryId}")
	@ResponseBody
	public PlayerInventoryDTO getPlayerInventory(@PathVariable Integer playerInventoryId) {
		
		return (playerInventoryService.getPlayerInventoryDTOById(playerInventoryId));
	}

 	@RequestMapping(value = "/addPlayerInventory", method = RequestMethod.POST)
	public ResponseEntity<?> addPlayerInventory(@RequestBody PlayerInventoryDTO playerInventoryDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = playerInventoryService.addPlayerInventory(playerInventoryDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/playerInventorys")
	public ResponseEntity<PlayerInventoryPageDTO> getPlayerInventorys(PlayerInventorySearchDTO playerInventorySearchDTO) {
 
		return playerInventoryService.getPlayerInventorys(playerInventorySearchDTO);
	}	

	@RequestMapping(value = "/updatePlayerInventory", method = RequestMethod.POST)
	public ResponseEntity<?> updatePlayerInventory(@RequestBody PlayerInventoryDTO playerInventoryDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = playerInventoryService.updatePlayerInventory(playerInventoryDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
