package com.imagegame.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.imagegame.domain.GameSession;
import com.imagegame.dto.GameSessionDTO;
import com.imagegame.dto.GameSessionSearchDTO;
import com.imagegame.dto.GameSessionPageDTO;
import com.imagegame.dto.GameSessionConvertCriteriaDTO;
import com.imagegame.service.GenericService;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface GameSessionService extends GenericService<GameSession, Integer> {

	List<GameSession> findAll();

	ResultDTO addGameSession(GameSessionDTO gameSessionDTO, RequestDTO requestDTO);

	ResultDTO updateGameSession(GameSessionDTO gameSessionDTO, RequestDTO requestDTO);

    Page<GameSession> getAllGameSessions(Pageable pageable);

    Page<GameSession> getAllGameSessions(Specification<GameSession> spec, Pageable pageable);

	ResponseEntity<GameSessionPageDTO> getGameSessions(GameSessionSearchDTO gameSessionSearchDTO);
	
	List<GameSessionDTO> convertGameSessionsToGameSessionDTOs(List<GameSession> gameSessions, GameSessionConvertCriteriaDTO convertCriteria);

	GameSessionDTO getGameSessionDTOById(Integer gameSessionId);







}





