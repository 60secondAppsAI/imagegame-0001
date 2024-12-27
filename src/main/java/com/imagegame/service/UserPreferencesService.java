package com.imagegame.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.imagegame.domain.UserPreferences;
import com.imagegame.dto.UserPreferencesDTO;
import com.imagegame.dto.UserPreferencesSearchDTO;
import com.imagegame.dto.UserPreferencesPageDTO;
import com.imagegame.dto.UserPreferencesConvertCriteriaDTO;
import com.imagegame.service.GenericService;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface UserPreferencesService extends GenericService<UserPreferences, Integer> {

	List<UserPreferences> findAll();

	ResultDTO addUserPreferences(UserPreferencesDTO userPreferencesDTO, RequestDTO requestDTO);

	ResultDTO updateUserPreferences(UserPreferencesDTO userPreferencesDTO, RequestDTO requestDTO);

    Page<UserPreferences> getAllUserPreferencess(Pageable pageable);

    Page<UserPreferences> getAllUserPreferencess(Specification<UserPreferences> spec, Pageable pageable);

	ResponseEntity<UserPreferencesPageDTO> getUserPreferencess(UserPreferencesSearchDTO userPreferencesSearchDTO);
	
	List<UserPreferencesDTO> convertUserPreferencessToUserPreferencesDTOs(List<UserPreferences> userPreferencess, UserPreferencesConvertCriteriaDTO convertCriteria);

	UserPreferencesDTO getUserPreferencesDTOById(Integer userPreferencesId);







}





