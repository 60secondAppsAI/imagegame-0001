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
import com.imagegame.dao.PlayerDAO;
import com.imagegame.domain.Player;
import com.imagegame.dto.PlayerDTO;
import com.imagegame.dto.PlayerSearchDTO;
import com.imagegame.dto.PlayerPageDTO;
import com.imagegame.dto.PlayerConvertCriteriaDTO;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;
import com.imagegame.service.PlayerService;
import com.imagegame.util.ControllerUtils;





@Service
public class PlayerServiceImpl extends GenericServiceImpl<Player, Integer> implements PlayerService {

    private final static Logger logger = LoggerFactory.getLogger(PlayerServiceImpl.class);

	@Autowired
	PlayerDAO playerDao;

	


	@Override
	public GenericDAO<Player, Integer> getDAO() {
		return (GenericDAO<Player, Integer>) playerDao;
	}
	
	public List<Player> findAll () {
		List<Player> players = playerDao.findAll();
		
		return players;	
		
	}

	public ResultDTO addPlayer(PlayerDTO playerDTO, RequestDTO requestDTO) {

		Player player = new Player();

		player.setPlayerId(playerDTO.getPlayerId());


		player.setUsername(playerDTO.getUsername());


		player.setEmail(playerDTO.getEmail());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		player = playerDao.save(player);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Player> getAllPlayers(Pageable pageable) {
		return playerDao.findAll(pageable);
	}

	public Page<Player> getAllPlayers(Specification<Player> spec, Pageable pageable) {
		return playerDao.findAll(spec, pageable);
	}

	public ResponseEntity<PlayerPageDTO> getPlayers(PlayerSearchDTO playerSearchDTO) {
	
			Integer playerId = playerSearchDTO.getPlayerId(); 
 			String username = playerSearchDTO.getUsername(); 
 			String email = playerSearchDTO.getEmail(); 
 			String sortBy = playerSearchDTO.getSortBy();
			String sortOrder = playerSearchDTO.getSortOrder();
			String searchQuery = playerSearchDTO.getSearchQuery();
			Integer page = playerSearchDTO.getPage();
			Integer size = playerSearchDTO.getSize();

	        Specification<Player> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, playerId, "playerId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, username, "username"); 
			
			spec = ControllerUtils.andIfNecessary(spec, email, "email"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("username")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("email")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Player> players = this.getAllPlayers(spec, pageable);
		
		//System.out.println(String.valueOf(players.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(players.getTotalPages()));
		
		List<Player> playersList = players.getContent();
		
		PlayerConvertCriteriaDTO convertCriteria = new PlayerConvertCriteriaDTO();
		List<PlayerDTO> playerDTOs = this.convertPlayersToPlayerDTOs(playersList,convertCriteria);
		
		PlayerPageDTO playerPageDTO = new PlayerPageDTO();
		playerPageDTO.setPlayers(playerDTOs);
		playerPageDTO.setTotalElements(players.getTotalElements());
		return ResponseEntity.ok(playerPageDTO);
	}

	public List<PlayerDTO> convertPlayersToPlayerDTOs(List<Player> players, PlayerConvertCriteriaDTO convertCriteria) {
		
		List<PlayerDTO> playerDTOs = new ArrayList<PlayerDTO>();
		
		for (Player player : players) {
			playerDTOs.add(convertPlayerToPlayerDTO(player,convertCriteria));
		}
		
		return playerDTOs;

	}
	
	public PlayerDTO convertPlayerToPlayerDTO(Player player, PlayerConvertCriteriaDTO convertCriteria) {
		
		PlayerDTO playerDTO = new PlayerDTO();
		
		playerDTO.setPlayerId(player.getPlayerId());

	
		playerDTO.setUsername(player.getUsername());

	
		playerDTO.setEmail(player.getEmail());

	

		
		return playerDTO;
	}

	public ResultDTO updatePlayer(PlayerDTO playerDTO, RequestDTO requestDTO) {
		
		Player player = playerDao.getById(playerDTO.getPlayerId());

		player.setPlayerId(ControllerUtils.setValue(player.getPlayerId(), playerDTO.getPlayerId()));

		player.setUsername(ControllerUtils.setValue(player.getUsername(), playerDTO.getUsername()));

		player.setEmail(ControllerUtils.setValue(player.getEmail(), playerDTO.getEmail()));



        player = playerDao.save(player);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public PlayerDTO getPlayerDTOById(Integer playerId) {
	
		Player player = playerDao.getById(playerId);
			
		
		PlayerConvertCriteriaDTO convertCriteria = new PlayerConvertCriteriaDTO();
		return(this.convertPlayerToPlayerDTO(player,convertCriteria));
	}







}
