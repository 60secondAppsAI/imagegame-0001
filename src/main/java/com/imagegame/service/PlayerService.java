package com.imagegame.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.imagegame.domain.Player;
import com.imagegame.dto.PlayerDTO;
import com.imagegame.dto.PlayerSearchDTO;
import com.imagegame.dto.PlayerPageDTO;
import com.imagegame.dto.PlayerConvertCriteriaDTO;
import com.imagegame.service.GenericService;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface PlayerService extends GenericService<Player, Integer> {

	List<Player> findAll();

	ResultDTO addPlayer(PlayerDTO playerDTO, RequestDTO requestDTO);

	ResultDTO updatePlayer(PlayerDTO playerDTO, RequestDTO requestDTO);

    Page<Player> getAllPlayers(Pageable pageable);

    Page<Player> getAllPlayers(Specification<Player> spec, Pageable pageable);

	ResponseEntity<PlayerPageDTO> getPlayers(PlayerSearchDTO playerSearchDTO);
	
	List<PlayerDTO> convertPlayersToPlayerDTOs(List<Player> players, PlayerConvertCriteriaDTO convertCriteria);

	PlayerDTO getPlayerDTOById(Integer playerId);







}





