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
import com.imagegame.dao.UserPreferencesDAO;
import com.imagegame.domain.UserPreferences;
import com.imagegame.dto.UserPreferencesDTO;
import com.imagegame.dto.UserPreferencesSearchDTO;
import com.imagegame.dto.UserPreferencesPageDTO;
import com.imagegame.dto.UserPreferencesConvertCriteriaDTO;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;
import com.imagegame.service.UserPreferencesService;
import com.imagegame.util.ControllerUtils;





@Service
public class UserPreferencesServiceImpl extends GenericServiceImpl<UserPreferences, Integer> implements UserPreferencesService {

    private final static Logger logger = LoggerFactory.getLogger(UserPreferencesServiceImpl.class);

	@Autowired
	UserPreferencesDAO userPreferencesDao;

	


	@Override
	public GenericDAO<UserPreferences, Integer> getDAO() {
		return (GenericDAO<UserPreferences, Integer>) userPreferencesDao;
	}
	
	public List<UserPreferences> findAll () {
		List<UserPreferences> userPreferencess = userPreferencesDao.findAll();
		
		return userPreferencess;	
		
	}

	public ResultDTO addUserPreferences(UserPreferencesDTO userPreferencesDTO, RequestDTO requestDTO) {

		UserPreferences userPreferences = new UserPreferences();

		userPreferences.setUserPreferencesId(userPreferencesDTO.getUserPreferencesId());


		userPreferences.setPreferredTheme(userPreferencesDTO.getPreferredTheme());


		userPreferences.setLanguage(userPreferencesDTO.getLanguage());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		userPreferences = userPreferencesDao.save(userPreferences);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<UserPreferences> getAllUserPreferencess(Pageable pageable) {
		return userPreferencesDao.findAll(pageable);
	}

	public Page<UserPreferences> getAllUserPreferencess(Specification<UserPreferences> spec, Pageable pageable) {
		return userPreferencesDao.findAll(spec, pageable);
	}

	public ResponseEntity<UserPreferencesPageDTO> getUserPreferencess(UserPreferencesSearchDTO userPreferencesSearchDTO) {
	
			Integer userPreferencesId = userPreferencesSearchDTO.getUserPreferencesId(); 
 			String preferredTheme = userPreferencesSearchDTO.getPreferredTheme(); 
 			String language = userPreferencesSearchDTO.getLanguage(); 
 			String sortBy = userPreferencesSearchDTO.getSortBy();
			String sortOrder = userPreferencesSearchDTO.getSortOrder();
			String searchQuery = userPreferencesSearchDTO.getSearchQuery();
			Integer page = userPreferencesSearchDTO.getPage();
			Integer size = userPreferencesSearchDTO.getSize();

	        Specification<UserPreferences> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, userPreferencesId, "userPreferencesId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, preferredTheme, "preferredTheme"); 
			
			spec = ControllerUtils.andIfNecessary(spec, language, "language"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("preferredTheme")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("language")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<UserPreferences> userPreferencess = this.getAllUserPreferencess(spec, pageable);
		
		//System.out.println(String.valueOf(userPreferencess.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(userPreferencess.getTotalPages()));
		
		List<UserPreferences> userPreferencessList = userPreferencess.getContent();
		
		UserPreferencesConvertCriteriaDTO convertCriteria = new UserPreferencesConvertCriteriaDTO();
		List<UserPreferencesDTO> userPreferencesDTOs = this.convertUserPreferencessToUserPreferencesDTOs(userPreferencessList,convertCriteria);
		
		UserPreferencesPageDTO userPreferencesPageDTO = new UserPreferencesPageDTO();
		userPreferencesPageDTO.setUserPreferencess(userPreferencesDTOs);
		userPreferencesPageDTO.setTotalElements(userPreferencess.getTotalElements());
		return ResponseEntity.ok(userPreferencesPageDTO);
	}

	public List<UserPreferencesDTO> convertUserPreferencessToUserPreferencesDTOs(List<UserPreferences> userPreferencess, UserPreferencesConvertCriteriaDTO convertCriteria) {
		
		List<UserPreferencesDTO> userPreferencesDTOs = new ArrayList<UserPreferencesDTO>();
		
		for (UserPreferences userPreferences : userPreferencess) {
			userPreferencesDTOs.add(convertUserPreferencesToUserPreferencesDTO(userPreferences,convertCriteria));
		}
		
		return userPreferencesDTOs;

	}
	
	public UserPreferencesDTO convertUserPreferencesToUserPreferencesDTO(UserPreferences userPreferences, UserPreferencesConvertCriteriaDTO convertCriteria) {
		
		UserPreferencesDTO userPreferencesDTO = new UserPreferencesDTO();
		
		userPreferencesDTO.setUserPreferencesId(userPreferences.getUserPreferencesId());

	
		userPreferencesDTO.setPreferredTheme(userPreferences.getPreferredTheme());

	
		userPreferencesDTO.setLanguage(userPreferences.getLanguage());

	

		
		return userPreferencesDTO;
	}

	public ResultDTO updateUserPreferences(UserPreferencesDTO userPreferencesDTO, RequestDTO requestDTO) {
		
		UserPreferences userPreferences = userPreferencesDao.getById(userPreferencesDTO.getUserPreferencesId());

		userPreferences.setUserPreferencesId(ControllerUtils.setValue(userPreferences.getUserPreferencesId(), userPreferencesDTO.getUserPreferencesId()));

		userPreferences.setPreferredTheme(ControllerUtils.setValue(userPreferences.getPreferredTheme(), userPreferencesDTO.getPreferredTheme()));

		userPreferences.setLanguage(ControllerUtils.setValue(userPreferences.getLanguage(), userPreferencesDTO.getLanguage()));



        userPreferences = userPreferencesDao.save(userPreferences);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public UserPreferencesDTO getUserPreferencesDTOById(Integer userPreferencesId) {
	
		UserPreferences userPreferences = userPreferencesDao.getById(userPreferencesId);
			
		
		UserPreferencesConvertCriteriaDTO convertCriteria = new UserPreferencesConvertCriteriaDTO();
		return(this.convertUserPreferencesToUserPreferencesDTO(userPreferences,convertCriteria));
	}







}
