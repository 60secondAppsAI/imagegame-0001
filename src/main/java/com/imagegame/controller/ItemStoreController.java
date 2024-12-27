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

import com.imagegame.domain.ItemStore;
import com.imagegame.dto.ItemStoreDTO;
import com.imagegame.dto.ItemStoreSearchDTO;
import com.imagegame.dto.ItemStorePageDTO;
import com.imagegame.service.ItemStoreService;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/itemStore")
@RestController
public class ItemStoreController {

	private final static Logger logger = LoggerFactory.getLogger(ItemStoreController.class);

	@Autowired
	ItemStoreService itemStoreService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<ItemStore> getAll() {

		List<ItemStore> itemStores = itemStoreService.findAll();
		
		return itemStores;	
	}

	@GetMapping(value = "/{itemStoreId}")
	@ResponseBody
	public ItemStoreDTO getItemStore(@PathVariable Integer itemStoreId) {
		
		return (itemStoreService.getItemStoreDTOById(itemStoreId));
	}

 	@RequestMapping(value = "/addItemStore", method = RequestMethod.POST)
	public ResponseEntity<?> addItemStore(@RequestBody ItemStoreDTO itemStoreDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = itemStoreService.addItemStore(itemStoreDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/itemStores")
	public ResponseEntity<ItemStorePageDTO> getItemStores(ItemStoreSearchDTO itemStoreSearchDTO) {
 
		return itemStoreService.getItemStores(itemStoreSearchDTO);
	}	

	@RequestMapping(value = "/updateItemStore", method = RequestMethod.POST)
	public ResponseEntity<?> updateItemStore(@RequestBody ItemStoreDTO itemStoreDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = itemStoreService.updateItemStore(itemStoreDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
