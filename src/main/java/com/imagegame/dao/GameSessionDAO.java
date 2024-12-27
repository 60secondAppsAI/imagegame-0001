package com.imagegame.dao;

import java.util.List;

import com.imagegame.dao.GenericDAO;
import com.imagegame.domain.GameSession;





public interface GameSessionDAO extends GenericDAO<GameSession, Integer> {
  
	List<GameSession> findAll();
	






}


