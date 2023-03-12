package com.example.demo.rateLimit.repo;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.rateLimit.model.RateLimitModel;

@Repository
public interface RateLimitRepo extends JpaRepository<RateLimitModel,Integer>{

	@Query("select  r.requestCount from RateLimitModel r where r.windowTime=?1 and r.userId=?2")
	int findByRequestCount(Date date,Integer id);

}
