package com.imagegame.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class GameSessionDTO {

	private Integer gameSessionId;

	private Integer playerId;

	private Date startTime;

	private Date endTime;






}
