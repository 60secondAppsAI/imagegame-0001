package com.imagegame.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.imagegame.domain.Achievement;
import com.imagegame.dto.AchievementDTO;
import com.imagegame.dto.AchievementSearchDTO;
import com.imagegame.dto.AchievementPageDTO;
import com.imagegame.dto.AchievementConvertCriteriaDTO;
import com.imagegame.service.GenericService;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface AchievementService extends GenericService<Achievement, Integer> {

	List<Achievement> findAll();

	ResultDTO addAchievement(AchievementDTO achievementDTO, RequestDTO requestDTO);

	ResultDTO updateAchievement(AchievementDTO achievementDTO, RequestDTO requestDTO);

    Page<Achievement> getAllAchievements(Pageable pageable);

    Page<Achievement> getAllAchievements(Specification<Achievement> spec, Pageable pageable);

	ResponseEntity<AchievementPageDTO> getAchievements(AchievementSearchDTO achievementSearchDTO);
	
	List<AchievementDTO> convertAchievementsToAchievementDTOs(List<Achievement> achievements, AchievementConvertCriteriaDTO convertCriteria);

	AchievementDTO getAchievementDTOById(Integer achievementId);







}





