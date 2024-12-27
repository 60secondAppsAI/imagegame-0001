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
import com.imagegame.dao.UserBadgeDAO;
import com.imagegame.domain.UserBadge;
import com.imagegame.dto.UserBadgeDTO;
import com.imagegame.dto.UserBadgeSearchDTO;
import com.imagegame.dto.UserBadgePageDTO;
import com.imagegame.dto.UserBadgeConvertCriteriaDTO;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;
import com.imagegame.service.UserBadgeService;
import com.imagegame.util.ControllerUtils;





@Service
public class UserBadgeServiceImpl extends GenericServiceImpl<UserBadge, Integer> implements UserBadgeService {

    private final static Logger logger = LoggerFactory.getLogger(UserBadgeServiceImpl.class);

	@Autowired
	UserBadgeDAO userBadgeDao;

	


	@Override
	public GenericDAO<UserBadge, Integer> getDAO() {
		return (GenericDAO<UserBadge, Integer>) userBadgeDao;
	}
	
	public List<UserBadge> findAll () {
		List<UserBadge> userBadges = userBadgeDao.findAll();
		
		return userBadges;	
		
	}

	public ResultDTO addUserBadge(UserBadgeDTO userBadgeDTO, RequestDTO requestDTO) {

		UserBadge userBadge = new UserBadge();

		userBadge.setUserBadgeId(userBadgeDTO.getUserBadgeId());


		userBadge.setAwardedDate(userBadgeDTO.getAwardedDate());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		userBadge = userBadgeDao.save(userBadge);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<UserBadge> getAllUserBadges(Pageable pageable) {
		return userBadgeDao.findAll(pageable);
	}

	public Page<UserBadge> getAllUserBadges(Specification<UserBadge> spec, Pageable pageable) {
		return userBadgeDao.findAll(spec, pageable);
	}

	public ResponseEntity<UserBadgePageDTO> getUserBadges(UserBadgeSearchDTO userBadgeSearchDTO) {
	
			Integer userBadgeId = userBadgeSearchDTO.getUserBadgeId(); 
   			String sortBy = userBadgeSearchDTO.getSortBy();
			String sortOrder = userBadgeSearchDTO.getSortOrder();
			String searchQuery = userBadgeSearchDTO.getSearchQuery();
			Integer page = userBadgeSearchDTO.getPage();
			Integer size = userBadgeSearchDTO.getSize();

	        Specification<UserBadge> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, userBadgeId, "userBadgeId"); 
			
 			

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

		Page<UserBadge> userBadges = this.getAllUserBadges(spec, pageable);
		
		//System.out.println(String.valueOf(userBadges.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(userBadges.getTotalPages()));
		
		List<UserBadge> userBadgesList = userBadges.getContent();
		
		UserBadgeConvertCriteriaDTO convertCriteria = new UserBadgeConvertCriteriaDTO();
		List<UserBadgeDTO> userBadgeDTOs = this.convertUserBadgesToUserBadgeDTOs(userBadgesList,convertCriteria);
		
		UserBadgePageDTO userBadgePageDTO = new UserBadgePageDTO();
		userBadgePageDTO.setUserBadges(userBadgeDTOs);
		userBadgePageDTO.setTotalElements(userBadges.getTotalElements());
		return ResponseEntity.ok(userBadgePageDTO);
	}

	public List<UserBadgeDTO> convertUserBadgesToUserBadgeDTOs(List<UserBadge> userBadges, UserBadgeConvertCriteriaDTO convertCriteria) {
		
		List<UserBadgeDTO> userBadgeDTOs = new ArrayList<UserBadgeDTO>();
		
		for (UserBadge userBadge : userBadges) {
			userBadgeDTOs.add(convertUserBadgeToUserBadgeDTO(userBadge,convertCriteria));
		}
		
		return userBadgeDTOs;

	}
	
	public UserBadgeDTO convertUserBadgeToUserBadgeDTO(UserBadge userBadge, UserBadgeConvertCriteriaDTO convertCriteria) {
		
		UserBadgeDTO userBadgeDTO = new UserBadgeDTO();
		
		userBadgeDTO.setUserBadgeId(userBadge.getUserBadgeId());

	
		userBadgeDTO.setAwardedDate(userBadge.getAwardedDate());

	

		
		return userBadgeDTO;
	}

	public ResultDTO updateUserBadge(UserBadgeDTO userBadgeDTO, RequestDTO requestDTO) {
		
		UserBadge userBadge = userBadgeDao.getById(userBadgeDTO.getUserBadgeId());

		userBadge.setUserBadgeId(ControllerUtils.setValue(userBadge.getUserBadgeId(), userBadgeDTO.getUserBadgeId()));

		userBadge.setAwardedDate(ControllerUtils.setValue(userBadge.getAwardedDate(), userBadgeDTO.getAwardedDate()));



        userBadge = userBadgeDao.save(userBadge);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public UserBadgeDTO getUserBadgeDTOById(Integer userBadgeId) {
	
		UserBadge userBadge = userBadgeDao.getById(userBadgeId);
			
		
		UserBadgeConvertCriteriaDTO convertCriteria = new UserBadgeConvertCriteriaDTO();
		return(this.convertUserBadgeToUserBadgeDTO(userBadge,convertCriteria));
	}







}
