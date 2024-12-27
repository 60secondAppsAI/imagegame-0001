package com.imagegame.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.imagegame.domain.Badge;
import com.imagegame.dto.BadgeDTO;
import com.imagegame.dto.BadgeSearchDTO;
import com.imagegame.dto.BadgePageDTO;
import com.imagegame.dto.BadgeConvertCriteriaDTO;
import com.imagegame.service.GenericService;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface BadgeService extends GenericService<Badge, Integer> {

	List<Badge> findAll();

	ResultDTO addBadge(BadgeDTO badgeDTO, RequestDTO requestDTO);

	ResultDTO updateBadge(BadgeDTO badgeDTO, RequestDTO requestDTO);

    Page<Badge> getAllBadges(Pageable pageable);

    Page<Badge> getAllBadges(Specification<Badge> spec, Pageable pageable);

	ResponseEntity<BadgePageDTO> getBadges(BadgeSearchDTO badgeSearchDTO);
	
	List<BadgeDTO> convertBadgesToBadgeDTOs(List<Badge> badges, BadgeConvertCriteriaDTO convertCriteria);

	BadgeDTO getBadgeDTOById(Integer badgeId);







}





