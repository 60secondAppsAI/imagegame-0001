package com.imagegame.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.imagegame.domain.Leaderboard;
import com.imagegame.dto.LeaderboardDTO;
import com.imagegame.dto.LeaderboardSearchDTO;
import com.imagegame.dto.LeaderboardPageDTO;
import com.imagegame.dto.LeaderboardConvertCriteriaDTO;
import com.imagegame.service.GenericService;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface LeaderboardService extends GenericService<Leaderboard, Integer> {

	List<Leaderboard> findAll();

	ResultDTO addLeaderboard(LeaderboardDTO leaderboardDTO, RequestDTO requestDTO);

	ResultDTO updateLeaderboard(LeaderboardDTO leaderboardDTO, RequestDTO requestDTO);

    Page<Leaderboard> getAllLeaderboards(Pageable pageable);

    Page<Leaderboard> getAllLeaderboards(Specification<Leaderboard> spec, Pageable pageable);

	ResponseEntity<LeaderboardPageDTO> getLeaderboards(LeaderboardSearchDTO leaderboardSearchDTO);
	
	List<LeaderboardDTO> convertLeaderboardsToLeaderboardDTOs(List<Leaderboard> leaderboards, LeaderboardConvertCriteriaDTO convertCriteria);

	LeaderboardDTO getLeaderboardDTOById(Integer leaderboardId);







}





