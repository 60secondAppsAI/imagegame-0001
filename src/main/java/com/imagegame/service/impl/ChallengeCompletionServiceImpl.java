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
import com.imagegame.dao.ChallengeCompletionDAO;
import com.imagegame.domain.ChallengeCompletion;
import com.imagegame.dto.ChallengeCompletionDTO;
import com.imagegame.dto.ChallengeCompletionSearchDTO;
import com.imagegame.dto.ChallengeCompletionPageDTO;
import com.imagegame.dto.ChallengeCompletionConvertCriteriaDTO;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;
import com.imagegame.service.ChallengeCompletionService;
import com.imagegame.util.ControllerUtils;





@Service
public class ChallengeCompletionServiceImpl extends GenericServiceImpl<ChallengeCompletion, Integer> implements ChallengeCompletionService {

    private final static Logger logger = LoggerFactory.getLogger(ChallengeCompletionServiceImpl.class);

	@Autowired
	ChallengeCompletionDAO challengeCompletionDao;

	


	@Override
	public GenericDAO<ChallengeCompletion, Integer> getDAO() {
		return (GenericDAO<ChallengeCompletion, Integer>) challengeCompletionDao;
	}
	
	public List<ChallengeCompletion> findAll () {
		List<ChallengeCompletion> challengeCompletions = challengeCompletionDao.findAll();
		
		return challengeCompletions;	
		
	}

	public ResultDTO addChallengeCompletion(ChallengeCompletionDTO challengeCompletionDTO, RequestDTO requestDTO) {

		ChallengeCompletion challengeCompletion = new ChallengeCompletion();

		challengeCompletion.setChallengeCompletionId(challengeCompletionDTO.getChallengeCompletionId());


		challengeCompletion.setCompletionDate(challengeCompletionDTO.getCompletionDate());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		challengeCompletion = challengeCompletionDao.save(challengeCompletion);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<ChallengeCompletion> getAllChallengeCompletions(Pageable pageable) {
		return challengeCompletionDao.findAll(pageable);
	}

	public Page<ChallengeCompletion> getAllChallengeCompletions(Specification<ChallengeCompletion> spec, Pageable pageable) {
		return challengeCompletionDao.findAll(spec, pageable);
	}

	public ResponseEntity<ChallengeCompletionPageDTO> getChallengeCompletions(ChallengeCompletionSearchDTO challengeCompletionSearchDTO) {
	
			Integer challengeCompletionId = challengeCompletionSearchDTO.getChallengeCompletionId(); 
   			String sortBy = challengeCompletionSearchDTO.getSortBy();
			String sortOrder = challengeCompletionSearchDTO.getSortOrder();
			String searchQuery = challengeCompletionSearchDTO.getSearchQuery();
			Integer page = challengeCompletionSearchDTO.getPage();
			Integer size = challengeCompletionSearchDTO.getSize();

	        Specification<ChallengeCompletion> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, challengeCompletionId, "challengeCompletionId"); 
			
 			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

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

		Page<ChallengeCompletion> challengeCompletions = this.getAllChallengeCompletions(spec, pageable);
		
		//System.out.println(String.valueOf(challengeCompletions.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(challengeCompletions.getTotalPages()));
		
		List<ChallengeCompletion> challengeCompletionsList = challengeCompletions.getContent();
		
		ChallengeCompletionConvertCriteriaDTO convertCriteria = new ChallengeCompletionConvertCriteriaDTO();
		List<ChallengeCompletionDTO> challengeCompletionDTOs = this.convertChallengeCompletionsToChallengeCompletionDTOs(challengeCompletionsList,convertCriteria);
		
		ChallengeCompletionPageDTO challengeCompletionPageDTO = new ChallengeCompletionPageDTO();
		challengeCompletionPageDTO.setChallengeCompletions(challengeCompletionDTOs);
		challengeCompletionPageDTO.setTotalElements(challengeCompletions.getTotalElements());
		return ResponseEntity.ok(challengeCompletionPageDTO);
	}

	public List<ChallengeCompletionDTO> convertChallengeCompletionsToChallengeCompletionDTOs(List<ChallengeCompletion> challengeCompletions, ChallengeCompletionConvertCriteriaDTO convertCriteria) {
		
		List<ChallengeCompletionDTO> challengeCompletionDTOs = new ArrayList<ChallengeCompletionDTO>();
		
		for (ChallengeCompletion challengeCompletion : challengeCompletions) {
			challengeCompletionDTOs.add(convertChallengeCompletionToChallengeCompletionDTO(challengeCompletion,convertCriteria));
		}
		
		return challengeCompletionDTOs;

	}
	
	public ChallengeCompletionDTO convertChallengeCompletionToChallengeCompletionDTO(ChallengeCompletion challengeCompletion, ChallengeCompletionConvertCriteriaDTO convertCriteria) {
		
		ChallengeCompletionDTO challengeCompletionDTO = new ChallengeCompletionDTO();
		
		challengeCompletionDTO.setChallengeCompletionId(challengeCompletion.getChallengeCompletionId());

	
		challengeCompletionDTO.setCompletionDate(challengeCompletion.getCompletionDate());

	

		
		return challengeCompletionDTO;
	}

	public ResultDTO updateChallengeCompletion(ChallengeCompletionDTO challengeCompletionDTO, RequestDTO requestDTO) {
		
		ChallengeCompletion challengeCompletion = challengeCompletionDao.getById(challengeCompletionDTO.getChallengeCompletionId());

		challengeCompletion.setChallengeCompletionId(ControllerUtils.setValue(challengeCompletion.getChallengeCompletionId(), challengeCompletionDTO.getChallengeCompletionId()));

		challengeCompletion.setCompletionDate(ControllerUtils.setValue(challengeCompletion.getCompletionDate(), challengeCompletionDTO.getCompletionDate()));



        challengeCompletion = challengeCompletionDao.save(challengeCompletion);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public ChallengeCompletionDTO getChallengeCompletionDTOById(Integer challengeCompletionId) {
	
		ChallengeCompletion challengeCompletion = challengeCompletionDao.getById(challengeCompletionId);
			
		
		ChallengeCompletionConvertCriteriaDTO convertCriteria = new ChallengeCompletionConvertCriteriaDTO();
		return(this.convertChallengeCompletionToChallengeCompletionDTO(challengeCompletion,convertCriteria));
	}







}
