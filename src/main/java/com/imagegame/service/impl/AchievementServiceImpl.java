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
import com.imagegame.dao.AchievementDAO;
import com.imagegame.domain.Achievement;
import com.imagegame.dto.AchievementDTO;
import com.imagegame.dto.AchievementSearchDTO;
import com.imagegame.dto.AchievementPageDTO;
import com.imagegame.dto.AchievementConvertCriteriaDTO;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;
import com.imagegame.service.AchievementService;
import com.imagegame.util.ControllerUtils;





@Service
public class AchievementServiceImpl extends GenericServiceImpl<Achievement, Integer> implements AchievementService {

    private final static Logger logger = LoggerFactory.getLogger(AchievementServiceImpl.class);

	@Autowired
	AchievementDAO achievementDao;

	


	@Override
	public GenericDAO<Achievement, Integer> getDAO() {
		return (GenericDAO<Achievement, Integer>) achievementDao;
	}
	
	public List<Achievement> findAll () {
		List<Achievement> achievements = achievementDao.findAll();
		
		return achievements;	
		
	}

	public ResultDTO addAchievement(AchievementDTO achievementDTO, RequestDTO requestDTO) {

		Achievement achievement = new Achievement();

		achievement.setAchievementId(achievementDTO.getAchievementId());


		achievement.setTitle(achievementDTO.getTitle());


		achievement.setDescription(achievementDTO.getDescription());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		achievement = achievementDao.save(achievement);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Achievement> getAllAchievements(Pageable pageable) {
		return achievementDao.findAll(pageable);
	}

	public Page<Achievement> getAllAchievements(Specification<Achievement> spec, Pageable pageable) {
		return achievementDao.findAll(spec, pageable);
	}

	public ResponseEntity<AchievementPageDTO> getAchievements(AchievementSearchDTO achievementSearchDTO) {
	
			Integer achievementId = achievementSearchDTO.getAchievementId(); 
 			String title = achievementSearchDTO.getTitle(); 
 			String description = achievementSearchDTO.getDescription(); 
 			String sortBy = achievementSearchDTO.getSortBy();
			String sortOrder = achievementSearchDTO.getSortOrder();
			String searchQuery = achievementSearchDTO.getSearchQuery();
			Integer page = achievementSearchDTO.getPage();
			Integer size = achievementSearchDTO.getSize();

	        Specification<Achievement> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, achievementId, "achievementId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, title, "title"); 
			
			spec = ControllerUtils.andIfNecessary(spec, description, "description"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("title")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("description")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Achievement> achievements = this.getAllAchievements(spec, pageable);
		
		//System.out.println(String.valueOf(achievements.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(achievements.getTotalPages()));
		
		List<Achievement> achievementsList = achievements.getContent();
		
		AchievementConvertCriteriaDTO convertCriteria = new AchievementConvertCriteriaDTO();
		List<AchievementDTO> achievementDTOs = this.convertAchievementsToAchievementDTOs(achievementsList,convertCriteria);
		
		AchievementPageDTO achievementPageDTO = new AchievementPageDTO();
		achievementPageDTO.setAchievements(achievementDTOs);
		achievementPageDTO.setTotalElements(achievements.getTotalElements());
		return ResponseEntity.ok(achievementPageDTO);
	}

	public List<AchievementDTO> convertAchievementsToAchievementDTOs(List<Achievement> achievements, AchievementConvertCriteriaDTO convertCriteria) {
		
		List<AchievementDTO> achievementDTOs = new ArrayList<AchievementDTO>();
		
		for (Achievement achievement : achievements) {
			achievementDTOs.add(convertAchievementToAchievementDTO(achievement,convertCriteria));
		}
		
		return achievementDTOs;

	}
	
	public AchievementDTO convertAchievementToAchievementDTO(Achievement achievement, AchievementConvertCriteriaDTO convertCriteria) {
		
		AchievementDTO achievementDTO = new AchievementDTO();
		
		achievementDTO.setAchievementId(achievement.getAchievementId());

	
		achievementDTO.setTitle(achievement.getTitle());

	
		achievementDTO.setDescription(achievement.getDescription());

	

		
		return achievementDTO;
	}

	public ResultDTO updateAchievement(AchievementDTO achievementDTO, RequestDTO requestDTO) {
		
		Achievement achievement = achievementDao.getById(achievementDTO.getAchievementId());

		achievement.setAchievementId(ControllerUtils.setValue(achievement.getAchievementId(), achievementDTO.getAchievementId()));

		achievement.setTitle(ControllerUtils.setValue(achievement.getTitle(), achievementDTO.getTitle()));

		achievement.setDescription(ControllerUtils.setValue(achievement.getDescription(), achievementDTO.getDescription()));



        achievement = achievementDao.save(achievement);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public AchievementDTO getAchievementDTOById(Integer achievementId) {
	
		Achievement achievement = achievementDao.getById(achievementId);
			
		
		AchievementConvertCriteriaDTO convertCriteria = new AchievementConvertCriteriaDTO();
		return(this.convertAchievementToAchievementDTO(achievement,convertCriteria));
	}







}
