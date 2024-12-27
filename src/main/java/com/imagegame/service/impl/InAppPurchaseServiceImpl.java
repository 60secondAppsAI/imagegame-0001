package com.imagegame.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;



import com.imagegame.dao.GenericDAO;
import com.imagegame.service.GenericService;
import com.imagegame.service.impl.GenericServiceImpl;
import com.imagegame.dao.InAppPurchaseDAO;
import com.imagegame.domain.InAppPurchase;
import com.imagegame.dto.InAppPurchaseDTO;
import com.imagegame.dto.InAppPurchaseSearchDTO;
import com.imagegame.dto.InAppPurchasePageDTO;
import com.imagegame.dto.InAppPurchaseConvertCriteriaDTO;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;
import com.imagegame.service.InAppPurchaseService;
import com.imagegame.util.ControllerUtils;





@Service
public class InAppPurchaseServiceImpl extends GenericServiceImpl<InAppPurchase, Integer> implements InAppPurchaseService {

    private final static Logger logger = LoggerFactory.getLogger(InAppPurchaseServiceImpl.class);

	@Autowired
	InAppPurchaseDAO inAppPurchaseDao;

	


	@Override
	public GenericDAO<InAppPurchase, Integer> getDAO() {
		return (GenericDAO<InAppPurchase, Integer>) inAppPurchaseDao;
	}
	
	public List<InAppPurchase> findAll () {
		List<InAppPurchase> inAppPurchases = inAppPurchaseDao.findAll();
		
		return inAppPurchases;	
		
	}

	public ResultDTO addInAppPurchase(InAppPurchaseDTO inAppPurchaseDTO, RequestDTO requestDTO) {

		InAppPurchase inAppPurchase = new InAppPurchase();

		inAppPurchase.setInAppPurchaseId(inAppPurchaseDTO.getInAppPurchaseId());


		inAppPurchase.setProductId(inAppPurchaseDTO.getProductId());


		inAppPurchase.setPurchaseDate(inAppPurchaseDTO.getPurchaseDate());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		inAppPurchase = inAppPurchaseDao.save(inAppPurchase);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<InAppPurchase> getAllInAppPurchases(Pageable pageable) {
		return inAppPurchaseDao.findAll(pageable);
	}

	public Page<InAppPurchase> getAllInAppPurchases(Specification<InAppPurchase> spec, Pageable pageable) {
		return inAppPurchaseDao.findAll(spec, pageable);
	}

	public ResponseEntity<InAppPurchasePageDTO> getInAppPurchases(InAppPurchaseSearchDTO inAppPurchaseSearchDTO) {
	
			Integer inAppPurchaseId = inAppPurchaseSearchDTO.getInAppPurchaseId(); 
 			String productId = inAppPurchaseSearchDTO.getProductId(); 
   			String sortBy = inAppPurchaseSearchDTO.getSortBy();
			String sortOrder = inAppPurchaseSearchDTO.getSortOrder();
			String searchQuery = inAppPurchaseSearchDTO.getSearchQuery();
			Integer page = inAppPurchaseSearchDTO.getPage();
			Integer size = inAppPurchaseSearchDTO.getSize();

	        Specification<InAppPurchase> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, inAppPurchaseId, "inAppPurchaseId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, productId, "productId"); 
			
 			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("productId")), "%" + searchQuery.toLowerCase() + "%") 
		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<InAppPurchase> inAppPurchases = this.getAllInAppPurchases(spec, pageable);
		
		//System.out.println(String.valueOf(inAppPurchases.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(inAppPurchases.getTotalPages()));
		
		List<InAppPurchase> inAppPurchasesList = inAppPurchases.getContent();
		
		InAppPurchaseConvertCriteriaDTO convertCriteria = new InAppPurchaseConvertCriteriaDTO();
		List<InAppPurchaseDTO> inAppPurchaseDTOs = this.convertInAppPurchasesToInAppPurchaseDTOs(inAppPurchasesList,convertCriteria);
		
		InAppPurchasePageDTO inAppPurchasePageDTO = new InAppPurchasePageDTO();
		inAppPurchasePageDTO.setInAppPurchases(inAppPurchaseDTOs);
		inAppPurchasePageDTO.setTotalElements(inAppPurchases.getTotalElements());
		return ResponseEntity.ok(inAppPurchasePageDTO);
	}

	public List<InAppPurchaseDTO> convertInAppPurchasesToInAppPurchaseDTOs(List<InAppPurchase> inAppPurchases, InAppPurchaseConvertCriteriaDTO convertCriteria) {
		
		List<InAppPurchaseDTO> inAppPurchaseDTOs = new ArrayList<InAppPurchaseDTO>();
		
		for (InAppPurchase inAppPurchase : inAppPurchases) {
			inAppPurchaseDTOs.add(convertInAppPurchaseToInAppPurchaseDTO(inAppPurchase,convertCriteria));
		}
		
		return inAppPurchaseDTOs;

	}
	
	public InAppPurchaseDTO convertInAppPurchaseToInAppPurchaseDTO(InAppPurchase inAppPurchase, InAppPurchaseConvertCriteriaDTO convertCriteria) {
		
		InAppPurchaseDTO inAppPurchaseDTO = new InAppPurchaseDTO();
		
		inAppPurchaseDTO.setInAppPurchaseId(inAppPurchase.getInAppPurchaseId());

	
		inAppPurchaseDTO.setProductId(inAppPurchase.getProductId());

	
		inAppPurchaseDTO.setPurchaseDate(inAppPurchase.getPurchaseDate());

	

		
		return inAppPurchaseDTO;
	}

	public ResultDTO updateInAppPurchase(InAppPurchaseDTO inAppPurchaseDTO, RequestDTO requestDTO) {
		
		InAppPurchase inAppPurchase = inAppPurchaseDao.getById(inAppPurchaseDTO.getInAppPurchaseId());

		inAppPurchase.setInAppPurchaseId(ControllerUtils.setValue(inAppPurchase.getInAppPurchaseId(), inAppPurchaseDTO.getInAppPurchaseId()));

		inAppPurchase.setProductId(ControllerUtils.setValue(inAppPurchase.getProductId(), inAppPurchaseDTO.getProductId()));

		inAppPurchase.setPurchaseDate(ControllerUtils.setValue(inAppPurchase.getPurchaseDate(), inAppPurchaseDTO.getPurchaseDate()));



        inAppPurchase = inAppPurchaseDao.save(inAppPurchase);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public InAppPurchaseDTO getInAppPurchaseDTOById(Integer inAppPurchaseId) {
	
		InAppPurchase inAppPurchase = inAppPurchaseDao.getById(inAppPurchaseId);
			
		
		InAppPurchaseConvertCriteriaDTO convertCriteria = new InAppPurchaseConvertCriteriaDTO();
		return(this.convertInAppPurchaseToInAppPurchaseDTO(inAppPurchase,convertCriteria));
	}







}
