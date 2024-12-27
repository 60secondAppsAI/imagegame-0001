package com.imagegame.dao;

import java.util.List;

import com.imagegame.dao.GenericDAO;
import com.imagegame.domain.Badge;





public interface BadgeDAO extends GenericDAO<Badge, Integer> {
  
	List<Badge> findAll();
	






}


