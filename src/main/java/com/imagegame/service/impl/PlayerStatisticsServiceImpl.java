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
import com.imagegame.dao.PlayerStatisticsDAO;
import com.imagegame.domain.PlayerStatistics;
import com.imagegame.dto.PlayerStatisticsDTO;
import com.imagegame.dto.PlayerStatisticsSearchDTO;
import com.imagegame.dto.PlayerStatisticsPageDTO;
import com.imagegame.dto.PlayerStatisticsConvertCriteriaDTO;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;
import com.imagegame.service.PlayerStatisticsService;
import com.imagegame.util.ControllerUtils;





@Service
public class PlayerStatisticsServiceImpl extends GenericServiceImpl<PlayerStatistics, Integer> implements PlayerStatisticsService {

    private final static Logger logger = LoggerFactory.getLogger(PlayerStatisticsServiceImpl.class);

	@Autowired
	PlayerStatisticsDAO playerStatisticsDao;

	


	@Override
	public GenericDAO<PlayerStatistics, Integer> getDAO() {
		return (GenericDAO<PlayerStatistics, Integer>) playerStatisticsDao;
	}
	
	public List<PlayerStatistics> findAll () {
		List<PlayerStatistics> playerStatisticss = playerStatisticsDao.findAll();
		
		return playerStatisticss;	
		
	}

	public ResultDTO addPlayerStatistics(PlayerStatisticsDTO playerStatisticsDTO, RequestDTO requestDTO) {

		PlayerStatistics playerStatistics = new PlayerStatistics();

		playerStatistics.setPlayerStatisticsId(playerStatisticsDTO.getPlayerStatisticsId());


		playerStatistics.setGamesPlayed(playerStatisticsDTO.getGamesPlayed());


		playerStatistics.setGamesWon(playerStatisticsDTO.getGamesWon());


		playerStatistics.setTotalScore(playerStatisticsDTO.getTotalScore());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		playerStatistics = playerStatisticsDao.save(playerStatistics);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<PlayerStatistics> getAllPlayerStatisticss(Pageable pageable) {
		return playerStatisticsDao.findAll(pageable);
	}

	public Page<PlayerStatistics> getAllPlayerStatisticss(Specification<PlayerStatistics> spec, Pageable pageable) {
		return playerStatisticsDao.findAll(spec, pageable);
	}

	public ResponseEntity<PlayerStatisticsPageDTO> getPlayerStatisticss(PlayerStatisticsSearchDTO playerStatisticsSearchDTO) {
	
			Integer playerStatisticsId = playerStatisticsSearchDTO.getPlayerStatisticsId(); 
   			Integer totalScore = playerStatisticsSearchDTO.getTotalScore(); 
 			String sortBy = playerStatisticsSearchDTO.getSortBy();
			String sortOrder = playerStatisticsSearchDTO.getSortOrder();
			String searchQuery = playerStatisticsSearchDTO.getSearchQuery();
			Integer page = playerStatisticsSearchDTO.getPage();
			Integer size = playerStatisticsSearchDTO.getSize();

	        Specification<PlayerStatistics> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, playerStatisticsId, "playerStatisticsId"); 
			
			
			
			spec = ControllerUtils.andIfNecessary(spec, totalScore, "totalScore"); 
			

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

		Page<PlayerStatistics> playerStatisticss = this.getAllPlayerStatisticss(spec, pageable);
		
		//System.out.println(String.valueOf(playerStatisticss.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(playerStatisticss.getTotalPages()));
		
		List<PlayerStatistics> playerStatisticssList = playerStatisticss.getContent();
		
		PlayerStatisticsConvertCriteriaDTO convertCriteria = new PlayerStatisticsConvertCriteriaDTO();
		List<PlayerStatisticsDTO> playerStatisticsDTOs = this.convertPlayerStatisticssToPlayerStatisticsDTOs(playerStatisticssList,convertCriteria);
		
		PlayerStatisticsPageDTO playerStatisticsPageDTO = new PlayerStatisticsPageDTO();
		playerStatisticsPageDTO.setPlayerStatisticss(playerStatisticsDTOs);
		playerStatisticsPageDTO.setTotalElements(playerStatisticss.getTotalElements());
		return ResponseEntity.ok(playerStatisticsPageDTO);
	}

	public List<PlayerStatisticsDTO> convertPlayerStatisticssToPlayerStatisticsDTOs(List<PlayerStatistics> playerStatisticss, PlayerStatisticsConvertCriteriaDTO convertCriteria) {
		
		List<PlayerStatisticsDTO> playerStatisticsDTOs = new ArrayList<PlayerStatisticsDTO>();
		
		for (PlayerStatistics playerStatistics : playerStatisticss) {
			playerStatisticsDTOs.add(convertPlayerStatisticsToPlayerStatisticsDTO(playerStatistics,convertCriteria));
		}
		
		return playerStatisticsDTOs;

	}
	
	public PlayerStatisticsDTO convertPlayerStatisticsToPlayerStatisticsDTO(PlayerStatistics playerStatistics, PlayerStatisticsConvertCriteriaDTO convertCriteria) {
		
		PlayerStatisticsDTO playerStatisticsDTO = new PlayerStatisticsDTO();
		
		playerStatisticsDTO.setPlayerStatisticsId(playerStatistics.getPlayerStatisticsId());

	
		playerStatisticsDTO.setGamesPlayed(playerStatistics.getGamesPlayed());

	
		playerStatisticsDTO.setGamesWon(playerStatistics.getGamesWon());

	
		playerStatisticsDTO.setTotalScore(playerStatistics.getTotalScore());

	

		
		return playerStatisticsDTO;
	}

	public ResultDTO updatePlayerStatistics(PlayerStatisticsDTO playerStatisticsDTO, RequestDTO requestDTO) {
		
		PlayerStatistics playerStatistics = playerStatisticsDao.getById(playerStatisticsDTO.getPlayerStatisticsId());

		playerStatistics.setPlayerStatisticsId(ControllerUtils.setValue(playerStatistics.getPlayerStatisticsId(), playerStatisticsDTO.getPlayerStatisticsId()));

		playerStatistics.setGamesPlayed(ControllerUtils.setValue(playerStatistics.getGamesPlayed(), playerStatisticsDTO.getGamesPlayed()));

		playerStatistics.setGamesWon(ControllerUtils.setValue(playerStatistics.getGamesWon(), playerStatisticsDTO.getGamesWon()));

		playerStatistics.setTotalScore(ControllerUtils.setValue(playerStatistics.getTotalScore(), playerStatisticsDTO.getTotalScore()));



        playerStatistics = playerStatisticsDao.save(playerStatistics);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public PlayerStatisticsDTO getPlayerStatisticsDTOById(Integer playerStatisticsId) {
	
		PlayerStatistics playerStatistics = playerStatisticsDao.getById(playerStatisticsId);
			
		
		PlayerStatisticsConvertCriteriaDTO convertCriteria = new PlayerStatisticsConvertCriteriaDTO();
		return(this.convertPlayerStatisticsToPlayerStatisticsDTO(playerStatistics,convertCriteria));
	}







}
