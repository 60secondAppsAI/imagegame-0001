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
import com.imagegame.dao.KeyWordPairDAO;
import com.imagegame.domain.KeyWordPair;
import com.imagegame.dto.KeyWordPairDTO;
import com.imagegame.dto.KeyWordPairSearchDTO;
import com.imagegame.dto.KeyWordPairPageDTO;
import com.imagegame.dto.KeyWordPairConvertCriteriaDTO;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;
import com.imagegame.service.KeyWordPairService;
import com.imagegame.util.ControllerUtils;





@Service
public class KeyWordPairServiceImpl extends GenericServiceImpl<KeyWordPair, Integer> implements KeyWordPairService {

    private final static Logger logger = LoggerFactory.getLogger(KeyWordPairServiceImpl.class);

	@Autowired
	KeyWordPairDAO keyWordPairDao;

	


	@Override
	public GenericDAO<KeyWordPair, Integer> getDAO() {
		return (GenericDAO<KeyWordPair, Integer>) keyWordPairDao;
	}
	
	public List<KeyWordPair> findAll () {
		List<KeyWordPair> keyWordPairs = keyWordPairDao.findAll();
		
		return keyWordPairs;	
		
	}

	public ResultDTO addKeyWordPair(KeyWordPairDTO keyWordPairDTO, RequestDTO requestDTO) {

		KeyWordPair keyWordPair = new KeyWordPair();

		keyWordPair.setKeyWordPairId(keyWordPairDTO.getKeyWordPairId());


		keyWordPair.setWordOne(keyWordPairDTO.getWordOne());


		keyWordPair.setWordTwo(keyWordPairDTO.getWordTwo());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		keyWordPair = keyWordPairDao.save(keyWordPair);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<KeyWordPair> getAllKeyWordPairs(Pageable pageable) {
		return keyWordPairDao.findAll(pageable);
	}

	public Page<KeyWordPair> getAllKeyWordPairs(Specification<KeyWordPair> spec, Pageable pageable) {
		return keyWordPairDao.findAll(spec, pageable);
	}

	public ResponseEntity<KeyWordPairPageDTO> getKeyWordPairs(KeyWordPairSearchDTO keyWordPairSearchDTO) {
	
			Integer keyWordPairId = keyWordPairSearchDTO.getKeyWordPairId(); 
 			String wordOne = keyWordPairSearchDTO.getWordOne(); 
 			String wordTwo = keyWordPairSearchDTO.getWordTwo(); 
 			String sortBy = keyWordPairSearchDTO.getSortBy();
			String sortOrder = keyWordPairSearchDTO.getSortOrder();
			String searchQuery = keyWordPairSearchDTO.getSearchQuery();
			Integer page = keyWordPairSearchDTO.getPage();
			Integer size = keyWordPairSearchDTO.getSize();

	        Specification<KeyWordPair> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, keyWordPairId, "keyWordPairId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, wordOne, "wordOne"); 
			
			spec = ControllerUtils.andIfNecessary(spec, wordTwo, "wordTwo"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("wordOne")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("wordTwo")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<KeyWordPair> keyWordPairs = this.getAllKeyWordPairs(spec, pageable);
		
		//System.out.println(String.valueOf(keyWordPairs.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(keyWordPairs.getTotalPages()));
		
		List<KeyWordPair> keyWordPairsList = keyWordPairs.getContent();
		
		KeyWordPairConvertCriteriaDTO convertCriteria = new KeyWordPairConvertCriteriaDTO();
		List<KeyWordPairDTO> keyWordPairDTOs = this.convertKeyWordPairsToKeyWordPairDTOs(keyWordPairsList,convertCriteria);
		
		KeyWordPairPageDTO keyWordPairPageDTO = new KeyWordPairPageDTO();
		keyWordPairPageDTO.setKeyWordPairs(keyWordPairDTOs);
		keyWordPairPageDTO.setTotalElements(keyWordPairs.getTotalElements());
		return ResponseEntity.ok(keyWordPairPageDTO);
	}

	public List<KeyWordPairDTO> convertKeyWordPairsToKeyWordPairDTOs(List<KeyWordPair> keyWordPairs, KeyWordPairConvertCriteriaDTO convertCriteria) {
		
		List<KeyWordPairDTO> keyWordPairDTOs = new ArrayList<KeyWordPairDTO>();
		
		for (KeyWordPair keyWordPair : keyWordPairs) {
			keyWordPairDTOs.add(convertKeyWordPairToKeyWordPairDTO(keyWordPair,convertCriteria));
		}
		
		return keyWordPairDTOs;

	}
	
	public KeyWordPairDTO convertKeyWordPairToKeyWordPairDTO(KeyWordPair keyWordPair, KeyWordPairConvertCriteriaDTO convertCriteria) {
		
		KeyWordPairDTO keyWordPairDTO = new KeyWordPairDTO();
		
		keyWordPairDTO.setKeyWordPairId(keyWordPair.getKeyWordPairId());

	
		keyWordPairDTO.setWordOne(keyWordPair.getWordOne());

	
		keyWordPairDTO.setWordTwo(keyWordPair.getWordTwo());

	

		
		return keyWordPairDTO;
	}

	public ResultDTO updateKeyWordPair(KeyWordPairDTO keyWordPairDTO, RequestDTO requestDTO) {
		
		KeyWordPair keyWordPair = keyWordPairDao.getById(keyWordPairDTO.getKeyWordPairId());

		keyWordPair.setKeyWordPairId(ControllerUtils.setValue(keyWordPair.getKeyWordPairId(), keyWordPairDTO.getKeyWordPairId()));

		keyWordPair.setWordOne(ControllerUtils.setValue(keyWordPair.getWordOne(), keyWordPairDTO.getWordOne()));

		keyWordPair.setWordTwo(ControllerUtils.setValue(keyWordPair.getWordTwo(), keyWordPairDTO.getWordTwo()));



        keyWordPair = keyWordPairDao.save(keyWordPair);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public KeyWordPairDTO getKeyWordPairDTOById(Integer keyWordPairId) {
	
		KeyWordPair keyWordPair = keyWordPairDao.getById(keyWordPairId);
			
		
		KeyWordPairConvertCriteriaDTO convertCriteria = new KeyWordPairConvertCriteriaDTO();
		return(this.convertKeyWordPairToKeyWordPairDTO(keyWordPair,convertCriteria));
	}







}
