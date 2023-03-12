package com.example.demo.rateLimit.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.rateLimit.exception.TooManyRequestException;
import com.example.demo.rateLimit.service.RateLimitService;

@RestController
public class RateLimitController {

	private static final Logger LOG = LoggerFactory.getLogger(RateLimitController.class);

	@Autowired
	private RateLimitService service;

	private int count = 0;

	@Value("${request.api.count}")
	private int apiRate;

	@GetMapping("/getNames")
	public List<String> getResponse(@RequestParam("id") Integer id) {
		if (count < apiRate) {
			try {
				List<String> response = service.getResponseNames(id);
				count++;
				return response;
			} catch (TooManyRequestException ex) {
				LOG.error("{}",ex.getMsg());
				throw new TooManyRequestException(ex.getMsg());
			}
		} else {
			LOG.error("API Limit has been reached already");
			throw new TooManyRequestException("Sry ,already reached api rate limit...!");
		}
	}

}
