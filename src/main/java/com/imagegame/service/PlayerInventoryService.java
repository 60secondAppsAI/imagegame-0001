package com.imagegame.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.imagegame.domain.PlayerInventory;
import com.imagegame.dto.PlayerInventoryDTO;
import com.imagegame.dto.PlayerInventorySearchDTO;
import com.imagegame.dto.PlayerInventoryPageDTO;
import com.imagegame.dto.PlayerInventoryConvertCriteriaDTO;
import com.imagegame.service.GenericService;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface PlayerInventoryService extends GenericService<PlayerInventory, Integer> {

	List<PlayerInventory> findAll();

	ResultDTO addPlayerInventory(PlayerInventoryDTO playerInventoryDTO, RequestDTO requestDTO);

	ResultDTO updatePlayerInventory(PlayerInventoryDTO playerInventoryDTO, RequestDTO requestDTO);

    Page<PlayerInventory> getAllPlayerInventorys(Pageable pageable);

    Page<PlayerInventory> getAllPlayerInventorys(Specification<PlayerInventory> spec, Pageable pageable);

	ResponseEntity<PlayerInventoryPageDTO> getPlayerInventorys(PlayerInventorySearchDTO playerInventorySearchDTO);
	
	List<PlayerInventoryDTO> convertPlayerInventorysToPlayerInventoryDTOs(List<PlayerInventory> playerInventorys, PlayerInventoryConvertCriteriaDTO convertCriteria);

	PlayerInventoryDTO getPlayerInventoryDTOById(Integer playerInventoryId);







}





