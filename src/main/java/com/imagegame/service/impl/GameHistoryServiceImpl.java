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
import com.imagegame.dao.GameHistoryDAO;
import com.imagegame.domain.GameHistory;
import com.imagegame.dto.GameHistoryDTO;
import com.imagegame.dto.GameHistorySearchDTO;
import com.imagegame.dto.GameHistoryPageDTO;
import com.imagegame.dto.GameHistoryConvertCriteriaDTO;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;
import com.imagegame.service.GameHistoryService;
import com.imagegame.util.ControllerUtils;





@Service
public class GameHistoryServiceImpl extends GenericServiceImpl<GameHistory, Integer> implements GameHistoryService {

    private final static Logger logger = LoggerFactory.getLogger(GameHistoryServiceImpl.class);

	@Autowired
	GameHistoryDAO gameHistoryDao;

	


	@Override
	public GenericDAO<GameHistory, Integer> getDAO() {
		return (GenericDAO<GameHistory, Integer>) gameHistoryDao;
	}
	
	public List<GameHistory> findAll () {
		List<GameHistory> gameHistorys = gameHistoryDao.findAll();
		
		return gameHistorys;	
		
	}

	public ResultDTO addGameHistory(GameHistoryDTO gameHistoryDTO, RequestDTO requestDTO) {

		GameHistory gameHistory = new GameHistory();

		gameHistory.setGameHistoryId(gameHistoryDTO.getGameHistoryId());


		gameHistory.setOutcome(gameHistoryDTO.getOutcome());


		gameHistory.setScore(gameHistoryDTO.getScore());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		gameHistory = gameHistoryDao.save(gameHistory);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<GameHistory> getAllGameHistorys(Pageable pageable) {
		return gameHistoryDao.findAll(pageable);
	}

	public Page<GameHistory> getAllGameHistorys(Specification<GameHistory> spec, Pageable pageable) {
		return gameHistoryDao.findAll(spec, pageable);
	}

	public ResponseEntity<GameHistoryPageDTO> getGameHistorys(GameHistorySearchDTO gameHistorySearchDTO) {
	
			Integer gameHistoryId = gameHistorySearchDTO.getGameHistoryId(); 
 			String outcome = gameHistorySearchDTO.getOutcome(); 
 			Integer score = gameHistorySearchDTO.getScore(); 
 			String sortBy = gameHistorySearchDTO.getSortBy();
			String sortOrder = gameHistorySearchDTO.getSortOrder();
			String searchQuery = gameHistorySearchDTO.getSearchQuery();
			Integer page = gameHistorySearchDTO.getPage();
			Integer size = gameHistorySearchDTO.getSize();

	        Specification<GameHistory> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, gameHistoryId, "gameHistoryId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, outcome, "outcome"); 
			
			spec = ControllerUtils.andIfNecessary(spec, score, "score"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("outcome")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<GameHistory> gameHistorys = this.getAllGameHistorys(spec, pageable);
		
		//System.out.println(String.valueOf(gameHistorys.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(gameHistorys.getTotalPages()));
		
		List<GameHistory> gameHistorysList = gameHistorys.getContent();
		
		GameHistoryConvertCriteriaDTO convertCriteria = new GameHistoryConvertCriteriaDTO();
		List<GameHistoryDTO> gameHistoryDTOs = this.convertGameHistorysToGameHistoryDTOs(gameHistorysList,convertCriteria);
		
		GameHistoryPageDTO gameHistoryPageDTO = new GameHistoryPageDTO();
		gameHistoryPageDTO.setGameHistorys(gameHistoryDTOs);
		gameHistoryPageDTO.setTotalElements(gameHistorys.getTotalElements());
		return ResponseEntity.ok(gameHistoryPageDTO);
	}

	public List<GameHistoryDTO> convertGameHistorysToGameHistoryDTOs(List<GameHistory> gameHistorys, GameHistoryConvertCriteriaDTO convertCriteria) {
		
		List<GameHistoryDTO> gameHistoryDTOs = new ArrayList<GameHistoryDTO>();
		
		for (GameHistory gameHistory : gameHistorys) {
			gameHistoryDTOs.add(convertGameHistoryToGameHistoryDTO(gameHistory,convertCriteria));
		}
		
		return gameHistoryDTOs;

	}
	
	public GameHistoryDTO convertGameHistoryToGameHistoryDTO(GameHistory gameHistory, GameHistoryConvertCriteriaDTO convertCriteria) {
		
		GameHistoryDTO gameHistoryDTO = new GameHistoryDTO();
		
		gameHistoryDTO.setGameHistoryId(gameHistory.getGameHistoryId());

	
		gameHistoryDTO.setOutcome(gameHistory.getOutcome());

	
		gameHistoryDTO.setScore(gameHistory.getScore());

	

		
		return gameHistoryDTO;
	}

	public ResultDTO updateGameHistory(GameHistoryDTO gameHistoryDTO, RequestDTO requestDTO) {
		
		GameHistory gameHistory = gameHistoryDao.getById(gameHistoryDTO.getGameHistoryId());

		gameHistory.setGameHistoryId(ControllerUtils.setValue(gameHistory.getGameHistoryId(), gameHistoryDTO.getGameHistoryId()));

		gameHistory.setOutcome(ControllerUtils.setValue(gameHistory.getOutcome(), gameHistoryDTO.getOutcome()));

		gameHistory.setScore(ControllerUtils.setValue(gameHistory.getScore(), gameHistoryDTO.getScore()));



        gameHistory = gameHistoryDao.save(gameHistory);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public GameHistoryDTO getGameHistoryDTOById(Integer gameHistoryId) {
	
		GameHistory gameHistory = gameHistoryDao.getById(gameHistoryId);
			
		
		GameHistoryConvertCriteriaDTO convertCriteria = new GameHistoryConvertCriteriaDTO();
		return(this.convertGameHistoryToGameHistoryDTO(gameHistory,convertCriteria));
	}







}
