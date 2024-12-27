package com.imagegame.dao;

import java.util.List;

import com.imagegame.dao.GenericDAO;
import com.imagegame.domain.Challenge;





public interface ChallengeDAO extends GenericDAO<Challenge, Integer> {
  
	List<Challenge> findAll();
	






}


