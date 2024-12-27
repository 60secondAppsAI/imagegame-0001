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
import com.imagegame.dao.ChallengeDAO;
import com.imagegame.domain.Challenge;
import com.imagegame.dto.ChallengeDTO;
import com.imagegame.dto.ChallengeSearchDTO;
import com.imagegame.dto.ChallengePageDTO;
import com.imagegame.dto.ChallengeConvertCriteriaDTO;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;
import com.imagegame.service.ChallengeService;
import com.imagegame.util.ControllerUtils;





@Service
public class ChallengeServiceImpl extends GenericServiceImpl<Challenge, Integer> implements ChallengeService {

    private final static Logger logger = LoggerFactory.getLogger(ChallengeServiceImpl.class);

	@Autowired
	ChallengeDAO challengeDao;

	


	@Override
	public GenericDAO<Challenge, Integer> getDAO() {
		return (GenericDAO<Challenge, Integer>) challengeDao;
	}
	
	public List<Challenge> findAll () {
		List<Challenge> challenges = challengeDao.findAll();
		
		return challenges;	
		
	}

	public ResultDTO addChallenge(ChallengeDTO challengeDTO, RequestDTO requestDTO) {

		Challenge challenge = new Challenge();

		challenge.setChallengeId(challengeDTO.getChallengeId());


		challenge.setDescription(challengeDTO.getDescription());


		challenge.setReward(challengeDTO.getReward());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		challenge = challengeDao.save(challenge);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Challenge> getAllChallenges(Pageable pageable) {
		return challengeDao.findAll(pageable);
	}

	public Page<Challenge> getAllChallenges(Specification<Challenge> spec, Pageable pageable) {
		return challengeDao.findAll(spec, pageable);
	}

	public ResponseEntity<ChallengePageDTO> getChallenges(ChallengeSearchDTO challengeSearchDTO) {
	
			Integer challengeId = challengeSearchDTO.getChallengeId(); 
 			String description = challengeSearchDTO.getDescription(); 
 			Integer reward = challengeSearchDTO.getReward(); 
 			String sortBy = challengeSearchDTO.getSortBy();
			String sortOrder = challengeSearchDTO.getSortOrder();
			String searchQuery = challengeSearchDTO.getSearchQuery();
			Integer page = challengeSearchDTO.getPage();
			Integer size = challengeSearchDTO.getSize();

	        Specification<Challenge> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, challengeId, "challengeId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, description, "description"); 
			
			spec = ControllerUtils.andIfNecessary(spec, reward, "reward"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("description")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Challenge> challenges = this.getAllChallenges(spec, pageable);
		
		//System.out.println(String.valueOf(challenges.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(challenges.getTotalPages()));
		
		List<Challenge> challengesList = challenges.getContent();
		
		ChallengeConvertCriteriaDTO convertCriteria = new ChallengeConvertCriteriaDTO();
		List<ChallengeDTO> challengeDTOs = this.convertChallengesToChallengeDTOs(challengesList,convertCriteria);
		
		ChallengePageDTO challengePageDTO = new ChallengePageDTO();
		challengePageDTO.setChallenges(challengeDTOs);
		challengePageDTO.setTotalElements(challenges.getTotalElements());
		return ResponseEntity.ok(challengePageDTO);
	}

	public List<ChallengeDTO> convertChallengesToChallengeDTOs(List<Challenge> challenges, ChallengeConvertCriteriaDTO convertCriteria) {
		
		List<ChallengeDTO> challengeDTOs = new ArrayList<ChallengeDTO>();
		
		for (Challenge challenge : challenges) {
			challengeDTOs.add(convertChallengeToChallengeDTO(challenge,convertCriteria));
		}
		
		return challengeDTOs;

	}
	
	public ChallengeDTO convertChallengeToChallengeDTO(Challenge challenge, ChallengeConvertCriteriaDTO convertCriteria) {
		
		ChallengeDTO challengeDTO = new ChallengeDTO();
		
		challengeDTO.setChallengeId(challenge.getChallengeId());

	
		challengeDTO.setDescription(challenge.getDescription());

	
		challengeDTO.setReward(challenge.getReward());

	

		
		return challengeDTO;
	}

	public ResultDTO updateChallenge(ChallengeDTO challengeDTO, RequestDTO requestDTO) {
		
		Challenge challenge = challengeDao.getById(challengeDTO.getChallengeId());

		challenge.setChallengeId(ControllerUtils.setValue(challenge.getChallengeId(), challengeDTO.getChallengeId()));

		challenge.setDescription(ControllerUtils.setValue(challenge.getDescription(), challengeDTO.getDescription()));

		challenge.setReward(ControllerUtils.setValue(challenge.getReward(), challengeDTO.getReward()));



        challenge = challengeDao.save(challenge);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public ChallengeDTO getChallengeDTOById(Integer challengeId) {
	
		Challenge challenge = challengeDao.getById(challengeId);
			
		
		ChallengeConvertCriteriaDTO convertCriteria = new ChallengeConvertCriteriaDTO();
		return(this.convertChallengeToChallengeDTO(challenge,convertCriteria));
	}







}
