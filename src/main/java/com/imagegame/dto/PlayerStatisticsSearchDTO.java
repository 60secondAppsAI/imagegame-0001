package com.imagegame.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PlayerStatisticsSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer playerStatisticsId;
	
	private int gamesPlayed;
	
	private int gamesWon;
	
	private Integer totalScore;
	
}
