package com.example.demo.rateLimit.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "rateLimitTable")
@Data
public class RateLimitModel {

	// @Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	// private int sNo;
	@Id
	private int userId;
	
	private Date windowTime;

	private int requestCount;

}
