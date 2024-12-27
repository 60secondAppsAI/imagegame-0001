package com.imagegame.dao;

import java.util.List;

import com.imagegame.dao.GenericDAO;
import com.imagegame.domain.ChallengeCompletion;





public interface ChallengeCompletionDAO extends GenericDAO<ChallengeCompletion, Integer> {
  
	List<ChallengeCompletion> findAll();
	






}


