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
@Table(name="player_statisticss")
@Getter @Setter @NoArgsConstructor
public class PlayerStatistics {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="player_statistics_id")
	private Integer playerStatisticsId;
    
  	@Column(name="games_played")
	private int gamesPlayed;
    
  	@Column(name="games_won")
	private int gamesWon;
    
  	@Column(name="total_score")
	private Integer totalScore;
    
	




}
