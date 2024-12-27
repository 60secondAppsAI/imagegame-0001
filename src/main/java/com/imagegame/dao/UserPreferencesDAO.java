package com.imagegame.dao;

import java.util.List;

import com.imagegame.dao.GenericDAO;
import com.imagegame.domain.UserPreferences;





public interface UserPreferencesDAO extends GenericDAO<UserPreferences, Integer> {
  
	List<UserPreferences> findAll();
	






}


