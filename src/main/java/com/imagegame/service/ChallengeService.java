package com.imagegame.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.imagegame.domain.Challenge;
import com.imagegame.dto.ChallengeDTO;
import com.imagegame.dto.ChallengeSearchDTO;
import com.imagegame.dto.ChallengePageDTO;
import com.imagegame.dto.ChallengeConvertCriteriaDTO;
import com.imagegame.service.GenericService;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface ChallengeService extends GenericService<Challenge, Integer> {

	List<Challenge> findAll();

	ResultDTO addChallenge(ChallengeDTO challengeDTO, RequestDTO requestDTO);

	ResultDTO updateChallenge(ChallengeDTO challengeDTO, RequestDTO requestDTO);

    Page<Challenge> getAllChallenges(Pageable pageable);

    Page<Challenge> getAllChallenges(Specification<Challenge> spec, Pageable pageable);

	ResponseEntity<ChallengePageDTO> getChallenges(ChallengeSearchDTO challengeSearchDTO);
	
	List<ChallengeDTO> convertChallengesToChallengeDTOs(List<Challenge> challenges, ChallengeConvertCriteriaDTO convertCriteria);

	ChallengeDTO getChallengeDTOById(Integer challengeId);







}





