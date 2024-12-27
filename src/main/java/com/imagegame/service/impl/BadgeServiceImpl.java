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
import com.imagegame.dao.BadgeDAO;
import com.imagegame.domain.Badge;
import com.imagegame.dto.BadgeDTO;
import com.imagegame.dto.BadgeSearchDTO;
import com.imagegame.dto.BadgePageDTO;
import com.imagegame.dto.BadgeConvertCriteriaDTO;
import com.imagegame.dto.common.RequestDTO;
import com.imagegame.dto.common.ResultDTO;
import com.imagegame.service.BadgeService;
import com.imagegame.util.ControllerUtils;





@Service
public class BadgeServiceImpl extends GenericServiceImpl<Badge, Integer> implements BadgeService {

    private final static Logger logger = LoggerFactory.getLogger(BadgeServiceImpl.class);

	@Autowired
	BadgeDAO badgeDao;

	


	@Override
	public GenericDAO<Badge, Integer> getDAO() {
		return (GenericDAO<Badge, Integer>) badgeDao;
	}
	
	public List<Badge> findAll () {
		List<Badge> badges = badgeDao.findAll();
		
		return badges;	
		
	}

	public ResultDTO addBadge(BadgeDTO badgeDTO, RequestDTO requestDTO) {

		Badge badge = new Badge();

		badge.setBadgeId(badgeDTO.getBadgeId());


		badge.setName(badgeDTO.getName());


		badge.setIconUrl(badgeDTO.getIconUrl());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		badge = badgeDao.save(badge);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Badge> getAllBadges(Pageable pageable) {
		return badgeDao.findAll(pageable);
	}

	public Page<Badge> getAllBadges(Specification<Badge> spec, Pageable pageable) {
		return badgeDao.findAll(spec, pageable);
	}

	public ResponseEntity<BadgePageDTO> getBadges(BadgeSearchDTO badgeSearchDTO) {
	
			Integer badgeId = badgeSearchDTO.getBadgeId(); 
 			String name = badgeSearchDTO.getName(); 
 			String iconUrl = badgeSearchDTO.getIconUrl(); 
 			String sortBy = badgeSearchDTO.getSortBy();
			String sortOrder = badgeSearchDTO.getSortOrder();
			String searchQuery = badgeSearchDTO.getSearchQuery();
			Integer page = badgeSearchDTO.getPage();
			Integer size = badgeSearchDTO.getSize();

	        Specification<Badge> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, badgeId, "badgeId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
			spec = ControllerUtils.andIfNecessary(spec, iconUrl, "iconUrl"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("iconUrl")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Badge> badges = this.getAllBadges(spec, pageable);
		
		//System.out.println(String.valueOf(badges.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(badges.getTotalPages()));
		
		List<Badge> badgesList = badges.getContent();
		
		BadgeConvertCriteriaDTO convertCriteria = new BadgeConvertCriteriaDTO();
		List<BadgeDTO> badgeDTOs = this.convertBadgesToBadgeDTOs(badgesList,convertCriteria);
		
		BadgePageDTO badgePageDTO = new BadgePageDTO();
		badgePageDTO.setBadges(badgeDTOs);
		badgePageDTO.setTotalElements(badges.getTotalElements());
		return ResponseEntity.ok(badgePageDTO);
	}

	public List<BadgeDTO> convertBadgesToBadgeDTOs(List<Badge> badges, BadgeConvertCriteriaDTO convertCriteria) {
		
		List<BadgeDTO> badgeDTOs = new ArrayList<BadgeDTO>();
		
		for (Badge badge : badges) {
			badgeDTOs.add(convertBadgeToBadgeDTO(badge,convertCriteria));
		}
		
		return badgeDTOs;

	}
	
	public BadgeDTO convertBadgeToBadgeDTO(Badge badge, BadgeConvertCriteriaDTO convertCriteria) {
		
		BadgeDTO badgeDTO = new BadgeDTO();
		
		badgeDTO.setBadgeId(badge.getBadgeId());

	
		badgeDTO.setName(badge.getName());

	
		badgeDTO.setIconUrl(badge.getIconUrl());

	

		
		return badgeDTO;
	}

	public ResultDTO updateBadge(BadgeDTO badgeDTO, RequestDTO requestDTO) {
		
		Badge badge = badgeDao.getById(badgeDTO.getBadgeId());

		badge.setBadgeId(ControllerUtils.setValue(badge.getBadgeId(), badgeDTO.getBadgeId()));

		badge.setName(ControllerUtils.setValue(badge.getName(), badgeDTO.getName()));

		badge.setIconUrl(ControllerUtils.setValue(badge.getIconUrl(), badgeDTO.getIconUrl()));



        badge = badgeDao.save(badge);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public BadgeDTO getBadgeDTOById(Integer badgeId) {
	
		Badge badge = badgeDao.getById(badgeId);
			
		
		BadgeConvertCriteriaDTO convertCriteria = new BadgeConvertCriteriaDTO();
		return(this.convertBadgeToBadgeDTO(badge,convertCriteria));
	}







}
