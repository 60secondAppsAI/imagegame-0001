package com.imagegame.dao;

import java.util.List;

import com.imagegame.dao.GenericDAO;
import com.imagegame.domain.ItemStore;





public interface ItemStoreDAO extends GenericDAO<ItemStore, Integer> {
  
	List<ItemStore> findAll();
	






}


