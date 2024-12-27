package com.imagegame.dao;

import java.util.List;

import com.imagegame.dao.GenericDAO;
import com.imagegame.domain.PlayerInventory;





public interface PlayerInventoryDAO extends GenericDAO<PlayerInventory, Integer> {
  
	List<PlayerInventory> findAll();
	






}


