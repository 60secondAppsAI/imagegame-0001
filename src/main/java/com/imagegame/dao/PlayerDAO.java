package com.imagegame.dao;

import java.util.List;

import com.imagegame.dao.GenericDAO;
import com.imagegame.domain.Player;





public interface PlayerDAO extends GenericDAO<Player, Integer> {
  
	List<Player> findAll();
	






}


