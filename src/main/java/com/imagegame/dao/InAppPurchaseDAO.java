package com.imagegame.dao;

import java.util.List;

import com.imagegame.dao.GenericDAO;
import com.imagegame.domain.InAppPurchase;





public interface InAppPurchaseDAO extends GenericDAO<InAppPurchase, Integer> {
  
	List<InAppPurchase> findAll();
	






}


