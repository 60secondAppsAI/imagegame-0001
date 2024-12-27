package com.imagegame.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.imagegame.domain.PlayerStatistics;
import com.imagegame.dto.PlayerStatisticsDTO;
import com.imagegame.dto.PlayerStatisticsSearchDTO;
import com.imagegame.dto.PlayerStatisticsPageDTO;
import com.imagegame.dto.PlayerStatisticsConvertCriteriaDTO;
import com.imagegame.service.GenericService;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface PlayerStatisticsService extends GenericService<PlayerStatistics, Integer> {

	List<PlayerStatistics> findAll();

	ResultDTO addPlayerStatistics(PlayerStatisticsDTO playerStatisticsDTO, RequestDTO requestDTO);

	ResultDTO updatePlayerStatistics(PlayerStatisticsDTO playerStatisticsDTO, RequestDTO requestDTO);

    Page<PlayerStatistics> getAllPlayerStatisticss(Pageable pageable);

    Page<PlayerStatistics> getAllPlayerStatisticss(Specification<PlayerStatistics> spec, Pageable pageable);

	ResponseEntity<PlayerStatisticsPageDTO> getPlayerStatisticss(PlayerStatisticsSearchDTO playerStatisticsSearchDTO);
	
	List<PlayerStatisticsDTO> convertPlayerStatisticssToPlayerStatisticsDTOs(List<PlayerStatistics> playerStatisticss, PlayerStatisticsConvertCriteriaDTO convertCriteria);

	PlayerStatisticsDTO getPlayerStatisticsDTOById(Integer playerStatisticsId);







}





