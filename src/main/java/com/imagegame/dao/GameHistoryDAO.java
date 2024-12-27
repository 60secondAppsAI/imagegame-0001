package com.imagegame.dao;

import java.util.List;

import com.imagegame.dao.GenericDAO;
import com.imagegame.domain.GameHistory;





public interface GameHistoryDAO extends GenericDAO<GameHistory, Integer> {
  
	List<GameHistory> findAll();
	






}


