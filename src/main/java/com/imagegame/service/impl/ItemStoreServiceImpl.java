package com.imagegame.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;



import com.imagegame.dao.GenericDAO;
import com.imagegame.service.GenericService;
import com.imagegame.service.impl.GenericServiceImpl;
import com.imagegame.dao.ItemStoreDAO;
import com.imagegame.domain.ItemStore;
import com.imagegame.dto.ItemStoreDTO;
import com.imagegame.dto.ItemStoreSearchDTO;
import com.imagegame.dto.ItemStorePageDTO;
import com.imagegame.dto.ItemStoreConvertCriteriaDTO;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;
import com.imagegame.service.ItemStoreService;
import com.imagegame.util.ControllerUtils;





@Service
public class ItemStoreServiceImpl extends GenericServiceImpl<ItemStore, Integer> implements ItemStoreService {

    private final static Logger logger = LoggerFactory.getLogger(ItemStoreServiceImpl.class);

	@Autowired
	ItemStoreDAO itemStoreDao;

	


	@Override
	public GenericDAO<ItemStore, Integer> getDAO() {
		return (GenericDAO<ItemStore, Integer>) itemStoreDao;
	}
	
	public List<ItemStore> findAll () {
		List<ItemStore> itemStores = itemStoreDao.findAll();
		
		return itemStores;	
		
	}

	public ResultDTO addItemStore(ItemStoreDTO itemStoreDTO, RequestDTO requestDTO) {

		ItemStore itemStore = new ItemStore();

		itemStore.setItemStoreId(itemStoreDTO.getItemStoreId());


		itemStore.setItemName(itemStoreDTO.getItemName());


		itemStore.setItemPrice(itemStoreDTO.getItemPrice());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		itemStore = itemStoreDao.save(itemStore);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<ItemStore> getAllItemStores(Pageable pageable) {
		return itemStoreDao.findAll(pageable);
	}

	public Page<ItemStore> getAllItemStores(Specification<ItemStore> spec, Pageable pageable) {
		return itemStoreDao.findAll(spec, pageable);
	}

	public ResponseEntity<ItemStorePageDTO> getItemStores(ItemStoreSearchDTO itemStoreSearchDTO) {
	
			Integer itemStoreId = itemStoreSearchDTO.getItemStoreId(); 
 			String itemName = itemStoreSearchDTO.getItemName(); 
  			String sortBy = itemStoreSearchDTO.getSortBy();
			String sortOrder = itemStoreSearchDTO.getSortOrder();
			String searchQuery = itemStoreSearchDTO.getSearchQuery();
			Integer page = itemStoreSearchDTO.getPage();
			Integer size = itemStoreSearchDTO.getSize();

	        Specification<ItemStore> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, itemStoreId, "itemStoreId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, itemName, "itemName"); 
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("itemName")), "%" + searchQuery.toLowerCase() + "%") 
		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<ItemStore> itemStores = this.getAllItemStores(spec, pageable);
		
		//System.out.println(String.valueOf(itemStores.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(itemStores.getTotalPages()));
		
		List<ItemStore> itemStoresList = itemStores.getContent();
		
		ItemStoreConvertCriteriaDTO convertCriteria = new ItemStoreConvertCriteriaDTO();
		List<ItemStoreDTO> itemStoreDTOs = this.convertItemStoresToItemStoreDTOs(itemStoresList,convertCriteria);
		
		ItemStorePageDTO itemStorePageDTO = new ItemStorePageDTO();
		itemStorePageDTO.setItemStores(itemStoreDTOs);
		itemStorePageDTO.setTotalElements(itemStores.getTotalElements());
		return ResponseEntity.ok(itemStorePageDTO);
	}

	public List<ItemStoreDTO> convertItemStoresToItemStoreDTOs(List<ItemStore> itemStores, ItemStoreConvertCriteriaDTO convertCriteria) {
		
		List<ItemStoreDTO> itemStoreDTOs = new ArrayList<ItemStoreDTO>();
		
		for (ItemStore itemStore : itemStores) {
			itemStoreDTOs.add(convertItemStoreToItemStoreDTO(itemStore,convertCriteria));
		}
		
		return itemStoreDTOs;

	}
	
	public ItemStoreDTO convertItemStoreToItemStoreDTO(ItemStore itemStore, ItemStoreConvertCriteriaDTO convertCriteria) {
		
		ItemStoreDTO itemStoreDTO = new ItemStoreDTO();
		
		itemStoreDTO.setItemStoreId(itemStore.getItemStoreId());

	
		itemStoreDTO.setItemName(itemStore.getItemName());

	
		itemStoreDTO.setItemPrice(itemStore.getItemPrice());

	

		
		return itemStoreDTO;
	}

	public ResultDTO updateItemStore(ItemStoreDTO itemStoreDTO, RequestDTO requestDTO) {
		
		ItemStore itemStore = itemStoreDao.getById(itemStoreDTO.getItemStoreId());

		itemStore.setItemStoreId(ControllerUtils.setValue(itemStore.getItemStoreId(), itemStoreDTO.getItemStoreId()));

		itemStore.setItemName(ControllerUtils.setValue(itemStore.getItemName(), itemStoreDTO.getItemName()));

		itemStore.setItemPrice(ControllerUtils.setValue(itemStore.getItemPrice(), itemStoreDTO.getItemPrice()));



        itemStore = itemStoreDao.save(itemStore);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public ItemStoreDTO getItemStoreDTOById(Integer itemStoreId) {
	
		ItemStore itemStore = itemStoreDao.getById(itemStoreId);
			
		
		ItemStoreConvertCriteriaDTO convertCriteria = new ItemStoreConvertCriteriaDTO();
		return(this.convertItemStoreToItemStoreDTO(itemStore,convertCriteria));
	}







}
