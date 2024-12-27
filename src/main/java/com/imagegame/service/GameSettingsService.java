package com.imagegame.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.imagegame.domain.GameSettings;
import com.imagegame.dto.GameSettingsDTO;
import com.imagegame.dto.GameSettingsSearchDTO;
import com.imagegame.dto.GameSettingsPageDTO;
import com.imagegame.dto.GameSettingsConvertCriteriaDTO;
import com.imagegame.service.GenericService;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface GameSettingsService extends GenericService<GameSettings, Integer> {

	List<GameSettings> findAll();

	ResultDTO addGameSettings(GameSettingsDTO gameSettingsDTO, RequestDTO requestDTO);

	ResultDTO updateGameSettings(GameSettingsDTO gameSettingsDTO, RequestDTO requestDTO);

    Page<GameSettings> getAllGameSettingss(Pageable pageable);

    Page<GameSettings> getAllGameSettingss(Specification<GameSettings> spec, Pageable pageable);

	ResponseEntity<GameSettingsPageDTO> getGameSettingss(GameSettingsSearchDTO gameSettingsSearchDTO);
	
	List<GameSettingsDTO> convertGameSettingssToGameSettingsDTOs(List<GameSettings> gameSettingss, GameSettingsConvertCriteriaDTO convertCriteria);

	GameSettingsDTO getGameSettingsDTOById(Integer gameSettingsId);







}





