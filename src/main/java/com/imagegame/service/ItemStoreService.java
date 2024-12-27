package com.imagegame.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.imagegame.domain.ItemStore;
import com.imagegame.dto.ItemStoreDTO;
import com.imagegame.dto.ItemStoreSearchDTO;
import com.imagegame.dto.ItemStorePageDTO;
import com.imagegame.dto.ItemStoreConvertCriteriaDTO;
import com.imagegame.service.GenericService;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface ItemStoreService extends GenericService<ItemStore, Integer> {

	List<ItemStore> findAll();

	ResultDTO addItemStore(ItemStoreDTO itemStoreDTO, RequestDTO requestDTO);

	ResultDTO updateItemStore(ItemStoreDTO itemStoreDTO, RequestDTO requestDTO);

    Page<ItemStore> getAllItemStores(Pageable pageable);

    Page<ItemStore> getAllItemStores(Specification<ItemStore> spec, Pageable pageable);

	ResponseEntity<ItemStorePageDTO> getItemStores(ItemStoreSearchDTO itemStoreSearchDTO);
	
	List<ItemStoreDTO> convertItemStoresToItemStoreDTOs(List<ItemStore> itemStores, ItemStoreConvertCriteriaDTO convertCriteria);

	ItemStoreDTO getItemStoreDTOById(Integer itemStoreId);







}





