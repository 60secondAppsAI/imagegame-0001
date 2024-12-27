package com.imagegame.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.imagegame.domain.GameHistory;
import com.imagegame.dto.GameHistoryDTO;
import com.imagegame.dto.GameHistorySearchDTO;
import com.imagegame.dto.GameHistoryPageDTO;
import com.imagegame.dto.GameHistoryConvertCriteriaDTO;
import com.imagegame.service.GenericService;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface GameHistoryService extends GenericService<GameHistory, Integer> {

	List<GameHistory> findAll();

	ResultDTO addGameHistory(GameHistoryDTO gameHistoryDTO, RequestDTO requestDTO);

	ResultDTO updateGameHistory(GameHistoryDTO gameHistoryDTO, RequestDTO requestDTO);

    Page<GameHistory> getAllGameHistorys(Pageable pageable);

    Page<GameHistory> getAllGameHistorys(Specification<GameHistory> spec, Pageable pageable);

	ResponseEntity<GameHistoryPageDTO> getGameHistorys(GameHistorySearchDTO gameHistorySearchDTO);
	
	List<GameHistoryDTO> convertGameHistorysToGameHistoryDTOs(List<GameHistory> gameHistorys, GameHistoryConvertCriteriaDTO convertCriteria);

	GameHistoryDTO getGameHistoryDTOById(Integer gameHistoryId);







}





