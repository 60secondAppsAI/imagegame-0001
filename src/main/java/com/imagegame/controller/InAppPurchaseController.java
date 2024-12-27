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

import com.imagegame.domain.InAppPurchase;
import com.imagegame.dto.InAppPurchaseDTO;
import com.imagegame.dto.InAppPurchaseSearchDTO;
import com.imagegame.dto.InAppPurchasePageDTO;
import com.imagegame.service.InAppPurchaseService;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/inAppPurchase")
@RestController
public class InAppPurchaseController {

	private final static Logger logger = LoggerFactory.getLogger(InAppPurchaseController.class);

	@Autowired
	InAppPurchaseService inAppPurchaseService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<InAppPurchase> getAll() {

		List<InAppPurchase> inAppPurchases = inAppPurchaseService.findAll();
		
		return inAppPurchases;	
	}

	@GetMapping(value = "/{inAppPurchaseId}")
	@ResponseBody
	public InAppPurchaseDTO getInAppPurchase(@PathVariable Integer inAppPurchaseId) {
		
		return (inAppPurchaseService.getInAppPurchaseDTOById(inAppPurchaseId));
	}

 	@RequestMapping(value = "/addInAppPurchase", method = RequestMethod.POST)
	public ResponseEntity<?> addInAppPurchase(@RequestBody InAppPurchaseDTO inAppPurchaseDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = inAppPurchaseService.addInAppPurchase(inAppPurchaseDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/inAppPurchases")
	public ResponseEntity<InAppPurchasePageDTO> getInAppPurchases(InAppPurchaseSearchDTO inAppPurchaseSearchDTO) {
 
		return inAppPurchaseService.getInAppPurchases(inAppPurchaseSearchDTO);
	}	

	@RequestMapping(value = "/updateInAppPurchase", method = RequestMethod.POST)
	public ResponseEntity<?> updateInAppPurchase(@RequestBody InAppPurchaseDTO inAppPurchaseDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = inAppPurchaseService.updateInAppPurchase(inAppPurchaseDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
