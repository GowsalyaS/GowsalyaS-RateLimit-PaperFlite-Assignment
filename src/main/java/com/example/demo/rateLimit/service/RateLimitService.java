package com.example.demo.rateLimit.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.rateLimit.exception.TooManyRequestException;
import com.example.demo.rateLimit.model.RateLimitModel;
import com.example.demo.rateLimit.repo.RateLimitRepo;

@Service
public class RateLimitService {
	
	private static final Logger LOG = LoggerFactory.getLogger(RateLimitService.class);
	
	@Autowired
	private RateLimitRepo repo;

	private static final LocalDateTime DATE_TIMING = LocalDateTime.now();

	private static final LocalTime TIMING = DATE_TIMING.toLocalTime();

	private static final LocalTime DESIRED_START_TIMING = LocalTime.parse("09:00:00",
			DateTimeFormatter.ofPattern("HH:mm:ss"));
	private static final LocalTime DESIRED_END_TIMING = LocalTime.parse("17:00:00",
			DateTimeFormatter.ofPattern("HH:mm:ss"));

	private int defaultLimit = 5;
	private int requestCount = 1;

	public List<String> getResponseNames(Integer id) {
		if (TIMING.isBefore(DESIRED_END_TIMING) && TIMING.isAfter(DESIRED_START_TIMING)) {
			return gettingNames(id);
		} else {
			LOG.error("Reached desired timelimit already");
			throw new TooManyRequestException("Sorry reached desired timelimit");
		}
	}

	private List<String> gettingNames(Integer id) {
		RateLimitModel model = repo.findById(id).orElse(null);
		List<String> names = null;
		if (null != model) {
			if (model.getWindowTime().before(Date.valueOf(LocalDate.now()))) {
				model.setRequestCount(0);
			}
			int currentCountValue = model.getRequestCount();
			model.setRequestCount(++currentCountValue);
			model.setWindowTime(Date.valueOf(LocalDate.now()));
			int requestCount = model.getRequestCount();
			if (requestCount <= defaultLimit) {
				names = Arrays.asList("Blossom", "ButterCup", "Bubbles", "Mojojojo");
			} else {
				
				LOG.error("API Limit has been reached already for the user id {}",id);
				throw new TooManyRequestException("you have reached request limit!!..");
			}
			repo.save(model);
			return names;
		} else {
			RateLimitModel newmodel = new RateLimitModel();
			newmodel.setUserId(id);
			newmodel.setRequestCount(requestCount);
			newmodel.setWindowTime(Date.valueOf(LocalDate.now()));
			repo.save(newmodel);
			names = Arrays.asList("Blossom", "ButterCup", "Bubbles", "Mojojojo");
			return names;
		}
	}

}
