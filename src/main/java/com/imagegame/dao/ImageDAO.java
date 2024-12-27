package com.imagegame.dao;

import java.util.List;

import com.imagegame.dao.GenericDAO;
import com.imagegame.domain.Image;





public interface ImageDAO extends GenericDAO<Image, Integer> {
  
	List<Image> findAll();
	






}


