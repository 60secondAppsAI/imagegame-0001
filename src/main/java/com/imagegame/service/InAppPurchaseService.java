package com.imagegame.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.imagegame.domain.InAppPurchase;
import com.imagegame.dto.InAppPurchaseDTO;
import com.imagegame.dto.InAppPurchaseSearchDTO;
import com.imagegame.dto.InAppPurchasePageDTO;
import com.imagegame.dto.InAppPurchaseConvertCriteriaDTO;
import com.imagegame.service.GenericService;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface InAppPurchaseService extends GenericService<InAppPurchase, Integer> {

	List<InAppPurchase> findAll();

	ResultDTO addInAppPurchase(InAppPurchaseDTO inAppPurchaseDTO, RequestDTO requestDTO);

	ResultDTO updateInAppPurchase(InAppPurchaseDTO inAppPurchaseDTO, RequestDTO requestDTO);

    Page<InAppPurchase> getAllInAppPurchases(Pageable pageable);

    Page<InAppPurchase> getAllInAppPurchases(Specification<InAppPurchase> spec, Pageable pageable);

	ResponseEntity<InAppPurchasePageDTO> getInAppPurchases(InAppPurchaseSearchDTO inAppPurchaseSearchDTO);
	
	List<InAppPurchaseDTO> convertInAppPurchasesToInAppPurchaseDTOs(List<InAppPurchase> inAppPurchases, InAppPurchaseConvertCriteriaDTO convertCriteria);

	InAppPurchaseDTO getInAppPurchaseDTOById(Integer inAppPurchaseId);







}





