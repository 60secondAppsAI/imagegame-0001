package com.imagegame.dao;

import java.util.List;

import com.imagegame.dao.GenericDAO;
import com.imagegame.domain.KeyWordPair;





public interface KeyWordPairDAO extends GenericDAO<KeyWordPair, Integer> {
  
	List<KeyWordPair> findAll();
	






}


