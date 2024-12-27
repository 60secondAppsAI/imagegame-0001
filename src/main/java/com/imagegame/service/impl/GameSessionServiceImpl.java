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
import com.imagegame.dao.GameSessionDAO;
import com.imagegame.domain.GameSession;
import com.imagegame.dto.GameSessionDTO;
import com.imagegame.dto.GameSessionSearchDTO;
import com.imagegame.dto.GameSessionPageDTO;
import com.imagegame.dto.GameSessionConvertCriteriaDTO;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;
import com.imagegame.service.GameSessionService;
import com.imagegame.util.ControllerUtils;





@Service
public class GameSessionServiceImpl extends GenericServiceImpl<GameSession, Integer> implements GameSessionService {

    private final static Logger logger = LoggerFactory.getLogger(GameSessionServiceImpl.class);

	@Autowired
	GameSessionDAO gameSessionDao;

	


	@Override
	public GenericDAO<GameSession, Integer> getDAO() {
		return (GenericDAO<GameSession, Integer>) gameSessionDao;
	}
	
	public List<GameSession> findAll () {
		List<GameSession> gameSessions = gameSessionDao.findAll();
		
		return gameSessions;	
		
	}

	public ResultDTO addGameSession(GameSessionDTO gameSessionDTO, RequestDTO requestDTO) {

		GameSession gameSession = new GameSession();

		gameSession.setGameSessionId(gameSessionDTO.getGameSessionId());


		gameSession.setPlayerId(gameSessionDTO.getPlayerId());


		gameSession.setStartTime(gameSessionDTO.getStartTime());


		gameSession.setEndTime(gameSessionDTO.getEndTime());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		gameSession = gameSessionDao.save(gameSession);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<GameSession> getAllGameSessions(Pageable pageable) {
		return gameSessionDao.findAll(pageable);
	}

	public Page<GameSession> getAllGameSessions(Specification<GameSession> spec, Pageable pageable) {
		return gameSessionDao.findAll(spec, pageable);
	}

	public ResponseEntity<GameSessionPageDTO> getGameSessions(GameSessionSearchDTO gameSessionSearchDTO) {
	
			Integer gameSessionId = gameSessionSearchDTO.getGameSessionId(); 
 			Integer playerId = gameSessionSearchDTO.getPlayerId(); 
     			String sortBy = gameSessionSearchDTO.getSortBy();
			String sortOrder = gameSessionSearchDTO.getSortOrder();
			String searchQuery = gameSessionSearchDTO.getSearchQuery();
			Integer page = gameSessionSearchDTO.getPage();
			Integer size = gameSessionSearchDTO.getSize();

	        Specification<GameSession> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, gameSessionId, "gameSessionId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, playerId, "playerId"); 
			
 			
 			

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

		Page<GameSession> gameSessions = this.getAllGameSessions(spec, pageable);
		
		//System.out.println(String.valueOf(gameSessions.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(gameSessions.getTotalPages()));
		
		List<GameSession> gameSessionsList = gameSessions.getContent();
		
		GameSessionConvertCriteriaDTO convertCriteria = new GameSessionConvertCriteriaDTO();
		List<GameSessionDTO> gameSessionDTOs = this.convertGameSessionsToGameSessionDTOs(gameSessionsList,convertCriteria);
		
		GameSessionPageDTO gameSessionPageDTO = new GameSessionPageDTO();
		gameSessionPageDTO.setGameSessions(gameSessionDTOs);
		gameSessionPageDTO.setTotalElements(gameSessions.getTotalElements());
		return ResponseEntity.ok(gameSessionPageDTO);
	}

	public List<GameSessionDTO> convertGameSessionsToGameSessionDTOs(List<GameSession> gameSessions, GameSessionConvertCriteriaDTO convertCriteria) {
		
		List<GameSessionDTO> gameSessionDTOs = new ArrayList<GameSessionDTO>();
		
		for (GameSession gameSession : gameSessions) {
			gameSessionDTOs.add(convertGameSessionToGameSessionDTO(gameSession,convertCriteria));
		}
		
		return gameSessionDTOs;

	}
	
	public GameSessionDTO convertGameSessionToGameSessionDTO(GameSession gameSession, GameSessionConvertCriteriaDTO convertCriteria) {
		
		GameSessionDTO gameSessionDTO = new GameSessionDTO();
		
		gameSessionDTO.setGameSessionId(gameSession.getGameSessionId());

	
		gameSessionDTO.setPlayerId(gameSession.getPlayerId());

	
		gameSessionDTO.setStartTime(gameSession.getStartTime());

	
		gameSessionDTO.setEndTime(gameSession.getEndTime());

	

		
		return gameSessionDTO;
	}

	public ResultDTO updateGameSession(GameSessionDTO gameSessionDTO, RequestDTO requestDTO) {
		
		GameSession gameSession = gameSessionDao.getById(gameSessionDTO.getGameSessionId());

		gameSession.setGameSessionId(ControllerUtils.setValue(gameSession.getGameSessionId(), gameSessionDTO.getGameSessionId()));

		gameSession.setPlayerId(ControllerUtils.setValue(gameSession.getPlayerId(), gameSessionDTO.getPlayerId()));

		gameSession.setStartTime(ControllerUtils.setValue(gameSession.getStartTime(), gameSessionDTO.getStartTime()));

		gameSession.setEndTime(ControllerUtils.setValue(gameSession.getEndTime(), gameSessionDTO.getEndTime()));



        gameSession = gameSessionDao.save(gameSession);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public GameSessionDTO getGameSessionDTOById(Integer gameSessionId) {
	
		GameSession gameSession = gameSessionDao.getById(gameSessionId);
			
		
		GameSessionConvertCriteriaDTO convertCriteria = new GameSessionConvertCriteriaDTO();
		return(this.convertGameSessionToGameSessionDTO(gameSession,convertCriteria));
	}







}
