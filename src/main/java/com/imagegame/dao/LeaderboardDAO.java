package com.imagegame.dao;

import java.util.List;

import com.imagegame.dao.GenericDAO;
import com.imagegame.domain.Leaderboard;





public interface LeaderboardDAO extends GenericDAO<Leaderboard, Integer> {
  
	List<Leaderboard> findAll();
	






}


