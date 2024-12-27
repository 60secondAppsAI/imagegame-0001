package com.imagegame.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.imagegame.domain.KeyWordPair;
import com.imagegame.dto.KeyWordPairDTO;
import com.imagegame.dto.KeyWordPairSearchDTO;
import com.imagegame.dto.KeyWordPairPageDTO;
import com.imagegame.dto.KeyWordPairConvertCriteriaDTO;
import com.imagegame.service.GenericService;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface KeyWordPairService extends GenericService<KeyWordPair, Integer> {

	List<KeyWordPair> findAll();

	ResultDTO addKeyWordPair(KeyWordPairDTO keyWordPairDTO, RequestDTO requestDTO);

	ResultDTO updateKeyWordPair(KeyWordPairDTO keyWordPairDTO, RequestDTO requestDTO);

    Page<KeyWordPair> getAllKeyWordPairs(Pageable pageable);

    Page<KeyWordPair> getAllKeyWordPairs(Specification<KeyWordPair> spec, Pageable pageable);

	ResponseEntity<KeyWordPairPageDTO> getKeyWordPairs(KeyWordPairSearchDTO keyWordPairSearchDTO);
	
	List<KeyWordPairDTO> convertKeyWordPairsToKeyWordPairDTOs(List<KeyWordPair> keyWordPairs, KeyWordPairConvertCriteriaDTO convertCriteria);

	KeyWordPairDTO getKeyWordPairDTOById(Integer keyWordPairId);







}





