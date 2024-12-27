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
import com.imagegame.dao.LeaderboardDAO;
import com.imagegame.domain.Leaderboard;
import com.imagegame.dto.LeaderboardDTO;
import com.imagegame.dto.LeaderboardSearchDTO;
import com.imagegame.dto.LeaderboardPageDTO;
import com.imagegame.dto.LeaderboardConvertCriteriaDTO;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;
import com.imagegame.service.LeaderboardService;
import com.imagegame.util.ControllerUtils;





@Service
public class LeaderboardServiceImpl extends GenericServiceImpl<Leaderboard, Integer> implements LeaderboardService {

    private final static Logger logger = LoggerFactory.getLogger(LeaderboardServiceImpl.class);

	@Autowired
	LeaderboardDAO leaderboardDao;

	


	@Override
	public GenericDAO<Leaderboard, Integer> getDAO() {
		return (GenericDAO<Leaderboard, Integer>) leaderboardDao;
	}
	
	public List<Leaderboard> findAll () {
		List<Leaderboard> leaderboards = leaderboardDao.findAll();
		
		return leaderboards;	
		
	}

	public ResultDTO addLeaderboard(LeaderboardDTO leaderboardDTO, RequestDTO requestDTO) {

		Leaderboard leaderboard = new Leaderboard();

		leaderboard.setLeaderboardId(leaderboardDTO.getLeaderboardId());


		leaderboard.setRank(leaderboardDTO.getRank());


		leaderboard.setHighScore(leaderboardDTO.getHighScore());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		leaderboard = leaderboardDao.save(leaderboard);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Leaderboard> getAllLeaderboards(Pageable pageable) {
		return leaderboardDao.findAll(pageable);
	}

	public Page<Leaderboard> getAllLeaderboards(Specification<Leaderboard> spec, Pageable pageable) {
		return leaderboardDao.findAll(spec, pageable);
	}

	public ResponseEntity<LeaderboardPageDTO> getLeaderboards(LeaderboardSearchDTO leaderboardSearchDTO) {
	
			Integer leaderboardId = leaderboardSearchDTO.getLeaderboardId(); 
  			Integer highScore = leaderboardSearchDTO.getHighScore(); 
 			String sortBy = leaderboardSearchDTO.getSortBy();
			String sortOrder = leaderboardSearchDTO.getSortOrder();
			String searchQuery = leaderboardSearchDTO.getSearchQuery();
			Integer page = leaderboardSearchDTO.getPage();
			Integer size = leaderboardSearchDTO.getSize();

	        Specification<Leaderboard> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, leaderboardId, "leaderboardId"); 
			
			
			spec = ControllerUtils.andIfNecessary(spec, highScore, "highScore"); 
			

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

		Page<Leaderboard> leaderboards = this.getAllLeaderboards(spec, pageable);
		
		//System.out.println(String.valueOf(leaderboards.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(leaderboards.getTotalPages()));
		
		List<Leaderboard> leaderboardsList = leaderboards.getContent();
		
		LeaderboardConvertCriteriaDTO convertCriteria = new LeaderboardConvertCriteriaDTO();
		List<LeaderboardDTO> leaderboardDTOs = this.convertLeaderboardsToLeaderboardDTOs(leaderboardsList,convertCriteria);
		
		LeaderboardPageDTO leaderboardPageDTO = new LeaderboardPageDTO();
		leaderboardPageDTO.setLeaderboards(leaderboardDTOs);
		leaderboardPageDTO.setTotalElements(leaderboards.getTotalElements());
		return ResponseEntity.ok(leaderboardPageDTO);
	}

	public List<LeaderboardDTO> convertLeaderboardsToLeaderboardDTOs(List<Leaderboard> leaderboards, LeaderboardConvertCriteriaDTO convertCriteria) {
		
		List<LeaderboardDTO> leaderboardDTOs = new ArrayList<LeaderboardDTO>();
		
		for (Leaderboard leaderboard : leaderboards) {
			leaderboardDTOs.add(convertLeaderboardToLeaderboardDTO(leaderboard,convertCriteria));
		}
		
		return leaderboardDTOs;

	}
	
	public LeaderboardDTO convertLeaderboardToLeaderboardDTO(Leaderboard leaderboard, LeaderboardConvertCriteriaDTO convertCriteria) {
		
		LeaderboardDTO leaderboardDTO = new LeaderboardDTO();
		
		leaderboardDTO.setLeaderboardId(leaderboard.getLeaderboardId());

	
		leaderboardDTO.setRank(leaderboard.getRank());

	
		leaderboardDTO.setHighScore(leaderboard.getHighScore());

	

		
		return leaderboardDTO;
	}

	public ResultDTO updateLeaderboard(LeaderboardDTO leaderboardDTO, RequestDTO requestDTO) {
		
		Leaderboard leaderboard = leaderboardDao.getById(leaderboardDTO.getLeaderboardId());

		leaderboard.setLeaderboardId(ControllerUtils.setValue(leaderboard.getLeaderboardId(), leaderboardDTO.getLeaderboardId()));

		leaderboard.setRank(ControllerUtils.setValue(leaderboard.getRank(), leaderboardDTO.getRank()));

		leaderboard.setHighScore(ControllerUtils.setValue(leaderboard.getHighScore(), leaderboardDTO.getHighScore()));



        leaderboard = leaderboardDao.save(leaderboard);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public LeaderboardDTO getLeaderboardDTOById(Integer leaderboardId) {
	
		Leaderboard leaderboard = leaderboardDao.getById(leaderboardId);
			
		
		LeaderboardConvertCriteriaDTO convertCriteria = new LeaderboardConvertCriteriaDTO();
		return(this.convertLeaderboardToLeaderboardDTO(leaderboard,convertCriteria));
	}







}
