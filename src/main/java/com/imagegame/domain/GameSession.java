package com.imagegame.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.time.Year;
import jakarta.persistence.Transient;



@Entity
@Table(name="game_sessions")
@Getter @Setter @NoArgsConstructor
public class GameSession {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="game_session_id")
	private Integer gameSessionId;
    
  	@Column(name="player_id")
	private Integer playerId;
    
  	@Column(name="start_time")
	private Date startTime;
    
  	@Column(name="end_time")
	private Date endTime;
    
	




}
