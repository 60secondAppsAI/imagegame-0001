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
@Table(name="key_word_pairs")
@Getter @Setter @NoArgsConstructor
public class KeyWordPair {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="key_word_pair_id")
	private Integer keyWordPairId;
    
  	@Column(name="word_one")
	private String wordOne;
    
  	@Column(name="word_two")
	private String wordTwo;
    
	




}
