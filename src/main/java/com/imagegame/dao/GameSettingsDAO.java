package com.imagegame.dao;

import java.util.List;

import com.imagegame.dao.GenericDAO;
import com.imagegame.domain.GameSettings;





public interface GameSettingsDAO extends GenericDAO<GameSettings, Integer> {
  
	List<GameSettings> findAll();
	






}


