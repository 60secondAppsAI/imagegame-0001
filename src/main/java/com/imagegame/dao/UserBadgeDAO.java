package com.imagegame.dao;

import java.util.List;

import com.imagegame.dao.GenericDAO;
import com.imagegame.domain.UserBadge;





public interface UserBadgeDAO extends GenericDAO<UserBadge, Integer> {
  
	List<UserBadge> findAll();
	






}


