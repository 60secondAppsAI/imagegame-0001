package com.imagegame.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.imagegame.domain.ChallengeCompletion;
import com.imagegame.dto.ChallengeCompletionDTO;
import com.imagegame.dto.ChallengeCompletionSearchDTO;
import com.imagegame.dto.ChallengeCompletionPageDTO;
import com.imagegame.dto.ChallengeCompletionConvertCriteriaDTO;
import com.imagegame.service.GenericService;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface ChallengeCompletionService extends GenericService<ChallengeCompletion, Integer> {

	List<ChallengeCompletion> findAll();

	ResultDTO addChallengeCompletion(ChallengeCompletionDTO challengeCompletionDTO, RequestDTO requestDTO);

	ResultDTO updateChallengeCompletion(ChallengeCompletionDTO challengeCompletionDTO, RequestDTO requestDTO);

    Page<ChallengeCompletion> getAllChallengeCompletions(Pageable pageable);

    Page<ChallengeCompletion> getAllChallengeCompletions(Specification<ChallengeCompletion> spec, Pageable pageable);

	ResponseEntity<ChallengeCompletionPageDTO> getChallengeCompletions(ChallengeCompletionSearchDTO challengeCompletionSearchDTO);
	
	List<ChallengeCompletionDTO> convertChallengeCompletionsToChallengeCompletionDTOs(List<ChallengeCompletion> challengeCompletions, ChallengeCompletionConvertCriteriaDTO convertCriteria);

	ChallengeCompletionDTO getChallengeCompletionDTOById(Integer challengeCompletionId);







}





