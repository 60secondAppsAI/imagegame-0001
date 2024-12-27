package com.imagegame.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.imagegame.domain.UserBadge;
import com.imagegame.dto.UserBadgeDTO;
import com.imagegame.dto.UserBadgeSearchDTO;
import com.imagegame.dto.UserBadgePageDTO;
import com.imagegame.dto.UserBadgeConvertCriteriaDTO;
import com.imagegame.service.GenericService;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface UserBadgeService extends GenericService<UserBadge, Integer> {

	List<UserBadge> findAll();

	ResultDTO addUserBadge(UserBadgeDTO userBadgeDTO, RequestDTO requestDTO);

	ResultDTO updateUserBadge(UserBadgeDTO userBadgeDTO, RequestDTO requestDTO);

    Page<UserBadge> getAllUserBadges(Pageable pageable);

    Page<UserBadge> getAllUserBadges(Specification<UserBadge> spec, Pageable pageable);

	ResponseEntity<UserBadgePageDTO> getUserBadges(UserBadgeSearchDTO userBadgeSearchDTO);
	
	List<UserBadgeDTO> convertUserBadgesToUserBadgeDTOs(List<UserBadge> userBadges, UserBadgeConvertCriteriaDTO convertCriteria);

	UserBadgeDTO getUserBadgeDTOById(Integer userBadgeId);







}





