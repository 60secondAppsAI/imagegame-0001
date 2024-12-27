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
import com.imagegame.dao.GameSettingsDAO;
import com.imagegame.domain.GameSettings;
import com.imagegame.dto.GameSettingsDTO;
import com.imagegame.dto.GameSettingsSearchDTO;
import com.imagegame.dto.GameSettingsPageDTO;
import com.imagegame.dto.GameSettingsConvertCriteriaDTO;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;
import com.imagegame.service.GameSettingsService;
import com.imagegame.util.ControllerUtils;





@Service
public class GameSettingsServiceImpl extends GenericServiceImpl<GameSettings, Integer> implements GameSettingsService {

    private final static Logger logger = LoggerFactory.getLogger(GameSettingsServiceImpl.class);

	@Autowired
	GameSettingsDAO gameSettingsDao;

	


	@Override
	public GenericDAO<GameSettings, Integer> getDAO() {
		return (GenericDAO<GameSettings, Integer>) gameSettingsDao;
	}
	
	public List<GameSettings> findAll () {
		List<GameSettings> gameSettingss = gameSettingsDao.findAll();
		
		return gameSettingss;	
		
	}

	public ResultDTO addGameSettings(GameSettingsDTO gameSettingsDTO, RequestDTO requestDTO) {

		GameSettings gameSettings = new GameSettings();

		gameSettings.setGameSettingsId(gameSettingsDTO.getGameSettingsId());


		gameSettings.setSoundEnabled(gameSettingsDTO.getSoundEnabled());


		gameSettings.setNotificationsEnabled(gameSettingsDTO.getNotificationsEnabled());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		gameSettings = gameSettingsDao.save(gameSettings);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<GameSettings> getAllGameSettingss(Pageable pageable) {
		return gameSettingsDao.findAll(pageable);
	}

	public Page<GameSettings> getAllGameSettingss(Specification<GameSettings> spec, Pageable pageable) {
		return gameSettingsDao.findAll(spec, pageable);
	}

	public ResponseEntity<GameSettingsPageDTO> getGameSettingss(GameSettingsSearchDTO gameSettingsSearchDTO) {
	
			Integer gameSettingsId = gameSettingsSearchDTO.getGameSettingsId(); 
 			String soundEnabled = gameSettingsSearchDTO.getSoundEnabled(); 
 			String notificationsEnabled = gameSettingsSearchDTO.getNotificationsEnabled(); 
 			String sortBy = gameSettingsSearchDTO.getSortBy();
			String sortOrder = gameSettingsSearchDTO.getSortOrder();
			String searchQuery = gameSettingsSearchDTO.getSearchQuery();
			Integer page = gameSettingsSearchDTO.getPage();
			Integer size = gameSettingsSearchDTO.getSize();

	        Specification<GameSettings> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, gameSettingsId, "gameSettingsId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, soundEnabled, "soundEnabled"); 
			
			spec = ControllerUtils.andIfNecessary(spec, notificationsEnabled, "notificationsEnabled"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("soundEnabled")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("notificationsEnabled")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<GameSettings> gameSettingss = this.getAllGameSettingss(spec, pageable);
		
		//System.out.println(String.valueOf(gameSettingss.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(gameSettingss.getTotalPages()));
		
		List<GameSettings> gameSettingssList = gameSettingss.getContent();
		
		GameSettingsConvertCriteriaDTO convertCriteria = new GameSettingsConvertCriteriaDTO();
		List<GameSettingsDTO> gameSettingsDTOs = this.convertGameSettingssToGameSettingsDTOs(gameSettingssList,convertCriteria);
		
		GameSettingsPageDTO gameSettingsPageDTO = new GameSettingsPageDTO();
		gameSettingsPageDTO.setGameSettingss(gameSettingsDTOs);
		gameSettingsPageDTO.setTotalElements(gameSettingss.getTotalElements());
		return ResponseEntity.ok(gameSettingsPageDTO);
	}

	public List<GameSettingsDTO> convertGameSettingssToGameSettingsDTOs(List<GameSettings> gameSettingss, GameSettingsConvertCriteriaDTO convertCriteria) {
		
		List<GameSettingsDTO> gameSettingsDTOs = new ArrayList<GameSettingsDTO>();
		
		for (GameSettings gameSettings : gameSettingss) {
			gameSettingsDTOs.add(convertGameSettingsToGameSettingsDTO(gameSettings,convertCriteria));
		}
		
		return gameSettingsDTOs;

	}
	
	public GameSettingsDTO convertGameSettingsToGameSettingsDTO(GameSettings gameSettings, GameSettingsConvertCriteriaDTO convertCriteria) {
		
		GameSettingsDTO gameSettingsDTO = new GameSettingsDTO();
		
		gameSettingsDTO.setGameSettingsId(gameSettings.getGameSettingsId());

	
		gameSettingsDTO.setSoundEnabled(gameSettings.getSoundEnabled());

	
		gameSettingsDTO.setNotificationsEnabled(gameSettings.getNotificationsEnabled());

	

		
		return gameSettingsDTO;
	}

	public ResultDTO updateGameSettings(GameSettingsDTO gameSettingsDTO, RequestDTO requestDTO) {
		
		GameSettings gameSettings = gameSettingsDao.getById(gameSettingsDTO.getGameSettingsId());

		gameSettings.setGameSettingsId(ControllerUtils.setValue(gameSettings.getGameSettingsId(), gameSettingsDTO.getGameSettingsId()));

		gameSettings.setSoundEnabled(ControllerUtils.setValue(gameSettings.getSoundEnabled(), gameSettingsDTO.getSoundEnabled()));

		gameSettings.setNotificationsEnabled(ControllerUtils.setValue(gameSettings.getNotificationsEnabled(), gameSettingsDTO.getNotificationsEnabled()));



        gameSettings = gameSettingsDao.save(gameSettings);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public GameSettingsDTO getGameSettingsDTOById(Integer gameSettingsId) {
	
		GameSettings gameSettings = gameSettingsDao.getById(gameSettingsId);
			
		
		GameSettingsConvertCriteriaDTO convertCriteria = new GameSettingsConvertCriteriaDTO();
		return(this.convertGameSettingsToGameSettingsDTO(gameSettings,convertCriteria));
	}







}
