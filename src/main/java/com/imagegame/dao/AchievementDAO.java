package com.imagegame.dao;

import java.util.List;

import com.imagegame.dao.GenericDAO;
import com.imagegame.domain.Achievement;





public interface AchievementDAO extends GenericDAO<Achievement, Integer> {
  
	List<Achievement> findAll();
	






}


