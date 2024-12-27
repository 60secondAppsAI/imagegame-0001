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
import com.imagegame.dao.PlayerInventoryDAO;
import com.imagegame.domain.PlayerInventory;
import com.imagegame.dto.PlayerInventoryDTO;
import com.imagegame.dto.PlayerInventorySearchDTO;
import com.imagegame.dto.PlayerInventoryPageDTO;
import com.imagegame.dto.PlayerInventoryConvertCriteriaDTO;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;
import com.imagegame.service.PlayerInventoryService;
import com.imagegame.util.ControllerUtils;





@Service
public class PlayerInventoryServiceImpl extends GenericServiceImpl<PlayerInventory, Integer> implements PlayerInventoryService {

    private final static Logger logger = LoggerFactory.getLogger(PlayerInventoryServiceImpl.class);

	@Autowired
	PlayerInventoryDAO playerInventoryDao;

	


	@Override
	public GenericDAO<PlayerInventory, Integer> getDAO() {
		return (GenericDAO<PlayerInventory, Integer>) playerInventoryDao;
	}
	
	public List<PlayerInventory> findAll () {
		List<PlayerInventory> playerInventorys = playerInventoryDao.findAll();
		
		return playerInventorys;	
		
	}

	public ResultDTO addPlayerInventory(PlayerInventoryDTO playerInventoryDTO, RequestDTO requestDTO) {

		PlayerInventory playerInventory = new PlayerInventory();

		playerInventory.setPlayerInventoryId(playerInventoryDTO.getPlayerInventoryId());


		playerInventory.setQuantity(playerInventoryDTO.getQuantity());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		playerInventory = playerInventoryDao.save(playerInventory);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<PlayerInventory> getAllPlayerInventorys(Pageable pageable) {
		return playerInventoryDao.findAll(pageable);
	}

	public Page<PlayerInventory> getAllPlayerInventorys(Specification<PlayerInventory> spec, Pageable pageable) {
		return playerInventoryDao.findAll(spec, pageable);
	}

	public ResponseEntity<PlayerInventoryPageDTO> getPlayerInventorys(PlayerInventorySearchDTO playerInventorySearchDTO) {
	
			Integer playerInventoryId = playerInventorySearchDTO.getPlayerInventoryId(); 
  			String sortBy = playerInventorySearchDTO.getSortBy();
			String sortOrder = playerInventorySearchDTO.getSortOrder();
			String searchQuery = playerInventorySearchDTO.getSearchQuery();
			Integer page = playerInventorySearchDTO.getPage();
			Integer size = playerInventorySearchDTO.getSize();

	        Specification<PlayerInventory> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, playerInventoryId, "playerInventoryId"); 
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

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

		Page<PlayerInventory> playerInventorys = this.getAllPlayerInventorys(spec, pageable);
		
		//System.out.println(String.valueOf(playerInventorys.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(playerInventorys.getTotalPages()));
		
		List<PlayerInventory> playerInventorysList = playerInventorys.getContent();
		
		PlayerInventoryConvertCriteriaDTO convertCriteria = new PlayerInventoryConvertCriteriaDTO();
		List<PlayerInventoryDTO> playerInventoryDTOs = this.convertPlayerInventorysToPlayerInventoryDTOs(playerInventorysList,convertCriteria);
		
		PlayerInventoryPageDTO playerInventoryPageDTO = new PlayerInventoryPageDTO();
		playerInventoryPageDTO.setPlayerInventorys(playerInventoryDTOs);
		playerInventoryPageDTO.setTotalElements(playerInventorys.getTotalElements());
		return ResponseEntity.ok(playerInventoryPageDTO);
	}

	public List<PlayerInventoryDTO> convertPlayerInventorysToPlayerInventoryDTOs(List<PlayerInventory> playerInventorys, PlayerInventoryConvertCriteriaDTO convertCriteria) {
		
		List<PlayerInventoryDTO> playerInventoryDTOs = new ArrayList<PlayerInventoryDTO>();
		
		for (PlayerInventory playerInventory : playerInventorys) {
			playerInventoryDTOs.add(convertPlayerInventoryToPlayerInventoryDTO(playerInventory,convertCriteria));
		}
		
		return playerInventoryDTOs;

	}
	
	public PlayerInventoryDTO convertPlayerInventoryToPlayerInventoryDTO(PlayerInventory playerInventory, PlayerInventoryConvertCriteriaDTO convertCriteria) {
		
		PlayerInventoryDTO playerInventoryDTO = new PlayerInventoryDTO();
		
		playerInventoryDTO.setPlayerInventoryId(playerInventory.getPlayerInventoryId());

	
		playerInventoryDTO.setQuantity(playerInventory.getQuantity());

	

		
		return playerInventoryDTO;
	}

	public ResultDTO updatePlayerInventory(PlayerInventoryDTO playerInventoryDTO, RequestDTO requestDTO) {
		
		PlayerInventory playerInventory = playerInventoryDao.getById(playerInventoryDTO.getPlayerInventoryId());

		playerInventory.setPlayerInventoryId(ControllerUtils.setValue(playerInventory.getPlayerInventoryId(), playerInventoryDTO.getPlayerInventoryId()));

		playerInventory.setQuantity(ControllerUtils.setValue(playerInventory.getQuantity(), playerInventoryDTO.getQuantity()));



        playerInventory = playerInventoryDao.save(playerInventory);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public PlayerInventoryDTO getPlayerInventoryDTOById(Integer playerInventoryId) {
	
		PlayerInventory playerInventory = playerInventoryDao.getById(playerInventoryId);
			
		
		PlayerInventoryConvertCriteriaDTO convertCriteria = new PlayerInventoryConvertCriteriaDTO();
		return(this.convertPlayerInventoryToPlayerInventoryDTO(playerInventory,convertCriteria));
	}







}
