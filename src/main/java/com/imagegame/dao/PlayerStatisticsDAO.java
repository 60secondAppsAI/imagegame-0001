package com.imagegame.dao;

import java.util.List;

import com.imagegame.dao.GenericDAO;
import com.imagegame.domain.PlayerStatistics;





public interface PlayerStatisticsDAO extends GenericDAO<PlayerStatistics, Integer> {
  
	List<PlayerStatistics> findAll();
	






}


